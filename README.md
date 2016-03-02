DayNight-Theme
===============
Google released Android Support Library 23.2 on February 24, 2016, it contains new DayNight Theme.
However right now (March 2), it is not work on Marshmallow, so there is a temporary way.

System have a night mode setting and we can use `UiModeManager.setNightMode` to change its value, it will only take effect on Marshmallow,
and system's default value is `MODE_NIGHT_NO`.

So we can set system's night mode for Marshmallow and use support library for pre-Marshmallow.

###Usage:
If you are using `AppCompatActivity`, use `BaseDayNightModeActivity` as `AppCompatActivity`
If not, see [`BaseDayNightModeActivity`](https://github.com/RikkaW/DayNight-Theme/blob/master/daynightmode/src/main/java/moe/xing/daynightmode/BaseDayNightModeActivity.java)
Use `BaseDayNightModeActivity.setNightMode()` to change night mode runtime<br/>
Set night mode in [`Application`](https://github.com/RikkaW/DayNight-Theme/blob/master/app/src/main/java/rikka/daynight/MyApplication.java) class to set default value

###Note:
On Marshmallow, it is atemporary way, any app which contains `-night` resource will be affected.
