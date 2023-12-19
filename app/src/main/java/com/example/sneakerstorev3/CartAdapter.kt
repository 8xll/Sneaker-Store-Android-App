import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sneakerstorev3.CartData
import com.example.sneakerstorev3.R

class CartAdapter(
    private val products: List<CartData>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onAddButtonClick(item: CartData)
        fun onDeleteButtonClick(item: CartData)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.itemImage)
        val productName: TextView = itemView.findViewById(R.id.itemName)
        val productDetail: TextView = itemView.findViewById(R.id.itemDetail)
        val productPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val productQuantity: TextView = itemView.findViewById(R.id.itemQuantity)
        val addButton: Button = itemView.findViewById(R.id.plus)
        val deleteButton: Button = itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        // Set data for the product
        Glide.with(holder.productImage.context)
            .load(product.imageUrl) // URL รูปภาพ
            .placeholder(R.drawable.icon_product) // รูปภาพ placeholder สำหรับตอนโหลด
            .error(R.drawable.icon_product) // รูปภาพที่ใช้เมื่อโหลดไม่สำเร็จ
            .into(holder.productImage)

        holder.productName.text = product.name
        holder.productDetail.text = product.detail
        holder.productPrice.text = product.price.toString()
        holder.productQuantity.text = product.quatity.toString()


        holder.addButton.setOnClickListener {
            itemClickListener.onAddButtonClick(product)
        }
        holder.deleteButton.setOnClickListener {
            itemClickListener.onDeleteButtonClick(product)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
