package com.revbridge.util
import android.content.Context; import android.content.SharedPreferences
class Prefs(ctx: Context) {
  private val sp: SharedPreferences = ctx.getSharedPreferences("revbridge", Context.MODE_PRIVATE)
  var url: String get() = sp.getString("url","") ?: "" ; set(v){ sp.edit().putString("url",v).apply() }
  var secret: String get() = sp.getString("secret","") ?: "" ; set(v){ sp.edit().putString("secret",v).apply() }
  var overlayUrl: String get() = sp.getString("overlayUrl","") ?: "" ; set(v){ sp.edit().putString("overlayUrl",v).apply() }
}