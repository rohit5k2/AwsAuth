package rohit5k2.awsauth.ui.helper

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Rohit on 7/31/2019:6:29 PM
 */
abstract class BaseActivity: AppCompatActivity() {

    protected fun showToast(message:String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}