package com.azure.iothub;

import com.microsoft.azure.sdk.iot.device.DeviceTwin.DeviceMethodCallback;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.DeviceMethodData;

public class DirectMethodCallback implements DeviceMethodCallback
{
  @Override
  public DeviceMethodData call(String methodName, Object methodData, Object context)
  {
    DeviceMethodData deviceMethodData;
    switch (methodName)
    {
      case "reboot" :
      {
        int status = 200;
        System.err.println("Received reboot request");
        deviceMethodData = new DeviceMethodData(status, "Started reboot");
        RebootDeviceThread rebootThread = new RebootDeviceThread();
        Thread t = new Thread(rebootThread);
        t.start();
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