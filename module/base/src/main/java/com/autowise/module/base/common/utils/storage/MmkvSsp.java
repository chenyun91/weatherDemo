package com.autowise.module.base.common.utils.storage;

import android.content.Context;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tencent.mmkv.MMKV;
import java.util.Map;
import androidx.annotation.Nullable;

public abstract class MmkvSsp {
    private MMKV mmkv;
    private String mmkvId;

    private MmkvSsp(Context context) {
        mmkv = MMKV.defaultMMKV();
    }


    public MmkvSsp(Context context, String spName) {
        this(context, spName, null);
    }

    /**
     * @param context
     * @param spName   文件名
     * @param rootPath 根目录，默认 /files/mmkv/
     */
    public MmkvSsp(Context context, String spName, String rootPath) {
        MMKV.initialize(context);
        if (TextUtils.isEmpty(spName)) {
            mmkv = MMKV.defaultMMKV();
            mmkvId = MMKV.getRootDir();
        } else {
            mmkv = MMKV.mmkvWithID(spName, rootPath);
            mmkvId = spName;
        }
    }


    public String getName() {
        return mmkvId;
    }


    public void putBoolean(String key, boolean value) {
        mmkv.encode(key, value);
    }


    public boolean getBoolean(String key, boolean defaultValue) {
        return mmkv.decodeBool(key, defaultValue);
    }


    public void putLong(String key, long value) {
        mmkv.putLong(key, value);
    }


    public long getLong(String key, long defaultValue) {
        return mmkv.getLong(key, defaultValue);
    }


    public void putInt(String key, int value) {
        mmkv.putInt(key, value);
    }


    public int getInt(String key, int defaultValue) {
        return mmkv.getInt(key, defaultValue);
    }


    public void putString(String key, String value) {
        mmkv.putString(key, value);
    }

    public void putParcelable(String key, Parcelable value) {
        mmkv.encode(key, value);
    }

    @Nullable
    public <T extends Parcelable> T getParcelable(String key, Class<T> tClass) {
        return mmkv.decodeParcelable(key, tClass);
    }


    public String getString(String key, String defaultValue) {
        return mmkv.getString(key, defaultValue);
    }


    public boolean contains(String key) {
        return mmkv.contains(key);
    }


    public Map<String, ?> getAll() {
        return mmkv.getAll();
    }


    protected void onRemove(String key) {
        mmkv.remove(key);
    }


    protected void onClear() {
        mmkv.clear();
    }

    public abstract void logout();
}
