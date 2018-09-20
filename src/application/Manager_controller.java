package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Manager_controller {
	private Stage Stage_manager = null;
	@FXML
	private ChoiceBox<String> ChoiceBox_item, ChoiceBox_competition, ChoiceBox_athlete, ChoiceBox_unit;
	
	@FXML
	private Label Label_record;
	
	@FXML
	private TabPane TabPane1;
	
	public void onButtonClick_exit() {
		if(Stage_manager == null) {
			System.out.println("Some Error happend!");
			System.exit(1);
		}
		else {
			Stage_manager.close();
		}
	}
	
	private ObservableList<AthleteCompetition_info> initTable(Tab t, String name, String item) {
		System.out.println("Manager_controller : initTable");
		//set title
		t.setText(item + " : " + name);
		
		//input table
		ScrollPane Pane = new ScrollPane();
		TableView<AthleteCompetition_info> table = new TableView<AthleteCompetition_info>();
		TableColumn<AthleteCompetition_info, Integer> col_Rank = new TableColumn<AthleteCompetition_info, Integer>();
		TableColumn<AthleteCompetition_info, String>
				col_Unit = new TableColumn<AthleteCompetition_info, String>(),
				col_ID = new TableColumn<AthleteCompetition_info, String>(),
				col_Name = new TableColumn<AthleteCompetition_info, String>(),
				col_Country = new TableColumn<AthleteCompetition_info, String>(),
				col_Competition = new TableColumn<AthleteCompetition_info, String>(),
				col_Score = new TableColumn<AthleteCompetition_info, String>();
		table.getColumns().clear();
		col_Rank.setCellValueFactory(new PropertyValueFactory<AthleteCompetition_info, Integer>("Rank"));
		col_Rank.setText("名次");
		col_Unit.setCellValueFactory(new PropertyValueFactory<AthleteCompetition_info, String>("Unit")); 
		col_Unit.setText("代表国家");
		col_ID.setCellValueFactory(new PropertyValueFactory<AthleteCompetition_info, String>("ID"));//映射         
		col_ID.setText("编号");
		col_Name.setCellValueFactory(new PropertyValueFactory<AthleteCompetition_info, String>("Name")); 
		col_Name.setText("姓名");
		col_Country.setCellValueFactory(new PropertyValueFactory<AthleteCompetition_info, String>("Country")); 
		col_Country.setText("国籍");
		col_Competition.setCellValueFactory(new PropertyValueFactory<AthleteCompetition_info, String>("Competition")); 
		col_Competition.setText("比赛轮次");
		col_Score.setCellValueFactory(new PropertyValueFactory<AthleteCompetition_info, String>("Score")); 
		col_Score.setText("分数");
		table.getColumns().add(col_Rank);
		table.getColumns().add(col_Unit);
		table.getColumns().add(col_ID);
		table.getColumns().add(col_Name);
		table.getColumns().add(col_Country);
		table.getColumns().add(col_Competition);
		table.getColumns().add(col_Score);
		ObservableList<AthleteCompetition_info> list = FXCollections.observableArrayList();
		table.setItems(list);
		Pane.setContent(table);
		t.setContent(Pane);
		
		//set doubleClick event
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                	Alert alert = new Alert(AlertType.INFORMATION);
                	String _name = (String) table.getColumns().get(3).getCellObservableValue
                			(table.getSelectionModel().getSelectedIndex()).getValue();
                	String _passwd = (String) table.getColumns().get(2).getCellObservableValue
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
                	List<Map<String,Object>> rs = Sql.ExcuteSQL(SQL, "show", "show");
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
		
		return list;
	}
	
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
                	List<Map<String,Object>> rs = Sql.ExcuteSQL(SQL, "show", "show");
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
	
	public void onButtonClick_byUnit() {
		System.out.println("Manager_controller : onButtonClick_byunit()");
		Tab tab = new Tab();
		ObservableList<AthleteCompetition_info> list = null;
		if(ChoiceBox_unit.getSelectionModel().getSelectedItem() != null && ChoiceBox_item.getSelectionModel().getSelectedItem() != null) {
			list = initTable(tab, ChoiceBox_unit.getSelectionModel().getSelectedItem(), ChoiceBox_item.getSelectionModel().getSelectedItem());
		}
		TabPane1.tabClosingPolicyProperty().set(TabClosingPolicy.SELECTED_TAB);
		String SQL = "select * from (select ROW_NUMBER() over (order by dbo.GetType(jno) desc, rank asc, jno asc) as rank,\r\n" + 
				"unit, ano, aname, country, jno, score from viewRank, item where item.iname = '"
				+ ChoiceBox_item.getSelectionModel().getSelectedItem()
				+ "' and viewRank.ino = item.ino) as t1 where unit = '"
				+ ChoiceBox_unit.getSelectionModel().getSelectedItem()
				+ "'";
		List<Map<String, Object>> rs = Sql.ExcuteSQL(SQL, "show", "show");
		int i = 0;
		for(i = 0; i < rs.size(); i += 7) {
			AthleteCompetition_info info = new AthleteCompetition_info(
					Integer.valueOf((String) rs.get(i + 0).get("rank")),
					(String) rs.get(i + 1).get("unit"),
					(String) rs.get(i + 2).get("ano"),
					(String) rs.get(i + 3).get("aname"),
					(String) rs.get(i + 4).get("country"),
					Integer.valueOf((String) rs.get(i + 5).get("jno")) ,
					(String) rs.get(i + 6).get("score")
					);
			list.add(info);
		}
		TabPane1.getTabs().add(tab);
	}
	
	public void onButtonClick_byPerson() {
		System.out.println("Manager_controller : onButtonClick_byPerson()");
		if(ChoiceBox_athlete.getSelectionModel().getSelectedItem() != null) {
			Tab tab = new Tab();
			ObservableList<AthleteCompetition_info> list = null;
			if(ChoiceBox_unit.getSelectionModel().getSelectedItem() != null && ChoiceBox_item.getSelectionModel().getSelectedItem() != null) {
				list = initTable(tab, ChoiceBox_unit.getSelectionModel().getSelectedItem(), ChoiceBox_item.getSelectionModel().getSelectedItem());
			}
			TabPane1.tabClosingPolicyProperty().set(TabClosingPolicy.SELECTED_TAB);
			String SQL = "select * from (select ROW_NUMBER() over (order by dbo.GetType(jno) desc, rank asc, jno asc) as rank,\r\n" + 
					"unit, ano, aname, country, jno, score from viewRank, item where item.iname = '"
					+ ChoiceBox_item.getSelectionModel().getSelectedItem()
					+ "' and viewRank.ino = item.ino) as t1 where aname = '"
					+ ChoiceBox_athlete.getSelectionModel().getSelectedItem()
					+ "'";
			List<Map<String, Object>> rs = Sql.ExcuteSQL(SQL, "show", "show");
			AthleteCompetition_info info = new AthleteCompetition_info(
					Integer.valueOf((String) rs.get(0).get("rank")),
					(String) rs.get(1).get("unit"),
					(String) rs.get(2).get("ano"),
					(String) rs.get(3).get("aname"),
					(String) rs.get(4).get("country"),
					Integer.valueOf((String) rs.get(5).get("jno")) ,
					(String) rs.get(6).get("score")
					);
			list.add(info);
			TabPane1.getTabs().add(tab);
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Tips");
			alert.setContentText("请检查是否选中运动员");
			alert.setHeaderText(null);
			alert.showAndWait();
		}
	}
	
	public void onButtonClick_byCompetition() {
		System.out.println("Manager_controller : onButtonClick_byCompetition()");
		int temp = ChoiceBox_competition.getSelectionModel().getSelectedIndex();
		if(ChoiceBox_item.getSelectionModel().getSelectedItem() != null
			&& ChoiceBox_competition.getSelectionModel().getSelectedItem() != null) {
			String SQL = "select iname, unit, ano, aname, country, rname, score, jno "
					+ "from viewCompetition as view1 where (jno = '"
					+ (temp < 8 ? temp + 1 : (temp < 12 ? temp + 9 : (temp < 14 ? temp + 13 : temp + 15)))
					+ "' or jno = '"
					+ (temp < 8 ? temp + 9 : (temp < 12 ? temp + 13 : (temp < 14 ? temp + 15 : temp + 16)))
					+ "') and iname = '"
					+ ChoiceBox_item.getSelectionModel().getSelectedItem()
					+ "' order by jno asc, ano asc";
			List<Map<String,Object>> rs = Sql.ExcuteSQL(SQL, "show", "show");
			Tab t = new Tab();
			loadTable(t, rs, 0, 1, 2, 3, 4, 5, 6, 8, 
					(String) rs.get(0).get("iname"), Integer.valueOf((String) rs.get(7).get("jno"))
					);
			TabPane1.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
			TabPane1.getTabs().add(t);
		}
		
	}
	
	public void onChoiceBoxEdit_unit(String Unit) {
		System.out.println("Manager_controller : onChoiceBox_unit()");
		ChoiceBox_athlete.getItems().clear();
		int i = 0;
		String SQL = null;
		List<Map<String, Object>> rs = null;
		SQL = "select aname from unit, athlete where unit.country = '" 
		+ Unit + "' and unit.uno = athlete.uno";
		rs = Sql.ExcuteSQL(SQL, "show", "show");
		List<String> options_athlete = new ArrayList<String>();
		for(i = 0; i < rs.size(); i++) {
			options_athlete.add((String) rs.get(i).get("aname"));
		}
		ChoiceBox_athlete.getItems().addAll(options_athlete);
	}
	
	public void init(Stage _Stage_manager) {
		int i = 0;
		//init ChoiceBox_item
		List<String> options_item = new ArrayList<String>();
		String SQL = "select iname from item";
		List<Map<String, Object>> rs = Sql.ExcuteSQL(SQL, "show", "show");
		for(i = 0; i < rs.size(); i++) {
			options_item.add((String) rs.get(i).get("iname"));
		}
		ChoiceBox_item.getItems().addAll(options_item);
		ChoiceBox_item.setTooltip(new Tooltip("Select Event"));
		ChoiceBox_item.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->{  
			String string = ChoiceBox_item.getItems().get(newv.intValue());
			String sql = "select record from item where iname = '"
					+ string
					+ "'";
			List<Map<String, Object>> Rs = Sql.ExcuteSQL(sql, "show", "show");
			if(Rs.size() != 0) {
				Label_record.setText((String) Rs.get(0).get("record") + " s");
			}
		});
		ChoiceBox_item.setValue(ChoiceBox_item.getItems().get(0));
		
		//init ChoiceBox_competition
		List<String> options_competition = new ArrayList<String>();
		for(i = 1; i <= 30; i++) {
			options_competition.add(transToStringFromJno("", i));
			if(i == 8) {
				i = 16;
			}
			else if(i == 20) {
				i = 24;
			}
			else if(i == 26) {
				i = 28;
			}
			else if(i == 29) {
				break;
			}
		}
		ChoiceBox_competition.getItems().addAll(options_competition);
		ChoiceBox_competition.setTooltip(new Tooltip("Select Competition"));
		ChoiceBox_competition.setValue(ChoiceBox_competition.getItems().get(0));
		
		//init ChoiceBox_unit
		List<String> options_unit = new ArrayList<String>();
		SQL = "select country from unit";
		rs = Sql.ExcuteSQL(SQL, "show", "show");
		for(i = 0; i < rs.size(); i++) {
			options_unit.add((String) rs.get(i).get("country"));
		}
		ChoiceBox_unit.getItems().addAll(options_unit);
		ChoiceBox_unit.setTooltip(new Tooltip("Select Unit"));
		ChoiceBox_unit.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->{  
			onChoiceBoxEdit_unit(ChoiceBox_unit.getItems().get(newv.intValue())); 
			ChoiceBox_athlete.setValue(ChoiceBox_athlete.getItems().get(0));
		});
		ChoiceBox_unit.setValue(ChoiceBox_unit.getItems().get(0));
		
		ChoiceBox_athlete.setTooltip(new Tooltip("Select Athlete"));
		Stage_manager = _Stage_manager;
	}
	
	public static String transToStringFromJno(String item, int jno) {
		String title = null;
		if(jno <= 16 && jno > 0) {
			switch(jno % 8){
			case 1: title = item + " 1/8 match - A group match"; break;
			case 2: title = item + " 1/8 match - B group match"; break;
			case 3: title = item + " 1/8 match - C group match"; break;
			case 4: title = item + " 1/8 match - D group match"; break;
			case 5: title = item + " 1/8 match - E group match"; break;
			case 6: title = item + " 1/8 match - F group match"; break;
			case 7: title = item + " 1/8 match - G group match"; break;
			case 0: title = item + " 1/8 match - H group match"; break;
			}
		}
		else if(jno <= 24 && jno > 16) {
			switch(jno % 4){
			case 1: title = item + " 1/4 match - A group match"; break;
			case 2: title = item + " 1/4 match - B group match"; break;
			case 3: title = item + " 1/4 match - C group match"; break;
			case 0: title = item + " 1/4 match - D group match"; break;
			}
		}
		else if(jno <= 28 && jno > 24) {
			switch(jno % 2){
			case 1: title = item + " 1/2 match - A group match"; break;
			case 0: title = item + " 1/2 match - B group match"; break;
			}
		}
		else if(jno <= 30 && jno > 28) {
			title = item + " finals";
		}
		return title;
	}
}
