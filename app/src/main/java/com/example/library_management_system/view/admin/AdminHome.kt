package com.example.library_management_system.view.admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.library_management_system.HomeFragment
import com.example.library_management_system.LibrarianFragment
import com.example.library_management_system.R
import com.example.library_management_system.UserFragment
import com.example.library_management_system.view.LoginActivity
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
				R.id.fragment_user -> replaceFragment(UserFragment())
				else -> return@setOnItemSelectedListener false
			}
		}

	}

	override fun onStart() {
		super.onStart()
		supportActionBar?.hide()
	}

	private fun replaceFragment(fragment: Fragment) : Boolean {
		val fragmentManager = supportFragmentManager
		val fragmentTransaction = fragmentManager.beginTransaction()
		fragmentTransaction.replace(R.id.fragment, fragment)
		fragmentTransaction.commit()
		return true
	}

	override fun onBackPressed() {
		val intent = Intent(this, LoginActivity::class.java)
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
		startActivity(intent)
	}
}