package n.models;

import java.util.Arrays;
import java.util.List;

public class Match {

	public static final String JUNIOR = "Junior";
	public static final String SENIOR = "Senior";
	public static final String[] CATEGORIES = { JUNIOR, SENIOR };
	public static final short MIN_MATCHES = 1;
	public static final short MAX_MATCHES = 52;

	int week;
	Area area;
	String category;
	List<Referee> allocatedReferees;

	public Match() {
		this(1, new North(), Match.JUNIOR);
	}
	
	public Match(int week, Area area, String category) {
		this.week = week;
		this.area = area;
		this.category = category;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}



	public List<Referee> getAllocatedReferees() {
		return allocatedReferees;
	}

	public void setAllocatedReferees(List<Referee> allocatedReferees) {
		setAllocatedReferees(allocatedReferees.toArray(new Referee[allocatedReferees.size()]));
	}

	/*
	 * Returns an array of referees sorted in the order of suitability for the 
	 * match. 
	 * 
	 * NOTE: The goal was to implement the method using array algorithms only
	 * avoiding the use of Lists or Comparators. 
	 * 
	 * TODO: Consider moving this function elsewhere and perhaps making it 
	 * static to decrease the size of the object instance.  
	 */
	public void setAllocatedReferees(Referee[] registeredReferees) {
		Referee[] referees = new Referee[registeredReferees.length];
		int current = 0;

		/* --------------------------------------------------------------------
		 * The referees should suitably qualified in order to be considered for
		 * allocation therefore the preference is given with respect to area 
		 * and the least number of allocations
		 */
		
		// Select referees with suitable qualifications
		for (int i = 0; i < registeredReferees.length; i++) {
			if (registeredReferees[i].getQualification().getCategory()
					.equals(this.getCategory())) {
				referees[current] = registeredReferees[i];
				current++;
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
		allocatedReferees = Arrays.asList(referees);
	}

	@Override
	public String toString() {
		return "Match [week=" + week + ", area=" + area + ", category="
				+ category + ", allocatedReferees=" + allocatedReferees + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
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
