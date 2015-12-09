package com.loveboyuan.smarttrader;

/**
 * Created by jiahui on 11/29/15.
 *
 * DrawerListEntry is a model that contains information coming from
 * "list_entry" or "list_header_entry" xml waiting to be actually
 * inflated by the DrawerListAdapter model.
 */
public class DrawerListEntry {
    // Based on http://hmkcode.com/android-custom-listview-titles-icons-counter/
    // but changed extensively
    private int icon;
    private String title;
    private boolean isHeader = false;

    /**
     * Constructor for the header entries.
     * @param title title to be displayed
     */
    public DrawerListEntry(String title) {
        this(-1, title);
        isHeader = true;
    }

    /**
     * Constructor for ordinary entries;
     * note that ordinary entries contain references to icons
     * while header entries do not.
     * @param icon reference to an drawable png
     * @param title title to be displayed
     */
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
