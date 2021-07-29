package com.example.pharmainc.presentation.dataBinding.data

import androidx.lifecycle.MutableLiveData
import com.example.pharmainc.domain.model.ItemCheckGender

class ItemCheckGenderData(
    private var checkGender: ItemCheckGender = ItemCheckGender(),
    val female: MutableLiveData<Boolean> = MutableLiveData<Boolean>().also {
        it.value = checkGender.female
    },
    val male: MutableLiveData<Boolean> = MutableLiveData<Boolean>().also {
        it.value = checkGender.male
    }
) {
    fun setCheckGenderData(checkGender: ItemCheckGender) {
        this.checkGender = checkGender
        female.postValue(this.checkGender.female)
        male.postValue(this.checkGender.male)
    }

    fun getCheckGenderData(): ItemCheckGender? {
        return this.checkGender.copy(
            female = female.value ?: return null,
            male = male.value ?: return null
        )
    }
}