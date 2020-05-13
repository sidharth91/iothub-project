package com.azure.iothub;

import com.microsoft.azure.sdk.iot.device.IotHubEventCallback;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;

public class MessageReceivedAckCallBack implements IotHubEventCallback{

	@Override
	public void execute(IotHubStatusCode responseStatus, Object callbackContext) {
		 System.out.println("Message sent successfully Response status: " + responseStatus);
		
	}

}
