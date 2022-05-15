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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.library_management_system.HomeFragment
import com.example.library_management_system.LibrarianFragment
import com.example.library_management_system.R
import com.example.library_management_system.UserFragment
import com.example.library_management_system.adapter.UserViewAdapter
import com.example.library_management_system.model.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class AdminHome : AppCompatActivity() {

//	private lateinit var recyclerView: RecyclerView
//	private lateinit var myAdapter: UserViewAdapter


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.admin_home)


//		recyclerView = findViewById(R.id.userView)
//
//
//		var usersList: ArrayList<User> = ArrayList()
//
//		FirebaseFirestore.getInstance().collection("Users").whereEqualTo("Type", "User").get().addOnSuccessListener { documents ->
//			for (document in documents) {
//				usersList.add(document.toObject(User::class.java))
//			}
//		}
//
//		myAdapter = UserViewAdapter(this, usersList)
//		recyclerView.adapter = myAdapter
//		recyclerView.layoutManager = LinearLayoutManager(this)

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