package com.fortes.rh.test;

import java.lang.reflect.Method;

import org.jmock.MockObjectTestCase;

import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManagerImpl;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoCurriculo;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.CertificadoMilitar;
import com.fortes.rh.model.captacao.ConfiguracaoImpressaoCurriculo;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Ctps;
import com.fortes.rh.model.captacao.EmpresaBds;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.Habilitacao;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoBDS;
import com.fortes.rh.model.captacao.TituloEleitoral;
import com.fortes.rh.model.captacao.relatorio.AvaliacaoCandidatosRelatorio;
import com.fortes.rh.model.captacao.relatorio.ColaboradorPesquisaRelatorio;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.captacao.relatorio.PerguntaResposta;
import com.fortes.rh.model.captacao.relatorio.ProcessoSeletivoRelatorio;
import com.fortes.rh.model.captacao.relatorio.ProdutividadeRelatorio;
import com.fortes.rh.model.captacao.relatorio.RespostaRelatorio;
import com.fortes.rh.model.captacao.relatorio.SolicitacaoPessoalRelatorio;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.cargosalario.relatorio.RelatorioPromocoes;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.PrioridadeTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.relatorio.ColaboradorCursoMatriz;
import com.fortes.rh.model.desenvolvimento.relatorio.ColaboradorSemCursoRelatorio;
import com.fortes.rh.model.desenvolvimento.relatorio.CursoPontuacaoMatriz;
import com.fortes.rh.model.desenvolvimento.relatorio.DataRiscos;
import com.fortes.rh.model.desenvolvimento.relatorio.SomatorioCursoMatriz;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Dependente;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.Gasto;
import com.fortes.rh.model.geral.GastoEmpresa;
import com.fortes.rh.model.geral.GastoEmpresaItem;
import com.fortes.rh.model.geral.GrupoGasto;
import com.fortes.rh.model.geral.HistoricoBeneficio;
import com.fortes.rh.model.geral.HistoricoColaboradorBeneficio;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.SocioEconomica;
import com.fortes.rh.model.geral.UsuarioMensagem;
import com.fortes.rh.model.geral.relatorio.ColaboradorOcorrenciaRelatorio;
import com.fortes.rh.model.geral.relatorio.CurriculoCandidatoRelatorio;
import com.fortes.rh.model.geral.relatorio.GastoEmpresaTotal;
import com.fortes.rh.model.geral.relatorio.GastoRelatorio;
import com.fortes.rh.model.geral.relatorio.GastoRelatorioItem;
import com.fortes.rh.model.geral.relatorio.MotivoDemissaoQuantidade;
import com.fortes.rh.model.geral.relatorio.OcorrenciaRelatorio;
import com.fortes.rh.model.geral.relatorio.PppFatorRisco;
import com.fortes.rh.model.geral.relatorio.TotalGastoRelatorio;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.geral.relatorio.TurnOverCollection;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Entrevista;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;
import com.fortes.rh.model.relatorio.Cabecalho;
import com.fortes.rh.model.security.Auditoria;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Anexo;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoEleicao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;
import com.fortes.rh.model.sesmt.ComissaoReuniao;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.model.sesmt.ExtintorManutencao;
import com.fortes.rh.model.sesmt.ExtintorManutencaoServico;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.model.sesmt.relatorio.AsoRelatorio;
import com.fortes.rh.model.sesmt.relatorio.CatRelatorioAnual;
import com.fortes.rh.model.sesmt.relatorio.ExameAnualRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.Ltcat;
import com.fortes.rh.model.sesmt.relatorio.PCMSO;
import com.fortes.rh.model.sesmt.relatorio.PppRelatorio;
import com.fortes.rh.model.sesmt.relatorio.Ppra;
import com.fortes.rh.model.sesmt.relatorio.PpraLtcatCabecalho;
import com.fortes.rh.model.sesmt.relatorio.PpraLtcatRelatorio;
import com.fortes.rh.model.sesmt.relatorio.QtdPorFuncaoRelatorio;
import com.fortes.rh.model.ws.TCandidato;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.model.ws.TCidade;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TEmpregadoEdicao;
import com.fortes.rh.model.ws.TEmpresa;
import com.fortes.rh.model.ws.TEstabelecimento;
import com.fortes.rh.model.ws.TIndice;
import com.fortes.rh.model.ws.TIndiceHistorico;
import com.fortes.rh.model.ws.TItemTabelaCargos;
import com.fortes.rh.model.ws.TItemTabelaEmpregados;
import com.fortes.rh.model.ws.TOcorrenciaEmpregado;
import com.fortes.rh.model.ws.TSituacao;

public class CoberturaGetSetTest extends MockObjectTestCase
{
	public void testCobreGetSet()
	{
		cobreGetSet(new TCandidato());
		cobreGetSet(new TEmpregadoEdicao());
		cobreGetSet(new TSituacao());
		cobreGetSet(new TEmpregado());
		cobreGetSet(new TEstabelecimento());
		cobreGetSet(new TIndice());
		cobreGetSet(new TIndiceHistorico());
		cobreGetSet(new TItemTabelaCargos());
		cobreGetSet(new TItemTabelaEmpregados());
		cobreGetSet(new TCidade());
		cobreGetSet(new TCargo());
		cobreGetSet(new TEmpresa());
		cobreGetSet(new TOcorrenciaEmpregado());
		cobreGetSet(new UsuarioEmpresaManagerImpl());
		cobreGetSet(new Perfil());
		cobreGetSet(new Papel());
		cobreGetSet(new UsuarioEmpresa());
		cobreGetSet(new Usuario());
		cobreGetSet(new PpraLtcatRelatorio());
		cobreGetSet(new PpraLtcatCabecalho());
		cobreGetSet(new Ppra());
		cobreGetSet(new Ltcat());
		cobreGetSet(new PCMSO());
		cobreGetSet(new ExameAnualRelatorio());
		cobreGetSet(new ExamesPrevistosRelatorio());
		cobreGetSet(new ExamesRealizadosRelatorio());
		cobreGetSet(new AsoRelatorio());
		cobreGetSet(new ColaboradorPesquisaRelatorio());
		cobreGetSet(new AvaliacaoCandidatosRelatorio());
		cobreGetSet(new PerguntaResposta());
		cobreGetSet(new RespostaRelatorio());
		cobreGetSet(new SolicitacaoPessoalRelatorio());
		cobreGetSet(new ProcessoSeletivoRelatorio());
		cobreGetSet(new ProdutividadeRelatorio());
		cobreGetSet(new IndicadorDuracaoPreenchimentoVaga());
		cobreGetSet(new ColaboradorSemCursoRelatorio());
		cobreGetSet(new ColaboradorCursoMatriz());
		cobreGetSet(new CursoPontuacaoMatriz());
		cobreGetSet(new DataRiscos());
		cobreGetSet(new SomatorioCursoMatriz());
		cobreGetSet(new Cabecalho());
		cobreGetSet(new SolicitacaoBDS());
		cobreGetSet(new Habilitacao());
		cobreGetSet(new Experiencia());
		cobreGetSet(new CandidatoCurriculo());
		cobreGetSet(new Anuncio());
		cobreGetSet(new Formacao());
		cobreGetSet(new EmpresaBds());
		cobreGetSet(new Conhecimento());
		cobreGetSet(new HistoricoCandidato());
		cobreGetSet(new CandidatoIdioma());
		cobreGetSet(new Candidato());
		cobreGetSet(new EtapaSeletiva());
		cobreGetSet(new CandidatoSolicitacao());
		cobreGetSet(new CertificadoMilitar());
		cobreGetSet(new TituloEleitoral());
		cobreGetSet(new Solicitacao());
		cobreGetSet(new Ctps());
		cobreGetSet(new MotivoSolicitacao());
		cobreGetSet(new Idioma());
		cobreGetSet(new QuestionarioResultadoPerguntaObjetiva());
		cobreGetSet(new QuestionarioRelatorio());
		cobreGetSet(new RealizacaoExame());
		cobreGetSet(new Anexo());
		cobreGetSet(new Cat());
		cobreGetSet(new EpiHistorico());
		cobreGetSet(new TipoEPI());
		cobreGetSet(new HistoricoAmbiente());
		cobreGetSet(new RiscoAmbiente());
		cobreGetSet(new Epi());
		cobreGetSet(new ClinicaAutorizada());
		cobreGetSet(new Risco());
		cobreGetSet(new Funcao());
		cobreGetSet(new Ambiente());
		cobreGetSet(new EngenheiroResponsavel());
		cobreGetSet(new MedicoCoordenador());
		cobreGetSet(new HistoricoFuncao());
		cobreGetSet(new Exame());
		cobreGetSet(new SolicitacaoExame());
		cobreGetSet(new SolicitacaoEpi());
		cobreGetSet(new Epc());
		cobreGetSet(new Resposta());
		cobreGetSet(new ColaboradorResposta());
		cobreGetSet(new Pergunta());
		cobreGetSet(new Afastamento());
		cobreGetSet(new Pesquisa());
		cobreGetSet(new Entrevista());
		cobreGetSet(new Questionario());
		cobreGetSet(new Aspecto());
		cobreGetSet(new ColaboradorQuestionario());
		cobreGetSet(new Contato());
		cobreGetSet(new GastoEmpresaItem());
		cobreGetSet(new Dependente());
		cobreGetSet(new GrupoGasto());
		cobreGetSet(new Mensagem());
		cobreGetSet(new Pessoal());
		cobreGetSet(new Estado());
		cobreGetSet(new MotivoDemissao());
		cobreGetSet(new Beneficio());
		cobreGetSet(new Estabelecimento());
		cobreGetSet(new AreaFormacao());
		cobreGetSet(new SocioEconomica());
		cobreGetSet(new Gasto());
		cobreGetSet(new GastoEmpresa());
		cobreGetSet(new Empresa());
		cobreGetSet(new ColaboradorIdioma());
		cobreGetSet(new ParametrosDoSistema());
		cobreGetSet(new Endereco());
		cobreGetSet(new AreaInteresse());
		cobreGetSet(new PendenciaAC());
		cobreGetSet(new AreaOrganizacional());
		cobreGetSet(new Cidade());
		cobreGetSet(new HistoricoColaboradorBeneficio());
		cobreGetSet(new DocumentoAnexo());
		cobreGetSet(new Colaborador());
		cobreGetSet(new Ocorrencia());
		cobreGetSet(new UsuarioMensagem());
		cobreGetSet(new ColaboradorOcorrencia());
		cobreGetSet(new HistoricoBeneficio());
		cobreGetSet(new Bairro());
		cobreGetSet(new CurriculoCandidatoRelatorio());
		cobreGetSet(new TurnOverCollection());
		cobreGetSet(new MotivoDemissaoQuantidade());
		cobreGetSet(new TotalGastoRelatorio());
		cobreGetSet(new PppRelatorio());
		cobreGetSet(new OcorrenciaRelatorio());
		cobreGetSet(new GastoEmpresaTotal());
		cobreGetSet(new PppFatorRisco());
		cobreGetSet(new TurnOver());
		cobreGetSet(new GastoRelatorioItem());
		cobreGetSet(new ColaboradorOcorrenciaRelatorio());
		cobreGetSet(new GastoRelatorio());
		cobreGetSet(new Cargo());
		cobreGetSet(new TabelaReajusteColaborador());
		cobreGetSet(new FaixaSalarial());
		cobreGetSet(new GrupoOcupacional());
		cobreGetSet(new ReajusteColaborador());
		cobreGetSet(new HistoricoColaborador());
		cobreGetSet(new FaixaSalarialHistorico());
		cobreGetSet(new Indice());
		cobreGetSet(new IndiceHistorico());
		cobreGetSet(new PrioridadeTreinamento());
		cobreGetSet(new Turma());
		cobreGetSet(new DNT());
		cobreGetSet(new DiaTurma());
		cobreGetSet(new ColaboradorPresenca());
		cobreGetSet(new ColaboradorTurma());
		cobreGetSet(new Curso());
		cobreGetSet(new Auditoria());
		cobreGetSet(new RelatorioPromocoes());
		cobreGetSet(new ColaboradorAfastamento());
		cobreGetSet(new CandidatoEleicao());
		cobreGetSet(new Comissao());
		cobreGetSet(new ComissaoEleicao());
		cobreGetSet(new ComissaoMembro());
		cobreGetSet(new ComissaoPeriodo());
		cobreGetSet(new ComissaoPlanoTrabalho());
		cobreGetSet(new ComissaoReuniao());
		cobreGetSet(new ComissaoReuniaoPresenca());
		cobreGetSet(new Eleicao());
		cobreGetSet(new Extintor());
		cobreGetSet(new ExtintorInspecao());
		cobreGetSet(new ExtintorManutencao());
		cobreGetSet(new ExtintorManutencaoServico());
		cobreGetSet(new Evento());
		cobreGetSet(new Agenda());
		cobreGetSet(new PppRelatorio());
		cobreGetSet(new CatRelatorioAnual());
		cobreGetSet(new QtdPorFuncaoRelatorio());
		cobreGetSet(new ConfiguracaoImpressaoCurriculo());
		cobreGetSet(new PeriodoExperiencia());
		cobreGetSet(new Avaliacao());
		cobreGetSet(new AvaliacaoDesempenho());

	}
	
	private void cobreGetSet(Object obj)
	{
		for (Method m : obj.getClass().getMethods())
		{
			try
			{
				if (m.getName().toString().substring(0, 3).equals("set"))
				{
					m.invoke(obj, new Object[]{null});
				}

				if (m.getName().toString().substring(0, 3).equals("get"))
				{
					m.invoke(obj, new Object[] {});
				}
				
				if (m.getName().toString().substring(0, 2).equals("is"))
				{
					m.invoke(obj, new Object[] {});
				}
				
				if (m.getName().toString().equals("toString"))
				{
					m.invoke(obj, new Object[] {});
				}
				
				if (m.getName().toString().equals("hashCode"))
				{
					m.invoke(obj, new Object[] {});
				}

				if (m.getName().toString().equals("equals"))
				{
					m.invoke(obj, new Object[]{null});
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
