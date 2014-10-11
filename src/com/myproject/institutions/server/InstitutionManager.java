package com.myproject.institutions.server;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.myproject.institutions.client.FIManager;
import com.myproject.institutions.shared.Institution;
import com.myproject.institutions.shared.InstitutionType;
import com.myproject.institutions.server.InstitutionTypeManager;

public class InstitutionManager extends RemoteServiceServlet implements FIManager{
		
	private static Connection conn;
	private static InstitutionTypeManager typeManager;
	
	public InstitutionManager(){
		conn = DBManager.getConnection();
		typeManager = new InstitutionTypeManager();
	}
		
	public boolean createInstitution(Institution inst){
		
		try {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO Institutions (Code, Name, Address, Phone, Email, Fax, TypeID, RegDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
			statement.setString(1, inst.getCode());
			statement.setString(2, inst.getName());
			statement.setString(3, inst.getAddress());
			statement.setString(4, inst.getPhone());
			statement.setString(5, inst.getEmail());
			statement.setString(6, inst.getFax());
			statement.setInt(7, inst.getType().getId());
			if(inst.getRegDate() != null){
			    java.sql.Date sqlDate = new java.sql.Date(inst.getRegDate().getTime());
				statement.setDate(8, (java.sql.Date) sqlDate);
			} else statement.setDate(8, null);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean updateInstitution(Institution inst){
		
		try {
			PreparedStatement statement = conn.prepareStatement("UPDATE Institutions SET Code = ?, Name = ?, Address = ?, Phone = ?, Email = ?, Fax = ?, TypeID = ?, RegDate = ? WHERE ID = ?;");
			statement.setString(1, inst.getCode());
			statement.setString(2, inst.getName());
			statement.setString(3, inst.getAddress());
			statement.setString(4, inst.getPhone());
			statement.setString(5, inst.getEmail());
			statement.setString(6, inst.getFax());
			statement.setInt(7, inst.getType().getId());
			java.sql.Date sqlDate = new java.sql.Date(inst.getRegDate().getTime());
			statement.setDate(8, (java.sql.Date) sqlDate);
			statement.setInt(9, inst.getId());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean deleteInstitution(Institution inst){
		
		try {
			PreparedStatement statement = conn.prepareStatement("DELETE FROM Institutions WHERE ID = ?");
			statement.setInt(1, inst.getId());
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
			PreparedStatement statement = conn.prepareStatement("SELECT ID FROM Institutions WHERE Code = ? AND Name = ?;");
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
	
	public Institution getInstitutionByID(int ID){
		
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT Code, Name, Address, Phone, Email, Fax, TypeID, RegDate FROM Institutions WHERE ID = ?;");
			statement.setInt(1, ID);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				String code = rs.getString("Code");
				String name = rs.getString("Name");
				String address = rs.getString("Address");
				String phone = rs.getString("Phone");
				String email = rs.getString("Email");
				String fax = rs.getString("Fax");
				int typeID = rs.getInt("TypeID");
				InstitutionType type = typeManager.getTypeByID(typeID);
				Date regDate = rs.getDate("RegDate");
				Institution inst = new Institution(ID, code, name, address, phone, email, fax, type, regDate);
				return inst;
			}
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
		}
		return null;
		
	}
	
	public Institution getInstitutionByCode(String code){
		
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT ID, Name, Address, Phone, Email, Fax, TypeID, RegDate FROM Institutions WHERE Code = ?;");
			statement.setString(1, code);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				int ID = rs.getInt("ID");
				String name = rs.getString("Name");
				String address = rs.getString("Address");
				String phone = rs.getString("Phone");
				String email = rs.getString("Email");
				String fax = rs.getString("Fax");
				int typeID = rs.getInt("TypeID");
				InstitutionType type = typeManager.getTypeByID(typeID);
				Date regDate = rs.getDate("RegDate");
				Institution inst = new Institution(ID, code, name, address, phone, email, fax, type, regDate);
				return inst;
			}
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
		}
		return null;
		
	}
	
	public int checkInstitutionByTypeCode(String code){
		
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) as InstCount FROM Institutions WHERE TypeID = ?;");
			InstitutionType type = typeManager.getTypeByCode(code);
			statement.setInt(1, type.getId());
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				return rs.getInt("InstCount");
			}
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
		}
		return -1;
		
	}
	
	public ArrayList<Institution> getInstitutions(){
		
		ArrayList<Institution> institutions = new ArrayList<Institution>();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM Institutions;");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				int ID = rs.getInt("ID");
				String code = rs.getString("Code");
				String name = rs.getString("Name");
				String address = rs.getString("Address");
				String phone = rs.getString("Phone");
				String email = rs.getString("Email");
				String fax = rs.getString("Fax");
				int typeID = rs.getInt("TypeID");
				InstitutionType type = typeManager.getTypeByID(typeID);
				Date regDate = rs.getDate("RegDate");
				institutions.add(new Institution(ID, code, name, address, phone, email, fax, type, regDate));
			}
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
		}
		
		return institutions;
		
	}
		
	
}
