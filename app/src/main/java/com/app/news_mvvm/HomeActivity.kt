package com.app.news_mvvm

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.news_mvvm.viewModel.NewsViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "HomeActivityScreen"
    }

    private lateinit var bottomNav :BottomNavigationView
    private lateinit var toolbar: MaterialToolbar
    private lateinit var tabLayout: TabLayout
    private lateinit var navController: NavController
    private lateinit var toolbarNavIcon :Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.home_toolbar)
        toolbar.apply {
            elevation = 4.0F
        }
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        setSupportActionBar(toolbar)
        bottomNav = findViewById(R.id.bottom_nav)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_home_host) as NavHostFragment
        navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)

        tabLayout = findViewById(R.id.home_tabLayout)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.headlineFragment -> {
                    tabLayout.visibility = View.GONE
                    toolbar.visibility = View.VISIBLE
                    bottomNav.visibility = View.VISIBLE
                    toolbar.apply {
                        setNavIconVisibility(false)
                        title = "HeadLines"
                    }
                }
                R.id.sourceFragment -> {
                    tabLayout.visibility = View.VISIBLE
                    toolbar.visibility = View.GONE
                    bottomNav.visibility = View.VISIBLE
                }

                R.id.searchNewsFragment -> {
                    tabLayout.visibility = View.GONE
                    toolbar.visibility = View.GONE
                    bottomNav.visibility = View.VISIBLE
                }

                R.id.savedNewsFragment -> {
                    tabLayout.visibility = View.GONE
                    toolbar.visibility = View.VISIBLE
                    toolbar.apply {
                        setNavIconVisibility(false)
                        title = "Saved News"
                    }
                    bottomNav.visibility = View.VISIBLE
                }

                R.id.newsDetailsFragment -> {
                    tabLayout.visibility = View.GONE
                    toolbar.visibility = View.VISIBLE
                    bottomNav.visibility = View.GONE
                    toolbar.apply {
                        setNavIconVisibility(true)
                        title = "News App"
                    }

                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        navController.popBackStack()
        Log.d(TAG, "onSupportNavigateUp: ")
        return true
    }

    private fun MaterialToolbar.setNavIconVisibility(isVisible :Boolean) {
        if (isVisible) {
            this.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        }
        else {
            this.navigationIcon = null
        }
    }
}