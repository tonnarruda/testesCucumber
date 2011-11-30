package com.fortes.rh.business.sesmt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = findAllSelect(0, 0, empresaId, nomeBusca, estabelecimentoCheck, areasCheck, colaboradorAfastamento, "ASC", ordenaColaboradorPorNome, ordenaPorCid, afastadoPeloINSS);

		if (colaboradorAfastamentos == null || colaboradorAfastamentos.isEmpty())
			throw new ColecaoVaziaException("Não há afastamentos para o filtro informado.");

		if(ordenaPorCid)
		{
			for (ColaboradorAfastamento colaboradorAfastamentotemp : colaboradorAfastamentos)
			{
				String descricaoCid = cidManager.findDescricaoByCodigo(colaboradorAfastamentotemp.getCid());
				colaboradorAfastamentotemp.setCid(colaboradorAfastamentotemp.getCid() +  (descricaoCid.equals("")?"":" - " + descricaoCid));
			}
		}
		
		setFamiliaAreas(colaboradorAfastamentos, empresaId);

		return colaboradorAfastamentos;
	}

	private void setFamiliaAreas(Collection<ColaboradorAfastamento> colaboradorAfastamentos, Long empresaId)
	{
		Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllList(empresaId, AreaOrganizacional.TODAS);

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
	Map<String, Afastamento> mapDescricaoAfastamento=null; 
	Integer countAfastamentosImportados=0;
	Integer countTiposAfastamentosCriados=0;
	
	/**
	 * Importação de Afastamentos dos Empregados. 
	 * Recebe Csv no formato:
	 * Cód. Empregado;Nome Completo;Nome de Escala;Doença(Tipo Afastamento);Data Inicial;Data Final;Médico;Descrição do Tipo
	 */
	public void importarCSV(File arquivo, Empresa empresa) throws IOException
	{
		countAfastamentosImportados = 0;
		countTiposAfastamentosCriados = 0;
		
		mapDescricaoAfastamento = getMapDescricaoAfastamentos();
		
		Collection<ColaboradorAfastamento> colaboradorAfastamentos = null;
		
		// importacao do CSV para colecao
		ImportacaoCSVUtil importacaoCSVUtil = new ImportacaoCSVUtil();
		
		importacaoCSVUtil.importarCSV(arquivo, OpcaoImportacao.AFASTAMENTOS_TRU);
		colaboradorAfastamentos = importacaoCSVUtil.getAfastamentos();
		
		for (ColaboradorAfastamento colaboradorAfastamento : colaboradorAfastamentos)
		{
			setAfastamentoCadastrado(colaboradorAfastamento);
			
			String codigoAC = colaboradorAfastamento.getColaborador().getCodigoAC();
			Colaborador colaborador = colaboradorManager.findByCodigoAC(codigoAC, empresa);
			
			colaboradorAfastamento.setColaborador(colaborador);
			
			getDao().save(colaboradorAfastamento);
		}
		
		countAfastamentosImportados = colaboradorAfastamentos.size();
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
}