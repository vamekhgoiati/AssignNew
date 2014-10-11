package com.myproject.institutions.shared;

import java.util.Date;

public class InstitutionBuilder {

	private int id;
	private String code;
	private String name;
	private String address;
	private String phone;
	private String email;
	private String fax;
	private InstitutionType type;
	private Date regDate;
	
	public InstitutionBuilder(){}
	
	public InstitutionBuilder setCode(String code){
		this.code = code;
		return this;
	}
	
	public InstitutionBuilder setName(String name){
		this.name = name;
		return this;
	}
	
	public InstitutionBuilder setAddress(String address){
		this.address = address;
		return this;
	}
	
	public InstitutionBuilder setPhone(String phone){
		this.phone = phone;
		return this;
	}
	
	public InstitutionBuilder setEmail(String email){
		this.email = email;
		return this;
	}
	
	public InstitutionBuilder setFax(String fax){
		this.fax = fax;
		return this;
	}
	
	public InstitutionBuilder setType(InstitutionType type){
		this.type = type;
		return this;
	}
	
	public InstitutionBuilder setRegDate(Date regDate){
		this.regDate = regDate;
		return this;
	}
	
	public Institution build(){
		return new Institution(id, code, name, address, phone, email, fax, type, regDate);
	}
}


