package n.models;

import java.util.List;

import n.db.DataSource;

public class Match {

	public static final String JUNIOR = "Junior";
	public static final String SENIOR = "Senior";
	public static final String[] CATEGORIES = {JUNIOR,SENIOR};
	
	short week;
	Area area;
	String category;
	List<Referee> allocatedReferees;
	DataSource dataSource;
	
	public Match(short week,Area area,String category,DataSource dataSource) {
		this.week = week;
		this.area = area;
		this.category = category;
		this.allocatedReferees = dataSource.getMatchCandidates(area,category); 
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
	

	public void allocateReferees(Referee[] registeredReferees) {
		Referee[] selected = new Referee[registeredReferees.length];
		short index = 0;
		// Pre-select referees with suitable qualifications
		for (int i=0;i<registeredReferees.length;i++) {
			if (registeredReferees[i].getQualification().getCategory().equals(this.getCategory())) {
				selected[index] = registeredReferees[i];
				index++;
			}
		}
		if (index != 0) {
			Referee[] qualified = new Referee[index+1];
			System.arraycopy(selected, 0, qualified, 0, qualified.length);
			selected = qualified;
			index = 0;
			// Sort the qualified referees by area
			for (int i=0;i<selected.length;i++) {
				//iterate to find a non match
				if (this.getArea().isAdjacent(selected[i].getHomeArea())) {
					//take the 
				}
			}
			
		} else {
			selected = null;
		}
	}


	@Override
	public String toString() {
		return "Match [week=" + week + ", area=" + area + ", category="
				+ category + ", allocatedReferees=" + allocatedReferees
				+ ", dataSource=" + dataSource + "]";
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
