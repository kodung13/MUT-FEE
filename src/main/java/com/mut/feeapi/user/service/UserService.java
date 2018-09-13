package com.mut.feeapi.user.service;

import com.mut.feeapi.user.domain.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Service that provides operations for {@link User}s.
 *
 * @author TanagornS
 */
@Configuration
@ComponentScan(basePackages = { "com.mut.feeapi.*" })
@ApplicationScoped
public class UserService {

//    @Inject
//    private EntityManager em;
    

    /**
     * Find a user by username or email.
     *
     * @param identifier
     * @return
     */
    public User findByUsernameOrEmail(String identifier,JdbcTemplate jdbcTemplate) {

    	System.out.println("jdbcTemplate : "+jdbcTemplate);
    	
        StringBuffer sqlStr = new StringBuffer();
        sqlStr.append("SELECT * FROM USER_ACCT ");
        sqlStr.append("WHERE USERNAME = ? OR EMAIL = ?");
		List<User> userList = jdbcTemplate.query(sqlStr.toString(), new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, identifier);
				preparedStatement.setString(2, identifier);
			}
		}, new BeanPropertyRowMapper<User>(User.class)); 
		System.out.println("userList : "+userList);
		if (userList.isEmpty()) {
            return null;
        }
		
        return userList.get(0);
    }

    /**
     * Find all users.
     *
     * @return
     */
//    public List<User> findAll() {
//        return em.createNativeQuery("SELECT u FROM User u", User.class).getResultList();
//    }

    /**
     * Find a user by id.
     *
     * @param userId
     * @return
     */
//    public Optional<User> findById(Long userId) {
//        return Optional.ofNullable(em.find(User.class, userId));
//    }
}
