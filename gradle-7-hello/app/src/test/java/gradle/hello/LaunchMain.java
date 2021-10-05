package gradle.hello;

import org.junit.jupiter.engine.JupiterTestEngine;
import org.junit.jupiter.engine.config.DefaultJupiterConfiguration;
import org.junit.jupiter.engine.descriptor.ClassTestDescriptor;
import org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor;
import org.junit.platform.engine.*;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;
import org.junit.platform.engine.support.hierarchical.EngineExecutionContext;
import org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine;
import org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutorService;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.LauncherConfig;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.vintage.engine.VintageTestEngine;

import java.util.Optional;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class LaunchMain {
  public static void main(String[] args) {
    System.out.println("LaunchMain says hello!");

    LauncherConfig launcherConfig = LauncherConfig.builder()
      .enableTestEngineAutoRegistration(false)
      .enableTestExecutionListenerAutoRegistration(false)
//      .addTestEngines(new JupiterTestEngine())
      .addTestEngines(javaSpecTestEngine())
//      .addTestEngines(new VintageTestEngine())
      .addLauncherDiscoveryListeners(launcherDiscoveryListener())
      .addTestExecutionListeners(executionListener())
      .build();

    Launcher launcher = LauncherFactory.create(launcherConfig);
    launcher.execute(discoverRequestForTestClass());
  }

  private static LauncherDiscoveryRequest discoverRequestForTestClass() {
    return LauncherDiscoveryRequestBuilder.request()
      .selectors(selectClass(AppTest.class))
      .build();
  }

  private static TestExecutionListener executionListener() {
    return new TestExecutionListener() {
      @Override
      public void testPlanExecutionStarted(TestPlan testPlan) {
        System.out.println("[testPlanExecutionStarted]");
      }

      @Override
      public void executionStarted(TestIdentifier testId) {
        System.out.println("[executionStarted] %s (%s)".formatted(testId.getUniqueId(), testId.getDisplayName()));
      }

      @Override
      public void executionFinished(TestIdentifier testId, TestExecutionResult testExecutionResult) {
        System.out.println("[executionFinished] %s (%s)".formatted(testId.getUniqueId(), testId.getDisplayName()));
      }

      @Override
      public void testPlanExecutionFinished(TestPlan testPlan) {
        System.out.println("[testPlanExecutionFinished]");
      }
    };
  }

  private static HierarchicalTestEngine<EngineExecutionContext> javaSpecTestEngine() {
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
    return new HierarchicalTestEngine<>() {
      @Override
      protected JavaSpecExecutionContext createExecutionContext(ExecutionRequest request) {
        System.out.println("[createExecutionContext] %s".formatted(request.getConfigurationParameters()));
        return new JavaSpecExecutionContext();
      }

      @Override
      public TestDescriptor discover(EngineDiscoveryRequest discoveryRequest, UniqueId engineId) {
        //TODO KDK: Implement a custom TestEngine https://junit.org/junit5/docs/5.8.1/user-guide/index.html#launcher-api-execution
        //TODO KDK: Maybe try making Nodes and TestDescriptors manually, instead of using the built-in types
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
    };
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

  static class JavaSpecExecutionContext implements EngineExecutionContext { }

  private static LauncherDiscoveryListener launcherDiscoveryListener() {
    return new LauncherDiscoveryListener() {
      @Override
      public void launcherDiscoveryStarted(LauncherDiscoveryRequest request) {
        System.out.println("[launcherDiscoveryStarted]");
      }

      @Override
      public void engineDiscoveryStarted(UniqueId engineId) {
        System.out.println("[engineDiscoveryStarted] %s".formatted(engineId));
      }

      @Override
      public void engineDiscoveryFinished(UniqueId engineId, EngineDiscoveryResult result) {
        System.out.println("[engineDiscoveryFinished] %s: %s".formatted(engineId, result.getStatus()));
      }

      @Override
      public void launcherDiscoveryFinished(LauncherDiscoveryRequest request) {
        System.out.println("[launcherDiscoveryFinished]");
      }
    };
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
