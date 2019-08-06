package com.github.vazmin.manage.support.query;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Chwing on 2018/3/19.
 */
public class Query {

//    /** 排序字段名 */
//    private String fullordering;

    /** 参数map集合 */
    private Map<String, Object> filter = new LinkedHashMap<>();
    /** 原始参数集合 */
    private Map<String, Object> _filter = new LinkedHashMap<>();
    /**
     * 获取解码后的Filter
     * @return Map
     */
    public Map<String, Object> getFilter() {
        this._filter.putAll(filter);
        filter.forEach((key, value) -> {
            try {
                filter.put(key, URLDecoder.decode(value.toString(), StandardCharsets.UTF_8.toString()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        return filter;
    }

    public Map<String, Object> get_filter() {
        return _filter;
    }

    public void set_filter(Map<String, Object> _filter) {
        this._filter = _filter;
    }

    public void setFilter(Map<String, Object> filter) {
        this.filter = filter;
    }

//    public String getFullordering() {
//        return fullordering;
//    }
//
//    public void setFullordering(String fullordering) {
//        this.fullordering = fullordering;
//    }
}
