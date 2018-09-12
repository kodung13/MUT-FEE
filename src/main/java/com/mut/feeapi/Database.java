package com.mut.feeapi;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
@ComponentScan(basePackages = { "com.tmb.fee.*" })
@PropertySource("classpath:config.properties")
public class Database {

	@Value("${batch.jdbc.driver}")
	private String dbDriver;

	@Value("${batch.jdbc.url}")
	private String dbUrl;

	@Value("${batch.jdbc.user}")
	private String dbUser;

	@Value("${batch.jdbc.password}")
	private String dbPass;
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(dbDriver);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(dbUser);
		dataSource.setPassword(dbPass);
		return dataSource;
	}


}
