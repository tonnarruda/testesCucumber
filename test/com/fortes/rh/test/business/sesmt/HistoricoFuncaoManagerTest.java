package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManagerImpl;
import com.fortes.rh.business.sesmt.RiscoFuncaoManager;
import com.fortes.rh.dao.sesmt.HistoricoFuncaoDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.ExameFactory;
import com.fortes.rh.util.DateUtil;

public class HistoricoFuncaoManagerTest extends MockObjectTestCase
{
	private HistoricoFuncaoManagerImpl historicoFuncaoManager = new HistoricoFuncaoManagerImpl();
	private Mock historicoFuncaoDao = null;
	private Mock funcaoManager = null;
	private Mock exameManager = null;
	private Mock epiManager = null;
	private Mock riscoFuncaoManager = null;

    protected void setUp() throws Exception
    {
        super.setUp();
        historicoFuncaoDao = new Mock(HistoricoFuncaoDao.class);
        funcaoManager = new Mock(FuncaoManager.class);
        exameManager = new Mock(ExameManager.class);
        epiManager = new Mock(EpiManager.class);
        riscoFuncaoManager = new Mock(RiscoFuncaoManager.class);
        
        historicoFuncaoManager.setExameManager((ExameManager) exameManager.proxy());
        historicoFuncaoManager.setDao((HistoricoFuncaoDao) historicoFuncaoDao.proxy());
        historicoFuncaoManager.setFuncaoManager((FuncaoManager) funcaoManager.proxy());
        historicoFuncaoManager.setEpiManager((EpiManager) epiManager.proxy());
        historicoFuncaoManager.setRiscoFuncaoManager((RiscoFuncaoManager) riscoFuncaoManager.proxy());
    }

	public void testGetUltimoHistoricosByDateFuncaos() throws Exception
	{
		Funcao f1 = new Funcao();
		f1.setId(1L);

		Funcao f2 = new Funcao();
		f2.setId(2L);

		HistoricoFuncao hf1 = new HistoricoFuncao();
		hf1.setId(1L);
		hf1.setFuncao(f1);

		HistoricoFuncao hf2 = new HistoricoFuncao();
		hf2.setId(2L);
		hf2.setFuncao(f1);

		HistoricoFuncao hf3 = new HistoricoFuncao();
		hf3.setId(3L);
		hf3.setFuncao(f2);

		Collection<Long> funcaoIds = new ArrayList<Long>();
		funcaoIds.add(1L);

		Collection<HistoricoFuncao> historicoFuncaos = new ArrayList<HistoricoFuncao>();
		historicoFuncaos.add(hf1);
		historicoFuncaos.add(hf2);
		historicoFuncaos.add(hf3);

		historicoFuncaoDao.expects(once()).method("getHistoricosByDateFuncaos").with(ANYTHING, ANYTHING).will(returnValue(historicoFuncaos));
		Collection<HistoricoFuncao> hisroricoFuncaosRetorno = historicoFuncaoManager.getUltimoHistoricosByDateFuncaos(funcaoIds, new Date());

		assertEquals(2, hisroricoFuncaosRetorno.size());
	}

	public void testInserirPeriodos() throws Exception
	{
		HistoricoFuncao hf = new HistoricoFuncao();
		hf.setId(1L);
		hf.setData(new Date());

		HistoricoFuncao hf2 = new HistoricoFuncao();
		hf2.setId(2L);
		hf2.setData(new Date());

		Collection<HistoricoFuncao> colhf = new ArrayList<HistoricoFuncao>();
		colhf.add(hf);
		colhf.add(hf2);

		Collection<HistoricoFuncao> colhfRetorno = historicoFuncaoManager.inserirPeriodos(colhf);

		assertEquals(colhf, colhfRetorno);
	}

	public void testFindByIdProjection() throws Exception
	{
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setId(1L);
		historicoFuncao.setData(new Date());
		
		Exame exame = ExameFactory.getEntity();
		exame.setId(1L);
		
		Collection<Exame> exames = new ArrayList<Exame>();
		exames.add(exame);
		
		Epi epi = EpiFactory.getEntity(1L);
		
		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(epi);
		
		historicoFuncaoDao.expects(once()).method("findByIdProjection").with(eq(historicoFuncao.getId())).will(returnValue(historicoFuncao));
		exameManager.expects(once()).method("findByHistoricoFuncao").with(eq(historicoFuncao.getId())).will(returnValue(exames));		
		epiManager.expects(once()).method("findByHistoricoFuncao").with(eq(historicoFuncao.getId())).will(returnValue(epis));		
		
		HistoricoFuncao historicoFuncaoRetorno = historicoFuncaoManager.findByIdProjection(historicoFuncao.getId());
		
		assertEquals(historicoFuncao, historicoFuncaoRetorno);
		assertEquals(1, historicoFuncaoRetorno.getExames().size());
		assertEquals(1, historicoFuncaoRetorno.getEpis().size());
	}

	public void testFindHistoricoFuncaoColaborador() throws Exception
	{

		Funcao f1 = new Funcao();
		f1.setId(1L);
		f1.setNome("F1");

		HistoricoFuncao h1f1 = new HistoricoFuncao();
		h1f1.setFuncao(f1);
		h1f1.setData(DateUtil.criarDataMesAno(1, 1, 2005));

		HistoricoFuncao h2f1 = new HistoricoFuncao();
		h2f1.setFuncao(f1);
		h2f1.setData(DateUtil.criarDataMesAno(1, 2, 2006));

		HistoricoFuncao h3f1 = new HistoricoFuncao();
		h3f1.setFuncao(f1);
		h3f1.setData(DateUtil.criarDataMesAno(1, 2, 2007));

		HistoricoFuncao h4f1 = new HistoricoFuncao();
		h4f1.setFuncao(f1);
		h4f1.setData(DateUtil.criarDataMesAno(1, 3, 2007));

		Collection<HistoricoFuncao> colhf1 = new ArrayList<HistoricoFuncao>();
		colhf1.add(h2f1);
		colhf1.add(h3f1);
		colhf1.add(h4f1);

		Funcao f2 = new Funcao();
		f2.setId(2L);
		f2.setNome("F2");

		HistoricoFuncao h1f2 = new HistoricoFuncao();
		h1f2.setFuncao(f2);
		h1f2.setData(DateUtil.criarDataMesAno(1, 5, 2006));

		HistoricoFuncao h2f2 = new HistoricoFuncao();
		h2f2.setFuncao(f2);
		h2f2.setData(DateUtil.criarDataMesAno(1, 6, 2007));

		Collection<HistoricoFuncao> colhf2 = new ArrayList<HistoricoFuncao>();
		colhf2.add(h1f2);
		colhf2.add(h2f2);

		Collection<HistoricoFuncao> col2hf1 = new ArrayList<HistoricoFuncao>();
		col2hf1.add(h4f1);

		HistoricoColaborador hc = new HistoricoColaborador();
		hc.setId(1L);
		hc.setFuncao(f1);
		hc.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		hc.setDataProximoHistorico(DateUtil.criarDataMesAno(1, 5, 2007));

		HistoricoColaborador hc2 = new HistoricoColaborador();
		hc2.setId(2L);
		hc2.setFuncao(f2);
		hc2.setData(DateUtil.criarDataMesAno(1, 5, 2007));
		hc2.setDataProximoHistorico(DateUtil.criarDataMesAno(1, 7, 2007));

		HistoricoColaborador hc3 = new HistoricoColaborador();
		hc3.setId(3L);
		hc3.setFuncao(f1);
		hc3.setData(DateUtil.criarDataMesAno(1, 7, 2007));

		Collection<HistoricoColaborador> colhc = new ArrayList<HistoricoColaborador>();
		colhc.add(hc);
		colhc.add(hc2);
		colhc.add(hc3);

		Date dataLimite = DateUtil.criarDataMesAno(1, 1, 2008);
		Collection<Long> ids = new ArrayList<Long>();

		ids.add(hc3.getFuncao().getId());
		historicoFuncaoDao.expects(once()).method("findHistoricoByFuncoesId").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING}).will(returnValue(col2hf1));

		ids.clear();
		ids.add(hc2.getFuncao().getId());
		historicoFuncaoDao.expects(once()).method("findHistoricoByFuncoesId").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING}).will(returnValue(colhf2));

		ids.clear();
		ids.add(hc.getFuncao().getId());
		historicoFuncaoDao.expects(once()).method("findHistoricoByFuncoesId").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING}).will(returnValue(colhf1));

		Collection<HistoricoFuncao> colhfAll = new ArrayList<HistoricoFuncao>();
		colhfAll.addAll(colhf1);
		colhfAll.addAll(colhf2);
		colhfAll.addAll(col2hf1);

		Collection<HistoricoFuncao>	colhfRetorno = historicoFuncaoManager.findHistoricoFuncaoColaborador(colhc, dataLimite, null);

		assertEquals(colhfRetorno.size(), colhfAll.size());

	}

	public void testSaveHistorico()
	{
		Funcao funcao = FuncaoFactory.getEntity(1L);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setFuncao(funcao);

		Long[] examesChecked = new Long[]{1L};
		Long[] episChecked = new Long[]{1L};
		Long[] riscosChecked = new Long[]{1L};
		Collection<RiscoFuncao> riscosFuncoes = new ArrayList<RiscoFuncao>();
		historicoFuncaoDao.expects(once()).method("findByData").with(eq(historicoFuncao.getData()), eq(historicoFuncao.getId()), eq(funcao.getId())).will(returnValue(null));
		historicoFuncaoDao.expects(once()).method("save").with(eq(historicoFuncao));

		Exception exception = null;
		
		try {
			historicoFuncaoManager.saveHistorico(historicoFuncao, examesChecked, episChecked, riscosChecked, riscosFuncoes);
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	public void testUpdateHistorico()
	{
		Funcao funcao = FuncaoFactory.getEntity(1L);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setFuncao(funcao);
		historicoFuncao.setId(1L);
		
		Long[] examesChecked = new Long[]{1L};
		Long[] episChecked = new Long[]{1L};
		Long[] riscosChecked = new Long[]{1L};
		Collection<RiscoFuncao> riscosFuncoes = new ArrayList<RiscoFuncao>();
		
		historicoFuncaoDao.expects(once()).method("findByData").with(eq(historicoFuncao.getData()), eq(historicoFuncao.getId()), eq(funcao.getId())).will(returnValue(null));
		riscoFuncaoManager.expects(once()).method("removeByHistoricoFuncao").with(eq(historicoFuncao.getId())).will(returnValue(true));
		historicoFuncaoDao.expects(once()).method("update").with(eq(historicoFuncao));
		
		Exception exception = null;
		
		try {
			historicoFuncaoManager.saveHistorico(historicoFuncao, examesChecked, episChecked, riscosChecked, riscosFuncoes);
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	public void testFindByFuncoes() 
	{
		Long[] funcoesCheck = {1L};
		Collection<Funcao> funcoes = Arrays.asList(FuncaoFactory.getEntity(1L));
		historicoFuncaoDao.expects(once()).method("findByFuncoes").with(ANYTHING, ANYTHING).will(returnValue(funcoes));

		assertEquals(funcoes.size(), historicoFuncaoManager.findByFuncoes(new Date(), funcoesCheck).size());
	}
}