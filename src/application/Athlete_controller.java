package application;

import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.JavaFXBuilderFactory;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
public class Athlete_controller {
	private Stage Stage_athlete = null;
	private String name = null, passwd = null;
	
	@FXML
	private Label Label_name, Label_ID, Label_height, 
	Label_weight, Label_sex, Label_country, Label_age, Label_unit;
	
	@FXML
	private TabPane TabPane1;
	
	private void loadTable(Tab t, List<Map<String,Object>> rs ,int baseIndex, int unitIndex, int idIndex,
			int nameIndex,int countryIndex, int refereeIndex, int scoreIndex, int cycleNum, String item, int jno) {
		System.out.println("Athlete_controller : loadTable");
		ObservableList<Athlete_info> list = FXCollections.observableArrayList();
		//set title
		String title = Manager_controller.transToStringFromJno(item, jno);
		t.setText(title);
		
		//input table
		ScrollPane Pane = new ScrollPane();
		TableView<Athlete_info> table = new TableView<Athlete_info>();
		TableColumn<Athlete_info, String>
				col_Unit = new TableColumn<Athlete_info, String>(),
				col_ID = new TableColumn<Athlete_info, String>(),
				col_Name = new TableColumn<Athlete_info, String>(),
				col_Country = new TableColumn<Athlete_info, String>(),
				col_Referee = new TableColumn<Athlete_info, String>(),
				col_Score = new TableColumn<Athlete_info, String>();
		table.getColumns().clear();
		col_Unit.setCellValueFactory(new PropertyValueFactory<Athlete_info, String>("Unit")); 
		col_Unit.setText("代表国家");
		col_ID.setCellValueFactory(new PropertyValueFactory<Athlete_info, String>("ID"));//映射         
		col_ID.setText("编号");
		col_Name.setCellValueFactory(new PropertyValueFactory<Athlete_info, String>("Name")); 
		col_Name.setText("姓名");
		col_Country.setCellValueFactory(new PropertyValueFactory<Athlete_info, String>("Country")); 
		col_Country.setText("国籍");
		col_Referee.setCellValueFactory(new PropertyValueFactory<Athlete_info, String>("Referee")); 
		col_Referee.setText("裁判");
		col_Score.setCellValueFactory(new PropertyValueFactory<Athlete_info, String>("Score")); 
		col_Score.setText("分数");
		table.getColumns().add(col_Unit);
		table.getColumns().add(col_ID);
		table.getColumns().add(col_Name);
		table.getColumns().add(col_Country);
		table.getColumns().add(col_Referee);
		table.getColumns().add(col_Score);
		int i = 0;
		for(i = 0; i < 8; i++) {
			Athlete_info info = new Athlete_info(
					(String)rs.get(baseIndex + unitIndex + cycleNum * i).get("unit"),
					(String)rs.get(baseIndex + idIndex + cycleNum * i).get("ano"),
					(String)rs.get(baseIndex + nameIndex + cycleNum * i).get("aname"),
					(String)rs.get(baseIndex + countryIndex + cycleNum * i).get("country"),
					(String)rs.get(baseIndex + refereeIndex + cycleNum * i).get("rname"),
					(String)rs.get(baseIndex + scoreIndex + cycleNum * i).get("score")
					);
			list.add(info);
		}
		table.setItems(list);
		Pane.setContent(table);
		t.setContent(Pane);
		
		//set doubleClick event
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                	Alert alert = new Alert(AlertType.INFORMATION);
                	String _name = (String) table.getColumns().get(2).getCellObservableValue
                			(table.getSelectionModel().getSelectedIndex()).getValue();
                	String _passwd = (String) table.getColumns().get(1).getCellObservableValue
                			(table.getSelectionModel().getSelectedIndex()).getValue();
                	String SQL = null;
                	if(item.equals("200 meter dash")) {
                		System.out.println(item);
                		 SQL = "select ano, unit.country as unit, aname, sex, age, h, w, athlete.country,"
                    			+ " lastScore2 as lastScore from athlete, unit where ano = "
                    			+ _passwd + " and aname = '" + _name + "' and athlete.uno = unit.uno";
                	}
                	else if(item.equals("100 meter dash")) {
                		System.out.println(item);
                		SQL = "select ano, unit.country as unit, aname, sex, age, h, w, athlete.country,"
                    			+ " lastScore1 as lastScore from athlete, unit where ano = "
                    			+ _passwd + " and aname = '" + _name + "' and athlete.uno = unit.uno";
                	}
                	List<Map<String,Object>> rs = Sql.ExcuteSQL(SQL, "athlete", "athlete");
        			String ID = null, unit = null, name = null, sex = null, age = null, height = null, weight = null,
        					country = null, lastScore = null;
        			if(rs.size() != 0) {
        				ID = (String)rs.get(0).get("ano");
        				unit = (String)rs.get(1).get("unit");
        				name = (String)rs.get(2).get("aname");
	                	alert.setTitle(name + "'s personal information");
	                	alert.setHeaderText(null);
        				if(((String)rs.get(3).get("sex")).equals("1")) {
        					sex = "男";
        				}
        				else {
        					sex = "女";
        				}
        				age = (String)rs.get(4).get("age");
        				height = (String) rs.get(5).get("h");
        				weight = (String)rs.get(6).get("w");
        				country = (String)rs.get(7).get("country");
        				if(rs.get(8).get("lastScore") == null) {
        					lastScore = "暂无";
        				}
        				else {
        					lastScore = (String)rs.get(8).get("lastScore");
        				}
        			}
        			else {
        				System.out.println("Athlete_controller.init Wrong!");
        				System.exit(1);
        			}
        			alert.setContentText(
        					"运动员编号:\t" + ID + "\n" + 
        					"代表国家:\t" + unit + "\n" +
        					"姓名:\t" + name + "\n" + 
        					"性别:\t" + sex + "\n" +
        					"年龄:\t" + age + " 岁\n" +
        					"身高:\t" + height + "CM\n" + 
        					"重量:\t" + weight + "KG\n" +
        					"国籍:\t" + country + "\n" +
        					"上轮成绩:\t" + lastScore + "\n");
                	alert.showAndWait();
                }
            }
        });
	}
	
	private void fresh() {
		System.out.println("Athlete_controller : fresh()");
		String SQL = "select iname, unit, ano, aname, country, rname, score, jno "
				+ "from viewCompetition as view1 where jno in (select jno from viewCompetition as view2"
				+ " where view2.ano = " + passwd
				+ ") order by jno asc, ano asc";
		
		int i = 0;
		List<Map<String,Object>> rs = Sql.ExcuteSQL(SQL, "athlete", "athlete");
		TabPane1.getTabs().clear();
		for(i = 0; i < rs.size() / 64 ; i++) {
			Tab t = new Tab();
			loadTable(t, rs, 64 * i, 1, 2, 3, 4, 5, 6, 8, 
					(String) rs.get(i * 8 * 8).get("iname"), Integer.valueOf((String) rs.get(i * 64 + 7).get("jno"))
					);
			TabPane1.getTabs().add(t);
		}
	}
	
	public void onButtonClick_exit() {
		if(Stage_athlete == null) {
			System.out.println("Some Error happend!");
		}
		else {
			Stage_athlete.close();
		}
	}
	
	public void onButtonClick_fresh() {	
		fresh();
	}
	public void init(Stage _Stage_athlete, String _passwd, String _name) {
		name = _name;
		passwd = _passwd;
		Stage_athlete = _Stage_athlete;
		String SQL = "select ano, unit.country as unit, aname, sex, age, h, w, athlete.country from athlete, unit where ano = "
		+ passwd + " and aname = '" + name + "' and athlete.uno = unit.uno";
		List<Map<String,Object>> rs = Sql.ExcuteSQL(SQL, "athlete", "athlete");
		if(rs.size() != 0) {
			Label_ID.setText((String)rs.get(0).get("ano"));
			Label_unit.setText((String)rs.get(1).get("unit"));
			Label_name.setText((String)rs.get(2).get("aname"));
			if(((String)rs.get(3).get("sex")).equals("1")) {
				Label_sex.setText("男");
			}
			else {
				Label_sex.setText("女");
			}
			Label_age.setText((String)rs.get(4).get("age"));
			Label_height.setText((String) rs.get(5).get("h"));
			Label_weight.setText((String)rs.get(6).get("w"));
			Label_country.setText((String)rs.get(7).get("country"));
			TabPane1.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
		}
		else {
			System.out.println("Athlete_controller.init Wrong!");
			System.exit(1);
		}
		fresh();
	}
}
