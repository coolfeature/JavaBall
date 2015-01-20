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

	/** Name of text file for inputing referee data  */
	public static final String INPUT_REFEREE_FILE = "RefereesIn.txt";
	/** Name of text file for outputting referee data  */
	public static final String OUTPUT_REFEREE_FILE = "RefereesOut.txt";
	/** Name of text file for outputting match allocations  */
	public static final String OUTPUT_MATCH_FILE = "MatchAllocs.txt";
	/** Maximum number of referee objects  */
	public static final short MAX_REFEREES = 12;
	/** Referee objects array is instantiated to null  */
	private Referee[] referees = null;
	/** Match objects array is instantiated to null  */
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
		//Create a List data structure that copies the content of the match array
		List<Match> matchesList = new ArrayList<Match>(Arrays.asList(matches));
		boolean found = false;
		int position = 0;
		//Iterate through the matches array
		for(int i=0;i<matches.length;i++) {
			if (matches[i].getWeek() == match.getWeek()) {
				found = true;
				position = i;
				break;
			}
		}
		if (found) {
			//Removes the match object from the array list
			matchesList.remove(position);
			//Initialises the matches array to the array list after removing referee
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
		//create a local match variable and assign it to null
		Match exists = null;
		if (matches==null) {
			return exists; 
		}
		//iterate over matches array and check if a match exists
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
		//Create a List data structure that copies the content of the referee array
		List<Referee> refereeList = new ArrayList<Referee>(Arrays.asList(referees));
		boolean found = false;
		int position = 0;
		//Iterate through the referees array
		for(int i=0;i<referees.length;i++) {
			if (referees[i].getId().equals(referee.getId())) {
				found = true;
				position = i;
				break;
			}
		}
		if (found) {
			//Removes referee object from the array list
			refereeList.remove(position);
			//Initialises the referees array to the array list after removing referee
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
		// calls the search method of this class to test if referee object is not null
		if (search(referee) != null) {
			//call on the remove method within this class to remove a referee
			remove(referee);
			// call on the add method of this class to add a new referee object to the array
			return add(referee);
		//if the referee object being passed in does not exist then return false		
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
		// checks if the number of referee objects in the array is less than the max number (12).
		if (referees.length < DataSource.MAX_REFEREES) {
			// check to ensure the referee does not already exist by calling the search method
			if (search(referee) == null) {
				//create a local referee arrayList using the contents of the current referees object array
				List<Referee> refereeList = new ArrayList<Referee>(Arrays.asList(referees));
				//add the new referee object (being passed into this method) to the array list
				refereeList.add(referee);
				//update the referees object array by assigning it to the new arraylist with the newly added object
				referees = refereeList.toArray(new Referee[refereeList.size()]);
				//sort Referee objects in the referees array
				Arrays.sort(referees);
				return true;   // returns true once the new referee object has been added to the array
			} else {
				return false;   // returns false if the referee object already exists
			}	
		} else {
			return false;  //returns false if the number of referee objects in array exceeds 12
		}
	}
	
	/**
	 * Searches the referees array.
	 * @param firstName
	 * @param lastName
	 * @return a referee if found or a null
	 */
	public Referee search(String firstName, String lastName) {
		//create a local referee variable and assign it to null
		Referee referee = null;
		//loop through the referees array and check each referee object against the first name and last name parameter
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
		//loop through the referees array checking each Referee object
		for (Referee ref : referees) {
			//if there is an id match, then assign the local Referee object to the current ref object within the array
			if (referee.idMatch(ref)) {
				exists = ref;
				//if match is found at current referee object, break out the array
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
		//create a local Referee object list and call on the readIn method to input the contents of the file and store it in the list
		List<Referee> refereeList = readIn(DataSource.INPUT_REFEREE_FILE);
		//check to ensure the local refereeList has been populated using the contents of the file
		if (refereeList != null) {
			
			//instantiate a new local Referee objects array and initialise this to the referee list(by converting this to an array)
			// and setting this to the size of the populated referee list size
			Referee[] referees = refereeList.toArray(new Referee[refereeList.size()]);
			
			//call the sort method on the referees array
			Arrays.sort(referees);
			
			//return the referees array contain Referee objects
			return referees;	
		} else {
			//otherwise if the refereeList has not been populated then return null
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
		// a local 'referees' object list is created to store Referee objects
		List<Referee> referees = new ArrayList<Referee>();
		try {
			//instantiate FileReader and Scanner objects and set them to null
			FileReader fr = null;
			Scanner s = null;
			try {
				fr = new FileReader(filename);
				s = new Scanner(fr);
				//create a String array 'line' for each line that is input from the text file - filename
				String[] line = null;
				
				// loop through the contents of the file as long as it has a next line
				while (s.hasNextLine()) {
					// populate the contents of the array by tokenizing each line into separate words
					line = s.nextLine().split("[\\s]+");
					/*
					 *  .trim() on other than the first and the last token 
					 *  would be redundant as .split() removes the 
					 *  white spaces between tokens
					 */
					String id = line[0].trim();  //assign variable id as the first position of the line array
					String firstName = line[1];  //assign variable firstName as the second position of the line array
					String lastName = line[2];   //assign variable lastName as the third position of the line array
					
					/*instantiate a Qualification object and pass in the fourth position of the line array as a parameter
					 * which would be the qualification parameter */
					Qualification qualification = new Qualification(line[3]);
					
					// parse the fifth position of the line array into an integer and assign it to the variable allocations
					int allocations = Integer.parseInt(line[4]);
					
					/* instantiate an Area object and perform a check to see whether the area is North, Central and South,
					 * then create a new object accordingly based on the check */
					Area homeArea = line[5].equals("North") ? new North(true) : 
						line[5].equals("Central") ? new Central(true) :
							line[5].equals("South") ? new South(true) : null;
					
					/* assign variable to travels, north, central and south based on the contents read from the file
					 *  and stored in the line array */
					String travels = line[6].trim();
					North north = travels.substring(0,1).equals("Y") ? new North(true) : new North();
					Central central = travels.substring(1,2).equals("Y") ? new Central(true) : new Central() ;
					South south = travels.substring(2,3).equals("Y") ? new South(true) : new South() ; 
					
					//instantiate a TravelAreas object and pass in the areas read in from the file as parameters
					TravelAreas travelAreas = new TravelAreas(north,central,south);
										
					/* create a new Referee object passing as parameters all the variables created above which were
					 *  read in from the file */
					Referee referee = new Referee(id,firstName,lastName
							,qualification,allocations,homeArea,travelAreas);
					//add the new Referee (referee) object to the referees list
					referees.add(referee);
				}
			} finally {    //if the files were opened successfully then close them
				if (fr != null) { fr.close(); };
				if (s != null) { s.close(); };
			}
		} catch (FileNotFoundException e) {   //if the files were not found then catch this exception and print an error
			e.printStackTrace();
		} catch (IOException e) {   //if there was an error reading the contents from the file then print an error
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
			// create file writer object
			FileWriter fw = null;
			try {
				// instantiate file writer object
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
