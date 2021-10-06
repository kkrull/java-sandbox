package gradle.hello;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public class TestMethodDescriptor extends AbstractTestDescriptor {
  private final Method testMethod;
  private final Supplier<Object> makeTestInstance;

  public static TestMethodDescriptor forMethod(UniqueId engineId, Class<?> testClass, Method testMethod) {
    Supplier<Object> testObjectSupplier = () -> {
      Constructor<?> constructor;
      try {
        constructor = testClass.getDeclaredConstructor();
      } catch (NoSuchMethodException e) {
        throw new RuntimeException("Failed to access test class constructor", e);
      }

      try {
        return constructor.newInstance();
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
        throw new RuntimeException("Failed to instantiate test class", e);
      }
    };

    return new TestMethodDescriptor(
      engineId.append("method", "%s()".formatted(testMethod.getName())),
      "%s#%s".formatted(testClass.getSimpleName(), testMethod.getName()),
      testObjectSupplier,
      testMethod
    );
  }

  private TestMethodDescriptor(UniqueId testId, String displayName, Supplier<Object> testObject, Method testMethod) {
    super(testId, displayName);

    this.makeTestInstance = testObject;
    this.testMethod = testMethod;
  }

  @Override
  public Type getType() {
    return Type.TEST;
  }

  public void runTest() {
    Object testInstance = makeTestInstance.get();
    try {
      testMethod.invoke(testInstance);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException("Failed to invoke test", e);
    }
  }
}
