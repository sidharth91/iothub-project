package com.azure.iothub;

import com.microsoft.azure.sdk.iot.provisioning.device.ProvisioningDeviceClient;
import com.microsoft.azure.sdk.iot.provisioning.device.ProvisioningDeviceClientTransportProtocol;
import com.microsoft.azure.sdk.iot.provisioning.device.internal.exceptions.ProvisioningDeviceClientException;
import com.microsoft.azure.sdk.iot.provisioning.security.hsm.SecurityProviderX509Cert;

public class ProvisioningDeviceClientProvider {
    private static final String idScope = "0ne000F6BD3";
    private static final String globalEndpoint = "global.azure-devices-provisioning.net";
	private static final ProvisioningDeviceClientTransportProtocol PROVISIONING_DEVICE_CLIENT_TRANSPORT_PROTOCOL = ProvisioningDeviceClientTransportProtocol.HTTPS;
	private static final int MAX_TIME_TO_WAIT_FOR_REGISTRATION = 10000;
	private static ProvisioningDeviceClientProvider provisioningDeviceClientProvider = null;
	private ProvisioningDeviceClient provisioningDeviceClient;
	
	
	public ProvisioningDeviceClientProvider(ProvisioningDeviceClient provisioningDeviceClient) {
		super();
		this.provisioningDeviceClient = provisioningDeviceClient;
	}
	
	
	private ProvisioningDeviceClientProvider(SecurityProviderX509Cert securityProviderX509Cert) throws ProvisioningDeviceClientException {
		provisioningDeviceClient= ProvisioningDeviceClient.create(globalEndpoint, idScope, PROVISIONING_DEVICE_CLIENT_TRANSPORT_PROTOCOL, securityProviderX509Cert); 
	}


	public static ProvisioningDeviceClientProvider getInstance(SecurityProviderX509Cert securityProviderX509Cert) throws ProvisioningDeviceClientException {

		if (provisioningDeviceClientProvider == null) {
			provisioningDeviceClientProvider = new ProvisioningDeviceClientProvider(securityProviderX509Cert);
		}
		return provisioningDeviceClientProvider;
	}
		  
	
	public ProvisioningDeviceClient getProvisioningDeviceClient() {
		return provisioningDeviceClientProvider.provisioningDeviceClient;
	}
	 
	
	
}
