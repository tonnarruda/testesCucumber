package com.fortes.rh.test.business.desenvolvimento;

import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManagerImpl;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorAvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorCertificacaoFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class ColaboradorAvaliacaoPraticaManagerTest extends MockObjectTestCase
{
	private ColaboradorAvaliacaoPraticaManagerImpl colaboradorAvaliacaoPraticaManager = new ColaboradorAvaliacaoPraticaManagerImpl();
	private Mock colaboradorAvaliacaoPraticaDao;
	private Mock colaboradorCertificacaoManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        colaboradorAvaliacaoPraticaDao = new Mock(ColaboradorAvaliacaoPraticaDao.class);
        colaboradorAvaliacaoPraticaManager.setDao((ColaboradorAvaliacaoPraticaDao) colaboradorAvaliacaoPraticaDao.proxy());
        
        colaboradorCertificacaoManager =  new Mock(ColaboradorCertificacaoManager.class);
		MockSpringUtil.mocks.put("colaboradorCertificacaoManager", colaboradorCertificacaoManager);

		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
    }

	public void testFindAllSelect()
	{
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = ColaboradorAvaliacaoPraticaFactory.getCollection(1L);

		colaboradorAvaliacaoPraticaDao.expects(once()).method("findAll").will(returnValue(colaboradorAvaliacaoPraticas));
		assertEquals(colaboradorAvaliacaoPraticas, colaboradorAvaliacaoPraticaManager.findAll());
	}
	
	public void testFindByColaboradorIdAndCertificacaoId() {
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity();
		colaboradorCertificacao.setColaborador(colaborador);
		colaboradorCertificacao.setCertificacao(certificacao);
		colaboradorCertificacao.setData(DateUtil.criarDataMesAno(1, 12, 2015));

		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colaboradorAvaliacaoPratica.setColaboradorCertificacao(colaboradorCertificacao);
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		colaboradorAvaliacaoPratica.setCertificacao(certificacao);
		colaboradorAvaliacaoPratica.setColaborador(colaborador);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPraticas = Arrays.asList(colaboradorAvaliacaoPratica);
		
		colaboradorAvaliacaoPraticaDao.expects(once()).method("findByColaboradorIdAndCertificacaoId").with(eq(colaborador.getId()), eq(certificacao.getId()), eq(colaboradorCertificacao.getId())).will(returnValue(colaboradorAvaliacoesPraticas));
		
		assertEquals(1, colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId(), colaboradorCertificacao.getId()).size());
	}
	
	public void testFindByColaboradorIdAndCertificacaoIdRetonaSomenteColaboradoresCertificados() {
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity(1L);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colaboradorAvaliacaoPratica.setCertificacao(certificacao);
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		colaboradorAvaliacaoPratica.setColaborador(colaborador);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPraticas = Arrays.asList(colaboradorAvaliacaoPratica);
		
		colaboradorAvaliacaoPraticaDao.expects(once()).method("findColaboradorAvaliacaoPraticaQueNaoEstaCertificado").with(eq(colaborador.getId()), eq(certificacao.getId())).will(returnValue(colaboradorAvaliacoesPraticas));
		
		assertEquals(1, colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId()).size());
	}
	
	public void testRemoveColaboradorAvaliacaoPraticaByColaboradorCertificacaoId(){
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		
		colaboradorAvaliacaoPraticaDao.expects(once()).method("removeColaboradorAvaliacaoPraticaByColaboradorCertificacaoId").with(eq(colaboradorCertificacao.getId())).isVoid();

		Exception exception = null;
		try {
			colaboradorAvaliacaoPraticaManager.removeByColaboradorCertificacaoId(colaboradorCertificacao.getId());
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
}