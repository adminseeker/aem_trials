package com.demo.core.models;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.abdera.protocol.Request;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.demo.core.bo.ProfileResponse;
import com.demo.core.bo.RequestModel;
import com.demo.core.services.ProfileService;

/**
 * The Class ProfileListingModel.
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProfileListingModel {

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;
	
	@SlingObject
	private Resource resource;

    @Inject
    ProfileService profileService;
	
    private List<ProfileResponse> profilesList;

	

    @PostConstruct
	public void init() {
        RequestModel requestModel = new RequestModel();
        requestModel.setIsDemo("true");
		profilesList=profileService.getProfileList(requestModel);
	}

    public List<ProfileResponse> getProfilesList() {
        return profilesList;
    }

    public void setProfilesList(List<ProfileResponse> profilesList) {
        this.profilesList = profilesList;
    }

}