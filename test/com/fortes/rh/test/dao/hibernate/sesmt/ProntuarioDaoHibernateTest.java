package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.ProntuarioDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Prontuario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ProntuarioFactory;
import com.fortes.rh.util.DateUtil;

public class ProntuarioDaoHibernateTest extends GenericDaoHibernateTest<Prontuario>
{
	ProntuarioDao prontuarioDao;
	ColaboradorDao colaboradorDao;
	EmpresaDao empresaDao;

	public void setProntuarioDao(ProntuarioDao prontuarioDao)
	{
		this.prontuarioDao = prontuarioDao;
	}

	@Override
	public Prontuario getEntity()
	{
		return ProntuarioFactory.getEntity();
	}

	@Override
	public GenericDao<Prontuario> getGenericDao()
	{
		return prontuarioDao;
	}

	public void testFindByColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Prontuario prontuario = ProntuarioFactory.getEntity();
		prontuario.setColaborador(colaborador);
		prontuarioDao.save(prontuario);
		
		assertEquals(1, prontuarioDao.findByColaborador(colaborador).size());
	}
	
	public void testFindQtdByEmpresa() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		Prontuario prontuario1 = ProntuarioFactory.getEntity();
		prontuario1.setColaborador(colaborador);
		prontuario1.setData(DateUtil.criarDataMesAno(1, 12, 2009));
		prontuarioDao.save(prontuario1);
		
		Prontuario prontuario2 = ProntuarioFactory.getEntity();
		prontuario2.setColaborador(colaborador);
		prontuario2.setData(DateUtil.criarDataMesAno(2, 12, 2009));
		prontuarioDao.save(prontuario2);
		
		Prontuario prontuario3 = ProntuarioFactory.getEntity();
		prontuario3.setColaborador(colaborador);
		prontuario3.setData(DateUtil.criarDataMesAno(3, 12, 2010));
		prontuarioDao.save(prontuario3);
		
		Prontuario prontuario4 = ProntuarioFactory.getEntity();
		prontuario4.setColaborador(colaborador);
		prontuario4.setData(DateUtil.criarDataMesAno(3, 12, 2011));
		prontuarioDao.save(prontuario4);
		
		assertEquals(new Integer(2), prontuarioDao.findQtdByEmpresa(empresa.getId(), DateUtil.criarDataMesAno(2, 12, 2009), DateUtil.criarDataMesAno(2, 12, 2011)));
		
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}