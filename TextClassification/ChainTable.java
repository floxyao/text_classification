
package TextClassification;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author flo
 */
public class ChainTable {
    private int table_size;
    private LinkedList<String>[] table;
    private Term[] term_table;
    private double load_factor;
    private int elements;
    
    public ChainTable(int _table_size){
        table = new LinkedList[_table_size];
        table_size  = _table_size;
        elements = _table_size;
    }
    
    public int get_term_size(){
        return term_table.length;
    }
    
    public Term[] get_terms(){
        return term_table;
    }
    
    public void frequency(ArrayList<String> in){
        term_table = new Term[table_size];
        int elts = 0;
        double lf = 0.0;
        
        for(int i=0; i<table_size; i++){
            term_table[i] = new Term();
        }
                
        for(int i=0; i<in.size(); i++){   
            String word = in.get(i).toLowerCase();
            hash(new Term(word, 1));
            elts++;
            lf = (double) elts / table_size;
            if(lf >= 20.0){
                System.out.println("resizing term");
                resize_term();
                elts=0;
            }
        }
    }
    
    public void hash(ArrayList<String> in){
        init(table_size);
        int elts=0;
        double lf = 0.0;
        
        for(int i=0;i<in.size(); i++){  
            String word = in.get(i);
            KeyPolynomial k = new KeyPolynomial(word);     
            int hashValue = (int) (k.p(37)%table_size);
            
            table[hashValue].add(word);
            
//            elts++;
//            lf = (double) elts / table_size;
//            if( lf >= 1.0 ){
//                resize();
//                elts=0; //reset
//            }
        }
    }
    
    public void hash(Term term){
        KeyPolynomial k = new KeyPolynomial(term.word());
        int hash_value = (int) (k.p(37) % table_size);
        
        if(term_table[hash_value].word().equals(term.word())){
            term_table[hash_value].incr_count();
        }
        else{
            term_table[hash_value] = term;
        }
    }
    
    public int hash(String in){
        int hash_value;
        KeyPolynomial k = new KeyPolynomial(in);
        hash_value = (int) (k.p(37) % table_size);
        table[hash_value].add(in);
        return hash_value;
    }
    
    private LinkedList<String>[] copy(LinkedList<String>[] copy_this){
        System.out.println("copy()");
        int length = copy_this.length;
        LinkedList<String>[] temp = new LinkedList[length];
        
        //System.out.println("=================print before resizing==============");
        for(int i=0; i<length; i++){
            temp[i] = copy_this[i];
            //System.out.println(temp[i]);
        }
        //System.out.print("==================================================");
        return temp;
    }
    
    private Term[] copy(Term[] copy_this){
        //System.out.println("term copy()");
        int length = copy_this.length;
        Term[] temp = new Term[length];
        
        for(int i=0; i<length; i++){
            if(copy_this[i] != null && !copy_this[i].word().equals("-")){
                temp[i] = copy_this[i];
            }
        }
        return temp;
    }
    
    public void resize_term(){
        Term[] temp = copy(term_table);
        int temp_size = temp.length;
        table_size *= 2;
        term_table = new Term[table_size];
        
        for(int i=0; i<table_size; i++){
            //System.out.print("init");
            term_table[i] = new Term();
        }
        
        for(int i=0; i<temp_size; i++){
            if(temp[i] != null && !temp[i].word().equals("-")){
                term_table[i] = temp[i];
            }
        }
    }
    
    public void resize(){
        //System.out.println("resize()");
        LinkedList<String>[] temp = copy(table);
        table_size *= 2;
        table = new LinkedList[table_size];
        
        init(table_size);
        
        for(int i=0; i<temp.length; i++){
            table[i] = temp[i];
            //System.out.println(table[i]);
        }
//        System.out.println("\n==================print after resizing================");
//        for(int i=0; i<table_size; i++){
//            System.out.println(table[i]);
//        }
//        System.out.println("====================================================");
    }

    public void init(int size){
        for(int i=0; i<size; i++){
            table[i] = new LinkedList<>();
        }
    }
    
    public void print_freq(){
        for(int i=0;i<table_size; i++){
            term_table[i].print();
        }
    }
    
    public void print(){
        int i=0;
        for(LinkedList l : table){
            String s = Integer.toString(i);

            if(l.isEmpty()){
                System.out.print(s+": (empty)\n");
            }
            else{
                System.out.print(s+": "+l+"\n");
            }
            i++;
        }
    }
    
    public int get_elements(){
        return elements;
    }
    
}



