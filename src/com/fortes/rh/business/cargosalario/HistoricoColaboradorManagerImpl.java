package com.fortes.rh.business.cargosalario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.apache.commons.lang.Validate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.cargosalario.SituacaoColaborador;
import com.fortes.rh.model.cargosalario.relatorio.RelatorioPromocoes;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoBuscaHistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.ws.TRemuneracaoVariavel;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.ModelUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.ws.AcPessoalClientColaborador;
import com.fortes.rh.web.ws.AcPessoalClientTabelaReajusteInterface;

@SuppressWarnings({"unchecked"})
public class HistoricoColaboradorManagerImpl extends GenericManagerImpl<HistoricoColaborador, HistoricoColaboradorDao> implements HistoricoColaboradorManager
{
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private IndiceHistoricoManager indiceHistoricoManager;
	private FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;
	private AcPessoalClientColaborador acPessoalClientColaborador;
	private AcPessoalClientTabelaReajusteInterface acPessoalClientTabelaReajuste;
	private PlatformTransactionManager transactionManager;
	private ReajusteColaboradorManager reajusteColaboradorManager;

	private EstabelecimentoManager estabelecimentoManager;
	private FaixaSalarialManager faixaSalarialManager;
	private IndiceManager indiceManager;
	private EmpresaManager empresaManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private SolicitacaoManager solicitacaoManager;
	
	@TesteAutomatico(metodoMock="findPromocaoByColaborador")
	public Collection<HistoricoColaborador> getByColaboradorId(Long colaboradorId)
	{
		return getDao().findPromocaoByColaborador(colaboradorId);
	}

	@TesteAutomatico
	public Collection findPromocaoByColaborador(Long colaboradorId)
	{
		return getDao().findPromocaoByColaborador(colaboradorId);
	}

	public HistoricoColaborador getHistoricoAtual(Long colaboradorId)
	{
		return getDao().getHistoricoAtual(colaboradorId, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
	}
	
	public HistoricoColaborador getHistoricoAtualOuFuturo(Long colaboradorId)
	{
		return getDao().getHistoricoAtual(colaboradorId, TipoBuscaHistoricoColaborador.COM_HISTORICO_FUTURO);
	}

	private Collection<Colaborador> distinctColaboradores(Collection<HistoricoColaborador> historicoTmp)
	{
		Hashtable hashColaboradores = new Hashtable();
		Collection<Colaborador> colaboradoresFiltroVecDistinct = new ArrayList<Colaborador>();

		for (HistoricoColaborador hist : historicoTmp)
		{
			if (hist.getColaborador() != null && !hist.getColaborador().isDesligado())
				hashColaboradores.put(hist.getColaborador().getId(), hist.getColaborador());
		}

		Enumeration<Colaborador> enumColaborador = hashColaboradores.elements();
		while (enumColaborador.hasMoreElements())
		{
			colaboradoresFiltroVecDistinct.add(enumColaborador.nextElement());
		}

		return colaboradoresFiltroVecDistinct;
	}

	public Collection<Colaborador> findByCargosIds(int page, int pagingSize, Long[] cargoIds, Colaborador colaborador, Long empresaId)
	{
		CollectionUtil<Long> cl = new CollectionUtil<Long>();

		Collection<HistoricoColaborador> historicoTmp = (Collection<HistoricoColaborador>) getDao().findByCargosIds(page, pagingSize, cl.convertArrayToCollection(cargoIds), empresaId, colaborador);
		Collection<Colaborador> colaboradoresFiltroVecDistinct = distinctColaboradores(historicoTmp);

		return colaboradoresFiltroVecDistinct;
	}

	public Collection<Colaborador> findByGrupoOcupacionalIds(int page, int pagingSize, Long[] gruposIds, Long empresaId)
	{
		CollectionUtil<Long> cl = new CollectionUtil<Long>();

		Collection<HistoricoColaborador> historicoTmp = (Collection<HistoricoColaborador>) getDao().findByGrupoOcupacionalIds(page, pagingSize,
				cl.convertArrayToCollection(gruposIds), empresaId);
		Collection<Colaborador> colaboradoresFiltroVecDistinct = distinctColaboradores(historicoTmp);

		return colaboradoresFiltroVecDistinct;
	}


	private void addPromocao(Collection<RelatorioPromocoes> promocoes, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, String tipoPromocao) 
	{
		boolean noExists = true;
		for (RelatorioPromocoes promocao : promocoes) 
		{
			if(estabelecimento.equals(promocao.getEstabelecimento()) && areaOrganizacional.equals(promocao.getArea()))
			{
				promocao.incrementa(tipoPromocao);
				noExists = false;
				break;
			}
		}

		if(noExists)
			promocoes.add(new RelatorioPromocoes(estabelecimento, areaOrganizacional, tipoPromocao));
	}
	
	private void addPromocaoMesAno(Collection<RelatorioPromocoes> promocoes, Date data, String tipoPromocao) 
	{
		boolean noExists = true;
		for (RelatorioPromocoes promocao : promocoes) 
		{
			if(data.equals(promocao.getMesAno()))
			{
				promocao.incrementa(tipoPromocao);
				noExists = false;
				break;
			}
		}
		
		if(noExists)
			promocoes.add(new RelatorioPromocoes(data, tipoPromocao));
	}

	public List<SituacaoColaborador> getColaboradoresSemReajuste(Long[] areasIds, Long[] estabelecimentosIds, Date data, Long empresaId, int mesesSemReajuste)
	{
		//filtro de area e estabelecimento não ta funcionando, tem que ajustar a consulta. Negócio de historico atual
		Collection<SituacaoColaborador> situacoes = getDao().getUltimasPromocoes(areasIds, estabelecimentosIds, data, empresaId);
		List<SituacaoColaborador> semReajustes = new ArrayList<SituacaoColaborador>();		
		
		Collection<AreaOrganizacional> areaOrganizacionals = ajustaFamilia(empresaId);
		Date dataBase = DateUtil.retornaDataAnteriorQtdMeses(data, mesesSemReajuste, true);
		
		Iterator<SituacaoColaborador> iterator = situacoes.iterator();
		SituacaoColaborador proximaSituacao = (SituacaoColaborador) iterator.next();
		SituacaoColaborador situacaoAnterior = null;
		Date dataUltimoReajusteColaborador = null;
		
		for (SituacaoColaborador situacao : situacoes) 
		{	
			proximaSituacao = iterator.hasNext() ? (SituacaoColaborador) iterator.next() : null;
			
			// primeira situacao do colaborador
			if (situacaoAnterior == null || !situacao.getColaborador().equals(situacaoAnterior.getColaborador()))
				dataUltimoReajusteColaborador = situacao.getData();
			
			// colaborador mudou de salario
			if (situacaoAnterior != null && situacao.getColaborador().equals(situacaoAnterior.getColaborador()) && !situacao.getSalario().equals(situacaoAnterior.getSalario()))
				dataUltimoReajusteColaborador = situacao.getData();
			
			// ultima situacao do colaborador
			if (proximaSituacao == null || !situacao.getColaborador().equals(proximaSituacao.getColaborador()))
			{
				// se o ultimo reajuste do colaborador foi antes do intervalo de datas especificado
				if (dataUltimoReajusteColaborador.before(dataBase))
				{
					situacao.setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, situacao.getAreaOrganizacional().getId()));
					// seta a data da ultima alteracao de salario
					situacao.setDataExtenso(DateUtil.formataDiaMesAno(dataUltimoReajusteColaborador) + " " + DateUtil.getIntervalDateString(dataUltimoReajusteColaborador, data));
					semReajustes.add(situacao);
				}
			}
			
			situacaoAnterior = situacao;
		}
		
		Collections.sort(semReajustes);
		return semReajustes;
	}
	
	public List<RelatorioPromocoes> getPromocoes(Long[] areasIds, Long[] estabelecimentosIds, Date dataIni, Date dataFim, Long... empresasIds)
	{
		return getDao().getRelatorioPromocoes(areasIds, estabelecimentosIds, dataIni, dataFim, empresasIds);
	}
	
	private List<RelatorioPromocoes> countPromocoesMesAno(Date dataIni, Date dataFim, Long empresaId, Long[] areasIds)
	{
		List<RelatorioPromocoes> promocoes = new ArrayList<RelatorioPromocoes>();
		Collection<SituacaoColaborador> situacaoColaboradors = getDao().getPromocoes(areasIds, null, null, dataFim, empresaId);

		if (situacaoColaboradors == null || situacaoColaboradors.isEmpty())
			return null;
		
		Iterator<SituacaoColaborador> iterator = situacaoColaboradors.iterator();
		SituacaoColaborador proximaSituacao = (SituacaoColaborador) iterator.next();
		
		for (SituacaoColaborador situacao : situacaoColaboradors) 
		{
			if(iterator.hasNext())
			{
				proximaSituacao = (SituacaoColaborador) iterator.next();
				//a ordem dos ifs são importantes
				if(!proximaSituacao.getColaborador().equals(situacao.getColaborador()))
					continue;
				
				if(!situacao.getCargo().equals(proximaSituacao.getCargo()))
				{
					if(proximaSituacao.getData().getTime() >= dataIni.getTime() && proximaSituacao.getData().getTime() <= dataFim.getTime())
						addPromocaoMesAno(promocoes, DateUtil.getInicioMesData(proximaSituacao.getData()), MotivoHistoricoColaborador.PROMOCAO);
					continue;
				}
					
				if(!situacao.getFaixaSalarial().equals(proximaSituacao.getFaixaSalarial()) || (situacao.getSalario() != null && proximaSituacao.getSalario()!= null && !situacao.getSalario().equals(proximaSituacao.getSalario())))
					if(proximaSituacao.getData().getTime() >= dataIni.getTime() && proximaSituacao.getData().getTime() <= dataFim.getTime())
						addPromocaoMesAno(promocoes, DateUtil.getInicioMesData(proximaSituacao.getData()), MotivoHistoricoColaborador.PROMOCAO_HORIZONTAL);
			}
		}
		
		CollectionUtil<RelatorioPromocoes> util = new CollectionUtil<RelatorioPromocoes>();
		promocoes = (List<RelatorioPromocoes>) util.sortCollectionDate(promocoes, "mesAno", "asc");
		
		return promocoes;
	}
	
	
	public Map<Character, Collection<Object[]>>  montaPromocoesHorizontalEVertical(Date dataIni, Date dataFim, Long empresaId, Long[] areasIds)
	{
		Date dataInicioTemp = DateUtil.getInicioMesData(dataIni);
		Date dataFimTemp = DateUtil.getUltimoDiaMes(dataFim);

		Map<Character, Collection<Object[]>> map = new HashMap<Character, Collection<Object[]>>(); 
		Collection<Object[]>  graficoPromocaoHorizontal = new ArrayList<Object[]>();
		Collection<Object[]>  graficoPromocaoVertical = new ArrayList<Object[]>();
		List<RelatorioPromocoes> promocoes = countPromocoesMesAno(dataInicioTemp, dataFimTemp, empresaId, areasIds);

		if(promocoes != null)
		{
			while (dataInicioTemp.before(dataFimTemp))
			{
				Object[] graficoHorizontal = new Object[]{dataInicioTemp.getTime(), 0};
				Object[] graficoVertical = new Object[]{dataInicioTemp.getTime(), 0};
				
				for (RelatorioPromocoes promocao : promocoes)
				{
					if(promocao.getMesAno().equals(dataInicioTemp))
					{
						graficoHorizontal = new Object[]{promocao.getMesAno().getTime(), promocao.getQtdHorizontal()};
						graficoVertical = new Object[]{promocao.getMesAno().getTime(), promocao.getQtdVertical()};
					}
				}
				
				graficoPromocaoHorizontal.add(graficoHorizontal);
				graficoPromocaoVertical.add(graficoVertical);
				
				dataInicioTemp = DateUtil.incrementaMes(dataInicioTemp, 1);
			}
		}
		
		map.put('H', graficoPromocaoHorizontal);
		map.put('V', graficoPromocaoVertical);
		
		return map;
	}
	
	public Map<Character, Collection<DataGrafico>> montaPromocoesHorizontalEVerticalPorArea(Date dataIni, Date dataFim, Long empresaId, boolean somenteAreasFilhas, Long... areasIds)
	{
		Date dataInicioTemp = DateUtil.getInicioMesData(dataIni);
		Date dataFimTemp = DateUtil.getUltimoDiaMes(dataFim);

		Collection<AreaOrganizacional> areasDescendentes = new ArrayList<AreaOrganizacional>();
		Collection<DataGrafico>  graficoPromocaoHorizontal = new ArrayList<DataGrafico>();
		Collection<DataGrafico>  graficoPromocaoVertical = new ArrayList<DataGrafico>();
		List<RelatorioPromocoes> promocoes = new ArrayList<RelatorioPromocoes>();
		AreaOrganizacional areaOrganizacional = null;
		
		Collection<AreaOrganizacional> areasOrganizacionais = areaOrganizacionalManager.findAllList(0, 0, null, empresaId, null);
		
		if(somenteAreasFilhas)
		{
			Collection<AreaOrganizacional> areasOrganizacionaisFilhas = areaOrganizacionalManager.getFilhos(areasOrganizacionais, areasIds[0]);

			if(areasOrganizacionaisFilhas.size() != 0)
			{	
				areaOrganizacional = areaOrganizacionalManager.findByIdProjection(areasIds[0]);

				for (AreaOrganizacional areaOrganizacionalFilha : areasOrganizacionaisFilhas) 
				{
					areasDescendentes = areaOrganizacionalManager.getDescendentes(areasOrganizacionais, areaOrganizacionalFilha.getId(), new ArrayList<AreaOrganizacional>());
					areasDescendentes.add(areaOrganizacional);
					areasDescendentes.add(areaOrganizacionalFilha);

					promocoes = getPromocoes(new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areasDescendentes), null, dataInicioTemp, dataFimTemp, empresaId);
					calculaPromocaoVerticalHorizontalDaArea(graficoPromocaoHorizontal, graficoPromocaoVertical,	areaOrganizacionalFilha, promocoes, false);
				}
			}
		}else
		{
			if(!LongUtil.arrayIsNotEmpty(areasIds))
				areasIds = areaOrganizacionalManager.findAreasMaesIdsByEmpresaId(empresaId);

			for (Long areaId : areasIds) 
			{
				areaOrganizacional = areaOrganizacionalManager.findByIdProjection(areaId);
				areasDescendentes = areaOrganizacionalManager.getDescendentes(areasOrganizacionais, areaId, new ArrayList<AreaOrganizacional>());
				areasDescendentes.add(areaOrganizacional);
				
				promocoes = getPromocoes(new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areasDescendentes), null, dataInicioTemp, dataFimTemp, empresaId);
				
				calculaPromocaoVerticalHorizontalDaArea(graficoPromocaoHorizontal, graficoPromocaoVertical,	areaOrganizacional, promocoes, true);
			}
		}

		Map<Character, Collection<DataGrafico>> mapRetorno = new HashMap<Character, Collection<DataGrafico>>();
		mapRetorno.put('H', graficoPromocaoHorizontal);
		mapRetorno.put('V', graficoPromocaoVertical);

		return mapRetorno;
	}

	private void calculaPromocaoVerticalHorizontalDaArea(Collection<DataGrafico> graficoPromocaoHorizontal, Collection<DataGrafico> graficoPromocaoVertical, AreaOrganizacional areaOrganizacional,	List<RelatorioPromocoes> promocoes, boolean considerarValoresVazios) 
	{
		int qtdPromocaoHorizontal = 0;
		int qtdPromocaoVertical = 0;

		for (RelatorioPromocoes promocao : promocoes){
			qtdPromocaoHorizontal += promocao.getQtdHorizontal();
			qtdPromocaoVertical += promocao.getQtdVertical();
		}
		
		if(qtdPromocaoHorizontal > 0 || considerarValoresVazios)
			graficoPromocaoHorizontal.add(new DataGrafico(areaOrganizacional.getId(), areaOrganizacional.getDescricao(), qtdPromocaoHorizontal, areaOrganizacional.getDescricao()));
		if(qtdPromocaoVertical > 0 || considerarValoresVazios)
			graficoPromocaoVertical.add(new DataGrafico(areaOrganizacional.getId(), areaOrganizacional.getDescricao(), qtdPromocaoVertical, areaOrganizacional.getDescricao()));
	}

	private Collection<AreaOrganizacional> ajustaFamilia(Long... empresasIds) 
	{
		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, empresasIds);
		try {
			areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return areaOrganizacionals;
	}

	@TesteAutomatico
	public HistoricoColaborador getHistoricoAnterior(HistoricoColaborador historico)
	{
		return getDao().getHistoricoAnterior(historico);
	}

	public Collection<HistoricoColaborador> inserirPeriodos(Collection<HistoricoColaborador> historicos)
	{
		HistoricoColaborador historico;
		HistoricoColaborador proxHistorico = new HistoricoColaborador();
		List<HistoricoColaborador> listHistoricos = new ArrayList<HistoricoColaborador>();
		listHistoricos = new CollectionUtil<HistoricoColaborador>().convertCollectionToList(historicos);
		for (int i = 0; i < listHistoricos.size(); i++)
		{
			historico = (HistoricoColaborador) listHistoricos.get(i);

			if (i + 1 < listHistoricos.size())
				if (listHistoricos.get(i + 1) != null)
					proxHistorico = (HistoricoColaborador) listHistoricos.get(i + 1);

			if (proxHistorico != null && proxHistorico.getData() != null)
				historico.setDataProximoHistorico(DateUtil.retornaDataDiaAnterior(proxHistorico.getData()));
			else if (historico.getColaborador() != null && historico.getColaborador().getDataDesligamento() != null)
				historico.setDataProximoHistorico(historico.getColaborador().getDataDesligamento());

			proxHistorico = null;
		}
		return historicos;
	}

	@TesteAutomatico
	public Collection<HistoricoColaborador> findByColaboradorData(Long idColaborador, Date data)
	{
		return getDao().findByColaboradorData(idColaborador, data);
	}

	public Collection<HistoricoColaborador> findDistinctAmbienteFuncao(Collection<HistoricoColaborador> historicoColaboradors)
	{
		Collection<HistoricoColaborador> historicoColaboradorsRetorno = new LinkedList<HistoricoColaborador>();

		HistoricoColaborador anterior = null;
		HistoricoColaborador ultimoAdicionado = null;

		for (HistoricoColaborador historicoColaborador : historicoColaboradors)
		{
			if ((anterior == null) || (!historicoColaborador.getAmbiente().equals(anterior.getAmbiente()))
					|| (!historicoColaborador.getFuncao().equals(anterior.getFuncao())))
			{
				if (ultimoAdicionado != null)
					ultimoAdicionado.setDataProximoHistorico(DateUtil.retornaDataDiaAnterior(historicoColaborador.getData()));
				historicoColaboradorsRetorno.add(historicoColaborador);
				ultimoAdicionado = historicoColaborador;
			}
			anterior = historicoColaborador;
		}

		return historicoColaboradorsRetorno;
	}

	public Collection<HistoricoColaborador> findDistinctFuncao(Collection<HistoricoColaborador> historicos)
	{
		Collection<HistoricoColaborador> historicoColaboradorsRetorno = new LinkedList<HistoricoColaborador>();

		HistoricoColaborador anterior = null;
		HistoricoColaborador ultimoAdicionado = null;

		for (HistoricoColaborador historicoColaborador : historicos)
		{
			if ((anterior == null) || (!historicoColaborador.getFuncao().equals(anterior.getFuncao())))
			{
				if (ultimoAdicionado != null)
					ultimoAdicionado.setDataProximoHistorico(historicoColaborador.getData());
				historicoColaboradorsRetorno.add(historicoColaborador);
				ultimoAdicionado = historicoColaborador;
			}
			anterior = historicoColaborador;
		}

		return historicoColaboradorsRetorno;
	}

	private HistoricoColaborador getHistoricoProximo(HistoricoColaborador hist)
	{
		return getDao().getHistoricoProximo(hist);
	}

	public boolean existeHistoricoData(HistoricoColaborador historicoColaborador)
	{
		Collection<HistoricoColaborador> historicos = getDao().findByData(historicoColaborador.getColaborador().getId(), historicoColaborador.getData());

		return !historicos.isEmpty();
	}

	@TesteAutomatico
	public Collection<HistoricoColaborador> getHistoricosAtuaisByEstabelecimentoAreaGrupo(Long[] estabelecimentoIds, char filtrarPor,
			Long[] areaOrganizacionalIds, Long[] grupoOcupacionalIds, Long empresaId, Date dataTabela)
	{
		return getDao().getHistoricosAtuaisByEstabelecimentoAreaGrupo(estabelecimentoIds, filtrarPor, areaOrganizacionalIds, grupoOcupacionalIds, empresaId, dataTabela);
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public HistoricoColaborador ajustaTipoSalario(HistoricoColaborador historico, int salarioPropostoPor, Indice indice, Double quantidadeIndice, Double salarioColaborador)
	{
		switch (salarioPropostoPor)
		{
			case TipoAplicacaoIndice.CARGO:
				historico.setTipoSalario(TipoAplicacaoIndice.CARGO);
				historico.setIndice(null);
				historico.setQuantidadeIndice(0.0);
				historico.setSalario(null);
				break;
			case TipoAplicacaoIndice.VALOR:
				historico.setTipoSalario(TipoAplicacaoIndice.VALOR);
				historico.setIndice(null);
				historico.setQuantidadeIndice(0.0);
				historico.setSalario(salarioColaborador);
				break;
			case TipoAplicacaoIndice.INDICE:
				historico.setTipoSalario(TipoAplicacaoIndice.INDICE);
				historico.setIndice(indice);
				historico.setQuantidadeIndice(quantidadeIndice);
				historico.setSalario(null);
				break;
		}

		return historico;
	}

	public String montaTipoSalario(Double quantidadeIndice, int tipoSalario, String indiceNome)
	{
		String tipo = "";

		tipo = "Por " + TipoAplicacaoIndice.getDescricao(tipoSalario);

		if (tipoSalario == TipoAplicacaoIndice.getIndice())
			tipo += " (" + quantidadeIndice + " x " + indiceNome + ")";

		return tipo;
	}

	public Collection<HistoricoColaborador> progressaoColaborador(Long colaboradorId, Long empresaId) throws Exception
	{
		Collection<HistoricoColaborador> historicosColaboradoresProgressao = new ArrayList<HistoricoColaborador>();
		List<HistoricoColaborador> historicoColaboradors = (List<HistoricoColaborador>) findPromocaoByColaborador(colaboradorId);

		int proximo = 1;
		for (HistoricoColaborador historicoColaboradorTmp : historicoColaboradors)
		{
			if (historicoColaboradorTmp.getMotivo() == null || historicoColaboradorTmp.getMotivo().equals(""))
				historicoColaboradorTmp.setMotivo("Mudança de Situação");

			switch (historicoColaboradorTmp.getTipoSalario())
			{
				case TipoAplicacaoIndice.VALOR:
				{
					historicosColaboradoresProgressao.add(historicoColaboradorTmp);
					break;
				}
				case TipoAplicacaoIndice.INDICE:
				{
					Date dataProximo = getDataProximoHistorico(historicoColaboradors, proximo);

					Collection<IndiceHistorico> indiceHistoricos = indiceHistoricoManager.findByPeriodo(historicoColaboradorTmp.getIndice().getId(),
							historicoColaboradorTmp.getData(), dataProximo, historicoColaboradorTmp.getColaborador().getDataDesligamento());

					historicosColaboradoresProgressao.add(historicoColaboradorTmp);
					for (IndiceHistorico indiceHistorico : indiceHistoricos)
					{
						HistoricoColaborador historicoColaboradorClone = (HistoricoColaborador) historicoColaboradorTmp.clone();
						historicoColaboradorClone.setData(indiceHistorico.getData());
						historicoColaboradorClone.getIndice().setIndiceHistoricoAtual(indiceHistorico);
						historicoColaboradorClone.setMotivo("Reajuste do Índice");

						historicosColaboradoresProgressao.add(historicoColaboradorClone);
					}

					break;
				}
				case TipoAplicacaoIndice.CARGO:
				{
					Date dataProximo = getDataProximoHistorico(historicoColaboradors, proximo);

					Collection<FaixaSalarialHistorico> faixaHistoricos = faixaSalarialHistoricoManager.findByPeriodo(historicoColaboradorTmp, dataProximo);

					historicosColaboradoresProgressao.add(historicoColaboradorTmp);
					for (FaixaSalarialHistorico faixaHistorico : faixaHistoricos)
					{
						HistoricoColaborador historicoColaboradorClone = (HistoricoColaborador) historicoColaboradorTmp.clone();
						historicoColaboradorClone.setData(faixaHistorico.getData());
						historicoColaboradorClone.getFaixaSalarial().setFaixaSalarialHistoricoAtual(faixaHistorico);
						historicoColaboradorClone.setStatus(faixaHistorico.getStatus());

						if (faixaHistorico.getObsReajuste() == null || faixaHistorico.getObsReajuste().equals(""))
							historicoColaboradorClone.setMotivo("Reajuste da Faixa");
						else
							historicoColaboradorClone.setMotivo(faixaHistorico.getObsReajuste());

						historicosColaboradoresProgressao.add(historicoColaboradorClone);
					}

					break;
				}
			}

			proximo++;
		}

		montaAreaOrganizacional(historicosColaboradoresProgressao, empresaId);

		return historicosColaboradoresProgressao;
	}

	public void montaAreaOrganizacional(Collection<HistoricoColaborador> historicosColaboradores, Long... empresaIds) throws Exception
	{
		if (empresaIds != null && empresaIds.length > 0)
		{
			Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, empresaIds);
			areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);

			for (HistoricoColaborador historico : historicosColaboradores)
				historico.setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, historico.getAreaOrganizacional().getId()));
		}
	}

	private Date getDataProximoHistorico(List<HistoricoColaborador> historicoColaboradors, int proximo)
	{
		Date dataProximo = null;
		if (proximo == historicoColaboradors.size())
			dataProximo = new Date();
		else
			dataProximo = historicoColaboradors.get(proximo).getData();

		return dataProximo;
	}

	public void setFaixaSalarialHistoricoManager(FaixaSalarialHistoricoManager faixaSalarialHistoricoManager)
	{
		this.faixaSalarialHistoricoManager = faixaSalarialHistoricoManager;
	}

	public void setIndiceHistoricoManager(IndiceHistoricoManager indiceHistoricoManager)
	{
		this.indiceHistoricoManager = indiceHistoricoManager;
	}

	public Collection<HistoricoColaborador> findByColaboradorProjection(Long colaboradorId, Integer statusRetornoAC)
	{
		return getDao().findByColaboradorProjection(colaboradorId, statusRetornoAC);
	}

	public Collection<HistoricoColaborador> findByColaborador(Long colaboradorId, Long empresaId) throws Exception
	{
		Collection<HistoricoColaborador> historicos = getDao().findPromocaoByColaborador(colaboradorId);

		montaAreaOrganizacional(historicos, empresaId);

		return historicos;
	}

	@TesteAutomatico
	public HistoricoColaborador findByIdHQL(Long historicoColaboradorId)
	{
		return getDao().findByIdHQL(historicoColaboradorId);
	}

	@TesteAutomatico
	public boolean setStatus(Long historicoColaboradorId, Boolean aprovado)
	{
		return getDao().setStatus(historicoColaboradorId, aprovado);
	}
	
	public void updateStatusAcByEmpresaAndStatusAtual(int novoStatusAC, int statusACAtual, Long... colaboradoresIds)
	{
		getDao().updateStatusAcByEmpresaAndStatusAtual(novoStatusAC, statusACAtual, colaboradoresIds);
	}

	public boolean verificaHistoricoNaFolhaAC(Long historicoColaboradorId, String colaboradorCodigoAC, Empresa empresa)
	{
		return acPessoalClientColaborador.verificaHistoricoNaFolhaAC(historicoColaboradorId, colaboradorCodigoAC, empresa);
	}

	public void updateHistorico(HistoricoColaborador historicoColaborador, Empresa empresa) throws Exception
	{
		HistoricoColaborador historicoColaboradorTmp = findByIdProjection(historicoColaborador.getId());
		historicoColaboradorTmp.setMotivo(historicoColaborador.getMotivo());
		Double salarioAntigo = historicoColaboradorTmp.getSalario();

		historicoColaboradorTmp.setAmbiente(historicoColaborador.getAmbiente());
		historicoColaboradorTmp.setFuncao(historicoColaborador.getFuncao());

		boolean atualizaDados = false;
		boolean alterouDadosIntegradoAC = alterouDadosIntegradoAC(historicoColaborador, historicoColaboradorTmp);

		if(alterouDadosIntegradoAC)
		{
			if(empresa.isAcIntegra() && !historicoColaboradorTmp.getColaborador().isNaoIntegraAc())
			{
				if(! verificaHistoricoNaFolhaAC(historicoColaboradorTmp.getId(), historicoColaboradorTmp.getColaborador().getCodigoAC(), empresa))
				{
					atualizaDados = true;
					historicoColaboradorTmp.setStatus(StatusRetornoAC.AGUARDANDO);

					historicoColaboradorTmp.setEstabelecimento(estabelecimentoManager.findEstabelecimentoCodigoAc(historicoColaborador.getEstabelecimento().getId()));
					historicoColaboradorTmp.setAreaOrganizacional(areaOrganizacionalManager.findAreaOrganizacionalCodigoAc(historicoColaborador.getAreaOrganizacional().getId()));
					historicoColaboradorTmp.setFaixaSalarial(faixaSalarialManager.findCodigoACById(historicoColaborador.getFaixaSalarial().getId()));
					historicoColaboradorTmp.setGfip(historicoColaborador.getGfip()); // exp agente nocivo

					if(historicoColaborador.getTipoSalario() == TipoAplicacaoIndice.INDICE)
						historicoColaboradorTmp.setIndice(indiceManager.findByIdProjection(historicoColaborador.getIndice().getId()));
				}
			}
			else
			{
				atualizaDados = true;
				historicoColaboradorTmp.setEstabelecimento(historicoColaborador.getEstabelecimento());
				historicoColaboradorTmp.setAreaOrganizacional(historicoColaborador.getAreaOrganizacional());
				historicoColaboradorTmp.setFaixaSalarial(historicoColaborador.getFaixaSalarial());
				historicoColaboradorTmp.setGfip(historicoColaborador.getGfip()); // exp agente nocivo

				if(historicoColaborador.getTipoSalario() == TipoAplicacaoIndice.INDICE)
					historicoColaboradorTmp.setIndice(historicoColaborador.getIndice());
			}

			if(atualizaDados)
			{
				historicoColaboradorTmp.setTipoSalario(historicoColaborador.getTipoSalario());
				historicoColaboradorTmp.setSalario(historicoColaborador.getSalario());
				historicoColaboradorTmp.setQuantidadeIndice(historicoColaborador.getQuantidadeIndice());

			}
		}

		historicoColaboradorTmp = ajustaTipoSalario(historicoColaboradorTmp, historicoColaboradorTmp.getTipoSalario(), historicoColaboradorTmp.getIndice(), historicoColaboradorTmp.getQuantidadeIndice(), historicoColaboradorTmp.getSalario());

		if(historicoColaboradorTmp.getReajusteColaborador() != null && historicoColaboradorTmp.getReajusteColaborador().getId() != null)
			reajusteColaboradorManager.updateFromHistoricoColaborador(historicoColaboradorTmp);

		update(historicoColaboradorTmp);
		// garante que um erro no banco do RH levantará uma Exception antes de alterar o outro banco.
		getDao().getHibernateTemplateByGenericDao().flush(); 

		if(empresa.isAcIntegra() && !historicoColaboradorTmp.getColaborador().isNaoIntegraAc() && atualizaDados && alterouDadosIntegradoAC)
		{
			Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>(1);
			historicoColaboradorTmp.setObsACPessoal(historicoColaborador.getObsACPessoal());
			historicoColaboradors.add(historicoColaboradorTmp);
			
			acPessoalClientTabelaReajuste.saveHistoricoColaborador(historicoColaboradors, empresa, salarioAntigo, false);
		}
	}
	
	public boolean alterouDadosIntegradoAC(HistoricoColaborador historicoColaboradorTela, HistoricoColaborador historicoColaboradorBanco)
	{
		if(historicoColaboradorBanco.getStatus() == StatusRetornoAC.CANCELADO)
			return true;

		if (!historicoColaboradorBanco.getEstabelecimento().equals(historicoColaboradorTela.getEstabelecimento()) ||
			!historicoColaboradorBanco.getAreaOrganizacional().equals(historicoColaboradorTela.getAreaOrganizacional()) ||
			!historicoColaboradorBanco.getFaixaSalarial().equals(historicoColaboradorTela.getFaixaSalarial()) ||
			!(historicoColaboradorBanco.getTipoSalario() == historicoColaboradorTela.getTipoSalario()))
			return true;
		
		if ( (historicoColaboradorBanco.getGfip() == null && historicoColaboradorTela.getGfip() != null) ||
			(!historicoColaboradorBanco.getGfip().equals(historicoColaboradorTela.getGfip())))
			return true;

		switch (historicoColaboradorTela.getTipoSalario())
		{
			case TipoAplicacaoIndice.INDICE:
				if (!historicoColaboradorBanco.getIndice().equals(historicoColaboradorTela.getIndice()) ||
				    !historicoColaboradorBanco.getQuantidadeIndice().equals(historicoColaboradorTela.getQuantidadeIndice()))
					return true;
				break;
			case TipoAplicacaoIndice.VALOR:
				if (!historicoColaboradorBanco.getSalario().equals(historicoColaboradorTela.getSalario()))
					return true;
				break;
		}

		return false;
	}

	public HistoricoColaborador findByIdProjection(Long historicoColaboradorId)
	{
		HistoricoColaborador historico = getDao().findByIdProjection(historicoColaboradorId);

		//Ajuste quando a consulta monta um objeto com atributos nulos.
		if (historico.getReajusteColaborador() != null && historico.getReajusteColaborador().getId() == null)
			historico.setReajusteColaborador(null);

		return historico;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	// TODO Mensagem de erro usando HTML... achar solução para o problema, chorarrrr
	public void removeHistoricoAndReajuste(Long historicoColaboradorId, Long colaboradorId, Empresa empresa, boolean removerDoAC) throws Exception
	{
		HistoricoColaborador historicoColaboradorTmp = findByIdProjection(historicoColaboradorId);
		
		ColaboradorManager colaboradorManager = (ColaboradorManager) SpringUtil.getBean("colaboradorManager");
		Colaborador colaborador = colaboradorManager.findColaboradorByIdProjection(colaboradorId);
		
		if (historicoColaboradorTmp.getData().equals(colaborador.getDataAdmissao()))
			throw new Exception("Este histórico não pode ser removido, pois é o primeiro histórico do colaborador.");
		
		verificaUnicoHistorico(colaboradorId, empresa, historicoColaboradorTmp);
		
		if(empresa.isAcIntegra() && !historicoColaboradorTmp.getColaborador().isNaoIntegraAc() && historicoColaboradorTmp.getStatus() != StatusRetornoAC.CANCELADO)
		{
			if(historicoColaboradorTmp.getColaborador().getCodigoAC() == null)
				throw new Exception("Cadastro do Colaborador pendente no Fortes Pessoal.");

			if(!existeHistoricoAprovado(historicoColaboradorTmp.getId(), historicoColaboradorTmp.getColaborador().getId()))
				throw new Exception("<div>Histórico do colaborador "+historicoColaboradorTmp.getColaborador().getNome()+"</div><div>Não existe outro histórico aprovado pelo Fortes Pessoal.<br>Não é permitido excluir.</div>");

			if(verificaHistoricoNaFolhaAC(historicoColaboradorId, historicoColaboradorTmp.getColaborador().getCodigoAC(), empresa))
				throw new Exception("<div>Uma Folha de Pagamento foi processada no Fortes Pessoal com este Histórico.<br>Não é permitido excluir.</div>");
		}

		Long reajusteColaboradorId = getDao().findReajusteByHistoricoColaborador(historicoColaboradorId);
		
		remove(historicoColaboradorId);
		getDao().setaContratadoNoPrimeiroHistorico(colaboradorId);
		
		if(reajusteColaboradorId != null)
			reajusteColaboradorManager.remove(reajusteColaboradorId);
		
		getDao().getHibernateTemplateByGenericDao().flush();

		if(removerDoAC && empresa.isAcIntegra() && !historicoColaboradorTmp.getColaborador().isNaoIntegraAc() && historicoColaboradorTmp.getStatus() != StatusRetornoAC.CANCELADO)
		{
			TSituacao situacao = bindSituacao(historicoColaboradorTmp, empresa.getCodigoAC());
			acPessoalClientTabelaReajuste.deleteHistoricoColaboradorAC(empresa, situacao);
			colaboradorManager.findUltimoVinculo(colaborador.getCodigoAC(), empresa.getCodigoAC(), empresa.getGrupoAC());
		}
		
		if(historicoColaboradorTmp.getCandidatoSolicitacao() != null && historicoColaboradorTmp.getCandidatoSolicitacao().getId() != null)
			candidatoSolicitacaoManager.updateStatusAndRemoveDataContratacaoOrPromocao(historicoColaboradorTmp.getCandidatoSolicitacao().getId(), StatusCandidatoSolicitacao.APROMOVER);
	}

	private void verificaUnicoHistorico(Long colaboradorId, Empresa empresa, HistoricoColaborador historicoColaboradorTmp) throws Exception 
	{
		if(historicoColaboradorTmp.getStatus() == StatusRetornoAC.CONFIRMADO){
			Collection<HistoricoColaborador> historicoColaboradors = findByColaboradorProjection(colaboradorId, StatusRetornoAC.CONFIRMADO);
			if(historicoColaboradors.size() <= 1)
			{
				String msg = "<div>Histórico do colaborador "+historicoColaboradorTmp.getColaborador().getNome()+"</div>Não é permitido deletar o único histórico do colaborador" + ((empresa.isAcIntegra() && !historicoColaboradorTmp.getColaborador().isNaoIntegraAc())?" confirmado.":".");
				throw new Exception(msg);
			}
		}
	}

	public void removeHistoricoAndReajusteAC(HistoricoColaborador historicoColaborador) throws Exception
	{
		if(!existeHistoricoAprovado(historicoColaborador.getId(), historicoColaborador.getColaborador().getId()))
			throw new Exception("Não existe outro histórico aprovado pelo Fortes Pessoal. Não é permitido excluir.");

		if(getCount(new String[]{"colaborador.id"}, new Object[]{historicoColaborador.getColaborador().getId()}) <= 1)
			throw new Exception("Não é permitido deletar o último histórico.");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			Long reajusteColaboradorId = getDao().findReajusteByHistoricoColaborador(historicoColaborador.getId());
			if(historicoColaborador.getCandidatoSolicitacao() != null && historicoColaborador.getCandidatoSolicitacao().getId() != null)
				candidatoSolicitacaoManager.updateStatusAndRemoveDataContratacaoOrPromocao(historicoColaborador.getCandidatoSolicitacao().getId(), StatusCandidatoSolicitacao.APROMOVER);
			
			remove(historicoColaborador.getId());

			if(reajusteColaboradorId != null)
				reajusteColaboradorManager.remove(reajusteColaboradorId);
			
			transactionManager.commit(status);
		}
		catch (Exception e)
		{
			transactionManager.rollback(status);
			e.printStackTrace();
		}
	}

	// TODO Link montado dentro de um manager... pensar melhor sobre isso
	public HistoricoColaborador cancelarSituacao(TSituacao situacao, String mensagem) throws Exception
	{
		HistoricoColaborador historicoColaborador = null;
		boolean cancelaSituacaoRhSep = false;
		if(situacao.getId() != null && situacao.getId() != 0)
		{
			historicoColaborador = getDao().findByIdProjectionHistorico(situacao.getId().longValue());
			cancelaSituacaoRhSep = true;
		}
		else
			historicoColaborador = getDao().findByAC(situacao.getDataFormatada(), situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());
		
		if(historicoColaborador != null)
		{
			gerenciadorComunicacaoManager.enviaMensagemCancelamentoSituacao(situacao, mensagem, historicoColaborador);
	
			//Cancelamento de um insert (RH_SEP)
			if(cancelaSituacaoRhSep)
			{
				setStatus(historicoColaborador.getId(), false);
			}
			else		//Cancelamento de uma edição (SEP)
			{
				historicoColaborador = bindSituacao(situacao, historicoColaborador);
				historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
				update(historicoColaborador);
			}

			atualizaStatusCandidatoSolicitacao(historicoColaborador);
		}
		
		return historicoColaborador;
	}

	private void atualizaStatusCandidatoSolicitacao(HistoricoColaborador historicoColaborador) {
		try {
			if(historicoColaborador.getCandidatoSolicitacao() != null && historicoColaborador.getCandidatoSolicitacao().getId() != null){
				candidatoSolicitacaoManager.updateStatusAndRemoveDataContratacaoOrPromocao(historicoColaborador.getCandidatoSolicitacao().getId(), StatusCandidatoSolicitacao.APROMOVER);
				removeVinculoCandidatoSolicitacao(historicoColaborador.getCandidatoSolicitacao().getId());
				if(historicoColaborador.getColaborador().getEmpresa() != null ){
					Empresa empresa = empresaManager.findByIdProjection(historicoColaborador.getColaborador().getEmpresa().getId()); 
						if(empresa.isSolPessoalReabrirSolicitacao())
							solicitacaoManager.updateEncerraSolicitacao(false, null, candidatoSolicitacaoManager.findByCandidatoSolicitacao(historicoColaborador.getCandidatoSolicitacao()).getSolicitacao().getId());
				}
			}
		} catch (Exception e) {
			System.out.println("Ocorreu uma inconsistência ao atualizar status de candidato solicitação.");
			e.printStackTrace();
		}
	}
	
	public Collection<PendenciaAC> findPendenciasByHistoricoColaborador(Long empresaId)
	{
		Collection<PendenciaAC> pendenciaACs = new ArrayList<PendenciaAC>();

		Collection<HistoricoColaborador> historicoColaboradors = getDao().findPendenciasByHistoricoColaborador(empresaId, new Integer[]{StatusRetornoAC.AGUARDANDO, StatusRetornoAC.CANCELADO});

		for (HistoricoColaborador historicoColaborador : historicoColaboradors)
		{
			PendenciaAC pendenciaAC = new PendenciaAC(historicoColaborador);
			pendenciaAC.montarDetalhes();

			pendenciaACs.add(pendenciaAC);
		}

		return pendenciaACs;
	}

	public Collection<HistoricoColaborador>  findPendenciasByHistoricoColaborador(Long empresaId, Integer... statusAC)
	{
		return getDao().findPendenciasByHistoricoColaborador(empresaId, statusAC);
	}
	
	public Collection<TSituacao> findHistoricosByTabelaReajuste(Long tabelaReajusteColaboradorId, Empresa empresa)
	{
		Collection<HistoricoColaborador> historicos = getDao().findHistoricosByTabelaReajuste(tabelaReajusteColaboradorId);
		return converterHistoricoColaboradorEmTSituacao(historicos, empresa);
	}

	public Collection<HistoricoColaborador> findHistoricosByTabelaReajuste(Long tabelaReajusteColaboradorId){
	    return getDao().findHistoricosByTabelaReajuste(tabelaReajusteColaboradorId);
	}
	
	public Collection<TSituacao>  converterHistoricoColaboradorEmTSituacao(Collection<HistoricoColaborador> historicos, Empresa empresa) {
	    Collection<TSituacao> situacoes = new ArrayList<TSituacao>();

        for (HistoricoColaborador hc : historicos)
        {
            TSituacao situacao = bindSituacao(hc, empresa.getCodigoAC());
            situacoes.add(situacao);
        }

        return situacoes;
	}
	        
	@TesteAutomatico
	public String findColaboradorCodigoAC(Long historicoColaboradorId)
	{
		return getDao().findColaboradorCodigoAC(historicoColaboradorId);
	}

	public void setReajusteColaboradorManager(ReajusteColaboradorManager reajusteColaboradorManager)
	{
		this.reajusteColaboradorManager = reajusteColaboradorManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public void setIndiceManager(IndiceManager indiceManager)
	{
		this.indiceManager = indiceManager;
	}

	public void setAcPessoalClientTabelaReajuste(AcPessoalClientTabelaReajusteInterface acPessoalClientTabelaReajuste)
	{
		this.acPessoalClientTabelaReajuste = acPessoalClientTabelaReajuste;
	}

	public void setAcPessoalClientColaborador(AcPessoalClientColaborador acPessoalClientColaborador)
	{
		this.acPessoalClientColaborador = acPessoalClientColaborador;
	}

	public HistoricoColaborador ajustaAmbienteFuncao(HistoricoColaborador historicoColaborador)
	{
		if (historicoColaborador.getAmbiente() == null || historicoColaborador.getAmbiente().getId() == null || historicoColaborador.getAmbiente().getId() == -1)
			historicoColaborador.setAmbiente(null);

		if (historicoColaborador.getFuncao() == null || historicoColaborador.getFuncao().getId() == null || historicoColaborador.getFuncao().getId() == -1)
			historicoColaborador.setFuncao(null);

		return historicoColaborador;
	}

	public boolean existeHistoricoAprovado(Long historicoColaboradorId, Long colaboradorId)
	{
		Collection<HistoricoColaborador> historicoColaboradors = getDao().findHistoricoAprovado(historicoColaboradorId, colaboradorId);

		return historicoColaboradors.size() > 0;
	}
	
	@TesteAutomatico
	public HistoricoColaborador getHistoricoContratacaoAguardando(Long colaboradorId)
	{
		return getDao().getHistoricoContratacaoAguardando(colaboradorId);
	}

	public HistoricoColaborador updateSituacao(TSituacao situacao) throws Exception
	{
		HistoricoColaborador historicoColaborador = getDao().findByAC(situacao.getDataFormatada(), situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());
		
		if(historicoColaborador == null)
			throw new Exception("Situação não encontrada no Fortes RH.");

		historicoColaborador = bindSituacao(situacao, historicoColaborador);
		historicoColaborador.setStatusAnterior(historicoColaborador.getStatus());
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		
		getDao().update(historicoColaborador);
		return historicoColaborador;
	}
	
	@TesteAutomatico
	public void updateSituacaoByMovimentacao(String codigoEmpregado, String movimentacao, String codPessoalEstabOuArea, boolean atualizarTodasSituacoes, Long empresaId)
	{
		getDao().updateSituacaoByMovimentacao(codigoEmpregado, movimentacao, codPessoalEstabOuArea, atualizarTodasSituacoes, empresaId);
	}

	public HistoricoColaborador prepareSituacao(TSituacao situacao) throws Exception
	{
		HistoricoColaborador historicoColaborador = new HistoricoColaborador();

		HistoricoColaborador historicoColaboradorAnterior = getDao().findAtualByAC(situacao.getDataFormatada(), situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());
		if(historicoColaboradorAnterior != null)
		{
			if(historicoColaboradorAnterior.getGfip() != null)
				historicoColaborador.setGfip(historicoColaboradorAnterior.getGfip());
			if(historicoColaboradorAnterior.getFuncao() != null)
				historicoColaborador.setFuncao(historicoColaboradorAnterior.getFuncao());
			if(historicoColaboradorAnterior.getAmbiente() != null)
				historicoColaborador.setAmbiente(historicoColaboradorAnterior.getAmbiente());
		}

		historicoColaborador = bindSituacao(situacao, historicoColaborador);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.IMPORTADO);

		return historicoColaborador;
	}

	public HistoricoColaborador bindSituacao(TSituacao situacao, HistoricoColaborador historicoColaborador) throws Exception
	{
		historicoColaborador.setData(situacao.getDataFormatada());
		historicoColaborador.setEstabelecimento(estabelecimentoManager.findEstabelecimentoByCodigoAc(situacao.getEstabelecimentoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC()));
		if ( historicoColaborador.getEstabelecimento() == null )
			throw new Exception("Não foi possível realizar a operação. Estabelecimento não existe no RH.");
		historicoColaborador.setAreaOrganizacional(areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(situacao.getLotacaoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC()));
		if ( historicoColaborador.getAreaOrganizacional() == null )
			throw new Exception("Não foi possível realizar a operação. Área organizacional não existe no RH.");
		historicoColaborador.setFaixaSalarial(faixaSalarialManager.findFaixaSalarialByCodigoAc(situacao.getCargoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC()));
		if ( historicoColaborador.getFaixaSalarial() == null )
			throw new Exception("Não foi possível realizar a operação. Faixa salarial não existe no RH.");

		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.getValor(situacao.getTipoSalario()));
		historicoColaborador.setGfip(situacao.getExpAgenteNocivo());

		historicoColaborador.setMovimentoSalarialId(situacao.getMovimentoSalarialId());
		
		switch (historicoColaborador.getTipoSalario())
		{
			case TipoAplicacaoIndice.CARGO:
				historicoColaborador.setIndice(null);
				historicoColaborador.setQuantidadeIndice(0.0);
				historicoColaborador.setSalario(null);
				break;
			case TipoAplicacaoIndice.VALOR:
				historicoColaborador.setSalario(situacao.getValor());
				historicoColaborador.setIndice(null);
				historicoColaborador.setQuantidadeIndice(0.0);
				break;
			case TipoAplicacaoIndice.INDICE:
				historicoColaborador.setIndice(indiceManager.findIndiceByCodigoAc(situacao.getIndiceCodigoAC(), situacao.getGrupoAC()));
				historicoColaborador.setQuantidadeIndice(situacao.getIndiceQtd());
				historicoColaborador.setSalario(null);
				break;
		}

		if(historicoColaborador.getAmbiente() != null && historicoColaborador.getAmbiente().getId() == null)
			historicoColaborador.setAmbiente(null);
		if(historicoColaborador.getFuncao() != null && historicoColaborador.getFuncao().getId() == null)
			historicoColaborador.setFuncao(null);
		if(historicoColaborador.getIndice() != null && historicoColaborador.getIndice().getId() == null)
			historicoColaborador.setIndice(null);
		if(historicoColaborador.getReajusteColaborador() != null && historicoColaborador.getReajusteColaborador().getId() == null)
			historicoColaborador.setReajusteColaborador(null);

		return historicoColaborador;
	}

	public TSituacao bindSituacao(HistoricoColaborador historicoColaborador, String empresaCodigoAC)
	{
		TSituacao situacao = new TSituacao();
		situacao.setObs(historicoColaborador.getObsACPessoal());

		if(historicoColaborador.getColaborador() != null && historicoColaborador.getColaborador().getCodigoAC() != null)
			situacao.setEmpregadoCodigoAC(historicoColaborador.getColaborador().getCodigoAC());

		situacao.setValorAnterior(0.0);
		situacao.setId(historicoColaborador.getId().intValue());
		situacao.setTipoSalario(TipoAplicacaoIndice.getCodigoAC(historicoColaborador.getTipoSalario()));
		situacao.setEmpresaCodigoAC(empresaCodigoAC);

		situacao.setExpAgenteNocivo(historicoColaborador.getGfip());
		
		if (historicoColaborador.getData() != null)
			situacao.setData(DateUtil.formataDiaMesAno(historicoColaborador.getData()));
		if(historicoColaborador.getAreaOrganizacional() != null && historicoColaborador.getAreaOrganizacional().getCodigoAC() != null)
			situacao.setLotacaoCodigoAC(historicoColaborador.getAreaOrganizacional().getCodigoAC());
		if(historicoColaborador.getEstabelecimento() != null && historicoColaborador.getEstabelecimento().getCodigoAC() != null)
			situacao.setEstabelecimentoCodigoAC(historicoColaborador.getEstabelecimento().getCodigoAC());
		if(historicoColaborador.getFaixaSalarial() != null &&  historicoColaborador.getFaixaSalarial().getCodigoAC() != null)
			situacao.setCargoCodigoAC(historicoColaborador.getFaixaSalarial().getCodigoAC());
		if (historicoColaborador.getIndice() != null && historicoColaborador.getIndice().getCodigoAC() != null)
			situacao.setIndiceCodigoAC(historicoColaborador.getIndice().getCodigoAC());

		if (historicoColaborador.getQuantidadeIndice() == null)
			situacao.setIndiceQtd(0.0);
		else
			situacao.setIndiceQtd(historicoColaborador.getQuantidadeIndice());

		if (historicoColaborador.getSalario() == null)
			situacao.setValor(0.0);
		else
			situacao.setValor(historicoColaborador.getSalario());

		return situacao;
	}

	@TesteAutomatico
	public HistoricoColaborador findByAC(Date data, String empregadoCodigoAC, String empresaCodigoAC, String grupoAC)
	{
		return getDao().findByAC(data, empregadoCodigoAC, empresaCodigoAC, grupoAC);
	}

	public boolean verificaDataPrimeiroHistorico(Colaborador colaborador)
	{
		HistoricoColaborador historicoColaborador = getDao().getPrimeiroHistorico(colaborador.getId());
		return historicoColaborador.getData().before(colaborador.getDataAdmissao());
	}

	public boolean verificaPrimeiroHistoricoAdmissao(boolean editarHistorico, HistoricoColaborador historicoColaborador, Colaborador colaborador)
	{
		if(editarHistorico)
			return historicoColaborador.getData().before(colaborador.getDataAdmissao());
		else
			return verificaDataPrimeiroHistorico(colaborador);
	}

	@TesteAutomatico
	public void removeColaborador(Long colaboradorId)
	{
		getDao().removeColaborador(colaboradorId);
	}

	public boolean verifyDataHistoricoAdmissao(Long colaboradorId)
	{
		HistoricoColaborador historicoColaborador = getDao().findHistoricoAdmissao(colaboradorId);
		return historicoColaborador.getData()
				.compareTo(historicoColaborador.getColaborador().getDataAdmissao()) == 1;
	}

	@TesteAutomatico
	public Collection<HistoricoColaborador> findColaboradoresByTabelaReajusteData(Long tabelaReajusteColaboradorId, Date data)
	{
		return getDao().findColaboradoresByTabelaReajusteData(tabelaReajusteColaboradorId, data);
	}

	@TesteAutomatico
	public HistoricoColaborador getPrimeiroHistorico(Long colaboradorId)
	{
		return getDao().getPrimeiroHistorico(colaboradorId);
	}

	public void insertHistorico(HistoricoColaborador historicoColaborador, Empresa empresa) throws Exception
	{
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		
		if(empresa.isAcIntegra() && !historicoColaborador.getColaborador().isNaoIntegraAc())
		{
			historicoColaborador.setStatus(StatusRetornoAC.AGUARDANDO);
			
			historicoColaborador.setEstabelecimento(estabelecimentoManager.findEstabelecimentoCodigoAc(historicoColaborador.getEstabelecimento().getId()));
			historicoColaborador.setAreaOrganizacional(areaOrganizacionalManager.findAreaOrganizacionalCodigoAc(historicoColaborador.getAreaOrganizacional().getId()));
			historicoColaborador.setFaixaSalarial(faixaSalarialManager.findCodigoACById(historicoColaborador.getFaixaSalarial().getId()));

			if(historicoColaborador.getTipoSalario() == TipoAplicacaoIndice.INDICE)
				historicoColaborador.setIndice(indiceManager.findByIdProjection(historicoColaborador.getIndice().getId()));
		}

		historicoColaborador = ajustaTipoSalario(historicoColaborador, historicoColaborador.getTipoSalario(), historicoColaborador.getIndice(), historicoColaborador.getQuantidadeIndice(), historicoColaborador.getSalario());
		
		save(historicoColaborador);
		// garante que um erro no banco do RH levantará uma Exception antes de alterar o outro banco.
		getDao().getHibernateTemplateByGenericDao().flush(); 

		//envia para o AC
		if(empresa.isAcIntegra() && !historicoColaborador.getColaborador().isNaoIntegraAc())
		{
			historicoColaborador.getColaborador().setCodigoAC(findColaboradorCodigoAC(historicoColaborador.getId()));
			Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>(1);
			historicoColaboradors.add(historicoColaborador);

			acPessoalClientTabelaReajuste.saveHistoricoColaborador(historicoColaboradors, empresa, null, false);
		}
	}
	
	public void saveHistoricoColaboradorNoAc(Collection<HistoricoColaborador> historicosColaboradores, Empresa empresa) throws Exception
	{
		acPessoalClientTabelaReajuste.saveHistoricoColaborador(historicosColaboradores, empresa, null, false);
	}
	
	@SuppressWarnings("static-access")
	public Collection<HistoricoColaborador> relatorioColaboradorCargo(Date dataHistorico, String[] cargosCheck, String[] estabelecimentosCheck, Integer qtdMeses, char opcaoFiltro, String[] areaOrganizacionalCheck, Boolean exibeColabAdmitido, Integer qtdMesesDesatualizacao, String vinculo, boolean exibirSalarioVariavel, Long... empresasIds) throws Exception,ColecaoVaziaException
	{
		Collection<HistoricoColaborador> historicoColaboradors;
		Date dataConsulta = null;
		Date dataAtualizacao = null;
		Date dataAtual = new Date();
		DateUtil dateUtil = new DateUtil();
		
		if (exibeColabAdmitido)
			if(qtdMeses != null && qtdMeses > 0)
			{
				//consulta por quantidade de Meses 
				if (opcaoFiltro == '0')//data atual 
					dataConsulta = dateUtil.retornaDataAnteriorQtdMeses(dataAtual, qtdMeses, false);			
				else if (opcaoFiltro == '1')//data de referencia 
					dataConsulta = dateUtil.retornaDataAnteriorQtdMeses(dataHistorico, qtdMeses, false);
			}

		if (qtdMesesDesatualizacao != null)
			dataAtualizacao = dateUtil.retornaDataAnteriorQtdMeses(dataAtual, qtdMesesDesatualizacao, false);
		
		historicoColaboradors = getDao().findByCargoEstabelecimento(dataHistorico, LongUtil.arrayStringToArrayLong(cargosCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), dataConsulta, LongUtil.arrayStringToArrayLong(areaOrganizacionalCheck), dataAtualizacao, vinculo, empresasIds);
		
		if(exibirSalarioVariavel)
			getRemuneracaoVariavelByAcPessoal(dataHistorico, historicoColaboradors);
		
		if(historicoColaboradors.isEmpty())
			throw new ColecaoVaziaException("Não existem dados para o filtro informado.");
			
		return historicoColaboradors;
	}
	
	private void getRemuneracaoVariavelByAcPessoal(Date dataHistorico, Collection<HistoricoColaborador> historicoColaboradors) throws Exception 
	{
		ArrayList<String> colaboradoresIdsList = new ArrayList<String>();
		ArrayList<Long> empresasIdsList = new ArrayList<Long>();
		
		for (HistoricoColaborador historicoColaborador : historicoColaboradors) 
		{			
			if (historicoColaborador.getColaborador() != null && historicoColaborador.getColaborador().getEmpresa().isAcIntegra() && historicoColaborador.getColaborador().getCodigoAC() != null && !historicoColaborador.getColaborador().getCodigoAC().equals("") && !historicoColaborador.getColaborador().isNaoIntegraAc())
			{
				colaboradoresIdsList.remove(historicoColaborador.getColaborador().getCodigoAC().toString());
				colaboradoresIdsList.add(historicoColaborador.getColaborador().getCodigoAC().toString());
				
				Long empresaId = historicoColaborador.getColaborador().getEmpresa().getId();
				if (empresaId != null && !empresasIdsList.contains(empresaId))
					empresasIdsList.add(empresaId);
			}
		}
		
		String[] colaboradoresIds = new String[colaboradoresIdsList.size()];
		colaboradoresIds = colaboradoresIdsList.toArray(colaboradoresIds);
		
		
		if (colaboradoresIdsList.size() != 0)
		{
			List<TRemuneracaoVariavel> remuneracoesVariaveisList = new ArrayList<TRemuneracaoVariavel>();
			Empresa empresa;
			TRemuneracaoVariavel[] remuneracoesVariaveisTemp;

			for (Long empresaId : empresasIdsList)
			{
				empresa = empresaManager.findById(empresaId);
				remuneracoesVariaveisTemp = acPessoalClientColaborador.getRemuneracoesVariaveis(empresa, colaboradoresIds, DateUtil.formataAnoMes(dataHistorico), DateUtil.formataAnoMes(dataHistorico)); 
				CollectionUtil<TRemuneracaoVariavel> util = new CollectionUtil<TRemuneracaoVariavel>();
				remuneracoesVariaveisList.addAll(util.convertArrayToCollection(remuneracoesVariaveisTemp));
			}
						
			for (TRemuneracaoVariavel remuneracaoVariavel : remuneracoesVariaveisList)
			{
				for (HistoricoColaborador historicoColaborador : historicoColaboradors) 
				{
					if (historicoColaborador.getColaborador() != null && historicoColaborador.getColaborador().getCodigoAC() != null && !historicoColaborador.getColaborador().getCodigoAC().equals(""))
					{
						if (historicoColaborador.getColaborador().getCodigoAC().equals(remuneracaoVariavel.getCodigoEmpregado()))
						{
							historicoColaborador.setSalarioVariavel(remuneracaoVariavel.getValor());
							historicoColaborador.setMensalidade(remuneracaoVariavel.getMensalidade());
							break;
						}
					}
				}
			}
		}
	}
	
	public Collection<HistoricoColaborador> montaRelatorioSituacoes(Long empresaId, Date dataIni, Date dataFim, Long[] estabelecimentosIds, Long[] areasIds, String origemSituacao, char agruparPor, boolean imprimirDesligados) throws ColecaoVaziaException, Exception {

		return getDao().findByPeriodo(empresaId, dataIni, dataFim, estabelecimentosIds, areasIds, origemSituacao, agruparPor, imprimirDesligados);
	}

	@TesteAutomatico
	public Collection<HistoricoColaborador> findAllByColaborador(Long colaboradorId) {
		return getDao().findAllByColaborador(colaboradorId);
	}
	
	public Collection<HistoricoColaborador> getHistoricosComAmbienteEFuncao(Long colaboradorId, Long empresaId) {
		
		Collection<HistoricoColaborador> historicoColaboradors = findAllByColaborador(colaboradorId);
		
		prepareAmbientes(historicoColaboradors);
		prepareFuncoes(historicoColaboradors, empresaId);
		
		return historicoColaboradors;
	}
	
	// TODO consertar as referências cíclicas / refatorar os managers de ambiente e funcao 
	
	private void prepareAmbientes(Collection<HistoricoColaborador> historicoColaboradors) {
		
		AmbienteManager ambienteManager = (AmbienteManager) SpringUtil.getBean("ambienteManager");
		
		HashMap<Long, Collection<Ambiente>> mapEstabelecimentoAmbiente = new HashMap<Long, Collection<Ambiente>>();
		
		for (HistoricoColaborador historicoColaborador : historicoColaboradors) {
			
			Estabelecimento estabelecimento = historicoColaborador.getEstabelecimento();
			
			if (mapEstabelecimentoAmbiente.get(estabelecimento.getId()) == null)
				mapEstabelecimentoAmbiente.put(estabelecimento.getId(), ambienteManager.findByEstabelecimento(estabelecimento.getId()));
			
			historicoColaborador.setAmbientes(mapEstabelecimentoAmbiente.get(estabelecimento.getId()));
		}
	}
	private void prepareFuncoes(Collection<HistoricoColaborador> historicoColaboradors, Long empresaId) {
		
		FuncaoManager funcaoManager = (FuncaoManager) SpringUtil.getBean("funcaoManager");
		Collection<Funcao> funcoes = funcaoManager.findByEmpresa(empresaId);
		
		for (HistoricoColaborador historicoColaborador : historicoColaboradors) {
			historicoColaborador.setFuncoes(funcoes);
		}
	}
	
	public void updateAmbientesEFuncoes(Collection<HistoricoColaborador> historicoColaboradors) throws Exception
	{
		for (HistoricoColaborador historicoColaborador : historicoColaboradors)
		{
			boolean update = updateAmbienteEFuncao(historicoColaborador);
			
			if (!update)
				throw new PersistenceException();
		}
	}
	
	public boolean updateAmbienteEFuncao(HistoricoColaborador historicoColaborador) throws Exception
	{
		if (historicoColaborador == null || historicoColaborador.getId() == null)
			return false;
		if (historicoColaborador.getAmbiente() == null)
			historicoColaborador.setAmbiente(new Ambiente());
		if (historicoColaborador.getFuncao() == null)
			historicoColaborador.setFuncao(new Funcao());
		
		return getDao().updateAmbienteEFuncao(historicoColaborador);
	}

	public Double getValorTotalFolha(Long empresaId, Date data)
	{
		Collection<HistoricoColaborador> historicoColaboradors = getDao().findHistoricoAdmitidos(empresaId, data);
		double valorTotalFolha = 0.0;
		
		for (HistoricoColaborador historicoColaborador : historicoColaboradors)
		{
			if (historicoColaborador.getSalarioCalculado() != null)
				valorTotalFolha += historicoColaborador.getSalarioCalculado();
		}
		
 		return valorTotalFolha;
	}

	@TesteAutomatico
	public void deleteSituacaoByMovimentoSalarial(Long movimentoSalarialId, Long empresaId)
	{
		getDao().deleteSituacaoByMovimentoSalarial(movimentoSalarialId, empresaId);
	}

	public Collection<HistoricoColaborador> findImprimirListaFrequencia(Estabelecimento estabelecimento, Date votacaoIni, Date votacaoFim) {
		// valida
		Validate.notNull(estabelecimento, "Estabelecimento inválido.");
		Validate.notNull(votacaoIni, "Data da Votação Inicial inválido.");
		Validate.notNull(votacaoFim, "Data da Votação Final inválido.");
		// gera relatorio
		return getDao().findImprimirListaFrequencia(estabelecimento, votacaoIni, votacaoFim);
	}

	@TesteAutomatico
	public void setMotivo(Long[] historicoColaboradorIds, String tipo) {
		getDao().setMotivo(historicoColaboradorIds, tipo);
	}

	public Collection<HistoricoColaborador> findSemDissidioByDataPercentual(Date dataIni, Date dataFim, Double percentualDissidio, Long empresaId, String[] cargosIds, String[] areasIds, String[] estabelecimentosIds)
	{
		Collection<HistoricoColaborador> historicos = getDao().findSemDissidioByDataPercentual(dataIni, dataFim, percentualDissidio, empresaId);
		
		Long idColaborador = 0L;
		Double salarioAnterior = 0.0;
		for (HistoricoColaborador historico : historicos) 
		{
			historico.setSalario(historico.getSalarioCalculado());
			
			if(idColaborador.equals(historico.getColaborador().getId()))
				historico.setSalarioVariavel(salarioAnterior);
			
			idColaborador = historico.getColaborador().getId();
			salarioAnterior = historico.getSalario();
			
			if(historico.getSalario() != null && historico.getSalarioVariavel() != null && !historico.getSalarioVariavel().equals(0.0))
				historico.setDiferencaSalarialEmPorcentam(((historico.getSalario() - historico.getSalarioVariavel()) / historico.getSalarioVariavel()) * 100);
		}
		
		Collection<Long> cargosIdsLong = Arrays.asList(StringUtil.stringToLong(cargosIds));
		Collection<Long> areasIdsLong = Arrays.asList(StringUtil.stringToLong(areasIds));
		Collection<Long> estabelecimentosIdsLong = Arrays.asList(StringUtil.stringToLong(estabelecimentosIds));
		
		if (!cargosIdsLong.isEmpty() || !areasIdsLong.isEmpty() || !estabelecimentosIdsLong.isEmpty())
		{
			Collection<Long> colaboradoresIds = new ArrayList<Long>();
			Collection<HistoricoColaborador> historicosFiltrados = new ArrayList<HistoricoColaborador>();
			
			// Checa se o colaborador ja possuiu um dos cargos selecionados
			for (HistoricoColaborador historicoColaborador : historicos) 
			{
				if  ( 	(cargosIdsLong.isEmpty() || cargosIdsLong.contains(historicoColaborador.getFaixaSalarial().getCargo().getId()) && !colaboradoresIds.contains(historicoColaborador.getColaborador().getId())) &&
						(areasIdsLong.isEmpty() || areasIdsLong.contains(historicoColaborador.getAreaOrganizacional().getId()) && !colaboradoresIds.contains(historicoColaborador.getColaborador().getId())) &&
						(estabelecimentosIdsLong.isEmpty() || estabelecimentosIdsLong.contains(historicoColaborador.getEstabelecimento().getId()) && !colaboradoresIds.contains(historicoColaborador.getColaborador().getId()))
					)
					colaboradoresIds.add(historicoColaborador.getColaborador().getId());
			}
			
			// Inclui todo o historico de cada colaborador que ja possuiu um dos cargos selecionados
			for (HistoricoColaborador historicoColaborador : historicos)
			{
				if (colaboradoresIds.contains(historicoColaborador.getColaborador().getId()))
					historicosFiltrados.add(historicoColaborador);
			}
			
			historicos = historicosFiltrados;
		}
		
		return historicos;
	}

	@TesteAutomatico
	public void ajustaMotivoContratado(Long colaboradorId) 
	{
		getDao().ajustaMotivoContratado(colaboradorId);
	}

	@TesteAutomatico
	public void removeVinculoCandidatoSolicitacao(Long candidatoSolicitacaoId) {
		getDao().removeVinculoCandidatoSolicitacao(candidatoSolicitacaoId);		
	}
	
	public Collection<HistoricoColaborador> relatorioColaboradorGrupoOcupacional(Date dataHistorico, String[] cargosCheck, String[] estabelecimentosCheck, String[] areaOrganizacionalsCheck, Boolean areasAtivas, String[] gruposCheck, String vinculo, Long... empresaIds) throws Exception
	{
		Collection<HistoricoColaborador> historicos = getDao().findByAreaGrupoCargo(empresaIds, dataHistorico, LongUtil.arrayStringToArrayLong(cargosCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areaOrganizacionalsCheck), areasAtivas, LongUtil.arrayStringToArrayLong(gruposCheck), vinculo);
		if (historicos.isEmpty())
			throw new ColecaoVaziaException("Não existem dados para o filtro informado.");
		
		montaAreaOrganizacional(historicos, empresaIds);
		
		return historicos;
	}

	public Collection<HistoricoColaborador> filtraHistoricoColaboradorParaPPP(Collection<HistoricoColaborador> todosHistoricos) throws Exception 
	{
		if (todosHistoricos == null || todosHistoricos.isEmpty())
			return null;
		
		HistoricoColaborador ultimoHistoricoValido = (HistoricoColaborador) todosHistoricos.toArray()[0];
		
		Collection<HistoricoColaborador> historicosFiltrados = new ArrayList<HistoricoColaborador>();
		historicosFiltrados.add(ultimoHistoricoValido);
		
		for (HistoricoColaborador historicoColaborador : todosHistoricos) {
			if( !(historicoColaborador.getEstabelecimento().getId().equals(ultimoHistoricoValido.getEstabelecimento().getId())) 
					|| !(historicoColaborador.getFaixaSalarial().getCargo().getId().equals(ultimoHistoricoValido.getFaixaSalarial().getCargo().getId()))
					|| (!ModelUtil.hasNull("getAmbiente().getId()", historicoColaborador) && !(historicoColaborador.getAmbiente().getId().equals(ModelUtil.getValor(ultimoHistoricoValido, "getAmbiente().getId()", true)))
					|| (!ModelUtil.hasNull("getFuncao().getId()", historicoColaborador) && !(historicoColaborador.getFuncao().getId().equals(ModelUtil.getValor(ultimoHistoricoValido, "getFuncao().getId()", true)))))){
				
				historicosFiltrados.add(historicoColaborador);
				ultimoHistoricoValido = historicoColaborador;
			}
		}
		return historicosFiltrados;
	}
	
	public void deleteHistoricosAguardandoConfirmacaoByColaborador(Long... colaboradoresIds)
	{
		getDao().deleteHistoricosAguardandoConfirmacaoByColaborador(colaboradoresIds);
	}
	
	@TesteAutomatico
	public void deleteHistoricoColaborador(Long[] colaboradorIds) throws Exception 
	{
		getDao().deleteHistoricoColaborador(colaboradorIds);
	}
	
	@TesteAutomatico
	public boolean existeHistoricoPorIndice(Long empresaId) 
	{
		return getDao().existeHistoricoPorIndice(empresaId);
	}
	
	public void updateStatusAc(int statusRetornoAC, Long... id) 
	{
		getDao().updateStatusAc(statusRetornoAC, id);
	}
	
	@TesteAutomatico
	public void updateArea(Long areaIdMae, Long areaId) 
	{
		getDao().updateArea(areaIdMae, areaId);	
	}

	public Collection<HistoricoColaborador> findByEmpresaComHistorico(Long empresaId, Integer status) 
	{
		return getDao().findByEmpresaComHistorico(empresaId, status, false) ;
	}

	public boolean existeDependenciaComHistoricoIndice(Date dataHistoricoExcluir, Long indiceId) 
	{
		Collection<IndiceHistorico> indiceHistoricos = indiceHistoricoManager.findToList(new String[]{"data"}, new String[]{"data"}, new String[]{"indice.id"}, new Long[]{indiceId}, 1, 2, new String[]{"data"});
		
		if(indiceHistoricos.size() == 1)
			return getDao().existeDependenciaComHistoricoIndice(dataHistoricoExcluir, null, indiceId);
		else if(indiceHistoricos.size() > 1)
			return getDao().existeDependenciaComHistoricoIndice(dataHistoricoExcluir, ((IndiceHistorico) indiceHistoricos.toArray()[1]).getData(), indiceId);
		
		return false;
	}

	public void reenviaAguardandoConfirmacao(Empresa empresa) throws Exception  
	{
		Collection<HistoricoColaborador> historicosColaboradores = getDao().findByEmpresaComHistorico(empresa.getId(), StatusRetornoAC.AGUARDANDO, true);
		acPessoalClientTabelaReajuste.saveHistoricoColaborador(historicosColaboradores, empresa, null, false);
	}
	
    @TesteAutomatico
    public HistoricoColaborador findHistoricoColaboradorByData(Long colaboradorId, Date data) {
        return getDao().findHistoricoColaboradorByData(colaboradorId, data);
    }

    public HistoricoColaborador atualizarHistoricoContratacao(TSituacao situacao) throws Exception {
        HistoricoColaborador historicoColaborador = getDao().findHistoricoMotivoContratacao(situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());
        if (historicoColaborador == null)
            throw new Exception("Situação não encontrada no Fortes RH.");

        historicoColaborador = bindSituacao(situacao, historicoColaborador);
        historicoColaborador.setStatusAnterior(historicoColaborador.getStatus());
        historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);

        getDao().update(historicoColaborador);

        return historicoColaborador;
    }
	
	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager) {
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}


	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager) {
		this.solicitacaoManager = solicitacaoManager;
	}

    public boolean existeHistoricoConfirmadoByTabelaReajusteColaborador(Long tabelaReajusteColaboradorId) {
        return getDao().existeHistoricoConfirmadoByTabelaReajusteColaborador(tabelaReajusteColaboradorId);
    }

	public Boolean existeHistoricoContratualComPendenciaNoESocial(Empresa empresa, String colaboradorCodigoAC) throws Exception {
		return acPessoalClientTabelaReajuste.existeHistoricoContratualComPendenciaNoESocial(empresa, colaboradorCodigoAC);
	}

	public Boolean situacaoContratualEhInicioVinculo(Empresa empresa, String colaboradorCodigoAC, Date dataSituacao) throws Exception {
		return acPessoalClientTabelaReajuste.situacaoContratualEhInicioVinculo(empresa, colaboradorCodigoAC, dataSituacao);
	}

	public boolean isUltimoHistoricoOrPosteriorAoUltimo(Date data, String empregadoCodigoAC, String empresaCodigoAC, String grupoAC) {
		return getDao().isUltimoHistoricoOrPosteriorAoUltimo(data, empregadoCodigoAC, empresaCodigoAC, grupoAC);
	}
}