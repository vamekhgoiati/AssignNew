package com.myproject.institutions.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.myproject.institutions.shared.InstitutionType;

@RemoteServiceRelativePath("FITypeManager")
public interface FITypeManager extends RemoteService{

	public boolean createType(InstitutionType type);
	
	public boolean updateType(InstitutionType type);
	
	public boolean deleteType(InstitutionType type);
	
	public int getID(String code, String name);
	
	public InstitutionType getTypeByID(int ID);
	
	public InstitutionType getTypeByCode(String code);
	
	public ArrayList<InstitutionType> getTypes();

}
