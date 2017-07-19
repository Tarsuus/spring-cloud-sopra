package com.sopra.cloud.invoiceserviceclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.sopra.cloud.invoiceserviceclient.InvoiceClient;
import com.sopra.cloud.invoiceserviceclient.InvoiceClientJsonMock;
import com.sopra.cloud.invoiceserviceclient.InvoiceClientRestImpl;

@Configuration
public class InvoiceClientAutoConfiguration {
	@Bean
	@ConditionalOnMissingBean(value = RestTemplate.class)
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	@ConditionalOnProperty(name = "invoice.service.mock", havingValue = "false", matchIfMissing = true)
	public InvoiceClient invoiceClientRestImpl(@Value("${invoice.service.url:http://localhost:8080}") String url) {
		return new InvoiceClientRestImpl(url, restTemplate());
	}

	@Bean
	@ConditionalOnProperty(name = "invoice.service.mock", havingValue = "true", matchIfMissing = false)
	public InvoiceClient invoiceClientMockImpl() {
		return new InvoiceClientJsonMock();
	}
}
