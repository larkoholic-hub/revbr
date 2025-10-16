package com.revbridge.svc
import android.app.*; import android.content.*; import android.graphics.PixelFormat; import android.os.*; import android.view.*; import android.webkit.*; import android.widget.ImageButton
import androidx.core.app.NotificationCompat; import com.revbridge.R; import com.revbridge.util.Prefs
class OverlayService: Service(){
  private var wm: WindowManager?=null; private var view: View?=null
  override fun onCreate(){ super.onCreate(); val id="revbridge_overlay"; val nm=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if(Build.VERSION.SDK_INT>=26){ nm.createNotificationChannel(NotificationChannel(id,"RevBridge Overlay", NotificationManager.IMPORTANCE_MIN)) }
    startForeground(1001, NotificationCompat.Builder(this,id).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Overlay activ").build()); show()
  }
  private fun show(){
    val url=Prefs(this).overlayUrl.ifBlank{ "http://192.168.100.146:8000/overlay?theme=neon" }
    wm=getSystemService(WINDOW_SERVICE) as WindowManager; val inf=getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val v=inf.inflate(R.layout.overlay_view,null); val p=WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
      if(Build.VERSION.SDK_INT>=26) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_PHONE,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, PixelFormat.TRANSLUCENT)
    p.gravity=Gravity.TOP or Gravity.CENTER_HORIZONTAL; p.y=40
    val web=v.findViewById<WebView>(R.id.webOverlay); web.setBackgroundColor(0x00000000); web.settings.javaScriptEnabled=true; web.settings.domStorageEnabled=true; web.settings.mixedContentMode=WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE; web.loadUrl(url)
    v.findViewById<ImageButton>(R.id.btnClose).setOnClickListener{ stopSelf() }
    v.setOnTouchListener(object: View.OnTouchListener{ var iy=0; var ty=0f; override fun onTouch(vw:View,e:MotionEvent):Boolean{ when(e.action){ MotionEvent.ACTION_DOWN->{iy=p.y; ty=e.rawY; return true}; MotionEvent.ACTION_MOVE->{p.y=iy+(e.rawY-ty).toInt(); wm?.updateViewLayout(v, p); return true} }; return false } })
    wm?.addView(v,p); view=v
  }
  override fun onDestroy(){ super.onDestroy(); if(view!=null) wm?.removeView(view) }
  override fun onBind(i:Intent?): IBinder?=null
}