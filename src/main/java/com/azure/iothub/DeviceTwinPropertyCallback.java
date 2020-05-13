package com.azure.iothub;

import com.microsoft.azure.sdk.iot.device.DeviceTwin.PropertyCallBack;

public  class DeviceTwinPropertyCallback implements PropertyCallBack<String, Object>
{
	


public void PropertyCall(String propertyKey, Object propertyValue, Object context)
  {
    System.out.println("PropertyKey:     " + propertyKey);
    System.out.println("PropertyKvalue:  " + propertyValue.toString());
  
  }
}