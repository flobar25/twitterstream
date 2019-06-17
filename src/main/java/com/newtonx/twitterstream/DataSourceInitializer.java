package com.newtonx.twitterstream;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class DataSourceInitializer {
	private static final Logger LOG = LoggerFactory.getLogger(DataSourceInitializer.class);

	public DataSource dataSource() {
		// TODO add not embedded DB for real application
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
	}

	@Bean
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource());
	}
}
