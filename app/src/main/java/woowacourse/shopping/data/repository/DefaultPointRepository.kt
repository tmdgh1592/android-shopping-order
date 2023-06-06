package woowacourse.shopping.data.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import woowacourse.shopping.data.dto.PointResponse
import woowacourse.shopping.data.service.point.PointService
import woowacourse.shopping.domain.model.discount.Point
import woowacourse.shopping.domain.repository.PointRepository

class DefaultPointRepository(private val service: PointService) : PointRepository {

    override fun getPoint(
        onSuccess: (Point) -> Unit,
        onFailed: (Throwable) -> Unit,
    ) {
        service.getPoint().enqueue(object : Callback<PointResponse> {
            override fun onResponse(call: Call<PointResponse>, response: Response<PointResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    onSuccess(Point(responseBody.availablePoint))
                    return
                }
                onFailed(Throwable(response.message()))
            }

            override fun onFailure(call: Call<PointResponse>, throwable: Throwable) {
                onFailed(throwable)
            }
        })
    }
}
