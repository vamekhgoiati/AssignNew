package com.myproject.institutions.client;

import java.io.File;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface XMLImportExportAsync {


	void XMLExport(AsyncCallback<Void> callback);

	void XMLImport(AsyncCallback<Void> callback);

}
