package encryptionCiphers;


public class DES {
	private static String key;
	
	final static int[][] pc_1 = 
		{{57, 49, 41, 33, 25, 17, 9}, 
		{1, 58, 50, 42, 34, 26, 18}, 
		{10, 2, 59, 51, 43, 35, 27}, 
		{19, 11, 3, 60, 52, 44, 36}, 
		{63, 55, 47, 39, 31, 23, 15}, 
		{7, 62, 54, 46, 38, 30, 22}, 
		{14, 6, 61, 53, 45, 37, 29}, 
		{21, 13, 5, 28, 20, 12, 4}};
    final static int[] shift_table = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    final static int[][] pc_2 = 
    	{{14, 17, 11, 24, 1, 5}, 
    	{3, 28, 15, 6, 21, 10}, 
    	{23, 19, 12, 4, 26, 8}, 
    	{16, 7, 27, 20, 13, 2}, 
    	{41, 52, 31, 37, 47, 55}, 
    	{30, 40, 51, 45, 33, 48}, 
    	{44, 49, 39, 56, 34, 53}, 
    	{46, 42, 50, 36, 29, 32}};

    final static int Ip[][] = 
    	{{58, 50, 42, 34, 26, 18, 10, 2}, 
    	{60, 52, 44, 36, 28, 20, 12, 4}, 
    	{62, 54, 46, 38, 30, 22, 14, 6}, 
    	{64, 56, 48, 40, 32, 24, 16, 8}, 
    	{57, 49, 41, 33, 25, 17, 9, 1}, 
    	{59, 51, 43, 35, 27, 19, 11, 3}, 
    	{61, 53, 45, 37, 29, 21, 13, 5}, 
    	{63, 55, 47, 39, 31, 23, 15, 7}};
    final static int Expansion_Table[][] = 
    	{{32, 1, 2, 3, 4, 5}, 
    	{4, 5, 6, 7, 8, 9}, 
    	{8, 9, 10, 11, 12, 13}, 
    	{12, 13, 14, 15, 16, 17}, 
    	{16, 17, 18, 19, 20, 21}, 
    	{20, 21, 22, 23, 24, 25}, 
    	{24, 25, 26, 27, 28, 29}, 
    	{28, 29, 30, 31, 32, 1}};
    final static int sbox[][][] = {
	    {{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
	    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
	    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
	    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}},
	    
	    {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10}, 
	    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5}, 
	    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15}, 
	    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}},
	    
	    {{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
	    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
	    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
	    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}},
	    
	    {{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
	    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
	    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
	    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}},
	    
	    {{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
	    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
	    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
	    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}},
	    
	    {{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
	    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
	    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
	    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}},
	    
	    {{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
	    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
	    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
	    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}},
	    
	    {{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
	    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
	    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
	    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}}
	    };
    final static int four_bit_p[][] = 
	    {{16, 7, 20, 21},
	    {29, 12, 28, 17},
	    {1, 15, 23, 26},
	    {5, 18, 31, 10},
	    {2, 8, 24, 14},
	    {32, 27, 3, 9},
	    {19, 13, 30, 6},
	    {22, 11, 4, 25}};
    final static int[][] Final_p = 
	    {{40, 8, 48, 16, 56, 24, 64, 32},
	    {39, 7, 47, 15, 55, 23, 63, 31},
	    {38, 6, 46, 14, 54, 22, 62, 30},
	    {37, 5, 45, 13, 53, 21, 61, 29},
	    {36, 4, 44, 12, 52, 20, 60, 28},
	    {35, 3, 43, 11, 51, 19, 59, 27},
	    {34, 2, 42, 10, 50, 18, 58, 26},
	    {33, 1, 41, 9, 49, 17, 57, 25}};

    static String[] round_key = new String[16];
    static String[] permutated_keys = new String[round_key.length];
    
    
    public DES(String Key){
    	key=Key.replaceAll(" ", "");
    	createkeys();
    }
    
    private void createkeys() {
    	String p_key = permutate(key, pc_1);
        String c0 = p_key.substring(0, p_key.length() / 2);
        String d0 = p_key.substring(p_key.length() / 2, p_key.length());
        Key_shifting(c0, d0);
        permutate_allkeys();		
	}
   
    public  String permutate(String key, int pc1[][]) {
        String permutated_key = "";
        int x;
        for (int i = 0; i < pc1.length; i++) {
            for (int j = 0; j < pc1[i].length; j++) {
                x = pc1[i][j];
                permutated_key = permutated_key + key.charAt(x - 1);
            }

        }
        return permutated_key;
    }

    public  void Key_shifting(String c0, String d0) {
        String[] shifted = new String[2];
        shifted[0] = c0;
        shifted[1] = d0;
        for (int r = 0; r < shift_table.length; r++) {
            for (int i = 0; i < shift_table[r]; i++) {
                shifted[0] = shifted[0] + shifted[0].charAt(0);
                shifted[0] = shifted[0].replaceFirst(String.valueOf(shifted[0].charAt(0)), "");
                shifted[1] = shifted[1] + shifted[1].charAt(0);
                shifted[1] = shifted[1].replaceFirst(String.valueOf(shifted[1].charAt(0)), "");
            }
            round_key[r] = shifted[0] + shifted[1];
        }

    }
    
    public  void permutate_allkeys() {
        for (int i = 0; i < round_key.length; i++) {
            permutated_keys[i] = permutate(round_key[i], pc_2);
        }
    }
   
	public String Encrypt(String plain_text) {
		plain_text = plain_text.replaceAll(" ", "");
		while(plain_text.length()<64)
			plain_text += "0";
        String message_ip = permutate(plain_text, Ip);
        String[] Ln = new String[17];
        String[] Rn = new String[17];
        Ln[0] = message_ip.substring(0, message_ip.length() / 2);
        Rn[0] = message_ip.substring(message_ip.length() / 2, message_ip.length());
        String Exp_R = "";
        String K_XOR_R = "";
        String L_XOR_F = "";
        String sbox_result = "";
        for (int i = 1; i < 17; i++) {
            Ln[i] = Rn[i - 1];
            Exp_R = permutate(Rn[i - 1], Expansion_Table);
            
            for (int j = 0; j < Exp_R.length(); j++) {
                K_XOR_R = K_XOR_R + (permutated_keys[i - 1].charAt(j) ^ Exp_R.charAt(j));
            }
            for (int j = 0, k = 0; j < K_XOR_R.length(); j += 6, k++) {
                String each_6bit = K_XOR_R.substring(j, j + 6);
                String row = "", col;
                row = row + each_6bit.charAt(0) + each_6bit.charAt(each_6bit.length() - 1);
                col = each_6bit.substring(1, each_6bit.length() - 1);
                each_6bit = String.valueOf(Integer.toBinaryString(sbox[k][Integer.parseInt(row, 2)][Integer.parseInt(col, 2)]));
                while (each_6bit.length() != 4) {
                    each_6bit = "0" + each_6bit;
                }
                sbox_result = sbox_result + each_6bit;
            }
            K_XOR_R = "";
            sbox_result = permutate(sbox_result, four_bit_p);

            for (int j = 0; j < sbox_result.length(); j++) {
                L_XOR_F = L_XOR_F + (Ln[i - 1].charAt(j) ^ sbox_result.charAt(j));
            }
            Rn[i] = L_XOR_F;
            L_XOR_F = "";
            sbox_result = "";

        }

        String cipher_text = permutate(Rn[16] + Ln[16], Final_p);
        return  cipher_text;
    }

    public String Decrypt(String cipherText){
    	String plainText = ""; int j=0;
    	for(int i = 0; i < permutated_keys.length / 2; i++)
    	{
    	    String temp = permutated_keys[i];
    	    permutated_keys[i] = permutated_keys[round_key.length - i - 1];
    	    permutated_keys[permutated_keys.length - i - 1] = temp;
    	}
    	while(cipherText.length()>=j){
    		if(plainText.length() != 64) {
    			plainText += Encrypt(cipherText.substring(j, cipherText.length()));
    		}
			j+=64;
		}
    	return plainText;
    }    
}