package n.models;

/**
 * The class represents a match region and referee home area.
 */
public class Central implements Area {
	
	boolean travel;  //boolean variable to determine whether the referee can travel to or not
	
	/*default constructor, when an object of this class is created travel variable initialised 
	 * to false */	
	public Central() {
		this.travel = false;
	}
	
	/*non-default constructor, when a boolean variable is passed in as a parameter, travel is 
	 * initialised to this  */
	public Central(boolean travel) {
		this.travel = travel;
	}

	//public accessor method that returns the state of travel as a boolean
	@Override
	public boolean getTravel() {
		return travel;
	}
	
	/*public modifier method that allows you to change the state of travel by passing 
	 * it in as a parameter */
	@Override
	public void setTravel(boolean travel) {
		this.travel = travel;
		
	}
	
	//returns a String indicating Y or N based on whether boolean variable is true or false
	@Override
	public String getTravelFlag() {
		return getTravel() ? "Y" : "N";
	}
	
	//if the other parameter region being passed in is adjacent then return true otherwise false
	@Override
	public boolean isAdjacent(Area other) {
		return true;
	}
	
	@Override
	public String toString() {
		String[] tokens = java.lang.invoke.MethodHandles.lookup()
				.lookupClass().getName().split("\\.");
		return tokens[tokens.length-1];
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (travel ? 1231 : 1237);
		return result;
	}
	

	/**
	 * Method to allow you to check the state of an object being passed in.
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
		Central other = (Central) obj;
		if (travel != other.travel)
			return false;
		return true;
	}
	
	
	
}