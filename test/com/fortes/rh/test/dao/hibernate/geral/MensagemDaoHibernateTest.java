package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.MensagemDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.geral.MensagemFactory;
import com.fortes.rh.util.DateUtil;

public class MensagemDaoHibernateTest extends GenericDaoHibernateTest
{
	private MensagemDao mensagemDao;
	private ColaboradorDao colaboradorDao;
	private AvaliacaoDao avaliacaoDao;

	public Mensagem getEntity()
	{
		Mensagem mensagem =  new Mensagem();

		mensagem.setRemetente("RH");
		mensagem.setData(DateUtil.criarAnoMesDia(2009, 01, 05));
		mensagem.setTexto("Teste do sistema de caixa de mensagem.");

		return mensagem;
	}

	public GenericDao<Mensagem> getGenericDao()
	{
		return mensagemDao;
	}

	public void setMensagemDao(MensagemDao mensagemDao)
	{
		this.mensagemDao = mensagemDao;
	}
	
	public void testRemoveMensagensColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("nome");
		colaboradorDao.save(colaborador);
		
		Mensagem mensagem = MensagemFactory.getEntity();
		mensagem.setColaborador(colaborador);
		mensagem.setTipo(TipoMensagem.INFO_FUNCIONAIS);
		mensagemDao.save(mensagem);
		
		Collection<Mensagem> msgs = mensagemDao.find(new String[]{"colaborador.id"}, new Object[]{colaborador.getId()});
		assertEquals(1, msgs.size());
		
		mensagemDao.removeMensagensColaborador(colaborador.getId(), TipoMensagem.INFO_FUNCIONAIS);
		
		msgs = mensagemDao.find(new String[]{"colaborador.id"}, new Object[]{colaborador.getId()});
		assertEquals(0, msgs.size());
	}

	public void testRemoveByAvaliacaoId()
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao);
		
		Mensagem mensagem = MensagemFactory.getEntity();
		mensagem.setAvaliacao(avaliacao);
		mensagem.setTipo(TipoMensagem.INFO_FUNCIONAIS);
		mensagemDao.save(mensagem);
		
		Collection<Mensagem> msgs = mensagemDao.find(new String[]{"avaliacao.id"}, new Object[]{avaliacao.getId()});
		assertEquals(1, msgs.size());
		
		mensagemDao.removeByAvaliacaoId(avaliacao.getId());
		
		msgs = mensagemDao.find(new String[]{"avaliacao.id"}, new Object[]{avaliacao.getId()});
		assertEquals(0, msgs.size());
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setAvaliacaoDao(AvaliacaoDao avaliacaoDao) {
		this.avaliacaoDao = avaliacaoDao;
	}

}
