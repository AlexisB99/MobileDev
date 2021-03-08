package com.devapps.projet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.*
import java.io.InputStream
import java.net.URL
import java.net.URLConnection
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.BufferedReader
import java.lang.Exception
import java.lang.NumberFormatException

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }
    @Serializable
    data class Account(val id: String, val accountName: String, val amount: String, val iban: String, val currency: String )
    override fun onStart() {
        super.onStart()
        display()

        val refreshbtn = findViewById<Button>(R.id.button)
        refreshbtn.setOnClickListener{
            display()
        }
    }

    fun display(){
        try {
            var res = runBlocking {
                fetchAll()
            }

            var acc2 = Json.decodeFromString<Account>(res[1])
            var account2 = findViewById<TextView>(R.id.account2)
            account2.text = acc2.toString()

            var acc3 = Json.decodeFromString<Account>(res[2])
            var account3 = findViewById<TextView>(R.id.account3)
            account3.text = acc3.toString()

            var acc4 = Json.decodeFromString<Account>(res[3])
            var account4 = findViewById<TextView>(R.id.account4)
            account4.text = acc4.toString()

            var acc5 = Json.decodeFromString<Account>(res[4])
            var account5 = findViewById<TextView>(R.id.account5)
            account5.text = acc5.toString()

            var acc6 = Json.decodeFromString<Account>(res[5])
            var account6 = findViewById<TextView>(R.id.account6)
            account6.text = acc6.toString()

            Toast.makeText(applicationContext, "Data actualized", Toast.LENGTH_SHORT).show()
        }
        catch (exception : Exception){
            Toast.makeText(applicationContext, "Network error", Toast.LENGTH_SHORT).show()
        }

    }

    suspend fun fetchAll() =
        coroutineScope {
            var deferreds = listOf(
                async { fetchAccount("1") },
                async { fetchAccount("2") },
                async { fetchAccount("3") },
                async { fetchAccount("4") },
                async { fetchAccount("5") },
                async { fetchAccount("6") },
            )
            deferreds.awaitAll()
        }


    suspend fun fetchAccount(number: String): String {
        val address = Keys.apiKey()
        return get(address + number)
    }

    suspend fun get(url: String) =
        withContext(Dispatchers.IO) {
            val url = URL(url)
            val urlConnection: URLConnection = url.openConnection()
            var inputStream: InputStream = urlConnection.getInputStream()
            inputStream.bufferedReader().use(BufferedReader::readText)

        }
}




