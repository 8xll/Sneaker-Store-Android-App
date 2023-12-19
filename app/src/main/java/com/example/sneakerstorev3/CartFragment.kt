
package com.example.sneakerstorev3

import CartAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartFragment : Fragment() {
    private lateinit var dbHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        dbHelper = DBHelper(requireContext())
        val recyclerView = view.findViewById<RecyclerView>(R.id.cart_recycler_view)
        val totalPirce = view.findViewById<TextView>(R.id.totalPrice)
        var totalPriceN: Double = 0.0

        val id = arguments?.getString("id")

        val products = mutableListOf<CartData>()
        val cartCursor = dbHelper.getCartItemsByUserId(id.toString().toInt())

        if (cartCursor != null) {
            while (cartCursor.moveToNext()) {
                val productName = cartCursor.getString(cartCursor.getColumnIndexOrThrow(DBHelper.PRODUCT_NAME_COL))
                val productDetail = cartCursor.getString(cartCursor.getColumnIndexOrThrow(DBHelper.PRODUCT_DETAIL_COL))
                val productPrice = cartCursor.getDouble(cartCursor.getColumnIndexOrThrow(DBHelper.PRODUCT_PRICE_COL))
                val productQuantity = cartCursor.getInt(cartCursor.getColumnIndexOrThrow(DBHelper.PRODUCT_QUANTITY_COL))
                val productUrl = cartCursor.getString(cartCursor.getColumnIndexOrThrow(DBHelper.PRODUCT_URL_COL))
                val product = CartData(productUrl, productName, productDetail, productPrice*productQuantity, productQuantity)
                products.add(product)
                totalPriceN += productPrice*productQuantity
            }
            cartCursor.close()
            totalPirce.text = "Total Price : " + totalPriceN.toString()
        }

        val adapter = CartAdapter(products, object : CartAdapter.OnItemClickListener {
            override fun onAddButtonClick(item: CartData) {
                dbHelper.addToCart(id.toString().toInt(), item.name, item.detail, item.price, item.imageUrl)
                Toast.makeText(requireContext(), "คุณได้เพิ่ม ${item.name}", Toast.LENGTH_SHORT).show()
                val CartFragment = CartFragment()
                CartFragment.arguments = Bundle().apply {
                    putString("id", id.toString())
                }
                replaceFragment(CartFragment)
            }

            override fun onDeleteButtonClick(item: CartData) {
                dbHelper.updateCartItemQuantity(id.toString().toInt(), item.name, item.detail, -1)
                Toast.makeText(requireContext(), "คุณได้ลบ ${item.name}", Toast.LENGTH_SHORT).show()
                val CartFragment = CartFragment()
                CartFragment.arguments = Bundle().apply {
                    putString("id", id.toString())
                }
                replaceFragment(CartFragment)
            }
        })

        recyclerView.adapter = adapter

        val spanCount = 1 // Number of columns in the grid layout
        val layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.layoutManager = layoutManager

        val Order = view.findViewById<Button>(R.id.order)
        Order.setOnClickListener {
            if (totalPriceN > 0) {
                val OrderFragment = OrderFragment()
                OrderFragment.arguments = Bundle().apply {
                    putString("id", id.toString())
                }
                replaceFragment(OrderFragment)
            } else {
                Toast.makeText(requireContext(), "ไม่มีรายการสินค้า", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment, fragment.javaClass.simpleName)
        fragmentTransaction.commit()
    }
}


//package com.example.sneakerstorev3
//
//import CartAdapter
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//
//
//class CartFragment : Fragment() {
//    private lateinit var dbHelper: DBHelper
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_cart, container, false)
//
//        dbHelper = DBHelper(requireContext())
//        val recyclerView = view.findViewById<RecyclerView>(R.id.cart_recycler_view)
//
//        val id = arguments?.getString("id")
//
//        val products = mutableListOf<CartData>()
//        val cartCursor = dbHelper.getCartItemsByUserId(id.toString().toInt())
//
//
//        if (cartCursor != null) {
//            while (cartCursor.moveToNext()) {
//                val productName = cartCursor.getString(cartCursor.getColumnIndexOrThrow(DBHelper.PRODUCT_NAME_COL))
//                val productDetail = cartCursor.getString(cartCursor.getColumnIndexOrThrow(DBHelper.PRODUCT_DETAIL_COL))
//                val productPrice = cartCursor.getDouble(cartCursor.getColumnIndexOrThrow(DBHelper.PRODUCT_PRICE_COL))
//                val productQuantity = cartCursor.getInt(cartCursor.getColumnIndexOrThrow(DBHelper.PRODUCT_QUANTITY_COL))
//                val product = CartData(R.drawable.product1, productName, productDetail, productPrice, productQuantity)
//                products.add(product)
//            }
//            cartCursor.close()
//        }
//
//        val adapter = CartAdapter(products, object : CartAdapter.OnItemClickListener {
//            override fun onAddButtonClick(item: CartData) {
//                dbHelper.addToCart(id.toString().toInt(), item.name, item.detail, item.price)
//                Toast.makeText(requireContext(), "คุณได้เพิ่ม ${item.name}", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onDeleteButtonClick(item: CartData) {
//                dbHelper.updateCartItemQuantity(id.toString().toInt(), item.name, item.detail, -1)
//                Toast.makeText(requireContext(), "คุณได้ลบ ${item.name}", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        recyclerView.adapter = adapter
//
//        val spanCount = 1 // จำนวนคอลัมน์ในรูปแบบตาราง
//        val layoutManager = GridLayoutManager(requireContext(), spanCount)
//        recyclerView.layoutManager = layoutManager
//
//        return view
//    }
//
//}
