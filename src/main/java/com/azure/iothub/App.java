package com.azure.iothub;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;
import com.microsoft.azure.sdk.iot.device.IotHubEventCallback;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.device.Message;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.Pair;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.Property;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.PropertyCallBack;
import com.microsoft.azure.sdk.iot.provisioning.device.ProvisioningDeviceClient;
import com.microsoft.azure.sdk.iot.provisioning.device.ProvisioningDeviceClientStatus;
import com.microsoft.azure.sdk.iot.provisioning.device.internal.exceptions.ProvisioningDeviceClientException;
import com.microsoft.azure.sdk.iot.provisioning.security.exceptions.SecurityProviderException;
import com.microsoft.azure.sdk.iot.provisioning.security.hsm.SecurityProviderX509Cert;

/**
 * Hello world!
 *
 */
public class App 
{
   
	private static final int MAX_TIME_TO_WAIT_FOR_REGISTRATION = 10000; // in milli seconds
    
	
    public static void main( String[] args ) throws SecurityProviderException, URISyntaxException, IOException
    {
    	System.out.println("Starting...");
        System.out.println("Beginning setup.");
        
        ExecutorService reportService=Executors.newFixedThreadPool(1);
        ExecutorService messageService=Executors.newFixedThreadPool(1);
        
        ProvisioningDeviceClient provisioningDeviceClient = null;
        try
        {
            ProvisioningStatus provisioningStatus = new ProvisioningStatus();
            
            //Create the Security provider by providing the private and publick key
            SecurityProviderX509Cert securityProvider=SecurityProvider.getInstance().getSecurityProviderX509Cert(); 
            //construct the provisioning device client
            provisioningDeviceClient = ProvisioningDeviceClientProvider.getInstance(securityProvider).getProvisioningDeviceClient();

            //register the device to iot hub through device provisioning service
            provisioningDeviceClient.registerDevice(new ProvisioningDeviceClientRegistrationCallbackImpl(), provisioningStatus);

            
            //will try for checking the result status of provisioning  device
            while (provisioningStatus.provisioningDeviceClientRegistrationInfoClient.getProvisioningDeviceClientStatus() != ProvisioningDeviceClientStatus.PROVISIONING_DEVICE_STATUS_ASSIGNED)
            {   
            	//if device provisioning failed for some reason whilte trying to connect 
                if (provisioningStatus.provisioningDeviceClientRegistrationInfoClient.getProvisioningDeviceClientStatus() == ProvisioningDeviceClientStatus.PROVISIONING_DEVICE_STATUS_ERROR ||
                        provisioningStatus.provisioningDeviceClientRegistrationInfoClient.getProvisioningDeviceClientStatus() == ProvisioningDeviceClientStatus.PROVISIONING_DEVICE_STATUS_DISABLED ||
                        provisioningStatus.provisioningDeviceClientRegistrationInfoClient.getProvisioningDeviceClientStatus() == ProvisioningDeviceClientStatus.PROVISIONING_DEVICE_STATUS_FAILED )

                {
                    provisioningStatus.exception.printStackTrace();
                    System.out.println("Registration error, bailing out");
                    break;
                }
                System.out.println("Waiting for Provisioning Service to register");
                Thread.sleep(MAX_TIME_TO_WAIT_FOR_REGISTRATION);
            }
            
            
             //successfully provisioned the device 
            if (provisioningStatus.provisioningDeviceClientRegistrationInfoClient.getProvisioningDeviceClientStatus() == ProvisioningDeviceClientStatus.PROVISIONING_DEVICE_STATUS_ASSIGNED)
            {
                System.out.println("IotHUb Uri : " + provisioningStatus.provisioningDeviceClientRegistrationInfoClient.getIothubUri());
                System.out.println("Device ID : " + provisioningStatus.provisioningDeviceClientRegistrationInfoClient.getDeviceId());
                // connect to iothub
                
                
                String iotHubUri = provisioningStatus.provisioningDeviceClientRegistrationInfoClient.getIothubUri();
                String deviceId = provisioningStatus.provisioningDeviceClientRegistrationInfoClient.getDeviceId();
                
           
                
                
                DeviceClient deviceClient = null;
                try
                {   
                	//deviceId and iothub details fetched from device provisioning and used to connect device to iot hub
                    deviceClient = DeviceClient.createFromSecurityProvider(iotHubUri, deviceId, securityProvider, IotHubClientProtocol.MQTT);
                    
                    DeviceObject device=new DeviceObject(deviceId, iotHubUri, deviceClient);
               
                    //cloud to device message can be received 
                    device.setCloudToDeviceMessageCallBack(new CloudToDeviceMessageCallback());
                    
                    //subscribe to direct method call message in from cloud to device
                    device.subscribeToDeviceMethod(new DirectMethodCallback(), new DirectMethodStatusCallback());
                   

                   //started the device twin
                    device.subscribeToDeviceTwin(new DeviceTwinStatusCallback(), new DeviceTwinPropertyCallback());
    
                    
                  //subscribe to device twin desiredProperties
                    device.subscribeToDeviceMessageInterval(new DesiredPropertyMessageIntervalCallback(device));
                    
                    
                    reportService.execute(new ReporPropertyThread(device));
                    messageService.execute(new TelemetryMessageThread(device));
                    
                 
                }
                catch (IOException e)
                {
                    System.out.println("Device client threw an exception: " + e.getMessage());
                    if (deviceClient != null)
                    {
                        deviceClient.closeNow();
                    }
                }	
                
                
            }
        }
        catch (ProvisioningDeviceClientException | InterruptedException e)
        {
            System.out.println("Provisioning Device Client threw an exception" + e.getMessage());
            if (provisioningDeviceClient != null)
            {
                provisioningDeviceClient.closeNow();
            }
        }
        provisioningDeviceClient.closeNow();
    }
}
