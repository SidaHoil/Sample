package otherForm;

import java.awt.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ControlInput {
	String string = null;
	int number = 0;

	public boolean EmainControll(String email) {// correct
		boolean status = false;
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches())
			status = true;
		else
			status = false;
		return status;
	}

	public boolean PhoneControll(String phone, String n) {// correct
		boolean status = false;
		Pattern parttern = Pattern.compile("\\d{3}\\d{" + n + "}");
		Matcher matcher = parttern.matcher(phone);
		if (matcher.matches())
			status = true;
		else
			status = false;
		return status;
	}

	public String AllowString(String text, String s) {
		char ch[] = text.toCharArray();
		String str = "";
		for (int i = 0; i < text.length(); i++) {
			if (ch[i] >= (char) 65 && ch[i] <= (char) 90 || ch[i] <= (char) 97 && ch[i] >= (char) 122) {
				str += ch[i];
				System.out.println(ch[i]);
			} else {
				return null;
			}
		}
		return str;
	}

	public String AllowString(String text) {
		char ch[] = text.toCharArray();
		String str = "";
		for (int i = 0; i < text.length(); i++) {
			if (ch[i] >= (char) 65 && ch[i] <= (char) 90 || ch[i] <= (char) 97 && ch[i] >= (char) 122) {
				str += ch[i];
			} else {
				return null;
			}
		}
		return str;
	}

	public String OnlyNumber(String text, String s) {
		char ch[] = text.toCharArray();
		String str = "";
		for (int i = 0; i < text.length(); i++) {
			if (ch[i] >= (char) 0 && ch[i] <= 9) {
				str += ch[i];
			} else {
				return null;
			}
		}
		return str;
	}

	public void doOnNumberInput(char store1, JTextField jTextField1, JLabel jLabel1) {
		if (store1 == '1' | store1 == '2' | store1 == '3' | store1 == '4' | store1 == '5' | store1 == '5'
				| store1 == '6' | store1 == '7' | store1 == '8' | store1 == '9' | store1 == '0') {
			jLabel1.setText("");
		} else if (store1 == 'a' | store1 == 'A' | store1 == 'b' | store1 == 'B' | store1 == 'c' | store1 == 'C'
				| store1 == 'd' | store1 == 'D' | store1 == 'e' | store1 == 'E' | store1 == 'f' | store1 == 'F'
				| store1 == 'g' | store1 == 'G' | store1 == 'h' | store1 == 'H' | store1 == 'i' | store1 == 'I'
				| store1 == 'j' | store1 == 'J' | store1 == 'k' | store1 == 'K' | store1 == 'l' | store1 == 'L'
				| store1 == 'm' | store1 == 'M' | store1 == 'n' | store1 == 'N' | store1 == 'o' | store1 == 'O'
				| store1 == 'p' | store1 == 'P' | store1 == 'q' | store1 == 'Q' | store1 == 'r' | store1 == 'R'
				| store1 == 's' | store1 == 'S' | store1 == 't' | store1 == 'T' | store1 == 'u' | store1 == 'U'
				| store1 == 'v' | store1 == 'V' | store1 == 'w' | store1 == 'W' | store1 == 'x' | store1 == 'X'
				| store1 == 'y' | store1 == 'Y' | store1 == 'z' | store1 == 'Z' | store1 == '`' | store1 == '|'
				| store1 == '-' | store1 == '_' | store1 == '=' | store1 == '+' | store1 == '*' | store1 == '&'
				| store1 == '%' | store1 == '^' | store1 == '$' | store1 == '!' | store1 == '~' | store1 == '>'
				| store1 == '?' | store1 == '<' | store1 == '{' | store1 == '}' | store1 == '[' | store1 == ']'
				| store1 == '#' | store1 == '@' | store1 == '.') {

			jLabel1.setText("You can input Number only!!!");
			String replace = jTextField1.getText().toString();
			jTextField1.setText(replace.substring(0, replace.length() - 1));
			//
		} // else
			// jLabel1.setText("You shouldn't press function key.");
	}

	public void doOnFloatInput(char store1, JTextField jTextField1, JLabel jLabel1) {
		if (store1 == '1' | store1 == '2' | store1 == '3' | store1 == '4' | store1 == '5' | store1 == '5'
				| store1 == '6' | store1 == '7' | store1 == '8' | store1 == '9' | store1 == '0' | store1 == '.') {
			jLabel1.setText("");
		} else if (store1 == 'a' | store1 == 'A' | store1 == 'b' | store1 == 'B' | store1 == 'c' | store1 == 'C'
				| store1 == 'd' | store1 == 'D' | store1 == 'e' | store1 == 'E' | store1 == 'f' | store1 == 'F'
				| store1 == 'g' | store1 == 'G' | store1 == 'h' | store1 == 'H' | store1 == 'i' | store1 == 'I'
				| store1 == 'j' | store1 == 'J' | store1 == 'k' | store1 == 'K' | store1 == 'l' | store1 == 'L'
				| store1 == 'm' | store1 == 'M' | store1 == 'n' | store1 == 'N' | store1 == 'o' | store1 == 'O'
				| store1 == 'p' | store1 == 'P' | store1 == 'q' | store1 == 'Q' | store1 == 'r' | store1 == 'R'
				| store1 == 's' | store1 == 'S' | store1 == 't' | store1 == 'T' | store1 == 'u' | store1 == 'U'
				| store1 == 'v' | store1 == 'V' | store1 == 'w' | store1 == 'W' | store1 == 'x' | store1 == 'X'
				| store1 == 'y' | store1 == 'Y' | store1 == 'z' | store1 == 'Z' | store1 == '`' | store1 == '|'
				| store1 == '-' | store1 == '_' | store1 == '=' | store1 == '+' | store1 == '*' | store1 == '&'
				| store1 == '%' | store1 == '^' | store1 == '$' | store1 == '!' | store1 == '~' | store1 == '>'
				| store1 == '?' | store1 == '<' | store1 == '{' | store1 == '}' | store1 == '[' | store1 == ']'
				| store1 == '#' | store1 == '@') {

			jLabel1.setText("You can input Number only!!!");
			String replace = jTextField1.getText().toString();
			jTextField1.setText(replace.substring(0, replace.length() - 1));
			//
		} // else
			// jLabel1.setText("You shouldn't press function key.");
	}

	public void doOnTextInput(char store2, JTextField jTextField2, JLabel jLabel1) {

		if (store2 == 'a' | store2 == 'A' | store2 == 'b' | store2 == 'B' | store2 == 'c' | store2 == 'C'
				| store2 == 'd' | store2 == 'D' | store2 == 'e' | store2 == 'E' | store2 == 'f' | store2 == 'F'
				| store2 == 'g' | store2 == 'G' | store2 == 'h' | store2 == 'H' | store2 == 'i' | store2 == 'I'
				| store2 == 'j' | store2 == 'J' | store2 == 'k' | store2 == 'K' | store2 == 'l' | store2 == 'L'
				| store2 == 'm' | store2 == 'M' | store2 == 'n' | store2 == 'N' | store2 == 'o' | store2 == 'O'
				| store2 == 'p' | store2 == 'P' | store2 == 'q' | store2 == 'Q' | store2 == 'r' | store2 == 'R'
				| store2 == 's' | store2 == 'S' | store2 == 't' | store2 == 'T' | store2 == 'u' | store2 == 'U'
				| store2 == 'v' | store2 == 'V' | store2 == 'w' | store2 == 'W' | store2 == 'x' | store2 == 'X'
				| store2 == 'y' | store2 == 'Y' | store2 == 'z' | store2 == 'Z') {
			jLabel1.setText("");
		} else if (store2 == '1' | store2 == '2' | store2 == '3' | store2 == '4' | store2 == '5' | store2 == '5'
				| store2 == '6' | store2 == '7' | store2 == '8' | store2 == '9' | store2 == '0' | store2 == '`'
				| store2 == '|' | store2 == '-' | store2 == '_' | store2 == '=' | store2 == '+' | store2 == '*'
				| store2 == '&' | store2 == '%' | store2 == '^' | store2 == '$' | store2 == '!' | store2 == '~'
				| store2 == '>' | store2 == '?' | store2 == '<' | store2 == '{' | store2 == '}' | store2 == '['
				| store2 == ']' | store2 == '#' | store2 == '@') {
			jLabel1.setText("You can input text only!!!");
			String replace = jTextField2.getText().toString();
			jTextField2.setText(replace.substring(0, replace.length() - 1));
		}

	}
	public void doOnTextInput(char store2, JComboBox jTextField2, JLabel jLabel1) {

		if (store2 == 'a' | store2 == 'A' | store2 == 'b' | store2 == 'B' | store2 == 'c' | store2 == 'C'
				| store2 == 'd' | store2 == 'D' | store2 == 'e' | store2 == 'E' | store2 == 'f' | store2 == 'F'
				| store2 == 'g' | store2 == 'G' | store2 == 'h' | store2 == 'H' | store2 == 'i' | store2 == 'I'
				| store2 == 'j' | store2 == 'J' | store2 == 'k' | store2 == 'K' | store2 == 'l' | store2 == 'L'
				| store2 == 'm' | store2 == 'M' | store2 == 'n' | store2 == 'N' | store2 == 'o' | store2 == 'O'
				| store2 == 'p' | store2 == 'P' | store2 == 'q' | store2 == 'Q' | store2 == 'r' | store2 == 'R'
				| store2 == 's' | store2 == 'S' | store2 == 't' | store2 == 'T' | store2 == 'u' | store2 == 'U'
				| store2 == 'v' | store2 == 'V' | store2 == 'w' | store2 == 'W' | store2 == 'x' | store2 == 'X'
				| store2 == 'y' | store2 == 'Y' | store2 == 'z' | store2 == 'Z') {
			jLabel1.setText("");
		} else if (store2 == '1' | store2 == '2' | store2 == '3' | store2 == '4' | store2 == '5' | store2 == '5'
				| store2 == '6' | store2 == '7' | store2 == '8' | store2 == '9' | store2 == '0' | store2 == '`'
				| store2 == '|' | store2 == '-' | store2 == '_' | store2 == '=' | store2 == '+' | store2 == '*'
				| store2 == '&' | store2 == '%' | store2 == '^' | store2 == '$' | store2 == '!' | store2 == '~'
				| store2 == '>' | store2 == '?' | store2 == '<' | store2 == '{' | store2 == '}' | store2 == '['
				| store2 == ']' | store2 == '#' | store2 == '@') {
			jLabel1.setText("You can input text only!!!");
//			String replace = jTextField2.getText().toString();
//			jTextField2.setText(replace.substring(0, replace.length() - 1));
		}

	}

	public void Email(char store2, JTextField jTextField2, JLabel jLabel1) {

		if (store2 == 'a' | store2 == 'A' | store2 == 'b' | store2 == 'B' | store2 == 'c' | store2 == 'C'
				| store2 == 'd' | store2 == 'D' | store2 == 'e' | store2 == 'E' | store2 == 'f' | store2 == 'F'
				| store2 == 'g' | store2 == 'G' | store2 == 'h' | store2 == 'H' | store2 == 'i' | store2 == 'I'
				| store2 == 'j' | store2 == 'J' | store2 == 'k' | store2 == 'K' | store2 == 'l' | store2 == 'L'
				| store2 == 'm' | store2 == 'M' | store2 == 'n' | store2 == 'N' | store2 == 'o' | store2 == 'O'
				| store2 == 'p' | store2 == 'P' | store2 == 'q' | store2 == 'Q' | store2 == 'r' | store2 == 'R'
				| store2 == 's' | store2 == 'S' | store2 == 't' | store2 == 'T' | store2 == 'u' | store2 == 'U'
				| store2 == 'v' | store2 == 'V' | store2 == 'w' | store2 == 'W' | store2 == 'x' | store2 == 'X'
				| store2 == 'y' | store2 == 'Y' | store2 == 'z' | store2 == 'Z' | store2 == '1' | store2 == '2'
				| store2 == '3' | store2 == '4' | store2 == '5' | store2 == '5' | store2 == '6' | store2 == '7'
				| store2 == '8' | store2 == '9' | store2 == '0' | store2 == '@' | store2 == '.' | store2 == '_') {
			jLabel1.setText("");
		} else if (store2 == '`' | store2 == '|' | store2 == '-' | store2 == '=' | store2 == '+' | store2 == '*'
				| store2 == '&' | store2 == '%' | store2 == '^' | store2 == '$' | store2 == '!' | store2 == '~'
				| store2 == '>' | store2 == '?' | store2 == '<' | store2 == '{' | store2 == '}' | store2 == '['
				| store2 == ']' | store2 == '#') {
			jLabel1.setText("You can input text only!!!");
			String replace = jTextField2.getText().toString();
			jTextField2.setText(replace.substring(0, replace.length() - 1));
		}
	}

	public void doOnTextAre(char store2, JTextArea jTextField2, JLabel jLabel1) {

		if (store2 == 'a' | store2 == 'A' | store2 == 'b' | store2 == 'B' | store2 == 'c' | store2 == 'C'
				| store2 == 'd' | store2 == 'D' | store2 == 'e' | store2 == 'E' | store2 == 'f' | store2 == 'F'
				| store2 == 'g' | store2 == 'G' | store2 == 'h' | store2 == 'H' | store2 == 'i' | store2 == 'I'
				| store2 == 'j' | store2 == 'J' | store2 == 'k' | store2 == 'K' | store2 == 'l' | store2 == 'L'
				| store2 == 'm' | store2 == 'M' | store2 == 'n' | store2 == 'N' | store2 == 'o' | store2 == 'O'
				| store2 == 'p' | store2 == 'P' | store2 == 'q' | store2 == 'Q' | store2 == 'r' | store2 == 'R'
				| store2 == 's' | store2 == 'S' | store2 == 't' | store2 == 'T' | store2 == 'u' | store2 == 'U'
				| store2 == 'v' | store2 == 'V' | store2 == 'w' | store2 == 'W' | store2 == 'x' | store2 == 'X'
				| store2 == 'y' | store2 == 'Y' | store2 == 'z' | store2 == 'Z' | store2 == '1' | store2 == '2'
				| store2 == '3' | store2 == '4' | store2 == '5' | store2 == '5' | store2 == '6' | store2 == '7'
				| store2 == '8' | store2 == '9' | store2 == '0') {
			jLabel1.setText("");
		} else if (store2 == '`' | store2 == '|' | store2 == '-' | store2 == '_' | store2 == '=' | store2 == '+'
				| store2 == '*' | store2 == '&' | store2 == '%' | store2 == '^' | store2 == '$' | store2 == '!'
				| store2 == '~' | store2 == '>' | store2 == '?' | store2 == '<' | store2 == '{' | store2 == '}'
				| store2 == '[' | store2 == ']' | store2 == '#' | store2 == '@') {
			jLabel1.setText("You can input text only!!!");
			String replace = jTextField2.getText().toString();
			jTextField2.setText(replace.substring(0, replace.length() - 1));
		}

	}

	public void doOnNumberInput(char store1, JComboBox cboColorName, JLabel jLabel1) {
		if (store1 == '1' | store1 == '2' | store1 == '3' | store1 == '4' | store1 == '5' | store1 == '5'
				| store1 == '6' | store1 == '7' | store1 == '8' | store1 == '9' | store1 == '0') {
			jLabel1.setText("");
		} else if (store1 == 'a' | store1 == 'A' | store1 == 'b' | store1 == 'B' | store1 == 'c' | store1 == 'C'
				| store1 == 'd' | store1 == 'D' | store1 == 'e' | store1 == 'E' | store1 == 'f' | store1 == 'F'
				| store1 == 'g' | store1 == 'G' | store1 == 'h' | store1 == 'H' | store1 == 'i' | store1 == 'I'
				| store1 == 'j' | store1 == 'J' | store1 == 'k' | store1 == 'K' | store1 == 'l' | store1 == 'L'
				| store1 == 'm' | store1 == 'M' | store1 == 'n' | store1 == 'N' | store1 == 'o' | store1 == 'O'
				| store1 == 'p' | store1 == 'P' | store1 == 'q' | store1 == 'Q' | store1 == 'r' | store1 == 'R'
				| store1 == 's' | store1 == 'S' | store1 == 't' | store1 == 'T' | store1 == 'u' | store1 == 'U'
				| store1 == 'v' | store1 == 'V' | store1 == 'w' | store1 == 'W' | store1 == 'x' | store1 == 'X'
				| store1 == 'y' | store1 == 'Y' | store1 == 'z' | store1 == 'Z' | store1 == '`' | store1 == '|'
				| store1 == '-' | store1 == '_' | store1 == '=' | store1 == '+' | store1 == '*' | store1 == '&'
				| store1 == '%' | store1 == '^' | store1 == '$' | store1 == '!' | store1 == '~' | store1 == '>'
				| store1 == '?' | store1 == '<' | store1 == '{' | store1 == '}' | store1 == '[' | store1 == ']'
				| store1 == '#' | store1 == '@' | store1 == '.') {

			 jLabel1.setText("You can input Number only!!!");
			// String replace=cboColorName.getText().toString();
			// cboColorName.setText(replace.substring(0, replace.length() - 1));
			//
		}

	}

}
