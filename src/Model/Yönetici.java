package Model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Yönetici extends User {
	Statement st =null;
	ResultSet rs=null;
	Connection con=conn.connDb();
	PreparedStatement preparedStatement=null;

	public Yönetici(int id, String tcno, String name, String password) {
		super(id, tcno, name, password);
	}
	public Yönetici() {
		
	}
	public ArrayList<User> getAsistanList() throws SQLException{
		ArrayList<User>list=new ArrayList<>();
		
		User obj;
		try {
			st=con.createStatement();
			rs=st.executeQuery("SELECT*FROM otomasyon.asistan");
			while(rs.next()) {
				obj=new User(rs.getInt("id"),rs.getString("tcno"),rs.getString("name"),rs.getString("password"));
				list.add(obj);}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	public ArrayList<User> getDepartmentAsistanList(int department_id) throws SQLException{
		ArrayList<User>list=new ArrayList<>();
		
		User obj;
		try {
			st=con.createStatement();
			rs=st.executeQuery("SELECT a.id, a.tcno, a.name, a.password FROM otomasyon.worker w LEFT JOIN otomasyon.asistan a ON w.user_id=a.id WHERE department_id=" +department_id);
			while(rs.next()) {
				obj=new User(rs.getInt("a.id"),rs.getString("a.tcno"),rs.getString("a.name"),rs.getString("a.password"));
				list.add(obj);}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
public boolean  addAsistan(String tcno,String password,String name) throws SQLException {
	
	String query ="INSERT INTO otomasyon.asistan"+"(tcno,password,name)VALUES"+"(?,?,?)";
	boolean key=false;
	try {
		st=con.createStatement();
		preparedStatement=con.prepareStatement(query);
		preparedStatement.setString(1,tcno);
		preparedStatement.setString(2,password);
		preparedStatement.setString(3,name);
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
public boolean  updateAsistan(int id,String tcno,String password,String name) throws SQLException {
	
	String query ="UPDATE otomasyon.asistan SET tcno=?,password=?,name=? WHERE id=?";
	boolean key=false;
	try {
		st=con.createStatement();
		preparedStatement=con.prepareStatement(query);
		preparedStatement.setString(1,name);
		preparedStatement.setString(2,tcno);
		preparedStatement.setString(3,password);
		preparedStatement.setInt(4,id);
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
public boolean deleteAsistan(int id) throws SQLException{
	String query ="DELETE FROM otomasyon.asistan WHERE id=?";
	boolean key=false;
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
public boolean  addWorker(int user_id,int department_id) throws SQLException {
	
	String query ="INSERT INTO otomasyon.worker"+"(user_id,department_id)VALUES"+"(?,?)";
	boolean key=false;
	int count=0;
	try {
		st=con.createStatement();
		rs=st.executeQuery("SELECT*FROM otomasyon.worker WHERE department_id="+department_id+" AND user_id="+user_id);
		while(rs.next()) {
			count++;
			
		}
		if(count==0) {
		preparedStatement=con.prepareStatement(query);
		preparedStatement.setInt(1,user_id);
		preparedStatement.setInt(2,department_id);
		preparedStatement.executeUpdate();
		}
		key=true;
	}catch(Exception e) {
		e.printStackTrace();
		
	}
	if(key)
		return true;
	else
		return false;
	

}
}
