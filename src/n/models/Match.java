package n.models;

import java.util.List;

public class Match {

	public static final String JUNIOR = "Junior";
	public static final String SENIOR = "Senior";
	public static final String[] CATEGORIES = {JUNIOR,SENIOR};
	
	short week;
	Area area;
	String category;
	List<Referee> allocatedReferees;
	
	public Match(short week,Area area,String category) {
		this.week = week;
		this.area = area;
		this.category = category;
	}

	public short getWeek() {
		return week;
	}

	public void setWeek(short week) {
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
	
	/*
	 * The referees should suitably qualified in order to be considered for 
	 * allocation then the preference is given with respect to 
	 * 1) area
	 * 2) the least number of allocations
	 * 
	 * After that, referees are considered who live in adjacent areas and
	 * are prepared to travel to the stadium area and have the least number
	 * of allocations compared to other referees in this category.
	 * 
	 * Finally the referees who live in non-adjacent area but who are willing
	 * to travel to the destination area and have the least number of 
	 * allocations compared to other referees in this category are considered.
	 */
	public Referee[] allocateReferees(Referee[] registeredReferees) {
		Referee[] selected = new Referee[registeredReferees.length];
		int index = 0;
		// Pre-select referees with suitable qualifications
		for (int i=0;i<registeredReferees.length;i++) {
			if (registeredReferees[i].getQualification().getCategory().equals(this.getCategory())) {
				selected[index] = registeredReferees[i];
				index++;
			}
		}
		
		if (index != 0) {
			Referee[] qualified = new Referee[index];
			System.arraycopy(selected, 0, qualified, 0, qualified.length);
			selected = qualified;
			index = 0;
		} else {
			return null;
		}
		
		// Sort the qualified referees by area
		for (int i=0;i<selected.length-1;i++) {
            index = i;
            // iterate the tail moving left from right 
            for (int next = i + 1; next < selected.length; next++) {
            	// if the next element matches match area swap positions 
                if (!selected[next].getHomeArea().toString()
                		.equals(this.getArea().toString())) {
                	index = next;
                }
                // temporarily save the variable
                Referee matchArea = selected[index]; 
                // swap
                selected[index] = selected[next];
                selected[next] = matchArea;                 
            }
		}
		
		// Sort the qualified referees within area by number of allocations
		// Split by area
		int[] areaReferees = new int[TravelAreas.AREAS.length];
		for (int a=0;a<TravelAreas.AREAS.length;a++) {
			int count = 0;
			for (int i=0;i<selected.length;i++) {
				if (selected[i].getHomeArea().getClass()
						.isAssignableFrom(TravelAreas.AREAS[a].getClass())) {
					count++;
				}
			}
			areaReferees[a] = count; 
		}
		
		// 
		for (int i=0;i<selected.length;i++) {
		}
		return selected;
	}


	@Override
	public String toString() {
		return "Match [week=" + week + ", area=" + area + ", category="
			+ category + ", allocatedReferees=" + allocatedReferees
			+ "]";
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
