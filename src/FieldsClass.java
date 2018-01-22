
public class FieldsClass {
	public IssueTypeClass issuetype;
	public ProjectClass project;

	public String summary;
	public String description;

	public IssueTypeClass getIssuetype() {
		return issuetype;
	}

	public void setIssuetype(IssueTypeClass issuetype) {
		this.issuetype = issuetype;
	}

	public ProjectClass getProject() {
		return project;
	}

	public void setProject(ProjectClass project) {
		this.project = project;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}