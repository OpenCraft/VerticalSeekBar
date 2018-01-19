# VerticalSeekBar
This component act like seekbar but support vertical position. With this component you can change de thumb icon, the background color and also listen for every value changes and every Y position changes. Also you can setup the start value.

![VerticalSeekBar](https://lh6.googleusercontent.com/q4c7ke905i78vITgVvNLAkXKz6fSkotVLe533S9je9Mso1ZTL2Fp1I5XHaQJoYizjokg0auKqvIZYD_mM6u0=w1280-h581-rw)

# Usage with gradle

```xml
  compile 'com.github.OpenCraft:VerticalSeekBar:v1.0.10'
```



# Sample

```xml
<opencraft.com.verticalseekbarlib.VerticalSeekBar
        android:id="@+id/seekbar"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_above="@+id/bottom"
        android:layout_below="@+id/top"
        android:clipChildren="false"
        app:seekbar_backgroundColor="@color/colorPrimaryDark"
        app:seekbar_max_value="2500"
        app:seekbar_step="25"
        app:seekbar_thumb="@drawable/check_on"
        app:seekbar_thumbMarginTop="-25dp"
        app:seekbar_value="5000" />
```

## Author
Vinicius Sossella
viniciussossella@gmail.com
