package otherForm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateAndTime {

	private SimpleDateFormat formatter;
	private Date date;
	String s;
	String[] sr;
	public static void main(String[] args) {
		 new DateAndTime();

	}
	public DateAndTime() {
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    date = new Date();
	    s=formatter.format(date);
	    sr=s.split(" ");
	}
	public String getDateAndTime() {
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    date = new Date();
	    s=formatter.format(date);
	    return s;
	}
	public String getTime() {
		String[] t=sr[1].split(":");
		int hour=Integer.parseInt(t[0]);
		int hour1 = 0;
		if(hour>12) {
			hour1=hour-12;	
			s=hour1+":"+t[1]+":"+t[2]+" PM";
		}
		else {
			s=sr[1]+" AM";
		}
		return s;
	}
	public String getDate() {
		return sr[0];
	}
	public java.sql.Date getDate1(){
		Calendar calendar = Calendar.getInstance();
		java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
		return ourJavaDateObject;
	}
}
