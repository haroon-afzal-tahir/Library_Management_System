package com.example.library_management_system.database

import java.sql.Connection
import java.sql.DriverManager

class MySQL {
	fun getConnection(): Connection {
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management_system", "root", "zxasqw123edc")
	}
}