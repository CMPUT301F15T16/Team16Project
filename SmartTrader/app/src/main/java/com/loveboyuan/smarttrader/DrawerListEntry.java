package com.loveboyuan.smarttrader;

/**
 * Created by jiahui on 11/29/15.
 */

// Based on http://hmkcode.com/android-custom-listview-titles-icons-counter/
// but changed extensively
public class DrawerListEntry {
    private int icon;
    private String title;
    private boolean isHeader = false;

    public DrawerListEntry(String title) {
        this(-1, title);
        isHeader = true;
    }

    public DrawerListEntry(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setIsHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }

    // override method for usage in MainActivity
    @Override
    public String toString() {
        return this.title;
    }
}
