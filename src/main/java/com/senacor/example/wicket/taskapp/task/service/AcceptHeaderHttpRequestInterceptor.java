package com.senacor.example.wicket.taskapp.task.service;

import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;
import java.util.Collections;

/**
 * @author Sven Ludwig, Senacor Technologies AG
 */
public class AcceptHeaderHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    private final MediaType mediaType;

    public AcceptHeaderHttpRequestInterceptor(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {

        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);

        requestWrapper.getHeaders().setAccept(Collections.singletonList(mediaType));

        return execution.execute(requestWrapper, body);
    }
}
