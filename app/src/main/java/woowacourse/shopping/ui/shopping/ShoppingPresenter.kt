package woowacourse.shopping.ui.shopping

import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.DomainCartProduct
import woowacourse.shopping.domain.model.ProductCount
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.model.RecentProducts
import woowacourse.shopping.domain.model.page.LoadMore
import woowacourse.shopping.domain.model.page.Page
import woowacourse.shopping.domain.repository.CartRemoteRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUi
import woowacourse.shopping.model.UiCartProduct
import woowacourse.shopping.model.UiProductCount
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.ui.shopping.ShoppingContract.Presenter
import woowacourse.shopping.ui.shopping.ShoppingContract.View

class ShoppingPresenter(
    view: View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRemoteRepository,
    private val recentProductSize: Int = 10,
    private var recentProducts: RecentProducts = RecentProducts(),
    sizePerPage: Int = 20,
) : Presenter(view) {
    private var cart = Cart()
    private var currentPage: Page = LoadMore(sizePerPage = sizePerPage)
    private val cartProductCount: UiProductCount
        get() = UiProductCount(cart.totalCartProductCount)

    override fun fetchAll() {
        updateCart(newCartProducts = loadAllCartProducts())
        fetchRecentProducts()
    }

    override fun fetchRecentProducts() {
        updateRecentProducts(recentProductRepository.getPartially(recentProductSize))
    }

    override fun loadMoreProducts() {
        currentPage = currentPage.next()
        updateCartView()
    }

    override fun inquiryProductDetail(cartProduct: UiCartProduct) {
        val recentProduct = RecentProduct(product = cartProduct.product.toDomain())
        view.navigateToProductDetail(cartProduct.product, recentProducts.getLatest()?.toUi())
        recentProductRepository.add(recentProduct)
        updateRecentProducts(recentProducts + recentProduct)
    }

    override fun inquiryRecentProductDetail(recentProduct: UiRecentProduct) {
        view.navigateToProductDetail(recentProduct.product, recentProducts.getLatest()?.toUi())
        recentProductRepository.add(recentProduct.toDomain())
    }

    override fun navigateToCart() {
        view.navigateToCart()
    }

    override fun addCartProduct(cartProduct: UiCartProduct) {
        cartRepository.addCartProductByProductId(cartProduct.toDomain().productId)
        updateCart(newCartProducts = loadAllCartProducts())
    }

    override fun changeCartCount(cartProduct: UiCartProduct, changedCount: Int) {
        cartRepository.updateProductCountById(cartProduct.toDomain().id, ProductCount(changedCount))
        updateCart(newCartProducts = loadAllCartProducts())
    }

    override fun addCartCount(cartProduct: UiCartProduct, addCount: Int) {
        if (cartProduct.selectedCount.value == 0) {
            addCartProduct(cartProduct)
            changeCartCount(cartProduct, cartProduct.selectedCount.value + addCount - 1)
        } else {
            changeCartCount(cartProduct, cartProduct.selectedCount.value + addCount)
        }

        updateCart(newCartProducts = loadAllCartProducts())
    }

    private fun loadAllCartProducts(): List<CartProduct> {
        val products = productRepository.getAllProducts()
        val cartProducts = cartRepository.getAllCartProducts()

        return products.map { product ->
            cartProducts.find { it.productId == product.id } ?: DomainCartProduct(
                product = product,
                selectedCount = ProductCount(0)
            )
        }
    }

    private fun updateCart(newCartProducts: List<CartProduct>) {
        cart = cart.update(newCartProducts)
        updateCartView()
    }

    private fun updateCartView() {
        view.updateProducts(currentPage.takeItems(cart).toUi())
        view.updateCartBadge(cartProductCount)
        view.updateLoadMoreVisible()
    }

    private fun View.updateLoadMoreVisible() {
        if (currentPage.hasNext(cart)) showLoadMoreButton() else hideLoadMoreButton()
    }

    private fun updateRecentProducts(newRecentProducts: RecentProducts) {
        recentProducts = recentProducts.update(newRecentProducts)
        view.updateRecentProducts(recentProducts.getItems().toUi())
    }
}
