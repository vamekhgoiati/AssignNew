package com.myproject.institutions.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.myproject.institutions.client.FITypeManager;
import com.myproject.institutions.shared.InstitutionType;


public class InstitutionTypeManager extends RemoteServiceServlet implements FITypeManager{
	
	private static Connection conn;
	
	public InstitutionTypeManager(){
		conn = DBManager.getConnection();
	}
	
	public boolean createType(InstitutionType type){
		
		try {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO Institution_types (Code, Name) VALUES (?, ?);");
			statement.setString(1, type.getCode());
			statement.setString(2, type.getName());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean updateType(InstitutionType type){
		
		try {
			PreparedStatement statement = conn.prepareStatement("UPDATE Institution_types SET Code = ?, Name = ? WHERE ID = ?;");
			statement.setString(1, type.getCode());
			statement.setString(2, type.getName());
			statement.setInt(3, type.getId());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean deleteType(InstitutionType type){
		
		try {
			PreparedStatement statement = conn.prepareStatement("DELETE FROM Institution_types WHERE ID = ?");
			statement.setInt(1, type.getId());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	public int getID(String code, String name){
		
		int ID = -1;
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT ID FROM Institution_types WHERE Code = ? AND Name = ?;");
			statement.setString(1, code);
			statement.setString(2, name);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				ID = rs.getInt("ID"); 
			}
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
		}
		
		return ID;
		
	}
	
	public InstitutionType getTypeByID(int ID){
		
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT Code, Name FROM Institution_types WHERE ID = ?;");
			statement.setInt(1, ID);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				String code = rs.getString("Code");
				String name = rs.getString("Name");
				InstitutionType type = new InstitutionType(ID, code, name);
				return type;
			}
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
		}
		return null;
		
	}
	
	public InstitutionType getTypeByCode(String code){
		
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT ID, Name FROM Institution_types WHERE Code = ?;");
			statement.setString(1, code);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				int ID = rs.getInt("ID");
				String name = rs.getString("Name");
				InstitutionType type = new InstitutionType(ID, code, name);
				return type;
			}
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
		}
		return null;
		
	}
	
	public ArrayList<InstitutionType> getTypes(){
		
		ArrayList<InstitutionType> types = new ArrayList<InstitutionType>();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM Institution_types;");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				int ID = rs.getInt("ID");
				String code = rs.getString("Code");
				String name = rs.getString("Name");
				types.add(new InstitutionType(ID, code, name));
			}
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
		}
		
		return types;
		
	}
	
}
