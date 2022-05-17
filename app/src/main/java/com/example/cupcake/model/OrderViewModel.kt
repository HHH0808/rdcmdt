package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.cupcake.network.*
import java.util.*
import java.util.Locale
import java.text.SimpleDateFormat
import java.util.Calendar
import java.text.NumberFormat

class OrderViewModel: ViewModel() {
    //private val _username= MutableLiveData<String>("")
    var userName: String = ""

    //private val _password= MutableLiveData<String>("")
    var passWord: String = ""

    //private val _amount = MutableLiveData<Double>(0.0)
    var amount: Double = 0.0

    //private val _descriptions = MutableLiveData<String>("")
    var desc: String = ""

    //private val _payee = MutableLiveData<String>("")
    var paYee: String = ""
    var receipientAccNo = ""

    var sharedLoginResult = LoginUser("", "", "", "")
    var sharedRegisterResult = RegisterUser("", "")
    var sharedPayeeResult = GetPayee("", listOf())
    var sharedTransferResult = TransferFund("","", 0.0, "", "")
    var sharedTransactionResult = UserTransaction("", listOf())
    var sharedBalanceResult = UserBalance("","","")
    val headers = HashMap<String, String>()

    fun setHeader(headerAuth: String) {
        headers["authorization"] = ""
    }

    fun setUsername(username: String) {
        userName = username
    }

    fun setPassword(password: String) {
        passWord = password
    }

    fun setTransferAmount(transferAmount: Double) {
        amount = transferAmount
    }

    fun setDescriptions(descriptions: String) {
        desc = descriptions
    }

    fun setPayee(payee: String, receipientaccno: String){
        paYee= payee
        receipientAccNo = receipientaccno
    }

    fun setLoginResult(loginResult: LoginUser?) {
        if (loginResult != null) {
            sharedLoginResult = loginResult
        }
    }

    fun setRegisterResult(registerResult: RegisterUser?) {
        if (registerResult != null) {
            sharedRegisterResult = registerResult
        }
    }

    fun setPayeeResult(payeeResult: GetPayee?) {
        if (payeeResult != null) {
            sharedPayeeResult = payeeResult
        }
    }

    fun setTransferResult(transferResult: TransferFund?) {
        if (transferResult != null) {
            sharedTransferResult = transferResult
        }
    }
    
    fun setTransactionResult(transactionResult: UserTransaction?) {
        if (transactionResult != null) {
            sharedTransactionResult = transactionResult
        }
    }

    fun setBalanceResult(balanceResult: UserBalance?) {
        if (balanceResult != null) {
            sharedBalanceResult = balanceResult
        }
    }


    init {
        resetOrder()
    }

    fun resetOrder() {
        userName = ""
        passWord = ""
        amount = 0.0
        desc = ""
        paYee = ""
        receipientAccNo = ""
        sharedPayeeResult = GetPayee("", listOf())
        sharedLoginResult = LoginUser(status = "", token = "", username = "", accountNo ="")
        sharedRegisterResult=RegisterUser("","")
        sharedBalanceResult = UserBalance("","", "")
        sharedTransferResult = TransferFund("","", 0.0, "", "")
        sharedTransactionResult = UserTransaction("", listOf())
    }

}