
package TextClassification;

/**
 *
 * @author flo
 */
public class Term implements Comparable<Term>{
    final private String word;
    private int count=0;
    
    public Term(){
        word = "-";
        count = 0;
    }
    
    public Term(String w, int c){
        word = w;
        count = c;
    }
    
    public void incr_count(){
        count++;
    }
    
    public String word(){
        return word;
    }
    
    public int count(){
        return count;
    }
    
    public void print(){
        System.out.println(count+" "+word);
    }

    @Override
    public int compareTo(Term other) {
        if(count > other.count()){
            return -1;
        }
        else if(count < other.count()){
            return 1;
        }
        return 0;
    
    }
    
    public boolean equals(Term t){
        if(t instanceof Term){
            Term temp =  (Term) t;
            if(temp.word().equals(this.word)){
                return true;
            }
        }
        return false;
    }
    
    
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
//        return hash;
//    }
    
    
}
