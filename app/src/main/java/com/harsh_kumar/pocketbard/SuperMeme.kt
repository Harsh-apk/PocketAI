package com.harsh_kumar.pocketbard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SuperMeme.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("DEPRECATION")
class SuperMeme : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bottomview = view?.findViewById<BottomNavigationView>(R.id.bottomNav)
        /*val button = view?.findViewById<Button>(R.id.btnRefresh)
        // webView1 = view.findViewById<WebView>(R.id.webViewMeme)
        //val url = "https://app.supermeme.ai"
        //webViewSetup(webView1,url)
        // Inflate the layout for this fragment
        button?.setOnClickListener {
            if (isConnectedToInternet()) {
                replaceWithFragment(BardAI())

                bottomview?.setOnItemSelectedListener {
                    when(it.itemId){
                        R.id.bottomItem1 -> replaceWithFragment(BardAI())
                        R.id.bottomItem2 -> replaceWithFragment(ChatGpt())
                    }
                    true
                }
            } else {
                Toast.makeText( requireContext() ,"Internet is still unavailable!",Toast.LENGTH_SHORT).show()
            }
        }*/

        return inflater.inflate(R.layout.fragment_super_meme, container, false)
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }

    private fun replaceWithFragment(fragment : Fragment) {
        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frameLayout,fragment)
        fragmentTransaction?.commit()
    }

    private fun webViewSetup(webView: WebView, url:String) {

        webView.webViewClient = WebViewClient()
        webView.apply {
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
            loadUrl(url)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SuperMeme.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SuperMeme().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}