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
        fun navigateToProductDetail(product: UiProduct, recentProduct: UiRecentProduct?)
        fun navigateToCart()
        fun showLoadMoreButton()
        fun hideLoadMoreButton()
        fun updateCartBadge(count: ProductCount)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun fetchAll()
        abstract fun fetchRecentProducts()
        abstract fun loadMoreProducts()
        abstract fun inquiryProductDetail(cartProduct: UiCartProduct)
        abstract fun inquiryRecentProductDetail(recentProduct: UiRecentProduct)
        abstract fun navigateToCart()
        abstract fun changeCartCount(cartProduct: UiCartProduct, changedCount: Int)
        abstract fun addCartProduct(cartProduct: UiCartProduct)
        abstract fun addCartCount(cartProduct: UiCartProduct, addCount: Int)
    }
}
