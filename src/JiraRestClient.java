/**
 * 
 */


import javax.naming.AuthenticationException;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;

public class JiraRestClient {

	/**
	 * 
	 */
	private String BASE_URL = "";
	private String auth = "";
	public JiraRestClient(ConfigClass config) {
		BASE_URL = config.getJiraBaseURL();
		//BASE_URL ="https://mtdevopscoe.atlassian.net/";
		
		auth = new String(Base64.encode(config.getJiraUserName()+":"+config.getJiraPassword()));
		//auth = new String(Base64.encode("dummy.user"+":"+"M!ndtree@1234"));
	}
	public void createBugInJira(String bugdetails) throws Exception {
		try { 
			System.setProperty("http.proxyHost","172.22.218.218") ;
		    System.setProperty("http.proxyPort", "8085");
		    System.setProperty("https.proxyHost","172.22.218.218") ;
		    System.setProperty("https.proxyPort", "8085");
		    
			String createBugResponse = invokePostMethod(auth, BASE_URL +"/rest/api/2/issue", bugdetails);
			System.out.println("bugresponse:"+createBugResponse);
			System.out.println("-----------------------");
			JSONObject issueObj = new JSONObject(createBugResponse);
			String newKey = issueObj.getString("key");
			System.out.println("success integrated Key:"+ newKey);
		} catch (AuthenticationException e) {
			//System.out.println("Username or Password wrong!");
			throw new Exception("Username or Password wrong!");
			//e.printStackTrace();
		} catch (ClientHandlerException e) {
			System.out.println("Error invoking REST method " + e.getMessage());
			throw new Exception("Error invoking REST method");
			//e.printStackTrace();
		} catch (JSONException e) {
			//System.out.println("Invalid JSON output");
			//throw new Exception("Invalid JSON output");
			e.printStackTrace();
		}

	}
	private static String invokePostMethod(String auth, String url, String data) throws AuthenticationException, ClientHandlerException {
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
				.accept("application/json").post(ClientResponse.class, data);
		System.out.println("response in invoke:"+response.getStatus());
		int statusCode = response.getStatus();
		if (statusCode == 401) {
			throw new AuthenticationException("Invalid Username or Password");
		}
		return response.getEntity(String.class);
	}
	@SuppressWarnings("unused")
	private static String invokeGetMethod(String auth, String url) throws AuthenticationException, ClientHandlerException {
		Client client = Client.create();
		
		WebResource webResource = client.resource(url);
		
		ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
				.accept("application/json").get(ClientResponse.class
						);
		int statusCode = response.getStatus();
		if (statusCode == 401) {
			throw new AuthenticationException("Invalid Username or Password");
		}
		return response.getEntity(String.class);
	}
	@SuppressWarnings("unused")
	private static void invokePutMethod(String auth, String url, String data) throws AuthenticationException, ClientHandlerException {
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
				.accept("application/json").put(ClientResponse.class, data);
		int statusCode = response.getStatus();
		if (statusCode == 401) {
			throw new AuthenticationException("Invalid Username or Password");
		}
	}
	
	@SuppressWarnings("unused")
	private static void invokeDeleteMethod(String auth, String url) throws AuthenticationException, ClientHandlerException {
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
				.accept("application/json").delete(ClientResponse.class);
		int statusCode = response.getStatus();
		if (statusCode == 401) {
			throw new AuthenticationException("Invalid Username or Password");
		}
	}


}
