package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.EngenheiroResponsavelDao;
import com.fortes.rh.model.dicionario.TipoEstabelecimentoResponsavel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.EngenheiroResponsavelFactory;
import com.fortes.rh.util.DateUtil;

public class EngenheiroResponsavelDaoHibernateTest extends GenericDaoHibernateTest<EngenheiroResponsavel>
{
	EngenheiroResponsavelDao engenheiroResponsavelDao = null;
	EstabelecimentoDao estabelecimentoDao;
	EmpresaDao empresaDao;

	public EngenheiroResponsavel getEntity()
	{
		return EngenheiroResponsavelFactory.getEntity();
	}

	public GenericDao<EngenheiroResponsavel> getGenericDao()
	{
		return engenheiroResponsavelDao;
	}

	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		EngenheiroResponsavel engenheiroResponsavel = new EngenheiroResponsavel();
		engenheiroResponsavel.setEmpresa(empresa);
		engenheiroResponsavel = engenheiroResponsavelDao.save(engenheiroResponsavel);

		EngenheiroResponsavel engenheiroResponsavelRetorno = engenheiroResponsavelDao.findByIdProjection(engenheiroResponsavel.getId());

		assertEquals(engenheiroResponsavel.getId(), engenheiroResponsavelRetorno.getId());
	}
	
	public void testFindResponsaveisPorEstabelecimento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);

		Collection<Estabelecimento> estabelecimentos = Arrays.asList(estabelecimento1, estabelecimento2);
		
		EngenheiroResponsavel engenheiroResponsavel1 = EngenheiroResponsavelFactory.getEntity(empresa, DateUtil.incrementaDias(new Date(), 0), TipoEstabelecimentoResponsavel.TODOS, null);
		engenheiroResponsavel1 = engenheiroResponsavelDao.save(engenheiroResponsavel1);
		
		EngenheiroResponsavel engenheiroResponsavel2 = EngenheiroResponsavelFactory.getEntity(empresa, DateUtil.incrementaDias(new Date(), -1), TipoEstabelecimentoResponsavel.ALGUNS, estabelecimentos);
		engenheiroResponsavel2 = engenheiroResponsavelDao.save(engenheiroResponsavel2);
		
		Collection<EngenheiroResponsavel> result = engenheiroResponsavelDao.findResponsaveisPorEstabelecimento(empresa.getId(), estabelecimento1.getId());
		
		assertEquals(2, result.size());
		assertEquals(engenheiroResponsavel2.getId(), ((EngenheiroResponsavel)result.toArray()[0]).getId());
		assertEquals(engenheiroResponsavel1.getId(), ((EngenheiroResponsavel)result.toArray()[1]).getId());
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
	
	public void setEngenheiroResponsavelDao(EngenheiroResponsavelDao engenheiroResponsavelDao) {
		this.engenheiroResponsavelDao = engenheiroResponsavelDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}
}