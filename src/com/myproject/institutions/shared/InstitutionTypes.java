package com.myproject.institutions.shared;


import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "institution_types")
@XmlAccessorType (XmlAccessType.FIELD)
public class InstitutionTypes {
	
	public InstitutionTypes(){};
	
	@XmlElement(name = "institution_type")
	private ArrayList<InstitutionType> types = null;
	
	public ArrayList<InstitutionType> getInstitutionTypes(){
		return types;
	}
	
	public void setInstitutionTypes(ArrayList<InstitutionType> types){
		this.types = types;
	}
	
}
