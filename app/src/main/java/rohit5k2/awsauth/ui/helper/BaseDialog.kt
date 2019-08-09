package rohit5k2.awsauth.ui.helper

import android.app.Dialog
import android.content.Context

/**
 * Created by Rohit on 8/2/2019:1:06 PM
 */
abstract class BaseDialog(context: Context):Dialog(context) {
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        initUi()
        wireEvents()
    }

    abstract fun initUi()
    abstract fun wireEvents()

    interface Callback{
        fun done()
        fun showMessage(message:String)
    }
}