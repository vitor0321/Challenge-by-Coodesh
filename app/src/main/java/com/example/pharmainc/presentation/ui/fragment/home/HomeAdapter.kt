package com.example.pharmainc.presentation.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmainc.databinding.ItemHomeFragmentBinding
import com.example.pharmainc.presentation.dataBinding.data.PatientData
import com.example.pharmainc.presentation.model.Patient

class HomeAdapter(
    var onClickListener: (patient: Patient) -> Unit = {}
) : ListAdapter<Patient, HomeAdapter.PatientViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        return PatientViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    class PatientViewHolder(
        private val itemBinding: ItemHomeFragmentBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(patient: Patient, onClickListener: (Patient) -> Unit) {
            itemBinding.run {
                itemHome = PatientData(patient)
                cardView.setOnClickListener {
                    onClickListener(patient)
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup): PatientViewHolder {
                val itemBinding = ItemHomeFragmentBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return PatientViewHolder(itemBinding)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Patient>() {
            override fun areItemsTheSame(
                oldItem: Patient,
                newItem: Patient
            ): Boolean {
                return oldItem.idIdentification == newItem.idIdentification
            }

            override fun areContentsTheSame(
                oldItem: Patient,
                newItem: Patient
            ): Boolean {
                return oldItem.idIdentification == newItem.idIdentification
            }

        }
    }
}