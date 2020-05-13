package com.azure.iothub;

import com.microsoft.azure.sdk.iot.device.DeviceTwin.PropertyCallBack;

public  class DesiredPropertyMessageIntervalCallback implements PropertyCallBack<String, Object>
{

	 DeviceObject device ;
	 

public DesiredPropertyMessageIntervalCallback(DeviceObject device) {

		this.device = device;
	}
  
  




public void PropertyCall(String propertyKey, Object propertyValue, Object context)
  {
    System.out.println("messageTimeInterval PropertyKey:     " + propertyKey);
    System.out.println("messageTimeInterval PropertyKvalue:  " + propertyValue.toString());
    device.setMessageTimeInterval(Double.valueOf(propertyValue.toString()).intValue());
    System.out.println("messageTimeInterval "+Double.valueOf(propertyValue.toString()).intValue());
  }
}