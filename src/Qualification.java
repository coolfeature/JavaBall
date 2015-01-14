

public class Qualification {
	
	public static final String[] AWARDING_BODIES = {"NJB","IJB"};
	public static final short[] LEVELS = {1,2,3,4};
	
	String awardingBody;
	short level;
	
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
			return "";
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
			return 0;
		}
	}
	
	public String getAwardingBody() {
		return awardingBody;
	}

	public void setAwardingBody(String awardingBody) {
		this.awardingBody = awardingBody;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
	}

	public static String getAdvice() {
		StringBuilder sb = new StringBuilder("Awarding bodies are:");
		for (String awardingBody : Qualification.AWARDING_BODIES) {
			sb.append(" " + awardingBody);
		}
		sb.append(". Qualification levels are:");
		for (short level : Qualification.LEVELS) {
			sb.append(" " + level);
		}
		sb.append(". Example Qualification is: " 
				+ Qualification.AWARDING_BODIES[0] + Qualification.LEVELS[3]);
		return sb.toString();
	}
	
	@Override
	public String toString() {
		if (awardingBody != null && level != 0) {
			return awardingBody + level;
		} else {
			return "";
		}
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
