package com.fortes.rh.test.dao.hibernate.cargosalario;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;

public class CargoDaoHibernateTest_JUnit4 extends GenericDaoHibernateTest_JUnit4<Cargo>
{
	@Autowired
	private CargoDao cargoDao;
	@Autowired
	private GrupoOcupacionalDao grupoOcupacionalDao;
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private AreaOrganizacionalDao areaOrganizacionalDao;
	@Autowired
	private FaixaSalarialDao faixaSalarialDao;
	@Autowired
	private ColaboradorDao colaboradorDao;
	@Autowired
	private HistoricoColaboradorDao historicoColaboradorDao;
	@Autowired
	private SolicitacaoDao solicitacaoDao;
	@Autowired
	private GrupoACDao grupoACDao;

	public Cargo getEntity()
	{
		Cargo cargo = new Cargo();

		cargo.setAreaFormacaos(null);
		cargo.setAreasOrganizacionais(null);
		cargo.setCompetencias("competencias");
		cargo.setConhecimentos(null);
		cargo.setEscolaridade("e");
		cargo.setExperiencia("e");
		cargo.setFaixaSalarials(null);
		cargo.setGrupoOcupacional(null);
		cargo.setId(null);
		cargo.setMissao("missão");
		cargo.setNome("nome");
		cargo.setNomeMercado("nomemercado");
		cargo.setObservacao("observação");
		cargo.setRecrutamento("recrutamento");
		cargo.setResponsabilidades("responsabilidades");
		cargo.setSelecao("seleção");
		cargo.setEmpresa(null);

		return cargo;
	}
	
	public GenericDao<Cargo> getGenericDao() {
		return null;
	}

	@Test
	public void testFindByArea()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Collection<AreaOrganizacional> areasOrganizacionais = new ArrayList<AreaOrganizacional>();
		areasOrganizacionais.add(areaOrganizacional);
		
		Cargo cobrador = CargoFactory.getEntity();
		cobrador.setAreasOrganizacionais(areasOrganizacionais);
		cobrador.setEmpresa(empresa);
		cargoDao.save(cobrador);
		
		Cargo motorista = CargoFactory.getEntity();
		motorista.setEmpresa(empresa);
		motorista.setAreasOrganizacionais(areasOrganizacionais);
		cargoDao.save(motorista);
		
		Collection<Cargo> retorno = cargoDao.findByAreasAndGrupoOcapcinal(empresa.getId(), null, null, areaOrganizacional.getId());
		
		assertEquals(2, retorno.size());
	}
}