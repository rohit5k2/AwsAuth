package rohit5k2.awsauth.ui.subui

import android.content.Context
import android.os.Bundle
import android.view.View
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils
import com.amazonaws.mobile.client.results.ForgotPasswordResult
import com.amazonaws.mobile.client.results.ForgotPasswordState
import kotlinx.android.synthetic.main.dialog_forgot_password.*
import rohit5k2.awsauth.R
import rohit5k2.awsauth.backend.handler.AwsAPIHandler
import rohit5k2.awsauth.ui.helper.BaseDialog
import rohit5k2.awsauth.ui.helper.SuccessFailureContract
import java.lang.Exception

/**
 * Created by Rohit on 8/2/2019:12:55 PM
 */
class ForgotPasswordDialog(context: Context, listener:Callback):BaseDialog(context) {
    private val l = listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_forgot_password)
    }

    override fun initUi() {
        forgot_password_reset_ll.visibility = View.GONE
        forgot_password_reset.visibility = View.GONE
    }

    override fun wireEvents() {
        forgot_password_getcode.setOnClickListener {
            getResetCode()
        }

        forgot_password_reset.setOnClickListener {
            resetPassword()
        }
    }

    private fun getResetCode(){
        AwsAPIHandler.instance.forgotPassword(forgot_password_username.text.toString(),
            object :SuccessFailureContract<ForgotPasswordResult, Exception>{
                override fun successful(data: ForgotPasswordResult) {
                    if(data.state == ForgotPasswordState.CONFIRMATION_CODE) {
                        ThreadUtils.runOnUiThread {
                            showResetUi()
                        }
                    }
                    else
                        failed(Exception())
                }

                override fun failed(data: Exception) {
                    ThreadUtils.runOnUiThread {
                        l.showMessage("Password Reset Failed.")
                    }
                }
            }
        )
    }

    private fun showResetUi(){
        forgot_password_username.isEnabled = false
        forgot_password_reset_ll.visibility = View.VISIBLE
        forgot_password_getcode.visibility = View.GONE
        forgot_password_reset.visibility = View.VISIBLE
    }

    private fun resetPassword(){
        AwsAPIHandler.instance.confirmForgotPassword(forgot_password.text.toString(), forgot_password_code.text.toString(),
            object :SuccessFailureContract<ForgotPasswordResult, Exception>{
                override fun successful(data: ForgotPasswordResult) {
                    if(data.state == ForgotPasswordState.DONE) {
                        dismiss()
                        l.done()
                    }
                    else
                        failed(Exception())
                }

                override fun failed(data: Exception) {
                    ThreadUtils.runOnUiThread {
                        l.showMessage("Password Reset Failed")
                    }
                }
            }
        )
    }
}