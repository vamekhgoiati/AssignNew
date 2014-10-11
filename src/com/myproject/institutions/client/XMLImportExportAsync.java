package com.myproject.institutions.client;


import com.google.gwt.user.client.rpc.AsyncCallback;

public interface XMLImportExportAsync {


	void XMLExport(AsyncCallback<Void> callback);

	void XMLImport(AsyncCallback<Void> callback);

	void XMLImport(String filename, AsyncCallback<Void> callback);

	void XMLExport(String filename, AsyncCallback<Void> callback);

}
