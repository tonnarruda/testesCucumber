package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.MensagemDao;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.util.DateUtil;

public class MensagemDaoHibernateTest extends GenericDaoHibernateTest
{
	private MensagemDao mensagemDao;

	public Mensagem getEntity()
	{
		Mensagem mensagem =  new Mensagem();

		mensagem.setRemetente("Fortes RH");
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

}
