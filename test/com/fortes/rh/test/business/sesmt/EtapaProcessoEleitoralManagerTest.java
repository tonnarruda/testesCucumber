package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.business.sesmt.EtapaProcessoEleitoralManagerImpl;
import com.fortes.rh.dao.sesmt.EtapaProcessoEleitoralDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.model.sesmt.EtapaProcessoEleitoral;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EtapaProcessoEleitoralFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;

public class EtapaProcessoEleitoralManagerTest extends MockObjectTestCase
{
	private EtapaProcessoEleitoralManagerImpl etapaProcessoEleitoralManager = new EtapaProcessoEleitoralManagerImpl();
	private Mock etapaProcessoEleitoralDao = null;
	private Mock eleicaoManager;

	protected void setUp() throws Exception
    {
        super.setUp();
        etapaProcessoEleitoralDao = new Mock(EtapaProcessoEleitoralDao.class);
        etapaProcessoEleitoralManager.setDao((EtapaProcessoEleitoralDao) etapaProcessoEleitoralDao.proxy());

        eleicaoManager  = new Mock(EleicaoManager.class);
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);

    }

	@Override
	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
	}

	public void testFindAllSelect()
	{
		etapaProcessoEleitoralDao.expects(once()).method("findAllSelect").with(ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<EtapaProcessoEleitoral>()));
		Long empresaId=1L,eleicaoId=1L;
		assertNotNull(etapaProcessoEleitoralManager.findAllSelect(empresaId, eleicaoId));
	}

	public void testClonarEtapas()
	{
		Collection<EtapaProcessoEleitoral> colecaoEtapas = new ArrayList<EtapaProcessoEleitoral>();
		EtapaProcessoEleitoral etapaProcessoEleitoral = EtapaProcessoEleitoralFactory.getEntity(1L);
		colecaoEtapas.add(etapaProcessoEleitoral);
		Long empresaId=1L,eleicaoId=1L;
		Eleicao eleicao = EleicaoFactory.getEntity(eleicaoId);
		etapaProcessoEleitoralDao.expects(once()).method("findAllSelect").with(eq(empresaId),ANYTHING,ANYTHING).will(returnValue(colecaoEtapas));
		etapaProcessoEleitoralDao.expects(once()).method("save").with(ANYTHING).will(returnValue(ANYTHING));

		etapaProcessoEleitoralManager.clonarEtapas(empresaId, eleicao);
	}

    public void testRemoveByEleicao()
    {
    	Eleicao eleicao = EleicaoFactory.getEntity(1L);
    	etapaProcessoEleitoralDao.expects(once()).method("removeByEleicao").with(eq(eleicao.getId())).isVoid();
    	etapaProcessoEleitoralManager.removeByEleicao(eleicao.getId());
    }

    public void testFindImprimirCalendario()
    {
    	Eleicao eleicao = EleicaoFactory.getEntity(1L);
		eleicaoManager.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(eleicao));
		etapaProcessoEleitoralDao.expects(once()).method("findAllSelect").with(ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<EtapaProcessoEleitoral>()));
		MockSpringUtil.mocks.put("eleicaoManager", eleicaoManager);
		assertNotNull(etapaProcessoEleitoralManager.findImprimirCalendario(eleicao.getId()));
    }
    
    public void testGerarEtapaModelo()
    {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		etapaProcessoEleitoralDao.expects(atLeastOnce()).method("save").with(ANYTHING).will(returnValue(ANYTHING));
    	etapaProcessoEleitoralManager.gerarEtapasModelo(empresa);
    }
    
    public void testUpdatePrazos()
    {
    	Date posse = new Date();
    	EtapaProcessoEleitoral etapaProcessoEleitoral = EtapaProcessoEleitoralFactory.getEntity(11L);
    	etapaProcessoEleitoral.setData(posse);
    	Collection<EtapaProcessoEleitoral> etapaProcessoEleitorals = Arrays.asList(etapaProcessoEleitoral);
    	etapaProcessoEleitoralDao.expects(atLeastOnce()).method("update").with(ANYTHING);
    	etapaProcessoEleitoralManager.updatePrazos(etapaProcessoEleitorals, posse);
    }
}