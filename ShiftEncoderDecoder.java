import java.util.Scanner;

public class ShiftEncoderDecoder {

	private static char offsetChar;
	private final static String REFERENCE_TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()*+,-./";
	private static Scanner scanner = new Scanner(System.in);

	public ShiftEncoderDecoder(char offsetChar) {
		this.offsetChar = offsetChar;
	}

	// Get the index of the offset character in the reference table
	public static int getOffsetCharIndex(String offsetChar) {
		return REFERENCE_TABLE.indexOf(offsetChar);
	}

	//	Formula for encoding a character
	public static int encodeFormula(int indexOfCurrentChar, int offsetCharIndex) {
		return (indexOfCurrentChar - offsetCharIndex + REFERENCE_TABLE.length()) % (REFERENCE_TABLE.length());
	}

	//	Formula for decoding a character
	public static int decodeFormula(int indexOfCurrentChar, int offsetCharIndex) {
		return (indexOfCurrentChar + offsetCharIndex + REFERENCE_TABLE.length()) % (REFERENCE_TABLE.length());
	}

	// Append character found in reference table to encoded text
	public static void appendEncodedValidChar(StringBuilder encodedText, int indexOfCurrentChar, int offsetCharIndex) {
		int encodedCharIndex = encodeFormula(indexOfCurrentChar, offsetCharIndex);
		encodedText.append(REFERENCE_TABLE.charAt(encodedCharIndex));
	}

	// Append character found in reference table to decoded text
	public static void appendDecodedValidChar(StringBuilder decodedText, int indexOfCurrentChar, int offsetCharIndex) {
		int decodedCharIndex = decodeFormula(indexOfCurrentChar, offsetCharIndex);
		decodedText.append(REFERENCE_TABLE.charAt(decodedCharIndex));
	}

	//	Encoding Method
	public static String encode(String plainText) {

		StringBuilder encodedText = new StringBuilder();

		encodedText.append(offsetChar);

		int offsetCharIndex = REFERENCE_TABLE.indexOf(offsetChar);

		plainText = plainText.toUpperCase();

		for (char c : plainText.toCharArray()) {
			int indexOfCurrentChar = REFERENCE_TABLE.indexOf(c);

			if (indexOfCurrentChar != -1) {
				appendEncodedValidChar(encodedText, indexOfCurrentChar, offsetCharIndex);
			} else {
				encodedText.append(c);
			}

		}

		return encodedText.toString();

	}

	//	Decoding Method
	public static String decode(String plainText) {

		StringBuilder decodedText = new StringBuilder();
		int offsetCharIndex = REFERENCE_TABLE.indexOf(plainText.charAt(0));

		plainText = plainText.toUpperCase();
		for (char c : plainText.toCharArray()) {
			int indexOfCurrentChar = REFERENCE_TABLE.indexOf(c);

			if (indexOfCurrentChar != -1) {
				appendDecodedValidChar(decodedText, indexOfCurrentChar, offsetCharIndex);
			} else {
				decodedText.append(c);
			}

		}

		return decodedText.deleteCharAt(0).toString();

	}

	// Get the offset character from the user
	public static char userInputOffsetChar() {
		while (true) {
			System.out.print("Enter the offset character: ");
			String userInput = scanner.nextLine();
			if (userInput.length() == 1 && userInput.charAt(0) != ' ') {
				offsetChar = Character.toUpperCase(userInput.charAt(0));
				break;
			} else {
				System.out.println("Please enter a single character for the offset.");
			}
		}
		return offsetChar;
	}

	// Get the choice (E for encode, D for decode) from the user
	public static char userInputChoice() {
		char choice;
		while (true) {
			System.out.print("Enter 'E' to ENCODE or 'D' to DECODE: ");
			String userInput = scanner.nextLine();
			if (userInput.length() == 1) {
				choice = userInput.charAt(0);
				break;
			} else {
				System.out.println("Please enter a single character for the offset.");
			}
		}
		return choice;
	}

	//	Main method to interact with user inputs
	public static void main(String[] args) {
		char choice = userInputChoice();
		ShiftEncoderDecoder encoderDecoder = new ShiftEncoderDecoder(offsetChar);
		if (choice == 'E' || choice == 'e') {
			char offsetChar = userInputOffsetChar();

			System.out.print("Enter the text to encode: ");
			String toEncodeUserInputText = scanner.nextLine();
			String encoded = encoderDecoder.encode(toEncodeUserInputText);
			System.out.println("Encoded: " + encoded);
		} else if (choice == 'D' || choice == 'd') {

			System.out.print("Enter the text to decode: ");
			String toDecodeUserInputText = scanner.nextLine();
			String decoded = encoderDecoder.decode(toDecodeUserInputText);
			System.out.println("Decoded: " + decoded);
		} else {
			System.out.println("Invalid choice. Please enter 'E' or 'D' for encoding or decoding.");
		}

		scanner.close();
	}

}