package com.fortes.rh.test.dao.hibernate.cargosalario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.GrupoOcupacionalFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.util.DateUtil;

public class CargoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<Cargo>
{
	@Autowired private CargoDao cargoDao;
	@Autowired private GrupoOcupacionalDao grupoOcupacionalDao;
	@Autowired private EmpresaDao empresaDao;
	@Autowired private AreaOrganizacionalDao areaOrganizacionalDao;
	@Autowired private FaixaSalarialDao faixaSalarialDao;
	@Autowired private ColaboradorDao colaboradorDao;
	@Autowired private HistoricoColaboradorDao historicoColaboradorDao;
	@Autowired private SolicitacaoDao solicitacaoDao;
	@Autowired private GrupoACDao grupoACDao;

	public GenericDao<Cargo> getGenericDao()
    {
        return cargoDao;
    }
	
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

	@Test
	public void testGetCount()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		areaOrganizacionals.add(areaOrganizacional);

		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setNomeMercado("CARGO1");
		cargo1.setAreasOrganizacionais(areaOrganizacionals);
		cargo1.setEmpresa(empresa);
		cargo1 = cargoDao.save(cargo1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setEmpresa(empresa);
		cargo2 = cargoDao.save(cargo2);

		int retorno1 = cargoDao.getCount(empresa.getId(), null, null, null);
		int retorno2 = cargoDao.getCount(empresa.getId(), areaOrganizacional.getId(), null, null);
		int retorno3 = cargoDao.getCount(empresa.getId(), areaOrganizacional.getId(), "CARGO1", null);
		int retorno4 = cargoDao.getCount(empresa.getId(), areaOrganizacional.getId(), "CARGO2", null);

		assertEquals("Por empresa", 2, retorno1);
		assertEquals("Por empresa e área organizacional", 1, retorno2);
		assertEquals("Por empresa, área organizacional e nome do cargo", 1, retorno3);
		assertEquals("Por empresa, área organizacional e nome do cargo errado", 0, retorno4);
	}

	@Test
	public void testFindCargos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		areaOrganizacionals.add(areaOrganizacional);

		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setNomeMercado("CARGO1");
		cargo1.setAreasOrganizacionais(areaOrganizacionals);
		cargo1.setEmpresa(empresa);
		cargo1 = cargoDao.save(cargo1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setEmpresa(empresa);
		cargo2 = cargoDao.save(cargo2);

		Collection<Cargo> retorno1 = cargoDao.findCargos(0, 0, empresa.getId(), null, null, null, false);
		Collection<Cargo> retorno2 = cargoDao.findCargos(0, 0, empresa.getId(), areaOrganizacional.getId(), null, null, true);
		Collection<Cargo> retorno3 = cargoDao.findCargos(0, 0, empresa.getId(), areaOrganizacional.getId(), "CARGO1", null, false);
		Collection<Cargo> retorno4 = cargoDao.findCargos(1, 15, empresa.getId(), areaOrganizacional.getId(), "CARGO2", null, false);

		assertEquals("Por empresa", 2, retorno1.size());
		assertEquals("Por empresa e área organizacional", 1, retorno2.size());
		assertEquals("Por empresa, área organizacional e nome do cargo", 1, retorno3.size());
		assertEquals("Por empresa, área organizacional e nome do cargo errado", 0, retorno4.size());
	}

	@Test
	public void testFindByGrupoOcupacionalIdsProjection()
	{
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		Collection<Cargo> retorno = cargoDao.findByGrupoOcupacionalIdsProjection(new Long[] {grupoOcupacional.getId()}, null, null);

		assertEquals(1, retorno.size());
	}
	
	@Test
	public void testFindByEmpresaAC()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("asdf21");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresaDao.save(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setEmpresa(empresa);
		cargo = cargoDao.save(cargo);
		
		Collection<Cargo> retorno = cargoDao.findByEmpresaAC(empresa.getCodigoAC(), "XXX");
		
		assertEquals(1, retorno.size());
	}
	
	@Test
	public void testFindByEmpresaACCodigoFaixa()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("asdf21");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresaDao.save(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setEmpresa(empresa);
		cargo = cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		faixaSalarial.setCargo(cargo);

		Collection<Cargo> retorno = cargoDao.findByEmpresaAC(empresa.getCodigoAC(), faixaSalarial.getCodigoAC(), "XXX");
		
		assertEquals(1, retorno.size());
	}

	@Test
	public void testFindBySolicitacao()
	{
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		faixaSalarial.setCargo(cargo);

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setFaixaSalarial(faixaSalarial);
		solicitacaoDao.save(solicitacao);

		Collection<Cargo> retorno = cargoDao.findBySolicitacao(solicitacao.getId());
		assertEquals(1, retorno.size());

		//não existe essa solicitação
		retorno = cargoDao.findBySolicitacao(1155244L);
		assertTrue(retorno.isEmpty());
	}

	@Test
	public void testFindByGrupoOcupacional()
	{
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		Collection<Cargo> retorno = cargoDao.findByGrupoOcupacional(grupoOcupacional.getId());

		assertEquals(1, retorno.size());
	}

	@Test
	public void testFindByAreaOrganizacionalIdsProjection()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		areaOrganizacionals.add(areaOrganizacional);

		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setAreasOrganizacionais(areaOrganizacionals);
		cargo1 = cargoDao.save(cargo1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargo2 = cargoDao.save(cargo2);

		Collection<Cargo> retorno1 = cargoDao.findByAreaOrganizacionalIdsProjection(new Long[] {areaOrganizacional.getId()}, null);
		Collection<Cargo> retorno2 = cargoDao.findByAreaOrganizacionalIdsProjection(new Long[] {}, null);
		Collection<Cargo> retorno3 = cargoDao.findByAreaOrganizacionalIdsProjection(null, null);

		assertEquals("Ids das áreas preenchidos", 1, retorno1.size());
		assertTrue("Ids das áreas vazio", retorno2.size() >= 2);
		assertTrue("Ids das áreas nulo", retorno3.size() >= 2);
	}

	@Test
	public void testFindCargosByIds()
	{
		Cargo cargo1 = CargoFactory.getEntity();
		cargo1 = cargoDao.save(cargo1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargo2 = cargoDao.save(cargo2);

		Collection<Cargo> retorno = cargoDao.findCargosByIds(new Long[]{cargo1.getId(), cargo2.getId()}, null);

		assertEquals(2, retorno.size());
	}

	@Test
	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setEmpresa(empresa);
		cargo1 = cargoDao.save(cargo1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setEmpresa(empresa);
		cargo2 = cargoDao.save(cargo2);

		Collection<Cargo> retorno = cargoDao.findAllSelect(new Long[]{empresa.getId()}, "nomeMercado", null, Cargo.TODOS);

		assertEquals(2, retorno.size());
	}
	
	@Test
	public void testFindAllSelectModuloExterno()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setAtivo(true);
		cargo1.setEmpresa(empresa);
		cargo1 = cargoDao.save(cargo1);
		
		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setAtivo(true);
		cargo2.setEmpresa(empresa);
		cargo2 = cargoDao.save(cargo2);
		
		Cargo cargo3 = CargoFactory.getEntity();
		cargo3.setAtivo(true);
		cargo3.setEmpresa(empresa);
		cargo3 = cargoDao.save(cargo3);
		
		Cargo cargo4 = CargoFactory.getEntity();
		cargo4.setAtivo(false);
		cargo4.setEmpresa(empresa);
		cargo4 = cargoDao.save(cargo4);
		
		Collection<Cargo> retorno = cargoDao.findAllSelect(new Long[]{empresa.getId()}, "nomeMercado", null, Cargo.ATIVO);
		
		assertEquals(3, retorno.size());
	}

	@Test
	public void testFindAllSelectEmpresas()
	{
		Empresa vega = EmpresaFactory.getEmpresa();
		empresaDao.save(vega);
		Empresa urbana = EmpresaFactory.getEmpresa();
		empresaDao.save(urbana);
		
		Cargo cobrador = CargoFactory.getEntity();
		cobrador.setEmpresa(vega);
		cobrador = cargoDao.save(cobrador);
		
		Cargo motorista = CargoFactory.getEntity();
		motorista.setEmpresa(urbana);
		motorista = cargoDao.save(motorista);
		
		Collection<Cargo> retorno = cargoDao.findAllSelect(new Long[]{vega.getId(), urbana.getId()});
		
		assertEquals(2, retorno.size());
	}

	@Test
	public void testGetCargoSemAreaRelacionadaTodosRelacionados()
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
		
		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area1);
		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area2);
		
		Collection<AreaOrganizacional> areasOrganizacionais2 = new ArrayList<AreaOrganizacional>();
		areasOrganizacionais2.add(area1);
		areasOrganizacionais2.add(area2);
		
		Cargo motorista = CargoFactory.getEntity();
		motorista.setAreasOrganizacionais(areasOrganizacionais2);
		motorista.setEmpresa(empresa);
		cargoDao.save(motorista);
		
		assertEquals(0, cargoDao.getCargosSemAreaRelacionada(empresa.getId()).size());
	}
	
	@Test
	public void testGetCargoSemAreaRelacionada()
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
		motorista.setAreasOrganizacionais(null);
		motorista.setEmpresa(empresa);
		cargoDao.save(motorista);
		
		assertEquals(1, cargoDao.getCargosSemAreaRelacionada(empresa.getId()).size());
	}
	
	@Test
	public void testGetCargoSemGrupoRelacionado()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacionalDao.save(grupoOcupacional);
		
		Cargo cobrador = CargoFactory.getEntity();
		cobrador.setGrupoOcupacional(grupoOcupacional);
		cobrador.setEmpresa(empresa);
		cargoDao.save(cobrador);

		Cargo fiscal = CargoFactory.getEntity();
		fiscal.setGrupoOcupacional(grupoOcupacional);
		fiscal.setEmpresa(empresa);
		cargoDao.save(fiscal);
		
		Cargo motorista = CargoFactory.getEntity();
		motorista.setGrupoOcupacional(null);
		motorista.setEmpresa(empresa);
		cargoDao.save(motorista);
		
		assertEquals(1, cargoDao.getCargosSemGrupoRelacionado(empresa.getId()).size());
	}
	
	@Test
	public void testGetCargoSemAreaRelacionadaSemCargo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		assertEquals(0, cargoDao.getCargosSemAreaRelacionada(empresa.getId()).size());
	}
	
	@Test
	public void testFindAllSelectDistinctNome()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);

		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setEmpresa(empresa1);
		cargo1.setNomeMercado("Desenvolvedor");
		cargoDao.save(cargo1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setEmpresa(empresa1);
		cargo2.setNomeMercado("Analista");
		cargoDao.save(cargo2);

		Cargo cargo3 = CargoFactory.getEntity();
		cargo3.setEmpresa(empresa2);
		cargo3.setNomeMercado("Desenvolvedor");
		cargoDao.save(cargo3);

		Collection<Cargo> cargos = cargoDao.findAllSelectDistinctNomeMercado(new Long[]{empresa1.getId(), empresa2.getId()});

		int cont = 0;
		for (Cargo cargo : cargos)
		{
			if(cargo.getNomeMercado().equals("Desenvolvedor"))
				cont++;
		}
		
		assertEquals(1, cont);
	}

	@Test
	public void testFindByIdProjection()
	{
		Cargo cargo = CargoFactory.getEntity();
		cargo = cargoDao.save(cargo);

		Cargo retorno = cargoDao.findByIdProjection(cargo.getId());

		assertEquals(cargo.getId(), retorno.getId());
	}

	@Test
	public void testFfindByIdAllProjection()
	{
		Cargo cargo = CargoFactory.getEntity();
		cargo = cargoDao.save(cargo);

		Cargo cargoRetorno = cargoDao.findByIdAllProjection(cargo.getId());

		assertEquals(cargo.getId(), cargoRetorno.getId());
	}

	@Test
	public void testVerifyExistCargoNome()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("GERENTE");
		cargo.setEmpresa(empresa);
		cargo = cargoDao.save(cargo);

		assertTrue(cargoDao.verifyExistCargoNome(cargo.getNome(), empresa.getId()));
	}

	@Test
	public void testFindByAreaDoHistoricoColaborador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional1);

		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional2);

		Cargo cargo1 = CargoFactory.getEntity();
		cargoDao.save(cargo1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargoDao.save(cargo2);

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setCargo(cargo1);
		faixaSalarialDao.save(faixaSalarial1);

		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCargo(cargo2);
		faixaSalarialDao.save(faixaSalarial2);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setColaborador(colaborador);
		historicoColaborador1.setAreaOrganizacional(areaOrganizacional1);
		historicoColaborador1.setFaixaSalarial(faixaSalarial1);
		historicoColaborador1.setData(DateUtil.criarDataMesAno(01, 01, 2000));
		historicoColaboradorDao.save(historicoColaborador1);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setColaborador(colaborador);
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional2);
		historicoColaborador2.setFaixaSalarial(faixaSalarial2);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(01, 01, 2005));
		historicoColaboradorDao.save(historicoColaborador2);

		Collection<Cargo> cargos = cargoDao.findByAreaDoHistoricoColaborador(new Long[] {areaOrganizacional1.getId(), areaOrganizacional2.getId()});
		assertEquals(1, cargos.size());
		assertEquals(cargo2, cargos.toArray()[0]);
	}
	
	@Test
	public void testFindSincronizarCargos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setEmpresa(empresa);
		cargo1 = cargoDao.save(cargo1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setEmpresa(empresa);
		cargo2 = cargoDao.save(cargo2);

		Collection<Cargo> retorno = cargoDao.findSincronizarCargos(empresa.getId());

		assertEquals(2, retorno.size());
	}

	@Test
	public void testDeleteByAreaOrganizacional()
	{
		Exception exception = null;
		
		try {
			cargoDao.deleteByAreaOrganizacional(new Long[] {9999999999999999L,9999999999999998L});
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	@Test
	public void testFindByAreaGrupo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area1);

		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(area2);
		
		GrupoOcupacional grupo1 = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacionalDao.save(grupo1);
		
		GrupoOcupacional grupo2 = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacionalDao.save(grupo2);
		
		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setEmpresa(empresa);
		cargo1.setGrupoOcupacional(grupo1);
		cargo1.setAreasOrganizacionais(Arrays.asList(new AreaOrganizacional[] { area1, area2 }));
		cargoDao.save(cargo1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setEmpresa(empresa);
		cargo2.setGrupoOcupacional(grupo2);
		cargo2.setAreasOrganizacionais(Arrays.asList(new AreaOrganizacional[] { area1 }));
		cargoDao.save(cargo2);
		
		assertEquals(2, cargoDao.findByAreaGrupo(null, null, empresa.getId()).size());
		
		assertEquals(2, cargoDao.findByAreaGrupo(new Long[] { area1.getId() }, null, empresa.getId()).size());
		
		assertEquals(2, cargoDao.findByAreaGrupo(new Long[] { area1.getId(), area2.getId() }, null, empresa.getId()).size());
		
		assertEquals(1, cargoDao.findByAreaGrupo(new Long[] { area2.getId() }, null, empresa.getId()).size());
		
		assertEquals(1, cargoDao.findByAreaGrupo(new Long[] { area1.getId(), area2.getId() }, new Long[] { grupo1.getId() }, empresa.getId()).size());
		
		assertEquals(2, cargoDao.findByAreaGrupo(null, new Long[] { grupo1.getId(), grupo2.getId() }, empresa.getId()).size());
	}
	
	@Test
    public void testFindCargoByEmpresaEAreaComFlagExibirSomenteCargosVnnculadosAsAreasMarcadas(){
        Empresa empresa = EmpresaFactory.getEmpresa();
        empresaDao.save(empresa);
        
        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
        areaOrganizacionalDao.save(areaOrganizacional);
        
        Cargo cargoComAreaRelacionada = CargoFactory.getEntity("Cargo Vinculado a uma Area", empresa, Arrays.asList(areaOrganizacional));
        cargoDao.save(cargoComAreaRelacionada);
    
        Cargo cargoSemAreaRelacionada = CargoFactory.getEntity("Cargo Vinculado a uma Area", empresa, null);
        cargoDao.save(cargoSemAreaRelacionada);
        
        Collection<Cargo> cargosRetorno = cargoDao.findCargoByEmpresaEArea(new Long[]{empresa.getId()}, new Long[]{areaOrganizacional.getId()}, true);
        
        assertEquals(1, cargosRetorno.size());
        assertEquals(cargoComAreaRelacionada.getId(), cargosRetorno.iterator().next().getId());
    }
	
	@Test
    public void testFindCargoByEmpresaEAreaComFlagExibirSomenteCargosVnnculadosAsAreasMarcadasMasSemAreasSelecionadas(){
        Empresa empresa = EmpresaFactory.getEmpresa();
        empresaDao.save(empresa);
        
        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
        areaOrganizacionalDao.save(areaOrganizacional);
        
        Cargo cargoComAreaRelacionada = CargoFactory.getEntity("Cargo Vinculado a uma Area", empresa, Arrays.asList(areaOrganizacional));
        cargoDao.save(cargoComAreaRelacionada);
    
        Cargo cargoSemAreaRelacionada = CargoFactory.getEntity("Cargo Vinculado a uma Area", empresa, null);
        cargoDao.save(cargoSemAreaRelacionada);
        
        assertEquals(2,cargoDao.findCargoByEmpresaEArea(new Long[]{empresa.getId()}, new Long[]{}, true).size());
        assertEquals(2, cargoDao.findCargoByEmpresaEArea(new Long[]{empresa.getId()}, null, true).size());
    }
	 
	@Test
	public void testFindCargoByEmpresaEAreaFlagExibirSomenteCargosVnnculadosAsAreasMarcadasDesmarcadaESemAreasSelecionadas(){
        Empresa empresa = EmpresaFactory.getEmpresa();
        empresaDao.save(empresa);
        
        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
        areaOrganizacionalDao.save(areaOrganizacional);
        
        Cargo cargoComAreaRelacionada = CargoFactory.getEntity("Cargo Vinculado a uma Area", empresa, Arrays.asList(areaOrganizacional));
        cargoDao.save(cargoComAreaRelacionada);
        
        Cargo cargoSemAreaRelacionada = CargoFactory.getEntity("Cargo Vinculado a uma Area", empresa, null);
        cargoDao.save(cargoSemAreaRelacionada);
        
        assertEquals(2,cargoDao.findCargoByEmpresaEArea(new Long[]{empresa.getId()}, new Long[]{}, false).size());
        assertEquals(2, cargoDao.findCargoByEmpresaEArea(new Long[]{empresa.getId()}, null, false).size());
    }
	
	@Test
    public void testFindCargoByEmpresaEAreaFlagExibirSomenteCargosVnnculadosAsAreasMarcadasDesmarcadaComAreasSelecionadas(){
        Empresa empresa = EmpresaFactory.getEmpresa();
        empresaDao.save(empresa);
        
        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
        areaOrganizacionalDao.save(areaOrganizacional);
        
        Cargo cargoComAreaRelacionada = CargoFactory.getEntity("Cargo Vinculado a uma Area", empresa, Arrays.asList(areaOrganizacional));
        cargoDao.save(cargoComAreaRelacionada);
        
        Cargo cargoSemAreaRelacionada = CargoFactory.getEntity("Cargo Vinculado a uma Area", empresa, null);
        cargoDao.save(cargoSemAreaRelacionada);
        
        Collection<Cargo> cargosRetorno = cargoDao.findCargoByEmpresaEArea(new Long[]{empresa.getId()}, new Long[]{areaOrganizacional.getId()}, false);
        assertEquals(2, cargosRetorno.size());
    }
}