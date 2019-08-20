package rohit5k2.awsauth.ui.secure

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import rohit5k2.awsauth.R
import rohit5k2.awsauth.backend.handler.AwsAPIHandler
import rohit5k2.awsauth.ui.helper.BaseActivity

/**
 * Created by Rohit on 7/31/2019:2:19 PM
 */

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun wireEvents() {
        // Nothing for now
        initScreen()
    }

    private fun initScreen(){
        username.text = AwsAPIHandler.instance.getUsername()
        signin_status.text = AwsAPIHandler.instance.isSignedIn()
        identity_id.text = AwsAPIHandler.instance.getIdentityId()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        logout()
    }

    private fun logout(){
        AwsAPIHandler.instance.logout()
    }
}
