package com.harsh_kumar.pocketbard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.content.SharedPreferences
import android.net.Uri
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toggle:ActionBarDrawerToggle
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.navView)
        sharedPreferences = getSharedPreferences("WebViewPreferences", MODE_PRIVATE)
        val bottomview = findViewById<BottomNavigationView>(R.id.bottomNav)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        navView.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.item1 -> Toast.makeText(applicationContext,"Because, why not?", Toast.LENGTH_SHORT ).show()
                R.id.item2 -> {
                    val urlPortfolio = "https://www.linkedin.com/in/harsh-kumar-13496925a"
                    openUrlInChrome(urlPortfolio)

                }
                R.id.item3 -> Toast.makeText(applicationContext,"Coming Soon ",Toast.LENGTH_SHORT).show()

            }
            true
        }


        if (isConnectedToInternet()) {
            // Internet connection is available
            // Perform your desired actions here

            replaceWithFragment(BardAI())

            bottomview.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.bottomItem1 -> replaceWithFragment(BardAI())
                    R.id.bottomItem2 -> replaceWithFragment(ChatGpt())
                }
                true
            }

        } else {
            // No internet connection
            // Handle the lack of connectivity here
            replaceWithFragment(SuperMeme())

        }

        /*replaceWithFragment(BardAI())

        bottomview.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottomItem1 -> replaceWithFragment(BardAI())
                R.id.bottomItem2 -> replaceWithFragment(ChatGpt())
            }
            true
        }*/
    }
    private fun openUrlInChrome(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.linkedin.android")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            // Chrome is not installed, handle the situation as needed
            Toast.makeText(applicationContext, "LinkedIn is not installed", Toast.LENGTH_SHORT).show()
        }
    }


    private fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }


    private fun replaceWithFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}