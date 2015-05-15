# ToolBarLib
Java Android v21 v19 toolBar Library advanced
=================================================

#setup
=======
not yet implemented
```gradle
   compile 'com.hkm:adanvedtoolbar:v1.0.1'

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
quick review

![demo1](screenshot/device-2015-05-15-171739.png)
![demo2](screenshot/device-2015-05-15-171813.png)