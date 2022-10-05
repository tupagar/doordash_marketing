package com.example.doordash_marketing.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class MarketingControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MarketingController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void whenPostPhoneNumbersValidInput() throws URISyntaxException {
        ResponseExtractor<Integer> responseExtractor = new ResponseExtractor<Integer>() {
            @Override
            public Integer extractData(ClientHttpResponse response) throws IOException {
                return response.getRawStatusCode();
            }
        };
        RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void doWithRequest(ClientHttpRequest request) throws IOException {
                request.getHeaders().setAccept(Collections.singletonList(MediaType.ALL));
                request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                request.getBody()
                        .write("{\"raw_phone_numbers\": \"(Home).  415-415-4155 (Cell) 415-123-4567\"}".getBytes());
            }
        };

        int output = this.restTemplate.execute(
                new URI("http://localhost:" + port + "/phone_numbers"),
                HttpMethod.POST, requestCallback, responseExtractor);
        Assert.assertEquals(200, output);
    }

    @Test
    public void whenPostPhoneNumbersMissingBody() throws URISyntaxException {
        ResponseExtractor<Integer> responseExtractor = new ResponseExtractor<Integer>() {
            @Override
            public Integer extractData(ClientHttpResponse response) throws IOException {
                return response.getRawStatusCode();
            }
        };
        RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void doWithRequest(ClientHttpRequest request) throws IOException {
                request.getHeaders().setAccept(Collections.singletonList(MediaType.ALL));
                request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            }
        };

        int output = this.restTemplate.execute(
                new URI("http://localhost:" + port + "/phone_numbers"),
                HttpMethod.POST, requestCallback, responseExtractor);
        Assert.assertEquals(400, output);
    }

    @Test
    public void whenPostPhoneNumbersIgnoringInvalidNumbers() throws URISyntaxException {
        ResponseExtractor<Integer> responseExtractor = new ResponseExtractor<Integer>() {
            @Override
            public Integer extractData(ClientHttpResponse response) throws IOException {
                return response.getRawStatusCode();
            }
        };
        RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void doWithRequest(ClientHttpRequest request) throws IOException {
                request.getHeaders().setAccept(Collections.singletonList(MediaType.ALL));
                request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                request.getBody()
                        .write("{\"raw_phone_numbers\": \"(ddddd).  415-415-4155 (Cell) sss-123-4567\"}".getBytes());
            }
        };

        int output = this.restTemplate.execute(
                new URI("http://localhost:" + port + "/phone_numbers"),
                HttpMethod.POST, requestCallback, responseExtractor);
        Assert.assertEquals(200, output);
    }

    @Test
    public void whenInvalidGetRequest() throws URISyntaxException {
        ResponseExtractor<Integer> responseExtractor = new ResponseExtractor<Integer>() {
            @Override
            public Integer extractData(ClientHttpResponse response) throws IOException {
                return response.getRawStatusCode();
            }
        };
        RequestCallback requestCallback = new RequestCallback() {
            @Override
            public void doWithRequest(ClientHttpRequest request) throws IOException {
                request.getHeaders().setAccept(Collections.singletonList(MediaType.ALL));
                request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                request.getBody()
                        .write("{\"raw_phone_numbers\": \"(Home).  415-415-4155 (Cell) 415-123-4567\"}".getBytes());
            }
        };

        int output = this.restTemplate.execute(
                new URI("http://localhost:" + port + "/phone_numbers"),
                HttpMethod.GET, requestCallback, responseExtractor);
        Assert.assertEquals(405, output);
    }
}
