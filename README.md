DayNight-Theme
===============
Google released Android Support Library 23.2 on February 24, 2016, it contains new DayNight Theme.
However right now (March 2), it is not work on Marshmallow, so there is a temporary way.

System have a night mode setting and we can use `UiModeManager.setNightMode` to change its value, it will only take effect on Marshmallow,
and system's default value is `MODE_NIGHT_NO`.

So we can set system's night mode for Marshmallow and use support library for pre-Marshmallow.

###Usage:
Add it in your root build.gradle at the end of repositories:<br/>
`maven { url "https://jitpack.io" }`<br/>
Add the dependency<br/>
`compile 'com.github.RikkaW:DayNight-Theme:v1.0.3'`

Use `BaseDayNightModeActivity.setNightMode()` to change night mode runtime<br/>
Use `DayNightMode.setDefaultNightMode` in `application` class to set default night mode<br/>

If you are using `AppCompatActivity`, use `BaseDayNightModeActivity` as `AppCompatActivity`<br/>
If not, see [`BaseDayNightModeActivity`](https://github.com/RikkaW/DayNight-Theme/blob/master/daynightmode/src/main/java/moe/xing/daynightmode/BaseDayNightModeActivity.java)<br/>

###Note:
On Marshmallow, it is a temporary way, any app which contains `-night` resource will be affected.
