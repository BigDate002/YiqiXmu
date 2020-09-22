package com.netcity.util;

import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ExcelHelper {
	public static Sheet setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol,
			int endCol) {
		XSSFDataValidationHelper helper = new XSSFDataValidationHelper((XSSFSheet) sheet);
		DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
		DataValidation dataValidation = null;
		for (int i = firstRow; i < endRow + firstRow; i++) {
			CellRangeAddressList addressList = new CellRangeAddressList(firstRow, endRow + firstRow, firstCol, endCol);
			dataValidation = helper.createValidation(constraint, addressList);
			dataValidation.setShowErrorBox(true);
			dataValidation.createErrorBox("输入格式错误", "选择下拉框中的内容");
			sheet.addValidationData(dataValidation);
		}

		return sheet;
	}
}