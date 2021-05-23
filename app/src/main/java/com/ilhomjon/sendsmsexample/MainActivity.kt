package com.ilhomjon.sendsmsexample

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.github.florent37.runtimepermission.kotlin.askPermission
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            ruxsatSorash()
            val phone = edt_nomer.text.toString().trim()
            val text = edt_text.text.toString().trim()
            if (phone != "" && text != ""){
            var obj = SmsManager.getDefault()
            obj.sendTextMessage(phone,
            null,  text,
            null, null)
                Toast.makeText(this, "Send message", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Ma'lumotlar yetarli emas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun ruxsatSorash(){
        askPermission(Manifest.permission.SEND_SMS){
            //all permissions already granted or just granted

        }.onDeclined { e ->
            if (e.hasDenied()) {

                AlertDialog.Builder(this)
                    .setMessage("Ruxsat bermasangiz ilova ishlay olmaydi ruxsat bering...")
                    .setPositiveButton("yes") { dialog, which ->
                        e.askAgain();
                    } //ask again
                    .setNegativeButton("no") { dialog, which ->
                        dialog.dismiss();
                    }
                    .show();
            }

            if(e.hasForeverDenied()) {
                //the list of forever denied permissions, user has check 'never ask again'

                // you need to open setting manually if you really need it
                e.goToSettings();
            }
        }

    }
}