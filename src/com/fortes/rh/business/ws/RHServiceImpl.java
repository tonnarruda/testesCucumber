package com.fortes.rh.business.ws;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
	private PlatformTransactionManager transactionManager;
	private MensagemManager mensagemManager;
	private CargoManager cargoManager;
	private CandidatoManager candidatoManager;
	private OcorrenciaManager ocorrenciaManager;
	private ColaboradorOcorrenciaManager colaboradorOcorrenciaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private FaixaSalarialManager faixaSalarialManager;
	private GrupoACManager grupoACManager;

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
		candidato.setColocacao(cand.getColocacao().charAt(0));
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

	public boolean desligarEmpregado(String codigo, String empCodigo, String dataDesligamento, String grupoAC)
	{
		try
		{
			Empresa empresa = empresaManager.findByCodigoAC(empCodigo, grupoAC);
			Colaborador colaborador = colaboradorManager.findByCodigoAC(codigo, empresa);

			Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(empCodigo, grupoAC);
			usuarioMensagemManager.saveMensagemAndUsuarioMensagem(getMensagem(colaborador.getNomeComercial()), "AC Pessoal", getLink(colaborador.getId()), usuarioEmpresas);

			return colaboradorManager.desligaColaboradorAC(codigo, empresa, DateUtil.montaDataByString(dataDesligamento));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	private String getLink(Long id)
	{
		return "pesquisa/entrevista/prepareResponderEntrevista.action?colaborador.id=" + id + "&voltarPara=../../index.action";
	}

	private String getMensagem(String nomeComercial) 
	{
		return "O Colaborador " + nomeComercial + " foi desligado no AC Pessoal.\n\n Para preencher a Entrevista de Desligamento, acesse a listagem de Colaboradores.";
	}

	public boolean religarEmpregado(String codigo, String empCodigo, String grupoAC)
	{
		try
		{
			return colaboradorManager.religaColaboradorAC(codigo, empCodigo, grupoAC);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean atualizarEmpregado(TEmpregado empregado)
	{
		try
		{
			colaboradorManager.updateEmpregado(empregado);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean atualizarEmpregadoAndSituacao(TEmpregado empregado, TSituacao situacao)
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

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

			transactionManager.commit(status);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			transactionManager.rollback(status);
			return false;
		}
	}

	public boolean criarSituacaoEmLote(TSituacao[] situacaos)
	{
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		try
		{
			for (TSituacao tSituacao : situacaos)
			{
				historicoColaboradors.add(montaSituacao(tSituacao));
			}
			
			historicoColaboradorManager.saveOrUpdate(historicoColaboradors);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean criarSituacao(TSituacao situacao)
	{
		try
		{
			HistoricoColaborador historicoColaborador = montaSituacao(situacao);
			historicoColaboradorManager.save(historicoColaborador);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	private HistoricoColaborador montaSituacao(TSituacao situacao) throws Exception
	{
		HistoricoColaborador historicoColaborador = historicoColaboradorManager.prepareSituacao(situacao);

		Colaborador colaborador = colaboradorManager.findByCodigoAC(situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());
		historicoColaborador.setColaborador(colaborador);
		return historicoColaborador;
	}

	public boolean atualizarSituacao(TSituacao situacao)
	{
		try
		{
			historicoColaboradorManager.updateSituacao(situacao);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removerSituacaoEmLote(Integer movimentoSalarialId, String empCodigo, String grupoAC)
	{
		try
		{
			Empresa empresa = empresaManager.findByCodigoAC(empCodigo, grupoAC);
			if(empresa == null || empresa.getId() == null)
				return false;
			
			historicoColaboradorManager.deleteSituacaoByMovimentoSalarial(movimentoSalarialId.longValue(), empresa.getId());
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
	}

	public boolean removerSituacao(TSituacao situacao)
	{
		try
		{
			HistoricoColaborador historico = historicoColaboradorManager.findByAC(situacao.getDataFormatada(), situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC(), situacao.getGrupoAC());
			if(historico != null)
				historicoColaboradorManager.removeHistoricoAndReajusteAC(historico);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

	}

	public boolean criarEstabelecimento(TEstabelecimento testabelecimento)
	{
		try
		{
			Estabelecimento estabelecimento = new Estabelecimento();
			bindEstabelecimento(testabelecimento, estabelecimento);
			estabelecimento.setEmpresa(empresaManager.findByCodigoAC(testabelecimento.getCodigoEmpresa(), testabelecimento.getGrupoAC()));
			estabelecimentoManager.save(estabelecimento);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean atualizarEstabelecimento(TEstabelecimento testabelecimento)
	{
		try
		{
			Estabelecimento estabelecimento = estabelecimentoManager.findByCodigo(testabelecimento.getCodigo(), testabelecimento.getCodigoEmpresa(), testabelecimento.getGrupoAC());
			bindEstabelecimento(testabelecimento, estabelecimento);
			estabelecimentoManager.update(estabelecimento);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean removerEstabelecimento(String codigo, String empCodigo, String grupoAC)
	{
		try
		{
			Empresa empresa = empresaManager.findByCodigoAC(empCodigo, grupoAC);
			return estabelecimentoManager.remove(codigo, empresa.getId());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean criarIndice(TIndice tindice)
	{
		try
		{
			Indice indice = new Indice();
			bindIndice(tindice, indice);
			indiceManager.save(indice);

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean atualizarIndice(TIndice tindice)
	{
		try
		{
			Indice indice = indiceManager.findByCodigo(tindice.getCodigo(), tindice.getGrupoAC());
			bindIndice(tindice, indice);
			indiceManager.update(indice);

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean removerIndice(String codigo, String grupoAC)
	{
		try
		{
			return indiceManager.remove(codigo, grupoAC);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
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

	public boolean criarIndiceHistorico(TIndiceHistorico tindiceHistorico)
	{
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

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean removerIndiceHistorico(String data, String indiceCodigo, String grupoAC)
	{
		Indice indice = indiceManager.findByCodigo(indiceCodigo, grupoAC);

		if (indice != null && indice.getId() != null)
			return indiceHistoricoManager.remove(DateUtil.montaDataByString(data), indice.getId());
		else
			return true; // caso o índice com esse código não exista, ignora
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

	private void bindEstabelecimento(TEstabelecimento testabelecimento, Estabelecimento estabelecimento)
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

		endereco.setCidade(cidade);
		endereco.setUf(cidade.getUf());

		estabelecimento.setEndereco(endereco);
	}

	public boolean setStatusFaixaSalarialHistorico(Long faixaSalarialHistoricoId, Boolean aprovado, String mensagem, String empresaCodigoAC, String grupoAC)
	{
		if (!aprovado)
		{
			FaixaSalarialHistorico faixaSalarialHistorico = faixaSalarialHistoricoManager.findByIdProjection(faixaSalarialHistoricoId);

			String mensagemFinal = mensagemManager.formataMensagemCancelamentoFaixaSalarialHistorico(mensagem, faixaSalarialHistorico);

			Collection<UsuarioEmpresa> usuarioEmpresas = usuarioEmpresaManager.findUsuariosByEmpresaRoleSetorPessoal(empresaCodigoAC, grupoAC);
			usuarioMensagemManager.saveMensagemAndUsuarioMensagem(mensagemFinal, "AC Pessoal", null, usuarioEmpresas);
		}

		return faixaSalarialHistoricoManager.setStatus(faixaSalarialHistoricoId, aprovado);
	}

	public boolean cancelarSituacao(TSituacao situacao, String mensagem)
	{
		try
		{
			historicoColaboradorManager.cancelarSituacao(situacao, mensagem);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean criarOcorrencia(TOcorrencia tocorrencia)
	{
		try
		{
			Ocorrencia ocorrencia = new Ocorrencia();
			Empresa empresa = empresaManager.findByCodigoAC(tocorrencia.getEmpresa(), tocorrencia.getGrupoAC());

			bindOcorrencia(tocorrencia, ocorrencia);
			ocorrencia.setIntegraAC(true);
			ocorrencia.setEmpresa(empresa);

			ocorrenciaManager.saveFromAC(ocorrencia);

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean removerOcorrencia(TOcorrencia tocorrencia)
	{
		try
		{
			Empresa empresa = empresaManager.findByCodigoAC(tocorrencia.getEmpresa(), tocorrencia.getGrupoAC());
			return ocorrenciaManager.removeByCodigoAC(tocorrencia.getCodigo(), empresa.getId());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean criarOcorrenciaEmpregado(TOcorrenciaEmpregado[] ocorrenciaEmpregados)
	{
		try
		{
			Collection<ColaboradorOcorrencia> colaboradorOcorrencias = bindColaboradorOcorrencias(ocorrenciaEmpregados);
			colaboradorOcorrenciaManager.saveOcorrenciasFromAC(colaboradorOcorrencias);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean removerOcorrenciaEmpregado(TOcorrenciaEmpregado[] ocorrenciaEmpregados)
	{
		Collection<ColaboradorOcorrencia> colaboradorOcorrencias = bindColaboradorOcorrencias(ocorrenciaEmpregados);

		try
		{
			colaboradorOcorrenciaManager.removeFromAC(colaboradorOcorrencias);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
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

	public boolean criarAreaOrganizacional(TAreaOrganizacional areaOrganizacional)
	{
		try
		{
			Empresa empresa = empresaManager.findByCodigoAC(areaOrganizacional.getEmpresaCodigo(), areaOrganizacional.getGrupoAC());
			
			AreaOrganizacional areaOrganizacionalTmp = new AreaOrganizacional();
			areaOrganizacionalTmp.setEmpresa(empresa);
			areaOrganizacionalManager.bind(areaOrganizacionalTmp, areaOrganizacional);
			
			areaOrganizacionalManager.save(areaOrganizacionalTmp);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean atualizarAreaOrganizacional(TAreaOrganizacional tAreaOrganizacional)
	{
		try
		{
			Empresa empresa = empresaManager.findByCodigoAC(tAreaOrganizacional.getEmpresaCodigo(), tAreaOrganizacional.getGrupoAC());
			AreaOrganizacional areaOrganizacionalTmp = areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(tAreaOrganizacional.getCodigo(), tAreaOrganizacional.getEmpresaCodigo(), tAreaOrganizacional.getGrupoAC());
			areaOrganizacionalTmp.setEmpresa(empresa);
			areaOrganizacionalManager.bind(areaOrganizacionalTmp, tAreaOrganizacional);
			
			areaOrganizacionalManager.update(areaOrganizacionalTmp);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removerAreaOrganizacional(TAreaOrganizacional tAreaOrganizacional)
	{
		try
		{
			AreaOrganizacional areaOrganizacionalTmp = areaOrganizacionalManager.findAreaOrganizacionalByCodigoAc(tAreaOrganizacional.getCodigo(), tAreaOrganizacional.getEmpresaCodigo(), tAreaOrganizacional.getGrupoAC());
			areaOrganizacionalManager.remove(areaOrganizacionalTmp);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean criarSituacaoCargo(TSituacaoCargo tSituacaoCargo)
	{
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
			
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean atualizarSituacaoCargo(TSituacaoCargo tSituacaoCargo)
	{
		try
		{
			FaixaSalarial faixaSalarial = faixaSalarialManager.findFaixaSalarialByCodigoAc(tSituacaoCargo.getCodigo(), tSituacaoCargo.getEmpresaCodigoAC(), tSituacaoCargo.getGrupoAC());
			FaixaSalarialHistorico faixaSalarialHistorico =  faixaSalarialHistoricoManager.bind(tSituacaoCargo, faixaSalarial);
			faixaSalarialHistorico.setId(faixaSalarialHistoricoManager.findIdByDataFaixa(faixaSalarialHistorico));
						
			faixaSalarialHistoricoManager.update(faixaSalarialHistorico);
			
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean removerSituacaoCargo(TSituacaoCargo tSituacaoCargo)
	{
		try
		{
			FaixaSalarial faixaSalarial = faixaSalarialManager.findFaixaSalarialByCodigoAc(tSituacaoCargo.getCodigo(), tSituacaoCargo.getEmpresaCodigoAC(), tSituacaoCargo.getGrupoAC());
			
			FaixaSalarialHistorico faixaSalarialHistorico =  new FaixaSalarialHistorico();
			faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
			faixaSalarialHistorico.setData(tSituacaoCargo.getDataFormatada());
			faixaSalarialHistorico.setId(faixaSalarialHistoricoManager.findIdByDataFaixa(faixaSalarialHistorico));
			
			faixaSalarialHistoricoManager.remove(faixaSalarialHistorico);
			
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean criarCargo(TCargo tCargo)
	{
		try
		{
			FaixaSalarial faixaSalarial = faixaSalarialManager.montaFaixa(tCargo);
			faixaSalarial.setCargo(cargoManager.preparaCargoDoAC(tCargo));

			faixaSalarialManager.save(faixaSalarial);

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean atualizarCargo(TCargo tCargo)
	{
		try
		{
			FaixaSalarial faixaSalarial = faixaSalarialManager.findFaixaSalarialByCodigoAc(tCargo.getCodigo(), tCargo.getEmpresaCodigoAC(), tCargo.getGrupoAC());
			
			tCargo.setId(faixaSalarial.getId());
			faixaSalarialManager.updateAC(tCargo);
			cargoManager.updateCBO(faixaSalarial.getCargo().getId(), tCargo);
			
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removerCargo(TCargo tCargo)
	{
		try
		{
			FaixaSalarial faixaSalarial = faixaSalarialManager.findFaixaSalarialByCodigoAc(tCargo.getCodigo(), tCargo.getEmpresaCodigoAC(), tCargo.getGrupoAC());
			faixaSalarialManager.remove(faixaSalarial);
			
			Collection<FaixaSalarial> faixas = faixaSalarialManager.findByCargo(faixaSalarial.getCargo().getId());
			if(faixas.isEmpty())
				cargoManager.remove(faixaSalarial.getCargo());
			
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
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

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
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


}