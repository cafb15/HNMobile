package com.macary.hnmobile.utils.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.macary.hnmobile.databinding.ItemNewsBinding
import com.macary.hnmobile.utils.extensions.inflater
import com.macary.hnmobile.utils.extensions.onClick

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
abstract class AbstractAdapter<ITEM>(protected val items: List<ITEM>, private val layoutResId: Int)
    : RecyclerView.Adapter<AbstractAdapter.Holder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val holder = Holder(ItemNewsBinding.inflate(parent.inflater(), parent, false))
        val itemView = holder.itemView

        itemView.onClick {
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onItemClick(itemView, adapterPosition)
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]

        holder.bind(item, position)
    }

    fun update(items: List<ITEM>) {
        updateAdapterWithDiffResult(calculateDiff(items))
    }

    private fun updateAdapterWithDiffResult(result: DiffUtil.DiffResult) {
        result.dispatchUpdatesTo(this)
    }

    private fun calculateDiff(newItems: List<ITEM>) = DiffUtil.calculateDiff(DiffUtilCallback(items, newItems))

    fun add(item: ITEM) {
        items.toMutableList().add(item)
        notifyItemInserted(items.size)
    }

    fun addAll(items: List<ITEM>) {
        items.toMutableList().addAll(items)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        items.toMutableList().removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: ITEM, position: Int) {
        items.toMutableList().add(position, item)
        notifyItemInserted(position)
    }

    fun clear() {
        items.toMutableList().clear()
    }

    fun getItem(position: Int): ITEM = items[position]

    protected open fun onItemClick(itemView: View, position: Int) {}

    protected open fun Holder.bind(item: ITEM, position: Int) {}

    class Holder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)
}