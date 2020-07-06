package com.ioankat.dao;


import com.ioankat.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
