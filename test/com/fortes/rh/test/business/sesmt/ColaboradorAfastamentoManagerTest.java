package com.fortes.rh.test.business.sesmt;

import java.io.File;
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
import com.fortes.rh.model.sesmt.relatorio.ColaboradorAfastamentoMatriz;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.AfastamentoFactory;
import com.fortes.rh.test.util.mockObjects.MockImportacaoCSVUtil;
import com.fortes.rh.util.DateUtil;
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
		assertNotNull(colaboradorAfastamentoManager.findAllSelect(0, 0, 1L, "", estabelecimentoCheck, null, colaboradorAfastamento, "DESC", false, false, 'T'));
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
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").will(returnValue(areaOrganizacionais ));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(areaOrganizacionais));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").will(returnValue(areaOrganizacional));

		assertNotNull(colaboradorAfastamentoManager.findRelatorioAfastamentos(1L, "", estabelecimentoCheck, null, colaboradorAfastamento, false, false, 'T'));
	}

	public void testMontaMatrizResumo() throws Exception
	{
		Collection<ColaboradorAfastamento> afastamentos = new ArrayList<ColaboradorAfastamento>();
		ColaboradorAfastamento colaboradorAfastamentoMock = new ColaboradorAfastamento();
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);
		colaborador.setNome("Joao");
		colaborador.setAreaOrganizacional(areaOrganizacional );
		
		colaboradorAfastamentoMock.setColaborador(colaborador);
		colaboradorAfastamentoMock.setInicio(DateUtil.criarDataMesAno(01, 02, 2000));
		afastamentos.add(colaboradorAfastamentoMock);
		
		colaboradorAfastamentoDao.expects(once()).method("findRelatorioResumoAfastamentos").will(returnValue(afastamentos));
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativas").will(returnValue(new ArrayList<AreaOrganizacional>()));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(new ArrayList<AreaOrganizacional>()));
		areaOrganizacionalManager.expects(atLeastOnce()).method("getAreaOrganizacional").will(returnValue(new AreaOrganizacional()));

		ColaboradorAfastamento colaboradorAfastamento = new ColaboradorAfastamento();
		colaboradorAfastamento.setInicio(DateUtil.criarDataMesAno(01, 01, 2000));
		colaboradorAfastamento.setFim(DateUtil.criarDataMesAno(01, 05, 2000));

		Collection<ColaboradorAfastamentoMatriz> retorno = colaboradorAfastamentoManager.montaMatrizResumo(null, null, null, null, colaboradorAfastamento, 'N', 'I', false);
		Collection<ColaboradorAfastamento> retornoAfastamentos = ((ColaboradorAfastamentoMatriz) retorno.toArray()[0]).getColaboradorAfastamentos();
		
		assertEquals(5, retornoAfastamentos.size());
		
		ColaboradorAfastamento afastamento01 = (ColaboradorAfastamento) retornoAfastamentos.toArray()[0];
		assertEquals(colaborador.getId(), afastamento01.getColaborador().getId());
		assertEquals(null, afastamento01.getAfastamento());
		assertEquals(new Integer(0), afastamento01.getQtdDias());
		
		ColaboradorAfastamento afastamento02 = (ColaboradorAfastamento) retornoAfastamentos.toArray()[1];
		assertEquals(colaborador.getId(), afastamento02.getColaborador().getId());
	}
	
	public void testFindRelatorioAfastamentosException()
	{
		colaboradorAfastamentoDao.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<ColaboradorAfastamento>()));

		Exception exception = null;

		try
		{
			colaboradorAfastamentoManager.findRelatorioAfastamentos(1L, "", estabelecimentoCheck, null, colaboradorAfastamento, false, false, 'T');
		}
		catch (ColecaoVaziaException e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
	
	public void testImportarAfastamentos() throws Exception
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
		
		ColaboradorAfastamento colaboradorAfastamento3 = new ColaboradorAfastamento();
		colaboradorAfastamento3.setAfastamentoDescricao("babau");
		colaboradorAfastamento3.setColaboradorCodigoAC("000999");
		colaboradorAfastamento3.setMedicoCrm("Mariola");
		
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = Arrays.asList(colaboradorAfastamento1,colaboradorAfastamento2, colaboradorAfastamento3);
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
		Colaborador colaborador3 = ColaboradorFactory.getEntity(3L);
		colaborador3.setCodigoAC("000999");
		
		colaboradorManager.expects(once()).method("findByCodigoAC").with(eq("000391"),eq(empresa)).will(returnValue(colaborador1));
		colaboradorManager.expects(once()).method("findByCodigoAC").with(eq("000010"),eq(empresa)).will(returnValue(colaborador2));
		colaboradorManager.expects(once()).method("findByCodigoAC").with(eq("000999"),eq(empresa)).will(returnValue(null));
		
		colaboradorAfastamentoDao.expects(atLeastOnce()).method("exists").will(returnValue(false));
		colaboradorAfastamentoDao.expects(atLeastOnce()).method("save");
		
		colaboradorAfastamentoManager.importarCSV(arquivo, null, empresa);
		
		assertEquals(1, colaboradorAfastamentoManager.getCountTiposAfastamentosCriados().intValue());
		assertEquals(2, colaboradorAfastamentoManager.getCountAfastamentosImportados().intValue());
	}
	
	public void testPossuiAfastamentoNestePeriodoDuasDatasFalse() throws Exception
	{
		Colaborador colab = ColaboradorFactory.getEntity();
		colab.setId(1L);
		
		ColaboradorAfastamento colabAfastamentoExistente = new ColaboradorAfastamento();
		colabAfastamentoExistente.setAfastamentoDescricao("Existente");
		colabAfastamentoExistente.setColaborador(colab);
		colabAfastamentoExistente.setInicio(DateUtil.criarDataMesAno(01, 12, 2012));
		colabAfastamentoExistente.setFim(DateUtil.criarDataMesAno(05, 12, 2012));

		Collection<ColaboradorAfastamento> colaboradorAfastamentos = new ArrayList<ColaboradorAfastamento>();
		colaboradorAfastamentos.add(colabAfastamentoExistente);
		
		ColaboradorAfastamento colabAfastamentoNovo = new ColaboradorAfastamento();
		colabAfastamentoNovo.setColaborador(colab);
		colabAfastamentoNovo.setAfastamentoDescricao("Novo");
		colabAfastamentoNovo.setInicio(DateUtil.criarDataMesAno(03, 12, 2012));
		colabAfastamentoNovo.setFim(DateUtil.criarDataMesAno(04, 12, 2012));
		
		colaboradorAfastamentoDao.expects(once()).method("findByColaborador").with(eq(colabAfastamentoNovo.getColaborador().getId())).will(returnValue(colaboradorAfastamentos));

		assertFalse(colaboradorAfastamentoManager.possuiAfastamentoNestePeriodo(colabAfastamentoNovo, false));
	}

	public void testPossuiAfastamentoNestePeriodoDuasDataTrue() throws Exception
	{
		Colaborador colab = ColaboradorFactory.getEntity();
		colab.setId(1L);
		
		ColaboradorAfastamento colabAfastamentoExistente = new ColaboradorAfastamento();
		colabAfastamentoExistente.setAfastamentoDescricao("Existente");
		colabAfastamentoExistente.setColaborador(colab);
		colabAfastamentoExistente.setInicio(DateUtil.criarDataMesAno(01, 12, 2012));
		colabAfastamentoExistente.setFim(DateUtil.criarDataMesAno(05, 12, 2012));
		
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = new ArrayList<ColaboradorAfastamento>();
		colaboradorAfastamentos.add(colabAfastamentoExistente);
		
		ColaboradorAfastamento colabAfastamentoNovo = new ColaboradorAfastamento();
		colabAfastamentoNovo.setColaborador(colab);
		colabAfastamentoNovo.setAfastamentoDescricao("Novo");
		colabAfastamentoNovo.setInicio(DateUtil.criarDataMesAno(06, 12, 2012));
		colabAfastamentoNovo.setFim(DateUtil.criarDataMesAno(07, 12, 2012));
		
		colaboradorAfastamentoDao.expects(once()).method("findByColaborador").with(eq(colabAfastamentoNovo.getColaborador().getId())).will(returnValue(colaboradorAfastamentos));
		
		assertTrue(colaboradorAfastamentoManager.possuiAfastamentoNestePeriodo(colabAfastamentoNovo, false));
	}
	
	public void testPossuiAfastamentoNestePeriodoUmaDataTrue() throws Exception
	{
		Colaborador colab = ColaboradorFactory.getEntity();
		colab.setId(1L);
		
		ColaboradorAfastamento colabAfastamentoExistente = new ColaboradorAfastamento();
		colabAfastamentoExistente.setAfastamentoDescricao("Existente");
		colabAfastamentoExistente.setColaborador(colab);
		colabAfastamentoExistente.setInicio(DateUtil.criarDataMesAno(01, 12, 2012));
		colabAfastamentoExistente.setFim(DateUtil.criarDataMesAno(05, 12, 2012));
		
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = new ArrayList<ColaboradorAfastamento>();
		colaboradorAfastamentos.add(colabAfastamentoExistente);
		
		ColaboradorAfastamento colabAfastamentoNovo = new ColaboradorAfastamento();
		colabAfastamentoNovo.setColaborador(colab);
		colabAfastamentoNovo.setAfastamentoDescricao("Novo");
		colabAfastamentoNovo.setInicio(DateUtil.criarDataMesAno(06, 12, 2012));
		
		colaboradorAfastamentoDao.expects(once()).method("findByColaborador").with(eq(colabAfastamentoNovo.getColaborador().getId())).will(returnValue(colaboradorAfastamentos));
		
		assertTrue(colaboradorAfastamentoManager.possuiAfastamentoNestePeriodo(colabAfastamentoNovo, false));
	}

	public void testPossuiAfastamentoNestePeriodoUmaDatafalse() throws Exception
	{
		Colaborador colab = ColaboradorFactory.getEntity();
		colab.setId(1L);
		
		ColaboradorAfastamento colabAfastamentoExistente = new ColaboradorAfastamento();
		colabAfastamentoExistente.setAfastamentoDescricao("Existente");
		colabAfastamentoExistente.setColaborador(colab);
		colabAfastamentoExistente.setInicio(DateUtil.criarDataMesAno(01, 12, 2012));
		colabAfastamentoExistente.setFim(DateUtil.criarDataMesAno(05, 12, 2012));
		
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = new ArrayList<ColaboradorAfastamento>();
		colaboradorAfastamentos.add(colabAfastamentoExistente);
		
		ColaboradorAfastamento colabAfastamentoNovo = new ColaboradorAfastamento();
		colabAfastamentoNovo.setColaborador(colab);
		colabAfastamentoNovo.setAfastamentoDescricao("Novo");
		colabAfastamentoNovo.setInicio(DateUtil.criarDataMesAno(03, 12, 2012));
		
		colaboradorAfastamentoDao.expects(once()).method("findByColaborador").with(eq(colabAfastamentoNovo.getColaborador().getId())).will(returnValue(colaboradorAfastamentos));
		
		assertFalse(colaboradorAfastamentoManager.possuiAfastamentoNestePeriodo(colabAfastamentoNovo, false));
	}
	
	public void testPossuiAfastamentoNestePeriodoUmaData2True() throws Exception
	{
		Colaborador colab = ColaboradorFactory.getEntity();
		colab.setId(1L);
		
		ColaboradorAfastamento colabAfastamentoExistente = new ColaboradorAfastamento();
		colabAfastamentoExistente.setAfastamentoDescricao("Existente");
		colabAfastamentoExistente.setColaborador(colab);
		colabAfastamentoExistente.setInicio(DateUtil.criarDataMesAno(01, 12, 2012));
		
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = new ArrayList<ColaboradorAfastamento>();
		colaboradorAfastamentos.add(colabAfastamentoExistente);
		
		ColaboradorAfastamento colabAfastamentoNovo = new ColaboradorAfastamento();
		colabAfastamentoNovo.setColaborador(colab);
		colabAfastamentoNovo.setAfastamentoDescricao("Novo");
		colabAfastamentoNovo.setInicio(DateUtil.criarDataMesAno(03, 12, 2012));
		
		colaboradorAfastamentoDao.expects(once()).method("findByColaborador").with(eq(colabAfastamentoNovo.getColaborador().getId())).will(returnValue(colaboradorAfastamentos));
		
		assertTrue(colaboradorAfastamentoManager.possuiAfastamentoNestePeriodo(colabAfastamentoNovo, false));
	}
	
	public void testPossuiAfastamentoNestePeriodoUmaData2False() throws Exception
	{
		Colaborador colab = ColaboradorFactory.getEntity();
		colab.setId(1L);
		
		ColaboradorAfastamento colabAfastamentoExistente = new ColaboradorAfastamento();
		colabAfastamentoExistente.setAfastamentoDescricao("Existente");
		colabAfastamentoExistente.setColaborador(colab);
		colabAfastamentoExistente.setInicio(DateUtil.criarDataMesAno(01, 12, 2012));
		
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = new ArrayList<ColaboradorAfastamento>();
		colaboradorAfastamentos.add(colabAfastamentoExistente);
		
		ColaboradorAfastamento colabAfastamentoNovo = new ColaboradorAfastamento();
		colabAfastamentoNovo.setColaborador(colab);
		colabAfastamentoNovo.setAfastamentoDescricao("Novo");
		colabAfastamentoNovo.setInicio(DateUtil.criarDataMesAno(01, 12, 2012));
		
		colaboradorAfastamentoDao.expects(once()).method("findByColaborador").with(eq(colabAfastamentoNovo.getColaborador().getId())).will(returnValue(colaboradorAfastamentos));
		
		assertFalse(colaboradorAfastamentoManager.possuiAfastamentoNestePeriodo(colabAfastamentoNovo, false));
	}
	
	public void testFindByColaborador()
	{
		colaboradorAfastamentoDao.expects(once()).method("findByColaborador").with(eq(1000L)).will(returnValue(new ArrayList<ColaboradorAfastamento>()));
		assertTrue(colaboradorAfastamentoManager.findByColaborador(1000L).isEmpty());
	}
}