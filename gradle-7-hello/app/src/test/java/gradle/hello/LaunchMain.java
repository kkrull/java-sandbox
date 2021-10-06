package gradle.hello;

import org.junit.platform.engine.*;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.LauncherConfig;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class LaunchMain {
  public static void main(String[] args) {
    System.out.println("LaunchMain says hello!");

    LauncherConfig launcherConfig = LauncherConfig.builder()
      .enableTestEngineAutoRegistration(false)
      .enableTestExecutionListenerAutoRegistration(false)
//      .addTestEngines(new JupiterTestEngine())
      .addTestEngines(new JavaSpecTestEngine())
//      .addTestEngines(new VintageTestEngine())
      .addLauncherDiscoveryListeners(launcherDiscoveryListener())
      .addTestExecutionListeners(testExecutionListener())
      .build();

    Launcher launcher = LauncherFactory.create(launcherConfig);
    launcher.execute(discoverRequestForTestClass());
    AppTest.assertRanOnce();
  }

  private static LauncherDiscoveryRequest discoverRequestForTestClass() {
    return LauncherDiscoveryRequestBuilder.request()
      .selectors(selectClass(AppTest.class))
      .build();
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

  private static TestExecutionListener testExecutionListener() {
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
}
