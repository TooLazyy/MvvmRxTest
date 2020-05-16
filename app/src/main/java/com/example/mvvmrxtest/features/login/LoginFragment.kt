package com.example.mvvmrxtest.features.login

import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import com.example.mvvmrxtest.R
import com.example.mvvmrxtest.core.fragment.BaseFragment
import com.example.mvvmrxtest.core.loading.LoadingPlaceholder
import com.example.mvvmrxtest.core.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<LoginState>(R.layout.fragment_login) {

    companion object {

        const val TAG = "LoginFragment"
    }

    private val vm: LoginVm by viewModel()

    override fun getLoadingPlaceholder(): LoadingPlaceholder = v_loading

    override fun afterCreate(savedInstanceState: Bundle?, recreated: Boolean) {
        vm.subscribeToVmState(::render)
        initListeners()
    }

    override fun render(state: LoginState) {
        super.render(state)
        btn_login.isEnabled = state.isLoginButtonEnabled
    }

    private fun initListeners() {
        et_email.doAfterTextChanged {
            vm.onEmailChanged(it?.toString() ?: return@doAfterTextChanged)
        }
        et_password.doAfterTextChanged {
            vm.onPasswordChanged(it?.toString() ?: return@doAfterTextChanged)
        }
        btn_login.setOnClickListener {
            hideKeyboard()
            vm.onLoginClicked(
                et_email.text?.toString() ?: "",
                et_password.text?.toString() ?: "",
                cb_fake_error.isChecked
            )
        }
    }
}