package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Department {
	private int id;
	private String name;
	
    DBConnection conn=new DBConnection();
	Statement st =null;
	ResultSet rs=null;
	PreparedStatement preparedStatement=null;
	
	public Department() {	
	}
	
	public Department(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public ArrayList<Department> getList() throws SQLException{
		ArrayList<Department>list=new ArrayList<>();
		
		Department obj;
		Connection con=conn.connDb();

		 try {
			 st=con.createStatement();
			 rs=st.executeQuery("SELECT * FROM otomasyon.department");
			 while(rs.next()) {
				 obj=new Department();
				 obj.setId(rs.getInt("id"));
				 obj.setName(rs.getString("name"));
				 list.add(obj);
				 
				 
			 }
			 
		 }catch(SQLException e) {
			 e.printStackTrace();
		 }finally {
			 st.close();
			 rs.close();
			 con.close();
			 
		 }
		
		return list;
		
	}
	
	public Department getFetch(int id) {
		Connection con=conn.connDb();
		Department d=new Department();
		try {
			st=con.createStatement();
			rs=st.executeQuery("SELECT * FROM otomasyon.department WHERE id="+ id);
			while (rs.next()) {
				d.setId(rs.getInt("id"));
				d.setName(rs.getString("name"));
				break;	
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return d;
		
		
	}
	public boolean  addDepartment(String name) throws SQLException {
		
		String query ="INSERT INTO otomasyon.department"+"(name)VALUES"+"(?)";
		boolean key=false;
		Connection con=conn.connDb();
		try {
			
			st=con.createStatement();
			preparedStatement=con.prepareStatement(query);
			preparedStatement.setString(1,name);
			preparedStatement.executeUpdate();
			key=true;
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		if(key)
			return true;
		else
			return false;
		

	}
	public boolean deleteDepartment(int id) throws SQLException{
		String query ="DELETE FROM otomasyon.department WHERE id=?";
		boolean key=false;
		Connection con=conn.connDb();
		try {
			st=con.createStatement();
			preparedStatement=con.prepareStatement(query);
			preparedStatement.setInt(1,id);
			preparedStatement.executeUpdate();
			key=true;
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		if(key)
			return true;
		else
			return false;
		

	}
	public boolean  updateDepartment(int id,String name) throws SQLException {
		
		String query ="UPDATE otomasyon.department SET name=? WHERE id=?";
		boolean key=false;
		Connection con=conn.connDb();
		try {
			st=con.createStatement();
			preparedStatement=con.prepareStatement(query);
			preparedStatement.setString(1,name);
			preparedStatement.setInt(2,id);
			preparedStatement.executeUpdate();
			key=true;
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		if(key)
			return true;
		else
			return false;
		

	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name= name;
	}

	
}
