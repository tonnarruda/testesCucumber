package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Certificado;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.relatorio.ColaboradorCertificacaoRelatorio;
import com.fortes.rh.model.desenvolvimento.relatorio.ColaboradorCursoMatriz;
import com.fortes.rh.model.desenvolvimento.relatorio.CursoPontuacaoMatriz;
import com.fortes.rh.model.desenvolvimento.relatorio.SomatorioCursoMatriz;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TAula;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;
import com.ibm.icu.math.BigDecimal;

@SuppressWarnings("unchecked")
public class ColaboradorTurmaManagerImpl extends GenericManagerImpl<ColaboradorTurma, ColaboradorTurmaDao> implements ColaboradorTurmaManager
{
	private ColaboradorManager colaboradorManager;
	private EmpresaManager empresaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private CursoManager cursoManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager;
	private AvaliacaoCursoManager avaliacaoCursoManager;
	private CertificacaoManager certificacaoManager;

	public Collection<ColaboradorTurma> filtrarColaboradores(int page, int pagingSize, String[] areasCheck, String[] cargosCheck, String[] estabelecimentosCheck, String[] gruposCheck, String[] colaboradoresCursosCheck, Turma turma, Colaborador colaborador, Date dataAdmissaoIni, Date dataAdmissaoFim, Long empresaId) throws ColecaoVaziaException
	{
		Collection<ColaboradorTurma> retorno = new ArrayList<ColaboradorTurma>();
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();

		colaboradores = colaboradorManager.findByAreasOrganizacionalIds(page, pagingSize, LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(cargosCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), colaborador, dataAdmissaoIni, dataAdmissaoFim, empresaId, true);
		if (colaboradores != null && colaboradores.size() > 0)
			retorno = geraColaboradorTurma(colaboradores, turma);

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

	public Integer getCount(Long turmaId, Long empresaId, String colaboradorNome, Long[] estabelecimentoIds, Long[] cargoIds)
	{
		return getDao().getCount(turmaId, empresaId, colaboradorNome, estabelecimentoIds, cargoIds);
	}

	public Collection<ColaboradorTurma> filtroRelatorioPlanoTrei(LinkedHashMap filtro)
	{
		return getDao().filtroRelatorioPlanoTrei(filtro);
	}

	public Collection<ColaboradorTurma> findRelatorioSemIndicacaoTreinamento(Long empresaId, Long[] areaIds, Long[] estabelecimentoIds, int qtdMeses) throws ColecaoVaziaException
	{
		Calendar data = criaDataDiminuindoMeses(qtdMeses);

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
				resultado.add(colaboradorTurma);
		}

		if (resultado.isEmpty())
			throw new ColecaoVaziaException("Não existem dados para o filtro informado.");

		return resultado;
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
		parametros.put("CURSO_CARGA_HORARIA", colaboradorTurma.getCurso().getCargaHorariaMinutos());
		parametros.put("TURMA_DESCRICAO", colaboradorTurma.getTurma().getDescricao());
		parametros.put("TURMA_INSTRUTOR", colaboradorTurma.getTurma().getInstrutor());
		parametros.put("TURMA_HORARIO", colaboradorTurma.getTurma().getHorario());

		if(colaboradorTurma.getTurma() == null || colaboradorTurma.getTurma().getDataPrevIni() == null || colaboradorTurma.getTurma().getDataPrevFim() == null)
			throw new Exception("A Turma informada esta sem Período.");

		parametros.put("TURMA_DATA_PREVI_INI", DateUtil.formataDiaMesAno(colaboradorTurma.getTurma().getDataPrevIni()));
		parametros.put("TURMA_DATA_PREVI_FIM", DateUtil.formataDiaMesAno(colaboradorTurma.getTurma().getDataPrevFim()));

		return parametros;
	}
	
	public Collection<Long> findIdEstabelecimentosByTurma(Long turmaid, Long empresaId) 
	{
		return getDao().findIdEstabelecimentosByTurma(turmaid, empresaId);
	}
	
	public Collection<ColaboradorTurma> findByTurma(Long turmaId, Long empresaId, boolean exibirSituacaoAtualColaborador, Integer page, Integer pagingSize)
	{
		return getDao().findByTurma(turmaId, null, empresaId, null, null, exibirSituacaoAtualColaborador, page, pagingSize);
	}

	public Collection<ColaboradorTurma> findByTurmaColaborador(Long turmaId, Long empresaId, String colaboradorNome, Long[] estabelecimentoIds, Long[] cargoIds, Integer page, Integer pagingSize)
	{
		return getDao().findByTurma(turmaId, colaboradorNome, empresaId, estabelecimentoIds, cargoIds, true, page, pagingSize);
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
		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, empresaId);
		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);

		for (ColaboradorTurma colaboradorTurma: colaboradorTurmas)
		{
			if(colaboradorTurma.getColaborador() != null && colaboradorTurma.getColaborador().getAreaOrganizacional() != null && colaboradorTurma.getColaborador().getAreaOrganizacional().getId() != null)
				colaboradorTurma.getColaborador().setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, colaboradorTurma.getColaborador().getAreaOrganizacional().getId()));
		}

		return colaboradorTurmas;
	}

	public Collection<ColaboradorTurma> findRelatorioSemTreinamento(Long empresaId, Long[] cursosIds, Long[] areaIds, Long[] estabelecimentoIds, Integer qtdMesesSemCurso) throws Exception
	{
		Date data = null;
		if(qtdMesesSemCurso != null && qtdMesesSemCurso >= 0)
			data = criaDataDiminuindoMeses(qtdMesesSemCurso).getTime();
		
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findRelatorioSemTreinamento(empresaId, cursosIds, areaIds, estabelecimentoIds, data);

		if(colaboradorTurmas == null || colaboradorTurmas.isEmpty())
			throw new ColecaoVaziaException("Não existem dados para o filtro informado.");
		
		return colaboradorTurmas;
	}

	public Collection<ColaboradorTurma> findRelatorioComTreinamento(Long empresaId, Long[] cursosIds, Long[] areaIds, Long[] estabelecimentoIds, Date dataIni, Date dataFim, char aprovadoFiltro, String situacao) throws Exception
	{
		Boolean aprovado = aprovadoFiltro == 'T' ? null : aprovadoFiltro == 'S';
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findAprovadosReprovados(empresaId, null, cursosIds, areaIds, estabelecimentoIds, dataIni, dataFim, " c.nome, emp.nome, e.nome, a.nome, co.nome ", true, situacao);
		
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

	public Collection<ColaboradorTurma> findByTurmaSemPresenca(Long turmaId, Long diaTurmaId)
	{
		return getDao().findByTurmaSemPresenca(turmaId, diaTurmaId);
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
				if(ccTemp.getColaborador().getId().equals(colaboradoresId[i]) && turma.getId().equals(ccTemp.getTurma().getId()))
					jaInscrito = true;

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
	
	public String checaColaboradorInscritoEmOutraTurma(Long[] colaboradoresId, Collection<ColaboradorTurma> colaboradoresTurmas, Long turmaId)
	{
		String msgAlert = "";
		
		for(int i = 0; i < colaboradoresId.length; i++)
		{
			for (ColaboradorTurma ccTemp : colaboradoresTurmas)
			{
				if(ccTemp.getColaborador().getId().equals(colaboradoresId[i]) && !turmaId.equals(ccTemp.getTurma().getId()))
				{
					msgAlert += ccTemp.getColaborador().getNome() + "  (Turma: " + ccTemp.getTurma().getDescricao() + ")<br>";
					break;
				}
			}
		}
		
		return msgAlert;
	}

	@Override
	public void remove(ColaboradorTurma colaboradorTurma)
	{
		// Remove os Questionarios/Respostas vinculados ao colaborador nesta turma
		colaboradorQuestionarioManager.removeByColaboradorETurma(colaboradorTurma.getColaborador().getId(), colaboradorTurma.getTurma().getId());
		aproveitamentoAvaliacaoCursoManager.removeByColaboradorTurma(colaboradorTurma.getId());
		super.remove(colaboradorTurma);
	}

	public Collection<ColaboradorTurma> montaColunas(Collection<ColaboradorTurma> colaboradorTurmas, boolean exibirNomeComercial, boolean exibirCargo, boolean exibirEstabelecimento, boolean exibirAssinatura, boolean exibirArea, boolean exibirCPF)
	{
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas)
		{
			//TODO A ordem dos ifs abaixo tem de ser a mesma ordem dos ifs que contem em relatoriopresencaaction.montaParametros()
			if(exibirNomeComercial)
				colaboradorTurma.setColuna01RelatorioPresenca(colaboradorTurma.getColaboradorNomeComercial());
			else
				colaboradorTurma.setColuna01RelatorioPresenca(colaboradorTurma.getColaboradorNome());

			if(exibirCPF)
				colaboradorTurma.setColuna02RelatorioPresenca(colaboradorTurma.getColaborador().getPessoal().getCpfFormatado());
			
			if(exibirCargo)
				if(colaboradorTurma.getColuna02RelatorioPresenca() == null)
					colaboradorTurma.setColuna02RelatorioPresenca(colaboradorTurma.getColaborador().getHistoricoColaborador().getFaixaSalarial().getDescricao());
				else
					colaboradorTurma.setColuna03RelatorioPresenca(colaboradorTurma.getColaborador().getHistoricoColaborador().getFaixaSalarial().getDescricao());

			if(exibirEstabelecimento)
				if(colaboradorTurma.getColuna02RelatorioPresenca() == null)
					colaboradorTurma.setColuna02RelatorioPresenca(colaboradorTurma.getColaborador().getHistoricoColaborador().getEstabelecimento().getNome());
				else
					colaboradorTurma.setColuna03RelatorioPresenca(colaboradorTurma.getColaborador().getHistoricoColaborador().getEstabelecimento().getNome());
			
			if(exibirArea)
			{
				String area = "";
				if(colaboradorTurma.getColaborador().getHistoricoColaborador().getAreaOrganizacional() != null && colaboradorTurma.getColaborador().getHistoricoColaborador().getAreaOrganizacional().getNome() != null)
					area = colaboradorTurma.getColaborador().getHistoricoColaborador().getAreaOrganizacional().getNome();
				
				if(colaboradorTurma.getColuna02RelatorioPresenca() == null)
					colaboradorTurma.setColuna02RelatorioPresenca(area);
				else
					colaboradorTurma.setColuna03RelatorioPresenca(area);
			}

			if(exibirAssinatura)
				if(colaboradorTurma.getColuna02RelatorioPresenca() == null)
					colaboradorTurma.setColuna02RelatorioPresenca("");
				else
					colaboradorTurma.setColuna03RelatorioPresenca("");
		}
		

		return colaboradorTurmas;
	}
	
	public Collection<ColaboradorTurma> findRelatorioHistoricoTreinamentos(Long empresaId, Long[] colaboradorIds, Date dataIni, Date dataFim) throws Exception
	{
		Collection<ColaboradorTurma> colaboradorTurmas = findHistoricoTreinamentosByColaborador(empresaId, dataIni, dataFim, colaboradorIds);

		return colaboradorTurmas;
	}
	
	public Collection<ColaboradorTurma> findHistoricoTreinamentosByColaborador(Long empresaId, Date dataIni, Date dataFim, Long... colaboradorIds) throws Exception
	{
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findHistoricoTreinamentosByColaborador(empresaId, dataIni, dataFim, colaboradorIds);
		
		if(colaboradorTurmas != null)
			setAprovacoesDosColaboradoresTurmas(colaboradorTurmas);

		return colaboradorTurmas;
	}

	public Collection<Colaborador> montaExibicaoAprovadosReprovados(Long empresaId, Long turmaId)
	{
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findAprovadosReprovados(empresaId, null, null, null, null, null, null, " co.nome ", true, SituacaoColaborador.ATIVO, turmaId);
		
		//add colaboradores aprovados. Tem que ser dois for, primeiro os aprovados(é uma regra do multiSelectBox)
		for (ColaboradorTurma ct : colaboradorTurmas) 
		{
			if(verificaPresenca(ct) && verificaNota(ct))
				colaboradores.add(ct.getColaborador());
		}
		
		montaSelectBoxColaborador(colaboradores, colaboradorTurmas);

		return colaboradores;
	}
	
	private void montaSelectBoxColaborador(Collection<Colaborador> colaboradores, Collection<ColaboradorTurma> colaboradorTurmas) {
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
				texto = "(reprovado por nota)";
			
			if(!aprovadoPresenca || !aprovadoNota)
			{
				ct.getColaborador().setNome("<span style='color: red;'>" + ct.getColaborador().getNome() + " " + texto + "</span>");
				colaboradores.add(ct.getColaborador());
			}
		}
	}

	public Double percentualFrequencia(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds, Long[] areasIds, Long[] estabelecimentosIds)
	{
		Integer qtdDiasTotal = colaboradorManager.qtdTotalDiasDaTurmaVezesColaboradoresInscritos(dataIni, dataFim, empresaIds, cursoIds, areasIds, estabelecimentosIds);
		ColaboradorPresencaManager colaboradorPresencaManager = (ColaboradorPresencaManager) SpringUtil.getBean("colaboradorPresencaManager");
		Integer qtdDiasPresentes = colaboradorPresencaManager.qtdDiaPresentesTurma(dataIni, dataFim, empresaIds, cursoIds, areasIds, estabelecimentosIds);

		if (qtdDiasTotal.equals(0)){
			if (qtdDiasPresentes == 0)
				return 0.0;
			return 100.0;
		}
			
		double resultado = (double) (qtdDiasPresentes.doubleValue() / qtdDiasTotal.doubleValue());

		BigDecimal valor = new BigDecimal(resultado);  
		valor.setScale(2, BigDecimal.ROUND_UP); //Seta o n° de casas decimais para 2 e o arredondamento para cima  

		return valor.doubleValue() * 100;  
	}
	
	public Collection<Certificado> montaCertificados(Collection<Colaborador> colaboradores, Certificado certificado, Long empresaId)
	{
		Empresa empresa = empresaManager.findById(empresaId); 
		Boolean imprimirLogoCertificado = empresa.getLogoCertificadoUrl() != null ? true : false;
		
		Collection<Certificado> certificados = new ArrayList<Certificado>(colaboradores.size());
		for (Colaborador colaborador : colaboradores)
		{
			Certificado certificadoTmp = new Certificado();
			certificadoTmp = (Certificado) certificado.clone();
			certificadoTmp.setNomeColaborador(colaborador.getNome());
			certificadoTmp.setImprimirLogoCertificado(imprimirLogoCertificado);
			certificados.add(certificadoTmp);
		}

		return certificados;
	}

	public Collection<Colaborador> findAprovadosByCertificacao(Certificacao certificacao, int qtdCursos)
	{
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findAprovadosReprovados(null, certificacao, null, null, null, null, null, " co.id ", true, SituacaoColaborador.ATIVO);
		
		Collection<Colaborador> aprovados = new ArrayList<Colaborador>();
		Collection<Colaborador> reprovados = new ArrayList<Colaborador>();
		
		
		HashMap<Long, Integer> qtdColabCursos = new HashMap<Long, Integer>();
		
		for (ColaboradorTurma ct : colaboradorTurmas) 
		{
			Long colabId = ct.getColaborador().getId();
			if(qtdColabCursos.get(colabId) != null)
				qtdColabCursos.put(colabId, qtdColabCursos.get(colabId) + 1);
			else
				qtdColabCursos.put(colabId, 1);
		}
		
		for (ColaboradorTurma ct : colaboradorTurmas) 
		{
			if(verificaAprovacao(ct) && qtdColabCursos.get(ct.getColaborador().getId()) == qtdCursos)
				aprovados.add(ct.getColaborador());
			else
			{
				ct.getColaborador().setNome("<span style='color: red;'>" + ct.getColaborador().getNome() + " (Não certificado)</span>");
				reprovados.add(ct.getColaborador());
			}
		}
				
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		
		CollectionUtil<Colaborador> util = new CollectionUtil<Colaborador>();
		aprovados = util.distinctCollection(aprovados);
		aprovados = util.sortCollectionStringIgnoreCase(aprovados, "nome");
		reprovados = util.distinctCollection(reprovados);
		reprovados = util.sortCollectionStringIgnoreCase(reprovados, "nome");
		
		colaboradores.addAll(aprovados);
		for (Colaborador aprovado : aprovados) 
		{
			if(reprovados.contains(aprovado))
				colaboradores.remove(aprovado);
		}
		
		colaboradores.addAll(reprovados);
		
		return colaboradores;
	}
	
	//TODO BACALHAU refatorar todo o metodo, tem varios metodos dependentes, criar teste
	public Collection<ColaboradorCertificacaoRelatorio> montaRelatorioColaboradorCertificacao(Long empresaId, Certificacao certificacao, Long[] areaIds, Long[] estabelecimentoIds, Date dataInicio, Date dataFim) throws Exception
	{
		certificacao.setNome(certificacaoManager.findById(certificacao.getId()).getNome());
		Collection<Curso> cursos = cursoManager.findByCertificacao(certificacao.getId());
		
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findAprovadosReprovados(empresaId, certificacao, null, areaIds, estabelecimentoIds, dataInicio, dataFim, " e.nome, a.nome, co.nome, c.nome ", true, SituacaoColaborador.ATIVO);
		
		if (colaboradorTurmas == null || colaboradorTurmas.isEmpty())
			throw new ColecaoVaziaException();
		
		/**
		 *monta aprovados e não aprovados
		 */
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
			ct.setAprovado(verificaAprovacao(ct));
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
			
			if(ct.getCurso() != null && ct.getCurso().getPercentualMinimoFrequencia() != null && presenca < ct.getCurso().getPercentualMinimoFrequencia())
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
		
		for (Colaborador colab : colaboradorAprovados) 
		{
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

	public void saveColaboradorTurmaNota(Turma turma, Colaborador colaborador, Long[] avaliacaoCursoIds, String[] notas) throws Exception
	{
		Collection<ColaboradorTurma> colaboradoresTurmas = findByTurmaCurso(turma.getCurso().getId());
		
		for (ColaboradorTurma cTurma : colaboradoresTurmas)
		{
			if (cTurma.getColaborador().getId().equals(colaborador.getId())
				&& !cTurma.getTurma().getId().equals(turma.getId()))
			{
				throw new FortesException("Colaborador já inscrito em outra turma deste curso");
			}
		}
		
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

	public Collection<ColaboradorTurma> findColaboradorByTurma(Long turmaId, Long avaliacaoCursoId)
	{
		return getDao().findColaboradorByTurma(turmaId, avaliacaoCursoId);
	}
	
	public HashMap<String, Integer> getResultado(Date dataIni, Date dataFim, Long[] empresaIds, Long[] areasIds, Long[] cursoIds, Long[] estabelecimentosIds) 
	{
		HashMap<String, Integer> resultados = new HashMap<String, Integer>();
		
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findAprovadosReprovados(dataIni, dataFim, empresaIds, areasIds, cursoIds, estabelecimentosIds);
		Integer qtdAprovados = new Integer(0);
		Integer qtdReprovados = new Integer(0);

		for (ColaboradorTurma ct : colaboradorTurmas) 
		{
			if(verificaAprovacao(ct))
				qtdAprovados++;
			else
				qtdReprovados++;
		}
		
		resultados.put("qtdAprovados", qtdAprovados);
		resultados.put("qtdReprovados", qtdReprovados);
		
		return resultados;
	}

	public Collection<Colaborador> findAprovadosByTurma(Collection<Long> turmaIds)
	{
		Collection<Long> colaboradorIds = new ArrayList<Long>();
		
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findAprovadosReprovados(null, null, null, null, null, null, null, " co.nome ", false, "T", LongUtil.collectionStringToArrayLong(turmaIds));
		for (ColaboradorTurma ct : colaboradorTurmas) 
		{
			if(verificaAprovacao(ct))
				colaboradorIds.add(ct.getColaborador().getId());
		}

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		if(!colaboradorIds.isEmpty())
			colaboradors = colaboradorManager.findAllSelect(colaboradorIds, null);

		return colaboradors;
	}

	public Collection<ColaboradorTurma> findAprovadosByTurma(Long turmaId) 
	{
		Collection<ColaboradorTurma> aprovados = new ArrayList<ColaboradorTurma>();
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findAprovadosReprovados(null, null, null, null, null, null, null, " co.nome ", false, "T", turmaId);

		for (ColaboradorTurma ct : colaboradorTurmas) 
		{
			if(verificaAprovacao(ct))
				aprovados.add(ct);
		}
		
		return aprovados;
	}

	public Collection<ColaboradorTurma> filtraAprovadoReprovado(Collection<ColaboradorTurma> colaboradorTurmas, char aprovado) 
	{
		if(aprovado != 'S' && aprovado != 'N')
			return colaboradorTurmas;

		CollectionUtil<ColaboradorTurma> cu = new CollectionUtil<ColaboradorTurma>();
		Long[] colaboradorTurmaIds = cu.convertCollectionToArrayIds(colaboradorTurmas);
		Collection<ColaboradorTurma> colaboradorTurmasTemp = getDao().findAprovadosReprovados(colaboradorTurmaIds);
		
		Collection<ColaboradorTurma> colaboradorTurmasAprovados = new ArrayList<ColaboradorTurma>(); 
		Collection<ColaboradorTurma> colaboradorTurmasReprovados = new ArrayList<ColaboradorTurma>(); 
		
		for (ColaboradorTurma ct : colaboradorTurmasTemp)
		{
			if(verificaPresenca(ct) && verificaNota(ct))
			{
				for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) 
				{
					if(colaboradorTurma.getId().equals(ct.getId()))
					{
						colaboradorTurmasAprovados.add(colaboradorTurma);
						break;
					}
				}
			}
			
			else
			{
				for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) 
				{
					if(colaboradorTurma.getId().equals(ct.getId()))
					{
						colaboradorTurmasReprovados.add(colaboradorTurma);
						break;
					}
				}
				
			}
		}

		if(aprovado == 'S')
			return colaboradorTurmasAprovados;
		else
			return colaboradorTurmasReprovados;
	}
	
	private boolean verificaAprovacao(ColaboradorTurma ct)
	{
		boolean aprovadoPresenca = verificaPresenca(ct);
		boolean aprovadoNota = verificaNota(ct);
		
		return aprovadoPresenca && aprovadoNota;
	}
	
	private Calendar criaDataDiminuindoMeses(int qtdMeses) 
	{
		Calendar data = Calendar.getInstance();
		data.setTime(new Date());
		data.add(Calendar.MONTH, -qtdMeses);
		return data;
	}
	
	public Collection<ColaboradorTurma> findColaboradoresComCustoTreinamentos(Long colaboradorId, Date dataIni, Date dataFim, Boolean realizada) 
	{
		return getDao().findColaboradoresComCustoTreinamentos(colaboradorId, dataIni, dataFim, realizada);
	}
	
	public Collection<ColaboradorTurma> findColaboradoresComEmailByTurma(Long turmaId, boolean somentePresentes) {
		return getDao().findColaboradoresComEmailByTurma(turmaId, somentePresentes);
	}
	
	public Collection<ColaboradorTurma> findColabTreinamentos(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Long[] cursoIds, Long[] turmaIds, boolean considerarSomenteDiasPresente)
	{
		return getDao().findColabTreinamentos(empresaId, estabelecimentoIds, areaIds, cursoIds, turmaIds, considerarSomenteDiasPresente);
	}
	
	public Collection<Colaborador> findColaboradorByCurso(Long[] cursosIds, Long[] turmasIds) 
	{
		return getDao().findColaboradorByCursos(cursosIds, turmasIds);
	}

	public TAula[] getTreinamentosPrevistoParaTRU(String colaboradorCodigoAC, Empresa empresa, String periodoIni, String periodoFim) 
	{
		return getDao().findColaboradorTreinamentosParaTRU(colaboradorCodigoAC, empresa.getId(), periodoIni, periodoFim, false);
	}

	public TAula[] getTreinamentosRealizadosParaTRU(String colaboradorCodigoAC, Empresa empresa, String periodoIni, String periodoFim) 
	{
		return getDao().findColaboradorTreinamentosParaTRU(colaboradorCodigoAC, empresa.getId(), periodoIni, periodoFim, true);
	}
	
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
	
	public void setAproveitamentoAvaliacaoCursoManager(AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager)
	{
		this.aproveitamentoAvaliacaoCursoManager = aproveitamentoAvaliacaoCursoManager;
	}

	public void setAvaliacaoCursoManager(AvaliacaoCursoManager avaliacaoCursoManager)
	{
		this.avaliacaoCursoManager = avaliacaoCursoManager;
	}
	
	public void setCertificacaoManager(CertificacaoManager certificacaoManager) 
	{
		this.certificacaoManager = certificacaoManager;
	}
	
	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
	
	public void setEmpresaManager(EmpresaManager empresaManager) 
	{
		this.empresaManager = empresaManager;
	}

	public Collection<ColaboradorTurma> findCursosVencidosAVencer(Date dataIni, Date dataFim, Long[] empresasIds, Long[] cursosIds, char filtroAgrupamento, char filtroSituacao, char filtroAprovado) {
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findCursosVencidosAVencer(dataIni, empresasIds, cursosIds, filtroAgrupamento, filtroSituacao, filtroAprovado);
		Collection<ColaboradorTurma> colaboradorTurmasRetorno = new ArrayList<ColaboradorTurma>();
		
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) 
			if(colaboradorTurma.getTurma()!= null && colaboradorTurma.getTurma().getVencimento() != null && colaboradorTurma.getTurma().getVencimento().before(dataFim))
				colaboradorTurmasRetorno.add(colaboradorTurma);
		
		return colaboradorTurmasRetorno;
	}

	public Collection<ColaboradorTurma> findCursosCertificacoesAVencer(Date dataReferencia, Long empresaId) {
		return getDao().findCursosCertificacoesAVencer(dataReferencia, empresaId);
	}
}