package com.demo.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * API Config Service
 */
@ObjectClassDefinition(name = "API Rest Configurations", description = "API REST Configurations will Provides the End Ponit URl For all Service")
public @interface ApiRestConfig {

	@AttributeDefinition(name = "Public API Endpoint URL", description = "This holds the value of  Public API endpoint", type = AttributeType.STRING)
	String apiEndPointUrl() default "http://localhost:3000";

	@AttributeDefinition(name = "profiles", description = "This holds the rest path of profile", type = AttributeType.STRING)
	String apiGetProfilePath() default "/api/v1/profile";

}
