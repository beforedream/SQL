package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

//import java.io.IOException;
//import java.net.URL;
//import java.util.*;


import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.JavaFXBuilderFactory;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Login_controller {
	
	private Stage Stage_login = null;
	@FXML
	private TextField TextField_login_name, TextField_login_passwd;
	
	@FXML
	private Button Button_login, Button_cancel;
	
	@FXML
	private CheckBox CheckBox_login_athlete, CheckBox_login_manager, CheckBox_login_referee;
	
	@FXML
	public void onButtonClick_login(ActionEvent event){
		String name = TextField_login_name.getText();
		String passwd = TextField_login_passwd.getText();
		if(name.equals("") || passwd.equals("")) {
			Alert a = new Alert(Alert.AlertType.INFORMATION);
			a.setTitle("ÌáÊ¾£¡");
	        a.setHeaderText(null);
	        a.setContentText("Çë¼ì²éÊÇ·ñÓÐÎ´Íê³ÉÊäÈë£¡");
	        a.show();
			return;
		}
		String SQL;
		if(CheckBox_login_athlete.isSelected()) {
			SQL = "select * from athlete where ano = " + passwd + " and aname = '" + name + "'";
			List<Map<String,Object>> rs = Sql.ExcuteSQL(SQL, "athlete", "athlete");
			if(rs.size() != 0) {
				Stage Stage_athlete = new Stage();
				FXMLLoader loader = new FXMLLoader();
		        URL location = getClass().getResource("athlete.fxml");
		    	loader.setLocation(location);
		        loader.setBuilderFactory(new JavaFXBuilderFactory());
		        Parent Parent_athlete = null;
				try {
					Parent_athlete = loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Parent_athlete.toString();
				Stage_athlete.toString();
		        Stage_athlete.setScene(new Scene(Parent_athlete, 600, 400));
		        Stage_athlete.setTitle(name);
		        Stage_athlete.show();
		        Stage_login.close();
		        
		        Athlete_controller athlete_controller = loader.getController();
		        athlete_controller.init(Stage_athlete, passwd, name);
			}
			else {
				Alert a = new Alert(Alert.AlertType.INFORMATION);
				a.setTitle("ÌáÊ¾£¡");
		        a.setHeaderText(null);
		        a.setContentText("µÇÂ½Ê§°Ü");
		        a.show();
			}
		}
		else if(CheckBox_login_manager.isSelected()) {
			if(passwd.equals("manager") && name.equals("manager")) {
				Stage Stage_manager = new Stage();
				FXMLLoader loader = new FXMLLoader();
		        URL location = getClass().getResource("manager.fxml");
		    	loader.setLocation(location);
		        loader.setBuilderFactory(new JavaFXBuilderFactory());
		        Parent Parent_manager = null;
				try {
					Parent_manager = loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Parent_manager.toString();
				Stage_manager.toString();
		        Stage_manager.setScene(new Scene(Parent_manager, 600, 400));
		        Stage_manager.setTitle("manager");
		        Stage_manager.show();
		        Stage_login.close();
		        
		        Manager_controller manager_controller = loader.getController();
		        manager_controller.init(Stage_manager);
			}
			else {
				Alert a = new Alert(Alert.AlertType.INFORMATION);
				a.setTitle("ÌáÊ¾£¡");
		        a.setHeaderText(null);
		        a.setContentText("µÇÂ½Ê§°Ü");
		        a.show();
			}
			
		}
		else {
			SQL = "select * from referee where rno = " + passwd + " and rname = '" + name + "'";
			List<Map<String,Object>> rs = Sql.ExcuteSQL(SQL, "referee", "referee");
			if(rs.size() != 0) {
				Stage Stage_referee = new Stage();
				FXMLLoader loader = new FXMLLoader();
		        URL location = getClass().getResource("referee.fxml");
		    	loader.setLocation(location);
		        loader.setBuilderFactory(new JavaFXBuilderFactory());
		        Parent Parent_referee = null;
				try {
					Parent_referee = loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Parent_referee.toString();
				Stage_referee.toString();
		        Stage_referee.setScene(new Scene(Parent_referee, 600, 400));
		        Stage_referee.setTitle(name);
		        Stage_referee.show();
		        Stage_login.close();
		        
		        Referee_controller referee_controller = loader.getController();
		        referee_controller.init(Stage_referee, passwd, name);
			}
			else {
				Alert a = new Alert(Alert.AlertType.INFORMATION);
				a.setTitle("ÌáÊ¾£¡");
		        a.setHeaderText(null);
		        a.setContentText("µÇÂ½Ê§°Ü");
		        a.show();
			}
			
		}
	}
	
	@FXML
	public void onBUttonClick_cancel(ActionEvent event) {
		if(Stage_login == null) {
			System.out.println("Some Error happend!");
		}
		else {
			Stage_login.close();
		}
	}
	
	@FXML
	public void onCheckBoxClick_athlete(ActionEvent event) {
		if(CheckBox_login_manager.isSelected()) {
			TextField_login_name.setText("ChinaAthlete1");
			TextField_login_passwd.setText("1");
			CheckBox_login_manager.setSelected(false);
		}
		else if(CheckBox_login_referee.isSelected()) {
			TextField_login_name.setText("ChinaAthlete1");
			TextField_login_passwd.setText("1");
			CheckBox_login_referee.setSelected(false);
		}
		if(!CheckBox_login_athlete.isSelected()) {
			CheckBox_login_athlete.setSelected(true);
		}
	}
	
	@FXML
	public void onCheckBoxClick_referee(ActionEvent event) {
		if(CheckBox_login_manager.isSelected()) {
			TextField_login_name.setText("Referee1");
			TextField_login_passwd.setText("1");
			CheckBox_login_manager.setSelected(false);
		}
		else if(CheckBox_login_athlete.isSelected()) {
			TextField_login_name.setText("Referee1");
			TextField_login_passwd.setText("1");
			CheckBox_login_athlete.setSelected(false);
		}
		if(!CheckBox_login_referee.isSelected()) {
			CheckBox_login_referee.setSelected(true);
		}
	}
	
	@FXML
	public void onCheckBoxClick_manager(ActionEvent event) {
		if(CheckBox_login_referee.isSelected()) {
			TextField_login_name.setText("manager");
			TextField_login_passwd.setText("manager");
			CheckBox_login_referee.setSelected(false);
		}
		else if(CheckBox_login_athlete.isSelected()) {
			TextField_login_name.setText("manager");
			TextField_login_passwd.setText("manager");
			CheckBox_login_athlete.setSelected(false);
		}
		if(!CheckBox_login_manager.isSelected()) {
			CheckBox_login_manager.setSelected(true);
		}
	}
	
	public void init(Stage _Stage_login) {
		Stage_login = _Stage_login;
	}
}
