package com.dxc.eproc.template;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dxc.eproc.catalogue.model.CatalogueUploadFileExcelTemplate;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueItemsFileExporter.
 */
public final class CatalogueItemUploadFileExporter {

	/**
	 * Instantiates a new catalogue item upload file exporter.
	 */
	private CatalogueItemUploadFileExporter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * To excel file.
	 *
	 * @param catalogueItems the catalogue items
	 * @return the byte array input stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static ByteArrayInputStream toExcelFile(List<CatalogueUploadFileExcelTemplate> catalogueItems)
			throws IOException {

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("CatalogueItems");

		Row row = sheet.createRow(0);

		XSSFFont defaultFont = (XSSFFont) workbook.createFont();
		defaultFont.setFontHeightInPoints((short) 10);
		defaultFont.setFontName("Arial");
		defaultFont.setColor(IndexedColors.BLACK.getIndex());
		defaultFont.setBold(true);
		defaultFont.setItalic(false);

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(defaultFont);

		// Creating header
		Cell cell = row.createCell(0);
		cell.setCellValue("SNo");
		cell.setCellStyle(headerCellStyle);

		cell = row.createCell(1);
		cell.setCellValue("itemCode");
		cell.setCellStyle(headerCellStyle);

		cell = row.createCell(2);
		cell.setCellValue("itemName");
		cell.setCellStyle(headerCellStyle);

		cell = row.createCell(3);
		cell.setCellValue("uom");
		cell.setCellStyle(headerCellStyle);

		cell = row.createCell(4);
		cell.setCellValue("categoryCode");
		cell.setCellStyle(headerCellStyle);

		cell = row.createCell(5);
		cell.setCellValue("status");
		cell.setCellStyle(headerCellStyle);

		cell = row.createCell(6);
		cell.setCellValue("error");
		cell.setCellStyle(headerCellStyle);

		// Creating data rows for each customer
		for (int i = 0; i < catalogueItems.size(); i++) {
			Row dataRow = sheet.createRow(i + 1);
			dataRow.createCell(0).setCellValue(catalogueItems.get(i).getsNo());
			dataRow.createCell(1).setCellValue(catalogueItems.get(i).getItemCode());
			dataRow.createCell(2).setCellValue(catalogueItems.get(i).getItemName());
			dataRow.createCell(3).setCellValue(catalogueItems.get(i).getUnit());
			dataRow.createCell(4).setCellValue(catalogueItems.get(i).getCategoryCode());
			dataRow.createCell(5).setCellValue(catalogueItems.get(i).getStatus());
			dataRow.createCell(6).setCellValue(catalogueItems.get(i).getErrorReason());
		}

		// Making size of column auto resize to fit with data
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		return new ByteArrayInputStream(outputStream.toByteArray());
	}

}
