package woowacourse.shopping.domain.model.page

import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.util.safeSubList

typealias DomainPagination = Pagination

class Pagination(
    value: Int = 1,
    sizePerPage: Int = 5,
) : Page(value, sizePerPage) {
    override fun getStartPage(): Page = Pagination(FIRST_PAGE, sizePerPage)

    override fun next(): Page = Pagination(value + 1, sizePerPage)

    override fun update(value: Int): Page = Pagination(value, sizePerPage)

    override fun takeItems(cart: Cart): List<CartProduct> =
        cart.items.safeSubList((value - 1) * sizePerPage, value * sizePerPage)

    companion object {
        private const val FIRST_PAGE = 1
    }
}
