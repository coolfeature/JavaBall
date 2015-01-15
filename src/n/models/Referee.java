package n.models;

public class Referee implements Comparable<Referee> {
	
	public static final String[] FIELD_NAMES = {"ID","FIRST NAME","LAST NAME"
		,"QUALIFICATION","ALLOCATIONS","HOME AREA","TRAVEL AREA"};
	
	String id;
	String firstName;
	String lastName;
	Qualification qualification;
	int allocations;
	Area homeArea;
	TravelAreas travelAreas;
	

	public Referee(String firstName,String lastName) {
		this.id = "";
		this.firstName = firstName;
		this.lastName = lastName;
		this.qualification = new Qualification("");
		this.allocations = 0;
		this.homeArea = new North(true);
		this.travelAreas = new TravelAreas(this.homeArea);
	}
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public int getAllocations() {
		return allocations;
	}

	public void setAllocations(int allocations) {
		this.allocations = allocations;
	}

	public Area getHomeArea() {
		return homeArea;
	}

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

	public TravelAreas getTravelAreas() {
		return travelAreas;
	}

	public void setTravelAreas(TravelAreas travelAreas) {
		this.travelAreas = travelAreas;
	}

	@Override
	public String toString() {
		return "Referee [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", qualification=" + qualification
				+ ", allocations=" + allocations + ", homeArea=" + homeArea
				+ ", travelArea=" + travelAreas + ", category=" 
				+ getQualification().getCategory() + "]";
	}
	
	public boolean idMatch(Referee other) {
		return this.getId().equals(other.getId()) ? true : false;
	}


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

	/*
	 * Sorting should be done on first two characters of the ID
	 * and then the number
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
}
