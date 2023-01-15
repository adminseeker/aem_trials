/**
 * 
 */
package com.demo.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * The Class CommonsUtil.
 *
 * @author ppalla
 */
public class CommonsUtil {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CommonsUtil.class);
	 
 	/**
 	 * Instantiates a new commons util.
 	 */
 	private CommonsUtil() {}
	  
  	/**
  	 * Gets the json from object.
  	 *
  	 * @param obj the obj
  	 * @return the json from object
  	 */
  	public static String getJsonFromObject(Object obj) {
	        Gson gson = new Gson();
	        String json = null;
	        try {
	        	json = gson.toJson(obj);
	        } catch (Exception e) {
	            log.error("Exception occured in CommonsUtil :: getJsonFromObject {}", e);
	        }
	        return json;
	    }
  	
  	/**
  	 * Gets the object from json.
  	 *
  	 * @param jsonString 
  	 * @param obj 
  	 * @return the object from the json
  	 */
  	public static <T> T getObjectFromJson(String jsonString, Class<T> valueType) {
		Gson gson = new Gson();
        Object returnValue = null;
        try {
            returnValue = gson.fromJson(jsonString, valueType);
        } catch (Exception e) {
            log.error("Exception occured in CommonsUtil :: getObjectFromJson {}", e);
        }
        return (T)returnValue;
	}

}
