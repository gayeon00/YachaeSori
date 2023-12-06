package com.yachae.yachaesori.presentation.feature.signin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.yachae.yachaesori.MainActivity
import com.yachae.yachaesori.data.repository.SignInRepository
import com.yachae.yachaesori.databinding.FragmentSignInBinding
import com.yachae.yachaesori.domain.usecase.SignInUserUseCase

class SignInFragment : Fragment() {
    private var _fragmentSignInBinding: FragmentSignInBinding? = null
    private val fragmentSignInBinding get() = _fragmentSignInBinding!!

    private val signInViewModel: SignInViewModel by viewModels {
        SignInViewModelFactory(
            SignInUserUseCase(SignInRepository())
        )
    }

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
            kakaoSignInButton.setOnClickListener {
                signInWithKakao()
            }
        }

        signInViewModel.signInResult.observe(viewLifecycleOwner) {
            //UI 업데이트 로직
            it.user?.let { it1 ->
                (activity as MainActivity).showSnackBar(it1.uid)
            }
        }
    }

    private fun signInWithKakao() {
        // 카카오톡 어플이 있으면 카톡으로 로그인, 없으면 카카오 계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {

            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                Log.d("카카오", "카톡으로 로그인")
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    loginWithKaKaoAccount(requireContext())
                } else if (token != null) {
                    signInViewModel.signInWithYachae(token.accessToken)
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
        UserApiClient.instance.loginWithKakaoAccount(context) { token: OAuthToken?, error: Throwable? ->
            if (error != null) {
                Log.e("카카오", "로그인 실패", error)
            } else if (token != null) {
                signInViewModel.signInWithYachae(token.accessToken)
//                getCustomToken(token.accessToken)
            }
        }
    }

    // firebase functions에 배포한 kakaoCustomAuth 호출
    /*private fun getCustomToken(accessToken: String) {

        Log.d("카카오", "커스텀 토큰 받아오기")
        val functions: FirebaseFunctions = Firebase.functions("asia-northeast3")

        val data = hashMapOf(
            "token" to accessToken
        )

        functions
            .getHttpsCallable("kakaoCustomAuth")
            .call(data)
            .addOnCompleteListener { task ->
                try {
                    // 호출 성공
                    val result = task.result?.data as HashMap<*, *>
                    Log.d("카카오", "호출 성공")
                    var mKey: String? = null
                    for (key in result.keys) {
                        mKey = key.toString()
                    }
                    val customToken = result[mKey!!].toString()

                    // 호출 성공해서 반환받은 커스텀 토큰으로 Firebase Authentication 인증받기
                    firebaseAuthWithKakao(customToken)
                } catch (e: RuntimeExecutionException) {
                    // 호출 실패
                    Log.d("카카오", "호출 실패")
                    Snackbar.make(requireView(), "로그인에 실패하였습니다. 다시 시도해주세요.", Snackbar.LENGTH_SHORT)
                        .show()
                    Log.d("카카오", e.message!!)
                }
            }
    }*/

    /*private fun firebaseAuthWithKakao(customToken: String) {
        Log.d("카카오", "Firebase authentication 받아오기")
        auth.signInWithCustomToken(customToken).addOnCompleteListener { result ->
            if (result.isSuccessful) {
//                updateUI(auth.currentUser)
            } else {
                // 실패 후 로직
                Snackbar.make(requireView(), "로그인에 실패하였습니다. 다시 시도해주세요.", Snackbar.LENGTH_SHORT)
                    .show()
//                updateUI(null)
            }
        }
    }*/

}