package com.myproject.institutions.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.google.appengine.api.appidentity.AppIdentityServicePb.SigningService.Streams;

@WebServlet("upload")
public class FileUploadServlet extends HttpServlet {

	private static final String UPLOAD_DIRECTORY = "C:\\Users\\vamekh\\Desktop";

	private static final String PROPERTIES_FILE = "WEB-INF/classes/uploadprogress.properties";
	private static final String FILE_SEPERATOR = System
			.getProperty("file.separator");
	private String uploadDirectory = "C:\\Users\\vamekh\\Desktop";
	private XMLImportExportImpl XMLManager = new XMLImportExportImpl();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FileItemFactory factory = new DiskFileItemFactory();
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			// Parse the request
			List items = upload.parseRequest(request);

			// Process the uploaded items
			Iterator iter = items.iterator();

			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				// handling a normal form-field
				if (!item.isFormField()) {
					String fileName = item.getName();
					if (fileName != null) {
						fileName = FilenameUtils.getName(fileName);
					}
					String contentType = item.getContentType();
					if(!contentType.equals("text/xml")){
						throw new IllegalArgumentException();
					}
					fileName = getServletContext().getRealPath(
							"/uploadedFiles/" + fileName);
					byte[] data = item.get();
					FileOutputStream fileOutSt = new FileOutputStream(fileName);
					fileOutSt.write(data);
					fileOutSt.close();
					XMLManager.XMLImport(fileName);
					System.out.print("File Uploaded Successfully!");
				}
			}
		} catch (Exception e) {
			System.out.print("File Uploading Failed!" + e.getMessage());
		}
	}
}
