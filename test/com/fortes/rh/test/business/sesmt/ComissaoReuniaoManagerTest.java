package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.ComissaoManager;
import com.fortes.rh.business.sesmt.ComissaoMembroManager;
import com.fortes.rh.business.sesmt.ComissaoPeriodoManager;
import com.fortes.rh.business.sesmt.ComissaoReuniaoManagerImpl;
import com.fortes.rh.business.sesmt.ComissaoReuniaoPresencaManager;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.dicionario.TipoComissaoReuniao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.model.sesmt.ComissaoReuniao;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoPeriodoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoReuniaoFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class ComissaoReuniaoManagerTest extends MockObjectTestCase
{
	private ComissaoReuniaoManagerImpl comissaoReuniaoManager = new ComissaoReuniaoManagerImpl();
	private Mock comissaoReuniaoDao;
	private Mock comissaoMembroManager;
	private Mock comissaoReuniaoPresencaManager;
	private Mock comissaoPeriodoManager;
	Mock comissaoManager;

	protected void setUp() throws Exception
    {
        super.setUp();
        comissaoReuniaoDao = new Mock(ComissaoReuniaoDao.class);
        comissaoReuniaoManager.setDao((ComissaoReuniaoDao) comissaoReuniaoDao.proxy());

        comissaoMembroManager = new Mock(ComissaoMembroManager.class);
        comissaoReuniaoManager.setComissaoMembroManager((ComissaoMembroManager) comissaoMembroManager.proxy());

        comissaoReuniaoPresencaManager = new Mock(ComissaoReuniaoPresencaManager.class);
        comissaoReuniaoManager.setComissaoReuniaoPresencaManager((ComissaoReuniaoPresencaManager)comissaoReuniaoPresencaManager.proxy());

        comissaoPeriodoManager = mock(ComissaoPeriodoManager.class);
        comissaoReuniaoManager.setComissaoPeriodoManager((ComissaoPeriodoManager) comissaoPeriodoManager.proxy());
        
        comissaoManager = mock(ComissaoManager.class);
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
    }

	public void testFindByIdProjection()
	{
		ComissaoReuniao comissaoReuniao = ComissaoReuniaoFactory.getEntity(1L);
		comissaoReuniaoDao.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(comissaoReuniao));
		assertEquals(comissaoReuniao,comissaoReuniaoManager.findByIdProjection(comissaoReuniao.getId()));
	}

	public void testFindByComissao()
	{
		Long comissaoId = 1L;
		Comissao comissao = ComissaoFactory.getEntity(comissaoId);
		ComissaoReuniao comissaoReuniao = ComissaoReuniaoFactory.getEntity(1L);
		comissaoReuniao.setComissao(comissao);
		Collection<ComissaoReuniao> colecao = new ArrayList<ComissaoReuniao>();
		colecao.add(comissaoReuniao);

		comissaoReuniaoDao.expects(once()).method("findByComissao").with(eq(comissaoId)).will(returnValue(colecao));

		colecao = comissaoReuniaoManager.findByComissao(comissaoId);
		ComissaoReuniao resultado = ((ComissaoReuniao)colecao.toArray()[0]);
		assertEquals(Long.valueOf(1), resultado.getId());
	}

	public void testFindImprimirCalendario()
	{
		Long comissaoId = 1L;
		Comissao comissao = ComissaoFactory.getEntity(comissaoId);
		ComissaoReuniao comissaoReuniao = ComissaoReuniaoFactory.getEntity(1L);
		comissaoReuniao.setTipo(TipoComissaoReuniao.ORDINARIA);
		comissaoReuniao.setComissao(comissao);
		Collection<ComissaoReuniao> colecao = new ArrayList<ComissaoReuniao>();
		colecao.add(comissaoReuniao);
		comissaoReuniaoDao.expects(once()).method("findByComissao").with(eq(comissaoId)).will(returnValue(colecao));

		Exception exception = null;

		try
		{
			colecao = comissaoReuniaoManager.findImprimirCalendario(comissaoId);
		}
		catch (ColecaoVaziaException e)
		{
			exception = e;
		}
		ComissaoReuniao resultado = ((ComissaoReuniao)colecao.toArray()[0]);
		assertEquals(Long.valueOf(1), resultado.getId());
		assertNull(exception);

		// exceção
		exception = null;
		comissaoReuniaoDao.expects(once()).method("findByComissao").with(eq(comissaoId)).will(returnValue(new ArrayList<ComissaoReuniao>()));
		try
		{
			colecao = comissaoReuniaoManager.findImprimirCalendario(comissaoId);
		}
		catch (ColecaoVaziaException e)
		{
			exception = e;
		}
		assertNotNull(exception);
	}

	public void testFindImprimirAta()
	{
		Date dataReuniao = DateUtil.montaDataByString("07/05/2010");
		Long comissaoId = 1L;
		Comissao comissao = ComissaoFactory.getEntity(comissaoId);
		ComissaoReuniao comissaoReuniao = ComissaoReuniaoFactory.getEntity(1L);
		comissaoReuniao.setData(dataReuniao);
		comissaoReuniao.setHorario("16:00");
		comissaoReuniao.setTipo(TipoComissaoReuniao.ORDINARIA);
		comissaoReuniao.setAta("Pontos discutidos na Reunião");
		comissaoReuniao.setLocalizacao("Sala de Reuniões");
		comissaoReuniao.setComissao(comissao);
		
		ComissaoPeriodo periodo1Fora = ComissaoPeriodoFactory.getEntity(1L);
		periodo1Fora.setaPartirDe(DateUtil.montaDataByString("01/01/2010"));
		periodo1Fora.setFim(DateUtil.montaDataByString("30/04/2010"));
		
		ComissaoPeriodo periodo2DentroDaDataDaReuniao = ComissaoPeriodoFactory.getEntity(2L);
		periodo2DentroDaDataDaReuniao.setaPartirDe(DateUtil.montaDataByString("01/05/2010"));
		periodo2DentroDaDataDaReuniao.setFim(DateUtil.montaDataByString("30/12/2010"));
		
		Collection<ComissaoPeriodo> periodos = Arrays.asList(periodo1Fora, periodo2DentroDaDataDaReuniao);
		Collection<ComissaoReuniaoPresenca> comissaoReuniaoPresencas = new ArrayList<ComissaoReuniaoPresenca>();
		
		Collection<ComissaoMembro> comissaoMembros = new ArrayList<ComissaoMembro>();

		comissaoReuniaoDao.expects(once()).method("findByIdProjection").with(eq(comissaoId)).will(returnValue(comissaoReuniao));
		
		comissaoPeriodoManager.expects(once()).method("findByComissao").with(eq(comissaoId)).will(returnValue(periodos));
		
		comissaoReuniaoPresencaManager.expects(once()).method("findByReuniao").with(eq(comissaoReuniao.getId())).will(returnValue(comissaoReuniaoPresencas));
		
		comissaoMembroManager.expects(once()).method("findDistinctByComissaoPeriodo").with(eq(periodo2DentroDaDataDaReuniao.getId()), eq(comissaoReuniao.getData())).will(returnValue(comissaoMembros));

		comissaoMembros = comissaoReuniaoManager.findImprimirAta(comissaoReuniao, comissaoId);
	}

	public void testFindRelatorioPresenca()
	{
//		"1";"ALEXSANDRO DE SOUZA";"t";"162";"2009-08-25";"asdfg";"12:00"
//		"2";"PAULO ROBERTO DE ALENCAR";"t";"163";"2009-08-28";"Reunião sobre as ações do mês de setembro";"14:00"
//		"1";"ALEXSANDRO DE SOUZA";"f";"163";"2009-08-28";"Reunião sobre as ações do mês de setembro";"14:00"

		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		colaborador1.setNome("ALEXSANDRO DE SOUZA");
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		colaborador2.setNome("PAULO ROBERTO DE ALENCAR");

		ComissaoReuniao comissaoReuniao1 = ComissaoReuniaoFactory.getEntity(162L);
		ComissaoReuniao comissaoReuniao2 = ComissaoReuniaoFactory.getEntity(163L);

		ComissaoReuniaoPresenca presencaDoColaborador1 = new ComissaoReuniaoPresenca();
		presencaDoColaborador1.setId(1L);
		presencaDoColaborador1.setComissaoReuniao(comissaoReuniao1);
		presencaDoColaborador1.setColaborador(colaborador1);

		ComissaoReuniaoPresenca outraPresencaDoColaborador1 = new ComissaoReuniaoPresenca();
		outraPresencaDoColaborador1.setId(3L);
		outraPresencaDoColaborador1.setComissaoReuniao(comissaoReuniao2);
		outraPresencaDoColaborador1.setColaborador(colaborador1);

		ComissaoReuniaoPresenca presencaDoColaborador2 = new ComissaoReuniaoPresenca();
		presencaDoColaborador2.setId(2L);
		presencaDoColaborador2.setComissaoReuniao(comissaoReuniao2);
		presencaDoColaborador2.setColaborador(colaborador2);

		Collection<ComissaoReuniaoPresenca> presencas = new ArrayList<ComissaoReuniaoPresenca>();
		presencas.add(presencaDoColaborador1);
		presencas.add(presencaDoColaborador2);
		presencas.add(outraPresencaDoColaborador1);

		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador1);
		colaboradores.add(colaborador2);
		
		comissaoReuniaoPresencaManager.expects(once()).method("findPresencasByComissao").with(eq(1L)).will(returnValue(presencas));
		comissaoMembroManager.expects(once()).method("findColaboradoresNaComissao").with(ANYTHING).will(returnValue(new ArrayList<Colaborador>()));

		Collection<ComissaoReuniaoPresenca> resultado = null;
		Exception exception = null;

		try {
			resultado = comissaoReuniaoManager.findRelatorioPresenca(1L);
		} catch (FortesException e) {
			exception = e;
		}
		
		assertNull(exception);
		assertEquals(3,resultado.size());
	}

	public void testSaveOrUpdate() throws Exception
	{
		ComissaoReuniao comissaoReuniao = ComissaoReuniaoFactory.getEntity();
		comissaoReuniao.setComissao(ComissaoFactory.getEntity(1L));

		comissaoReuniaoDao.expects(once()).method("save");
		comissaoReuniaoPresencaManager.expects(once()).method("saveOrUpdateByReuniao");

		assertEquals(comissaoReuniao, comissaoReuniaoManager.saveOrUpdate(comissaoReuniao, null, null, null));

		//update
		comissaoReuniao.setId(1L);

		comissaoReuniaoDao.expects(once()).method("update");
		comissaoReuniaoPresencaManager.expects(once()).method("saveOrUpdateByReuniao");

		assertEquals(comissaoReuniao, comissaoReuniaoManager.saveOrUpdate(comissaoReuniao, null, null, null));
	}

	public void testSaveOrUpdateException()
	{
		ComissaoReuniao comissaoReuniao = ComissaoReuniaoFactory.getEntity(1L);
		comissaoReuniao.setComissao(ComissaoFactory.getEntity(1L));

		comissaoReuniaoDao.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(comissaoReuniao.getId(),""))));

		Exception exception=null;
		try
		{
			comissaoReuniaoManager.saveOrUpdate(comissaoReuniao, null, null, null);
		} catch(Exception e)
		{
			exception = e;
		}
		assertNotNull(exception);
	}

	public void testRemove()
	{
		comissaoReuniaoPresencaManager.expects(once()).method("removeByReuniao").with(eq(1L));
		comissaoReuniaoDao.expects(once()).method("remove").with(eq(1L));

		comissaoReuniaoManager.remove(1L);
	}

	public void testRemoveArray()
	{
		Long[] ids = new Long[]{1L,2L};
		comissaoReuniaoPresencaManager.expects(atLeastOnce()).method("removeByReuniao");
		comissaoReuniaoDao.expects(once()).method("remove").with(eq(ids));

		comissaoReuniaoManager.remove(ids);
	}

	public void testRemoveByComissao()
	{
		Collection<ComissaoReuniao> colecao = new ArrayList<ComissaoReuniao>();
		colecao.add(ComissaoReuniaoFactory.getEntity(1L));
		comissaoReuniaoDao.expects(once()).method("findByComissao").with(eq(1L)).will(returnValue(colecao));
		Long[] ids = new Long[]{1L};
		comissaoReuniaoPresencaManager.expects(atLeastOnce()).method("removeByReuniao");
		comissaoReuniaoDao.expects(once()).method("remove").with(eq(ids));

		comissaoReuniaoManager.removeByComissao(1L);
	}
}