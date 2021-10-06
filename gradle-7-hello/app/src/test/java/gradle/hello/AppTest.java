package gradle.hello;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
  private static int _numTimesRun = 0;

  public static void assertRanOnce() {
    assertEquals(1, _numTimesRun, "Expected AppTest to have been run once");
  }

  public AppTest() {
    System.out.println("[AppTest::AppTest]");
  }

  @Test
  public void appHasAGreeting() {
    System.out.println("[AppTest#appHasAGreeting]");
    _numTimesRun++;
    App subject = new App();
    assertNotNull(subject.getGreeting(), "app should have a greeting");
  }
}
