package woowacourse.shopping.ui.shopping.recyclerview.adapter.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.model.CartProductModel
import woowacourse.shopping.util.listener.CartProductClickListener
import woowacourse.shopping.widget.SkeletonCounterView

class ProductViewHolder(
    parent: ViewGroup,
    cartProductClickListener: CartProductClickListener,
    counterListener: SkeletonCounterView.OnCountChangedListener,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
) {
    private val binding = ItemProductBinding.bind(itemView)

    init {
        binding.productClickListener = cartProductClickListener
        binding.counterListener = counterListener
    }

    fun bind(cartProduct: CartProductModel) {
        binding.cartProduct = cartProduct
    }
}
