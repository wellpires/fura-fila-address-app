package br.com.furafila.addressapp.handler;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import br.com.furafila.addressapp.exception.ServerErrorApiException;

@Component
public class ApiResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return response.getStatusCode().series() == Series.SERVER_ERROR;
	}

	@Override
	public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
		if (response.getStatusCode().series() == Series.SERVER_ERROR) {
			throw new ServerErrorApiException(url.toString(), response.getStatusCode());
		}
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
	}

}
