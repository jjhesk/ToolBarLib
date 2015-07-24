# ToolBarLib

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ToolBarLib-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2158)

Android ActionBar Advanced Support Library for APIv22,v21,v19. This will be a better one for all kinds of design. This library bundled alot of design patterns in the advanced actionbar supports.

Java Android v22 v21 v19 toolBar Library advanced
Projec Website: [TOOLBARLIB](https://github.com/jjhesk/ToolBarLib)

##Features
v0.3.2
- [x] added the social bar into the library

v0.2.2
- [x] automatic show and hide softkeyboard

v0.2.1
- [x] support external Textview library
- [x] support search design redesign
- [x] support title center
- [x] support animation on search bar
- [x] support overlay/drawable backgrounds

##Quick review

- [x] Custom Textview
- [x] Center Textview alignment
![demo1](screenshot/device-2015-05-15-171739.png)



- [x] Search pattern redesigned
![demo2](screenshot/device-2015-05-15-171813.png)




##setup
[![Download](https://api.bintray.com/packages/jjhesk/maven/advancedtoolbar/images/download.svg) ](https://bintray.com/jjhesk/maven/advancedtoolbar/_latestVersion)
X.X.X please check out the above version name.
```gradle
repositories {
    maven {  url "http://dl.bintray.com/jjhesk/maven"  }
}

dependencies {
     compile 'ToolBarLib:advancedtoolbar:0.2.20'
}

```

##implementation
You can call by simple direct API from the code and it will do the magic for you.
The new implementation - [the easiest](https://github.com/jjhesk/ToolBarLib/wiki/The-Easy-Way)
Simple way to start your module
```java
TopBarManager worker = TopBarManager.ManagerBulider.with(this)
            .companyLogo(R.drawable.actionbar_bg_pb_logo)
            .background(R.drawable.hb_white_normal_border)
            .burgerIcon(R.mipmap.ic_action_menu)
            .searchView(SearchCustom.LAYOUT.classic_3)
            .build(this, tb);
```

# Methods table for custom toolbar introduction

There are several attributes you can set:

| methods | func |
|:---|:---|
| searchView | there are 4 options. The available options are ```classic_3```, ```classic_2```, ```classic_1```  |
| companyLogo | The drawable Id for the company logo |
| background | The navigation bar background drawable Id |
| burgerIcon | customize the top left corner icon with the resource Id |
| searchBarEvents | the implementation of the listener event for the plugins |

# Implementation for the social tool bar

Add this component into your xml layout in anywhere
```xml
      <com.hkm.advancedtoolbar.socialbar.combar
                    android:id="@+id/social_bar_combar"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginLeft="10dp"
                    android:layout_marginTop="10dp" />

```
retrieve the component ```combar``` and use the method ```setShareContent``` to inject the information needed for the sharing intents. Please also refer to the table.
```java


      public void setShareContentBar(String title, String except, String link) {
            combar br = (combar) content_view.findViewById(R.id.social_bar_combar);
            br.setShareContent(title, except, link);
        }

```

Social Bar method table for ```combar```:

| methods | func |
|:---|:---|
| searchView | there are 4 options. The available options are ```classic_3```, ```classic_2```, ```classic_1```  |
| setShareContent | inject information into the sharing intents |