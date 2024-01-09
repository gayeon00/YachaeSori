package com.yachae.yachaesori.presentation.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.FragmentDetailImagesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_DETAIL = "detailImageUrl"

class DetailImagesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var detailImageUrl: String? = null

    private var _binding: FragmentDetailImagesBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            detailImageUrl = it.getString(ARG_DETAIL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailImagesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDetailImage()

    }

    private fun setDetailImage() {
        val storageReference = Firebase.storage.reference.child(detailImageUrl!!)

        Glide.with(this)
            .load(storageReference)
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.ivProductDetail)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param detailImageUrl Parameter 1.
         * @return A new instance of fragment DetailImagesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(detailImageUrl: String) =
            DetailImagesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DETAIL, detailImageUrl)
                }
            }
    }
}