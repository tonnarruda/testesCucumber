package com.fortes.rh.test.dao.hibernate.cargosalario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialHistoricoDao;
import com.fortes.rh.dao.cargosalario.ReajusteFaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.ReajusteFaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;

public class FaixaSalarialDaoHibernateTest_JUnit4 extends GenericDaoHibernateTest_JUnit4<FaixaSalarial>
{
    @Autowired private CargoDao cargoDao;
    @Autowired private EmpresaDao empresaDao;
    @Autowired private FaixaSalarialDao faixaSalarialDao;
    @Autowired private AreaOrganizacionalDao areaOrganizacionalDao;
    @Autowired private ReajusteFaixaSalarialDao reajusteFaixaSalarialDao;
    @Autowired private FaixaSalarialHistoricoDao faixaSalarialHistoricoDao;
    @Autowired private TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao;

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
    
    @Test
    public void testExisteHistoricoConfirmadoByTabelaReajusteColaborador() {
    	FaixaSalarial faixaSalarial1 = saveFaixaSalarial();
    	FaixaSalarial faixaSalarial2 = saveFaixaSalarial();

    	TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
    	tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);
    	
    	ReajusteFaixaSalarial reajusteFaixaSalarial1 = ReajusteFaixaSalarialFactory.getEntity(tabelaReajusteColaborador, faixaSalarial1, 0.0, 0.0);
    	reajusteFaixaSalarialDao.save(reajusteFaixaSalarial1);  
    	
    	ReajusteFaixaSalarial reajusteFaixaSalarial2 = ReajusteFaixaSalarialFactory.getEntity(tabelaReajusteColaborador, faixaSalarial2, 0.0, 0.0);
    	reajusteFaixaSalarialDao.save(reajusteFaixaSalarial2);

    	saveFaixaSalarialHistorico(faixaSalarial1, reajusteFaixaSalarial1, new Date(), StatusRetornoAC.CONFIRMADO);
    	saveFaixaSalarialHistorico(faixaSalarial2, reajusteFaixaSalarial2, new Date(), StatusRetornoAC.AGUARDANDO);
    	
    	assertTrue(faixaSalarialHistoricoDao.existeHistoricoConfirmadoByTabelaReajusteColaborador(tabelaReajusteColaborador.getId()));
	}
    
    private Cargo saveCargo(Empresa empresa, boolean ativo, Collection<AreaOrganizacional> areas){
    	Cargo cargo = CargoFactory.getEntity();
		cargo.setEmpresa(empresa);
		cargo.setAreasOrganizacionais(areas);
		cargo.setAtivo(ativo);
		cargoDao.save(cargo);
		return cargo;
    }
    
    private FaixaSalarial saveFaixaSalarial(){
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
    	faixaSalarialDao.save(faixaSalarial);
    	return faixaSalarial;
    }
    
    private FaixaSalarialHistorico saveFaixaSalarialHistorico(FaixaSalarial faixaSalarial, ReajusteFaixaSalarial reajusteFaixaSalarial, Date data, Integer status){
    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(faixaSalarial, data, status);
    	faixaSalarialHistorico.setReajusteFaixaSalarial(reajusteFaixaSalarial);
    	faixaSalarialHistoricoDao.save(faixaSalarialHistorico);
    	return faixaSalarialHistorico;
    }
    
    
    
}