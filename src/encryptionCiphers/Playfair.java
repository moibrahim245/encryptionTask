package encryptionCiphers;

import java.awt.Point;


public class Playfair {
	
	private  String  key;
	private  String [][] matrix;
	private  int numofop, length;

	public Playfair(String mykey){
		key = mykey.toUpperCase();
		key = key.replaceAll("[^A-Z]", "");
		key = key.replace("J", "I");
		matrix = createMatrix(key);

	}
	
	private  String[][] createMatrix(String key) {
		String[][] playfairmatrix = new String[5][5];
	    String keyString = key + "ABCDEFGHIKLMNOPQRSTUVWXYZ";
	    
	    for(int i = 0; i < 5; i++)
	      for(int j = 0; j < 5; j++)
	        playfairmatrix[i][j] = "";
	    
	    for(int k = 0; k < keyString.length(); k++){
	      boolean repeat = false;
	      boolean used = false;
	      for(int i = 0; i < 5; i++){
	        for(int j = 0; j < 5; j++){
	          if(playfairmatrix[i][j].equals("" + keyString.charAt(k))){
	            repeat = true;
	          }else if(playfairmatrix[i][j].equals("") && !repeat && !used){
	            playfairmatrix[i][j] = "" + keyString.charAt(k);
	            used = true;
	          }
	        }
	      }
	    }
	    return playfairmatrix;
	}

	public String encrypt(String plainText){
		String ciphertext = "";
		
		String[] words = plainText.split("\\s+");
		for(String word : words){
			word = word.replaceAll(" ", "");
			length = word.length();
	  		numofop = (length/2) + (length % 2);
	  		
	  		word = prepareString(word);
	
			ciphertext += playfairCipher(word) + " ";
		}
		return ciphertext;
	}

	public String decrypt(String cipherText){
			String plainText = "";
			String[] words = cipherText.split("\\s+");
			for(String word : words){
			    for(int i = 0; i < word.length() / 2; i++){
			      char a = word.charAt(2*i);
			      char b = word.charAt(2*i+1);
			      int r1 = (int) getPoint(a).getX();
			      int r2 = (int) getPoint(b).getX();
			      int c1 = (int) getPoint(a).getY();
			      int c2 = (int) getPoint(b).getY();
			      if(r1 == r2){
			        c1 = (c1 + 4) % 5;
			        c2 = (c2 + 4) % 5;
			      }else if(c1 == c2){
			        r1 = (r1 + 4) % 5;
			        r2 = (r2 + 4) % 5;
			      }else{
			        int temp = c1;
			        c1 = c2;
			        c2 = temp;
			      }
			      plainText = plainText + matrix[r1][c1] + matrix[r2][c2];
			    }
			    plainText += " ";
			}
		    return plainText;
		}
	
	private  String prepareString(String mystring) {
		String mytext = mystring.toUpperCase();
		mytext = mytext.replaceAll("[^A-Z]", "");
		mytext = mytext.replace("J", "I");
		for(int i = 0; i < numofop; i++){
			if((2 * i + 1)== mytext.length())
		    	  mytext = mytext + "X";
		    if(mytext.charAt(2 * i) == mytext.charAt(2 * i + 1)){
		    	  mytext = new StringBuffer(mytext).insert(2 * i + 1, 'X').toString();
		    	  length = mytext.length();
		    	  numofop = (length/2) + (length % 2);
		      }
		      
		    }
		return mytext;
		
	}
	


	private  String playfairCipher(String plaintext) {
	    String[] couples = new String[numofop];

		for(int j = 0; j < numofop ; j++){
		      couples[j] = plaintext.charAt(2 * j) +""+ plaintext.charAt(2 * j + 1);
		}
		String out = "";
	    String[] encoded = new String[numofop];
	    encoded = encodecouples(couples);
	    for(int i = 0; i < numofop; i++)
	      out = out + encoded[i];
	    return out;
		
	}
	
	private  String[] encodecouples(String di[]){
	    String[] enc = new String[numofop];
	    for(int i = 0; i < numofop; i++){
	      char a = di[i].charAt(0);
	      char b = di[i].charAt(1);
	      int r1 = (int) getPoint(a).getX();
	      int r2 = (int) getPoint(b).getX();
	      int c1 = (int) getPoint(a).getY();
	      int c2 = (int) getPoint(b).getY();
	      
	      if(r1 == r2){
	        c1 = (c1 + 1) % 5;
	        c2 = (c2 + 1) % 5;
	        
	      }else if(c1 == c2){
	        r1 = (r1 + 1) % 5;
	        r2 = (r2 + 1) % 5;
	      
	      }else{
	        int temp = c1;
	        c1 = c2;
	        c2 = temp;
	      }
	      
	      enc[i] = matrix[r1][c1] + "" + matrix[r2][c2];
	    }
	    return enc;
	  }
	private  Point getPoint(char c){
	    Point pt = new Point(0,0);
	    for(int i = 0; i < 5; i++)
	      for(int j = 0; j < 5; j++)
	        if(c == matrix[i][j].charAt(0))
	          pt = new Point(i,j);
	    return pt;
	  }
	
	public String[][] getmatrix(){
	    return matrix;
	}
	
}
