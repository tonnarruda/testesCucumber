package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.geral.AreaInteresseDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.geral.AreaInteresseFactory;

@SuppressWarnings("unused")
public class AreaOrganizacionalDaoHibernateTest extends GenericDaoHibernateTest<AreaOrganizacional>
{
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private CargoDao cargoDao;
	private AreaInteresseDao areaInteresseDao;
	private EmpresaDao empresaDao;
	private ConhecimentoDao conhecimentoDao;
	private GrupoACDao grupoACDao;
	private ColaboradorDao colaboradorDao;
	private UsuarioDao usuarioDao;

	public AreaOrganizacional getEntity()
	{
		AreaOrganizacional areaOrganizacional = new AreaOrganizacional();

		areaOrganizacional.setId(null);
		areaOrganizacional.setAreaMae(null);
		areaOrganizacional.setAreasInteresse(null);
		areaOrganizacional.setCodigoAC("0");
		areaOrganizacional.setConhecimentos(null);
		areaOrganizacional.setDescricao("descrição");
		areaOrganizacional.setNome("nome da area organizacional");

		return areaOrganizacional;
	}

	public GenericDao<AreaOrganizacional> getGenericDao()
	{
		return areaOrganizacionalDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void testFindAreaOrganizacionalCodigoAc()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setCodigoAC("001");
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		AreaOrganizacional retorno = areaOrganizacionalDao.findAreaOrganizacionalCodigoAc(areaOrganizacional.getId());

		assertEquals(areaOrganizacional.getCodigoAC(), retorno.getCodigoAC());
	}
	
	public void testFindCodigoACDuplicado()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("24342333");
		empresaDao.save(empresa);
		
		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity();
		area1.setCodigoAC("001");
		area1.setEmpresa(empresa);
		areaOrganizacionalDao.save(area1);
		
		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity();
		area2.setCodigoAC("001");
		area2.setEmpresa(empresa);
		areaOrganizacionalDao.save(area2);
		
		AreaOrganizacional area3 = AreaOrganizacionalFactory.getEntity();
		area3.setCodigoAC("003");
		area3.setEmpresa(empresa);
		areaOrganizacionalDao.save(area3);
		
		AreaOrganizacional area4 = AreaOrganizacionalFactory.getEntity();
		area4.setCodigoAC("003");
		area4.setEmpresa(empresa);
		areaOrganizacionalDao.save(area4);
		
		AreaOrganizacional area5 = AreaOrganizacionalFactory.getEntity();
		area5.setCodigoAC("005");
		area5.setEmpresa(empresa);
		areaOrganizacionalDao.save(area5);
		
		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		outraEmpresa.setCodigoAC("24342333");
		empresaDao.save(outraEmpresa);
		
		AreaOrganizacional areaOutraEmpresa = AreaOrganizacionalFactory.getEntity();
		areaOutraEmpresa.setCodigoAC("005");
		areaOutraEmpresa.setEmpresa(outraEmpresa);
		areaOrganizacionalDao.save(areaOutraEmpresa);
		
		assertEquals("001,003", areaOrganizacionalDao.findCodigoACDuplicado(empresa.getId()));
	}
	
	public void testFindCodigoACDuplicadoVazio()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("24342333");
		empresaDao.save(empresa);
		
		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity();
		area1.setCodigoAC("001");
		area1.setEmpresa(empresa);
		areaOrganizacionalDao.save(area1);
		
		assertEquals("", areaOrganizacionalDao.findCodigoACDuplicado(empresa.getId()));
	}

	public void testGetCount()
	{
		Collection<AreaOrganizacional> lista = areaOrganizacionalDao.find(new String[]{"empresa.id"}, new Object[] {1L});
		int result = areaOrganizacionalDao.getCount("", 1L);

		assertEquals(result, lista.size());
	}

	public void testFindByCargo()
	{
		Cargo cargo = CargoFactory.getEntity();

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional1);
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional2);

		Collection<AreaOrganizacional> areasOrganizacionais = new ArrayList<AreaOrganizacional>();
		areasOrganizacionais.add(areaOrganizacional1);
		areasOrganizacionais.add(areaOrganizacional2);

		cargo.setAreasOrganizacionais(areasOrganizacionais);
		cargo = cargoDao.save(cargo);

		Collection<AreaOrganizacional> areas = areaOrganizacionalDao.findByCargo(cargo.getId());
		assertEquals(2, areas.size());
	}

	public void testVerificaMaternidade()
	{
		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacionalMae);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setAreaMae(areaOrganizacionalMae);
		areaOrganizacionalDao.save(areaOrganizacional);

		assertEquals(true, areaOrganizacionalDao.verificaMaternidade(areaOrganizacionalMae.getId(), null));
		assertEquals(false, areaOrganizacionalDao.verificaMaternidade(areaOrganizacional.getId(), null));
	}

	public void testFindByIdProjection()
	{
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);

		assertEquals(areaOrganizacional, areaOrganizacionalDao.findByIdProjection(areaOrganizacional.getId()));
	}

	public void testFindAreaIdsByAreaInteresse()
	{
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional1);

		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional2);

		Collection<AreaOrganizacional> areasOrganizacionais =  new ArrayList<AreaOrganizacional>();
		areasOrganizacionais.add(areaOrganizacional1);
		areasOrganizacionais.add(areaOrganizacional2);

		AreaInteresse areaInteresse = AreaInteresseFactory.getAreaInteresse();
		areaInteresse.setAreasOrganizacionais(areasOrganizacionais);
		areaInteresse = areaInteresseDao.save(areaInteresse);

		Collection<AreaOrganizacional> areasRetorno = areaOrganizacionalDao.findAreaIdsByAreaInteresse(areaInteresse.getId());

		assertEquals(2, areasRetorno.size());
	}
	
	public void testFindAreaOrganizacionalByCodigoAc()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("24342333");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresa = empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setCodigoAC("010203");
		areaOrganizacional.setEmpresa(empresa);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		AreaOrganizacional areaOrganizacionalRetorno = areaOrganizacionalDao.findAreaOrganizacionalByCodigoAc(areaOrganizacional.getCodigoAC(), empresa.getCodigoAC(), "XXX");
		
		assertEquals(areaOrganizacional, areaOrganizacionalRetorno);
	}

	public void testFindSemCodigoAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("24342333");
		empresa = empresaDao.save(empresa);

		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity();
		area1.setEmpresa(empresa);
		area1.setCodigoAC(null);
		areaOrganizacionalDao.save(area1);
		
		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity();
		area2.setEmpresa(empresa);
		area2.setCodigoAC("df3r433");
		areaOrganizacionalDao.save(area2);
		
		AreaOrganizacional area3 = AreaOrganizacionalFactory.getEntity();
		area3.setEmpresa(empresa);
		area3.setCodigoAC("");
		areaOrganizacionalDao.save(area3);

		assertEquals(2, areaOrganizacionalDao.findSemCodigoAC(empresa.getId()).size());
	}

	public void testFindAllList()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setCodigoAC("010203");
		areaOrganizacional.setEmpresa(empresa);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();

		areaOrganizacionals = areaOrganizacionalDao.findAllList(0, 0, null, null, empresa.getId(), AreaOrganizacional.TODAS, null);

		assertEquals(1, areaOrganizacionals.size());
	}

	public void testFindIdMaeById()
	{
		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalMae = areaOrganizacionalDao.save(areaOrganizacionalMae);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setAreaMae(areaOrganizacionalMae);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		AreaOrganizacional retorno = areaOrganizacionalDao.findIdMaeById(areaOrganizacional.getId());

		assertEquals(areaOrganizacionalMae.getId(), retorno.getAreaMae().getId());
	}

	public void testFindByConhecimento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1.setEmpresa(empresa);
		areaOrganizacional1 = areaOrganizacionalDao.save(areaOrganizacional1);

		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setEmpresa(empresa);
		areaOrganizacional2 = areaOrganizacionalDao.save(areaOrganizacional2);

		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		areaOrganizacionals.add(areaOrganizacional1);
		areaOrganizacionals.add(areaOrganizacional2);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setAreaOrganizacionals(areaOrganizacionals);
		conhecimento = conhecimentoDao.save(conhecimento);

		assertEquals(2, areaOrganizacionalDao.findByConhecimento(conhecimento.getId()).size());
	}
	
	public void testFindQtdColaboradorPorArea()
	{
		Collection<AreaOrganizacional> colabAreaCount = areaOrganizacionalDao.findQtdColaboradorPorArea(4L, new Date());
	}
	
	public void testFindByEmpresasIds()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);

		Long[] empresaIds = new Long[]{empresa.getId(),empresa2.getId()};

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setAtivo(true);
		areaOrganizacional.setEmpresa(empresa);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setAtivo(true);
		areaOrganizacional2.setEmpresa(empresa2);
		areaOrganizacional2 = areaOrganizacionalDao.save(areaOrganizacional2);
		
		AreaOrganizacional areaOrganizacionalFora = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalFora.setAtivo(false);
		areaOrganizacionalFora.setEmpresa(empresa);
		areaOrganizacionalFora = areaOrganizacionalDao.save(areaOrganizacionalFora);

		Collection<AreaOrganizacional> areaOrganizacionals = null;

		areaOrganizacionals = areaOrganizacionalDao.findByEmpresasIds(empresaIds, AreaOrganizacional.ATIVA);

		assertEquals(2, areaOrganizacionals.size());
	}
	
	public void testFindByEmpresaId()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setEmpresa(empresa);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setEmpresa(empresa2);
		areaOrganizacional2 = areaOrganizacionalDao.save(areaOrganizacional2);
		
		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalDao.findByEmpresa(empresa.getId());
		
		assertEquals(1, areaOrganizacionals.size());
	}
	
	public void testFindSincronizar()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setAtivo(true);
		areaOrganizacional.setEmpresa(empresa);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		Collection<AreaOrganizacional> areas = areaOrganizacionalDao.findSincronizarAreas(empresa.getId());
		
		assertEquals(1, areas.size());
	}
	
	public void testFindIdsAreasDoResponsavel()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setUsuario(usuario);
		colaboradorDao.save(colaborador);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setEmpresa(empresa);
		areaOrganizacional.setResponsavel(colaborador);
		areaOrganizacional.setCoResponsavel(colaborador);
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Exception e = null;
		try {
			areaOrganizacionalDao.findIdsAreasDoResponsavelCoResponsavel(usuario.getId(), empresa.getId());
		} catch (Exception e2) {
			e = e2;
		}
		assertNull(e);
	}
	
	public void testFindIdsAreasFilhas()
	{
		AreaOrganizacional areaAvo = AreaOrganizacionalFactory.getEntity();
		areaAvo.setNome("areaAvo");
		areaOrganizacionalDao.save(areaAvo);
		
		AreaOrganizacional areaMae1 = AreaOrganizacionalFactory.getEntity();
		areaMae1.setNome("areaMae1");
		areaMae1.setAreaMae(areaAvo);
		areaOrganizacionalDao.save(areaMae1);
		
		AreaOrganizacional areaMae2 = AreaOrganizacionalFactory.getEntity();
		areaMae2.setNome("areaMae2");
		areaMae2.setAreaMae(areaAvo);
		areaOrganizacionalDao.save(areaMae2);
		
		AreaOrganizacional areaFilha1 = AreaOrganizacionalFactory.getEntity();
		areaFilha1.setNome("areaFilha1");
		areaFilha1.setAreaMae(areaMae1);
		areaOrganizacionalDao.save(areaFilha1);
		
		AreaOrganizacional areaFilha2 = AreaOrganizacionalFactory.getEntity();
		areaFilha2.setNome("areaFilha2");
		areaFilha2.setAreaMae(areaMae2);
		areaOrganizacionalDao.save(areaFilha2);
		
		Collection<Long> resultadoAreaAvo = Arrays.asList(areaMae1.getId(), areaMae2.getId());
		assertEquals(resultadoAreaAvo, areaOrganizacionalDao.findIdsAreasFilhas(Arrays.asList( areaAvo.getId())));

		Collection<Long> resultadoAreaMae1 = Arrays.asList(areaFilha1.getId());
		assertEquals(resultadoAreaMae1, areaOrganizacionalDao.findIdsAreasFilhas(Arrays.asList( areaMae1.getId() )));
		
		Collection<Long> resultadoAreaMae2 = Arrays.asList(areaFilha2.getId());
		assertEquals(resultadoAreaMae2, areaOrganizacionalDao.findIdsAreasFilhas(Arrays.asList( areaMae2.getId() )));
		
		Collection<Long> resultadoAreaFilha1 = new ArrayList<Long>();
		assertEquals(resultadoAreaFilha1, areaOrganizacionalDao.findIdsAreasFilhas(Arrays.asList( areaFilha1.getId() )));
	}

	public void testDesvinculaResponsavel()
	{
		Colaborador colaboradorResponsavel = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaboradorResponsavel);
		
		Colaborador colaboradorCoResponsavel = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaboradorCoResponsavel);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1.setEmpresa(empresa);
		areaOrganizacional1.setResponsavel(colaboradorResponsavel);
		areaOrganizacional1.setCoResponsavel(colaboradorCoResponsavel);
		areaOrganizacionalDao.save(areaOrganizacional1);
		
		assertNotNull(areaOrganizacionalDao.findByIdProjection(areaOrganizacional1.getId()).getResponsavel().getId());
		assertNotNull(areaOrganizacionalDao.findByIdProjection(areaOrganizacional1.getId()).getCoResponsavel().getId());
		
		areaOrganizacionalDao.desvinculaResponsavel(colaboradorResponsavel.getId());
		areaOrganizacionalDao.desvinculaCoResponsavel(colaboradorCoResponsavel.getId());
		
		assertNull(areaOrganizacionalDao.findByIdProjection(areaOrganizacional1.getId()).getResponsavel().getId());
		assertNull(areaOrganizacionalDao.findByIdProjection(areaOrganizacional1.getId()).getCoResponsavel().getId());
		
	}
	
	public void setCargoDao(CargoDao cargoDao)
	{
		this.cargoDao = cargoDao;
	}

	public void setAreaInteresseDao(AreaInteresseDao areaInteresseDao)
	{
		this.areaInteresseDao = areaInteresseDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setConhecimentoDao(ConhecimentoDao conhecimentoDao)
	{
		this.conhecimentoDao = conhecimentoDao;
	}

	public void setGrupoACDao(GrupoACDao grupoACDao) {
		this.grupoACDao = grupoACDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
}