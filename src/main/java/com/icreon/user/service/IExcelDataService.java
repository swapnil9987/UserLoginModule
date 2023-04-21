package com.icreon.user.service;

import java.util.List;

import com.icreon.user.model.User;

/**
 * @author swapnil.mane
 *
 */
public interface IExcelDataService {
	
	List<User> getExcelDataAsList();
	
	int saveExcelData(List<User> invoices);
	
	
}
