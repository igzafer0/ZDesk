package com.igzafer.zdesk.presentation.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context.FINGERPRINT_SERVICE
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.igzafer.zdesk.R
import com.igzafer.zdesk.databinding.FragmentFingerPrintPageBinding
import com.igzafer.zdesk.util.FingerPrintHandler
import com.igzafer.zdesk.util.IAuthHandler
import java.util.*
import java.util.concurrent.Executor


class FingerPrintPage : Fragment() {


    private lateinit var binding: FragmentFingerPrintPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFingerPrintPageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    private lateinit var biometricPromptInfo: androidx.biometric.BiometricPrompt.PromptInfo
    private val KEY_NAME = "keyname"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val fingerprintManager =
            activity?.getSystemService(FINGERPRINT_SERVICE) as FingerprintManager

        FingerPrintHandler(requireContext(), object : IAuthHandler {
            override fun success() {
                binding.idTv.setText("Hoş geldin Zafer Çetin")
                binding.greenDot.visibility=View.VISIBLE
                val pvhX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 100f)
                val pvhY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 100f)
                val scaleAnimation: ObjectAnimator =
                    ObjectAnimator.ofPropertyValuesHolder(binding.greenDot, pvhX, pvhY)

                val setAnimation = AnimatorSet()

                setAnimation.play(scaleAnimation)
                setAnimation.duration = 250
                setAnimation.start()
            }


            override fun onError() {
                binding.idTv.setText("Tanımlanamayan Kimlik!")
                binding.redDot.visibility=View.VISIBLE
                val pvhX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 100f)
                val pvhY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 100f)
                val scaleAnimation: ObjectAnimator =
                    ObjectAnimator.ofPropertyValuesHolder(binding.redDot, pvhX, pvhY)

                val setAnimation = AnimatorSet()

                setAnimation.play(scaleAnimation)
                setAnimation.duration = 250
                setAnimation.start()
            }

        }).startAuth(fingerprintManager, null)


    }


}