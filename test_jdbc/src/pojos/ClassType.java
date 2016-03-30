package pojos;

public class ClassType {
	private String name;
	private String description;
	
	public ClassType(String classTypeName, String classTypeDescription) {
		name = classTypeName;
		description = classTypeDescription;
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}

}
