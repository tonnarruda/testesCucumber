package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="colaboradorturma_sequence", allocationSize=1)
public class ColaboradorTurma extends AbstractModel implements Serializable
{
	@ManyToOne
	private Colaborador colaborador;
	@ManyToOne
	private PrioridadeTreinamento prioridadeTreinamento;
	@ManyToOne
	private Turma turma;
	@ManyToOne
	private Curso curso;
	@ManyToOne
	private DNT dnt;
	@OneToMany(fetch = FetchType.LAZY, mappedBy="colaboradorTurma")
	private Collection<AproveitamentoAvaliacaoCurso> aproveitamentoAvaliacaoCursos;
	@OneToMany(fetch = FetchType.LAZY, mappedBy="colaboradorTurma")
	private Collection<ColaboradorPresenca> colaboradorPresencas;

	private boolean origemDnt;
	private boolean aprovado;

	@Transient
	private Double custoRateado;

	@Transient
	private String tempoSemCurso;
	@Transient
	private String coluna01RelatorioPresenca;
	@Transient
	private String coluna02RelatorioPresenca;
	@Transient
	private String coluna03RelatorioPresenca;

	@Transient
	private boolean respondeuAvaliacaoTurma;

	@Transient
	private Double valorAvaliacao;
	@Transient
	private Date diaPresente;

	@Transient
	private Integer totalDias;
	@Transient
	private Integer qtdPresenca;
	@Transient
	private Integer qtdAvaliacoesCurso;
	@Transient
	private Integer qtdAvaliacoesAprovadasPorNota;
	@Transient
	private Integer qtdRespostasAvaliacaoTurma;
	@Transient
	private Double nota;
	@Transient
	private Collection<MatrizTreinamento> matrizTreinamentos;
	
	public ColaboradorTurma() {	}

	public ColaboradorTurma(Long id)
	{
		setId(id);
	}
	
	public ColaboradorTurma(Long id, Long colaboradorId, String colaboradorNome, String colaboradorNomeComercial, String colaboradorMatricula, Long colaboradorQuestionarioId)
	{
		this.setId(id);
		
		this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);
		this.colaborador.setNome(colaboradorNome);
		this.colaborador.setNomeComercial(colaboradorNomeComercial);
		this.colaborador.setMatricula(colaboradorMatricula);
		this.respondeuAvaliacaoTurma = colaboradorQuestionarioId != null;
	}

	//findRelatorioSemTreinamento
	public ColaboradorTurma(Long colaboradorId, String colaboradorNome, String colaboradorMatricula, Long areaId, String areaNome, String estabelecimentoNome, String empresaNome, Date dataPrevFim )
	{
		this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);
		this.colaborador.setNome(colaboradorNome);
		this.colaborador.setMatricula(colaboradorMatricula);

		this.colaborador.setAreaOrganizacional(new AreaOrganizacional());
		this.colaborador.getAreaOrganizacional().setId(areaId);
		this.colaborador.getAreaOrganizacional().setNome(areaNome);

		this.colaborador.setEstabelecimento(new Estabelecimento());
		this.colaborador.getEstabelecimento().setNome(estabelecimentoNome);
		
		this.colaborador.setEmpresa(new Empresa());
		this.colaborador.getEmpresa().setNome(empresaNome);
		
		this.setTurma(new Turma());
		this.getTurma().setDataPrevFim(dataPrevFim);
	}

	public ColaboradorTurma(Long colaboradorId, String colaboradorNome, String colaboradorMatricula, Long areaId, String estabelecimentoNome, String turmaDescricao, Date dataPrevIni, Date dataPrevFim, Long id, Boolean aprovado)
	{
		this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);
		this.colaborador.setNome(colaboradorNome);
		this.colaborador.setMatricula(colaboradorMatricula);

		this.colaborador.setAreaOrganizacional(new AreaOrganizacional());
		this.colaborador.getAreaOrganizacional().setId(areaId);

		this.colaborador.setEstabelecimento(new Estabelecimento());
		this.colaborador.getEstabelecimento().setNome(estabelecimentoNome);

		this.turma = new Turma();
		this.turma.setDescricao(turmaDescricao);
		this.turma.setDataPrevIni(dataPrevIni);
		this.turma.setDataPrevFim(dataPrevFim);
		this.setId(id);
//		this.setAprovado(aprovado);
	}

	public ColaboradorTurma(Long colaboradorId, String colaboradorNome, String colaboradorMatricula, Long areaId, String estabelecimentoNome, Long turmaId, String turmaDescricao, Date dataPrevIni, Date dataPrevFim, Long id, Boolean aprovado, Long cursoId, String cursoNome, Double percentualMinimoFrequencia)
	{
		this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);
		this.colaborador.setNome(colaboradorNome);
		this.colaborador.setMatricula(colaboradorMatricula);
		
		this.colaborador.setAreaOrganizacional(new AreaOrganizacional());
		this.colaborador.getAreaOrganizacional().setId(areaId);
		
		this.colaborador.setEstabelecimento(new Estabelecimento());
		this.colaborador.getEstabelecimento().setNome(estabelecimentoNome);
		
		this.turma = new Turma();
		this.turma.setId(turmaId);
		this.turma.setDescricao(turmaDescricao);
		this.turma.setDataPrevIni(dataPrevIni);
		this.turma.setDataPrevFim(dataPrevFim);
		this.setId(id);
//		this.setAprovado(aprovado);
		
		this.curso = new Curso();
		this.curso.setId(cursoId);
		this.curso.setNome(cursoNome);
		this.curso.setPercentualMinimoFrequencia(percentualMinimoFrequencia);
	}

	// findByTurma
	public ColaboradorTurma(Long id, Long prioridadeTreinamentoId, Long turmaId, Long colaboradorId, String colaboradorNome, String colaboradorNomeComercial, String colaboradorMatricula, String colaboradorCPF,Long areaOrganizacionalId, String areaOrganizacionalNome, boolean aprovado, Estabelecimento estabelecimento, String faixaSalarialNome, String cargoNome, Long empresaId, String empresaNome, String empresaRazaoSocial, String empresaCnpj, Integer qtdRespostasAvaliacaoTurma)
	{
		this.setId(id);
		this.setTurmaId(turmaId);
		
		this.aprovado = aprovado;
		this.qtdRespostasAvaliacaoTurma = qtdRespostasAvaliacaoTurma;

		this.prioridadeTreinamento = new PrioridadeTreinamento();
		this.prioridadeTreinamento.setId(prioridadeTreinamentoId);

		this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);
		this.colaborador.setNome(colaboradorNome);
		this.colaborador.setNomeComercial(colaboradorNomeComercial);
		this.colaborador.setMatricula(colaboradorMatricula);
		this.colaborador.setColaboradorCPF(colaboradorCPF);
		//não sei quem usa, ta pegando do historico tb
		this.colaborador.setAreaOrganizacional(new AreaOrganizacional());
		this.colaborador.getAreaOrganizacional().setId(areaOrganizacionalId);
		this.colaborador.getAreaOrganizacional().setNome(areaOrganizacionalNome);

		if(this.colaborador.getEmpresa() == null)
			this.colaborador.setEmpresa(new Empresa());
		
		this.colaborador.getEmpresa().setId(empresaId);
		this.colaborador.getEmpresa().setNome(empresaNome);
		this.colaborador.getEmpresa().setRazaoSocial(empresaRazaoSocial);
		this.colaborador.getEmpresa().setCnpj(empresaCnpj);
		
		if(this.colaborador.getHistoricoColaborador() == null)
			this.colaborador.setHistoricoColaborador(new HistoricoColaborador());

		if(this.colaborador.getHistoricoColaborador().getAreaOrganizacional() == null)
			this.colaborador.getHistoricoColaborador().setAreaOrganizacional(new AreaOrganizacional());
		this.colaborador.getHistoricoColaborador().getAreaOrganizacional().setNome(areaOrganizacionalNome);
		
		estabelecimento.setEmpresa(colaborador.getEmpresa());
		this.colaborador.getHistoricoColaborador().setEstabelecimento(estabelecimento);

		if(this.colaborador.getHistoricoColaborador().getFaixaSalarial() == null)
			this.colaborador.getHistoricoColaborador().setFaixaSalarial(new FaixaSalarial());
		this.colaborador.getHistoricoColaborador().getFaixaSalarial().setNome(faixaSalarialNome);

		if(this.colaborador.getHistoricoColaborador().getFaixaSalarial().getCargo() == null)
			this.colaborador.getHistoricoColaborador().getFaixaSalarial().setCargo(new Cargo());
		this.colaborador.getHistoricoColaborador().getFaixaSalarial().getCargo().setNome(cargoNome);
	}

	public ColaboradorTurma(Long id, Long colaboradorId, String colaboradorNomeComercial, String estabelecimentoNome, String areaOrganizacionalNome, Turma turma, Long cursoId, String cursoNome, Integer cursoCargaHoraria)
	{
		this.setId(id);

		this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);
		this.colaborador.setNomeComercial(colaboradorNomeComercial);

		this.colaborador.setAreaOrganizacional(new AreaOrganizacional());
		this.colaborador.getAreaOrganizacional().setNome(areaOrganizacionalNome);

		this.colaborador.setEstabelecimento(new Estabelecimento());
		this.colaborador.getEstabelecimento().setNome(estabelecimentoNome);

		this.turma = turma;

		this.curso = new Curso();
		this.curso.setId(cursoId);
		this.curso.setNome(cursoNome);
		this.curso.setCargaHoraria(cursoCargaHoraria);
	}

	public ColaboradorTurma(Long id, Integer prioridadeTreinamentoNumero, String prioridadeTreinamentoSigla, Long colaboradorId, String colaboradorNomeComercial, Long turmaId, Integer cargaHoraria, Double custo, Long cursoId, String cursoNome)
	{
		this.setId(id);

		this.prioridadeTreinamento = new PrioridadeTreinamento();
		this.prioridadeTreinamento.setSigla(prioridadeTreinamentoSigla);
		this.prioridadeTreinamento.setNumero(prioridadeTreinamentoNumero);

		this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);
		this.colaborador.setNomeComercial(colaboradorNomeComercial);

		this.turma = new Turma();
		this.turma.setId(turmaId);
		this.turma.setCusto(custo);

		this.curso = new Curso();
		this.curso.setId(cursoId);
		this.curso.setNome(cursoNome);
		this.curso.setCargaHoraria(cargaHoraria);
	}

	public ColaboradorTurma(Long id, Long colaboradorId, String colaboradorNomeComercial, Long turmaId, String turmaDescricao, String cursoNome, String areaOrganizacionalNome)
	{
		this.setId(id);

		this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);
		this.colaborador.setNomeComercial(colaboradorNomeComercial);

		if(this.colaborador.getAreaOrganizacional() == null)
			this.colaborador.setAreaOrganizacional(new AreaOrganizacional());
		this.colaborador.getAreaOrganizacional().setNome(areaOrganizacionalNome);

		this.turma = new Turma();
		this.turma.setId(turmaId);
		this.turma.setDescricao(turmaDescricao);

		this.turma.setCurso(new Curso());
		this.turma.getCurso().setNome(cursoNome);
	}

	public ColaboradorTurma(Long id, Long colaboradorId, String colaboradorNome, String cursoNome, String areaOrganizacionalNome, String estabelecimentoNome, String turmaDescricao, Date turmaDataPrevIni, Date turmaDataPrevFim)
	{
		this.setId(id);
		setColaboradorId(colaboradorId);
		setColaboradorNome(colaboradorNome);
		setCursoNome(cursoNome);
		this.colaborador.setAreaOrganizacionalNome(areaOrganizacionalNome);
		this.colaborador.setEstabelecimentoNomeProjection(estabelecimentoNome);
		setTurmaDescricao(turmaDescricao);
		setTurmaDataPrevIni(turmaDataPrevIni);
		setTurmaDataPrevFim(turmaDataPrevFim);
	}

	//usado por findHistoricoTreinamentos
	public ColaboradorTurma(Long id, Long colaboradorId, String colaboradorNome, Long cursoId, String cursoNome, Integer cursoCargaHoraria, String cargoNome, Long faixaSalarialId, String faixaSalarialNome, Long turmaId, String turmaDescricao, Date turmaDataPrevIni, Date turmaDataPrevFim, String instrutor)
	{
		this.setId(id);
		setColaboradorId(colaboradorId);
		setColaboradorNome(colaboradorNome);
		setCursoNome(cursoNome);
		setProjectionCursoCargaHoraria(cursoCargaHoraria);
		setCursoId(cursoId);
		this.colaborador.setCargoNomeProjection(cargoNome);
		this.colaborador.setFaixaSalarialIdProjection(faixaSalarialId);
		this.colaborador.setFaixaSalarialNomeProjection(faixaSalarialNome);
		setTurmaId(turmaId);
		setTurmaDescricao(turmaDescricao);
		setTurmaDataPrevIni(turmaDataPrevIni);
		setTurmaDataPrevFim(turmaDataPrevFim);
		setInstrutor(instrutor);
				
	}
	
	public ColaboradorTurma(Long id, Double valorAvaliacao)
	{
		this.setId(id);
		this.valorAvaliacao = valorAvaliacao;
	}

	public ColaboradorTurma(Long colaboradorId, String colaboradorNome, String empresaNome, String cursoNome, Integer cursoCargaHoraria, String turmaDescricao, Date dataPrevIni, Date dataPrevFim, boolean turmaRealizada, Double custoRateado)
	{
		this.setColaboradorId(colaboradorId);
		this.setColaboradorNome(colaboradorNome);
		
		this.setCursoNome(cursoNome);
		this.setProjectionCursoCargaHoraria(cursoCargaHoraria);
		if(this.curso.getEmpresa() == null)
			curso.setEmpresa(new Empresa());
		this.curso.getEmpresa().setNome(empresaNome);
		
		this.setTurmaDescricao(turmaDescricao);
		this.setProjectionDataPrevIni(dataPrevIni);
		this.setProjectionDataPrevFim(dataPrevFim);
		this.setTurmaRealizada(turmaRealizada);
		this.setCustoRateado(custoRateado);
	}
	
	//	findColabTreinamentos	
	public ColaboradorTurma(Long Id, String colabCodigoAc, Date diaPresente)
	{
		setId(Id);
		setColaboradorCodigoAc(colabCodigoAc);
		this.diaPresente = diaPresente;
	}
	
	public void setTurmaRealizada(Boolean turmaRealizada)
	{
		if (this.turma == null)
			this.turma = new Turma();
		this.turma.setRealizada(turmaRealizada);
	}

	public void setTurmaDataPrevIni(Date turmaDataPrevIni)
	{
		if (this.turma == null)
			this.turma = new Turma();
		this.turma.setDataPrevIni(turmaDataPrevIni);
	}

	public void setTurmaDataPrevFim(Date turmaDataPrevFim)
	{
		if (this.turma == null)
			this.turma = new Turma();
		this.turma.setDataPrevFim(turmaDataPrevFim);
	}

	//Projection
	public void setColaboradorNomeComercial(String colaboradorNomeComercial)
	{
		inicializaColaborador();
		colaborador.setNomeComercial(colaboradorNomeComercial);
	}

	private void inicializaColaborador()
	{
		if(colaborador == null)
			this.colaborador = new Colaborador();
	}
	
	public void setDntId(Long dntId)
	{
		if(this.dnt == null)
			this.dnt = new DNT();
		this.dnt.setId(dntId);
	}

	public void setColaboradorId(Long colaboradorId)
	{
		inicializaColaborador();
		colaborador.setId(colaboradorId);
	}

	public void setColaboradorNome(String colaboradorNome)
	{
		inicializaColaborador();
		colaborador.setNome(colaboradorNome);
	}
	
	public void setColaboradorEmail(String colaboradorEmail)
	{
		inicializaColaborador();
		if(colaborador.getContato() == null)
			colaborador.setContato(new Contato());
		
		colaborador.getContato().setEmail(colaboradorEmail);
	}

	public void setColaboradorMatricula(String matricula) 
	{
		inicializaColaborador();
		colaborador.setMatricula(matricula);
	}
	
	public void setColaboradorCodigoAc(String codigoAc) 
	{
		inicializaColaborador();
		colaborador.setCodigoAC(codigoAc);
	}
	
	private void inicializaTurma() {
		if(turma == null)
			this.turma = new Turma();
	}
	
	public void setTurmaDescricao(String turmaDescricao)
	{
		inicializaTurma();
		turma.setDescricao(turmaDescricao);
	}
	
	public void setTurmaHorario(String turmaHorario)
	{
		inicializaTurma();
		turma.setHorario(turmaHorario);
	}

	public void setTurmaId(Long turmaId)
	{
		inicializaTurma();
		turma.setId(turmaId);
	}

	public void setInstrutor(String instrutor)
	{
		inicializaTurma();
		turma.setInstrutor(instrutor);
	}

	public void setProjectionDataPrevIni(Date dataPrevIni)
	{
		inicializaTurma();
		turma.setDataPrevIni(dataPrevIni);
	}

	public void setProjectionDataPrevFim(Date dataPrevFim)
	{
		inicializaTurma();
		turma.setDataPrevFim(dataPrevFim);
	}

	public void setCursoId(Long cursoId)
	{
		if(curso == null)
			this.curso = new Curso();
		curso.setId(cursoId);
	}

	public void setCursoNome(String cursoNome)
	{
		if(curso == null)
			this.curso = new Curso();
		curso.setNome(cursoNome);
	}

	public void setProjectionCursoConteudoProgramatico(String projectionCursoConteudoProgramatico)
	{
		if(curso == null)
			this.curso = new Curso();
		curso.setConteudoProgramatico(projectionCursoConteudoProgramatico);
	}

	public void setProjectionCursoCargaHoraria(Integer cursoCargaHoraria)
	{
		if(curso == null)
			this.curso = new Curso();
		curso.setCargaHoraria(cursoCargaHoraria);
	}

	public void setPrioridadeTreinamentoId(Long prioridadeTreinamentoId)
	{
		if(prioridadeTreinamento == null)
			this.prioridadeTreinamento = new PrioridadeTreinamento();
		prioridadeTreinamento.setId(prioridadeTreinamentoId);
	}

	public void setPrioridadeTreinamentoDescricao(String prioridadeTreinamentoDescricao)
	{
		if(prioridadeTreinamento == null)
			this.prioridadeTreinamento = new PrioridadeTreinamento();
		prioridadeTreinamento.setDescricao(prioridadeTreinamentoDescricao);
	}
	public Curso getCurso()
	{
		return curso;
	}
	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}
	public Colaborador getColaborador()
	{
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}
	public PrioridadeTreinamento getPrioridadeTreinamento()
	{
		return prioridadeTreinamento;
	}
	public void setPrioridadeTreinamento(PrioridadeTreinamento prioridadeTreinamento)
	{
		this.prioridadeTreinamento = prioridadeTreinamento;
	}
	public Turma getTurma()
	{
		return turma;
	}
	public void setTurma(Turma turma)
	{
		this.turma = turma;
	}

	public DNT getDnt()
	{
		return dnt;
	}

	public void setDnt(DNT dnt)
	{
		this.dnt = dnt;
	}

	public boolean isOrigemDnt()
	{
		return origemDnt;
	}

	public void setOrigemDnt(boolean origemDnt)
	{
		this.origemDnt = origemDnt;
	}

	public boolean isAprovado()
	{
		return aprovado;
	}

	public void setAprovado(boolean aprovado)
	{
		this.aprovado = aprovado;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", this.getId()).append("colaborador", this.colaborador)
				.append("turma", this.turma).append("prioridadeTreinamento",
						this.prioridadeTreinamento).toString();
	}

	public String getColaboradorNome()
	{
		if(colaborador != null)
		{
			return colaborador.getNome();
		}
		return "";
	}

	public String getColaboradorNomeComercial()
	{
		if(colaborador != null)
		{
			return colaborador.getNomeComercial();
		}
		return "";
	}

	public Double getCustoRateado()
	{
		return custoRateado;
	}

	public void setCustoRateado(Double custoRateado)
	{
		this.custoRateado = custoRateado;
	}

	public String getTempoSemCurso()
	{
		String anoMes = "";
		if (turma != null && turma.getDataPrevFim() != null)
		{
			GregorianCalendar dataAtual = new GregorianCalendar();

			int anoAtual = dataAtual.get(GregorianCalendar.YEAR);
			int mesAtual = dataAtual.get(GregorianCalendar.MONTH);
			int diaAtual = dataAtual.get(GregorianCalendar.DAY_OF_MONTH);

			int anoUltimo = (turma.getDataPrevFim().getYear() + 1900);
			int mesUltimo = turma.getDataPrevFim().getMonth();
			int diaUltimo = turma.getDataPrevFim().getDate();

			int anos = anoAtual - anoUltimo;
			int meses;

			if (anos > 0)
			{
				if (mesAtual < mesUltimo)
					anos--;
				else
				{
					if (mesAtual == mesUltimo)
					{
						if (diaAtual < diaUltimo)
							anos--;
					}
				}
			}

			if (mesAtual > mesUltimo)
				meses = mesAtual - mesUltimo;
			else
				meses = (12 - mesUltimo) + mesAtual;

			if (diaAtual < diaUltimo)
				meses--;

			if (anos > 0)
			{
				if(anos == 1)
					anoMes += anos + " ano ";
				else
					anoMes += anos + " anos ";
			}

			if (meses > 0)
			{
				if(meses == 1)
					anoMes += (anos > 0 ?"e ":"") + meses + " mês";
				else
					anoMes += (anos > 0 ?"e ":"") + meses + " meses";
			}
		}

		return anoMes.trim();
	}

	public void setTempoSemCurso(String tempoSemCurso)
	{
		this.tempoSemCurso = tempoSemCurso;
	}

	/** atributo <i>transiente</i>. */
	public boolean isRespondeuAvaliacaoTurma()
	{
		return respondeuAvaliacaoTurma;
	}

	/** atributo <i>transiente</i>. */
	public void setRespondeuAvaliacaoTurma(boolean respondeuAvaliacaoTurma)
	{
		this.respondeuAvaliacaoTurma = respondeuAvaliacaoTurma;
	}

	@Override
	public boolean equals(Object object)
	{
		if (object == null || !(object instanceof ColaboradorTurma))
			return false;

		ColaboradorTurma colaboradorTurma = (ColaboradorTurma)object;
		if (colaboradorTurma.getId() != null && this.getId() != null)
			return this.getId().equals(colaboradorTurma.getId());

		return false;
	}

	public void setProjectionEstabelecimentoId(Long estabelecimentoId) 
	{
		this.colaborador = new Colaborador();
		this.colaborador.setHistoricoColaborador(new HistoricoColaborador());
		this.colaborador.getHistoricoColaborador().setEstabelecimento(new Estabelecimento());
		this.colaborador.getHistoricoColaborador().getEstabelecimento().setId(estabelecimentoId);
	}
	
	public Collection<AproveitamentoAvaliacaoCurso> getAproveitamentoAvaliacaoCursos()
	{
		return aproveitamentoAvaliacaoCursos;
	}

	public void setAproveitamentoAvaliacaoCursos(Collection<AproveitamentoAvaliacaoCurso> aproveitamentoAvaliacaoCursos)
	{
		this.aproveitamentoAvaliacaoCursos = aproveitamentoAvaliacaoCursos;
	}

	/** atributo <i>transiente</i>. */
	public Double getValorAvaliacao() {
		return valorAvaliacao;
	}

	public void setValorAvaliacao(Double valorAvaliacao) {
		this.valorAvaliacao = valorAvaliacao;
	}
	
	//usado no relatorio e ftl
	public String getAprovadoFormatado()
	{
		String aprovadoFmt; 
		
		if (aprovado)
			aprovadoFmt = "Sim";
		else
		{
			aprovadoFmt = "-";
			
			if (valorAvaliacao != null)
				aprovadoFmt = "Não";
		}
		
		return aprovadoFmt;
	}
	
	//usado no relatorio e ftl
	public String getAprovadoMaisNota()
	{
		if (aprovado)
			return "Sim";

		return "Não";
	}

	public Integer getTotalDias() {
		return totalDias;
	}

	public void setTotalDias(Integer totalDias) {
		this.totalDias = totalDias;
	}

	public Integer getQtdPresenca() {
		return qtdPresenca;
	}

	public void setQtdPresenca(Integer qtdPresenca) {
		this.qtdPresenca = qtdPresenca;
	}

	public Integer getQtdAvaliacoesAprovadasPorNota() {
		return qtdAvaliacoesAprovadasPorNota;
	}

	public void setQtdAvaliacoesAprovadasPorNota(Integer qtdAvaliacoesAprovadasPorNota) {
		this.qtdAvaliacoesAprovadasPorNota = qtdAvaliacoesAprovadasPorNota;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public Integer getQtdAvaliacoesCurso() {
		return qtdAvaliacoesCurso;
	}

	public void setQtdAvaliacoesCurso(Integer qtdAvaliacoesCurso) {
		this.qtdAvaliacoesCurso = qtdAvaliacoesCurso;
	}
	
	public String getColuna01RelatorioPresenca()
	{
		return coluna01RelatorioPresenca;
	}

	public void setColuna01RelatorioPresenca(String coluna01RelatorioPresenca)
	{
		this.coluna01RelatorioPresenca = coluna01RelatorioPresenca;
	}

	public String getColuna02RelatorioPresenca()
	{
		return coluna02RelatorioPresenca;
	}

	public void setColuna02RelatorioPresenca(String coluna02RelatorioPresenca)
	{
		this.coluna02RelatorioPresenca = coluna02RelatorioPresenca;
	}

	public String getColuna03RelatorioPresenca()
	{
		return coluna03RelatorioPresenca;
	}

	public void setColuna03RelatorioPresenca(String coluna03RelatorioPresenca)
	{
		this.coluna03RelatorioPresenca = coluna03RelatorioPresenca;
	}

	public Collection<MatrizTreinamento> getMatrizTreinamentos()
	{
		return matrizTreinamentos;
	}
	
	public void setMatrizTreinamentos(Collection<MatrizTreinamento> matrizTreinamentos)
	{
		this.matrizTreinamentos = matrizTreinamentos;
	}

	public Collection<ColaboradorPresenca> getColaboradorPresencas()
	{
		return colaboradorPresencas;
	}

	public void setColaboradorPresencas(Collection<ColaboradorPresenca> colaboradorPresencas)
	{
		this.colaboradorPresencas = colaboradorPresencas;
	}
	
	public Integer getQtdRespostasAvaliacaoTurma()
	{
		return qtdRespostasAvaliacaoTurma;
	}

	public Date getDiaPresente() 
	{
		return diaPresente;
	}
}