package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.relatorio.QtdPorFuncaoRelatorio;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class FuncaoListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

	private FuncaoManager funcaoManager;
	private CargoManager cargoManager;
	private ColaboradorManager colaboradorManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private HistoricoFuncaoManager historicoFuncaoManager;
	
	private Collection<Funcao> funcaos = new ArrayList<Funcao>();
	private Collection<AreaOrganizacional> areas;
	private Collection<Colaborador> colaboradors;
	private Collection<Estabelecimento> estabelecimentos;
	private Collection<Cargo> cargos;

	private Long[] funcoesCheck;
	private Collection<CheckBox> funcoesCheckList = new ArrayList<CheckBox>();
	
	private String cpfBusca;
	private String nomeBusca;

	private Cargo cargoTmp;
	private Funcao funcao;
	private AreaOrganizacional areaBusca;
	private Colaborador colaborador;
	private HistoricoColaborador historicoColaborador;

	// relatorio
	private Date data;

	private Collection<QtdPorFuncaoRelatorio> dataSource;

	private Map<String, Object> parametros;

	private Estabelecimento estabelecimento;

	private char tipoAtivo;
	
	public String list() throws Exception
	{
		setTotalSize(funcaoManager.getCount(cargoTmp.getId()));
		funcaos = funcaoManager.findByCargo(getPage(), getPagingSize(), cargoTmp.getId());
		cargoTmp = (Cargo) cargoManager.findByIdProjection(cargoTmp.getId());

		return Action.SUCCESS;
	}
	
	public String listFiltro() throws Exception
	{
		if(cargoTmp != null && cargoTmp.getId() != null)
			list();
			
		cargos = cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, getEmpresaSistema().getId());
		
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String mudancaFuncaoFiltro() throws Exception
	{
		if(areaBusca == null)
			areaBusca = new AreaOrganizacional();

		Map parametros = new HashMap();
		parametros.put("nomeBusca", nomeBusca);
		parametros.put("cpfBusca", cpfBusca);
		parametros.put("empresaId", getEmpresaSistema().getId());
		parametros.put("areaId", areaBusca.getId());

		setTotalSize(colaboradorManager.getCount(parametros));
		colaboradors = colaboradorManager.findList(getPage(), getPagingSize(), parametros);

		if(colaboradors == null || colaboradors.isEmpty())
			addActionMessage("Não existem mudanças de função a serem listadas!");

		areas = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, getEmpresaSistema().getId());

		if(!areas.isEmpty())
		{
			CollectionUtil<AreaOrganizacional> areasOrdenadas = new CollectionUtil<AreaOrganizacional>();
			areas = areasOrdenadas.sortCollectionStringIgnoreCase(areas, "descricao");
		}

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try {
			funcaoManager.removeFuncao(funcao);
			addActionMessage("Função excluída com sucesso.");

		} catch (DataIntegrityViolationException e) {
			addActionError("Não é possível excluir a Função, pois esta possui dependências.");
			e.printStackTrace();

		} catch (ConstraintViolationException e) {
			addActionError("Não é possível excluir a Função, pois esta possui dependências.");
			e.printStackTrace();
		
		} catch (Exception e) {
			addActionError(e.getMessage());
			e.printStackTrace();
		}

		return Action.SUCCESS;
	}

	public String deleteFiltro() throws Exception
	{
		delete();
		return Action.SUCCESS;
	}
	
	public String prepareRelatorioQtdPorFuncao()
	{
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		return SUCCESS;
	}

	public String gerarRelatorioQtdPorFuncao()
	{
		String msg = new String();
		try
		{
			parametros = RelatorioUtil.getParametrosRelatorio("Distribuição de Colaboradores por Função", getEmpresaSistema(), "Data: " + DateUtil.formataDiaMesAno(data));
			dataSource = funcaoManager.getQtdColaboradorByFuncao(getEmpresaSistema().getId(), estabelecimento.getId(), data, tipoAtivo);
			
			if (dataSource.isEmpty())
			{
				msg = "Não existem dados para relatório";
				throw new Exception(msg);  
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
			if (msg == null )
				addActionError("Erro ao gerar relatório.");
			else
				addActionError(msg);
			prepareRelatorioQtdPorFuncao();
			return INPUT;
		}
		return SUCCESS;
	}

	
	public String prepareRelatorioFuncoesExames()
	{
		funcoesCheckList = funcaoManager.populaCheckBox();
		return SUCCESS;
	}
	
	public String relatorioFuncoesExames()
	{
		String msg = new String();
		try
		{
			parametros = RelatorioUtil.getParametrosRelatorio("Funções por Exames", getEmpresaSistema(), "Data: " + DateUtil.formataDiaMesAno(data));
			funcaos = historicoFuncaoManager.findByFuncoes(data, funcoesCheck);  
			
			if (funcaos.isEmpty())
			{
				msg = "Não existem dados para relatório";
				throw new Exception(msg);  
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
			if (msg == null )
				addActionError("Erro ao gerar relatório.");
			else
				addActionError(msg);
			prepareRelatorioFuncoesExames();
			return INPUT;
		}
		return SUCCESS;
	}

	public Collection<Funcao> getFuncaos()
	{
		return funcaos;
	}

	public Funcao getFuncao()
	{
		if(funcao == null)
		{
			funcao = new Funcao();
		}
		return funcao;
	}
	
	public void setFuncao(Funcao funcao)
	{
		this.funcao = funcao;
	}

	public void setFuncaoManager(FuncaoManager funcaoManager)
	{
		this.funcaoManager = funcaoManager;
	}

	public Cargo getCargoTmp()
	{
		return cargoTmp;
	}

	public void setCargoTmp(Cargo cargoTmp)
	{
		this.cargoTmp = cargoTmp;
	}

	public void setFuncaos(Collection<Funcao> funcaos)
	{
		this.funcaos = funcaos;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public void setColaboradors(Collection<Colaborador> colaboradors)
	{
		this.colaboradors = colaboradors;
	}

	public String getCpfBusca()
	{
		return cpfBusca;
	}

	public void setCpfBusca(String cpfBusca)
	{
		this.cpfBusca = cpfBusca;
	}

	public String getNomeBusca()
	{
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = StringUtil.retiraAcento(nomeBusca);
	}

	public Collection<AreaOrganizacional> getAreas()
	{
		return areas;
	}

	public void setAreas(Collection<AreaOrganizacional> areas)
	{
		this.areas = areas;
	}

	public AreaOrganizacional getAreaBusca()
	{
		return areaBusca;
	}

	public void setAreaBusca(AreaOrganizacional areaBusca)
	{
		this.areaBusca = areaBusca;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Colaborador getColaborador()
	{
		return this.colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public HistoricoColaborador getHistoricoColaborador()
	{
		return historicoColaborador;
	}

	public void setHistoricoColaborador(HistoricoColaborador historicoColaborador)
	{
		this.historicoColaborador = historicoColaborador;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Collection<Estabelecimento> getEstabelecimentos()
	{
		return estabelecimentos;
	}

	public Date getData()
	{
		return data;
	}

	public void setData(Date data)
	{
		this.data = data;
	}

	public Collection<QtdPorFuncaoRelatorio> getDataSource()
	{
		return dataSource;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	public Collection<Cargo> getCargos()
	{
		return cargos;
	}

	public Long[] getFuncoesCheck() {
		return funcoesCheck;
	}

	public void setFuncoesCheck(Long[] funcoesCheck) {
		this.funcoesCheck = funcoesCheck;
	}

	public Collection<CheckBox> getFuncoesCheckList() {
		return funcoesCheckList;
	}

	public void setFuncoesCheckList(Collection<CheckBox> funcoesCheckList) {
		this.funcoesCheckList = funcoesCheckList;
	}

	public void setHistoricoFuncaoManager(HistoricoFuncaoManager historicoFuncaoManager)
	{
		this.historicoFuncaoManager = historicoFuncaoManager;
	}

	public void setTipoAtivo(char tipoAtivo)
	{
		this.tipoAtivo = tipoAtivo;
	}

}