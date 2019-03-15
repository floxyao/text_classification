package TextClassification;

import java.util.ArrayList;

public class KeyPolynomial {
    private String input;
    private ArrayList<String> expression;
    private ArrayList<Integer> kp;
    private int length;
    
    public KeyPolynomial(){
        //default
    }
    
    public KeyPolynomial(String in){
        input      = in;
        length     = input.length();
        kp         = new ArrayList<>();
        expression = new ArrayList<>();
        
        for(int i=0, j=length-1; i<length; i++,j--){
            int ascii = input.charAt(i);
            //System.out.print(ascii+" ");
            
            kp.add(ascii);

            if(j>1){
                expression.add(Integer.toString(ascii)+"x^"+j);
            }
            else if(j==1){
                expression.add(Integer.toString(ascii)+"x");
            }
            else if(j==0){
                expression.add(Integer.toString(ascii));
            } 
        }
    }
    
    //returns an index
    //in practice, horner's algorithm: degree => overflow
    public long p(int value){
        long index = 0;
        for(int i=0, degree=length-1; i<length ; i++, degree--){
            //System.out.println("j = "+j+"    i ="+i);
            index += kp.get(i) * Math.pow(value,degree);
        }
        return index;
    }
    
    public void print(){
        for(int i=0; i<length; i++){
            if(i<length-1){
                System.out.print(expression.get(i)+" + "); 
            }
            else{
                System.out.print(expression.get(i)+"\n");
            }  
        }
    }
    
}
