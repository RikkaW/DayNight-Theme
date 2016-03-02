DayNight-Theme
===============
Google released Android Support Library 23.2 on February 24, 2016, it contains new DayNight Theme.
However right now (March 2), it is not work on Marshmallow, so there is a temporary way.

System have a night mode setting and we can use `UiModeManager.setNightMode` to change its value, it will only take effect on Marshmallow,
and system's default value is `MODE_NIGHT_NO`.

So we can set system's night mode for Marshmallow and use support library for pre-Marshmallow.

Change system setting will affect any app which contains `-night` resource, so the best way is wait Google fix bug.
