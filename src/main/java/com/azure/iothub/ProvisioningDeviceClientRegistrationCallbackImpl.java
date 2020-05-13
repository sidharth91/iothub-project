package com.azure.iothub;

import com.microsoft.azure.sdk.iot.provisioning.device.ProvisioningDeviceClientRegistrationCallback;
import com.microsoft.azure.sdk.iot.provisioning.device.ProvisioningDeviceClientRegistrationResult;

class ProvisioningDeviceClientRegistrationCallbackImpl implements ProvisioningDeviceClientRegistrationCallback
{
    @Override
    public void run(ProvisioningDeviceClientRegistrationResult provisioningDeviceClientRegistrationResult, Exception exception, Object context)
    {
        if (context instanceof ProvisioningStatus)
        {
            ProvisioningStatus status = (ProvisioningStatus) context;
            status.provisioningDeviceClientRegistrationInfoClient = provisioningDeviceClientRegistrationResult;
            status.exception = exception;
        }
        else
        {
            System.out.println("Received unknown context");
        }
    }
}