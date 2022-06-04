package com.example.library_management_system.view.user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.library_management_system.*
import com.example.library_management_system.view.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserHome : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.user_home)

		replaceFragment(FragmentUserHome())

//		findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener { item ->
//			when(item.itemId) {
//				R.id.user_fragment_home -> replaceFragment(FragmentUserHome())
//				R.id.user_fragment_books -> replaceFragment(FragmentUserBook())
//				else -> return@setOnItemSelectedListener false
//			}
//		}
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