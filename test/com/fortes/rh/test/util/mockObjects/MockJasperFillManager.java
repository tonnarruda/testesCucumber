package com.fortes.rh.test.util.mockObjects;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class MockJasperFillManager {

	public static JasperPrint fillReport(JasperReport jasperReport, Map<String, Object> parameters, JRDataSource dataSource) throws JRException
	{
		return null;
	}
}
