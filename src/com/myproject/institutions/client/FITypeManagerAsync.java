package com.myproject.institutions.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.myproject.institutions.shared.InstitutionType;

public interface FITypeManagerAsync {

	void createType(InstitutionType type, AsyncCallback<Boolean> callback);

	void updateType(InstitutionType type, AsyncCallback<Boolean> callback);

	void deleteType(InstitutionType type, AsyncCallback<Boolean> callback);

	void getID(String code, String name, AsyncCallback<Integer> callback);

	void getTypeByID(int ID, AsyncCallback<InstitutionType> callback);

	void getTypeByCode(String code, AsyncCallback<InstitutionType> callback);

	void getTypes(AsyncCallback<ArrayList<InstitutionType>> callback);

}
