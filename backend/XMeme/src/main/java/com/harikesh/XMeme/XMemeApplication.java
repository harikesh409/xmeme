package com.harikesh.XMeme;

import javax.servlet.Filter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * The Class XMemeApplication.
 * 
 * @author harikesh.pallantla
 */
@SpringBootApplication
@EnableOpenApi
public class XMemeApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(XMemeApplication.class, args);
	}

	/**
	 * Shallow etag header filter. With this method the etag header is generated.
	 *
	 * @return the filter
	 */
	@Bean
	public Filter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}

}
