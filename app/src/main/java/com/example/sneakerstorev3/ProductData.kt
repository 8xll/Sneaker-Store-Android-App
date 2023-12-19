//data class ProductData(
////    val imageResId: Int,  // รูปภาพสินค้า (เช่น R.drawable.product_image)
//    val imageUrl: String,
//    val name: String,      // ชื่อสินค้า
//    val detail: String,    // รายละเอียดสินค้า
//    val price: Double,   // ราคาสินค้า
//)

data class ProductData(
    val imageUrl: String, // URL รูปภาพ
    val name: String,
    val detail: String,
    val price: Double
)
