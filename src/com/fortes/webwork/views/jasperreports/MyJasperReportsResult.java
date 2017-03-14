package com.fortes.webwork.views.jasperreports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fortes.rh.util.ArquivoUtil;
import com.opensymphony.util.TextUtils;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.views.jasperreports.JasperReportConstants;
import com.opensymphony.webwork.views.jasperreports.JasperReportsResult;
import com.opensymphony.webwork.views.jasperreports.OgnlValueStackDataSource;
import com.opensymphony.webwork.views.jasperreports.OgnlValueStackShadowMap;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.util.OgnlValueStack;

public class MyJasperReportsResult extends JasperReportsResult implements JasperReportConstants
{
	private final static Log LOG = LogFactory.getLog(JasperReportsResult.class);

	protected String parametersMap = null;
	
	public String getParametersMap()
	{
		return parametersMap;
	}

	public void setParametersMap(String parametersMap)
	{
		this.parametersMap = parametersMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doExecute(String finalLocation, ActionInvocation invocation)
			throws Exception
	{
		if (this.format == null)
		{
			this.format = FORMAT_PDF;
		}

		if (dataSource == null)
		{
			String message = "No dataSource specified...";
			LOG.error(message);
			throw new RuntimeException(message);
		}

		if (LOG.isDebugEnabled())
		{
			LOG.debug("Creating JasperReport for dataSource = " + dataSource + ", format = " + this.format);
		}

		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(ServletActionContext.HTTP_RESPONSE);

		criaCookieProcessando(response);

		// construct the data source for the report
		OgnlValueStack stack = invocation.getStack();
		OgnlValueStackDataSource stackDataSource = new OgnlValueStackDataSource(stack, dataSource);

		format = conditionalParse(format, invocation);
		dataSource = conditionalParse(dataSource, invocation);

		if (contentDisposition != null)
		{
			contentDisposition = conditionalParse(contentDisposition,
					invocation);
		}

		if (documentName != null)
		{
			documentName = conditionalParse(documentName, invocation);
		}

		// (Map) ActionContext.getContext().getSession().get("IMAGES_MAP");
		if (!TextUtils.stringSet(format))
		{
			format = FORMAT_PDF;
		}

		if (!"contype".equals(request.getHeader("User-Agent")))
		{
			// Determine the directory that the report file is in and set the
			// reportDirectory parameter
			// For WW 2.1.7:
			// ServletContext servletContext = ((ServletConfig)
			// invocation.getInvocationContext().get(ServletActionContext.SERVLET_CONFIG)).getServletContext();
			ServletContext servletContext = (ServletContext) invocation.getInvocationContext().get(ServletActionContext.SERVLET_CONTEXT);
			String systemId = servletContext.getRealPath(finalLocation);
			Map parameters = new OgnlValueStackShadowMap(stack);
			File directory = new File(systemId.substring(0, systemId.lastIndexOf(File.separator)));

			JRSwapFile arquivoSwap = new JRSwapFile(ArquivoUtil.getRhHome(), 4096, 100);
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(100, arquivoSwap, true);
			
			parameters.put("reportDirectory", directory);
			parameters.put(JRParameter.REPORT_LOCALE, invocation.getInvocationContext().getLocale());
			parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			 
			// Instancia o virtualizador
			 
			// Map contendo os parâmetros para o relatório
			if (parametersMap != null)
			{
				Map obj = (Map) invocation.getStack().findValue(parametersMap);
				parameters.putAll(obj);
			}

			byte[] output;
			JasperPrint jasperPrint;

			// Fill the report and produce a print object
			try
			{
//				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(systemId);
				JasperReport jasperReport = compileReport(systemId, (InputStream) stack.findValue("reportInputStream"));
				jasperReport.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
				jasperReport.setProperty("net.sf.jasperreports.default.font.name", "SansSerif");
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, stackDataSource);
			}
			catch (JRException e)
			{
				LOG.error("Error building report for uri " + systemId, e);
				throw new ServletException(e.getMessage(), e);
			}

			// Export the print object to the desired output format
			try
			{
				if (contentDisposition != null || documentName != null)
				{
					final StringBuffer tmp = new StringBuffer();
					tmp.append((contentDisposition == null) ? "inline" : contentDisposition);

					if (documentName != null)
					{
						tmp.append("; filename=");
						tmp.append(documentName);
						tmp.append(".");
						tmp.append(format.toLowerCase());
					}

					response.setHeader("Content-disposition", tmp.toString());
					//response.se
				}

				if (format.equals(FORMAT_PDF))
				{
					response.setContentType("application/pdf");
					
					// 12/12/2006 - Modificação para não abrir na mesma janela da aplicação
					//response.setHeader("Content-disposition", "inline; filename=report.pdf");
					if(documentName == null)
						response.setHeader("Content-disposition", "attachment; filename=report.pdf");
					else
						response.setHeader("Content-disposition", "attachment; filename=" + documentName);
					
					output = JasperExportManager.exportReportToPdf(jasperPrint);
				}
				else
				{
					JRExporter exporter;

					if (format.equals(FORMAT_CSV))
					{
						response.setContentType("text/plain");
						exporter = new JRCsvExporter();
					}
					else if (format.equals(FORMAT_HTML))
					{
						response.setContentType("text/html");

						// IMAGES_MAPS seems to be only supported as "backward
						// compatible" from JasperReports 1.1.0

						Map imagesMap = new HashMap();

						request.getSession(true).setAttribute("IMAGES_MAP",
								imagesMap);
						exporter = new JRHtmlExporter();
						exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
						exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath() + imageServletUrl);
						// Needed to support chart images:
						exporter.setParameter(JRExporterParameter.JASPER_PRINT,	jasperPrint);
						request.getSession().setAttribute("net.sf.jasperreports.j2ee.jasper_print", jasperPrint);
					}
					else if (format.equals(FORMAT_XLS))
					{
						response.setContentType("application/vnd.ms-excel");
						exporter = new JRXlsExporter();
					}
					else if (format.equals(FORMAT_XML))
					{
						response.setContentType("text/xml");
						exporter = new JRXmlExporter();
					}
					else
					{
						throw new ServletException("Unknown report format: "
								+ format);
					}

					output = exportReportToBytes(jasperPrint, exporter);
				}
			}
			catch (JRException e)
			{
				String message = "Error producing " + format
						+ " report for uri " + systemId;
				LOG.error(message, e);
				throw new ServletException(e.getMessage(), e);
			}

			response.setContentLength(output.length);

			ServletOutputStream ouputStream;

			try
			{
				ouputStream = response.getOutputStream();
				ouputStream.write(output);
				ouputStream.flush();
				ouputStream.close();
			}
			catch (IOException e)
			{
				LOG.error("Error writing report output", e);
				throw new ServletException(e.getMessage(), e);
			}
		}
		else
		{
			// Code to handle "contype" request from IE
			try
			{
				ServletOutputStream outputStream;
				response.setContentType("application/pdf");
				response.setContentLength(0);
				outputStream = response.getOutputStream();
				outputStream.close();
			}
			catch (IOException e)
			{
				LOG.error("Error writing report output", e);
				throw new ServletException(e.getMessage(), e);
			}
		}
		System.gc();
	}
	
	private void criaCookieProcessando(HttpServletResponse response) {
		Long time = new Date().getTime();
		Cookie myCookie = new Cookie("processando", time.toString());
		response.addCookie(myCookie);
	}
	
	private JasperReport compileReport(String reportPath, InputStream reportInputStream) throws JRException {
		JasperReport jasperReport;// = JasperCompileManager.compileReport(systemId.replaceAll("jasper", "jrxml"));
		if (reportInputStream == null)
			jasperReport = JasperCompileManager.compileReport(reportPath.replaceAll("jasper", "jrxml"));
		else
			jasperReport = JasperCompileManager.compileReport(reportInputStream);
		return jasperReport;
	}

	private byte[] exportReportToBytes(JasperPrint jasperPrint, JRExporter exporter) throws JRException
	{
		byte[] output;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		
		if (delimiter != null)
		{
			exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER,
					delimiter);
		}

		exporter.exportReport();
		output = baos.toByteArray();
		return output;
	}
}