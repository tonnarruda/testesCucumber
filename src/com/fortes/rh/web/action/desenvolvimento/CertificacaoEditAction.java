package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;

public class CertificacaoEditAction extends MyActionSupportEdit implements ModelDriven
{
	private static final long serialVersionUID = 1L;
	
	private CertificacaoManager certificacaoManager;
	private CursoManager cursoManager;
	private AvaliacaoPraticaManager avaliacaoPraticaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private EmpresaManager empresaManager;
	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	
	private Collection<ColaboradorCertificacao> colaboradorCertificacoes = new ArrayList<ColaboradorCertificacao>();
	private Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();
	private Collection<Empresa> empresas;
	private Certificacao certificacao;
	private Long empresaId;
	
	private String[] cursosCheck;
	private Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();
	private String[] avaliacoesPraticasCheck;
	private Collection<CheckBox> avaliacoesPraticasCheckList = new ArrayList<CheckBox>();
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] certificacoesCheck;
	private Collection<CheckBox> certificacoesCheckList = new ArrayList<CheckBox>();
	private String[] colaboradoresCheck;
	private Collection<CheckBox> colaboradoresCheckList = new ArrayList<CheckBox>();
	private Map situacaos = new SituacaoColaborador();
	private String situacao;
	
	private Map<String,Object> parametros = new HashMap<String, Object>();
	private String reportFilter;
	private String reportTitle;
		
	private String nomeBusca;//filtro listagem
	private Date dataIni;
	private Date dataFim;
	private boolean colaboradorCertificado;
	private boolean colaboradorNaoCertificado;
	private Integer mesesCertificacoesAVencer;
	private Character agruparPor;
	private Integer qtdTotalColaboradoresCertificados;
	private Integer qtdTotalColaboradoresNaoCertificados;
	private boolean alterouCertificacao;

	private void prepare() throws Exception
	{
		if (certificacao != null && certificacao.getId() != null)
			certificacao = (Certificacao) certificacaoManager.findById(certificacao.getId());
		
		Collection<Curso> cursos = cursoManager.findAllByEmpresasParticipantes(getEmpresaSistema().getId());
		cursosCheckList = CheckListBoxUtil.populaCheckListBox(cursos, "getId", "getNome", null);
		
		Collection<AvaliacaoPratica> avaliacaoPraticas = avaliacaoPraticaManager.find(new String[] {"empresa.id"}, new Object[] { getEmpresaSistema().getId() }, new String[] { "titulo" });
		avaliacoesPraticasCheckList = CheckListBoxUtil.populaCheckListBox(avaliacaoPraticas, "getId", "getTitulo", null);
		
		if(getEmpresaSistema().isControlarVencimentoPorCertificacao())
			certificacoes = certificacaoManager.findAllSelectNotCertificacaoIdAndCertificacaoPreRequisito(getEmpresaSistema().getId(), certificacao.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		if (certificacao != null && certificacao.getId() != null && certificacaoManager.verificaEmpresa(certificacao.getId(), getEmpresaSistema().getId()))
		{
			prepare();
			cursosCheckList = CheckListBoxUtil.marcaCheckListBox(cursosCheckList, certificacao.getCursos(), "getId");
			avaliacoesPraticasCheckList = CheckListBoxUtil.marcaCheckListBox(avaliacoesPraticasCheckList, certificacao.getAvaliacoesPraticas(), "getId");
		}
		else
			addActionError("A Certificação solicitada não existe na empresa " + getEmpresaSistema().getNome() +".");
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		populaCertificacao();
		certificacaoManager.save(certificacao);
		return Action.SUCCESS;
	}

	public String update() throws Exception	{
		populaCertificacao();
		certificacaoManager.update(certificacao);

		if(alterouCertificacao && getEmpresaSistema().isControlarVencimentoPorCertificacao())
			colaboradorCertificacaoManager.reprocessaCertificacao(certificacao.getId(), certificacaoManager);
		
		return Action.SUCCESS;
	}

	private void populaCertificacao() throws Exception 
	{
		if(certificacao.getPeriodicidade() != null && certificacao.getPeriodicidade() == 0)
			certificacao.setPeriodicidade(null);
		
		CollectionUtil<Curso> util = new CollectionUtil<Curso>();
		certificacao.setCursos(util.convertArrayStringToCollection(Curso.class, cursosCheck));
		
		CollectionUtil<AvaliacaoPratica> utilAvaliacoesPraticas = new CollectionUtil<AvaliacaoPratica>();
		certificacao.setAvaliacoesPraticas(utilAvaliacoesPraticas.convertArrayStringToCollection(AvaliacaoPratica.class, avaliacoesPraticasCheck));
		
		if(!(certificacao.getCertificacaoPreRequisito() != null && certificacao.getCertificacaoPreRequisito().getId() != null))
			certificacao.setCertificacaoPreRequisito(null);
		
		certificacao.setEmpresa(getEmpresaSistema());
	}
	
	public String prepareImprimirCertificadosVencidosAVencer()
	{
		certificacoesCheckList = certificacaoManager.populaCheckBox(getEmpresaSistema().getId());
		certificacoesCheckList = CheckListBoxUtil.marcaCheckListBox(certificacoesCheckList, certificacoesCheck);

		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		
		empresaId = empresaManager.ajustaCombo(empresaId, getEmpresaSistema().getId());
		populaEmpresa(new String[]{"ROLE_REL_CERTIFICADOS_VENCIDOS_A_VENCER"});	
		empresaId = getEmpresaSistema().getId();
		
		return Action.SUCCESS;
	}
	
	private void populaEmpresa(String... roles)
	{
		boolean compartilharColaboradores = parametrosDoSistemaManager.findById(1L).getCompartilharColaboradores();
		empresas = empresaManager.findEmpresasPermitidas(compartilharColaboradores , getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), roles);
	}
	
	public String imprimirCertificadosVencidosAVencer(){
		try {
			Long[] areaIds = LongUtil.arrayStringToArrayLong(areasCheck);
			Long[] estabelecimentoIds = LongUtil.arrayStringToArrayLong(estabelecimentosCheck);
			Long[] certificacoesIds = LongUtil.arrayStringToArrayLong(certificacoesCheck);
			Long[] colaboradoresIds = LongUtil.arrayStringToArrayLong(colaboradoresCheck);
			
			Boolean certificado = null;
			if(colaboradorCertificado && !colaboradorNaoCertificado)
				certificado = true;
			else if(!colaboradorCertificado && colaboradorNaoCertificado)
				certificado = false;
			
			colaboradorCertificacoes = colaboradorCertificacaoManager.montaRelatorioColaboradoresNasCertificacoes(dataIni, dataFim, mesesCertificacoesAVencer, certificado, areaIds, estabelecimentoIds, certificacoesIds, colaboradoresIds, situacao, getEmpresaSistema().getId());
			if(colaboradorCertificacoes.size() == 0){
				addActionMessage("Não existem dados para o filtro informado.");
				prepareImprimirCertificadosVencidosAVencer();
				return Action.INPUT;	
			}

			calculoCertificadoENaoCertificados(certificacoesIds, (agruparPor != null && agruparPor == 'T'));
			montaReportTitleAndFilter();
			parametros = RelatorioUtil.getParametrosRelatorio(reportTitle, getEmpresaSistema(), reportFilter);
			
			if(agruparPor != null && agruparPor == 'T') 
				return "sucessoAgrupadoPorCertificacao";

			return Action.SUCCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
			addActionMessage("Não foi possível gerar relatório.");
			prepareImprimirCertificadosVencidosAVencer();
			return Action.INPUT;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void calculoCertificadoENaoCertificados(Long[] certificacoesIds, boolean agruparPorCertificacao){
		qtdTotalColaboradoresCertificados = 0;
		qtdTotalColaboradoresNaoCertificados = 0;
		Collection<ColaboradorCertificacao> colabsCertiticacaoOrdenados = new ArrayList<ColaboradorCertificacao>(); 
		colaboradorCertificacoes = new CollectionUtil().sortCollectionStringIgnoreCase(colaboradorCertificacoes, "nomeCurso");
		
		if(agruparPorCertificacao){
			colaboradorCertificacoes = new CollectionUtil().sortCollectionStringIgnoreCase(colaboradorCertificacoes, "colaborador.nome");
			colaboradorCertificacoes = new CollectionUtil<ColaboradorCertificacao>().sortCollectionStringIgnoreCase(colaboradorCertificacoes, "certificacao.nome");
		}else{
			colaboradorCertificacoes = new CollectionUtil<ColaboradorCertificacao>().sortCollectionStringIgnoreCase(colaboradorCertificacoes, "certificacao.nome");
			colaboradorCertificacoes = new CollectionUtil().sortCollectionStringIgnoreCase(colaboradorCertificacoes, "colaborador.nome");
		}
		
		for (Long certificacaoId : certificacoesIds) {
			Map <Long, Collection<ColaboradorCertificacao>> colabsCertificado = new HashMap<Long, Collection<ColaboradorCertificacao>>();
			Map <Long, Collection<ColaboradorCertificacao>> colabsCertificadoVencido = new HashMap<Long, Collection<ColaboradorCertificacao>>();
			Map <Long, Collection<ColaboradorCertificacao>> colabsNaoCertificado = new HashMap<Long, Collection<ColaboradorCertificacao>>();
			Collection<ColaboradorCertificacao> colabsCertiticados = new ArrayList<ColaboradorCertificacao>();
			Collection<ColaboradorCertificacao> colabsCertiticadosVencidos = new ArrayList<ColaboradorCertificacao>();
			Collection<ColaboradorCertificacao> colabsNãoCertiticados = new ArrayList<ColaboradorCertificacao>();
			Collection<ColaboradorCertificacao> colabsCertiticacoes = new ArrayList<ColaboradorCertificacao>();
			
			for(ColaboradorCertificacao colabcertificacao : colaboradorCertificacoes){
				colabcertificacao.setNomeCurso(colabcertificacao.getNomeCurso().replace(AvaliacaoPratica.SUFIXO_ORDENACAO_ULTIMA_POSICAO, ""));
				if(colabcertificacao.getCertificacao().getId().equals(certificacaoId)){
					Long colaboradorId = colabcertificacao.getColaborador().getId();
					
					if(colabcertificacao.getData() != null){
						if(colabcertificacao.getCertificacao().getPeriodicidade() == null || DateUtil.incrementaMes(colabcertificacao.getData(), colabcertificacao.getCertificacao().getPeriodicidade()).getTime() >= (new Date()).getTime()){
							if(!colabsCertificado.containsKey(colaboradorId))
								colabsCertificado.put(colaboradorId, new ArrayList<ColaboradorCertificacao>());
							
							colabsCertificado.get(colaboradorId).add(colabcertificacao);
						}else{
							if(!colabsCertificadoVencido.containsKey(colaboradorId))
								colabsCertificadoVencido.put(colaboradorId, new ArrayList<ColaboradorCertificacao>());
								
							colabsCertificadoVencido.get(colaboradorId).add(colabcertificacao);
						}
					}else{
						if(!colabsNaoCertificado.containsKey(colaboradorId))
							colabsNaoCertificado.put(colaboradorId, new ArrayList<ColaboradorCertificacao>());
						
						colabsNaoCertificado.get(colaboradorId).add(colabcertificacao);
					}
				}
			}

			if(agruparPorCertificacao){
				for(Long colaboradorId : colabsCertificado.keySet())
					colabsCertiticados.addAll(colabsCertificado.get(colaboradorId));
				colabsCertiticacoes.addAll(new CollectionUtil().sortCollectionStringIgnoreCase(colabsCertiticados, "colaborador.nome"));
				
				for(Long colaboradorId : colabsCertificadoVencido.keySet())
					colabsCertiticadosVencidos.addAll(colabsCertificadoVencido.get(colaboradorId));
				colabsCertiticacoes.addAll(new CollectionUtil().sortCollectionStringIgnoreCase(colabsCertiticadosVencidos, "colaborador.nome"));
				
				for(Long colaboradorId : colabsNaoCertificado.keySet())
					colabsNãoCertiticados.addAll(colabsNaoCertificado.get(colaboradorId));
				colabsCertiticacoes.addAll(new CollectionUtil().sortCollectionStringIgnoreCase(colabsNãoCertiticados, "colaborador.nome"));

				for (ColaboradorCertificacao colaboradorCertificacao : colabsCertiticacoes) {
					colaboradorCertificacao.setQtdColaboradorAprovado(((Integer)colabsCertificado.size()).toString());
					colaboradorCertificacao.setQtdColaboradorNaoAprovado(((Integer)(colabsCertificadoVencido.size() + colabsNaoCertificado.size())).toString());
				}
				
				colabsCertiticacaoOrdenados.addAll(colabsCertiticacoes);
			}

			qtdTotalColaboradoresCertificados = qtdTotalColaboradoresCertificados + colabsCertificado.size();
			qtdTotalColaboradoresNaoCertificados = qtdTotalColaboradoresNaoCertificados + colabsCertificadoVencido.size() + colabsNaoCertificado.size();
		}
		
		if(agruparPorCertificacao)
			colaboradorCertificacoes = colabsCertiticacaoOrdenados;
	}

	private void montaReportTitleAndFilter() 
	{
		reportTitle = "Relatório de Colaboradores por Certificações";
		reportTitle += "\nData do Relatório: " + DateUtil.formataDiaMesAno(new Date());

		reportFilter = "Colaboradores";
		
		if(colaboradorCertificado)
			reportFilter += " certificados"; 
		
		if(colaboradorCertificado && colaboradorNaoCertificado)
			reportFilter += " e";
		
		if(colaboradorNaoCertificado)
			reportFilter += " não certificados";

		if (dataIni != null && dataFim != null)
			reportFilter += "\nPeríodo certificado: " + DateUtil.formataDiaMesAno(dataIni) + " a " + DateUtil.formataDiaMesAno(dataFim);
		
		if (mesesCertificacoesAVencer != null && mesesCertificacoesAVencer != 0)
			reportFilter += "\nColaboradores com certificação a vencer em até " + mesesCertificacoesAVencer + " meses.";
		
		if(colaboradorCertificado)
			reportFilter += "\nQuantidade total de colaboradores certificados: " + qtdTotalColaboradoresCertificados;
		
		if(colaboradorNaoCertificado)
			reportFilter += "\nQuantidade total de colaboradores não certificados: " + qtdTotalColaboradoresNaoCertificados;
	}
	
	public String prepareReprocessaCertificacao(){
		certificacoesCheckList = certificacaoManager.populaCheckBox(getEmpresaSistema().getId());
		certificacoesCheckList = CheckListBoxUtil.marcaCheckListBox(certificacoesCheckList, certificacoesCheck);
		return Action.SUCCESS;
	}
	
	public String reprocessaCertificacao(){
		try {
			Date inicio = new Date();

			Long[] certificacoesIds = new Long[]{};

			if (certificacoesCheck == null || certificacoesCheck.length == 0)
				certificacoesIds = new CollectionUtil<Certificacao>().convertCollectionToArrayIds(certificacaoManager.findAllSelect(getEmpresaSistema().getId()));
			else
				certificacoesIds = LongUtil.arrayStringToArrayLong(certificacoesCheck);

			for (Long certificacaoId : certificacoesIds) 
				colaboradorCertificacaoManager.reprocessaCertificacao(certificacaoId, certificacaoManager);

			addActionSuccess("Reprocessamento das certificações realizadas com sucesso.");
			prepareReprocessaCertificacao();
			
			System.out.println(inicio + "  -   " + new Date());
			return Action.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Ocorreu um problema no reprocessamento das certificações.");
			prepareReprocessaCertificacao();
			return Action.INPUT;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String imprimirColaboradoresSemCertificacoes(){
		try {
			Long[] areaIds = LongUtil.arrayStringToArrayLong(areasCheck);
			Long[] estabelecimentoIds = LongUtil.arrayStringToArrayLong(estabelecimentosCheck);
			Long[] certificacoesIds = LongUtil.arrayStringToArrayLong(certificacoesCheck);
			Long[] colaboradoresIds = LongUtil.arrayStringToArrayLong(colaboradoresCheck);
			
			colaboradorCertificacoes = colaboradorCertificacaoManager.colaboradoresSemCertificacao(getEmpresaSistema().getId(), areaIds, estabelecimentoIds, colaboradoresIds, certificacoesIds, situacao);
			if(colaboradorCertificacoes.size() == 0){
				addActionMessage("Não existem dados para o filtro informado.");
				prepareImprimirCertificadosVencidosAVencer();
				return Action.INPUT;	
			}

			if((agruparPor != null && agruparPor == 'T'))
				colaboradorCertificacoes = new CollectionUtil<ColaboradorCertificacao>().sortCollectionStringIgnoreCase(colaboradorCertificacoes, "certificacao.nome");
			else
				colaboradorCertificacoes = new CollectionUtil().sortCollectionStringIgnoreCase(colaboradorCertificacoes, "colaborador.nome");
			
			reportTitle = "Relatório de Colaboradores Sem Certificações";
			reportTitle += "\nData do Relatório: " + DateUtil.formataDiaMesAno(new Date());

			parametros = RelatorioUtil.getParametrosRelatorio(reportTitle, getEmpresaSistema(), "");
			
			if(agruparPor != null && agruparPor == 'T') 
				return "sucessoAgrupadoPorCertificacao";

			return Action.SUCCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
			addActionMessage("Não foi possível gerar relatório.");
			prepareImprimirCertificadosVencidosAVencer();
			return Action.INPUT;
		}
	}
	
	public Object getModel()
	{
		return getCertificacao();
	}

	public Certificacao getCertificacao()
	{
		if (certificacao == null)
			certificacao = new Certificacao();
		return certificacao;
	}

	public void setCertificacao(Certificacao certificacao)
	{
		this.certificacao = certificacao;
	}

	public void setCertificacaoManager(CertificacaoManager certificacaoManager)
	{
		this.certificacaoManager = certificacaoManager;
	}

	public Collection<CheckBox> getCursoCheckList()
	{
		return cursosCheckList;
	}

	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
	}

	public Collection<CheckBox> getCursosCheckList()
	{
		return cursosCheckList;
	}

	public void setCursosCheck(String[] cursosCheck)
	{
		this.cursosCheck = cursosCheck;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}

	public Collection<CheckBox> getAvaliacoesPraticasCheckList() {
		return avaliacoesPraticasCheckList;
	}

	public void setAvaliacaoPraticaManager(
			AvaliacaoPraticaManager avaliacaoPraticaManager) {
		this.avaliacaoPraticaManager = avaliacaoPraticaManager;
	}

	public void setAvaliacoesPraticasCheck(String[] avaliacoesPraticasCheck) {
		this.avaliacoesPraticasCheck = avaliacoesPraticasCheck;
	}

	public Collection<Certificacao> getCertificacoes() {
		return certificacoes;
	}

	public void setAreasCheck(String[] areasCheck) {
		this.areasCheck = areasCheck;
	}

	public Collection<CheckBox> getAreasCheckList() {
		return areasCheckList;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck) {
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList() {
		return estabelecimentosCheckList;
	}

	public void setAreaOrganizacionalManager(
			AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setEstabelecimentoManager(
			EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public void setParametrosDoSistemaManager(
			ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Collection<ColaboradorCertificacao> getColaboradorCertificacoes() {
		return colaboradorCertificacoes;
	}

	public void setColaboradorCertificacaoManager(
			ColaboradorCertificacaoManager colaboradorCertificacaoManager) {
		this.colaboradorCertificacaoManager = colaboradorCertificacaoManager;
	}

	public Map<String, Object> getParametros() {
		return parametros;
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

	public void setCertificacoesCheck(String[] certificacoesCheck) {
		this.certificacoesCheck = certificacoesCheck;
	}

	public Collection<CheckBox> getCertificacoesCheckList() {
		return certificacoesCheckList;
	}

	public String getReportFilter() {
		return reportFilter;
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public boolean isColaboradorCertificado() {
		return colaboradorCertificado;
	}

	public void setColaboradorCertificado(boolean colaboradorCertificado) {
		this.colaboradorCertificado = colaboradorCertificado;
	}

	public boolean isColaboradorNaoCertificado() {
		return colaboradorNaoCertificado;
	}

	public void setColaboradorNaoCertificado(boolean colaboradorNaoCertificado) {
		this.colaboradorNaoCertificado = colaboradorNaoCertificado;
	}

	public Integer getMesesCertificacoesAVencer() {
		return mesesCertificacoesAVencer;
	}

	public void setMesesCertificacoesAVencer(Integer mesesCertificacoesAVencer) {
		this.mesesCertificacoesAVencer = mesesCertificacoesAVencer;
	}

	public void setColaboradoresCheck(String[] colaboradoresCheck) {
		this.colaboradoresCheck = colaboradoresCheck;
	}

	public Collection<CheckBox> getColaboradoresCheckList() {
		return colaboradoresCheckList;
	}

	public Character getAgruparPor() {
		return agruparPor;
	}

	public void setAgruparPor(Character agruparPor) {
		this.agruparPor = agruparPor;
	}

	public boolean isAlterouCertificacao() {
		return alterouCertificacao;
	}

	public void setAlterouCertificacao(boolean alterouCertificacao) {
		this.alterouCertificacao = alterouCertificacao;
	}

	public Map getSituacaos() {
		return situacaos;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getSituacao() {
		return situacao;
	}
}