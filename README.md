# UD405 Samples & Assignments

Author of [UD405 - 2D Game Development with LibGDX](https://www.udacity.com/courses/ud405) decided to post every sample / exercise / assignment in different projects and this is driving me crazy. It is really annoying to import a project just to see a short example, course author!

So, I decided to re-create the examples in one project. And in a shiny new Kotlin language from JetBrains. 

I *could* have used different branches, tags, etc... but did the simplest thing and placed them all in one project. You'll need to change the starting class from `desktop` or `android` projects to review different examples. 

I am adding code as I advance throughout the course. 

To run the projects, install JDK 1.8 (or JDK 1.6 but seriously...) and do these:

 1. Install Android SDK, Platform Tools and Build tools for 23.0.1
 2. Clone the project (obviously) 
 3. Create file `local.properties` containing `sdk.dir=<local-android-sdk>` 
 4. Type `./gradlew clean desktop:run`

Enjoy!
