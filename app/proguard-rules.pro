# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep model classes
-keep class com.greekevents.domain.model.** { *; }

# Keep Hilt classes
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.lifecycle.HiltViewModel

# Keep Retrofit and OkHttp classes
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-dontwarn okhttp3.internal.platform.**
-dontwarn org.bouncycastle.**
-dontwarn org.conscrypt.**
-dontwarn org.openjsse.**

# Keep Glide classes
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Keep Room classes
-keep class androidx.room.** { *; }

# Keep Navigation component classes
-keep class androidx.navigation.** { *; }

# Keep Coroutines classes
-keep class kotlinx.coroutines.** { *; } 