package com.myproject.institutions.shared;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "institution_type")
@XmlAccessorType (XmlAccessType.FIELD)
public class InstitutionType implements Serializable{
	
	private int id;
	private String code;
	private String name;
	
	public InstitutionType(){};
	
	public InstitutionType(String code, String name){
		this.code = code;
		this.name = name;
	}
	
	public InstitutionType(int id, String code, String name){
		this.id = id;
		this.code = code;
		this.name = name;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
}
