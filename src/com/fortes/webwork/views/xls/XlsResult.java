package com.fortes.webwork.views.xls;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.opensymphony.util.BeanUtils;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.WebWorkResultSupport;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.util.OgnlValueStack;

public class XlsResult extends WebWorkResultSupport {

    protected String dataSource;
    protected String columns;
    protected String properties;
    protected String documentName;
    protected String reportFilter;
    protected String reportTitle;
    protected String sheetName;
    protected String dinamicColumns;
    protected String dinamicProperties;
    
	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception 
	{
		try {
			OgnlValueStack stack = invocation.getStack();
			
			if(StringUtils.isNotBlank(dinamicColumns) && StringUtils.isNotBlank(dinamicProperties))
			{
				columns = (String)stack.findValue(dinamicColumns);				
				properties = (String)stack.findValue(dinamicProperties);
			}
			
			String[] columnsArray = columns.split(",");
			String[] propertiesArray = properties.split(",");

			
		    Collection<Object> dataSourceRef = (Collection<Object>) stack.findValue(dataSource);
		    String reportFilterRef = (String)stack.findValue(reportFilter);
		    String reportTitleRef = (String)stack.findValue(reportTitle);
	
			Workbook wb = new HSSFWorkbook();//PLANHILHA (documento)
		    Sheet sheet = wb.createSheet(sheetName);//planilha filha
	
		    Font fontBold = wb.createFont();
		    fontBold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		    
		    CellStyle boldStyle = wb.createCellStyle();
		    boldStyle.setFont(fontBold);
	
		    CellStyle columnHeaderStyle = wb.createCellStyle();
		    columnHeaderStyle.setFont(fontBold);
		    columnHeaderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		    columnHeaderStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		    
		    // Cabecalho
		    Row row = sheet.createRow(0);		    
		    Cell cell = row.createCell(0);
		    cell.setCellStyle(boldStyle);
		    cell.setCellValue(reportTitleRef);
		    
		    row = sheet.createRow(1);
		    cell = row.createCell(0);
		    cell.setCellStyle(boldStyle);
		    cell.setCellValue(reportFilterRef);
		    
		    row = sheet.createRow(3);
		    
		    for (int i = 0; i < columnsArray.length; i++) 
		    {
				cell = row.createCell(i);
				cell.setCellValue(columnsArray[i]);
				cell.setCellStyle(columnHeaderStyle);
			}
	
		    int rowIndex = 4;
		    for (Object obj : dataSourceRef) 
		    {
		    	row = sheet.createRow(rowIndex++);
		    	
		    	Object prop;
			    for (int i = 0; i < propertiesArray.length; i++)
			    {
			    	prop = BeanUtils.getValue(obj, propertiesArray[i]);
			    	row.createCell(i).setCellValue((prop != null)?prop.toString():"");		    	
			    }
			}
	
		    for (int i = 0; i < propertiesArray.length; i++) 
		    	sheet.autoSizeColumn(i);		    	
		    
			HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(ServletActionContext.HTTP_RESPONSE);
			
			response.addHeader("Expires", "0");
			response.addHeader("Pragma", "no-cache");
			response.addHeader("Content-type", "application/vnd.ms-excel");
			response.addHeader("Content-Transfer-Encoding", "binary");
			response.addHeader("Content-Disposition:", "attachment; filename=\"" + documentName + "\"");
			
		    wb.write(response.getOutputStream());

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao exportar para Excel.");
		}	    
	}
	

    public void setReportFilter(String reportFilter) {
		this.reportFilter = reportFilter;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}


	public void setDinamicColumns(String dinamicColumns) {
		this.dinamicColumns = dinamicColumns;
	}


	public void setDinamicProperties(String dinamicProperties) {
		this.dinamicProperties = dinamicProperties;
	}

}
