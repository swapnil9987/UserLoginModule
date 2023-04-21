package com.icreon.user.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.icreon.user.model.User;
import com.icreon.user.repository.UserRepository;


import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author swapnil.mane
 *
 */
@Service
public class ExcelDataServiceImpl implements IExcelDataService {
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	
	Workbook workbook;
	
	
	@Override
	public List<User> getExcelDataAsList() {
		
		List<String> list = new ArrayList<String>();

		// Create a DataFormatter to format and get each cell's value as String
		DataFormatter dataFormatter = new DataFormatter();

		// Create the Workbook
		try {
			workbook = WorkbookFactory.create(new File("UserDetailsSheet.xlsx"));
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}

		// Retrieving the number of sheets in the Workbook
		System.out.println("-------Workbook has '" + workbook.getNumberOfSheets() + "' Sheets-----");

		// Getting the Sheet at index zero
		Sheet sheet = workbook.getSheetAt(0);

		// Getting number of columns in the Sheet
		int noOfColumns = sheet.getRow(0).getLastCellNum();
		System.out.println("-------Sheet has '"+noOfColumns+"' columns------");

		// Using for-each loop to iterate over the rows and columns
		for (Row row : sheet) {
			for (Cell cell : row) {
				String cellValue = dataFormatter.formatCellValue(cell);
				list.add(cellValue);
			}
		}

		// filling excel data and creating list as List<useroice>
		List<User> userList = createList(list, noOfColumns);

		// Closing the workbook
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userList;
	}

	private List<User> createList(List<String> excelData, int noOfColumns) {

		ArrayList<User> userList = new ArrayList<User>();

		int i = noOfColumns;
		do {
			User user = new User();
			user.setFirstName(excelData.get(i).trim());
			user.setLastName(excelData.get(i + 1).trim());
			user.setEmail(excelData.get(i + 2).trim());
			user.setPassword(this.bCryptPasswordEncoder.encode(excelData.get(i + 3).trim()));
			user.setPhoneNumber(excelData.get(i + 4).trim());
			user.setStatus(excelData.get(i + 5).trim());
			user.setIsActive(true);
			user.setCreatedOn(new Date());
			userList.add(user);
			i = i + (noOfColumns);

		} while (i < excelData.size());
		return userList;
	}

	@Override
	public int saveExcelData(List<User> users) {
		
		users = userRepository.saveAll(users);
		return users.size();
	}

}
