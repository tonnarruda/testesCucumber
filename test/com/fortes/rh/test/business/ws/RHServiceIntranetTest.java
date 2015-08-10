package com.fortes.rh.test.business.ws;

import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.ws.RHServiceIntranetImpl;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.ws.UnidadeIntranet;
import com.fortes.rh.model.ws.UsuarioIntranet;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;

public class RHServiceIntranetTest extends MockObjectTestCase
{
	private RHServiceIntranetImpl rHServiceIntranetImpl = new RHServiceIntranetImpl();
	private Mock colaboradorManager;
	private Mock areaOrganizacionalManager;
	private Mock estabelecimentoManager;

	protected void setUp() throws Exception
	{
		super.setUp();

		colaboradorManager = new Mock(ColaboradorManager.class);
		rHServiceIntranetImpl.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		rHServiceIntranetImpl.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		rHServiceIntranetImpl.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
	}
	
	public void testAtualizaUsuarios() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		
		Cargo cargo = CargoFactory.getEntity(1L);
		cargo.setNome("Cargo I");
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		colaborador1.setEmpresa(empresa);
		colaborador1.setAreaOrganizacional(areaOrganizacional);
		colaborador1.setCargoIdProjection(cargo.getId());
		colaborador1.setCargoNomeProjection(cargo.getNome());
		colaborador1.setEstabelecimento(estabelecimento);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		colaborador2.setEmpresa(empresa);
		colaborador2.setAreaOrganizacional(areaOrganizacional);
		colaborador2.setCargoIdProjection(cargo.getId());
		colaborador2.setCargoNomeProjection(cargo.getNome());
		colaborador2.setEstabelecimento(estabelecimento);
		
		colaboradorManager.expects(once()).method("findByEmpresaAndStatusAC").with(new Constraint[]{eq(empresa.getId()),eq(null),eq(null),eq(StatusRetornoAC.CONFIRMADO),eq(false), eq(false),eq(true), ANYTHING}).will(returnValue(Arrays.asList(colaborador1, colaborador2)));
		
		Collection<UsuarioIntranet> usuarioIntranets = rHServiceIntranetImpl.usuariosIntranetList(empresa.getId().toString());
		
		assertEquals(2 ,usuarioIntranets.size());
	}
	
	public void testUnidadesIntranetList() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity(1L);
		estabelecimento1.setCidadeNome("Bostaleza");
		estabelecimento1.setUfSigla("CE");
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity(2L);
		estabelecimento2.setCidadeNome("Caucaia");
		estabelecimento2.setUfSigla("Inferno");
		
		estabelecimentoManager.expects(once()).method("findByEmpresa").with(eq(empresa.getId())).will(returnValue(Arrays.asList(estabelecimento1, estabelecimento2)));
		
		Collection<UnidadeIntranet> unidadesIntranet =  rHServiceIntranetImpl.unidadesIntranetList(empresa.getId().toString());
		
		assertEquals(2 ,unidadesIntranet.size());
	}
}
