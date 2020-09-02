package com.azure.iothub;

import com.microsoft.azure.sdk.iot.device.IotHubMessageResult;
import com.microsoft.azure.sdk.iot.device.Message;
import com.microsoft.azure.sdk.iot.device.MessageCallback;


//az iot device c2d-message send --device-id sidtestdevice --hub-name sidiothub --data "messsage from cloud to device 2"

public class CloudToDeviceMessageCallback implements MessageCallback {
	 DeviceObject device ;
	 

public CloudToDeviceMessageCallback(DeviceObject device) {

		this.device = device;
	}

//Message can contain a payload as well as propety 
  public IotHubMessageResult execute(Message msg, Object context) {
    System.out.println("Received message from clound : "
      + new String(msg.getBytes(), Message.DEFAULT_IOTHUB_MESSAGE_CHARSET));

    return IotHubMessageResult.COMPLETE;
  }
}