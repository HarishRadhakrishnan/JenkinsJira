import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class Util {
	private static Charset ENCODING = StandardCharsets.UTF_8;
	public static void log(Object aMsg){
		System.out.println(String.valueOf(aMsg));
		
	}
	public static List<String> readFile(String fileName) throws Exception {
		Path path= Paths.get(fileName);
		try {
			return Files.readAllLines(path,ENCODING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new Exception("File Not found");
		}
	}
	public static void logBug(JiraRestClient client, FieldClassWrapper bugdetail, FieldsClass fields,
			IssueTypeClass issuetype, ProjectClass project, String test)
			throws IOException, JsonGenerationException, JsonMappingException, Exception {
		
		fields.setSummary(test.substring(15, Math.min(test.length(),91)));
		// System.out.println("test . trim function ---:"+test.trim());

		fields.setDescription(test);
		fields.setIssuetype(issuetype);
		fields.setProject(project);
		bugdetail.setFields(fields);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String bugDetailJson = ow.writeValueAsString(bugdetail);
		Util.log(bugDetailJson);

		// Rest-call code
		client.createBugInJira(bugDetailJson);
		System.out.println("after method------client.createBugInJira(bugDetailJson)------END-----Successfull");
	}
}
