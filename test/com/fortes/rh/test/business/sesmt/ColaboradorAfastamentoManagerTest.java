package com.fortes.rh.test.business.sesmt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManagerImpl;
import com.fortes.rh.dao.sesmt.ColaboradorAfastamentoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.AfastamentoFactory;
import com.fortes.rh.test.util.mockObjects.MockImportacaoCSVUtil;
import com.fortes.rh.util.importacao.ImportacaoCSVUtil;

public class ColaboradorAfastamentoManagerTest extends MockObjectTestCase
{
	private ColaboradorAfastamentoManagerImpl colaboradorAfastamentoManager = new ColaboradorAfastamentoManagerImpl();
	private Mock colaboradorAfastamentoDao = null;
	private Mock areaOrganizacionalManager;
	private Mock afastamentoManager;
	private Mock colaboradorManager;

	String[] estabelecimentoCheck = new String[]{};
	ColaboradorAfastamento colaboradorAfastamento = new ColaboradorAfastamento();

	protected void setUp() throws Exception
    {
        super.setUp();
        colaboradorAfastamentoDao = new Mock(ColaboradorAfastamentoDao.class);
        colaboradorAfastamentoManager.setDao((ColaboradorAfastamentoDao) colaboradorAfastamentoDao.proxy());

        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        colaboradorAfastamentoManager.setAreaOrganizacionalManager((AreaOrganizacionalManager)areaOrganizacionalManager.proxy());
        
        afastamentoManager = mock(AfastamentoManager.class);
        colaboradorAfastamentoManager.setAfastamentoManager((AfastamentoManager) afastamentoManager.proxy());
        
        colaboradorManager = mock(ColaboradorManager.class);
        colaboradorAfastamentoManager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        
        Mockit.redefineMethods(ImportacaoCSVUtil.class, MockImportacaoCSVUtil.class);
    }

	public void testGetCount()
	{
		colaboradorAfastamentoDao.expects(once()).method("getCount").will(returnValue(1));
		assertEquals(Integer.valueOf(1),colaboradorAfastamentoManager.getCount(1L, "", estabelecimentoCheck, colaboradorAfastamento));
	}

	public void testFindAllSelect()
	{
		colaboradorAfastamentoDao.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<ColaboradorAfastamento>()));
		assertNotNull(colaboradorAfastamentoManager.findAllSelect(0, 0, 1L, "", estabelecimentoCheck, colaboradorAfastamento, "DESC", false));
	}

	public void testFindRelatorioAfastamentos() throws Exception
	{
		Collection<ColaboradorAfastamento> colecao = new ArrayList<ColaboradorAfastamento>();
		ColaboradorAfastamento colaboradorAfastamento = new ColaboradorAfastamento();
		Colaborador colaborador = new Colaborador();
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		colaborador.setAreaOrganizacional(areaOrganizacional );
		colaboradorAfastamento.setColaborador(colaborador);
		colecao.add(colaboradorAfastamento);
		Collection<AreaOrganizacional> areaOrganizacionais = new ArrayList<AreaOrganizacional>();

		colaboradorAfastamentoDao.expects(once()).method("findAllSelect").will(returnValue(colecao));
		areaOrganizacionalManager.expects(once()).method("findAllList").will(returnValue(areaOrganizacionais ));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(areaOrganizacionais));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").will(returnValue(areaOrganizacional));

		assertNotNull(colaboradorAfastamentoManager.findRelatorioAfastamentos(1L, "", estabelecimentoCheck, colaboradorAfastamento, false));
	}

	public void testFindRelatorioAfastamentosException()
	{
		colaboradorAfastamentoDao.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<ColaboradorAfastamento>()));

		Exception exception = null;

		try
		{
			colaboradorAfastamentoManager.findRelatorioAfastamentos(1L, "", estabelecimentoCheck, colaboradorAfastamento, false);
		}
		catch (ColecaoVaziaException e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
	
	public void testImportarAfastamentos() throws IOException
	{
		File arquivo = new File("arquivo.csv");
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		ColaboradorAfastamento colaboradorAfastamento1 = new ColaboradorAfastamento();
		colaboradorAfastamento1.setAfastamentoDescricao("Licença paternidade");
		colaboradorAfastamento1.setColaboradorCodigoAC("000391");
		colaboradorAfastamento1.setMedicoCrm("Juscelino Silva");
		
		ColaboradorAfastamento colaboradorAfastamento2 = new ColaboradorAfastamento();
		colaboradorAfastamento2.setAfastamentoDescricao("Fratura");
		colaboradorAfastamento2.setColaboradorCodigoAC("000010");
		colaboradorAfastamento2.setMedicoCrm("Roberta Braga");
		
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = Arrays.asList(colaboradorAfastamento1,colaboradorAfastamento2);
		MockImportacaoCSVUtil.afastamentos = colaboradorAfastamentos;
		
		Afastamento afastamentoNaoCadastrado = AfastamentoFactory.getEntity();
		afastamentoNaoCadastrado.setDescricao("Licença paternidade");
		
		Afastamento afastamento = AfastamentoFactory.getEntity(1L);
		afastamento.setDescricao("FRATURA");
		Collection<Afastamento> afastamentosJaCadastrados = Arrays.asList(afastamento);
		
		afastamentoManager.expects(once()).method("findAll").withNoArguments().will(returnValue(afastamentosJaCadastrados));
		afastamentoManager.expects(once()).method("save").will(returnValue(afastamentoNaoCadastrado));
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		colaborador1.setCodigoAC("000391");
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		colaborador2.setCodigoAC("000010");
		
		colaboradorManager.expects(once()).method("findByCodigoAC").with(eq("000391"),eq(empresa)).will(returnValue(colaborador1));
		colaboradorManager.expects(once()).method("findByCodigoAC").with(eq("000010"),eq(empresa)).will(returnValue(colaborador2));
		
		colaboradorAfastamentoDao.expects(atLeastOnce()).method("save");
		
		colaboradorAfastamentoManager.importarCSV(arquivo, empresa);
		
		assertEquals(1, colaboradorAfastamentoManager.getCountTiposAfastamentosCriados().intValue());
		assertEquals(2, colaboradorAfastamentoManager.getCountAfastamentosImportados().intValue());
	}
	
	public void testFindByColaborador()
	{
		colaboradorAfastamentoDao.expects(once()).method("findByColaborador").with(eq(1000L)).will(returnValue(new ArrayList<ColaboradorAfastamento>()));
		assertTrue(colaboradorAfastamentoManager.findByColaborador(1000L).isEmpty());
	}
}