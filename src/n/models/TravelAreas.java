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

	@Override
	public String toString() {
		return north.getTravelFlag() + central.getTravelFlag() + south.getTravelFlag();
	}
	
}
