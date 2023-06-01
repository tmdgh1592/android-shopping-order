package woowacourse.shopping.util.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder

@BindingAdapter(
    "bind:adapter",
    "bind:onAdapted",
    requireAll = false
)
fun RecyclerView.setAdapter(adapter: Adapter<ViewHolder>, onAdapted: () -> Unit) {
    this.adapter = adapter
    onAdapted.invoke()
}

@BindingAdapter("bind:fixedSize")
fun RecyclerView.setFixedSize(fixedSize: Boolean) {
    setHasFixedSize(fixedSize)
}

@BindingAdapter("bind:layoutManager")
fun RecyclerView.setLayoutManager(layoutManager: LayoutManager) {
    this.layoutManager = layoutManager
}

@BindingAdapter("bind:animator")
fun RecyclerView.setAnimator(itemAnimator: RecyclerView.ItemAnimator?) {
    this.itemAnimator = itemAnimator
}
