package encryptionCiphers;

public class Caeser {
	
	private int key;
	public Caeser(int key){
		this.key = key;
	}
	public String encrypt(String plainText){
		String cipherText = "";
		char c;

		for(int i=0; i<plainText.length(); i++){
			c = (char) (plainText.charAt(i) + key);
			if ((Character.isLowerCase(plainText.charAt(i)) && c > 'z')
		            || (Character.isUpperCase(plainText.charAt(i)) && c > 'Z'))
				c -= (char) (26);

			cipherText += c;
		}
		
		return cipherText;
		
	}
	
	public String decrypt(String cipherText){
		String plainText="";
		char c;
		for(int i=0; i<cipherText.length(); i++){
			c = (char) (cipherText.charAt(i) - key);

			if ((Character.isLowerCase(cipherText.charAt(i)) && c < 'a')
		            || (Character.isUpperCase(cipherText.charAt(i)) && c < 'A'))
				c += (char) (26);

			plainText += c;
		}
		return plainText;
	}
}
