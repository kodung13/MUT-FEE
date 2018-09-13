package com.mut.feeapi;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.mut.feeapi.security.api.filter.AuthenticationFilter;
import com.mut.feeapi.security.api.resource.AuthenticationResource;
import com.mut.feeapi.service.GetFeeResource;

@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig 
{
    public JerseyConfig() 
    {
    	 register(AuthenticationResource.class);
    	 register(AuthenticationFilter.class);
    	 register(GetFeeResource.class);
    	 
    }
}
