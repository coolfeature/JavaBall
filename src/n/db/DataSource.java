//this class belongs to the n.db package
package n.db;

//import the following for use within this class
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import n.models.Area;
import n.models.TravelAreas;
import n.models.Central;
import n.models.North;
import n.models.Qualification;
import n.models.Referee;
import n.models.South;

//this class is responsible for storing the Referee objects within the Referee array called 'referees'
public class DataSource {

	//the input file name and size of  array have been declared as final as these should not change
	//throughout the program
	public static final String INPUT_FILE_NAME = "RefereesIn.txt";
	public static final short MAX_REFEREES = 12; 		//max number of referee objects
	private Referee[] referees = null;					//Referee objects array is instantiated to null

	/*the constructor for DataSource, when the instance of this class is created, the code within
	  this is executed first */

	public DataSource() {

		//the readIn() is called upon to initialise the referees array
		this.referees = readIn(); 		
	}

	//the below is accessor (get) method to allow external classes/packages to access the data, 
	//when called upon this returns the referees array
	public Referee[] getReferees() {
		return referees;
	}

	//mutator method to change the state of the referees object array when called upon, a Referees
	//array object is passed as a parameter
	public void setReferees(Referee[] referees) {
		this.referees = referees;
	}

	//the below public method is called upon from the gui which removes a Referee object when
	//specified by the user, a Referee object is passed in as a parameter
	public boolean removeReferee(Referee referee) {

		//Create a List data structure that copies the content of the referee array data structure as it is
		List<Referee> refereeList = new ArrayList<Referee>(Arrays.asList(referees));
		boolean found = false;	
		int position = 0;

		/* loop through the referees array, and when the id of the current object is equal to that 
		 * of the referee object that has been passed in as a parameter then change the state of boolean
		 * variable 'found' to true, set the value of integer position to the current position(i) of the 
		 * array, following this break out of the loop */

		for(int i=0;i<referees.length;i++) {
			if (referees[i].getId().equals(referee.getId())) {
				found = true;
				position = i;
				break;
			}
		}

		//if the value of found is true then execute the following code
		if (found) {
			//remove the Referee object from the array list at the assigned position(i)
			refereeList.remove(position);
			// then initialise the referees array to the array list after changing this data structure 
			// to an array
			referees = refereeList.toArray(new Referee[refereeList.size()]);
		} 

		// return the state of found and return back to the part of the program from where this method was called
		return found;  

	}

	//this is method is called from the gui when the user wishes to update the details of a referee object	
	public boolean updateReferee(Referee referee) {

		//call on the search method of this class and if the existing referee object is not null then..
		if (search(referee) != null) {

			//call on the removeReferee method within this class and pass the referee object as a parameter
			removeReferee(referee);

			//then call on the addReferee method of this class to add a new object to the array
			return addReferee(referee);
		} 
		//if the referee object being passed in does not exist then return false
		else {
			return false;
		}
	}

	//public boolean method that allows a new referee object to be added to the array when requsted by the user
	public boolean addReferee(Referee referee) {

		//before trying to add any more objects to the existing referees array, check if the number
		// of referee objects in the array is less than the max number (12).
		if (referees.length < DataSource.MAX_REFEREES) {

			//perform a check to ensure the referee does not already exist by calling on the search method
			if (search(referee) == null) {

				//if the referee object does not already exist  then create a local referee arrayList 
				//using the contents of the current referees object array
				List<Referee> refereeList = new ArrayList<Referee>(Arrays.asList(referees));

				//add the new referee object (being passed into this method) to the array list
				refereeList.add(referee);
				//update the referees object array by assigning it to the new arraylist with the newly added object
				referees = refereeList.toArray(new Referee[refereeList.size()]);
				//then sort Referee objects in the referees array
				Arrays.sort(referees);

				return true;	//once the new referee object has been added to the array, return boolean value true
			} else {
				return false;	//otherwise if the referee object already exists return false 
			}	
		} else {
			return false;		//if the current number of referee objects in array exceeds 12, return false
		}
	}


	// public method that allows a search to be performed using the parameters, firstName and lastName of the referee
	public Referee search(String firstName, String lastName) {

		//create a local referee variable and assign it to null
		Referee referee = null;

		//loop through the referees array and check each referee object
		for (Referee ref : referees) {

			//if the first name AND last name of the current referee object are equal to string 
			//parameters that have been passed in then assign the local referee object to the current
			//ref object in the array
			if (ref.getFirstName().equals(firstName)
					&& ref.getLastName().equals(lastName)) {
				referee = ref;

				break;		//then break out the loop otherwise continue, until you have looped through the entire array
			}
		}
		return referee;		//return the local referee object array
	}


	//public method that allows the search of referee object (being passed as a parameter) within the referees array 
	public Referee search(Referee referee) {

		//create a local Referee object called 'exists' and assign it as null
		Referee exists = null;

		//loop through the referees array checking checking each Referee object 
		for (Referee ref : referees) {
			// perform the check by calling on the idMatch method within the Referee class
			if (referee.idMatch(ref)) {
				//if there is an id match, then assign the local Referee object to the current ref object within the array
				exists = ref;
				//if match is found at current referee object, break out the array
				break;
			}
		}
		//return the Referee object 'exists'
		return exists;
	}

	
	
	//HELP
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

	
	
	//helper method, only accessible within this class that allows the Referee list to be populated using
	//the contents of the input file 'RefereesIn.txt'
	private Referee[] readIn() {

		//create a local Referee object list and call on the readIn method to input the contents of the file and store it in the list
		List<Referee> refereeList = readIn(DataSource.INPUT_FILE_NAME);

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

	
	/*
	 * Reads in filename as specified in INPUT_FILE_NAME and
	 * return an ArrayList of Referee object or null
	 */
	public List<Referee> readIn(String filename) {

		// a local 'referees' object list is created to store Referee objects
		List<Referee> referees = new ArrayList<Referee>();

		try { // all code is within a try block to catch any FileNotFound exceptions

			//instantiate FileReader and Scanner objects and set them to null
			FileReader fr = null;
			Scanner s = null;

			try { // another try block to catch any file input exceptions

				fr = new FileReader(filename); 	//pass the input 'filename as a parameter into the FileReader object
				s = new Scanner(fr);	//pass the FileReader object as a parameter to the Scanner object
				String[] line = null;	//create a String array 'line' for each line that is input from the text file - filename

				//keep looping through the contents of the file as long as it has a next line
				while (s.hasNextLine()) {

					// populate the contents of the array by tokenizing each line into separate words
					line = s.nextLine().split("[\\s]+");
					/*
					 *  .trim() on other than the first and the last token 
					 *  would be redundant as .split() removes the 
					 *  whitespaces between tokens
					 */
					String id = line[0].trim(); 	//assign variable id as the first position of the line array
					String firstName = line[1];		//assign variable firstName as the second position of the line array
					String lastName = line[2];		//assign variable lastName as the third position of the line array

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

							// assign variable to travels, north, central and south based on the contents read from the file
							// and stored in the line array
							String travels = line[6].trim();
							North north = travels.substring(0,1).equals("Y") ? new North(true) : new North();
							Central central = travels.substring(1,2).equals("Y") ? new Central(true) : new Central() ;
							South south = travels.substring(2,3).equals("Y") ? new South(true) : new South() ; 
							
							//instantiate a TravelAreas object and pass in the areas read in from the file as parameters
							TravelAreas travelAreas = new TravelAreas(north,central,south);

							//create a new Referee object passing as parameters all the variables created above which were 
							//read in from the file
							Referee referee = new Referee(id,firstName,lastName
									,qualification,allocations,homeArea,travelAreas);
							//add the new Referee (referee) object to the referees list
							referees.add(referee);
				}
			} finally { 	//if the files were opened successfully then close them
				if (fr != null) { fr.close(); };
				if (s != null) { s.close(); };
			}
		} catch (FileNotFoundException e) { //if the files were not found then catch this exception and print an error
			e.printStackTrace();
		} catch (IOException e) {	//if there was an error reading the contents from the file then print an error
			e.printStackTrace();
		}
		return referees;	//return the referees list
	}

	
	
	/*public method which can be called on when the programmer wishes to print out the content of referee objects
	 * array, using the toString this can convert it into a meaningul String reference for the user to see */
	public void printReferees() {
		for (Referee referee : referees) {
			System.out.println(referee.toString());
		}
	}

	

	/*public method which when called on, returns the reference id (referee first name and last name for a referee 
	 * object (passed in as a parameter) */
	public String getRefereeId(Referee referee) {
		return getRefereeId(referee.getFirstName(),referee.getLastName());
	}
	
	

	/*public method which when called on, returns the Referee's id using his first name and last name which
	 * are passed in as parameters */
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
