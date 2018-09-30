package com.github.devjn.imgurimagegallery.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.devjn.imgurimagegallery.R
import com.github.devjn.imgurimagegallery.Section
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var navigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        navigation = findViewById(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            navigation.selectedItemId = R.id.navigation_hot
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_about -> {
            startActivity(Intent(this, AboutActivity::class.java)); true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val section = when (item.itemId) {
            R.id.navigation_hot -> Section.HOT
            R.id.navigation_top -> Section.TOP
            R.id.navigation_user -> Section.USER
            else -> ""
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(section), "Main")
                .commit()
        true
    }


}
