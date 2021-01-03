package com.github.kkrull;

public final class App {
  public static void main(String[] args) {
    System.out.println(new App().getGreeting());
  }

  public String getGreeting(String name) {
    return String.format("Hello %s!", name);
  }

  public String getGreeting() {
    return "Hello World!";
  }
}
