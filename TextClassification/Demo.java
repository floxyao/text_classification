package TextClassification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class Demo {
    
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        char key = 'a';
        
        do{
            showMenu();
            key = in.next().charAt(0);
            switch(key){
                case '1':
                    key_poly();
                    break;
                case '2':
                    chain_table();
                    break;
                case '3':
                    probe_table();
                    break;
                case '4':
                    clean_doc();
                    break;
                case '5':
                    choose_data_struc();
                    break;
                default:
                    System.out.println("Default");
                    break;
            }
            System.out.println("\nPress 'y' to go back to main menu or any key to exit");
            key = in.next().charAt(0);
        }while(key == 'y');
    }
    
    public static void key_poly(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter string: ");
        String s = in.next();
        KeyPolynomial key_temp = new KeyPolynomial(s);
        System.out.println("Key Polynomial of "+s+": "+key_temp.p(37));
    }
    
    public static void chain_table(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter table size prime number: ");
        int temp_size = in.nextInt();
        System.out.println("Enter list of words: ");
        ArrayList<String> list = split(in.next());
        ChainTable chain = new ChainTable(temp_size);
        chain.hash(list);
        chain.print();
    }
    
    public static void probe_table(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter table size prime number: ");
        int temp_size2 = in.nextInt();
        System.out.println("Enter list of words: ");
        ArrayList<String> list2 = split(in.next());
        ProbeTable probe = new ProbeTable(temp_size2);
        probe.hash(list2);
        probe.print();
        
    }
    
    public static void clean_doc(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter name of text document: ");
        String doc_name = in.next();
        FileTokenizer fr = new FileTokenizer(doc_name+".txt");
        fr.print_tokens();
    }
    
    public static void choose_data_struc(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter name of text document: ");
        String doc_name2 = in.next();
        FileTokenizer f = new FileTokenizer(doc_name2+".txt");
        ArrayList<String> token_list = new ArrayList<>();
        token_list = f.get_tokens();
        double temp = token_list.size() / Math.log(token_list.size());
        int table_size = (int) temp;  
        
        System.out.println("Choose data structure \n 1 = List \n 2 = Chain \n 3 = Probe");
        char choose_table = in.next().charAt(0);

            switch(choose_table){
                case '1':
                    
                    ArrayList<Term> t = new ArrayList<>();
                    int num_tokens = token_list.size();
                    
                    long startTime = System.currentTimeMillis();
                    
                    for(int i=0; i<num_tokens; i++){
                        boolean dup = false;
                        String word = token_list.get(i).toLowerCase();
                        Term term = new Term(word, 1);
                        //System.out.println("inner loop: "+term.word());
                        for(int j=0; j<t.size(); j++){
                            
                           if(t.get(j).word().equals(term.word())){
                               //System.out.println("dup count");
                               t.get(j).incr_count();
                               dup = true;
                           }
                        }
                        if(dup == false){
                            t.add(term);
                        }
                    }
                    Collections.sort(t);
                    
                    int result_term_size = t.size();
                    for(int i=0; i<result_term_size; i++){
                        t.get(i).print();
                    }
                    long endTime = System.currentTimeMillis();
                    long totalTime = endTime - startTime;
                    System.out.println("Time elapsed: "+totalTime/1000+" seconds");
                    break;
                case '2':
                    ChainTable c = new ChainTable(table_size);
                    long startTime1 = System.currentTimeMillis();
                    c.frequency(token_list);

                    Term[] term_list2 = new Term[c.get_term_size()];
                    term_list2 = c.get_terms();

                    ArrayList<Term> array_terms2 = new ArrayList<>();
                    for(int i=0; i<term_list2.length;i++){
                        array_terms2.add(term_list2[i]);
                    }
                    Collections.sort(array_terms2);
                    for(int i=0; i<term_list2.length;i++){
                        array_terms2.get(i).print();
                    }
                    long endTime1 = System.currentTimeMillis();
                    long totalTime1 = endTime1 - startTime1;
                    if(totalTime1 > 1000){
                        System.out.println("Time: " + totalTime1/1000 +" seconds");
                    }else{
                        System.out.println("Time: " + totalTime1+" ms");
                    }
                    break;
                case '3':
                    ProbeTable p = new ProbeTable(table_size);
                    long startTime2 = System.currentTimeMillis();
                    p.frequency(token_list);

                    Term[] term_list = new Term[p.get_term_size()];
                    term_list = p.get_terms();

                    ArrayList<Term> array_terms = new ArrayList<>();
                    for(int i=0; i<term_list.length;i++){
                        array_terms.add(term_list[i]);
                    }
                    Collections.sort(array_terms);
                    for(int i=0; i<term_list.length;i++){
                        array_terms.get(i).print();
                    }
                    long endTime2 = System.currentTimeMillis();
                    long totalTime2 = endTime2 - startTime2;
                    if(totalTime2 > 1000){
                        System.out.println("Time: " + totalTime2/1000 +" seconds");
                    }else{
                        System.out.println("Time: " + totalTime2+" ms");
                    }
                    break;
                default:
                    break;
            }
    }
    
    public static void showMenu(){
        System.out.println("[1] Find Key Polynomial");
        System.out.println("[2] Separate Chaining Table");
        System.out.println("[3] Quadatric Probing Table");
        System.out.println("[4] Clean Document");
        System.out.println("[5] Frequency");
        System.out.println("[x] Exit");
                
    }
    
    public static ArrayList split(String in) {
        ArrayList<String> temp = new ArrayList<>();
        String[] str = in.split(",");
        for(int i=0; i<str.length; i++){
            temp.add(str[i]);
        }
        return temp;
    }
}
