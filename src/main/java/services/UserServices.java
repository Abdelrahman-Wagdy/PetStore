package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.UserModel;
import utils.Config;

import java.util.List;

public class UserServices {
    private static final String BASE_URL = Config.get("base.url");

    public static Response createUser(UserModel userData){
        return RestAssured.given()
                .log().all()
                .baseUri("https://" + BASE_URL)
                .contentType("application/json")
                .body(userData)
                .post(Config.get("create.user"));
    }
    public static Response loginUser(String username, String password){
        return RestAssured.given()
                .log().all()
                .baseUri("https://" + BASE_URL)
                .queryParam("username", username)
                .queryParam("password", password)
                .get(Config.get("login.user"));
    }
    public static Response logoutUser(){
        return RestAssured.given()
                .log().all()
                .baseUri("https://" + BASE_URL)
                .get(Config.get("logout.user"));
    }
    public static Response getUser(String username){
        return RestAssured.given()
                .log().all()
                .baseUri("https://" + BASE_URL)
                .pathParam("username", username)
                .get(Config.get("get.update.delete.user"));
    }
    public static Response updateUser(UserModel userData){
        return RestAssured.given()
                .log().all()
                .baseUri("https://" + BASE_URL)
                .pathParam("username", userData.getUsername())
                .body(userData)
                .put(Config.get("get.update.delete.user"));
    }
    public static Response deleteUser(String username){
        return RestAssured.given()
                .log().all()
                .baseUri("https://" + BASE_URL)
                .pathParam("username", username)
                .delete(Config.get("get.update.delete.user"));
    }


}
