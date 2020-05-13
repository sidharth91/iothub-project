package com.azure.iothub;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.Property;

public class ReporPropertyThread implements Runnable {

	 DeviceObject device ;
	 


	public ReporPropertyThread(DeviceObject device) {
		super();
		this.device = device;
	}

	@Override
	public void run() {
		   while(true) {
        
               try {
            	   
				int battery = device.getBattery();
				Property reportedProperty = new Property("battery", battery);
				Set<Property> reportedProperties = new HashSet<Property>();
				reportedProperties.add(reportedProperty);
				System.out.println("battery " + battery);
				device.reportStatusToTwin(reportedProperties);
				if (battery <= 0) {
					battery = 100;
				} else {
					battery--;
				}
				device.setBattery(battery);
				Thread.sleep(5000);
			} catch (IllegalArgumentException | InterruptedException e) {
				e.printStackTrace();
			}
        }
		
	}

}
