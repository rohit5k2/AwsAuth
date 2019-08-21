package rohit5k2.awsauth.ui.secure

import android.os.Bundle
import android.view.View
import com.amazonaws.mobile.client.results.Device
import com.amazonaws.mobile.client.results.ListDevicesResult
import kotlinx.android.synthetic.main.activity_main.*
import rohit5k2.awsauth.R
import rohit5k2.awsauth.backend.handler.AwsAPIHandler
import rohit5k2.awsauth.ui.helper.BaseActivity
import rohit5k2.awsauth.ui.helper.SuccessFailureContract
import rohit5k2.awsauth.utils.CLog
import java.lang.Exception

/**
 * Created by Rohit on 7/31/2019:2:19 PM
 */

class MainActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun wireEvents() {
        initScreen()

        remember_me.setOnClickListener(this)
        forget_me.setOnClickListener(this)
        device_info.setOnClickListener(this)
        device_list.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.remember_me ->{rememberMe()}
            R.id.forget_me -> {forgetMe()}
            R.id.device_info -> {deviceInfo()}
            R.id.device_list -> {deviceList()}
        }
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

    private fun rememberMe(){
        AwsAPIHandler.instance.updateDeviceStatus(true, object :SuccessFailureContract<Boolean, Exception>{
            override fun successful(data: Boolean) {
                CLog.i("Successfully remembered this device")
            }

            override fun failed(data: Exception) {
                CLog.e("Error while remembering this device")
            }
        })
    }

    private fun forgetMe(){
        AwsAPIHandler.instance.forgetDevice(object :SuccessFailureContract<Boolean, Exception>{
            override fun successful(data: Boolean) {
                CLog.i("Successfully forgot this device")
            }

            override fun failed(data: Exception) {
                CLog.e("Error while forgetting this device")
            }
        })
    }

    private fun deviceInfo(){
        AwsAPIHandler.instance.deviceInfo(object :SuccessFailureContract<Device, Exception>{
            override fun successful(data: Device) {
                CLog.i("Successfully fetched device information")
            }

            override fun failed(data: Exception) {
                CLog.e("Error while fetching device information")
            }
        })
    }

    private fun deviceList(){
        AwsAPIHandler.instance.deviceList(object :SuccessFailureContract<ListDevicesResult, Exception>{
            override fun successful(data: ListDevicesResult) {
                CLog.i("Successfully fetched device list")
            }

            override fun failed(data: Exception) {
                CLog.e("Error while fetching device list")
            }
        })
    }
}
