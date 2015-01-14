

public class Qualification {
	
	public static final String[] AWARDING_BODIES = {"NJB","IJB"};
	public static final Short[] LEVELS = {1,2,3,4};
	
	String awardingBody = Qualification.AWARDING_BODIES[0];
	short level = Qualification.LEVELS[0];
	
	/*
	 * Since the input from the file and from the form will always be correct
	 * it is safe to have the instance initialising to the default of 
	 * AWARDING_BODIES[0] + LEVELS[0] when the qualification constructor 
	 * String argument is not valid which should never occur. 
	 */
	public Qualification (String qualification) {
		this(inferAwardingBody(qualification),inferLevel(qualification));
	}
	
	public Qualification (String awardingBody, short level) {
		for (String ab : Qualification.AWARDING_BODIES) {
			if (awardingBody.equals(ab)) {
				this.awardingBody = awardingBody;
			}
		}
		for (short l : Qualification.LEVELS) {
			if (l == level) {
				this.level = level;
			}
		}
	}

	private static String inferAwardingBody(String qualification) {
		if (qualification.length() > 3) {
			return qualification.substring(0,3);
		} else {
			return Qualification.AWARDING_BODIES[0];
		}
	}
	
	private static short inferLevel(String qualification) {
		if (qualification.length() > 3) {
			try {
				return Short.parseShort(qualification.substring(3,4));
			} catch (NumberFormatException nfe) {
				return 0;
			}
		} else {
			return Qualification.LEVELS[0];
		}
	}
	
	public int getAwardingBodyIndex() {
		int awardingBodyIndex = -1;
		for (int i=0;i<Qualification.AWARDING_BODIES.length;i++) {
			if (Qualification.AWARDING_BODIES[i].equals(this.getAwardingBody())) {
				awardingBodyIndex = i;
				break;
			}
		}
		return awardingBodyIndex;
	}
	
	public int getLevelIndex() {
		int levelIndex = -1;
		for (int i=0;i<Qualification.LEVELS.length;i++) {
			if (Qualification.LEVELS[i] == this.getLevel()) {
				levelIndex = i;
				break;
			}
		}
		return levelIndex;
	}
	
	public String getAwardingBody() {
		return awardingBody;
	}

	public void setAwardingBody(String awardingBody) {
		this.awardingBody = awardingBody;
	}

	public short getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
	}
	
	@Override
	public String toString() {
		return awardingBody + level;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Qualification other = (Qualification) obj;
		if (awardingBody == null) {
			if (other.awardingBody != null)
				return false;
		} else if (!awardingBody.equals(other.awardingBody))
			return false;
		if (level != other.level)
			return false;
		return true;
	}
}
