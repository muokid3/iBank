package com.dm.berxley.ibank.core.presentation.util

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.dm.berxley.ibank.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID

class FirebaseAuthHelper(
    private val context: Context,
) {
    private val auth = Firebase.auth

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
    fun googleSignIn(): Flow<Result<AuthResult>> {
        val firebaseAuth = FirebaseAuth.getInstance()
        return callbackFlow {
            try {
                // Initialize Credential Manager
                val credentialManager: CredentialManager = CredentialManager.create(context)

                // Generate a nonce (a random number used once)
                val ranNonce: String = UUID.randomUUID().toString()
                val bytes: ByteArray = ranNonce.toByteArray()
                val md: MessageDigest = MessageDigest.getInstance("SHA-256")
                val digest: ByteArray = md.digest(bytes)
                val hashedNonce: String = digest.fold("") { str, it -> str + "%02x".format(it) }

                // Set up Google ID option
                val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .setNonce(hashedNonce)
                    .build()


                // Request credentials
                val request: GetCredentialRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                // Get the credential result
                val result = credentialManager.getCredential(context, request)
                val credential = result.credential

                // Check if the received credential is a valid Google ID Token
                if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    val authCredential =
                        GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                    val authResult = firebaseAuth.signInWithCredential(authCredential).await()
                    trySend(Result.success(authResult))
                } else {
                    throw RuntimeException("Received an invalid credential type")
                }
            } catch (e: GetCredentialCancellationException) {
                trySend(Result.failure(Exception("Sign-in was canceled. Please try again.")))

            } catch (e: Exception) {
                trySend(Result.failure(e))
            }
            awaitClose { }
        }
    }
}