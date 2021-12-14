import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// class to load and solve the Mathdoku puzzle
public class Mathdoku {

	Cell[][] puzzle;
	Map<Character, Constraint> groups;
	int choices;
	final private static char equal = '=';
	final private static char multiply = '*';
	final private static char add = '+';
	final private static char subtract = '-';
	final private static char divide = '/';

	// class constructor
	public Mathdoku() {
		this.puzzle = null;
		this.groups = null;
		this.choices = 0;
	}

	// will load the puzzle from a stream
	boolean loadPuzzle(BufferedReader stream) {

		this.puzzle = null;
		this.groups = null;
		this.choices = 0;

		if (stream == null) {
			return false;
		}

		try {

			String line = stream.readLine();

			if (line == null) {
				return false;
			}
			else {
				line = line.trim();
			}

			int size = line.length();

			if(size == 0) {
				return false;
			}

			// create a puzzle of size n*n  where n is equal to the number of characters in a line
			this.puzzle = new Cell[size][size];

			for (int i=0; i< size; i++) {
				for (int j=0; j< size; j++) {
					this.puzzle[i][j] = new Cell(0, line.charAt(j));
				}

				if(i == size-1) {
					break;
				}

				line = stream.readLine();

				// if there are any blank line, check for the next line
				while(line != null && line.trim().isEmpty()) {
					line = stream.readLine();
				}

				if (line==null) {
					this.puzzle = null;
					return false;
				}

				line = line.trim();

				// if the number of characters differ from the first line, then it is a bad input
				if (line.length()!= size) {
					this.puzzle = null;
					return false;
				}

			}

			line = stream.readLine();
			String constraintsInfo = "";

			// store constraints in constraintsInfo string
			while(line != null) {

				// if the line is empty, check for the next line
				if (line.trim().isEmpty()) {
					line = stream.readLine();
					continue;
				}

				constraintsInfo += line.trim()+"\n";
				line = stream.readLine();
			}

			// load Constraints
			loadConstraints(constraintsInfo);

		} catch (IOException e) {
			this.puzzle = null;
			this.groups = null;
			return false;
		}
		return true;
	}

	// check if we can make an attempt to solve the puzzle
	boolean readyToSolve() {

		if (puzzle == null || groups == null) {
			return false;
		}

		// if puzzle is alreay complete
		if(puzzleComplete())
		{
			return true;
		}

		Set<Character> s = new HashSet<>();

		for(int i=0; i< this.puzzle.length; i++) {
			for(int j=0; j< this.puzzle.length; j++) {
				s.add(puzzle[i][j].group);
			}
		}

		// check if all the groups that belong to the puzzle are provided in the constraints
		if (!this.groups.keySet().containsAll(s)) {
			return false;
		}


		for (char c: this.groups.keySet()) {
			// number of cells for a = operator should be one
			if (this.groups.get(c).operator == equal) {
				if (this.groups.get(c).location.size() != 1) {
					return false;
				}
			}
			// number of cells for a - operator should be two
			if (this.groups.get(c).operator == subtract) {
				if (this.groups.get(c).location.size() !=2 ) {
					return false;
				}
			}
			// number of cells for a / operator should be two
			else if (this.groups.get(c).operator == divide) {
				if (this.groups.get(c).location.size() != 2) {
					return false;
				}
			}
		}

		// outcome of any cell should never be zero or less than that
		for (char c: this.groups.keySet()) {
			if (this.groups.get(c).outcome <= 0) {
				return false;
			}
		}

		return true;
	}

	// load contrains into this.groups
	private void loadConstraints(String constraintsInfo) {

		if(!constraintsInfo.isEmpty()) {

			this.groups = new HashMap<Character, Constraint>();
			ArrayList<Character> validOperators = 
					new ArrayList<>(Arrays.asList(multiply, divide, add, subtract, equal)); 

			String[] lines = constraintsInfo.split("\n");

			for (String line: lines) {
				String[] params = line.split("\\s+");

				if(params.length != 3) {
					this.groups = null;
					return;
				}

				// get the group
				char group = params[0].charAt(0);

				int outcome;

				try {
					// get the outcome
					outcome = Integer.valueOf(params[1]);
				}
				catch(NumberFormatException e){
					this.groups = null;
					return;
				}

				// get the operator
				char operator = params[2].charAt(0);

				if (!validOperators.contains(operator)) {
					this.groups = null;
					return;
				}

				ArrayList<Location> al = new ArrayList<>();

				for(int i=0; i < this.puzzle.length; i++) {
					for (int j=0; j< this.puzzle.length; j++) {
						if (this.puzzle[i][j].group == group) {
							// store locations of the group
							al.add(new Location(i, j));
						}
					}
				}

				// store the outcome, operator and locations for each group
				this.groups.put(group , new Constraint(outcome, operator, al));
			}
		}
	}

	// solves the puzzzle
	boolean solve() {

		if (this.puzzle == null || this.groups == null) {
			return false;
		}

		// if puzzle is already solved, return true
		if(puzzleComplete())
		{
			return true;
		}

		// reset choices
		this.choices = 0;

		Set<Character> validGroups = new HashSet<>();

		for(int i=0; i< this.puzzle.length; i++) {
			for(int j=0; j< this.puzzle.length; j++) {
				validGroups.add(puzzle[i][j].group);
			}
		}

		// remove any additional groups which are not part of the puzzle
		if (this.groups.size() != validGroups.size()) {
			this.groups.keySet().retainAll(validGroups);
		}

		Set<Character> equalOperatorGroups = new HashSet<>();

		// insert the values in the group with '=' constraint
		for (char ch: this.groups.keySet()) {
			if(this.groups.get(ch).operator == equal) {
				Location loc = this.groups.get(ch).location.get(0);
				int x = loc.x;
				int y = loc.y;
				int outcome = this.groups.get(ch).outcome;
				// check for row, column collision while inserting values
				if (!checkConflict(x, y, outcome)) {
					resetPuzzle();
					return false;
				}
				else if (outcome > this.puzzle.length) {
					resetPuzzle();
					return false;
				}
				this.puzzle[x][y].value = outcome;
				equalOperatorGroups.add(ch);
			}
		}

		// remove the '=' constraint groups as we have filled those cells noe
		this.groups.keySet().removeAll(equalOperatorGroups);


		ArrayList<ArrayList<Location>> groupPoints = new ArrayList<>();

		// stores the locations of vertices for each group
		for (char ch: this.groups.keySet()) {
			groupPoints.add(new ArrayList<Location>(this.groups.get(ch).location));
		}

		int[] list = new int[this.puzzle.length];

		// create a list of all the possible values that can come in a cell
		for(int i=0; i < this.puzzle.length; i++) {
			list[i] = i+1;
		}

		// solve the puzzle now
		if (groupPoints.size()!=0) {
			solvePuzzle(groupPoints, list, 0);
		}

		// if the puzzle is solved, return true
		if(puzzleComplete())
		{
			return true;
		}

		// if puzzle did not get solved, return false and reset the puzzle and #choices
		else {
			this.choices = 0;
			return false;
		}
	}

	/*
	 *  actually solves the puzzle
	 *  @groupPoints - the list of vertices' locations of all the groups
	 *  @list - the list of possible values that can be used in the puzzle
	 *  @index - index for the groupPoints arrayList
	 */
	private void solvePuzzle(ArrayList<ArrayList<Location>> groupPoints, int[] list, int index) {

		// return when all the groups are visited
		if (index == groupPoints.size()) {
			return;
		}

		// get the first point for an index of groupPoints 
		int x = groupPoints.get(index).get(0).x;
		int y = groupPoints.get(index).get(0).y;

		// get the group from the point
		char group = this.puzzle[x][y].group;

		// get the operator for the group
		char operator = this.groups.get(group).operator;

		// get the outcome for the group
		int outcome = this.groups.get(group).outcome;

		// get the locations for the group
		ArrayList<Location> locations = this.groups.get(group).location;

		int numberOfLocations = locations.size();

		// stores the list of possible pairs that can be put into a group
		ArrayList<ArrayList<Integer>> allPossibilityPairs = new ArrayList<>();

		// check the operator
		switch(operator) {
		case add:
			allPossibilityPairs = new PossiblePairs().getAddPairs(list,
					numberOfLocations, outcome, new ArrayList<>(), allPossibilityPairs);
			break;
		case multiply:
			allPossibilityPairs = new PossiblePairs().getMultiplyPairs(list,
					numberOfLocations, outcome, new ArrayList<>(), allPossibilityPairs);
			break;
		case subtract:
			if(numberOfLocations <= 2)
			allPossibilityPairs = new PossiblePairs().getSubtractPairs(list,
					outcome, allPossibilityPairs);
			break;
		case divide:
			if(numberOfLocations <= 2)
			allPossibilityPairs = new PossiblePairs().getDividePairs(list,
					outcome, allPossibilityPairs);
			break;
		}
		
		for(int i=0; i < allPossibilityPairs.size(); i++) {			

			boolean pairExists = true;

			for (int j=0; j < locations.size(); j++) {

				// get the row index of the location
				int row = locations.get(j).x;

				// get the column index of the location
				int column = locations.get(j).y;

				// get value for a cell from the possibility pair
				int value = allPossibilityPairs.get(i).get(j);

				// if the value already exists in the same row or column,
				// reset the group and assign a value of zero to its cells
				// break and check for the next possibility pair
				if (!checkConflict(row, column, value)) {
					resetCellValues(locations);
					pairExists = false;
					break;
				}

				// if the value is unique in the row and column, 
				// then insert the value in the puzzle
				this.puzzle[row][column].value = value;
			}

			// if we found a pair that fits in the current group with no conflicts
			if(pairExists) {

				// check for the next group now
				solvePuzzle(groupPoints, list, index+1);

				// check if the puzzle is complete, exit the loop
				if(puzzleComplete()) {
					break;
				}

				// reset cell values and try for a diff combination
				else {
					resetCellValues(locations);
					continue;
				}
			}

			// if none of the pairs worked for a group then
			// we need to backtrack and choose a different set of pair for the previous group
			if (pairExists == false && i == (allPossibilityPairs.size()-1)) {

				this.choices++;
				return;
			}
		}

	}

	// check if the puzzle is complete
	private boolean puzzleComplete() {
		
		if(this.puzzle == null) {
			return false;
		}
		
		for (int i=0; i< this.puzzle.length; i++) {
			for (int j=0; j< this.puzzle.length; j++) {
				if (this.puzzle[i][j].value==0) {
					return false;
				}
			}
		}
		return true;
	}

	// reset the puzzle
	private void resetPuzzle() {
		for (int i=0; i< this.puzzle.length; i++) {
			for (int j=0; j< this.puzzle.length; j++) {
				this.puzzle[i][j].value = 0;
			}
		}
	}

	// reset the cell values to zero for the input locations
	private void resetCellValues(ArrayList<Location> locations) {
		for (Location loc: locations) {
			puzzle[loc.x][loc.y].value = 0;
		}
	}

	// returns a printable version of the puzzle in a string
	String print() {

		if (this.puzzle == null) {
			return null;
		}

		String s = "";

		// if puzzle is completed, print values
		if (puzzleComplete()) {
			for (int i=0; i< this.puzzle.length; i++) {
				for (int j=0; j< this.puzzle.length; j++) {
					s += this.puzzle[i][j].value;
				}
				s+="\n";
			}
		}
		
		// if puzzle did not solve or is incomplete, print the group names
		else {
			for (int i=0; i< this.puzzle.length; i++) {
				for (int j=0; j< this.puzzle.length; j++) {
					char ch = this.puzzle[i][j].group;
					if(this.groups != null && this.groups.get(ch) != null && this.groups.get(ch).operator == '=') {
						s += this.groups.get(ch).outcome;
					}
					else {
						s += this.puzzle[i][j].group;
					}
				}
				s+="\n";
			}
		}

		return s;
	}

	// return the number of times, we came back and changed a made decision
	int choices() {
		return this.choices;
	}

	// to check if the same value exits in that row or column
	private boolean checkConflict(int row, int column, int value) {

		// check row
		for(int i=0; i< this.puzzle.length; i++) {
			if(this.puzzle[row][i].value == value) {
				return false;
			}
		}

		// check column
		for(int i=0; i< this.puzzle.length; i++) {
			if(this.puzzle[i][column].value == value) {
				return false;
			}
		}

		return true;
	}


}