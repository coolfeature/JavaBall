package n.models;

import java.util.Arrays;
import java.util.List;

/**
 * The class represents a match object.
 */
public class Match {
	
	public static final String FIELD_WEEK = "WEEK";
	public static final String FIELD_AREA = "AREA";
	public static final String FIELD_GROUP= "GROUP";
	public static final String FIELD_REFEREE1 = "REFEREE 1";
	public static final String FIELD_REFEREE2 = "REFEREE 2";
	public static final String REPORT_DISPLAY_FORMAT = "%6s %8s %9s %20s %20s";
	public static final String LIST_DISPLAY_FORMAT = "%4s %6s %7s %15s %15s";
	public static final String NO_REFEREE_MSG = "N/A";
	public static final String JUNIOR = "Junior";
	public static final String SENIOR = "Senior";
	public static final String[] CATEGORIES = { JUNIOR, SENIOR };
	public static final short MIN_MATCHES = 1;
	public static final short MAX_MATCHES = 52;
	public static final int PER_MATCH = Referee.SELECTED_REFEREES.length;

	int week;
	Area area;
	String category;
	List<Referee> allocatedReferees;

	/**
	 * The default constructor instantiates the class with some default values.
	 */
	public Match() {
		this(Match.MIN_MATCHES, new North(), Match.JUNIOR);
	}
	
	/**
	 * This constructor is used to clone the object.
	 * @param clone
	 */
	public Match(Match clone) {
		this.week = clone.getWeek();
		this.area = clone.getArea();
		this.category = clone.getCategory();
		this.allocatedReferees = clone.getAllocatedRefereesList();
	}
	
	/**
	 * This constructor instantiates the class with some specified instance 
	 * variables.
	 * @param week
	 * @param area
	 * @param category
	 */
	public Match(int week, Area area, String category) {
		this.week = week;
		this.area = area;
		this.category = category;
	}

	/**
	 * Getter method for week.
	 * @return
	 */
	public int getWeek() {
		return week;
	}

	/**
	 * Setter method for week.
	 * @param week
	 */
	public void setWeek(int week) {
		this.week = week;
	}

	/**
	 * Getter method for area
	 * @return
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * Setter method for area
	 * @param area
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * Getter method for category
	 * @return
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Setter method for category
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * Returns a string with referee details formatted.
	 * @param refereeArrayIndex
	 * @return
	 */
	public String printRefereeDetails(int refereeArrayIndex) {
		Referee[] referees = getAllocatedReferees();
		if (referees != null) {
			if (referees.length > refereeArrayIndex) {
				Referee referee = referees[refereeArrayIndex];
				return referee.printRefereeRecord();				
			} 
		}
		return NO_REFEREE_MSG;
	}
	
	/**
	 * Prints referees argument to console.
	 * @param referees
	 */
	public void printReferees(Referee[] referees) {
		if (referees == null ) return;
		for (Referee r : referees) {
			System.out.println(r.toString());
		}
	}

	/**
	 * Returns a string with this Match object details formatted for the 
	 * display on the final report.
	 * @return
	 */
	public String toReportString() {
		String week = Integer.toString(getWeek());
		String category = getCategory();
		String area = getArea().toString();
		String[] refs = {"UNKNOWN","UNKNOWN"};
		Referee[] referees = getSelectedReferees();
		if (referees != null) {
			for (int i=0;i<referees.length;i++) {
				refs[i] = referees[i].toString(); 
			}			
		}
		return String.format(Match.REPORT_DISPLAY_FORMAT, 
			week,category,area,refs[0],refs[1]);
	}
	
	@Override
	public String toString() {
		String week = Integer.toString(getWeek());
		String category = getCategory();
		String area = getArea().toString();
		String[] refs = {"UNKNOWN","UNKNOWN"};
		Referee[] referees = getSelectedReferees();
		if (referees != null) {
			for (int i=0;i<referees.length;i++) {
				refs[i] = referees[i].toString(); 
			}			
		}
		return String.format(Match.LIST_DISPLAY_FORMAT, 
			week,category,area,refs[0],refs[1]);
	}

	/**
	 * Getter for the allocatedReferees.
	 * @return
	 */
	public List<Referee> getAllocatedRefereesList() {
		return allocatedReferees;
	}

	/**
	 * Getter for the allocated referees that returns an array.
	 * @return
	 */
	public Referee[] getAllocatedReferees() {
		if (allocatedReferees != null) {
			return allocatedReferees.toArray(new Referee[allocatedReferees.size()]);	
		} else {
			return null;
		}
	}
	

	/**
	 * Returns the referees selected for the match.
	 * 
	 * The selected referees are the most suitable allocated referees.
	 * The number of selected referees is set in PER_MATCH final 
	 * variable.
	 * @return
	 */
	public Referee[] getSelectedReferees() {
		Referee[] allocated = getAllocatedReferees() ;
		if (allocated != null) {
			if (allocated.length >= Match.PER_MATCH) {
				Referee[] selected = new Referee[Match.PER_MATCH];	
				for (int i=0;i<Match.PER_MATCH;i++) {
					selected[i] = allocated[i]; 
				}	
				return selected;
			}
		} 
		return null;
	}
	
	/**
	 * Setter for allocated referees.
	 * 
	 * A convenience method for the setting of allocated referees with a List
	 * argument.
	 * @param allocatedReferees
	 */
	public void setAllocatedReferees(List<Referee> allocatedReferees) {
		setAllocatedReferees(allocatedReferees.toArray(new Referee[allocatedReferees.size()]));
	}

	
	/**
	 * Returns an array of referees sorted in the order of suitability for the 
	 * match. The new array contains deeply copied elements (without 
	 * descendants) from the source array.
	 * 
	 * @NOTES: 
	 * 	
	 * 1) The goal was to implement the method using array algorithms only
	 * avoiding the use of Lists or Comparators.
	 * 
	 * 2) The suitable referees are selected on the basis of their 
	 * qualification and the willingness to travel to the match area. The
	 * meeting of the two requirements is essential in order to be considered
	 * for allocation. The referees with a 'Senior' qualification may referee
	 * both Senior and Junior matches.
	 * 
	 * 3) The method returns a deeply copied array of allocation candidates 
	 * sorted in descending order. The array is deeply copied only on the level
	 * of array elements. The copies of these elements however will still have
	 * references to the descendants of the original object. Therefore if the 
	 * qualification of an allocated referee changes, this will be reflected
	 * on the list of all available referees as well as the allocated referee 
	 * which was copied from the original array before allocation. The 
	 * specification does not explicitly require the allocated referees to 
	 * show their qualification at the time of allocation after it has been 
	 * changed post-allocation therefore no attempt was made to make the copy 
	 * of the array along with the element descendants.
	 * 
	 * TODO: Consider moving this function elsewhere and perhaps making it 
	 * static to decrease the size of the object instance.  
	 * 
	 * @param registered referees
	 */
	public void setAllocatedReferees(Referee[] registeredReferees) {
		Referee[] referees = new Referee[registeredReferees.length];
		int current = 0;

		/* --------------------------------------------------------------------
		 * The referees should suitably qualified and willing to travel to the 
		 * match area in order to be considered for allocation. The preference 
		 * is given with respect to area and the least number of allocations
		 */
		
		for (int i = 0; i < registeredReferees.length; i++) {
			// If a referee is willing to travel to the match area
			if (registeredReferees[i].getTravelAreas().travelsTo(this.getArea())) {
				//If the match in 'Senior' category				
				if (this.getCategory().equals(Match.SENIOR)) {
					//assign only 'Senior' referees
					if (registeredReferees[i].getQualification().getCategory().equals(this.getCategory())) {
						referees[current] = registeredReferees[i];
						current++;
					} 
				// else assign all referees
				} else {
					referees[current] = registeredReferees[i];
					current++;
				}
			}
		}

		// Copy the result array to ensure there are no nulls in the result set
		if (current != 0) {
			Referee[] qualified = new Referee[current];
			System.arraycopy(referees, 0, qualified, 0, qualified.length);
			referees = qualified;
			current = 0;
		} else {
			return;
		}

		// Sort the qualified referees by match area descending
		for (int i = 0; i < referees.length - 1; i++) {
			current = i;
			// iterate the tail moving left from right
			for (int next = i + 1; next < referees.length; next++) {
				// if the next element matches match area swap positions
				if (referees[next].getHomeArea().toString()
						.equals(this.getArea().toString())) {
					Referee matchArea = referees[current];
					referees[current] = referees[next];
					referees[next] = matchArea;
					break;
				}
			}
		}

		// Sort the qualified referees within area by number of allocations
		for (int i = 0; i < referees.length - 1; i++) {
			current = i;
			// iterate the tail moving left from right
			for (int next = i + 1; next < referees.length; next++) {
				// make sure there is no action for the next new area
				if (referees[next].getHomeArea().toString()
						.equals(referees[current].getHomeArea().toString())) {
					if (referees[next].getAllocations() > referees[current]
							.getAllocations()) {
						current = next;
					} 
					// temporarily save the variable
					Referee matchArea = referees[current];
					// swap
					referees[current] = referees[next];
					referees[next] = matchArea;
				}
			}
		}
	
		/* --------------------------------------------------------------------
		 * After that, referees are considered who live in adjacent areas and 
		 * are prepared to travel to the stadium area and have the least number 
		 * of allocations compared to other referees in this category.
		 */
		
		for (int i = 0; i < referees.length - 1; i++) {
			current = i;
			for (int next = i + 1; next < referees.length; next++) {
				// if does not live in adjacent area move down
				if (!(this.getArea().isAdjacent(referees[current].getHomeArea())
						&& referees[current].getTravelAreas().travelsTo(this.getArea()))) {
					// move down
					Referee matchArea = referees[current];
					referees[current] = referees[next];
					referees[next] = matchArea;
				// if is not willing to travel move down
				} else if (!referees[current].getTravelAreas().travelsTo(this.getArea())) {
					// move down
					Referee matchArea = referees[current];
					referees[current] = referees[next];
					referees[next] = matchArea;
				
				// if the current referee home area is not the match area
				} else if (!this.getArea().toString().equals(referees[current].getHomeArea().toString())) {
					
					// if the next referee lives also lives in adjacent area and is willing to travel 
					if (this.getArea().isAdjacent(referees[next].getHomeArea())
							&& referees[next].getTravelAreas().travelsTo(this.getArea())) {
						// then compare the number of allocations
						if (referees[next].getAllocations() < referees[current]
								.getAllocations()) {
							// swap the referees
							Referee matchArea = referees[current];
							referees[current] = referees[next];
							referees[next] = matchArea;
						} 	
					}
				}
			}
		}
		
		/* --------------------------------------------------------------------
		 * Finally the referees who live in non-adjacent area but who are 
		 * willing to travel to the destination area and have the least number 
		 * of allocations compared to other referees in this category are 
		 * considered.
		 */
		
		for (int i = 0; i < referees.length - 1; i++) {
			current = i;
			for (int next = i + 1; next < referees.length; next++) {
				// ignore referees from the match area
				if (!referees[current].getHomeArea().equals(this.getArea().toString())) {
					// The central area is adjacent to both areas
					if (!(this.getArea() instanceof Central)) {
						/*
						 * if both current and the next referee live in the same
						 * area which is not adjacent to the match area and is 
						 * not the match area
						 */
						if (referees[current].getHomeArea().toString()
								.equals(referees[next].getHomeArea().toString())
								&& !referees[current].getHomeArea()
								.isAdjacent(this.getArea())) {
							//if both are prepared to travel to the match area 
							if (referees[current].getTravelAreas().travelsTo(this.getArea())
									&& referees[next].getTravelAreas()
									.travelsTo(this.getArea())) {
								
								// then compare the number of allocations
								if (referees[next].getAllocations() < referees[current]
										.getAllocations()) {
									// swap the referees
									Referee matchArea = referees[current];
									referees[current] = referees[next];
									referees[next] = matchArea;
								} 	
							}
						}
					} 
				}
			}
		}
		allocatedReferees = Arrays.asList(cloneReferees(referees));
	}
	
	/**
	 * A convenience method used by setAllocatedReferees method to deep copy
	 * Referee array elements. The copy is done only on the level of array
	 * elements therefore the references to the descendant object will be
	 * preserved in the copied array.
	 * 
	 * @param allocated
	 * @return a deep copy of the parameter array
	 */
	public Referee[] cloneReferees(Referee[] allocated) {
		Referee[] suitableReferees = new Referee[allocated.length];
		for (int i=0;i<allocated.length;i++) {
			suitableReferees[i] = new Referee(allocated[i]);
		}
		return suitableReferees;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((allocatedReferees == null) ? 0 : allocatedReferees
						.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + week;
		return result;
	}
	
	/**
	 * Method to check the state of an object being passed in.
	 * @param obj
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)  //if the current object is equal to one being passed in return true
			return true;
		if (obj == null)  //if it is null return false
			return false;
		if (getClass() != obj.getClass())  //perform a check to ensure they have the same Match class
			return false;
		Match other = (Match) obj;
		if (allocatedReferees == null) {
			if (other.allocatedReferees != null)
				return false;
		} else if (!allocatedReferees.equals(other.allocatedReferees))
			return false;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (week != other.week)
			return false;
		return true;
	}
}
