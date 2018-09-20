package application;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Referee_controller {
	private Stage Stage_referee = null;
	private String name = null, passwd = null;
	private StringBuffer SQLBuffer = new StringBuffer();
	
	@FXML
	private Button Button_exit;
	
	@FXML
	private TabPane TabPane1;
	
	@FXML
	private Label Label_ID, Label_name, Label_country;
	
	public void onButtonClick_exit() {
		if(Stage_referee == null) {
			System.out.println("Some Error happend!");
		}
		else {
			Stage_referee.close();
		}
	}
	
	public void onButtonClick_confirm() {
		if(SQLBuffer != null) {
			System.out.println(SQLBuffer);
			Sql.DoSQL(SQLBuffer.toString(), "referee", "referee");
			SQLBuffer.delete(0, SQLBuffer.length());
			fresh();
		}
//		else {
//			fresh();
//		}
	}
	
	public void onButtonClick_fresh() {
		fresh();
	}
	
	private void loadTable(Tab t, List<Map<String,Object>> rs ,int baseIndex, int unitIndex, int idIndex, int nameIndex,
			int countryIndex, int refereeIndex, int scoreIndex, int cycleNum, String item, int jno, boolean a) {
		System.out.println("Referee_controller : loadTable");
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
		col_Score.setCellFactory(TextFieldTableCell.forTableColumn());
		col_Score.setOnEditCommit(new EventHandler<CellEditEvent<Athlete_info, String>>(){
			@Override
			public void handle(CellEditEvent<Athlete_info, String> arg0) {
				String SQL = null;
				Pattern pattern = Pattern.compile("^[-\\+]?[0-9]*[.]?[0-9]*$");  
				if(pattern.matcher(arg0.getNewValue()).matches()) {
					((Athlete_info) arg0.getTableView().getItems().get(table.getEditingCell().getRow())).setScore(arg0.getNewValue());
					SQL = "update viewCompetition set score = " +  
						((Athlete_info) arg0.getTableView().getItems().get(table.getEditingCell().getRow())).getScore()
						+ " where ano = '" 
						+ ((Athlete_info) arg0.getTableView().getItems().get(table.getEditingCell().getRow())).getID()
						+ "' and jno = '" + jno + "';\n";
					SQLBuffer.append(SQL);
				}
				else {
					System.out.println("Referee_controller : editConfirm() error: " + arg0.getNewValue());
				}
			}
		});
		if(a) {
			col_Score.setEditable(true);
		}
		else {
			col_Score.setEditable(false);
		}
		table.setEditable(true);
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
                	List<Map<String,Object>> rs = Sql.ExcuteSQL(SQL, "referee", "referee");
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
		System.out.println("Referee_controller : fresh()");
		String SQL = "select iname, unit, ano, aname, country, rname, score, jno "
				+ "from viewCompetition as view1 where jno in (select jno from viewCompetition as view2"
				+ " where view2.rname = '" + name
				+ "') order by jno asc, ano asc";
		int i = 0;
		List<Map<String,Object>> rs = Sql.ExcuteSQL(SQL, "referee", "referee");
		TabPane1.getTabs().clear();
		int maxjno1 = 0, maxjno2 = 0, jno = 0;
		for(i = 0; i < rs.size() / 64 ; i++) {
			jno = Integer.valueOf((String) rs.get(i * 64 + 7).get("jno"));
			if(((jno <= 8 && jno > 0) || (jno > 16 && jno <= 20)
				|| (jno > 24 && jno <= 26) || (jno == 29)) && jno > maxjno1) {
				maxjno1 = jno;
			}	
			else if(((jno > 8 && jno <= 16) || (jno > 20 && jno <= 24)
				|| (jno > 26 && jno <= 28) || (jno == 30)) && jno > maxjno2) {
				maxjno2 = jno;
			}
		}
		for(i = 0; i < rs.size() / 64 ; i++) {
			Tab t = new Tab();
			boolean a = false;
			jno = Integer.valueOf((String) rs.get(i * 64 + 7).get("jno"));
			if((maxjno1 > 16 && maxjno1 <= 20 && jno > 16 && jno <= 20) || (maxjno1 > 0 && maxjno1 <= 8 && jno > 0 && jno <= 8)
					|| (maxjno2 > 8 && maxjno2 <= 16 && jno > 8 && jno <= 16) || (maxjno2 > 20 && maxjno2 <= 24 && jno > 20 && jno <= 24)
					|| (maxjno1 > 24 && maxjno1 <= 26 && jno > 24 && jno <= 26) || (maxjno2 > 26 && maxjno2 <= 28 && jno > 26 && jno <= 28)
					|| (maxjno1 == 29 && jno == 29) || (maxjno2 == 30 && jno == 30)) {
					a = true;
				}
			else {
				a = false;
			}
			loadTable(t, rs, 64 * i, 1, 2, 3, 4, 5, 6, 8, 
					(String) rs.get(i * 8 * 8).get("iname"),
					Integer.valueOf((String) rs.get(i * 64 + 7).get("jno")),a
					);
			TabPane1.getTabs().add(t);
		}
	}
	
	public void init(Stage _Stage_referee, String _passwd, String _name) {
		name = _name;
		passwd = _passwd;
		Stage_referee = _Stage_referee;
		String SQL = "select rno, country, rname from referee where rno = "
		+ passwd + " and rname = '" + name + "'";
		List<Map<String,Object>> rs = Sql.ExcuteSQL(SQL, "referee", "referee");
		if(rs.size() != 0) {
			Label_ID.setText((String)rs.get(0).get("rno"));
			Label_country.setText((String)rs.get(1).get("country"));
			Label_name.setText((String)rs.get(2).get("rname"));
		}
		else {
			System.out.println("Label_controller.init Wrong!");
			System.exit(1);
		}
		TabPane1.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
		fresh();
	}
}
