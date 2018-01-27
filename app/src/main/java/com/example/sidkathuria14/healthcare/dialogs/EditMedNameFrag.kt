package com.example.sidkathuria14.healthcare.dialogs

/**
 * Created by sidkathuria14 on 27/1/18.
 */
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import com.example.sidkathuria14.healthcare.R


class EditMedNameFragment : DialogFragment(), DialogInterface.OnClickListener {

    interface OnPlaceNameListener {
        fun onPlaceNameDone(string: String)
    }
    private var etPlaceBox: EditText?=null
    private var placeNameListener: OnPlaceNameListener? = null


    fun setOnPlaceNameListener(placeNameListener: OnPlaceNameListener) {
        this.placeNameListener = placeNameListener
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when (which) {
            -1 -> {
                placeNameListener?.onPlaceNameDone(etPlaceBox?.text.toString())
            }
            -2 -> {
                this.dialog.cancel()
            }
        }
    }

    /*
    *  View is created after this method call
    *  thus kotlinx synthetic doesn't initialize components here
    *  therefore if needed these components initialize it explicitly by 'findViewById()'
    */

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        etPlaceBox=inflater?.inflate(R.layout.dialog_frag_place_name,container)?.findViewById(R.id.etPlaceBox)   DOESN'T WORK GOD KNOWS
//        Log.d("pui",etPlaceBox?.toString())
        Log.d("dialog","onCreateView")
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val li = activity.layoutInflater
        val rootView = li.inflate(R.layout.dialog_medname_frag, null)
        etPlaceBox=rootView?.findViewById(R.id.etPlaceBox)
        Log.d("dialog","onCreateDialog")
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Place Name")
                .setView(rootView) // just sets the view NOT initialize the view
        builder.setPositiveButton(R.string.btn_OK, this)
        builder.setNegativeButton(R.string.btn_cancel, this)
        return builder.create()
    }


}