package encryptionCiphers;

import java.math.BigDecimal;
import java.math.BigInteger;


public class RSA {
	int p,q,n,z,d=0,e,i;
	int[] c;
	BigInteger[] msgback; 
	int[] msg;
	

	public RSA(int myp,int myq){		
		p = myp;
		q = myq;
		n=p*q;
		z=(p-1)*(q-1);

		for(e=2; e<z; e++)
		{
			if(gcd(e,z)==1)            
			{				
				break;
			}
		}
		
		
		for(i=0;i<=9;i++)
		{
			int x=1+(i*z);
			if(x%e==0)      
			{
				d=x/e;
				break;
			}
		}

	}

	static int gcd(int e, int z)
	{
		if(e==0)
			return z;	
		else
			return gcd(z%e,e);
	}

	public String encrypt(String myplaintext){
		msg=new int[myplaintext.length()];
		c = new int[myplaintext.length()];
		msg = EssentialConversion.getAsciicode(myplaintext);
		for(int i=0; i<msg.length;i++){
			c[i]= (int) ((Math.pow(msg[i],e))%n);

		}
	
		String cipher = "";
		for(int i=0; i<c.length;i++){
			cipher += EssentialConversion.getAscii(c[i]);
		}

		return cipher;
	}
	
	public String decrypt(String cipherText){
		
		c=EssentialConversion.getAsciicode(cipherText);
		BigInteger N = BigInteger.valueOf(n);
		BigInteger C ; 
		msgback = new BigInteger[cipherText.length()];
		String plain="";
		for(int i=0; i<c.length;i++){
	        C = BigDecimal.valueOf(c[i]).toBigInteger();

			msgback[i] = C.pow(d).mod(N);
			plain += EssentialConversion.getAscii(msgback[i].intValue());
		}
		

		return plain;
	}
}
