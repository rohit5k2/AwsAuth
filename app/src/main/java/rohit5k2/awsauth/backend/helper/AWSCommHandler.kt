package rohit5k2.awsauth.backend.helper

import android.content.Context
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserStateDetails
import rohit5k2.awsauth.ui.helper.SuccessFailureContract

/**
 * Created by Rohit on 7/31/2019:2:19 PM
 */
class AWSCommHandler <S, F>(context: Context, setupCallback: SuccessFailureContract<S, F>) {
    companion object {
        fun getMobileClient():AWSMobileClient{
            return AWSMobileClient.getInstance()
        }
    }

    init{
        initMobileClient(context, setupCallback)
    }

    private fun initMobileClient(context: Context, setupCallback: SuccessFailureContract<S, F>){
        AWSMobileClient.getInstance().initialize(context.applicationContext, object : Callback<UserStateDetails> {
            override fun onResult(userStateDetails: UserStateDetails) {
                setupCallback.successful(userStateDetails as S)
            }

            override fun onError(e: Exception) {
                setupCallback.failed(e.message as F)
            }
        })
    }
}