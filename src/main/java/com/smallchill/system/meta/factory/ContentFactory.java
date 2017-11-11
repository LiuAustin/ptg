package com.smallchill.system.meta.factory;

import com.smallchill.core.meta.MetaManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/14.
 */
public class ContentFactory extends MetaManager {

//    public Class<? extends MetaIntercept> intercept() {
//        return ContentIntercept.class;
//    }

    public String controllerKey() {
        return "parameter";
    }

//    public String paraPrefix() {
//        return getTableName(Content.class);
//    }

    public Map<String, String> renderMap() {
        Map<String, String> renderMap = new HashMap<>();
        renderMap.put(KEY_INDEX, "/system/content/content.html");
        renderMap.put(KEY_ADD, "/system/content/content_add.html");
        renderMap.put(KEY_EDIT, "/system/content/content_edit.html");
        renderMap.put(KEY_VIEW, "/system/content/content_view.html");
        return renderMap;
    }

    public Map<String, String> sourceMap() {
        Map<String, String> sourceMap = new HashMap<>();
        sourceMap.put(KEY_INDEX, "content.sourceList");
        return sourceMap;
    }

}
