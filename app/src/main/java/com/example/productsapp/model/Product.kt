package com.example.productsapp.model

data class Product(
    var id: Int,
    var name: String,
    var price: Float
) {
    constructor() : this(0, "", 0.0f)
}