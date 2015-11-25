package converter;

import java.util.ArrayList;

public class Converter {
	
	
	
	public static String Convert(String input, String sourceAlphabet, String targetAlphabet) throws Throwable {
		CheckAlphabetDefinition(sourceAlphabet);
		CheckAlphabetDefinition(targetAlphabet);
		CheckInput(input, sourceAlphabet);
		
		int decimalValue = ConvertFromSource(input,sourceAlphabet);
		String output = ConvertToTarget(decimalValue, targetAlphabet);
		
		return output;
	}
	private static int ConvertFromSource(String input, String sourceAlphabet) {
		// Find source values for conversion.
		int sourceBase = sourceAlphabet.length();
		char[] sourceLetters = sourceAlphabet.toCharArray();
		
		char[] inputLetters = input.toCharArray();	
		
		int decimalValue = 0;
		
		for (int i = 0; i < inputLetters.length; i++) {
			char currentChar = inputLetters[inputLetters.length-(i+1)];
			int index = 0;
			for (int j = 0; j < sourceLetters.length; j++) {
				if (sourceLetters[j] == currentChar) {
					index = j;
					break;
				}
			}
			decimalValue += index*Math.pow(sourceBase,i);
		}
		return decimalValue;
	}
	
	private static String ConvertToTarget(int decimalInput, String targetAlphabet) {
		int targetBase = targetAlphabet.length();
		char[] targetLetters = targetAlphabet.toCharArray();
		
		int decimalValue = decimalInput;
		ArrayList<Character> targetOutput = new ArrayList<Character>();
		
		while (decimalValue > 0) {
			try {
				targetOutput.add(targetLetters[decimalValue % targetBase]);
				decimalValue = decimalValue / targetBase;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String output = "";
		
		for (int i = 0; i < targetOutput.size(); i++)
			output += targetOutput.toArray()[targetOutput.size()-(i+1)];
		return output;
	}
	
	private static void CheckAlphabetDefinition(String alphabet) throws Exception {
		char[] alphabetLetters = alphabet.toCharArray();
		ArrayList<Character> uniqueLetters = new ArrayList<Character>();
		
		for (char letter : alphabetLetters) {
			if (!uniqueLetters.contains(letter)) {
				uniqueLetters.add(letter);
			}
		}
		System.out.print(uniqueLetters.size());
		if (!(uniqueLetters.size() == alphabetLetters.length)) {
			throw new Exception("Alphabet contains duplicate letters");
		}
		if (uniqueLetters.size() <= 1)
			throw new Exception("Alphabet must have atleast two letters");
	}
	
	private static void CheckInput(String input, String sourceAlphabet) throws Exception {
		char[] alphabetLetters = sourceAlphabet.toCharArray();
		char[] inputLetters = input.toCharArray();

		for (char inputLetter : inputLetters) {
			boolean inAlphabet = false;
			for (char sourceLetter : alphabetLetters) {
				if (inputLetter == sourceLetter)
					inAlphabet = true;
					continue;
			}
			if (!inAlphabet) {
				throw new Exception("Input cannot be formed from sourceAlphabet");
			}
		}
		
	}
}
