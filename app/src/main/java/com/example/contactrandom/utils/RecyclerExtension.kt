package com.example.contactrandom.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactrandom.events.EventReturnBoolean

/** Created by marlon on 28/5/23. **/
fun RecyclerView.scrollLogicPag(linearLayoutManager: LinearLayoutManager, eventLoadMoreItems: EventReturnBoolean){
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy > 0) {
                eventLoadMoreItems.selection(loadMoreItemsList(linearLayoutManager))
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                eventLoadMoreItems.selection(loadMoreItemsList(linearLayoutManager))
            }
        }
    })
}

private fun loadMoreItemsList(linearLayoutManager: LinearLayoutManager):Boolean {
    val currentItemOnScreen = linearLayoutManager.childCount
    val totalItem = linearLayoutManager.itemCount
    val scrollOutITem = linearLayoutManager.findFirstVisibleItemPosition()

    return (currentItemOnScreen + scrollOutITem == totalItem)
}
