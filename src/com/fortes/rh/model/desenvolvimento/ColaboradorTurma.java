package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

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
import com.fortes.rh.util.DateUtil;
import com.ibm.icu.text.SimpleDateFormat;

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
	@ManyToOne
	private CursoLnt cursoLnt;

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
	private Date diaTurma;
	@Transient
	private String diaTurmaHoraIni;
	@Transient
	private String diaTurmaHoraFim;
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
	@Transient
    private boolean certificadoEmTurmaPosterior;
	@Transient
	private Long colaboradorCertificacaoId;
	@Transient
	private boolean certificado;
	@Transient
	private String cargaHorariaEfetiva;
	@Transient
	private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	@Transient
	private String certificacoesNomes;
	@Transient
	private Boolean possuiLntASerVinculado;

	public ColaboradorTurma() {	}
	
	public ColaboradorTurma(Colaborador colaborador, Curso curso) {
		this.colaborador = colaborador;
		this.curso = curso;
	}

	public ColaboradorTurma(Long id, Long colaboradorId )
	{
		setId(id);
		setColaboradorId(colaboradorId);
	}
	
	public ColaboradorTurma(Long id, Boolean aprovado, Long colaboradorId, Long turmaId, Long cursoId )
	{
		setId(id);
		setAprovado(aprovado);
		setColaboradorId(colaboradorId);
		setTurmaId(turmaId);
		setCursoId(cursoId);
	}
	
	public ColaboradorTurma(Long id, Boolean aprovado, Long colaboradorId)
	{
		setId(id);
		setAprovado(aprovado);
		setColaboradorId(colaboradorId);
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
	public ColaboradorTurma(Long colaboradorId, String colaboradorNome, String colaboradorMatricula, Long areaId, String areaNome, String estabelecimentoNome, String empresaNome, Date dataPrevFim, Long cursoId, String cursoNome )
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
		
		this.curso = new Curso();
		this.curso.setId(cursoId);
		this.curso.setNome(cursoNome);
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
	public ColaboradorTurma(Long id, Long cursoId, Long prioridadeTreinamentoId, Long turmaId, Long colaboradorId, String colaboradorNome, String colaboradorNomeComercial, String colaboradorMatricula, String colaboradorCPF,Long areaOrganizacionalId, String areaOrganizacionalNome, boolean aprovado, Estabelecimento estabelecimento, String faixaSalarialNome, String cargoNome, Long empresaId, String empresaNome, String empresaRazaoSocial, String empresaCnpj, Integer qtdRespostasAvaliacaoTurma, Boolean possuiLntASerVinculado)
	{
		this.setId(id);
		this.setCursoId(cursoId);
		this.setTurmaId(turmaId);
		this.possuiLntASerVinculado = possuiLntASerVinculado;
		
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
	
	//usado por findHistoricoTreinamentosByColaborador
	public ColaboradorTurma(Long id, Boolean aprovado, Double valorAvaliacao, Long colaboradorId, String colaboradorNome, Long cursoId, String cursoNome, Integer cursoCargaHoraria, String cargoNome, Long faixaSalarialId, String faixaSalarialNome, Long turmaId, String turmaDescricao, Date turmaDataPrevIni, Date turmaDataPrevFim, String instrutor)
	{
		this.setId(id);
		this.setAprovado(aprovado);
		this.setValorAvaliacao(valorAvaliacao);
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
	
	public ColaboradorTurma(CursoLnt cursoLnt, Long colaboradorId, Long cursoId)
	{
		this.setCursoLnt(cursoLnt);
		this.setColaboradorId(colaboradorId);
		this.setCursoId(cursoId);
	}
	
	public void setTurmaRealizada(Boolean turmaRealizada)
	{
		iniciaTurma();
		this.turma.setRealizada(turmaRealizada);
	}

	public void setTurmaCusto(Double custo)
	{
		iniciaTurma();
		this.turma.setCusto(custo);
	}

	public void setTurmaInstituicao(String turmaInstituicao)
	{
		iniciaTurma();
		this.turma.setInstituicao(turmaInstituicao);
	}
	
	public void setTurmaDataPrevIni(Date turmaDataPrevIni)
	{
		iniciaTurma();
		this.turma.setDataPrevIni(turmaDataPrevIni);
	}

	public void setTurmaDataPrevFim(Date turmaDataPrevFim)
	{
		iniciaTurma();
		this.turma.setDataPrevFim(turmaDataPrevFim);
	}

	private void iniciaTurma() {
		if (this.turma == null)
			this.turma = new Turma();
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
		iniciaCurso();
		curso.setId(cursoId);
	}

	public void setCursoNome(String cursoNome)
	{
		iniciaCurso();
		curso.setNome(cursoNome);
	}

	public void setCursoPeriodicidade(Integer cursoPeriodicidade)
	{
		iniciaCurso();
		curso.setPeriodicidade(cursoPeriodicidade);
	}
	
	public void setCursoCargaHoraria(Integer cursoCargaHoraria)
	{
		iniciaCurso();
		curso.setCargaHoraria(cursoCargaHoraria);
	}

	private void iniciaCurso() {
		if(curso == null)
			this.curso = new Curso();
	}
	
	public void setProjectionCursoConteudoProgramatico(String projectionCursoConteudoProgramatico)
	{
		iniciaCurso();
		curso.setConteudoProgramatico(projectionCursoConteudoProgramatico);
	}

	public void setProjectionCursoCargaHoraria(Integer cursoCargaHoraria)
	{
		iniciaCurso();
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
	
	public void setVencimento(Date vencimento){
		iniciaTurma();
		
		turma.setVencimento(vencimento);
	}

	public void setVencido(boolean vencido){
		iniciaTurma();
		
		turma.setVencido(vencido);
	}
	
	public void setCertificacaoNome(String certificacaoNome){
		if (this.curso == null) {
			this.curso = new Curso();
		}
		
		curso.setCertificacaoNome(certificacaoNome);
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

	public String getTempoSemCurso(){
		if(turma != null)
			return DateUtil.formataTempoExtenso(turma.getDataPrevFim(), new Date());
		else
			return "";
	}

	public void setTempoSemCurso(String tempoSemCurso)
	{
		this.tempoSemCurso = tempoSemCurso;
	}

	public String getStatusAprovacao(){
		if(turma != null)
			return this.aprovado == true ? "Aprovado": "Reprovado";  
		else
			return "";
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

	public void setEstabelecimentoId(Long estabelecimentoId) 
	{
		inicializaColaborador();
		this.colaborador.setEstabelecimentoIdProjection(estabelecimentoId);
	}

	public void setEstabelecimentoNome(String estabelecimentoNome) 
	{
		inicializaColaborador();
		this.colaborador.setEstabelecimentoNomeProjection(estabelecimentoNome);
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

	public void setDiaPresente(Date diaPresente) {
		this.diaPresente = diaPresente;
	}

	public Date getDiaTurma() {
		return diaTurma;
	}

	public void setDiaTurma(Date diaTurma) {
		this.diaTurma = diaTurma;
	}

	public String getDiaTurmaHoraIni() {
		return diaTurmaHoraIni;
	}

	public void setDiaTurmaHoraIni(String diaTurmaHoraIni) {
		this.diaTurmaHoraIni = diaTurmaHoraIni;
	}

	public String getDiaTurmaHoraFim() {
		return diaTurmaHoraFim;
	}

	public void setDiaTurmaHoraFim(String diaTurmaHoraFim) {
		this.diaTurmaHoraFim = diaTurmaHoraFim;
	}
	
	public void setEmpresaId(Long empresaId){
		inicializaColaborador();
		this.colaborador.setEmpresaId(empresaId);
	}
	
	public void setEmpresaNome(String empresaNome){
		inicializaColaborador();
		this.colaborador.setEmpresaNome(empresaNome);
	}
	
	public void setCursoPercentualMinimoFrequencia(Double percentualMinimoFrequencia){
		if (this.curso == null) {
			this.curso = new Curso();
		}
		
		this.curso.setPercentualMinimoFrequencia(percentualMinimoFrequencia);
	}
	
	public void setAreaOrganizacionalId(Long areaOrganizacionalId){
		inicializaColaborador();
		this.colaborador.setAreaOrganizacionalId(areaOrganizacionalId);
	}
	
	public void setAreaOrganizacionalNome(String areaOrganizacionalNome){
		inicializaColaborador();
		this.colaborador.setAreaOrganizacionalNome(areaOrganizacionalNome);
	}
	
	public void setAreaOrganizacionalNomeComHierarquia(String areaNomeComHierarquia){
		inicializaColaborador();
		this.colaborador.setAreaOrganizacionalNomeComHierarquia(areaNomeComHierarquia);
	}
	
	public void setFaixaSalarialNome(String faixaSalarialNome){
		inicializaColaborador();		
		this.colaborador.setFaixaSalarialNomeProjection(faixaSalarialNome);
	}
	
	public void setCargoNome(String cargoNome){
		inicializaColaborador();		
		if(this.colaborador.getFaixaSalarial() == null)
			this.colaborador.setFaixaSalarial(new FaixaSalarial());
		
		this.colaborador.getFaixaSalarial().setCargo(new Cargo());
		this.colaborador.getFaixaSalarial().getCargo().setNome(cargoNome);
	}

	public boolean isCertificadoEmTurmaPosterior() {
		return certificadoEmTurmaPosterior;
	}

	public void setCertificadoEmTurmaPosterior(boolean certificadoEmTurmaPosterior) {
		this.certificadoEmTurmaPosterior = certificadoEmTurmaPosterior;
	}

	public Long getColaboradorCertificacaoId() {
		return colaboradorCertificacaoId;
	}

	public void setColaboradorCertificacaoId(Long colaboradorCertificacaoId) {
		this.colaboradorCertificacaoId = colaboradorCertificacaoId;
	}
	
	public boolean isCertificado() {
		return certificado;
	}

	public void setCertificado(boolean certificado) {
		this.certificado = certificado;
	}

	public String getCargaHorariaEfetiva() {
		return cargaHorariaEfetiva;
	}

	public void setCargaHorariaEfetiva(String cargaHorariaEfetiva) {
		this.cargaHorariaEfetiva = cargaHorariaEfetiva;
	}
	
	public Timestamp getCargaHorariaEfetivaTime(){
		try {
			if(cargaHorariaEfetiva != null && !"".equals(cargaHorariaEfetiva))
				return new Timestamp(dateFormat.parse(cargaHorariaEfetiva).getTime());
			
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getCertificacoesNomes() {
		return certificacoesNomes;
	}

	public void setCertificacoesNomes(String certificacoesNomes) {
		this.certificacoesNomes = certificacoesNomes;
	}

	public CursoLnt getCursoLnt() {
		return cursoLnt;
	}

	public void setCursoLnt(CursoLnt cursoLnt) {
		this.cursoLnt = cursoLnt;
	}
	
	public void setCursoLntId(Long cursoLntId) {
		if(this.cursoLnt ==null)
			this.cursoLnt = new CursoLnt();
		
		this.cursoLnt.setId(cursoLntId);
	}

	public Boolean getPossuiLntASerVinculado() {
		return possuiLntASerVinculado;
	}

	public void setPossuiLntASerVinculado(Boolean possuiLntASerVinculado) {
		this.possuiLntASerVinculado = possuiLntASerVinculado;
	}
}