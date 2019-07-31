package rohit5k2.awsauth.backend.handler

import com.amazonaws.mobile.client.results.SignUpResult
import rohit5k2.awsauth.backend.helper.AWSCommHandler
import com.amazonaws.mobile.client.Callback
import rohit5k2.awsauth.ui.helper.SuccessFailureContract
import java.lang.Exception

/**
 * Created by Rohit on 7/31/2019:7:22 PM
 */
class AwsAPIHandler {
    companion object {
        val instance:AwsAPIHandler = AwsAPIHandler()
    }

    fun <S, F>signup(listener:SuccessFailureContract<S, F>){
        AWSCommHandler.getMobileClient().signUp("","", null, null, object:Callback<SignUpResult>{
            override fun onResult(result: SignUpResult?) {
                listener.successful(result as S)
            }

            override fun onError(e: Exception?) {
                listener.failed(e?.message as F)
            }

        })
    }
}