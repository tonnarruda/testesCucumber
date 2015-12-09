package com.fortes.rh.web.action.captacao;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.business.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.NivelCompetenciaHistoricoManager;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaVO;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class NivelCompetenciaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private NivelCompetenciaManager nivelCompetenciaManager;
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager; 
	private FaixaSalarialManager faixaSalarialManager;
	private CandidatoManager candidatoManager;
	private ColaboradorManager colaboradorManager;
	private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;
	private ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager;
	private NivelCompetenciaHistoricoManager nivelCompetenciaHistoricoManager;
	private SolicitacaoManager solicitacaoManager;
	private ConfiguracaoCompetenciaAvaliacaoDesempenhoManager configuracaoCompetenciaAvaliacaoDesempenhoManager;
	
	private NivelCompetencia nivelCompetencia;
	private FaixaSalarial faixaSalarial;
	private Candidato candidato;
	private Colaborador colaborador;
	private Solicitacao solicitacao;
	private Date data;
	private Date dataIni;
	private Date dataFim;
	private boolean edicao;
	private ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador;
	private ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial;
	
	private Collection<FaixaSalarial> faixaSalarials;
	private Collection<NivelCompetencia> nivelCompetencias = new ArrayList<NivelCompetencia>();
	private Collection<Colaborador> colaboradores;
	
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais = new ArrayList<ConfiguracaoNivelCompetencia>();
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisConhecimento;
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisHabilidade;
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisAtitude;
	
	private Collection<Competencia> competencias = new ArrayList<Competencia>();
	
	private Collection<Solicitacao> solicitacoesNiveisCompetenciaFaixaSalariaisSalvos;
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisSalvos;
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisSalvosConhecimento;
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisSalvosHabilidade;
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisSalvosAtitude;
	
	private Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisSugeridos;
	private Collection<ConfiguracaoNivelCompetenciaColaborador> configuracaoNivelCompetenciaColaboradores;
	private Collection<ConfiguracaoNivelCompetenciaFaixaSalarial> configuracaoNivelCompetenciaFaixasSalariais;
	private Collection<Solicitacao> solicitacoes;

	private String[] competenciasCheck;
	private Collection<CheckBox> competenciasCheckList = new ArrayList<CheckBox>();
	private Map<String,Object> parametros;

	private Collection<ConfiguracaoNivelCompetenciaVO> configuracaoNivelCompetenciaVOs;
	private Long nivelCompetenciaHistoricoId;
	
	private void prepare() throws Exception
	{
		if(nivelCompetencia != null && nivelCompetencia.getId() != null)
			nivelCompetencia = (NivelCompetencia) nivelCompetenciaManager.findById(nivelCompetencia.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert()
	{
		try
		{
			nivelCompetenciaManager.validaLimite(getEmpresaSistema().getId());
			nivelCompetencia.setEmpresa(getEmpresaSistema());
			nivelCompetenciaManager.save(nivelCompetencia);
			
			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		nivelCompetenciaManager.update(nivelCompetencia);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		nivelCompetencias = nivelCompetenciaManager.findAllSelect(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			nivelCompetenciaManager.remove(nivelCompetencia.getId());
			addActionSuccess("Nível de competência excluído com sucesso");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionWarning("Não foi possível excluir este nível de competência, pois possui refererências no sistema");
		}

		return list();
	}
	
	public String prepareCompetenciasByFaixaSalarial()
	{
		faixaSalarial = faixaSalarialManager.findByFaixaSalarialId(faixaSalarial.getId());
		
		if(configuracaoNivelCompetenciaFaixaSalarial == null || configuracaoNivelCompetenciaFaixaSalarial.getId() == null)
			niveisCompetenciaFaixaSalariais = nivelCompetenciaManager.findByCargoOrEmpresa(faixaSalarial.getCargo().getId(), null);
		else {
			niveisCompetenciaFaixaSalariais = configuracaoNivelCompetenciaManager.findByConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial.getId(), configuracaoNivelCompetenciaFaixaSalarial.getData());
			
			if(configuracaoNivelCompetenciaFaixaSalarial != null && configuracaoNivelCompetenciaFaixaSalarial.getId() != null){
				niveisCompetenciaFaixaSalariaisSalvos = configuracaoNivelCompetenciaManager.findByFaixa(faixaSalarial.getId(), configuracaoNivelCompetenciaFaixaSalarial.getData());
				niveisCompetenciaFaixaSalariaisSalvosConhecimento = new ArrayList<ConfiguracaoNivelCompetencia>();
				niveisCompetenciaFaixaSalariaisSalvosHabilidade = new ArrayList<ConfiguracaoNivelCompetencia>();
				niveisCompetenciaFaixaSalariaisSalvosAtitude = new ArrayList<ConfiguracaoNivelCompetencia>();

				for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : niveisCompetenciaFaixaSalariaisSalvos) {
					if (TipoCompetencia.CONHECIMENTO.equals(configuracaoNivelCompetencia.getTipoCompetencia()))
						niveisCompetenciaFaixaSalariaisSalvosConhecimento.add(configuracaoNivelCompetencia);

					if (TipoCompetencia.HABILIDADE.equals(configuracaoNivelCompetencia.getTipoCompetencia()))
						niveisCompetenciaFaixaSalariaisSalvosHabilidade.add(configuracaoNivelCompetencia);

					if (TipoCompetencia.ATITUDE.equals(configuracaoNivelCompetencia.getTipoCompetencia()))
						niveisCompetenciaFaixaSalariaisSalvosAtitude.add(configuracaoNivelCompetencia);
				}
			}
		}

		if(configuracaoNivelCompetenciaFaixaSalarial != null && configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico() != null && configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId() != null)
			nivelCompetenciaHistoricoId = configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId();
		else
			nivelCompetenciaHistoricoId = nivelCompetenciaHistoricoManager.findByData(new Date(), getEmpresaSistema().getId());
		
		nivelCompetencias = nivelCompetenciaManager.findAllSelect(getEmpresaSistema().getId(), nivelCompetenciaHistoricoId, null);
		
		if (niveisCompetenciaFaixaSalariais == null)
			niveisCompetenciaFaixaSalariais = new ArrayList<ConfiguracaoNivelCompetencia>();
		
		niveisCompetenciaFaixaSalariaisConhecimento = new ArrayList<ConfiguracaoNivelCompetencia>();
		niveisCompetenciaFaixaSalariaisHabilidade = new ArrayList<ConfiguracaoNivelCompetencia>();
		niveisCompetenciaFaixaSalariaisAtitude = new ArrayList<ConfiguracaoNivelCompetencia>();
		
		for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : niveisCompetenciaFaixaSalariais) {
			if (TipoCompetencia.CONHECIMENTO.equals(configuracaoNivelCompetencia.getTipoCompetencia())) {
				niveisCompetenciaFaixaSalariaisConhecimento.add(configuracaoNivelCompetencia);
			}
			if (TipoCompetencia.HABILIDADE.equals(configuracaoNivelCompetencia.getTipoCompetencia())) {
				niveisCompetenciaFaixaSalariaisHabilidade.add(configuracaoNivelCompetencia);
			}
			if (TipoCompetencia.ATITUDE.equals(configuracaoNivelCompetencia.getTipoCompetencia())) {
				niveisCompetenciaFaixaSalariaisAtitude.add(configuracaoNivelCompetencia);
			}
		}
		
		return Action.SUCCESS;
	}
	
	public String prepareCompetenciasByCandidato()
	{
		setVideoAjuda(571L);
		
		candidato = candidatoManager.findByCandidatoId(candidato.getId());
		faixaSalarial = faixaSalarialManager.findByFaixaSalarialId(faixaSalarial.getId());
		solicitacao = solicitacaoManager.findById(solicitacao.getId());
		configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaFaixaSalarialManager.findByFaixaSalarialIdAndData(faixaSalarial.getId(), solicitacao.getData());
		
		if(configuracaoNivelCompetenciaFaixaSalarial != null && configuracaoNivelCompetenciaFaixaSalarial.getId() != null)
			niveisCompetenciaFaixaSalariais = configuracaoNivelCompetenciaManager.findCompetenciaByFaixaSalarial(faixaSalarial.getId(), solicitacao.getData(), configuracaoNivelCompetenciaFaixaSalarial.getId());
		
		nivelCompetencias = nivelCompetenciaManager.findAllSelect(getEmpresaSistema().getId(), null, solicitacao.getData());
		
		niveisCompetenciaFaixaSalariaisSalvos = configuracaoNivelCompetenciaManager.findByCandidatoAndSolicitacao(candidato.getId(), solicitacao.getId());
		
		return Action.SUCCESS;
	}
	
	public String saveCompetenciasByFaixaSalarial() throws Exception
	{
		try
		{
			if (niveisCompetenciaFaixaSalariais == null) 
				niveisCompetenciaFaixaSalariais = new ArrayList<ConfiguracaoNivelCompetencia>();
			
			atualizaNivelCometenciaFaixaSalarial(niveisCompetenciaFaixaSalariaisConhecimento);
			atualizaNivelCometenciaFaixaSalarial(niveisCompetenciaFaixaSalariaisHabilidade);
			atualizaNivelCometenciaFaixaSalarial(niveisCompetenciaFaixaSalariaisAtitude);
			
			Collection<Competencia> conhecimentosAnteriores = configuracaoNivelCompetenciaManager.findCompetenciasByFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial.getFaixaSalarial().getId(), new Date(), Competencia.CONHECIMENTO);
			Collection<Competencia> habilidadesAnteriores = configuracaoNivelCompetenciaManager.findCompetenciasByFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial.getFaixaSalarial().getId(), new Date(), Competencia.HABILIDADE);
			Collection<Competencia> atitudesAnteriores = configuracaoNivelCompetenciaManager.findCompetenciasByFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial.getFaixaSalarial().getId(), new Date(), Competencia.ATITUDE);
			
			configuracaoNivelCompetenciaManager.saveCompetenciasFaixaSalarial(niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaFaixaSalarial);
			
			configuracaoCompetenciaAvaliacaoDesempenhoManager.reajusteByConfiguracaoNivelCompetenciaFaixaSalarial(conhecimentosAnteriores, habilidadesAnteriores, atitudesAnteriores, competencias, configuracaoNivelCompetenciaFaixaSalarial);
			
			setActionMsg("Níveis de competência da faixa salarial salvos com sucesso.");
		}
		catch (DataIntegrityViolationException e)
		{
			addActionWarning("Já existe uma configuração de competências nesta data.");
			e.printStackTrace();
			configuracaoNivelCompetenciaFaixaSalarial.setId(null);
			prepareCompetenciasByFaixaSalarial();
			return Action.INPUT;
		}
		catch (Exception e)
		{
			addActionError(e.getMessage());
			e.printStackTrace();
		}

		prepareCompetenciasByFaixaSalarial();
		return Action.SUCCESS;
	}

	private void atualizaNivelCometenciaFaixaSalarial(Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias)
	{
		if (configuracaoNivelCompetencias != null && !configuracaoNivelCompetencias.isEmpty()) {
			for (ConfiguracaoNivelCompetencia configuracaoNivelCompetencia : configuracaoNivelCompetencias) {
				if (configuracaoNivelCompetencia != null) {
					niveisCompetenciaFaixaSalariais.add(configuracaoNivelCompetencia);
					
					if ( configuracaoNivelCompetencia.getCompetenciaId() != null ) {
						Competencia competencia = new Competencia();
						competencia.setId(configuracaoNivelCompetencia.getCompetenciaId());
						competencia.setNome(configuracaoNivelCompetencia.getCompetenciaDescricao());
						competencia.setTipo(configuracaoNivelCompetencia.getTipoCompetencia());
						competencias.add(competencia);
					}
				}
			}
		}
	}
	
	public String saveCompetenciasByCandidato()
	{
		try
		{
			configuracaoNivelCompetenciaManager.saveCompetenciasCandidato(niveisCompetenciaFaixaSalariais, faixaSalarial.getId(), candidato.getId(), solicitacao.getId());
			addActionSuccess("Níveis de competência do candidato salvos com sucesso");
		}
		catch (Exception e)
		{
			addActionError(e.getMessage());
			e.printStackTrace();
		}
		
		prepareCompetenciasByCandidato();
		return Action.SUCCESS;
	}
	
	public String visualizarCandidato()
	{
		solicitacoes = solicitacaoManager.findAllByCandidato(candidato.getId());
		solicitacoesNiveisCompetenciaFaixaSalariaisSalvos = configuracaoNivelCompetenciaManager.getCompetenciasCandidato(getEmpresaSistema().getId(), candidato.getId());
		return Action.SUCCESS;
	}
	
	public String listCompetenciasColaborador()
	{
		colaborador = colaboradorManager.findColaboradorByIdProjection(colaborador.getId());
		configuracaoNivelCompetenciaColaboradores = configuracaoNivelCompetenciaColaboradorManager.findByColaborador(colaborador.getId()); 
		
		return Action.SUCCESS;	
	}
	
	public String listCompetenciasFaixaSalarial()
	{
		faixaSalarial = faixaSalarialManager.findByFaixaSalarialId(faixaSalarial.getId());
		configuracaoNivelCompetenciaFaixasSalariais = configuracaoNivelCompetenciaFaixaSalarialManager.findToList(new String[]{"id", "data"}, new String[]{"id", "data"}, new String[]{"faixaSalarial.id"}, new Long[]{faixaSalarial.getId()});
		
		return Action.SUCCESS;	
	}
	
	public String prepareInsertCompetenciasColaborador()
	{
		configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();
		configuracaoNivelCompetenciaColaborador.setData(new Date());
		
		colaborador = colaboradorManager.findById(colaborador.getId());
		colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(new Colaborador("Anônimo", 0L));
		colaboradores.addAll(colaboradorManager.findByEmpresaAndStatusAC(getEmpresaSistema().getId(), null, null, StatusRetornoAC.CONFIRMADO, false, false, SituacaoColaborador.ATIVO, true, "c.nome"));
		faixaSalarial = colaborador.getHistoricoColaborador().getFaixaSalarial();
		configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaFaixaSalarialManager.findByFaixaSalarialIdAndData(faixaSalarial.getId(), new Date());
		
		if(configuracaoNivelCompetenciaFaixaSalarial != null && configuracaoNivelCompetenciaFaixaSalarial.getId() != null)
			niveisCompetenciaFaixaSalariais = configuracaoNivelCompetenciaManager.findCompetenciaByFaixaSalarial(faixaSalarial.getId(), configuracaoNivelCompetenciaFaixaSalarial.getData(), configuracaoNivelCompetenciaFaixaSalarial.getId());
		
		nivelCompetencias = nivelCompetenciaManager.findAllSelect(getEmpresaSistema().getId(), null, data);
		
		return Action.SUCCESS;
	}
	
	public String prepareInsertCompetenciasFaixaSalarial()
	{
		configuracaoNivelCompetenciaFaixaSalarial = new ConfiguracaoNivelCompetenciaFaixaSalarial();
		configuracaoNivelCompetenciaFaixaSalarial.setData(new Date());
		
		prepareCompetenciasByFaixaSalarial();
		
		return Action.SUCCESS;
	}
	
	public String prepareUpdateCompetenciasColaborador()
	{
		configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaboradorManager.findByIdProjection(configuracaoNivelCompetenciaColaborador.getId());
		colaborador = configuracaoNivelCompetenciaColaborador.getColaborador();
		configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaColaborador.getConfiguracaoNivelCompetenciaFaixaSalarial();
		faixaSalarial = configuracaoNivelCompetenciaColaborador.getFaixaSalarial();
		
		data = configuracaoNivelCompetenciaColaborador.getData();
		
		niveisCompetenciaFaixaSalariais = configuracaoNivelCompetenciaManager.findCompetenciaByFaixaSalarial(null, null, configuracaoNivelCompetenciaFaixaSalarial.getId());
		niveisCompetenciaFaixaSalariaisSalvos = configuracaoNivelCompetenciaManager.findByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId(), configuracaoNivelCompetenciaFaixaSalarial.getId(), configuracaoNivelCompetenciaFaixaSalarial.getData());
		
		nivelCompetencias = nivelCompetenciaManager.findAllSelect(getEmpresaSistema().getId(), configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId(), configuracaoNivelCompetenciaFaixaSalarial.getData());
		
		return Action.SUCCESS;
	}
	
	public String prepareUpdateCompetenciasFaixaSalarial()
	{
		edicao = true;
		
		configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaFaixaSalarialManager.findById(configuracaoNivelCompetenciaFaixaSalarial.getId());
		faixaSalarial = configuracaoNivelCompetenciaFaixaSalarial.getFaixaSalarial();
		
		prepareCompetenciasByFaixaSalarial();
		
		return Action.SUCCESS;
	}
	
	public String saveCompetenciasColaborador()
	{
		try
		{
			configuracaoNivelCompetenciaManager.saveCompetenciasColaboradorAndRecalculaPerformance(getEmpresaSistema().getId(), niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
			setActionMsg("Níveis de competência do colaborador salvos com sucesso.");
		}
		catch (Exception e)
		{
			addActionError(e.getMessage());
			e.printStackTrace();
		}
		
		if (configuracaoNivelCompetenciaColaborador.getId() != null)
			prepareUpdateCompetenciasColaborador();
		else
			prepareInsertCompetenciasColaborador();
		
		return Action.SUCCESS;
	}
	
	public String deleteCompetenciasColaborador()
	{
		try
		{
			configuracaoNivelCompetenciaManager.removeConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId());
			addActionSuccess("Competências excluídas com sucesso.");
		}
		catch(FortesException fe)
		{
			addActionWarning(fe.getMessage());
		}
		catch (Exception e)
		{
			addActionError("Não foi possível excluir as competências.");
			addActionError(e.getMessage());
			e.printStackTrace();
		}

		listCompetenciasColaborador();
		return Action.SUCCESS;
	}
	
	public String deleteCompetenciasFaixaSalarial()
	{
		try
		{
			configuracaoNivelCompetenciaManager.removeConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial.getId());
			addActionSuccess("Competências excluídas com sucesso");
		}
		catch (FortesException e)
		{
			addActionWarning(e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e)
		{
			addActionError("Não foi possível excluir as competências.");
			e.printStackTrace();
		}
		
		listCompetenciasFaixaSalarial();
		return Action.SUCCESS;
	}
	
	public String prepareRelatorioCompetenciasColaborador()
	{
		faixaSalarials = faixaSalarialManager.findAllSelectByCargo(getEmpresaSistema().getId());

		return Action.SUCCESS;
	}
	
	public String imprimirRelatorioCompetenciasColaborador()
	{
		faixaSalarial = faixaSalarialManager.findByFaixaSalarialId(faixaSalarial.getId());
		parametros = RelatorioUtil.getParametrosRelatorio("Relatório Colaboradores com nível de competência inferior ao exigido", getEmpresaSistema(), "Cargo/Faixa: " + faixaSalarial.getDescricao());
		niveisCompetenciaFaixaSalariais = configuracaoNivelCompetenciaManager.findColaboradorAbaixoNivel(LongUtil.arrayStringToArrayLong(competenciasCheck), faixaSalarial.getId(), data);
		
		if(niveisCompetenciaFaixaSalariais.isEmpty())
		{
			prepareRelatorioCompetenciasColaborador();
			faixaSalarial = null;

			addActionMessage("Não existem dados para o filtro informado.");
			return Action.INPUT;			
		}
		
		return Action.SUCCESS;
	}
	
	public String imprimirMatrizCompetenciasColaborador()
	{
		faixaSalarial = faixaSalarialManager.findByFaixaSalarialId(faixaSalarial.getId());
		parametros = RelatorioUtil.getParametrosRelatorio("Matriz comparativa Cargo x Colaborador", getEmpresaSistema(), "Cargo/Faixa: " + faixaSalarial.getDescricao());
		parametros.put("ENTIDADE", "Colabotrador Avaliado");
		parametros.put("ISNIVELCOLABORADOR", true);
		configuracaoNivelCompetenciaVOs = configuracaoNivelCompetenciaManager.montaRelatorioConfiguracaoNivelCompetencia(dataIni, dataFim, getEmpresaSistema().getId(), faixaSalarial.getId(), LongUtil.arrayStringToArrayLong(competenciasCheck));
		
		if(configuracaoNivelCompetenciaVOs.isEmpty())
		{
			prepareRelatorioCompetenciasColaborador();
			faixaSalarial = null;
			
			addActionMessage("Não existem dados para o filtro informado.");
			return Action.INPUT;			
		}
		
		return Action.SUCCESS;
	}
	
	public String imprimirMatrizCompetenciasCandidatos()
	{
		try {
		
			faixaSalarial = faixaSalarialManager.findByFaixaSalarialId(faixaSalarial.getId());
			parametros = RelatorioUtil.getParametrosRelatorio("Matriz comparativa Cargo x Candidato", getEmpresaSistema(), "Cargo/Faixa: " + faixaSalarial.getDescricao());
			parametros.put("ENTIDADE", "Candidato Avaliado");
			parametros.put("ISNIVELCOLABORADOR", false);
			configuracaoNivelCompetenciaVOs = configuracaoNivelCompetenciaManager.montaMatrizCompetenciaCandidato(getEmpresaSistema().getId(), faixaSalarial.getId(), solicitacaoManager.findByIdProjection(solicitacao.getId()));
			
			if(configuracaoNivelCompetenciaVOs.size() == 0)
				throw new FortesException("Não existem competências para os candidatos desta seleção.");
			
			return Action.SUCCESS;
		} catch (FortesException e) {
			System.out.println(e.getMessage());
			addActionMessage(e.getMessage());
			return Action.INPUT;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			addActionError("Erro ao Gerar relatório da matriz de competencia dos candidatos\n" + e.getMessage());
			return Action.INPUT;
		}
	}
	
	public NivelCompetencia getNivelCompetencia()
	{
		if(nivelCompetencia == null)
			nivelCompetencia = new NivelCompetencia();
		return nivelCompetencia;
	}

	public void setNivelCompetencia(NivelCompetencia nivelCompetencia)
	{
		this.nivelCompetencia = nivelCompetencia;
	}

	public void setNivelCompetenciaManager(NivelCompetenciaManager nivelCompetenciaManager)
	{
		this.nivelCompetenciaManager = nivelCompetenciaManager;
	}
	
	public Collection<NivelCompetencia> getNivelCompetencias()
	{
		return nivelCompetencias;
	}

	public Collection<ConfiguracaoNivelCompetencia> getNiveisCompetenciaFaixaSalariais() 
	{
		return niveisCompetenciaFaixaSalariais;
	}

	public void setNiveisCompetenciaFaixaSalariais(Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais) 
	{
		this.niveisCompetenciaFaixaSalariais = niveisCompetenciaFaixaSalariais;
	}

	public FaixaSalarial getFaixaSalarial() 
	{
		return faixaSalarial;
	}

	public void setFaixaSalarial(FaixaSalarial faixaSalarial)
	{
		this.faixaSalarial = faixaSalarial;
	}
	
	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

	public Collection<ConfiguracaoNivelCompetencia> getNiveisCompetenciaFaixaSalariaisSalvos() {
		return niveisCompetenciaFaixaSalariaisSalvos;
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager) {
		this.candidatoManager = candidatoManager;
	}

	public Collection<ConfiguracaoNivelCompetencia> getNiveisCompetenciaFaixaSalariaisSugeridos() {
		return niveisCompetenciaFaixaSalariaisSugeridos;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public ConfiguracaoNivelCompetenciaColaborador getConfiguracaoNivelCompetenciaColaborador() {
		return configuracaoNivelCompetenciaColaborador;
	}

	public void setConfiguracaoNivelCompetenciaColaborador(ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador) {
		this.configuracaoNivelCompetenciaColaborador = configuracaoNivelCompetenciaColaborador;
	}

	public void setConfiguracaoNivelCompetenciaColaboradorManager(ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager) {
		this.configuracaoNivelCompetenciaColaboradorManager = configuracaoNivelCompetenciaColaboradorManager;
	}

	public Collection<ConfiguracaoNivelCompetenciaColaborador> getConfiguracaoNivelCompetenciaColaboradores() {
		return configuracaoNivelCompetenciaColaboradores;
	}

	public void setConfiguracaoNivelCompetenciaColaboradores(Collection<ConfiguracaoNivelCompetenciaColaborador> configuracaoNivelCompetenciaColaboradores) {
		this.configuracaoNivelCompetenciaColaboradores = configuracaoNivelCompetenciaColaboradores;
	}

	public Collection<FaixaSalarial> getFaixaSalarials() {
		return faixaSalarials;
	}

	public void setCompetenciasCheck(String[] competenciasCheck) {
		this.competenciasCheck = competenciasCheck;
	}

	public Collection<CheckBox> getCompetenciasCheckList() {
		return competenciasCheckList;
	}

	public Collection<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(Collection<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager) {
		this.solicitacaoManager = solicitacaoManager;
	}
	
	public Map<String, Object> getParametros() {
		return parametros;
	}

	public Collection<ConfiguracaoNivelCompetenciaVO> getConfiguracaoNivelCompetenciaVOs() {
		return configuracaoNivelCompetenciaVOs;
	}

	public Collection<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Collection<ConfiguracaoNivelCompetencia> getNiveisCompetenciaFaixaSalariaisConhecimento() {
		return niveisCompetenciaFaixaSalariaisConhecimento;
	}

	public void setNiveisCompetenciaFaixaSalariaisConhecimento(
			Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisConhecimento) {
		this.niveisCompetenciaFaixaSalariaisConhecimento = niveisCompetenciaFaixaSalariaisConhecimento;
	}

	public Collection<ConfiguracaoNivelCompetencia> getNiveisCompetenciaFaixaSalariaisHabilidade() {
		return niveisCompetenciaFaixaSalariaisHabilidade;
	}

	public void setNiveisCompetenciaFaixaSalariaisHabilidade(
			Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisHabilidade) {
		this.niveisCompetenciaFaixaSalariaisHabilidade = niveisCompetenciaFaixaSalariaisHabilidade;
	}

	public Collection<ConfiguracaoNivelCompetencia> getNiveisCompetenciaFaixaSalariaisAtitude() {
		return niveisCompetenciaFaixaSalariaisAtitude;
	}

	public void setNiveisCompetenciaFaixaSalariaisAtitude(
			Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariaisAtitude) {
		this.niveisCompetenciaFaixaSalariaisAtitude = niveisCompetenciaFaixaSalariaisAtitude;
	}

	public Collection<ConfiguracaoNivelCompetencia> getNiveisCompetenciaFaixaSalariaisSalvosConhecimento() {
		return niveisCompetenciaFaixaSalariaisSalvosConhecimento;
	}

	public Collection<ConfiguracaoNivelCompetencia> getNiveisCompetenciaFaixaSalariaisSalvosHabilidade() {
		return niveisCompetenciaFaixaSalariaisSalvosHabilidade;
	}

	public Collection<ConfiguracaoNivelCompetencia> getNiveisCompetenciaFaixaSalariaisSalvosAtitude() {
		return niveisCompetenciaFaixaSalariaisSalvosAtitude;
	}

	public void setConfiguracaoNivelCompetenciaFaixasSalariais(Collection<ConfiguracaoNivelCompetenciaFaixaSalarial> configuracaoNivelCompetenciaFaixasSalariais) {
		this.configuracaoNivelCompetenciaFaixasSalariais = configuracaoNivelCompetenciaFaixasSalariais;
	}
	
	public Collection<ConfiguracaoNivelCompetenciaFaixaSalarial> getConfiguracaoNivelCompetenciaFaixasSalariais() {
		return configuracaoNivelCompetenciaFaixasSalariais;
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarialManager(ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager) {
		this.configuracaoNivelCompetenciaFaixaSalarialManager = configuracaoNivelCompetenciaFaixaSalarialManager;
	}

	public ConfiguracaoNivelCompetenciaFaixaSalarial getConfiguracaoNivelCompetenciaFaixaSalarial() {
		return configuracaoNivelCompetenciaFaixaSalarial;
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarial(ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial)
	{
		this.configuracaoNivelCompetenciaFaixaSalarial = configuracaoNivelCompetenciaFaixaSalarial;
	}

	public boolean getEdicao()
	{
		return edicao;
	}

	public Collection<Solicitacao> getSolicitacoesNiveisCompetenciaFaixaSalariaisSalvos() 
	{
		return solicitacoesNiveisCompetenciaFaixaSalariaisSalvos;
	}

	public void setNivelCompetenciaHistoricoManager(
			NivelCompetenciaHistoricoManager nivelCompetenciaHistoricoManager) {
		this.nivelCompetenciaHistoricoManager = nivelCompetenciaHistoricoManager;
	}

	public Long getNivelCompetenciaHistoricoId() {
		return nivelCompetenciaHistoricoId;
	}

	public void setConfiguracaoCompetenciaAvaliacaoDesempenhoManager(
			ConfiguracaoCompetenciaAvaliacaoDesempenhoManager configuracaoCompetenciaAvaliacaoDesempenhoManager) {
		this.configuracaoCompetenciaAvaliacaoDesempenhoManager = configuracaoCompetenciaAvaliacaoDesempenhoManager;
	}
}