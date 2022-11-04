package com.example.productsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.productsapp.database.DBHelper
import com.example.productsapp.model.Product

class ProductDetails : AppCompatActivity() {

    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button
    private lateinit var editName: EditText
    private lateinit var editPrice: EditText

    private lateinit var dbHelper: DBHelper
    private var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        saveButton = findViewById(R.id.saveBtn)
        deleteButton = findViewById(R.id.deleteBtn)
        editName = findViewById(R.id.editName)
        editPrice = findViewById(R.id.editPrice)

        dbHelper = DBHelper(this)

        if (intent != null && intent.getStringExtra("MODE") == "E") {
            isEditMode = true
            saveButton.text = resources.getString(R.string.update)
            deleteButton.visibility = View.VISIBLE
            val product = dbHelper.getProductById(intent.getIntExtra("ID", 0))
            editName.setText(product.name)
            editPrice.setText(product.price.toString())
        } else {
            isEditMode = false
            saveButton.text = resources.getString(R.string.save)
            deleteButton.visibility = View.GONE
        }

        saveButton.setOnClickListener {
            var success = false
            val prod = Product()
            if (isEditMode) {
                try {
                    prod.id = intent.getIntExtra("ID", 0)
                    prod.name = editName.text.toString()
                    prod.price = editPrice.text.toString().toFloat()
                    success = dbHelper.updateProduct(prod)
                } catch (e: NumberFormatException) {
                    Toast.makeText(
                        applicationContext,
                        "Insert a correct number",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                if (editName.text.isEmpty() || editPrice.text.isEmpty()) {
                    Toast.makeText(applicationContext, "Fill each field", Toast.LENGTH_SHORT).show()
                }
                try {
                    prod.name = editName.text.toString()
                    prod.price = editPrice.text.toString().toFloat()
                    success = dbHelper.insertProduct(prod)
                } catch (e: NumberFormatException) {
                    Toast.makeText(
                        applicationContext,
                        "Insert a correct number",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (success) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        deleteButton.setOnClickListener {
            dbHelper.deleteProduct(intent.getIntExtra("ID", 0))
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}