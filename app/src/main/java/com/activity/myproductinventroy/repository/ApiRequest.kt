package com.activity.myproductinventroy.repository

import android.util.JsonReader
import android.util.Log
import com.activity.myproductinventroy.model.Product
import com.activity.myproductinventroy.model.ProductUnit
import okhttp3.*

import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.util.ArrayList

class ApiRequest(var viewData: ViewData) {
    private val mClient = OkHttpClient()
    var productList: ArrayList<Product> = ArrayList<Product>()
    var unitList: ArrayList<ProductUnit> = ArrayList<ProductUnit>()


    fun viewProduct(){
        val request = Request.Builder().url(URLManager.PRODUCT_LIST_API).build()
        mClient.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                Log.i("tag", "RECIEVED INFO"+response.body.toString())
                if (response.isSuccessful) {
                    val data = response.body?.string()
                    try{
                        val products = JSONArray(data)
                        for (i in 0 until products.length()) {
                            val product = products.getJSONObject(i)
                            val id = product.getString("id")
                            val name = product.getString("product_name")
                            val code = product.getString("product_code")

                            val productUnit = product.getJSONArray("product_unit")
                            for (k in 0 until productUnit.length()) {
                                val unit = productUnit.getJSONObject(k)
                                val unitID = unit.getString("id")
                                val productID = unit.getString("product_id")
                                val productName = unit.getString("product_name")
                                val unitMeasure = unit.getString("unit_of_measure")
                                val price = unit.getString("price")

                                unitList.add(
                                    ProductUnit(
                                        unitID,
                                        productID,
                                        price,
                                        productName,
                                        unitMeasure
                                    )
                                )
                            }
                            productList.add(Product(id, name, code, unitList))
                        }
                        viewData.onViewProduct(productList)
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }

                }
            }
        })
    }

    fun addProduct(productCode: String, productName: String) {
        val requestBody: RequestBody = FormBody.Builder()
            .add("product_code", productCode)
            .add("product_name", productName)
            .build()
        val request: Request = Request.Builder()
            .url(URLManager.ADD_PRODUCT)
            .post(requestBody)
            .build()
        mClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
            }
        })
    }

    interface ViewData{
        fun onViewProduct(productList: List<Product>)
    }

}