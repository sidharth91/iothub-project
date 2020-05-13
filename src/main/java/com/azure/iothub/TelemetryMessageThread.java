package com.azure.iothub;

public class TelemetryMessageThread implements Runnable {

	 DeviceObject device ;
	 
	public TelemetryMessageThread(DeviceObject device) {
		super();
		this.device = device;
	}



	@Override
	public void run() {
		   while(true) {

				try {
                    System.out.println("Sending message from provisioned device...");
					 device.sendMessage("device to cloud telemetry message");
					 System.out.println("Message interval "+device.getMessageTimeInterval());
					Thread.sleep(device.getMessageTimeInterval());
				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}
			}
        }
		
	}


