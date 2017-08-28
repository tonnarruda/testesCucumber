package com.fortes.rh.test.web.acpessoal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.axis.client.Service;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.ws.AcPessoalClientImpl;

public abstract class AcPessoalClientTest extends MockObjectTestCase
{
	protected AcPessoalClientImpl acPessoalClientImpl;

	protected Empresa empresa;
	protected GrupoAC grupoAC;
	private Mock grupoACManager;
	private static String IP = "10.1.18.214";
	
	public Connection getConexaoAC() throws ClassNotFoundException, SQLException 
	{
		Class.forName("org.firebirdsql.jdbc.FBDriver");
		Connection conexao = DriverManager.getConnection("jdbc:firebirdsql:" + IP + "/53052:C:\\Fortes\\AC\\AC.FDB?user=SYSDBA&password=masterkey");
		conexao.setAutoCommit(true);
		
		return conexao;
	}
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		String baseAcUrl = "http://" + IP + ":1024";

		acPessoalClientImpl = new AcPessoalClientImpl();
		acPessoalClientImpl.setService(new Service());

		empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCodigoAC("0006");
		empresa.setAcIntegra(true);
		
		grupoAC = new GrupoAC();
		grupoAC.setAcUsuario("ADMIN");
		grupoAC.setAcSenha("");
		grupoAC.setAcUrlSoap(baseAcUrl + "/soap/IAcPessoal");
		grupoAC.setAcUrlWsdl(baseAcUrl + "/wsdl/IAcPessoal");
		
		grupoACManager = mock(GrupoACManager.class);
        acPessoalClientImpl.setGrupoACManager((GrupoACManager) grupoACManager.proxy());

	}
	
	public void montaMockGrupoAC()
	{
		grupoACManager.expects(atLeastOnce()).method("findByCodigo").withAnyArguments().will(returnValue(grupoAC));		
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

	public GrupoAC getGrupoAC() {
		return grupoAC;
	}
}
