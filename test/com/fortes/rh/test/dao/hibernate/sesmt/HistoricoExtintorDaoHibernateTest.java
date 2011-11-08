package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.ExtintorDao;
import com.fortes.rh.dao.sesmt.HistoricoExtintorDao;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.HistoricoExtintor;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorFactory;

public class HistoricoExtintorDaoHibernateTest extends GenericDaoHibernateTest<HistoricoExtintor>
{
	private HistoricoExtintorDao historicoExtintorDao;
	private ExtintorDao extintorDao;
	private EstabelecimentoDao estabelecimentoDao;

	public HistoricoExtintor getEntity()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Extintor extintor = ExtintorFactory.getEntity();
		extintorDao.save(extintor);
		
		HistoricoExtintor historicoExtintor = new HistoricoExtintor();

		historicoExtintor.setId(1L);
		historicoExtintor.setExtintor(extintor);
		historicoExtintor.setLocalizacao("carro 35001");
		historicoExtintor.setEstabelecimento(estabelecimento);
		historicoExtintor.setData(new Date());

		return historicoExtintor;
	}
	
	public void testFindByExtintor()
	{
		Date hoje = new Date();
		
		Extintor extintor1 = new Extintor();
		extintorDao.save(extintor1);

		Extintor extintor2 = new Extintor();
		extintorDao.save(extintor2);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		HistoricoExtintor historicoExtintor1 = new HistoricoExtintor();
		historicoExtintor1.setExtintor(extintor1);
		historicoExtintor1.setLocalizacao("carro 999");
		historicoExtintor1.setEstabelecimento(estabelecimento);
		historicoExtintor1.setData(hoje);
		historicoExtintorDao.save(historicoExtintor1);
		
		HistoricoExtintor historicoExtintor2 = new HistoricoExtintor();
		historicoExtintor2.setExtintor(extintor2);
		historicoExtintor2.setLocalizacao("carro 1000");
		historicoExtintor2.setEstabelecimento(estabelecimento);
		historicoExtintor2.setData(hoje);
		historicoExtintorDao.save(historicoExtintor2);
		
		assertEquals(1, historicoExtintorDao.findByExtintor(extintor1.getId()).size());
	}

	public void setHistoricoExtintorDao(HistoricoExtintorDao historicoExtintorDao) {
		this.historicoExtintorDao = historicoExtintorDao;
	}

	public void setExtintorDao(ExtintorDao extintorDao) {
		this.extintorDao = extintorDao;
	}
	
	public GenericDao<HistoricoExtintor> getGenericDao() {
		return historicoExtintorDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}
}