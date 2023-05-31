package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.CartProduct
import woowacourse.shopping.model.ProductCount
import woowacourse.shopping.model.UiCartProduct
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct

interface ShoppingContract {
    interface View {
        fun updateProducts(products: List<CartProduct>)
        fun updateRecentProducts(recentProducts: List<UiRecentProduct>)
        fun navigateToProductDetail(product: UiProduct)
        fun navigateToCart()
        fun showLoadMoreButton()
        fun hideLoadMoreButton()
        fun updateCartBadge(count: ProductCount)
        fun showErrorMessage(message: String)
        fun navigateToOrderList()
    }

    abstract class Presenter(protected val view: View) {
        abstract fun fetchAll()
        abstract fun fetchRecentProducts()
        abstract fun loadMoreProducts()
        abstract fun inquiryProductDetail(cartProduct: UiCartProduct)
        abstract fun inquiryRecentProductDetail(recentProduct: UiRecentProduct)
        abstract fun navigateToCart()
        abstract fun updateCartCount(cartProduct: UiCartProduct, changedCount: Int)
        abstract fun addCartProduct(product: UiProduct, addCount: Int = 1)
        abstract fun increaseCartCount(product: UiProduct, addCount: Int)
        abstract fun inquiryOrders()
    }
}
