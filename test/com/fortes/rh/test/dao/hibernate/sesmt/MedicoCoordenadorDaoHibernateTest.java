package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.MedicoCoordenadorDao;
import com.fortes.rh.model.dicionario.TipoEstabelecimentoResponsavel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.MedicoCoordenadorFactory;
import com.fortes.rh.util.DateUtil;

public class MedicoCoordenadorDaoHibernateTest extends GenericDaoHibernateTest<MedicoCoordenador>
{
	MedicoCoordenadorDao medicoCoordenadorDao = null;
	private EmpresaDao empresaDao;
	private EstabelecimentoDao estabelecimentoDao;

	@Override
	public MedicoCoordenador getEntity()
	{
		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
		medicoCoordenador.setNome("medicoCoordenador");
		return medicoCoordenador;
	}

	@Override
	public GenericDao<MedicoCoordenador> getGenericDao()
	{
		return medicoCoordenadorDao;
	}

	public void setMedicoCoordenadorDao(MedicoCoordenadorDao medicoCoordenadorDao)
	{
		this.medicoCoordenadorDao = medicoCoordenadorDao;
	}

	@SuppressWarnings("deprecation")
	public void testFindByDataEmpresa()
	{
		Empresa empresa1 = new Empresa();
		empresa1.setId(123434214L);
		empresa1.setNome("fortes");
		empresa1.setCnpj("65465");
		empresa1.setRazaoSocial("fortes");

		Empresa empresa2 = new Empresa();
		empresa1.setId(12345454L);
		empresa2.setNome("fortes");
		empresa2.setCnpj("65465");
		empresa2.setRazaoSocial("fortes");

		empresa1 = empresaDao.save(empresa1);
		empresa2 = empresaDao.save(empresa2);

		MedicoCoordenador medico1 = new MedicoCoordenador();
		medico1.setEmpresa(empresa1);
		medico1.setInicio(new Date("2000/01/01"));

		MedicoCoordenador medico2 = new MedicoCoordenador();
		medico2.setEmpresa(empresa1);
		medico2.setInicio(new Date("2007/01/01"));

		MedicoCoordenador medico3 = new MedicoCoordenador();
		medico3.setEmpresa(empresa2);
		medico3.setInicio(new Date("2008/01/01"));

		medico1 = medicoCoordenadorDao.save(medico1);
		medico2 = medicoCoordenadorDao.save(medico2);
		medico3 = medicoCoordenadorDao.save(medico3);

		MedicoCoordenador medicoRetorno = medicoCoordenadorDao.findByDataEmpresa(empresa1.getId(), new Date("2008/01/01"));

		assertEquals(medico2, medicoRetorno);
	}

	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		MedicoCoordenador medico = new MedicoCoordenador();
		medico.setEmpresa(empresa);
		medico = medicoCoordenadorDao.save(medico);

		MedicoCoordenador medicoRetorno = medicoCoordenadorDao.findByIdProjection(medico.getId());

		assertEquals(medico, medicoRetorno);
	}

	@SuppressWarnings("unused")
	public void testFindResponsaveisPorEstabelecimentoComEstabelecimento()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);

		Collection<Estabelecimento> estabelecimentos = Arrays.asList(estabelecimento1, estabelecimento2);

		MedicoCoordenador medicoCoordenador1 = saveMedicoCoordenador(MedicoCoordenadorFactory.getEntity(empresa1, DateUtil.incrementaMes(new Date(), -2), TipoEstabelecimentoResponsavel.TODOS, null));
		MedicoCoordenador medicoCoordenador2 = saveMedicoCoordenador(MedicoCoordenadorFactory.getEntity(empresa1, DateUtil.incrementaMes(new Date(), -3), TipoEstabelecimentoResponsavel.ALGUNS, estabelecimentos));
		MedicoCoordenador medicoCoordenador3 = saveMedicoCoordenador(MedicoCoordenadorFactory.getEntity(empresa2, DateUtil.incrementaMes(new Date(), 3), TipoEstabelecimentoResponsavel.TODOS, null));
		MedicoCoordenador medicoCoordenador4 = saveMedicoCoordenador(MedicoCoordenadorFactory.getEntity(empresa1, DateUtil.incrementaDias(new Date(), 1), TipoEstabelecimentoResponsavel.TODOS, null));
		MedicoCoordenador medicoCoordenador5 = saveMedicoCoordenador(MedicoCoordenadorFactory.getEntity(empresa1, DateUtil.incrementaDias(new Date(), 1), TipoEstabelecimentoResponsavel.ALGUNS, estabelecimentos));
		Collection<MedicoCoordenador> medicosCoordenadores = medicoCoordenadorDao.findResponsaveisPorEstabelecimento(empresa1.getId(), estabelecimento1.getId());

		assertEquals(2, medicosCoordenadores.size());
		assertEquals(medicoCoordenador2.getId(), ((MedicoCoordenador)medicosCoordenadores.toArray()[0]).getId());
		assertEquals(medicoCoordenador1.getId(), ((MedicoCoordenador)medicosCoordenadores.toArray()[1]).getId());
	}
	
	@SuppressWarnings("unused")
	public void testFindResponsaveisPorEstabelecimentoSemEstabelecimento()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Collection<Estabelecimento> estabelecimentos = Arrays.asList(estabelecimento1, estabelecimento2);
		
		MedicoCoordenador medicoCoordenador1 = saveMedicoCoordenador(MedicoCoordenadorFactory.getEntity(empresa1, DateUtil.incrementaMes(new Date(), -2), TipoEstabelecimentoResponsavel.TODOS, null));
		MedicoCoordenador medicoCoordenador2 = saveMedicoCoordenador(MedicoCoordenadorFactory.getEntity(empresa1, DateUtil.incrementaMes(new Date(), -3), TipoEstabelecimentoResponsavel.ALGUNS, estabelecimentos));
		MedicoCoordenador medicoCoordenador3 = saveMedicoCoordenador(MedicoCoordenadorFactory.getEntity(empresa2, DateUtil.incrementaMes(new Date(), -3), TipoEstabelecimentoResponsavel.TODOS, null));
		MedicoCoordenador medicoCoordenador4 = saveMedicoCoordenador(MedicoCoordenadorFactory.getEntity(empresa1, DateUtil.incrementaDias(new Date(), 1), TipoEstabelecimentoResponsavel.TODOS, null));
		MedicoCoordenador medicoCoordenador5 = saveMedicoCoordenador(MedicoCoordenadorFactory.getEntity(empresa1, DateUtil.incrementaDias(new Date(), -1), TipoEstabelecimentoResponsavel.TODOS, null));
		MedicoCoordenador medicoCoordenador6 = saveMedicoCoordenador(MedicoCoordenadorFactory.getEntity(empresa1, DateUtil.incrementaMes(new Date(), -5), TipoEstabelecimentoResponsavel.TODOS, null));
		
		Collection<MedicoCoordenador> medicosCoordenadores = medicoCoordenadorDao.findResponsaveisPorEstabelecimento(empresa1.getId(), null);
		
		assertEquals(3, medicosCoordenadores.size());
		assertEquals(medicoCoordenador6.getId(), ((MedicoCoordenador)medicosCoordenadores.toArray()[0]).getId());
		assertEquals(medicoCoordenador1.getId(), ((MedicoCoordenador)medicosCoordenadores.toArray()[1]).getId());
		assertEquals(medicoCoordenador5.getId(), ((MedicoCoordenador)medicosCoordenadores.toArray()[2]).getId());
	}

	private MedicoCoordenador saveMedicoCoordenador(MedicoCoordenador medicoCoordenador) {
		medicoCoordenadorDao.save(medicoCoordenador);
		return medicoCoordenador;
	}
	
	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}
}