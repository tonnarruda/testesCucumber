package com.fortes.webwork.views.xls;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
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

	protected Row row;
	protected Sheet sheet;
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
    protected String totalizadorGroup = null;
    protected String[] nomeAgruoadorAnterior;
    protected String[] propertiesArray;
    protected String[] propertiesGroupArray;
    protected int[] rowNumIni, rowNumFim;
    Map<String, CellRangeAddress> celMescladas = new HashMap<String, CellRangeAddress>();
    protected String propertiesCalculo;
    protected String operacao;
    protected String considerarUltimaColunaComo;
    protected String formatoDouble = "#,##0.00";
    protected String formatoInteiro = "#,##0";
    
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
		
		propertiesArray = properties.split(",");
		propertiesGroupArray = new String[]{};
		String[] columnsArray = columns.split(",");
				
		if(propertiesGroup != null)
			propertiesGroupArray = propertiesGroup.split(",");

	    String reportFilterRef = (String)stack.findValue(reportFilter);
	    String reportTitleRef = (String)stack.findValue(reportTitle);

		Workbook wb = new HSSFWorkbook();//PLANHILHA (documento)
	    sheet = wb.createSheet(sheetName);//planilha filha

	    Font fontBold = wb.createFont();
	    fontBold.setBoldweight(Font.BOLDWEIGHT_BOLD);
	    
	    CellStyle boldStyle = wb.createCellStyle();
	    boldStyle.setWrapText(true);
	    boldStyle.setFont(fontBold);

	    CellStyle columnHeaderStyle = wb.createCellStyle();
	    columnHeaderStyle.setFont(fontBold);
	    columnHeaderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	    columnHeaderStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    
	    CellStyle columnStyle = wb.createCellStyle();
	    columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
	    
	    CellStyle columnDouble = wb.createCellStyle();
	    columnDouble.setDataFormat(wb.createDataFormat().getFormat(formatoDouble));
	    
	    CellStyle columnInteger = wb.createCellStyle();
	    columnInteger.setDataFormat(wb.createDataFormat().getFormat(formatoInteiro));
	    
	    CellStyle styleUltimaColuna = wb.createCellStyle();
	    if(considerarUltimaColunaComo!=null && considerarUltimaColunaComo.equals("Percentual"))
	    	styleUltimaColuna.setDataFormat(wb.createDataFormat().getFormat("0.00%"));
	    
	    // Cabecalho
	    row = sheet.createRow(0);		    
	    Cell cell = row.createCell(0);
	    cell.setCellStyle(boldStyle);
	    cell.setCellValue(reportTitleRef);
	    
	    row = sheet.createRow(1);
	    row.setHeight((short)700);
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
				
				if(propertiesGroupArray.length > i)
				{
					propGroup = BeanUtils.getValue(obj, propertiesGroupArray[i]);
					propNameGroup = propGroup!=null?propGroup.toString():"";

					if(propertiesCalculo != null && propertiesCalculo.equals(propertiesGroupArray[i]))
						rowIndex = insereTotalizadorCelulaMescalda(rowIndex, propNameGroup);
						
					mesclaCelulas(propNameGroup, i, rowIndex);
				}
				
				cell = row.createCell(i);
				
				if(prop != null && prop.getClass() == Double.class){
					cell.setCellValue(new Double(propName));
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cell.setCellStyle(columnDouble);
				}else if(prop != null && prop.getClass() == Integer.class){
					cell.setCellValue(new Integer(propName));
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cell.setCellStyle(columnInteger);
				}else{
					cell.setCellValue(propName);
					cell.setCellStyle(columnStyle);
				}
				
				if((propertiesArray.length - 1)  == i  && propertiesCalculo != null && propName != null && !propName.equals(""))
				{
					if(considerarUltimaColunaComo!=null && considerarUltimaColunaComo.equals("Texto"))
						cell.setCellValue(propName.toString());
					else
						cell.setCellValue(new Double(propName.toString()));
					
					cell.setCellStyle(styleUltimaColuna);
				}
		    }
		}
	    
	    Row tempRow;
	    Double soma, media;
	    row = sheet.createRow(rowIndex++);
	    
	    CellStyle columnStyleOperacao = wb.createCellStyle();
	    columnStyleOperacao.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
	    columnStyleOperacao.setFont(fontBold);
	    
	    CellStyle columnStyleOperacaoValue = wb.createCellStyle();
	    columnStyleOperacaoValue.setFont(fontBold);
	    if(considerarUltimaColunaComo!=null && considerarUltimaColunaComo.equals("Percentual"))
	    	columnStyleOperacaoValue.setDataFormat(wb.createDataFormat().getFormat("0.00%"));
	    
	    for (CellRangeAddress celMesclada : celMescladas.values())
	    {
	    	sheet.addMergedRegion(celMesclada);
	    	
	    	if(propertiesCalculo != null &&  celMesclada.getFirstColumn() == 0)//Somente Primeiro Agrupador
	    	{
	    		NumberFormat formata = new DecimalFormat("#0.0000");
	    		tempRow = sheet.getRow(celMesclada.getLastRow() + 1);
				cell = tempRow.createCell(celMesclada.getFirstColumn());
				cell.setCellValue(operacao);
				cell.setCellStyle(columnStyleOperacao);
				
				cell = tempRow.createCell(propertiesArray.length - 1);
				soma = somaCelulas(celMesclada.getFirstRow(), celMesclada.getLastRow());
				
				if(operacao != null && operacao.equals("Soma"))
				{
					if(considerarUltimaColunaComo!=null && considerarUltimaColunaComo.equals("Texto"))
						cell.setCellValue(formata.format(soma).toString().replace(",", "."));
					else
						cell.setCellValue(new Double(formata.format(soma).toString().replace(",", ".")));
				}else if(operacao != null && operacao.equals("Média")){
					media = soma / (celMesclada.getLastRow() - celMesclada.getFirstRow() + 1);
					
					if(considerarUltimaColunaComo!=null && considerarUltimaColunaComo.equals("Texto"))
						cell.setCellValue(formata.format(media).toString().replace(",", "."));
					else
						cell.setCellValue(new Double(formata.format(media).toString().replace(",", ".")));
				}
				
				cell.setCellStyle(columnStyleOperacaoValue);
				sheet.addMergedRegion(new CellRangeAddress(tempRow.getRowNum(), tempRow.getRowNum(), celMesclada.getFirstColumn(), propertiesArray.length - 2));
	    	}
	    }
	    
	    for (int i = 0; i < propertiesArray.length; i++)
	    	sheet.autoSizeColumn(i, true);
	    
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
		
		criaCookieProcessando(response);
		
		ServletOutputStream outputStream = response.getOutputStream();

		wb.write(outputStream);
	    
	    outputStream.flush();
		outputStream.close();
	}
	
	private void criaCookieProcessando(HttpServletResponse response) {
		Long time = new Date().getTime();
		Cookie myCookie = new Cookie("processando", time.toString());
		response.addCookie(myCookie);
	}

	private int insereTotalizadorCelulaMescalda(int rowIndex, String groupName) 
	{
		if(totalizadorGroup == null)
			totalizadorGroup = groupName;
		else if(!totalizadorGroup.equals(groupName)) 
		{
			totalizadorGroup = groupName;
			row = sheet.createRow(rowIndex++);
			for (int in = 0; in < propertiesGroupArray.length; in++)
				rowNumFim[in] = ++rowNumFim[in];
		}
		
		return rowIndex;
	}
	
	private Double somaCelulas(int ini, int fim) 
	{
		Cell cellSum;
		Double soma = 0.0;
		
		for(int i = ini ; i <= fim ; i++)
		{
			Row rowSum = sheet.getRow(i);
			cellSum = rowSum.getCell(propertiesArray.length - 1);
			if(cellSum != null)
			{
				if(considerarUltimaColunaComo!=null && considerarUltimaColunaComo.equals("Texto"))
					soma += convertStringToDoubleByRegex(cellSum.getStringCellValue());
				else
					soma += cellSum.getNumericCellValue();
			}
		}
		
		return soma;
	}

	private Double convertStringToDoubleByRegex(String numberString) 
	{
		String concatena = ""; 
		Pattern pattern = Pattern.compile("(\\d+)([?,]|[?.])(\\d+)");
		Matcher m = pattern.matcher(numberString);
		while (m.find())
			concatena += m.group();

		try {
			return new Double(concatena.replace(",", "."));
		} catch (Exception e) {
			return 0.0;
		}
	}

	private void mesclaCelulas(String nomeAgrupador, int colNum, int rowIndex) 
	{
		if(colNum != 0)
			nomeAgrupador += "_" + nomeAgruoadorAnterior[colNum-1];

		if(celMescladas.get(nomeAgrupador) != null)//se agrupador existir no MAP
			rowNumFim[colNum] = rowIndex - 1;
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

	public void setPropertiesCalculo(String propertiesCalculo) {
		this.propertiesCalculo = propertiesCalculo;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public void setConsiderarUltimaColunaComo(String considerarUltimaColunaComo) {
		this.considerarUltimaColunaComo = considerarUltimaColunaComo;
	}

	public void setFormatoDouble(String formatoDouble) {
		this.formatoDouble = formatoDouble;
	}

	public void setFormatoInteiro(String formatoInteiro) {
		this.formatoInteiro = formatoInteiro;
	}
}
