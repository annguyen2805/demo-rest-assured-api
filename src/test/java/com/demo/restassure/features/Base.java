package com.demo.restassure.features;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;

import java.io.File;

import static com.demo.restassure.helper.LoadConfig.LOAD_CONFIG;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class Base {
    protected static String tokenAdmin;
    protected static String refreshTokenAdmin;
    protected static String agentId;
    protected static String agentId2;

    @BeforeAll
    public static void init() {
        // Config for RestAssured
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.replaceFiltersWith(new AllureRestAssured());
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.baseURI = LOAD_CONFIG.getProperty("domain");

        // Login to get access_token
        PostLogin login = new PostLogin();
        tokenAdmin = login.getTokenAdmin();
        refreshTokenAdmin = login.getRefreshToken();

        // Make agentId available on all tests
        agentId = LOAD_CONFIG.getProperty("agentId");
        agentId2 = LOAD_CONFIG.getProperty("agentId2");

        // Create directory to download file
        new File(LOAD_CONFIG.getProperty("dir-download")).mkdir();
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        System.out.println(String.format("---------------%s---------------",
                testInfo.getTestMethod()));
    }

}
