/* Autor: Robertson Freitas
 * Data: 22/06/2006
 * Requisito: RFA0012 */
package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.MotivoDemissaoManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

public class ColaboradorDesligaAction extends MyActionSupport implements ModelDriven
{
	private static final long serialVersionUID = 1L;
	private ColaboradorManager colaboradorManager;
	private MotivoDemissaoManager motivoDemissaoManager;
	private Colaborador colaborador;
	private Collection<MotivoDemissao> motivoDemissaos;
	private Map<String,Object> parametros = new HashMap<String, Object>();

	private boolean desligado;
	private Date dataDesligamento;
	private String observacaoDemissao;
	private MotivoDemissao motDemissao;
	
	private Comissao comissao;

	private String nomeBusca;
	private String cpfBusca;
	private boolean integraAc;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		integraAc = getEmpresaSistema().isAcIntegra();
		motivoDemissaos = motivoDemissaoManager.findAllSelect(getEmpresaSistema().getId());
		colaborador = colaboradorManager.findColaboradorById(colaborador.getId());
		motDemissao = colaborador.getMotivoDemissao();
		return Action.SUCCESS;
	}

	public String prepareDesliga() throws Exception
	{
		integraAc = getEmpresaSistema().isAcIntegra();
		motivoDemissaos = motivoDemissaoManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"motivo asc"});

		return Action.SUCCESS;
	}
	
	public String desliga() throws Exception
	{
		try {
			if (dataDesligamento.before(colaborador.getDataAdmissao()))
				throw new Exception("Data de desligamento anterior à data de admissão");
			
			colaboradorManager.desligaColaborador(desligado, dataDesligamento, observacaoDemissao, motDemissao.getId(), colaborador.getId(), false);
	
			addActionSuccess("Colaborador desligado com sucesso.");
		
		} catch (Exception e) {
			addActionError(e.getMessage());
			prepareUpdate();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}
	
	public String solicitacaoDesligamento() throws Exception
	{
		try {
			if (dataDesligamento.before(colaborador.getDataAdmissao()))
				throw new Exception("Data de desligamento anterior à data de admissão");
			
			observacaoDemissao = "Solicitado por: " + getUsuarioLogado().getNome() + ".  " +  observacaoDemissao;
			
			colaboradorManager.solicitacaoDesligamentoAc(dataDesligamento, observacaoDemissao, motDemissao.getId(), colaborador.getId(), getEmpresaSistema());
			
			addActionMessage("Solicitação de desligamento enviada com sucesso.");
			
		} catch (Exception e) {
			addActionError(e.getMessage());
			prepareUpdate();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String reLiga() throws Exception
	{
		try
		{
			colaboradorManager.validaQtdCadastros();			
			colaboradorManager.religaColaborador(colaborador.getId());
			
			addActionMessage("Colaborador religado com sucesso.");
		} 
		catch (FortesException e) 
		{
			addActionMessage(e.getMessage());
		} 
		catch (Exception e) 
		{
			addActionError(e.getMessage());
		}
				
		return Action.SUCCESS;
	}

	public String imprimiSolicitacaoDesligamento() throws Exception
	{
		try {
			String titulo = "Desligar Colaborador";
			
			if(getEmpresaSistema().isAcIntegra())
				titulo = "Solicitação de Desligamento de Colaborador no AC Pessoal";
			
			parametros = RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), "");
			MotivoDemissao motivo = motivoDemissaoManager.findById(motDemissao.getId());
			
			//migue para funcionar reports	
			motivoDemissaos = new ArrayList<MotivoDemissao>();
			motivoDemissaos.add(motivo);

			parametros.put("EMPRESANOME", getEmpresaSistema().getNome());
			parametros.put("COLABORADORNOME", colaborador.getNome());
			parametros.put("DATADESLIGAMENTO", DateUtil.formataDiaMesAno(dataDesligamento));
			parametros.put("MOTIVODEMISSAO", motivo.getMotivo());
			parametros.put("OBSDEMISSAO", observacaoDemissao);

			return Action.SUCCESS;
		} catch (Exception e) {
			addActionError("Erro ao gerar relatório");
			e.printStackTrace();
			
			return Action.INPUT;
		}
	}

	public Colaborador getColaborador()
	{
		if (colaborador == null)
		{
			colaborador = new Colaborador();
		}
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Object getModel()
	{
		return getColaborador();
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Date getDataDesligamento()
	{
		return dataDesligamento;
	}

	public void setDataDesligamento(Date dataDesligamento)
	{
		this.dataDesligamento = dataDesligamento;
	}

	public boolean isDesligado()
	{
		return desligado;
	}

	public void setDesligado(boolean desligado)
	{
		this.desligado = desligado;
	}

	public String getCpfBusca() {
		return cpfBusca;
	}

	public void setCpfBusca(String cpfBusca) {
		this.cpfBusca = cpfBusca;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		 this.nomeBusca = StringUtil.retiraAcento(nomeBusca);
	}

	public Collection<MotivoDemissao> getMotivoDemissaos()
	{
		return motivoDemissaos;
	}

	public void setMotivoDemissaos(Collection<MotivoDemissao> motivoDemissaos)
	{
		this.motivoDemissaos = motivoDemissaos;
	}

	public void setMotivoDemissaoManager(MotivoDemissaoManager motivoDemissaoManager)
	{
		this.motivoDemissaoManager = motivoDemissaoManager;
	}

	public MotivoDemissao getMotDemissao()
	{
		return motDemissao;
	}

	public void setMotDemissao(MotivoDemissao motivoDemissao)
	{
		this.motDemissao = motivoDemissao;
	}

	public boolean isIntegraAc() {
		return integraAc;
	}

	public String getObservacaoDemissao() {
		return observacaoDemissao;
	}

	public void setObservacaoDemissao(String observacaoDemissao) {
		this.observacaoDemissao = observacaoDemissao;
	}
	
	public Date getDataAtual() {
		return new Date();
	}

	public Comissao getComissao() {
		return comissao;
	}

	public void setComissao(Comissao comissao) {
		this.comissao = comissao;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}
}