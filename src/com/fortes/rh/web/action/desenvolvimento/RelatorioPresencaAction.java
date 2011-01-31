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
	private Collection<ListaDePresenca> listaDePresencas;

	private ColaboradorTurma colaboradorTurma;

	private Map parametros = new HashMap();

	private String[] diasCheck;
	private Collection<CheckBox> diasCheckList = new ArrayList<CheckBox>();
	private int qtdLinhas = 20;
	private boolean exibirNomeComercial;
	private boolean exibirCargo;
	private boolean exibirEstabelecimento;
	private boolean exibirAssinatura;
	private boolean exibirNota;
	private boolean exibirConteudoProgramatico;
	private boolean exibirCriteriosAvaliacao;

	@SuppressWarnings("unchecked")
	public String execute()
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
			colaboradorTurmas = colaboradorTurmaManager.findByTurma(colaboradorTurma.getTurma().getId(), null);
			colaboradorTurmas = colaboradorTurmaManager.montaColunas(colaboradorTurmas, exibirNomeComercial, exibirCargo, exibirEstabelecimento, exibirAssinatura);

			listaDePresencas = new ArrayList<ListaDePresenca>();
			for (String dia : diasCheck)
			{
				colaboradorTurmas = colaboradorPresencaManager.preparaLinhaEmBranco(colaboradorTurmas, qtdLinhas);
				ListaDePresenca listaDePresenca = new ListaDePresenca(dia, colaboradorTurmas);
				listaDePresencas.add(listaDePresenca);
			}

			parametros = RelatorioUtil.getParametrosRelatorio("Lista de Frequência", getEmpresaSistema(), "");
			parametros = montaParametros(turma);

		}
		catch (Exception e)
		{
			e.printStackTrace();

			return Action.ERROR;
		}
		return Action.SUCCESS;
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
		parametros.put("cargaHoraria", turma.getCurso().getCargaHoraria());
		parametros.put("dataPrevIni", DateUtil.formataDiaMesAno(turma.getDataPrevIni()));
		parametros.put("dataPrevFim", DateUtil.formataDiaMesAno(turma.getDataPrevFim()));
		parametros.put("SUBREPORT_DIR", path);
		parametros.put("exibirNota", exibirNota);
		parametros.put("exibirCriteriosAvaliacao", exibirCriteriosAvaliacao);
		parametros.put("exibirConteudoProgramatico", exibirConteudoProgramatico);
		
		
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
		
		if(exibirAssinatura)
		{
			if(parametros.get("coluna02") == null)
				parametros.put("coluna02", "Assinatura");
			else
				parametros.put("coluna03", "Assinatura");
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
}
