package com.github.kkrull.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;

public class RunJetty {
  public static void main(String[] args) throws Exception {
    Server server = makeServer(new File("target/jetty-sandbox"), new File("target/jetty-sandbox"));
    server.start();
    server.join();
  }

  public static Server makeServer(File staticPath, File serverClassPath) throws Exception {
    if(!staticPath.canRead())
      throw new RuntimeException("Not readable: " + staticPath.getAbsolutePath());
    if(!serverClassPath.canRead())
      throw new RuntimeException("Not readable: " + serverClassPath.getAbsolutePath());

    Server server = new Server(8080);
    Configuration.ClassList configurationClasses = Configuration.ClassList.setServerDefault(server);
    configurationClasses.addBefore(
      "org.eclipse.jetty.webapp.JettyWebXmlConfiguration", //required for AnnotationConfiguration
      "org.eclipse.jetty.annotations.AnnotationConfiguration");

    WebAppContext context = new WebAppContext();
    context.setContextPath("/");
    context.setResourceBase(staticPath.getAbsolutePath());
    context.setExtraClasspath(serverClassPath.getAbsolutePath() + File.pathSeparator);

    server.setHandler(context);
    return server;
  }
}