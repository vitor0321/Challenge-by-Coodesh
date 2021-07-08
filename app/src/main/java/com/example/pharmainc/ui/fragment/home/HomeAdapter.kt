package com.example.pharmainc.ui.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmainc.databinding.ItemHomeFragmentBinding
import com.example.pharmainc.ui.dataBinding.ItemPatientData
import com.example.pharmainc.ui.model.ItemPatient

class HomeAdapter(
    private val context: Context,
    private val items: MutableList<ItemPatient> = mutableListOf(),
    var onItemClickListener: (selectItem: ItemPatient) -> Unit = {}
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = ItemHomeFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun update(newItem: List<ItemPatient>) {
        this.items.clear()
        this.items.addAll(newItem)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val viewDataBinding: ItemHomeFragmentBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener {

        private lateinit var itemPhoto: ItemPatient

        override fun onClick(view: View?) {
            if (::itemPhoto.isInitialized) {
                onItemClickListener(itemPhoto)
            }
        }

        init {
            viewDataBinding.clickCardView = this
        }

        fun bind(item: ItemPatient) {
            this.itemPhoto = item
            viewDataBinding.itemHome = ItemPatientData(item)
        }
    }
}