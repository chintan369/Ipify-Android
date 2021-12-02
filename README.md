# Ipify-Android
[![](https://jitpack.io/v/chintan369/Ipify.svg)](https://jitpack.io/#chintan369/Ipify)

Ipify allows you to get current public IP address when connected to internet in real-time

## Add Dependency
Use Gradle:

**Step 1:** Add it in your root _`build.gradle`_ at the end of repositories:
```gradle
allprojects {
    repositories {
      ...
      maven { url 'https://jitpack.io' }
    }
}
```

**Note:** In New Android studio updates, now `allProjects` block has been removed from root `build.gradle` file. So you must add this repository to your root _`settings.gradle`_ as below:
```gradle
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
      ...
      maven { url "https://jitpack.io" }
  }
}
```

**Step 2:** Add the dependency in your module's (e.g. app or any other) _`build.gradle`_ file:
```gradle
dependencies {
    ...
    implementation 'com.github.chintan369:Ipify:<latest-version>'
}
```

## How do I use Ipify-Android?

### Step 1
Initialize Ipify in your application class of Android app's `onCreate()` method

```kotlin
public class MyApplication : Application() {
    ...
    
    override fun onCreate() {
        super.onCreate();
        
        Ipfy.init(this) // this is a context of application
        //or you can also pass IpfyClass type to get either IPv4 address only or universal address IPv4/v6 as
        Ipfy.init(this,IpfyClass.IPv4) //to get only IPv4 address
        //and
        Ipfy.init(this,IpfyClass.UniversalIP) //to get Universal address in IPv4/v6
    }
    ...
}
```

### Step 2
Observe IP address at anywhere in your application. This function will provide you an observer to observe the IP address while changing the network connection from mobile data to Wi-Fi or any other Wi-Fi.

```kotlin
    Ipfy.getInstance().getPublicIpObserver().observe(this, { ipData ->
          ipData.currentIpAddress // this is a value which is your current public IP address, null if no/lost internet connection
          ipData.lastStoredIpAddress // this is a previous IP address while network lost/reconnected and current IP address assigned to null/new one
    })
```

This is how you can get a public IP address without doing any more code! :smile: 

Feel free to post any issue if you found on this library :smile: !

If you :heart: this library and my hard work, you can buy me a coffee at link here :coffee:

<a href="https://www.buymeacoffee.com/chintan369" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy Me A Coffee" style="height: 60px !important;width: 217px !important;" ></a>
