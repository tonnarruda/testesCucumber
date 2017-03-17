package com.fortes.rh.web.action.geral;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class GerenciadorComunicacaoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	@Autowired private UsuarioManager usuarioManager;
	@Autowired private AreaOrganizacionalManager areaOrganizacionalManager;
	@Autowired private EstabelecimentoManager estabelecimentoManager;
	@Autowired private ParametrosDoSistemaManager parametrosDoSistemaManager;
	
	private GerenciadorComunicacao gerenciadorComunicacao;
	private Collection<GerenciadorComunicacao> gerenciadorComunicacaos;
	private TreeMap<Integer, String> meioComunicacoes;
	private TreeMap<Integer, String> enviarParas;
	
	private String[] usuariosCheck;
	private Collection<CheckBox> usuariosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();

	private void prepare() throws Exception
	{
		Collection<Usuario> usuarios = usuarioManager.findAllSelect(getEmpresaSistema().getId());
		usuariosCheckList = CheckListBoxUtil.populaCheckListBox(usuarios, "getId", "getNome", null);

		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox((getEmpresaSistema().getId()));
		
		if(gerenciadorComunicacao != null )
		{
			if (gerenciadorComunicacao.getId() != null)
			{
				gerenciadorComunicacao = (GerenciadorComunicacao) gerenciadorComunicacaoManager.findById(gerenciadorComunicacao.getId());
				usuariosCheckList = CheckListBoxUtil.marcaCheckListBox(usuariosCheckList, gerenciadorComunicacao.getUsuarios(), "getId");
			}

			meioComunicacoes = Operacao.getMeioComunicacaosById(gerenciadorComunicacao.getOperacao());
			enviarParas = (MeioComunicacao.getMeioComunicacaoById(gerenciadorComunicacao.getMeioComunicacao())).getListEnviarPara();
			
		}else
		{
			meioComunicacoes = new TreeMap<Integer, String>();
			enviarParas = new TreeMap<Integer, String>();
		}
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		
		setShowFilter(false);
		
		return Action.SUCCESS;
	}

	public boolean isShowFilter() {
		return getShowFilter();
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		gerenciadorComunicacao.setEmpresa(getEmpresaSistema());
		
		if (gerenciadorComunicacaoManager.verifyExists(gerenciadorComunicacao)){
			addActionMessage("Já existe uma configuração cadastrada com os dados informados.");
			prepareInsert();
			return Action.INPUT;
		}
		
		carregaChecksUsuarios();
		gerenciadorComunicacaoManager.save(gerenciadorComunicacao);
		addActionSuccess("Configuração cadastrada com sucesso.");

		return Action.SUCCESS;
	}

	private void carregaChecksUsuarios()
	{
		if (usuariosCheck != null && usuariosCheck.length > 0)
		{
			Collection<Usuario> usuariosTmp = new ArrayList<Usuario>();
			for (int i = 0; i < usuariosCheck.length; i++)
			{
				Long usuarioId = Long.valueOf(usuariosCheck[i]);
				Usuario usu = new Usuario();
				usu.setId(usuarioId);
				usuariosTmp.add(usu);
			}
			gerenciadorComunicacao.setUsuarios(usuariosTmp);
		}
	}
	
	public String update() throws Exception
	{
		if (gerenciadorComunicacaoManager.verifyExists(gerenciadorComunicacao)){
			addActionWarning("Já existe uma configuração cadastrada com os dados informados.");
			prepareUpdate();
			return Action.INPUT;
		}

		carregaChecksUsuarios();
		gerenciadorComunicacaoManager.update(gerenciadorComunicacao);
		addActionSuccess("Configuração atualizada com sucesso.");
		
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		gerenciadorComunicacaos = gerenciadorComunicacaoManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{});
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			gerenciadorComunicacaoManager.remove(gerenciadorComunicacao.getId());
			addActionSuccess("Configuração excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta configuração.");
		}

		return list();
	}
	
	// Utilizado no ftl
	public ArrayList<Integer> getOperacoesConfiguradasComLembrete()
	{
		ArrayList<Integer> operacoesIds = new ArrayList<Integer>();
		operacoesIds.add(Operacao.COLABORADORES_COM_ANO_DE_EMPRESA.getId());
		operacoesIds.add(Operacao.PESQUISA_NAO_LIBERADA.getId());
		operacoesIds.add(Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId());
		operacoesIds.add(Operacao.NAO_ABERTURA_SOLICITACAO_EPI.getId());
		operacoesIds.add(Operacao.NAO_ENTREGA_SOLICITACAO_EPI.getId());
		operacoesIds.add(Operacao.TERMINO_CONTRATO_COLABORADOR.getId());
		operacoesIds.add(Operacao.HABILITACAO_A_VENCER.getId());
		operacoesIds.add(Operacao.CURSOS_A_VENCER.getId());
		operacoesIds.add(Operacao.CERTIFICACOES_A_VENCER.getId());
		operacoesIds.add(Operacao.ENCERRAR_PERIODO_LNT.getId());
		
		return operacoesIds;
	}
	
	// Utilizado no ftl
	public ArrayList<Integer> getOperacoesComAvisoAposPrazo()
	{
		ArrayList<Integer> operacoesIds = new ArrayList<Integer>();
		operacoesIds.add(Operacao.NAO_ABERTURA_SOLICITACAO_EPI.getId());
		operacoesIds.add(Operacao.NAO_ENTREGA_SOLICITACAO_EPI.getId());
		
		return operacoesIds;
	}
	
	// Utilizado no ftl
	public int getEmailQuandoColaboradorCompletaAnoDeEmpresaId(){
		return Operacao.COLABORADORES_COM_ANO_DE_EMPRESA.getId();
	}
	
	public GerenciadorComunicacao getGerenciadorComunicacao()
	{
		if(gerenciadorComunicacao == null)
			gerenciadorComunicacao = new GerenciadorComunicacao();
		return gerenciadorComunicacao;
	}

	public void setGerenciadorComunicacao(GerenciadorComunicacao gerenciadorComunicacao)
	{
		this.gerenciadorComunicacao = gerenciadorComunicacao;
	}
	
	public Collection<GerenciadorComunicacao> getGerenciadorComunicacaos()
	{
		return gerenciadorComunicacaos;
	}

	public Map<String, Collection<Operacao>> getOperacoes()
	{
		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		return Operacao.getHashMapGrupos(parametrosDoSistema);
	}
	
	public TreeMap<Integer, String> getEnviarParas() {
		return enviarParas;
	}

	public TreeMap<Integer, String> getMeioComunicacoes() {
		return meioComunicacoes;
	}

	public String[] getUsuariosCheck() {
		return usuariosCheck;
	}

	public void setUsuariosCheck(String[] usuariosCheck) {
		this.usuariosCheck = usuariosCheck;
	}

	public Collection<CheckBox> getUsuariosCheckList() {
		return usuariosCheckList;
	}

	public void setUsuariosCheckList(Collection<CheckBox> usuariosCheckList) {
		this.usuariosCheckList = usuariosCheckList;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}
	
	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}
}