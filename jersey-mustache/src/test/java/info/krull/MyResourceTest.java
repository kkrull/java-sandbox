package info.krull;

import org.glassfish.grizzly.http.server.HttpServer;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class MyResourceTest {

  private HttpServer server;
  private WebTarget target;

  @Before
  public void setUp() throws Exception {
    server = Main.startServer();
    Client c = ClientBuilder.newClient();
    target = c.target(Main.BASE_URI);
  }

  @After
  public void tearDown() throws Exception {
    server.stop();
  }

  @Test
  public void testGetItFromPlainText() {
    String responseMsg = target.path("myresource").request().get(String.class);
    assertThat("Got it!", Matchers.equalTo(responseMsg));
  }

  @Test
  public void testGetItFromTemplate() {
    String rawResponse = target.path("myresource/template").request().get(String.class);
    assertThat(rawResponse, containsString("The answer to life"));
    assertThat(rawResponse, containsString("Answer: 42"));
  }
}
