package application;

import java.util.regex.Pattern;

import javafx.beans.property.SimpleStringProperty;

public class Athlete_info {
	private SimpleStringProperty Unit;
	private SimpleStringProperty ID;
	private SimpleStringProperty Name;
	private SimpleStringProperty Country;
	private SimpleStringProperty Referee;
	private SimpleStringProperty Score;
	
	public Athlete_info(){
		this(null, null, null, null, null, null);
	}
	public Athlete_info(String _Unit, String _ID, String _Name, String _Country, String _Referee, String _Score) {
		Unit = new SimpleStringProperty(_Unit);
		ID = new SimpleStringProperty(_ID);
		Name = new SimpleStringProperty(_Name);
		Country = new SimpleStringProperty(_Country);
		Referee = new SimpleStringProperty(_Referee);
		Score = new SimpleStringProperty(_Score);
	}
	public String getUnit() {
		return this.Unit.get();
	}
	public void setUnit(String _Unit) {
		this.Unit.set(_Unit);
	}
	
	public String getID() {
		return this.ID.get();
	}
	public void setID(String _ID) {
		this.ID.set(_ID);
	}
	
	public String getName() {
		return this.Name.get();
	}
	public void setName(String _Name) {
		this.Name.set(_Name);
	}
	
	public String getCountry() {
		return this.Country.get();
	}
	public void setCountry(String _Age) {
		this.Country.set(_Age);
	}
	
	public String getReferee() {
		return this.Referee.get();
	}
	public void setReferee(String _Age) {
		this.Referee.set(_Age);
	}
	public String getScore() {
		if(this.Score.get() != null) {
			return this.Score.get();
		}
		else {
			return "ÔÝÎÞ";
		}
	}
	public void setScore(String _Score) {
		Pattern pattern = Pattern.compile("^[-\\+]?[0-9]*[.]?[0-9]*$");  
		if(pattern.matcher(_Score).matches()) {
			this.Score.set(_Score);
		}
	}
}
