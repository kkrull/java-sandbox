package info.krull;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("myresource")
public class MyResource {

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
