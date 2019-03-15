
package TextClassification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileTokenizer {
    private String file_name;
    private ArrayList<String> tokens;
    private int total_tokens;
    
    public FileTokenizer(String _file_name){
        file_name    = _file_name;
        total_tokens = 0;
        tokens = new ArrayList<>();
        
        try{
            Scanner scanner = new Scanner(new FileInputStream(file_name));
            scanner.useDelimiter("[^A-Za-z]+");
           

            while(scanner.hasNext()){
                String word = scanner.next();
                //System.out.print(word+",");
                tokens.add(word);
                total_tokens++;
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
    public int num_tokens(){
        return total_tokens;
    }
    
    public ArrayList<String> get_tokens(){
        return tokens;
    }
    
    public ArrayList<String> clean_file(){
        
        try{
            Scanner scanner = new Scanner(new FileInputStream(file_name));
            scanner.useDelimiter("[.,:;()?!\"\\s]+");

            while(scanner.hasNext()){
                String word = scanner.next();
                //System.out.print(word+",");
                tokens.add(word);
                total_tokens++;
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        
        //System.out.println("File has "+total_tokens+" total tokens.");
        return tokens;
    }
    
    public void print_tokens(){
        for(int i=0;i<total_tokens;i++){
            System.out.println(tokens.get(i)+" ");
        }
    }
}
