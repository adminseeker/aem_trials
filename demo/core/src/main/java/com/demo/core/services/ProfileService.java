package com.demo.core.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.core.bo.ProfileResponse;
import com.demo.core.bo.RequestModel;
import com.demo.core.bo.ResponseStatusMsg;
import com.demo.core.services.apiservices.ApiProfileService;
import com.demo.core.utils.CommonsUtil;

/**
 * 
 * 
 */

public interface ProfileService {

	/** The Constant log. */



	public String getProfileDetails(SlingHttpServletRequest req, SlingHttpServletResponse resp, RequestModel requestModel);

	public List<ProfileResponse>  getProfileList(RequestModel requestModel) ;
	
	public String addProfile(SlingHttpServletRequest req, SlingHttpServletResponse resp, RequestModel requestModel);

	public String deleteProfile(SlingHttpServletRequest req, SlingHttpServletResponse resp, RequestModel requestModel);

	public String editProfile(SlingHttpServletRequest req, SlingHttpServletResponse resp, RequestModel requestModel);	

}
