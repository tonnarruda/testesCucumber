/* Autor: Igo Coelho
 * Data: 29/05/2006
 * Requisito: RFA32 */
package com.fortes.rh.model.acesso;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Colaborador;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "sequence", sequenceName = "usuario_sequence", allocationSize = 1)
public class Usuario extends AbstractModel implements Serializable, Cloneable
{
	@Column(length=100)
	private String nome;
	
	@Column(length=30)
	private String login;
	
	@Column(length=30)
	private String senha;
	
	private boolean acessoSistema;
	
	private boolean superAdmin;
	
	@Column(length=255)
	private String caixasMensagens = "{'caixasDireita':['T','C','F','U'],'caixasEsquerda':['P','R','A','S'],'caixasMinimizadas':[]}";

	@OneToMany(mappedBy="usuario")
	private Collection<UsuarioEmpresa> usuarioEmpresas;
	
	@Transient
	private String novaSenha = new String();//tem que ser inicializado, Francisco Barroso.
	@Transient
	private String confNovaSenha = new String();//tem que ser inicializado, Francisco Barroso.
	
	@Transient
	private boolean usuarioFortes;

	@Transient
	private Colaborador colaborador;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ultimoLogin;

	public Usuario()
	{

	}

	public Usuario(Long id, String nome, String login, String senha, Date ultimoLogin, boolean acessoSistema, boolean superAdmin, Long idColaborador, String nomeColaborador, String nomeComercialDoColaborador)
	{
		this.setId(id);
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.ultimoLogin = ultimoLogin;
		this.acessoSistema = acessoSistema;
		this.superAdmin = superAdmin;
		this.colaborador = new Colaborador();
		this.colaborador.setId(idColaborador);
		this.colaborador.setNome(nomeColaborador);
		this.colaborador.setNomeComercial(nomeComercialDoColaborador);
	}

	public boolean confirmarSenha()
	{
		if(senha == null || confNovaSenha == null)
			return false;
		
		return senha.equals(confNovaSenha);
	}

	public boolean isAcessoSistema()
	{
		return acessoSistema;
	}

	public void setAcessoSistema(boolean acessoSistema)
	{
		this.acessoSistema = acessoSistema;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getSenha()
	{
		return senha;
	}

	public void setSenha(String senha)
	{
		this.senha = senha;
	}

	public String getNome()
	{
		return nome;
	}

	public String getNomeFormatado()
	{
		if(nome == null)
			return "Usuário não Identificado";
		
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getNovaSenha()
	{
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha)
	{
		this.novaSenha = novaSenha;
	}

	public String getConfNovaSenha()
	{
		return confNovaSenha;
	}

	public void setConfNovaSenha(String confNovaSenha)
	{
		this.confNovaSenha = confNovaSenha;
	}

	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error("This should not occur since we implement Cloneable");
		}
	}

	@Override
	public String toString()
	{
		return "";
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Collection<UsuarioEmpresa> getUsuarioEmpresas() {
		return usuarioEmpresas;
	}

	public void setUsuarioEmpresas(Collection<UsuarioEmpresa> usuarioEmpresas) {
		this.usuarioEmpresas = usuarioEmpresas;
	}

	public Date getUltimoLogin() {
		return ultimoLogin;
	}

	public void setUltimoLogin(Date ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public String getCaixasMensagens() {
		return caixasMensagens;
	}

	public void setCaixasMensagens(String caixasMensagens) {
		this.caixasMensagens = caixasMensagens;
	}

	public boolean isUsuarioFortes() {
		return getId() == 1;
	}
}