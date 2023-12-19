package com.example.sneakerstorev3

data class CartData(
    val imageUrl: String,  // รูปภาพสินค้า (เช่น R.drawable.product_image)
    val name: String,      // ชื่อสินค้า
    val detail: String,    // รายละเอียดสินค้า
    val price: Double,   // ราคาสินค้า
    var quatity: Int,
)
