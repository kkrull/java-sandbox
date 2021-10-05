package gradle.hello;

import org.junit.jupiter.engine.config.DefaultJupiterConfiguration;
import org.junit.jupiter.engine.descriptor.ClassTestDescriptor;
import org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor;
import org.junit.platform.engine.*;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;
import org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine;

import java.util.Optional;

public class JavaSpecTestEngine extends HierarchicalTestEngine<JavaSpecExecutionContext> {
  /*
LaunchMain says hello!
[launcherDiscoveryStarted]
[engineDiscoveryStarted] [engine:junit-jupiter]
[engineDiscoveryFinished] [engine:junit-jupiter]: SUCCESSFUL
[launcherDiscoveryFinished]
[testPlanExecutionStarted]
[executionStarted] [engine:junit-jupiter] (JUnit Jupiter)
[executionStarted] [engine:junit-jupiter]/[class:gradle.hello.AppTest] (AppTest)
[executionStarted] [engine:junit-jupiter]/[class:gradle.hello.AppTest]/[method:appHasAGreeting()] (appHasAGreeting())
[executionFinished] [engine:junit-jupiter]/[class:gradle.hello.AppTest]/[method:appHasAGreeting()] (appHasAGreeting())
[executionFinished] [engine:junit-jupiter]/[class:gradle.hello.AppTest] (AppTest)
[executionFinished] [engine:junit-jupiter] (JUnit Jupiter)
[testPlanExecutionFinished]
  */

  @Override
  protected JavaSpecExecutionContext createExecutionContext(ExecutionRequest request) {
    System.out.println("[createExecutionContext] %s".formatted(request.getConfigurationParameters()));
    return new JavaSpecExecutionContext();
  }

  //TODO KDK: Look at VintageDiscoverer#discover from VintageTestEngine, for an example
  @Override
  public TestDescriptor discover(EngineDiscoveryRequest discoveryRequest, UniqueId engineId) {
    System.out.println("[discover engine] %s".formatted(engineId));

    UniqueId classId = engineId.append("class", "gradle.hello.AppTest");
    System.out.println("[discover class] %s".formatted(classId));
    ClassTestDescriptor classDescriptor = new ClassTestDescriptor(
      classId,
      AppTest.class,
      emptyConfiguration()
    );
    classDescriptor.addChild(testMethodDescriptor(classId)); //TODO KDK: Why isn't the test method descriptor being executed?

    EngineDescriptor engineDescriptor = new EngineDescriptor(engineId, "JavaSpec");
    engineDescriptor.addChild(classDescriptor);
    return engineDescriptor;
  }

  @Override
  public String getId() {
    return "javaspec-engine";
  }

  private static DefaultJupiterConfiguration emptyConfiguration() {
    return new DefaultJupiterConfiguration(new ConfigurationParameters() {
      @Override
      public Optional<String> get(String key) {
        return Optional.empty();
      }

      @Override
      public Optional<Boolean> getBoolean(String key) {
        return Optional.empty();
      }

      @Override
      public int size() {
        return 0;
      }
    });
  }

  private static TestMethodTestDescriptor testMethodDescriptor(UniqueId classId) {
    UniqueId methodId = classId.append("method", "appHasAGreeting()");
    System.out.println("[discover method] %s".formatted(methodId));

    try {
      return new TestMethodTestDescriptor(
        methodId,
        AppTest.class,
        AppTest.class.getDeclaredMethod("appHasAGreeting"),
        emptyConfiguration()
      );
    } catch (NoSuchMethodException e) {
      throw new RuntimeException("Failed to look up test method", e);
    }
  }
}