package woowacourse.shopping.ui.productdetail.dialog

import android.app.Dialog
import android.content.Context
import woowacourse.shopping.R
import woowacourse.shopping.databinding.CounterBinding
import woowacourse.shopping.model.ProductModel

class ProductCounterDialog(
    context: Context,
    product: ProductModel,
    putInCart: (count: Int) -> Unit,
) : Dialog(context) {

    init {
        val binding: CounterBinding = CounterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDialogSize(context)
        binding.product = product
        binding.onPutInCart = { count ->
            putInCart(count)
            dismiss()
        }
    }

    private fun initDialogSize(context: Context) {
        val metrics = context.resources.displayMetrics
        val width = (metrics.widthPixels * 0.9).toInt()
        val height = (width * 0.4).toInt()
        window?.setLayout(width, height)
        window?.setBackgroundDrawableResource(R.drawable.shape_woowa_round_4_white_rect)
    }
}
