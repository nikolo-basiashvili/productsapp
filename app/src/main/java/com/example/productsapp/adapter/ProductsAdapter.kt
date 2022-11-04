package com.example.productsapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.productsapp.ProductDetails
import com.example.productsapp.R
import com.example.productsapp.model.Product

class ProductsAdapter(private val todos: List<Product>, internal var context: Context) :
    RecyclerView.Adapter<ProductsAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleView = itemView.findViewById<TextView>(R.id.nameView)
        private val contentView = itemView.findViewById<TextView>(R.id.priceView)
        private val editButton = itemView.findViewById<Button>(R.id.editBtn)

        fun setData(product: Product) {
            titleView.text = product.name
            contentView.text = product.price.toString()
            editButton.setOnClickListener {
                val intent = Intent(context, ProductDetails::class.java)
                intent.putExtra("MODE", "E")
                intent.putExtra("ID", product.id)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return TodoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]
        holder.setData(todo)
    }

    override fun getItemCount() = todos.size

}