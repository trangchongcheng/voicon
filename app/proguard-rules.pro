-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-keepattributes Signature
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-dontwarn android.support.**
-dontwarn com.instagram.common.json.**
-dontwarn com.fasterxml.jackson.databind.ext.DOMSerializer
-dontwarn com.squareup.javawriter.JavaWriter
-dontwarn com.google.common.primitives.UnsignedBytes*
-dontwarn sun.misc.Unsafe
-dontwarn javax.annotation.**
-dontwarn com.squareup.picasso.**
-dontwarn okio.Okio.**
-dontoptimize
-dontpreverify
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keepattributes *Annotation*
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment

-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

# Facebook library
-keep class com.facebook.** {*;}

-keep public class org.jsoup.** {
public protected *;
}
-keep class android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep class com.google.** { *; }
-keep class de.hdodenhof.** { *; }
-keep class com.jmpergar.** { *; }
-keep class com.romainpiel.shimmer.** { *; }
-keep class com.robohorse.gpversionchecker.** { *; }
-keep class com.apradanas.simplelinkabletext.** { *; }
-keep class com.github.bumptech.glide.** { *; }
-keep class com.jpardogo.** { *; }
-keep class com.davemorrissey.** { *; }
-keep class com.robohorse.gpversionchecker.** { *; }

-dontwarn com.squareup.okhttp.**


-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

 -keepclassmembers public class * extends android.view.View {
  void set*(***);
  *** get*();
 }

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
  public static <fields>;
}