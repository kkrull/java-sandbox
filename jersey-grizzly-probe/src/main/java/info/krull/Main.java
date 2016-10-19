package info.krull;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.http.server.*;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
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

  public static HttpServer startServer() throws IOException {
    final ResourceConfig config = new ResourceConfig().packages(
      "info.krull",
      "org.glassfish.jersey.examples.multipart");

    //If you want to configure Grizzly, you have to do it before starting the server.  Doing it afterwards will not work.
    HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config, false);
    httpServer.getServerConfiguration().getMonitoringConfig().getWebServerConfig().addProbes(new RequestLoggingProbe());

    httpServer.start();
    return httpServer;
  }

  static class RequestLoggingProbe implements HttpServerProbe {
    @Override
    public void onRequestReceiveEvent(HttpServerFilter filter, Connection connection, Request request) {
      System.out.printf("Remote address: %s\n", request.getRemoteAddr());
      System.out.printf("User-Agent: %s\n", request.getHeader("User-Agent"));
      System.out.printf("Local address: %s\n", request.getLocalAddr());
    }

    @Override
    public void onRequestCompleteEvent(HttpServerFilter filter, Connection connection, Response response) { }

    @Override
    public void onRequestSuspendEvent(HttpServerFilter filter, Connection connection, Request request) { }

    @Override
    public void onRequestResumeEvent(HttpServerFilter filter, Connection connection, Request request) { }

    @Override
    public void onRequestTimeoutEvent(HttpServerFilter filter, Connection connection, Request request) { }

    @Override
    public void onRequestCancelEvent(HttpServerFilter filter, Connection connection, Request request) { }

    @Override
    public void onBeforeServiceEvent(HttpServerFilter filter, Connection connection, Request request, HttpHandler httpHandler) { }
  }
}
