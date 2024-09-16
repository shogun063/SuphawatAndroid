package com.example.suphawat7

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class shogunza2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shogunza2)

        //For an synchronous task
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val homeid = intent.getStringExtra("name")
        if (homeid == null){
            val intent = Intent(this,Shogunza1::class.java)
            startActivity(intent)
            finish()
        }
        val editTextText12 = findViewById<EditText>(R.id.editTextText12)
        val editTextText13 = findViewById<EditText>(R.id.editTextText13)
        val editTextText14 = findViewById<EditText>(R.id.editTextText14)
        val editTextText15 = findViewById<EditText>(R.id.editTextText15)
        val editTextText16 = findViewById<EditText>(R.id.editTextText16)
        val editTextText17 = findViewById<EditText>(R.id.editTextText17)
        val editTextText18 = findViewById<EditText>(R.id.editTextText18)
        val editTextText19 = findViewById<EditText>(R.id.editTextText19)
        val editTextText20 = findViewById<EditText>(R.id.editTextText20)
        val imageViewFile = findViewById<ImageView>(R.id.imageView)

        val AreaSize = intent.getStringExtra("AreaSize")
        val NumberOfRooms = intent.getStringExtra("NumberOfRooms")
        val NumberOfBathrooms = intent.getStringExtra("NumberOfBathrooms")
        val Price = intent.getStringExtra("Price")
        val Conditionn = intent.getStringExtra("Conditionn")
        val HouseType = intent.getStringExtra("HouseType")
        val YearBuilt = intent.getStringExtra("YearBuilt")
        val ParkingSpaces = intent.getStringExtra("ParkingSpaces")
        val Address = intent.getStringExtra("Address")
        val Image = intent.getStringExtra("Image")

        editTextText12.setText(AreaSize)
        editTextText13.setText(NumberOfRooms)
        editTextText14.setText(NumberOfBathrooms)
        editTextText15.setText(Price)
        editTextText16.setText(Conditionn)
        editTextText17.setText(HouseType)
        editTextText18.setText(YearBuilt)
        editTextText19.setText(ParkingSpaces)
        editTextText20.setText(Address)

        if (Image != null) {
            val url = "http://10.13.1.34:3000" + Image.toString()
            // Load image using Glide
            Glide.with(this)
                .load(url)
                .into(imageViewFile)
        }


    }
}
