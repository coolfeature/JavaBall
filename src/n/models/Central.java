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
	
	
	
}