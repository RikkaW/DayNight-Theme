DayNight-Theme
===============
Google released Android Support Library 23.2 on February 24, 2016, it contains new DayNight Theme.
However right now (March 2), it is not work on Marshmallow, so there is a temporary way.

###Usage:
Add it in your root build.gradle at the end of repositories:<br/>
`maven { url "https://jitpack.io" }`<br/>
Add the dependency<br/>
`compile 'com.github.RikkaW:DayNight-Theme:v1.0.5'`

Use `BaseDayNightModeActivity.setNightMode()` to change night mode runtime<br/>
Use `DayNightMode.setDefaultNightMode` in `application` class to set default night mode<br/>

If you are using `AppCompatActivity`, use `BaseDayNightModeActivity` as `AppCompatActivity`<br/>
If not, see [`BaseDayNightModeActivity`](https://github.com/RikkaW/DayNight-Theme/blob/master/daynightmode/src/main/java/moe/xing/daynightmode/BaseDayNightModeActivity.java)<br/>

###Note:
On Marshmallow, if you use `DayNightMode.setSystemNightMode` to change system setting, any app which contains `-night` resource will be affected.
