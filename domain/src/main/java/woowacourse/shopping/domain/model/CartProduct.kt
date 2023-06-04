package woowacourse.shopping.domain.model

data class CartProduct(
    val id: Int = 0,
    val product: Product,
    val selectedCount: ProductCount = ProductCount(0),
    val isChecked: Boolean = true,
) {
    val productId: Int = product.id

    fun plusCount(count: Int = 1): CartProduct =
        copy(selectedCount = selectedCount + count)

    fun minusCount(count: Int = 1): CartProduct =
        copy(selectedCount = selectedCount - count)

    fun changeCount(count: Int): CartProduct =
        copy(selectedCount = selectedCount.changeCount(count))

    fun select(): CartProduct =
        copy(isChecked = true)

    fun unselect(): CartProduct =
        copy(isChecked = false)

    fun getTotalPrice(onlyChecked: Boolean): Int {
        if (onlyChecked && isChecked) {
            return product.price.value * selectedCount.value
        }
        return 0
    }
}
