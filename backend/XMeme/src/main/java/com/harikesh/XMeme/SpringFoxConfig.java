package com.harikesh.XMeme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * The Class SpringFoxConfig.
 *
 * @author harikesh.pallantla
 */
@Configuration
public class SpringFoxConfig {

	/** The Constant DEFAULT_PRODUCES_CONSUMES. */
	private static final Set<String> DEFAULT_PRODUCES_CONSUMES =
			new HashSet<String>(Arrays.asList("application/json"));

	// @Value("${server.port}")
	// private int serverPort;

	/**
	 * Api.
	 *
	 * @return the docket
	 */
	@Bean
	public Docket api() {
		// Uncomment the below code for custom swagger port.
		// return new Docket(DocumentationType.SWAGGER_2).host("localhost:" + serverPort).select()
		// .apis(RequestHandlerSelectors.basePackage("com.harikesh.XMeme")).paths(PathSelectors.any()).build()
		// .apiInfo(metaData()).produces(DEFAULT_PRODUCES_CONSUMES).consumes(DEFAULT_PRODUCES_CONSUMES);
		return new Docket(DocumentationType.OAS_30).select()
				.apis(RequestHandlerSelectors.basePackage("com.harikesh.XMeme")).build()
				.apiInfo(apiInfo()).produces(DEFAULT_PRODUCES_CONSUMES)
				.consumes(DEFAULT_PRODUCES_CONSUMES);
	}

	/**
	 * Api info method returns the ApiInfo object with the details.
	 *
	 * @return the api info
	 */
	private ApiInfo apiInfo() {

		Contact contact = new Contact("Harikesh", "https://harikesh409.github.io/",
				"p.harikesh409@gmail.com");

		return new ApiInfo("XMeme", "Meme Stream Page to Post and View Memes", "1.0", "", contact,
				"Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0",
				new ArrayList<>());
	}
}
