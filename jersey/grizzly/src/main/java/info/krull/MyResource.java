package info.krull;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

  /**
   * Method handling HTTP GET requests. The returned object will be sent
   * to the client as "text/plain" media type.
   *
   * @return String that will be returned as a text/plain response.
   */
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getIt() {
    return "Got it!";
  }

  @POST
  @Path("part-file-name")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public String takeIt(@FormDataParam("part") String s,
                       @FormDataParam("part") FormDataContentDisposition d) {
    return "s" + ":" + d.getFileName();
  }
}
