package n.db;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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

/**
 * This class is responsible for storing the Referee objects within the Referee
 * array called 'referees' and Match objects in an array 'matches'. All other 
 * objects manipulating data in these array have a reference to this class.
 */
public class DataSource {

	public static final String INPUT_REFEREE_FILE = "RefereesIn.txt";
	public static final String OUTPUT_REFEREE_FILE = "RefereesOut.txt";
	public static final String OUTPUT_MATCH_FILE = "MatchAllocs.txt";
	public static final short MAX_REFEREES = 12;
	private Referee[] referees = null;
	private Match[] matches = null;
	
	/**
	 * The constructor reads a list of referees in from the INPUT_REFEREE_FILE
	 * file and populates the referees array. 
	 */
	public DataSource() {
		this.referees = readIn();
	}
	
	/**
	 * Getter for matches
	 * @return matches
	 */
	public Match[] getMatches() {
		return matches;
	}

	/**
	 * Setter for matches
	 * @param matches
	 */
	public void setMatches(Match[] matches) {
		this.matches = matches;
	}

	/**
	 * Adds a match to the matches array.
	 * @param match
	 * @return whether add has been successful
	 */
	public boolean add(Match match) {
		if (search(match) == null) {
			List<Match> matchList = null;
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

	/**
	 * Removes a match from the matches array.
	 * @param match
	 * @return whether successful
	 */
	public boolean remove(Match match) {
		List<Match> matchesList = new ArrayList<Match>(Arrays.asList(matches));
		boolean found = false;
		int position = 0;
		for(int i=0;i<matches.length;i++) {
			if (matches[i].getWeek() == match.getWeek()) {
				found = true;
				position = i;
				break;
			}
		}
		if (found) {
			matchesList.remove(position);
			matches = matchesList.toArray(new Match[matchesList.size()]);
		} 
		return found;
	}

	/**
	 * Searches the matches array.
	 * @param match
	 * @return match or null
	 */
	public Match search(Match match) {
		Match exists = null;
		if (matches==null) {
			return exists; 
		}
		for (Match m : matches) {
			if (m.getWeek() == match.getWeek()) {
				return m;
			}
		}
		return exists;
	}
	
	
	/**
	 * Getter for referees array.
	 * @return
	 */
	public Referee[] getReferees() {
		return referees;
	}

	/**
	 * Setter for referees array.
	 * @param referees
	 */
	public void setReferees(Referee[] referees) {
		this.referees = referees;
	}
	
	/**
	 * Removes a referee from the referees array.
	 * @param referee
	 * @return whether successful
	 */
	public boolean remove(Referee referee) {
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

	/**
	 * Replaces an existing referees object with the parameter object.
	 * @param referee
	 * @return if successful
	 */
	public boolean update(Referee referee) {
		if (search(referee) != null) {
			remove(referee);
			return add(referee);
		} else {
			return false;
		}
	}
	
	/**
	 * Adds a new referee to the referees array if the array size does not
	 * exceed MAX_REFEREES.
	 * @param referee
	 * @return whether successful
	 */
	public boolean add(Referee referee) {
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
	
	/**
	 * Searches the referees array.
	 * @param firstName
	 * @param lastName
	 * @return a referee if found or a null
	 */
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

	/**
	 * Searches referees array using object id.
	 * @param referee
	 * @return a referee if found or null.
	 */
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

	/**
	 * Returns a 2D array that is used with the table object in n.gui.Referees 
	 * class.
	 * @return the referees array in 2D or null if referees is empty.
	 */
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
	
	/**
	 * A convenience method to return a referee id which uses overloading to
	 * delegate a part of the job to another method.
	 * @param referee
	 * @return referee id
	 */
	public String getRefereeId(Referee referee) {
		return getRefereeId(referee.getFirstName(),referee.getLastName());
	}
	
	/**
	 * A convenience method that returns a unique referee id.
	 * @param firstName
	 * @param lastName
	 * @return referee id
	 */
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
	
	/**
	 * Increases allocation count for the selected employee..
	 * @param selected
	 */
	public void increaseRefereeAllocation (Referee[] selected) {
		if (selected != null) {
			for (int r=0;r<selected.length;r++) {
				for (int i=0;i<referees.length;i++) {
					if (referees[i].idMatch(selected[r])) {
						referees[i].increaseAllocations();
					}
				} 
			}			
		} 	
	}
	
	/**
	 * Decreases allocation count for the selected employee..
	 * @param selected
	 */
	public void decreaseRefereeAllocation (Referee[] selected) {
		if (selected != null) {
			for (int r=0;r<selected.length;r++) {
				for (int i=0;i<referees.length;i++) {
					if (referees[i].idMatch(selected[r])) {
						referees[i].decreaseAllocations();
					}
				} 
			}			
		} 	
	}	
	
	/**
	 * This method reads in the INPUT_REFEREE_FILE and turns the results into
	 * an array.
	 * @return referees
	 */
	private Referee[] readIn() {
		List<Referee> refereeList = readIn(DataSource.INPUT_REFEREE_FILE);
		if (refereeList != null) {
			Referee[] referees = refereeList.toArray(new Referee[refereeList.size()]);
			Arrays.sort(referees);
			return referees;	
		} else {
			return null;
		}
	}
	
	/**
	 * Reads in filename as specified in INPUT_FILE_NAME and
	 * return an ArrayList of Referee object or null
	 * @param filename
	 * @return referees as ArrayList
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
					 *  white spaces between tokens
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

	/**
	 * Outputs sorted referees array to OUTPUT_REFEREE_FILE.
	 */
	public void writeReferees() {
		if (referees == null) {
			return;
		}
		Arrays.sort(referees);
		try {
			FileWriter fw = null;
			try {
				fw = new FileWriter(DataSource.OUTPUT_REFEREE_FILE);
				String header = String.format(Referee.FILE_RECORD_FORMAT, 
						Referee.FIELD_ID,Referee.FIELD_FIRST_NAME
						,Referee.FIELD_LAST_NAME,Referee.FIELD_QUALIFICATION
						,Referee.FIELD_ALLOCATIONS,Referee.FIELD_HOME_AREA
						,Referee.FIELD_TRAVEL_AREA);
				fw.write(header + System.lineSeparator());
				for (Referee referee : referees) {
					fw.write(referee
							.printRefereeRecord(Referee.FILE_RECORD_FORMAT) 
							+ System.lineSeparator());
				}
			} finally {
				if (fw != null)
					fw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Outputs the match report to OUTPUT_MATCH_FILE.
	 */
	public void writeReport() {
		if (matches == null) {
			return;
		}
		try {
			FileWriter fw = null;
			try {
				fw = new FileWriter(DataSource.OUTPUT_MATCH_FILE);
				String header = String.format(Match.REPORT_DISPLAY_FORMAT, 
						Match.FIELD_WEEK,Match.FIELD_GROUP,Match.FIELD_AREA,
						Match.FIELD_REFEREE1,Match.FIELD_REFEREE2);
				fw.write(header + System.lineSeparator());
				for (Match match : matches) {
					fw.write(match.toReportString() + System.lineSeparator());
				}
			} finally {
				if (fw != null)
					fw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A convenience method for printing referees array to the console.
	 */
	public void printReferees() {
		if (referees != null) {
			for (Referee r : referees) {
				System.out.println(r.toString());
			}	
		} else {
			System.out.println("No Referees");
		}
	}
}
