
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigClass {

	/**
	 * Reads all required Configuration-properties from given Config.file
	 * 
	 */
	private static Charset ENCODING = StandardCharsets.UTF_8;
	private String logFolder = "";
	private String Jiraissuetypename = "";
	private String Jiraprojectkey = "";
	private String JiraBaseURL ="";
	private String JiraCreateIssueURI = "";
	private String JiraGetProjectsURI = "";
	private String JiraUserName = "";
	private String JiraPassword = "";
	public static Charset getENCODING() {
		return ENCODING;
	}
	public String getLogFolder() {
		return logFolder;
	}
	public String getJiraissuetypename() {
		return Jiraissuetypename;
	}
	public String getJiraprojectkey() {
		return Jiraprojectkey;
	}
	public String getJiraBaseURL() {
		return JiraBaseURL;
	}
	public String getJiraCreateIssueURI() {
		return JiraCreateIssueURI;
	}
	public String getJiraGetProjectsURI() {
		return JiraGetProjectsURI;
	}
	public String getJiraUserName() {
		return JiraUserName;
	}
	public String getJiraPassword() {
		return JiraPassword;
	}
	
	private static Map<String, String> config = new HashMap<String, String>();
	 ConfigClass(String CONFIG_FILE) throws Exception {
		try {
			List<String>configdetails = Util.readFile(CONFIG_FILE);
			String[] configentry = new String[2];
			for(String str : configdetails){
				configentry = str.split("##");
				config.put(configentry[0], configentry[1]);
			}
			logFolder = config.get("LogFolder");
			Jiraissuetypename = config.get("Jiraissuetypename");
			Jiraprojectkey = config.get("Jiraprojectkey");
			JiraBaseURL = config.get("JiraBaseURL");
			JiraCreateIssueURI = config.get("JiraCreateIssueURI");
			JiraGetProjectsURI = config.get("JiraGetProjectsURI");
			JiraUserName = config.get("JiraUserName");
			JiraPassword = config.get("JiraPassword");
			} catch (Exception e) {
			
			throw new Exception("File Not found");
		}
	}

}
