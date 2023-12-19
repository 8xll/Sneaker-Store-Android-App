import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sneakerstorev3.R

class ProductAdapter(
    private val products: List<ProductData>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onBuyButtonClick(item: ProductData)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productDetail: TextView = itemView.findViewById(R.id.product_detail)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val buyButton: Button = itemView.findViewById(R.id.add_to_cart_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        // ใช้ Glide เพื่อโหลดและแสดงรูปภาพจาก URL
        Glide.with(holder.productImage.context)
            .load(product.imageUrl) // URL รูปภาพ
            .placeholder(R.drawable.icon_product) // รูปภาพ placeholder สำหรับตอนโหลด
            .error(R.drawable.icon_product) // รูปภาพที่ใช้เมื่อโหลดไม่สำเร็จ
            .into(holder.productImage)

        holder.productName.text = product.name
        holder.productDetail.text = product.detail
        holder.productPrice.text = product.price.toString()

        holder.buyButton.setOnClickListener {
            itemClickListener.onBuyButtonClick(product)
        }
    }


//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val product = products[position]
//        // Set data for the product
//        holder.productImage.setImageResource(product.imageResId)
//        holder.productName.text = product.name
//        holder.productDetail.text = product.detail
//        holder.productPrice.text = product.price.toString()
//
//        holder.buyButton.setOnClickListener {
//            itemClickListener.onBuyButtonClick(product)
//        }
//    }

    override fun getItemCount(): Int {
        return products.size
    }
}
