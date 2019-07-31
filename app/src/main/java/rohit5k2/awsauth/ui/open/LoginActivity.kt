package rohit5k2.awsauth.ui.open

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import rohit5k2.awsauth.R
import rohit5k2.awsauth.ui.helper.BaseOpenActivity

/**
 * Created by Rohit on 7/31/2019:4:25 PM
 */
class LoginActivity : BaseOpenActivity() {

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
    }
}
