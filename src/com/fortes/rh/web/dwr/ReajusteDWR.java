package com.fortes.rh.web.dwr;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.cargosalario.ReajusteColaboradorManager;
import com.fortes.rh.business.cargosalario.ReajusteFaixaSalarialManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SalarioUtil;
import com.fortes.web.tags.CheckBox;
import com.fortes.web.tags.Option;

public class ReajusteDWR
{
	private ColaboradorManager colaboradorManager;
	private FaixaSalarialManager faixaSalarialManager;
	private IndiceManager indiceManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;
	private ReajusteColaboradorManager reajusteColaboradorManager;
	private ReajusteFaixaSalarialManager reajusteFaixaSalarialManager; 

	public Map<String, Object> getColaboradorSolicitacaoReajuste(Long colaboradorId) throws Exception
	{
		Map<String, Object> retorno = new HashMap<String, Object>();
		HistoricoColaborador historicoColaborador = historicoColaboradorManager.getHistoricoAtual(colaboradorId);

		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaborador);

		historicoColaboradorManager.montaAreaOrganizacional(historicoColaborador.getColaborador().getEmpresa().getId(), historicoColaboradors);

		retorno.put("areaOrganizacionalAtualId", historicoColaborador.getAreaOrganizacional().getId());
		retorno.put("areaOrganizacionalAtualNome", historicoColaborador.getAreaOrganizacional().getDescricao());
		retorno.put("cargoAtualId", historicoColaborador.getFaixaSalarial().getCargo().getId());
		retorno.put("cargoAtualNome", historicoColaborador.getFaixaSalarial().getDescricao());
		retorno.put("estabelecimentoAtualId", historicoColaborador.getEstabelecimento().getId());
		retorno.put("estabelecimentoAtualNome", historicoColaborador.getEstabelecimento().getNome());
		retorno.put("tipoSalarioDescricao", historicoColaboradorManager.montaTipoSalario(historicoColaborador.getQuantidadeIndice(), historicoColaborador.getTipoSalario(), historicoColaborador.getIndice().getNome()));
		retorno.put("faixaSalarialAtual", historicoColaborador.getFaixaSalarial().getId());
		retorno.put("tipoSalarioAtual", historicoColaborador.getTipoSalario());
		
		if(historicoColaborador.getIndice() == null || historicoColaborador.getIndice().getId() == null)
			retorno.put("indiceAtualId", "");		
		else
			retorno.put("indiceAtualId", historicoColaborador.getIndice().getId());

		String qtdFormatada = "";
		if(historicoColaborador.getQuantidadeIndice() != null)
		{
			NumberFormat formata = new DecimalFormat("#0.0##");
			qtdFormatada = formata.format(historicoColaborador.getQuantidadeIndice());
		}
		retorno.put("quantidadeIndiceAtual", qtdFormatada);

		if (historicoColaborador.getFuncao() != null)
		{
			retorno.put("funcaoAtualId", historicoColaborador.getFuncao().getId());
			retorno.put("funcaoAtualNome", historicoColaborador.getFuncao().getNome());
		}
		if (historicoColaborador.getAmbiente() != null)
		{
			retorno.put("ambienteAtualId", historicoColaborador.getAmbiente().getId());
			retorno.put("ambienteAtualNome", historicoColaborador.getAmbiente().getNome());
		}
		
		try {
			retorno.put("salarioAtual", historicoColaborador.getSalarioCalculado().toString());			
		} catch (Exception e) {
			retorno.put("salarioAtual", "0.00");
		}

		return retorno;
	}

	public Long verificaColaborador(Long tabelaId, Long colaboradorId, boolean integraAC) throws Exception
	{
		if(tabelaId == -1)
			throw new Exception("Selecione um Planejamento de Realinhamento.");

		if(colaboradorId == -1)
			throw new Exception("Selecione um colaborador.");

		TabelaReajusteColaborador tabelaReajusteColaborador = tabelaReajusteColaboradorManager.findByIdProjection(tabelaId);

		Colaborador colaborador = colaboradorManager.findColaboradorById(colaboradorId);

		if(!tabelaReajusteColaborador.getData().after(colaborador.getDataAdmissao()))
			throw new Exception("A data da solicitação de realinhamento deve ser maior que a data de admissão do colaborador.");

    	if(integraAC && !colaborador.isNaoIntegraAc())
    	{
    		if(colaborador.getCodigoAC() == null || colaborador.getCodigoAC().equals(""))
    			throw new Exception("Antes de fazer uma solicitação de realinhamento, o cadastro deste colaborador precisa ser concluído no AC Pessoal.");
    	}

		if(reajusteColaboradorManager.verifyExists(new String[]{"colaborador.id", "tabelaReajusteColaborador.id"}, new Object[]{colaboradorId, tabelaId}))
			throw new Exception("Já existe uma solicitação de realinhamento para este colaborador.");


		return colaboradorId;
	}

	public Map<Long, String> getColaboradoresByAreaOrganizacional(String areaId)
	{
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		
		Map<Long, String> retorno = new LinkedHashMap<Long, String>();
		retorno.put(0L, "Selecione...");

		if (areaId != null && !areaId.equals(""))
		{
			Long[] idsLong = new Long[1];
			idsLong[0] = new Long(areaId);

			Collection<Colaborador> colaboradorLista = new LinkedList<Colaborador>();
			colaboradorLista = colaboradorManager.getColaboradoresIntegraAc(colaboradorManager.findByAreasOrganizacionalIds(idsLong));
			
			colaboradores = new CollectionUtil<Colaborador>().sortCollectionStringIgnoreCase(colaboradorLista, "nome");

			if (colaboradores != null && colaboradores.size() > 0)
				for (Colaborador colaborador : colaboradorLista) 
					retorno.put(colaborador.getId(), colaborador.getNomeMaisNomeComercial());
		}

		return retorno;
	}

	public String calculaSalario(String tipoSalario, String faixaSalarialId, String indiceId, String quantidade, String salario) throws Exception
	{
		return calculaSalarioHistorico(tipoSalario, faixaSalarialId, indiceId, quantidade, salario, DateUtil.formataDiaMesAno(new Date()));
	}

	public String calculaSalarioHistorico(String tipoSalario, String faixaSalarialId, String indiceId, String quantidade, String salario, String data) throws Exception
	{
//		DecimalFormat formatador = new DecimalFormat("#,##0.00");
		DecimalFormat formatador = (DecimalFormat) DecimalFormat.getInstance(new Locale("pt", "BR"));
		formatador.applyPattern("#,##0.00");
//		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dataHistorico = dateFormat.parse(data);

		if(faixaSalarialId.equals(""))
			return "";

		if(tipoSalario.equals(""))
			tipoSalario = String.valueOf(TipoAplicacaoIndice.VALOR);

		if(quantidade.equals(""))
			quantidade = "0";

		if(salario.equals(""))
			salario = "0";

		quantidade = quantidade.replace(".","").replace(",", ".");
		salario = salario.replace(".","").replace(",",".");

		int tipo = Integer.parseInt(tipoSalario);
		FaixaSalarial faixaSalarial = null;
		Indice indice = null;

		switch (tipo)
		{
			case TipoAplicacaoIndice.CARGO:
				faixaSalarial = faixaSalarialManager.findHistorico(Long.valueOf(faixaSalarialId), dataHistorico);
				break;
			case TipoAplicacaoIndice.INDICE:
				if(indiceId.equals(""))
					return formatador.format(0.0);
				else
				{
					indice = indiceManager.findHistorico(Long.valueOf(indiceId), dataHistorico);
				}
				break;
		}

		Double salarioCalculado = SalarioUtil.getValor(tipo, faixaSalarial, indice, Double.valueOf(quantidade), Double.valueOf(salario));

		if(salarioCalculado == null)
			throw new Exception("Atenção: Não existe Histórico nessa data para o tipo de salário selecionado.");

		return formatador.format(salarioCalculado);
	}
	
	public Collection<CheckBox> getByCargosDesabilitandoPorIndice(String[] cargoIds)
	{
		Collection<CheckBox> checkboxes = new ArrayList<CheckBox>();
		Collection<FaixaSalarial> faixasSalariais = faixaSalarialManager.findByCargos(LongUtil.arrayStringToArrayLong(cargoIds));
		CheckBox checkBox = null;
		
		for (FaixaSalarial faixaSalarial : faixasSalariais)
		{
			checkBox = new CheckBox();
			checkBox.setId(faixaSalarial.getId());
			checkBox.setNome(faixaSalarial.getDescricao());
			checkBox.setDesabilitado(true);
			
			if (reajusteFaixaSalarialManager.verificaPendenciasPorFaixa(faixaSalarial.getId()))
				checkBox.setTitulo("Essa faixa salarial possui um realinhamento pendente");
			else if (faixaSalarial.getFaixaSalarialHistoricoAtual() == null || faixaSalarial.getFaixaSalarialHistoricoAtual().getId() == null)
				checkBox.setTitulo("Essa faixa salarial não possui histórico");
			else if (faixaSalarial.getFaixaSalarialHistoricoAtual().getTipo().equals(TipoAplicacaoIndice.INDICE))
				checkBox.setTitulo("Essa faixa salarial possui valor por índice");
			else
				checkBox.setDesabilitado(false);
			
			checkboxes.add(checkBox);
		}
		
		return checkboxes;
	}
	
	public Collection<Option> getByCargoDesabilitandoPorIndice(Long cargoId)
	{
		Collection<Option> options = new ArrayList<Option>();
		Collection<FaixaSalarial> faixasSalariais = faixaSalarialManager.findByCargos(new Long[] { cargoId });
		Option option = null;
		
		for (FaixaSalarial faixaSalarial : faixasSalariais)
		{
			option = new Option();
			option.setId(faixaSalarial.getId());
			option.setNome(faixaSalarial.getDescricao());
			option.setDesabilitado(true);
			
			if (reajusteFaixaSalarialManager.verificaPendenciasPorFaixa(faixaSalarial.getId()))
				option.setTitulo("Essa faixa salarial possui um realinhamento pendente");
			else if (faixaSalarial.getFaixaSalarialHistoricoAtual() == null || faixaSalarial.getFaixaSalarialHistoricoAtual().getId() == null)
				option.setTitulo("Essa faixa salarial não possui histórico");
			else if (faixaSalarial.getFaixaSalarialHistoricoAtual().getTipo().equals(TipoAplicacaoIndice.INDICE))
				option.setTitulo("Essa faixa salarial possui valor por índice");
			else
				option.setDesabilitado(false);
			
			options.add(option);
		}
		
		return options;
	}
	
	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public void setIndiceManager(IndiceManager indiceManager)
	{
		this.indiceManager = indiceManager;
	}

	public void setReajusteColaboradorManager(ReajusteColaboradorManager reajusteColaboradorManager)
	{
		this.reajusteColaboradorManager = reajusteColaboradorManager;
	}

	public void setTabelaReajusteColaboradorManager(TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager)
	{
		this.tabelaReajusteColaboradorManager = tabelaReajusteColaboradorManager;
	}

	public void setReajusteFaixaSalarialManager(
			ReajusteFaixaSalarialManager reajusteFaixaSalarialManager) {
		this.reajusteFaixaSalarialManager = reajusteFaixaSalarialManager;
	}

}
