package com.example.pharmainc.presentation.ui.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmainc.databinding.ItemHomeFragmentBinding
import com.example.pharmainc.presentation.dataBinding.data.PatientData
import com.example.pharmainc.domain.model.Patient
import java.text.SimpleDateFormat

class HomeAdapter(
    private val context: Context,
    private val items: MutableList<Patient> = mutableListOf(),
    var onItemClickListener: (selectItem: Patient) -> Unit = {}
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

    fun update(newItem: List<Patient>) {
        this.items.clear()
        this.items.addAll(newItem)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val viewDataBinding: ItemHomeFragmentBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener {

        private lateinit var itemPatient: Patient

        override fun onClick(view: View?) {
            if (::itemPatient.isInitialized) {
                onItemClickListener(itemPatient)
            }
        }

        init {
            viewDataBinding.clickCardView = this
        }

        fun bind(item: Patient) {
            this.itemPatient = item
            viewDataBinding.itemHome = PatientData(item)
        }
    }
}