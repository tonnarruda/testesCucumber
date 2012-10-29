package com.fortes.rh.business.ws;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
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
import com.fortes.rh.model.ws.TCandidato;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.model.ws.TCidade;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TEmpresa;
import com.fortes.rh.model.ws.TEstabelecimento;
import com.fortes.rh.model.ws.TGrupo;
import com.fortes.rh.model.ws.TIndice;
import com.fortes.rh.model.ws.TIndiceHistorico;
import com.fortes.rh.model.ws.TOcorrencia;
import com.fortes.rh.model.ws.TOcorrenciaEmpregado;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.model.ws.TSituacaoCargo;
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

	private final String MSG_ERRO_REMOVER_SITUACAO_LOTE = "Erro ao excluir situação dos empregados, existem outros cadastros utilizando essa situação.";
	private final String MSG_ERRO_REMOVER_SITUACAO = "Erro ao excluir situação do empregado, existem outros cadastros utilizando essa situação.";
	private final String MSG_ERRO_REMOVER_LOTACAO = "Erro ao excluir lotação, existem outros cadastros utilizando essa lotação.";
	private final String MSG_ERRO_REMOVER_ESTABELECIMENTO = "Erro ao excluir estabelecimento, existem outros cadastros utilizando esse estabelecimento.";
	private final String MSG_ERRO_REMOVER_INDICE = "Erro ao excluir índice, existem outros cadastros utilizando esse índice.";
	private final String MSG_ERRO_REMOVER_INDICE_HISTORICO = "Erro ao excluir histórico desse índice, existem outros cadastros utilizando esse histórico.";
	private final String MSG_ERRO_REMOVER_OCORRENCIA = "Erro ao excluir ocorrência, existem outros cadastros utilizando essa ocorrência.";
	private final String MSG_ERRO_REMOVER_OCORRENCIA_EMPREGADO = "Erro ao excluir ocorrência do empregado, existem outros cadastros utilizando essa ocorrência.";
	private final String MSG_ERRO_REMOVER_SITUACAO_CARGO = "Erro ao excluir situação do cargo, existem outros cadastros utilizando essa situação.";
	private final String MSG_ERRO_REMOVER_CARGO = "Erro ao excluir cargo, existem outros cadastros utilizando esse cargo.";
	private final String MSG_ERRO_REMOVER_EMPREGADO = "Erro ao excluir empregado, existem outros cadastros utilizando esse empregado.";
	
	private String formataException(String parametros, Exception e) 
	{
		String msg = DateUtil.formataDiaMesAnoTime(new Date()) + "\n";
		
		if(parametros != null)
			msg += parametros + "\n\n";
		
		if(e != null)
			msg += e.toString() + "\n\n" + e.getCause() + "\n\n" + e.getMessage();
		
		return msg;
	}
	
	public String eco(String texto)
	{
		return texto;
	}

	public TEmpresa[] getEmpresas()
	{
		Collection<Empresa> empresas = empresaManager.findToList(new String[]{"id","nome","razaoSocial","codigoAC","grupoAC"}, new String[]{"id","nome","razaoSocial","codigoAC","grupoAC"}, new String[]{"nome"});
		return empresasToArray(empresas);
	}

	private TEmpresa[] empresasToArray(Collection<Empresa> empresas)
	{
		TEmpresa[] resultado = new TEmpresa[empresas.size()];
		int pos = 0;

		for (Empresa empresa : empresas)
		{
			TEmpresa emp = new TEmpresa(empresa.getId(), empresa.getNome(), empresa.getRazaoSocial(), empresa.getCodigoAC(), empresa.getGrupoAC());
			resultado[pos++] = emp;
		}

		return resultado;
	}

	public TCidade[] getCidades(String uf)
	{
		Collection<Cidade> cidades = cidadeManager.findAllByUf(uf);
		return cidadesToArray(cidades);
	}

	private TCidade[] cidadesToArray(Collection<Cidade> cidades)
	{
		TCidade[] resultado = new TCidade[cidades.size()];
		int pos = 0;

		for (Cidade cidade : cidades)
		{
			TCidade cid = new TCidade(cidade.getId(), cidade.getNome());
			resultado[pos++] = cid;
		}

		return resultado;
	}

	public TCargo[] getCargos(Long empresaId)
	{
		Collection<Cargo> cargos = cargoManager.findAllSelect(empresaId, "nomeMercado");
		return cargosToArray(cargos, true);
	}

	public TCargo[] getCargosAC(String empCodigo, String codigo, String grupoAC)
	{
		Collection<Cargo> cargos = cargoManager.findByEmpresaAC(empCodigo, codigo, grupoAC);
		return cargosToArray(cargos, false);
	}

	private TCargo[] cargosToArray(Collection<Cargo> cargos, boolean setaDescricao)
	{
		TCargo[] resultado = new TCargo[cargos.size()];
		int pos = 0;

		for (Cargo cargo : cargos)
		{
			TCargo car = null;
			if(setaDescricao)
				car = new TCargo(cargo.getId(), "", cargo.getNome());
			else
				car = new TCargo(cargo.getId(), cargo.getNome());
				
			resultado[pos++] = car;
		}

		return resultado;
	}
	
	public TCargo[] getFaixas() 
	{
		return faixaSalarialManager.getFaixasAC();
	}

	public String getNomesHomologos(String nomeCandidato)
	{
		Collection<Candidato> candidatos = candidatoManager.getCandidatosByNome(nomeCandidato);
		String nomeHomonimos = "";
		
		for (Candidato candidato : candidatos) 
		{
			nomeHomonimos += candidato.getNome();
			
			if (!candidato.getPessoal().getCpfFormatado().equals("") )
				nomeHomonimos += " (CPF "  + candidato.getPessoal().getCpfFormatado() +   ")";
			
			nomeHomonimos += " - " + candidato.getEmpresa().getNome()  + "\n";
				
		}
		
		return nomeHomonimos;
	}

	public boolean cadastrarCandidato(TCandidato cand) throws Exception
	{
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

		if (cand.getCidadeId() != null)
		{
			candidato.getEndereco().setCidade(cidadeManager.findById(cand.getCidadeId()));
			candidato.getEndereco().setUf(candidato.getEndereco().getCidade().getUf());
		}
		else
		{
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

	public FeedbackWebService desligarEmpregado(String codigo, String empCodigo, String dataDesligamento, String grupoAC)
	{
		String parametros = "codigo: " + codigo + " \nempCodigo:" + empCodigo + " \ndataDesligamento: " + dataDesligamento;
		try
		{
			Empresa empresa = empresaManager.findByCodigoAC(empCodigo, grupoAC);
			if(colaboradorManager.desligaColaboradorAC(codigo, empresa, DateUtil.montaDataByString(dataDesligamento)))
			{				
				gerenciadorComunicacaoManager.enviaMensagemDesligamentoColaboradorAC(codigo, empCodigo, grupoAC, empresa);
				return new FeedbackWebService(true);
			}
			else
				return new FeedbackWebService(false, "Erro: Empregado não encontrado no Fortes RH", formataException(parametros, null));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao desligar empregado.", formataException(parametros, e));
		}
	}

	public FeedbackWebService religarEmpregado(String codigo, String empCodigo, String grupoAC)
	{
		String parametros = "codigo: " + codigo + " \nempCodigo: " + empCodigo + " \ngrupoAC: " + grupoAC;
		try
		{
			Long colaboradorId = colaboradorManager.religaColaboradorAC(codigo, empCodigo, grupoAC);
			usuarioManager.reativaAcessoSistema(colaboradorId);
			
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao religar empregado.", formataException(parametros, e));
		}
	}

	public FeedbackWebService atualizarCodigoEmpregado(String grupoAC, String empresa, String codigo, String codigoNovo) 
	{
		String parametros = "empCodigo: " + empresa + "\ngrupoAC: " + grupoAC + "\ncodigo empregado: " +codigo+ "\nnovo codigo empregado: " + codigoNovo;
		try {
			Colaborador colaborador = colaboradorManager.findByCodigoAC(codigo, empresa, grupoAC);
			if(colaborador == null || colaborador.getId() == null)
				return new FeedbackWebService(false, "Erro ao alterar código do empregado.", "Colaborador não encontrado no RH.\n" + parametros);
				
			colaboradorManager.setCodigoColaboradorAC(codigoNovo, colaborador.getId());
			
			return new FeedbackWebService(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao alterar código do empregado.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService atualizarEmpregado(TEmpregado empregado)
	{
		String parametros = "empregado: " + empregado.getCodigoAC() + "\nempresa: " + empregado.getEmpresaCodigoAC() + "\ngrupo AC: " + empregado.getGrupoAC();
		try
		{
			colaboradorManager.updateEmpregado(empregado);
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar empregado.", formataException(parametros, e));
		}
	}

	public FeedbackWebService atualizarEmpregadoAndSituacao(TEmpregado empregado, TSituacao situacao)
	{
		String parametros = "empregado: " + empregado.getCodigoAC() + " \nsituacao: " + situacao.getData();
		
		try
		{
			try
			{
				Colaborador colaborador = colaboradorManager.updateEmpregado(empregado);
				if(colaborador == null)
					throw new Exception("Empregado não encontrado.");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Exception("Erro ao atualizar colaborador.");
			}

			try
			{
				historicoColaboradorManager.updateSituacao(situacao);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Exception("Erro ao atualizar situação do colaborador.");
			}

			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar empregado e situação.",  formataException(parametros, e));
		}
	}
	
	public FeedbackWebService cancelarContratacao(TEmpregado empregado, TSituacao situacao,  String mensagem)
	{
		String parametros = "empregado: " + empregado.getCodigoAC() + "\nempresa: " + empregado.getEmpresaCodigoAC() + "\ngrupo AC: " + empregado.getGrupoAC();
		try
		{
			Colaborador colaborador = colaboradorManager.findByIdComHistorico(new Long (empregado.getId()));
			
			if(colaborador == null)//Apagar contratações duplicadas na tabela temporária do ac Solicitado pela equipe ac
				return new FeedbackWebService(true);
			
			HistoricoColaborador historico = historicoColaboradorManager.findById(Long.valueOf(situacao.getId()));

			if(historico == null)
				throw new Exception();
			
			colaboradorManager.cancelarContratacaoNoAC(colaborador, historico, mensagem);
			
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao cancelar contratação do empregado.", formataException(parametros, e));
		}
	}

	public FeedbackWebService cancelarSolicitacaoDesligamentoAC(TEmpregado empregado, String mensagem)
	{
		String parametros = "empregado: " + empregado.getCodigoAC() + "\nempresa: " + empregado.getEmpresaCodigoAC() + "\ngrupo AC: " + empregado.getGrupoAC();
		try
		{
			Colaborador colaborador = colaboradorManager.findByCodigoACEmpresaCodigoAC(empregado.getCodigoAC(), empregado.getEmpresaCodigoAC(), empregado.getGrupoAC());		
			
			if(colaborador == null)
				return new FeedbackWebService(false, "Erro ao cancelar solicitação de desligamento do empregado.", formataException("Empregado inexistente no RH", null ));
			
			colaboradorManager.cancelarSolicitacaoDesligamentoAC(colaborador, mensagem, empregado.getEmpresaCodigoAC(), empregado.getGrupoAC());
			
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao cancelar solicitação de desligamento do empregado.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService criarSituacaoEmLote(TSituacao[] situacaos)
	{
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		try
		{
			for (TSituacao tSituacao : situacaos)
			{
				historicoColaboradors.add(montaSituacao(null, tSituacao));
			}
			
			historicoColaboradorManager.saveOrUpdate(historicoColaboradors);
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir situações em lote.", formataException(null, e));
		}
		
	}
	
	public FeedbackWebService atualizarSituacaoEmLote(TSituacao[] situacaos)
	{
		try
		{
			for (TSituacao tSituacao : situacaos)
				atualizarSituacao(tSituacao);
			
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar situações em lote.", formataException(null, e));
		}
		
	}
	
	public FeedbackWebService criarSituacao(TEmpregado empregado, TSituacao situacao)
	{
		String parametros = "Situacao data: " + situacao.getData() + "\nempregado: " + situacao.getEmpregadoCodigoAC() + "\nempresa: " + situacao.getEmpresaCodigoAC() + "\ngrupoAC: " + situacao.getGrupoAC(); 
		try
		{
			HistoricoColaborador historicoColaborador = montaSituacao(empregado, situacao);
			historicoColaboradorManager.save(historicoColaborador);
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir situação.", formataException(parametros, e));
		}
	}

	private HistoricoColaborador montaSituacao(TEmpregado empregado, TSituacao situacao) throws Exception
	{
		HistoricoColaborador historicoColaborador = historicoColaboradorManager.prepareSituacao(situacao);

		Colaborador colaborador = colaboradorManager.findByCodigoAC(situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());
		
		if(empregado != null)
			colaborador.setVinculo(colaboradorManager.getVinculo(empregado.getTipoAdmissao(), empregado.getVinculo(), empregado.getCategoria()));
		
		historicoColaborador.setColaborador(colaborador);
		return historicoColaborador;
	}

	public FeedbackWebService atualizarSituacao(TSituacao situacao)
	{
		String parametros = "";
		if(situacao.getId() != null && situacao.getId() != 0)
			parametros = "Situacao ID: " + situacao.getId();
		else
			parametros = "Situacao data: " + situacao.getData() + "\nempregado: " + situacao.getEmpregadoCodigoAC() + "\nempresa: " + situacao.getEmpresaCodigoAC() + "\ngrupoAC: " + situacao.getGrupoAC();
		
		try
		{
			historicoColaboradorManager.updateSituacao(situacao);
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar situação.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService removerSituacaoEmLote(Integer movimentoSalarialId, String empCodigo, String grupoAC)
	{
		String parametros = "movimentoSalarialId: " + movimentoSalarialId + "\nempCodigo: " + empCodigo + "\ngrupoAC: " + grupoAC;
		try
		{
			Empresa empresa = empresaManager.findByCodigoAC(empCodigo, grupoAC);
			if(empresa == null || empresa.getId() == null)
				return new FeedbackWebService(false, "Erro ao excluir a situação, empresa não encontrada.", formataException(parametros, null));
			
			historicoColaboradorManager.deleteSituacaoByMovimentoSalarial(movimentoSalarialId.longValue(), empresa.getId());
			return new FeedbackWebService(true);
		}
		catch (ConstraintViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_SITUACAO_LOTE, formataException(parametros, e));
		}
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_SITUACAO_LOTE, formataException(parametros, e));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir situação em lote.", formataException(parametros, e));
		}
		
	}

	public FeedbackWebService removerSituacao(TSituacao situacao)
	{
		String parametros = "situacao data: " + situacao.getDataFormatada() + "\nempregado: " + situacao.getEmpregadoCodigoAC() + "\nempresa: " + situacao.getEmpresaCodigoAC() + "\ngrupoAC: " + situacao.getGrupoAC();
		try
		{
			HistoricoColaborador historico = historicoColaboradorManager.findByAC(situacao.getDataFormatada(), situacao.getEmpregadoCodigoAC(),  situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());
			if(historico != null)
				historicoColaboradorManager.removeHistoricoAndReajusteAC(historico);
			return new FeedbackWebService(true);
		}
		catch (ConstraintViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_SITUACAO, formataException(parametros, e));
		}
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_SITUACAO, formataException(parametros, e));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao remover situação.", formataException(parametros, e));
		}
	}

	public FeedbackWebService criarEstabelecimento(TEstabelecimento testabelecimento)
	{
		String parametros = "estabelecimento: " + testabelecimento.getCodigo();
		try
		{
			Estabelecimento estabelecimento = new Estabelecimento();
			bindEstabelecimento(testabelecimento, estabelecimento);
			estabelecimento.setEmpresa(empresaManager.findByCodigoAC(testabelecimento.getCodigoEmpresa(), testabelecimento.getGrupoAC()));
			estabelecimentoManager.save(estabelecimento);
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir estabelecimento.", formataException(parametros, e));
		}
	}

	public FeedbackWebService atualizarEstabelecimento(TEstabelecimento testabelecimento)
	{
		String parametros = "estabelecimento: " + testabelecimento.getCodigo() + "\nempresa: " + testabelecimento.getCodigoEmpresa() + "\ngrupoAC: " + testabelecimento.getGrupoAC();
		try
		{
			Estabelecimento estabelecimento = estabelecimentoManager.findByCodigo(testabelecimento.getCodigo(), testabelecimento.getCodigoEmpresa(), testabelecimento.getGrupoAC());
			bindEstabelecimento(testabelecimento, estabelecimento);
			estabelecimentoManager.update(estabelecimento);
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar estabelecimento.", formataException(parametros, e));
		}
	}

	public FeedbackWebService removerEstabelecimento(String codigo, String empCodigo, String grupoAC)
	{
		String parametros = "estabelecimento: " + codigo + "\nempresa: " + empCodigo + "\ngrupoAC: " + grupoAC;
		try
		{
			Empresa empresa = empresaManager.findByCodigoAC(empCodigo, grupoAC);

			if(estabelecimentoManager.remove(codigo, empresa.getId()))
				return new FeedbackWebService(true);
			else
				return new FeedbackWebService(false, "Erro: Estabelecimento não encontrado no Fortes RH.", formataException(parametros, null));
		}
		catch (ConstraintViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_ESTABELECIMENTO, formataException(parametros, e));
		}
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_ESTABELECIMENTO, formataException(parametros, e));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir estabelecimento.", formataException(parametros, e));
		}
	}

	public FeedbackWebService criarIndice(TIndice tindice)
	{
		String parametros = "indice: " + tindice.getCodigo() + "\ngrupoAC: " + tindice.getGrupoAC();
		try
		{
			Indice indice = new Indice();
			bindIndice(tindice, indice);
			indiceManager.save(indice);

			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir índice.", formataException(parametros, e));
		}
	}

	public FeedbackWebService atualizarIndice(TIndice tindice)
	{
		String parametros = "indice: " + tindice.getCodigo() + "\ngrupoAC: " + tindice.getGrupoAC();
		try
		{
			Indice indice = indiceManager.findByCodigo(tindice.getCodigo(), tindice.getGrupoAC());
			bindIndice(tindice, indice);
			indiceManager.update(indice);

			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar índice.", formataException(parametros, e));
		}
	}

	public FeedbackWebService removerIndice(String codigo, String grupoAC)
	{
		String parametros = "indice: " + codigo + "\ngrupoAC: " + grupoAC;
		try
		{
			if(indiceManager.remove(codigo, grupoAC))
				return new FeedbackWebService(true);
			else
				return new FeedbackWebService(false, "Erro: Índice não encontrado no Fortes RH.", formataException(parametros, null));
		}
		catch (ConstraintViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_INDICE, formataException(parametros, e));
		}
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_INDICE, formataException(parametros, e));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir índice.", formataException(parametros, e));
		}
	}

	/*
	 * TODO . Ajuste no AC Pessoal
	 * NOVO método de remover Índice e históricos. descomentar após mudança no AC!
	 */
//	public boolean removerIndice(String codigo, TIndiceHistorico[] tindiceHistoricos)
//	{
//
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = transactionManager.getTransaction(def);
//
//		try
//		{
//			for (TIndiceHistorico indiceHistorico : tindiceHistoricos)
//			{
//				boolean ret = removerIndiceHistorico(indiceHistorico.getData(), indiceHistorico.getIndiceCodigo());
//
//				if (!ret)
//					throw new Exception("Erro ao remover histórico do Índice " + indiceHistorico.getIndiceCodigo() + " na data " + indiceHistorico.getData());
//			}
//
//			boolean ret = indiceManager.remove(codigo);
//
//			if (!ret)
//				throw new Exception("Erro ao remover Índice. " + codigo);
//
//			transactionManager.commit(status);
//		}
//		catch (Exception e)
//		{
//			transactionManager.rollback(status);
//			e.printStackTrace();
//
//			return false;
//		}
//
//		return true;
//	}

	public FeedbackWebService criarIndiceHistorico(TIndiceHistorico tindiceHistorico)
	{
		String parametros = "indiceHistorico: " + tindiceHistorico.getIndiceCodigo() + "\ndata: " + tindiceHistorico.getDataFormatada() + "\ngrupoAC: " + tindiceHistorico.getGrupoAC();
		try
		{
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
		catch (ConstraintViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_INDICE_HISTORICO, formataException(parametros, e));
		}
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_INDICE_HISTORICO, formataException(parametros, e));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir o histórico do índice.", formataException(parametros, e));
		}
	}

	public FeedbackWebService removerIndiceHistorico(String data, String indiceCodigo, String grupoAC)
	{
		String parametros = "indice: " + indiceCodigo + "\ndata: " + data + "\ngrupoAC: " + grupoAC;
		Indice indice = indiceManager.findByCodigo(indiceCodigo, grupoAC);

		if (indice != null && indice.getId() != null)
		{
			if(indiceHistoricoManager.remove(DateUtil.montaDataByString(data), indice.getId()))
				return new FeedbackWebService(true);
			else
				return new FeedbackWebService(false, "Erro: Histórico do índice não encontrado.", formataException(parametros, null));
		}
		else
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
		estabelecimento.setCodigoAC(testabelecimento.getCodigo());

		Endereco endereco = new Endereco();
		endereco.setLogradouro(testabelecimento.getLogradouro());
		endereco.setNumero(testabelecimento.getNumero());
		endereco.setComplemento(testabelecimento.getComplemento());
		endereco.setBairro(testabelecimento.getBairro());
		endereco.setCep(testabelecimento.getCep());

		Cidade cidade = cidadeManager.findByCodigoAC(testabelecimento.getCodigoCidade(), testabelecimento.getUf());
		if (cidade == null)
			throw new Exception("Cidade não encontrada no FortesRH");

		endereco.setCidade(cidade);
		endereco.setUf(cidade.getUf());

		estabelecimento.setEndereco(endereco);
	}

	public FeedbackWebService setStatusFaixaSalarialHistorico(Long faixaSalarialHistoricoId, Boolean aprovado, String mensagem, String empresaCodigoAC, String grupoAC)
	{
		String parametros = "faixaSalarialHistoricoId: " + faixaSalarialHistoricoId + "\nempresa: " + empresaCodigoAC + "\ngrupoAC: " + grupoAC;
		if (!aprovado)
		{
			FaixaSalarialHistorico faixaSalarialHistorico = faixaSalarialHistoricoManager.findByIdProjection(faixaSalarialHistoricoId);

			String mensagemFinal = mensagemManager.formataMensagemCancelamentoFaixaSalarialHistorico(mensagem, faixaSalarialHistorico);

			Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(empresaCodigoAC, grupoAC);
			usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagemFinal, "AC Pessoal", null, usuarioEmpresas, null, TipoMensagem.CARGO_SALARIO);
		}

		if(faixaSalarialHistoricoManager.setStatus(faixaSalarialHistoricoId, aprovado))
			return new FeedbackWebService(true);
		else
			return new FeedbackWebService(false, "Erro: Histórico da faixa salarial não encontrada.", formataException(parametros, null));
	}

	public FeedbackWebService cancelarSituacao(TSituacao situacao, String mensagem)
	{
		String parametros = "";
		
		if(situacao.getId() != null && situacao.getId() != 0)
			parametros = "Situacao ID: " + situacao.getId();
		else
			parametros = "Situacao data: " + situacao.getData() + "\nempregado: " + situacao.getEmpregadoCodigoAC() + "\nempresa: " + situacao.getEmpresaCodigoAC() + "\ngrupoAC: " + situacao.getGrupoAC();
		
		try
		{
			historicoColaboradorManager.cancelarSituacao(situacao, mensagem);
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao cancelar situação.", formataException(parametros, e));
		}
	}

	public FeedbackWebService criarOcorrencia(TOcorrencia tocorrencia)
	{
		String parametros = "ocorrencia: " + tocorrencia.getCodigo() + "\nempresa: " + tocorrencia.getEmpresa() + "\ngrupoAC: " + tocorrencia.getGrupoAC();
		try
		{
			Ocorrencia ocorrencia = new Ocorrencia();
			Empresa empresa = empresaManager.findByCodigoAC(tocorrencia.getEmpresa(), tocorrencia.getGrupoAC());

			bindOcorrencia(tocorrencia, ocorrencia);
			ocorrencia.setIntegraAC(true);
			ocorrencia.setEmpresa(empresa);

			ocorrenciaManager.saveFromAC(ocorrencia);

			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir ocorrência.", formataException(parametros, e));
		}
	}

	public FeedbackWebService removerOcorrencia(TOcorrencia tocorrencia)
	{
		String parametros = "ocorrencia: " + tocorrencia.getCodigo() + "\nempresa: " + tocorrencia.getEmpresa() + "\ngrupoAC: " + tocorrencia.getGrupoAC();
		try
		{
			Empresa empresa = empresaManager.findByCodigoAC(tocorrencia.getEmpresa(), tocorrencia.getGrupoAC());
			if(ocorrenciaManager.removeByCodigoAC(tocorrencia.getCodigo(), empresa.getId()))
				return new FeedbackWebService(true);
			else
				return new FeedbackWebService(false, "Erro: Ocorrência não encontrada.", formataException(parametros, null));
		}
		catch (ConstraintViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_OCORRENCIA, formataException(parametros, e));
		}
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_OCORRENCIA, formataException(parametros, e));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir ocorrência.", formataException(parametros, e));
		}
	}

	public FeedbackWebService criarOcorrenciaEmpregado(TOcorrenciaEmpregado[] ocorrenciaEmpregados)
	{
		try
		{
			Collection<ColaboradorOcorrencia> colaboradorOcorrencias = bindColaboradorOcorrencias(ocorrenciaEmpregados);
			colaboradorOcorrenciaManager.saveOcorrenciasFromAC(colaboradorOcorrencias);
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir ocorrências do empregado.", formataException(null, e));
		}
	}

	public FeedbackWebService removerOcorrenciaEmpregado(TOcorrenciaEmpregado[] ocorrenciaEmpregados)
	{
		Collection<ColaboradorOcorrencia> colaboradorOcorrencias = bindColaboradorOcorrencias(ocorrenciaEmpregados);

		try
		{
			colaboradorOcorrenciaManager.removeFromAC(colaboradorOcorrencias);
			return new FeedbackWebService(true);
		}
		catch (ConstraintViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_OCORRENCIA_EMPREGADO, formataException(null, e));
		}
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_OCORRENCIA_EMPREGADO, formataException(null, e));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir ocorrência do empregado.", formataException(null, e));
		}
	}

	private void bindOcorrencia(TOcorrencia tocorrencia, Ocorrencia ocorrencia)
	{
		ocorrencia.setCodigoAC(tocorrencia.getCodigo());
		ocorrencia.setDescricao(tocorrencia.getDescricao());
	}
	
	public Collection<ColaboradorOcorrencia> bindColaboradorOcorrencias(TOcorrenciaEmpregado[] tcolaboradorOcorrencias)
	{
		Collection<ColaboradorOcorrencia> colaboradorOcorrencias = new ArrayList<ColaboradorOcorrencia>(tcolaboradorOcorrencias.length);
		
		for (TOcorrenciaEmpregado tcolaboradorOcorrencia : tcolaboradorOcorrencias)
		{
			Ocorrencia ocorrencia = new Ocorrencia();
			ocorrencia.setCodigoAC(tcolaboradorOcorrencia.getCodigo());
			
			Empresa empresa = new Empresa();
			empresa.setCodigoAC(tcolaboradorOcorrencia.getEmpresa());
			empresa.setGrupoAC(tcolaboradorOcorrencia.getGrupoAC());
			
			ocorrencia.setEmpresa(empresa);
			Colaborador colaborador = new Colaborador();
			colaborador.setCodigoAC(tcolaboradorOcorrencia.getCodigoEmpregado());

			ColaboradorOcorrencia colaboradorOcorrenciaTemp = new ColaboradorOcorrencia();
			colaboradorOcorrenciaTemp.setColaborador(colaborador);
			colaboradorOcorrenciaTemp.setOcorrencia(ocorrencia);
			colaboradorOcorrenciaTemp.setDataIni(tcolaboradorOcorrencia.getDataFormatada());
			colaboradorOcorrenciaTemp.setDataFim(tcolaboradorOcorrencia.getDataFormatada());
			colaboradorOcorrenciaTemp.setObservacao(tcolaboradorOcorrencia.getObs());

			colaboradorOcorrencias.add(colaboradorOcorrenciaTemp);
		}
		
		return colaboradorOcorrencias;
	}

	public FeedbackWebService criarAreaOrganizacional(TAreaOrganizacional areaOrganizacional)
	{
		String parametros = "areaOrganizacional: " + areaOrganizacional.getCodigo() + "\nempresa: " + areaOrganizacional.getEmpresaCodigo() + "\ngrupoAC: " + areaOrganizacional.getGrupoAC();
		try
		{
			Empresa empresa = empresaManager.findByCodigoAC(areaOrganizacional.getEmpresaCodigo(), areaOrganizacional.getGrupoAC());
			
			AreaOrganizacional areaOrganizacionalTmp = new AreaOrganizacional();
			areaOrganizacionalTmp.setEmpresa(empresa);
			areaOrganizacionalManager.bind(areaOrganizacionalTmp, areaOrganizacional);
			
			areaOrganizacionalManager.save(areaOrganizacionalTmp);
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir lotação.", formataException(parametros, e));
		}
	}

	public FeedbackWebService atualizarAreaOrganizacional(TAreaOrganizacional tAreaOrganizacional)
	{
		String parametros = "areaOrganizacional: " + tAreaOrganizacional.getCodigo() + "\nempresa: " + tAreaOrganizacional.getEmpresaCodigo() + "\ngrupoAC: " + tAreaOrganizacional.getGrupoAC();
		try
		{
			Empresa empresa = empresaManager.findByCodigoAC(tAreaOrganizacional.getEmpresaCodigo(), tAreaOrganizacional.getGrupoAC());
			AreaOrganizacional areaOrganizacionalTmp = areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(tAreaOrganizacional.getCodigo(), tAreaOrganizacional.getEmpresaCodigo(), tAreaOrganizacional.getGrupoAC());
			areaOrganizacionalTmp.setEmpresa(empresa);
			areaOrganizacionalManager.bind(areaOrganizacionalTmp, tAreaOrganizacional);
			
			areaOrganizacionalManager.update(areaOrganizacionalTmp);
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar lotação.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService removerAreaOrganizacional(TAreaOrganizacional tAreaOrganizacional)
	{
		String parametros = "areaOrganizacional: " + tAreaOrganizacional.getCodigo() + "\nempresa: " + tAreaOrganizacional.getEmpresaCodigo() + "\ngrupoAC: " + tAreaOrganizacional.getGrupoAC();
		try
		{
			AreaOrganizacional areaOrganizacionalTmp = areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(tAreaOrganizacional.getCodigo(), tAreaOrganizacional.getEmpresaCodigo(), tAreaOrganizacional.getGrupoAC());		
			areaOrganizacionalManager.remove(areaOrganizacionalTmp);
			return new FeedbackWebService(true);
		}
		catch (ConstraintViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_LOTACAO, formataException(parametros, e));
		}
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_LOTACAO, formataException(parametros, e));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir lotação.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService criarSituacaoCargo(TSituacaoCargo tSituacaoCargo)
	{
		String parametros = "situacaoCargo: " + tSituacaoCargo.getCodigo() + "\nempresa: " + tSituacaoCargo.getEmpresaCodigoAC() + "\ngrupoAC: " + tSituacaoCargo.getGrupoAC();
		try
		{
			FaixaSalarial faixaSalarial = faixaSalarialManager.findFaixaSalarialByCodigoAc(tSituacaoCargo.getCodigo(), tSituacaoCargo.getEmpresaCodigoAC(), tSituacaoCargo.getGrupoAC());
			FaixaSalarialHistorico faixaSalarialHistorico =  faixaSalarialHistoricoManager.bind(tSituacaoCargo, faixaSalarial);
			
			Long id = faixaSalarialHistoricoManager.findIdByDataFaixa(faixaSalarialHistorico);
			if(id != null)
			{
				faixaSalarialHistorico.setId(id);
				faixaSalarialHistoricoManager.update(faixaSalarialHistorico);				
			}
			else
				faixaSalarialHistoricoManager.save(faixaSalarialHistorico);
			
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir a situação do cargo.", formataException(parametros, e));
		}
	}

	public FeedbackWebService atualizarSituacaoCargo(TSituacaoCargo tSituacaoCargo)
	{
		String parametros = "situacaoCargo: " + tSituacaoCargo.getCodigo() + "\nempresa: " + tSituacaoCargo.getEmpresaCodigoAC() + "\ngrupoAC: " + tSituacaoCargo.getGrupoAC();
		try
		{
			FaixaSalarial faixaSalarial = faixaSalarialManager.findFaixaSalarialByCodigoAc(tSituacaoCargo.getCodigo(), tSituacaoCargo.getEmpresaCodigoAC(), tSituacaoCargo.getGrupoAC());
			FaixaSalarialHistorico faixaSalarialHistorico =  faixaSalarialHistoricoManager.bind(tSituacaoCargo, faixaSalarial);
			faixaSalarialHistorico.setId(faixaSalarialHistoricoManager.findIdByDataFaixa(faixaSalarialHistorico));
						
			faixaSalarialHistoricoManager.update(faixaSalarialHistorico);
			
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar situação do cargo.", formataException(parametros, e));
		}
	}

	public FeedbackWebService removerSituacaoCargo(TSituacaoCargo tSituacaoCargo)
	{
		String parametros = "situacaoCargo: " + tSituacaoCargo.getCodigo() + "\nempresa: " + tSituacaoCargo.getEmpresaCodigoAC() + "\ngrupoAC: " + tSituacaoCargo.getGrupoAC();
		try
		{
			FaixaSalarial faixaSalarial = faixaSalarialManager.findFaixaSalarialByCodigoAc(tSituacaoCargo.getCodigo(), tSituacaoCargo.getEmpresaCodigoAC(), tSituacaoCargo.getGrupoAC());
			
			FaixaSalarialHistorico faixaSalarialHistorico =  new FaixaSalarialHistorico();
			faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
			faixaSalarialHistorico.setData(tSituacaoCargo.getDataFormatada());
			faixaSalarialHistorico.setId(faixaSalarialHistoricoManager.findIdByDataFaixa(faixaSalarialHistorico));
			
			faixaSalarialHistoricoManager.remove(faixaSalarialHistorico);
			
			return new FeedbackWebService(true);
		}
		catch (ConstraintViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_SITUACAO_CARGO, formataException(parametros, e));
		}
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_SITUACAO_CARGO, formataException(parametros, e));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir situação do cargo.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService criarCargo(TCargo tCargo)
	{
		String parametros = "cargo: " + tCargo.getCodigo() + "\nempresa: " + tCargo.getEmpresaCodigoAC() + "\ngrupoAC: " + tCargo.getGrupoAC();
		try
		{
			if (StringUtils.defaultString(tCargo.getDescricao()).equals("")) 
				throw new Exception("Descrição da faixa está vazia.");
			
			FaixaSalarial faixaSalarial = faixaSalarialManager.montaFaixa(tCargo);
			faixaSalarial.setCargo(cargoManager.preparaCargoDoAC(tCargo));

			faixaSalarialManager.save(faixaSalarial);

			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao inserir cargo.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService atualizarCargo(TCargo tCargo)
	{
		String parametros = "cargo: " + tCargo.getCodigo() + "\nempresa: " + tCargo.getEmpresaCodigoAC() + "\ngrupoAC: " + tCargo.getGrupoAC();
		try
		{
			if (StringUtils.defaultString(tCargo.getDescricao()).equals("")) 
				throw new Exception("Descrição da faixa está vazia.");

			FaixaSalarial faixaSalarial = faixaSalarialManager.findFaixaSalarialByCodigoAc(tCargo.getCodigo(), tCargo.getEmpresaCodigoAC(), tCargo.getGrupoAC());
			
			tCargo.setId(faixaSalarial.getId());
			faixaSalarialManager.updateAC(tCargo);
			cargoManager.updateCBO(faixaSalarial.getCargo().getId(), tCargo);
			
			return new FeedbackWebService(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao atualizar cargo.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService removerCargo(TCargo tCargo)
	{
		String parametros = "cargo: " + tCargo.getCodigo() + "\nempresa: " + tCargo.getEmpresaCodigoAC() + "\ngrupoAC: " + tCargo.getGrupoAC();
		try
		{
			FaixaSalarial faixaSalarial = faixaSalarialManager.findFaixaSalarialByCodigoAc(tCargo.getCodigo(), tCargo.getEmpresaCodigoAC(), tCargo.getGrupoAC());
			faixaSalarialManager.remove(faixaSalarial);
			
			Collection<FaixaSalarial> faixas = faixaSalarialManager.findByCargo(faixaSalarial.getCargo().getId());
			if(faixas.isEmpty())
				cargoManager.remove(faixaSalarial.getCargo());
			
			return new FeedbackWebService(true);
		}
		catch (ConstraintViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_CARGO, formataException(parametros, e));
		}
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_CARGO, formataException(parametros, e));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, "Erro ao excluir cargo.", formataException(parametros, e));
		}
	}
	
	public FeedbackWebService removerEmpregado(TEmpregado empregado)
	{
		String parametros = "empregado: " + empregado.getCodigoAC() + "\nempresa: " + empregado.getEmpresaCodigoAC() + "\ngrupoAC: " + empregado.getGrupoAC();
		
		if(StringUtils.isEmpty(empregado.getCodigoAC()) || StringUtils.isEmpty(empregado.getEmpresaCodigoAC()) ||  StringUtils.isEmpty(empregado.getGrupoAC()))
			return new FeedbackWebService(false, "Dados do empregado invalidos", formataException(parametros, null));
		
		try {
			Colaborador colaborador = colaboradorManager.findByCodigoACEmpresaCodigoAC(empregado.getCodigoAC(), empregado.getEmpresaCodigoAC(), empregado.getGrupoAC());		

			if(colaborador == null)
				return new FeedbackWebService(true);
			
			colaboradorManager.removeColaboradorDependencias(colaborador);
			return new FeedbackWebService(true);
		}
		catch (ConstraintViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_EMPREGADO, formataException(parametros, e));
		}
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			return new FeedbackWebService(false, MSG_ERRO_REMOVER_EMPREGADO, formataException(parametros, e));
		}
		catch (Exception e) 
		{
			return new FeedbackWebService(false, "Erro ao excluir empregado.", formataException(parametros, e));
		}
	}

	public TGrupo[] getGrupos() 
	{
		return grupoACManager.findTGrupos();
	}	
	
	public boolean criarEmpresa(TEmpresa empresaAC)
	{
		return empresaManager.criarEmpresa(empresaAC);
	}

	public boolean removerEmpresa(String empresaCodigoAC, String grupoAC)
	{
		return false;
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

}