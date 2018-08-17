package com.chujiu.core.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created on   2015年7月31日
 * Description: [描述该类功能介绍]
 * Copyright:   Copyright (c) 2015
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     nisicong
 * @version:    1.0
*/
public class JSONTool {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    /**
     * Created on   2015年7月31日
     * Discription: [把对象转换成json]
     * @author:     nisicong
     * @update:     2015年7月31日 下午3:31:16
     * @return      String .
     */
    public static String objectToJson(Object obj) {
        String result = null;
        try {
            result = mapper.writeValueAsString(obj);
        }
        catch (JsonGenerationException e) {
            e.printStackTrace();
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
