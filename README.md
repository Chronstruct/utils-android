To use, in your app/build.gradle:

```kotlin
dependencies {
    // ...implementations
    
    implementation('com.github.Chronstruct:utils-android:v0.0.1') {
        exclude group: 'com.android.support'
    }
}
```

# Helpful docs
## Library design
- [How to improve your Android Library – Maxi Rosson – Medium](https://medium.com/@maxirosson/how-to-improve-your-android-library-2e8b9fd6c090)

## Setting up
### JitPack.io
- [Publishing Java / android / kotlin libraries on Jitpack](https://medium.com/@erluxman/publishing-java-android-kotlin-libraries-on-jitpack-b33d0d26dc8a)
- [Publish an Android Library by JitPack](https://medium.com/@ome450901/publish-an-android-library-by-jitpack-a0342684cbd0)
- [android\-jitpack\-library\-example/build\.gradle at master · jitpack\-io/android\-jitpack\-library\-example](https://github.com/jitpack-io/android-jitpack-library-example/blob/master/app/build.gradle)

### Local testing
- [How to link an Android app to a local library project](https://gist.github.com/nolanlawson/fe5bfab1867d71000c81)