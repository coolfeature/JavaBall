package n.models;

/**
 * The class represents a match region and referee home area.
 */
public class South implements Area {
	
	boolean travel;
	
	public South() {
		this.travel = false;
	}
	
	public South(boolean travel) {
		this.travel = travel;
	}

	@Override
	public boolean getTravel() {
		return travel;
	}
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
	
	@Override
	public String getTravelFlag() {
		return getTravel() ? "Y" : "N";
	}
	@Override
	public boolean isAdjacent(Area other) {
		if (other instanceof North) {
			return false;
		} else {
			return true;	
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (travel ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		South other = (South) obj;
		if (travel != other.travel)
			return false;
		return true;
	}
	
	
}
