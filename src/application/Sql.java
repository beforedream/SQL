package application;
import java.sql.*;
import java.util.*;
public class Sql {
	// JDBC 驱动名及数据库 URL
    static private final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  
    static private final String DB_URL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=DB_EX3";
 
    // 数据库的用户名与密码，需要根据自己的设置
    public static void init() {
        // 注册 JDBC 驱动
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
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }
            catch(SQLException se2){
            	
            }// 什么都不做
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
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            boolean rs = stmt.execute(sql);
            return rs;
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }
            catch(SQLException se2){
            	
            }// 什么都不做
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
