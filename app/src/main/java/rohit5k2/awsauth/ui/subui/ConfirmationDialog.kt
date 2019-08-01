package rohit5k2.awsauth.ui.subui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import kotlinx.android.synthetic.main.dialog_confirmation.*
import rohit5k2.awsauth.R

/**
 * Created by Rohit on 8/1/2019:4:05 PM
 */
class ConfirmationDialog(context: Context, listener:Callback):Dialog(context) {
    val l = listener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(false)
        setContentView(R.layout.dialog_confirmation)

        initUi()
    }

    private fun initUi(){
        code_resend.setOnClickListener {
            l.onResend()
        }

        code_confirm.setOnClickListener {
            if(!TextUtils.isEmpty(code_code.text.toString())) {
                l.onConfirm(code_code.text.toString())
            }
        }
    }

    interface Callback{
        fun onResend()
        fun onConfirm(code:String)
    }
}