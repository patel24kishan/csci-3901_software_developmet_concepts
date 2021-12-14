/*
    This class is used as a pair object which stores Mobile_ID and Contact_ID as single 
    object in findGatherings()
*/
public class Pair {
    private String a,b;

    Pair(String a,String b){
        this.a = a;
        this.b = b;
    }

    //Get value of first element
    public String getA() {
        return this.a;
    }

    //Get value of second element
    public String getB() {
        return this.b;
    }

    //Set value of first element
    public void setA(String a) {
        this.a = a;
    }

    //Set value of second element
    public void setB(String b) {
        this.b = b;
    }

    //override the equals method to customize comparing evry elements of a pair to every element of other pair 
    @Override()
    public boolean equals(Object other) {
        if (other instanceof Pair) {
            Pair otherPair = (Pair) other;
            return (otherPair.getA().equals(this.getA()) && otherPair.getB().equals(this.getB()));
        }
        return false;
    }

    //Determines if a pairs contains str or not
    public boolean has(String str)
    {
        if (this.a.equals(str) || this.b.equals(str))
            return true;
        else
            return false;  
    }

}
