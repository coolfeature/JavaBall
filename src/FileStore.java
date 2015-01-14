import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileStore {
	
	public static final String INPUT_FILE_NAME = "RefereesIn.txt";
	private Referee[] referees = null;
	
	public FileStore() {
		this.referees = readIn();
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
		if (search(referee) == null) {
			List<Referee> refereeList = new ArrayList<Referee>(Arrays.asList(referees));
			refereeList.add(referee);
			referees = refereeList.toArray(new Referee[refereeList.size()]);
			Arrays.sort(referees);
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
		List<Referee> refereeList = readIn(FileStore.INPUT_FILE_NAME);
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
					String homeArea = line[5];
					TravelAreas travelAreas = new TravelAreas(homeArea,line[6].trim());
					
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
	
	public void printReferees() {
		for (Referee referee : referees) {
			System.out.println(referee.toString());
		}
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
