package com.revbridge.svc
import android.app.Notification; import android.service.notification.*; import android.util.Log; import com.revbridge.util.Prefs
import okhttp3.*; import okhttp3.MediaType.Companion.toMediaType; import okhttp3.logging.HttpLoggingInterceptor; import org.json.JSONObject
class RevNotificationListener: NotificationListenerService(){
  override fun onNotificationPosted(sbn: StatusBarNotification){ try{
    if(sbn.packageName!="com.revolut.revolut") return
    val e=sbn.notification.extras; val title=e.getCharSequence(Notification.EXTRA_TITLE)?.toString()?:""; val text=e.getCharSequence(Notification.EXTRA_TEXT)?.toString()?:""
    forward(this, title, text, "Revolut")
  }catch(e:Throwable){ Log.e("RevBridge","err",e)}}
  companion object{
    fun postTest(ctx: android.content.Context)=forward(ctx,"Ai primit 5,00 RON de la Test","mesaj: Salut!","Revolut")
    fun forward(ctx: android.content.Context, title:String, text:String, app:String){
      try{
        val p=Prefs(ctx); val url=p.url; if(url.isBlank()) return
        val json=JSONObject().put("title",title).put("text",text).put("app",app).toString()
        val body=RequestBody.create("application/json".toMediaType(), json)
        val b=Request.Builder().url(url).post(body); if(p.secret.isNotBlank()) b.addHeader("X-Webhook-Secret", p.secret)
        val client=OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply{level=HttpLoggingInterceptor.Level.BASIC}).build()
        client.newCall(b.build()).execute().use{}
      }catch(e:Throwable){ Log.e("RevBridge","send",e) }
    }
  }
}