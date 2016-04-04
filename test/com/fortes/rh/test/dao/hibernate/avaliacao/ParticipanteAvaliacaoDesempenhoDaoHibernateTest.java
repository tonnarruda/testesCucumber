package com.fortes.rh.test.dao.hibernate.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.avaliacao.ParticipanteAvaliacaoDesempenhoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoParticipanteAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;

public class ParticipanteAvaliacaoDesempenhoDaoHibernateTest extends GenericDaoHibernateTest<ParticipanteAvaliacaoDesempenho>
{
	private ParticipanteAvaliacaoDesempenhoDao participanteAvaliacaoDesempenhoDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private ColaboradorDao colaboradorDao;
	private EmpresaDao empresaDao;
	private CargoDao cargoDao;

	@Override
	public ParticipanteAvaliacaoDesempenho getEntity()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 02, 2001));
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador);

		ParticipanteAvaliacaoDesempenho participanteAvaliacaoDesempenho = new ParticipanteAvaliacaoDesempenho();
		participanteAvaliacaoDesempenho.setAvaliacaoDesempenho(avaliacaoDesempenho);
		participanteAvaliacaoDesempenho.setTipo(TipoParticipanteAvaliacao.AVALIADO);
		participanteAvaliacaoDesempenho.setColaborador(colaborador);
		participanteAvaliacaoDesempenho.setId(null);
		
		return participanteAvaliacaoDesempenho;
	}

	@Override
	public GenericDao<ParticipanteAvaliacaoDesempenho> getGenericDao()
	{
		return participanteAvaliacaoDesempenhoDao;
	}

	public void setParticipanteAvaliacaoDesempenhoDao(ParticipanteAvaliacaoDesempenhoDao participanteAvaliacaoDesempenhoDao)
	{
		this.participanteAvaliacaoDesempenhoDao = participanteAvaliacaoDesempenhoDao;
	}
	
	public void testFindParticipantes()
	{
		ParticipanteAvaliacaoDesempenho participanteAvaliacaoDesempenho = getEntity();
		participanteAvaliacaoDesempenhoDao.save(participanteAvaliacaoDesempenho);
		
		participanteAvaliacaoDesempenhoDao.getHibernateTemplateByGenericDao().flush();
		
		assertEquals(1, participanteAvaliacaoDesempenhoDao.findColaboradoresParticipantes(participanteAvaliacaoDesempenho.getAvaliacaoDesempenho().getId(), TipoParticipanteAvaliacao.AVALIADO).size());
	}
	
	public void testFindFaixasSalariaisDosAvaliadosByAvaliacaoDesempenho()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		ParticipanteAvaliacaoDesempenho participanteAvaliacaoDesempenho = getEntity();
		participanteAvaliacaoDesempenho.setAvaliacaoDesempenho(avaliacaoDesempenho);
		participanteAvaliacaoDesempenhoDao.save(participanteAvaliacaoDesempenho);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 02, 2001));
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador);
		
		ParticipanteAvaliacaoDesempenho participanteAvaliacaoDesempenho2 = new ParticipanteAvaliacaoDesempenho();
		participanteAvaliacaoDesempenho2.setAvaliacaoDesempenho(avaliacaoDesempenho);
		participanteAvaliacaoDesempenho2.setColaborador(colaborador);
		participanteAvaliacaoDesempenho2.setTipo(TipoParticipanteAvaliacao.AVALIADO);
		participanteAvaliacaoDesempenhoDao.save(participanteAvaliacaoDesempenho2);
		
		participanteAvaliacaoDesempenhoDao.getHibernateTemplateByGenericDao().flush();
		assertEquals(2, participanteAvaliacaoDesempenhoDao.findFaixasSalariaisDosAvaliadosByAvaliacaoDesempenho(participanteAvaliacaoDesempenho.getAvaliacaoDesempenho().getId(), null).size());
		assertEquals(1, participanteAvaliacaoDesempenhoDao.findFaixasSalariaisDosAvaliadosByAvaliacaoDesempenho(participanteAvaliacaoDesempenho.getAvaliacaoDesempenho().getId(), new Long[]{faixaSalarial.getId()}).size());
	}
	
	public void testFindFaixasSalariaisDosAvaliadosByAvaliador()
	{
		ParticipanteAvaliacaoDesempenho participanteAvaliadoAvaliacaoDesempenho = getEntity();
		participanteAvaliacaoDesempenhoDao.save(participanteAvaliadoAvaliacaoDesempenho);
		
		Colaborador avaliadorAvaliacaoDesempenho = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliadorAvaliacaoDesempenho);
		
		ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario();
		colaboradorQuestionario.setAvaliador(avaliadorAvaliacaoDesempenho);
		colaboradorQuestionario.setColaborador(participanteAvaliadoAvaliacaoDesempenho.getColaborador());
		colaboradorQuestionario.setAvaliacaoDesempenho(participanteAvaliadoAvaliacaoDesempenho.getAvaliacaoDesempenho());
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		participanteAvaliacaoDesempenhoDao.getHibernateTemplateByGenericDao().flush();
		
		assertEquals(1, participanteAvaliacaoDesempenhoDao.findFaixasSalariaisDosAvaliadosByAvaliador(participanteAvaliadoAvaliacaoDesempenho.getAvaliacaoDesempenho().getId(), avaliadorAvaliacaoDesempenho.getId()).size());
	}
	
	public void testRemoveNotIn() throws Exception
	{
		ParticipanteAvaliacaoDesempenho participanteAvaliacaoDesempenho = getEntity();
		participanteAvaliacaoDesempenhoDao.save(participanteAvaliacaoDesempenho);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2.setNome("Colaborador 2");
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador2);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 02, 2000));
		historicoColaboradorDao.save(historicoColaborador);
		
		ParticipanteAvaliacaoDesempenho participanteAvaliacaoDesempenho2 = new ParticipanteAvaliacaoDesempenho();
		participanteAvaliacaoDesempenho2.setColaborador(colaborador2);
		participanteAvaliacaoDesempenho2.setAvaliacaoDesempenho(participanteAvaliacaoDesempenho.getAvaliacaoDesempenho());
		participanteAvaliacaoDesempenho2.setTipo(TipoParticipanteAvaliacao.AVALIADO);
		participanteAvaliacaoDesempenhoDao.save(participanteAvaliacaoDesempenho2);
		
		Collection<Colaborador> participantes = new ArrayList<Colaborador>();
		participantes.add(participanteAvaliacaoDesempenho.getColaborador());
		
		participanteAvaliacaoDesempenhoDao.getHibernateTemplateByGenericDao().flush();
		
		assertEquals(2, participanteAvaliacaoDesempenhoDao.findColaboradoresParticipantes(participanteAvaliacaoDesempenho.getAvaliacaoDesempenho().getId(), TipoParticipanteAvaliacao.AVALIADO).size());
		participanteAvaliacaoDesempenhoDao.removeNotIn(LongUtil.collectionToArrayLong(participantes), participanteAvaliacaoDesempenho.getAvaliacaoDesempenho().getId(), TipoParticipanteAvaliacao.AVALIADO);
		
		participanteAvaliacaoDesempenhoDao.getHibernateTemplateByGenericDao().flush();
		
		assertEquals(1, participanteAvaliacaoDesempenhoDao.findColaboradoresParticipantes(participanteAvaliacaoDesempenho.getAvaliacaoDesempenho().getId(), TipoParticipanteAvaliacao.AVALIADO).size());
	}
	
	public void testfindByAvalDesempenhoIdAbadColaboradorId() throws Exception
	{
		AvaliacaoDesempenho avalDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avalDesempenho);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ParticipanteAvaliacaoDesempenho participanteAvaliacaoDesempenho = getEntity();
		participanteAvaliacaoDesempenho.setColaborador(colaborador);
		participanteAvaliacaoDesempenho.setAvaliacaoDesempenho(avalDesempenho);
		participanteAvaliacaoDesempenho.setProdutividade(5.0);
		participanteAvaliacaoDesempenho.setTipo(TipoParticipanteAvaliacao.AVALIADO);
		participanteAvaliacaoDesempenhoDao.save(participanteAvaliacaoDesempenho);
		
		assertEquals(participanteAvaliacaoDesempenho.getProdutividade(), participanteAvaliacaoDesempenhoDao.findByAvalDesempenhoIdAbadColaboradorId(avalDesempenho.getId(), colaborador.getId(), TipoParticipanteAvaliacao.AVALIADO));
	}

	public AvaliacaoDesempenhoDao getAvaliacaoDesempenhoDao() {
		return avaliacaoDesempenhoDao;
	}

	public void setAvaliacaoDesempenhoDao(
			AvaliacaoDesempenhoDao avaliacaoDesempenhoDao) {
		this.avaliacaoDesempenhoDao = avaliacaoDesempenhoDao;
	}

	public ColaboradorDao getColaboradorDao() {
		return colaboradorDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public HistoricoColaboradorDao getHistoricoColaboradorDao() {
		return historicoColaboradorDao;
	}

	public void setHistoricoColaboradorDao(
			HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public EmpresaDao getEmpresaDao() {
		return empresaDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao) {
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setColaboradorQuestionarioDao(
			ColaboradorQuestionarioDao colaboradorQuestionarioDao) {
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}

	public void setCargoDao(CargoDao cargoDao) {
		this.cargoDao = cargoDao;
	}
}
