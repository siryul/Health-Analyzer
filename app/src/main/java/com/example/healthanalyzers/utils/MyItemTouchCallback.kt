package com.example.healthanalyzers.utils

// 设置对 recyclerView 中的 item 实现向左滑动删除该 item
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MyItemTouchCallback(private val itemCallback: ItemTouchResultCallback) :
    ItemTouchHelper.Callback() {

    // 设置可以触发对应事件的操作
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) = makeMovementFlags(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.START
    )

    // 可以实现对 item 的自定义排序，若有必要可以实现，当前暂未实现
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    // 左右滑动实现方法
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // 实现滑动删除的真正实现代码
        itemCallback.onItemDelete(viewHolder.adapterPosition)
    }

    // 规定实际使用时实现对 item 的操作需要实现的接口
    interface ItemTouchResultCallback {
        fun onItemMove(startPosition: Int, endPosition: Int)
        fun onItemDelete(position: Int)
    }
}