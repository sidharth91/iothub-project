
IOT HUB DEVICE PROVISIONING AND LIFE CYCLE
---------------------------------------------

Description
-------------
1.individual device enrollment using device provisioning service and x509 certificate.  
2.device to cloud message.  
3.cloud to device message.  
4.cloud to device direct method invoke.  
5.device twin concepta change in cloud device twin receive event in device (desired properties).  
6.reporting data from device to device twin (reported properties).    


Certificate creation for individual device enrollment
-------------------------------------------------------

Generate Root CA private key  
openssl genrsa -out rootCA.key 4096  //generate RSA  private key and output to rootCA.key   

Generate Root CA certificate  
please do provide a common name but dont use the same common name in below steps  
openssl req -x509 -new -key rootCA.key -days 1024 -out rootCA.pem  
  

Generate key for device (we will call it a leaf)  
openssl genrsa -out leaf_private_key.pem 4096  
 
Generate Certificate Signing Request for the device  
please do provide a common name but different then previous one . this common name should be same as device id if device is 
created or it will be used to create the deviceid same as common name  
openssl req -new -key leaf_private_key.pem -out leaf.csr  
  
Generate device certificate (leaf certificate):give common name same as deviceid which will be registed  
openssl x509 -req -in leaf.csr -CA rootCA.pem -CAkey rootCA.key -CAcreateserial -out leaf_certificate.pem  
  


Steps to upload the certificate to DPS Service
---------------------------------------------------
step1:create a device provisioning service using azure client or portal   
step2:create a IOT HUB using azure client or portal  
step3:link the DPS to IOTHUB   
step4:go to Manage enrollments->Add Individual enrollments   
step5:in the primary certificate upload the leaf_certificate.pem   


Before running the program copy  leaf_certificate.pem string to SecurityProvider->leafPublicPem variable and leaf_private_key.pem string to  SecurityProvider->leafPrivateKey  
check the idscope and globalEndpoint as per your created device provisioning service
