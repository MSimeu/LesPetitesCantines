package com.msimeu.lespetitescantines

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DataBaseManager {
    val DATABASE_URI = "35.246.119.224"
    val DATABASE_USER = "postgres"
    val DATABASE_PASSWORD = "cantnreal"
    val DATABASE_NAME = "CANTNREAL_frequentationrestaurant_by_debut"

    init {
        initConnection()
    }

    private fun initConnection() {
        println("-------- PostgreSQL " + "JDBC Connection Testing ------------")

        try {

            Class.forName("org.postgresql.Driver")

        } catch (e: ClassNotFoundException) {

            println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!")
            e.printStackTrace()
            return

        }


        println("PostgreSQL JDBC Driver Registered!")

        var connection: Connection? = null

        try {

            connection = DriverManager.getConnection(
                "jdbc:postgresql://$DATABASE_URI/$DATABASE_NAME", DATABASE_USER,
                DATABASE_PASSWORD
            )

        } catch (e: SQLException) {
            println("Connection Failed! Check output console")
            e.printStackTrace()
            return

        }


        if (connection != null) {
            println("You made it, take control your database now!")
        } else {
            println("Failed to make connection!")
        }
    }
}
