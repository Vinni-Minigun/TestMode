package ru.netology.data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;


import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private DataGenerator() {
    }

    static Faker faker = new Faker(new Locale("en"));

    @Value
    public static class AuthInfo {
        String login;
        String password;
        String status;
    }

    public static class Registration {
        private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        public static AuthInfo registrationDto(String status) {
            var user = new AuthInfo(generateLogin(), generatePassword(), status);
            Gson gson = new Gson();
            given() // "дано"
                    .spec(requestSpec)
                    .body(gson.toJson(user))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
            return user;
        }

        public static String generateLogin() {
            return faker.name().username();
        }

        public static String generatePassword() {
            return faker.internet().password();
        }

        public static String generateInvalidLogin() {
            return faker.name().fullName();
        }

        public static String generateInvalidPassword() {
            return faker.internet().password(1, 2);
        }

    }
}