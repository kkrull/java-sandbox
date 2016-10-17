package info.krull;

import org.glassfish.jersey.server.mvc.Template;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("myresource")
public class MyResource {
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getItFromString() {
    return "Got it!";
  }

  @GET()
  @Path("template")
  @Template(name = "/myresource.mustache")
  public Answer getItFromTemplate() {
    return new Answer(42);
  }

  public static class Answer {
    public Integer value;

    public Answer(Integer value) {
      this.value = value;
    }
  }
}
