package com.activity.myproductinventroy.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.activity.myproductinventroy.model.Product
import com.activity.myproductinventroy.repository.ApiRequest
import kotlinx.coroutines.delay


class ProductViewModel : ViewModel(), ApiRequest.ViewData  {

    private val productLiveData = MutableLiveData<List<Product>>()
    private var repo = ApiRequest(this)

    init {
        repo.viewProduct()
    }

    fun getProductLiveData(): LiveData<List<Product>>{
        return productLiveData
    }

    fun addProduct(productCode: String, productName: String) {
        repo.addProduct(productCode, productName)
    }

    suspend fun downloadData(){
        //var x = 0
        for(i in 0..1000000){
            Log.i("tag","Downloading User $i")
            //x++
        }
        delay(5000L)

    }


    override fun onViewProduct(productList: List<Product>) {
        productLiveData.postValue(productList)
    }

}