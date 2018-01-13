-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志
-dontskipnonpubliclibraryclasses

#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

#-libraryjars libs/android-serialport.jar
#-libraryjars libs/cashiersdk.jar
#-libraryjars libs/core.jar

#-dontwarn    com.landfoneapi.misposwa.*
#-dontwarn    LfApi.*
#-dontwarn    com.landfoneapi.protocol.*
#-dontwarn    com.landfoneapi.protocol.pkg.*
-dontwarn    com.zxing.*
-dontwarn    com.zxing.encoding.*
-dontwarn    com.google.*
-dontwarn    com.google.zxing.*
-dontwarn    com.landfoneapi.protocol.util.*

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
#-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
#-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆
-keep public class * extends java.lang.Exception


-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆   
    public void *(android.view.View);
}
#-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆  
    public static final android.os.Parcelable$Creator *;
}
#------------------------------------------------my----------------------------------------
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    *;
}
#保持LfException的public *;
-keep  public class com.landfoneapi.protocol.result.LfException {
    public *;
}
#保持SerialPort所有内容
-keepclasseswithmembers public class android_serialport_api.SerialPort{
    *;
}

-keep public class com.landfoneapi.protocol.LfPosApiMispos {
    public *;
}
-keep  public class com.landfoneapi.misposwa.LfApi {
    public *;
}
#-keepclasseswithmembers public class com.landfoneapi.misposwa.MyApi {
#     public *;
# }
-keep  public class com.landfone.common.utils.LfException {
    public *;
}

#-keepclasseswithmembers public class com.landfoneapi.mispos.ILfListener{
#    public *;
#}
-keep public class com.landfoneapi.mispos.CallbackMsg{
   public *;
}
-keep public class com.landfoneapi.mispos.Display{
   public *;
}
-keep public class com.landfoneapi.mispos.DisplayType{
    public *;
 }
 -keep public class com.landfoneapi.protocol.pkg.REPLY{
    public *;
 }
 -keep public class com.landfoneapi.protocol.pkg.jxnx._04_BoundAccountQueryReply{
  public *;
}
 -keep public class com.landfoneapi.protocol.pkg.jxnx._04_DownloadAppcodeReply{
   public *;
}
 -keep public class com.landfoneapi.protocol.pkg.jxnx._04_GetZNRecordInfoReply {
   public *;
 }
  -keep public class com.landfoneapi.protocol.pkg._04_GetRecordReply {
    public *;
  }
 -keep public class com.landfoneapi.protocol.pkg.jxnx._04_PassbookRenewReply {
   public *;
 }
 -keep public class com.landfoneapi.protocol.pkg.jxnx._04_PayfeesQueryReply {
   public *;
 }
 -keep public class com.landfoneapi.protocol.pkg.jxnx._04_ReadAccountNameReply {
   public *;
 }
  -keep public class com.landfoneapi.protocol.pkg._04_GetPrintInfoReply {
    public *;
  }
 -keep public class com.landfoneapi.protocol.pkg.RequestPojo {
      public *;
 }
  -keep public class com.landfoneapi.protocol.pkg.ResponsePojo {
  public *;
 }
#   -keep public class com.landfoneapi.protocol.pkg.jxnx._04_GetCardReaderReply {
#     public *;
#   }
-keep public class com.landfoneapi.mispos.E_REQ_RETURN{
    public *;
}
-keep public abstract interface com.landfoneapi.mispos.ILfListener{
    *;
}

-keep public class com.landfoneapi.misposwa.MyApi {
     public *;
 }
#-keep class  com.landfoneapi.misposwa.** { public *;}
#-keep class  LfApi.** { *;}
#-keep class  com.landfoneapi.protocol.** { *;}
#-keep class  com.landfoneapi.protocol.pkg.** { *;}
-keep class  com.zxing.** { *;}
-keep class  com.zxing.encoding.** { *;}
-keep class  com.google.** { *;}
-keep class  com.google.zxing.** { *;}
#-keep class  com.landfoneapi.protocol.util.** { *;}
