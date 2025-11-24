package com.example.papaspizzeria.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

@Configuration
public class GsonConfig {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()   // optional: makes JSON output nice
                .create();
    }

    @Bean
    public HttpMessageConverter<Object> gsonHttpMessageConverter(Gson gson) {
        return new GsonHttpMessageConverter(gson);
    }
}
