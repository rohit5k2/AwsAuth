package rohit5k2.awsauth.backend.handler

import rohit5k2.awsauth.backend.helper.AWSCommHandler
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.results.*
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

    //region authentication related API

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

    fun <S,F>forgotPassword(username: String, listener:SuccessFailureContract<S,F>){
        AWSCommHandler.getMobileClient().forgotPassword(username, object :Callback<ForgotPasswordResult>{
            override fun onResult(result: ForgotPasswordResult?) {
                listener.successful(result as S)
            }

            override fun onError(e: Exception?) {
                listener.failed(e as F)
            }
        })
    }

    fun <S, F> confirmForgotPassword(newPassword:String, confirmationCode:String, listener: SuccessFailureContract<S,F>){
        AWSCommHandler.getMobileClient().confirmForgotPassword(newPassword, confirmationCode, object :Callback<ForgotPasswordResult>{
            override fun onResult(result: ForgotPasswordResult?) {
                listener.successful(result as S)
            }

            override fun onError(e: Exception?) {
                listener.failed(e as F)
            }
        })
    }

    fun<S,F> forceChangePassword(newPassword: String, listener:SuccessFailureContract<S,F>){
        AWSCommHandler.getMobileClient().confirmSignIn(newPassword, object:Callback<SignInResult>{
            override fun onResult(signInResult: SignInResult) {
                listener.successful(signInResult as S)
            }

            override fun onError(e: Exception) {
                listener.failed(e as F)
            }
        })
    }

    fun logout(){
        AWSCommHandler.getMobileClient().signOut()
    }

    fun getUsername():String{
        return AWSCommHandler.getMobileClient().username
    }

    fun isSignedIn():String{
        return if(AWSCommHandler.getMobileClient().isSignedIn) "Logged In" else "Not logged In"
    }

    fun getIdentityId():String{
        return AWSCommHandler.getMobileClient().identityId
    }

    //endregion authentication related API

    //region Device Related API

    fun <S,F> updateDeviceStatus(status:Boolean, listener: SuccessFailureContract<S, F>){
        AWSCommHandler.getMobileClient().deviceOperations.updateStatus(status, object :Callback<Void?>{
            override fun onResult(result: Void?) {
                listener.successful(true as S)
            }

            override fun onError(e: Exception?) {
                listener.failed(e as F)
            }
        })
    }

    fun <S,F> forgetDevice(listener:SuccessFailureContract<S,F>){
        AWSCommHandler.getMobileClient().deviceOperations.forget(object :Callback<Void>{
            override fun onResult(result: Void?) {
                listener.successful(true as S)
            }

            override fun onError(e: Exception) {
                listener.failed(e as F)
            }
        })
    }

    fun <S,F> deviceInfo(listener:SuccessFailureContract<S,F>){
        AWSCommHandler.getMobileClient().deviceOperations.get(object :Callback<Device>{
            override fun onResult(result: Device) {
                listener.successful(result as S)
            }

            override fun onError(e: Exception) {
                listener.failed(e as F)
            }

        })
    }

    fun <S,F> deviceList(listener:SuccessFailureContract<S,F>){
        AWSCommHandler.getMobileClient().deviceOperations.list(object :Callback<ListDevicesResult>{
            override fun onResult(result: ListDevicesResult) {
                listener.successful(result as S)
            }

            override fun onError(e: Exception) {
                listener.failed(e as F)
            }

        })
    }

    //endregion Device Related API
}