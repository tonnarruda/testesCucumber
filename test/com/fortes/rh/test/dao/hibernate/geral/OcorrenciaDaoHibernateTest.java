package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.dao.geral.OcorrenciaDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;

public class OcorrenciaDaoHibernateTest extends GenericDaoHibernateTest<Ocorrencia>
{
	private OcorrenciaDao ocorrenciaDao;
	private EmpresaDao empresaDao;
	private GrupoACDao grupoACDao;

	public Ocorrencia getEntity()
	{
		Ocorrencia ocorrencia = new Ocorrencia();

		ocorrencia.setId(1L);
		ocorrencia.setDescricao("teste");
		ocorrencia.setPontuacao(10);

		return ocorrencia;
	}

	public void setOcorrenciaDao(OcorrenciaDao ocorrenciaDao)
	{
		this.ocorrenciaDao = ocorrenciaDao;
	}

	public GenericDao<Ocorrencia> getGenericDao()
	{
		return ocorrenciaDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void testFindByCodigoAC()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("1");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresaDao.save(empresa);
		
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia.setCodigoAC("123");
		ocorrencia.setEmpresa(empresa);
		ocorrenciaDao.save(ocorrencia);

		Ocorrencia resultado =  ocorrenciaDao.findByCodigoAC(ocorrencia.getCodigoAC(), ocorrencia.getEmpresa().getCodigoAC(), "XXX");
		assertEquals(ocorrencia,resultado);
	}

	public void testRemoveByCodigoAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("1");
		empresaDao.save(empresa);
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia.setCodigoAC("123");
		ocorrencia.setEmpresa(empresa);
		ocorrenciaDao.save(ocorrencia);
		assertTrue(ocorrenciaDao.removeByCodigoAC(ocorrencia.getCodigoAC(), empresa.getId()));
	}
	
	public void testFindSincronizarOcorrenciaInteresse()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("1");
		empresaDao.save(empresa);
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia.setEmpresa(empresa);
		ocorrencia.setPontuacao(5);
		ocorrencia.setDescricao("teste");
		ocorrencia.setIntegraAC(false);
		ocorrencia.setCodigoAC("123");
		ocorrenciaDao.save(ocorrencia);
		
		Collection<Ocorrencia> ocorrencias = ocorrenciaDao.findSincronizarOcorrenciaInteresse(empresa.getId());
		assertEquals(1,ocorrencias.size());
	}
	
	public void testFindAllSelect()
	{
		Empresa vega = EmpresaFactory.getEmpresa();
		empresaDao.save(vega);

		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia.setEmpresa(vega);
		ocorrenciaDao.save(ocorrencia);

		assertEquals(1, ocorrenciaDao.findAllSelect(new Long[]{vega.getId()}).size());
	}

	public void setGrupoACDao(GrupoACDao grupoACDao) {
		this.grupoACDao = grupoACDao;
	}
}