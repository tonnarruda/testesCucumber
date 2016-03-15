package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.sesmt.FuncaoManagerImpl;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoMedicaoRiscoManager;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.exception.PppRelatorioException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.relatorio.QtdPorFuncaoRelatorio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class FuncaoManagerTest extends MockObjectTestCase
{
	private FuncaoManagerImpl funcaoManager = new FuncaoManagerImpl();
	private Mock funcaoDao = null;
	private Mock historicoColaboradorManager;
	private Mock historicoAmbienteManager;
	private Mock historicoFuncaoManager;
	private Mock riscoMedicaoRiscoManager;
	private Mock riscoFuncaoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        funcaoDao = new Mock(FuncaoDao.class);
        funcaoManager.setDao((FuncaoDao) funcaoDao.proxy());
        
        historicoAmbienteManager = mock(HistoricoAmbienteManager.class);
        funcaoManager.setHistoricoAmbienteManager((HistoricoAmbienteManager) historicoAmbienteManager.proxy());
        
        riscoMedicaoRiscoManager = mock(RiscoMedicaoRiscoManager.class);
        funcaoManager.setRiscoMedicaoRiscoManager((RiscoMedicaoRiscoManager) riscoMedicaoRiscoManager.proxy());
        
        historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
        historicoFuncaoManager = mock(HistoricoFuncaoManager.class);
        riscoFuncaoManager = mock(RiscoFuncaoManager.class);
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        MockSpringUtil.mocks.put("historicoFuncaoManager", historicoFuncaoManager);
        MockSpringUtil.mocks.put("historicoColaboradorManager", historicoColaboradorManager);
        MockSpringUtil.mocks.put("riscoFuncaoManager", riscoFuncaoManager);
    }
    
    @Override
    protected void tearDown() throws Exception {
    	MockSecurityUtil.verifyRole = false;
    	Mockit.restoreAllOriginalDefinitions();
    }

    public void testGetCount()
	{
    	Collection<Funcao> funcaos = new ArrayList<Funcao>();

    	funcaoDao.expects(once()).method("getCount").with(ANYTHING).will(returnValue(funcaos.size()));

    	assertEquals(funcaoManager.getCount(1L).intValue(), funcaos.size());
	}

	public void testFindByCargo()throws Exception
	{
		Collection<Funcao> funcaos = new ArrayList<Funcao>();

		Cargo cargo = new Cargo();
		cargo.setId(2L);

		Funcao f1 = new Funcao();
		f1.setId(1L);
		f1.setCargo(cargo);

		Funcao f2 = new Funcao();
		f2.setId(2L);
		f2.setCargo(cargo);

		funcaos.add(f1);
		funcaos.add(f2);

		funcaoDao.expects(once()).method("findByCargo").with(eq(0), eq(0), eq(cargo.getId())).will(returnValue(funcaos));
		Collection<Funcao> retorno1 = funcaoManager.findByCargo(cargo.getId());

		assertEquals(funcaos, retorno1);

		funcaoDao.expects(once()).method("findByCargo").with(eq(1), eq(15), eq(cargo.getId())).will(returnValue(funcaos));
		Collection<Funcao> retorno2 = funcaoManager.findByCargo(1, 15, cargo.getId());

		assertEquals(funcaos, retorno2);
	}

	public void testFindByEmpresa()throws Exception
	{
		Collection<Funcao> funcaos = new ArrayList<Funcao>();
		funcaoDao.expects(once()).method("findByEmpresa").with(eq(1L)).will(returnValue(funcaos));
		Collection<Funcao> funcaoRetorno = funcaoManager.findByEmpresa(1L);
		assertEquals(funcaos, funcaoRetorno);
	}
	
	public void testFindByIdProjection()
	{
		Funcao funcao = FuncaoFactory.getEntity(1L);
		
		funcaoDao.expects(once()).method("findByIdProjection").with(eq(funcao.getId())).will(returnValue(funcao));

		assertEquals(funcao, funcaoManager.findByIdProjection(funcao.getId()));
	}

	public void testGetIdsFuncoes()throws Exception
	{
		Funcao f1 = new Funcao();
		f1.setId(1L);
		f1.setNome("F1");

		Funcao f2 = new Funcao();
		f2.setId(2L);
		f2.setNome("F2");

		Collection<Long> colLong = new ArrayList<Long>();
		colLong.add(f1.getId());
		colLong.add(f2.getId());

		HistoricoColaborador hc1 = new HistoricoColaborador();
		hc1.setId(3L);
		hc1.setFuncao(f1);

		HistoricoColaborador hc2 = new HistoricoColaborador();
		hc2.setId(4L);
		hc2.setFuncao(f2);

		HistoricoColaborador hc3 = new HistoricoColaborador();
		hc3.setId(5L);
		hc3.setFuncao(f2);

		Collection<HistoricoColaborador> colhc = new ArrayList<HistoricoColaborador>();
		colhc.add(hc1);
		colhc.add(hc2);
		colhc.add(hc3);

		Collection<Long> idsRetorno = funcaoManager.getIdsFuncoes(colhc);
		assertEquals(colLong.size(),idsRetorno.size());
	}

	public void testFindFuncaoByFaixa()
	{
		Cargo cargo = CargoFactory.getEntity(1L);

		FaixaSalarial faixa = FaixaSalarialFactory.getEntity(1L);
		faixa.setCargo(cargo);

		Funcao funcao1 = FuncaoFactory.getEntity(1L);
		funcao1.setNome("Programador");
		funcao1.setCargo(cargo);

		Funcao funcao2 = FuncaoFactory.getEntity(2L);
		funcao2.setNome("Arquiteto");
		funcao2.setCargo(cargo);

		Collection<Funcao> funcaos = new ArrayList<Funcao>();
		funcaos.add(funcao1);
		funcaos.add(funcao2);

		funcaoDao.expects(once()).method("findFuncaoByFaixa").with(ANYTHING).will(returnValue(funcaos));

		Collection<Funcao> retorno = funcaoManager.findFuncaoByFaixa(faixa.getId());

		assertEquals(funcaos.size(), retorno.size());
	}
	
	public void testGetQtdColaboradorByFuncao()
	{
		Funcao funcao1 = FuncaoFactory.getEntity(1L);
		funcao1.setNome("Programador");

		Funcao funcao2 = FuncaoFactory.getEntity(2L);
		funcao2.setNome("Arquiteto");
		
		Collection<Funcao> funcaos = new ArrayList<Funcao>();
		funcaos.add(funcao1);
		funcaos.add(funcao2);
		
		Date data = new Date();
		Collection<Object[]> retorno = new ArrayList<Object[]>();
		retorno.add(new Object[]{1L, "Motorista", 1, null});
		retorno.add(new Object[]{1L, "Motorista", null, 1});
		retorno.add(new Object[]{2L, "Manobrista", null, 1});
		
		funcaoDao.expects(atLeastOnce()).method("getQtdColaboradorByFuncao").with(ANYTHING, ANYTHING, eq(data), eq('T')).will(returnValue(retorno));
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(3L);
		
		Collection<QtdPorFuncaoRelatorio> funcoesComTotalHomens_E_Mulheres = funcaoManager.getQtdColaboradorByFuncao(empresa.getId(), estabelecimento.getId(), data, 'T');
		assertEquals(2, funcoesComTotalHomens_E_Mulheres.size());
		
		QtdPorFuncaoRelatorio qtdPorFuncaoRelatorio1 = ((QtdPorFuncaoRelatorio)funcoesComTotalHomens_E_Mulheres.toArray()[0]);
		assertEquals((Long)1L, qtdPorFuncaoRelatorio1.getFuncaoId());
		assertEquals((Integer)1, qtdPorFuncaoRelatorio1.getQtdHomens());
		assertEquals((Integer)1, qtdPorFuncaoRelatorio1.getQtdMulheres());
		
		QtdPorFuncaoRelatorio qtdPorFuncaoRelatorio2 = ((QtdPorFuncaoRelatorio)funcoesComTotalHomens_E_Mulheres.toArray()[1]);
		assertEquals((Long)2L, qtdPorFuncaoRelatorio2.getFuncaoId());
		assertNull(qtdPorFuncaoRelatorio2.getQtdHomens());
		assertEquals((Integer)1, qtdPorFuncaoRelatorio2.getQtdMulheres());
	}
	
    public void testPopulaCheckBox()
    {
    	Funcao fun1 = FuncaoFactory.getEntity(1L);
    	Funcao fun2 = FuncaoFactory.getEntity(2L);
    	
    	funcaoDao.expects(once()).method("findAll").will(returnValue(Arrays.asList(fun1, fun2)));
    	assertEquals(2, funcaoManager.populaCheckBox().size());
    	
    }
    public void testPopulaCheckBoxException()
    {
    	funcaoDao.expects(once()).method("findAll").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
    	assertEquals(0, funcaoManager.populaCheckBox().size());
    }
	
	@SuppressWarnings("deprecation")
	public void testPopulaRelatorioPppExcecao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setControlaRiscoPor('A');
		
		Ambiente ambiente = AmbienteFactory.getEntity(10L);
		Ambiente ambiente2 = AmbienteFactory.getEntity(12L);
		Funcao funcao = FuncaoFactory.getEntity(3L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
		
		Cargo cargo = CargoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		
		HistoricoColaborador historicoColaboradorSemAmbiente = HistoricoColaboradorFactory.getEntity(3L);
		historicoColaboradorSemAmbiente.setData(DateUtil.criarAnoMesDia(2005, 9, 1));
		historicoColaboradorSemAmbiente.setFuncao(funcao);
		historicoColaboradorSemAmbiente.setColaborador(colaborador);
		historicoColaboradorSemAmbiente.setFaixaSalarial(faixaSalarial);
		
		HistoricoColaborador historicoColaboradorSemAmbienteEFuncao = new HistoricoColaborador();
		historicoColaboradorSemAmbienteEFuncao.setData(DateUtil.criarAnoMesDia(2005, 9, 27));
		historicoColaboradorSemAmbienteEFuncao.setColaborador(colaborador);
		historicoColaboradorSemAmbienteEFuncao.setFaixaSalarial(faixaSalarial);
		
		HistoricoColaborador historicoColaboradorComAmbienteSemHistorico = HistoricoColaboradorFactory.getEntity(10L);
		historicoColaboradorComAmbienteSemHistorico.setData(DateUtil.criarAnoMesDia(2006, 1, 30));
		historicoColaboradorComAmbienteSemHistorico.setFuncao(funcao);
		historicoColaboradorComAmbienteSemHistorico.setAmbiente(ambiente);
		historicoColaboradorComAmbienteSemHistorico.setColaborador(colaborador);
		historicoColaboradorComAmbienteSemHistorico.setFaixaSalarial(faixaSalarial);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setData(DateUtil.criarAnoMesDia(2009, 4, 19));
		historicoAmbiente.setAmbiente(ambiente2);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setData(DateUtil.criarAnoMesDia(2005, 4, 19));
		historicoFuncao.setFuncao(funcao);
		
		HistoricoColaborador historicoColaboradorComHistoricoAmbienteSemMedicao = HistoricoColaboradorFactory.getEntity(15L);
		historicoColaboradorComHistoricoAmbienteSemMedicao.setData(DateUtil.criarAnoMesDia(2009, 4, 20));
		historicoColaboradorComHistoricoAmbienteSemMedicao.setAmbiente(ambiente2);
		historicoColaboradorComHistoricoAmbienteSemMedicao.setFuncao(funcao);
		historicoColaboradorComHistoricoAmbienteSemMedicao.setColaborador(colaborador);
		historicoColaboradorComHistoricoAmbienteSemMedicao.setFaixaSalarial(faixaSalarial);
		
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaboradorSemAmbiente);
		historicoColaboradors.add(historicoColaboradorSemAmbienteEFuncao);
		historicoColaboradors.add(historicoColaboradorComAmbienteSemHistorico);
		historicoColaboradors.add(historicoColaboradorComHistoricoAmbienteSemMedicao);
		
		historicoColaboradorManager.expects(once()).method("findByColaboradorData").will(returnValue(historicoColaboradors));
		historicoAmbienteManager.expects(once()).method("findUltimoHistoricoAteData").with(eq(historicoColaboradorComAmbienteSemHistorico.getAmbiente().getId()), eq(historicoColaboradorComAmbienteSemHistorico.getData())).will(returnValue(null));
		historicoAmbienteManager.expects(once()).method("findUltimoHistoricoAteData").with(eq(historicoColaboradorComHistoricoAmbienteSemMedicao.getAmbiente().getId()), eq(historicoColaboradorComHistoricoAmbienteSemMedicao.getData())).will(returnValue(historicoAmbiente));
		
		historicoColaboradorManager.expects(once()).method("filtraHistoricoColaboradorParaPPP").withAnyArguments().will(returnValue(historicoColaboradors));
		historicoFuncaoManager.expects(once()).method("findUltimoHistoricoAteData").with(eq(funcao.getId()), eq(historicoColaboradorSemAmbiente.getData())).will(returnValue(null));
		historicoFuncaoManager.expects(once()).method("findUltimoHistoricoAteData").with(eq(funcao.getId()), eq(historicoColaboradorComAmbienteSemHistorico.getData())).will(returnValue(historicoFuncao));
		historicoFuncaoManager.expects(once()).method("findUltimoHistoricoAteData").with(eq(funcao.getId()), eq(historicoColaboradorComHistoricoAmbienteSemMedicao.getData())).will(returnValue(historicoFuncao));
		PppRelatorioException pppRelatorioException = null;
		Exception exception = null;
		
		MockSecurityUtil.verifyRole = true;
		
		try 
		{
			funcaoManager.populaRelatorioPpp(colaborador , empresa, new Date(), "111", null, "Resp.", "obs", new String[5]);
		}
		catch (PppRelatorioException e)
		{
			pppRelatorioException = e;
		} 
		catch (Exception e) 
		{
			exception = e;
			e.printStackTrace();
		}
		
		assertNull(exception);
		assertNotNull(pppRelatorioException);
		
		String mensagemFormatada = pppRelatorioException.getMensagemDeInformacao();
		
		
		String result = "Existem pendências para a geração desse relatório. Verifique as informações abaixo antes de prosseguir: <br>" +
		"<a href='../../cargosalario/historicoColaborador/prepareUpdateAmbientesEFuncoes.action?colaborador.id=1000'>" +
		"01/09/2005 - Situação do colaborador não possui ambiente definido.</a><br />" +
		"<a href='../../cargosalario/historicoColaborador/prepareUpdateAmbientesEFuncoes.action?colaborador.id=1000'>" +
		"27/09/2005 - Situação do colaborador não possui ambiente definido.</a><br />" +
		"<a href='../../cargosalario/historicoColaborador/prepareUpdateAmbientesEFuncoes.action?colaborador.id=1000'>" +
		"27/09/2005 - Situação do colaborador não possui função definida.</a><br />" +
		"<a href='../ambiente/prepareUpdate.action?ambiente.id=10'>" +
		"30/01/2006 - Ambiente do colaborador não possui histórico nesta data.</a><br />" +
		"<a href='../funcao/prepareUpdate.action?funcao.id=3&cargoTmp.id=1&veioDoSESMT=false'>" +
		"01/09/2005 - Função do colaborador não possui histórico nesta data.</a><br />";

		assertEquals(result, mensagemFormatada);
		
	}
	
	public void testRemoveFuncao() throws Exception
	{
		Funcao funcao = FuncaoFactory.getEntity(32L);
		
		HistoricoFuncao hist1 = new HistoricoFuncao();
		hist1.setId(1L);

		HistoricoFuncao hist2 = new HistoricoFuncao();
		hist2.setId(2L);
		
		riscoFuncaoManager.expects(once()).method("removeByFuncao");
		historicoFuncaoManager.expects(once()).method("findByFuncao").will(returnValue(Arrays.asList(hist1, hist2)));
		historicoFuncaoManager.expects(atLeastOnce()).method("remove");
		
		funcaoDao.expects(once()).method("remove").isVoid();
		
		funcaoManager.removeFuncao(funcao);
	}
	
	public void testRemoveFuncaoException() 
	{
		Funcao funcao = FuncaoFactory.getEntity(32L);
		
		HistoricoFuncao hist1 = new HistoricoFuncao();
		hist1.setId(1L);

		HistoricoFuncao hist2 = new HistoricoFuncao();
		hist2.setId(2L);
		
		riscoFuncaoManager.expects(once()).method("removeByFuncao");
		historicoFuncaoManager.expects(once()).method("findByFuncao").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		
		Exception ex= null;
		
		try {
			funcaoManager.removeFuncao(funcao);
		} catch (Exception e) {
			ex = e;
		}
		
		assertNotNull(ex);
	}
}