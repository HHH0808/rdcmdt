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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cupcake.databinding.FragmentFlavorBinding
import com.example.cupcake.model.OrderViewModel
import com.example.cupcake.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * [FlavorFragment] allows a user to choose a cupcake flavor for the order.
 */
class FlavorFragment : Fragment() {

    // Binding object instance corresponding to the fragment_flavor.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentFlavorBinding? = null

    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentFlavorBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerview = view.findViewById<RecyclerView>(R.id.transactionContent)
        val data = mutableListOf<TransactionData>()
        //Log.d("OCBC: ", sharedViewModel.sharedTransactionResult.data.toString())
        for (i in 0..1) {
            data.add(
                TransactionData(
                    transactionId = sharedViewModel.sharedTransactionResult.data[i].transactionId,
                    amount = sharedViewModel.sharedTransactionResult.data[i].amount,
                    transactionDate = sharedViewModel.sharedTransactionResult.data[i].transactionDate,
                    description = sharedViewModel.sharedTransactionResult.data[i].description,
                    transactionType = sharedViewModel.sharedTransactionResult.data[i].transactionType,
                    receipient = sharedViewModel.sharedTransactionResult.data[i].receipient,
                    sender = sharedViewModel.sharedTransactionResult.data[i].sender
                )
            )
        }

        var adapter = ClassViewDesign(data)
        recyclerview.adapter = adapter

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            flavorFragment = this@FlavorFragment
        }
    }

    /**
     * Navigate to the next screen to choose pickup date.
     */
    fun goToNextScreen() {
        val API = RetrofitHelper.getInstance().create(LoginAPI::class.java)
        // launching a new coroutine
        GlobalScope.launch {
            withContext(Dispatchers.Main){
                sharedViewModel.setPayeeResult(API.getPayee().body())
                //Log.d("OCBC: ", sharedViewModel.sharedPayeeResult.toString())
                findNavController().navigate(R.id.action_flavorFragment_to_pickupFragment)
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

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_flavorFragment_to_startFragment)
    }
}