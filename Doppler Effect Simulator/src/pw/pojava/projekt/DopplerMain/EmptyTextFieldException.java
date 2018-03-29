package pw.pojava.projekt.DopplerMain;

import javax.swing.JTextField;

//wyjatek - jesli pole tekstowe jest puste
	public class EmptyTextFieldException extends Exception{	
		EmptyTextFieldException(JTextField triedTextField){			
			triedTextField.setText("");
		}		
		public String toString(){
			return "Wyjatek! Pole tekstowe jest puste - wpisuje zero.";
		}
	}
