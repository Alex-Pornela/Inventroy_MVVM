package com.activity.myproductinventroy.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.activity.myproductinventroy.model.Product
import com.activity.myproductinventroy.repository.ApiRequest


class ProductViewModel : ViewModel(), ApiRequest.ViewData  {

    private val productLiveData = MutableLiveData<List<Product>>()

    init {
        val repo = ApiRequest(this)
        repo.viewProduct()
    }

    fun getProductLiveData(): LiveData<List<Product>>{
        return productLiveData
    }


    override fun onViewProduct(productList: List<Product>) {
        productLiveData.postValue(productList)
    }

}