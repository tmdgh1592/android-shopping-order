package woowacourse.shopping.ui.cart

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.model.page.Page
import woowacourse.shopping.domain.model.page.Pagination
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.model.PriceModel
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.model.mapper.toDomain

internal class CartPresenterTest {
    private lateinit var presenter: CartContract.Presenter
    private lateinit var view: CartContract.View
    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        cartRepository = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        view = mockk(relaxed = true)
        presenter = CartPresenter(view, productRepository, cartRepository)
    }

    @Test
    internal fun 장바구니를_목록을_갱신하면_현재_페이지에_해당하는_아이템을_보여주고_네비게이터를_갱신한다() {
        // given
        val page = 1

        // when
        presenter.fetchCart(page)

        // then
        verify(exactly = 1) { view.updateCart(any()) }
        verify(exactly = 1) { view.updateNavigatorEnabled(any(), any()) }
        verify(exactly = 1) { view.updatePageNumber(any()) }
    }

    @Test
    internal fun 이전_장바구니를_불러오면_페이지를_변경하고_장바구니를_갱신한다() {
        // given
        val page = 2
        presenter = CartPresenter(view, productRepository, cartRepository)

        val currentPage = slot<Page>()

        // when
        presenter.fetchCart(page - 1)

        // then
        assertEquals(currentPage.captured, Pagination(page - 1))
        verify(exactly = 1) { view.updateCart(any()) }
        verify(exactly = 1) { view.updateNavigatorEnabled(any(), any()) }
        verify(exactly = 1) { view.updatePageNumber(any()) }
    }

    @Test
    internal fun 다음_장바구니를_불러오면_페이지를_변경하고_장바구니를_갱신한다() {
        // given
        val page = 1
        presenter = CartPresenter(view, productRepository, cartRepository)

        val currentPage = slot<Page>()

        // when
        presenter.fetchCart(page + 1)

        // then
        assertEquals(currentPage.captured, Pagination(page + 1))
        verify(exactly = 1) { view.updateCart(any()) }
        verify(exactly = 1) { view.updateNavigatorEnabled(any(), any()) }
        verify(exactly = 1) { view.updatePageNumber(any()) }
    }

    @Test
    internal fun 장바구니_목록에_있는_제품을_제거하면_뷰를_갱신한다() {
        // given
        val products = MutableList(8) { id ->
            ProductModel(id, "상품 $id", PriceModel(1000), "")
        }
        val product = ProductModel(0, "상품 0", PriceModel(1000), "")
        every { cartRepository.decreaseCartCount(product.toDomain(), 1) } answers {
            products.remove(product)
        }

        // when
        presenter.deleteProduct(product)

        // then
        verify(exactly = 1) { cartRepository.decreaseCartCount(product.toDomain(), 1) }
        verify(exactly = 1) { view.updateCart(any()) }
        verify(exactly = 1) { view.updateNavigatorEnabled(any(), any()) }
        verify(exactly = 1) { view.updatePageNumber(any()) }
    }

    @Test
    internal fun 종료하면_화면을_닫는다() {
        // given
        /* ... */

        // when
        presenter.navigateToHome()

        // then
        verify(exactly = 1) { view.navigateToHome() }
    }
}
