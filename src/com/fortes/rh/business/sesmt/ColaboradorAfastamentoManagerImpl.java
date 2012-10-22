package com.fortes.rh.business.sesmt;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CidManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.sesmt.ColaboradorAfastamentoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.OpcaoImportacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.relatorio.Absenteismo;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.util.importacao.ImportacaoCSVUtil;

public class ColaboradorAfastamentoManagerImpl extends GenericManagerImpl<ColaboradorAfastamento, ColaboradorAfastamentoDao> implements ColaboradorAfastamentoManager
{
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private AfastamentoManager afastamentoManager;
	private ColaboradorManager colaboradorManager; 
	private CidManager cidManager; 

	public Integer getCount(Long empresaId, String nomeBusca, String[] estabelecimentoCheck, ColaboradorAfastamento colaboradorAfastamento)
	{
		Date inicio=null,fim=null;
		Long afastamentoId = null;

		if (colaboradorAfastamento != null)
		{
			inicio = colaboradorAfastamento.getInicio();
			fim = colaboradorAfastamento.getFim();

			if (colaboradorAfastamento.getAfastamento() != null)
				afastamentoId = colaboradorAfastamento.getAfastamento().getId();
		}

		Long[] estabelecimentoIds = LongUtil.arrayStringToArrayLong(estabelecimentoCheck);

		return getDao().getCount(empresaId, afastamentoId, nomeBusca, estabelecimentoIds, inicio, fim);
	}

	public Collection<ColaboradorAfastamento> findAllSelect(int page, int pagingSize, Long empresaId, String nomeBusca, String[] estabelecimentoCheck, String[] areasCheck, ColaboradorAfastamento colaboradorAfastamento, String ascOuDesc, boolean ordenaColaboradorPorNome, boolean ordenaPorCid, char afastadoPeloINSS)
	{
		Date inicio=null,fim=null;
		Long afastamentoId = null;

		if (colaboradorAfastamento != null)
		{
			inicio = colaboradorAfastamento.getInicio();
			fim = colaboradorAfastamento.getFim();

			if (colaboradorAfastamento.getAfastamento() != null)
				afastamentoId = colaboradorAfastamento.getAfastamento().getId();
		}

		Long[] estabelecimentoIds = LongUtil.arrayStringToArrayLong(estabelecimentoCheck);
		Long[] areaIds = LongUtil.arrayStringToArrayLong(areasCheck);
		
		return getDao().findAllSelect(page, pagingSize, empresaId, afastamentoId, nomeBusca, estabelecimentoIds, areaIds, inicio, fim, ascOuDesc, ordenaColaboradorPorNome, ordenaPorCid, afastadoPeloINSS);
	}

	public Collection<ColaboradorAfastamento> findRelatorioAfastamentos(Long empresaId, String nomeBusca, String[] estabelecimentoCheck, String[] areasCheck, ColaboradorAfastamento colaboradorAfastamento, boolean ordenaColaboradorPorNome, boolean ordenaPorCid, char afastadoPeloINSS) throws ColecaoVaziaException
	{
		//cuidado com os parametros desse metodo eles são unha e carne com o relatorio gerado, os parametros são fundamentais
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = findAllSelect(0, 0, empresaId, nomeBusca, estabelecimentoCheck, areasCheck, colaboradorAfastamento, "ASC", ordenaColaboradorPorNome, ordenaPorCid, afastadoPeloINSS);

		if (colaboradorAfastamentos == null || colaboradorAfastamentos.isEmpty())
			throw new ColecaoVaziaException("Não há afastamentos para o filtro informado.");

		if(ordenaPorCid)
		{
			for (ColaboradorAfastamento colaboradorAfastamentotemp : colaboradorAfastamentos)
			{
				if (StringUtils.isBlank(colaboradorAfastamentotemp.getCid())) {
					colaboradorAfastamentotemp.setCid("");
				} else {
					String descricaoCid = cidManager.findDescricaoByCodigo(colaboradorAfastamentotemp.getCid());
					colaboradorAfastamentotemp.setCid(colaboradorAfastamentotemp.getCid() + " - " + (descricaoCid.equals("")?"Não identificado":descricaoCid));
				}
			}
		}
		
		setFamiliaAreas(colaboradorAfastamentos, empresaId);

		return colaboradorAfastamentos;
	}
	
	public Collection<ColaboradorAfastamento> findRelatorioResumoAfastamentos(Long empresaId, String[] estabelecimentosCheck, String[] areasCheck, String[] motivosCheck, ColaboradorAfastamento colaboradorAfastamento) throws ColecaoVaziaException 
	{
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = getDao().findRelatorioResumoAfastamentos(empresaId, LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(motivosCheck), colaboradorAfastamento);
	
		if (colaboradorAfastamentos == null || colaboradorAfastamentos.isEmpty())
			throw new ColecaoVaziaException("Não há afastamentos para o filtro informado.");
		
		return colaboradorAfastamentos;
	}

	private void setFamiliaAreas(Collection<ColaboradorAfastamento> colaboradorAfastamentos, Long empresaId)
	{
		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativas(empresaId, AreaOrganizacional.TODAS, null);

		try
		{
			areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		for (ColaboradorAfastamento colaboradorAfastamento: colaboradorAfastamentos)
		{
			if(colaboradorAfastamento.getColaborador().getAreaOrganizacional() != null && colaboradorAfastamento.getColaborador().getAreaOrganizacional().getId() != null)
				colaboradorAfastamento.getColaborador().setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, colaboradorAfastamento.getColaborador().getAreaOrganizacional().getId()));
		}
	}
	
	// usados pela importação de Afastamentos.
	Map<String, Afastamento> mapDescricaoAfastamento = null; 
	Integer countAfastamentosImportados = 0;
	Integer countTiposAfastamentosCriados = 0;
	
	/**
	 * Importação de Afastamentos dos Empregados. 
	 * Recebe Csv no formato:
	 * Cód. Empregado;Nome Completo;Nome de Escala;Doença(Tipo Afastamento);Data Inicial;Data Final;Médico;Descrição do Tipo
	 * @throws Exception 
	 */
	public void importarCSV(File arquivo, Map<String, Long> afastamentos, Empresa empresa) throws Exception
	{
		countAfastamentosImportados = 0;
		countTiposAfastamentosCriados = 0;
		
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = this.carregarCSV(arquivo);
		String descricao = null;
		String codigoAC = null;
		Colaborador colaborador = null;
		
		for (ColaboradorAfastamento colaboradorAfastamento : colaboradorAfastamentos)
		{
			descricao 	= colaboradorAfastamento.getAfastamento().getDescricao();
			codigoAC 	= colaboradorAfastamento.getColaborador().getCodigoAC();
			colaborador = colaboradorManager.findByCodigoAC(codigoAC, empresa);

			if(colaborador != null)
			{
				if (afastamentos != null && afastamentos.containsKey(descricao) && afastamentos.get(descricao) != null)
					colaboradorAfastamento.setAfastamentoId(afastamentos.get(descricao));//usuário relacionou o afastamento correspondente no RH
				else
					setAfastamentoCadastrado(colaboradorAfastamento);
				
				colaboradorAfastamento.setColaborador(colaborador);
				
				if(!getDao().exists(colaboradorAfastamento))
				{
					getDao().save(colaboradorAfastamento);
					countAfastamentosImportados++;					
				}
			}
		}
	}
	
	public Collection<ColaboradorAfastamento> carregarCSV(File arquivo) throws Exception 
	{
		mapDescricaoAfastamento = getMapDescricaoAfastamentos();
		
		// importacao do CSV para colecao
		ImportacaoCSVUtil importacaoCSVUtil = new ImportacaoCSVUtil();
		
		importacaoCSVUtil.importarCSV(arquivo, OpcaoImportacao.AFASTAMENTOS_TRU);
		return importacaoCSVUtil.getAfastamentos();
	}
	
	// seta tipo afastamento que já existe no banco
	private void setAfastamentoCadastrado(ColaboradorAfastamento colaboradorAfastamento) {
		
		String afastamentoDescricao = StringUtil.retiraAcento(colaboradorAfastamento.getAfastamento().getDescricao()).toLowerCase();
		Afastamento afastamento = mapDescricaoAfastamento.get(afastamentoDescricao);
		
		// se nao existe com essa descricao, 
		// salva um novo tipo de Afastamento no banco
		if (afastamento == null)
		{
			afastamento = colaboradorAfastamento.getAfastamento();
			afastamentoManager.save(afastamento);
			
			mapDescricaoAfastamento.put(afastamentoDescricao, afastamento);
			countTiposAfastamentosCriados++;
		}
		
		colaboradorAfastamento.setAfastamento(afastamento);
	}

	// busca e mapeia Descricao -> Afastamento  
	private Map<String, Afastamento> getMapDescricaoAfastamentos() {
		
		Map<String, Afastamento> mapDescricaoAfastamento = new HashMap<String, Afastamento>();
		
		Collection<Afastamento> afastamentos = afastamentoManager.findAll();
		
		for (Afastamento afastamento : afastamentos)
		{
			String descricao = StringUtil.retiraAcento(afastamento.getDescricao()).toLowerCase();
			mapDescricaoAfastamento.put(descricao, afastamento);
		}
		return mapDescricaoAfastamento;
	}
	
	public Collection<DataGrafico> findQtdCatsPorDiaSemana(Long empresaId, Date dataIni, Date dataFim) 
	{
		Collection<DataGrafico> graficoAfastamentosPorMotivo = new ArrayList<DataGrafico>();
		Collection<Afastamento> qtdAfastamentosPorMotivo = getDao().findQtdAfastamentosPorMotivo(empresaId, dataIni, dataFim);
		
		for (Afastamento afastamento : qtdAfastamentosPorMotivo)
			graficoAfastamentosPorMotivo.add(new DataGrafico(null, afastamento.getDescricao(), afastamento.getQtd(), ""));
		
		return graficoAfastamentosPorMotivo;
	}
	
	public Integer findQtdAfastamentosInss(Long empresaId, Date dataIni, Date dataFim, boolean inss) 
	{
		return getDao().findQtdAfastamentosInss(empresaId, dataIni, dataFim, inss);
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setAfastamentoManager(AfastamentoManager afastamentoManager) {
		this.afastamentoManager = afastamentoManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public Integer getCountAfastamentosImportados() {
		return countAfastamentosImportados;
	}

	public Integer getCountTiposAfastamentosCriados() {
		return countTiposAfastamentosCriados;
	}

	public Collection<ColaboradorAfastamento> findByColaborador(Long colaboradorId) {
		return getDao().findByColaborador(colaboradorId);
	}

	public void setCidManager(CidManager cidManager) {
		this.cidManager = cidManager;
	}

	public Collection<ColaboradorAfastamento> montaMatrizResumo(Long empresaId, String[] estabelecimentosCheck, String[] areasCheck, String[] motivosCheck, ColaboradorAfastamento colaboradorAfastamento) throws ColecaoVaziaException 
	{
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = findRelatorioResumoAfastamentos(empresaId, estabelecimentosCheck, areasCheck, motivosCheck, colaboradorAfastamento);
		Map<Colaborador, Collection<Date>> datasColaboradores = new HashMap<Colaborador, Collection<Date>>();
		
		// agrupa meses onde houveram afastamentos para o colaborador
		for (ColaboradorAfastamento colabAfastamento : colaboradorAfastamentos) 
		{
			if (!datasColaboradores.containsKey(colabAfastamento.getColaborador().getId()))
				datasColaboradores.put(colabAfastamento.getColaborador(), new ArrayList<Date>());
			
			datasColaboradores.get(colabAfastamento.getColaborador()).add(colabAfastamento.getInicio());
		}
		
		// preenche o registro do colaborador com os meses que nao possuem afastamentos
		Date dataAtual = null;
		Colaborador colab = null;
		for (Map.Entry<Colaborador, Collection<Date>> datasColaborador : datasColaboradores.entrySet())
		{
			dataAtual = DateUtil.getInicioMesData(colaboradorAfastamento.getInicio());
			
			while (dataAtual.before(DateUtil.getUltimoDiaMes(colaboradorAfastamento.getFim())))
			{
				if (!datasColaborador.getValue().contains(dataAtual))
				{
					colab = datasColaborador.getKey();
					colaboradorAfastamentos.add(new ColaboradorAfastamento(colab.getId(), colab.getMatricula(), colab.getNome(), colab.getDataAdmissao(), dataAtual, null, null));
				}
				dataAtual = DateUtil.incrementaMes(dataAtual, 1);
			}
		}
		
		return colaboradorAfastamentos;
	}

	public Collection<Absenteismo> countAfastamentosByPeriodo(Date dataIni, Date dataFim, Collection<Long> empresaIds, Collection<Long> estabelecimentosIds, Collection<Long> areasIds, Collection<Long> afastamentosIds) 
	{
		return getDao().countAfastamentosByPeriodo(dataIni, dataFim, empresaIds, estabelecimentosIds, areasIds, afastamentosIds);
	}

	public ColaboradorAfastamento findByColaboradorAfastamentoId(Long colaboradorAfastamentoId)
	{
			return getDao().findByColaboradorAfastamentoId(colaboradorAfastamentoId);
	}
}