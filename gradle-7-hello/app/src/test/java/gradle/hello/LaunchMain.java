package gradle.hello;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.engine.JupiterTestEngine;
import org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor;
import org.junit.platform.engine.*;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;
import org.junit.platform.engine.support.hierarchical.EngineExecutionContext;
import org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.LauncherConfig;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import java.util.Optional;
import java.util.Set;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class LaunchMain {
  public static void main(String[] args) {
    System.out.println("LaunchMain says hello!");

    LauncherConfig launcherConfig = LauncherConfig.builder()
      .enableTestEngineAutoRegistration(false)
      .enableTestExecutionListenerAutoRegistration(false)
//      .addTestEngines(new JupiterTestEngine())
      .addTestEngines(javaSpecTestEngine())
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
[executionStarted] [engine:junit-jupiter] (JUnit Jupiter)
[executionStarted] [engine:junit-jupiter]/[class:gradle.hello.AppTest] (AppTest)
[executionStarted] [engine:junit-jupiter]/[class:gradle.hello.AppTest]/[method:appHasAGreeting()] (appHasAGreeting())
[executionFinished] [engine:junit-jupiter]/[class:gradle.hello.AppTest]/[method:appHasAGreeting()] (appHasAGreeting())
[executionFinished] [engine:junit-jupiter]/[class:gradle.hello.AppTest] (AppTest)
[executionFinished] [engine:junit-jupiter] (JUnit Jupiter)
     */
    return new HierarchicalTestEngine<>() {
      @Override
      protected JavaSpecExecutionContext createExecutionContext(ExecutionRequest request) {
        throw new UnsupportedOperationException();
      }

      @Override
      public TestDescriptor discover(EngineDiscoveryRequest discoveryRequest, UniqueId id) {
        //TODO KDK: Implement a custom TestEngine https://junit.org/junit5/docs/5.8.1/user-guide/index.html#launcher-api-execution
        System.out.println("[discover] %s".formatted(id));
        return new EngineDescriptor(id.appendEngine(this.getId()), "JavaSpec");
      }

      @Override
      public String getId() {
        return "javaspec-engine";
      }
    };
  }

  interface JavaSpecExecutionContext extends EngineExecutionContext { }

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
}
