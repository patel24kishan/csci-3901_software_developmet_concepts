import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;;

public class MainClass {

	public static void main(String[] args) throws IOException {
		Mathdoku md = new Mathdoku();
		
		// TODO Assign the text file path to inputfilepath eg. C:\\Users\\Harpreet\\Desktop\\test1.txt
		
		String inputfilepath = "F:\\kishan\\study\\Dalhousie University\\SDC (3901)\\Assignment\\A 4\\CompleteSolver\\src\\test1.txt";
		
		BufferedReader br = new BufferedReader(new FileReader(inputfilepath));
		
		System.out.println("loadPuzzle returned: "+md.loadPuzzle(br));
		System.out.println("readyToSolve returned: "+md.readyToSolve());
		System.out.println("solve returned: "+md.solve());
		System.out.println("choices returned: "+md.choices);
		System.out.println("print returned: \n"+md.print());
		
	}
	
}
