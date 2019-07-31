package rohit5k2.awsauth.ui.secure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import rohit5k2.awsauth.R
import rohit5k2.awsauth.ui.helper.BaseSecureActivity

/**
 * Created by Rohit on 7/31/2019:2:19 PM
 */

class MainActivity : BaseSecureActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
