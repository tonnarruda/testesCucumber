package com.fortes.rh.test.business.importacao;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.importacao.ImportacaoColaboradorManagerImpl;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockImportacaoCSVUtil;
import com.fortes.rh.util.importacao.ImportacaoCSVUtil;

public class ImportacaoColaboradorManagerTest extends MockObjectTestCase {

	private ImportacaoColaboradorManagerImpl importacaoColaboradorManager = new ImportacaoColaboradorManagerImpl();
	private Mock colaboradorManager;
	
	@Override
	protected void setUp() throws Exception {
		
		colaboradorManager = mock(ColaboradorManager.class);
		importacaoColaboradorManager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		
		 Mockit.redefineMethods(ImportacaoCSVUtil.class, MockImportacaoCSVUtil.class);
	}
	
	public void testImportarDadosPessoaisByCpf() throws Exception
    {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
    	Colaborador colaborador = new Colaborador();
    	colaborador.setMatricula("123");
    	colaborador.setPessoalCpf("26745534304");
    	colaborador.setEnderecoLogradouro("Rua Três Corações");
    	
    	Colaborador colaborador2NaoExisteNoRH = new Colaborador();
    	colaborador2NaoExisteNoRH.setPessoalCpf("52986063420");
    	colaborador2NaoExisteNoRH.setContatoCelular("88889000");
    	
    	Collection<Colaborador> colaboradoresDoCSV = Arrays.asList(colaborador,colaborador2NaoExisteNoRH);
    	MockImportacaoCSVUtil.colaboradores = colaboradoresDoCSV; //para uso do Mock
    	
    	colaboradorManager.expects(once()).method("findByCpf").with(eq("26745534304"), eq(1L), eq(null)).will(returnValue(Arrays.asList(colaborador)));
    	colaboradorManager.expects(once()).method("findByCpf").with(eq("52986063420"), eq(1L), eq(null)).will(returnValue(null));
    	colaboradorManager.expects(once()).method("updateInfoPessoaisByCpf").with(eq(colaborador), eq(1L)).will(returnValue(true));
    	
    	importacaoColaboradorManager.importarDadosPessoaisByCpf(new File("arquivo.csv"), empresa);
    }
}
