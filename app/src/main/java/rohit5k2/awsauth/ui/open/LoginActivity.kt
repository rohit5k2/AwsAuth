package rohit5k2.awsauth.ui.open

import android.content.Intent
import android.os.Bundle
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils
import com.amazonaws.mobile.client.results.SignInResult
import com.amazonaws.mobile.client.results.SignInState
import com.amazonaws.services.cognitoidentityprovider.model.UserNotConfirmedException
import kotlinx.android.synthetic.main.activity_login.*
import rohit5k2.awsauth.R
import rohit5k2.awsauth.backend.handler.AwsAPIHandler
import rohit5k2.awsauth.ui.helper.BaseActivity
import rohit5k2.awsauth.ui.helper.BaseDialog
import rohit5k2.awsauth.ui.helper.SuccessFailureContract
import rohit5k2.awsauth.ui.subui.ConfirmationDialog
import rohit5k2.awsauth.ui.subui.ForceChangePassDialog
import rohit5k2.awsauth.ui.subui.ForgotPasswordDialog
import rohit5k2.awsauth.utils.CLog

/**
 * Created by Rohit on 7/31/2019:4:25 PM
 */
class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun wireEvents(){
        forgot_password.setOnClickListener {
            showForgotPasswordDialog()
        }

        signup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            finish()
        }

        login.setOnClickListener {
            login()
        }
    }

    private fun login(){
        AwsAPIHandler.instance.login(login_emailid.text.toString(), login_password.text.toString(),
            object :SuccessFailureContract<SignInResult, Exception>{
                override fun successful(data: SignInResult) {
                    CLog.i("Sign in state is ${data.signInState}")
                    when {
                        data.signInState == SignInState.DONE -> goToMain()
                        data.signInState == SignInState.NEW_PASSWORD_REQUIRED -> {
                            showForceChangePasswordDialog()
                        }
                        else -> failed(java.lang.Exception())
                    }
                }

                override fun failed(data: Exception) {
                    ThreadUtils.runOnUiThread {
                        if(data is UserNotConfirmedException)
                            showCodeConfirmationDialog()
                        else
                            showToast("Login failed.")
                    }
                }
            })
    }

    private fun showForceChangePasswordDialog(){
        ForceChangePassDialog(this@LoginActivity, dialogListener).show()
    }

    private fun showForgotPasswordDialog(){
        ForgotPasswordDialog(this@LoginActivity, dialogListener).show()
    }

    private fun showCodeConfirmationDialog(){
        ConfirmationDialog(this@LoginActivity, login_emailid.text.toString(), dialogListener).show()
    }

    private val dialogListener = object :BaseDialog.Callback{
        override fun done() {
            goToLogin()
        }

        override fun showMessage(message: String) {
            ThreadUtils.runOnUiThread { showToast(message) }
        }
    }
}
