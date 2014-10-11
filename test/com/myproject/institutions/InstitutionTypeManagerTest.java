package com.myproject.institutions;

import static org.junit.Assert.*;

import org.junit.Test;

import com.myproject.institutions.shared.InstitutionType;
import com.myproject.institutions.server.InstitutionTypeManager;

public class InstitutionTypeManagerTest {

	private InstitutionTypeManager manager = new InstitutionTypeManager();
	
	@Test
	public void testInstitutionTypeManager() {
	
	}

	@Test
	public void testCreateType() {
		boolean test1 = false;
		boolean test2 = false;
		InstitutionType type1 = new InstitutionType("0002", "Type3");
		InstitutionType type2 = new InstitutionType("0003", "type4");
		if(manager.getTypeByCode(type1.getCode()) == null){
			test1 = manager.createType(type1);
			assert(test1 == true);
		}
		if(manager.getTypeByCode(type2.getCode()) == null){
			test2 = manager.createType(type2);
			assert(test2 == true);
		}
		
	}

	@Test
	public void testUpdateType() {
		boolean res = false;
		int id = manager.getTypeByCode("0002").getId();
		InstitutionType type1 = new InstitutionType(id, "01", "test6");
		if(manager.getTypeByCode(type1.getCode()) == null)
			res = manager.updateType(type1);
		assert(res == true);
	}

	@Test
	public void testDeleteType() {
		boolean res = false;
		InstitutionType type1 = manager.getTypeByCode("0003");
		if(type1 != null)
			res = manager.deleteType(type1);
		assert(res == true);
	}

	

}
