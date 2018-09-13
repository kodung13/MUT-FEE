package com.mut.feeapi.service;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.mut.feeapi.user.domain.Fee;

/**
 * JAX-RS resource class that provides operations for authentication.
 *
 * @author Tanagorns
 */
@RequestScoped
@Path("fee")
@ComponentScan(basePackages = { "com.mut.feeapi.*" })
@PropertySource("classpath:application.properties")
public class GetFeeResource {

    @Context
    private SecurityContext securityContext;

    
    @Autowired
	JdbcTemplate jdbcTemplate;
    
    @Autowired
    Environment env;
    /**
     * Validate user credentials and issue a token for the user.
     *
     * @param credentials
     * @return
     */
    @GET
    @Path("{prodId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("prodId") Long prodId) {
    	
    	try {
    		 String username = securityContext.getUserPrincipal().getName();
        	 StringBuffer sqlStr = new StringBuffer();
             sqlStr.append("SELECT * FROM FEE_PROD ");
             sqlStr.append("WHERE PRODID = ?");
     		List<Fee> feeList = jdbcTemplate.query(sqlStr.toString(), new PreparedStatementSetter() {
     			public void setValues(PreparedStatement preparedStatement) throws SQLException {
     				preparedStatement.setBigDecimal(1, new BigDecimal(prodId));
     			}
     		}, new BeanPropertyRowMapper<Fee>(Fee.class)); 
     		 return Response.ok(feeList).build();
    	}catch(Exception e) {
    		String txt = "You don't have a permission!!";
    		 return Response.status(404,txt).build();
    	}
    	
    	
       
    }
}
