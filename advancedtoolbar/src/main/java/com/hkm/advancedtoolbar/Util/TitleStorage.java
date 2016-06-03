package com.hkm.advancedtoolbar.Util;

import android.os.Bundle;
import android.support.annotation.Nullable;

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

    public String getCurrentTitle() {
        return items.get(items.size() - 1);
    }

    public String popback() {
        if (items.size() > 0) {
            String takenout = items.remove(items.size() - 1);
            if (items.size() > 0) {
                return items.get(items.size() - 1);
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public final static String
            STEPS = "BB_HISTORY_STEPS",
            IS_LOGOSHOWN = "BB_LOGO_SHOWN",
            IS_BACKSHOWN = "BB_BACK_SHOWN",
            IS_SEARCHSHOWN = "BB_SEARCH_SHOWN",
            IS_TITLESHOWN = "BB_TITLE_SHOWN",
            STRINGARRAY = "BB_HISTORY_TITLES";

    public final void onStateInstaceState(Bundle out) {
        out.putStringArrayList(STRINGARRAY, items);
        out.putInt(STEPS, getHistorySteps());
    }


    public final void onRestoreInstanceState(@Nullable Bundle input) {
        if (input == null) return;
        try {
            items.clear();
            items.addAll(input.getStringArrayList(STRINGARRAY));
        } catch (ClassCastException e) {
        } catch (NullPointerException e) {
        }
    }
}
