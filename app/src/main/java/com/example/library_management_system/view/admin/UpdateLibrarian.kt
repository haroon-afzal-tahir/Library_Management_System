package com.example.library_management_system.view.admin

import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.library_management_system.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class UpdateLibrarian : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.fragment_librarian_update)

		findViewById<TextInputEditText>(R.id.librarian_name).text = Editable.Factory.getInstance().newEditable(intent.getStringExtra("Name"))


		findViewById<MaterialButton>(R.id.librarian_update_button).setOnClickListener {
			val name = findViewById<TextInputEditText>(R.id.librarian_name).text.toString()
			val password = findViewById<TextInputEditText>(R.id.librarian_old_password).text.toString()
			val newPassword = findViewById<TextInputEditText>(R.id.update_librarian_new_password).text.toString()
			val retypeNewPassword = findViewById<TextInputEditText>(R.id.update_librarian_new_retype_password).text.toString()

			if (newPassword == retypeNewPassword) {
				val db = FirebaseFirestore.getInstance()
				db.collection("Users").whereEqualTo("Name", name).get().addOnSuccessListener { task ->
					for (document in task.documents) {
						if (document.get("Password").toString() == password) {
							val user = hashMapOf(
								"Email" to document.get("Email").toString(),
								"Name" to name,
								"Password" to newPassword,
								"Type" to "Librarian"
							)
							document.reference.update(user as Map<String, Any>)
						}
					}
					onBackPressed()
				}
			}
			else {
				Toast.makeText(this, "New and Retype New Password is Not Same", Toast.LENGTH_SHORT).show()
			}
		}

		findViewById<MaterialButton>(R.id.librarian_cancel_button).setOnClickListener {
			onBackPressed()
		}

	}

	override fun onStart() {
		super.onStart()
		supportActionBar?.hide()
	}


}