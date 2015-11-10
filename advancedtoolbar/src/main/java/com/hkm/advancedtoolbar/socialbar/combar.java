package com.hkm.advancedtoolbar.socialbar;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.hkm.advancedtoolbar.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by hesk on 24/7/15.
 */
public class combar extends FrameLayout implements View.OnClickListener {
    private Context rescontext;
    private List<ResolveInfo> list;
    private String confirm_context_except = "nothing in here";
    private String title = "New discovery";
    private FragmentManager frag;

    public combar(Context context) {
        super(context);
        init(context);
    }

    public combar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public combar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context cont) {
        View con = getView(cont, R.layout.socialbar);
        rescontext = cont;
        list = getList();
        con.findViewById(Hg.facebook.Id()).setOnClickListener(this);
        con.findViewById(Hg.message.Id()).setOnClickListener(this);
        con.findViewById(Hg.whatsapp.Id()).setOnClickListener(this);
        con.findViewById(Hg.pintrest.Id()).setOnClickListener(this);
        con.findViewById(Hg.twitter.Id()).setOnClickListener(this);
    }

    public combar connectAlert(FragmentManager fragmentm) {
        this.frag = fragmentm;
        return this;
    }

    public static combar with(Context h) {
        return new combar(h);
    }

    private String template;

    public combar setFormat(String template) {
        this.template = template;
        return this;
    }

    public combar setShareContent(String title, String except, String link) {
        if (template == null) {
            confirm_context_except = "I just read an article about " + title + ", check it out @ " + link;
        } else {
            confirm_context_except = String.format(template, title, except, link);
        }
        return this;
    }


    protected View getView(final Context m, final @LayoutRes int layout) {
        return LayoutInflater.from(m).inflate(layout, this);
    }

    @Override
    public void onClick(View v) {
        try {
            Hg instance_icon = Hg.reverseId(v.getId());
            int app_location = packagenameexist(instance_icon);
            if (app_location > -1) {
                share(app_location);
            } else {
                instance_icon.alert(frag, rescontext);
            }

        } catch (Exception e) {

        }
    }

    private void shareAppLinkViaFacebook(String packagename) {
        if (!packagename.equalsIgnoreCase(Hg.facebook.getPackageName())) {
            return;
        }
        /*String urlToShare = "YOUR_URL";

        try {
            Intent intent1 = new Intent();
            intent1.setClassName("com.facebook.katana", "com.facebook.katana.activity.composer.ImplicitShareIntentHandler");
            intent1.setAction("android.intent.action.SEND");
            intent1.setType("text/plain");
            intent1.putExtra("android.intent.extra.TEXT", urlToShare);
            startActivity(intent1);
        } catch (Exception e) {
            // If we failed (not native FB app installed), try share through SEND
            Intent intent = new Intent(Intent.ACTION_SEND);
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            startActivity(intent);
        }*/
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void share(final int position_app) {
        ActivityInfo activity = list.get(position_app).activityInfo;
        ComponentName nameComponent = new ComponentName(activity.applicationInfo.packageName, activity.name);

        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, confirm_context_except);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        Intent newIntent = (Intent) shareIntent.clone();
        //| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        newIntent.setComponent(nameComponent);
        newIntent.setPackage(activity.packageName);

        rescontext.startActivity(newIntent);
    }

    private int packagenameexist(final Hg compareHg) {
        Iterator<ResolveInfo> ipm = list.iterator();
        int g = 0;
        while (ipm.hasNext()) {
            ResolveInfo h = ipm.next();
            if (h.activityInfo.applicationInfo.packageName.contains(compareHg.getPackageName())) {
                return g;
            }
            g++;
        }
        return -1;
    }

    private List<ResolveInfo> getList() {

        final PackageManager pm = rescontext.getPackageManager();
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "action shared");
        return pm.queryIntentActivities(shareIntent, 0);
    }
}
