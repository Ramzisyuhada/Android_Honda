package com.example.honda

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomnavbar : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        bottomnavbar = findViewById(R.id.ButtomNavBar)

        bottomnavbar.setOnItemSelectedListener { menuitem ->
            when(menuitem.itemId){
                R.id.Home ->{
                    ReplaceFragment(HomeFragment())
                    true

                }
                R.id.Scane -> {
                    ReplaceFragment(ScanerFragment())
                    true
                }
                else -> false
            }
        }
    }


    private fun ReplaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.Frame,fragment).commit()

    }
}