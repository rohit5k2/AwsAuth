package rohit5k2.awsauth.ui.helper

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import rohit5k2.awsauth.ui.open.LoginActivity
import rohit5k2.awsauth.ui.secure.MainActivity

/**
 * Created by Rohit on 7/31/2019:6:29 PM
 */
abstract class BaseActivity: AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        wireEvents()
    }

    abstract fun wireEvents()

    protected fun showToast(message:String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    protected fun goToLogin(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    protected fun goToMain(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}