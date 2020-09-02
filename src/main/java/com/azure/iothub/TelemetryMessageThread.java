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
                   
					 device.sendMessage("device to cloud telemetry message");

					Thread.sleep(device.getMessageTimeInterval());
				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}
			}
        }
		
	}


