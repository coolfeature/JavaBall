import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileStore {
	
	public static final String INPUT_FILE_NAME = "RefereesIn.txt";
	private Referee[] referees = null;
	private Object[][] refereesData = null;
	
	public FileStore() {
		this.referees = readIn();
		this.refereesData = readInRefereesData();
	}
	
	public Referee[] getReferees() {
		return referees;
	}

	public void setReferees(Referee[] referees) {
		this.referees = referees;
	}

	
	public Object[][] getRefereesData() {
		return refereesData;
	}

	public void setRefereesData(Object[][] refereesData) {
		this.refereesData = refereesData;
	}

	private Object[][] readInRefereesData() {
		List<Referee> refereeList = readIn(FileStore.INPUT_FILE_NAME);
		if (refereeList != null) {
			Object[][] refereesData = new Object[refereeList.size()][Referee.FIELD_NAMES.length];	
			for (int i = 0; i < refereeList.size(); i++) {
			    Referee referee = refereeList.get(i);
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
			return refereeList.toArray(new Referee[refereeList.size()]);	
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
					String qualification = line[3];
					int allocations = Integer.parseInt(line[4]);
					String homeArea = line[5];
					String travelAreas = line[6].trim();
					
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
}
