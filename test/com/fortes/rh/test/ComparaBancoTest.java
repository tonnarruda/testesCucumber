package com.fortes.rh.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.dao.acesso.PerfilDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.captacao.AnuncioDao;
import com.fortes.rh.dao.captacao.CandidatoCurriculoDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoIdiomaDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.EmpresaBdsDao;
import com.fortes.rh.dao.captacao.EtapaSeletivaDao;
import com.fortes.rh.dao.captacao.ExperienciaDao;
import com.fortes.rh.dao.captacao.FormacaoDao;
import com.fortes.rh.dao.captacao.HistoricoCandidatoDao;
import com.fortes.rh.dao.captacao.IdiomaDao;
import com.fortes.rh.dao.captacao.MotivoSolicitacaoDao;
import com.fortes.rh.dao.captacao.SolicitacaoBDSDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.cargosalario.ReajusteColaboradorDao;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorPresencaDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.DNTDao;
import com.fortes.rh.dao.desenvolvimento.DiaTurmaDao;
import com.fortes.rh.dao.desenvolvimento.PrioridadeTreinamentoDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.AreaFormacaoDao;
import com.fortes.rh.dao.geral.AreaInteresseDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.BairroDao;
import com.fortes.rh.dao.geral.BeneficioDao;
import com.fortes.rh.dao.geral.CidadeDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorIdiomaDao;
import com.fortes.rh.dao.geral.ColaboradorOcorrenciaDao;
import com.fortes.rh.dao.geral.DependenteDao;
import com.fortes.rh.dao.geral.DocumentoAnexoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.dao.geral.GastoDao;
import com.fortes.rh.dao.geral.GastoEmpresaDao;
import com.fortes.rh.dao.geral.GastoEmpresaItemDao;
import com.fortes.rh.dao.geral.GrupoGastoDao;
import com.fortes.rh.dao.geral.HistoricoBeneficioDao;
import com.fortes.rh.dao.geral.HistoricoColaboradorBeneficioDao;
import com.fortes.rh.dao.geral.MotivoDemissaoDao;
import com.fortes.rh.dao.geral.OcorrenciaDao;
import com.fortes.rh.dao.geral.ParametrosDoSistemaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorRespostaDao;
import com.fortes.rh.dao.pesquisa.PerguntaDao;
import com.fortes.rh.dao.pesquisa.PesquisaDao;
import com.fortes.rh.dao.pesquisa.RespostaDao;
import com.fortes.rh.dao.security.AuditoriaDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.AnexoDao;
import com.fortes.rh.dao.sesmt.CatDao;
import com.fortes.rh.dao.sesmt.ClinicaAutorizadaDao;
import com.fortes.rh.dao.sesmt.EngenheiroResponsavelDao;
import com.fortes.rh.dao.sesmt.EpcDao;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.dao.sesmt.EpiHistoricoDao;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.dao.sesmt.HistoricoFuncaoDao;
import com.fortes.rh.dao.sesmt.MedicoCoordenadorDao;
import com.fortes.rh.dao.sesmt.RealizacaoExameDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.dao.sesmt.TipoEPIDao;
import com.fortes.rh.test.dao.BaseDaoHibernateTest;


/**
 * @author Francisco Barroso 21/08/2008
 *
 * Classe utilizada para comparar banco de dados, simplesmente conta quantos
 * registros existem nas tabelas.
 * Caso a tabela não existe no modelo tem que comentar a linha e passar null para o count().
 * Mudar o banco de dados no arquivo hibernate.properties
 *
 */
@SuppressWarnings("rawtypes")
public class ComparaBancoTest extends BaseDaoHibernateTest
{
	AmbienteDao ambienteDao;
	AnexoDao anexoDao;
	AnuncioDao anuncioDao;
	AreaFormacaoDao areaFormacaoDao;
	AreaInteresseDao areaInteresseDao;
	AreaOrganizacionalDao areaOrganizacionalDao;
	AuditoriaDao auditoriaDao;
	BairroDao bairroDao;
	BeneficioDao beneficioDao;
	CandidatoDao candidatoDao;
	CandidatoCurriculoDao candidatoCurriculoDao;
	CandidatoIdiomaDao candidatoIdiomaDao;
	CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	CargoDao cargoDao;
	CatDao catDao;
	CidadeDao cidadeDao;
	ClinicaAutorizadaDao clinicaAutorizadaDao;
	ColaboradorDao colaboradorDao;
	ColaboradorIdiomaDao colaboradorIdiomaDao;
	ColaboradorOcorrenciaDao colaboradorOcorrenciaDao;
	ColaboradorPresencaDao colaboradorPresencaDao;
	ColaboradorRespostaDao colaboradorRespostaDao;
	ColaboradorTurmaDao colaboradorTurmaDao;
	ConhecimentoDao conhecimentoDao;
	CursoDao cursoDao;
	DependenteDao dependenteDao;
	DiaTurmaDao diaTurmaDao;
	DNTDao DNTDao;
	DocumentoAnexoDao documentoAnexoDao;
	EmpresaDao empresaDao;
	EmpresaBdsDao empresaBdsDao;
	EngenheiroResponsavelDao engenheiroResponsavelDao;
	EpcDao epcDao;
	EpiDao epiDao;
	EpiHistoricoDao epiHistoricoDao;
	EstabelecimentoDao estabelecimentoDao;
	EstadoDao estadoDao;
	EtapaSeletivaDao etapaSeletivaDao;
	ExameDao exameDao;
	ExperienciaDao experienciaDao;
	FaixaSalarialDao faixaSalarialDao;
	FormacaoDao formacaoDao;
	FuncaoDao funcaoDao;
	GastoDao gastoDao;
	GastoEmpresaDao gastoEmpresaDao;
	GastoEmpresaItemDao gastoEmpresaItemDao;
	GrupoGastoDao grupoGastoDao;
	GrupoOcupacionalDao grupoOcupacionalDao;
	HistoricoAmbienteDao historicoAmbienteDao;
	HistoricoBeneficioDao  historicoBeneficioDao;
	HistoricoCandidatoDao historicoCandidatoDao;
	HistoricoColaboradorDao historicoColaboradorDao;
	HistoricoColaboradorBeneficioDao historicoColaboradorBeneficioDao;
	HistoricoFuncaoDao historicoFuncaoDao;
	IdiomaDao idiomaDao;
	MedicoCoordenadorDao medicoCoordenadorDao;
	MotivoDemissaoDao motivoDemissaoDao;
	MotivoSolicitacaoDao motivoSolicitacaoDao;
	OcorrenciaDao ocorrenciaDao;
	PapelDao papelDao;
	ParametrosDoSistemaDao parametrosDoSistemaDao;
	PerfilDao perfilDao;
	PerguntaDao perguntaDao;
	PesquisaDao pesquisaDao;
	PrioridadeTreinamentoDao prioridadeTreinamentoDao;
	ReajusteColaboradorDao reajusteColaboradorDao;
	RealizacaoExameDao realizacaoExameDao;
	RespostaDao respostaDao;
	RiscoDao riscoDao;
	SolicitacaoDao solicitacaoDao;
	SolicitacaoBDSDao solicitacaoBDSDao;
	TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao;
	TipoEPIDao tipoEPIDao;
	TurmaDao turmaDao;
	UsuarioDao usuarioDao;

	Collection<String> listaCount = new ArrayList<String>();

	public void testGetCount()
	{
		count(ambienteDao);
		count(anexoDao);
		count(anuncioDao);
		count(areaFormacaoDao);
		count(areaInteresseDao);
		count(areaOrganizacionalDao);
		count(auditoriaDao);
//		count(bairroDao);
		count(null);
		count(beneficioDao);
		count(candidatoDao);
//		count(candidatoCurriculoDao);
		count(null);
		count(candidatoIdiomaDao);
		count(candidatoSolicitacaoDao);
		count(cargoDao);
		count(catDao);
		count(cidadeDao);
		count(clinicaAutorizadaDao);
		count(colaboradorDao);
		count(colaboradorIdiomaDao);
		count(colaboradorOcorrenciaDao);
		count(colaboradorPresencaDao);
		count(colaboradorRespostaDao);
		count(colaboradorTurmaDao);
		count(conhecimentoDao);
		count(cursoDao);
		count(dependenteDao);
		count(diaTurmaDao);
		count(DNTDao);
//		count(documentoAnexoDao);
		count(null);
		count(empresaDao);
		count(empresaBdsDao);
		count(engenheiroResponsavelDao);
		count(epcDao);
		count(epiDao);
		count(epiHistoricoDao);
//		count(estabelecimentoDao);
		count(null);
		count(estadoDao);
		count(etapaSeletivaDao);
		count(exameDao);
		count(experienciaDao);
		count(faixaSalarialDao);
		count(formacaoDao);
		count(funcaoDao);
		count(gastoDao);
		count(gastoEmpresaDao);
		count(gastoEmpresaItemDao);
		count(grupoGastoDao);
		count(grupoOcupacionalDao);
		count(historicoAmbienteDao);
//		count(historicoBeneficioDao);
		count(null);
		count(historicoCandidatoDao);
		count(historicoColaboradorDao);
		count(historicoColaboradorBeneficioDao);
		count(historicoFuncaoDao);
		count(idiomaDao);
		count(medicoCoordenadorDao);
		count(motivoDemissaoDao);
		count(motivoSolicitacaoDao);
		count(ocorrenciaDao);
		count(papelDao);
		count(parametrosDoSistemaDao);
		count(perfilDao);
		count(perguntaDao);
		count(pesquisaDao);
		count(prioridadeTreinamentoDao);
		count(reajusteColaboradorDao);
		count(realizacaoExameDao);
		count(respostaDao);
		count(riscoDao);
		count(solicitacaoDao);
		count(solicitacaoBDSDao);
		count(tabelaReajusteColaboradorDao);
		count(tipoEPIDao);
		count(turmaDao);
		count(usuarioDao);

		System.out.println("Banco Atualizado da Vega " + new Date());
		System.out.println("Total de Tabelas: " + listaCount.size());
		for (String aviso: listaCount)
		{
			System.out.println(aviso);
		}
	}

	private void count(GenericDao entidadeDao)
	{
		if(entidadeDao == null)
			listaCount.add("Tabela ainda não existe");
		else
		{
			StringBuilder classeName = new StringBuilder(entidadeDao.toString());
			String aviso = classeName.substring(0, classeName.indexOf("@")) + " _ Qtd. de Registros: ";
			try
			{
				Integer count = entidadeDao.getCount();
				aviso += count;
			}
			catch (Exception e)
			{
				aviso += "ERRO";
			}

			listaCount.add(aviso);
		}
	}

	public void setAmbienteDao(AmbienteDao ambienteDao)
	{
		this.ambienteDao = ambienteDao;
	}

	public void setAnexoDao(AnexoDao anexoDao)
	{
		this.anexoDao = anexoDao;
	}

	public void setAnuncioDao(AnuncioDao anuncioDao)
	{
		this.anuncioDao = anuncioDao;
	}

	public void setAreaFormacaoDao(AreaFormacaoDao areaFormacaoDao)
	{
		this.areaFormacaoDao = areaFormacaoDao;
	}

	public void setAreaInteresseDao(AreaInteresseDao areaInteresseDao)
	{
		this.areaInteresseDao = areaInteresseDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setAuditoriaDao(AuditoriaDao auditoriaDao)
	{
		this.auditoriaDao = auditoriaDao;
	}

	public void setBairroDao(BairroDao bairroDao)
	{
		this.bairroDao = bairroDao;
	}

	public void setBeneficioDao(BeneficioDao beneficioDao)
	{
		this.beneficioDao = beneficioDao;
	}

	public void setCandidatoCurriculoDao(CandidatoCurriculoDao candidatoCurriculoDao)
	{
		this.candidatoCurriculoDao = candidatoCurriculoDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao)
	{
		this.candidatoDao = candidatoDao;
	}

	public void setCandidatoIdiomaDao(CandidatoIdiomaDao candidatoIdiomaDao)
	{
		this.candidatoIdiomaDao = candidatoIdiomaDao;
	}

	public void setCandidatoSolicitacaoDao(CandidatoSolicitacaoDao candidatoSolicitacaoDao)
	{
		this.candidatoSolicitacaoDao = candidatoSolicitacaoDao;
	}

	public void setCargoDao(CargoDao cargoDao)
	{
		this.cargoDao = cargoDao;
	}

	public void setCatDao(CatDao catDao)
	{
		this.catDao = catDao;
	}

	public void setCidadeDao(CidadeDao cidadeDao)
	{
		this.cidadeDao = cidadeDao;
	}

	public void setClinicaAutorizadaDao(ClinicaAutorizadaDao clinicaAutorizadaDao)
	{
		this.clinicaAutorizadaDao = clinicaAutorizadaDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setColaboradorIdiomaDao(ColaboradorIdiomaDao colaboradorIdiomaDao)
	{
		this.colaboradorIdiomaDao = colaboradorIdiomaDao;
	}

	public void setColaboradorOcorrenciaDao(ColaboradorOcorrenciaDao colaboradorOcorrenciaDao)
	{
		this.colaboradorOcorrenciaDao = colaboradorOcorrenciaDao;
	}

	public void setColaboradorPresencaDao(ColaboradorPresencaDao colaboradorPresencaDao)
	{
		this.colaboradorPresencaDao = colaboradorPresencaDao;
	}

	public void setColaboradorRespostaDao(ColaboradorRespostaDao colaboradorRespostaDao)
	{
		this.colaboradorRespostaDao = colaboradorRespostaDao;
	}

	public void setColaboradorTurmaDao(ColaboradorTurmaDao colaboradorTurmaDao)
	{
		this.colaboradorTurmaDao = colaboradorTurmaDao;
	}

	public void setConhecimentoDao(ConhecimentoDao conhecimentoDao)
	{
		this.conhecimentoDao = conhecimentoDao;
	}

	public void setCursoDao(CursoDao cursoDao)
	{
		this.cursoDao = cursoDao;
	}

	public void setDependenteDao(DependenteDao dependenteDao)
	{
		this.dependenteDao = dependenteDao;
	}

	public void setDiaTurmaDao(DiaTurmaDao diaTurmaDao)
	{
		this.diaTurmaDao = diaTurmaDao;
	}

	public void setDocumentoAnexoDao(DocumentoAnexoDao documentoAnexoDao)
	{
		this.documentoAnexoDao = documentoAnexoDao;
	}

	public void setEmpresaBdsDao(EmpresaBdsDao empresaBdsDao)
	{
		this.empresaBdsDao = empresaBdsDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setEngenheiroResponsavelDao(EngenheiroResponsavelDao engenheiroResponsavelDao)
	{
		this.engenheiroResponsavelDao = engenheiroResponsavelDao;
	}

	public void setEpcDao(EpcDao epcDao)
	{
		this.epcDao = epcDao;
	}

	public void setEpiDao(EpiDao epiDao)
	{
		this.epiDao = epiDao;
	}

	public void setEpiHistoricoDao(EpiHistoricoDao epiHistoricoDao)
	{
		this.epiHistoricoDao = epiHistoricoDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setEstadoDao(EstadoDao estadoDao)
	{
		this.estadoDao = estadoDao;
	}

	public void setEtapaSeletivaDao(EtapaSeletivaDao etapaSeletivaDao)
	{
		this.etapaSeletivaDao = etapaSeletivaDao;
	}

	public void setExameDao(ExameDao exameDao)
	{
		this.exameDao = exameDao;
	}

	public void setExperienciaDao(ExperienciaDao experienciaDao)
	{
		this.experienciaDao = experienciaDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao)
	{
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setFormacaoDao(FormacaoDao formacaoDao)
	{
		this.formacaoDao = formacaoDao;
	}

	public void setFuncaoDao(FuncaoDao funcaoDao)
	{
		this.funcaoDao = funcaoDao;
	}

	public void setGastoDao(GastoDao gastoDao)
	{
		this.gastoDao = gastoDao;
	}

	public void setGastoEmpresaDao(GastoEmpresaDao gastoEmpresaDao)
	{
		this.gastoEmpresaDao = gastoEmpresaDao;
	}

	public void setGastoEmpresaItemDao(GastoEmpresaItemDao gastoEmpresaItemDao)
	{
		this.gastoEmpresaItemDao = gastoEmpresaItemDao;
	}

	public void setGrupoGastoDao(GrupoGastoDao grupoGastoDao)
	{
		this.grupoGastoDao = grupoGastoDao;
	}

	public void setGrupoOcupacionalDao(GrupoOcupacionalDao grupoOcupacionalDao)
	{
		this.grupoOcupacionalDao = grupoOcupacionalDao;
	}

	public void setHistoricoAmbienteDao(HistoricoAmbienteDao historicoAmbienteDao)
	{
		this.historicoAmbienteDao = historicoAmbienteDao;
	}

	public void setHistoricoBeneficioDao(HistoricoBeneficioDao historicoBeneficioDao)
	{
		this.historicoBeneficioDao = historicoBeneficioDao;
	}

	public void setHistoricoCandidatoDao(HistoricoCandidatoDao historicoCandidatoDao)
	{
		this.historicoCandidatoDao = historicoCandidatoDao;
	}

	public void setHistoricoColaboradorBeneficioDao(HistoricoColaboradorBeneficioDao historicoColaboradorBeneficioDao)
	{
		this.historicoColaboradorBeneficioDao = historicoColaboradorBeneficioDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao)
	{
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setHistoricoFuncaoDao(HistoricoFuncaoDao historicoFuncaoDao)
	{
		this.historicoFuncaoDao = historicoFuncaoDao;
	}

	public void setIdiomaDao(IdiomaDao idiomaDao)
	{
		this.idiomaDao = idiomaDao;
	}

	public void setDNTDao(DNTDao dao)
	{
		DNTDao = dao;
	}

	public void setMedicoCoordenadorDao(MedicoCoordenadorDao medicoCoordenadorDao)
	{
		this.medicoCoordenadorDao = medicoCoordenadorDao;
	}

	public void setMotivoDemissaoDao(MotivoDemissaoDao motivoDemissaoDao)
	{
		this.motivoDemissaoDao = motivoDemissaoDao;
	}

	public void setMotivoSolicitacaoDao(MotivoSolicitacaoDao motivoSolicitacaoDao)
	{
		this.motivoSolicitacaoDao = motivoSolicitacaoDao;
	}

	public void setOcorrenciaDao(OcorrenciaDao ocorrenciaDao)
	{
		this.ocorrenciaDao = ocorrenciaDao;
	}

	public void setPapelDao(PapelDao papelDao)
	{
		this.papelDao = papelDao;
	}

	public void setParametrosDoSistemaDao(ParametrosDoSistemaDao parametrosDoSistemaDao)
	{
		this.parametrosDoSistemaDao = parametrosDoSistemaDao;
	}

	public void setPerfilDao(PerfilDao perfilDao)
	{
		this.perfilDao = perfilDao;
	}

	public void setPerguntaDao(PerguntaDao perguntaDao)
	{
		this.perguntaDao = perguntaDao;
	}

	public void setPesquisaDao(PesquisaDao pesquisaDao)
	{
		this.pesquisaDao = pesquisaDao;
	}

	public void setPrioridadeTreinamentoDao(PrioridadeTreinamentoDao prioridadeTreinamentoDao)
	{
		this.prioridadeTreinamentoDao = prioridadeTreinamentoDao;
	}

	public void setReajusteColaboradorDao(ReajusteColaboradorDao reajusteColaboradorDao)
	{
		this.reajusteColaboradorDao = reajusteColaboradorDao;
	}

	public void setRealizacaoExameDao(RealizacaoExameDao realizacaoExameDao)
	{
		this.realizacaoExameDao = realizacaoExameDao;
	}

	public void setRespostaDao(RespostaDao respostaDao)
	{
		this.respostaDao = respostaDao;
	}

	public void setRiscoDao(RiscoDao riscoDao)
	{
		this.riscoDao = riscoDao;
	}

	public void setSolicitacaoBDSDao(SolicitacaoBDSDao solicitacaoBDSDao)
	{
		this.solicitacaoBDSDao = solicitacaoBDSDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao)
	{
		this.solicitacaoDao = solicitacaoDao;
	}


	public void setTabelaReajusteColaboradorDao(TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao)
	{
		this.tabelaReajusteColaboradorDao = tabelaReajusteColaboradorDao;
	}

	public void setTipoEPIDao(TipoEPIDao tipoEPIDao)
	{
		this.tipoEPIDao = tipoEPIDao;
	}

	public void setTurmaDao(TurmaDao turmaDao)
	{
		this.turmaDao = turmaDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}
}