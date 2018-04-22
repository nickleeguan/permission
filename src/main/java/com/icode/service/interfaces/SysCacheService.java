package com.icode.service.interfaces;

import com.icode.beans.CacheKeyConstants;

public interface SysCacheService {

    void saveCache(String value, int timeout, CacheKeyConstants prefix);

    void saveCache(String value, int timeout, CacheKeyConstants prefix, String... keys);

    String getFromCache(CacheKeyConstants prefix, String... keys);
}
