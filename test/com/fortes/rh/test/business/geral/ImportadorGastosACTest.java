package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.GastoEmpresaManager;
import com.fortes.rh.business.geral.ImportadorGastosAC;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.web.ws.AcPessoalImportadorGastos;

public class ImportadorGastosACTest extends MockObjectTestCase
{
	private ImportadorGastosAC action;
	private Mock gastoEmpresaManager;
	private Mock empresaManager;
	private Mock acPessoalImportadorGastos;

	protected void setUp() throws Exception
    {
        super.setUp();
        action = new ImportadorGastosAC();

        gastoEmpresaManager = new Mock(GastoEmpresaManager.class);
        empresaManager = new Mock(EmpresaManager.class);
        acPessoalImportadorGastos = mock(AcPessoalImportadorGastos.class);

        action.setAcPessoalImportadorGastos((AcPessoalImportadorGastos) acPessoalImportadorGastos.proxy());
        action.setGastoEmpresaManager((GastoEmpresaManager) gastoEmpresaManager.proxy());
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
    }

	private Collection<Empresa> getEmpresas(boolean acIntegra)
	{
		Collection<Empresa> empresas = new ArrayList<Empresa>();
		Empresa empresa1 = new Empresa();
		empresa1.setId(1L);
		empresa1.setCodigoAC("1");
		empresa1.setAcIntegra(acIntegra);

		Empresa empresa2 = new Empresa();
		empresa2.setId(2L);
		empresa2.setCodigoAC("2");
		empresa2.setAcIntegra(acIntegra);

		empresas.add(empresa1);
		empresas.add(empresa2);

		return empresas;
	}

    public void testExecuteSemIntegracaoAC() throws Exception
    {
    	Collection<Empresa> empresas = getEmpresas(false);

    	empresaManager.expects(once()).method("findAll").will(returnValue(empresas));

    	action.execute();
    }

    public void testExecuteComIntegracaoAC() throws Exception
    {
    	Collection<Empresa> empresas = getEmpresas(true);

    	empresaManager.expects(once()).method("findAll").will(returnValue(empresas));
    	acPessoalImportadorGastos.expects(atLeastOnce()).method("importarGastos").with(ANYTHING,ANYTHING).will(returnValue(new String[1]));
    	gastoEmpresaManager.expects(atLeastOnce()).method("importarGastosAC").with(ANYTHING,ANYTHING,ANYTHING);

    	action.execute();
    }

	public void testExecuteCatch() throws Exception
    {
    	Collection<Empresa> empresas = getEmpresas(true);

    	empresaManager.expects(once()).method("findAll").will(returnValue(empresas));

    	acPessoalImportadorGastos.expects(atLeastOnce()).method("importarGastos").with(ANYTHING,ANYTHING).will(returnValue(new String[]{""}));

    	Exception exc = new Exception("Teste");
		gastoEmpresaManager.expects(atLeastOnce()).method("importarGastosAC").with(ANYTHING,ANYTHING,ANYTHING).will(throwException(exc));

		action.execute();
    }
}