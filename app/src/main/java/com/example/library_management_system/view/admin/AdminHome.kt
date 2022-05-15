package com.example.library_management_system.view.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.library_management_system.HomeFragment
import com.example.library_management_system.LibrarianFragment
import com.example.library_management_system.R
import com.example.library_management_system.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminHome : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.admin_home)
		replaceFragment(HomeFragment())

		findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener { item ->
			when(item.itemId) {
				R.id.fragment_home -> replaceFragment(HomeFragment())
				R.id.fragment_librarian -> replaceFragment(LibrarianFragment())
				R.id.fragment_user -> replaceFragment(UserFragment());
				else -> return@setOnItemSelectedListener false
			}
		}

	}

	override fun onStart() {
		super.onStart()
		supportActionBar?.hide()
	}

	fun replaceFragment(fragment: Fragment) : Boolean {
		var fragmentManager = supportFragmentManager
		var fragmentTransaction = fragmentManager.beginTransaction()
		fragmentTransaction.replace(R.id.fragment, fragment)
		fragmentTransaction.commit()
		return true
	}
}