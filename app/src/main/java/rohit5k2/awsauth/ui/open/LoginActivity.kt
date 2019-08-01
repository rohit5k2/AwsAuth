package rohit5k2.awsauth.ui.open

import android.content.Intent
import android.os.Bundle
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils
import com.amazonaws.mobile.client.results.SignInResult
import com.amazonaws.services.cognitoidentityprovider.model.UserNotConfirmedException
import kotlinx.android.synthetic.main.activity_login.*
import rohit5k2.awsauth.R
import rohit5k2.awsauth.backend.handler.AwsAPIHandler
import rohit5k2.awsauth.ui.helper.BaseActivity
import rohit5k2.awsauth.ui.helper.SuccessFailureContract
import rohit5k2.awsauth.ui.subui.ConfirmationDialog
import rohit5k2.awsauth.utils.CLog

/**
 * Created by Rohit on 7/31/2019:4:25 PM
 */
class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initUI()
    }

    private fun initUI(){
        forgot_password.setOnClickListener {
            //TODO: Open Forgot password activity
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
                    goToMain()
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

    private fun showCodeConfirmationDialog(){
        ConfirmationDialog(this@LoginActivity, login_emailid.text.toString(), object : ConfirmationDialog.Callback{
            override fun done() {
                goToLogin()
            }

            override fun showMessage(message: String) {
                showToast(message)
            }
        }).show()
    }
}
