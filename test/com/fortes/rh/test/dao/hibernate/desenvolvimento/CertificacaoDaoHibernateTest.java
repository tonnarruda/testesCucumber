package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;

public class CertificacaoDaoHibernateTest extends GenericDaoHibernateTest<Certificacao>
{
	private CertificacaoDao certificacaoDao;
	private EmpresaDao empresaDao;
	private FaixaSalarialDao faixaSalarialDao;
	private CursoDao cursoDao;

	public Certificacao getEntity()
	{
		Certificacao certificacao = CertificacaoFactory.getEntity();
		return certificacao;
	}
	public GenericDao<Certificacao> getGenericDao()
	{
		return certificacaoDao;
	}

	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacao.setEmpresa(empresa);
		certificacaoDao.save(certificacao);
		
		assertEquals(1, certificacaoDao.findAllSelect(empresa.getId()).size());
	}
	
	public void testFindAllSelectNomeBusca()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacao.setNome("Gerência de Projetos");
		certificacao.setEmpresa(empresa);
		certificacaoDao.save(certificacao);
		
		Certificacao certificacao2 = CertificacaoFactory.getEntity();
		certificacao2.setEmpresa(empresa);
		certificacaoDao.save(certificacao2);
		
		assertEquals(1, certificacaoDao.findAllSelect(0, 0, empresa.getId(), "Gerencia").size());
	}
	
	public void testGetCount()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacao.setNome("Gerência de Projetos");
		certificacao.setEmpresa(empresa);
		certificacaoDao.save(certificacao);
		
		Certificacao certificacao2 = CertificacaoFactory.getEntity();
		certificacao2.setEmpresa(empresa);
		certificacaoDao.save(certificacao2);
		
		assertEquals(new Integer(1), certificacaoDao.getCount(empresa.getId(), "Gerencia"));
	}

	public void testDeleteByFaixaSalarial()
	{
		Exception exception = null;
		
		try {
			certificacaoDao.deleteByFaixaSalarial(new Long[] {999999999999998L,999999999999999L});
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacao.setEmpresa(empresa);
		certificacaoDao.save(certificacao);
		
		assertEquals(certificacao, certificacaoDao.findByIdProjection(certificacao.getId()));
	}
	
	public void testFindByFaixa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Certificacao certificacao1 = CertificacaoFactory.getEntity();
		certificacao1.setEmpresa(empresa);
		certificacaoDao.save(certificacao1);
		
		Certificacao certificacao2 = CertificacaoFactory.getEntity();
		certificacao2.setEmpresa(empresa);
		certificacaoDao.save(certificacao2);

		Collection<Certificacao> certificacaos = new ArrayList<Certificacao>();
		certificacaos.add(certificacao1);
		certificacaos.add(certificacao2);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCertificacaos(certificacaos);
		faixaSalarialDao.save(faixaSalarial);
		
		Collection<Certificacao> retorno = certificacaoDao.findByFaixa(faixaSalarial.getId());
		assertEquals(2, retorno.size());
	}
	
	public void testFindMatrizTreinamento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Collection<Curso> cursos = new ArrayList<Curso>();
		cursos.add(curso);
		
		Certificacao certificacao1 = CertificacaoFactory.getEntity();
		certificacao1.setEmpresa(empresa);
		certificacao1.setCursos(cursos);
		certificacaoDao.save(certificacao1);
		
		Certificacao certificacao2 = CertificacaoFactory.getEntity();
		certificacao2.setEmpresa(empresa);
		certificacao2.setCursos(cursos);
		certificacaoDao.save(certificacao2);
		
		Collection<Certificacao> certificacaos = new ArrayList<Certificacao>();
		certificacaos.add(certificacao1);
		certificacaos.add(certificacao2);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCertificacaos(certificacaos);
		faixaSalarialDao.save(faixaSalarial);
		
		Collection<Long> faixaIds = new ArrayList<Long>();
		faixaIds.add(faixaSalarial.getId());
		
		Collection<MatrizTreinamento> retorno = certificacaoDao.findMatrizTreinamento(faixaIds);
		assertEquals(2, retorno.size());
	}
	
	public void setCertificacaoDao(CertificacaoDao certificacaoDao)
	{
		this.certificacaoDao = certificacaoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao)
	{
		this.faixaSalarialDao = faixaSalarialDao;
	}
	public void setCursoDao(CursoDao cursoDao)
	{
		this.cursoDao = cursoDao;
	}
}