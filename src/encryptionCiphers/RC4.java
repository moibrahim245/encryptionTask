package encryptionCiphers;


public class RC4 {
    private final int[] S = new int[256];
    private final int[] T = new int[256];
    private final int keylen;
    int[] key;

    public RC4(String mykey) {
    	key = EssentialConversion.getAsciicode(mykey);
    
        if (key.length < 1 || key.length > 256) {
            throw new IllegalArgumentException(
                    "key must be between 1 and 256 bytes");
        } else {
            keylen = key.length;
            for (int i = 0; i < 256; i++) {
                S[i] = i;
                T[i] = key[i % keylen];
            }

            int j = 0;
            for (int i = 0; i < 256; i++) {
                j = (j + S[i] + T[i])%256;
                swap(i,j,S);
            }
        }
    }
    public String encrypt(String myplaintext) {
    	
    	int [] plaintext= EssentialConversion.getAsciicode(myplaintext);
        int[] ciphertext = new int[plaintext.length];
       
        int i = 0, j = 0, k, t;
        
        for (int counter = 0; counter < plaintext.length; counter++) {
            i = (i + 1)%256;
            j = (j + S[i])%256;
            swap(i,j,S);
        
            t = (S[i] + S[j])%256;
            k = S[t];
            ciphertext[counter] =  (plaintext[counter] & 0xff ^ k)%256;

        }

        StringBuilder sb = new StringBuilder();

		for(int x : ciphertext){
	        sb.append(EssentialConversion.getAscii(x));
		}

		return sb.toString();
    }

    public String decrypt(String ciphertext) {
        return encrypt(ciphertext);
    }
    
    private void swap(int i, int j, int[] S) {
    	
        int temp = S[i];
        S[i] = S[j];
        S[j] =  temp;
}
    
}
