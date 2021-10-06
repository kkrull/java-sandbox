package gradle.hello;

import org.junit.platform.engine.*;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;

import java.lang.reflect.Method;

public class JavaSpecTestEngine implements TestEngine {
  @Override
  public TestDescriptor discover(EngineDiscoveryRequest discoveryRequest, UniqueId engineId) {
    EngineDescriptor engineDescriptor = new EngineDescriptor(engineId, "JavaSpec");

    //TODO KDK: [3] Use discoveryRequest#getSelectorsByType to get the class selector and generate TestDescriptors for the class and for the test method
    Class<?> testClass = AppTest.class;
    engineDescriptor.addChild(TestMethodDescriptor.forMethod(
      engineId,
      testClass,
      getDeclaredMethod(testClass, "appHasAGreeting"))
    );
    return engineDescriptor;
  }

  @Override
  public void execute(ExecutionRequest request) {
    TestDescriptor engineDescriptor = request.getRootTestDescriptor();
    EngineExecutionListener listener = request.getEngineExecutionListener();
    listener.executionStarted(engineDescriptor);

    //TODO KDK: [2] Allow for a class descriptor with TestMethodDescriptors under it
    for (TestDescriptor childDescriptor : engineDescriptor.getChildren()) {
      TestMethodDescriptor testDescriptor = (TestMethodDescriptor) childDescriptor;
      listener.executionStarted(testDescriptor);

      testDescriptor.runTest();
      listener.executionFinished(testDescriptor, TestExecutionResult.successful());
    }

    listener.executionFinished(engineDescriptor, TestExecutionResult.successful());
  }

  @Override
  public String getId() {
    return "javaspec-engine";
  }

  private static Method getDeclaredMethod(Class<?> declaringClass, String name) {
    try {
      return declaringClass.getDeclaredMethod(name);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException("Failed to access test method", e);
    }
  }
}
