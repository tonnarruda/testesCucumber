package com.fortes.rh.test.business.avaliacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.BaseDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.util.DateUtil;

public class PeriodoDeAcompanhamentoDeExperiencia extends BaseDaoHibernateTest {

	private EmpresaDao empresaDao;
	private ColaboradorDao colaboradorDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private Integer dias; 
	private Integer periodoAnterior;
	private Empresa empresa ;
	private String[] areasCheck;
	private String[] estabelecimentoCheck;
	private Colaborador colaborador;
	private HistoricoColaborador historicoColaborador;
	private AreaOrganizacional areaOrganizacional;

	//Todos estabelecimentos e áreas organizacionais sem Periodo 
	public void testfindAdmitidosNoPeriodoSemPreenchimento()
	{
		periodoAnterior = new Integer(0);
		dias = new Integer(360);

		empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		areasCheck  = null;
		estabelecimentoCheck = null;
				
		colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Bruno");
		colaborador.setEmpresa(empresa);
		colaborador.setDataAdmissao(DateUtil.montaDataByString("18/12/2009"));
		colaboradorDao.save(colaborador);
		
		areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setNome("Administrativo");
		areaOrganizacionalDao.save(areaOrganizacional);
		
		historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setData(DateUtil.montaDataByString("18/12/2009"));
		historicoColaboradorDao.save(historicoColaborador);
		
//		Collection<Colaborador> colaboradoresResultado = colaboradorDao.findAdmitidosNoPeriodo(dias,
//				empresa, periodoAnterior, areasCheck, estabelecimentoCheck);
//	
//		assertTrue("O colaborador Bruno deve esta na listagem", contem(colaboradoresResultado, "Bruno"));
		}
	
	
	//esse teste usa como menor periodo 30 dias
	public void testfindMenorPeriodo()
	{
		dias = new Integer(30);
		periodoAnterior = new Integer(0);
		
		empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		areasCheck = null;
		estabelecimentoCheck = null;
		
		
		colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Bruno");
		colaborador.setEmpresa(empresa);
		colaborador.setDataAdmissao(DateUtil.montaDataByString("18/02/2010"));
		colaboradorDao.save(colaborador);
		
		Colaborador colaborador2;
		colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNome("Milfont");
		colaborador2.setEmpresa(empresa);
		colaborador2.setDataAdmissao(DateUtil.montaDataByString("18/02/2010"));
		colaboradorDao.save(colaborador2);
		
		Colaborador colaborador3;
		colaborador3 = ColaboradorFactory.getEntity();
		colaborador3.setNome("Rafael");
		colaborador3.setEmpresa(empresa);
		colaborador3.setDataAdmissao(DateUtil.montaDataByString("18/02/2010"));
		colaboradorDao.save(colaborador2);
		
		areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		
		historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(DateUtil.montaDataByString("18/02/2010"));
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		
	}
	
	
	private Boolean contem(Collection<Colaborador> colaboradores, String nome)
	{
		Boolean encontrado = false;
		for (Colaborador colaborador : colaboradores)
		{
			if(colaborador.getNome().equals(nome))
				encontrado = true;
		}
		return encontrado;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao) {
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	
}
