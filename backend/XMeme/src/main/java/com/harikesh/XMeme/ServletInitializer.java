package com.harikesh.XMeme;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The Class ServletInitializer.
 * 
 * @author harikesh.pallantla
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configure.
	 *
	 * @param application the application
	 * @return the spring application builder
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(XMemeApplication.class);
	}

}
