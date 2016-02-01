package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoPraticaDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorAvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorCertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.util.DateUtil;

public class ColaboradorCertificacaoDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorCertificacao>
{
	private ColaboradorCertificacaoDao colaboradorCertificacaoDao;
	private ColaboradorDao colaboradorDao;
	private CertificacaoDao certificacaoDao;
	private CursoDao cursoDao;
	private ColaboradorTurmaDao colaboradorTurmaDao;
	private EmpresaDao empresaDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private CargoDao cargoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private TurmaDao turmaDao;
	private AvaliacaoPraticaDao avaliacaoPraticaDao;
	private ColaboradorAvaliacaoPraticaDao  colaboradorAvaliacaoPraticaDao;

	@Override
	public ColaboradorCertificacao getEntity()
	{
		return ColaboradorCertificacaoFactory.getEntity();
	}
	
	@Override
	public GenericDao<ColaboradorCertificacao> getGenericDao()
	{
		return colaboradorCertificacaoDao;
	}
	
	public void testFindByColaboradorIdAndCertificacaoId()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Colaborador colaboradorNaoCertificado = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaboradorNaoCertificado);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacaoDao.save(certificacao);
		
		ColaboradorCertificacao colaboradorCertificacao = new ColaboradorCertificacao();
		colaboradorCertificacao.setColaborador(colaborador);
		colaboradorCertificacao.setCertificacao(certificacao);
		colaboradorCertificacao.setData(DateUtil.criarDataMesAno(1, 1, 2015));
		colaboradorCertificacaoDao.save(colaboradorCertificacao);
		
		ColaboradorCertificacao colaboradorCertificacao2 = new ColaboradorCertificacao();
		colaboradorCertificacao2.setColaborador(colaborador);
		colaboradorCertificacao2.setCertificacao(certificacao);
		colaboradorCertificacao2.setData(DateUtil.criarDataMesAno(1, 2, 2015));
		colaboradorCertificacaoDao.save(colaboradorCertificacao2);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = colaboradorCertificacaoDao.findByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId());
		Collection<ColaboradorCertificacao> colaboradorNaoCertificados = colaboradorCertificacaoDao.findByColaboradorIdAndCertificacaoId(colaboradorNaoCertificado.getId(), certificacao.getId());
		
		assertEquals(2, colaboradorCertificacaos.size());
		assertEquals(0, colaboradorNaoCertificados.size());
	}
	
	
	public void testFindUltimaCertificacaoByColaboradorIdAndCertificacaoId()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Colaborador colaboradorNaoCertificado = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaboradorNaoCertificado);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacaoDao.save(certificacao);
		
		ColaboradorCertificacao colaboradorCertificacao = new ColaboradorCertificacao();
		colaboradorCertificacao.setColaborador(colaborador);
		colaboradorCertificacao.setCertificacao(certificacao);
		colaboradorCertificacao.setData(DateUtil.criarDataMesAno(1, 1, 2015));
		colaboradorCertificacaoDao.save(colaboradorCertificacao);
		
		ColaboradorCertificacao colaboradorCertificacao2 = new ColaboradorCertificacao();
		colaboradorCertificacao2.setColaborador(colaborador);
		colaboradorCertificacao2.setCertificacao(certificacao);
		colaboradorCertificacao2.setData(DateUtil.criarDataMesAno(1, 2, 2015));
		colaboradorCertificacaoDao.save(colaboradorCertificacao2);
		
		ColaboradorCertificacao colaboradorCertificacaoRetorno = colaboradorCertificacaoDao.findUltimaCertificacaoByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId());
		
		assertNotNull(colaboradorCertificacaoRetorno);
		assertEquals("01/02/2015", DateUtil.formataDiaMesAno(colaboradorCertificacaoRetorno.getData()));
	}
	
	public void testCertificacoesByColaboradorTurmaId()
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setRealizada(true);
		turma.setCurso(curso);
		turma.setDataPrevFim(DateUtil.criarDataMesAno(1, 1, 2015));
		turmaDao.save(turma);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setCurso(curso);
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setTurma(turma);
		colaboradorTurmaDao.save(colaboradorTurma);
		
		Collection<Curso> cursos = new ArrayList<Curso>();
		cursos.add(curso);
		
		Certificacao certificacaoPre = CertificacaoFactory.getEntity();
		certificacaoDao.save(certificacaoPre);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacao.setCursos(cursos);
		certificacao.setCertificacaoPreRequisito(certificacaoPre);
		certificacaoDao.save(certificacao);
		
		Certificacao certificacao2 = CertificacaoFactory.getEntity();
		certificacao2.setCursos(cursos);
		certificacao2.setCertificacaoPreRequisito(certificacaoPre);
		certificacaoDao.save(certificacao2);
		
		colaboradorCertificacaoDao.getHibernateTemplateByGenericDao().flush();
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = colaboradorCertificacaoDao.colaboradoresCertificadosByColaboradorTurmaId(colaboradorTurma.getId());
		
		assertEquals(2, colaboradorCertificacaos.size());
	}
	
	public void testColaboradoresCertificadosByColaboradorIdAndCertificacaId()
	{
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Curso curso2 = CursoFactory.getEntity();
		cursoDao.save(curso2);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setRealizada(true);
		turma.setCurso(curso);
		turma.setDataPrevFim(DateUtil.criarDataMesAno(1, 1, 2015));
		turmaDao.save(turma);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setRealizada(true);
		turma2.setCurso(curso2);
		turma2.setDataPrevFim(DateUtil.criarDataMesAno(1, 1, 2015));
		turmaDao.save(turma2);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setCurso(curso);
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setTurma(turma);
		colaboradorTurmaDao.save(colaboradorTurma);
		
		ColaboradorTurma colaboradorTurma2 = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma2.setCurso(curso2);
		colaboradorTurma2.setColaborador(colaborador);
		colaboradorTurma2.setTurma(turma2);
		colaboradorTurmaDao.save(colaboradorTurma2);
		
		Collection<Curso> cursos = new ArrayList<Curso>();
		cursos.add(curso);
		
		Certificacao certificacaoPre = CertificacaoFactory.getEntity();
		certificacaoDao.save(certificacaoPre);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacao.setCursos(cursos);
		certificacao.setCertificacaoPreRequisito(certificacaoPre);
		certificacaoDao.save(certificacao);
		
		colaboradorCertificacaoDao.getHibernateTemplateByGenericDao().flush();
		
		ColaboradorCertificacao colaboradorCertificacaos = colaboradorCertificacaoDao.colaboradorCertificadoByColaboradorIdAndCertificacaId(colaborador.getId(), certificacao.getId());
		
		assertNotNull(colaboradorCertificacaos);
	}
	
	public void testGetCertificacoesAVencer()
	{
		Date data = DateUtil.criarDataMesAno(1, 1, 2015);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Feião");
		colaborador.setEmpresa(empresa);
		colaborador.setEmailColaborador("bla@ble.com");
		colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2001));
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);

		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacao.setEmpresa(empresa);
		certificacao.setPeriodicidade(1);
		certificacaoDao.save(certificacao);
		
		ColaboradorCertificacao colaboradorCertificacao = new ColaboradorCertificacao();
		colaboradorCertificacao.setColaborador(colaborador);
		colaboradorCertificacao.setCertificacao(certificacao);
		colaboradorCertificacao.setData(data);
		colaboradorCertificacaoDao.save(colaboradorCertificacao);
		
		colaboradorCertificacaoDao.getHibernateTemplateByGenericDao().flush();
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = colaboradorCertificacaoDao.getCertificacoesAVencer(DateUtil.incrementaMes(data, 1), empresa.getId());
		
		assertEquals(1, colaboradorCertificacaos.size());
	}
	
	public void testRemoveDependencias(){
		Date data = DateUtil.criarDataMesAno(1, 1, 2015);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Feião");
		colaborador.setEmpresa(empresa);
		colaborador.setEmailColaborador("bla@ble.com");
		colaboradorDao.save(colaborador);

		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacao.setEmpresa(empresa);
		certificacao.setPeriodicidade(1);
		certificacaoDao.save(certificacao);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity();
		avaliacaoPraticaDao.save(avaliacaoPratica);

		ColaboradorCertificacao colaboradorCertificacao = new ColaboradorCertificacao();
		colaboradorCertificacao.setColaborador(colaborador);
		colaboradorCertificacao.setCertificacao(certificacao);
		colaboradorCertificacao.setData(data);
		colaboradorCertificacaoDao.save(colaboradorCertificacao);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);
		colaboradorAvaliacaoPratica.setColaborador(colaborador);
		colaboradorAvaliacaoPratica.setCertificacao(certificacao);
		colaboradorAvaliacaoPratica.setColaboradorCertificacao(colaboradorCertificacao);
		colaboradorAvaliacaoPratica.setData(data);
		colaboradorAvaliacaoPratica.setNota(10.0);
		colaboradorAvaliacaoPraticaDao.save(colaboradorAvaliacaoPratica);
		
		colaboradorCertificacaoDao.getHibernateTemplateByGenericDao().flush();
		
		colaboradorCertificacaoDao.removeDependencias(colaboradorCertificacao.getId());
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPraticas = colaboradorAvaliacaoPraticaDao.findColaboradorAvaliacaoPraticaQueNaoEstaCertificado(colaborador.getId(), certificacao.getId());
		assertNull(((ColaboradorAvaliacaoPratica) colaboradorAvaliacoesPraticas.toArray()[0]).getColaboradorCertificacao());
	}
	
	public void testFindByColaboradorTurma(){
		Date data = DateUtil.criarDataMesAno(1, 1, 2015);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Feião");
		colaborador.setEmpresa(empresa);
		colaborador.setEmailColaborador("bla@ble.com");
		colaboradorDao.save(colaborador);

		Curso curso = CursoFactory.getEntity();
		curso.setEmpresa(empresa);
		cursoDao.save(curso);
		
		Collection<Curso> cursos = Arrays.asList(curso);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacao.setEmpresa(empresa);
		certificacao.setPeriodicidade(1);
		certificacao.setCursos(cursos);
		certificacaoDao.save(certificacao);

		Turma turma = TurmaFactory.getEntity();
		turma.setCurso(curso);
		turmaDao.save(turma);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setCurso(curso);
		colaboradorTurma.setTurma(turma);
		colaboradorTurmaDao.save(colaboradorTurma);
		
		AvaliacaoPratica avaliacaoPratica = AvaliacaoPraticaFactory.getEntity();
		avaliacaoPraticaDao.save(avaliacaoPratica);

		Collection<ColaboradorTurma> colaboradorTurmas = Arrays.asList(colaboradorTurma);
		
		ColaboradorCertificacao colaboradorCertificacao = new ColaboradorCertificacao();
		colaboradorCertificacao.setColaborador(colaborador);
		colaboradorCertificacao.setCertificacao(certificacao);
		colaboradorCertificacao.setData(data);
		colaboradorCertificacao.setColaboradoresTurmas(colaboradorTurmas);
		colaboradorCertificacaoDao.save(colaboradorCertificacao);
				
		colaboradorCertificacaoDao.getHibernateTemplateByGenericDao().flush();
		
		assertEquals(colaboradorCertificacao.getId(), colaboradorCertificacaoDao.findByColaboradorTurma(colaboradorTurma.getId()).getId());
	}
	
	public void testColaboradoresQueParticipaDoCertificado()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional2);

		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador1);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador1);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2001));
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador2);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(01, 01, 2001));
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional2);
		historicoColaborador2.setFaixaSalarial(faixaSalarial);
		historicoColaborador2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		Curso curso = CursoFactory.getEntity();
		curso.setEmpresa(empresa);
		cursoDao.save(curso);
		
		Collection<Curso> cursos = Arrays.asList(curso);
		
		Certificacao certificacao = CertificacaoFactory.getEntity();
		certificacao.setEmpresa(empresa);
		certificacao.setPeriodicidade(1);
		certificacao.setCursos(cursos);
		certificacaoDao.save(certificacao);

		Turma turma = TurmaFactory.getEntity();
		turma.setCurso(curso);
		turmaDao.save(turma);
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma.setColaborador(colaborador1);
		colaboradorTurma.setCurso(curso);
		colaboradorTurma.setTurma(turma);
		colaboradorTurmaDao.save(colaboradorTurma);
		
		ColaboradorTurma colaboradorTurma2 = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma2.setColaborador(colaborador2);
		colaboradorTurma2.setCurso(curso);
		colaboradorTurma2.setTurma(turma);
		colaboradorTurmaDao.save(colaboradorTurma2);
		
		colaboradorCertificacaoDao.getHibernateTemplateByGenericDao().flush();
		
		assertEquals(2, colaboradorCertificacaoDao.colaboradoresQueParticipaDoCertificado(new Long[]{areaOrganizacional.getId(), areaOrganizacional2.getId()}, null, certificacao.getId()).size());
		assertEquals(1, colaboradorCertificacaoDao.colaboradoresQueParticipaDoCertificado(new Long[]{areaOrganizacional.getId()}, null, certificacao.getId()).size());
	}

	public void setColaboradorCertificacaoDao(ColaboradorCertificacaoDao colaboradorCertificacaoDao)
	{
		this.colaboradorCertificacaoDao = colaboradorCertificacaoDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}


	public void setCertificacaoDao(CertificacaoDao certificacaoDao) {
		this.certificacaoDao = certificacaoDao;
	}

	public void setCursoDao(CursoDao cursoDao) {
		this.cursoDao = cursoDao;
	}

	public void setColaboradorTurmaDao(ColaboradorTurmaDao colaboradorTurmaDao) {
		this.colaboradorTurmaDao = colaboradorTurmaDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao) {
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setCargoDao(CargoDao cargoDao) {
		this.cargoDao = cargoDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao) {
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setHistoricoColaboradorDao(
			HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setTurmaDao(TurmaDao turmaDao) {
		this.turmaDao = turmaDao;
	}

	public void setAvaliacaoPraticaDao(AvaliacaoPraticaDao avaliacaoPraticaDao) {
		this.avaliacaoPraticaDao = avaliacaoPraticaDao;
	}

	public void setColaboradorAvaliacaoPraticaDao(
			ColaboradorAvaliacaoPraticaDao colaboradorAvaliacaoPraticaDao) {
		this.colaboradorAvaliacaoPraticaDao = colaboradorAvaliacaoPraticaDao;
	}
}
