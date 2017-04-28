package com.fortes.rh.test.dao.hibernate.cargosalario;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;

public class FaixaSalarialDaoHibernateTest_JUnit4 extends GenericDaoHibernateTest_JUnit4<FaixaSalarial>
{
	@Autowired
	private FaixaSalarialDao faixaSalarialDao;
    @Autowired
    private CargoDao cargoDao;
    @Autowired
    private EmpresaDao empresaDao;
    @Autowired
    private AreaOrganizacionalDao areaOrganizacionalDao;

    public GenericDao<FaixaSalarial> getGenericDao()
    {
    	return faixaSalarialDao;
    }

    public FaixaSalarial getEntity()
    {
        FaixaSalarial faixaSalarial = new FaixaSalarial();

        faixaSalarial.setCargo(null);
        faixaSalarial.setCodigoAC("0");
        faixaSalarial.setId(null);
        faixaSalarial.setNome("faixa");

        return faixaSalarial;
    }

    @Test
	public void testgetCargosFaixaByAreaIdAndEmpresaId()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area);
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areas.add(area);
		
		Cargo cargo1 = saveCargo(empresa, true, areas);
		Cargo cargo2 = saveCargo(empresa, false, areas);
		Cargo cargo3 = saveCargo(empresa, true, null);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity("F1", cargo1);
		faixaSalarialDao.save(faixaSalarial);
		
		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity("F2", cargo1);
		faixaSalarialDao.save(faixaSalarial2);

		FaixaSalarial faixaSalarial3 = FaixaSalarialFactory.getEntity("F3", cargo2);
		faixaSalarialDao.save(faixaSalarial3);
		
		FaixaSalarial faixaSalarial4 = FaixaSalarialFactory.getEntity("F4", cargo3);
		faixaSalarialDao.save(faixaSalarial4);
		
		assertEquals(2,faixaSalarialDao.getCargosFaixaByAreaIdAndEmpresaId(area.getId(), empresa.getId(), null).size());
		assertEquals(3,faixaSalarialDao.getCargosFaixaByAreaIdAndEmpresaId(area.getId(), empresa.getId(), faixaSalarial3.getId()).size());
	}
    
    private Cargo saveCargo(Empresa empresa, boolean ativo, Collection<AreaOrganizacional> areas){
    	Cargo cargo = CargoFactory.getEntity();
		cargo.setEmpresa(empresa);
		cargo.setAreasOrganizacionais(areas);
		cargo.setAtivo(ativo);
		cargoDao.save(cargo);
		return cargo;
    }
}