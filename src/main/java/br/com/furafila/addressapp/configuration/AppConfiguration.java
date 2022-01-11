package br.com.furafila.addressapp.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import br.com.furafila.addressapp.handler.ApiResponseErrorHandler;

@Configuration
public class AppConfiguration {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder().errorHandler(new ApiResponseErrorHandler()).build();
	}

}
