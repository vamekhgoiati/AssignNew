package com.myproject.institutions.server;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.myproject.institutions.client.XMLImportExport;
import com.myproject.institutions.shared.Institution;
import com.myproject.institutions.shared.Institutions;

public class XMLImportExportImpl extends RemoteServiceServlet implements XMLImportExport{

	private InstitutionManager FIManager = new InstitutionManager();
	
	public void XMLExport(){
		JFileChooser fileDialog = new JFileChooser();
		File selectedFile = null;
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "XML Files", "xml");
		fileDialog.setFileFilter(filter);

		int returnVal = fileDialog.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			selectedFile = fileDialog.getSelectedFile();
			ArrayList<Institution> institutions = FIManager.getInstitutions();
			Institutions institutionList = new Institutions();
			institutionList.setInstitutions(institutions);
			try {
				
				JAXBContext jaxbContext = JAXBContext.newInstance(Institutions.class);
			    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			    
			    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			     
			    jaxbMarshaller.marshal(institutionList, selectedFile);
			   
			
			} catch (JAXBException e) {
				System.err.println(e);
				e.printStackTrace();
			}
		}
	}
	
	public void XMLImport(){
		JFileChooser fileDialog = new JFileChooser();
		File selectedFile = null;
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "XML Files", "xml");
		fileDialog.setFileFilter(filter);

		int returnVal = fileDialog.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			selectedFile = fileDialog.getSelectedFile();
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Institutions.class);
			    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			    Institutions institutions = (Institutions) jaxbUnmarshaller.unmarshal(selectedFile);
			    
			    for(Institution inst : institutions.getInstitutions()){
			    	if(FIManager.getInstitutionByCode(inst.getCode()) == null){
			    		FIManager.createInstitution(inst);
			    	} else {
			    		FIManager.updateInstitution(inst);
			    	}
			    }
			    
			} catch (JAXBException ex) {
				System.err.println(ex);
				ex.printStackTrace();
			}
		}
	}
	
}
