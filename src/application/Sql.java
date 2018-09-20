package application;
import java.sql.*;
import java.util.*;
public class Sql {
	// JDBC �����������ݿ� URL
    static private final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  
    static private final String DB_URL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=DB_EX3";
 
    // ���ݿ���û��������룬��Ҫ�����Լ�������
    public static void init() {
        // ע�� JDBC ����
        try {
        	Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static List<Map<String,Object>> ExcuteSQL(String sql, String USER, String PASS) {
        Statement stmt = null;
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return ResultSetToList(rs);
        }catch(SQLException se){
            // ���� JDBC ����
            se.printStackTrace();
        }catch(Exception e){
            // ���� Class.forName ����
            e.printStackTrace();
        }finally{
            // �ر���Դ
            try{
                if(stmt!=null) stmt.close();
            }
            catch(SQLException se2){
            	
            }// ʲô������
            try{
                if(conn!=null) conn.close();
            }
            catch(SQLException se){
                se.printStackTrace();
            }
        }
        return null;
    }
    
    public static boolean DoSQL(String sql, String USER, String PASS) {
        Statement stmt = null;
        Connection conn = null;
        try{
            // ������
            System.out.println("�������ݿ�...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            // ִ�в�ѯ
            System.out.println(" ʵ����Statement����...");
            stmt = conn.createStatement();
            boolean rs = stmt.execute(sql);
            return rs;
        }catch(SQLException se){
            // ���� JDBC ����
            se.printStackTrace();
        }catch(Exception e){
            // ���� Class.forName ����
            e.printStackTrace();
        }finally{
            // �ر���Դ
            try{
                if(stmt!=null) stmt.close();
            }
            catch(SQLException se2){
            	
            }// ʲô������
            try{
                if(conn!=null) conn.close();
            }
            catch(SQLException se){
                se.printStackTrace();
            }
        }
        return false;
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String,Object>> ResultSetToList(ResultSet rs) throws SQLException{
    	List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
    	ResultSetMetaData rsmd = rs.getMetaData();  
    	int colCount=rsmd.getColumnCount();
    	List<String> colNameList=new ArrayList<String>();
    	for(int i=0;i<colCount;i++){
    		colNameList.add(rsmd.getColumnName(i+1));
    	} 
    	while(rs.next()){

    		for(int i=0;i<colCount;i++){
    			Map map=new HashMap<String, Object>();
    			String key=colNameList.get(i);
    			Object value=rs.getString(colNameList.get(i));
    			map.put(key, value);
    			
    			results.add(map);
    		}
    	}
    	return results;
    }
}
