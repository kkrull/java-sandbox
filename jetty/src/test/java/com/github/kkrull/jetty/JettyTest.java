package com.github.kkrull.jetty;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class JettyTest {
  private Server server;

  @Before
  public void startJetty() throws Exception {
    server = RunJetty.makeServer(new File("src/main/webapp"), new File("target/classes"));
    server.start();
  }

  @After
  public void stopJetty() throws Exception {
    server.stop();
  }

  @Test
  public void jettyServesStaticContent() {
    Response response = RestAssured.when().get("/");
    response.then().statusCode(200);
  }

  @Test
  public void jettyRunsServlets() {
    Response response = RestAssured.when().get("/hello");
    response.then().statusCode(200);
  }
}