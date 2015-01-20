package n.models;

/**
 * Represents a referee.
 */
public class Referee implements Comparable<Referee> {
	
	public static final int[] SELECTED_REFEREES = {0,1};
	public static final String LIST_RECORD_FORMAT = "%3s %12s %12s %5s %3s %8s %3s" ;
	public static final String FILE_RECORD_FORMAT = "%5s %15s %15s %15s %15s %15s %15s" ;
	public static final String FIELD_ID = "ID";
	public static final String FIELD_FIRST_NAME = "FIRST NAME";
	public static final String FIELD_LAST_NAME = "LAST NAME";
	public static final String FIELD_QUALIFICATION = "QUALIFICATION";
	public static final String FIELD_ALLOCATIONS = "ALLOCATIONS";
	public static final String FIELD_HOME_AREA = "HOME AREA";
	public static final String FIELD_TRAVEL_AREA = "TRAVEL AREA";
	public static final String[] FIELD_NAMES = {FIELD_ID,FIELD_FIRST_NAME
		,FIELD_LAST_NAME,FIELD_QUALIFICATION,FIELD_ALLOCATIONS,FIELD_HOME_AREA
		,FIELD_TRAVEL_AREA};
	
	String id;
	String firstName;
	String lastName;
	Qualification qualification;
	int allocations;
	Area homeArea;
	TravelAreas travelAreas;
	
	/**
	 * Used to construct an instance with first and last name only.
	 * Uses defaults for other instance variables.
	 * @param firstName
	 * @param lastName
	 */
	public Referee(String firstName,String lastName) {
		this.id = "";
		this.firstName = firstName;
		this.lastName = lastName;
		this.qualification = new Qualification("");
		this.allocations = 0;
		this.homeArea = new North(true);
		this.travelAreas = new TravelAreas(this.homeArea);
	}
	
	/**
	 * Used to construct an instance will all instance variables passed in the
	 * constructor.
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param qualification
	 * @param allocations
	 * @param homeArea
	 * @param travelAreas
	 */
	public Referee(String id,String firstName,String lastName
			,Qualification qualification,int allocations
			,Area homeArea,TravelAreas travelAreas) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.qualification = qualification;
		this.allocations = allocations;
		this.homeArea = homeArea;
		this.travelAreas = travelAreas;
	}

	/**
	 * Used for creating object instance clones.
	 * @param clone
	 */
	public Referee(Referee clone) {
		this.id = clone.getId();
		this.firstName = clone.getFirstName();
		this.lastName = clone.getLastName();
		this.qualification = clone.getQualification();
		this.allocations = clone.getAllocations();
		this.homeArea = clone.getHomeArea();
		this.travelAreas = clone.getTravelAreas();
	}
	
	/**
	 * Getter for id.
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter for id.
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter for first name.
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for first name.
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for last name.
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for last name.
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for qualification
	 * @return qualification
	 */
	public Qualification getQualification() {
		return qualification;
	}

	/**
	 * Setter for Qualification
	 * @param qualification
	 */
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	/**
	 * Getter for allocations.
	 * @return allocation
	 */
	public int getAllocations() {
		return allocations;
	}

	/**
	 * Setter for allocaitons.
	 * @param allocations
	 */
	public void setAllocations(int allocations) {
		this.allocations = allocations;
	}

	/**
	 * Getter for home area.
	 * @return home area.
	 */
	public Area getHomeArea() {
		return homeArea;
	}
	
	/**
	 * Increases the number of allocation by 1.
	 */
	public void increaseAllocations() {
		this.allocations++;
	}
	
	/**
	 * Decreases the number of allocation by 1.
	 */
	public void decreaseAllocations() {
		if (this.allocations > 0) {
			this.allocations--;	
		}
	}
	
	/**
	 * Setter for home area.
	 * @param homeArea
	 */
	public void setHomeArea(Area homeArea) {
		this.homeArea = homeArea;
		if (homeArea instanceof North) {
			this.getTravelAreas().getNorth().setTravel(true);
		} else if (homeArea instanceof Central) {
			this.getTravelAreas().getCentral().setTravel(true);
		} else if (homeArea instanceof South) {
			this.getTravelAreas().getSouth().setTravel(true); 	
		}
	}

	/**
	 * Getter for travel areas.
	 * @return
	 */
	public TravelAreas getTravelAreas() {
		return travelAreas;
	}

	/**
	 * Setter for travel area.
	 * @param travelAreas
	 */
	public void setTravelAreas(TravelAreas travelAreas) {
		this.travelAreas = travelAreas;
	}

	/**
	 * Returns a formatted with with the instance variables of this class used
	 * by the GUI classes.
	 * @return formatted string with the values of instance variables.
	 */
	public String printRefereeRecord() {
		return "Id: " + this.getId() 
				+ "\nName: " + this.getFirstName() + " " + this.getLastName()
				+ "\nArea: " + this.getHomeArea()
				+ "\nQualification: " + this.getQualification().toString()
				+ "\nAllocations: " + this.getAllocations()
				+ "\nTravels: " + this.getTravelAreas().toString();
	}
	
	/**
	 * Returns a formatted with with the instance variables of this class used
	 * by the GUI classes.
	 * @param format
	 * @return formatted string with the values of instance variables.
	 */
	public String printRefereeRecord(String format) {
		return String.format(format, this.getId()
				,this.getFirstName(),this.getLastName()
				,this.getQualification().toString()
				,Integer.toString(this.getAllocations())
				,this.getHomeArea().toString()
				,this.getTravelAreas().toString());
	}

	/**
	 * Checks if the id instance variable of the parameter Referee object
	 * equals the id instance variable of this instance.
	 * @param other
	 * @return if id matches
	 */
	public boolean idMatch(Referee other) {
		return this.getId().equals(other.getId()) ? true : false;
	}


	/**
	 * An overridden method used by Array.sort().
	 * 
	 * Sorting should be done on first two characters of the ID
	 * and then the number.
	 */
	@Override
	public int compareTo(Referee other) {
		String thisInitials = this.getId().substring(0,2);
		String otherInitials = other.getId().substring(0,2);
		int thisIdNum = Integer.parseInt(this.getId().substring(2,3));
		int otherIdNum = Integer.parseInt(other.getId().substring(2,3));
		int areEqual = thisInitials.compareTo(otherInitials);
		if (areEqual == 0) {
			return thisIdNum == otherIdNum ? 0 : thisIdNum < otherIdNum ? -1 : 1; 
		} else {
			return areEqual;
		}
	}
	
	@Override
	public String toString() {
		return this.getFirstName() + " " + this.getLastName();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + allocations;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((homeArea == null) ? 0 : homeArea.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((qualification == null) ? 0 : qualification.hashCode());
		result = prime * result
				+ ((travelAreas == null) ? 0 : travelAreas.hashCode());
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
		Referee other = (Referee) obj;
		if (allocations != other.allocations)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (homeArea == null) {
			if (other.homeArea != null)
				return false;
		} else if (!homeArea.equals(other.homeArea))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (qualification == null) {
			if (other.qualification != null)
				return false;
		} else if (!qualification.equals(other.qualification))
			return false;
		if (travelAreas == null) {
			if (other.travelAreas != null)
				return false;
		} else if (!travelAreas.equals(other.travelAreas))
			return false;
		return true;
	}
	
}
