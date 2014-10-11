package com.myproject.institutions.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.myproject.institutions.shared.Institution;
import com.myproject.institutions.shared.InstitutionType;

public interface FIManagerAsync {

	void createInstitution(Institution inst, AsyncCallback<Boolean> callback);

	void updateInstitution(Institution inst, AsyncCallback<Boolean> callback);

	void deleteInstitution(Institution inst, AsyncCallback<Boolean> callback);

	void getID(String code, String name, AsyncCallback<Integer> callback);

	void getInstitutionByID(int ID, AsyncCallback<Institution> callback);

	void getInstitutionByCode(String code, AsyncCallback<Institution> callback);

	void getInstitutions(AsyncCallback<ArrayList<Institution>> callback);

	void checkInstitutionByTypeCode(String code, AsyncCallback<Integer> callback);
	
	

}
