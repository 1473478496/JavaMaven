package com.vivebest.mall.core.util;

import java.lang.reflect.Field;
import java.util.Date;

public class BeanUtils {
	
	public static boolean anyEmptyOrNull(Object obj){
        boolean result = true;
		if (obj == null) {
            return result;
        }
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
            	
            	if(f.get(obj) instanceof Date){
            		continue;
            	}
            	
	            if (f.get(obj) == null) { 
	                return result;
	            }
				if(f.get(obj) instanceof String){
					if("".equals(f.get(obj))){
						return result;
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
        result = false;
        return result;
        
	}
	
}
