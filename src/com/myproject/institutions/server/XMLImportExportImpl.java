package com.myproject.institutions.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.google.appengine.api.files.FileWriteChannel;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.myproject.institutions.client.XMLImportExport;
import com.myproject.institutions.shared.Institution;
import com.myproject.institutions.shared.InstitutionType;
import com.myproject.institutions.shared.InstitutionTypes;
import com.myproject.institutions.shared.Institutions;

public class XMLImportExportImpl extends RemoteServiceServlet implements XMLImportExport{

	private static final String ExportDirectory = "D:\\java_workspace\\AssignNew\\war\\file.xml";
	private InstitutionManager FIManager = new InstitutionManager();
	private InstitutionTypeManager FITypeManager = new InstitutionTypeManager();
	
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
	
	public void XMLExport(String filename){
		
		ArrayList<Institution> institutions = FIManager.getInstitutions();
		Institutions institutionList = new Institutions();
		institutionList.setInstitutions(institutions);
		
		ArrayList<InstitutionType> institutionTypes = FITypeManager.getTypes();
		InstitutionTypes institutionTypeList = new InstitutionTypes();
		institutionTypeList.setInstitutionTypes(institutionTypes);
		
		File exportFile = new File(ExportDirectory);
		FileWriter fw = null;
		try {
			fw = new FileWriter(exportFile, true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			
			
			JAXBContext jaxbContextTypes = JAXBContext.newInstance(InstitutionTypes.class);
		    Marshaller jaxbMarshallerTypes = jaxbContextTypes.createMarshaller();
		    
		   jaxbMarshallerTypes.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		     
		    jaxbMarshallerTypes.marshal(institutionTypeList, fw);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(Institutions.class);
		    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		    
		   jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		    
		    
		    jaxbMarshaller.marshal(institutionList, fw);
		    
		} catch (JAXBException e) {
			System.err.println(e);
			e.printStackTrace();
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
				JAXBContext jaxbContextInstitutionTypes = JAXBContext.newInstance(InstitutionTypes.class);
			    Unmarshaller jaxbUnmarshaller = jaxbContextInstitutionTypes.createUnmarshaller();
			    InstitutionTypes institutionTypes = (InstitutionTypes) jaxbUnmarshaller.unmarshal(selectedFile);
			    
			    JAXBContext jaxbContextInstitutions = JAXBContext.newInstance(Institutions.class);
			    jaxbUnmarshaller = jaxbContextInstitutions.createUnmarshaller();
			    Institutions institutions = (Institutions) jaxbUnmarshaller.unmarshal(selectedFile);
			    
			    
			    for(InstitutionType type : institutionTypes.getInstitutionTypes()){
			    	if(FITypeManager.getTypeByCode(type.getCode()) == null){
			    		FITypeManager.createType(type);
			    	} else {
			    		FITypeManager.updateType(type);
			    	}
			    }
			    
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
	
	public void XMLImport(String filename){
		
		try {
			JAXBContext jaxbContextInstitutionTypes = JAXBContext.newInstance(InstitutionTypes.class);
		    Unmarshaller jaxbUnmarshaller = jaxbContextInstitutionTypes.createUnmarshaller();
		    InstitutionTypes institutionTypes = (InstitutionTypes) jaxbUnmarshaller.unmarshal(new File(filename));
		    
		    JAXBContext jaxbContextInstitutions = JAXBContext.newInstance(Institutions.class);
		    jaxbUnmarshaller = jaxbContextInstitutions.createUnmarshaller();
		    Institutions institutions = (Institutions) jaxbUnmarshaller.unmarshal(new File(filename));
		    
		    
		    for(InstitutionType type : institutionTypes.getInstitutionTypes()){
		    	if(FITypeManager.getTypeByCode(type.getCode()) == null){
		    		FITypeManager.createType(type);
		    	} else {
		    		FITypeManager.updateType(type);
		    	}
		    }
		    
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
