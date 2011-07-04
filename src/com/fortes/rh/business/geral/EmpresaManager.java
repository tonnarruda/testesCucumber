/* Autor: Bruno Bachiega
 * Data: 6/06/2006
 * Requisito: RFA0026 */
package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.model.type.File;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TEmpresa;
import com.fortes.web.tags.CheckBox;

public interface EmpresaManager extends GenericManager<Empresa>
{
	String[] getEmpresasByUsuarioEmpresa(Long usuarioId);
	Collection<UsuarioEmpresa> getPerfilEmpresasByUsuario(Long usuarioId);
	Empresa findByCodigoAC(String codigo, String grupoAC);
	String saveLogo(File logo, String local) throws Exception;
	Empresa setLogo(Empresa empresa, File logo, String local, File logoCertificado) throws Exception;
	boolean findIntegracaoAC(Long id);
	boolean findExibirSalarioById(Long empresaId);
	boolean criarEmpresa(TEmpresa empresa);
	boolean verifyExistsCnpj(Long id, String cnpj);
	String findCidade(Long id);
	Collection<Empresa> findDistinctEmpresasByQuestionario(Long questionarioId);
	Collection<CheckBox> populaCadastrosCheckBox();
	void sincronizaEntidades(Long empresaOrigemId, Long empresaDestinoId, String[] cadastrosCheck) throws Exception;
	Empresa findByIdProjection(Long id);
	Collection<Empresa> findByUsuarioPermissao(Long usuarioId, String... roles);
	Long[] selecionaEmpresa(Empresa empresa, Long usuarioId, String role);
	void removeEmpresaPadrao(long id);
	Collection<Empresa> findEmailsEmpresa();
	Long ajustaCombo(Long empresaId, Long empresaDoSistemaId);
	void atualizaCamposExtras(Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras, Empresa empresa, boolean habilitaCampoExtraColaborador, boolean habilitaCampoExtraCandidato);
	Collection<Empresa> findTodasEmpresas();
	boolean checkEmpresaCodACGrupoAC(Empresa empresa);
	Collection<Empresa> findEmpresasPermitidas(Boolean compartilharCandidatos, Long empresId, Long usuarioId, String... roles);
	String getEmpresasNaoListadas(Collection<UsuarioEmpresa> usuarioEmpresas, Collection<Empresa> empresas);
}