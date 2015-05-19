# ToolBarLib
Java Android v21 v19 toolBar Library advanced
=================================================

#setup
=======
[ ![Download](https://api.bintray.com/packages/jjhesk/maven/advancedtoolbarsupport/images/download.svg) ](https://bintray.com/jjhesk/maven/advancedtoolbarsupport/_latestVersion)
```gradle
repositories {
    maven {  url "http://dl.bintray.com/jjhesk/maven"  }
}

dependencies {
     compile 'com.hkm:advancedtoolbarsupport:1.0.1@aar'
}

```

#implementation
=======
You can call by simple direct API from the code and it will do the magic for you.

```java

    private advBar toolbar;
    private ActionBar actionbar;
    private iOSActionBarWorker worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* toolbar = (advBar) findViewById(R.id.toolbar);
        toolbar.colorize(R.color.red_300, this);
        setSupportActionBar(toolbar);*/
        actionbar = getSupportActionBar();
        worker = new iOSActionBarWorker(actionbar);
    }


```


call by direct API method on setup or using xml theme styling;
```java

        worker = new iOSActionBarWorker(actionbar);
        worker.setTitleLayoutArrangement(...)
        worker.setSearchLayoutArrangement(...)
        worker.setCompanyLogo(...)

```


define your own listener for action callbacks

```java

       worker.setSearchEngineListener(...)
       worker.setActionListener(...)

```
define your listener implemenations

This is the required callback action listener:
```java
implements ActionBarActionListener
```

This is the search engine action listener:
```java
implements SearchCustomActionBar.OnSearchListener
```

quick review

![demo1](screenshot/device-2015-05-15-171739.png)
![demo2](screenshot/device-2015-05-15-171813.png)