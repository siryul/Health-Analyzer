package com.example.healthanalyzers.utils

// 设置对 recyclerView 中的 item 实现向左滑动删除该 item
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MyItemTouchCallback(val itemCallback: ItemTouchResultCallback) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) = makeMovementFlags(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.START
    )

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // 实现滑动删除的真正实现代码
        itemCallback.onItemDelete(viewHolder.adapterPosition)
    }

    interface ItemTouchResultCallback {
        fun onItemMove(startPosition: Int, endPosition: Int)
        fun onItemDelete(position: Int)
    }
}