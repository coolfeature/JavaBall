
public class TravelAreas {
	
	boolean north;
	boolean central;
	boolean south;
	String homeArea;
	
	public TravelAreas(String homeArea) {
		this.homeArea = homeArea;
		if (homeArea.equals(Referee.HOME_AREAS[0])) north = true;
		if (homeArea.equals(Referee.HOME_AREAS[1])) central = true;
		if (homeArea.equals(Referee.HOME_AREAS[2])) south = true;
		validate();
	}

	public TravelAreas(String homeArea, boolean north, boolean central, boolean south) {
		this.homeArea = homeArea;
		this.north = north;
		this.central = central;
		this.south = south;
		validate();
	}
	
	public TravelAreas(String homeArea, String travelAreas) {
		this.homeArea = homeArea;
		if (travelAreas.length() < 4 && travelAreas.length() > 0) {
			this.north = travelAreas.substring(0,1).equals("Y") ? true : false ;
			this.central = travelAreas.substring(1,2).equals("Y") ? true : false ;
			this.south = travelAreas.substring(2,3).equals("Y") ? true : false ;
		}
		validate();
	}
	
	public void validate() {
		if ((homeArea.equals(Referee.HOME_AREAS[0]) && !north)
				|| (homeArea.equals(Referee.HOME_AREAS[1]) && !central)
				|| (homeArea.equals(Referee.HOME_AREAS[2]) && !south)) {
			this.north = false;
			this.central = false;
			this.south = false;;
		}
	}
	
	@Override
	public String toString() {
		validate();
		if (!north && !central && !south) {
			return "";
		} else {
			return getNorth() + getCentral() + getSouth();
		}
	}

	
	public String getHomeArea() {
		return homeArea;
	}

	public void setHomeArea(String homeArea) {
		this.homeArea = homeArea;
	}

	public boolean isNorth() {
		return north;
	}
	
	public String getNorth() {
		return north ? "Y" : "N";
	}

	public void setNorth(boolean north) {
		this.north = north;
	}

	public boolean isCentral() {
		return central;
	}

	public String getCentral() {
		return central ? "Y" : "N";
	}
	
	public void setCentral(boolean central) {
		this.central = central;
	}

	public boolean isSouth() {
		return south;
	}
	
	public String getSouth() {
		return south ? "Y" : "N";
	}
	
	public static String getAreaFlag(boolean area) {
		return area ? "Y" : "N";
	}
	
	public void setSouth(boolean south) {
		this.south = south;
	}
	
	public static String getAdvice() {
		return "Ensure the referee travel areas include the home area e.g."
				+ " if the home area is " + Referee.HOME_AREAS[0] + " then the"
					+ " String of the class may be " + getAreaFlag(true) + "NN" ;
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
		if (central != other.central)
			return false;
		if (north != other.north)
			return false;
		if (south != other.south)
			return false;
		return true;
	}
}
