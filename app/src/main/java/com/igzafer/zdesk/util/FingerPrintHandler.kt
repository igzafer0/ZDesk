package com.igzafer.zdesk.util

import android.content.Context
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.os.Handler
import android.widget.Toast
import androidx.annotation.RequiresApi
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.M)
class FingerPrintHandler(private val context: Context, private val handler: IAuthHandler) :
    FingerprintManager.AuthenticationCallback() {
    private lateinit var managerMain: FingerprintManager
    private var cryptoObjectMain: FingerprintManager.CryptoObject? = null
    public fun startAuth(
        manager: FingerprintManager,
        cryptoObject: FingerprintManager.CryptoObject?
    ) {
        Timber.d("sdafsfas")
        val cancellationSignal = CancellationSignal()
        managerMain=manager
        cryptoObjectMain=cryptoObject
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
    }


    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        this.update(false)
    }

    private fun update(b: Boolean) {
        if (!b) {
            handler.onError()
            this.startAuth(managerMain,cryptoObjectMain)
        } else {
            handler.success()
        }
    }

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {

        this.update( true)

    }

    override fun onAuthenticationFailed() {
        this.update( false)
    }
}