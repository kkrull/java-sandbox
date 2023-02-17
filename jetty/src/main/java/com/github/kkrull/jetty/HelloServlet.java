package com.github.kkrull.jetty;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HelloServlet", urlPatterns = { "/hello" })
public class HelloServlet extends HttpServlet {
  private static int _numPosts = 0;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    _numPosts++;
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("text/html");
    PrintWriter writer = response.getWriter();
    writer.println("<h2>Hello from an annotated servlet!</h2>");
    writer.println("Number of posts: " + _numPosts);
  }
}