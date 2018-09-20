package application;

import java.util.regex.Pattern;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AthleteCompetition_info {
	private SimpleIntegerProperty Rank;
	private SimpleStringProperty Unit;
	private SimpleStringProperty ID;
	private SimpleStringProperty Name;
	private SimpleStringProperty Country;
	private SimpleIntegerProperty Competition;
	private SimpleStringProperty Score;
	
	public AthleteCompetition_info(){
		this(-1, null, null, null, null, 0, null);
	}
	public AthleteCompetition_info(Integer _Rank, String _Unit, String _ID, String _Name, String _Country, Integer _Competition, String _Score) {
		Rank = new SimpleIntegerProperty(_Rank);
		Unit = new SimpleStringProperty(_Unit);
		ID = new SimpleStringProperty(_ID);
		Name = new SimpleStringProperty(_Name);
		Country = new SimpleStringProperty(_Country);
		Competition = new SimpleIntegerProperty(_Competition);
		Score = new SimpleStringProperty(_Score);
	}
	public Integer getRank() {
		return Rank.intValue();
	}
	public void setRank(SimpleIntegerProperty rank) {
		Rank = rank;
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

	public String getCompetition() {
		return Manager_controller.transToStringFromJno("", this.Competition.intValue());
	}
	public void setCompetition(int competition) {
		this.Competition.set(competition);
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
