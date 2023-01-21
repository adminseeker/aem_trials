package com.demo.core.services.apiservices.impl;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

import com.demo.core.services.apiservices.ApiRestConfig;

@Component(immediate = true, service = ApiRestConfigImpl.class, property = { "process.label=API REST Configurations",
		Constants.SERVICE_DESCRIPTION + "=API REST Configurations End Ponit URl For all Service.", Constants.SERVICE_VENDOR + "=Demo" })
@Designate(ocd = ApiRestConfig.class)
public class ApiRestConfigImpl {

	/**
	 * API Config Interface
	 */
	private ApiRestConfig configService;

	@Activate
	protected void activate(ApiRestConfig config) {
		this.configService = config;
	}

	public ApiRestConfig getConfigService() {
		return configService;
	}

	public String getApiEndPointUrl() {
		return configService.apiEndPointUrl();
	}

	public String getApiGetProfilePath() {
		return configService.apiGetProfilePath();
	}
}
