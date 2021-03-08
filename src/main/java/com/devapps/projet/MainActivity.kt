package com.devapps.projet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(sharedPref.getBoolean("Logged",false)){
            val opt = findViewById<Button>(R.id.button2)
            opt.isClickable=false
            opt.visibility= View.GONE
        }



    }

    private val sharedPref by lazy {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
                "secret_shared_prefs",
                masterKeyAlias,
                applicationContext ,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


    override fun onStart() {
        super.onStart()
        val btn =findViewById<Button>(R.id.button)
        val input = findViewById<EditText>(R.id.editTextTextPassword)



        btn.setOnClickListener {
            if(login(input)){
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
            }

        }
        val btn2 =findViewById<Button>(R.id.button2)
        btn2.setOnClickListener {
            register(input)
            Toast.makeText(applicationContext, "Registered", Toast.LENGTH_SHORT).show()
        }
    }

    private fun register(input: EditText) {
        Encryption().KeyGen()
        val pass = Encryption().Encrypt(input.text.toString())
        with(sharedPref.edit()) {
            putString("IV", pass["iv"]?.let { String(it, Charsets.ISO_8859_1) })
            putString("PWD", pass["encrypted"]?.let { String(it, Charsets.ISO_8859_1) })
            putBoolean("Logged",true)
            apply()
        }
    }

    private fun login(input: EditText): Boolean {
        var success =false
        val map = HashMap<String, ByteArray>()
        var temp : String? = sharedPref.getString("IV", null)
        if (temp != null) {
            map["iv"] = temp.toByteArray(Charsets.ISO_8859_1)
        }
        temp = sharedPref.getString("PWD", null)
        if (temp != null) {
            map["encrypted"] = temp.toByteArray(Charsets.ISO_8859_1)
        }
        if(input.text.toString() == Encryption().Decrypt(map)){
            success = true
        }
        return success

    }

}