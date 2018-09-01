import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.naming.*;
import javax.naming.InitialContext;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.psi.HelloRemote;

public class Test {

	static {
		// I'm using this for testing purposes only; disable certificate validation in
		// Java SSL connections for localhost.
		disableSslVerification();
	}

	private static final String INITIAL_CONTEXT = "org.apache.openejb.client.RemoteInitialContextFactory";
	// private static final String PROVIDER_URL = "http://127.0.0.1:8080/psi/ejb"; // HTTP Instance
	// private static final String PROVIDER_URL = "https://127.0.0.1:443/psi/ejb"; // HTTPS Instance
	// private static final String PROVIDER_URL = "ejbd://localhost:4201"; // EJBD Instance
	// private static final String PROVIDER_URL = "ejbds://localhost:4203"; // HTTPEJBD Instance - Need to be configured
	// private static final String PROVIDER_URL = "http://localhost:4204/ejb"; // HTTPEJBD Instance

	public static void main(String[] args) throws Exception {
		System.out.println("Testing EJB via HTTP");
		testEJB("http://localhost:8080/psi/ejb");

		System.out.println("Testing EJB via HTTPS");
		testEJB("https://localhost:443/psi/ejb");

		System.out.println("Testing EJB via EJBD");
		testEJB("ejbd://localhost:4201");

		// System.out.println("Testing EJB via HTTP");
		// testEJB("ejbds://localhost:4203");

		System.out.println("Testing EJB via HTTPEJBD");
		testEJB("http://localhost:4204/ejb");

		System.out.println("Testing EJB via HTTP");
		testEJB("http://localhost:8081/psi/ejb");

		System.out.println("Testing EJB via HTTPS");
		testEJB("https://localhost:444/psi/ejb");

		System.out.println("Testing EJB via EJBD");
		testEJB("ejbd://localhost:4301");

		// System.out.println("Testing EJB via HTTP");
		// testEJB("ejbds://localhost:4203");

		System.out.println("Testing EJB via HTTPEJBD");
		testEJB("http://localhost:4304/ejb");

		// NOTE: While running this -- kill the first docker instance that is 4201->4201.
		System.out.println("Testing EJB via HTTPEJBD");
		testEJB("failover:ejbd://localhost:4201,ejbd://localhost:4301");
		testEJB("ejbd://localhost:4201");
	}

	private static void testEJB(String providerURL){
		try{
			Properties p = new Properties();
			p.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT);
			p.put(Context.PROVIDER_URL, providerURL);

			InitialContext ctx = new InitialContext(p);
			
			StringBuilder contexts = new StringBuilder("\n\t---	AVAILABLE CONTEXTS ----->>>>> "+providerURL+"\n");
			NamingEnumeration<NameClassPair> list = ctx.list("");
			while (list.hasMore()) {
			  contexts.append("\t - ").append(list.next().getName()).append("\n");
			}

			System.out.println(contexts);
			HelloRemote myBean = (HelloRemote) ctx.lookup("HelloRemote");
			System.out.println("\t - OUTPUT FROM EJB \t ::::: " +myBean.sayHello());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void disableSslVerification() {
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}
}
