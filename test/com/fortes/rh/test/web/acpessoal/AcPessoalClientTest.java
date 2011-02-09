package com.fortes.rh.test.web.acpessoal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

import org.apache.axis.client.Service;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.ws.AcPessoalClientImpl;

public abstract class AcPessoalClientTest extends TestCase
{
	protected static final String baseAcUrl = "http://10.1.4.26:1024";

	protected AcPessoalClientImpl acPessoalClientImpl;

	protected Empresa empresa;

	public Connection getConexaoAC() throws ClassNotFoundException, SQLException 
	{
		Class.forName("org.firebirdsql.jdbc.FBDriver");
		Connection conexao = DriverManager.getConnection("jdbc:firebirdsql:10.1.4.26/3051:C:\\Fortes\\AC\\AC.FDB?user=SYSDBA&password=masterkey");
		conexao.setAutoCommit(true);
		
		return conexao;
	}
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		acPessoalClientImpl = new AcPessoalClientImpl();
		acPessoalClientImpl.setService(new Service());

		empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("0006");
		empresa.setAcUsuario("ADMIN");
		empresa.setAcSenha("");
		empresa.setAcUrlSoap(baseAcUrl + "/soap/IAcPessoal");
		empresa.setAcUrlWsdl(baseAcUrl + "/wsdl/IAcPessoal");
		empresa.setAcIntegra(true);
	}
	
    public static ResultSet execQuery(Connection conn, String sql) throws Exception 
    {
        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return stm.executeQuery(sql);
    }
	
	public ResultSet query(String sql) throws Exception
	{
		return execQuery(getConexaoAC(), sql);
	}

	public void execute(String sql) throws Exception
	{
		try
		{
			Statement stm = getConexaoAC().createStatement();
			stm.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Empresa getEmpresa() {
		return empresa;
	}
}
