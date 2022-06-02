package com.example.library_management_system.view.librarian

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.library_management_system.FragmentLibrarianUserFragment
import com.example.library_management_system.Librarian_Home_Fragment
import com.example.library_management_system.R
import com.example.library_management_system.view.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class LibrarianHome : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.librarian_home)

		replaceFragment(Librarian_Home_Fragment())

		findViewById<BottomNavigationView>(R.id.librarian_home_bottom_navigation_view).setOnItemSelectedListener { item ->
			when(item.itemId) {
				R.id.librarian_fragment_home -> replaceFragment(Librarian_Home_Fragment())
				R.id.librarian_fragment_user -> replaceFragment(FragmentLibrarianUserFragment())
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