package com.demo.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

/**
 * The Class UrlResolverModel.
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class UrlResolverModel {

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;
	
	@SlingObject
	private Resource resource;
	
	public String getShortUrl() {
		return resourceResolver.map(resource.getPath());
	}

}