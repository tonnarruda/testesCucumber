package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.NaturezaLesaoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.NaturezaLesao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.NaturezaLesaoFactory;

public class NaturezaLesaoDaoHibernateTest extends GenericDaoHibernateTest<NaturezaLesao>
{
	private NaturezaLesaoDao naturezaLesaoDao;
	private EmpresaDao empresaDao;

	@Override
	public NaturezaLesao getEntity()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		NaturezaLesao naturezaLesao = NaturezaLesaoFactory.getEntity();
		naturezaLesao.setEmpresa(empresa);
		return naturezaLesao;
	}

	@Override
	public GenericDao<NaturezaLesao> getGenericDao()
	{
		return naturezaLesaoDao;
	}

	public void setNaturezaLesaoDao(NaturezaLesaoDao naturezaLesaoDao)
	{
		this.naturezaLesaoDao = naturezaLesaoDao;
	}
	
	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		NaturezaLesao naturezaLesao = NaturezaLesaoFactory.getEntity();
		naturezaLesao.setDescricao("Teste");
		naturezaLesao.setEmpresa(empresa);
		naturezaLesaoDao.save(naturezaLesao);

		NaturezaLesao naturezaLesao2 = NaturezaLesaoFactory.getEntity();
		naturezaLesao2.setDescricao("Teste 2");
		naturezaLesao2.setEmpresa(empresa);
		naturezaLesaoDao.save(naturezaLesao2);

		NaturezaLesao naturezaLesao3 = NaturezaLesaoFactory.getEntity();
		naturezaLesao3.setDescricao("Teste 3");
		naturezaLesao3.setEmpresa(empresa2);
		naturezaLesaoDao.save(naturezaLesao3);
		
		Collection<NaturezaLesao> naturezaLesaos = naturezaLesaoDao.findAllSelect(empresa.getId());
		
		assertEquals(2, naturezaLesaos.size());
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}
