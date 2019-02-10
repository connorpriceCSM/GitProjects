
public enum Setting {

	OFF("---"), LOW("--+"), MEDIUM("-++"), HIGH("+++");
	private String burnerSetting;
	
	Setting(String setting)
	{
		burnerSetting = setting;
	}
	
	 public String toString() {
		
		 return burnerSetting;
	}
	
	
}
	
	

