package com.example.sneakerstorev3

import ProductAdapter
import ProductData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductFragment : Fragment() {
    private lateinit var dbHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.product_recycler_view)

        val id = arguments?.getString("id") // ค่า ID ควรเป็น String หรือ Int?
        // กำหนดรายการสินค้า
        val products = getProducts()

        dbHelper = DBHelper(requireContext())
        val adapter = ProductAdapter(products, object : ProductAdapter.OnItemClickListener {
            override fun onBuyButtonClick(item: ProductData) {
                dbHelper.addToCart(id.toString().toInt(),  item.name, item.detail, item.price, item.imageUrl)
//                Toast.makeText(requireContext(), "คุณได้ซื้อ ${item.name}", Toast.LENGTH_SHORT).show()
            }
        })

        recyclerView.adapter = adapter

        // กำหนด GridLayoutManager
        val spanCount = 2 // จำนวนคอลัมน์ในรูปแบบตาราง
        val layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.layoutManager = layoutManager

        return view
    }

    private fun getProducts(): List<ProductData> {
        // นี่คือส่วนที่คุณควรดึงข้อมูลสินค้าจากแหล่งข้อมูล เช่น API หรือฐานข้อมูล
        // และสร้างรายการสินค้าขึ้นมาเพื่อส่งคืน

        // ตัวอย่างสร้างรายการสินค้าแบบฟิกข้อมูล

        val product1 = ProductData("https://d2cva83hdk3bwc.cloudfront.net/nike-dunk-low-polar-blue-3.jpg", "Nike Dunk Low Polar Blue", "Bule", 3000.0)
        val product2 = ProductData("https://d2cva83hdk3bwc.cloudfront.net/jordan-1-low-light-smoke-grey-1.jpg", "Jordan 1 Low Light Smoke Grey", "Smoke Grey", 5500.0)
        val product3 = ProductData("https://d2cva83hdk3bwc.cloudfront.net/nike-dunk-low-court-purple-1.jpg", "Nike Dunk Low Court Purple", "Purple", 3400.0)
        val product4 = ProductData("https://d2cva83hdk3bwc.cloudfront.net/jordan-4-retro-sb-pine-green-1.jpg", "Jordan 4 Retro SB Pine Green", "Green", 15000.0)
        val product5 = ProductData("https://d2cva83hdk3bwc.cloudfront.net/new-balance-530-white-silver-metallic-black-1.jpg", "New Balance 530", "White Silver Metallic Black", 4870.0)
        val product6 = ProductData("https://d2cva83hdk3bwc.cloudfront.net/new-balance-530-deep-purple-white-1.jpg", "New Balance 530 Deep ", "Purple White", 6100.0)
        val product7 = ProductData("https://d2cva83hdk3bwc.cloudfront.net/adidas-forum-low-white-black-1.jpg", "adidas Forum Low White Black", "White Black", 3000.0)
        val product8 = ProductData("https://d2cva83hdk3bwc.cloudfront.net/nike-dunk-low-essential-paisley-pack-barley-1.jpg", "Nike Dunk Low Essential Paisley Pack Barley ", "White-barley", 4787.0)
        val product9 = ProductData("https://d2cva83hdk3bwc.cloudfront.net/nike-dunk-low-se--multi-camo-2.jpg", "Nike Dunk Low SE Multi Camo", "Sail/Vintage Green", 3200.0)
        val product10 = ProductData("https://d2cva83hdk3bwc.cloudfront.net/nike-cortez-4-0-sacai-white-university-red-blue-1.jpg", "Nike Cortez 4.0 Sacai White", "University Red Blue", 5400.0)
        val product11 =  ProductData("https://d2cva83hdk3bwc.cloudfront.net/jordan-1-retro-high-og-yellow-toe-1.jpg", "Jordan 1 Retro High OG Yellow Toe", "Taxi/Black-Sail", 6550.0)

        return listOf(product1,
            product2,
            product3,
            product4,
            product5,
            product6,
            product7,
            product8,
            product9,
            product10,
            product11,)
    }
}
