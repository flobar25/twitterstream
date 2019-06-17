package com.newtonx.twitterstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceInitializer {
	private static final Logger LOG = LoggerFactory.getLogger(DataSourceInitializer.class);

//	public DataSource dataSource() {
//		// TODO add not embedded DB for real application
//		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
//	}
//
//	@Bean
//	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
//		return new NamedParameterJdbcTemplate(dataSource());
//	}
}
