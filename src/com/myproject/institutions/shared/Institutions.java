package com.myproject.institutions.shared;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "institutions")
@XmlAccessorType (XmlAccessType.FIELD)
public class Institutions {
	
	public Institutions(){};
	
	@XmlElement(name = "institution")
	private ArrayList<Institution> institutions = null;
	
	public ArrayList<Institution> getInstitutions(){
		return institutions;
	}
	
	public void setInstitutions(ArrayList<Institution> institutions){
		this.institutions = institutions;
	}
	
}