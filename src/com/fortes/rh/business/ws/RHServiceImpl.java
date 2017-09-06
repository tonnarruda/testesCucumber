package com.fortes.rh.business.ws;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.CodigoCBOManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.business.pesquisa.PesquisaManager;
import com.fortes.rh.business.security.AuditoriaManager;
import com.fortes.rh.business.security.TokenManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.exception.TokenException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.dicionario.MovimentacaoAC;
import com.fortes.rh.model.dicionario.OrigemCandidato;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.SocioEconomica;
import com.fortes.rh.model.ws.FeedbackWebService;
import com.fortes.rh.model.ws.TAreaOrganizacional;
import com.fortes.rh.model.ws.TAuditoria;
import com.fortes.rh.model.ws.TAula;
import com.fortes.rh.model.ws.TCandidato;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.model.ws.TCidade;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TEmpresa;
import com.fortes.rh.model.ws.TEstabelecimento;
import com.fortes.rh.model.ws.TFeedbackPessoalWebService;
import com.fortes.rh.model.ws.TGrupo;
import com.fortes.rh.model.ws.TIndice;
import com.fortes.rh.model.ws.TIndiceHistorico;
import com.fortes.rh.model.ws.TOcorrencia;
import com.fortes.rh.model.ws.TOcorrenciaEmpregado;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.model.ws.TSituacaoCargo;
import com.fortes.rh.model.ws.Token;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;

public class RHServiceImpl implements RHService
{
	private ColaboradorManager colaboradorManager;
	private EstabelecimentoManager estabelecimentoManager;
	private EmpresaManager empresaManager;
	private CidadeManager cidadeManager;
	private IndiceManager indiceManager;
	private IndiceHistoricoManager indiceHistoricoManager;
	private FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private UsuarioMensagemManager usuarioMensagemManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;
	private MensagemManager mensagemManager;
	private CargoManager cargoManager;
	private CandidatoManager candidatoManager;
	private OcorrenciaManager ocorrenciaManager;
	private ColaboradorOcorrenciaManager colaboradorOcorrenciaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private FaixaSalarialManager faixaSalarialManager;
	private GrupoACManager grupoACManager;
	private UsuarioManager usuarioManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private PlatformTransactionManager transactionManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private PesquisaManager pesquisaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private AuditoriaManager auditoriaManager;
	private TokenManager tokenManager;
	private CodigoCBOManager codigoCBOManager;

	private final String MSG_ERRO_REMOVER_SITUACAO_LOTE = "Não é possível excluir situação dos empregados, existem outros cadastros utilizando essa situação.";
	private final String MSG_ERRO_REMOVER_SITUACAO = "Não é possível excluir situação do empregado, existem outros cadastros utilizando essa situação.";
	private final String MSG_ERRO_REMOVER_LOTACAO = "Não é possível excluir lotação, existem cadastros utilizando essa lotação.";
	private final String MSG_ERRO_REMOVER_ESTABELECIMENTO = "Não é possível excluir estabelecimento, existem outros cadastros utilizando esse estabelecimento.";
	private final String MSG_ERRO_REMOVER_INDICE = "Não é possível excluir índice, existem outros cadastros utilizando esse índice.";
	private final String MSG_ERRO_REMOVER_INDICE_HISTORICO = "Não é possível excluir histórico desse índice, existem outros cadastros utilizando esse histórico.";
	private final String MSG_ERRO_REMOVER_OCORRENCIA = "Não é possível excluir ocorrência, existem outros cadastros utilizando essa ocorrência.";
	private final String MSG_ERRO_REMOVER_OCORRENCIA_EMPREGADO = "Não é possível excluir ocorrência do empregado, existem outros cadastros utilizando essa ocorrência.";
	private final String MSG_ERRO_REMOVER_SITUACAO_CARGO = "Não é possível excluir situação do cargo, existem outros cadastros utilizando essa situação.";
	private final String MSG_ERRO_REMOVER_CARGO = "Não é possível excluir cargo, existem outros cadastros utilizando esse cargo.";
	private final String MSG_ERRO_REMOVER_EMPREGADO = "Não é possível excluir empregado, existem outros cadastros utilizando esse empregado.";
	
	private static boolean realizandoReenvioPendencias = false;
	
	private boolean realizandoOperacaoEmLote = false;
	
	private String formataException(String parametros, Exception e) 
	{
		String msg = DateUtil.formataDiaMesAnoTime(new Date()) + "\n";
		
		if(parametros != null)
			msg += parametros + "\n\n";
		
		if(e != null)
			msg += e.toString() + "\n\n" + e.getCause() + "\n\n" + e.getMessage();
		
		return msg;
	}
	
	public String getToken(String login, String senha) {
		Usuario usuario = usuarioManager.findByLogin(login);
		if (usuario != null && usuario.getSenha().equals(StringUtil.encodeString(senha))) {
			String token = StringUtil.encodeString(login + senha + new Date().getTime());
			token = DigestUtils.md5Hex(token.getBytes());
			
			Token t = new Token(token);
			tokenManager.save(t);
			return token;
		} else {
			return "";
		}
	}
	
	public String eco(String texto)
	{
		return texto;
	}

	public TEmpresa[] getEmpresas(String token) throws TokenException{
		verifyToken(token, true);
		Collection<Empresa> empresas = empresaManager.findToList(new String[]{"id","nome","razaoSocial","codigoAC","grupoAC"}, new String[]{"id","nome","razaoSocial","codigoAC","grupoAC"}, new String[]{"nome"});
		return empresasToArray(empresas);
	}

	private TEmpresa[] empresasToArray(Collection<Empresa> empresas)
	{
		TEmpresa[] resultado = new TEmpresa[empresas.size()];
		int pos = 0;

		for (Empresa empresa : empresas){
			TEmpresa emp = new TEmpresa(empresa.getId(), empresa.getNome(), empresa.getRazaoSocial(), empresa.getCodigoAC(), empresa.getGrupoAC());
			resultado[pos++] = emp;
		}

		return resultado;
	}

	public TCidade[] getCidades(String token, String uf) throws TokenException
	{
		verifyToken(token, true);
		Collection<Cidade> cidades = cidadeManager.findAllByUf(uf);
		return cidadesToArray(cidades);
	}

	private TCidade[] cidadesToArray(Collection<Cidade> cidades)
	{
		TCidade[] resultado = new TCidade[cidades.size()];
		int pos = 0;

		for (Cidade cidade : cidades){
			TCidade cid = new TCidade(cidade.getId(), cidade.getNome());
			resultado[pos++] = cid;
		}

		return resultado;
	}

	public TCargo[] getCargos(String token, Long empresaId) throws TokenException
	{
		verifyToken(token, true);
		Collection<Cargo> cargos = cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, empresaId);
		return cargosToArray(cargos, true);
	}

	public TCargo[] getCargosAC(String token, String empCodigo, String codigo, String grupoAC) throws TokenException 
	{
		verifyToken(token, true);
		Collection<Cargo> cargos = cargoManager.findByEmpresaAC(empCodigo, codigo, grupoAC);
		return cargosToArray(cargos, false);
	}

	private TCargo[] cargosToArray(Collection<Cargo> cargos, boolean setaDescricao)
	{
		TCargo[] resultado = new TCargo[cargos.size()];
		int pos = 0;

		for (Cargo cargo : cargos){
			TCargo car = null;
			if(setaDescricao)
				car = new TCargo(cargo.getId(), "", cargo.getNome());
			else
				car = new TCargo(cargo.getId(), cargo.getNome());
				
			resultado[pos++] = car;
		}

		return resultado;
	}
	
	public TCargo[] getFaixas(String token) throws TokenException 
	{
		verifyToken(token, true);
		return faixaSalarialManager.getFaixasAC();
	}

	public String getNomesHomologos(String token, String nomeCandidato) throws TokenException{
		verifyToken(token, true);
		Collection<Candidato> candidatos = candidatoManager.getCandidatosByNome(nomeCandidato);
		String nomeHomonimos = "";
		
		for (Candidato candidato : candidatos){
			nomeHomonimos += candidato.getNome();
			
			if (!candidato.getPessoal().getCpfFormatado().equals("") )
				nomeHomonimos += " (CPF "  + candidato.getPessoal().getCpfFormatado() +   ")";
			
			nomeHomonimos += " - " + candidato.getEmpresa().getNome()  + "\n";
				
		}
		
		return nomeHomonimos;
	}

	public boolean cadastrarCandidato(TCandidato cand) throws Exception{
		prepareDataNascimento(cand);
		Candidato candidato = new Candidato();
		
		Empresa empresa = new Empresa();
		empresa.setId(cand.getEmpresaId());
		candidato.setEmpresa(empresa);

		candidato.setNome(cand.getNome());
		candidato.setPessoal(new Pessoal());
		candidato.getPessoal().setDataNascimento(cand.getNascimento());
		candidato.getPessoal().setSexo(cand.getSexo().charAt(0));
		candidato.getPessoal().setCpf(StringUtil.removeMascara(cand.getCpf()));
		candidato.getPessoal().setIndicadoPor(cand.getIndicadoPor());
		candidato.setColocacao(cand.getColocacao());
		candidato.setEndereco(new Endereco());
		candidato.getEndereco().setBairro(cand.getBairro());

		if (cand.getCidadeId() != null){
			candidato.getEndereco().setCidade(cidadeManager.findById(cand.getCidadeId()));
			candidato.getEndereco().setUf(candidato.getEndereco().getCidade().getUf());
		}else{
			candidato.getEndereco().setCidade(null);
			candidato.getEndereco().setUf(null);
		}

		candidato.setCargos(cargoManager.populaCargos(cand.getCargos()));
		if(cand.getCurriculo() != null)
			candidato.setOcrTexto(ArquivoUtil.convertToLatin1Compatible(cand.getCurriculo().getBytes()));
		
		candidato.setBlackList(false);
		candidato.setContratado(false);
		candidato.setDisponivel(true);
		candidato.setDataCadastro(new Date());
		candidato.setDataAtualizacao(new Date());
		candidato.setOrigem(OrigemCandidato.OFFICE);
		candidato.setSocioEconomica(new SocioEconomica());
		candidato.getSocioEconomica().setPagaPensao(false);

		candidatoManager.save(candidato);

		return true;
	}

	// Se a data for de 1890, é setada como nula
	private void prepareDataNascimento(TCandidato cand) throws Exception
	{
		if (cand.getNascimento() != null)
		{
			Calendar data = Calendar.getInstance();
			data.setTime(cand.getNascimento());

			if (data.get(Calendar.YEAR) == 1890)
				cand.setNascimento(null);
		}
	}

	public FeedbackWebService desligarEmpregado(String token, String codigoColaborador, String codigoEmpresa, String dataDesligamento, String grupoAC){
		String parametros = "codigo: " + codigoColaborador + " \nempCodigo:" + codigoEmpresa + " \ndataDesligamento: " + dataDesligamento;
		try	{
			verifyToken(token, true);
			Empresa empresa = empresaManager.findByCodigoAC(codigoEmpresa, grupoAC);
			if(colaboradorManager.desligaColaboradorAC(empresa, DateUtil.montaDataByString(dataDesligamento), codigoColaborador)){	
				try{
					gerenciadorComunicacaoManager.enviaAvisoDesligamentoColaboradorAC(codigoEmpresa, grupoAC, empresa, codigoColaborador);
				} catch (Exception e) {
					System.out.println("Erro no envio de email de desligamento:\n"  + e.getMessage());
				}
				
				return new FeedbackWebService(true);
			}else
				return new FeedbackWebService(false, "Erro: Empregado não encontrado no RH", formataException(parametros, null));
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao desligar empregado.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService desligarEmpregadosEmLote(String token, String[] codigosACColaboradores, String codigoACEmpresa, String dataDesligamento, String grupoAC){
		String parametros = "codigo: " + codigosACColaboradores + " \nempCodigo:" + codigoACEmpresa + " \ndataDesligamento: " + dataDesligamento;
		try{
			if (!realizandoOperacaoEmLote)
				verifyToken(token, true);
			Empresa empresa = empresaManager.findByCodigoAC(codigoACEmpresa, grupoAC);
			if(colaboradorManager.desligaColaboradorAC(empresa, DateUtil.montaDataByString(dataDesligamento), codigosACColaboradores))
			{				
				try {
					gerenciadorComunicacaoManager.enviaAvisoDesligamentoColaboradorAC(codigoACEmpresa, grupoAC, empresa, codigosACColaboradores);
				} catch (Exception e) {
					System.out.println("Erro no envio de email de desligamento:\n"  + e.getMessage());
				}
				
				return new FeedbackWebService(true);
			}
			else
				return new FeedbackWebService(false, "Existem empregados que não foram encontrados no sistema RH", formataException(parametros, null));
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (FortesException e){
			e.printStackTrace();
			return new FeedbackWebService(false, e.getMessage(), formataException(parametros, e));
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao desligar empregado em lote.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService religarEmpregado(String token, String codigo, String empCodigo, String grupoAC)
	{
		String parametros = "codigo: " + codigo + " \nempCodigo: " + empCodigo + " \ngrupoAC: " + grupoAC;
		try{
			verifyToken(token, true);
			Long colaboradorId = colaboradorManager.religaColaboradorAC(codigo, empCodigo, grupoAC);
			usuarioManager.reativaAcessoSistema(colaboradorId);
			
			return new FeedbackWebService(true);
		}
		catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}
		catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao religar empregado.", formataException(parametros, e));
		}
	}

	public FeedbackWebService atualizarCodigoEmpregado(String token, String grupoAC, String empresaCodigo, String codigo, String codigoNovo) 
	{
		String parametros = "empCodigo: " + empresaCodigo + "\ngrupoAC: " + grupoAC + "\ncodigo empregado: " +codigo+ "\nnovo codigo empregado: " + codigoNovo;
		try {
			verifyToken(token, true);
			Colaborador colaborador = colaboradorManager.findByCodigoAC(codigo, empresaCodigo, grupoAC);
			if(colaborador == null || colaborador.getId() == null)
				return new FeedbackWebService(false, "Erro ao alterar código do empregado.", "Colaborador não encontrado no RH.\n" + parametros);
			
			Empresa empresa = empresaManager.findByCodigoAC(empresaCodigo, grupoAC);
			colaboradorManager.setCodigoColaboradorAC(codigoNovo, colaborador.getId(), empresa);
			
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e) {
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao alterar código do empregado.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService transferir(String token, TEmpresa tEmpresaOrigin, TEmpresa tEmpresaDestino, TEmpregado[] tEmpregados, TSituacao[] tSituacoes, String dataDesligamento){
		try {
			realizandoOperacaoEmLote = true;
			verifyToken(token, true);
			Empresa empresaOrigin = null;
			Empresa empresaDestino= null;
			
			if(tEmpregados.length == 0)
				return new FeedbackWebService(false, "Não existem empregados a serem transferidos", null);
				
			if(tEmpregados.length != tSituacoes.length)
				return new FeedbackWebService(false, "Existe uma inconsistência entre a quantidade de empregados e situações.", null);
	
			if(tEmpresaOrigin != null && tEmpresaOrigin.getGrupoAC() != null && !"".equals(tEmpresaOrigin.getGrupoAC())){
				empresaOrigin = empresaManager.findByCodigoAC(tEmpresaOrigin.getCodigoAC(), tEmpresaOrigin.getGrupoAC());
				
				if(empresaOrigin == null)
					return new FeedbackWebService(false, "Empresa origem não encontrada no sistema RH", formataException( "empCodigo: " + tEmpresaOrigin.getCodigoAC() + "\ngrupoAC: " + tEmpresaOrigin.getGrupoAC(), null));
			}
			
			if(tEmpresaDestino != null && tEmpresaDestino.getGrupoAC() != null && !"".equals(tEmpresaDestino.getGrupoAC()))	{
				empresaDestino = empresaManager.findByCodigoAC(tEmpresaDestino.getCodigoAC(), tEmpresaDestino.getGrupoAC());
	
				if(empresaDestino == null)
					return new FeedbackWebService(false, "Empresa destino não encontrada no sistema RH", formataException( "empCodigo: " + tEmpresaDestino.getCodigoAC() + "\ngrupoAC: " + tEmpresaDestino.getGrupoAC(), null));
			}
			
			if (empresaOrigin != null && empresaDestino == null)
				return desligarEmpregadosEmLote(token, tEmpregadoToArrayCodigoAC(tEmpregados), empresaOrigin.getCodigoAC(), dataDesligamento, empresaOrigin.getGrupoAC());
	
			else if (empresaOrigin == null && empresaDestino != null)
				return inserirEmpregados(tEmpregados, tSituacoes, empresaDestino);
			
			else if(empresaOrigin != null && empresaDestino != null)
				return desligaInsereEmpregados(token, tEmpregados, tSituacoes, dataDesligamento, empresaOrigin, empresaDestino);
			
			else
				return new FeedbackWebService(false, "Nenhuma empresa esta integrada com o sistena RH.", "");
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}
	}
	
	public FeedbackWebService atualizarMovimentacaoEmLote(String token, String[] empregadoCodigos, String movimentacao, String codPessoalEstabOuArea, boolean atualizarTodasSituacoes, String empCodigo, String grupoAC)
	{
		String parametros = "\nempCodigo: " + empCodigo + "\ngrupoAC: " + grupoAC;

		try {
			verifyToken(token, true);
			Empresa empresa = empresaManager.findByCodigoAC(empCodigo, grupoAC);
			
			if(empresa == null || empresa.getId() == null)
				return new FeedbackWebService(false, "Empresa não encontrada no sistema RH.", formataException(parametros, null));
			
			if(!MovimentacaoAC.AREA.equals(movimentacao) && !MovimentacaoAC.ESTABELECIMENTO.equals(movimentacao))
				return new FeedbackWebService(false, "Movimentação não encontrada no RH.", "Movimentação: " + movimentacao);
			
			if(MovimentacaoAC.AREA.equals(movimentacao) && areaOrganizacionalManager.find(new String[]{"codigoAC", "empresa.id"}, new Object[]{codPessoalEstabOuArea, empresa.getId()}).size() == 0 ) 
				return new FeedbackWebService(false, "Não foi possível realizar a movimentação em lote. A lotação destino não foi encontrada no Fortes RH.", "");
			
			if(MovimentacaoAC.AREA.equals(movimentacao) && areaOrganizacionalManager.possuiAreaFilhasByCodigoAC(codPessoalEstabOuArea, empresa.getId())) 
				return new FeedbackWebService(false, "Não foi possível realizar a atualização em lote. A lotação é mãe de outras lotações.", "");
			
			for (String empregadoCodigo : empregadoCodigos) 
				historicoColaboradorManager.updateSituacaoByMovimentacao(empregadoCodigo, (String) new MovimentacaoAC().get(movimentacao), codPessoalEstabOuArea, atualizarTodasSituacoes, empresa.getId());
			
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e) {
			e.printStackTrace();
			return new FeedbackWebService(false, "Não foi possível realizar a atualização em lote.", "");
		}
		
		return new FeedbackWebService(true, "Atualização realizada com sucesso.", "");
	}

	private FeedbackWebService desligaInsereEmpregados(String token, TEmpregado[] tEmpregados, TSituacao[] tSituacoes, String dataTransferencia, Empresa empresaOrigin, Empresa empresaDestino) 
	{
		String parametros = "empCodigo: " + tSituacoes[0].getEmpresaCodigoAC() + "\ngrupoAC: " + tSituacoes[0].getGrupoAC() + "\ncodigo estabelecimento: " + tSituacoes[0].getEstabelecimentoCodigoAC() +
				"\ncodigo lotação: " + tSituacoes[0].getLotacaoCodigoAC() + "\ncodigo cargo: " + tSituacoes[0].getCargoCodigoAC();
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
			FeedbackWebService feedbackWebService = desligarEmpregadosEmLote(token, tEmpregadoToArrayCodigoAC(tEmpregados), empresaOrigin.getCodigoAC(), dataTransferencia, empresaOrigin.getGrupoAC());

			if(!feedbackWebService.isSucesso())	{
				transactionManager.rollback(status);
				return feedbackWebService;
			}
			
			feedbackWebService = inserirEmpregados(tEmpregados, tSituacoes, empresaDestino);
			if(!feedbackWebService.isSucesso())	{
				transactionManager.rollback(status);
				return feedbackWebService;
			}

			transactionManager.commit(status);
			return feedbackWebService; 

		}catch (Exception e) {
			e.printStackTrace();
			transactionManager.rollback(status);
			return new FeedbackWebService(false, "Erro ao transferir empregado.", formataException(parametros, e));
		}
	}

	private String[] tEmpregadoToArrayCodigoAC(TEmpregado[] tEmpregados) 
	{
		String[] codigosEmpregados = new String[tEmpregados.length];
		
		for(int i = 0; i < tEmpregados.length; i++)
			codigosEmpregados[i] = ((TEmpregado) tEmpregados[i]).getCodigoAC();
		
		return codigosEmpregados;
	}
	
	private FeedbackWebService inserirEmpregados(TEmpregado[] tEmpregados, TSituacao[] tSituacoes, Empresa empresaDestino)
	{
		String parametros = "empCodigo: " + tSituacoes[0].getEmpresaCodigoAC() + "\ngrupoAC: " + tSituacoes[0].getGrupoAC() + "\ncodigo estabelecimento: " + tSituacoes[0].getEstabelecimentoCodigoAC() +
				"\ncodigo lotação: " + tSituacoes[0].getLotacaoCodigoAC() + "\ncodigo cargo: " + tSituacoes[0].getCargoCodigoAC();
		try{
			Estabelecimento estabelecimento = estabelecimentoManager.findEstabelecimentoByCodigoAc(tSituacoes[0].getEstabelecimentoCodigoAC(),  empresaDestino.getCodigoAC(), empresaDestino.getGrupoAC());
			AreaOrganizacional areaOrganizacional = areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(tSituacoes[0].getLotacaoCodigoAC(), empresaDestino.getCodigoAC(), empresaDestino.getGrupoAC());
			FaixaSalarial faixaSalarial = faixaSalarialManager.findFaixaSalarialByCodigoAc(tSituacoes[0].getCargoCodigoAC(), empresaDestino.getCodigoAC(), empresaDestino.getGrupoAC());
			
			String retorno = "";
			
			if(estabelecimento == null)
				retorno += "O estabelecimento destino não existe no sistema RH\n";

			if(areaOrganizacional == null)
				retorno += "A área organizacional destino não existe no sistema RH\n";

			if(faixaSalarial == null)
				retorno += "O cargo destino não existe no sistema RH\n";
			
			if(!retorno.equals(""))
				return new FeedbackWebService(false,  "Existem inconsistências de integração com o sistema RH na empresa destino.", formataException(retorno, null));	
		
			colaboradorManager.saveEmpregadosESituacoes(tEmpregados, tSituacoes, empresaDestino);
			
			return new FeedbackWebService(true);
		}catch (ConstraintViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Empregado existente na empresa destino com mesmo código AC.\nFavor verificar integração dos sistemas", formataException(parametros, e));
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao transferir empregado.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService atualizarEmpregado(String token, TEmpregado empregado)
	{
		String parametros = "empregado: " + empregado.getCodigoAC() + "\nempresa: " + empregado.getEmpresaCodigoAC() + "\ngrupo AC: " + empregado.getGrupoAC();
		try{
			verifyToken(token, true);
			colaboradorManager.updateEmpregado(empregado);
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar empregado.", formataException(parametros, e));
		}
	}

	public FeedbackWebService atualizarEmpregadoAndSituacao(String token, TEmpregado empregado, TSituacao situacao){
		String parametros = "empregado: " + empregado.getCodigoAC() + " \nsituacao: " + situacao.getData();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try{
			verifyToken(token, true);
			verifyAndUpdateColaborador(empregado);
			historicoColaboradorManager.updateSituacao(situacao);
				
			transactionManager.commit(status);
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			e.printStackTrace();
			transactionManager.rollback(status);
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			transactionManager.rollback(status);
			return new FeedbackWebService(false, "Erro ao atualizar empregado e/ou situação.",  formataException(parametros, e));
		}
	}
	
    public FeedbackWebService confirmarContratacao(String token, TEmpregado empregado, TSituacao situacao) {
        String parametros = "empregado: " + empregado.getCodigoAC() + " \nsituacao: " + situacao.getData();
        try {
            verifyToken(token, true);
            colaboradorManager.confirmarContratacao(empregado, situacao);
            return new FeedbackWebService(true);
        } catch (TokenException e) {
            e.printStackTrace();
            return new FeedbackWebService(false, "Token incorreto.", "");
        } catch (Exception e) {
            e.printStackTrace();
            return new FeedbackWebService(false, "Erro ao atualizar empregado e/ou situação.", formataException(parametros, e));
        }
    }
	
	private Colaborador verifyAndUpdateColaborador(TEmpregado empregado) throws Exception {
		Colaborador colaborador = colaboradorManager.updateEmpregado(empregado);
		if(colaborador == null)
			throw new Exception("Empregado não encontrado.");
		
		return colaborador;
	}
	
	public FeedbackWebService cancelarContratacao(String token, TEmpregado empregado, TSituacao situacao,  String mensagem){
		String parametros = "código AC do empregado: " + empregado.getCodigoAC() +  "\nid_externo do empregado: " + empregado.getId() + "\nempresa: " + empregado.getEmpresaCodigoAC() + "\ngrupo AC: " + empregado.getGrupoAC();
		try{
			verifyToken(token, true);
			Colaborador colaborador = colaboradorManager.findByIdComHistorico(new Long (empregado.getId()));
			
			if(colaborador == null)//Apagar contratações duplicadas na tabela temporária do ac Solicitado pela equipe ac
				return new FeedbackWebService(true);
			
			HistoricoColaborador historico = historicoColaboradorManager.findById(Long.valueOf(situacao.getId()));

			if(historico == null)
				throw new Exception();
			
			colaboradorManager.cancelarContratacaoNoAC(colaborador, historico, mensagem);
			
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (FortesException e) {
			e.printStackTrace();
			return new FeedbackWebService(false, e.getMessage(), formataException(parametros, e));
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao cancelar contratação do empregado.", formataException(parametros, e));
		}
	}

	public FeedbackWebService cancelarSolicitacaoDesligamentoAC(String token, TEmpregado empregado, String mensagem){
		String parametros = "empregado: " + empregado.getCodigoAC() + "\nempresa: " + empregado.getEmpresaCodigoAC() + "\ngrupo AC: " + empregado.getGrupoAC();
		try	{
			verifyToken(token, true);
			Colaborador colaborador = colaboradorManager.findByCodigoACEmpresaCodigoAC(empregado.getCodigoAC(), empregado.getEmpresaCodigoAC(), empregado.getGrupoAC());		
			
			if(colaborador == null)
				return new FeedbackWebService(true);
			
			colaboradorManager.cancelarSolicitacaoDesligamentoAC(colaborador, mensagem, empregado.getEmpresaCodigoAC(), empregado.getGrupoAC());
			
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao cancelar solicitação de desligamento do empregado.", formataException(parametros, e));
		}
	}
	
	// TODO: SEM TESTE
	public FeedbackWebService criarSituacaoEmLote(String token, TSituacao[] situacaos){
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		try{
			verifyToken(token, true);
			for (TSituacao tSituacao : situacaos)
			{
				historicoColaboradors.add(montaSituacao(null, tSituacao));
			}
			
			historicoColaboradorManager.saveOrUpdate(historicoColaboradors);
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir situações em lote.", formataException(null, e));
		}
	}
	
	public FeedbackWebService atualizarSituacaoEmLote(String token, TSituacao[] situacaos){
		try	{
			realizandoOperacaoEmLote = true;
			verifyToken(token, true);
			for (TSituacao tSituacao : situacaos)
				atualizarSituacao(token, tSituacao);
			
			return new FeedbackWebService(true);
		}
		catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar situações em lote.", formataException(null, e));
		}
	}
	
	public FeedbackWebService criarSituacao(String token, TEmpregado empregado, TSituacao situacao){
		String parametros = "Situacao data: " + situacao.getData() + "\nempregado: " + situacao.getEmpregadoCodigoAC() + "\nempresa: " + situacao.getEmpresaCodigoAC() + "\ngrupoAC: " + situacao.getGrupoAC(); 
		try{
			verifyToken(token, true);
			HistoricoColaborador historicoColaborador = montaSituacao(empregado, situacao);

			historicoColaboradorManager.save(historicoColaborador);
			
			gerenciadorComunicacaoManager.enviaMensagemCadastroSituacaoAC(empregado.getNome(), situacao);

			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir situação.", formataException(parametros, e));
		}
	}

	private HistoricoColaborador montaSituacao(TEmpregado empregado, TSituacao situacao) throws Exception
	{
		HistoricoColaborador historicoColaborador = historicoColaboradorManager.prepareSituacao(situacao);

		Colaborador colaborador = colaboradorManager.findByCodigoAC(situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());
		
		if(colaborador == null)
			throw new Exception("Colaborador não encontrado no Fortes RH.");
		
		colaborador.setVinculo(colaboradorManager.updateVinculo(colaborador.getVinculo(), situacao, situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC()));
		
		historicoColaborador.setColaborador(colaborador);
		return historicoColaborador;
	}

	public FeedbackWebService atualizarSituacao(String token, TSituacao situacao){
		String parametros = "";
		if(situacao.getId() != null && situacao.getId() != 0)
			parametros = "Situacao ID: " + situacao.getId();
		else
			parametros = "Situacao data: " + situacao.getData() + "\nempregado: " + situacao.getEmpregadoCodigoAC() + "\nempresa: " + situacao.getEmpresaCodigoAC() + "\ngrupoAC: " + situacao.getGrupoAC();

		try	{
			if (!realizandoOperacaoEmLote)
				verifyToken(token, true);
			historicoColaboradorManager.updateSituacao(situacao);
			colaboradorManager.updateVinculo(null, situacao, situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar situação.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService removerSituacaoEmLote(String token, Integer movimentoSalarialId, String empCodigo, String grupoAC)
	{
		String parametros = "movimentoSalarialId: " + movimentoSalarialId + "\nempCodigo: " + empCodigo + "\ngrupoAC: " + grupoAC;
		try{
			verifyToken(token, true);
			Empresa empresa = empresaManager.findByCodigoAC(empCodigo, grupoAC);
			if(empresa == null || empresa.getId() == null)
				return new FeedbackWebService(false, "Erro ao excluir a situação, empresa não encontrada.", formataException(parametros, null));
			
			historicoColaboradorManager.deleteSituacaoByMovimentoSalarial(movimentoSalarialId.longValue(), empresa.getId());
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (ConstraintViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_SITUACAO_LOTE, formataException(parametros, e));
		}catch (DataIntegrityViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_SITUACAO_LOTE, formataException(parametros, e));
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir situação em lote.", formataException(parametros, e));
		}
	}

	public FeedbackWebService removerSituacao(String token, TSituacao situacao){
		String parametros = "situacao data: " + situacao.getDataFormatada() + "\nempregado: " + situacao.getEmpregadoCodigoAC() + "\nempresa: " + situacao.getEmpresaCodigoAC() + "\ngrupoAC: " + situacao.getGrupoAC();
		try{
			verifyToken(token, true);
			HistoricoColaborador historico = historicoColaboradorManager.findByAC(situacao.getDataFormatada(), situacao.getEmpregadoCodigoAC(),  situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());
			if(historico != null){
				historicoColaboradorManager.removeHistoricoAndReajusteAC(historico);
			}
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (ConstraintViolationException e)	{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_SITUACAO, formataException(parametros, e));
		}catch (DataIntegrityViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_SITUACAO, formataException(parametros, e));
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao remover situação.", formataException(parametros, e));
		}
	}

	public FeedbackWebService criarEstabelecimento(String token, TEstabelecimento testabelecimento){
		String parametros = "estabelecimento: " + testabelecimento.getCodigo();
		try	{
			verifyToken(token, true);
			Estabelecimento estabelecimento = new Estabelecimento();
			bindEstabelecimento(testabelecimento, estabelecimento);
			estabelecimento.setEmpresa(empresaManager.findByCodigoAC(testabelecimento.getCodigoEmpresa(), testabelecimento.getGrupoAC()));
			estabelecimentoManager.save(estabelecimento);
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir estabelecimento.", formataException(parametros, e));
		}
	}

	public FeedbackWebService atualizarEstabelecimento(String token, TEstabelecimento testabelecimento)
	{
		String parametros = "estabelecimento: " + testabelecimento.getCodigo() + "\nempresa: " + testabelecimento.getCodigoEmpresa() + "\ngrupoAC: " + testabelecimento.getGrupoAC();
		try{
			verifyToken(token, true);
			Estabelecimento estabelecimento = estabelecimentoManager.findByCodigo(testabelecimento.getCodigo(), testabelecimento.getCodigoEmpresa(), testabelecimento.getGrupoAC());
			bindEstabelecimento(testabelecimento, estabelecimento);
			estabelecimentoManager.update(estabelecimento);
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar estabelecimento.", formataException(parametros, e));
		}
	}

	public FeedbackWebService removerEstabelecimento(String token, String codigo, String empCodigo, String grupoAC)
	{
		String parametros = "estabelecimento: " + codigo + "\nempresa: " + empCodigo + "\ngrupoAC: " + grupoAC;
		try	{
			verifyToken(token, true);
			Empresa empresa = empresaManager.findByCodigoAC(empCodigo, grupoAC);

			if(estabelecimentoManager.remove(codigo, empresa.getId()))
				return new FeedbackWebService(true);
			else
				return new FeedbackWebService(false, "Erro: Estabelecimento não encontrado no RH.", formataException(parametros, null));
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (ConstraintViolationException e)	{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_ESTABELECIMENTO, formataException(parametros, e));
		}catch (DataIntegrityViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_ESTABELECIMENTO, formataException(parametros, e));
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir estabelecimento.", formataException(parametros, e));
		}
	}

	public FeedbackWebService criarIndice(String token, TIndice tindice)
	{
		String parametros = "indice: " + tindice.getCodigo() + "\ngrupoAC: " + tindice.getGrupoAC();
		try	{
			verifyToken(token, true);
			Indice indice = new Indice();
			bindIndice(tindice, indice);
			indiceManager.save(indice);

			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir índice.", formataException(parametros, e));
		}
	}

	public FeedbackWebService atualizarIndice(String token, TIndice tindice)
	{
		String parametros = "indice: " + tindice.getCodigo() + "\ngrupoAC: " + tindice.getGrupoAC();
		try	{
			verifyToken(token, true);
			Indice indice = indiceManager.findByCodigo(tindice.getCodigo(), tindice.getGrupoAC());
			bindIndice(tindice, indice);
			indiceManager.update(indice);

			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar índice.", formataException(parametros, e));
		}
	}

	public FeedbackWebService removerIndice(String token, String codigo, String grupoAC)
	{
		String parametros = "indice: " + codigo + "\ngrupoAC: " + grupoAC;
		try	{
			verifyToken(token, true);
			if(indiceManager.remove(codigo, grupoAC))
				return new FeedbackWebService(true);
			else
				return new FeedbackWebService(false, "Erro: Índice não encontrado no RH.", formataException(parametros, null));
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (ConstraintViolationException e)	{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_INDICE, formataException(parametros, e));
		}catch (DataIntegrityViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_INDICE, formataException(parametros, e));
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir índice.", formataException(parametros, e));
		}
	}

	public FeedbackWebService criarIndiceHistorico(String token, TIndiceHistorico tindiceHistorico)
	{
		String parametros = "indiceHistorico: " + tindiceHistorico.getIndiceCodigo() + "\ndata: " + tindiceHistorico.getDataFormatada() + "\ngrupoAC: " + tindiceHistorico.getGrupoAC();
		try	{
			verifyToken(token, true);
			IndiceHistorico indiceHistorico = new IndiceHistorico();
			bindIndiceHistorico(tindiceHistorico, indiceHistorico);

			Indice indice = indiceManager.findByCodigo(tindiceHistorico.getIndiceCodigo(), tindiceHistorico.getGrupoAC());
			if (indice != null)
				indiceHistorico.setIndice(indice);
			else
				throw new Exception("Não existe indice com este codigo: " + tindiceHistorico.getIndiceCodigo() + " GrupoAC: " + tindiceHistorico.getGrupoAC());

			String[] properties = new String[] { "data", "indice.id" };
			Object[] value = new Object[] { indiceHistorico.getData(), indice.getId() };

			if (indiceHistoricoManager.verifyExists(properties, value))
				indiceHistoricoManager.updateValor(indiceHistorico.getData(), indice.getId(), indiceHistorico.getValor());
			else
				indiceHistoricoManager.save(indiceHistorico);

			return new FeedbackWebService(true);
		}
		catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (ConstraintViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_INDICE_HISTORICO, formataException(parametros, e));
		}catch (DataIntegrityViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_INDICE_HISTORICO, formataException(parametros, e));
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir o histórico do índice.", formataException(parametros, e));
		}
	}

	public FeedbackWebService removerIndiceHistorico(String token, String data, String indiceCodigo, String grupoAC)
	{
		String parametros = "indice: " + indiceCodigo + "\ndata: " + data + "\ngrupoAC: " + grupoAC;
		Indice indice = indiceManager.findByCodigo(indiceCodigo, grupoAC);

		if (indice != null && indice.getId() != null){
			try {
				verifyToken(token, true);
				if(indiceHistoricoManager.remove(DateUtil.montaDataByString(data), indice.getId()))
					return new FeedbackWebService(true);
				else
					return new FeedbackWebService(false, "Erro: Histórico do índice não encontrado.", formataException(parametros, null));
			} 
			catch (TokenException e) {
				return new FeedbackWebService(false, "Token incorreto.", "");
			}
			catch (FortesException e) {
				return new FeedbackWebService(false, e.getMessage(), formataException(parametros, null));
			}
		}else
			return new FeedbackWebService(true);
	}

	public void bindIndice(TIndice tindice, Indice indice)
	{
		indice.setNome(tindice.getNome());
		indice.setCodigoAC(tindice.getCodigo());
		indice.setGrupoAC(tindice.getGrupoAC());
	}

	private void bindIndiceHistorico(TIndiceHistorico tindiceHistorico, IndiceHistorico indiceHistorico) throws Exception
	{
		indiceHistorico.setData(tindiceHistorico.getDataFormatada());
		indiceHistorico.setValor(tindiceHistorico.getValor());
	}

	private void bindEstabelecimento(TEstabelecimento testabelecimento, Estabelecimento estabelecimento) throws Exception
	{
		estabelecimento.setNome(testabelecimento.getNome());
		estabelecimento.setComplementoCnpj(testabelecimento.getComplementoCnpj());
		//TODO: codigoac vazio
		estabelecimento.setCodigoAC(testabelecimento.getCodigo());

		Endereco endereco = new Endereco();
		endereco.setLogradouro(testabelecimento.getLogradouro());
		endereco.setNumero(testabelecimento.getNumero());
		endereco.setComplemento(testabelecimento.getComplemento());
		endereco.setBairro(testabelecimento.getBairro());
		endereco.setCep(testabelecimento.getCep());

		Cidade cidade = cidadeManager.findByCodigoAC(testabelecimento.getCodigoCidade(), testabelecimento.getUf());
		if (cidade == null)
			throw new Exception("Cidade não encontrada no RH");

		endereco.setCidade(cidade);
		endereco.setUf(cidade.getUf());

		estabelecimento.setEndereco(endereco);
	}

	public FeedbackWebService setStatusFaixaSalarialHistorico(String token, Long faixaSalarialHistoricoId, Boolean aprovado, String mensagem, String empresaCodigoAC, String grupoAC)
	{
		try {
			verifyToken(token, true);
			if (!aprovado){
				FaixaSalarialHistorico faixaSalarialHistorico = faixaSalarialHistoricoManager.findByIdProjection(faixaSalarialHistoricoId);
				if(faixaSalarialHistorico != null)
				{
					String mensagemFinal = mensagemManager.formataMensagemCancelamentoFaixaSalarialHistorico(mensagem, faixaSalarialHistorico);
					Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(empresaCodigoAC, grupoAC, null);
					usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagemFinal, "Fortes Pessoal", null, usuarioEmpresas, null, TipoMensagem.CARGO_SALARIO, null, null);
				}
			}
	
			faixaSalarialHistoricoManager.setStatus(faixaSalarialHistoricoId, aprovado);
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}
	}

	public FeedbackWebService cancelarSituacao(String token, TSituacao situacao, String mensagem)
	{
		String parametros = "";
		if(situacao.getId() != null && situacao.getId() != 0)
			parametros = "Situacao ID: " + situacao.getId();
		else
			parametros = "Situacao data: " + situacao.getData() + "\nempregado: " + situacao.getEmpregadoCodigoAC() + "\nempresa: " + situacao.getEmpresaCodigoAC() + "\ngrupoAC: " + situacao.getGrupoAC();
		
		try	{
			verifyToken(token, true);
			historicoColaboradorManager.cancelarSituacao(situacao, mensagem);
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao cancelar situação.", formataException(parametros, e));
		}
	}

	public FeedbackWebService criarOcorrencia(String token, TOcorrencia tocorrencia)
	{
		String parametros = "ocorrencia: " + tocorrencia.getCodigo() + "\nempresa: " + tocorrencia.getEmpresa() + "\ngrupoAC: " + tocorrencia.getGrupoAC();
		try	{
			verifyToken(token, true);
			Ocorrencia ocorrencia = new Ocorrencia();
			Empresa empresa = empresaManager.findByCodigoAC(tocorrencia.getEmpresa(), tocorrencia.getGrupoAC());

			bindOcorrencia(tocorrencia, ocorrencia);
			ocorrencia.setIntegraAC(true);
			ocorrencia.setEmpresa(empresa);

			ocorrenciaManager.saveFromAC(ocorrencia);

			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir ocorrência.", formataException(parametros, e));
		}
	}

	public FeedbackWebService removerOcorrencia(String token, TOcorrencia tocorrencia){
		String parametros = "ocorrencia: " + tocorrencia.getCodigo() + "\nempresa: " + tocorrencia.getEmpresa() + "\ngrupoAC: " + tocorrencia.getGrupoAC();
		try{
			verifyToken(token, true);
			Empresa empresa = empresaManager.findByCodigoAC(tocorrencia.getEmpresa(), tocorrencia.getGrupoAC());
			if(ocorrenciaManager.removeByCodigoAC(tocorrencia.getCodigo(), empresa.getId()))
				return new FeedbackWebService(true);
			else
				return new FeedbackWebService(false, "Erro: Ocorrência não encontrada.", formataException(parametros, null));
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (ConstraintViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_OCORRENCIA, formataException(parametros, e));
		}catch (DataIntegrityViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_OCORRENCIA, formataException(parametros, e));
		}catch (Exception e)	{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir ocorrência.", formataException(parametros, e));
		}
	}

	public FeedbackWebService criarOcorrenciaEmpregado(String token, TOcorrenciaEmpregado[] ocorrenciaEmpregados)
	{
		try{
			verifyToken(token, true);
			for (TOcorrenciaEmpregado tOcorrenciaEmpregado : ocorrenciaEmpregados) {
				if(ocorrenciaManager.findByCodigoAC(tOcorrenciaEmpregado.getCodigo(), tOcorrenciaEmpregado.getEmpresa(), tOcorrenciaEmpregado.getGrupoAC()) == null)
					return new FeedbackWebService(false, "Ocorrência não encontrada no sistema RH", "Ocorrência com o código " + tOcorrenciaEmpregado.getCodigo() + " não existe no sistema RH.\nFavor entrar em contato com o suporte.");
			}
			
			Collection<ColaboradorOcorrencia> colaboradorOcorrencias = colaboradorOcorrenciaManager.bindColaboradorOcorrencias(ocorrenciaEmpregados);
			colaboradorOcorrenciaManager.saveOcorrenciasFromAC(colaboradorOcorrencias);
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir ocorrências do empregado.", formataException(null, e));
		}
	}

	public FeedbackWebService removerOcorrenciaEmpregado(String token, TOcorrenciaEmpregado[] ocorrenciaEmpregados){
		try{
			verifyToken(token, true);
			Collection<ColaboradorOcorrencia> colaboradorOcorrencias = colaboradorOcorrenciaManager.bindColaboradorOcorrencias(ocorrenciaEmpregados);
			colaboradorOcorrenciaManager.removeFromAC(colaboradorOcorrencias);
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (ConstraintViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_OCORRENCIA_EMPREGADO, formataException(null, e));
		}catch (DataIntegrityViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_OCORRENCIA_EMPREGADO, formataException(null, e));
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir ocorrência do empregado.", formataException(null, e));
		}
	}

	private void bindOcorrencia(TOcorrencia tocorrencia, Ocorrencia ocorrencia){
		//TODO: codigoac vazio
		ocorrencia.setCodigoAC(tocorrencia.getCodigo());
		ocorrencia.setDescricao(tocorrencia.getDescricao());
	}
	
	public FeedbackWebService criarAreaOrganizacional(String token, TAreaOrganizacional areaOrganizacional){
		String parametros = "areaOrganizacional: " + areaOrganizacional.getCodigo() + "\nempresa: " + areaOrganizacional.getEmpresaCodigo() + "\ngrupoAC: " + areaOrganizacional.getGrupoAC();
		try{
			verifyToken(token, true);
			Empresa empresa = empresaManager.findByCodigoAC(areaOrganizacional.getEmpresaCodigo(), areaOrganizacional.getGrupoAC());
			AreaOrganizacional area = areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(areaOrganizacional.getCodigo(), areaOrganizacional.getEmpresaCodigo(), areaOrganizacional.getGrupoAC());
			
			if(area != null && area.getId() != null)
				return new FeedbackWebService(false, "Não é possível inserir a área organizacional, pois existe uma área organizacional com o mesmo código no sistema RH.", null);
			
			AreaOrganizacional areaOrganizacionalTmp = new AreaOrganizacional();
			areaOrganizacionalTmp.setEmpresa(empresa);
			areaOrganizacionalManager.bind(areaOrganizacionalTmp, areaOrganizacional);
			
			areaOrganizacionalManager.save(areaOrganizacionalTmp);
			areaOrganizacionalManager.transferirColabDaAreaMaeParaAreaFilha(areaOrganizacionalTmp);

			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir lotação.", formataException(parametros, e));
		}
	}

	public FeedbackWebService atualizarAreaOrganizacional(String token, TAreaOrganizacional tAreaOrganizacional){
		String parametros = "areaOrganizacional: " + tAreaOrganizacional.getCodigo() + "\nempresa: " + tAreaOrganizacional.getEmpresaCodigo() + "\ngrupoAC: " + tAreaOrganizacional.getGrupoAC();
		try	{
			verifyToken(token, true);
			Empresa empresa = empresaManager.findByCodigoAC(tAreaOrganizacional.getEmpresaCodigo(), tAreaOrganizacional.getGrupoAC());
			AreaOrganizacional areaOrganizacionalTmp = areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(tAreaOrganizacional.getCodigo(), tAreaOrganizacional.getEmpresaCodigo(), tAreaOrganizacional.getGrupoAC());
			areaOrganizacionalTmp.setEmpresa(empresa);
			areaOrganizacionalManager.bind(areaOrganizacionalTmp, tAreaOrganizacional);
			
			areaOrganizacionalManager.update(areaOrganizacionalTmp);
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar lotação.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService removerAreaOrganizacional(String token, TAreaOrganizacional tAreaOrganizacional)
	{
		String parametros = "areaOrganizacional: " + tAreaOrganizacional.getCodigo() + "\nempresa: " + tAreaOrganizacional.getEmpresaCodigo() + "\ngrupoAC: " + tAreaOrganizacional.getGrupoAC();
		try{
			verifyToken(token, true);
			AreaOrganizacional areaOrganizacionalTmp = areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(tAreaOrganizacional.getCodigo(), tAreaOrganizacional.getEmpresaCodigo(), tAreaOrganizacional.getGrupoAC());
			if(areaOrganizacionalTmp != null)
				areaOrganizacionalManager.remove(areaOrganizacionalTmp);
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (ConstraintViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_LOTACAO, formataException(parametros, e));
		}catch (DataIntegrityViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_LOTACAO, formataException(parametros, e));
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir lotação.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService criarSituacaoCargo(String token, TSituacaoCargo tSituacaoCargo)
	{
		String parametros = "situacaoCargo: " + tSituacaoCargo.getCodigo() + "\nempresa: " + tSituacaoCargo.getEmpresaCodigoAC() + "\ngrupoAC: " + tSituacaoCargo.getGrupoAC();
		try	{
			verifyToken(token, true);
			FaixaSalarial faixaSalarial = faixaSalarialManager.findFaixaSalarialByCodigoAc(tSituacaoCargo.getCodigo(), tSituacaoCargo.getEmpresaCodigoAC(), tSituacaoCargo.getGrupoAC());
			FaixaSalarialHistorico faixaSalarialHistorico =  faixaSalarialHistoricoManager.bind(tSituacaoCargo, faixaSalarial);
			
			Long id = faixaSalarialHistoricoManager.findIdByDataFaixa(faixaSalarialHistorico);
			if(id != null){
				faixaSalarialHistorico.setId(id);
				faixaSalarialHistoricoManager.update(faixaSalarialHistorico);				
			}else
				faixaSalarialHistoricoManager.save(faixaSalarialHistorico);
			
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao criar a situação do cargo.", formataException(parametros, e));
		}
	}

	public FeedbackWebService atualizarSituacaoCargo(String token, TSituacaoCargo tSituacaoCargo)
	{
		String parametros = "situacaoCargo: " + tSituacaoCargo.getCodigo() + "\nempresa: " + tSituacaoCargo.getEmpresaCodigoAC() + "\ngrupoAC: " + tSituacaoCargo.getGrupoAC();
		try{
			verifyToken(token, true);
			FaixaSalarial faixaSalarial = faixaSalarialManager.findFaixaSalarialByCodigoAc(tSituacaoCargo.getCodigo(), tSituacaoCargo.getEmpresaCodigoAC(), tSituacaoCargo.getGrupoAC());
			FaixaSalarialHistorico faixaSalarialHistorico =  faixaSalarialHistoricoManager.bind(tSituacaoCargo, faixaSalarial);
			faixaSalarialHistorico.setId(faixaSalarialHistoricoManager.findIdByDataFaixa(faixaSalarialHistorico));
						
			faixaSalarialHistoricoManager.update(faixaSalarialHistorico);
			
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar situação do cargo.", formataException(parametros, e));
		}
	}

	public FeedbackWebService removerSituacaoCargo(String token, TSituacaoCargo tSituacaoCargo)
	{
		String parametros = "situacaoCargo: " + tSituacaoCargo.getCodigo() + "\nempresa: " + tSituacaoCargo.getEmpresaCodigoAC() + "\ngrupoAC: " + tSituacaoCargo.getGrupoAC();
		try	{
			verifyToken(token, true);
			FaixaSalarial faixaSalarial = faixaSalarialManager.findFaixaSalarialByCodigoAc(tSituacaoCargo.getCodigo(), tSituacaoCargo.getEmpresaCodigoAC(), tSituacaoCargo.getGrupoAC());
			
			FaixaSalarialHistorico faixaSalarialHistorico =  new FaixaSalarialHistorico();
			faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
			faixaSalarialHistorico.setData(tSituacaoCargo.getDataFormatada());
			faixaSalarialHistorico.setId(faixaSalarialHistoricoManager.findIdByDataFaixa(faixaSalarialHistorico));
			
			faixaSalarialHistoricoManager.remove(faixaSalarialHistorico);
			
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (ConstraintViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_SITUACAO_CARGO, formataException(parametros, e));
		}catch (DataIntegrityViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_SITUACAO_CARGO, formataException(parametros, e));
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir situação do cargo.", formataException(parametros, e));
		}
	}
	
	public Token verifyToken(String token, boolean removeToken) throws TokenException {
		Token tokenCached = tokenManager.findFirst(new String[]{"hash"}, new Object[]{token}, new String[]{});
		if ( tokenCached != null && token.equals(tokenCached.getHash()) ) {
			if ( removeToken ) {
				tokenManager.remove(tokenCached);
				return null;
			} else 
				return tokenCached;
		} else
			throw new TokenException();
	}
	
	public FeedbackWebService criarCargo(String token, TCargo tCargo)
	{
		String parametros = "cargo: " + tCargo.getCodigo() + "\nempresa: " + tCargo.getEmpresaCodigoAC() + "\ngrupoAC: " + tCargo.getGrupoAC();
		try	{
			verifyToken(token, true);
			if (StringUtils.defaultString(tCargo.getDescricao()).equals("")) 
				throw new Exception("Descrição da faixa está vazia.");
			if (StringUtils.defaultString(tCargo.getCargoDescricao()).length() > 100) 
				throw new Exception("Descrição do cargo deve ter no máximo 100 caracteres.");
			
			FaixaSalarial faixaSalarial = faixaSalarialManager.montaFaixa(tCargo);
			faixaSalarial.setCargo(cargoManager.preparaCargoDoAC(tCargo));
			codigoCBOManager.updateCBO(tCargo);
			faixaSalarialManager.save(faixaSalarial);
			
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir cargo.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService atualizarCargo(String token, TCargo tCargo)
	{
		String parametros = "cargo: " + tCargo.getCodigo() + "\nempresa: " + tCargo.getEmpresaCodigoAC() + "\ngrupoAC: " + tCargo.getGrupoAC();
		try	{
			verifyToken(token, true);
			if (StringUtils.defaultString(tCargo.getDescricao()).equals("")) 
				throw new Exception("Descrição da faixa está vazia.");

			FaixaSalarial faixaSalarial = faixaSalarialManager.findFaixaSalarialByCodigoAc(tCargo.getCodigo(), tCargo.getEmpresaCodigoAC(), tCargo.getGrupoAC());
			
			tCargo.setId(faixaSalarial.getId());
			faixaSalarialManager.updateAC(tCargo);
			codigoCBOManager.updateCBO(tCargo);
			
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar cargo.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService removerCargo(String token, TCargo tCargo)
	{
		String parametros = "cargo: " + tCargo.getCodigo() + "\nempresa: " + tCargo.getEmpresaCodigoAC() + "\ngrupoAC: " + tCargo.getGrupoAC();
		try	{
			verifyToken(token, true);
			FaixaSalarial faixaSalarial = faixaSalarialManager.findFaixaSalarialByCodigoAc(tCargo.getCodigo(), tCargo.getEmpresaCodigoAC(), tCargo.getGrupoAC());
			faixaSalarialHistoricoManager.removeByFaixas(new Long[]{faixaSalarial.getId()});
			faixaSalarialManager.remove(faixaSalarial);

			Collection<FaixaSalarial> faixas = faixaSalarialManager.findByCargo(faixaSalarial.getCargo().getId());
			if(faixas.isEmpty())
				cargoManager.remove(faixaSalarial.getCargo());
			
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (ConstraintViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_CARGO, formataException(parametros, e));
		}catch (DataIntegrityViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_CARGO, formataException(parametros, e));
		}catch (Exception e){
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir cargo.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService removerEmpregado(String token, TEmpregado empregado)
	{
		String parametros = "empregado: " + empregado.getCodigoAC() + "\nempresa: " + empregado.getEmpresaCodigoAC() + "\ngrupoAC: " + empregado.getGrupoAC();
		try {
			verifyToken(token, true);
		
			if(StringUtils.isEmpty(empregado.getCodigoAC()) || StringUtils.isEmpty(empregado.getEmpresaCodigoAC()) ||  StringUtils.isEmpty(empregado.getGrupoAC()))
				return new FeedbackWebService(false, "Dados do empregado invalidos", formataException(parametros, null));
		
			Colaborador colaborador = colaboradorManager.findByCodigoACEmpresaCodigoAC(empregado.getCodigoAC(), empregado.getEmpresaCodigoAC(), empregado.getGrupoAC());		

			if(colaborador == null)
				return new FeedbackWebService(true);
			
			colaboradorManager.removeColaboradorDependencias(colaborador);
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (ConstraintViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_EMPREGADO, formataException(parametros, e));
		}catch (DataIntegrityViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_EMPREGADO, formataException(parametros, e));
		}catch (Exception e){
			return new FeedbackWebService(false, "Erro ao excluir empregado.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService removerEmpregadoComDependencia(String token, TEmpregado empregado, TAuditoria tAuditoria){
		String parametros = "empregado: " + empregado.getCodigoAC() + "\nempresa: " + empregado.getEmpresaCodigoAC() + "\ngrupoAC: " + empregado.getGrupoAC();
		
		if(StringUtils.isEmpty(empregado.getCodigoAC()) || StringUtils.isEmpty(empregado.getEmpresaCodigoAC()) ||  StringUtils.isEmpty(empregado.getGrupoAC()))
			return new FeedbackWebService(false, "Dados do empregado invalidos", formataException(parametros, null));
		
		try {
			verifyToken(token, true);
			Colaborador colaborador = colaboradorManager.findByCodigoACEmpresaCodigoAC(empregado.getCodigoAC(), empregado.getEmpresaCodigoAC(), empregado.getGrupoAC());		

			if(colaborador == null)
				return new FeedbackWebService(true);
			
			colaboradorManager.removeComDependencias(colaborador.getId());
			
			try {
				Empresa empresa = empresaManager.findByCodigoAC(empregado.getEmpresaCodigoAC() , empregado.getGrupoAC());
				auditoriaManager.auditaRemoverEnpregadoFortesPessoal(empresa, tAuditoria, colaborador);
			} catch (Exception e) {
				System.out.println("Problema na auditoria ao remover empregado com dependência do Fortes Pessoal.");
				e.printStackTrace();
			}
			
			return new FeedbackWebService(true);
		}catch (TokenException e) {
			return new FeedbackWebService(false, "Token incorreto.", "");
		}catch (ConstraintViolationException e){
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_EMPREGADO, formataException(parametros, e));
		}catch (Exception e){
			return new FeedbackWebService(false, "Erro ao excluir empregado.", formataException(parametros, e));
		}
	}

	public TGrupo[] getGrupos(String token) throws TokenException
	{
		verifyToken(token, true);
		return grupoACManager.findTGrupos();
	}	
	
	public boolean criarEmpresa(String token, TEmpresa empresaAC)
	{
		try {
			verifyToken(token, true);
		} catch (Exception e) {
			return false;
		}
		return empresaManager.criarEmpresa(empresaAC);
	}

	public boolean removerEmpresa(String empresaCodigoAC, String grupoAC)
	{
		return false;
	}
	
	public TAula[] getTreinamentosPrevistos(String empregadoCodigo, String empresaCodigo, String empresaGrupo, String periodoIni, String periodoFim)
	{
		try {
			TAula[] tAulas = null;
			Empresa empresa = empresaManager.findByCodigoAC(empresaCodigo, empresaGrupo);
			
			if(empresa != null && empresaCodigo != null && periodoIni!= null && periodoFim != null && !"".equals(empresaCodigo) && !"".equals(periodoIni) && !"".equals(periodoFim))
				tAulas = colaboradorTurmaManager.getTreinamentosPrevistoParaTRU(empregadoCodigo, empresa, periodoIni, periodoFim);
			
			return tAulas;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public TAula[] getTreinamentosCursados(String empregadoCodigo, String empresaCodigo, String empresaGrupo, String periodoIni, String periodoFim)
	{
		try {
			TAula[] tAulas = null;
			Empresa empresa = empresaManager.findByCodigoAC(empresaCodigo, empresaGrupo);
			
			if(empresa != null && empresaCodigo != null && periodoIni!= null && periodoFim != null && !"".equals(empresaCodigo) && !"".equals(periodoIni) && !"".equals(periodoFim))
				tAulas = colaboradorTurmaManager.getTreinamentosRealizadosParaTRU(empregadoCodigo, empresa, periodoIni, periodoFim);
			
			return tAulas;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean existePesquisaParaSerRespondida(String empregadoCodigo, String empresaCodigo, String empresaGrupo)
	{
		try {
			Empresa empresa = empresaManager.findByCodigoAC(empresaCodigo, empresaGrupo);
			
			if(empresa != null && empregadoCodigo != null && !"".equals(empregadoCodigo))
				return pesquisaManager.existePesquisaParaSerRespondida(empregadoCodigo, empresa.getId());
			
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void reSincronizarTabelaTemporariaAC (String token, String gruposAC) throws Exception
	{
		if(!realizandoReenvioPendencias){	
			verifyToken(token, true);
			realizandoReenvioPendencias = true;
			Collection<Empresa> empresas = empresaManager.findByGruposAC(gruposAC);
			for (Empresa empresa : empresas) {
				try {
					colaboradorManager.reenviaAguardandoContratacao(empresa);
					colaboradorManager.confirmaReenvios(new TFeedbackPessoalWebService(true, "Reenvio das pendências realizada com sucesso", null), empresa);
					realizandoReenvioPendencias = false;
					if(false){//NÃO REMOVER SERVIRÁ PARA A FUNCIONALIDADE FUTURA DE ALINHAMENTO DA TABELA TEMPORÁRIA COM O FORTES PESSOAL
						faixaSalarialHistoricoManager.reenviaAguardandoConfirmacao(empresa);
						historicoColaboradorManager.reenviaAguardandoConfirmacao(empresa);
						colaboradorManager.reenviaSolicitacaoDesligamento(empresa);
					}
				} catch (Exception e) {
					e.printStackTrace();
					realizandoReenvioPendencias = false;
					colaboradorManager.confirmaReenvios(new TFeedbackPessoalWebService(false, e.getMessage(), formataException("GrupoAC: " + empresa.getGrupoAC() + "  Empresa: " + empresa.getCodigoAC(), e)), empresa);
				}
			}
		}
	}
	
	public boolean existeColaboradorAtivo(String cpf){
		return colaboradorManager.existeColaboradorAtivo(cpf, new Date());
	}
	
	public String versaoDoSistema()
	{
		return parametrosDoSistemaManager.findById(1L).getAppVersao();
	}
	
	public FeedbackWebService setUltimaCategoriaESocialColaborador(String token, String categoriaESocial, String colaboradorCodigoAC, String empresaCodigoAC, String grupoAC) {
		try {
			verifyToken(token, true);
			colaboradorManager.updateVinculo(categoriaESocial, colaboradorCodigoAC, empresaCodigoAC, grupoAC);
			return new FeedbackWebService(true);
		} catch (TokenException e) {
			e.printStackTrace();
			return new FeedbackWebService(false, "Token incorreto.", "");
		}
	}
	
	public void setCidadeManager(CidadeManager cidadeManager)
	{
		this.cidadeManager = cidadeManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public void setIndiceManager(IndiceManager indiceManager)
	{
		this.indiceManager = indiceManager;
	}

	public void setIndiceHistoricoManager(IndiceHistoricoManager indiceHistoricoManager)
	{
		this.indiceHistoricoManager = indiceHistoricoManager;
	}

	public void setFaixaSalarialHistoricoManager(FaixaSalarialHistoricoManager faixaSalarialHistoricoManager)
	{
		this.faixaSalarialHistoricoManager = faixaSalarialHistoricoManager;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public void setUsuarioMensagemManager(UsuarioMensagemManager usuarioMensagemManager)
	{
		this.usuarioMensagemManager = usuarioMensagemManager;
	}

	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager)
	{
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}

	public void setMensagemManager(MensagemManager mensagemManager)
	{
		this.mensagemManager = mensagemManager;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}

	public void setOcorrenciaManager(OcorrenciaManager ocorrenciaManager)
	{
		this.ocorrenciaManager = ocorrenciaManager;
	}

	public void setColaboradorOcorrenciaManager(ColaboradorOcorrenciaManager colaboradorOcorrenciaManager)
	{
		this.colaboradorOcorrenciaManager = colaboradorOcorrenciaManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public void setGrupoACManager(GrupoACManager grupoACManager) {
		this.grupoACManager = grupoACManager;
	}

	public void setUsuarioManager(UsuarioManager usuarioManager) {
		this.usuarioManager = usuarioManager;
	}

	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager) {
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}

	public void setPesquisaManager(PesquisaManager pesquisaManager) {
		this.pesquisaManager = pesquisaManager;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setAuditoriaManager(AuditoriaManager auditoriaManager) {
		this.auditoriaManager = auditoriaManager;
	}

	public void setTokenManager(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

    public void setCodigoCBOManager(CodigoCBOManager codigoCBOManager) {
        this.codigoCBOManager = codigoCBOManager;
    }
}