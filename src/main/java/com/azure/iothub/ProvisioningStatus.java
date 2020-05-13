package com.azure.iothub;

import com.microsoft.azure.sdk.iot.provisioning.device.ProvisioningDeviceClientRegistrationResult;

public class ProvisioningStatus {
	 ProvisioningDeviceClientRegistrationResult provisioningDeviceClientRegistrationInfoClient = new ProvisioningDeviceClientRegistrationResult();
	 Exception exception;
	 
	 
	public ProvisioningDeviceClientRegistrationResult getProvisioningDeviceClientRegistrationInfoClient() {
		return provisioningDeviceClientRegistrationInfoClient;
	}
	public void setProvisioningDeviceClientRegistrationInfoClient(
			ProvisioningDeviceClientRegistrationResult provisioningDeviceClientRegistrationInfoClient) {
		this.provisioningDeviceClientRegistrationInfoClient = provisioningDeviceClientRegistrationInfoClient;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	 
	 
}
