package com.fortes.rh.test.dao.hibernate.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;

public class NivelCompetenciaDaoHibernateTest extends GenericDaoHibernateTest<NivelCompetencia>
{
	private NivelCompetenciaDao nivelCompetenciaDao;
	private EmpresaDao empresaDao;

	@Override
	public NivelCompetencia getEntity()
	{
		return NivelCompetenciaFactory.getEntity();
	}

	public void testFindAllSelect()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		NivelCompetencia nivelRuim = NivelCompetenciaFactory.getEntity();
		nivelRuim.setDescricao("Ruim");
		nivelRuim.setOrdem(1);
		nivelRuim.setEmpresa(empresa1);
		nivelCompetenciaDao.save(nivelRuim);

		NivelCompetencia nivelRegular = NivelCompetenciaFactory.getEntity();
		nivelRegular.setDescricao("Regular");
		nivelRegular.setOrdem(2);
		nivelRegular.setEmpresa(empresa1);
		nivelCompetenciaDao.save(nivelRegular);

		NivelCompetencia nivelBom = NivelCompetenciaFactory.getEntity();
		nivelBom.setDescricao("Bom");
		nivelBom.setOrdem(3);
		nivelBom.setEmpresa(empresa1);
		nivelCompetenciaDao.save(nivelBom);
		
		NivelCompetencia nivelBomEmpresa2 = NivelCompetenciaFactory.getEntity();
		nivelBomEmpresa2.setDescricao("Bom");
		nivelBomEmpresa2.setOrdem(3);
		nivelBomEmpresa2.setEmpresa(empresa2);
		nivelCompetenciaDao.save(nivelBomEmpresa2);
		
		assertEquals(3, nivelCompetenciaDao.findAllSelect(empresa1.getId()).size());
		assertTrue(nivelCompetenciaDao.findAllSelect(null).size() >= 4);
	}

	@Override
	public GenericDao<NivelCompetencia> getGenericDao()
	{
		return nivelCompetenciaDao;
	}

	public void setNivelCompetenciaDao(NivelCompetenciaDao nivelCompetenciaDao)
	{
		this.nivelCompetenciaDao = nivelCompetenciaDao;
	}
	
	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}
