package gradle.hello;

import org.junit.jupiter.engine.JupiterTestEngine;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.core.LauncherConfig;
import org.junit.platform.launcher.core.LauncherFactory;

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
//  LauncherDiscoveryRequest discoveryRequest = ...
//      launcher.execute(discoveryRequest);
  }
}
