package n.models;

public class TravelAreas {

	public static final Area[] AREAS = {new North(),new Central(),new South()};
	
	North north;
	Central central;
	South south;

	public TravelAreas(Area homeArea) {
		this.north = homeArea instanceof North ? (North) homeArea : new North(false);
		this.central = homeArea instanceof Central ? (Central) homeArea : new Central(false);
		this.south = homeArea instanceof South ? (South) homeArea : new South(false);
	}
	
	public TravelAreas(North north,Central central,South south) {
		this.north = north;
		this.central = central;
		this.south = south;
	}

	public North getNorth() {
		return north;
	}

	public void setNorth(North north) {
		this.north = north;
	}

	public Central getCentral() {
		return central;
	}

	public void setCentral(Central central) {
		this.central = central;
	}

	public South getSouth() {
		return south;
	}

	public void setSouth(South south) {
		this.south = south;
	}
	
	public boolean travelsTo(Area area) {
		if (area instanceof North) {
			return getNorth().getTravel();
		} else if (area instanceof Central) {
			return getCentral().getTravel();
		} else {
			return getSouth().getTravel();
		}
	}

	@Override
	public String toString() {
		return north.getTravelFlag() + central.getTravelFlag() + south.getTravelFlag();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((central == null) ? 0 : central.hashCode());
		result = prime * result + ((north == null) ? 0 : north.hashCode());
		result = prime * result + ((south == null) ? 0 : south.hashCode());
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
		TravelAreas other = (TravelAreas) obj;
		if (central == null) {
			if (other.central != null)
				return false;
		} else if (!central.equals(other.central))
			return false;
		if (north == null) {
			if (other.north != null)
				return false;
		} else if (!north.equals(other.north))
			return false;
		if (south == null) {
			if (other.south != null)
				return false;
		} else if (!south.equals(other.south))
			return false;
		return true;
	}
	
}
