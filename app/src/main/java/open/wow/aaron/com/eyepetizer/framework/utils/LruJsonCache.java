package open.wow.aaron.com.eyepetizer.framework.utils;

import android.support.v4.util.LruCache;
import android.text.TextUtils;

import open.wow.aaron.com.eyepetizer.delicacy.bean.DelicacyChoiceBean;


/**
 * 作者：H
 * 时间: 2017/5/22
 * 功能描述:缓存Json数据 内存
 */

public class LruJsonCache {
    public static String DELICACY_CHOICE = "DelicacyChoice";

    private LruCache<String, DelicacyChoiceBean> mMemoryCache;
    //private Gson gson;

    public LruJsonCache() {
        //gson = new Gson();
        int maxMemory = (int) Runtime.getRuntime().maxMemory() / 8;
//        mMemoryCache = new LruCache<String, String>(maxMemory) {
//            @Override
//            protected int sizeOf(String key, String value) {
//                return value.length();
//            }
//        };

        //用于计算每个条目的大小
        mMemoryCache = new LruCache<String, DelicacyChoiceBean>(maxMemory) {
            @Override
            protected int sizeOf(String key, DelicacyChoiceBean mMemoryCache) {
                return mMemoryCache.toString().length();
            }
        };
    }

    /**
     * @return void
     * @Title: addJsonToMemoryCache
     * @Description: TODO 添加json内存
     */
//    public void addJsonToMemoryCache(String key, String jsonString) {
//        //String jsonString = gson.toJson(json);
//
//        if (mMemoryCache == null) {
//            return;
//        }
//        if (TextUtils.isEmpty(key)) {
//            return;
//        }
//
//        if (getJsonFromMemCache(key) == null && jsonString != null) {
//            mMemoryCache.put(key, jsonString);
//        }
//    }
    public void addJsonToMemoryCache(String key, DelicacyChoiceBean delicacyChoiceBean) {
        //String jsonString = gson.toJson(json);

        if (mMemoryCache == null) {
            return;
        }
        if (TextUtils.isEmpty(key)) {
            return;
        }

        if (getJsonFromMemCache(key) == null && delicacyChoiceBean != null) {
            mMemoryCache.put(key, delicacyChoiceBean);
            //Log.e("TAG", "put-------------------");
        }
    }

    /**
     * 从内存缓存中获取一个Json
     *
     * @param key
     * @return
     */
//    public String getJsonFromMemCache(String key) {
//        if (mMemoryCache == null) {
//            return null;
//        }
//        return mMemoryCache.get(key);
//    }
    public DelicacyChoiceBean getJsonFromMemCache(String key) {
        if (mMemoryCache == null) {
            //Log.e("TAG","getJsonFromMemCache() return null");
            return null;
        }
        return mMemoryCache.get(key);
    }
}
