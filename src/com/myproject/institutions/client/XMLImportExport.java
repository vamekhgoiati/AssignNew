package com.myproject.institutions.client;

import java.io.File;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("XML")
public interface XMLImportExport extends RemoteService{
	
	public void XMLImport();
	
	public void XMLExport();
	
	public void XMLExport(String filename);

	void XMLImport(String filename);

}
