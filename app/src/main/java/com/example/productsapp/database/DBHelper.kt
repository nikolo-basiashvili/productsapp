package com.example.productsapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.productsapp.model.Product

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "product"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "products"
        private const val ID = "id"
        private const val NAME = "name"
        private const val PRICE = "price"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblStudent =
            ("CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $NAME TEXT, $PRICE NUMBER)")
        db?.execSQL(createTblStudent)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getAllProducts(): ArrayList<Product> {
        val stdList: ArrayList<Product> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val product = Product(
                    cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PRICE)).toFloat()
                )
                stdList.add(product)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return stdList
    }

    fun getProductById(prodId: Int): Product {
        val db = writableDatabase
        val query = "select * from $TABLE_NAME where $ID = $prodId"
        val cursor = db.rawQuery(query, null)
        cursor?.moveToFirst()
        val product = Product(
            cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
            cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
            cursor.getString(cursor.getColumnIndexOrThrow(PRICE)).toFloat()
        )
        cursor.close()
        return product
    }

    fun insertProduct(product: Product): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, product.name)
        values.put(PRICE, product.price)
        val success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    fun deleteProduct(id: Int): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val success = db.delete(TABLE_NAME, "$ID=?", arrayOf(id.toString())).toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }

    fun updateProduct(product: Product): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, product.name)
        values.put(PRICE, product.price)
        val success =
            db.update(TABLE_NAME, values, "$ID = ?", arrayOf(product.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }
}