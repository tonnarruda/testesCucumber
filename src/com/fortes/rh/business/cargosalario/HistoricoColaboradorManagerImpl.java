package com.fortes.rh.business.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
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
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.cargosalario.relatorio.RelatorioPromocoes;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoBuscaHistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.ws.AcPessoalClientColaborador;
import com.fortes.rh.web.ws.AcPessoalClientTabelaReajusteInterface;

@SuppressWarnings({"unchecked","deprecation"})
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
	private UsuarioMensagemManager usuarioMensagemManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private MensagemManager mensagemManager;
	
	public Collection<HistoricoColaborador> getByColaboradorId(Long colaboradorId)
	{
		return getDao().findPromocaoByColaborador(colaboradorId);
	}

	public Collection findPromocaoByColaborador(Long colaboradorId)
	{
		return getDao().findPromocaoByColaborador(colaboradorId);
	}

	public HistoricoColaborador getHistoricoAtual(Long colaboradorId)
	{
		return getDao().getHistoricoAtual(colaboradorId, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
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

	public Collection<Colaborador> findByCargosIds(int page, int pagingSize, Long[] cargoIds, Long empresaId, Colaborador colaborador)
	{
		CollectionUtil<Long> cl = new CollectionUtil<Long>();

		Collection<HistoricoColaborador> historicoTmp = (Collection<HistoricoColaborador>) getDao().findByCargosIds(page, pagingSize,
				cl.convertArrayToCollection(cargoIds), empresaId, colaborador);
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

	public Collection<RelatorioPromocoes> getPromocoes(Long[] areasIds, Long[] estabelecimentosIds, Date dataIni, Date dataFim)
	{
		Collection<HistoricoColaborador> historicoColaboradors = getDao().getPromocoes(areasIds, estabelecimentosIds, dataIni, dataFim);

		Map promocaoHorizontal = new HashMap<String, Integer>();
		Map promocaoVertical = new HashMap<String, Integer>();

		Map areas = new HashMap<Long, AreaOrganizacional>();
		Map estabelecimentos = new HashMap<Long, Estabelecimento>();

		String chave = "";
		Integer subTotal;

		for (HistoricoColaborador historico : historicoColaboradors)
		{
			areas.put(historico.getAreaOrganizacional().getId(), historico.getAreaOrganizacional());
			estabelecimentos.put(historico.getEstabelecimento().getId(), historico.getEstabelecimento());

			if (historico.getMotivo().equals(MotivoHistoricoColaborador.PROMOCAO_HORIZONTAL))
			{
				Long areaId = historico.getAreaOrganizacional().getId();
				Long estabelecimentoId = historico.getEstabelecimento().getId();

				chave = areaId + "/" + estabelecimentoId;

				if (promocaoHorizontal.containsKey(chave))
				{
					subTotal = (Integer) promocaoHorizontal.get(chave) + 1;
					promocaoHorizontal.put(chave, subTotal);
				}
				else
				{
					promocaoHorizontal.put(chave, 1);
				}
			}
			else if (historico.getMotivo().equals(MotivoHistoricoColaborador.PROMOCAO_VERTICAL))
			{
				Long areaAnteriorId = historico.getHistoricoAnterior().getAreaOrganizacional().getId();
				Long estabelecimentoAnteriorId = historico.getHistoricoAnterior().getEstabelecimento().getId();

				chave = areaAnteriorId + "/" + estabelecimentoAnteriorId;

				if (promocaoVertical.containsKey(chave))
				{
					subTotal = (Integer) promocaoVertical.get(chave) + 1;
					promocaoVertical.put(chave, subTotal);
				}
				else
				{
					promocaoVertical.put(chave, 1);
				}
			}
		}

		return montaRelatorioPromocoes(areas, estabelecimentos, promocaoHorizontal, promocaoVertical);
	}

	public Collection<RelatorioPromocoes> montaRelatorioPromocoes(Map areas, Map estabelecimentos, Map promocaoHorizontal, Map promocaoVertical)
	{
		Collection<RelatorioPromocoes> relatorioPromocoes = new ArrayList<RelatorioPromocoes>();
		Collection<String> chaves = addColl(promocaoHorizontal, promocaoVertical);

		String[] chaveTmp = new String[2];

		for (String chave : chaves)
		{
			RelatorioPromocoes relatorioPromo = new RelatorioPromocoes();
			chaveTmp = chave.split("/");

			relatorioPromo.setArea((AreaOrganizacional) areas.get(Long.valueOf(chaveTmp[0])));
			relatorioPromo.setEstabelecimento((Estabelecimento) estabelecimentos.get(Long.valueOf(chaveTmp[1])));

			if (promocaoHorizontal.containsKey(chave))
				relatorioPromo.setQtdHorizontal((Integer) promocaoHorizontal.get(chave));

			if (promocaoVertical.containsKey(chave))
				relatorioPromo.setQtdVertical((Integer) promocaoVertical.get(chave));

			relatorioPromocoes.add(relatorioPromo);
		}

		return relatorioPromocoes;
	}

	private Collection<String> addColl(Map promocaoHorizontal, Map promocaoVertical)
	{
		Collection<String> chaves = new ArrayList<String>();
		Collection<String> chavesHorizontal = promocaoHorizontal.keySet();
		Collection<String> chavesVertical = promocaoVertical.keySet();

		for (String chave : chavesHorizontal)
		{
			if (!chaves.contains(chave))
				chaves.add(chave);
		}

		for (String chave : chavesVertical)
		{
			if (!chaves.contains(chave))
				chaves.add(chave);
		}

		return chaves;
	}

	public Collection<RelatorioPromocoes> montaRelatorio(Collection<HistoricoColaborador> historicoColaboradors,
			Collection<HistoricoColaborador> historicoColaboradorsTodos)
	{
		Collection<RelatorioPromocoes> relatorioPromocoes = new ArrayList<RelatorioPromocoes>();
		Map promocaoHorizontal = new HashMap<AreaOrganizacional, Integer>();
		Map promocaoVertical = new HashMap<AreaOrganizacional, Integer>();
		Collection<AreaOrganizacional> chaveAreas = new ArrayList<AreaOrganizacional>();

		AreaOrganizacional area;
		int subTotal;
		for (HistoricoColaborador histColaborador : historicoColaboradors)
		{
			subTotal = 1;
			if (histColaborador.getMotivo().equals(MotivoHistoricoColaborador.PROMOCAO_HORIZONTAL))
			{
				area = histColaborador.getAreaOrganizacional();
				if (!chaveAreas.contains(histColaborador.getAreaOrganizacional()))
					chaveAreas.add(histColaborador.getAreaOrganizacional());

				if (promocaoHorizontal.containsKey(area))
					subTotal = ((Integer) promocaoHorizontal.get(area)) + 1;
				promocaoHorizontal.put(area, subTotal);
			}
			else if (histColaborador.getMotivo().equals(MotivoHistoricoColaborador.PROMOCAO_VERTICAL))
			{

				area = extraiAreaHistoricoAnterior(histColaborador, historicoColaboradorsTodos);

				if (!chaveAreas.contains(histColaborador.getAreaOrganizacional()))
					chaveAreas.add(histColaborador.getAreaOrganizacional());

				if (promocaoVertical.containsKey(area))
					subTotal = ((Integer) promocaoVertical.get(area)) + 1;
				promocaoVertical.put(area, subTotal);

			}
		}

		RelatorioPromocoes relatorioPromocao;
		for (AreaOrganizacional areaTmp : chaveAreas)
		{
			relatorioPromocao = new RelatorioPromocoes();
			relatorioPromocao.setArea(areaTmp);
			if (promocaoHorizontal.get(areaTmp) != null)
				relatorioPromocao.setQtdHorizontal((Integer) promocaoHorizontal.get(areaTmp));

			if (promocaoVertical.get(areaTmp) != null)
				relatorioPromocao.setQtdVertical((Integer) promocaoVertical.get(areaTmp));

			relatorioPromocoes.add(relatorioPromocao);
		}

		return relatorioPromocoes;

	}

	private AreaOrganizacional extraiAreaHistoricoAnterior(HistoricoColaborador histColaborador, Collection<HistoricoColaborador> historicoColaboradorsTodos)
	{
		for (HistoricoColaborador hc : historicoColaboradorsTodos)
		{
			if (hc.getColaborador().getId().equals(histColaborador.getColaborador().getId()))
			{
				if (!hc.getId().equals(histColaborador.getId()))
				{
					return hc.getAreaOrganizacional();
				}
			}
		}
		return null;
	}

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

			proxHistorico = null;
		}
		return historicos;
	}

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

	public void atualizaHistoricosImediatos(HistoricoColaborador hist)
	{
		HistoricoColaborador historicoAnterior = getHistoricoAnterior(hist);
		if (historicoAnterior != null && historicoAnterior.getId() != null)
			getDao().atualizarHistoricoAnterior(historicoAnterior);
		HistoricoColaborador historicoProximo = getHistoricoProximo(hist);
		if (historicoProximo != null && historicoProximo.getId() != null)
			getDao().atualizarHistoricoAnterior(historicoProximo);
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

	public Collection<HistoricoColaborador> getHistoricosAtuaisByEstabelecimentoAreaGrupo(Long[] estabelecimentoIds, char filtrarPor,
			Long[] areaOrganizacionalIds, Long[] grupoOcupacionalIds, Long empresaId, Date dataTabela)
	{
		return getDao().getHistoricosAtuaisByEstabelecimentoAreaGrupo(estabelecimentoIds, filtrarPor, areaOrganizacionalIds, grupoOcupacionalIds, empresaId, dataTabela);
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public HistoricoColaborador ajustaTipoSalario(HistoricoColaborador historico, int salarioPropostoPor, Indice indice, Double quantidadeIndice,
			Double salarioColaborador)
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
		Collection<HistoricoColaborador> retorno = new ArrayList<HistoricoColaborador>();
		List<HistoricoColaborador> historicoColaboradors = (List<HistoricoColaborador>) findPromocaoByColaborador(colaboradorId);

		int proximo = 1;
		for (HistoricoColaborador historicoColaboradorTmp : historicoColaboradors)
		{
			if (historicoColaboradorTmp.getMotivo().equals("null") || historicoColaboradorTmp.getMotivo().equals(""))
				historicoColaboradorTmp.setMotivo("Mudança de Situação");

			switch (historicoColaboradorTmp.getTipoSalario())
			{
				case TipoAplicacaoIndice.VALOR:
				{
					retorno.add(historicoColaboradorTmp);
					break;
				}
				case TipoAplicacaoIndice.INDICE:
				{
					Date dataProximo = getDataProximoHistorico(historicoColaboradors, proximo);

					Collection<IndiceHistorico> indiceHistoricos = indiceHistoricoManager.findByPeriodo(historicoColaboradorTmp.getIndice().getId(),
							historicoColaboradorTmp.getData(), dataProximo);

					retorno.add(historicoColaboradorTmp);
					for (IndiceHistorico indiceHistorico : indiceHistoricos)
					{
						HistoricoColaborador historicoColaboradorClone = (HistoricoColaborador) historicoColaboradorTmp.clone();
						historicoColaboradorClone.setData(indiceHistorico.getData());
						historicoColaboradorClone.getIndice().setIndiceHistoricoAtual(indiceHistorico);
						historicoColaboradorClone.setMotivo("Reajuste do Índice");

						retorno.add(historicoColaboradorClone);
					}

					break;
				}
				case TipoAplicacaoIndice.CARGO:
				{
					Date dataProximo = getDataProximoHistorico(historicoColaboradors, proximo);

					Collection<FaixaSalarialHistorico> faixaHistoricos = faixaSalarialHistoricoManager.findByPeriodo(historicoColaboradorTmp.getFaixaSalarial()
							.getId(), historicoColaboradorTmp.getData(), dataProximo);

					retorno.add(historicoColaboradorTmp);
					for (FaixaSalarialHistorico faixaHistorico : faixaHistoricos)
					{
						if (faixaHistorico.getData().before(historicoColaboradorTmp.getData()))
							continue;

						HistoricoColaborador historicoColaboradorClone = (HistoricoColaborador) historicoColaboradorTmp.clone();
						historicoColaboradorClone.setData(faixaHistorico.getData());
						historicoColaboradorClone.getFaixaSalarial().setFaixaSalarialHistoricoAtual(faixaHistorico);

						if (faixaHistorico.getObsReajuste() == null || faixaHistorico.getObsReajuste().equals(""))
							historicoColaboradorClone.setMotivo("Reajuste da Faixa");
						else
							historicoColaboradorClone.setMotivo(faixaHistorico.getObsReajuste());

						retorno.add(historicoColaboradorClone);
					}

					break;
				}
			}

			proximo++;
		}

		montaAreaOrganizacional(empresaId, retorno);

		return retorno;
	}

	public void montaAreaOrganizacional(Long empresaId, Collection<HistoricoColaborador> retorno) throws Exception
	{
		if (empresaId != null)
		{
			Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllList(empresaId, AreaOrganizacional.TODAS);
			areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);

			for (HistoricoColaborador historico : retorno)
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

	public Collection<HistoricoColaborador> findByColaboradorProjection(Long colaboradorId)
	{
		return getDao().findByColaboradorProjection(colaboradorId);
	}

	public Collection<HistoricoColaborador> findByColaborador(Long colaboradorId, Long empresaId) throws Exception
	{
		Collection<HistoricoColaborador> historicos = getDao().findPromocaoByColaborador(colaboradorId);

		montaAreaOrganizacional(empresaId, historicos);

		return historicos;
	}

	public HistoricoColaborador findByIdHQL(Long historicoColaboradorId)
	{
		return getDao().findByIdHQL(historicoColaboradorId);
	}

	public boolean setStatus(Long historicoColaboradorId, Boolean aprovado)
	{
		return getDao().setStatus(historicoColaboradorId, aprovado);
	}

	public boolean verificaHistoricoNaFolhaAC(Long historicoColaboradorId, String colaboradorCodigoAC, Empresa empresa)
	{
		return acPessoalClientColaborador.verificaHistoricoNaFolhaAC(historicoColaboradorId, colaboradorCodigoAC, empresa);
	}

	public void setAcPessoalClientColaborador(AcPessoalClientColaborador acPessoalClientColaborador)
	{
		this.acPessoalClientColaborador = acPessoalClientColaborador;
	}

	public void updateHistorico(HistoricoColaborador historicoColaborador, Empresa empresa) throws Exception
	{
		HistoricoColaborador historicoColaboradorTmp = findByIdProjection(historicoColaborador.getId());
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

		if (historico.getHistoricoAnterior() != null && historico.getHistoricoAnterior().getId() == null)
			historico.setHistoricoAnterior(null);

		return historico;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	// TODO Mensagem de erro usando HTML... achar solução para o problema
	public void removeHistoricoAndReajuste(Long historicoColaboradorId, Long colaboradorId, Empresa empresa) throws Exception
	{
		HistoricoColaborador historicoColaboradorTmp = findByIdProjection(historicoColaboradorId);
		if(empresa.isAcIntegra() && !historicoColaboradorTmp.getColaborador().isNaoIntegraAc())
		{
			if(historicoColaboradorTmp.getColaborador().getCodigoAC() == null)
				throw new Exception("Cadastro do Colaborador pendente no AC Pessoal.");

			if(!existeHistoricoAprovado(historicoColaboradorTmp.getId(), historicoColaboradorTmp.getColaborador().getId()))
				throw new Exception("<div>Não existe outro histórico aprovado pelo AC Pessoal.<br>Não é permitido excluir.</div>");

			if(verificaHistoricoNaFolhaAC(historicoColaboradorId, historicoColaboradorTmp.getColaborador().getCodigoAC(), empresa))
				throw new Exception("<div>Uma Folha de Pagamento foi processada no AC Pessoal com este Histórico.<br>Não é permitido excluir.</div>");
		}

		if(getCount(new String[]{"colaborador.id"}, new Object[]{colaboradorId}) <= 1)
			throw new Exception("Não é permitido deletar o último histórico.");

		getDao().updateHistoricoAnterior(historicoColaboradorId);
		Long reajusteColaboradorId = getDao().findReajusteByHistoricoColaborador(historicoColaboradorId);
		
		remove(historicoColaboradorId);
		
		if(reajusteColaboradorId != null)
			reajusteColaboradorManager.remove(reajusteColaboradorId);
		
		getDao().getHibernateTemplateByGenericDao().flush();

		if(empresa.isAcIntegra() && !historicoColaboradorTmp.getColaborador().isNaoIntegraAc())
		{
			TSituacao situacao = bindSituacao(historicoColaboradorTmp, empresa.getCodigoAC());
			
			acPessoalClientTabelaReajuste.deleteHistoricoColaboradorAC(empresa, situacao);
		}
	}

	public void removeHistoricoAndReajusteAC(HistoricoColaborador historicoColaborador) throws Exception
	{
		if(!existeHistoricoAprovado(historicoColaborador.getId(), historicoColaborador.getColaborador().getId()))
			throw new Exception("Não existe outro histórico aprovado pelo AC Pessoal. Não é permitido excluir.");

		if(getCount(new String[]{"colaborador.id"}, new Object[]{historicoColaborador.getColaborador().getId()}) <= 1)
			throw new Exception("Não é permitido deletar o último histórico.");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			getDao().updateHistoricoAnterior(historicoColaborador.getId());

			Long reajusteColaboradorId = getDao().findReajusteByHistoricoColaborador(historicoColaborador.getId());

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
			historicoColaborador = getDao().findByAC(situacao.getDataFormatada(), situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC());

		String mensagemFinal = mensagemManager.formataMensagemCancelamentoHistoricoColaborador(mensagem, historicoColaborador);

		Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(situacao.getEmpresaCodigoAC());

		String link = "cargosalario/historicoColaborador/prepareUpdate.action?historicoColaborador.id="+ historicoColaborador.getId() +"&colaborador.id=" + historicoColaborador.getColaborador().getId();
		usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagemFinal, "AC Pessoal", link, usuarioEmpresas);

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

		return historicoColaborador;
	}

	public Collection<PendenciaAC> findPendenciasByHistoricoColaborador(Long empresaId)
	{
		Collection<PendenciaAC> pendenciaACs = new ArrayList<PendenciaAC>();

		Collection<HistoricoColaborador> historicoColaboradors = getDao().findPendenciasByHistoricoColaborador(empresaId);

		for (HistoricoColaborador historicoColaborador : historicoColaboradors)
		{
			PendenciaAC pendenciaAC = new PendenciaAC(historicoColaborador);
			
			pendenciaAC.montarDetalhes();

			pendenciaACs.add(pendenciaAC);
		}

		return pendenciaACs;
	}

	public Collection<TSituacao> findHistoricosByTabelaReajuste(Long tabelaReajusteColaboradorId, Empresa empresa)
	{
		Collection<HistoricoColaborador> historicos = getDao().findHistoricosByTabelaReajuste(tabelaReajusteColaboradorId);
		Collection<TSituacao> situacoes = new ArrayList<TSituacao>();

		for (HistoricoColaborador hc : historicos)
		{
			TSituacao situacao = bindSituacao(hc, empresa.getCodigoAC());
			situacoes.add(situacao);
		}

		return situacoes;
	}

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

	public void setUsuarioMensagemManager(UsuarioMensagemManager usuarioMensagemManager)
	{
		this.usuarioMensagemManager = usuarioMensagemManager;
	}

	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager)
	{
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}

	public void setAcPessoalClientTabelaReajuste(AcPessoalClientTabelaReajusteInterface acPessoalClientTabelaReajuste)
	{
		this.acPessoalClientTabelaReajuste = acPessoalClientTabelaReajuste;
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

	public void setMensagemManager(MensagemManager mensagemManager)
	{
		this.mensagemManager = mensagemManager;
	}

	public HistoricoColaborador getHistoricoAtualOuFuturo(Long colaboradorId)
	{
		return getDao().getHistoricoAtual(colaboradorId, TipoBuscaHistoricoColaborador.COM_HISTORICO_FUTURO);
	}

	public HistoricoColaborador updateSituacao(TSituacao situacao) throws Exception
	{
		HistoricoColaborador historicoColaborador = null;
		if(situacao.getId() != null && situacao.getId() != 0)
			historicoColaborador = getDao().findByIdProjectionHistorico(situacao.getId().longValue());
		else
			historicoColaborador = getDao().findByAC(situacao.getDataFormatada(), situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC());

		historicoColaborador = bindSituacao(situacao, historicoColaborador);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);

		getDao().update(historicoColaborador);

		return historicoColaborador;
	}

	public HistoricoColaborador prepareSituacao(TSituacao situacao) throws Exception
	{
		HistoricoColaborador historicoColaborador = new HistoricoColaborador();

		HistoricoColaborador historicoColaboradorAnterior = getDao().findAtualByAC(situacao.getDataFormatada(), situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC());
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

	private HistoricoColaborador bindSituacao(TSituacao situacao, HistoricoColaborador historicoColaborador) throws Exception
	{
		historicoColaborador.setData(situacao.getDataFormatada());
		historicoColaborador.setEstabelecimento(estabelecimentoManager.findEstabelecimentoByCodigoAc(situacao.getEstabelecimentoCodigoAC(), situacao.getEmpresaCodigoAC()));
		historicoColaborador.setAreaOrganizacional(areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(situacao.getLotacaoCodigoAC(), situacao.getEmpresaCodigoAC()));
		historicoColaborador.setFaixaSalarial(faixaSalarialManager.findFaixaSalarialByCodigoAc(situacao.getCargoCodigoAC(), situacao.getEmpresaCodigoAC()));

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
				historicoColaborador.setIndice(indiceManager.findIndiceByCodigoAc(situacao.getIndiceCodigoAC()));
				historicoColaborador.setQuantidadeIndice(situacao.getIndiceQtd());
				historicoColaborador.setSalario(null);
				break;
		}

		if(historicoColaborador.getAmbiente() != null && historicoColaborador.getAmbiente().getId() == null)
			historicoColaborador.setAmbiente(null);
		if(historicoColaborador.getFuncao() != null && historicoColaborador.getFuncao().getId() == null)
			historicoColaborador.setFuncao(null);
		if(historicoColaborador.getHistoricoAnterior() != null && historicoColaborador.getHistoricoAnterior().getId() == null)
			historicoColaborador.setHistoricoAnterior(null);
		if(historicoColaborador.getIndice() != null && historicoColaborador.getIndice().getId() == null)
			historicoColaborador.setIndice(null);
		if(historicoColaborador.getReajusteColaborador() != null && historicoColaborador.getReajusteColaborador().getId() == null)
			historicoColaborador.setReajusteColaborador(null);

		return historicoColaborador;
	}

	public TSituacao bindSituacao(HistoricoColaborador historicoColaborador, String empresaCodigoAC)
	{
		TSituacao situacao = new TSituacao();

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

	public HistoricoColaborador findByAC(Date data, String empregadoCodigoAC, String empresaCodigoAC)
	{
		return getDao().findByAC(data, empregadoCodigoAC, empresaCodigoAC);
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

	public Collection<HistoricoColaborador> findColaboradoresByTabelaReajusteData(Long tabelaReajusteColaboradorId, Date data)
	{
		return getDao().findColaboradoresByTabelaReajusteData(tabelaReajusteColaboradorId, data);
	}

	public HistoricoColaborador getPrimeiroHistorico(Long colaboradorId)
	{
		return getDao().getPrimeiroHistorico(colaboradorId);
	}

	public void insertHistorico(HistoricoColaborador historicoColaborador, Empresa empresa) throws Exception
	{
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setMotivo("");
		
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

	//testes samuel
	@SuppressWarnings("static-access")
	public Collection<HistoricoColaborador> relatorioColaboradorCargo(Date dataHistorico, String[] cargosCheck, String[] estabelecimentosCheck, Integer qtdMeses, char opcaoFiltro, String[] areaOrganizacionalCheck) throws Exception
	{
		Collection<HistoricoColaborador> historicoColaboradors;
		Date dataConsulta = null;
		
		if(qtdMeses != null && qtdMeses > 0)
		{
			//consulta por quantidade de Meses 
			DateUtil dateUtil = new DateUtil();
			Date dataAtual = new Date();

			if (opcaoFiltro == '0')//data atual 
				dataConsulta = dateUtil.retornaDataAnteriorQtdMeses(dataAtual, qtdMeses, false);			
			else if (opcaoFiltro == '1')//data de referencia 
				dataConsulta = dateUtil.retornaDataAnteriorQtdMeses(dataHistorico, qtdMeses, false);
		}

		historicoColaboradors = getDao().findByCargoEstabelecimento(dataHistorico, LongUtil.arrayStringToArrayLong(cargosCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), dataConsulta, LongUtil.arrayStringToArrayLong(areaOrganizacionalCheck));
			
		if(historicoColaboradors.isEmpty())
			throw new ColecaoVaziaException("Não existem dados para o filtro informado.");
			
		return historicoColaboradors;
	}

	public Collection<HistoricoColaborador> montaRelatorioSituacoes(Long empresaId, Date dataIni, Date dataFim, Long[] estabelecimentosIds, Long[] areasIds, String origemSituacao) throws ColecaoVaziaException, Exception {

		Collection<HistoricoColaborador> situacoes = getDao().findByPeriodo(empresaId, dataIni, dataFim, estabelecimentosIds, areasIds, origemSituacao);
		montaAreaOrganizacional(empresaId, situacoes);
		
		return situacoes;
	}

	public Collection<HistoricoColaborador> findAllByColaborador(Long colaboradorId) {
		return getDao().findAllByColaborador(colaboradorId);
	}
	
	public Collection<HistoricoColaborador> getHistoricosComAmbienteEFuncao(Long colaboradorId) {
		
		Collection<HistoricoColaborador> historicoColaboradors = findAllByColaborador(colaboradorId);
		
		prepareAmbientes(historicoColaboradors);
		prepareFuncoes(historicoColaboradors);
		
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
	private void prepareFuncoes(Collection<HistoricoColaborador> historicoColaboradors) {
		
		FuncaoManager funcaoManager = (FuncaoManager) SpringUtil.getBean("funcaoManager");
		
		HashMap<Long, Collection<Funcao>> mapCargoFuncao = new HashMap<Long, Collection<Funcao>>();
		
		for (HistoricoColaborador historicoColaborador : historicoColaboradors) {
			
			Cargo cargo = historicoColaborador.getFaixaSalarial().getCargo();
			
			if (mapCargoFuncao.get(cargo.getId()) == null)
				mapCargoFuncao.put(cargo.getId(), funcaoManager.findByCargo(cargo.getId()));
			
			historicoColaborador.setFuncoes(mapCargoFuncao.get(cargo.getId()));
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
}