package com.fortes.rh.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaFormacaoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CodigoCBOManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class CargoEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	
	private CargoManager cargoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ConhecimentoManager conhecimentoManager;
	private HabilidadeManager habilidadeManager;
	private AtitudeManager atitudeManager;
	private AreaFormacaoManager areaFormacaoManager;
	private GrupoOcupacionalManager grupoOcupacionalManager;
	private FaixaSalarialManager faixaSalarialManager;
	private EstabelecimentoManager estabelecimentoManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
    private EtapaSeletivaManager etapaSeletivaManager;
    private EmpresaManager empresaManager;
    private CodigoCBOManager codigoCBOManager;

    private boolean exibirSalario;
	private Collection<Cargo> cargos = new ArrayList<Cargo>();
	private Collection<HistoricoColaborador> historicoColaboradors;
	private Collection<GrupoOcupacional> grupoOcupacionals = new ArrayList<GrupoOcupacional>();
	private Cargo cargo;
	private HashMap<String, String> escolaridades;
	private Map<String, Object> parametros;
	private String filtro;
	private String descricaoCBO;

	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] areasFormacaoCheck;
	private Collection<CheckBox> areasFormacaoCheckList = new ArrayList<CheckBox>();
	private String[] conhecimentosCheck;
	private Collection<CheckBox> conhecimentosCheckList = new ArrayList<CheckBox>();
	private String[] habilidadesCheck;
	private Collection<CheckBox> habilidadesCheckList = new ArrayList<CheckBox>();
	private String[] atitudesCheck;
	private Collection<CheckBox> atitudesCheckList = new ArrayList<CheckBox>();
	private String[] cargosCheck;
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	private String[] gruposCheck;
	private Collection<CheckBox> gruposCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] areaOrganizacionalsCheck;
	private Collection<CheckBox> areaOrganizacionalsCheckList = new ArrayList<CheckBox>();
	private int page;
	private Integer qtdMeses; 
	private Integer qtdMesesDesatualizacao = null; 
	private char opcaoFiltro = '0';
	private char opcaoFiltroAdmitidosOuDEsatualizados = '0';
    private Collection<CheckBox> etapaSeletivaCheckList = new ArrayList<CheckBox>();
    private String[] etapaCheck;
	//Transferir faixas entre cargos
	private Collection<FaixaSalarial> faixasDoCargo = new ArrayList<FaixaSalarial>();
	private FaixaSalarial faixa;
	private String novaFaixaNome;
	
	private boolean integraAC;
	private boolean exibColabAdmitido;
	private boolean exibColabDesatualizado ;
	private boolean relatorioResumido;
	private Date dataHistorico;
	private Collection<Empresa> empresas;
	private Long[] empresaIds;//repassado para o DWR
	private Empresa empresa;

	private void prepare() throws Exception
	{
		if (cargo != null && cargo.getId() != null)
		{
			cargo = cargoManager.findByIdAllProjection(cargo.getId());

			cargo.setAreaFormacaos(areaFormacaoManager.findByCargo(cargo.getId()));
			cargo.setAreasOrganizacionais(areaOrganizacionalManager.findByCargo(cargo.getId()));
			cargo.setConhecimentos(conhecimentoManager.findByCargo(cargo.getId()));
			cargo.setHabilidades(habilidadeManager.findByCargo(cargo.getId()));
			cargo.setAtitudes(atitudeManager.findByCargo(cargo.getId()));
			cargo.setEtapaSeletivas(etapaSeletivaManager.findByCargo(cargo.getId()));
		}

		escolaridades = new Escolaridade();
		grupoOcupacionals = grupoOcupacionalManager.findAllSelect(getEmpresaSistema().getId());

		gruposCheckList = grupoOcupacionalManager.populaCheckOrderNome(getEmpresaSistema().getId());
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		areasFormacaoCheckList = areaFormacaoManager.populaCheckOrderNome();
		conhecimentosCheckList = conhecimentoManager.populaCheckOrderNome(getEmpresaSistema().getId());
		habilidadesCheckList = habilidadeManager.populaCheckOrderNome(getEmpresaSistema().getId());
		atitudesCheckList = atitudeManager.populaCheckOrderNome(getEmpresaSistema().getId());
		etapaSeletivaCheckList = etapaSeletivaManager.populaCheckOrderNome(getEmpresaSistema().getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();

		return Action.SUCCESS;
	}

	public String prepareRelatorioCargo() throws Exception
	{
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		areasFormacaoCheckList = areaFormacaoManager.populaCheckOrderNome();
		gruposCheckList = grupoOcupacionalManager.populaCheckOrderNome(getEmpresaSistema().getId());
		cargosCheckList = cargoManager.populaCheckBox(getEmpresaSistema().getId());
		etapaSeletivaCheckList = etapaSeletivaManager.populaCheckOrderNome(getEmpresaSistema().getId());

		return Action.SUCCESS;
	}
	
	public String prepareRelatorioColaboradorCargo() throws Exception
	{
		empresas = empresaManager.findByUsuarioPermissao(SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_COLAB_CARGO");
		CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
		empresaIds = clu.convertCollectionToArrayIds(empresas);
		
		empresa = getEmpresaSistema();
		dataHistorico = new Date();
		
		return Action.SUCCESS;
	}
	
	public String relatorioColaboradorCargo() throws Exception
	{
		if (!exibColabDesatualizado)
			qtdMesesDesatualizacao = null;
		
		historicoColaboradors = historicoColaboradorManager.relatorioColaboradorCargo(getEmpresaSistema(), dataHistorico, cargosCheck, estabelecimentosCheck, qtdMeses, opcaoFiltro, areaOrganizacionalsCheck, exibColabAdmitido, qtdMesesDesatualizacao);
		parametros = RelatorioUtil.getParametrosRelatorio("Colaboradores por Cargos", getEmpresaSistema(), "Quantidade de Colaboradores por Cargo em " + DateUtil.formataDiaMesAno(dataHistorico));
		parametros.put("EXIBIRSALARIO", exibirSalario);
		
		if(relatorioResumido)
			return "successResumido";
		else
			return Action.SUCCESS;
	}
	
	public String imprimir() throws Exception
	{
		if (getCargo().getId() == null)
			return INPUT;
		
		cargosCheck = new String[]{cargo.getId().toString()};
		
		return relatorioCargo();
	}

	public String relatorioCargo() throws Exception
	{
		try
		{
			cargos = cargoManager.getCargosByIds(LongUtil.arrayStringToArrayLong(cargosCheck), getEmpresaSistema().getId());
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Cargos", getEmpresaSistema(), null);
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
			e.printStackTrace();
			prepareRelatorioCargo();

			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String prepareTransferirFaixasCargo() throws Exception
	{
//		if (!getEmpresaSistema().isAcIntegra())
//		{
//			addActionMessage("O FortesRH não está integrado com o AC Pessoal.");
//			return Action.SUCCESS;
//		}
		
		cargo = cargoManager.findByIdProjection(cargo.getId());
		cargos = cargoManager.findAllSelect(getEmpresaSistema().getId(), "nome");
		cargos.remove(cargo);
		faixasDoCargo = faixaSalarialManager.findFaixaSalarialByCargo(cargo.getId());
		return Action.SUCCESS;
	}

	public String transferirFaixasCargo() throws Exception
	{
		try
		{
			faixa.setNome(novaFaixaNome);
			
			faixaSalarialManager.transfereFaixasCargo(faixa, cargo, getEmpresaSistema());
			
			addActionMessage("Faixa Salarial transferida com sucesso.");
			
			return prepareTransferirFaixasCargo();
		}
		catch (IntegraACException e)
		{
			addActionError(e.getMessage());
			prepareTransferirFaixasCargo();
			return Action.INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao transferir a faixa.");
			prepareTransferirFaixasCargo();
			return Action.INPUT;
		}
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, cargo.getAreasOrganizacionais(), "getId");
		areasFormacaoCheckList = CheckListBoxUtil.marcaCheckListBox(areasFormacaoCheckList, cargo.getAreaFormacaos(), "getId");
		etapaSeletivaCheckList = CheckListBoxUtil.marcaCheckListBox(etapaSeletivaCheckList, cargo.getEtapaSeletivas(), "getId");

		if (cargo.getAreasOrganizacionais() == null || cargo.getAreasOrganizacionais().size() == 0)
			conhecimentosCheckList = conhecimentoManager.populaCheckOrderNome(getEmpresaSistema().getId());
		else
			conhecimentosCheckList = conhecimentoManager.populaCheckOrderNomeByAreaOrganizacionals(new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(cargo.getAreasOrganizacionais()), getEmpresaSistema().getId());

		habilidadesCheckList = habilidadeManager.populaCheckOrderNome(getEmpresaSistema().getId());
		atitudesCheckList = atitudeManager.populaCheckOrderNome(getEmpresaSistema().getId());
		
		conhecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(conhecimentosCheckList, cargo.getConhecimentos(), "getId");
		habilidadesCheckList = CheckListBoxUtil.marcaCheckListBox(habilidadesCheckList, cargo.getHabilidades(), "getId");
		atitudesCheckList = CheckListBoxUtil.marcaCheckListBox(atitudesCheckList, cargo.getAtitudes(), "getId");
		descricaoCBO = codigoCBOManager.findDescricaoByCodigo(cargo.getCboCodigo());
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		if(cargoManager.verifyExistCargoNome(cargo.getNome(), getEmpresaSistema().getId()))
		{
			addActionError("Já existe um cargo com este nome: " + cargo.getNome());
			prepareInsert();
			return Action.INPUT;
		}

		cargo.setEmpresa(getEmpresaSistema());
		cargo.setAreasOrganizacionais(areaOrganizacionalManager.populaAreas(areasCheck));
		cargo.setAreaFormacaos(areaFormacaoManager.populaAreas(areasFormacaoCheck));
		cargo.setConhecimentos(conhecimentoManager.populaConhecimentos(conhecimentosCheck));
		cargo.setHabilidades(habilidadeManager.populaHabilidades(habilidadesCheck));
		cargo.setAtitudes(atitudeManager.populaAtitudes(atitudesCheck));
		cargo.setEtapaSeletivas(etapaSeletivaManager.populaEtapaseletiva(etapaCheck));

		if (cargo.getGrupoOcupacional().getId() == null || cargo.getGrupoOcupacional().getId() == -1)
			cargo.setGrupoOcupacional(null);

		cargoManager.save(cargo);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		Cargo cargoVerifica = cargoManager.findByIdAllProjection(cargo.getId());

		if(cargoManager.verifyExistCargoNome(cargo.getNome(), null) && !cargoVerifica.getNome().equalsIgnoreCase(cargo.getNome()))
		{
			addActionError("Já existe um cargo com este nome");
			prepareUpdate();
			return Action.INPUT;
		}

		cargo.setEmpresa(getEmpresaSistema());
		cargo.setAreasOrganizacionais(areaOrganizacionalManager.populaAreas(areasCheck));
		cargo.setAreaFormacaos(areaFormacaoManager.populaAreas(areasFormacaoCheck));
		cargo.setConhecimentos(conhecimentoManager.populaConhecimentos(conhecimentosCheck));
		cargo.setHabilidades(habilidadeManager.populaHabilidades(habilidadesCheck));
		cargo.setAtitudes(atitudeManager.populaAtitudes(atitudesCheck));
		cargo.setFaixaSalarials(faixaSalarialManager.findFaixaSalarialByCargo(cargo.getId()));
		cargo.setEtapaSeletivas(etapaSeletivaManager.populaEtapaseletiva(etapaCheck));

		if (cargo.getGrupoOcupacional().getId() == null || cargo.getGrupoOcupacional().getId() == -1)
			cargo.setGrupoOcupacional(null);

		try 
		{
			cargoManager.update(cargo);
			return Action.SUCCESS;
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Atualização não pôde ser realizada.");
			return Action.INPUT;
		}
	}

	public Cargo getCargo()
	{
		if (cargo == null)
		{
			cargo = new Cargo();
		}
		return cargo;
	}

	public void setCargo(Cargo cargo)
	{
		this.cargo = cargo;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public HashMap<String, String> getEscolaridades()
	{
		return escolaridades;
	}

	public void setEscolaridades(HashMap<String, String> escolaridades)
	{
		this.escolaridades = escolaridades;
	}

	public void setGrupoOcupacionalManager(GrupoOcupacionalManager grupoOcupacionalManager)
	{
		this.grupoOcupacionalManager = grupoOcupacionalManager;
	}

	public ConhecimentoManager getConhecimentoManager()
	{
		return conhecimentoManager;
	}

	public void setConhecimentoManager(ConhecimentoManager conhecimentoManager)
	{
		this.conhecimentoManager = conhecimentoManager;
	}

	public void setAreaFormacaoManager(AreaFormacaoManager areaFormacaoManager)
	{
		this.areaFormacaoManager = areaFormacaoManager;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public String getFiltro()
	{
		return filtro;
	}

	public void setFiltro(String filtro)
	{
		this.filtro = filtro;
	}

	public Collection<Cargo> getCargos()
	{
		return cargos;
	}

	public void setCargos(Collection<Cargo> cargos)
	{
		this.cargos = cargos;
	}

	public String[] getAreasCheck()
	{
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck)
	{
		this.areasCheck = areasCheck;
	}

	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	public void setAreasCheckList(Collection<CheckBox> areasCheckList)
	{
		this.areasCheckList = areasCheckList;
	}

	public String[] getAreasFormacaoCheck()
	{
		return areasFormacaoCheck;
	}

	public void setAreasFormacaoCheck(String[] areasFormacaoCheck)
	{
		this.areasFormacaoCheck = areasFormacaoCheck;
	}

	public Collection<CheckBox> getAreasFormacaoCheckList()
	{
		return areasFormacaoCheckList;
	}

	public void setAreasFormacaoCheckList(Collection<CheckBox> areasFormacaoCheckList)
	{
		this.areasFormacaoCheckList = areasFormacaoCheckList;
	}

	public String[] getConhecimentosCheck()
	{
		return conhecimentosCheck;
	}

	public void setConhecimentosCheck(String[] conhecimentosCheck)
	{
		this.conhecimentosCheck = conhecimentosCheck;
	}

	public Collection<CheckBox> getConhecimentosCheckList()
	{
		return conhecimentosCheckList;
	}

	public void setConhecimentosCheckList(Collection<CheckBox> conhecimentosCheckList)
	{
		this.conhecimentosCheckList = conhecimentosCheckList;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public String[] getCargosCheck()
	{
		return cargosCheck;
	}

	public void setCargosCheck(String[] cargosCheck)
	{
		this.cargosCheck = cargosCheck;
	}

	public Collection<CheckBox> getCargosCheckList()
	{
		return cargosCheckList;
	}

	public void setCargosCheckList(Collection<CheckBox> cargosCheckList)
	{
		this.cargosCheckList = cargosCheckList;
	}

	public String[] getGruposCheck()
	{
		return gruposCheck;
	}

	public void setGruposCheck(String[] gruposCheck)
	{
		this.gruposCheck = gruposCheck;
	}

	public Collection<CheckBox> getGruposCheckList()
	{
		return gruposCheckList;
	}

	public void setGruposCheckList(Collection<CheckBox> gruposCheckList)
	{
		this.gruposCheckList = gruposCheckList;
	}

	public Collection<GrupoOcupacional> getGrupoOcupacionals()
	{
		return grupoOcupacionals;
	}

	public void setGrupoOcupacionals(Collection<GrupoOcupacional> grupoOcupacionals)
	{
		this.grupoOcupacionals = grupoOcupacionals;
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public Collection<FaixaSalarial> getFaixasDoCargo()
	{
		return faixasDoCargo;
	}

	public void setFaixasDoCargo(Collection<FaixaSalarial> faixasDoCargo)
	{
		this.faixasDoCargo = faixasDoCargo;
	}

	public void setFaixa(FaixaSalarial faixa)
	{
		this.faixa = faixa;
	}

	public void setNovaFaixaNome(String novaFaixaNome)
	{
		this.novaFaixaNome = novaFaixaNome;
	}

	public boolean isIntegraAC() {
		return integraAC;
	}

	public void setIntegraAC(boolean integraAC) {
		this.integraAC = integraAC;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck)
	{
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Date getData()
	{
		return dataHistorico;
	}

	public void setData(Date data)
	{
		this.dataHistorico = data;
	}

	public boolean isRelatorioResumido()
	{
		return relatorioResumido;
	}

	public void setRelatorioResumido(boolean relatorioResumido)
	{
		this.relatorioResumido = relatorioResumido;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public Collection<HistoricoColaborador> getHistoricoColaboradors()
	{
		return historicoColaboradors;
	}

	public char getOpcaoFiltro() {
		return opcaoFiltro;
	}

	public void setOpcaoFiltro(char opcaoFiltro) {
		this.opcaoFiltro = opcaoFiltro;
	}

	public Integer getQtdMeses() {
		return qtdMeses;
	}

	public void setQtdMeses(Integer qtdMeses) {
		this.qtdMeses = qtdMeses;
	}

	public Collection<CheckBox> getEtapaSeletivaCheckList() {
		return etapaSeletivaCheckList;
	}

	public void setEtapaSeletivaCheckList(
			Collection<CheckBox> etapaSeletivaCheckList) {
		this.etapaSeletivaCheckList = etapaSeletivaCheckList;
	}

	public String[] getEtapaCheck() {
		return etapaCheck;
	}

	public void setEtapaCheck(String[] etapaCheck) {
		this.etapaCheck = etapaCheck;
	}

	public void setEtapaSeletivaManager(EtapaSeletivaManager etapaSeletivaManager) {
		this.etapaSeletivaManager = etapaSeletivaManager;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public Collection<Empresa> getEmpresas()
	{
		return empresas;
	}

	public Long[] getEmpresaIds()
	{
		return empresaIds;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	
	public String[] getAreaOrganizacionalsCheck() 
	{
		return areaOrganizacionalsCheck;
	}

	public Collection<CheckBox> getAreaOrganizacionalsCheckList() 
	{
		return areaOrganizacionalsCheckList;
	}

	public void setAreaOrganizacionalsCheck(String[] areaOrganizacionalsCheck) 
	{
		this.areaOrganizacionalsCheck = areaOrganizacionalsCheck;
	}

	public void setAreaOrganizacionalsCheckList(Collection<CheckBox> areaOrganizacionalsCheckList) 
	{
		this.areaOrganizacionalsCheckList = areaOrganizacionalsCheckList;
	}
	
	public boolean isExibirSalario() 
	{
		return exibirSalario;
	}

	public void setExibirSalario(boolean exibirSalario) 
	{
		this.exibirSalario = exibirSalario;
	}

	public char getOpcaoFiltroAdmitidosOuDEsatualizados() {
		return opcaoFiltroAdmitidosOuDEsatualizados;
	}

	public void setOpcaoFiltroAdmitidosOuDEsatualizados(char opcaoFiltroAdmitidosOuDEsatualizados) {
		this.opcaoFiltroAdmitidosOuDEsatualizados = opcaoFiltroAdmitidosOuDEsatualizados;
	}

	public boolean isExibColabAdmitido() {
		return exibColabAdmitido;
	}

	public boolean isExibColabDesatualizado() {
		return exibColabDesatualizado;
	}

	public void setExibColabAdmitido(boolean exibColabAdmitido) {
		this.exibColabAdmitido = exibColabAdmitido;
	}

	public void setExibColabDesatualizado(Boolean exibColabDesatualizado) {
		this.exibColabDesatualizado = exibColabDesatualizado;
	}

	public Integer getQtdMesesDesatualizacao() {
		return qtdMesesDesatualizacao;
	}

	public void setQtdMesesDesatualizacao(Integer qtdMesesDesatualizacao) {
		this.qtdMesesDesatualizacao = qtdMesesDesatualizacao;
	}

	public String[] getHabilidadesCheck() {
		return habilidadesCheck;
	}

	public void setHabilidadesCheck(String[] habilidadesCheck) {
		this.habilidadesCheck = habilidadesCheck;
	}

	public Collection<CheckBox> getHabilidadesCheckList() {
		return habilidadesCheckList;
	}

	public void setHabilidadesCheckList(Collection<CheckBox> habilidadesCheckList) {
		this.habilidadesCheckList = habilidadesCheckList;
	}

	public String[] getAtitudesCheck() {
		return atitudesCheck;
	}

	public void setAtitudesCheck(String[] atitudesCheck) {
		this.atitudesCheck = atitudesCheck;
	}

	public Collection<CheckBox> getAtitudesCheckList() {
		return atitudesCheckList;
	}

	public void setAtitudesCheckList(Collection<CheckBox> atitudesCheckList) {
		this.atitudesCheckList = atitudesCheckList;
	}

	public void setAtitudeManager(AtitudeManager atitudeManager) {
		this.atitudeManager = atitudeManager;
	}

	public HabilidadeManager getHabilidadeManager() {
		return habilidadeManager;
	}

	public void setHabilidadeManager(HabilidadeManager habilidadeManager) {
		this.habilidadeManager = habilidadeManager;
	}

	public AtitudeManager getAtitudeManager() {
		return atitudeManager;
	}

	public String getDescricaoCBO() {
		return descricaoCBO;
	}

	public void setCodigoCBOManager(CodigoCBOManager codigoCBOManager) {
		this.codigoCBOManager = codigoCBOManager;
	}

}