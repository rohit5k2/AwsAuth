package rohit5k2.awsauth.ui.subui

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils
import com.amazonaws.mobile.client.results.SignUpResult
import kotlinx.android.synthetic.main.dialog_confirmation.*
import rohit5k2.awsauth.R
import rohit5k2.awsauth.backend.handler.AwsAPIHandler
import rohit5k2.awsauth.ui.helper.BaseDialog
import rohit5k2.awsauth.ui.helper.SuccessFailureContract
import java.lang.Exception

/**
 * Created by Rohit on 8/1/2019:4:05 PM
 */
class ConfirmationDialog(context: Context, userName: String, listener:Callback):BaseDialog(context) {
    val l = listener
    val data = userName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(false)
        setContentView(R.layout.dialog_confirmation)
    }

    override fun initUi() {

    }

    override fun wireEvents(){
        code_resend.setOnClickListener {
            resendCode()
        }

        code_confirm.setOnClickListener {
            if(!TextUtils.isEmpty(code_code.text.toString())) {
                confirmCode(code_code.text.toString())
            }
        }
    }

    private fun confirmCode(code:String){
        AwsAPIHandler.instance.confirmCode(data, code, object : SuccessFailureContract<SignUpResult, Exception> {
            override fun successful(data: SignUpResult) {
                ThreadUtils.runOnUiThread{
                    if (!data.confirmationState) {
                        l.showMessage("Confirm sign up with " + data.userCodeDeliveryDetails.destination)
                    } else {
                        l.showMessage("Confirmation successful.")
                        l.done()
                        dismiss()
                    }
                }
            }

            override fun failed(data: Exception) {
                ThreadUtils.runOnUiThread {
                    l.showMessage("Confirmation failed, please try again.")
                }
            }
        })
    }

    private fun resendCode(){
        AwsAPIHandler.instance.resendCode(data, object : SuccessFailureContract<SignUpResult, Exception> {
            override fun successful(data: SignUpResult) {
                ThreadUtils.runOnUiThread{
                    l.showMessage("A verification code has been sent via" + data.userCodeDeliveryDetails.deliveryMedium
                            + " at " + data.userCodeDeliveryDetails.destination)
                }
            }

            override fun failed(data: Exception) {
                ThreadUtils.runOnUiThread {
                    l.showMessage("Something weird happened while resending code.")
                }
            }
        })
    }
}