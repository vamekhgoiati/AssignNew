package com.myproject.institutions.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.myproject.institutions.shared.Institution;
import com.myproject.institutions.shared.InstitutionType;

@RemoteServiceRelativePath("FIManager")
public interface FIManager extends RemoteService{

	public boolean createInstitution(Institution inst);
	
	public boolean updateInstitution(Institution inst);
	
	public boolean deleteInstitution(Institution inst);
	
	public int getID(String code);
	
	public Institution getInstitutionByID(int ID);
	
	public Institution getInstitutionByCode(String code);
	
	public int checkInstitutionByTypeCode(String code);
	
	public ArrayList<Institution> getInstitutions();
	
}
