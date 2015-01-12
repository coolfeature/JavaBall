
public class Referee implements Comparable<Referee> {
	
	static final String[] FIELD_NAMES = {"ID","FIRST NAME","LAST NAME"
		,"QUALIFICATION","ALLOCATIONS","HOME AREA","TRAVEL AREA"};
	
	String id;
	String firstName;
	String lastName;
	String qualification;
	int allocations;
	String homeArea;
	String travelAreas;
	
	public Referee(String id,String firstName,String lastName
			,String qualification,int allocations
			,String homeArea,String travelAreas) {

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

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public int getAllocations() {
		return allocations;
	}

	public void setAllocations(int allocations) {
		this.allocations = allocations;
	}

	public String getHomeArea() {
		return homeArea;
	}

	public void setHomeArea(String homeArea) {
		this.homeArea = homeArea;
	}

	public String getTravelAreas() {
		return travelAreas;
	}

	public void setTravelAreas(String travelAreas) {
		this.travelAreas = travelAreas;
	}

	@Override
	public String toString() {
		return "Referee [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", qualification=" + qualification
				+ ", allocations=" + allocations + ", homeArea=" + homeArea
				+ ", travelArea=" + travelAreas + "]";
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

	@Override
	public int compareTo(Referee other) {
		/*
		 * Sorting should be done on first two characters of the ID
		 * and then the number
		 */
		String thisInitials = this.getId().substring(0,1);
		String otherInitials = other.getId().substring(0,1);
		int thisIdNum = Integer.parseInt(this.getId().substring(1,2));
		int otherIdNum = Integer.parseInt(other.getId().substring(1,2));
		int areEqual = thisInitials.compareTo(otherInitials);
		if (areEqual == 0) {
			return thisIdNum == otherIdNum ? 0 : thisIdNum < otherIdNum ? -1 : 1; 
		} else {
			return areEqual;
		}
	}
}
