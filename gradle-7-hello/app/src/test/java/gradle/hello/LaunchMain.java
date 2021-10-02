package gradle.hello;

import org.junit.platform.engine.*;
import org.junit.platform.engine.support.hierarchical.EngineExecutionContext;
import org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.LauncherConfig;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class LaunchMain {
  public static void main(String[] args) {
    System.out.println("LaunchMain says hello!");

    LauncherConfig launcherConfig = LauncherConfig.builder()
      .enableTestEngineAutoRegistration(false)
      .enableTestExecutionListenerAutoRegistration(false)
      .addTestEngines(new HierarchicalTestEngine<JavaSpecExecutionContext>() {
        @Override
        protected JavaSpecExecutionContext createExecutionContext(ExecutionRequest request) {
          throw new UnsupportedOperationException();
        }

        @Override
        public TestDescriptor discover(EngineDiscoveryRequest discoveryRequest, UniqueId uniqueId) {
          //TODO KDK: Implement a custom TestEngine https://junit.org/junit5/docs/5.8.1/user-guide/index.html#launcher-api-execution
          throw new UnsupportedOperationException();
        }

        @Override
        public String getId() {
          return "javaspec-engine";
        }
      })
      .addLauncherDiscoveryListeners(launcherDiscoveryListener())
      .addTestExecutionListeners(executionListener())
      .build();

    Launcher launcher = LauncherFactory.create(launcherConfig);
    launcher.execute(discoverRequestForTestClass());
  }

  interface JavaSpecExecutionContext extends EngineExecutionContext { }

  private static LauncherDiscoveryRequest discoverRequestForTestClass() {
    return LauncherDiscoveryRequestBuilder.request()
      .selectors(
        selectPackage("gradle.hello"),
        selectClass(AppTest.class)
      )
      .filters(includeClassNamePatterns(".*Test"))
      .build();
  }

  private static TestExecutionListener executionListener() {
    return new TestExecutionListener() {
      @Override
      public void testPlanExecutionStarted(TestPlan testPlan) {
        System.out.println("[testPlanExecutionStarted]");
      }

      @Override
      public void executionStarted(TestIdentifier testIdentifier) {
        System.out.println("[executionStarted] %s".formatted(testIdentifier.getDisplayName()));
      }

      @Override
      public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        System.out.println("[executionFinished] %s".formatted(testIdentifier.getDisplayName()));
      }

      @Override
      public void testPlanExecutionFinished(TestPlan testPlan) {
        System.out.println("[testPlanExecutionFinished]");
      }
    };
  }

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
