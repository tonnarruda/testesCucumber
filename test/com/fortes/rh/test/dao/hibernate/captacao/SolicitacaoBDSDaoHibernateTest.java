package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.SolicitacaoBDSDao;
import com.fortes.rh.model.captacao.SolicitacaoBDS;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;

public class SolicitacaoBDSDaoHibernateTest extends GenericDaoHibernateTest<SolicitacaoBDS>
{
	private SolicitacaoBDSDao solicitacaoBDSDao;

	public SolicitacaoBDS getEntity()
	{
		SolicitacaoBDS solicitacaoBDS = new SolicitacaoBDS();
		
		solicitacaoBDS.setAreaOrganizacional(null);
		solicitacaoBDS.setCargo(null);
		solicitacaoBDS.setData(new Date());
		solicitacaoBDS.setEmpresasBDSs(null);
		solicitacaoBDS.setEscolaridade('m');
		solicitacaoBDS.setExperiencia("exp");
		solicitacaoBDS.setId(null);
		solicitacaoBDS.setIdadeMaxima(50);
		solicitacaoBDS.setIdadeMinima(15);
		solicitacaoBDS.setObservacao("obs");
		solicitacaoBDS.setSexo('m');
		solicitacaoBDS.setTipo('t');
		return solicitacaoBDS;
	}

	public GenericDao<SolicitacaoBDS> getGenericDao()
	{
		return solicitacaoBDSDao;
	}

	public void setSolicitacaoBDSDao(SolicitacaoBDSDao solicitacaoBDSDao)
	{
		this.solicitacaoBDSDao = solicitacaoBDSDao;
	}

}
