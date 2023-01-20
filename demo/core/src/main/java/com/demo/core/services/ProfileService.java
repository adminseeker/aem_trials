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
import com.demo.core.services.ApiProfileService;
import com.demo.core.utils.CommonsUtil;

/**
 * 
 * 
 */
@Component(service = ProfileService.class, immediate = true, property = { "process.label= Profile Service", Constants.SERVICE_DESCRIPTION + "=Demo - Profile Service", Constants.SERVICE_VENDOR + "=Demo",
		Constants.SERVICE_RANKING + ":Integer=1001" })
public class ProfileService {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(ProfileService.class);

	/** The login service. */
	@Reference
	private ApiProfileService apiProfileService;



	/**
	 * Activate.
	 *
	 * @param componentContext the component context
	 * @throws RepositoryException the repository exception
	 * @throws LoginException      the login exception
	 */
	@Activate
	protected void activate(final ComponentContext componentContext) throws RepositoryException, LoginException {
		log.info(" Profile Login Service :: Activate Method");
	}
		/*
		 * Making getProfileById call
		 * 
		 */

	public String getProfileDetails(SlingHttpServletRequest req, SlingHttpServletResponse resp, RequestModel requestModel) {
		
		
		
		ProfileResponse profile = apiProfileService.getMemberProfile(requestModel,requestModel.getProfileId(),requestModel.getIsDemo());
		if(null != profile ) {
			return CommonsUtil.getJsonFromObject(profile);
		} else {
			return CommonsUtil.getJsonFromObject(null);
		}
		
	}

		/*
		 * Making getProfiles call
		 * 
		 */

	public List<ProfileResponse>  getProfileList(RequestModel requestModel) {
		
		List<ProfileResponse> profiles = apiProfileService.getAllMemberProfile(requestModel, requestModel.getIsDemo());
		if(null != profiles ) {
			return profiles;
		} else {
			return null;
		}
		
	}

		
		/*
		 * Making addProfile call
		 * 
		 */

	
	public String addProfile(SlingHttpServletRequest req, SlingHttpServletResponse resp, RequestModel requestModel) {		
		String profileResp = null;
		try {

			String body = IOUtils.toString(req.getReader());
			ProfileResponse profile = apiProfileService.addMemberProfile(requestModel,body,requestModel.getIsDemo());
		if(null != profile ) {
			profileResp= CommonsUtil.getJsonFromObject(profile);
		} else {
			profileResp = CommonsUtil.getJsonFromObject(null);
		} 
		
	}
		catch (IOException e) {
			e.printStackTrace();
			profileResp = CommonsUtil.getJsonFromObject(null);

		} 
		return profileResp;
		
	} 

	/*
		 * delete getProfileById call
		 * 
		 */

		 public String deleteProfile(SlingHttpServletRequest req, SlingHttpServletResponse resp, RequestModel requestModel) {
		
		
		
			ResponseStatusMsg statusMsg = apiProfileService.deleteMemberProfile(requestModel,requestModel.getProfileId(),requestModel.getIsDemo());
			if(null != statusMsg ) {
				return CommonsUtil.getJsonFromObject(statusMsg);
			} else {
				return CommonsUtil.getJsonFromObject(null);
			}
			
		}

		/*
		 * edit getProfileById call
		 * 
		 */

		public String editProfile(SlingHttpServletRequest req, SlingHttpServletResponse resp, RequestModel requestModel) {		
			String profileResp = null;
			try {
	
				String body = IOUtils.toString(req.getReader());
				ProfileResponse profile = apiProfileService.editMemberProfile(requestModel,body,requestModel.getProfileId(),requestModel.getIsDemo());
			if(null != profile ) {
				profileResp= CommonsUtil.getJsonFromObject(profile);
			} else {
				profileResp = CommonsUtil.getJsonFromObject(null);
			} 
			
		}
			catch (IOException e) {
				e.printStackTrace();
				profileResp = CommonsUtil.getJsonFromObject(null);
	
			} 
			return profileResp;
			
		} 
	

}
