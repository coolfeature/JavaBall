package n.models;

/**
 * The class represents a match region and referee home area.
 */
public class North implements Area {
	
	boolean travel;
	
	/** default constructor */
	public North() {
		this.travel = false;
	}
	
	/**
	 * non-default constructor
	 * @param travel
	 */
	public North(boolean travel) {
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
	
	@Override
	public String toString() {
		String[] tokens = java.lang.invoke.MethodHandles.lookup()
				.lookupClass().getName().split("\\.");
		return tokens[tokens.length-1];
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
	@Override
	public boolean isAdjacent(Area other) {
		if (other instanceof South) {
			return false;
		} else {
			return true;	
		}
	}
	

	/**
	 * Method to check the state of an object being passed in.
	 * @param obj
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		North other = (North) obj;
		if (travel != other.travel)
			return false;
		return true;
	}
	
	
}
