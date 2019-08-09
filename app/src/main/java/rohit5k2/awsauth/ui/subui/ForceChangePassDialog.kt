package rohit5k2.awsauth.ui.subui

import android.content.Context
import android.os.Bundle
import com.amazonaws.mobile.client.results.SignInResult
import kotlinx.android.synthetic.main.dialog_force_change_password.*
import rohit5k2.awsauth.R
import rohit5k2.awsauth.backend.handler.AwsAPIHandler
import rohit5k2.awsauth.ui.helper.BaseDialog
import rohit5k2.awsauth.ui.helper.SuccessFailureContract
import java.lang.Exception
import com.amazonaws.mobile.client.results.SignInState


/**
 * Created by Rohit on 8/9/2019:2:21 PM
 */
class ForceChangePassDialog(context: Context, listener:Callback):BaseDialog(context) {

    private val l = listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_force_change_password)
    }

    override fun initUi() {

    }

    override fun wireEvents() {
        force_change_pass_btn.setOnClickListener {
            changePassword()
        }
    }

    private fun changePassword(){
        AwsAPIHandler.instance.forceChangePassword("", object :SuccessFailureContract<SignInResult, Exception>{
            override fun successful(data: SignInResult) {
                when (data.signInState) {
                    SignInState.DONE -> {
                        l.done()
                        dismiss()
                    }
                    else -> failed(Exception())
                }
            }

            override fun failed(data: Exception) {
                l.showMessage("Force Password change failed.")
            }
        })
    }
}