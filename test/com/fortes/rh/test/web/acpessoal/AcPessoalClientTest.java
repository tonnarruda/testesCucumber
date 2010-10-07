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
	protected static final String baseAcUrl = "http://127.0.0.1:1024";

	protected AcPessoalClientImpl acPessoalClientImpl;

	protected Empresa empresa;

	public Connection getConexaoAC() throws ClassNotFoundException, SQLException 
	{
		Class.forName("org.firebirdsql.jdbc.FBDriver");
		Connection conexao = DriverManager.getConnection("jdbc:firebirdsql:localhost/3051:C:\\Fortes\\testRH\\AC.FDB?user=SYSDBA&password=masterkey");
		conexao.setAutoCommit(false);
		
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
	
    public static ResultSet execSelect(Connection conn, String sql) throws Exception 
    {
        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return stm.executeQuery(sql);
    }
	
	public void execute() throws Exception
	{
		Statement stmt = getConexaoAC().createStatement();

		ResultSet rs = stmt.executeQuery("select * from car");
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
	}

	public String[] getCargoAC(String carCod, String empCod) throws Exception
	{
		String[] result = null;
		
        try
        {
        	ResultSet consulta = execSelect(getConexaoAC(), "select emp_codigo, codigo, nome, nome_faixa_rh, rh_car from car where codigo = '" + carCod + "' and emp_codigo = '" + empCod + "'");
            
            if (consulta.next())
            {
                result = new String[5];
                result[0] = consulta.getString(1);
                result[1] = consulta.getString(2);
                result[2] = consulta.getString(3);
                result[3] = consulta.getString(4);
                result[4] = consulta.getString(5);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        
        return result; 
	}
	
	public String[] getSituacaoCargoAC(String carCod, String empCod, String data, String tabela) throws Exception
	{
		String[] result = null;
		
		try
		{
			ResultSet consulta = execSelect(getConexaoAC(), "select EMP_CODIGO, CAR_CODIGO, DATA, SALCONTRATUAL, IND_CODIGO_SALARIO, SALTIPO, VALOR, INDQTDE, RH_SCA_ID " +
															" from "+ tabela +" where CAR_CODIGO = '" + carCod + "' and EMP_CODIGO = '" + empCod + "' and DATA = '" + data.replace("/", ".") + "'");
			
			if (consulta.next())
			{
				result = new String[9];
				result[0] = consulta.getString(1);
				result[1] = consulta.getString(2);
				result[2] = consulta.getString(3);
				result[3] = consulta.getString(4);
				result[4] = consulta.getString(5);
				result[5] = consulta.getString(6);
				result[6] = consulta.getString(7);
				result[7] = consulta.getString(8);
				result[8] = consulta.getString(9);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return result; 
	}

	public void insertSituacaoCargoAC() throws Exception
	{
		try
		{
	        Statement stm = getConexaoAC().createStatement();
	        String sql = 	"insert into sca (EMP_CODIGO, CAR_CODIGO, DATA, SALCONTRATUAL, IND_CODIGO_SALARIO, SALTIPO, VALOR, INDQTDE, RH_SCA_ID) " +
							"values('0006', '073', '12/15/2010', 'S', null, 'V', 200.0, 0.0, 1)";
	        
	        stm.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
