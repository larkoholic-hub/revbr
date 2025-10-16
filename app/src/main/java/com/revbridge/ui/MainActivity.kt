package com.revbridge.ui
import android.content.Intent; import android.net.Uri; import android.os.Bundle; import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import android.widget.*; import com.revbridge.R; import com.revbridge.util.Prefs; import com.revbridge.svc.OverlayService; import com.revbridge.svc.RevNotificationListener
class MainActivity: AppCompatActivity(){
  override fun onCreate(s: Bundle?){ super.onCreate(s); setContentView(R.layout.activity_main)
    val url=findViewById<EditText>(R.id.editUrl); val sec=findViewById<EditText>(R.id.editSecret); val ov=findViewById<EditText>(R.id.editOverlayUrl)
    val save=findViewById<Button>(R.id.btnSave); val notif=findViewById<Button>(R.id.btnNotifAccess); val overlayPerm=findViewById<Button>(R.id.btnOverlayPerm)
    val start=findViewById<Button>(R.id.btnOverlayStart); val stop=findViewById<Button>(R.id.btnOverlayStop); val test=findViewById<Button>(R.id.btnTest); val open=findViewById<Button>(R.id.btnOpenOverlayInBrowser)
    val status=findViewById<TextView>(R.id.txtStatus); val p=Prefs(this)
    if(p.overlayUrl.isBlank()) p.overlayUrl="http://192.168.100.146:8000/overlay?theme=neon"
    url.setText(p.url); sec.setText(p.secret); ov.setText(p.overlayUrl)
    save.setOnClickListener{ p.url=url.text.toString().trim(); p.secret=sec.text.toString().trim(); p.overlayUrl=ov.text.toString().trim(); status.text="Config salvat ✓" }
    notif.setOnClickListener{ startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")) }
    overlayPerm.setOnClickListener{ startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))) }
    start.setOnClickListener{ startService(Intent(this, OverlayService::class.java)) }
    stop.setOnClickListener{ stopService(Intent(this, OverlayService::class.java)) }
    test.setOnClickListener{ RevNotificationListener.postTest(this) }
    open.setOnClickListener{ startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(p.overlayUrl))) }
  }
}