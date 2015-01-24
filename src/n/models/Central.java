package n.models;

/**
 * The class represents a match region and referee home area.
 */
public class Central implements Area {
	
	boolean travel;
	
	/** 
	 * default constructor
	 */
	public Central() {
		this.travel = false;
	}
	
	/**non-default constructor
	 * @param travel
	 */
	public Central(boolean travel) {
		this.travel = travel;
	}

	/**
	 * Getter method that returns the state of travel
	 * @return travel
	 */
	@Override
	public boolean getTravel() {
		return travel;
	}
	
	/**
	 * Setter for travel
	 * @param travel
	 */
	@Override
	public void setTravel(boolean travel) {
		this.travel = travel;		
	}
	
	/**
	 * Getter for TravelFlag
	 * @return getTravel
	 */
	@Override
	public String getTravelFlag() {
		return getTravel() ? "Y" : "N";
	}
	
	/**
	 * Checks whether an area is adjacent to another
	 * @param other
	 * @return boolean
	 */
	public boolean isAdjacent(Area other) {
		return true;
	}
	

	public String toString() {
		String[] tokens = java.lang.invoke.MethodHandles.lookup()
				.lookupClass().getName().split("\\.");
		return tokens[tokens.length-1];
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (travel ? 1231 : 1237);
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
		Central other = (Central) obj;
		if (travel != other.travel)
			return false;
		return true;
	}
	
	
	
}