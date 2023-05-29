package com.example.contactrandom.viewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactrandom.databinding.ViewLoaderAdapterBinding
import com.example.contactrandom.utils.basicDiffUtil

/** Created by marlon on 28/5/23. **/
abstract class BaseAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    var listItems: List<T> by basicDiffUtil(
        areItemsTheSame = { old, new -> diffUtilAreItemsTheSame(old, new) }
    )

    constructor(listItems: ArrayList<T>) {
        this.listItems = listItems
    }

    constructor() {
        listItems = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            getViewHolder(parent)
        } else {
            val binding = ViewLoaderAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoadingViewHolder) {
            holder.initViewItem()
        } else {
            setBindViewHolder(holder, listItems[position])
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItemViewTypeIsLoader(position)) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }

    abstract fun getItemViewTypeIsLoader(position: Int): Boolean

    protected abstract fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    protected abstract fun setBindViewHolder(holder: RecyclerView.ViewHolder, data: T)

    protected abstract fun diffUtilAreItemsTheSame(old: T, new: T): Boolean

    abstract fun addItemLoader()

    abstract fun removeItemLoader()

    abstract fun addNewItemsType(list: MutableList<T>)

    class LoadingViewHolder(val bindingLoad: ViewLoaderAdapterBinding) : RecyclerView.ViewHolder(bindingLoad.root) {
        fun initViewItem() {
            bindingLoad.lottieAnimationView.playAnimation()
        }
    }
}