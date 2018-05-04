To use, in your app/build.gradle:

```kotlin
dependencies {
    // ...implementations
    
    implementation('com.github.Chronstruct:utils-android:v0.0.1') {
        exclude group: 'com.android.support'
    }
}
```