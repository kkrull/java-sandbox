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
}
