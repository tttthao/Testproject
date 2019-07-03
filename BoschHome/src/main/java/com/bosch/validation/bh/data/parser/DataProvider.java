package com.bosch.validation.bh.data.parser;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bosch.validation.bh.data.model.UserDetail;

public class DataProvider {
	
	private String dataFile = "BGN-Start-Page";
	private FileInputStream dataSources = null;
	private Workbook workbook = null;
	
	public DataProvider() {
		try {
			this.dataSources = new FileInputStream(new File(String.format("src/test/data/%s.xlsx", dataFile)));
			this.workbook = new XSSFWorkbook(dataSources);
		} catch (Exception e) {
			this.dataSources = null;
		}
	}
	
	public List<UserDetail> fetchUserDetails(String sheetName) {
		try {
			List<UserDetail> userDetails = new ArrayList<>();
			
			Sheet sheet = workbook.getSheet(sheetName);
			
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);

				UserDetail userDetail = new UserDetail();
				Cell cellName = row.getCell(0);
				if (cellName != null) {
					userDetail.setNtID(cellName.getStringCellValue());
				}

				Cell cellDesc = row.getCell(1);
				if (cellDesc != null) {
					userDetail.setDisplayName(cellDesc.getStringCellValue());
				}

				Cell cellType = row.getCell(2);
				if (cellType != null) {
					userDetail.setLocation(cellType.getStringCellValue());
				}

				userDetails.add(userDetail);
			}

			workbook.close();
			
			return userDetails;
		} catch (Exception e) {
			return null;
		}
	}
}
