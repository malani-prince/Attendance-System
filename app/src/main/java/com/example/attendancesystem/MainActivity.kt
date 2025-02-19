package com.example.attendancesystem

import android.graphics.Color
import android.os.*
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.attendancesystem.Constants.Preference.IS_FROM
import com.example.attendancesystem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    // VARIABLE LIST
    private var isExit = false
    private var currentFragment: Fragment? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        
        init()
        onClicks()
    }
    
    private fun init() {
        window.statusBarColor = Color.TRANSPARENT
        
        prefManager().setStringPreference(IS_FROM, getString(R.string.overview))
        
        binding.bottomNavigation.apply {
            when (prefManager().getStringPreference(IS_FROM)) {
                getString(R.string.overview) -> {
                    setText(binding.toolbar.tvScreenName, "OverView")
                    setToolBarAndBottomNavigationBarName(getString(R.string.overview))
                    navigateToFragment(OverViewFragment(), llOverView, ivOverView)
                }
                
                getString(R.string.attendance) -> {
                    setText(binding.toolbar.tvScreenName, "Attendance")
                    setToolBarAndBottomNavigationBarName(getString(R.string.attendance))
                    navigateToFragment(AttendanceFragment(), llAttendance, ivAttendance)
                }
                
                else -> {
                    setToolBarAndBottomNavigationBarName(getString(R.string.overview))
                    setText(binding.toolbar.tvScreenName, "OverView")
                    navigateToFragment(OverViewFragment(), llOverView, ivOverView)
                }
            }
        }
    }
    
    private fun onClicks() {
        binding.apply {
            bottomNavigation.apply {
                llOverView.setOnClickListener {
                    setText(binding.toolbar.tvScreenName, "OverView")
                    setToolBarAndBottomNavigationBarName(getString(R.string.overview))
                    navigateToFragment(OverViewFragment(), llOverView, ivOverView)
                }
                
                llAttendance.setOnClickListener {
                    setText(binding.toolbar.tvScreenName, "OverView")
                    setToolBarAndBottomNavigationBarName(getString(R.string.attendance))
                    navigateToFragment(AttendanceFragment(), llAttendance, ivAttendance)
                }
            }
            
            addOnBackPressedDispatcher {
                manageBackPress()
            }
        }
    }
    
    private fun manageBackPress() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
            
        } else {
            askForPressBackButtonTwiceForExitApp()
        }
    }
    
    private fun askForPressBackButtonTwiceForExitApp() {
        if (isExit) {
            finishAndRemoveTask()
            return
        }
        toast("Press One more time to exit")
        isExit = true
        Handler(Looper.getMainLooper()).postDelayed({ isExit = false }, 2000)
    }
    
    private fun navigateToFragment(fragment: Fragment, linearLayout: View, imageView: View) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.addToBackStack(fragment::class.java.simpleName)
        transaction.commit()
        
        currentFragment = fragment
        
        binding.bottomNavigation.apply {
            llOverView.isSelected = false
            ivOverView.isSelected = false
            
            llAttendance.isSelected = false
            ivAttendance.isSelected = false
            
            linearLayout.isSelected = true
            imageView.isSelected = true
        }
    }
    
    private fun setToolBarAndBottomNavigationBarName(name: String) {
        binding.toolbar.apply {
            tvScreenName.text = name.trim()
        }
        
        binding.bottomNavigation.apply {
            viewGone(tvOverView)
            viewGone(tvAttendance)
            
            when (name) {
                getString(R.string.overview) -> {
                    viewVisible(tvOverView)
                }
                
                getString(R.string.attendance) -> {
                    viewVisible(tvAttendance)
                }
                
                else -> {
                    viewVisible(tvOverView)
                }
            }
        }
    }
}