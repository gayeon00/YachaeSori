package com.yachae.yachaesori.presentation.feature.payment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.FragmentAddressDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressDialogFragment : Fragment() {
    private val paymentViewModel: PaymentViewModel by activityViewModels()
    lateinit var fragmentAddressDialogBinding: FragmentAddressDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAddressDialogBinding = FragmentAddressDialogBinding.inflate(layoutInflater)

        setupToolbar()
        handleBackPress()
        fragmentAddressDialogBinding.webViewAddress.settings.javaScriptEnabled = true
        fragmentAddressDialogBinding.webViewAddress.addJavascriptInterface(
            BridgeInterface(),
            "Android"
        )
        //캐시 삭제
        fragmentAddressDialogBinding.webViewAddress.clearCache(true);
        fragmentAddressDialogBinding.webViewAddress.clearHistory();

        fragmentAddressDialogBinding.webViewAddress.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                // 페이지 로딩이 완료된 후Andorid-> JavaScript 함수를 호출
                view?.loadUrl("javascript:sample2_execDaumPostcode();")
            }
        }

        // WebView에 원하는 페이지를 로드
        val mainUrl = "https://swimmer-f0505.web.app"
        fragmentAddressDialogBinding.webViewAddress.loadUrl(mainUrl)

        return fragmentAddressDialogBinding.root
    }

    //백버튼 제어
    private fun handleBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun setupToolbar() {
        fragmentAddressDialogBinding.toolbarAddressDialog.run {
            title = "배송지 검색"
            setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
            setNavigationOnClickListener {
                Toast.makeText(context, "배송지 등록이 취소되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    fun navigateWithData(data: String) {
        val splitData = data.split(",")
        //split에 쉼표로 분기하는데 애초에 api에서 넘어온 data는 data.address+처리 코드 // data.zonecode 두개를 쉼표로 나누기
        //위함이었는데 data.address에서 ,가 붙여져서 넘어오면 IndexOutOfBoundsException가 생긴다 그래서
        //splitData의 크기가 3이면 주소에 splitData[0]+splitData[1] 를 해서 오류를 방지
        if (splitData.size >= 3) {

            paymentViewModel.setAddress(splitData[0] + " " + splitData[1].trim())
            paymentViewModel.setPostcode(splitData[2].trim())
        } else if (splitData.size == 2) {
            paymentViewModel.setAddress(splitData[0].trim())
            paymentViewModel.setPostcode(splitData[1].trim())
        } else {
            // 데이터 형식이 예상과 다를 경우의 처리
            return
        }
        Log.d("Address", "뭐징")
        CoroutineScope(Dispatchers.Main).launch {
            findNavController().navigate(R.id.action_addressDialogFragment_to_paymentFragment2)
        }

    }

    inner class BridgeInterface {
        @JavascriptInterface
        fun processDATA(data: String) {

            navigateWithData(data)


        }

    }

}