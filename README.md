# ToolBarLib

Android ActionBar Advanced Support Library for APIv22,v21,v19. This will be a better one for all kinds of design. This library bundled alot of design patterns in the advanced actionbar supports.

Java Android v22 v21 v19 toolBar Library advanced
Projec Website: [TOOLBARLIB](https://github.com/jjhesk/ToolBarLib)

##Features
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

# Methods table introduction

There are several attributes you can set:

| methods | func |
|:---|:---|
| searchView | there are 4 options. The available options are ```classic_3```, ```classic_2```, ```classic_1```  |
| companyLogo | The drawable Id for the company logo |
| background | The navigation bar background drawable Id |
| burgerIcon | customize the top left corner icon with the resource Id |
| searchBarEvents | the implementation of the listener event for the plugins |
