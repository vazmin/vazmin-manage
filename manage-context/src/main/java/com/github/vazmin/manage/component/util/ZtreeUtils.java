package com.github.vazmin.manage.component.util;

import com.github.vazmin.framework.core.util.GsonUtils;
import com.github.vazmin.manage.component.model.ZtreeItem;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Ztree处理工具类
 *
 */
public class ZtreeUtils {

    /**
     * 将条目id组合成以逗号分隔的字符串
     * @param ztreeItemList List<ZtreeItem> 条目列表
     * @return String id 组合字符串
     */
    public static String getCheckedIdStr(List<ZtreeItem> ztreeItemList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ZtreeItem item: ztreeItemList) {
            if (item.isChecked()) {
                stringBuilder.append(item.getId()).append(",");
            }
        }
        if (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    /**
     * 将条目列表转换成json字符串
     * @param ztreeItemList List<ZtreeItem> 条目列表
     * @return String Json字符串
     */
    public static String toJsonStr(List<ZtreeItem> ztreeItemList) {
        Type type = new TypeToken<List<ZtreeItem>>() {}.getType();
        return GsonUtils.toJson(ztreeItemList, type);
    }
}
