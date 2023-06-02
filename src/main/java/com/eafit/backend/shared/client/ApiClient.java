package com.eafit.backend.shared.client;

import org.springframework.beans.factory.annotation.Value;

public class ApiClient {
    @Value("{api.url}")
    private String API_URL;
}
