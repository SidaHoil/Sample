package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OpenAndCloseConnection {
	public Connection cnn;
	public PreparedStatement pr;
	public ResultSet rs;
	public void OpenConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			cnn=DriverManager.getConnection("jdbc:mysql://localhost:3300/Stock","root","root");
	}
	public void CloseConnection(){
		try {
			rs.close();
			cnn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int Updated(String tableName,String column1,String value1,String column2,String value2,
			String columnCondi,int condition) throws SQLException 
	{
		pr=cnn.prepareStatement("UPDATE "+tableName+" SET "+column1+"=?, "+column2+"=? WHERE "+columnCondi+"=?;");
		pr.setString(1, value1);
		pr.setString(2, value2);
		pr.setInt(3, condition);
		int i=pr.executeUpdate();
		return i;
	}
	public int Updat5Col(String tableName,String column1,String value1,String column2,String value2,String column3,
			String value3,String column4,String value4,String columnCondi,int condition) 
	{
		int i = 0;
		try {
			pr=cnn.prepareStatement("UPDATE "+tableName+" SET "+column1+"=?, "+column2+"=?, "+column3+"=?, "+column4+"=? WHERE "+columnCondi+"=?;");
			pr.setString(1, value1);
			pr.setString(2, value2);
			pr.setString(3, value3);
			pr.setString(4, value4);
			pr.setInt(5, condition);
			i=pr.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	public int Insert(String tableName,int column1,int column2,int column3,int colunm4,float column5,java.sql.Date column6,int column7,String status) throws SQLException {
		pr=cnn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?,?,?,?,?,?);");
		pr.setInt(1, column1);
		pr.setInt(2, column2);
		pr.setInt(3, column3);
		pr.setInt(4, colunm4);
		pr.setFloat(5, column5);
		pr.setDate(6, column6);
		pr.setInt(7, column7);
		pr.setString(8, status);
		int i=pr.executeUpdate();
		return i;
	}
	public int Insert(String tableName,int column1,String column2,String column3) throws SQLException {
		pr=cnn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?);");
		pr.setInt(1, column1);
		pr.setString(2, column2);
		pr.setString(3, column3);
		int i=pr.executeUpdate();
		return i;
	}
	public int Delete(int id,String tableName) throws SQLException {
		pr=cnn.prepareStatement("DELETE FROM "+tableName+" WHERE Id=?;");
		pr.setInt(1, id);
		int i=pr.executeUpdate();
		return i;
	}
	public ResultSet Select(String tableName,String columnName) throws SQLException {
		pr=cnn.prepareStatement("SELECT * FROM "+tableName+" ORDER BY "+columnName+" DESC;");
		rs=pr.executeQuery();
		return rs;
	}
	public ResultSet SelectView(String tableName,String columnCondi,String condition,String columnDESC) throws SQLException {
		pr=cnn.prepareStatement("SELECT * FROM "+tableName+" WHERE "+columnCondi+"=? ORDER BY "+columnDESC+" DESC;");
		pr.setString(1, condition);
		rs=pr.executeQuery();
		return rs;
	}
	public ResultSet Selected(String tableName,String columnName) throws SQLException {
		pr=cnn.prepareStatement("SELECT "+columnName+" FROM "+tableName+" ORDER BY "+columnName+" DESC;");
		rs=pr.executeQuery();
		return rs;
	}
	public ResultSet SelectCondition(String tableName,String columnName,String condition) throws SQLException {
		pr=cnn.prepareStatement("SELECT * FROM "+tableName+" WHERE "+columnName+"=?;");
		pr.setString(1, condition);
		rs=pr.executeQuery();
		return rs;
	}
	public ResultSet SelectCondition(String columeName,String tableName,String columnName,int condition) throws SQLException {
		pr=cnn.prepareStatement("SELECT "+columeName+" FROM "+tableName+" WHERE "+columnName+"=?;");
		pr.setInt(1, condition);
		rs=pr.executeQuery();
		return rs;
	}
	public ResultSet SelectOne(String tableName,String columnName,String column,String condition) throws SQLException {
		pr=cnn.prepareStatement("SELECT "+columnName+" FROM "+tableName+" WHERE "+column+"=?;");
		pr.setString(1, condition);
		rs=pr.executeQuery();
		return rs;
	}
	public ResultSet SelectCondition(String tableName,String wherecolume,int condition) throws SQLException {
		pr=cnn.prepareStatement("SELECT * FROM "+tableName+" WHERE "+wherecolume+"=?;");
		pr.setInt(1, condition);
		rs=pr.executeQuery();
		return rs;
	}
	public ResultSet Select(String tableName) throws SQLException {
		pr=cnn.prepareStatement("SELECT * FROM "+tableName+";");
		rs=pr.executeQuery();
		return rs;
	}
	public ResultSet SelectDESC(String tableName,String columnDESC) throws SQLException {
		pr=cnn.prepareStatement("SELECT * FROM "+tableName+" ORDER BY "+columnDESC+" DESC ;");
		rs=pr.executeQuery();
		return rs;
	}
	public ResultSet Selected(String tableName) throws SQLException {
		pr=cnn.prepareStatement("SELECT DISTINCT * FROM "+tableName+";");
		rs=pr.executeQuery();
		return rs;
	}
	public int Insert(String tableName, String column1, int value1, String column2, String value2, 
			String column3,String value3) throws SQLException {
		pr=cnn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?);");
		pr.setInt(1, value1);
		pr.setString(2, value2);
		pr.setString(3, value3);
		int i=pr.executeUpdate();
		return i;
	}
	public int Updat6Col(String tableName, String column1, String value1, String column2, String value2, String column3,
			int value3, String column4, String value4, String column5, String value5, String column6, String value6,
			String columnCondi, int condition) {
		int i = 0;
		try {
			pr=cnn.prepareStatement("UPDATE "+tableName+" SET "+column1+"=?, "+column2+"=?, "+column3+"=?, "+column4+"=?, "+column5+"=?,"+column6+"=? WHERE "+columnCondi+"=?;");
			pr.setString(1, value1);
			pr.setString(2, value2);
			pr.setInt(3, value3);
			pr.setString(4, value4);
			pr.setString(5, value5);
			pr.setString(6, value6);
			pr.setInt(7, condition);
			i=pr.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	public ResultSet SelectOne(String tableName, String columnName, String column, int int1) {
		try {
			pr=cnn.prepareStatement("SELECT "+columnName+" FROM "+tableName+" WHERE "+column+"=?;");
			pr.setInt(1, int1);
			rs=pr.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public int Updat7Col(String tableName, String col1, int val1, String col2, int val2,
			String col3, int val3, String col4, float val4, String col5, java.sql.Date val5,
			String col6, int val6, String col7, String val7, String colCondi, int valCondi) {
		int i = 0;
		try {
			pr=cnn.prepareStatement("UPDATE "+tableName+" SET "+col1+"=?, "+col2+"=?, "+col3+"=?, "
		+col4+"=?, "+col5+"=?,"+col6+"=?, "+col7+"=? WHERE "+colCondi+"=?;");
			pr.setInt(1, val1);
			pr.setInt(2, val2);
			pr.setInt(3, val3);
			pr.setFloat(4, val4);
			pr.setDate(5, val5);
			pr.setInt(6, val6);
			pr.setString(7, val7);
			pr.setInt(8, valCondi);
			i=pr.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return i;
	}
	public ResultSet SearchinLogin(String userName, String pass) {
		try {
			pr=cnn.prepareStatement("SELECT * FROM tbluser WHERE userName=? AND userPassword=? ;");
			pr.setString(1,userName);
			pr.setString(2,pass);
			rs=pr.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet SelectWithStatus(String tableName,String columnDESC, String statusName, String statusValue) throws SQLException {
		pr=cnn.prepareStatement("SELECT * FROM "+tableName+" WHERE "+statusName+" =? ORDER BY "+columnDESC+" DESC ;");
		pr.setString(1,statusValue);
		rs=pr.executeQuery();
		return rs;
	}
	public ResultSet Search(String tableName,String colName, String searchUser) {
		try {
			pr=cnn.prepareStatement("SELECT * FROM "+tableName+" WHERE "+colName+" like ? ;");
			pr.setString(1, "%"+searchUser+"%");
			rs=pr.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
}
