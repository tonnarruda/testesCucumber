package com.fortes.rh.web.action.desenvolvimento;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupport;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class RelatorioPresencaAction extends MyActionSupport
{
	private CursoManager cursoManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private TurmaManager turmaManager;
	private ColaboradorPresencaManager colaboradorPresencaManager;

	private Collection<Curso> cursos;
	private Collection<ColaboradorTurma> colaboradorTurmas;
	private Collection<Turma> turmas;
	private Collection<ListaDePresenca> listaDePresencas = new ArrayList<ListaDePresenca>();;

	private ColaboradorTurma colaboradorTurma;

	private Map parametros = new HashMap();

	private String[] diasCheck;
	private Collection<CheckBox> diasCheckList = new ArrayList<CheckBox>();
	private int qtdLinhas = 20;
	private boolean exibirCnpj;
	private boolean exibirRazaoSocial;
	private boolean exibirEndereco;
	private boolean exibirNomeComercial;
	private boolean exibirCargo;
	private boolean exibirCPF;
	private boolean exibirEstabelecimento;
	private boolean exibirArea;
	private boolean exibirAssinatura;
	private boolean exibirNota;
	private boolean exibirConteudoProgramatico;
	private boolean exibirCriteriosAvaliacao;
	private boolean quebraPaginaEstabelecimento;

	@SuppressWarnings("unchecked")
	public String imprimirRelatorio()
	{
		Turma turma = turmaManager.findById(colaboradorTurma.getTurma().getId());
		if(!getEmpresaSistema().getId().equals(turma.getCurso().getEmpresa().getId()))
		{
			setActionMsg("O Curso solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			prepareRelatorio();
			return "acessonegado";
		}

		try
		{
			colaboradorTurmas = colaboradorTurmaManager.findByTurma(colaboradorTurma.getTurma().getId(), null, null, null);
			colaboradorTurmas = colaboradorTurmaManager.montaColunas(colaboradorTurmas, exibirNomeComercial, exibirCargo, exibirEstabelecimento, exibirAssinatura, exibirArea, exibirCPF);

			if(quebraPaginaEstabelecimento)
				montaListaDePresencaPorEstabelecimento();
			else
				montaListaDePresenca(null, (ArrayList<ColaboradorTurma> )colaboradorTurmas);

			parametros = RelatorioUtil.getParametrosRelatorio("Lista de Presença", getEmpresaSistema(), "");
			parametros = montaParametros(turma);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível gerar o relatório");
			return Action.ERROR;
		}
		
		if(quebraPaginaEstabelecimento)
			return "relatorioPorEstabelecimento";
		 
		return Action.SUCCESS;
	}

	private void montaListaDePresencaPorEstabelecimento() 
	{
		Collection<Long> estabelecimentoIds = colaboradorTurmaManager.findIdEstabelecimentosByTurma(colaboradorTurma.getTurma().getId(), getEmpresaSistema().getId());
		
		for (Long estabelecimentoId : estabelecimentoIds) 
		{
			ArrayList<ColaboradorTurma> listaColaboradorTurmaPorEstabelecimento = new ArrayList<ColaboradorTurma>();
			for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) {
				if (estabelecimentoId.equals(colaboradorTurma.getColaborador().getHistoricoColaborador().getEstabelecimento().getId())) {
					listaColaboradorTurmaPorEstabelecimento.add(colaboradorTurma);
				}
			}
			
			montaListaDePresenca(estabelecimentoId, listaColaboradorTurmaPorEstabelecimento);
		}
	}

	private void montaListaDePresenca(Long estabelecimentoId, ArrayList<ColaboradorTurma> listaColaboradorTurmaPorEstabelecimento) 
	{
		Collection<ColaboradorTurma> colaboradorTurmasTemp;
	
		for (String dia : diasCheck)
		{
			colaboradorTurmasTemp = colaboradorPresencaManager.preparaLinhaEmBranco(listaColaboradorTurmaPorEstabelecimento, qtdLinhas, estabelecimentoId);
			ListaDePresenca listaDePresenca = new ListaDePresenca(dia, colaboradorTurmasTemp);
			listaDePresencas.add(listaDePresenca);
		}
	}

	@SuppressWarnings("unchecked")
	private Map montaParametros(Turma turma)
	{
		String path = ServletActionContext.getServletContext().getRealPath("/WEB-INF/report/")+File.separator;

		parametros.put("enderecoEmpresa", getEmpresaSistema().getEndereco());
		parametros.put("curso", 	turma.getCurso().getNome());
		parametros.put("turma", 	turma.getDescricao());
		parametros.put("instrutor", turma.getInstrutor());
		parametros.put("horario", turma.getHorario());
		parametros.put("cargaHoraria", turma.getCurso().getCargaHorariaMinutos());
		parametros.put("dataPrevIni", DateUtil.formataDiaMesAno(turma.getDataPrevIni()));
		parametros.put("dataPrevFim", DateUtil.formataDiaMesAno(turma.getDataPrevFim()));
		parametros.put("SUBREPORT_DIR", path);
		parametros.put("exibirNota", exibirNota);
		parametros.put("exibirCnpj", exibirCnpj);
		parametros.put("exibirRazaoSocial", exibirRazaoSocial);
		parametros.put("exibirEndereco", exibirEndereco);
		parametros.put("exibirCriteriosAvaliacao", exibirCriteriosAvaliacao);
		parametros.put("exibirConteudoProgramatico", exibirConteudoProgramatico);
		parametros.put("quebraPaginaEstabelecimento", quebraPaginaEstabelecimento);
		
		
		if(exibirConteudoProgramatico)
		{
			parametros.put("labelLinha01", "Conteúdo Programático:");			
			parametros.put("linha01", turma.getCurso().getConteudoProgramatico());
		}
		
		if(exibirCriteriosAvaliacao)
		{
			if(parametros.get("labelLinha01") == null)
			{
				parametros.put("labelLinha01", "Critérios de Avaliação:");							
				parametros.put("linha01", turma.getCurso().getCriterioAvaliacao());
			}
			else
			{
				parametros.put("labelLinha02", "Critérios de Avaliação:");											
				parametros.put("linha02", turma.getCurso().getCriterioAvaliacao());
			}
		}

		if(exibirCargo)
			parametros.put("coluna02", "Cargo");
		
		if(exibirEstabelecimento)
		{
			if(parametros.get("coluna02") == null)
				parametros.put("coluna02", "Estabelecimento");
			else
				parametros.put("coluna03", "Estabelecimento");
		}
		
		if(exibirArea)
		{
			if(parametros.get("coluna02") == null)
				parametros.put("coluna02", "Área Organizacional");
			else
				parametros.put("coluna03", "Área Organizacional");
		}
		
		if(exibirAssinatura)
		{
			if(parametros.get("coluna02") == null)
				parametros.put("coluna02", "Assinatura");
			else
				parametros.put("coluna03", "Assinatura");
		}
		
		if(exibirCPF)
		{
			if(parametros.get("coluna02") == null)
				parametros.put("coluna02", "CPF");
			else
				parametros.put("coluna03", "CPF");
		}
		
		return parametros;
	}

	public String prepareRelatorio()
	{
		cursos = cursoManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"nome"});
		diasCheckList = new ArrayList<CheckBox>();
		return Action.SUCCESS;
	}

	class ListaDePresenca
	{
		private String data;
		private Collection<ColaboradorTurma> colaboradorTurmas;

		ListaDePresenca(String data, Collection<ColaboradorTurma> colaboradorTurmas)
		{
			this.data = data;
			this.colaboradorTurmas = colaboradorTurmas;
		}

		public Collection<ColaboradorTurma> getColaboradorTurmas()
		{
			return colaboradorTurmas;
		}

		public void setColaboradorTurmas(Collection<ColaboradorTurma> colaboradorTurmas)
		{
			this.colaboradorTurmas = colaboradorTurmas;
		}

		public String getData()
		{
			return data;
		}

		public void setData(String data)
		{
			this.data = data;
		}
	}
	public ColaboradorTurma getColaboradorTurma()
	{
		return colaboradorTurma;
	}

	public void setColaboradorTurma(ColaboradorTurma colaboradorTurma)
	{
		this.colaboradorTurma = colaboradorTurma;
	}

	public Collection<ColaboradorTurma> getColaboradorTurmas()
	{
		return colaboradorTurmas;
	}

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager)
	{
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}

	public Map getParametros()
	{
		return parametros;
	}

	public Collection<Curso> getCursos()
	{
		return cursos;
	}

	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
	}

	public void setParametros(Map parametros)
	{
		this.parametros = parametros;
	}

	public void setTurmaManager(TurmaManager turmaManager)
	{
		this.turmaManager = turmaManager;
	}

	public Collection<Turma> getTurmas()
	{
		return turmas;
	}

	public Collection<CheckBox> getDiasCheckList()
	{
		return diasCheckList;
	}

	public void setDiasCheckList(Collection<CheckBox> diasCheckList)
	{
		this.diasCheckList = diasCheckList;
	}

	public String[] getDiasCheck()
	{
		return diasCheck;
	}

	public void setDiasCheck(String[] diasCheck)
	{
		this.diasCheck = diasCheck;
	}

	public Collection<ListaDePresenca> getListaDePresencas()
	{
		return listaDePresencas;
	}

	public void setColaboradorPresencaManager(ColaboradorPresencaManager colaboradorPresencaManager)
	{
		this.colaboradorPresencaManager = colaboradorPresencaManager;
	}

	public int getQtdLinhas()
	{
		return qtdLinhas;
	}

	public void setQtdLinhas(int qtdLinhas)
	{
		this.qtdLinhas = qtdLinhas;
	}

	public boolean isExibirAssinatura()
	{
		return exibirAssinatura;
	}

	public void setExibirAssinatura(boolean exibirAssinatura)
	{
		this.exibirAssinatura = exibirAssinatura;
	}

	public boolean isExibirCargo()
	{
		return exibirCargo;
	}

	public void setExibirCargo(boolean exibirCargo)
	{
		this.exibirCargo = exibirCargo;
	}

	public boolean isExibirEstabelecimento()
	{
		return exibirEstabelecimento;
	}

	public void setExibirEstabelecimento(boolean exibirEstabelecimento)
	{
		this.exibirEstabelecimento = exibirEstabelecimento;
	}

	public boolean isExibirNota()
	{
		return exibirNota;
	}

	public void setExibirNota(boolean exibirNota)
	{
		this.exibirNota = exibirNota;
	}

	public boolean isExibirNomeComercial()
	{
		return exibirNomeComercial;
	}

	public void setExibirNomeComercial(boolean exibirNomeComercial)
	{
		this.exibirNomeComercial = exibirNomeComercial;
	}

	public boolean isExibirConteudoProgramatico()
	{
		return exibirConteudoProgramatico;
	}

	public void setExibirConteudoProgramatico(boolean exibirConteudoProgramatico)
	{
		this.exibirConteudoProgramatico = exibirConteudoProgramatico;
	}

	public boolean isExibirCriteriosAvaliacao()
	{
		return exibirCriteriosAvaliacao;
	}

	public void setExibirCriteriosAvaliacao(boolean exibirCriteriosAvaliacao)
	{
		this.exibirCriteriosAvaliacao = exibirCriteriosAvaliacao;
	}

	public boolean isExibirArea() {
		return exibirArea;
	}

	public void setExibirArea(boolean exibirArea) {
		this.exibirArea = exibirArea;
	}

	public boolean isExibirCnpj() {
		return exibirCnpj;
	}

	public void setExibirCnpj(boolean exibirCnpj) {
		this.exibirCnpj = exibirCnpj;
	}

	public boolean isExibirRazaoSocial() {
		return exibirRazaoSocial;
	}

	public void setExibirRazaoSocial(boolean exibirRazaoSocial) {
		this.exibirRazaoSocial = exibirRazaoSocial;
	}

	public boolean isExibirEndereco() {
		return exibirEndereco;
	}
	
	public boolean isExibirCPF() {
		return exibirCPF;
	}

	public void setExibirCPF(boolean exibirCPF) {
		this.exibirCPF = exibirCPF;
	}

	public void setExibirEndereco(boolean exibirEndereco) {
		this.exibirEndereco = exibirEndereco;
	}

	public boolean isQuebraPaginaEstabelecimento() {
		return quebraPaginaEstabelecimento;
	}

	public void setQuebraPaginaEstabelecimento(boolean quebraPaginaEstabelecimento) {
		this.quebraPaginaEstabelecimento = quebraPaginaEstabelecimento;
	}
}
