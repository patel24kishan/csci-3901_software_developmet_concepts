import java.util.ArrayList;
import java.util.Arrays;

// will create possible pairs for a group, according to the operation
// multiply and add operations can have mutiple operands and hence need recursion to get the results
// subtract and divide operations will have only two operands so their logic is alike
public class PossiblePairs {

	/*
	 *  get the possible pairs for the add operation  
	 *  @list - the list of the possible values that can be filled in a cell
	 *  @size - the size of the group
	 *  @outcome - the outcome after applying the operator
	 *  @solution - one possible pair for a group
	 *  @allSolutions - list of all possible pairs for a group
	 */ 
	ArrayList<ArrayList<Integer>> getAddPairs(int[] list, int size, int outcome, 
			ArrayList<Integer> solution, ArrayList<ArrayList<Integer>> allSolutions){
		
		// one pair is complete, check whether the pair satifies the contraints
		if (solution.size() == size) {
			int sum = 0;
			for (int value: solution) {
				sum += value;
			}
			// if it satisfies, add the pair to the solution list
			if (sum == outcome) {
				allSolutions.add(new ArrayList<Integer>(solution));
			}			
			return allSolutions;
		}

		// try all the values in the list one by one
		for (int i: list) {
			
			// add a number to the solution
			solution.add(i);
			
			getAddPairs(list, size, outcome, solution, allSolutions);
			
			// keep on changing the last element with all the possible combinations
			solution.remove(solution.size()-1);
		}
		
		return allSolutions;
	}


	/*
	 *  get the possible pairs for the multiplication operation  
	 *  @list - the list of the possible values that can be filled in a cell
	 *  @size - the size of the group
	 *  @outcome - the outcome after applying the operator
	 *  @solution - one possible pair for a group
	 *  @allSolutions - list of all possible pairs for a group
	 */ 
	ArrayList<ArrayList<Integer>> getMultiplyPairs(int[] list, int size, int outcome,
			ArrayList<Integer> solution, ArrayList<ArrayList<Integer>> allSolutions){
		
		// one pair is complete, check whether the pair satifies the contraints
		if (solution.size() == size) {
			int product = 1;
			for (int value: solution) {
				product *= value;
			}
			
			// if it satisfies, add the pair to the solution list
			if (product == outcome) {
				allSolutions.add(new ArrayList<Integer>(solution));
			}			
			return allSolutions;
		}

		for (int i: list) {
			
			// add a number to the solution
			solution.add(i);
			
			getMultiplyPairs(list, size, outcome, solution, allSolutions);
			
			// keep on changing the last element with all the possible combinations
			solution.remove(solution.size()-1);
		}
		return allSolutions;
	}

	/*  
	 *  get the possible pairs for the subtract operation
	 *  @list - the list of the possible values that can be filled in a cell
	 *  @outcome - the outcome after applying the operator
	 *  @solutions - list of all possible pairs for a group
	 */
	ArrayList<ArrayList<Integer>> getSubtractPairs (int[] list, int outcome,
			ArrayList<ArrayList<Integer>> allSolutions) {
		
		for (int i=0; i < list.length; i++) {
			for (int j=i; j < list.length; j++) {
				
				// if the difference of the numbers equals the outcome, 
				// add the pair to the solution list and also its reverse form
				if(list[j]-list[i] == outcome) {
					allSolutions.add(new ArrayList<Integer>(Arrays.asList(list[i], list[j])));
					allSolutions.add(new ArrayList<Integer>(Arrays.asList(list[j], list[i])));
				}
			}
		}
		return allSolutions;
	}

	/*  
	 *  get the possible pairs for the divide operation
	 *  @list - the list of the possible values that can be filled in a cell
	 *  @outcome - the outcome after applying the operator
	 *  @solutions - list of all possible pairs for a group
	 */
	ArrayList<ArrayList<Integer>> getDividePairs (int[] list, int outcome,
			ArrayList<ArrayList<Integer>> allSolutions) {
		
		for (int i=0; i < list.length; i++) {
			for (int j=i; j < list.length; j++) {
				
				// if the division of the numbers equals the outcome, 
				// add the pair to the solution list and also its reverse form
				if(list[j]%list[i] == 0 && list[j]/list[i] == outcome) {
					allSolutions.add(new ArrayList<Integer>(Arrays.asList(list[i], list[j])));
					allSolutions.add(new ArrayList<Integer>(Arrays.asList(list[j], list[i])));
				}
			}
		}
		return allSolutions;
	}
}
