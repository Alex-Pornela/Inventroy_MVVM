package com.activity.myproductinventroy.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.activity.myproductinventroy.R
import com.activity.myproductinventroy.adapter.ProductAdapter
import com.activity.myproductinventroy.databinding.ActivityMainBinding
import com.activity.myproductinventroy.viewModel.ProductViewModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dialog: Dialog
    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()

        binding.apply {
            addProduct.setOnClickListener{
                openDialog()
            }

            search.setOnClickListener{
                CoroutineScope(Dispatchers.IO).launch{
                    viewModel.downloadData()

                }
            }
        }


    }

    private fun loadData() {
        val productAdapter = ProductAdapter()
        binding.apply {
            recyclerView.apply {
                adapter = productAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
            }
        }
        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        viewModel.getProductLiveData().observe(this, Observer {
            productAdapter.submitList(it)
        })
    }

    private fun openDialog() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.add_product_layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val code = dialog.findViewById<EditText>(R.id.product_code)
        val name = dialog.findViewById<EditText>(R.id.product_name)
        val cancel = dialog.findViewById<Button>(R.id.closeDialog)
        val addBtn = dialog.findViewById<Button>(R.id.addBtn)

        addBtn.setOnClickListener{
            val productCode = code.text.toString()
            val productName = name.text.toString()
            viewModel.addProduct(productCode, productName)
            loadData()
            dialog.dismiss()
        }
        cancel.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }
}