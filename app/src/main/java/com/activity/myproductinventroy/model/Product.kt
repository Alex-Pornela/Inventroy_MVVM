package com.activity.myproductinventroy.model

data class Product(
    private var id_: String,
    private var product_name: String,
    private var product_code: String,
    private var product_unit: ArrayList<ProductUnit>){

    val id: String get() = id_
    val productName: String get() = product_name
    val productCode: String get() = product_code
    val productUnit: ArrayList<ProductUnit> get() = product_unit
}
