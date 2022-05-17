/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentPickupBinding
import com.example.cupcake.model.OrderViewModel
import com.example.cupcake.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * [PickupFragment] allows the user to choose a pickup date for the cupcake order.
 */
class PickupFragment : Fragment() {

    // Binding object instance corresponding to the fragment_pickup.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentPickupBinding? = null

    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentPickupBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val payeeSize = sharedViewModel.sharedPayeeResult.data.size -1

        fun getPayeesObjects(): ArrayList<PayeeData> {
            val payeesObjects = ArrayList<PayeeData>()
            payeesObjects.apply {
                for (x in 0..payeeSize) {
                    add(PayeeData(sharedViewModel.sharedPayeeResult.data[x].id, sharedViewModel.sharedPayeeResult.data[x].name, sharedViewModel.sharedPayeeResult.data[x].accountNo))
                }
            }
            return payeesObjects
        }


        val spinner = view.findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val spinAdapter = ArrayAdapter(
                view.context,
                android.R.layout.simple_spinner_item, getPayeesObjects()
            )
            spinner.adapter = spinAdapter
        }

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                var selectedObject = spinner.selectedItem as PayeeData
                sharedViewModel.setPayee(selectedObject.name, selectedObject.accountNo)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(activity, "Choose a payee", Toast.LENGTH_SHORT).show()
            }
        }


        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            pickupFragment = this@PickupFragment
        }
    }

    /**
     * Navigate to the next screen to see the order summary.
     */
    fun transferFund() {
        sharedViewModel.setTransferAmount(binding?.amount?.editableText.toString().toDouble())
        sharedViewModel.setDescriptions(binding?.descriptions?.editableText.toString())

        val transferBody = TransferBody(sharedViewModel.receipientAccNo, sharedViewModel.amount, sharedViewModel.desc)
        val API = RetrofitHelper.getInstance().create(LoginAPI::class.java)

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                sharedViewModel.setTransferResult(API.transferFunds(transferBody).body())
                //Log.d("OCBC: ", sharedViewModel.sharedTransferResult.toString())
                sharedViewModel.setTransactionResult(API.getTransactions().body())
                findNavController().navigate(R.id.action_pickupFragment_to_flavorFragment)
                Toast.makeText(activity, "Transferred", Toast.LENGTH_SHORT).show()

            }
        }
    }


    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}