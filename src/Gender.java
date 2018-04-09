
public enum Gender {
	Female("F"),
	Male("M");
	
	private String shorten;
	
	Gender(String shorten) {
		this.shorten = shorten;
	}
	
	public static Gender toGender(int value) {
		Gender values[] = Gender.values();
		if (0<=value && value < values.length) {
			return values[value];
		}
		return null;
	}
	
	public String toString() {
		return this.shorten;
	}
}
