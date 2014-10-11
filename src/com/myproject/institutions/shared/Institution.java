package com.myproject.institutions.shared;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "institution")
@XmlAccessorType (XmlAccessType.FIELD)
public class Institution implements Serializable{

	private int id;
	private String code;
	private String name;
	private String address;
	private String phone;
	private String email;
	private String fax;
	private InstitutionType type;
	private Date regDate;
	
	public Institution(){}
	
	public Institution(String code, String name, String address, String phone, String email, String fax, InstitutionType type, Date regDate){
		this.code = code;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.fax = fax;
		this.type = type;
		this.regDate = regDate;
	}
	
	public Institution(int id, String code, String name, String address, String phone, String email, String fax, InstitutionType type, Date regDate){
		this.id = id;
		this.code = code;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.fax = fax;
		this.type = type;
		this.regDate = regDate;
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
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getAddress(){
		return this.address;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public String getPhone(){
		return this.phone;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setFax(String fax){
		this.fax = fax;
	}
	
	public String getFax(){
		return this.fax;
	}
	
	public void setType(InstitutionType type){
		this.type = type;
	}
	
	public InstitutionType getType(){
		return this.type;
	}
	
	public void setRegDate(Date regDate){
		this.regDate = regDate;
	}
	
	public Date getRegDate(){
		return this.regDate;
	}
	
}
