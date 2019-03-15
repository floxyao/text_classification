
package TextClassification;
import java.util.ArrayList;

public class ProbeTable {
    private int table_size;
    private Term[] term_table;
    private String[] table;
    final private int elements;
    
    public ProbeTable(int _table_size){
        table = new String[_table_size];
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
        int size = in.size();
        
        for(int i=0; i<table_size; i++){
            term_table[i] = new Term();
        }
        
        for(int i=0;i<size;i++){
            String word = in.get(i).toLowerCase();
            Term term = new Term(word, 1);
            KeyPolynomial k = new KeyPolynomial(term.word());
            int hash_value = (int) (k.p(37) % table_size);
            
            int initial_hash_value = hash_value;
            int num_collisions=0;
            
            while(term_table[hash_value] != null && !term_table[hash_value].word().equals("-")){
                num_collisions++;
                hash_value = ( initial_hash_value + ((int) Math.pow(num_collisions,2)) ) % table_size;
            } 
            hash(term);
            elts++;
            
            lf = (double) elts / table_size;
            if( lf >= 20.0 ){
                //System.out.println("elts = " + elts + "\ntable size = "+table_size);
                resize_term();
                elts = 0; //reset
            }
        }
    }
    
    public int hash (Term term){
        int hash_value;
        KeyPolynomial k = new KeyPolynomial(term.word());
        hash_value = (int) (k.p(37) % table_size);
        
        if(term_table[hash_value].word().equals(term.word())){
            term_table[hash_value].incr_count();
        }
        else{
            term_table[hash_value] = term;
        }
        return hash_value;
    }
    
    public int hash(String in){
        int hash_value;
        KeyPolynomial k = new KeyPolynomial(in);
        hash_value = (int) (k.p(37) % table_size);
        table[hash_value] = in;
        return hash_value;
    }
    
    private String[] copy(String[] copy_this){
        //System.out.println("copy()");
        int length = copy_this.length;
        String[] temp = new String[length];
        
        //System.out.println("=================print before resizing==============");
        for(int i=0; i<length; i++){
            if(copy_this[i] != null){
                temp[i] = copy_this[i];
            }
            
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
        String[] temp = copy(table);
        table_size *= 2;
        table = new String[table_size];
        
        init(table_size);
        
        for(int i=0; i<temp.length; i++){
            table[i] = temp[i];
            //System.out.println(table[i]);
        }
    }
    
    public void init(int size){
        for(int i=0; i<size; i++){
            table[i] = null;
        }
    }
    
    public void hash(ArrayList<String> in){
        int elts = 0;
        double lf = 0.0;
        int size = in.size();
        
        for(int i=0; i<size; i++){   
            String word = in.get(i);
            KeyPolynomial k = new KeyPolynomial(word);     

            int hash_value = (int) (k.p(37)%table_size);
            //System.out.println("hash = "+hash_value);
            int initial_hash_value = hash_value;
            int num_collisions=0;

            while(table[hash_value] != null && !table[hash_value].isEmpty()){
                num_collisions++;
                hash_value = ( initial_hash_value + ((int) Math.pow(num_collisions,2)) ) % table_size;
                //System.out.println(":"+hash_value);
                //System.out.println("num collisions = "+num_collisions);
            }
            table[ hash_value ] = word;
//            elts++;
//            lf = (double) elts / table_size;
//            if( lf >= 0.5 ){
//                System.out.println("elts = " + elts + "\ntable size = "+table_size);
//                resize();
//                elts = 0; //reset
//            }
        }
    }
    
    public int get_elements(){
        return elements;
    }
    
    public void print_freq(){
        for(int i=0;i<table_size; i++){
            term_table[i].print();
        }
    }

    public void print(){
        for(String element : table){
            System.out.print("["+element+"]");
        }
    }
}
