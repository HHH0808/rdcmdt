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
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentStartBinding
import com.example.cupcake.model.OrderViewModel
import com.example.cupcake.network.AuthHeader
import com.example.cupcake.network.LoginAPI
import com.example.cupcake.network.LoginBody
import com.example.cupcake.network.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.http.Header

/**
 * This is the first screen of the Cupcake app. The user can choose how many cupcakes to order.
 */
class StartFragment : Fragment() {

    // Binding object instance corresponding to the fragment_start.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentStartBinding? = null

    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.startFragment = this
        binding?.apply {
            //loginPageWord.setOnClickListener { login() }
            //registerButtonWord.setOnClickListener { registerCustomer() }
        }
    }

    /**
     * Start an order with the desired quantity of cupcakes and navigate to the next screen.
     */
    fun login() {
        sharedViewModel.setUsername(binding?.username?.editableText.toString())
        sharedViewModel.setPassword(binding?.password?.editableText.toString())
        val loginbody = LoginBody(sharedViewModel.userName, sharedViewModel.passWord)
        val API = RetrofitHelper.getInstance().create(LoginAPI::class.java)
        // launching a new coroutine
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                sharedViewModel.setLoginResult(API.loginUser(loginbody).body())
                sharedViewModel.setBalanceResult(API.getBalance(Authorization = sharedViewModel.sharedLoginResult.token.toString()).body())
                sharedViewModel.setTransactionResult(API.getTransactions(Authorization = sharedViewModel.sharedLoginResult.token.toString()).body())
                //Log.d("OCBC: ", sharedViewModel.sharedLoginResult.toString())
                //Log.d("OCBC: ", sharedViewModel.sharedBalanceResult.toString())
                //Log.d("OCBC: ", sharedViewModel.sharedTransactionResult.toString())


                findNavController().navigate(R.id.action_startFragment_to_flavorFragment)
            }
        }
    }

    fun registerCustomer() {
        findNavController().navigate(R.id.action_startFragment_to_summaryFragment)
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