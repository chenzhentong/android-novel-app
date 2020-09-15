package com.example.novelapplication.model;

import android.app.Activity;

public class MySettingItem {
    private Integer resourceId;
    private String text;
    private Activity targetActivity;

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Activity getTargetActivity() {
        return targetActivity;
    }

    public void setTargetActivity(Activity targetActivity) {
        this.targetActivity = targetActivity;
    }

    public MySettingItem(Integer resourceId, String text, Activity targetActivity) {
        this.resourceId = resourceId;
        this.text = text;
        this.targetActivity = targetActivity;
    }

    public MySettingItem() {
    }


}
