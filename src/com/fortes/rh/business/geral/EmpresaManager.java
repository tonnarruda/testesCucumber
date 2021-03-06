/* Autor: Bruno Bachiega
 * Data: 6/06/2006
 * Requisito: RFA0026 */
package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.List;

import com.fortes.business.GenericManager;
import com.fortes.model.type.File;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TEmpresa;
import com.fortes.rh.security.spring.aop.callback.EmpresaAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;
import com.fortes.web.tags.CheckBox;

public interface EmpresaManager extends GenericManager<Empresa>
{
	String[] getEmpresasByUsuarioEmpresa(Long usuarioId);
	Collection<UsuarioEmpresa> getPerfilEmpresasByUsuario(Long usuarioId);
	Empresa findByCodigoAC(String codigoAc, String grupoAC);
	String saveLogo(File logo, String local) throws Exception;
	Empresa setLogo(Empresa empresa, File logo, String local, File logoCertificado) throws Exception;
	boolean findIntegracaoAC(Long id);
	boolean criarEmpresa(TEmpresa empresa);
	boolean verifyExistsCnpj(Long id, String cnpj);
	String findCidade(Long id);
	Collection<Empresa> findDistinctEmpresasByQuestionario(Long questionarioId);
	Collection<CheckBox> populaCadastrosCheckBox();
	List<String> sincronizaEntidades(Long empresaOrigemId, Long empresaDestinoId, String[] cadastrosCheck, String[] tipoOcorrenciasCheck) throws Exception;
	Empresa findByIdProjection(Long id);
	Collection<Empresa> findByUsuarioPermissao(Long usuarioId, String... roles);
	Long[] selecionaEmpresa(Empresa empresa, Long usuarioId, String role);
	@Audita(operacao="Remoção", auditor=EmpresaAuditorCallbackImpl.class)
	void removeEmpresa(Empresa empresa);
	Collection<Empresa> findEmailsEmpresa();
	public Empresa findEmailsEmpresa(Long empresaId);
	Long ajustaCombo(Long empresaId, Long empresaDoSistemaId);
	void atualizaCamposExtras(Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras, Empresa empresa, boolean habilitaCampoExtraColaborador, boolean habilitaCampoExtraCandidato, boolean habilitaCampoExtraAtualizarMeusDados);
	Collection<Empresa> findTodasEmpresas();
	boolean checkEmpresaCodACGrupoAC(Empresa empresa);
	Collection<Empresa> findEmpresasPermitidas(Boolean compartilharCandidatos, Long empresId, Long usuarioId, String... roles);
	String getEmpresasNaoListadas(Collection<UsuarioEmpresa> usuarioEmpresas, Collection<Empresa> empresas);
	boolean checkEmpresaIntegradaAc();
	boolean checkEmpresaIntegradaAc(Long empresaId);
	Collection<Empresa> findComCodigoAC();
	boolean verificaInconcistenciaIntegracaoAC(Empresa empresa);
	Collection<String> verificaIntegracaoAC(Empresa empresa);
	@Audita(operacao="Integração com AC", auditor=EmpresaAuditorCallbackImpl.class)
	void auditaIntegracao(Empresa empresa, boolean tavaIntegradaComAC);
	boolean isControlaRiscoPorAmbiente(Long empresaId);
	Collection<Empresa> findEmpresasIntegradas() ;
	Empresa getCnae(Long empresaId);
	void updateCodigoGrupoAC(Long empresaId, String codigoAC, String grupoAC);
	Collection<Empresa> findByGruposAC(String... gruposAC);
	String getCodigoGrupoAC(Long empresaId);
	boolean emProcessoExportacaoAC(Long empresaId);
	void setProcessoExportacaoAC(Long empresaId, boolean processoExportacaoAC);
	void enviaEmailInformandoDesintegracao(Empresa empresa, boolean tavaIntegradaComAC, String motivo, String usuario);
	@Audita(operacao="Remoção", auditor=EmpresaAuditorCallbackImpl.class)
	void remove(Empresa empresa);
	Collection<Empresa> findByLntId(Long lntId);
	public Collection<Empresa> findEmpresasComCodigoACAndAtualizouDddCelularAndUFHabilitacao();
	public void setDddCelularAndUFHabilitacaoAtualizados(Long empresaId);
	Collection<Empresa> findDistinctEmpresasByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, Long... empresasIds);
}