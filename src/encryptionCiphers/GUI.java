package encryptionCiphers;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.awt.event.ActionEvent;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javax.swing.JTextArea;
import java.awt.Font;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	TextArea detailsTextArea = new TextArea();
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() {
		
		this.setTitle("Encryption Project GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.DARK_GRAY);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] ciphertypes = {"Caeser", "Playfair", "Feistel", "DES", "DES ASCII", "RC4", "RSA"};
		
		JComboBox<Object> ciphertype = new JComboBox<Object>(ciphertypes);
		ciphertype.setBounds(177, 135, 150, 23);
		contentPane.add(ciphertype);
		
		JTextField keytext = new JTextField();
		keytext.setBounds(10, 167, 726, 23);
		contentPane.add(keytext);
		keytext.setColumns(10);
		
		JTextArea titleLabel = new JTextArea();
		titleLabel.setBounds(10, 135, 140, 23);
		titleLabel.setText(" The Key or Gate: ");
		titleLabel.setEditable(false);
		contentPane.add(titleLabel);
		
		TextArea inputTextArea = new TextArea();
		inputTextArea.setBounds(10, 198, 726, 130);
		contentPane.add(inputTextArea);
		
		TextArea ciphertextarea = new TextArea();
		ciphertextarea.setEditable(false);
		ciphertextarea.setBounds(10, 13, 726, 112);
		contentPane.add(ciphertextarea);
		
		JButton btnencrypt = new JButton("Encrypt");
		btnencrypt.setBounds(10, 333, 140, 50);
		contentPane.add(btnencrypt);
		
		JButton btndecrypt = new JButton("Decrypt");
		btndecrypt.setBounds(177, 333, 140, 50);
		contentPane.add(btndecrypt);
		
		JButton btncopy = new JButton("Copy");
		btncopy.setBounds(586, 135, 150, 23);
		contentPane.add(btncopy);
		
		JButton btnCopyToEncrypt = new JButton("Encrypt Copy");
		btnCopyToEncrypt.setBounds(430, 135, 150, 23);
		contentPane.add(btnCopyToEncrypt);
		
		detailsTextArea.setEditable(false);
		detailsTextArea.setFont(new Font("Helvetica", Font.PLAIN, 14));
		detailsTextArea.setBounds(327, 333, 409, 111);
		contentPane.add(detailsTextArea);
			
		JButton btnclear = new JButton("Clear");
		btnclear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inputTextArea.setText("");
				keytext.setText("");
				ciphertextarea.setText("");
				detailsTextArea.setText("");
			}
		});
		btnclear.setBounds(177, 394, 140, 50);
		contentPane.add(btnclear);
		
		JButton btnTest = new JButton("Test");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				switch (ciphertype.getSelectedItem().toString()){
				case "Caeser"	: 
					inputTextArea.setText("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
					keytext.setText("1");
							break;
				case "Playfair" : 
					inputTextArea.setText("Pyramids");
					keytext.setText("Egypt");
							break;
				case "Feistel" : 
					inputTextArea.setText("11010011");
					keytext.setText("AND");
							break;
				case "DES":
					inputTextArea.setText("00111001 00100011 01000101 01100111 10001001 10101011 11001101 11101111");
					keytext.setText("01010011 00110100 01010111 01111001 10011011 10111100 11011111 10110001");
							break;
				case "DES ASCII":
					inputTextArea.setText("Sukhoi35");
					keytext.setText("00010011 01010111 01111001 10011011 11110001 10111100 11011011 00110100");
							break;
				case "RC4" : 
					inputTextArea.setText("JustConcatenatedTextForRC4");
					keytext.setText("Doyouwishtoseeitworking?");
							break;
				case "RSA" : 
					inputTextArea.setText("An amazing RSA Algorithm that works only at 11 or higher");
							break;
			}
			}});
		btnTest.setBounds(10, 394, 140, 50);
		contentPane.add(btnTest);
		
		btnencrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try{
					String key = keytext.getText();
					String plaintext = inputTextArea.getText();
					switch (ciphertype.getSelectedItem().toString()){
					case "Caeser"	: 
						ciphertextarea.setText(encryptcaeser(plaintext,key));
						break;
					case "Playfair" : 
						ciphertextarea.setText(encryptPlayfair(plaintext,key));
						break;
					case "Feistel":
						ciphertextarea.setText(encryptFeistel(plaintext, key));
						break;
					case "DES":
						ciphertextarea.setText(encryptDES(plaintext,key).replaceAll("........", "$0 "));					
						break;
					case "DES ASCII":
						ciphertextarea.setText(EssentialConversion.binarytostr(encryptDES(EssentialConversion.tobinary(plaintext),key)));					
						break;
					case "RC4" : 
						ciphertextarea.setText(encryptRC4(plaintext,key));
						break;
					case "RSA" : 
						ciphertextarea.setText(encryptRSA(plaintext));
						break;
					}
				}catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Number format exception : \n"+e.getMessage());
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}		

		});
		
		btndecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String key = keytext.getText();
					String plaintext = inputTextArea.getText();
					switch (ciphertype.getSelectedItem().toString()){
					case "Caeser"	:
						ciphertextarea.setText(decryptcaeser(plaintext,key));
						break;
					case "Playfair" : 
						ciphertextarea.setText(decryptPlayfair(plaintext,key));
						break;
					case "Feistel":
						ciphertextarea.setText(decryptFeistel(plaintext, key));						
						break;
					case "DES":
						ciphertextarea.setText(decryptDES(plaintext,key).replaceAll("........", "$0 "));						
						break;
					case "DES ASCII":
						ciphertextarea.setText(EssentialConversion.binarytostr(decryptDES(EssentialConversion.tobinary(plaintext),key)));					
						break;
					case "RC4" : 
						ciphertextarea.setText(decryptRC4(plaintext,key));
						break;
					case "RSA" : 
						ciphertextarea.setText(decryptRSA(plaintext));
						break;
					}
				}catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Number format exception : \n"+e.getMessage());					
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}

		});
		btncopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection stringSelection = new StringSelection (ciphertextarea.getText());
				Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
				clpbrd.setContents (stringSelection, null);
			}
		});
		btnCopyToEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputTextArea.setText(ciphertextarea.getText());
				ciphertextarea.setText("");
			}
		});
		
	}
	
	protected String encryptcaeser(String plainText, String keyText) throws Exception {
		if(keyText.matches(".*[a-zA-Z].*"))
			throw new Exception("Key should be numeric value");
		int key = Integer.parseInt(keyText);
		if(key < 0)
			throw new Exception("Negative key value");
		else if (key > 25)
			throw new Exception("Key is larger than 25");
		
		Caeser caeser = new Caeser(key);
		detailsTextArea.setText("Your Caeser Cipher information for encryption: " + key + " is your Key.\n"
				+ "Your Original Text is: '" + plainText + "'");
		return caeser.encrypt(plainText);
	}

	protected String decryptcaeser(String cipherText, String keyText) throws Exception {
		if(keyText.matches(".*[a-zA-Z].*"))
			throw new Exception("Key should be numeric value");
		int key = Integer.parseInt(keyText);
		if(key < 0)
			throw new Exception("Negative key value");
		else if (key > 25)
			throw new Exception("Key is larger than 25");
		
		Caeser caeser = new Caeser(key);

		detailsTextArea.setText("Your Caeser Cipher information decryption: " + key + " is your Key.\n"
				+ "Your Cipher Text is: '" + cipherText + "'");
		return caeser.decrypt(cipherText);
	}

	protected String encryptPlayfair(String plainText, String keyText) throws Exception{
		if(!keyText.matches("[a-zA-Z]+"))
			throw new Exception("Key should be Charachters only");
		else if(!plainText.matches("([a-zA-Z]*( |\r\n|\r|\n)*)*"))
		throw new Exception("Plain text should be Charachters only");		
		Playfair pf = new Playfair(keyText);
		
		detailsTextArea.setText("Matrix: \n" + showMatrix(pf.getmatrix()));
		return pf.encrypt(plainText);
	}

	private String showMatrix(String[][] matrix) {
		String myMatrix="";
		for(int i =0 ;i<matrix.length;i++){
			for(int j=0;j<matrix[i].length;j++)
				myMatrix+= "\t"+matrix[i][j];
			myMatrix +="\n";
		}
		return myMatrix;
	}

	protected String decryptPlayfair(String plainText, String keyText) throws Exception {
		if(!keyText.matches("[a-zA-Z]+"))
			throw new Exception("Key should be Charachters only");
		else if(!plainText.matches("([a-zA-Z]*( |\r\n|\r|\n)*)*"))
		throw new Exception("Plain text should be Charachters only");
		Playfair pf = new Playfair(keyText);
		detailsTextArea.setText("Matrix: \n" + showMatrix(pf.getmatrix()));
		return pf.decrypt(plainText);	
	}
	
	protected String encryptFeistel(String plaintext, String logicGate) {
		String cipherText = "";
		int numberOfRounds = 0;
		numberOfRounds = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of rounds",numberOfRounds));

		Feistel feistel = new Feistel(numberOfRounds);
		feistel.setFunctionOperator(logicGate);
		cipherText = feistel.encrypt(plaintext);
		

		detailsTextArea.setText("Your Feistel Cipher information for 16 rounds: '" + logicGate + "' is your Logic Gate.\n"
				+ "Your Original Text is: '" + plaintext + "'");
		return cipherText;
	}
	
	protected String decryptFeistel(String cipherText, String logicGate) {
		
		int numberOfRounds = 0;
		numberOfRounds = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of rounds",numberOfRounds));
		
		Feistel feistel = new Feistel(numberOfRounds);
		feistel.setFunctionOperator(logicGate);
		String originalMessage = feistel.decrypt(cipherText);
		

		detailsTextArea.setText("Your Feistel Cipher information for 16 rounds: " + logicGate + " is your Logic Gate.\n"
				+ "Your Cipher Text is: '" + cipherText + "'");
		return originalMessage;
	}
	
	protected String encryptDES(String plainText, String key) {
		String cipherText = ""; int i=0;

		DES des = new DES(key);
		plainText = plainText.replaceAll(" ", "");
		plainText = plainText.replaceAll("\n", "");
		
		while(plainText.length()>=i){
			if(cipherText.length() != 64) {
				cipherText += des.Encrypt(plainText.substring(i, plainText.length()));
			}
			
			i+=64;
		}
		
		detailsTextArea.setText("Your DES Cipher information encryption: " + key + " is your Key.\n"
				+ "Your Original Text is: '" + plainText + "'");
		return cipherText;
	}
	
	protected String decryptDES(String cipherText, String key) {
		
		DES des = new DES(key);
		cipherText = cipherText.replaceAll(" ", "");
		cipherText = cipherText.replaceAll("\n", "");
		
		detailsTextArea.setText("Your DES Cipher information decryption: " + key + " is your Key.\n"
				+ "Your Cipher Text is: '" + cipherText + "'");
		
		return des.Decrypt(cipherText);
	}

	protected String encryptRC4(String plaintext, String key) {
		
		RC4 rc4 = new RC4(key);
		
		detailsTextArea.setText("Your RC4 information encryption: " + key + " is your Key.\n"
				+ "Your Original Text is: '" + plaintext + "'");
		return rc4.encrypt(plaintext);
	}

	private String decryptRC4(String ciphertext, String key) {
		RC4 rc4 = new RC4(key);

		detailsTextArea.setText("Your RC4 information decryption: " + key + " is your Key.\n"
				+ "Your Cipher Text is: '" + ciphertext + "'");
		return rc4.decrypt(ciphertext);
	}
	

	protected String encryptRSA(String plainText) throws Exception {
		int p = 0,q = 0;
		p= Integer.parseInt(JOptionPane.showInputDialog("Enter the first prime value",p));
		q= Integer.parseInt(JOptionPane.showInputDialog("Enter the second prime value",q));
		if(!(isPrime(p) && isPrime(q)))
			throw new Exception("p & q should be prime ");
		
		RSA rsa = new RSA(p,q);
		detailsTextArea.setText(" p : " + rsa.p +"\n q : "+ rsa.q+"\n n : "+rsa.n+"\n d : "+rsa.d+"\n z : "+rsa.z+
				"\nYour RSA information for encryption: '" + p + "' and '" + q + "' is your Prime numbers.\n"
				+ "Your Plain Text is: '" + plainText + "'");
		return rsa.encrypt(plainText);
	}

	protected String decryptRSA(String plainText) throws Exception {
		int p = 0,q = 0;
		p= Integer.parseInt(JOptionPane.showInputDialog("Enter the first prime value",p));
		q= Integer.parseInt(JOptionPane.showInputDialog("Enter the second prime value",q));
		if(!(isPrime(p) && isPrime(q)))
			throw new Exception("p & q should be prime ");
		RSA rsa = new RSA(p,q);
		
		detailsTextArea.setText(" p : " + rsa.p +"\n q : "+ rsa.q+"\n n : "+rsa.n+"\n d : "+rsa.d+"\n z : "+rsa.z+
				"\nYour RSA information for decryption: '" + p + "' and '" + q + "' is your Prime numbers.\n"
				+ "Your Cipher Text is: '" + plainText + "'");

		return rsa.decrypt(plainText);	
		}

	private boolean isPrime(int i) {
        BigInteger b = new BigInteger(String.valueOf(i)); 
        return (b.isProbablePrime(1));
        
	}
}