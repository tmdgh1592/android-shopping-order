package woowacourse.shopping.model.mapper

import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.model.UiRecentProduct

fun UiRecentProduct.toDomain(): RecentProduct =
    RecentProduct(id = id, product = product.toDomain())

fun RecentProduct.toUi(): UiRecentProduct =
    UiRecentProduct(id = id, product = product.toUi())

fun List<RecentProduct>.toUi(): List<UiRecentProduct> =
    map { recentProduct -> recentProduct.toUi() }
