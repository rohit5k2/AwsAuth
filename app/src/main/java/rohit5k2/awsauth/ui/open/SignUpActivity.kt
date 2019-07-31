package rohit5k2.awsauth.ui.open

import android.os.Bundle
import rohit5k2.awsauth.R
import rohit5k2.awsauth.ui.helper.BaseOpenActivity

/**
 * Created by Rohit on 7/31/2019:6:28 PM
 */
class SignUpActivity:BaseOpenActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initUI()
    }

    private fun initUI(){
        //AwsAPIHandler.instance.signup<SignUpResult, String>()

    }
}