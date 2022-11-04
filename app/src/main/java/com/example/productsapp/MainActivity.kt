package com.example.productsapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productsapp.adapter.ProductsAdapter
import com.example.productsapp.database.DBHelper
import com.example.productsapp.model.Product

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHelper: DBHelper
    private lateinit var adapter: ProductsAdapter
    private var products: List<Product> = ArrayList<Product>()

    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvProducts)
        addButton = findViewById(R.id.addBtn)
        dbHelper = DBHelper(this)
        fetchList()

        addButton.setOnClickListener {
            val intent = Intent(applicationContext, ProductDetails::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchList() {
        products = dbHelper.getAllProducts()
        adapter = ProductsAdapter(products, applicationContext)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}