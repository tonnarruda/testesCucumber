package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Certificado;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.relatorio.ColaboradorCertificacaoRelatorio;
import com.fortes.rh.model.desenvolvimento.relatorio.ColaboradorCursoMatriz;
import com.fortes.rh.model.desenvolvimento.relatorio.CursoPontuacaoMatriz;
import com.fortes.rh.model.desenvolvimento.relatorio.SomatorioCursoMatriz;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;
import com.ibm.icu.math.BigDecimal;

@SuppressWarnings("unchecked")
public class ColaboradorTurmaManagerImpl extends GenericManagerImpl<ColaboradorTurma, ColaboradorTurmaDao> implements ColaboradorTurmaManager
{
	private ColaboradorManager colaboradorManager;
	private DiaTurmaManager diaTurmaManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private CursoManager cursoManager;
	private TurmaManager turmaManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager;
	private AvaliacaoCursoManager avaliacaoCursoManager;
	private CertificacaoManager certificacaoManager;

	public void setColaboradorQuestionarioManager(ColaboradorQuestionarioManager colaboradorQuestionarioManager)
	{
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}
	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
	}
	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}
	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public Collection<ColaboradorTurma> filtrarColaboradores(int page, int pagingSize, String[] areasCheck, String[] cargosCheck, String[] gruposCheck, String[] colaboradoresCursosCheck, int filtrarPor, Turma turma, Colaborador colaborador, Long empresaId) throws ColecaoVaziaException
	{
		Collection<ColaboradorTurma> retorno = new ArrayList<ColaboradorTurma>();
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();

		switch (filtrarPor)
		{
			case 1: // Áreas organizacionais
				colaboradores = colaboradorManager.findByAreasOrganizacionalIds(page, pagingSize, LongUtil.arrayStringToArrayLong(areasCheck), colaborador, empresaId);
				retorno = geraColaboradorTurma(colaboradores, turma);
				break;
			case 3: // Cargos
				colaboradores = historicoColaboradorManager.findByCargosIds(page, pagingSize, LongUtil.arrayStringToArrayLong(cargosCheck), colaborador, empresaId);
				retorno = geraColaboradorTurma(colaboradores, turma);
				break;
			case 4:
				retorno = getDao().findByColaboradorAndTurma(page, pagingSize, LongUtil.arrayStringToArrayLong(colaboradoresCursosCheck), turma.getCurso().getId(), colaborador);
				break;
		}

		return retorno;
	}

	private Collection<ColaboradorTurma> geraColaboradorTurma(Collection<Colaborador> colaboradores, Turma turma)
	{
		Collection<ColaboradorTurma> retorno = new ArrayList<ColaboradorTurma>();
		ColaboradorTurma cc;

		for (Colaborador colaborador : colaboradores)
		{
			cc = new ColaboradorTurma();
			cc.setColaborador(colaborador);
			cc.setTurma(turma);
			cc.setId(colaborador.getId());// id Temporario vai ser modificado na inclusao do colaboradorTurmas

			retorno.add(cc);
		}

		return retorno;
	}

	public Collection<ColaboradorTurma> filtroRelatorioMatriz(LinkedHashMap filtro)
	{
		return getDao().filtroRelatorioMatriz(filtro);
	}

	public Collection<Colaborador> getListaColaboradores(Collection<ColaboradorTurma> colaboradorTurmasLista)
	{
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		for (ColaboradorTurma cc : colaboradorTurmasLista)
		{
			if (!colaboradores.contains(cc.getColaborador()))
				colaboradores.add(cc.getColaborador());
		}

		return colaboradores;
	}

	public Integer getCount(Long turmaId)
	{
		return getDao().getCount(turmaId);
	}

	public Collection<ColaboradorTurma> filtroRelatorioPlanoTrei(LinkedHashMap filtro)
	{
		return getDao().filtroRelatorioPlanoTrei(filtro);
	}

	public Collection<ColaboradorTurma> findRelatorioSemIndicacaoTreinamento(Long empresaId, Long[] areaIds, Long[] estabelecimentoIds, int qtdMeses) throws ColecaoVaziaException
	{
		Calendar data = Calendar.getInstance();
		data.setTime(new Date());
		data.add(Calendar.MONTH, -qtdMeses);

		Collection<ColaboradorTurma>  colaboradorTurmas = getDao().findRelatorioSemIndicacaoDeTreinamento(empresaId, areaIds, estabelecimentoIds, data.getTime());

		Collection<ColaboradorTurma> resultado = new ArrayList<ColaboradorTurma>();

		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas)
		{
			boolean jaExiste = false;
			for (ColaboradorTurma ct : resultado)
			{
				if (ct.getColaborador().getId().equals(colaboradorTurma.getColaborador().getId()))
				{
					jaExiste = true;
					break;
				}
			}
			if (!jaExiste)
			{
				if (colaboradorTurma.getTurma() != null)
				{
					String tempoSemCurso = calculaTempoSemCurso(colaboradorTurma.getTurma().getDataPrevFim());
					colaboradorTurma.setTempoSemCurso(tempoSemCurso);
				}
				resultado.add(colaboradorTurma);
			}
		}

		if (resultado.isEmpty())
			throw new ColecaoVaziaException("Não existem dados para o filtro informado.");

		return resultado;
	}

	@SuppressWarnings("deprecation")
	private String calculaTempoSemCurso(Date dataPrevFim)
	{
		String anoMes = "";
		if (dataPrevFim != null)
		{
			GregorianCalendar dataAtual = new GregorianCalendar();

			int anoAtual = dataAtual.get(GregorianCalendar.YEAR);
			int mesAtual = dataAtual.get(GregorianCalendar.MONTH);
			int diaAtual = dataAtual.get(GregorianCalendar.DAY_OF_MONTH);

			int anoUltimo = (dataPrevFim.getYear() + 1900);
			int mesUltimo = dataPrevFim.getMonth();
			int diaUltimo = dataPrevFim.getDate();

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
					anoMes += anos + " ano";
				else
					anoMes += anos + " anos";
			}

			if (meses > 0)
			{
				if(meses == 1)
					anoMes += meses + " mês";
				else
					anoMes += meses + " meses";
			}
		}

		return anoMes;
	}

	public void saveUpdate(String[] colaboradorTurma, String[] selectPrioridades) throws Exception
	{
		if(colaboradorTurma != null && colaboradorTurma.length > 0)
		{
			for(int i=0; i<colaboradorTurma.length; i++)
			{
				getDao().updateColaboradorTurmaSetPrioridade(Long.parseLong(colaboradorTurma[i].replace(".", "")), Long.parseLong(selectPrioridades[i].replace(".", "")));
			}
		}
	}

	public Collection<Curso> getListaCursos(Collection<ColaboradorTurma> colaboradorTurmasLista)
	{
		Collection<Curso> cursos = new ArrayList<Curso>();

		for (ColaboradorTurma cc : colaboradorTurmasLista)
		{
			if (!cursos.contains(cc.getCurso()))
				cursos.add(cc.getCurso());
		}

		return cursos;
	}

	public Collection<ColaboradorCursoMatriz> montaMatriz(Collection<ColaboradorTurma> colaboradorTurmasLista)
	{
		Collection<ColaboradorCursoMatriz> colaboradorCursoMatrizs = new ArrayList<ColaboradorCursoMatriz>();
		Collection<Colaborador> colaboradores = getListaColaboradores(colaboradorTurmasLista);

		ColaboradorCursoMatriz colaboradorCursoMatriz;

		for (Colaborador colaborador : colaboradores)
		{
			colaboradorCursoMatriz = new ColaboradorCursoMatriz();
			colaboradorCursoMatriz.setColaborador(colaborador);

			colaboradorCursoMatriz.setCursoPontuacaos(populaCursoPontuacao(colaboradorTurmasLista, colaborador));

			colaboradorCursoMatrizs.add(colaboradorCursoMatriz);
		}

		return colaboradorCursoMatrizs;
	}

	private Collection<CursoPontuacaoMatriz> populaCursoPontuacao(Collection<ColaboradorTurma> colaboradorTurmasLista, Colaborador colaborador)
	{
		Collection<CursoPontuacaoMatriz> cursoPontuacaoMatrizs = new ArrayList<CursoPontuacaoMatriz>();

		CursoPontuacaoMatriz cursoPontuacao;
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmasLista)
		{
			if(colaborador.getId().equals(colaboradorTurma.getColaborador().getId()))
			{
				cursoPontuacao = new CursoPontuacaoMatriz();
				cursoPontuacao.setCurso(colaboradorTurma.getCurso());
				cursoPontuacao.setPontuacao(colaboradorTurma.getPrioridadeTreinamento().getNumero());
				cursoPontuacao.setSigla(colaboradorTurma.getPrioridadeTreinamento().getSigla());
				cursoPontuacaoMatrizs.add(cursoPontuacao);
			}
		}

		return cursoPontuacaoMatrizs;
	}
	public Collection<ColaboradorTurma> findColaboradoresByCursoTurmaIsNull(Long cursoId)
	{
		return getDao().findColaboradoresByCursoTurmaIsNull(cursoId);
	}
	public void updateTurmaEPrioridade(Long colaboradorTurnaId, Long turmaId, Long prioridadeId)
	{
		getDao().updateTurmaEPrioridade(colaboradorTurnaId, turmaId, prioridadeId);
	}
	public Collection<ColaboradorTurma> findByTurmaCurso(Long cursoId)
	{
		return getDao().findByTurmaCurso(cursoId);
	}
	
	public Collection<SomatorioCursoMatriz> getSomaPontuacao(Collection<ColaboradorCursoMatriz> colaboradorCursoMatrizs)
	{
		Collection<CursoPontuacaoMatriz> cursoPontuacaos = new ArrayList<CursoPontuacaoMatriz>();

		for (ColaboradorCursoMatriz colabCursoMatriz : colaboradorCursoMatrizs)
		{
			cursoPontuacaos.addAll(colabCursoMatriz.getCursoPontuacaos());
		}

		Map soma = new HashMap<Long, Integer>();
		Integer subTotal = 0;
		for (CursoPontuacaoMatriz cursoPontuacao : cursoPontuacaos)
		{
			Long cursoId = cursoPontuacao.getCurso().getId();
			if(soma.containsKey(cursoId))
				subTotal = ((Integer)soma.get(cursoId)) + cursoPontuacao.getPontuacao();
			else
				subTotal = cursoPontuacao.getPontuacao();

			soma.put(cursoId, subTotal);
		}

		Set chaves = soma.keySet();

		Collection<SomatorioCursoMatriz> somatorios = new ArrayList<SomatorioCursoMatriz>();
		SomatorioCursoMatriz somatorio;
		for (Object chave : chaves)
		{
			somatorio = new SomatorioCursoMatriz();
			somatorio.setCursoId((Long) chave);
			somatorio.setSoma((Integer) soma.get(chave));

			somatorios.add(somatorio);
		}

		return somatorios;
	}

	public Collection<ColaboradorTurma> findByDNTColaboradores(DNT dnt, Collection<Colaborador> colaboradors)
	{
		return getDao().findByDNTColaboradores(dnt, colaboradors);
	}

	public boolean comparaEmpresa(ColaboradorTurma colaboradorTurma, Empresa empresa)
	{
		Empresa emp = getDao().findEmpresaDoColaborador(colaboradorTurma);

		return (emp != null && emp.getId().equals(empresa.getId()));
	}

	public boolean verifcaExisteNoCurso(Colaborador colaborador, Curso curso, DNT dnt)
	{
		Collection<ColaboradorTurma> resultado = findToList(new String[]{"id"}, new String[]{"id"}, new String[]{"colaborador.id", "curso.id", "dnt.id"}, new Object[]{colaborador.getId(), curso.getId(), dnt.getId()});
		return !resultado.isEmpty();
	}

	public Map<String, Object> getDadosTurma(Collection<ColaboradorTurma> colaboradorTurmas, Map<String, Object> parametros) throws Exception
	{
		if(colaboradorTurmas == null || colaboradorTurmas.isEmpty())
			throw new Exception("Não existem Colaboradores aprovados nessa Turma.");

		ColaboradorTurma colaboradorTurma = (ColaboradorTurma) colaboradorTurmas.toArray()[0];

		parametros.put("CURSO_NOME", colaboradorTurma.getCurso().getNome());
		parametros.put("CURSO_CONTEUDO", colaboradorTurma.getCurso().getConteudoProgramatico());
		parametros.put("CURSO_CARGA_HORARIA", colaboradorTurma.getCurso().getCargaHoraria());
		parametros.put("TURMA_DESCRICAO", colaboradorTurma.getTurma().getDescricao());
		parametros.put("TURMA_INSTRUTOR", colaboradorTurma.getTurma().getInstrutor());
		parametros.put("TURMA_HORARIO", colaboradorTurma.getTurma().getHorario());

		if(colaboradorTurma.getTurma() == null || colaboradorTurma.getTurma().getDataPrevIni() == null || colaboradorTurma.getTurma().getDataPrevFim() == null)
			throw new Exception("A Turma informada esta sem Período.");

		parametros.put("TURMA_DATA_PREVI_INI", DateUtil.formataDiaMesAno(colaboradorTurma.getTurma().getDataPrevIni()));
		parametros.put("TURMA_DATA_PREVI_FIM", DateUtil.formataDiaMesAno(colaboradorTurma.getTurma().getDataPrevFim()));

		return parametros;
	}

	public Collection<ColaboradorTurma> getColaboradoresAprovadoByTurma(Long turmaId)
	{
		Collection<ColaboradorTurma> colaboradorTurmas = null;
		Integer qtdAvaliacoes = avaliacaoCursoManager.countAvaliacoes(turmaId, "T");
		if(qtdAvaliacoes.equals(0))
		{
			Collection<Long> turmaIds = new ArrayList<Long>();
			turmaIds.add(turmaId);
			colaboradorTurmas = getDao().getColaboradoresByTurma(turmaIds);
		}
		else
		{
			Collection<Long> aprovadosIds = aproveitamentoAvaliacaoCursoManager.find(turmaId, qtdAvaliacoes, "T", true);
			if(aprovadosIds != null && !aprovadosIds.isEmpty())
				colaboradorTurmas = getDao().findByIdProjection(LongUtil.collectionStringToArrayLong(aprovadosIds));
		}

		return colaboradorTurmas;
	}

	public Collection<ColaboradorTurma> findByTurma(Long turmaId)
	{
		return getDao().findByTurma(turmaId);
	}

	public void saveUpdate(Collection<Long> colaboradoresTurmaId, boolean aprovado) throws Exception
	{
		if(colaboradoresTurmaId != null && colaboradoresTurmaId.size() > 0)
		{
			for (Long idTmp : colaboradoresTurmaId)
			{
				getDao().updateColaboradorTurmaSetAprovacao(idTmp, aprovado);
			}
		}
	}

	public Collection<ColaboradorTurma> setCustoRateado(Collection<ColaboradorTurma> colaboradorTurmas)
	{
		List custosRateados = getDao().findCustoRateado();

		for (Object custoRateado : custosRateados)
		{
			Object[] turmaCurso = (Object[]) custoRateado;

			for (ColaboradorTurma colaboradorTurma : colaboradorTurmas)
			{
				if(colaboradorTurma.getTurma().getId().equals((Long)turmaCurso[0]))
					colaboradorTurma.setCustoRateado((Double) turmaCurso[1]);
			}
		}

		return colaboradorTurmas;
	}

	public Collection<ColaboradorTurma> setFamiliaAreas(Collection<ColaboradorTurma> colaboradorTurmas, Long empresaId) throws Exception
	{
		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllList(empresaId, AreaOrganizacional.TODAS);
		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);

		for (ColaboradorTurma colaboradorTurma: colaboradorTurmas)
		{
			if(colaboradorTurma.getColaborador() != null && colaboradorTurma.getColaborador().getAreaOrganizacional() != null && colaboradorTurma.getColaborador().getAreaOrganizacional().getId() != null)
				colaboradorTurma.getColaborador().setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, colaboradorTurma.getColaborador().getAreaOrganizacional().getId()));
		}

		return colaboradorTurmas;
	}

	public Collection<ColaboradorTurma> findRelatorioSemTreinamento(Long empresaId, Curso curso, Long[] areaIds, Long[] estabelecimentoIds) throws Exception
	{
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findRelatorioSemTreinamento(empresaId, curso, areaIds, estabelecimentoIds);

		return validaRelatorioTreinamento(empresaId, curso, colaboradorTurmas);
	}

	public Collection<ColaboradorTurma> findRelatorioComTreinamento(Long empresaId, Curso curso, Long[] areaIds, Long[] estabelecimentoIds, char aprovadoFiltro) throws Exception
	{
		Boolean aprovado = aprovadoFiltro == 'T' ? null : aprovadoFiltro == 'S';
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findColaboradoresCertificacoes(empresaId, null, null, curso.getId(), areaIds, estabelecimentoIds, false);
		
		if (colaboradorTurmas == null || colaboradorTurmas.isEmpty())
			throw new ColecaoVaziaException();
		
		// monta aprovados e não aprovados
		carregaResultados(colaboradorTurmas);
		setFamiliaAreas(colaboradorTurmas, empresaId);
		
		if(aprovado != null)
		{
			Collection<ColaboradorTurma> retorno = new ArrayList<ColaboradorTurma>();
			
			for (ColaboradorTurma ct : colaboradorTurmas) 
			{
				if(ct.isAprovado() == aprovado)
					retorno.add(ct);
			}

			return retorno;
		}

		return colaboradorTurmas;
	}
	
	private void setReprovadosMaisNota(Long cursoOuTurmaId, Integer qtdAvaliacoes, String porCursoOuTurma, Collection<ColaboradorTurma> colaboradorTurmas)
	{
		Collection<ColaboradorTurma> reprovados = aproveitamentoAvaliacaoCursoManager.findColaboradorTurma(cursoOuTurmaId, qtdAvaliacoes, porCursoOuTurma, false);
		
		for (ColaboradorTurma colaboradorTurmaReprovado : reprovados) {
			for (ColaboradorTurma colaboradorTurma : colaboradorTurmas)
			{
				if (colaboradorTurma.equals(colaboradorTurmaReprovado))
				{
					colaboradorTurma.setAprovado(false);
					colaboradorTurma.setValorAvaliacao(colaboradorTurmaReprovado.getValorAvaliacao());
					break;
				}
			}
		}
	}

	private Collection<ColaboradorTurma> validaRelatorioTreinamento(Long empresaId, Curso curso, Collection<ColaboradorTurma> colaboradorTurmas) throws ColecaoVaziaException, Exception
	{
		if(colaboradorTurmas == null || colaboradorTurmas.isEmpty())
			throw new ColecaoVaziaException("Não existem dados para o filtro informado.");

		if (!curso.getId().equals(-1L))
			curso.setNome(cursoManager.findByIdProjection(curso.getId()).getNome());

		return setFamiliaAreas(colaboradorTurmas, empresaId);
	}
	public Collection<ColaboradorTurma> findByTurmaSemPresenca(Long turmaId, Long diaTurmaId)
	{
		return getDao().findByTurmaSemPresenca(turmaId, diaTurmaId);
	}
	public Collection<ColaboradorTurma> findByIdProjection(Long[] ids)
	{
		return getDao().findByIdProjection(ids);
	}
	public String insereColaboradorTurmas(Long[] colaboradoresId, Collection<ColaboradorTurma> colaboradoresTurmas, Turma turma, DNT dnt, int filtrarPor, String[] selectPrioridades)
	{
		ColaboradorTurma colaboradorTurma = null;
		String msgAlert = "";
		boolean jaInscrito;

		for(int i = 0; i < colaboradoresId.length; i++)
		{
			jaInscrito = false;
			for (ColaboradorTurma ccTemp : colaboradoresTurmas)
			{
				if(ccTemp.getColaborador().getId().equals(colaboradoresId[i]))
				{
					jaInscrito = true;
					if (msgAlert.equals(""))
						msgAlert = "Os seguintes colaboradores já estão inscritos neste curso: <br>";

					msgAlert += ccTemp.getColaborador().getNome() + "  (Turma: " + ccTemp.getTurma().getDescricao() + ")<br>";
					break;
				}
			}

			if(!jaInscrito)
			{
				//update nos colaboradores que ja estao pre inscritos, esse 4 vem no botão(listFiltro.action?filtrarPor=4&..) Francisco Barroso
				if(filtrarPor == 4)
				{
					//colaboradoresId[i] na verdade é o id do colaboradorTurma
					updateTurmaEPrioridade(colaboradoresId[i], turma.getId(), Long.parseLong(selectPrioridades[i]));
				}
				else
				{
					colaboradorTurma = new ColaboradorTurma();
					Colaborador colaborador = new Colaborador();
					colaborador.setId(colaboradoresId[i]);
					colaboradorTurma.setColaborador(colaborador);
					colaboradorTurma.setTurma(turma);

					colaboradorTurma.setDnt(dnt);
					colaboradorTurma.setCurso(turma.getCurso());

					save(colaboradorTurma);
				}
			}
		}

		if (msgAlert.equals(""))
			msgAlert = "Colaborador(es) incluído(s) com sucesso!";

		return msgAlert;
	}

	@Override
	public void remove(ColaboradorTurma colaboradorTurma)
	{
		super.remove(colaboradorTurma);
		// Remove os Questionarios/Respostas vinculados ao colaborador nesta turma
		colaboradorQuestionarioManager.removeByColaboradorETurma(colaboradorTurma.getColaborador().getId(), colaboradorTurma.getTurma().getId());
	}

	public Collection<ColaboradorTurma> montaColunas(Collection<ColaboradorTurma> colaboradorTurmas, boolean exibirNomeComercial, boolean exibirCargo, boolean exibirEstabelecimento, boolean exibirAssinatura)
	{
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas)
		{
			if(exibirNomeComercial)
				colaboradorTurma.setColuna01RelatorioPresenca(colaboradorTurma.getColaboradorNomeComercial());
			else
				colaboradorTurma.setColuna01RelatorioPresenca(colaboradorTurma.getColaboradorNome());

			if(exibirCargo)
				colaboradorTurma.setColuna02RelatorioPresenca(colaboradorTurma.getColaborador().getHistoricoColaborador().getFaixaSalarial().getDescricao());

			if(exibirEstabelecimento)
				if(colaboradorTurma.getColuna02RelatorioPresenca() == null)
					colaboradorTurma.setColuna02RelatorioPresenca(colaboradorTurma.getColaborador().getHistoricoColaborador().getEstabelecimento().getNome());
				else
					colaboradorTurma.setColuna03RelatorioPresenca(colaboradorTurma.getColaborador().getHistoricoColaborador().getEstabelecimento().getNome());

			if(exibirAssinatura)
				if(colaboradorTurma.getColuna02RelatorioPresenca() == null)
					colaboradorTurma.setColuna02RelatorioPresenca("");
				else
					colaboradorTurma.setColuna03RelatorioPresenca("");
		}

		return colaboradorTurmas;
	}
	
	public Collection<ColaboradorTurma> findRelatorioHistoricoTreinamentos(Long empresaId, Long colaboradorId, Date dataIni, Date dataFim) throws Exception
	{
		Collection<ColaboradorTurma> colaboradorTurmas = findHistoricoTreinamentosByColaborador(empresaId, colaboradorId, dataIni, dataFim);

		if(colaboradorTurmas == null || colaboradorTurmas.isEmpty())
			throw new ColecaoVaziaException("Não existem treinamentos para o colaborador informado.");

		return colaboradorTurmas;
	}
	
	public Collection<ColaboradorTurma> findHistoricoTreinamentosByColaborador(Long empresaId, Long colaboradorId, Date dataIni, Date dataFim) throws Exception
	{
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findHistoricoTreinamentosByColaborador(empresaId, colaboradorId, dataIni, dataFim);
		
		if(colaboradorTurmas != null)
			setAprovacoesDosColaboradoresTurmas(colaboradorTurmas);

		return colaboradorTurmas;
	}

	public Collection<Colaborador> findAprovadosByTurma(Collection<Long> turmaIds)
	{
		ColaboradorPresencaManager colaboradorPresencaManager = (ColaboradorPresencaManager) SpringUtil.getBean("colaboradorPresencaManager");
		turmaManager = (TurmaManager) SpringUtil.getBean("turmaManager");
		
		Collection<ColaboradorPresenca> colaboradorPresencas = colaboradorPresencaManager.findColabPresencaAprovOuRepAvaliacao(turmaIds, true);
		Collection<Turma> turmas = turmaManager.findTurmaPresencaMinima(turmaIds);
		
		Collection<Long> colaboradorIds = new ArrayList<Long>();
		
		for (ColaboradorPresenca colaboradorPresenca : colaboradorPresencas) 
		{
			for (Turma turma : turmas) 
			{
				if(colaboradorPresenca.getTurmaId().equals(turma.getId()))
				{
					if(colaboradorPresenca.getDiasPresente() >= turma.getDiasEstimadosParaAprovacao())
						colaboradorIds.add(colaboradorPresenca.getColaboradorId());
				}
			}			
		}
		
		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();

		if(!colaboradorIds.isEmpty())
			colaboradors = colaboradorManager.findAllSelect(colaboradorIds, false);
		else
			colaboradors = new ArrayList<Colaborador>();

		return colaboradors;
	}
	
	public Collection<Colaborador> montaExibicaoAprovadosReprovados(Long empresaId, Long turmaId)
	{
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findColaboradoresCertificacoes(empresaId, null, turmaId, null, null, null, true);
		
		//add colaboradores aprovados. Tem que ser dois for, primeiro os aprovados(é uma regra do multiSelectBox)
		for (ColaboradorTurma ct : colaboradorTurmas) 
		{
			if(verificaPresenca(ct) && verificaNota(ct))
				colaboradores.add(ct.getColaborador());
		}
		
		//add colaboradores reprovados. Tem que ser dois for, primeiro os aprovados(é uma regra do multiSelectBox)
		for (ColaboradorTurma ct : colaboradorTurmas) 
		{
			String texto = "";
			
			boolean aprovadoPresenca = verificaPresenca(ct);
			boolean aprovadoNota = verificaNota(ct);

			if(!aprovadoPresenca && !aprovadoNota)
				texto = "(reprovado por nota e falta)";
			else if(!aprovadoPresenca)
				texto = "(reprovado por falta)";
			else if(!aprovadoNota)
				texto = "(reprovado por Nota)";
			
			if(!aprovadoPresenca || !aprovadoNota)
			{
				ct.getColaborador().setNome("<span style='color: red;'>" + ct.getColaborador().getNome() + " " + texto + "</span>");
				colaboradores.add(ct.getColaborador());
			}
		}

		return colaboradores;
	}
	
	private Collection<Colaborador> colaboradorAprovOuRepAvaliacaos(Collection<Long> turmaIds, boolean aprovado)
	{
		ColaboradorPresencaManager colaboradorPresencaManager = (ColaboradorPresencaManager) SpringUtil.getBean("colaboradorPresencaManager");
		Collection<ColaboradorPresenca> colaboradorPresencaAprovOuRepAvaliacaos = colaboradorPresencaManager.findColabPresencaAprovOuRepAvaliacao(turmaIds, aprovado);

		// Id Colaboradores Aprovados
		Collection<Long> colaboradorPresencaAprovAvaliacaoIds = new ArrayList<Long>();
		for (ColaboradorPresenca colaboradorPresencaAprovRepAvaliacao : colaboradorPresencaAprovOuRepAvaliacaos)
			colaboradorPresencaAprovAvaliacaoIds.add(colaboradorPresencaAprovRepAvaliacao.getColaboradorId());
		
		Collection<Colaborador> colaboradorAprovAvaliacaos = new ArrayList<Colaborador>();
		if (!colaboradorPresencaAprovAvaliacaoIds.isEmpty())
			colaboradorAprovAvaliacaos = colaboradorManager.findAllSelect(colaboradorPresencaAprovAvaliacaoIds, null);
		
		return colaboradorAprovAvaliacaos;
	}
	
	public Double percentualFrequencia(Date dataIni, Date dataFim, Long empresaId)
	{
		double resultado = 0.0;
		Integer qtdDiasPresentes = 0;
		Integer qtdColaboradoresTurma = 0;
		Integer qtdDiasTurma = 0;

		turmaManager = (TurmaManager) SpringUtil.getBean("turmaManager");
		Collection<Turma> turmas = turmaManager.findByFiltro(dataIni, dataFim, 'T', empresaId);
		Collection<Long> turmaIds = LongUtil.collectionToCollectionLong(turmas);

		qtdColaboradoresTurma = colaboradorManager.qtdColaboradoresByTurmas(turmaIds);
		
		diaTurmaManager = (DiaTurmaManager) SpringUtil.getBean("diaTurmaManager");
		ColaboradorPresencaManager colaboradorPresencaManager = (ColaboradorPresencaManager) SpringUtil.getBean("colaboradorPresencaManager");
		
		for (Long turmaId : turmaIds) 
		{
			qtdDiasTurma += diaTurmaManager.qtdDiasDasTurmas(turmaId);
			qtdDiasPresentes += colaboradorPresencaManager.qtdDiaPresentesTurma(turmaId);
		}

		Integer qtdDiasTotal = (qtdColaboradoresTurma*qtdDiasTurma);

		if (!qtdDiasTotal.equals(0))
		{
			resultado = (double) (qtdDiasPresentes.doubleValue() / qtdDiasTotal.doubleValue());

			BigDecimal valor = new BigDecimal(resultado);  
			valor.setScale(2, BigDecimal.ROUND_UP); //Seta o n° de casas decimais para 2 e o arredondamento para cima  
			double valorFormatado = valor.doubleValue();  
			
			return valorFormatado*100 ;
		}else
			return 100.0;
	}
	
	public Collection<Certificado> montaCertificados(Collection<Colaborador> colaboradores, Certificado certificado)
	{
		Collection<Certificado> certificados = new ArrayList<Certificado>(colaboradores.size());
		for (Colaborador colaborador : colaboradores)
		{
			Certificado certificadoTmp = new Certificado();
			certificadoTmp = (Certificado) certificado.clone();
			certificadoTmp.setNomeColaborador(colaborador.getNome());
			certificados.add(certificadoTmp);
		}

		return certificados;
	}

	public Collection<Colaborador> findAprovadosByCertificacao(Collection<Curso> cursos)
	{
		CollectionUtil<Curso> util = new CollectionUtil<Curso>();
		Long[] cursoIds = util.convertCollectionToArrayIds(cursos);
		
		turmaManager = (TurmaManager) SpringUtil.getBean("turmaManager");
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		Collection<Colaborador> colaboradoresTemp = new ArrayList<Colaborador>();
		Collection<Colaborador> colaboradorCertificados = new ArrayList<Colaborador>();
		Collection<Long> todasTurmaIds = new ArrayList<Long>();
		Boolean decisao = true;
		
		for (Long cursoId : cursoIds)
		{
			Collection<Turma> turmas = turmaManager.getTurmaFinalizadas(cursoId);
			Collection<Long> turmaIds = LongUtil.collectionToCollectionLong(turmas);
			todasTurmaIds.addAll(turmaIds);
			colaboradores.addAll(findAprovadosByTurma(turmaIds));

			if (cursos.size() != 1){
				if (decisao)
					colaboradoresTemp.addAll(colaboradores);
				else
					for (Colaborador colaboradorTemp : colaboradoresTemp) 
						for (Colaborador colaborador : colaboradores)
							if (colaborador.getId().equals(colaboradorTemp.getId()))
								colaboradorCertificados.add(colaborador);
	
				colaboradores.clear();
				decisao = false;
			}else
				colaboradorCertificados.addAll(colaboradores);
		}

		Collection<Colaborador> colaboradorAprovAvaliacaos = colaboradorAprovOuRepAvaliacaos(todasTurmaIds, true);
		Collection<Colaborador> colaboradorRepAvaliacaos = colaboradorAprovOuRepAvaliacaos(todasTurmaIds, false);
		
		Collection<Colaborador> colaboradorTodos = new ArrayList<Colaborador>();
		colaboradorTodos.addAll(colaboradorAprovAvaliacaos);
		colaboradorTodos.addAll(colaboradorRepAvaliacaos);
		
		CollectionUtil<Colaborador> collectionUtil = new CollectionUtil<Colaborador>();
		colaboradorCertificados = collectionUtil.distinctCollection(colaboradorCertificados);
		colaboradorCertificados = collectionUtil.sortCollectionStringIgnoreCase(colaboradorCertificados, "nome");
		
		Collection<Colaborador> colaboradorNaoCertificados = new ArrayList<Colaborador>();
		colaboradorNaoCertificados = collectionUtil.distinctCollection(colaboradorTodos);
		colaboradorNaoCertificados = collectionUtil.sortCollectionStringIgnoreCase(colaboradorNaoCertificados, "nome");
		colaboradorNaoCertificados.removeAll(colaboradorCertificados);
		
		for (Colaborador colaboradorNaoCertificado : colaboradorNaoCertificados)
			colaboradorNaoCertificado.setNome("<span style='color: red;'>" + colaboradorNaoCertificado.getNome() + " (Não certificado)</span>");

		colaboradorTodos.clear();
		colaboradorTodos.addAll(colaboradorCertificados);
		colaboradorTodos.addAll(colaboradorNaoCertificados);
		
		return colaboradorTodos;
	}
	

	public Integer countAprovados(Date dataIni, Date dataFim, Long empresaId, boolean aprovado)
	{
		Integer resultado = 0;

		turmaManager = (TurmaManager) SpringUtil.getBean("turmaManager");
		Collection<Turma> turmas = turmaManager.findByFiltro(dataIni, dataFim, 'T', empresaId);
		Collection<Long> turmaIds = LongUtil.collectionToCollectionLong(turmas);

		if (aprovado)
		{
			resultado = findAprovadosByTurma(turmaIds).size();
		}
		else
		{
			Integer qtdtodosColaboradoresTurma = 0;
			Integer qtdColaboradoresTurmaAprovados = 0;
			qtdColaboradoresTurmaAprovados = findAprovadosByTurma(turmaIds).size();
			qtdtodosColaboradoresTurma = colaboradorManager.qtdColaboradoresByTurmas(turmaIds);
			resultado = qtdtodosColaboradoresTurma - qtdColaboradoresTurmaAprovados; 
		}

		return resultado;
	}
	
	//TODO BACALHAU refatorar todo o metodo, tem varios metodos dependentes, criar teste
	public Collection<ColaboradorCertificacaoRelatorio> montaRelatorioColaboradorCertificacao(Long empresaId, Certificacao certificacao, Long[] areaIds, Long[] estabelecimentoIds) throws Exception
	{
		certificacao.setNome(certificacaoManager.findById(certificacao.getId()).getNome());
		Collection<Curso> cursos = cursoManager.findByCertificacao(certificacao.getId());
		
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findColaboradoresCertificacoes(empresaId, certificacao, null, null, areaIds, estabelecimentoIds, false);
		
		if (colaboradorTurmas == null || colaboradorTurmas.isEmpty())
			throw new ColecaoVaziaException();
		
		// monta aprovados e não aprovados
		carregaResultados(colaboradorTurmas);
		
		setFamiliaAreas(colaboradorTurmas, empresaId);
		
		Collection<ColaboradorCertificacaoRelatorio> colaboradoresCertificacoes = new ArrayList<ColaboradorCertificacaoRelatorio>();
		
		setColaboradoresDaCertificacao(certificacao, cursos, colaboradorTurmas, colaboradoresCertificacoes);
		
		for (ColaboradorCertificacaoRelatorio colaboradorCertificacao : colaboradoresCertificacoes)
		{
			for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) 
			{
				// cruzamento Colaborador x Curso
				if (colaboradorTurma.getColaborador().equals(colaboradorCertificacao.getColaborador())
						&& colaboradorTurma.getCurso().equals(colaboradorCertificacao.getCurso())
						&& colaboradorTurma.getTurma() != null && colaboradorTurma.getTurma().getId() != null)
						// ignoramos instâncias de ColaboradorTurma sem Turma (veja DNT)
				{
					colaboradorCertificacao.setTurma(colaboradorTurma.getTurma());
					colaboradorCertificacao.setAprovado(colaboradorTurma.isAprovado());
					colaboradorCertificacao.setValorAvaliacao(colaboradorTurma.getNota());
				}
			}
		}
		
		return colaboradoresCertificacoes;
	}
	
	public void carregaResultados(Collection<ColaboradorTurma> colaboradorTurmas) 
	{
		for (ColaboradorTurma ct : colaboradorTurmas) 
		{
			boolean aprovadoPresenca = verificaPresenca(ct);
			boolean aprovadoNota = verificaNota(ct);
			
			ct.setAprovado(aprovadoPresenca && aprovadoNota);
		}
	}
	
	private boolean verificaNota(ColaboradorTurma ct)
	{
		boolean aprovadoNota = true;
		if(ct.getQtdAvaliacoesCurso() != null)
		{
			if(ct.getQtdAvaliacoesAprovadasPorNota() == null)
				ct.setQtdAvaliacoesAprovadasPorNota(0);
				
			if(!ct.getQtdAvaliacoesCurso().equals(ct.getQtdAvaliacoesAprovadasPorNota()))
				aprovadoNota = false;
			
			if(!ct.getQtdAvaliacoesCurso().equals(1))//só é para ter nota se existir apenas uma avaliação no curso
				ct.setNota(null);
		}
		return aprovadoNota;
	}
	
	private boolean verificaPresenca(ColaboradorTurma ct) 
	{
		boolean aprovado = true;
		if(ct.getTotalDias() != null && !ct.getTotalDias().equals(0))
		{
			Double presenca = 0.0;
			if(ct.getQtdPresenca() != null && !ct.getQtdPresenca().equals(0))
				presenca = calculaPresenca(ct);
			
			if(ct.getCurso().getPercentualMinimoFrequencia() != null && presenca < ct.getCurso().getPercentualMinimoFrequencia())
				aprovado = false;
		}
		
		return aprovado;
	}
	
	private Double calculaPresenca(ColaboradorTurma colaboradorTurma) 
	{
		Double presenca;
		double resultado = (double) (colaboradorTurma.getQtdPresenca().doubleValue() / colaboradorTurma.getTotalDias().doubleValue());

		BigDecimal valor = new BigDecimal(resultado);
		valor.setScale(2, BigDecimal.ROUND_UP); //Seta o n° de casas decimais para 2 e o arredondamento para cima  
		presenca = valor.doubleValue() * 100.00;
		return presenca;
	}

	//BACALHAU Consulta muito grande quando muitos colaboradores
	private void setAprovacoesDosColaboradoresTurmas(Collection<ColaboradorTurma> colaboradorTurmas) throws Exception 
	{
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas)
		{
			Long turmaId = colaboradorTurma.getTurma().getId();
			
			if (turmaId == null)
				continue;
			
			Integer qtdAvaliacoes = avaliacaoCursoManager.countAvaliacoes(turmaId, "T");

			if(qtdAvaliacoes.equals(0) && colaboradorAprovadoByTurma(colaboradorTurma.getColaborador().getId(), colaboradorTurma.getTurma().getId()))
			{
					colaboradorTurma.setAprovado(true);
					continue;
			}
			
			Collection<ColaboradorTurma> aprovados = null;
				
			// consulta com nota apenas se tiver só uma avaliação
			if (qtdAvaliacoes == 1)
				aprovados = aproveitamentoAvaliacaoCursoManager.findColaboradorTurma(turmaId, qtdAvaliacoes, "T", true);
			else
			{
				Collection<Long> colaboradorTurmaIds = aproveitamentoAvaliacaoCursoManager.find(turmaId, qtdAvaliacoes, "T", true);
				aprovados = new CollectionUtil<ColaboradorTurma>().convertArrayLongToCollection(ColaboradorTurma.class, (Long[])colaboradorTurmaIds.toArray(new Long[colaboradorTurmaIds.size()]));
			}

			for (ColaboradorTurma colaboradorTurma2 : aprovados)
			{
				if (colaboradorTurma.getId().equals(colaboradorTurma2.getId()))
				{
					if(colaboradorAprovadoByTurma(colaboradorTurma.getColaborador().getId(), colaboradorTurma.getTurma().getId()))
						colaboradorTurma.setAprovado(true);
					
					colaboradorTurma.setValorAvaliacao(colaboradorTurma2.getValorAvaliacao());
					
					break;
				}
			}
			
			if (qtdAvaliacoes == 1)
			{
				setReprovadosMaisNota(turmaId, qtdAvaliacoes, "T", colaboradorTurmas);
			}
		}
	}
	
	private Boolean colaboradorAprovadoByTurma (Long colaboradorId, Long turmaId)
	{
		Collection<Long> turmaIds = new ArrayList<Long>();
		turmaIds.add(turmaId);
		Collection<Colaborador> colaboradorAprovados = findAprovadosByTurma(turmaIds);
		
		for (Colaborador colab : colaboradorAprovados) {
			if (colaboradorId.equals(colab.getId()))
					return true;
			}
			
		return false;
	}
	
	private void setColaboradoresDaCertificacao(Certificacao certificacao, Collection<Curso> cursos, Collection<ColaboradorTurma> colaboradorTurmas,
			Collection<ColaboradorCertificacaoRelatorio> colaboradoresCertificacoes) {
		
		Collection<Long> colabIds = new TreeSet<Long>();
		
		for (ColaboradorTurma  colaboradorTurma : colaboradorTurmas)
		{
			if (colabIds.contains(colaboradorTurma.getColaborador().getId()))
					continue;
			else
				colabIds.add(colaboradorTurma.getColaborador().getId());
			
			for (Curso curso : cursos)
			{
				ColaboradorCertificacaoRelatorio relatorio = new ColaboradorCertificacaoRelatorio(colaboradorTurma.getColaborador(), certificacao, curso);
				colaboradoresCertificacoes.add(relatorio);
			}
		}
	}
	
	public void setAproveitamentoAvaliacaoCursoManager(AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager)
	{
		this.aproveitamentoAvaliacaoCursoManager = aproveitamentoAvaliacaoCursoManager;
	}

	public void setAvaliacaoCursoManager(AvaliacaoCursoManager avaliacaoCursoManager)
	{
		this.avaliacaoCursoManager = avaliacaoCursoManager;
	}
	public void setCertificacaoManager(CertificacaoManager certificacaoManager) {
		this.certificacaoManager = certificacaoManager;
	}

	public void saveColaboradorTurmaNota(Turma turma, Colaborador colaborador, Long[] avaliacaoCursoIds, String[] notas) throws Exception
	{
		ColaboradorTurma colaboradorTurma = getDao().findByColaboradorAndTurma(turma.getId(), colaborador.getId());
		
		if(colaboradorTurma == null || colaboradorTurma.getId() == null)
		{
			colaboradorTurma = new ColaboradorTurma();
			colaboradorTurma.setTurma(turma);
			colaboradorTurma.setCurso(turma.getCurso());
			colaboradorTurma.setColaborador(colaborador);
			getDao().save(colaboradorTurma);			
		}
		
		aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurma, notas, avaliacaoCursoIds);
	}
	
	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Collection<ColaboradorTurma> findColaboradorByTurma(Long turmaId)
	{
		return getDao().findColaboradorByTurma(turmaId);
	}
	
	public Integer findQuantidade(Date dataIni, Date dataFim, Long empresaId) 
	{
		return getDao().findQuantidade(dataIni, dataFim, empresaId);
	}

	public void setDiaTurmaManager(DiaTurmaManager diaTurmaManager) 
	{
		this.diaTurmaManager = diaTurmaManager;
	}
	
}