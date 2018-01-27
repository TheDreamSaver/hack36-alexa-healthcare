package com.example.sidkathuria14.healthcare

/**
 * Created by sidkathuria14 on 27/1/18.
 */
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.util.Log
import com.example.sidkathuria14.healthcare.dialogs.EditMedNameFragment

/**
 * Created by theninetails on 2/1/18.
 */

class MedTextView(private val mContext: Context, attrs: AttributeSet) : android.support.v7.widget.AppCompatTextView(mContext, attrs) {
    private val placeNameDialog: EditMedNameFragment = EditMedNameFragment()
    var medName: String? = null

    init {
        placeNameDialog.setOnPlaceNameListener(object : EditMedNameFragment.OnPlaceNameListener {
            override fun onPlaceNameDone(string: String) {
                Log.d(TAG, "onPlaceNameDone: " + string)
                this@MedTextView.text = string
                medName = string
            }
        })
    }


    fun showDialog() {
        placeNameDialog.show((mContext as AppCompatActivity).fragmentManager, "PlaceNameDialog")
    }

    companion object {

        val TAG = "MedTextView"
    }
}