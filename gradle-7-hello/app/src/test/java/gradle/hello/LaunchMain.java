package gradle.hello;

import org.junit.jupiter.engine.JupiterTestEngine;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.LauncherConfig;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class LaunchMain {
  public static void main(String[] args) {
    //TODO KDK: Register a custom TestEngine: https://junit.org/junit5/docs/5.8.1/user-guide/index.html#launcher-api-execution
    System.out.println("Launcher says hello!");

    LauncherConfig launcherConfig = LauncherConfig.builder()
      .enableTestEngineAutoRegistration(false)
      .enableTestExecutionListenerAutoRegistration(false)
      .addTestEngines(new JupiterTestEngine())
      .addTestExecutionListeners(executionListener())
      .build();

    Launcher launcher = LauncherFactory.create(launcherConfig);
    launcher.execute(discoverRequestForTestClass());
  }

  private static TestExecutionListener executionListener() {
    return new TestExecutionListener() {
      @Override
      public void testPlanExecutionStarted(TestPlan testPlan) {
        System.out.println("[testPlanExecutionStarted]");
        TestExecutionListener.super.testPlanExecutionStarted(testPlan);
      }

      @Override
      public void executionStarted(TestIdentifier testIdentifier) {
        System.out.println("[executionStarted] " + testIdentifier.getDisplayName());
        TestExecutionListener.super.executionStarted(testIdentifier);
      }

      @Override
      public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        System.out.println("[executionFinished] " + testIdentifier.getDisplayName());
        TestExecutionListener.super.executionFinished(testIdentifier, testExecutionResult);
      }

      @Override
      public void testPlanExecutionFinished(TestPlan testPlan) {
        System.out.println("[testPlanExecutionFinished]");
        TestExecutionListener.super.testPlanExecutionFinished(testPlan);
      }
    };
  }

  private static LauncherDiscoveryRequest discoverRequestForTestClass() {
    return LauncherDiscoveryRequestBuilder.request()
      .selectors(
        selectPackage("gradle.hello"),
        selectClass(AppTest.class)
      )
      .filters(includeClassNamePatterns(".*Test"))
      .build();
  }
}
