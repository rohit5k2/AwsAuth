package rohit5k2.awsauth.ui.open

import android.os.Bundle
import android.widget.Toast
import com.amazonaws.mobile.client.UserState
import com.amazonaws.mobile.client.UserStateDetails
import rohit5k2.awsauth.R
import rohit5k2.awsauth.backend.helper.AWSCommHandler
import rohit5k2.awsauth.ui.helper.BaseActivity
import rohit5k2.awsauth.ui.helper.SuccessFailureContract
import rohit5k2.awsauth.utils.CLog

/**
 * Created by Rohit on 7/31/2019:2:23 PM
 */
class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_ativity)

        initAwsComponents()
    }

    override fun wireEvents() {
        // Nothing to be done here
    }

    private fun initAwsComponents(){
        AWSCommHandler(this.applicationContext, object : SuccessFailureContract<UserStateDetails, String> {
            override fun successful(userStatusDetails: UserStateDetails) {
                runOnUiThread {
                    findUserStatusAndMove(userStatusDetails)
                }
            }

            override fun failed(reason: String) {
                runOnUiThread {
                    Toast.makeText(this@SplashActivity, reason, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun findUserStatusAndMove(userStateDetails: UserStateDetails){
        when(userStateDetails.userState){
            UserState.SIGNED_IN -> goToMain()
            UserState.SIGNED_OUT -> goToLogin()
        }

        CLog.i("Status is ${userStateDetails.userState.name}")
        finish()
    }
}
