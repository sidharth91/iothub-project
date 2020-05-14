package com.azure.iothub;

import java.util.Collection;
import java.util.LinkedList;

import com.microsoft.azure.sdk.iot.provisioning.security.exceptions.SecurityProviderException;
import com.microsoft.azure.sdk.iot.provisioning.security.hsm.SecurityProviderX509Cert;

public class SecurityProvider {
	private static String leafPublicPem = "<leafPublicPem>";
	private static String leafPrivateKey = "<leafPrivateKey>";
	private static Collection<String> signerCertificates = new LinkedList<>();

	private SecurityProviderX509Cert securityProviderX509Cert;

	private static SecurityProvider securityProvider = null;
		
	private SecurityProvider(String leafPublicPem, String leafPrivateKey, Collection<String> signerCertificates) {
		try {
			securityProviderX509Cert = new SecurityProviderX509Cert(leafPublicPem, leafPrivateKey, signerCertificates);
		} catch (SecurityProviderException e) {
			e.printStackTrace();
		}
	}
		
		
	public static SecurityProvider getInstance() {

		if (securityProvider == null) {
			securityProvider = new SecurityProvider(leafPublicPem, leafPrivateKey, signerCertificates);
		}
		return securityProvider;
	}
		  
		  
		
	public SecurityProviderX509Cert getSecurityProviderX509Cert() {
		return securityProvider.securityProviderX509Cert;
	}
		
		
		
		
		
}
