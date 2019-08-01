package rohit5k2.awsauth.backend.handler

import com.amazonaws.mobile.client.results.SignUpResult
import rohit5k2.awsauth.backend.helper.AWSCommHandler
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.results.SignInResult
import rohit5k2.awsauth.model.SignUpData
import rohit5k2.awsauth.ui.helper.SuccessFailureContract
import java.lang.Exception

/**
 * Created by Rohit on 7/31/2019:7:22 PM
 */
class AwsAPIHandler {
    companion object {
        val instance:AwsAPIHandler = AwsAPIHandler()
    }

    fun <S, F>signup(signUpData: SignUpData, listener:SuccessFailureContract<S, F>){
        AWSCommHandler.getMobileClient().signUp(signUpData.email, signUpData.password, signUpData.attributes, null, object:Callback<SignUpResult>{
            override fun onResult(result: SignUpResult?) {
                listener.successful(result as S)
            }

            override fun onError(e: Exception?) {
                listener.failed(e as F)
            }

        })
    }

    fun <S,F>confirmCode(userName:String?, code:String, listener:SuccessFailureContract<S,F>){
        AWSCommHandler.getMobileClient().confirmSignUp(userName, code, object :Callback<SignUpResult>{
            override fun onResult(result: SignUpResult?) {
                listener.successful(result as S)
            }

            override fun onError(e: Exception?) {
                listener.failed(e as F)
            }
        })
    }

    fun<S,F>resendCode(username:String, listener:SuccessFailureContract<S,F>){
        AWSCommHandler.getMobileClient().resendSignUp(username, object :Callback<SignUpResult>{
            override fun onResult(result: SignUpResult?) {
                listener.successful(result as S)
            }

            override fun onError(e: Exception?) {
                listener.failed(e as F)
            }
        })
    }

    fun <S,F> login(username: String, password:String, listener:SuccessFailureContract<S, F>){
        AWSCommHandler.getMobileClient().signIn(username, password, null, object :Callback<SignInResult>{
            override fun onResult(result: SignInResult?) {
                listener.successful(result as S)
            }

            override fun onError(e: Exception?) {
                listener.failed(e as F)
            }
        })
    }

    fun logout(){
        AWSCommHandler.getMobileClient().signOut()
    }
}