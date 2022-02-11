package com.tanvir.training.ecomadminbatch2.models;

import com.tanvir.training.ecomadminbatch2.R;
import com.tanvir.training.ecomadminbatch2.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DashboardItem {
    private int icon;
    private String itemName;

    public DashboardItem(int icon, String itemName) {
        this.icon = icon;
        this.itemName = itemName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public static List<DashboardItem> getDashboardItems() {
        final ArrayList<DashboardItem> items = new ArrayList<>();
        items.add(new DashboardItem(R.drawable.ic_baseline_photo_album_24, Constants.Item.ADD_PRODUCT));
        items.add(new DashboardItem(R.drawable.ic_baseline_photo_album_24, Constants.Item.ADD_CATEGORY));
        items.add(new DashboardItem(R.drawable.ic_baseline_photo_album_24, Constants.Item.VIEW_ORDERS));
        items.add(new DashboardItem(R.drawable.ic_baseline_photo_album_24, Constants.Item.VIEW_PRODUCT));
        items.add(new DashboardItem(R.drawable.ic_baseline_photo_album_24, Constants.Item.VIEW_USERS));
        items.add(new DashboardItem(R.drawable.ic_baseline_photo_album_24, Constants.Item.SETTINGS));
        items.add(new DashboardItem(R.drawable.ic_baseline_photo_album_24, Constants.Item.VIEW_REPORTS));
        return items;
    }
}
