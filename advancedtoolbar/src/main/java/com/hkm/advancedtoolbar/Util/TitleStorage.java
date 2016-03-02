package com.hkm.advancedtoolbar.Util;

import java.util.ArrayList;

/**
 * Created by hesk on 2/3/16.
 */
public class TitleStorage {
    final ArrayList<String> items = new ArrayList<>();

    public TitleStorage() {

    }

    public void reset() {
        items.clear();
    }

    public int saveTitle(final String title) {
        items.add(title);
        return items.size();
    }

    public int getHistorySteps() {
        return items.size();
    }

    public ArrayList<String> getHistory() {
        return items;
    }

    public String popback() {
        if (items.size() > 0) {
            items.remove(items.size() - 1);
            return items.get(items.size() - 1);
        } else {
            return "";
        }
    }
}
