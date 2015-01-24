package n.models;

/**
 * An object representing a referee qualification.
 */
public class Qualification {
	
	public static final String[] AWARDING_BODIES = {"NJB","IJB"};
	public static final Short[] LEVELS = {1,2,3,4};;
	
	String awardingBody = Qualification.AWARDING_BODIES[0];
	short level = Qualification.LEVELS[0];
	String category = Match.JUNIOR;
	
	/*
	 * Since the input from the file and from the form will always be correct
	 * it is safe to have the instance initialising to the default of 
	 * AWARDING_BODIES[0] + LEVELS[0] when the qualification constructor 
	 * String argument is not valid which should never occur. 
	 */
	
	/**
	 * Construct the object instance from a string parameter. 
	 * The parameter string should have the correct.
	 * @param qualification
	 */
	public Qualification (String qualification) {
		this(inferAwardingBody(qualification),inferLevel(qualification));
	}
	
	/**
	 * Construct the object instance using the awarding body and level 
	 * parameters.
	 * @param awardingBody
	 * @param level
	 */
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

	/**
	 * Infers the awarding body string from the qualification string.
	 * @param qualification
	 * @return the awarding body string
	 */
	private static String inferAwardingBody(String qualification) {
		if (qualification.length() > 3) {
			return qualification.substring(0,3);
		} else {
			return Qualification.AWARDING_BODIES[0];
		}
	}
	
	/**
	 * Infer the qualification level from the qualification string.
	 * @param qualification
	 * @return the qualification level
	 */
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
	
	/**
	 * A convenience method for determining at what index does the awarding 
	 * body string is defined in the AWARDING_BODIES array final variable.
	 * 
	 * The method is used by the GUI classes.
	 * 
	 * @return index position
	 */
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
	
	/**
	 * A convenience method for determining at what index does the 
	 * qualification level is defined in the LEVELS array final variable.
	 * 
	 * The method is used by the GUI classes.
	 * 
	 * @return index position
	 */
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
	
	/**
	 * Getter for awarding body.
	 * @return awarding body string.
	 */
	public String getAwardingBody() {
		return awardingBody;
	}

	/**
	 * Setter for awarding body.
	 * @param awardingBody
	 */
	public void setAwardingBody(String awardingBody) {
		this.awardingBody = awardingBody;
	}

	/**
	 * Getter for level.
	 * @return level
	 */
	public short getLevel() {
		return level;
	}

	/**
	 * Setter for level
	 * @param level
	 */
	public void setLevel(short level) {
		this.level = level;
	}
	
	/**
	 * Returns category based on the instance qualification level.
	 * @return
	 */
	public String getCategory() {
		return level == Qualification.LEVELS[0] ? 
			Match.JUNIOR : Match.SENIOR;
	}

	/**
	 * toString method that will return the awarding body
	 * and the qualification of the referee.
	 */
	public String toString() {
		return awardingBody + level;
	}
	
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((awardingBody == null) ? 0 : awardingBody.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + level;
		return result;
	}
	
	/**
	 * Method to check the state of an object being passed in.
	 * @param obj
	 * @return boolean
	 */
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
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (level != other.level)
			return false;
		return true;
	}
}
