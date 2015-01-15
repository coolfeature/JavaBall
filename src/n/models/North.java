package n.models;

public class North implements Area {
	
	boolean travel;
	
	public North() {
		this.travel = false;
	}
	
	public North(boolean travel) {
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
		if (other instanceof South) {
			return true;
		} else {
			return false;	
		}
	}
}
