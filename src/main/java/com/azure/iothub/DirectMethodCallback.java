package com.azure.iothub;

import java.util.Map;

import com.microsoft.azure.sdk.iot.device.DeviceTwin.DeviceMethodCallback;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.DeviceMethodData;

public class DirectMethodCallback implements DeviceMethodCallback
{

	 DeviceObject device ;
	 

public DirectMethodCallback(DeviceObject device) {

		this.device = device;
	}
	
	
  @Override
  public DeviceMethodData call(String methodName, Object methodData, Object context)
  {
    DeviceMethodData deviceMethodData;
    String payload = new String((byte[])methodData);
    switch (methodName)
    {
      case "Telemetry" :
      {
        int status = 200;
        if(payload.contains("true")) {
        System.err.println("Received start telemetry request");
        deviceMethodData = new DeviceMethodData(status, "telemetry starting");
        device.setTelemetryOn(true);
      	}else {
      	  System.err.println("Received stop telemetry request");
      	deviceMethodData = new DeviceMethodData(status, "telemetry stopng");
         device.setTelemetryOn(false);
      	}
        break;
      }
      default:
      {
        int status = 401;
        deviceMethodData = new DeviceMethodData(status, "Not defined direct method " + methodName);
      }
    }
    return deviceMethodData;
  }
}