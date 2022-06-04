package com.example.library_management_system.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.library_management_system.R
import com.example.library_management_system.helper.InsertDataIntoFirestore
import com.example.library_management_system.model.User
import com.example.library_management_system.view.admin.AdminHome
import com.example.library_management_system.view.librarian.LibrarianHome
import com.example.library_management_system.view.user.UserHome
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*


class LoginActivity : AppCompatActivity() {
	private lateinit var mAuth: FirebaseAuth
	override fun onCreate(savedInstanceState: Bundle?) {

		super.onCreate(savedInstanceState)
		setContentView(R.layout.login_activity)
		MobileAds.initialize(this){

		}

		val mAdView = findViewById<AdView>(R.id.adView)
		val adRequest = AdRequest.Builder().build()
		mAdView.loadAd(adRequest)
		mAuth = FirebaseAuth.getInstance()

		var docRef = FirebaseFirestore.getInstance().collection("Users").document("Haroon1")
			docRef.get().addOnCompleteListener { task ->
			if (task.isSuccessful) {
				val doc = task.result
				if (doc.exists()) {
					Log.d("Document: ", doc.data.toString())
					Log.d("Name", doc.get("Name").toString())
					Log.d("Email", doc.get("Email").toString())
					Log.d("Type", doc.get("Type").toString())
				}
				else {
					Log.d("Document: ", "No Data Exists")
				}
			} else {

			}
		}

		findViewById<Button>(R.id.sign_up).setOnClickListener {
			var intent = Intent(this, SignupActivity::class.java)
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
			startActivity(intent)
		}

		findViewById<MaterialButton>(R.id.sign_in).setOnClickListener {
			var intent = Intent(this, AdminHome::class.java)
			val email = findViewById<TextInputEditText>(R.id.login_email).text.toString()
			val password = findViewById<TextInputEditText>(R.id.login_password).text.toString()
			if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
				Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_LONG).show()
			}
			else if (TextUtils.isEmpty(email)) {
				Toast.makeText(this, "Please Enter Email", Toast.LENGTH_LONG).show()
			}
			else if (TextUtils.isEmpty(password)) {
				Toast.makeText(this, "Please Enter Password", Toast.LENGTH_LONG).show()
			}
			else {
				mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener	 { task ->
					if (task.isSuccessful) {
						val db = FirebaseFirestore.getInstance()
						db.collection("Users").whereEqualTo("Email", email).get()
							.addOnSuccessListener { task1 ->
								if (task1.documents.isEmpty()) {
									Toast.makeText(
										this,
										"This User is not in the database",
										Toast.LENGTH_SHORT
									).show()
								} else if (task.isSuccessful) {
									findViewById<TextInputEditText>(R.id.login_email).setText("")
									findViewById<TextInputEditText>(R.id.login_password).setText("")
									Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG)
										.show()
									docRef.get().addOnSuccessListener { document ->
										if (document != null) {
											if (email == document.get("Email").toString()) {
												// This means the user is admin
												val intent = Intent(this, AdminHome::class.java)
												startActivity(intent)
											} else {
												FirebaseFirestore.getInstance().collection("Users")
													.whereEqualTo("Email", email).get()
													.addOnSuccessListener { task2 ->
														val db1 = task2.documents
														// We will get type of the login user
														if (db1[0].get("Type")
																.toString() == "Librarian"
														) {
															// Launch Activity of Librarian
															val intent = Intent(
																this,
																LibrarianHome::class.java
															)
															startActivity(intent)
														} else {
															// Launch Activity of User
															InsertDataIntoFirestore.setUser(db1[0].get("Name").toString())
															val intent =
																Intent(this, UserHome::class.java)
															db.collection("Users")
																.whereEqualTo("Email", email).get()
																.addOnSuccessListener { task3 ->
																	intent.putExtra(
																		"Name",
																		task3.documents[0].get("Name")
																			.toString()
																	)
																	startActivity(intent)
																}
														}
													}
											}
										}
									}
								} else {
									Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
								}
							}
					}
					else {
						val db = FirebaseFirestore.getInstance()
						db.collection("User").whereEqualTo("Email", email).get().addOnSuccessListener { task1 ->
							if (task1.documents.isNotEmpty()) {
								if (task1.documents[0].get("Password").toString() == password) {
									if (task1.documents[0].get("Type").toString() == "Librarian") {
										// Launch Activity of Librarian
										val intent = Intent(this, LibrarianHome::class.java)
										startActivity(intent)
									} else {
										// Launch Activity of User
										val intent = Intent(this, UserHome::class.java)
										intent.putExtra("Name", task1.documents[0].get("Name").toString())
										startActivity(intent)
									}
								}
							}
							else {
								Toast.makeText(this, "This User doesn't exist", Toast.LENGTH_SHORT).show()
							}
						}
					}
				}
			}
		}

	}

	override fun onStart() {
		super.onStart()
		supportActionBar?.hide()
	}
}