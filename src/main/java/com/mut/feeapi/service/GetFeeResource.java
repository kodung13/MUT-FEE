package com.mut.feeapi.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.mut.feeapi.security.service.PasswordEncoder;
import com.mut.feeapi.user.domain.Fee;
import com.mut.feeapi.utility.Helper;

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

	Logger logs = Logger.getLogger(GetFeeResource.class);

	@Context
	private SecurityContext securityContext;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	Environment env;

	
	private String userSave;
	
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
		String username = "";
		try {
			username = securityContext.getUserPrincipal().getName();
			logs.info(username + "::: Try to get with productid :: "+prodId);
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("SELECT * FROM FEE_PROD ");
			sqlStr.append("WHERE PROD_ID = ?");
			List<Fee> feeList = jdbcTemplate.query(sqlStr.toString(), new PreparedStatementSetter() {
				public void setValues(PreparedStatement preparedStatement) throws SQLException {
					preparedStatement.setBigDecimal(1, new BigDecimal(prodId));
				}
			}, new BeanPropertyRowMapper<Fee>(Fee.class));

			JSONObject jsonobjs = new JSONObject(feeList.get(0));
			return Response.ok(jsonobjs.toString()).build();
		} catch (Exception e) {
			String errTxt = e.getMessage();
			JSONObject jsonobjs = new JSONObject();
			jsonobjs.put("status", "failed");
			jsonobjs.put("message",errTxt);
			logs.error(username + "::: have error ::" + errTxt);
			return Response.status(404, jsonobjs.toString()).build();
		}

	}

	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser() {
		String username = "";
		try {
			username = securityContext.getUserPrincipal().getName();
			logs.info(username + "::: To used list services");
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("SELECT * FROM FEE_PROD ");
			List<Fee> feeList = jdbcTemplate.query(sqlStr.toString(), new BeanPropertyRowMapper<Fee>(Fee.class));
			JSONArray jsonArr = new JSONArray(feeList);
			return Response.ok(jsonArr.toString()).build();
		} catch (Exception e) {
			String errTxt = e.getMessage();
			JSONObject jsonobjs = new JSONObject();
			jsonobjs.put("status", "failed");
			jsonobjs.put("message",errTxt);
			logs.error(username + "::: have error ::" + errTxt);
			return Response.status(404, jsonobjs.toString()).build();
		}

	}

	@POST
	@Path("savepayment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response savePayment(String jsonStr) throws JSONException, Exception {
		
	
		try {
			String username = securityContext.getUserPrincipal().getName();
			userSave = username;
			logs.info(username + "::: Used savepayment services");
			JSONObject jsonObjs = new JSONObject(jsonStr);
			String sqldate = Helper.SQLDateNow();
			String currentDateTime = Helper.getCurrentDatetime();
			StringBuffer insertSql = new StringBuffer();
			insertSql.append("INSERT into PAYMENT_TRAN(");
			insertSql.append("PROD_ID,"); // 1
			insertSql.append("FEE_AMOUNT,");// 2
			insertSql.append("TRAN_AMOUNT,");// 3
			insertSql.append("OWNER,");// 4
			insertSql.append("CREATEDATETIME");// 5
			insertSql.append(") VALUES (?,?,?,?,TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS'))");
			jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(insertSql.toString());
					ps.setInt(1, jsonObjs.getInt("PROD_ID"));
					ps.setFloat(2, jsonObjs.getFloat("FEE_AMOUNT"));
					ps.setFloat(3, jsonObjs.getFloat("TRAN_AMOUNT"));
					ps.setString(4, username);
					ps.setString(5, currentDateTime);
					return ps;
				}
			});
			String txtCap = jsonObjs.getInt("PROD_ID")+","+jsonObjs.getFloat("FEE_AMOUNT")+","+jsonObjs.getFloat("TRAN_AMOUNT");
			logs.info(username + "::: Save Data ("+txtCap+")");
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("status", "ok");
			return Response.ok(jsonObj.toString()).build();
			}catch (JSONException e) {
				String errTxt = "Incorrect input";
				JSONObject jsonobjs = new JSONObject();
				jsonobjs.put("status", "failed");
				jsonobjs.put("message",errTxt);
				logs.error(userSave + "::: have error ::" + e.getMessage());
				return Response.status(404, jsonobjs.toString()).build();
			}catch (Exception e) {
			String errTxt = e.getMessage();
			JSONObject jsonobjs = new JSONObject();
			jsonobjs.put("status", "failed");
			jsonobjs.put("message",errTxt);
			logs.error(userSave + "::: have error ::" + errTxt);
			return Response.status(404, jsonobjs.toString()).build();
		}

	}

	@POST
	@Path("genpass")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response genBcryptPass(String jsonStr) throws JSONException, Exception {
		logs.info("-------- Started gen password Webservices --------");

		try {
			JSONObject jsonObjs = new JSONObject(jsonStr);
			String ciphertext = new PasswordEncoder().hashPassword(jsonObjs.getString("PASSWORD"));
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("status", "ok");
			jsonObj.put("password", ciphertext);
			return Response.ok(jsonObj.toString()).build();
		} catch (Exception e) {
			String errTxt = e.getMessage();
			logs.error("::: have error ::" + errTxt);
			return Response.status(404, errTxt).build();
		}

	}
}
