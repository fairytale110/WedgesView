
## WedgesView
> A WedgesView based on android.View, nicely rotation、easy to use.

[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg)](https://android-arsenal.com/api?level=19) 
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Download](https://img.shields.io/badge/Download-1.0.1-B93B8F.svg) ](https://github.com/fairytale110/WedgesView/archive/1.0.1.zip)

### Preview
The actual running effect will be better
![preview](https://raw.githubusercontent.com/fairytale110/WedgesView/master/art/Screenrecord-2018-09-18-12-57-07-008.gif "optional title")


### Features

Supported functions：

- [x] Optionally configure the colors of each wedge

- [x] Manually stop and start the wedge rotation

- [x] Configurable rotation speed

- [x] Support the padding Settings


Support will be forthcoming:

- [ ] Manual rotation

- [ ] Refresh header view is supported

- [ ] More... 

### How to 

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
  allprojects {
     repositories {
       ...
       maven { url 'https://jitpack.io' }
     }
  }
```
Step 2. Add the dependency
```
 dependencies {
     implementation 'com.github.fairytale110:WedgesView:1.0.1'
 }
```

### Usage

```java
  <tech.nicesky.libwedgesview.WedgesView
        android:id="@+id/wedgesView"
        android:layout_width="222dp"
        android:layout_height="222dp"
        app:wv_background="@android:color/white"
        app:wv_rotate_speed="0.5"
        app:wv_wedge_alpha="0.8"
        app:wv_wedge_diameter="@dimen/dp_222"
        android:padding="@dimen/dp_20"
        />
```
or
```java

  int[] colors = new int[4];
        colors[0] = Color.parseColor("#C2DFD7");
        colors[1] = Color.parseColor("#FFE6F5");
        colors[2] = Color.parseColor("#FE718D");
        colors[3] = Color.parseColor("#E90C59");
        WedgesView wedgesView = new WedgesView(this);
        wedgesView.setBackgroundColor(Color.WHITE);//Set View's background color
        wedgesView.setColors(colors);//set wedges's color
        wedgesView.setRotateSpeed(0.5F);//set wedges's fastest speed
        wedgesView.setWedgeAlpha(0.8F);//set wedges's alpha
        //set wedges diameter
        wedgesView.setWedgeDiameter((int) getResources().getDimension(R.dimen.dp_200));
        wedgesView.reStart();//begin anim
        //wedgesView.stop();// stop anim
```

### Participate in the contribution
fairytale110@foxmail.com


### Author
fairytale110@foxmail.com
> 简书: http://jianshu.com/u/d95b27ffdd3c

> 掘金: https://juejin.im/user/596d91ee6fb9a06bb874a800

> CSDN: https://blog.csdn.net/LJYBQ

> MY WEB: https://nicesky.tech
 
 
## LICENSE

```
  Copyright 2018 fairytale110

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```
