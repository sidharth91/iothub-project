
IOT HUB DEVICE PROVISIONING AND LIFE CYCLE
---------------------------------------------

Generate Root CA private key  
openssl genrsa -out rootCA.key 4096  //generate RSA  private key and output to rootCA.key   

Generate Root CA certificate:  
openssl req -x509 -new -key rootCA.key -days 1024 -out rootCA.pem  
  

Generate key for device (we will call it a leaf):please do provide a common name  
openssl genrsa -out leaf_private_key.pem 4096  
 
Generate Certificate Signing Request for the device:  
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
