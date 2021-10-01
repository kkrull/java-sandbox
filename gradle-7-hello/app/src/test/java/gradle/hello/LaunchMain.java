package gradle.hello;

import org.junit.jupiter.engine.JupiterTestEngine;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherConfig;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class LaunchMain {
  public static void main(String[] args) {
    System.out.println("Launcher says hello!");
    LauncherConfig launcherConfig = LauncherConfig.builder()
        .enableTestEngineAutoRegistration(false)
        .enableTestExecutionListenerAutoRegistration(false)
        .addTestEngines(new JupiterTestEngine())
//      .addTestExecutionListeners(new CustomTestExecutionListener())
        .build();

    Launcher launcher = LauncherFactory.create(launcherConfig);

    LauncherDiscoveryRequest discoveryRequest = LauncherDiscoveryRequestBuilder.request()
        .selectors(
            selectPackage("gradle.hello"),
            selectClass(AppTest.class)
        )
        .filters(
            includeClassNamePatterns(".*Test")
        )
        .build();

    launcher.execute(discoveryRequest);
  }
}
