package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtils {

    public String[][] readDataFromExcel(String filePath, String sheetName)
    {
        String dataSet[][] = null;
        try {
            File file = new File(filePath);
            System.out.println("File Path: "+file.getAbsolutePath());
            FileInputStream fis = new FileInputStream(file);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheet(sheetName);
            int totalRows = sheet.getLastRowNum();
            int totalCols = sheet.getRow(0).getLastCellNum();
            System.out.println("Total Rows: "+totalRows);
            System.out.println("Total Columns: "+totalCols);

            dataSet = new String[totalRows][totalCols];
            DataFormatter df = new DataFormatter();

            for (int i = 1; i <= totalRows; i++) {
                for (int j = 0; j < totalCols; j++) {
                    dataSet[i-1][j] = df.formatCellValue(sheet.getRow(i).getCell(j));
                    System.out.print(dataSet[i-1][j]+" | ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataSet;
    }

    public int[] getRowAndColumnCount(String filePath, String sheetName)
    {
        try {
            File file = new File(filePath);
            System.out.println("File Path: "+file.getAbsolutePath());
            FileInputStream fis = new FileInputStream(file);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheet(sheetName);
            int totalRows = sheet.getLastRowNum();
            int totalCols = sheet.getRow(0).getLastCellNum();
            int[] totalRowAndCol = {totalRows, totalCols};
            return totalRowAndCol;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String[][] readDataRangeFromExcel(String filePath, String sheetName, int startRow, int endRow)
    {
        String dataSet[][] = null;
        try {
            File file = new File(filePath);
            System.out.println("File Path: "+file.getAbsolutePath());
            FileInputStream fis = new FileInputStream(file);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheet(sheetName);
            int totalRows = sheet.getLastRowNum();
            int totalRowsRange = (endRow-startRow)+1;
            int totalCols = sheet.getRow(0).getLastCellNum();
            System.out.println("Total Rows: "+totalRows);
            System.out.println("Total Columns: "+totalCols);

            dataSet = new String[totalRowsRange][totalCols];
            DataFormatter df = new DataFormatter();
            int minusRow = startRow;
            if(endRow>totalRows)
            {
                throw new RuntimeException("Invalid Row Range. Total Rows in the sheet are: "+totalRows);
            }

            for (int i = startRow; i <= endRow; i++) {
                for (int j = 0; j < totalCols; j++) {
                    dataSet[i-minusRow][j] = df.formatCellValue(sheet.getRow(i).getCell(j));
                    System.out.print(dataSet[i-minusRow][j]+" | ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataSet;
    }


    public void setHeaderRow(String filePath, String sheetName, List<String> rowHeaders) {
        // Use try-with-resources to ensure the workbook is closed automatically
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(filePath)) {

            // 1. Create the sheet
            Sheet sheet = workbook.createSheet(sheetName);
            // 2. Create the header row (index 0)
            Row headerRow = sheet.createRow(0);
            // 3. Create a cell style for headers (Bold text) and White (white looks better on dark blue)
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setBold(true);
            headerStyle.setFont(font);
            // 4. Set the background color to Blue
            headerStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // 5. Iterate through the list and create cells
            for (int i = 0; i < rowHeaders.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(rowHeaders.get(i));
                cell.setCellStyle(headerStyle);
                // Optional: Auto-size columns to fit the header text
                sheet.autoSizeColumn(i);
            }
            // 6. Write the output to the file
            workbook.write(fileOut);
            System.out.println("Excel sheet created successfully at: " + filePath);

        } catch (IOException e) {
            System.err.println("Error while creating Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void writeDataToExcelAppend(String filePath, String sheetName, List<String> headerSet, List<String> dataSet)
    {
    try {
        File file = new File(filePath);
        Workbook workbook;
        FileInputStream fis = null;

        // Check if file exists
        if (file.exists()) {
            fis = new FileInputStream(file);
            workbook = WorkbookFactory.create(fis);
        } else {
            workbook = new XSSFWorkbook();
        }

        // Get or create sheet

        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }

        // Check if sheet is empty and add headers if needed
        int nextRowIndex = sheet.getLastRowNum();
        if(nextRowIndex < 0)
        {
            // Position the nextRowIndex to 0 if sheet is newly created and headers are not set
            //Since Sheet.getLastRowNum() returns -1 for an empty sheet, not 0
            nextRowIndex = 0;
        }

        if (nextRowIndex == 0 && sheet.getRow(0) == null) {
            // Sheet is empty, create headers
            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setBold(true);
            headerStyle.setFont(font);
            headerStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            for (int i = 0; i < headerSet.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headerSet.get(i));
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(i);
            }
            nextRowIndex = 1;
        } else {
            // Sheet has data, append to next row
            nextRowIndex = sheet.getLastRowNum() + 1;
        }

        // Add data row
        if (sheet.getRow(0) != null && nextRowIndex > 0) {
            Row dataRow = sheet.createRow(nextRowIndex);
            for (int i = 0; i < dataSet.size(); i++) {
                dataRow.createCell(i).setCellValue(dataSet.get(i));
            }
        }

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            System.out.println("Data written successfully to: " + filePath);
        }

        if (fis != null) {
            fis.close();
        }
        workbook.close();

    } catch (IOException e) {
        throw new RuntimeException("Error while writing to Excel file: " + e.getMessage(), e);
    }
}


}
