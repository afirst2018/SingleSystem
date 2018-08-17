package com.chujiu.manager.codelist.managerservice;


import com.chujiu.manager.codelist.managerdao.CodeListDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on   2015年7月8日
 * Description: [数据字典缓存 ]
 * Copyright:   Copyright (c) 2015
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
@Component
public class CodeListCacheDBImpl {
    @Autowired
    private CodeListDAO codeListDAO;
    
    /**
     * Created on   2015年7月31日
     * Discription: [初始化codeList缓存]
     * @author:     nisicong
     * @update:     2015年7月31日 下午2:39:18
     * @return      Map<String,Map<String,String>> .
     */
    @Cacheable(value = "codeListCache", key="#root.methodName")
    public Map<String, List<Map<String, String>>> initCodeListCache () {
    	Map<String, List<Map<String, String>>> codeListCache = new HashMap<String, List<Map<String, String>>>(100);
        List<Map<String, String>> result = codeListDAO.selectAllCodeList();
        String tempKindValue = "";
        List<Map<String, String>> list = null;
        for (Map<String, String> map : result) {
            String kindValue = map.get("KIND_VALUE");
            if (!tempKindValue.equals(kindValue)) {
            	tempKindValue = kindValue;
            	list = new ArrayList<Map<String, String>>(10);
            	codeListCache.put(kindValue, list);
            }
            Map<String, String> temp = new HashMap<String, String>();
        	temp.put(map.get("CODE_VALUE"), map.get("CODE_NAME"));
			list.add(temp);
        }
        return codeListCache;
    }
	
    /**
     * Created on   2015年11月2日
     * Discription: [初始化转码codeList缓存]
     * @return Map<String,Map<String,String>>
     * @author:     nisicong
     * @update:     2015年11月2日 下午4:42:00
     */
    @Cacheable(value = "codeListCache", key="#root.methodName")
    public Map<String, Map<String, String>> getCodeListCacheForDecode () {
    	Map<String,Map<String,String>> codeListCache = new HashMap<String, Map<String,String>>(100);
        List<Map<String, String>> result = codeListDAO.selectAllCodeList();
        String tempKindValue = "";
        Map<String, String> kindValueMap = null;
        for (Map<String, String> map : result) {
            String kindValue = map.get("KIND_VALUE");
            if (!tempKindValue.equals(kindValue)) {
                kindValueMap = new HashMap<String, String>(10);
                tempKindValue = kindValue;
                codeListCache.put(kindValue, kindValueMap);
            }
            kindValueMap.put(map.get("CODE_VALUE"), map.get("CODE_NAME"));
        }
        return codeListCache;
    }
}
