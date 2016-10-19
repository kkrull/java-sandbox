package info.krull;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {
  public static final String BASE_URI = "http://localhost:8080/myapp/";

  public static void main(String[] args) throws IOException {
    final HttpServer server = startServer();
    System.out.println(String.format(
      "Jersey app started with WADL available at %sapplication.wadl\nHit enter to stop it...", BASE_URI));
    System.in.read();
    server.stop();
  }

  public static HttpServer startServer() {
    final ResourceConfig config = new ResourceConfig().packages(
      "info.krull",
      "org.glassfish.jersey.examples.multipart");
    config.register(MultiPartFeature.class);

    return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
  }
}
