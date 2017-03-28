package com.fortes.rh.test.dao.hibernate.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.desenvolvimento.LntDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.DaoHibernateAnnotationTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;

public class AreaOrganizacionalDaoHibernateTest_JUnit4  extends DaoHibernateAnnotationTest
{
	@Autowired
	private AreaOrganizacionalDao areaOrganizacionalDao;
	@Autowired
	private CargoDao cargoDao;
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private ConhecimentoDao conhecimentoDao;
	@Autowired
	private GrupoACDao grupoACDao;
	@Autowired
	private ColaboradorDao colaboradorDao;
	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private EstabelecimentoDao estabelecimentoDao;
	@Autowired
	private HistoricoColaboradorDao historicoColaboradorDao;
	@Autowired
	private LntDao lntDao;

	@Test
	public void testGetAncestraisIds(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacionalAvo = AreaOrganizacionalFactory.getEntity(null, "Area Avó", true, empresa);
		areaOrganizacionalDao.save(areaOrganizacionalAvo);
		
		AreaOrganizacional areaOrganizacionalMae = AreaOrganizacionalFactory.getEntity(null, "Area Mãe", true, empresa);
		areaOrganizacionalMae.setAreaMae(areaOrganizacionalAvo);
		areaOrganizacionalDao.save(areaOrganizacionalMae);
		
		AreaOrganizacional areaOrganizacionalFilha = AreaOrganizacionalFactory.getEntity(null, "Area Filha", true, empresa);
		areaOrganizacionalFilha.setAreaMae(areaOrganizacionalMae);
		areaOrganizacionalDao.save(areaOrganizacionalFilha);
		
		AreaOrganizacional areaOrganizacionalMae2 = AreaOrganizacionalFactory.getEntity(null, "Area Mãe 2", true, empresa);
		areaOrganizacionalMae2.setAreaMae(areaOrganizacionalAvo);
		areaOrganizacionalDao.save(areaOrganizacionalMae2);
		
		AreaOrganizacional areaOrganizacionalFilha2 = AreaOrganizacionalFactory.getEntity(null, "Area Filha 2", true, empresa);
		areaOrganizacionalFilha2.setAreaMae(areaOrganizacionalMae2);
		areaOrganizacionalDao.save(areaOrganizacionalFilha2);
		
		Collection<Long> areasIds = areaOrganizacionalDao.getAncestraisIds(new Long[]{areaOrganizacionalFilha.getId(), areaOrganizacionalFilha2.getId()});
		
		assertEquals(5, areasIds.size());
	}
	
	@Test
	public void testGetDescendentesIds(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaAvo = AreaOrganizacionalFactory.getEntity(null, "Area Avó", true, empresa);
		areaOrganizacionalDao.save(areaAvo);
		
		AreaOrganizacional areaMae = AreaOrganizacionalFactory.getEntity(null, "Area Mãe", true, empresa);
		areaMae.setAreaMae(areaAvo);
		areaOrganizacionalDao.save(areaMae);
		
		AreaOrganizacional areaFilha = AreaOrganizacionalFactory.getEntity(null, "Area Filha", true, empresa);
		areaFilha.setAreaMae(areaMae);
		areaOrganizacionalDao.save(areaFilha);
		
		AreaOrganizacional areaMae2 = AreaOrganizacionalFactory.getEntity(null, "Area Mãe 2", true, empresa);
		areaMae2.setAreaMae(areaAvo);
		areaOrganizacionalDao.save(areaMae2);
		
		AreaOrganizacional areaFilha2 = AreaOrganizacionalFactory.getEntity(null, "Area Filha 2", true, empresa);
		areaFilha2.setAreaMae(areaMae2);
		areaOrganizacionalDao.save(areaFilha2);
		
		Collection<Long> areasIds = areaOrganizacionalDao.getDescendentesIds(new Long[]{areaAvo.getId()});
		
		assertEquals(5, areasIds.size());
	}
	
	@Test
	public void testIsResposnsavelOrCoResponsavelPorPropriaArea() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaFilha = AreaOrganizacionalFactory.getEntity(null, "Area Filha", true, empresa);
		areaOrganizacionalDao.save(areaFilha);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, new Date(), StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setAreaOrganizacional(areaFilha);
		historicoColaboradorDao.save(historicoColaborador);
		
		areaFilha.setResponsavel(colaborador);
		areaOrganizacionalDao.update(areaFilha);
		
		areaOrganizacionalDao.getHibernateTemplateByGenericDao().flush();
		
		assertTrue(areaOrganizacionalDao.isResposnsavelOrCoResponsavelPorPropriaArea(colaborador.getId(), AreaOrganizacional.RESPONSAVEL));
		assertFalse(areaOrganizacionalDao.isResposnsavelOrCoResponsavelPorPropriaArea(colaborador.getId(), AreaOrganizacional.CORRESPONSAVEL));
	}
}