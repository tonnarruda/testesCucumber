package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.CandidatoEleicaoManager;
import com.fortes.rh.business.sesmt.ComissaoMembroManager;
import com.fortes.rh.business.sesmt.ComissaoPeriodoManagerImpl;
import com.fortes.rh.business.sesmt.ComissaoReuniaoPresencaManager;
import com.fortes.rh.dao.sesmt.ComissaoPeriodoDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.CandidatoEleicaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoMembroFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoPeriodoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;
import com.fortes.rh.util.DateUtil;

public class ComissaoPeriodoManagerTest extends MockObjectTestCase
{
	private ComissaoPeriodoManagerImpl comissaoPeriodoManager = new ComissaoPeriodoManagerImpl();
	private Mock comissaoPeriodoDao;
	private Mock candidatoEleicaoManager;
	private Mock comissaoMembroManager;
	private Mock comissaoReuniaoPresencaManager;

	protected void setUp() throws Exception
    {
        super.setUp();
        comissaoPeriodoDao = new Mock(ComissaoPeriodoDao.class);
        comissaoPeriodoManager.setDao((ComissaoPeriodoDao) comissaoPeriodoDao.proxy());

        candidatoEleicaoManager = new Mock(CandidatoEleicaoManager.class);
        comissaoPeriodoManager.setCandidatoEleicaoManager((CandidatoEleicaoManager)candidatoEleicaoManager.proxy());

        comissaoMembroManager = new Mock(ComissaoMembroManager.class);
        comissaoPeriodoManager.setComissaoMembroManager((ComissaoMembroManager)comissaoMembroManager.proxy());
        
        comissaoReuniaoPresencaManager = new Mock(ComissaoReuniaoPresencaManager.class);
        comissaoPeriodoManager.setComissaoReuniaoPresencaManager((ComissaoReuniaoPresencaManager)comissaoReuniaoPresencaManager.proxy());
    }

	public void testSave()
	{
		Collection<CandidatoEleicao> candidatoEleicaos = new ArrayList<CandidatoEleicao>();
		CandidatoEleicao candidatoEleito = CandidatoEleicaoFactory.getEntity(1L);
		candidatoEleito.setEleito(true);
		candidatoEleicaos.add(candidatoEleito);
		candidatoEleicaos.add(CandidatoEleicaoFactory.getEntity(2L));

		Eleicao eleicao = EleicaoFactory.getEntity(2L);
		Comissao comissao = ComissaoFactory.getEntity(1L);
		comissao.setEleicao(eleicao);

		ComissaoPeriodo comissaoPeriodo = new ComissaoPeriodo(new Date());
		comissaoPeriodo.setComissao(comissao);

		ComissaoMembro comissaoMembro = new ComissaoMembro(new Colaborador(), comissaoPeriodo);

		comissaoPeriodoDao.expects(once()).method("save").with(ANYTHING).will(returnValue(comissaoPeriodo));
		candidatoEleicaoManager.expects(once()).method("findByEleicao").with(eq(comissao.getEleicao().getId())).will(returnValue(candidatoEleicaos));
		comissaoMembroManager.expects(atLeastOnce()).method("save").with(ANYTHING).will(returnValue(comissaoMembro));

		comissaoPeriodoManager.save(comissao.getId(), eleicao.getId(), new Date());
	}

	public void testFindByComissao()
	{
		Comissao comissao = ComissaoFactory.getEntity(1L);
		comissao.setDataIni(new Date());
		comissao.setDataFim(new Date());
		
		Colaborador joao = ColaboradorFactory.getEntity(1L);
		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity(1L);
		comissaoPeriodo.setComissao(comissao);
		ComissaoPeriodo comissaoPeriodo2 = ComissaoPeriodoFactory.getEntity(2L);
		comissaoPeriodo2.setComissao(comissao);
		Collection<ComissaoPeriodo> comissaoPeriodos = new ArrayList<ComissaoPeriodo>();
		comissaoPeriodos.add(comissaoPeriodo);
		comissaoPeriodos.add(comissaoPeriodo2);
		Collection<ComissaoMembro> comissaoMembros = new ArrayList<ComissaoMembro>();
		ComissaoMembro comissaoMembro = ComissaoMembroFactory.getEntity(1L);
		comissaoMembro.setColaborador(joao);
		comissaoMembro.setComissaoPeriodo(comissaoPeriodo);
		comissaoMembros.add(comissaoMembro);

		comissaoPeriodoDao.expects(once()).method("findByComissao").with(ANYTHING).will(returnValue(comissaoPeriodos));
		
		comissaoPeriodoDao.expects(once()).method("findByIdProjection").with(eq(comissaoPeriodo.getId())).will(returnValue(comissaoPeriodo));
		comissaoPeriodoDao.expects(once()).method("findByIdProjection").with(eq(comissaoPeriodo2.getId())).will(returnValue(comissaoPeriodo2));
		comissaoPeriodoDao.expects(atLeastOnce()).method("findProximo");
		comissaoReuniaoPresencaManager.expects(atLeastOnce()).method("existeReuniaoPresenca").withAnyArguments().will(returnValue(false));
		
		comissaoMembroManager.expects(once()).method("findByComissaoPeriodo").with(ANYTHING).will(returnValue(comissaoMembros));

		Collection<ComissaoPeriodo> retorno = comissaoPeriodoManager.findByComissao(comissao.getId());
		assertEquals(2, retorno.size());
	}

	public void testFindByIdProjection()
	{
		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity(1L);
		comissaoPeriodoDao.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(comissaoPeriodo));
		assertEquals(comissaoPeriodo, comissaoPeriodoManager.findByIdProjection(comissaoPeriodo.getId()));
	}
	
	public void testClonar() throws Exception
	{
		Comissao comissao = ComissaoFactory.getEntity(1L);
		
		Long comissaoPeriodoId=10L;
		ComissaoPeriodo comissaoPeriodo=ComissaoPeriodoFactory.getEntity(comissaoPeriodoId);
		comissaoPeriodo.setComissao(comissao);
		comissaoPeriodo.setaPartirDe(new Date());
		Collection<ComissaoMembro> comissaoMembros = new ArrayList<ComissaoMembro>();
		ComissaoMembro comissaoMembro = ComissaoMembroFactory.getEntity(12L);
		comissaoMembros.add(comissaoMembro);
		
		comissaoPeriodoDao.expects(once()).method("findByIdProjection").with(eq(comissaoPeriodoId)).will(returnValue(comissaoPeriodo));
		comissaoMembroManager.expects(once()).method("findByComissaoPeriodo").will(returnValue(comissaoMembros ));
		comissaoPeriodoDao.expects(once()).method("save");
		comissaoPeriodoDao.expects(once()).method("maxDataComissaoPeriodo").with(eq(comissao.getId())).will(returnValue(new Date()));
		comissaoMembroManager.expects(once()).method("save").will(returnValue(comissaoMembro));
		
		comissaoPeriodoManager.clonar(comissaoPeriodoId, null);
	}
	
	public void testUpdate() throws Exception
	{
		ComissaoPeriodo comissaoPeriodo=ComissaoPeriodoFactory.getEntity(10L);
		
		String[] comissaoMembroIds = {"1"};
		String[] funcaoComissaos = {"2"};
		String[] tipoComissaos = null;
		
//		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		comissaoPeriodoDao.expects(once()).method("update");
		comissaoMembroManager.expects(once()).method("updateFuncaoETipo").with(eq(comissaoMembroIds),eq(funcaoComissaos),eq(tipoComissaos)).isVoid();
//		transactionManager.expects(once()).method("commit").with(ANYTHING);
		
		comissaoPeriodoManager.update(comissaoPeriodo, comissaoMembroIds, funcaoComissaos, tipoComissaos);
	}
	
	public void testUpdateException() 
	{
		ComissaoPeriodo comissaoPeriodo=ComissaoPeriodoFactory.getEntity(13L);
		
//		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		comissaoPeriodoDao.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));;
//		transactionManager.expects(once()).method("rollback").with(ANYTHING);
		
		Exception ex = null;
		try {
			comissaoPeriodoManager.update(comissaoPeriodo, null, null, null);
		} catch (Exception e) {
			ex = e;
		}
		
		assertNotNull(ex);
	}
	
	public void testRemoveComArray()
	{
		Long[] ids = new Long[]{1L,2L};
		
//		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		comissaoMembroManager.expects(once()).method("removeByComissaoPeriodo").with(eq(ids)).isVoid();
		comissaoPeriodoDao.expects(once()).method("remove").with(eq(ids)).isVoid();
//		transactionManager.expects(once()).method("commit").with(ANYTHING);
		
		comissaoPeriodoManager.remove(ids);
	}
	
	public void testRemoveException()
	{
		Long[] ids = new Long[]{1L,2L};
		
//		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		comissaoMembroManager.expects(once()).method("removeByComissaoPeriodo").with(eq(ids)).isVoid();
		comissaoPeriodoDao.expects(once()).method("remove").with(eq(ids)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));;
//		transactionManager.expects(once()).method("rollback").with(ANYTHING);
		
		Exception ex = null;
		try {
			comissaoPeriodoManager.remove(ids);
		} catch (Exception e) {
			ex = e;
		}
		
		assertNotNull(ex);
	}
	
	public void testRemove()
	{
		ComissaoPeriodo comissaoPeriodo=ComissaoPeriodoFactory.getEntity(10L);
		comissaoPeriodo.setComissao(ComissaoFactory.getEntity(1000L));
		
		Long[] ids = new Long[]{10L};
		
		comissaoPeriodoDao.expects(once()).method("getCount").will(returnValue(3));
		
//		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		comissaoMembroManager.expects(once()).method("removeByComissaoPeriodo").with(eq(ids)).isVoid();
		comissaoPeriodoDao.expects(once()).method("remove").with(eq(ids)).isVoid();
//		transactionManager.expects(once()).method("commit").with(ANYTHING);
		
		comissaoPeriodoManager.remove(comissaoPeriodo);
	}
	
	public void testRemoveExceptionUltimoPeriodoDaComissao()
	{
		ComissaoPeriodo comissaoPeriodo=ComissaoPeriodoFactory.getEntity(10L);
		comissaoPeriodo.setComissao(ComissaoFactory.getEntity(1000L));
		
		comissaoPeriodoDao.expects(once()).method("getCount").will(returnValue(1));
		
		Exception ex = null;
		try{
			comissaoPeriodoManager.remove(comissaoPeriodo);
		}
		catch(Exception e)
		{
			ex = e;	
		}
		assertNotNull(ex);
	}
	
	public void testRemoveByComissao()
	{
		Long comissaoId=23255L;
		Collection<ComissaoPeriodo> comissaoPeriodos = new ArrayList<ComissaoPeriodo>();
		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity(544L);
		comissaoPeriodos.add(comissaoPeriodo);
		
		Collection<ComissaoMembro> comissaoMembros = new ArrayList<ComissaoMembro>();
		ComissaoMembro comissaoMembro = ComissaoMembroFactory.getEntity(1L);
		comissaoMembro.setComissaoPeriodo(comissaoPeriodo);
		comissaoMembros.add(comissaoMembro);
		
		Long[] ids = new Long[]{comissaoPeriodo.getId()};
		
		comissaoPeriodoDao.expects(once()).method("findByComissao").with(ANYTHING).will(returnValue(comissaoPeriodos));
//		comissaoMembroManager.expects(once()).method("findByComissaoPeriodo").with(ANYTHING).will(returnValue(comissaoMembros));
		
//		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		comissaoMembroManager.expects(once()).method("removeByComissaoPeriodo").with(eq(ids)).isVoid();
		comissaoPeriodoDao.expects(once()).method("remove").with(eq(ids)).isVoid();
//		transactionManager.expects(once()).method("commit").with(ANYTHING);
		
		comissaoPeriodoManager.removeByComissao(comissaoId);
	}
	
	public void testGetDataFim()
	{
		Date dataPeriodo1 = DateUtil.criarDataMesAno(10, 12, 2009);
		Date dataPeriodo2 = DateUtil.criarDataMesAno(5, 4, 2010);
		
		//saída a ser calculada pelo método
		Date fimPeriodo1 = DateUtil.criarDataMesAno(4, 4, 2010);
		
		ComissaoPeriodo comissaoPeriodo=ComissaoPeriodoFactory.getEntity(10L);
		comissaoPeriodo.setaPartirDe(dataPeriodo1);
		
		Comissao comissao = ComissaoFactory.getEntity(2L);
		comissaoPeriodo.setComissao(comissao);
		
		ComissaoPeriodo proximoComissaoPeriodo=ComissaoPeriodoFactory.getEntity(11L);
		proximoComissaoPeriodo.setaPartirDe(dataPeriodo2);
		
		comissaoPeriodoDao.expects(once()).method("findByIdProjection").with(eq(comissaoPeriodo.getId())).will(returnValue(comissaoPeriodo));
		comissaoPeriodoDao.expects(once()).method("findProximo").with(eq(comissaoPeriodo)).will(returnValue(proximoComissaoPeriodo));
		
		assertEquals("deve retornar um dia antes do próximo período",
				fimPeriodo1, comissaoPeriodoManager.getDataFim(comissaoPeriodo));
	}
	
	public void testGetDataFimSemProximoPeriodo()
	{
		Date dataPeriodo1 = DateUtil.criarDataMesAno(10, 12, 2009);
		ComissaoPeriodo comissaoPeriodo=ComissaoPeriodoFactory.getEntity(10L);
		comissaoPeriodo.setaPartirDe(dataPeriodo1);
		
		Comissao comissao = ComissaoFactory.getEntity(2L);
		comissao.setDataIni(DateUtil.criarDataMesAno(10, 12, 2009));
		comissao.setDataFim(DateUtil.criarDataMesAno(10, 12, 2010));
		
		comissaoPeriodo.setComissao(comissao);
		
		comissaoPeriodoDao.expects(once()).method("findByIdProjection").with(eq(comissaoPeriodo.getId())).will(returnValue(comissaoPeriodo));
		comissaoPeriodoDao.expects(once()).method("findProximo").with(eq(comissaoPeriodo)).will(returnValue(null));
		
		assertEquals("deve retornar a data final da comissão",
				comissao.getDataFim(), comissaoPeriodoManager.getDataFim(comissaoPeriodo));
	}
	
	public void testValidaDataComissaoPeriodoExceptionExisteComissaoNoMesmoPeriodo()
	{
		Long comissaoPeriodoId = 100L;
		
		Date dataPeriodo = DateUtil.criarDataMesAno(10, 12, 2009); 
		
		Comissao comissao = ComissaoFactory.getEntity(2L);
		comissao.setDataIni(DateUtil.criarDataMesAno(10, 12, 2009));
		comissao.setDataFim(DateUtil.criarDataMesAno(10, 12, 2010));
		
		ComissaoPeriodo comissaoPeriodo=ComissaoPeriodoFactory.getEntity(comissaoPeriodoId);
		comissaoPeriodo.setaPartirDe(dataPeriodo);
		comissaoPeriodo.setComissao(comissao);
		
		ComissaoPeriodo outroComissaoPeriodo=ComissaoPeriodoFactory.getEntity(99L);
		outroComissaoPeriodo.setaPartirDe(dataPeriodo);
		
		Collection<ComissaoPeriodo> comissaoPeriodos = Arrays.asList(comissaoPeriodo, outroComissaoPeriodo);
		
		comissaoPeriodoDao.expects(once()).method("findByIdProjection").with(eq(comissaoPeriodo.getId())).will(returnValue(comissaoPeriodo));
		comissaoPeriodoDao.expects(once()).method("verificaComissaoNaMesmaData").with(eq(comissaoPeriodo)).will(returnValue(true));
		
		assertFalse("deve criticar se existe outro período na mesma data",	comissaoPeriodoManager.validaDataComissaoPeriodo(dataPeriodo, comissaoPeriodoId));
	}
	
	public void testValidaDataComissaoPeriodoForaDoPeriodoDaComissao()
	{
		Long comissaoPeriodoId = 100L;
		
		Date dataPeriodo = DateUtil.criarDataMesAno(10, 12, 2009); 
		
		Comissao comissaoFora = ComissaoFactory.getEntity(2L);
		comissaoFora.setDataIni(DateUtil.criarDataMesAno(10, 12, 2010));
		comissaoFora.setDataFim(DateUtil.criarDataMesAno(10, 12, 2011));
		
		ComissaoPeriodo comissaoPeriodo=ComissaoPeriodoFactory.getEntity(comissaoPeriodoId);
		comissaoPeriodo.setaPartirDe(dataPeriodo);
		comissaoPeriodo.setComissao(comissaoFora);
		
		comissaoPeriodoDao.expects(once()).method("findByIdProjection").with(eq(comissaoPeriodo.getId())).will(returnValue(comissaoPeriodo));
		
		assertFalse("deve criticar uma data fora do período da CIPA",
				comissaoPeriodoManager.validaDataComissaoPeriodo(dataPeriodo, comissaoPeriodoId));
	}
}