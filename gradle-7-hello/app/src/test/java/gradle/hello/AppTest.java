package gradle.hello;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
  @Test void appHasAGreeting() {
    App subject = new App();
    assertNotNull(subject.getGreeting(), "app should have a greeting");
  }
}
