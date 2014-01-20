package com.fortes.webwork.views.xls;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.fortes.rh.exception.XlsException;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.util.BeanUtils;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.WebWorkResultSupport;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.util.OgnlValueStack;

@SuppressWarnings("serial")
public class XlsResult extends WebWorkResultSupport {

    protected String dataSource;
    protected String columns;
    protected String columnsNameDinamic;
    protected String properties;
    protected String propertiesGroup;
    protected String documentName;
    protected String reportFilter;
    protected String reportTitle;
    protected String sheetName;
    protected String dinamicColumns;
    protected String dinamicProperties;
    protected String msgFinalRelatorioXls;
    protected int[] rowNumIni, rowNumFim;
    protected String[] nomeAgruoadorAnterior;
    Map<String, CellRangeAddress> celMescladas = new HashMap<String, CellRangeAddress>();
    
	@Override
	@SuppressWarnings("unchecked")
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws XlsException, Exception 
	{
		OgnlValueStack stack = invocation.getStack();

		Collection<Object> dataSourceRef = (Collection<Object>) stack.findValue(dataSource);
		Collection<Object> columnsNameDinamicRef = (Collection<Object>) stack.findValue(columnsNameDinamic);
		
		if(dataSourceRef!=null && dataSourceRef.size() > 65535)
		{
			MyActionSupport action = (MyActionSupport) invocation.getInvocationContext().getActionInvocation().getAction();
			action.addActionWarning("Não foi possível gerar o relatório XLS, pois o número de linhas excede a <strong>65535</strong>, que é o máximo suportado por esse formato.<br /> Tente reduzir o número de linhas do seu relatório refinando sua busca através dos filtros.");
	    	throw new XlsException();
		}
		
		if(StringUtils.isNotBlank(dinamicColumns) && StringUtils.isNotBlank(dinamicProperties))
		{
			columns = (String)stack.findValue(dinamicColumns);				
			properties = (String)stack.findValue(dinamicProperties);
		}
		
		String[] columnsArray = columns.split(",");
		String[] propertiesArray = properties.split(",");
		String[] propertiesGroupArray = new String[]{};
		
		if(propertiesGroup != null)
			propertiesGroupArray = propertiesGroup.split(",");

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
	    
	    CellStyle columnStyle = wb.createCellStyle();
	    columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
	    
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
	    
	    if(columnsNameDinamicRef != null)
	    {
	    	int pos = columnsArray.length;
		    for (Object columnsNameDinamic : columnsNameDinamicRef)
		    {
		    	cell = row.createCell(pos++);
				cell.setCellValue((columnsNameDinamic != null)?columnsNameDinamic.toString():"");
				cell.setCellStyle(columnHeaderStyle);
		    }
	    }
	    
	    int rowIndex = 4;
	    String propName="";
	    String propNameGroup="";
	    
	    rowNumIni = new int[propertiesGroupArray.length]; 
	    rowNumFim = new int[propertiesGroupArray.length]; 
	    nomeAgruoadorAnterior = new String[propertiesGroupArray.length]; 
	    
    	for (int i = 0; i < propertiesGroupArray.length; i++)
    	    rowNumIni[i] = rowNumFim[i]= 3;
    	
	    for (Object obj : dataSourceRef) 
	    {
	    	row = sheet.createRow(rowIndex++);
	    	Object prop;
	    	Object propGroup;
		    for (int i = 0; i < propertiesArray.length; i++)
		    {
		    	prop = BeanUtils.getValue(obj, propertiesArray[i]);
		    	propName = prop!=null?prop.toString():"";
		    	
		    	cell = row.createCell(i);
				cell.setCellValue(propName);
				cell.setCellStyle(columnStyle);
				
				if(propertiesGroupArray.length > i)
				{
					propGroup = BeanUtils.getValue(obj, propertiesGroupArray[i]);
					propNameGroup = propGroup!=null?propGroup.toString():"";
					mesclaCelulas(propNameGroup, i);
				}
		    }
		}
	    
	    for (CellRangeAddress celMesclada : celMescladas.values()) 
	    	sheet.addMergedRegion(celMesclada);
	    
	    for (int i = 0; i < propertiesArray.length; i++) 
	    	sheet.autoSizeColumn(i);		    	
	    
	    //mensagem final
	    msgFinalRelatorioXls = (String)stack.findValue(msgFinalRelatorioXls);
	    if(msgFinalRelatorioXls != null && !"".equals(msgFinalRelatorioXls))
	    {
		    row = sheet.createRow(++rowIndex);
		    cell = row.createCell(0);
		    cell.setCellStyle(boldStyle);
		    cell.setCellValue(msgFinalRelatorioXls);
		    
		    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, columnsArray.length - 1));
	    }
	    
		HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(ServletActionContext.HTTP_RESPONSE);
		
		response.addHeader("Expires", "0");
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Content-type", "application/vnd.ms-excel");
		response.addHeader("Content-Transfer-Encoding", "binary");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + documentName + "\"");
		
		ServletOutputStream outputStream = response.getOutputStream();

		wb.write(outputStream);
	    
	    outputStream.flush();
		outputStream.close();
	}

	private void mesclaCelulas(String nomeAgrupador, int colNum) 
	{
		if(colNum != 0)
			nomeAgrupador += "_" + nomeAgruoadorAnterior[colNum-1];

		if(celMescladas.get(nomeAgrupador) != null)
			rowNumFim[colNum] = celMescladas.get(nomeAgrupador).getLastRow() + 1;
		else 
			rowNumIni[colNum] = ++rowNumFim[colNum];

		celMescladas.put(nomeAgrupador, new CellRangeAddress(rowNumIni[colNum], rowNumFim[colNum], colNum, colNum));
		nomeAgruoadorAnterior[colNum] = nomeAgrupador;
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

	public void setPropertiesGroup(String propertiesGroup) {
		this.propertiesGroup = propertiesGroup;
	}

	public void setColumnsNameDinamic(String columnsNameDinamic) {
		this.columnsNameDinamic = columnsNameDinamic;
	}

	public void setMsgFinalRelatorioXls(String msgFinalRelatorioXls) {
		this.msgFinalRelatorioXls = msgFinalRelatorioXls;
	}
}
