package n.models;

public class Match {

	short week;
	String area;
	String category;
	Referee[] allocatedReferees;
	
	public Match(short week,String area,String category) {
		this.week = week;
		this.area = area;
		this.category = category;
		this.allocatedReferees = null; 
	}

	public short getWeek() {
		return week;
	}

	public void setWeek(short week) {
		this.week = week;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
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
	public void allocateReferees(Referee[] registeredReferees) {
		Referee[] result = new Referee[registeredReferees.length];
		short resultIndex = 0;
		// Pre-select referees with suitable qualifications
		for (int i=0;i<registeredReferees.length;i++) {
			if (registeredReferees[i].getQualification().getCategory().equals(this.getCategory())) {
				result[resultIndex] = registeredReferees[i];
				resultIndex++;
			}
		}
		// Sort the pre-selected referees if any by the number of allocations
		if (resultIndex != 0) {
			Referee[] preAllocated = new Referee[resultIndex + 1];
			for (int i=0;i<result.length;i++) {
				if (result[i] != null) {
					
				}
			}
			
		}
	}

	@Override
	public String toString() {
		return "Match [week=" + week + ", area=" + area + ", level=" + category
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
