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
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Certificado;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.relatorio.ColaboradorCertificacaoRelatorio;
import com.fortes.rh.model.desenvolvimento.relatorio.ColaboradorCursoMatriz;
import com.fortes.rh.model.desenvolvimento.relatorio.CursoPontuacaoMatriz;
import com.fortes.rh.model.desenvolvimento.relatorio.SomatorioCursoMatriz;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusAprovacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TAula;
import com.fortes.rh.thread.certificaColaboradorThread;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;
import com.ibm.icu.math.BigDecimal;

@SuppressWarnings("unchecked")
public class ColaboradorTurmaManagerImpl extends GenericManagerImpl<ColaboradorTurma, ColaboradorTurmaDao> implements ColaboradorTurmaManager
{
	private AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private CertificacaoManager certificacaoManager;
	private ColaboradorManager colaboradorManager;
	private EmpresaManager empresaManager;
	private CursoManager cursoManager;

	public Collection<ColaboradorTurma> filtrarColaboradores(int page, int pagingSize, String[] areasCheck, String[] cargosCheck, String[] estabelecimentosCheck, String[] gruposCheck, String[] colaboradoresCursosCheck, Turma turma, Colaborador colaborador, Date dataAdmissaoIni, Date dataAdmissaoFim, Long empresaId) throws ColecaoVaziaException
	{
		Collection<ColaboradorTurma> retorno = new ArrayList<ColaboradorTurma>();
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();

		colaboradores = colaboradorManager.findByAreasOrganizacionalIds(page, pagingSize, LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(cargosCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), colaborador, dataAdmissaoIni, dataAdmissaoFim, empresaId, true, false, null);
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

	@SuppressWarnings("rawtypes")
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


	public void saveUpdate(String[] colaboradorTurma, String[] selectPrioridades, boolean validarCertificacao) throws Exception
	{
		if(colaboradorTurma != null && colaboradorTurma.length > 0)
		{
			for(int i=0; i<colaboradorTurma.length; i++)
			{
				Long colaboradorTurmaId = Long.parseLong(colaboradorTurma[i].replace(".", ""));
				getDao().updateColaboradorTurmaSetPrioridade(colaboradorTurmaId, Long.parseLong(selectPrioridades[i].replace(".", "")));
				getDao().getHibernateTemplateByGenericDao().flush();
				
				if(validarCertificacao)
					new certificaColaboradorThread(colaboradorCertificacaoManager, colaboradorTurmaId, certificacaoManager).start();
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

		@SuppressWarnings("rawtypes")
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

		@SuppressWarnings("rawtypes")
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
	
	public Collection<ColaboradorTurma> findByTurma(Long turmaId, Long empresaId, boolean exibirSituacaoAtualColaborador, Integer page, Integer pagingSize, boolean controlaVencimentoPorCertificacao)
	{
		Collection<ColaboradorTurma> colaboradoresTurma = getDao().findByTurma(turmaId, null, empresaId, null, null, exibirSituacaoAtualColaborador, page, pagingSize);
		if(controlaVencimentoPorCertificacao)
			for (ColaboradorTurma colaboradorTurma : colaboradoresTurma) {
				colaboradorTurma.setCertificadoEmTurmaPosterior(colaboradorCertificacaoManager.existeColaboradorCertificadoEmUmaTurmaPosterior(turmaId, colaboradorTurma.getColaborador().getId()));
			}
		return colaboradoresTurma;
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
		@SuppressWarnings("rawtypes")
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

	public Collection<ColaboradorTurma> findRelatorioSemTreinamento(Long empresaId, Long[] cursosIds, Long[] areaIds, Long[] estabelecimentoIds, Integer qtdMesesSemCurso, String situacaoColaborador, char aprovadoFiltro) throws Exception
	{
		Date data = new Date();
		if(qtdMesesSemCurso != null && qtdMesesSemCurso >= 0)
			data = DateUtil.incrementaMes(data, -1*qtdMesesSemCurso); 
		
		Collection<ColaboradorTurma> colaboradorTurmasRetorno = new ArrayList<ColaboradorTurma>();
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findRelatorioSemTreinamento(empresaId, cursosIds, areaIds, estabelecimentoIds, data, situacaoColaborador);
		
		if(aprovadoFiltro != 'T')
			colaboradorTurmasRetorno = filtraColaboradorTurmaAprovadosOuReprovadosByFiltroAprovado(aprovadoFiltro, colaboradorTurmas);
		else
		{
			ColaboradorTurma ct;
			Collection<Curso> cursos = cursoManager.findByEmpresaIdAndCursosId(empresaId, cursosIds);
			Collection<Colaborador> colaboradores = colaboradorManager.findByEmpresaAndStatusAC(empresaId, estabelecimentoIds, areaIds, StatusRetornoAC.CONFIRMADO, false, false, situacaoColaborador, false, new String[]{"emp.nome", "e.nome","areaOrganizacionalNome", "c.nome"});
			boolean adicionarColaboardorTurma = true; 

			for (Curso curso : cursos) 
			{
				for (Colaborador colaborador : colaboradores) 
				{
					for (ColaboradorTurma colaboradorTurma : colaboradorTurmas)
					{
						if(curso.getId().equals(colaboradorTurma.getCurso().getId()) && colaborador.getId().equals(colaboradorTurma.getColaborador().getId()))
						{
							colaboradorTurmasRetorno.add(colaboradorTurma);
							adicionarColaboardorTurma = false;
							break;
						}
					}	
						
					if(adicionarColaboardorTurma)
					{
						ct = new ColaboradorTurma();
						ct.setColaborador(colaborador);
						ct.setCurso(curso);
						colaboradorTurmasRetorno.add(ct);
					}

					adicionarColaboardorTurma = true;
				}
			}		
		}
		
		if(colaboradorTurmasRetorno == null || colaboradorTurmasRetorno.isEmpty())
			throw new ColecaoVaziaException("Não existem dados para o filtro informado.");
		
		return colaboradorTurmasRetorno;
	}

	public Collection<ColaboradorTurma> findRelatorioComTreinamento(Long empresaId, Long[] cursosIds, Long[] areaIds, Long[] estabelecimentoIds, Date dataIni, Date dataFim, char aprovadoFiltro, String situacao) throws Exception
	{
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findRelatorioComTreinamento(empresaId, cursosIds, areaIds, estabelecimentoIds, dataIni, dataFim, situacao, aprovadoFiltro);
		if (colaboradorTurmas == null || colaboradorTurmas.isEmpty())
			throw new ColecaoVaziaException();
		
		return colaboradorTurmas;
	}

	private Collection<ColaboradorTurma> filtraColaboradorTurmaAprovadosOuReprovadosByFiltroAprovado(char aprovadoFiltro, Collection<ColaboradorTurma> colaboradorTurmas) 
	{
		Boolean aprovado = null;
		if(aprovadoFiltro == StatusAprovacao.APROVADO)
			aprovado = true;
		if(aprovadoFiltro == StatusAprovacao.REPROVADO)
			aprovado = false;
		
		Collection<ColaboradorTurma> retorno = new ArrayList<ColaboradorTurma>();
		for (ColaboradorTurma ct : colaboradorTurmas) 
		{
			ct.setAprovado(verificaAprovacao(ct));
			if(aprovado == null || ct.isAprovado() == aprovado)
				retorno.add(ct);
		}
		
		return retorno;
	}
	
	public Collection<ColaboradorTurma> findByTurmaSemPresenca(Long turmaId, Long diaTurmaId)
	{
		return getDao().findByTurmaSemPresenca(turmaId, diaTurmaId);
	}
	
	public Collection<ColaboradorTurma> findByTurmaPresenteNoDiaTurmaId(Long turmaId, Long diaTurmaId)
	{
		return getDao().findByTurmaPresenteNoDiaTurmaId(turmaId, diaTurmaId);
	}

	public String insereColaboradorTurmas(Long[] colaboradoresId, Collection<ColaboradorTurma> colaboradoresTurmas, Turma turma, DNT dnt, int filtrarPor, String[] selectPrioridades, boolean validarCertificacao, CursoLnt cursoLnt){
		ColaboradorTurma colaboradorTurma = null;
		String msgAlert = "";
		boolean jaInscrito;

		for(int i = 0; i < colaboradoresId.length; i++){
			jaInscrito = false;
			for (ColaboradorTurma ccTemp : colaboradoresTurmas)
				if(ccTemp.getColaborador().getId().equals(colaboradoresId[i]) && turma.getId().equals(ccTemp.getTurma().getId())){
					jaInscrito = true;
					colaboradorTurma = ccTemp;
					if(cursoLnt != null)
						getDao().updateCursoLnt(colaboradorTurma.getId(), cursoLnt.getId());
				}

			if(!jaInscrito){
				if(filtrarPor == 4)	{
					updateTurmaEPrioridade(colaboradoresId[i], turma.getId(), Long.parseLong(selectPrioridades[i]));
				}else{
					colaboradorTurma = new ColaboradorTurma();
					Colaborador colaborador = new Colaborador();
					colaborador.setId(colaboradoresId[i]);
					colaboradorTurma.setColaborador(colaborador);
					colaboradorTurma.setTurma(turma);
					colaboradorTurma.setDnt(dnt);
					colaboradorTurma.setCurso(turma.getCurso());
					colaboradorTurma.setCursoLnt(cursoLnt);
					save(colaboradorTurma);
				}
			}
			
			boolean colaboradorTurmaAprovado = aprovarOrReprovarColaboradorTurma(colaboradorTurma.getId(), colaboradorTurma.getTurma().getId(), turma.getCurso().getId());

			if(validarCertificacao && colaboradorTurma.getId() != null){
				getDao().getHibernateTemplateByGenericDao().flush();
				if(colaboradorTurmaAprovado)
					new certificaColaboradorThread(colaboradorCertificacaoManager, colaboradorTurma.getId(), certificacaoManager).start();
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
	
	public String verificaColaboradorCertificado(Long[] colaboradoresId, Long cursoId)
	{
		Colaborador colaborador = null;
		Collection<Certificacao> certificacoes = certificacaoManager.findByCursoId(cursoId);
		String msgAlert = "";
		String msgAlertColaborador = "";
		
		boolean possuiPreRequisito;

		for(Certificacao certificacao : certificacoes)
		{
			possuiPreRequisito = true;

			while(possuiPreRequisito){
				for(int i = 0; i < colaboradoresId.length; i++)
				{
					colaborador = getDao().verificaColaboradorCertificado(colaboradoresId[i], certificacao.getCertificacaoPreRequisito().getId());
					if(colaborador == null){
						colaborador = colaboradorManager.findColaboradorByIdProjection(colaboradoresId[i]);
						msgAlertColaborador += "&bull; " +colaborador.getNome() + "<br>";
					}
				}
				if(!msgAlertColaborador.isEmpty()  ){
					msgAlert += "Certificação que tem pré-requisito: <b>" + certificacao.getNome() + "</b><br>";
					msgAlert += "Pré-requisito: <b>"+ certificacao.getCertificacaoPreRequisito().getNome() + "</b><br><br>";
					msgAlert += "Colaboradores não certificados no pré-requisito: </br>" + msgAlertColaborador;
					msgAlert +="</br></br>";
				}
				msgAlertColaborador = "";
				
				Certificacao certificacaoTemp = certificacaoManager.findById(certificacao.getCertificacaoPreRequisito().getId());
				
				if(certificacaoTemp.getCertificacaoPreRequisito() != null && certificacaoTemp.getCertificacaoPreRequisito().getId() != null){
					certificacao = certificacaoTemp;
				}
				else possuiPreRequisito = false;
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
		colaboradorCertificacaoManager.descertificarColaboradorByColaboradorTurma(colaboradorTurma.getId());
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
		return getDao().findHistoricoTreinamentosByColaborador(empresaId, dataIni, dataFim, colaboradorIds);
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

	public Collection<Colaborador> findAprovadosByCertificacao(Certificacao certificacao, int qtdCursos, boolean controlarVencimentoPorCertificacao) {
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		Collection<Colaborador> aprovados = new ArrayList<Colaborador>();
		Collection<Colaborador> reprovados = new ArrayList<Colaborador>();
		
		if(controlarVencimentoPorCertificacao)
			colaboradorTurmas = getDao().findColaboradorTurmaByCertificacaoControleVencimentoPorCertificacao(certificacao.getId());
		else
			colaboradorTurmas = getDao().findColaboradorTurmaByCertificacaoControleVencimentoPorCurso(certificacao.getId(), qtdCursos);
		
		for (ColaboradorTurma ct : colaboradorTurmas){
			if(ct.isCertificado())
				aprovados.add(ct.getColaborador());
			else {
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
		colaboradores.addAll(reprovados);
		return colaboradores;
	}
	
	//TODO BACALHAU refatorar todo o metodo, tem varios metodos dependentes, criar teste
	public Collection<ColaboradorCertificacaoRelatorio> montaRelatorioColaboradorCertificacao(Long empresaId, Certificacao certificacao, Long[] areaIds, Long[] estabelecimentoIds, Date dataInicio, Date dataFim, char tipoAgrupamento) throws Exception
	{
		certificacao.setNome(certificacaoManager.findById(certificacao.getId()).getNome());
		Collection<Curso> cursos = cursoManager.findByCertificacao(certificacao.getId());
	
		String ordenacao = "";
		if (tipoAgrupamento == '0') {
			ordenacao = " e.nome, areaNome, co.nome, c.nome ";
		} else {
			ordenacao = " c.nome, e.nome, areaNome, co.nome ";
		}
		
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findAprovadosReprovados(empresaId, certificacao, null, areaIds, estabelecimentoIds, dataInicio, dataFim, ordenacao, true, SituacaoColaborador.ATIVO);
		
		if (colaboradorTurmas == null || colaboradorTurmas.isEmpty())
			throw new ColecaoVaziaException();
		
		/**
		 *monta aprovados e não aprovados
		 */
		carregaResultados(colaboradorTurmas);
		
		Collection<ColaboradorCertificacaoRelatorio> colaboradoresCertificacoes = new ArrayList<ColaboradorCertificacaoRelatorio>();
		
		setColaboradoresDaCertificacao(certificacao, cursos, colaboradorTurmas, colaboradoresCertificacoes, tipoAgrupamento);
		
		setTurmasColaboradorCertificacao(colaboradorTurmas, colaboradoresCertificacoes);
		
		return colaboradoresCertificacoes;
	}

	private void setTurmasColaboradorCertificacao(Collection<ColaboradorTurma> colaboradorTurmas, Collection<ColaboradorCertificacaoRelatorio> colaboradoresCertificacoes)
	{
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
	
	private void setColaboradoresDaCertificacao(Certificacao certificacao, Collection<Curso> cursos, Collection<ColaboradorTurma> colaboradorTurmas,
			Collection<ColaboradorCertificacaoRelatorio> colaboradoresCertificacoes, Character tipoAgrupamento) {
		
		Collection<Long> colabIds = new TreeSet<Long>();
		
		if ( tipoAgrupamento == '0' ) {
			for (ColaboradorTurma  colaboradorTurma : colaboradorTurmas)
			{
				if (colabIds.contains(colaboradorTurma.getColaborador().getId()))
					continue;

				colabIds.add(colaboradorTurma.getColaborador().getId());
				
				for (Curso curso : cursos)
				{
					ColaboradorCertificacaoRelatorio relatorio = new ColaboradorCertificacaoRelatorio(colaboradorTurma.getColaborador(), certificacao, curso);
					colaboradoresCertificacoes.add(relatorio);
				}
			}
		} else {
			for (Curso curso : cursos)
			{
				for (ColaboradorTurma  colaboradorTurma : colaboradorTurmas)
				{
					ColaboradorCertificacaoRelatorio relatorio = new ColaboradorCertificacaoRelatorio(colaboradorTurma.getColaborador(), certificacao, curso);
					colaboradoresCertificacoes.add(relatorio);
				}
			}
		}
	}

	public void saveColaboradorTurmaNota(Turma turma, Colaborador colaborador, Long[] avaliacaoCursoIds, String[] notas, boolean controlaVencimentoPorCertificacao) throws Exception
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
		
		aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurma, notas, avaliacaoCursoIds, controlaVencimentoPorCertificacao);
	}

	public Collection<ColaboradorTurma> findColaboradorByTurma(Long turmaId, Long avaliacaoCursoId, boolean controlaVencimentoPorCertificacao)
	{
		Collection<ColaboradorTurma> colaboradoresTurma = getDao().findColaboradorByTurma(turmaId, avaliacaoCursoId);

		if(controlaVencimentoPorCertificacao)
			for (ColaboradorTurma colaboradorTurma : colaboradoresTurma) {
				colaboradorTurma.setCertificadoEmTurmaPosterior(colaboradorCertificacaoManager.existeColaboradorCertificadoEmUmaTurmaPosterior(turmaId, colaboradorTurma.getColaborador().getId()));
			}
		
		return colaboradoresTurma;
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
		return getDao().findAprovadosByTurma(turmaId);
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
	
	public Collection<ColaboradorTurma> findCursosVencidosAVencer(Date dataIni, Date dataFim, Long[] empresasIds, Long[] cursosIds, char filtroAgrupamento, char filtroSituacao, char filtroAprovado) 
	{
		Collection<ColaboradorTurma> colaboradorTurmas = getDao().findCursosVencidosAVencer(dataIni, empresasIds, cursosIds, filtroAgrupamento, filtroSituacao, filtroAprovado);
		Collection<ColaboradorTurma> colaboradorTurmasRetorno = new ArrayList<ColaboradorTurma>();
		
		for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) 
			if(colaboradorTurma.getTurma()!= null && colaboradorTurma.getTurma().getVencimento() != null && colaboradorTurma.getTurma().getVencimento().compareTo(dataFim) <= 0 )
				colaboradorTurmasRetorno.add(colaboradorTurma);
		
		return colaboradorTurmasRetorno;
	}

	public Collection<ColaboradorTurma> findCursosCertificacoesAVencer(Date dataReferencia, Long empresaId) {
		return getDao().findCursosCertificacoesAVencer(dataReferencia, empresaId);
	}
	
	public Collection<ColaboradorTurma> findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId(Long certificacaoId, Long colaboradorCertificacaoId, Long... colaboradoresId){
		return getDao().findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId(certificacaoId, colaboradorCertificacaoId, colaboradoresId);
	}
	
	public Collection<ColaboradorTurma> findByTurmaId(Long turmaId) {
		return getDao().findByTurmaId(turmaId);
	}
	
	public boolean aprovarOrReprovarColaboradorTurma(Long colaboradorTurmaId, Long turmaId, Long cursoId) {
		return getDao().aprovarOrReprovarColaboradorTurma(colaboradorTurmaId, turmaId, cursoId);
	}
	
	public ColaboradorTurma findByProjection(Long colaboradorTurmaId) {
		return getDao().findByProjection(colaboradorTurmaId);
	}

	public Collection<Colaborador> findColabodoresByTurmaId(Long turmaId) {
		return getDao().findColabodoresByTurmaId(turmaId);
	}

	public Collection<ColaboradorTurma> findByCursoLntId(Long cursoLntId) {
		return getDao().findByCursoLntId(cursoLntId);
	}

	public void removeAllCursoLntByLnt(Long lntId){
		getDao().removeAllCursoLntByLnt(lntId);
	}
	
	public void updateCursoLnt(Long cursoId, Long colaboradorTurmaId, Long lntId) {
		if(lntId == null)
			getDao().removeCursoLnt(colaboradorTurmaId);
		else
			getDao().updateCursoLnt(cursoId, colaboradorTurmaId, lntId);
	}

	public void removeCursoLntByParticipantesCursoLnt(Long[] participantesRemovidos) {
		getDao().removeCursoLntByParticipantesCursoLnt(participantesRemovidos);
	}
	
	public Collection<Turma> findParticipantesCursoLntAgrupadoNaTurma(Long cursoLntId) {
		Collection<ColaboradorTurma> participantesCursoLnt = getDao().findParticipantesCursoLnt(cursoLntId); 
		Map<Long, Turma> turmasComParticipantes = new HashMap<Long, Turma>();
		
		if(participantesCursoLnt != null && participantesCursoLnt.size() > 0){
			for (ColaboradorTurma participanteCursoLnt : participantesCursoLnt) {
				if(!turmasComParticipantes.containsKey(participanteCursoLnt.getTurma().getId()))
					turmasComParticipantes.put(participanteCursoLnt.getTurma().getId(), participanteCursoLnt.getTurma());
				
				if(turmasComParticipantes.get(participanteCursoLnt.getTurma().getId()).getColaboradorTurmas() == null || turmasComParticipantes.get(participanteCursoLnt.getTurma().getId()).getColaboradorTurmas().size() == 0)
					turmasComParticipantes.get(participanteCursoLnt.getTurma().getId()).setColaboradorTurmas(new ArrayList<ColaboradorTurma>());
				
				turmasComParticipantes.get(participanteCursoLnt.getTurma().getId()).getColaboradorTurmas().add(participanteCursoLnt);
			}
		}
		
		return turmasComParticipantes.values();
	}

	
	public void setColaboradorCertificacaoManager(ColaboradorCertificacaoManager colaboradorCertificacaoManager) {
		this.colaboradorCertificacaoManager = colaboradorCertificacaoManager;
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

}