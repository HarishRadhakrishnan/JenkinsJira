import java.io.File;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public final class Appium {
	@SuppressWarnings("unchecked") 
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("/usr/share/tomcat7/.jenkins/workspace/TestMyArena/MyArenaTest1/test-output/testng-results.xml");
		
		//------------Jira COnfig
		final String CONFIG_FILE = "src/Config.txt";
		ConfigClass config = null;
	 	config = new ConfigClass(CONFIG_FILE);
		JiraRestClient client = new JiraRestClient(config);
		
		FieldClassWrapper bugdetail = new FieldClassWrapper();
		FieldsClass fields = new FieldsClass();

		IssueTypeClass issuetype = new IssueTypeClass();
		issuetype.setName(config.getJiraissuetypename());

		ProjectClass project = new ProjectClass();
		project.setKey(config.getJiraprojectkey());

		System.out.println("Creating Bugs in Jira server: "
				+ config.getJiraBaseURL());
		
		
		Document document = (Document) builder.build(xmlFile);
		Element rootNode = document.getRootElement();
		Attribute attributeFailedCount = rootNode.getAttribute("failed");
		Long failedCount = Long.valueOf(attributeFailedCount.getLongValue());
		System.out.println("Fail count = " + failedCount);
		Element suite = rootNode.getChild("suite");
		List<Element> tests = suite.getChildren("test");
		long foundFailureCount = 0;
		for (Element test :tests) {
			List<Element> classes = test.getChildren("class");
			for (Element klass : classes) {
				List<Element> testMethods = klass.getChildren("test-method");
				for (Element testMethod : testMethods) {
					if (testMethod.getAttribute("status").getValue().equals("FAIL")) {
						foundFailureCount++;
						String errorMessage = testMethod.getChild("exception").getChildText("message");
						Util.logBug(client, bugdetail, fields, issuetype, project,errorMessage);
					}
					if (foundFailureCount == failedCount) break;
				}
			}
			
		}

	}

}
