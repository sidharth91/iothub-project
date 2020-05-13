package com.azure.iothub;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.microsoft.azure.sdk.iot.device.DeviceTwin.Property;

public class RebootDeviceThread implements Runnable {
  public void run() {
    try {
      System.out.println("Rebooting...");
      Thread.sleep(5000);
      Property property = new Property("lastReboot", LocalDateTime.now());
      Set<Property> properties = new HashSet<Property>();
      properties.add(property);
      System.out.println("Rebooted");
    }
    catch (Exception ex) {
      System.out.println("Exception in reboot thread: " + ex.getMessage());
    }
  }
}