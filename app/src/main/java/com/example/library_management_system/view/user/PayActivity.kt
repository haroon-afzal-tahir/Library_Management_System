package com.example.library_management_system.view.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.library_management_system.R
import com.example.library_management_system.helper.InsertDataIntoFirestore
import com.example.library_management_system.model.Account
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class PayActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.pay_activity)

		findViewById<MaterialButton>(R.id.pay_button).setOnClickListener {
			InsertDataIntoFirestore().cleanUserAccount(intent.getStringExtra("Name").toString())
			super.onBackPressed()
		}

		val firebaseFirestore = FirebaseFirestore.getInstance()
		val info = firebaseFirestore.collection("Account").document(intent.getStringExtra("Name").toString())
		var count = 0
		info.get().addOnSuccessListener { task ->

			val arrays: Map<String, Timestamp> = task.get("Borrowed Books") as Map<String, Timestamp>
			arrays.forEach { entry ->
				val submissionDate = entry.value.toDate()
				val todayDate = Timestamp.now().toDate()

				if (todayDate > submissionDate) {
					count++
				}
			}
			findViewById<TextInputEditText>(R.id.pay_fine).setText((count * 100).toString())
			return@addOnSuccessListener
		}

	}

	override fun onStart() {
		super.onStart()
		supportActionBar?.hide()
	}
}