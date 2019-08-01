package rohit5k2.awsauth.ui.open

import android.content.Intent
import android.os.Bundle
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils
import com.amazonaws.mobile.client.results.SignUpResult
import com.amazonaws.services.cognitoidentityprovider.model.UsernameExistsException
import kotlinx.android.synthetic.main.activity_signup.*
import rohit5k2.awsauth.R
import rohit5k2.awsauth.backend.handler.AwsAPIHandler
import rohit5k2.awsauth.model.SignUpData
import rohit5k2.awsauth.ui.helper.BaseActivity
import rohit5k2.awsauth.ui.helper.SuccessFailureContract
import rohit5k2.awsauth.ui.subui.ConfirmationDialog
import rohit5k2.awsauth.utils.CLog
import java.lang.Exception

/**
 * Created by Rohit on 7/31/2019:6:28 PM
 */
class SignUpActivity: BaseActivity() {
    var signUpData:SignUpData? = null
    var confiramationDialog:ConfirmationDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initUI()
    }

    private fun initUI(){
        signup.setOnClickListener {
            signup()
            //showCodeConfirmationDialog()
        }
    }

    private fun signup(){
        val attributes = HashMap<String, String>()
        attributes["name"] = signup_name.text.toString()
        signUpData = SignUpData(signup_emailid.text.toString(), signup_password.text.toString(), attributes)

        AwsAPIHandler.instance.signup(signUpData!!, object :SuccessFailureContract<SignUpResult, Exception>{
            override fun successful(data: SignUpResult) {
                ThreadUtils.runOnUiThread{
                    if (!data.confirmationState) {
                        showToast("Confirm sign up with " + data.userCodeDeliveryDetails.destination)
                        showCodeConfirmationDialog()
                    }
                    else {
                        showToast("User registered successfully.")
                        goToLogin()
                    }
                }
            }

            override fun failed(data: Exception) {
                ThreadUtils.runOnUiThread{
                    if(data is UsernameExistsException)
                        showToast("Username is already registered")
                    else
                        showToast("User Registration Failed!")
                }
            }
        })
    }

    private fun goToLogin(){
        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
        finish()
    }

    private fun showCodeConfirmationDialog(){
        confiramationDialog = ConfirmationDialog(this@SignUpActivity, object :ConfirmationDialog.Callback{
            override fun onConfirm(code: String) {
                CLog.i("confirmed with $code")
                confirmCode(code)
            }

            override fun onResend() {
                CLog.i("Resend code")
                resendCode()
            }
        })

        confiramationDialog!!.show()
    }

    private fun confirmCode(code:String){
        AwsAPIHandler.instance.cofirmCode(signUpData!!.email, code, object :SuccessFailureContract<SignUpResult, Exception>{
            override fun successful(data: SignUpResult) {
                ThreadUtils.runOnUiThread{
                    if (!data.confirmationState) {
                        showToast("Confirm sign up with " + data.userCodeDeliveryDetails.destination)
                    } else {
                        confiramationDialog?.dismiss()
                        showToast("Confirmation successful.")
                        goToLogin()
                    }
                }
            }

            override fun failed(data: Exception) {
                ThreadUtils.runOnUiThread {
                    showToast("Confirmation failed, please try again.")
                }
            }
        })
    }

    private fun resendCode(){
        AwsAPIHandler.instance.resendCode(signUpData!!.email, object :SuccessFailureContract<SignUpResult, Exception>{
            override fun successful(data: SignUpResult) {
                ThreadUtils.runOnUiThread{
                    showToast("A verification code has been sent via" + data.userCodeDeliveryDetails.deliveryMedium
                            + " at " + data.userCodeDeliveryDetails.destination)
                }
            }

            override fun failed(data: Exception) {
                ThreadUtils.runOnUiThread {
                    showToast("Something weird happened while resending code.")
                }
            }
        })
    }
}