package com.example.suphawat7

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.content.Intent
import android.net.Uri
import android.os.StrictMode
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

class Shogunza1 : AppCompatActivity() {

    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //For an synchronous task
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        setContentView(R.layout.activity_shogunza1)
        val buttonNext = findViewById<Button>(R.id.buttonNext)
        val buttonSave = findViewById<Button>(R.id.buttonsave)
        val buttonUplaod = findViewById<Button>(R.id.buttonUplaod)

        val imageView2 = findViewById<ImageView>(R.id.imageView2)

        val editTextText1 = findViewById<EditText>(R.id.editTextText1)
        val editTextText2 = findViewById<EditText>(R.id.editTextText2)
        val editTextText3 = findViewById<EditText>(R.id.editTextText3)
        val editTextText4 = findViewById<EditText>(R.id.editTextText4)
        val editTextText5 = findViewById<EditText>(R.id.editTextText5)
        val editTextText6 = findViewById<EditText>(R.id.editTextText6)
        val editTextText7 = findViewById<EditText>(R.id.editTextText7)
        val editTextText8 = findViewById<EditText>(R.id.editTextText8)
        val editTextText9 = findViewById<EditText>(R.id.editTextText9)

        val editTextText10 = findViewById<EditText>(R.id.editTextText10)

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                imageUri = data?.data
                imageUri?.let {
                    imageView2.setImageURI(it)
                    // Handle the image URI here if needed (e.g., uploading to a server)
                }
            }
        }

        buttonUplaod.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .createIntent { intent ->
                    imagePickerLauncher.launch(intent)
                }
        }


        buttonNext.setOnClickListener{
            if (editTextText10.text.isEmpty()){
                editTextText10.error = "กรุณากรอกข้อมูลให้ครบถ้วน"
                return@setOnClickListener
            }

            val url = "http://10.13.1.34:3000/get/houses/"+editTextText10.text.toString()
            val okHttpClient = OkHttpClient()
            val request: Request = Request.Builder()
                .url(url)
                .get()
                .build()
            val response = okHttpClient.newCall(request).execute()
            if(response.isSuccessful){
                val obj = JSONObject(response.body!!.string())
                val status = obj["status"].toString()
                if (status == "true") {
                    val intent = Intent(this,shogunza2::class.java)
                    intent.putExtra("name",editTextText10.text.toString())
                    intent.putExtra("AreaSize",obj["AreaSize"].toString())
                    intent.putExtra("NumberOfRooms",obj["NumberOfRooms"].toString())
                    intent.putExtra("NumberOfBathrooms",obj["NumberOfBathrooms"].toString())
                    intent.putExtra("Price",obj["Price"].toString())
                    intent.putExtra("Conditionn",obj["Conditionn"].toString())
                    intent.putExtra("HouseType",obj["HouseType"].toString())
                    intent.putExtra("YearBuilt",obj["YearBuilt"].toString())
                    intent.putExtra("ParkingSpaces",obj["ParkingSpaces"].toString())
                    intent.putExtra("Address",obj["Address"].toString())
                    intent.putExtra("Image",obj["Image"].toString())
                    startActivity(intent)
                    finish()
                } else {
                    val message = obj["message"].toString()
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

            }else{
                Toast.makeText(applicationContext, "ไม่สามารถเชื่อต่อกับเซิร์ฟเวอร์ได้", Toast.LENGTH_LONG).show()
            }
        }

        buttonSave.setOnClickListener{
            if (editTextText1.text.isEmpty() || editTextText2.text.isEmpty() || editTextText3.text.isEmpty() ||
                editTextText4.text.isEmpty() || editTextText5.text.isEmpty() || editTextText6.text.isEmpty()
                || editTextText7.text.isEmpty() || editTextText8.text.isEmpty() || editTextText9.text.isEmpty()){
                editTextText1.error = "กรุณากรอกข้อมูลให้ครบถ้วน"
                return@setOnClickListener
            }

            val url = "http://10.13.1.34:3000/get/houses"

            val okHttpClient = OkHttpClient()
            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("AreaSize",editTextText1.text.toString())
                .addFormDataPart("NumberOfRooms",editTextText2.text.toString())
                .addFormDataPart("NumberOfBathrooms",editTextText3.text.toString())
                .addFormDataPart("Price",editTextText4.text.toString())
                .addFormDataPart("Condition",editTextText5.text.toString())
                .addFormDataPart("HouseType",editTextText6.text.toString())
                .addFormDataPart("YearBuilt",editTextText7.text.toString())
                .addFormDataPart("ParkingSpaces",editTextText8.text.toString())
                .addFormDataPart("Address",editTextText9.text.toString())
            // Add image if present
            imageUri?.let {
                val file = File(it.path!!)
                val requestFile = file.asRequestBody("image/jpeg".toMediaType())
                builder.addFormDataPart("Image", file.name, requestFile)
            }
            val requestBody = builder.build()

            val request: Request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = okHttpClient.newCall(request).execute()

            if(response.isSuccessful){
                val obj = JSONObject(response.body!!.string())
                val status = obj["status"].toString()

                if (status == "true") {
                    val message = obj["message"].toString()
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                    editTextText1.text.clear()
                    editTextText2.text.clear()
                    editTextText3.text.clear()
                    editTextText4.text.clear()
                    editTextText5.text.clear()
                    editTextText6.text.clear()
                    editTextText7.text.clear()
                    editTextText8.text.clear()
                    editTextText9.text.clear()
                    return@setOnClickListener
                } else {
                    val message = obj["message"].toString()
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

            }else{
                Toast.makeText(applicationContext, "ไม่สามารถเชื่อต่อกับเซิร์ฟเวอร์ได้", Toast.LENGTH_LONG).show()
            }

        }
    }
}
