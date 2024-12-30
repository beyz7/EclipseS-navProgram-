package Model;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.ArrayList;

	public class Asistan extends User {
	    Statement st = null;
	    ResultSet rs = null;
	    Connection con = conn.connDb();
	    PreparedStatement preparedStatement = null;

	    public Asistan(int id, String tcno, String name, String password) {
	        super(id, tcno, name, password);
	    }

	    public Asistan() {
	    }

	    
	    public boolean addCourse(String courseName, int departmentId) throws SQLException {
	        String query = "INSERT INTO otomasyon.ders (name, department_id) VALUES (?, ?)";
	        boolean key = false;

	        try {
	            preparedStatement = con.prepareStatement(query);
	            preparedStatement.setString(1, courseName);
	            preparedStatement.setInt(2, departmentId);
	            preparedStatement.executeUpdate();
	            key = true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return key;
	    }

	}
