package com.azure.iothub;

import java.util.Collection;
import java.util.LinkedList;

import com.microsoft.azure.sdk.iot.provisioning.security.exceptions.SecurityProviderException;
import com.microsoft.azure.sdk.iot.provisioning.security.hsm.SecurityProviderX509Cert;

public class SecurityProvider {
	private static String leafPublicPem = "-----BEGIN CERTIFICATE-----\r\n"
			+ "MIIFPTCCAyUCFBUUlchB/fEOg/0jLYAWubgLV+XmMA0GCSqGSIb3DQEBCwUAMFkx\r\n"
			+ "CzAJBgNVBAYTAkFVMRMwEQYDVQQIDApTb21lLVN0YXRlMSEwHwYDVQQKDBhJbnRl\r\n"
			+ "cm5ldCBXaWRnaXRzIFB0eSBMdGQxEjAQBgNVBAMMCXNpZHJvb3RjYTAeFw0yMDA1\r\n"
			+ "MDcwNjMxMTZaFw0yMDA2MDYwNjMxMTZaMF0xCzAJBgNVBAYTAkFVMRMwEQYDVQQI\r\n"
			+ "DApTb21lLVN0YXRlMSEwHwYDVQQKDBhJbnRlcm5ldCBXaWRnaXRzIFB0eSBMdGQx\r\n"
			+ "FjAUBgNVBAMMDXNpZHRlc3RkZXZpY2UwggIiMA0GCSqGSIb3DQEBAQUAA4ICDwAw\r\n"
			+ "ggIKAoICAQDernmD79stpVvrPdHSC0c3woXzT7ecq1+1posCi8IvfU9yj/PKdI6k\r\n"
			+ "lhjodNy3ERVpxXIgxJBX+ohfCv0Bv9VqInhxAnNOXMfx3Hh2D5Mxqys3OqvRDnmI\r\n"
			+ "oBHqTCfbSWy43JCSii6Y9TjoICsyi1/LJ8fwbUK4A/y+6jsOEdK4zMPt9cV1HwAM\r\n"
			+ "oJK23URilbMNSPtCqp4wK6Q0/FKtD9/zsxhIiRMjiDcI+/qEUO3EiGtElwNKIhMG\r\n"
			+ "Lyi5rku9+Qfxz4u5K1SC9IzNKBDXEHHV6+aPcRK8ljVUhA3sSJDLJMHt/rHNXEuN\r\n"
			+ "rjT0mjuzCLpkEu9eBZ1/iskWWhCVgXyoZKhg83AmMl0xyMfoJYgSZVjgmxDue+9V\r\n"
			+ "7xmtZ9QwRTjrEIrMecwFvQRB/Ytx4FS8uhgh8lknxcscAujpJmd5mwwOt29L+pkc\r\n"
			+ "Eo+dNzwK9OkBHj03dCDxFW77PcbCYAGRUU+33CpcqJWFOMenYt+G5sZHFtok9P7a\r\n"
			+ "uWGZCF56sBQ+21pmJT/8g8ZjeWBirvvQIlnh5LFiTWO0/BzBu0umb4B0eisFhOoz\r\n"
			+ "yHqbGDsDvkX4t3iK1S+wNlPMbLPbBpaT8yxgVCbsnXZf4toMytOjY/7rKl/p7Aej\r\n"
			+ "swK8NzwseEG6FcYsvsMxSeXvCFn7V+QSQAtAaPEazzurawNIVpq+oQIDAQABMA0G\r\n"
			+ "CSqGSIb3DQEBCwUAA4ICAQBQ/WW5v8CIGNtXkWmqpOP5YfqIaiJytgd1an/xHWeI\r\n"
			+ "EfZSDVeFl2/4qyWuI8yt1n9cZtiIifsX6KquZClsgM/w9Z8VtRqLo5u4GjRMLk/G\r\n"
			+ "2rNTaktvn+qQ88GDb1Ik4OenBTaNDdDtHtITwHfz3PNnizlXpryHfPKnUZzh3zOX\r\n"
			+ "MITjTtj5LArzdpY/EkIuU99idqqJ1zQNVQInLm7f9w+vsqYtiXSQ6lhFyjZWYen7\r\n"
			+ "opo2t1S0nGYQBzk0LoZOg8GwoGdUq50WH2f3MRcmp6ppqhOHIbwatsQh8ilvCfah\r\n"
			+ "ScKAJJ3SveSd0KABxIxbLv880IyKYM9qx2oIcoRXL0u7QXC0ZD9hW6FKy4SpBpjK\r\n"
			+ "xKkWjQ21djoGBubQedLQyhXd8YV7sI+a5/TYzao5WNRXwJHVranEgyjryyU94NeH\r\n"
			+ "DNKSgMT7DvfTTkB2TU7iqUsjDbqyYMJkNJ4pTs+Fx8aqeUCuB40wesNjRFilddeR\r\n"
			+ "mViMJvaCzLfbX1o5o5YMlNO5DTsn4+kPq1KcK/EzcHeykUtA0aX9+nl/sqOCkrBF\r\n"
			+ "ial9vmiF6S+juzQVa1vD0dvYV7FknO8gFnCrrRkg04O1fEWVmEbtgFWAtav3mP5r\r\n"
			+ "m666S8EhCNc/moPlZd+bKQVY7cZ4aBGm9GJ960ZJrfO8O1UHHX/XedQd14SfR2+D\r\n" + "cw==\r\n"
			+ "-----END CERTIFICATE-----";
	private static String leafPrivateKey = "-----BEGIN RSA PRIVATE KEY-----\r\n"
			+ "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\r\n" + "-----END RSA PRIVATE KEY-----";
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
