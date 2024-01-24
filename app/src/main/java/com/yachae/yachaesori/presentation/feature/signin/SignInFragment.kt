package com.yachae.yachaesori.presentation.feature.signin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.yachae.yachaesori.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private var _fragmentSignInBinding: FragmentSignInBinding? = null
    private val fragmentSignInBinding get() = _fragmentSignInBinding!!

    private val signInViewModel: SignInViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _fragmentSignInBinding = FragmentSignInBinding.inflate(layoutInflater)
        return fragmentSignInBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentSignInBinding.run {
            kakaoSigninButton.setOnClickListener {
                signInWithKakao()
            }
        }
    }

    private fun signInWithKakao() {
        // 카카오톡 어플이 있으면 카톡으로 로그인, 없으면 카카오 계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {

            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { kakaoToken, error ->
                Log.d("카카오", "카톡으로 로그인")
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    loginWithKaKaoAccount(requireContext())
                } else if (kakaoToken != null) {
                    signInViewModel.signInWithYachae(kakaoToken.accessToken)
//                    getCustomToken(token.accessToken)
                }
            }
        } else {

            loginWithKaKaoAccount(requireContext())
        }
    }

    // 카카오 계정으로 로그인
    private fun loginWithKaKaoAccount(context: Context) {
        Log.d("카카오", "카카오 계정으로 로그인")

        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {
                Log.e("카카오", "로그인 실패", error)
            } else if (token != null) {
                Log.i("카카오", "로그인 성공 ${token.accessToken}")
                signInViewModel.signInWithYachae(token.accessToken)
            }
        }
    }

}