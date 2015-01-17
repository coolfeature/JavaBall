package n.models;

public class Central implements Area {
	
	boolean travel;
	
	public Central() {
		this.travel = false;
	}	
	public Central(boolean travel) {
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
	public String getTravelFlag() {
		return getTravel() ? "Y" : "N";
	}
	
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