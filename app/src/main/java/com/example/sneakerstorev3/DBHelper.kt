package com.example.sneakerstorev3

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val userTableQuery = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USERNAME_COL + " TEXT, " +
                PASSWORD_COL + " TEXT, " +
                FIRSTNAME_COL + " TEXT, " +
                LASTNAME_COL + " TEXT, " +
                TEL_COL + " TEXT, " +
                ADDRESS_COL + " TEXT" + ")")
        db.execSQL(userTableQuery)

        val cartTableQuery = ("CREATE TABLE " + CART_TABLE_NAME + " ("
                + CART_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_ID_COL + " INTEGER, " +
                PRODUCT_NAME_COL + " TEXT, " +
                PRODUCT_DETAIL_COL + " TEXT, " +
                PRODUCT_PRICE_COL + " REAL, " +
                PRODUCT_QUANTITY_COL + " INTEGER, " +
                PRODUCT_URL_COL + " TEXT" + ")")
        db.execSQL(cartTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " + CART_TABLE_NAME)
        onCreate(db)
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val selection = "$USERNAME_COL = ? AND $PASSWORD_COL = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)

        val count = cursor.count
        cursor.close()
        db.close()
        return count > 0
    }

    fun addUser(username: String, password: String, firstname: String, lastname: String, tel: String, address: String) {
        val values = ContentValues()
        values.put(USERNAME_COL, username)
        values.put(PASSWORD_COL, password)
        values.put(FIRSTNAME_COL, firstname)
        values.put(LASTNAME_COL, lastname)
        values.put(TEL_COL, tel)
        values.put(ADDRESS_COL, address)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getUserId(username: String, password: String): Int {
        val db = this.readableDatabase
        val columns = arrayOf(ID_COL)
        val selection = "$USERNAME_COL = ? AND $PASSWORD_COL = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null)

        var userId = -1 // ค่าเริ่มต้นหากไม่พบผู้ใช้

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL))
        }

        cursor.close()
        db.close()

        return userId
    }

    fun getUserDetails(userId: String): Cursor? {
        val db = this.readableDatabase
        val selection = "$ID_COL = ?"
        val selectionArgs = arrayOf(userId)
        return db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)
    }

    fun deleteCartItemsByUserId(userId: Int) {
        val db = this.writableDatabase
        val whereClause = "$USER_ID_COL = ?"
        val whereArgs = arrayOf(userId.toString())
        db.delete(CART_TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun addToCart(userId: Int, productName: String, productDetail: String, productPrice: Double, productUrl: String) {
        val db = this.writableDatabase
        val columns = arrayOf(CART_ID_COL, PRODUCT_QUANTITY_COL)
        val selection = "$USER_ID_COL = ? AND $PRODUCT_NAME_COL = ? AND $PRODUCT_DETAIL_COL = ?"
        val selectionArgs = arrayOf(userId.toString(), productName, productDetail)
        val cursor = db.query(CART_TABLE_NAME, columns, selection, selectionArgs, null, null, null)

        if (cursor.moveToFirst()) {
            // Item with the same Product Name and Detail already exists; increment the quantity
            val cartId = cursor.getInt(cursor.getColumnIndexOrThrow(CART_ID_COL))
            val currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_QUANTITY_COL))

            val updatedQuantity = currentQuantity + 1

            val values = ContentValues()
            values.put(PRODUCT_QUANTITY_COL, updatedQuantity)
            values.put(PRODUCT_URL_COL, productUrl) // เพิ่ม URL สินค้า

            db.update(CART_TABLE_NAME, values, "$CART_ID_COL = ?", arrayOf(cartId.toString()))
        } else {
            // Item does not exist; add a new row
            val values = ContentValues()
            values.put(USER_ID_COL, userId)
            values.put(PRODUCT_NAME_COL, productName)
            values.put(PRODUCT_DETAIL_COL, productDetail)
            values.put(PRODUCT_PRICE_COL, productPrice)
            values.put(PRODUCT_QUANTITY_COL, 1) // Set initial quantity to 1
            values.put(PRODUCT_URL_COL, productUrl) // เพิ่ม URL สินค้า

            db.insert(CART_TABLE_NAME, null, values)
        }

        cursor.close()
        db.close()
    }


    fun getCartItemsByUserId(userId: Int): Cursor? {
        val db = this.readableDatabase
        val selection = "$USER_ID_COL = ?"
        val selectionArgs = arrayOf(userId.toString())
        return db.query(CART_TABLE_NAME, null, selection, selectionArgs, null, null, null)
    }

    fun updateCartItemQuantity(userId: Int, productName: String, productDetail: String, quantityChange: Int) {
        val db = this.writableDatabase
        val columns = arrayOf(CART_ID_COL, PRODUCT_QUANTITY_COL)
        val selection = "$USER_ID_COL = ? AND $PRODUCT_NAME_COL = ? AND $PRODUCT_DETAIL_COL = ?"
        val selectionArgs = arrayOf(userId.toString(), productName, productDetail)
        val cursor = db.query(CART_TABLE_NAME, columns, selection, selectionArgs, null, null, null)

        if (cursor.moveToFirst()) {
            val cartId = cursor.getInt(cursor.getColumnIndexOrThrow(CART_ID_COL))
            val currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_QUANTITY_COL))

            // คำนวณจำนวนที่ต้องการเปลี่ยนแปลง
            val updatedQuantity = currentQuantity + quantityChange

            if (updatedQuantity <= 0) {
                // ถ้าจำนวนสินค้าที่เหลือเป็น 0 หรือน้อยกว่า 0 ให้ลบรายการนี้ออกจากตะกร้า
                db.delete(CART_TABLE_NAME, "$CART_ID_COL = ?", arrayOf(cartId.toString()))
            } else {
                // ถ้าจำนวนสินค้าที่เหลือมากกว่า 0 ให้อัปเดตจำนวนสินค้าในตะกร้า
                val values = ContentValues()
                values.put(PRODUCT_QUANTITY_COL, updatedQuantity)

                db.update(CART_TABLE_NAME, values, "$CART_ID_COL = ?", arrayOf(cartId.toString()))
            }
        }

        cursor.close()
        db.close()
    }


    companion object {
        private val DATABASE_NAME = "sneaker_store.db"
        private val DATABASE_VERSION = 3
        val TABLE_NAME = "users"
        val ID_COL = "id"
        val USERNAME_COL = "username"
        val PASSWORD_COL = "password"
        val FIRSTNAME_COL = "firstname"
        val LASTNAME_COL = "lastname"
        val TEL_COL = "tel"
        val ADDRESS_COL = "address"

        val CART_TABLE_NAME = "cart"
        val CART_ID_COL = "cart_id"
        val USER_ID_COL = "user_id"
        val PRODUCT_NAME_COL = "product_name"
        val PRODUCT_DETAIL_COL = "product_detail"
        val PRODUCT_PRICE_COL = "product_price"
        val PRODUCT_QUANTITY_COL = "product_quantity"
        val PRODUCT_URL_COL = "product_url"  // เพิ่มคอลัมน์ URL สินค้า
    }
}






