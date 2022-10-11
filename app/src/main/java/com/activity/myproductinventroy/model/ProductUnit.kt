package com.activity.myproductinventroy.model

data class ProductUnit(
    private var id_: String,
    private var product_id: String,
    private var price: String,
    private var product_name: String,
    private var unit_measure: String ){

    val id: String get() = id_
    val productID: String get() = product_id
    val unitPrice: String get() = price
    val productName: String get() = product_name
    val unitMeasure: String get() = unit_measure
}
