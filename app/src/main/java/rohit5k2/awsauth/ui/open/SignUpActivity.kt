package rohit5k2.awsauth.ui.open

import android.os.Bundle
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils
import com.amazonaws.mobile.client.results.SignUpResult
import com.amazonaws.services.cognitoidentityprovider.model.UsernameExistsException
import kotlinx.android.synthetic.main.activity_signup.*
import rohit5k2.awsauth.R
import rohit5k2.awsauth.backend.handler.AwsAPIHandler
import rohit5k2.awsauth.model.SignUpData
import rohit5k2.awsauth.ui.helper.BaseActivity
import rohit5k2.awsauth.ui.helper.BaseDialog
import rohit5k2.awsauth.ui.helper.SuccessFailureContract
import rohit5k2.awsauth.ui.subui.ConfirmationDialog
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
    }

    override fun wireEvents(){
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

    private fun showCodeConfirmationDialog(){
        confiramationDialog = ConfirmationDialog(this@SignUpActivity, signUpData!!.email, object :BaseDialog.Callback{
            override fun done() {
                goToLogin()
            }

            override fun showMessage(message: String) {
                showToast(message)
            }
        })

        confiramationDialog!!.show()
    }
}