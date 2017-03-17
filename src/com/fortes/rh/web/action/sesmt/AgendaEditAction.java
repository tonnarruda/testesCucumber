package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AgendaManager;
import com.fortes.rh.business.sesmt.EventoManager;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class AgendaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private AgendaManager agendaManager;
	@Autowired private EventoManager eventoManager;
	@Autowired private EstabelecimentoManager estabelecimentoManager;

	private Agenda agenda;
	
	private Collection<Agenda> agendas;
	private Collection<Evento> eventos;
	private Collection<Estabelecimento> estabelecimentos;
	
	private String dataMesAno;
	
	//filtro listagem
	private String dataMesAnoIni; 
	private String dataMesAnoFim;
	private Estabelecimento estabelecimento;

	private Integer periodicidade = 1;
	private Integer tipoPeriodo = 0;// 0 = Mensal ou 1 = Anual
	private Integer qtdPeriodo = 1;
	
	private void prepare() throws Exception
	{
		if(agenda != null && agenda.getId() != null)
		{
			agenda = (Agenda) agendaManager.findByIdProjection(agenda.getId());
			dataMesAno = DateUtil.formataMesAno(agenda.getData());
		}

		eventos = eventoManager.findAll(new String[]{"nome"});
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
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

	public String insert() throws Exception
	{
		agenda.setDataMesAno(dataMesAno);
		agendaManager.save(agenda, qtdPeriodo, periodicidade, tipoPeriodo);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		agenda.setDataMesAno(dataMesAno);
		agendaManager.update(agenda);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId()); 
		setDatasMesAnoAtual();
		agendas = agendaManager.findByPeriodo(DateUtil.criarDataMesAno(dataMesAnoIni), DateUtil.criarDataMesAno(dataMesAnoFim), getEmpresaSistema().getId(), estabelecimento);
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		agendaManager.remove(agenda.getId());
		addActionMessage("Agenda excluída com sucesso.");

		return Action.SUCCESS;
	}
	
	public void setDatasMesAnoAtual()
	{
		String ano = "";
		if(StringUtils.isBlank(dataMesAnoIni) || StringUtils.isBlank(dataMesAnoFim))
			ano = DateUtil.getAno();
		
		if(StringUtils.isBlank(dataMesAnoIni))
			dataMesAnoIni = "01/" + ano;
			
		if(StringUtils.isBlank(dataMesAnoFim))
			dataMesAnoFim = "12/" + ano;
	}
	
	public Agenda getAgenda()
	{
		if(agenda == null)
			agenda = new Agenda();
		return agenda;
	}

	public void setAgenda(Agenda agenda)
	{
		this.agenda = agenda;
	}

	public Collection<Agenda> getAgendas()
	{
		return agendas;
	}
	public String getDataMesAnoFim()
	{
		return dataMesAnoFim;
	}

	public void setDataMesAnoFim(String dataMesAnoFim)
	{
		this.dataMesAnoFim = dataMesAnoFim;
	}

	public Collection<Evento> getEventos()
	{
		return eventos;
	}
	
	public String getDataMesAno()
	{
		return dataMesAno;
	}

	public void setDataMesAno(String dataMesAno)
	{
		this.dataMesAno = dataMesAno;
	}

	public Integer getPeriodicidade()
	{
		return periodicidade;
	}

	public void setPeriodicidade(Integer periodicidade)
	{
		this.periodicidade = periodicidade;
	}

	public Integer getTipoPeriodo()
	{
		return tipoPeriodo;
	}

	public void setTipoPeriodo(Integer tipoPeriodo)
	{
		this.tipoPeriodo = tipoPeriodo;
	}

	public Integer getQtdPeriodo()
	{
		return qtdPeriodo;
	}

	public void setQtdPeriodo(Integer qtdPeriodo)
	{
		this.qtdPeriodo = qtdPeriodo;
	}

	public Collection<Estabelecimento> getEstabelecimentos()
	{
		return estabelecimentos;
	}

	public String getDataMesAnoIni() {
		return dataMesAnoIni;
	}

	public void setDataMesAnoIni(String dataMesAnoIni) {
		this.dataMesAnoIni = dataMesAnoIni;
	}
	
	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
}