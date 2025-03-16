package com.example.tests.api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserApiTest {
    private static String baseUri = "https://petstore.swagger.io/v2";
    private static String username;
    private static User user; // POJO класс

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = baseUri;
        username = "user" + System.currentTimeMillis();
        user = new User();
        user.setId(System.currentTimeMillis());
        user.setUsername(username);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setEmail("ivanov@example.com");
        user.setPassword("password123");
        user.setPhone("1234567890");
        user.setUserStatus(1);
    }

    @Test
    @Order(1)
    @DisplayName("Create User")
    public void testCreateUser() {
        createUser(user)
                .then().statusCode(200)
                .body("message", equalTo(String.valueOf(user.getId())));
    }

    @Test
    @Order(2)
    @DisplayName("Update User")
    public void testUpdateUser() {
        user.setFirstName("Petr");
        updateUser(username, user)
                .then().statusCode(200)
                .body("message", equalTo(String.valueOf(user.getId())));
    }

    @Test
    @Order(3)
    @DisplayName("Get User")
    public void testGetUser() {
        getUser(username)
                .then().statusCode(200)
                .body("username", equalTo(username));
    }

    @Test
    @Order(4)
    @DisplayName("Delete User")
    public void testDeleteUser() {
        deleteUser(username)
                .then().statusCode(200);
    }

    @Step("Создаем пользователя: {user}")
    public io.restassured.response.Response createUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/user");
    }

    @Step("Обновляем данные пользователя: {username}")
    public io.restassured.response.Response updateUser(String username, User user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put("/user/" + username);
    }

    @Step("Получаем данные пользователя: {username}")
    public io.restassured.response.Response getUser(String username) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/user/" + username);
    }

    @Step("Удаляем пользователя: {username}")
    public io.restassured.response.Response deleteUser(String username) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/user/" + username);
    }
}
