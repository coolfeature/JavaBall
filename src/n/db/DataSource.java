package n.db;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import n.models.Area;
import n.models.Match;
import n.models.TravelAreas;
import n.models.Central;
import n.models.North;
import n.models.Qualification;
import n.models.Referee;
import n.models.South;

public class DataSource {
	
	public static final String INPUT_FILE_NAME = "RefereesIn.txt";
	public static final short MAX_REFEREES = 12;
	private Referee[] referees = null;
	private Match[] matches = null;
	
	public DataSource() {
		this.referees = readIn();
	}
	
	public Match[] getMatches() {
		return matches;
	}

	public void setMatches(Match[] matches) {
		this.matches = matches;
	}

	public Referee[] getReferees() {
		return referees;
	}

	public void setReferees(Referee[] referees) {
		this.referees = referees;
	}
	
	public boolean removeReferee(Referee referee) {
		List<Referee> refereeList = new ArrayList<Referee>(Arrays.asList(referees));
		boolean found = false;
		int position = 0;
		for(int i=0;i<referees.length;i++) {
			if (referees[i].getId().equals(referee.getId())) {
				found = true;
				position = i;
				break;
			}
		}
		if (found) {
			refereeList.remove(position);
			referees = refereeList.toArray(new Referee[refereeList.size()]);
		} 
		return found;
	}

	public boolean updateReferee(Referee referee) {
		if (search(referee) != null) {
			removeReferee(referee);
			return addReferee(referee);
		} else {
			return false;
		}
	}
	
	public boolean addReferee(Referee referee) {
		if (referees.length < DataSource.MAX_REFEREES) {
			if (search(referee) == null) {
				List<Referee> refereeList = new ArrayList<Referee>(Arrays.asList(referees));
				refereeList.add(referee);
				referees = refereeList.toArray(new Referee[refereeList.size()]);
				Arrays.sort(referees);
				return true;
			} else {
				return false;
			}	
		} else {
			return false;
		}
	}
	
	public boolean addMatch(Match match) {
		if (search(match) == null) {
			List<Match> matchList = null;
			System.out.println("Adding " + match.toString());
			if (matches==null) {	
				matches = new Match[]{match};
			} else {
				matchList = new ArrayList<Match>(Arrays.asList(matches));	
				matchList.add(match);
				matches = matchList.toArray(new Match[matchList.size()]);
			}
			return true;
		} else {
			return false;
		}
	}
	
	public Referee search(String firstName, String lastName) {
		Referee referee = null;
		for (Referee ref : referees) {
			if (ref.getFirstName().equals(firstName)
					&& ref.getLastName().equals(lastName)) {
				referee = ref;
				break;
			}
		}
		return referee;
	}

	public Match search(Match match) {
		Match exists = null;
		if (matches==null) {
			return exists; 
		}
		for (Match m : getMatches()) {
			if (m.getWeek() == match.getWeek()) {
				return m;
			}
		}
		return exists;
	}
	
	public Referee search(Referee referee) {
		Referee exists = null;
		for (Referee ref : referees) {
			if (referee.idMatch(ref)) {
				exists = ref;
				break;
			}
		}
		return exists;
	}

	public Object[][] getRefereesData() {
		if (referees != null && referees.length > 0) {
			Object[][] refereesData = new Object[referees.length][Referee.FIELD_NAMES.length];	
			for (int i = 0; i < referees.length; i++) {
			    Referee referee = referees[i];
			    refereesData[i][0] = referee.getId();
			    refereesData[i][1] = referee.getFirstName();
			    refereesData[i][2] = referee.getLastName();
			    refereesData[i][3] = referee.getQualification();
			    refereesData[i][4] = referee.getAllocations();
			    refereesData[i][5] = referee.getHomeArea();
			    refereesData[i][6] = referee.getTravelAreas();
			}
			return refereesData;
		} else {
			return null;
		}
	}
	
	private Referee[] readIn() {
		List<Referee> refereeList = readIn(DataSource.INPUT_FILE_NAME);
		if (refereeList != null) {
			Referee[] referees = refereeList.toArray(new Referee[refereeList.size()]);
			Arrays.sort(referees);
			return referees;	
		} else {
			return null;
		}
	}
	
	/*
	 * Reads in filename as specified in INPUT_FILE_NAME and
	 * return an ArrayList of Referee object or null
	 */
	public List<Referee> readIn(String filename) {
		List<Referee> referees = new ArrayList<Referee>();
		try {
			FileReader fr = null;
			Scanner s = null;
			try {
				fr = new FileReader(filename);
				s = new Scanner(fr);
				String[] line = null;
				while (s.hasNextLine()) {
					line = s.nextLine().split("[\\s]+");
					/*
					 *  .trim() on other than the first and the last token 
					 *  would be redundant as .split() removes the 
					 *  whitespaces between tokens
					 */
					String id = line[0].trim();
					String firstName = line[1];
					String lastName = line[2];
					Qualification qualification = new Qualification(line[3]);
					int allocations = Integer.parseInt(line[4]);
					Area homeArea = line[5].equals("North") ? new North(true) : 
						line[5].equals("Central") ? new Central(true) :
							line[5].equals("South") ? new South(true) : null;
					
					String travels = line[6].trim();
					North north = travels.substring(0,1).equals("Y") ? new North(true) : new North();
					Central central = travels.substring(1,2).equals("Y") ? new Central(true) : new Central() ;
					South south = travels.substring(2,3).equals("Y") ? new South(true) : new South() ; 
					TravelAreas travelAreas = new TravelAreas(north,central,south);
					
					Referee referee = new Referee(id,firstName,lastName
							,qualification,allocations,homeArea,travelAreas);
					referees.add(referee);
				}
			} finally {
				if (fr != null) { fr.close(); };
				if (s != null) { s.close(); };
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return referees;
	}
	
	public String getRefereeId(Referee referee) {
		return getRefereeId(referee.getFirstName(),referee.getLastName());
	}
	
	public String getRefereeId(String firstName,String lastName) {
		String initials = firstName.substring(0,1) + lastName.substring(0,1);
		int id = 1;
		for (Referee referee : referees) {
			if (referee.getId().substring(0,2).equals(initials)) {
				int refereeId = Integer.parseInt(referee.getId().substring(2,3));
				if (refereeId >= id) {
					id = refereeId + 1;
				}
			}
		}
		return initials + id;
	}
}
