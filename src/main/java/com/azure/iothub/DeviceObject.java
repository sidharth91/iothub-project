package com.azure.iothub;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubEventCallback;
import com.microsoft.azure.sdk.iot.device.Message;
import com.microsoft.azure.sdk.iot.device.MessageCallback;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.DeviceMethodCallback;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.Pair;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.Property;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.PropertyCallBack;

public class DeviceObject {
	private int messageTimeInterval;
	private String deviceId;
	private String iothubURL;
	private DeviceClient deviceClient;
	private int battery;

	public DeviceObject(String deviceId, String iothubURL, DeviceClient deviceClient) {
		super();
		this.deviceId = deviceId;
		this.iothubURL = iothubURL;
		this.deviceClient = deviceClient;
		this.battery = 100;
		this.messageTimeInterval = 5000;

		// cloud to device message can be received
		// deviceClient.setMessageCallback(new AppMessageCallback(), null);
		try {
			deviceClient.open();

		


		} catch (IOException e) {
			System.out.println("not able to connect to device");
			e.printStackTrace();
		}

	}

	public DeviceClient getDeviceClient() {
		return deviceClient;
	}

	public void setDeviceClient(DeviceClient deviceClient) {
		this.deviceClient = deviceClient;
	}

	public int getMessageTimeInterval() {
		return messageTimeInterval;
	}

	public void setMessageTimeInterval(int messageTimeInterval) {
		this.messageTimeInterval = messageTimeInterval;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getIothubURL() {
		return iothubURL;
	}

	public void setIothubURL(String iothubURL) {
		this.iothubURL = iothubURL;
	}

	public int getBattery() {
		return battery;
	}

	public void setBattery(int battery) {
		this.battery = battery;
	}

	public void setCloudToDeviceMessageCallBack(MessageCallback callback) {
		deviceClient.setMessageCallback(callback, null);
	}

	public void subscribeToDeviceMethod(DeviceMethodCallback directMethodCallback,
			IotHubEventCallback directMethodStatusCallback) {
		try {
			// subscribe to direct method call message in from cloud to device
			deviceClient.subscribeToDeviceMethod(directMethodCallback, null, directMethodStatusCallback, null);
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public void subscribeToDeviceTwin(IotHubEventCallback deviceTwinStatusCallback,PropertyCallBack PropertyCallBack) {
		try {
			// started the device twin
			deviceClient.startDeviceTwin(deviceTwinStatusCallback, null, PropertyCallBack, null);
			// deviceClient.getDeviceTwin();
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public void subscribeToDeviceMessageInterval(PropertyCallBack PropertyCallBack) {


		Map<Property, Pair<PropertyCallBack<String, Object>, Object>> onDesiredPropertyChange = new HashMap<Property, Pair<PropertyCallBack<String, Object>, Object>>() {
			{
				put(new Property("messageTimeInterval", null),
						new Pair<PropertyCallBack<String, Object>, Object>(PropertyCallBack, null));
			}
		};

	
		try {
			// subscribe to device twin desiredProperties
			deviceClient.subscribeToDesiredProperties(onDesiredPropertyChange);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

	public void sendMessage(String message) {
		Message messageToSendFromDeviceToHub = new Message(message);
		deviceClient.sendEventAsync(messageToSendFromDeviceToHub, new MessageReceivedAckCallBack(), null);
	}

	public void reportStatusToTwin(Set<Property> property) {
		try {
			deviceClient.sendReportedProperties(property);
		} catch (IllegalArgumentException e) {
			System.out.println("Not able to report to device twin");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
