import java.util.ArrayList;

// class consisting of constraints in each group
public class Constraint {

	int outcome;
	char operator;
	
	// vertices list of each group
	ArrayList<Location> location;
	
	public Constraint(int outcome, char operator, ArrayList<Location> location) {
		this.outcome = outcome;
		this.operator = operator;
		this.location = location;
	}

}
