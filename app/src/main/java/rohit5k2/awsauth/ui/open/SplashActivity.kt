package rohit5k2.awsauth.ui.open

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.amazonaws.mobile.client.UserState
import com.amazonaws.mobile.client.UserStateDetails
import rohit5k2.awsauth.R
import rohit5k2.awsauth.backend.helper.AWSCommHandler
import rohit5k2.awsauth.ui.helper.BaseOpenActivity
import rohit5k2.awsauth.ui.helper.SuccessFailureContract
import rohit5k2.awsauth.ui.secure.MainActivity
import rohit5k2.awsauth.utils.CLog

/**
 * Created by Rohit on 7/31/2019:2:23 PM
 */
class SplashActivity : BaseOpenActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_ativity)

        initAwsComponents()
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
            UserState.SIGNED_IN -> startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            UserState.SIGNED_OUT -> startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }

        CLog.i("Status is ${userStateDetails.userState.name}")
        finish()
    }
}
