package gradle.hello;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;

public class TestMethodDescriptor extends AbstractTestDescriptor {
  public TestMethodDescriptor(UniqueId testId, String displayName) {
    super(testId, displayName);
  }

  @Override
  public Type getType() {
    return Type.TEST;
  }

  public void runTest() {
    //TODO KDK: [1] Access the method via reflection instead of hard-coding the class and method
    //AppTest.class.getDeclaredMethod("appHasAGreeting")
    new AppTest().appHasAGreeting();
  }
}
