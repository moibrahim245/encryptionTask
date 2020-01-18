package encryptionCiphers;

import java.util.ArrayList;

public class Feistel {
    private int initialKey;
    private String functionOperator;
    private int totalRound;

    private ArrayList<String> keys = new ArrayList<>();

    Feistel(int numberOfRounds) {
        this.totalRound = numberOfRounds;

        keys.add("1110");
        keys.add("0100");
        keys.add("1101");
        keys.add("0001");
        keys.add("0010");
        keys.add("1111");
        keys.add("1011");
        keys.add("1000");
        keys.add("0011");
        keys.add("1010");
        keys.add("0110");
        keys.add("1100");
        keys.add("0101");
        keys.add("1001");
        keys.add("0000");
        keys.add("0111");

    }

    public void setInitialKey(int initialKey) {
        this.initialKey = initialKey;
    }

    public void setFunctionOperator(String functionOperator) {
        this.functionOperator = functionOperator;
    }

    public String encrypt(String message) {

        int messageMid = message.length() / 2;
        String left = message.substring(0, messageMid);
        String right = message.substring(messageMid);

        for (int roundIndex = 0; roundIndex < totalRound; roundIndex++) {
            String temp = right;
            String functionText = function(right, roundIndex);
            right = XOR(left, functionText);
            left = temp;
        }
        return left + "" + right;
    }

    public String decrypt(String encryptedMessage) {
        int messageMid = encryptedMessage.length() / 2;
        String left = encryptedMessage.substring(0, messageMid);
        String right = encryptedMessage.substring(messageMid);

        for (int roundIndex = 0; roundIndex < totalRound; roundIndex++) {
            String temp = left;
            String functionText = function(left, totalRound - roundIndex - 1);
            left = XOR(right, functionText);
            right = temp;
        }

        return left + "" + right;
    }

    private String function(String right, int roundIndex) {
        String currentKey = getSubKey(roundIndex);
        String encryptedText = "";

        switch (functionOperator) {
            case "AND":
                encryptedText = AND(right, currentKey);
                break;
            case "OR":
                encryptedText = OR(right, currentKey);
                break;
        }
        return encryptedText;
    }

    private String getSubKey(int roundIndex) {
        int x = (initialKey + roundIndex) % 16;

        return keys.get(x);
    }

    private String AND(String left, String right) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < left.length(); i++) {
            stringBuilder.append((left.charAt(i) - '0') & (right.charAt(i) - '0'));
        }
        return stringBuilder.toString();
    }

    private String OR(String left, String right) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < left.length(); i++) {
            stringBuilder.append((left.charAt(i) - '0') | (right.charAt(i) - '0'));
        }
        return stringBuilder.toString();
    }

    private String XOR(String left, String right) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < left.length(); i++) {
            stringBuilder.append((left.charAt(i) - '0') ^ (right.charAt(i) - '0'));
        }
        return stringBuilder.toString();
    }
    
}
