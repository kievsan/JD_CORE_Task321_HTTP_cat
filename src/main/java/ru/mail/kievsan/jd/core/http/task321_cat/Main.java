package ru.mail.kievsan.jd.core.http.task321_cat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class Main {
    public static final
    String REMOTE_SERVICE_URI = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    //Создадим в классе Main.java, json mapper:
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000) // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000) // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        // создание объекта запроса с произвольными заголовками
        HttpGet request = new HttpGet(REMOTE_SERVICE_URI);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        // отправка запроса
        CloseableHttpResponse response = httpClient.execute(request);

        //  код для преобразования json в java:
        List<Cat> cats = mapper.readValue(response.getEntity().getContent(),
                new TypeReference<>() {
                });
        // вывод полученных заголовков
        cats.stream().filter(value -> value.getUpvotes() != null && Integer.parseInt(value.getUpvotes()) > 0)
                .forEach(System.out::println);
    }
}
