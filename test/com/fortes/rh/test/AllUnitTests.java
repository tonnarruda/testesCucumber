package com.fortes.rh.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.fortes.rh.config.Log4jInitListenerTest;
import com.fortes.rh.config.LogCleanerJobTest;
import com.fortes.rh.config.ScriptReaderTest;
import com.fortes.rh.config.backup.BackupCleanerJobTest;
import com.fortes.rh.config.backup.BackupJobTest;
import com.fortes.rh.config.backup.BackupServiceImplTest;
import com.fortes.rh.config.backup.RunAntScriptTest;
import com.fortes.rh.security.spring.aop.AbstractModelJsonValueProcessorTest;
import com.fortes.rh.security.spring.aop.AuditoriaGeralAdviceTest;
import com.fortes.rh.security.spring.aop.AuditoriaPointcutTest;
import com.fortes.rh.security.spring.aop.DateFormatJsonValueProcessorTest;
import com.fortes.rh.security.spring.aop.GeraDadosAuditadosTest;
import com.fortes.rh.security.spring.aop.ProcuraChaveNaEntidadeTest;
import com.fortes.rh.security.spring.aop.callback.crud.CandidatoSolicitacaoAuditorCallbackImplTest;
import com.fortes.rh.security.spring.aop.callback.crud.ColaboradorQuestionarioAuditorCallbackImplTest;
import com.fortes.rh.security.spring.aop.callback.crud.InsertAuditorCallbackImplTest;
import com.fortes.rh.security.spring.aop.callback.crud.RemoveAuditorCallbackImplTest;
import com.fortes.rh.security.spring.aop.callback.crud.UpdateAuditorCallbackImplTest;
import com.fortes.rh.test.business.acesso.PapelManagerTest;
import com.fortes.rh.test.business.acesso.PerfilManagerTest;
import com.fortes.rh.test.business.acesso.UsuarioEmpresaManagerTest;
import com.fortes.rh.test.business.acesso.UsuarioEmpresaManagerTest_JUnit4;
import com.fortes.rh.test.business.acesso.UsuarioManagerTest;
import com.fortes.rh.test.business.avaliacao.AvaliacaoDesempenhoManagerTest;
import com.fortes.rh.test.business.avaliacao.AvaliacaoDesempenhoManagerTest_Junit4;
import com.fortes.rh.test.business.avaliacao.AvaliacaoManagerTest;
import com.fortes.rh.test.business.avaliacao.AvaliacaoManagerTest_JUnit4;
import com.fortes.rh.test.business.avaliacao.AvaliacaoPraticaManagerTest;
import com.fortes.rh.test.business.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerTest;
import com.fortes.rh.test.business.avaliacao.ParticipanteAvaliacaoDesempenhoManagerTest;
import com.fortes.rh.test.business.avaliacao.PeriodoExperienciaManagerTest;
import com.fortes.rh.test.business.avaliacao.PeriodoExperienciaManagerTest_Junit4;
import com.fortes.rh.test.business.captacao.AnuncioManagerTest;
import com.fortes.rh.test.business.captacao.AtitudeManagerTest;
import com.fortes.rh.test.business.captacao.CandidatoCurriculoManagerTest;
import com.fortes.rh.test.business.captacao.CandidatoIdiomaManagerTest;
import com.fortes.rh.test.business.captacao.CandidatoManagerTest;
import com.fortes.rh.test.business.captacao.CandidatoManagerTest_Junit4;
import com.fortes.rh.test.business.captacao.CandidatoSolicitacaoManagerTest;
import com.fortes.rh.test.business.captacao.CandidatoSolicitacaoManagerTest_JUnit4;
import com.fortes.rh.test.business.captacao.ConfigHistoricoNivelManagerTest;
import com.fortes.rh.test.business.captacao.ConfiguracaoImpressaoCurriculoManagerTest;
import com.fortes.rh.test.business.captacao.ConfiguracaoNivelCompetenciaCandidatoManagerTest;
import com.fortes.rh.test.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManagerTest;
import com.fortes.rh.test.business.captacao.ConfiguracaoNivelCompetenciaManagerTest;
import com.fortes.rh.test.business.captacao.ConhecimentoManagerTest;
import com.fortes.rh.test.business.captacao.DuracaoPreenchimentoVagaManagerTest;
import com.fortes.rh.test.business.captacao.EmpresaBdsManagerTest;
import com.fortes.rh.test.business.captacao.EtapaSeletivaManagerTest;
import com.fortes.rh.test.business.captacao.ExperienciaManagerTest;
import com.fortes.rh.test.business.captacao.FormacaoManagerTest;
import com.fortes.rh.test.business.captacao.HabilidadeManagerTest;
import com.fortes.rh.test.business.captacao.HistoricoCandidatoManagerTest;
import com.fortes.rh.test.business.captacao.NivelCompetenciaHistoricoManagerTest;
import com.fortes.rh.test.business.captacao.NivelCompetenciaManagerTest;
import com.fortes.rh.test.business.captacao.SolicitacaoAvaliacaoManagerTest;
import com.fortes.rh.test.business.captacao.SolicitacaoBDSManagerTest;
import com.fortes.rh.test.business.captacao.SolicitacaoManagerTest;
import com.fortes.rh.test.business.captacao.SolicitacaoManagerTest_Junit4;
import com.fortes.rh.test.business.cargosalario.CargoManagerTest;
import com.fortes.rh.test.business.cargosalario.FaixaSalarialHistoricoManagerTest;
import com.fortes.rh.test.business.cargosalario.FaixaSalarialHistoricoManagerTest_JUnit4;
import com.fortes.rh.test.business.cargosalario.FaixaSalarialManagerTest;
import com.fortes.rh.test.business.cargosalario.FaixaSalarialManagerTest_JUnit4;
import com.fortes.rh.test.business.cargosalario.FaturamentoMensalManagerTest;
import com.fortes.rh.test.business.cargosalario.GrupoOcupacionalManagerTest;
import com.fortes.rh.test.business.cargosalario.HistoricoColaboradorManagerTest;
import com.fortes.rh.test.business.cargosalario.HistoricoColaboradorManagerTest_Junit4;
import com.fortes.rh.test.business.cargosalario.IndiceHistoricoManagerTest;
import com.fortes.rh.test.business.cargosalario.IndiceManagerTest;
import com.fortes.rh.test.business.cargosalario.ReajusteColaboradorManagerTest;
import com.fortes.rh.test.business.cargosalario.TabelaReajusteColaboradorManagerTest;
import com.fortes.rh.test.business.desenvolvimento.AproveitamentoAvaliacaoCursoManagerTest;
import com.fortes.rh.test.business.desenvolvimento.AproveitamentoAvaliacaoCursoManagerTest_JUnit4;
import com.fortes.rh.test.business.desenvolvimento.CertificacaoManagerTest;
import com.fortes.rh.test.business.desenvolvimento.ColaboradorAvaliacaoPraticaManagerTest;
import com.fortes.rh.test.business.desenvolvimento.ColaboradorCertificacaoManagerTest;
import com.fortes.rh.test.business.desenvolvimento.ColaboradorPresencaManagerTest;
import com.fortes.rh.test.business.desenvolvimento.ColaboradorTurmaManagerTest;
import com.fortes.rh.test.business.desenvolvimento.ColaboradorTurmaManagerTest_Junit4;
import com.fortes.rh.test.business.desenvolvimento.CursoLntManagerTest;
import com.fortes.rh.test.business.desenvolvimento.CursoManagerTest;
import com.fortes.rh.test.business.desenvolvimento.DNTManagerTest;
import com.fortes.rh.test.business.desenvolvimento.DiaTurmaManagerTest;
import com.fortes.rh.test.business.desenvolvimento.LntManagerTest;
import com.fortes.rh.test.business.desenvolvimento.TurmaDocumentoAnexoManagerTest;
import com.fortes.rh.test.business.desenvolvimento.TurmaManagerTest;
import com.fortes.rh.test.business.desenvolvimento.TurmaManagerTest_Junit4;
import com.fortes.rh.test.business.geral.AreaFormacaoManagerTest;
import com.fortes.rh.test.business.geral.AreaInteresseManagerTest;
import com.fortes.rh.test.business.geral.AreaOrganizacionalManagerTest;
import com.fortes.rh.test.business.geral.BairroManagerTest;
import com.fortes.rh.test.business.geral.BeneficioManagerTest;
import com.fortes.rh.test.business.geral.CamposExtrasManagerTest;
import com.fortes.rh.test.business.geral.CartaoManagerTest;
import com.fortes.rh.test.business.geral.CidadeManagerTest;
import com.fortes.rh.test.business.geral.ClienteManagerTest;
import com.fortes.rh.test.business.geral.ColaboradorIdiomaManagerTest;
import com.fortes.rh.test.business.geral.ColaboradorManagerTest;
import com.fortes.rh.test.business.geral.ColaboradorManagerTest_Junit4;
import com.fortes.rh.test.business.geral.ColaboradorOcorrenciaManagerTest;
import com.fortes.rh.test.business.geral.ColaboradorPeriodoExperienciaAvaliacaoManagerTest;
import com.fortes.rh.test.business.geral.ComoFicouSabendoVagaManagerTest;
import com.fortes.rh.test.business.geral.ConfiguracaoCampoExtraVisivelObrigadotorioManagerTest;
import com.fortes.rh.test.business.geral.ConfiguracaoPerformanceManagerTest;
import com.fortes.rh.test.business.geral.ConfiguracaoRelatorioDinamicoManagerTest;
import com.fortes.rh.test.business.geral.DocumentoAnexoManagerTest;
import com.fortes.rh.test.business.geral.EmpresaManagerTest;
import com.fortes.rh.test.business.geral.EmpresaManagerTest_JUnit4;
import com.fortes.rh.test.business.geral.EstabelecimentoManagerTest;
import com.fortes.rh.test.business.geral.EstadoManagerTest;
import com.fortes.rh.test.business.geral.GastoEmpresaManagerTest;
import com.fortes.rh.test.business.geral.GastoManagerTest;
import com.fortes.rh.test.business.geral.GerenciadorComunicacaoManagerTest;
import com.fortes.rh.test.business.geral.GerenciadorComunicacaoManagerTest_Junit4;
import com.fortes.rh.test.business.geral.GrupoACManagerTest;
import com.fortes.rh.test.business.geral.GrupoGastoManagerTest;
import com.fortes.rh.test.business.geral.HistoricoBeneficioManagerTest;
import com.fortes.rh.test.business.geral.HistoricoColaboradorBeneficioManagerTest;
import com.fortes.rh.test.business.geral.ImportadorGastosACTest;
import com.fortes.rh.test.business.geral.MensagemManagerTest;
import com.fortes.rh.test.business.geral.MotivoDemissaoManagerTest;
import com.fortes.rh.test.business.geral.OcorrenciaManagerTest;
import com.fortes.rh.test.business.geral.ParametrosDoSistemaManagerTest;
import com.fortes.rh.test.business.geral.ProvidenciaManagerTest;
import com.fortes.rh.test.business.geral.QuantidadeLimiteColaboradoresPorCargoManagerTest;
import com.fortes.rh.test.business.geral.TipoDespesaManagerTest;
import com.fortes.rh.test.business.geral.TipoDocumentoManagerTest;
import com.fortes.rh.test.business.geral.TurmaTipoDespesaManagerTest;
import com.fortes.rh.test.business.geral.UsuarioMensagemManagerTest;
import com.fortes.rh.test.business.geral.UsuarioMensagemManagerTest_Junit4;
import com.fortes.rh.test.business.importacao.ImportacaoColaboradorManagerTest;
import com.fortes.rh.test.business.pesquisa.AspectoManagerTest;
import com.fortes.rh.test.business.pesquisa.ColaboradorQuestionarioManagerTest;
import com.fortes.rh.test.business.pesquisa.ColaboradorRespostaManagerTest;
import com.fortes.rh.test.business.pesquisa.ColaboradorRespostaManagerTest_Junit4;
import com.fortes.rh.test.business.pesquisa.EntrevistaManagerTest;
import com.fortes.rh.test.business.pesquisa.FichaMedicaManagerTest;
import com.fortes.rh.test.business.pesquisa.LembretePesquisaTest;
import com.fortes.rh.test.business.pesquisa.PerguntaManagerTest;
import com.fortes.rh.test.business.pesquisa.PesquisaManagerTest;
import com.fortes.rh.test.business.pesquisa.QuestionarioManagerTest;
import com.fortes.rh.test.business.pesquisa.RespostaManagerTest;
import com.fortes.rh.test.business.security.AuditoriaManagerTest;
import com.fortes.rh.test.business.sesmt.AfastamentoManagerTest;
import com.fortes.rh.test.business.sesmt.AgendaManagerTest;
import com.fortes.rh.test.business.sesmt.AmbienteManagerTest;
import com.fortes.rh.test.business.sesmt.AmbienteManagerTest_JUnit4;
import com.fortes.rh.test.business.sesmt.AnexoManagerTest;
import com.fortes.rh.test.business.sesmt.AreaVivenciaManagerTest;
import com.fortes.rh.test.business.sesmt.AreaVivenciaPcmatManagerTest;
import com.fortes.rh.test.business.sesmt.AtividadeSegurancaPcmatManagerTest;
import com.fortes.rh.test.business.sesmt.CandidatoEleicaoManagerTest;
import com.fortes.rh.test.business.sesmt.CatManagerTest;
import com.fortes.rh.test.business.sesmt.ClinicaAutorizadaManagerTest;
import com.fortes.rh.test.business.sesmt.ColaboradorAfastamentoManagerTest;
import com.fortes.rh.test.business.sesmt.ColaboradorAfastamentoManagerTest_JUnit4;
import com.fortes.rh.test.business.sesmt.ComissaoEleicaoManagerTest;
import com.fortes.rh.test.business.sesmt.ComissaoManagerTest;
import com.fortes.rh.test.business.sesmt.ComissaoMembroManagerTest;
import com.fortes.rh.test.business.sesmt.ComissaoPeriodoManagerTest;
import com.fortes.rh.test.business.sesmt.ComissaoPlanoTrabalhoManagerTest;
import com.fortes.rh.test.business.sesmt.ComissaoReuniaoManagerTest;
import com.fortes.rh.test.business.sesmt.ComissaoReuniaoManagerTest_Junit4;
import com.fortes.rh.test.business.sesmt.ComissaoReuniaoPresencaManagerTest;
import com.fortes.rh.test.business.sesmt.EleicaoManagerTest;
import com.fortes.rh.test.business.sesmt.EleicaoManagerTest_Junit4;
import com.fortes.rh.test.business.sesmt.EngenheiroResponsavelManagerTest;
import com.fortes.rh.test.business.sesmt.EpcManagerTest;
import com.fortes.rh.test.business.sesmt.EpcPcmatManagerTest;
import com.fortes.rh.test.business.sesmt.EpiHistoricoManagerTest;
import com.fortes.rh.test.business.sesmt.EpiManagerTest;
import com.fortes.rh.test.business.sesmt.EpiPcmatManagerTest;
import com.fortes.rh.test.business.sesmt.EtapaProcessoEleitoralManagerTest;
import com.fortes.rh.test.business.sesmt.ExameManagerTest;
import com.fortes.rh.test.business.sesmt.ExameManagerTest_JUnit4;
import com.fortes.rh.test.business.sesmt.ExameSolicitacaoExameManagerTest;
import com.fortes.rh.test.business.sesmt.ExtintorInspecaoManagerTest;
import com.fortes.rh.test.business.sesmt.ExtintorManagerTest;
import com.fortes.rh.test.business.sesmt.ExtintorManutencaoManagerTest;
import com.fortes.rh.test.business.sesmt.FaseManagerTest;
import com.fortes.rh.test.business.sesmt.FuncaoManagerTest;
import com.fortes.rh.test.business.sesmt.HistoricoAmbienteManagerTest;
import com.fortes.rh.test.business.sesmt.HistoricoExtintorManagerTest;
import com.fortes.rh.test.business.sesmt.HistoricoFuncaoManagerTest;
import com.fortes.rh.test.business.sesmt.MedicaoRiscoManagerTest;
import com.fortes.rh.test.business.sesmt.MedicoCoordenadorManagerTest;
import com.fortes.rh.test.business.sesmt.MedidaSegurancaManagerTest;
import com.fortes.rh.test.business.sesmt.NaturezaLesaoManagerTest;
import com.fortes.rh.test.business.sesmt.ObraManagerTest;
import com.fortes.rh.test.business.sesmt.OrdemDeServicoManagerTest;
import com.fortes.rh.test.business.sesmt.PcmatManagerTest;
import com.fortes.rh.test.business.sesmt.PcmsoManagerTest;
import com.fortes.rh.test.business.sesmt.ProntuarioManagerTest;
import com.fortes.rh.test.business.sesmt.RealizacaoExameManagerTest;
import com.fortes.rh.test.business.sesmt.RiscoAmbienteManagerTest;
import com.fortes.rh.test.business.sesmt.RiscoFuncaoManagerTest;
import com.fortes.rh.test.business.sesmt.RiscoManagerTest;
import com.fortes.rh.test.business.sesmt.RiscoMedicaoRiscoManagerTest;
import com.fortes.rh.test.business.sesmt.SolicitacaoEpiItemDevolucaoManagerTest;
import com.fortes.rh.test.business.sesmt.SolicitacaoEpiItemEntregaManagerTest;
import com.fortes.rh.test.business.sesmt.SolicitacaoEpiItemManagerTest;
import com.fortes.rh.test.business.sesmt.SolicitacaoEpiManagerTest;
import com.fortes.rh.test.business.sesmt.SolicitacaoEpiManagerTest_Junit4;
import com.fortes.rh.test.business.sesmt.SolicitacaoExameManagerTest;
import com.fortes.rh.test.business.sesmt.TamanhoEPIManagerTest;
import com.fortes.rh.test.business.sesmt.TestemunhaManagerTest;
import com.fortes.rh.test.business.ws.RHServiceIntranetTest;
import com.fortes.rh.test.business.ws.RHServiceTest;
import com.fortes.rh.test.dao.hibernate.acesso.PapelDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.acesso.PerfilDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.acesso.UsuarioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.acesso.UsuarioEmpresaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.avaliacao.AvaliacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.avaliacao.AvaliacaoDaoHibernateTest_Junit4;
import com.fortes.rh.test.dao.hibernate.avaliacao.AvaliacaoDesempenhoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.avaliacao.AvaliacaoPraticaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.avaliacao.PeriodoExperienciaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.avaliacao.PeriodoExperienciaDaoHibernateTest_Junit4;
import com.fortes.rh.test.dao.hibernate.captacao.AnuncioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.AtitudeDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.CandidatoCurriculoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.CandidatoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.CandidatoDaoHibernateTest_Junit4;
import com.fortes.rh.test.dao.hibernate.captacao.CandidatoIdiomaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.CandidatoSolicitacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.CandidatoSolicitacaoDaoHibernateTest_JUnit4;
import com.fortes.rh.test.dao.hibernate.captacao.CompetenciaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.ConfigHistoricoNivelDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.ConfiguracaoImpressaoCurriculoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.ConfiguracaoNivelCompetenciaCandidatoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.ConfiguracaoNivelCompetenciaColaboradorDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.ConfiguracaoNivelCompetenciaColaboradorDaoHibernateTest_JUnit4;
import com.fortes.rh.test.dao.hibernate.captacao.ConfiguracaoNivelCompetenciaCriterioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.ConfiguracaoNivelCompetenciaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.ConhecimentoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.CriterioAvaliacaoCompetenciaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.EmpresaBdsDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.EtapaSeletivaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.ExperienciaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.FormacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.HabilidadeDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.HistoricoCandidatoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.IdiomaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.MotivoSolicitacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.NivelCompetenciaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.NivelCompetenciaHistoricoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.SolicitacaoAvaliacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.SolicitacaoBDSDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.SolicitacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.SolicitacaoDaoHibernateTest_JUnit4;
import com.fortes.rh.test.dao.hibernate.cargosalario.CargoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.FaixaSalarialDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.FaixaSalarialHistoricoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.FaturamentoMensalDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.GrupoOcupacionalDaoHibernateTest_JUnit4;
import com.fortes.rh.test.dao.hibernate.cargosalario.HistoricoColaboradorDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.HistoricoColaboradorDaoHibernateTest_Junit4;
import com.fortes.rh.test.dao.hibernate.cargosalario.IndiceDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.IndiceHistoricoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.ReajusteColaboradorDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.ReajusteFaixaSalarialDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.ReajusteIndiceDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.RelatorioListaFrequenciaTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.TabelaReajusteColaboradorDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.AproveitamentoAvaliacaoCursoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.AvaliacaoCursoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.CertificacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.ColaboradorAvaliacaoPraticaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.ColaboradorCertificacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.ColaboradorPresencaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.ColaboradorTurmaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.ColaboradorTurmaDaoHibernateTest_JUnit4;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.CursoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.CursoLntDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.DNTDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.DiaTurmaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.LntDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.ParticipanteCursoLntDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.TurmaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.TurmaDaoHibernateTest_JUnit4;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.TurmaDocumentoAnexoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.AreaFormacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.AreaInteresseDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.AreaOrganizacionalDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.BairroDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.BeneficioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.CamposExtrasDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.CartaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.CidDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.CidadeDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.ClienteDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.CodigoCBODaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.ColaboradorDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.ColaboradorIdiomaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.ColaboradorOcorrenciaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.ColaboradorPeriodoExperienciaAvaliacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.ComoFicouSabendoVagaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.ConfiguracaoCampoExtraDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.ConfiguracaoCampoExtraVisivelObrigadotorioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.ConfiguracaoLimiteColaboradorDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.ConfiguracaoPerformanceDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.ConfiguracaoRelatorioDinamicoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.DependenteDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.DocumentoAnexoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.EmpresaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.EstabelecimentoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.EstadoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.GastoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.GastoEmpresaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.GastoEmpresaItemDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.GerenciadorComunicacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.GrupoACDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.GrupoGastoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.HistoricoBeneficioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.HistoricoColaboradorBeneficioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.MensagemDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.MotivoDemissaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.NoticiaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.OcorrenciaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.ParametrosDoSistemaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.ProvidenciaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.QuantidadeLimiteColaboradoresPorCargoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.TipoDespesaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.TipoDocumentoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.TurmaTipoDespesaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.UsuarioMensagemDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.AspectoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.AvaliacaoTurmaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.ColaboradorQuestionarioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.ColaboradorQuestionarioDaoHibernateTest_JUnit4;
import com.fortes.rh.test.dao.hibernate.pesquisa.ColaboradorRespostaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.EntrevistaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.FichaMedicaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.PerguntaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.PesquisaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.QuestionarioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.pesquisa.RespostaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.security.AuditorialDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.AfastamentoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.AgendaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.AmbienteDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.AnexoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.AreaVivenciaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.AreaVivenciaPcmatDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.AtividadeSegurancaPcmatDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.CandidatoEleicaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.CatDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.ClinicaAutorizadaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.ColaboradorAfastamentoDaoHibernateTest_JUnit4;
import com.fortes.rh.test.dao.hibernate.sesmt.ComissaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.ComissaoEleicaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.ComissaoMembroDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.ComissaoPeriodoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.ComissaoPlanoTrabalhoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.ComissaoReuniaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.ComissaoReuniaoPresencaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.EleicaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.EngenheiroResponsavelDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.EpcDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.EpcPcmatDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.EpiDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.EpiHistoricoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.EpiPcmatDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.EtapaProcessoEleitoralDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.EventoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.ExameDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.ExameSolicitacaoExameDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.ExtintorDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.ExtintorInspecaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.ExtintorManutencaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.FaseDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.FuncaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.HistoricoAmbienteDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.HistoricoExtintorDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.HistoricoFuncaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.MedicaoRiscoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.MedicoCoordenadorDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.MedidaSegurancaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.MotivoSolicitacaoEpiDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.NaturezaLesaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.ObraDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.OrdemDeServicoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.PcmatDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.ProntuarioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.RealizacaoExameDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.RiscoAmbienteDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.RiscoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.RiscoFuncaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.RiscoMedicaoRiscoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.SolicitacaoEpiDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.SolicitacaoEpiItemDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.SolicitacaoEpiItemDevolucaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.SolicitacaoEpiItemEntregaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.SolicitacaoExameDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.SolicitacaoExameDaoHibernateTest_Junit4;
import com.fortes.rh.test.dao.hibernate.sesmt.TestemunhaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.sesmt.TipoTamanhoEPIDaoHibernateTest;
import com.fortes.rh.test.dicionario.AptoTest;
import com.fortes.rh.test.dicionario.AreasFormacaoTest;
import com.fortes.rh.test.dicionario.DeficienciaTest;
import com.fortes.rh.test.dicionario.EnviarParaTest;
import com.fortes.rh.test.dicionario.EstadoTest;
import com.fortes.rh.test.dicionario.FiltrosRelatorioTest;
import com.fortes.rh.test.dicionario.MeioComunicacaoTest;
import com.fortes.rh.test.dicionario.MotivoExtintorManutencaoTest;
import com.fortes.rh.test.dicionario.MotivoHistoricoColaboradorTest;
import com.fortes.rh.test.dicionario.MotivoSolicitacaoExameTest;
import com.fortes.rh.test.dicionario.NivelIdiomaTest;
import com.fortes.rh.test.dicionario.OperacaoTest;
import com.fortes.rh.test.dicionario.OrigemCandidatoTest;
import com.fortes.rh.test.dicionario.ReajusteTest;
import com.fortes.rh.test.dicionario.SexoTest;
import com.fortes.rh.test.dicionario.SituacaoColaboradorTest;
import com.fortes.rh.test.dicionario.SituacaoFormacaoTest;
import com.fortes.rh.test.dicionario.SituacaoSolicitacaoTest;
import com.fortes.rh.test.dicionario.StatusCandidatoSolicitacaoTest;
import com.fortes.rh.test.dicionario.StatusRetornoACTest;
import com.fortes.rh.test.dicionario.TipoAcidenteTest;
import com.fortes.rh.test.dicionario.TipoAplicacaoIndiceTest;
import com.fortes.rh.test.dicionario.TipoAvaliacaoCursoTest;
import com.fortes.rh.test.dicionario.TipoAvaliacaoTest;
import com.fortes.rh.test.dicionario.TipoBuscaHistoricoColaboradorTest;
import com.fortes.rh.test.dicionario.TipoDeExposicaoTest;
import com.fortes.rh.test.dicionario.TipoExtintorTest;
import com.fortes.rh.test.dicionario.TipoFormacaoTest;
import com.fortes.rh.test.dicionario.TipoPerguntaTest;
import com.fortes.rh.test.dicionario.TipoQuestionarioTest;
import com.fortes.rh.test.dicionario.TipoReajusteColaboradorTest;
import com.fortes.rh.test.dicionario.TipoReajusteTest;
import com.fortes.rh.test.exception.AreaColaboradorExceptionTest;
import com.fortes.rh.test.exception.ColecaoVaziaExceptionTest;
import com.fortes.rh.test.exception.IntegraACExceptionTest;
import com.fortes.rh.test.model.acesso.UsuarioTest;
import com.fortes.rh.test.model.avaliacao.AcompanhamentoExperienciaColaboradorTest;
import com.fortes.rh.test.model.avaliacao.RelatorioAnaliseDesempenhoColaboradorTest;
import com.fortes.rh.test.model.captacao.CandidatoIdiomaTest;
import com.fortes.rh.test.model.captacao.CandidatoSolicitacaoTest;
import com.fortes.rh.test.model.captacao.CandidatoTest;
import com.fortes.rh.test.model.captacao.CtpsTest;
import com.fortes.rh.test.model.captacao.ExperienciaTest;
import com.fortes.rh.test.model.captacao.FormacaoTest;
import com.fortes.rh.test.model.cargosalario.HistoricoColaboradorTest;
import com.fortes.rh.test.model.cargosalario.RelatorioPromocoesTest;
import com.fortes.rh.test.model.desenvolvimento.CertificadoTest;
import com.fortes.rh.test.model.desenvolvimento.TurmaTest;
import com.fortes.rh.test.model.geral.AbsenteismoCollectionTest;
import com.fortes.rh.test.model.geral.AreaOrganizacionalTest;
import com.fortes.rh.test.model.geral.ColaboradorTest;
import com.fortes.rh.test.model.geral.ParametrosDoSistemaTest;
import com.fortes.rh.test.model.geral.PendenciaACTest;
import com.fortes.rh.test.model.geral.ReportColumnTest;
import com.fortes.rh.test.model.pesquisa.ColaboradorRespostaTest;
import com.fortes.rh.test.model.pesquisa.PerguntaTest;
import com.fortes.rh.test.model.pesquisa.ResultadoQuestionarioTest;
import com.fortes.rh.test.model.sesmt.AfastamentoTest;
import com.fortes.rh.test.model.sesmt.AgendaTest;
import com.fortes.rh.test.model.sesmt.AsoRelatorioTest;
import com.fortes.rh.test.model.sesmt.CandidatoEleicaoTest;
import com.fortes.rh.test.model.sesmt.ComissaoEleicaoTest;
import com.fortes.rh.test.model.sesmt.ComissaoTest;
import com.fortes.rh.test.model.sesmt.EtapaProcessoEleitoralTest;
import com.fortes.rh.test.model.sesmt.ExamesPrevistosRelatorioTest;
import com.fortes.rh.test.model.sesmt.PppRelatorioTest;
import com.fortes.rh.test.model.sesmt.SolicitacaoExameTest;
import com.fortes.rh.test.security.MenuTest;
import com.fortes.rh.test.security.UserDetailsImplTest;
import com.fortes.rh.test.thread.LntGerenciadorComunicacaoThreadTest;
import com.fortes.rh.test.util.ArquivoUtilTest;
import com.fortes.rh.test.util.AutenticadorTest;
import com.fortes.rh.test.util.AuthenticatorImplTest;
import com.fortes.rh.test.util.BooleanUtilTest;
import com.fortes.rh.test.util.CheckListBoxUtilTest;
import com.fortes.rh.test.util.CnpjUtilTest;
import com.fortes.rh.test.util.CollectionUtilTest;
import com.fortes.rh.test.util.ComparatorStringTest;
import com.fortes.rh.test.util.ConverterUtilTest;
import com.fortes.rh.test.util.DateUtilTest;
import com.fortes.rh.test.util.HistoricoColaboradorUtilTest;
import com.fortes.rh.test.util.IntegerUtilTest;
import com.fortes.rh.test.util.LongUtilTest;
import com.fortes.rh.test.util.MailTest;
import com.fortes.rh.test.util.MathUtilTest;
import com.fortes.rh.test.util.ModelUtilTest;
import com.fortes.rh.test.util.MontaRelatorioItextTest;
import com.fortes.rh.test.util.SpringUtilTest;
import com.fortes.rh.test.util.StringUtilTest;
import com.fortes.rh.test.util.ZipTest;
import com.fortes.rh.test.web.action.IndexTest;
import com.fortes.rh.test.web.action.MyActionSupportEditTest;
import com.fortes.rh.test.web.action.MyActionSupportListTest;
import com.fortes.rh.test.web.action.acesso.PerfilEditActionTest;
import com.fortes.rh.test.web.action.acesso.PerfilListActionTest;
import com.fortes.rh.test.web.action.acesso.UsuarioEditActionTest;
import com.fortes.rh.test.web.action.avaliacao.AvaliacaoDesempenhoEditActionTest;
import com.fortes.rh.test.web.action.avaliacao.AvaliacaoEditActionTest;
import com.fortes.rh.test.web.action.avaliacao.AvaliacaoEditActionTest_Junit4;
import com.fortes.rh.test.web.action.avaliacao.AvaliacaoExperienciaEditActionTest;
import com.fortes.rh.test.web.action.avaliacao.AvaliacaoPraticaEditActionTest;
import com.fortes.rh.test.web.action.avaliacao.PerguntaAvaliacaoEditActionTest;
import com.fortes.rh.test.web.action.avaliacao.PeriodoExperienciaEditActionTest;
import com.fortes.rh.test.web.action.avaliacao.PeriodoExperienciaEditActionTest_JUnit4;
import com.fortes.rh.test.web.action.backup.BackupActionTest;
import com.fortes.rh.test.web.action.captacao.AnuncioListActionTest;
import com.fortes.rh.test.web.action.captacao.AtitudeEditActionTest;
import com.fortes.rh.test.web.action.captacao.CandidatoEditActionTest;
import com.fortes.rh.test.web.action.captacao.CandidatoEditActionTest_JUnit4;
import com.fortes.rh.test.web.action.captacao.CandidatoListActionTest;
import com.fortes.rh.test.web.action.captacao.CandidatoSolicitacaoListActionTest;
import com.fortes.rh.test.web.action.captacao.CandidatoSolicitacaoListActionTest_JUnit4;
import com.fortes.rh.test.web.action.captacao.ConfigHistoricoNivelEditActionTest;
import com.fortes.rh.test.web.action.captacao.ConhecimentoEditActionTest;
import com.fortes.rh.test.web.action.captacao.ConhecimentoListActionTest;
import com.fortes.rh.test.web.action.captacao.EmpresaBdsEditActionTest;
import com.fortes.rh.test.web.action.captacao.EmpresaBdsListActionTest;
import com.fortes.rh.test.web.action.captacao.EtapaSeletivaEditActionTest;
import com.fortes.rh.test.web.action.captacao.EtapaSeletivaListActionTest;
import com.fortes.rh.test.web.action.captacao.HabilidadeEditActionTest;
import com.fortes.rh.test.web.action.captacao.MotivoSolicitacaoEditActionTest;
import com.fortes.rh.test.web.action.captacao.MotivoSolicitacaoListActionTest;
import com.fortes.rh.test.web.action.captacao.NivelCompetenciaEditActionTest;
import com.fortes.rh.test.web.action.captacao.NivelCompetenciaHistoricoEditActionTest;
import com.fortes.rh.test.web.action.captacao.SolicitacaoEditActionTest;
import com.fortes.rh.test.web.action.captacao.SolicitacaoListActionTest;
import com.fortes.rh.test.web.action.captacao.SolicitacaoListActionTest_JUnit4;
import com.fortes.rh.test.web.action.captacao.indicador.IndicadorDuracaoPreenchimentoVagaListActionTest;
import com.fortes.rh.test.web.action.captacao.indicador.IndicadorTurnOverListActionTest;
import com.fortes.rh.test.web.action.cargosalario.CargoEditActionTest;
import com.fortes.rh.test.web.action.cargosalario.FaixaSalarialHistoricoEditActionTest;
import com.fortes.rh.test.web.action.cargosalario.FaixaSalarialHistoricoListActionTest;
import com.fortes.rh.test.web.action.cargosalario.FaturamentoMensalEditActionTest;
import com.fortes.rh.test.web.action.cargosalario.IndiceEditActionTest;
import com.fortes.rh.test.web.action.cargosalario.IndiceHistoricoEditActionTest;
import com.fortes.rh.test.web.action.cargosalario.IndiceHistoricoListActionTest;
import com.fortes.rh.test.web.action.cargosalario.IndiceListActionTest;
import com.fortes.rh.test.web.action.cargosalario.ReajusteColaboradorEditActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.AvaliacaoCursoEditActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.AvaliacaoCursoListActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.CertificacaoListActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.ColaboradorAvaliacaoPraticaEditActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.ColaboradorPresencaEditActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.ColaboradorPresencaListActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.ColaboradorTurmaEditActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.ColaboradorTurmaListActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.CursoEditActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.CursoListActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.DNTEditActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.DNTListActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.DiaTurmaEditActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.DiaTurmaListActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.LntEditActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.PrioridadeTreinamentoEditActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.PrioridadeTreinamentoListActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.RelatorioPresencaActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.TurmaEditActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.TurmaEditActionTest_Junit4;
import com.fortes.rh.test.web.action.desenvolvimento.TurmaListActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.TurmaListActionTest_Junit4;
import com.fortes.rh.test.web.action.geral.AreaFormacaoEditActionTest;
import com.fortes.rh.test.web.action.geral.AreaFormacaoListActionTest;
import com.fortes.rh.test.web.action.geral.AreaInteresseEditActionTest;
import com.fortes.rh.test.web.action.geral.AreaInteresseListActionTest;
import com.fortes.rh.test.web.action.geral.BairroEditActionTest;
import com.fortes.rh.test.web.action.geral.BairroListActionTest;
import com.fortes.rh.test.web.action.geral.BeneficioEditActionTest;
import com.fortes.rh.test.web.action.geral.BeneficioListActionTest;
import com.fortes.rh.test.web.action.geral.CidEditActionTest;
import com.fortes.rh.test.web.action.geral.ClienteEditActionTest;
import com.fortes.rh.test.web.action.geral.CodigoCBOEditActionTest;
import com.fortes.rh.test.web.action.geral.ColaboradorDesligaActionTest;
import com.fortes.rh.test.web.action.geral.ColaboradorEditActionTest;
import com.fortes.rh.test.web.action.geral.ColaboradorEditActionTest_JUnit4;
import com.fortes.rh.test.web.action.geral.ColaboradorListActionTest;
import com.fortes.rh.test.web.action.geral.ColaboradorOcorrenciaEditActionTest;
import com.fortes.rh.test.web.action.geral.ColaboradorReportActionTest;
import com.fortes.rh.test.web.action.geral.ComoFicouSabendoVagaEditActionTest;
import com.fortes.rh.test.web.action.geral.ConfiguracaoRelatorioDinamicoEditActionTest;
import com.fortes.rh.test.web.action.geral.DocumentoAnexoEditActionTest;
import com.fortes.rh.test.web.action.geral.DocumentoAnexoListActionTest;
import com.fortes.rh.test.web.action.geral.EmpresaEditActionTest;
import com.fortes.rh.test.web.action.geral.EmpresaListActionTest;
import com.fortes.rh.test.web.action.geral.EstabelecimentoEditActionTest;
import com.fortes.rh.test.web.action.geral.EstabelecimentoListActionTest;
import com.fortes.rh.test.web.action.geral.GerenciadorComunicacaoEditActionTest;
import com.fortes.rh.test.web.action.geral.GrupoACEditActionTest;
import com.fortes.rh.test.web.action.geral.HistoricoBeneficioEditActionTest;
import com.fortes.rh.test.web.action.geral.HistoricoBeneficioListActionTest;
import com.fortes.rh.test.web.action.geral.HistoricoColaboradorBeneficioEditActionTest;
import com.fortes.rh.test.web.action.geral.HistoricoColaboradorBeneficioListActionTest;
import com.fortes.rh.test.web.action.geral.HistoricoColaboradorEditActionTest;
import com.fortes.rh.test.web.action.geral.HistoricoColaboradorListActionTest;
import com.fortes.rh.test.web.action.geral.MotivoDemissaoListActionTest;
import com.fortes.rh.test.web.action.geral.OcorrenciaEditActionTest;
import com.fortes.rh.test.web.action.geral.OcorrenciaProvidenciaEditActionTest;
import com.fortes.rh.test.web.action.geral.ParametrosDoSistemaEditActionTest;
import com.fortes.rh.test.web.action.geral.ProvidenciaEditActionTest;
import com.fortes.rh.test.web.action.geral.TipoDespesaEditActionTest;
import com.fortes.rh.test.web.action.geral.TipoDocumentoEditActionTest;
import com.fortes.rh.test.web.action.geral.UsuarioMensagemEditActionTest;
import com.fortes.rh.test.web.action.geral.UsuarioMensagemListActionTest;
import com.fortes.rh.test.web.action.pesquisa.AspectoEditActionTest;
import com.fortes.rh.test.web.action.pesquisa.AspectoListActionTest;
import com.fortes.rh.test.web.action.pesquisa.ColaboradorQuestionarioEditActionTest;
import com.fortes.rh.test.web.action.pesquisa.ColaboradorQuestionarioListActionTest;
import com.fortes.rh.test.web.action.pesquisa.ColaboradorQuestionarioListActionTest_JUnit4;
import com.fortes.rh.test.web.action.pesquisa.ColaboradorRespostaEditActionTest;
import com.fortes.rh.test.web.action.pesquisa.ColaboradorRespostaEditActionTest_JUnit4;
import com.fortes.rh.test.web.action.pesquisa.ColaboradorRespostaListActionTest;
import com.fortes.rh.test.web.action.pesquisa.EntrevistaListActionTest;
import com.fortes.rh.test.web.action.pesquisa.FichaMedicaListActionTest;
import com.fortes.rh.test.web.action.pesquisa.PerguntaEditActionTest;
import com.fortes.rh.test.web.action.pesquisa.PerguntaListActionTest;
import com.fortes.rh.test.web.action.pesquisa.PesquisaEditActionTest;
import com.fortes.rh.test.web.action.pesquisa.PesquisaListActionTest;
import com.fortes.rh.test.web.action.pesquisa.QuestionarioListActionTest;
import com.fortes.rh.test.web.action.pesquisa.QuestionarioListActionTest_Junit4;
import com.fortes.rh.test.web.action.sesmt.AfastamentoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.AgendaEditActionTest;
import com.fortes.rh.test.web.action.sesmt.AmbienteEditActionTest;
import com.fortes.rh.test.web.action.sesmt.AnexoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.AnexoListActionTest;
import com.fortes.rh.test.web.action.sesmt.AnuncioEditActionTest;
import com.fortes.rh.test.web.action.sesmt.AreaVivenciaEditActionTest;
import com.fortes.rh.test.web.action.sesmt.AreaVivenciaPcmatEditActionTest;
import com.fortes.rh.test.web.action.sesmt.AtividadeSegurancaPcmatEditActionTest;
import com.fortes.rh.test.web.action.sesmt.CandidatoEleicaoListActionTest;
import com.fortes.rh.test.web.action.sesmt.CatEditActionTest;
import com.fortes.rh.test.web.action.sesmt.CertificacaoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.ClinicaAutorizadaEditActionTest;
import com.fortes.rh.test.web.action.sesmt.ClinicaAutorizadaListActionTest;
import com.fortes.rh.test.web.action.sesmt.ColaboradorAfastamentoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.ColaboradorAfastamentoListActionTest;
import com.fortes.rh.test.web.action.sesmt.ComissaoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.ComissaoEleicaoListActionTest;
import com.fortes.rh.test.web.action.sesmt.ComissaoPeriodoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.ComissaoPlanoTrabalhoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.ComissaoReuniaoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.EleicaoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.EleicaoListActionTest;
import com.fortes.rh.test.web.action.sesmt.EngenheiroResponsavelEditActionTest;
import com.fortes.rh.test.web.action.sesmt.EngenheiroResponsavelListActionTest;
import com.fortes.rh.test.web.action.sesmt.EpcEditActionTest;
import com.fortes.rh.test.web.action.sesmt.EpcListActionTest;
import com.fortes.rh.test.web.action.sesmt.EpcPcmatEditActionTest;
import com.fortes.rh.test.web.action.sesmt.EpiEditActionTest;
import com.fortes.rh.test.web.action.sesmt.EpiHistoricoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.EpiListActionTest;
import com.fortes.rh.test.web.action.sesmt.EpiPcmatEditActionTest;
import com.fortes.rh.test.web.action.sesmt.EtapaProcessoEleitoralEditActionTest;
import com.fortes.rh.test.web.action.sesmt.EventoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.ExameEditActionTest;
import com.fortes.rh.test.web.action.sesmt.ExameListActionTest;
import com.fortes.rh.test.web.action.sesmt.ExameListActionTest_JUnit4;
import com.fortes.rh.test.web.action.sesmt.ExtintorEditActionTest;
import com.fortes.rh.test.web.action.sesmt.ExtintorInspecaoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.ExtintorManutencaoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.FaseEditActionTest;
import com.fortes.rh.test.web.action.sesmt.FichaMedicaEditActionTest;
import com.fortes.rh.test.web.action.sesmt.FuncaoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.FuncaoListActionTest;
import com.fortes.rh.test.web.action.sesmt.HistoricoAmbienteEditActionTest;
import com.fortes.rh.test.web.action.sesmt.HistoricoFuncaoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.HistoricoFuncaoListActionTest;
import com.fortes.rh.test.web.action.sesmt.IndicadorTreinamentosListActionTest;
import com.fortes.rh.test.web.action.sesmt.MedicaoRiscoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.MedicoCoordenadorEditActionTest;
import com.fortes.rh.test.web.action.sesmt.MedicoCoordenadorListActionTest;
import com.fortes.rh.test.web.action.sesmt.MedidaSegurancaEditActionTest;
import com.fortes.rh.test.web.action.sesmt.MotivoSolicitacaoEpiEditActionTest;
import com.fortes.rh.test.web.action.sesmt.NaturezaLesaoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.ObraEditActionTest;
import com.fortes.rh.test.web.action.sesmt.OrdemDeServicoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.PcmatEditActionTest;
import com.fortes.rh.test.web.action.sesmt.PcmsoListActionTest;
import com.fortes.rh.test.web.action.sesmt.PppEditActionTest;
import com.fortes.rh.test.web.action.sesmt.PpraEditActionTest;
import com.fortes.rh.test.web.action.sesmt.ProntuarioEditActionTest;
import com.fortes.rh.test.web.action.sesmt.RiscoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.SolicitacaoEpiEditActionTest;
import com.fortes.rh.test.web.action.sesmt.SolicitacaoEpiListActionTest;
import com.fortes.rh.test.web.action.sesmt.SolicitacaoEpiListActionTest_Junit4;
import com.fortes.rh.test.web.action.sesmt.SolicitacaoExameEditActionTest;
import com.fortes.rh.test.web.action.sesmt.SolicitacaoExameListActionTest;
import com.fortes.rh.test.web.action.sesmt.TamanhoEPIListActionTest;
import com.fortes.rh.test.web.action.sesmt.TamanhoEpiEditActionTest;
import com.fortes.rh.test.web.action.sesmt.TipoEpiEditActionTest;
import com.fortes.rh.test.web.action.sesmt.TipoEpiListActionTest;
import com.fortes.rh.test.web.chart.ProgressaoColaboradorTest;
import com.fortes.rh.test.web.chart.ResultadoPesquisaTest;
import com.fortes.rh.test.web.dwr.AfastamentoDWRTest;
import com.fortes.rh.test.web.dwr.AmbienteDWRTest;
import com.fortes.rh.test.web.dwr.AreaOrganizacionalDWRTest;
import com.fortes.rh.test.web.dwr.AreaOrganizacionalDWRTest_JUnit4;
import com.fortes.rh.test.web.dwr.AspectoDWRTest;
import com.fortes.rh.test.web.dwr.AvaliacaoDesempenhoDWRTest;
import com.fortes.rh.test.web.dwr.BairroDWRTest;
import com.fortes.rh.test.web.dwr.CandidatoDWRTest;
import com.fortes.rh.test.web.dwr.CargoDWRTest;
import com.fortes.rh.test.web.dwr.CidadeDWRTest;
import com.fortes.rh.test.web.dwr.ColaboradorDWRTest;
import com.fortes.rh.test.web.dwr.ColaboradorDWRTest_JUnit4;
import com.fortes.rh.test.web.dwr.ComissaoPeriodoDWRTest;
import com.fortes.rh.test.web.dwr.ComissaoPlanoTrabalhoDWRTest;
import com.fortes.rh.test.web.dwr.ComissaoReuniaoDWRTest;
import com.fortes.rh.test.web.dwr.CompetenciaDWRTest;
import com.fortes.rh.test.web.dwr.ConfiguracaoPerformanceDWRTest;
import com.fortes.rh.test.web.dwr.ConhecimentoDWRTest;
import com.fortes.rh.test.web.dwr.DiaTurmaDWRTest;
import com.fortes.rh.test.web.dwr.DntDWRTest;
import com.fortes.rh.test.web.dwr.EstabelecimentoDWRTest;
import com.fortes.rh.test.web.dwr.ExtintorDWRTest;
import com.fortes.rh.test.web.dwr.FaixaSalarialDWRTest;
import com.fortes.rh.test.web.dwr.FuncaoDWRTest;
import com.fortes.rh.test.web.dwr.GrupoOcupacionalDWRTest;
import com.fortes.rh.test.web.dwr.HistoricoCandidatoDWRTest;
import com.fortes.rh.test.web.dwr.ListaPresencaDWRTest;
import com.fortes.rh.test.web.dwr.MorroDWRTest;
import com.fortes.rh.test.web.dwr.OcorrenciaDWRTest;
import com.fortes.rh.test.web.dwr.OrdemDeServicoDWRTest;
import com.fortes.rh.test.web.dwr.ParticipanteCursoLntDWRTest;
import com.fortes.rh.test.web.dwr.PerguntaDWRTest;
import com.fortes.rh.test.web.dwr.ReajusteDWRTest;
import com.fortes.rh.test.web.dwr.RiscoAmbienteDWRTest;
import com.fortes.rh.test.web.dwr.RiscosDWRTest;
import com.fortes.rh.test.web.dwr.SolicitacaoDWRTest;
import com.fortes.rh.test.web.dwr.SolicitacaoExameDWRTest;
import com.fortes.rh.test.web.dwr.TurmaDWRTest;
import com.fortes.rh.test.web.dwr.TurmaDWRTest_Junit4;
import com.fortes.rh.test.web.dwr.UsuarioMensagemDWRTest;
import com.fortes.rh.test.web.dwr.lntDWRTest;
import com.fortes.rh.web.action.exportacao.ExportacaoActionTest;
import com.fortes.rh.web.action.externo.ExternoActionTest;
import com.fortes.test.web.tags.LinkTagTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	
//	SOMENTE PARA O COVERAGE
//	NoAllUnitTests.class,
//	IntegracaoACTests.class,
	
	PapelManagerTest.class,

	PapelDaoHibernateTest.class,
	PerfilDaoHibernateTest.class,
	PerfilManagerTest.class,
	UsuarioDaoHibernateTest.class,
	UsuarioManagerTest.class,
	UsuarioEditActionTest.class,
	UsuarioEmpresaDaoHibernateTest.class,
	UsuarioEmpresaManagerTest.class,
	UsuarioEmpresaManagerTest_JUnit4.class,

    //CAPTAÇÃO
	ConfiguracaoImpressaoCurriculoDaoHibernateTest.class,
	ConfiguracaoImpressaoCurriculoManagerTest.class,
	AnuncioListActionTest.class,
	AnuncioManagerTest.class,
	AnuncioDaoHibernateTest.class,
	CandidatoDaoHibernateTest.class,
	CandidatoDaoHibernateTest_Junit4.class,
	CandidatoManagerTest.class,
	CandidatoManagerTest_Junit4.class,
	CandidatoEditActionTest.class,
	CandidatoEditActionTest_JUnit4.class,
	CandidatoListActionTest.class,
	ConhecimentoEditActionTest.class,
	ConhecimentoListActionTest.class,
	ConhecimentoDaoHibernateTest.class,
	ConhecimentoManagerTest.class,
	        
	AtitudeEditActionTest.class,
	AtitudeDaoHibernateTest.class,
	AtitudeManagerTest.class,
	HabilidadeEditActionTest.class,
	HabilidadeDaoHibernateTest.class,
	HabilidadeManagerTest.class,
	DuracaoPreenchimentoVagaManagerTest.class,
	ExperienciaManagerTest.class,
	ExperienciaDaoHibernateTest.class,
	FormacaoDaoHibernateTest.class,
	FormacaoManagerTest.class,
	IdiomaDaoHibernateTest.class,
	SolicitacaoDaoHibernateTest.class,
	SolicitacaoDaoHibernateTest_JUnit4.class,
	SolicitacaoAvaliacaoManagerTest.class,
	SolicitacaoAvaliacaoDaoHibernateTest.class,
	SolicitacaoManagerTest.class,
	SolicitacaoManagerTest_Junit4.class,
	SolicitacaoListActionTest.class,
	SolicitacaoListActionTest_JUnit4.class,
	SolicitacaoBDSDaoHibernateTest.class,
	CandidatoIdiomaTest.class,
	CandidatoIdiomaManagerTest.class,
	CandidatoIdiomaDaoHibernateTest.class,
	EtapaSeletivaDaoHibernateTest.class,
	EtapaSeletivaManagerTest.class,
	EtapaSeletivaListActionTest.class,
	EtapaSeletivaEditActionTest.class,
	EmpresaBdsListActionTest.class,
	EmpresaBdsEditActionTest.class,
	EmpresaBdsManagerTest.class,
	EmpresaBdsDaoHibernateTest.class,
	HistoricoCandidatoManagerTest.class,
	HistoricoCandidatoDaoHibernateTest.class,
	RelatorioPromocoesTest.class,
	CandidatoSolicitacaoManagerTest.class,
	CandidatoSolicitacaoManagerTest_JUnit4.class,
	CandidatoSolicitacaoDaoHibernateTest.class,
	CandidatoSolicitacaoDaoHibernateTest_JUnit4.class,
	CandidatoSolicitacaoListActionTest.class,
	CandidatoSolicitacaoListActionTest_JUnit4.class,
	MotivoSolicitacaoListActionTest.class,
	MotivoSolicitacaoEditActionTest.class,
	MotivoSolicitacaoDaoHibernateTest.class,
	SolicitacaoBDSManagerTest.class,
	CandidatoCurriculoDaoHibernateTest.class,
	CandidatoCurriculoManagerTest.class,
	ExperienciaTest.class,
	FormacaoTest.class,
	CandidatoSolicitacaoTest.class,
	SolicitacaoEditActionTest.class,
	NivelCompetenciaEditActionTest.class,
	NivelCompetenciaManagerTest.class,
	NivelCompetenciaDaoHibernateTest.class,
	ConfiguracaoNivelCompetenciaDaoHibernateTest.class,
	ConfiguracaoNivelCompetenciaColaboradorDaoHibernateTest_JUnit4.class,
	ConfiguracaoNivelCompetenciaManagerTest.class,
	ConfiguracaoNivelCompetenciaColaboradorDaoHibernateTest.class,
	CompetenciaDaoHibernateTest.class,
	CompetenciaDWRTest.class,
	CtpsTest.class,
	ConfigHistoricoNivelDaoHibernateTest.class,
	ConfigHistoricoNivelManagerTest.class,
	NivelCompetenciaHistoricoManagerTest.class,
	ConfigHistoricoNivelEditActionTest.class,
	NivelCompetenciaHistoricoDaoHibernateTest.class,
	NivelCompetenciaHistoricoEditActionTest.class,
	ConfiguracaoNivelCompetenciaCriterioDaoHibernateTest.class,
	CriterioAvaliacaoCompetenciaDaoHibernateTest.class,
	ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerTest.class,
	ConfiguracaoCompetenciaAvaliacaoDesempenhoDaoHibernateTest.class,
	ParticipanteAvaliacaoDesempenhoManagerTest.class,
	ConfiguracaoNivelCompetenciaCandidatoManagerTest.class,
	ConfiguracaoNivelCompetenciaCandidatoDaoHibernateTest.class,
	ConfiguracaoNivelCompetenciaColaboradorManagerTest.class,

    //Indicadores
	IndicadorDuracaoPreenchimentoVagaListActionTest.class,
	IndicadorTurnOverListActionTest.class,

    //CARGOSALARIO
	CargoManagerTest.class,
	CargoDaoHibernateTest.class,
	CargoEditActionTest.class,

	FaixaSalarialDaoHibernateTest.class,
	FaixaSalarialManagerTest.class,
	FaixaSalarialManagerTest_JUnit4.class,
	FaixaSalarialHistoricoDaoHibernateTest.class,
	FaixaSalarialHistoricoManagerTest.class,
	FaixaSalarialHistoricoManagerTest_JUnit4.class,
	FaixaSalarialHistoricoListActionTest.class,
	FaixaSalarialHistoricoEditActionTest.class,

	GrupoOcupacionalManagerTest.class,
	GrupoOcupacionalDaoHibernateTest_JUnit4.class,

	HistoricoColaboradorDaoHibernateTest.class,
	HistoricoColaboradorDaoHibernateTest_Junit4.class,
	HistoricoColaboradorManagerTest.class,
	HistoricoColaboradorManagerTest_Junit4.class,
	HistoricoColaboradorListActionTest.class,
	HistoricoColaboradorEditActionTest.class,

	RelatorioListaFrequenciaTest.class,

	ColaboradorEditActionTest.class,
	ColaboradorEditActionTest_JUnit4.class,
	ColaboradorListActionTest.class,
	ColaboradorDesligaActionTest.class,

	ReajusteColaboradorDaoHibernateTest.class,
	ReajusteColaboradorManagerTest.class,
	ReajusteColaboradorEditActionTest.class,

	ReajusteFaixaSalarialDaoHibernateTest.class,
	ReajusteIndiceDaoHibernateTest.class,

	TabelaReajusteColaboradorDaoHibernateTest.class,
	TabelaReajusteColaboradorManagerTest.class,
	IndiceEditActionTest.class,
	IndiceListActionTest.class,
	IndiceDaoHibernateTest.class,
	IndiceManagerTest.class,
	IndiceHistoricoDaoHibernateTest.class,
	IndiceHistoricoManagerTest.class,
	IndiceHistoricoEditActionTest.class,
	IndiceHistoricoListActionTest.class,

	ConfiguracaoLimiteColaboradorDaoHibernateTest.class,
	QuantidadeLimiteColaboradoresPorCargoDaoHibernateTest.class,
	QuantidadeLimiteColaboradoresPorCargoManagerTest.class,
	FaturamentoMensalEditActionTest.class,
	FaturamentoMensalManagerTest.class,
	FaturamentoMensalDaoHibernateTest.class,

    //DESENVOLVIMENTO
	CursoDaoHibernateTest.class,
	CursoManagerTest.class,
	CursoEditActionTest.class,
	CursoListActionTest.class,

	DiaTurmaDaoHibernateTest.class,
	DiaTurmaEditActionTest.class,
	DiaTurmaListActionTest.class,
	DiaTurmaManagerTest.class,

	TurmaTest.class,
	TurmaDaoHibernateTest.class,
	TurmaDaoHibernateTest_JUnit4.class,
	TurmaEditActionTest.class,
	TurmaEditActionTest_Junit4.class,
	TurmaListActionTest.class,
	TurmaListActionTest_Junit4.class,
	TurmaManagerTest.class,
	TurmaManagerTest_Junit4.class,
	        
	TurmaDocumentoAnexoDaoHibernateTest.class,
	TurmaDocumentoAnexoManagerTest.class,

	DNTDaoHibernateTest.class,
	DNTManagerTest.class,
	DNTEditActionTest.class,
	DNTListActionTest.class,

	ColaboradorTurmaEditActionTest.class,
	ColaboradorTurmaListActionTest.class,
	ColaboradorTurmaManagerTest.class,
	ColaboradorTurmaManagerTest_Junit4.class,
	ColaboradorTurmaDaoHibernateTest.class,
	ColaboradorTurmaDaoHibernateTest_JUnit4.class,

	ColaboradorPresencaEditActionTest.class,
	ColaboradorPresencaListActionTest.class,
	ColaboradorPresencaDaoHibernateTest.class,
	ColaboradorPresencaManagerTest.class,

	PrioridadeTreinamentoEditActionTest.class,
	PrioridadeTreinamentoListActionTest.class,

	RelatorioPresencaActionTest.class,

	AvaliacaoCursoDaoHibernateTest.class,
	AvaliacaoCursoEditActionTest.class,
	AvaliacaoCursoListActionTest.class,

	AproveitamentoAvaliacaoCursoDaoHibernateTest.class,
	AproveitamentoAvaliacaoCursoManagerTest.class,
	AproveitamentoAvaliacaoCursoManagerTest_JUnit4.class,

	CertificacaoDaoHibernateTest.class,
	CertificacaoManagerTest.class,
	CertificacaoListActionTest.class,
	CertificadoTest.class,
	        
	ColaboradorAvaliacaoPraticaDaoHibernateTest.class,
	ColaboradorAvaliacaoPraticaManagerTest.class,
	ColaboradorAvaliacaoPraticaEditActionTest.class,
	        
	ColaboradorCertificacaoDaoHibernateTest.class,
	ColaboradorCertificacaoManagerTest.class,
	
	LntDaoHibernateTest.class,
	LntManagerTest.class,
	LntEditActionTest.class,
	LntGerenciadorComunicacaoThreadTest.class,
	
	CursoLntDaoHibernateTest.class,
	CursoLntManagerTest.class,
	ParticipanteCursoLntDaoHibernateTest.class,
	

    // Backup
	BackupActionTest.class,
	        
    //GERAL
	ParametrosDoSistemaTest.class,
	AreaFormacaoManagerTest.class,
	AreaFormacaoDaoHibernateTest.class,
	AreaFormacaoListActionTest.class,
	AreaFormacaoEditActionTest.class,
	AreaInteresseDaoHibernateTest.class,
	AreaInteresseManagerTest.class,
	AreaInteresseListActionTest.class,
	AreaInteresseEditActionTest.class,
	AreaOrganizacionalDaoHibernateTest.class,
	AreaOrganizacionalManagerTest.class,
	ColaboradorTest.class,
	ColaboradorManagerTest.class,
	ColaboradorManagerTest_Junit4.class,
	ColaboradorDaoHibernateTest.class,
	ColaboradorOcorrenciaEditActionTest.class,
	ColaboradorOcorrenciaDaoHibernateTest.class,
	ColaboradorOcorrenciaManagerTest.class,
	ColaboradorIdiomaManagerTest.class,
	ColaboradorIdiomaDaoHibernateTest.class,
	ImportadorGastosACTest.class,
	EmpresaManagerTest.class,
	EmpresaManagerTest_JUnit4.class,
	ConfiguracaoCampoExtraVisivelObrigadotorioManagerTest.class,
	ConfiguracaoCampoExtraVisivelObrigadotorioDaoHibernateTest.class,
	EmpresaListActionTest.class,
	EmpresaEditActionTest.class,
	EstabelecimentoEditActionTest.class,
	EstabelecimentoDaoHibernateTest.class,
	EstabelecimentoListActionTest.class,
	EstabelecimentoManagerTest.class,
	BeneficioListActionTest.class,
	BeneficioEditActionTest.class,
	BeneficioDaoHibernateTest.class,
	BeneficioManagerTest.class,
	HistoricoBeneficioDaoHibernateTest.class,
	HistoricoBeneficioManagerTest.class,
	HistoricoBeneficioEditActionTest.class,
	HistoricoBeneficioListActionTest.class,
	BairroEditActionTest.class,
	BairroListActionTest.class,
	BairroManagerTest.class,
	BairroDaoHibernateTest.class,
	CidadeManagerTest.class,
	CidadeDaoHibernateTest.class,
	ColaboradorReportActionTest.class,
	UsuarioMensagemDaoHibernateTest.class,
	UsuarioMensagemManagerTest.class,
	UsuarioMensagemManagerTest_Junit4.class,
	UsuarioMensagemEditActionTest.class,
	UsuarioMensagemListActionTest.class,
	UsuarioMensagemDWRTest.class,
	MensagemManagerTest.class,
	OcorrenciaEditActionTest.class,
	OcorrenciaDaoHibernateTest.class,
	OcorrenciaManagerTest.class,
	EstadoDaoHibernateTest.class,
	EstadoManagerTest.class,
	CamposExtrasDaoHibernateTest.class,
	CamposExtrasManagerTest.class,
	ConfiguracaoPerformanceManagerTest.class,
	ConfiguracaoPerformanceDaoHibernateTest.class,
	ConfiguracaoPerformanceDWRTest.class,
	ConfiguracaoRelatorioDinamicoDaoHibernateTest.class,
	ConfiguracaoRelatorioDinamicoManagerTest.class,
	ConfiguracaoRelatorioDinamicoEditActionTest.class,
	GrupoACDaoHibernateTest.class,
	GrupoACEditActionTest.class,
	GrupoACManagerTest.class,
	CodigoCBODaoHibernateTest.class,
	CidDaoHibernateTest.class,
	ConfiguracaoCampoExtraDaoHibernateTest.class,
	CodigoCBOEditActionTest.class,
	CidEditActionTest.class,
	ComoFicouSabendoVagaDaoHibernateTest.class,
	ComoFicouSabendoVagaManagerTest.class,
	ComoFicouSabendoVagaEditActionTest.class,
	ImportacaoColaboradorManagerTest.class,
	AbsenteismoCollectionTest.class,
	ColaboradorPeriodoExperienciaAvaliacaoManagerTest.class,
	ColaboradorPeriodoExperienciaAvaliacaoDaoHibernateTest.class,
	TipoDocumentoEditActionTest.class,
	TipoDocumentoManagerTest.class,
	TipoDocumentoDaoHibernateTest.class,
	TipoDespesaEditActionTest.class,
	TipoDespesaManagerTest.class,
	TipoDespesaDaoHibernateTest.class,
	TurmaTipoDespesaDaoHibernateTest.class,
	TurmaTipoDespesaManagerTest.class,
	ProvidenciaDaoHibernateTest.class,
	ProvidenciaManagerTest.class,
	ProvidenciaEditActionTest.class,
	GerenciadorComunicacaoDaoHibernateTest.class,
	GerenciadorComunicacaoManagerTest_Junit4.class,
	GerenciadorComunicacaoManagerTest.class,
	GerenciadorComunicacaoEditActionTest.class,
	OcorrenciaProvidenciaEditActionTest.class,
	
	CartaoManagerTest.class,
	CartaoDaoHibernateTest.class,

	        
    //contatoTest embedded Colaborador
	DependenteDaoHibernateTest.class,
	EmpresaDaoHibernateTest.class,
    //endereçoTest embedded Colaborador
	GrupoGastoDaoHibernateTest.class,
	GrupoGastoManagerTest.class,
	ParametrosDoSistemaDaoHibernateTest.class,
    //pessoalTest embedded Colaborador
	MotivoDemissaoDaoHibernateTest.class,
	MotivoDemissaoListActionTest.class,
	MotivoDemissaoManagerTest.class,
	DocumentoAnexoDaoHibernateTest.class,
	DocumentoAnexoManagerTest.class,
	DocumentoAnexoListActionTest.class,
	DocumentoAnexoEditActionTest.class,
	PerfilListActionTest.class,
	PerfilEditActionTest.class,
	MensagemDaoHibernateTest.class,
	ParametrosDoSistemaManagerTest.class,
	ParametrosDoSistemaEditActionTest.class,

    // beneficios
	HistoricoColaboradorBeneficioEditActionTest.class,
	HistoricoColaboradorBeneficioListActionTest.class,
	HistoricoColaboradorBeneficioDaoHibernateTest.class,
	HistoricoColaboradorBeneficioManagerTest.class,

    // gastos
	GastoEmpresaManagerTest.class,
	GastoManagerTest.class,
	GastoDaoHibernateTest.class,
	GastoEmpresaDaoHibernateTest.class,
	GastoEmpresaItemDaoHibernateTest.class,

    //PESQUISA
	ColaboradorRespostaEditActionTest.class,
	ColaboradorRespostaManagerTest.class,
	ColaboradorRespostaManagerTest_Junit4.class,
	ColaboradorRespostaDaoHibernateTest.class,
	ColaboradorRespostaListActionTest.class,
	PerguntaTest.class,
	PerguntaDaoHibernateTest.class,
	PerguntaManagerTest.class,
	PerguntaListActionTest.class,
	PerguntaEditActionTest.class,
	RespostaDaoHibernateTest.class,
	RespostaManagerTest.class,
	AspectoDaoHibernateTest.class,
	AspectoManagerTest.class,
	AspectoEditActionTest.class,
	AspectoListActionTest.class,
	QuestionarioDaoHibernateTest.class,
	QuestionarioManagerTest.class,
	PesquisaDaoHibernateTest.class,
	PesquisaManagerTest.class,
	QuestionarioListActionTest.class,
	QuestionarioListActionTest_Junit4.class,
	PesquisaListActionTest.class,
	PesquisaEditActionTest.class,
	LembretePesquisaTest.class,
	ColaboradorQuestionarioDaoHibernateTest.class,
	ColaboradorQuestionarioDaoHibernateTest_JUnit4.class,
	ColaboradorQuestionarioManagerTest.class,
	ColaboradorQuestionarioListActionTest.class,
	ColaboradorQuestionarioEditActionTest.class,
	EntrevistaDaoHibernateTest.class,
	EntrevistaManagerTest.class,
	EntrevistaListActionTest.class,
	FichaMedicaDaoHibernateTest.class,
	FichaMedicaManagerTest.class,
	FichaMedicaListActionTest.class,
	FichaMedicaEditActionTest.class,
	ResultadoQuestionarioTest.class,
	ColaboradorRespostaEditActionTest_JUnit4.class,
	ColaboradorQuestionarioListActionTest_JUnit4.class,

    //SESMT
	NaturezaLesaoDaoHibernateTest.class,
	NaturezaLesaoEditActionTest.class,
	NaturezaLesaoManagerTest.class,
	        
	ProntuarioDaoHibernateTest.class,
	ProntuarioManagerTest.class,
	ProntuarioEditActionTest.class,

	AnexoDaoHibernateTest.class,
	AnexoManagerTest.class,

	CatDaoHibernateTest.class,
	CatManagerTest.class,
	CatEditActionTest.class,
	TestemunhaManagerTest.class,
	TestemunhaDaoHibernateTest.class,
	FuncaoManagerTest.class,
	FuncaoDaoHibernateTest.class,
	FuncaoListActionTest.class,
	FuncaoEditActionTest.class,

	HistoricoFuncaoManagerTest.class,
	HistoricoFuncaoDaoHibernateTest.class,
	HistoricoFuncaoListActionTest.class,
	HistoricoFuncaoEditActionTest.class,

	AmbienteEditActionTest.class,
	AmbienteDaoHibernateTest.class,
	AmbienteManagerTest.class,
	AmbienteManagerTest_JUnit4.class,

	HistoricoAmbienteDaoHibernateTest.class,
	HistoricoAmbienteManagerTest.class,
	HistoricoAmbienteEditActionTest.class,

	RiscoAmbienteDaoHibernateTest.class,
	RiscoAmbienteManagerTest.class,
	RiscoAmbienteDWRTest.class,

	RiscoFuncaoManagerTest.class,
	RiscoFuncaoDaoHibernateTest.class,

	TipoEpiEditActionTest.class,
	TipoEpiListActionTest.class,

	EpiEditActionTest.class,
	EpiListActionTest.class,
	EpiManagerTest.class,
	EpiDaoHibernateTest.class,

	RiscoDaoHibernateTest.class,
	RiscoManagerTest.class,
	RiscoEditActionTest.class,

	AnexoListActionTest.class,
	AnexoEditActionTest.class,

	PpraEditActionTest.class,

	EpcListActionTest.class,
	EpcEditActionTest.class,
	EpcDaoHibernateTest.class,
	EpcManagerTest.class,

	SolicitacaoExameDaoHibernateTest.class,
	SolicitacaoExameDaoHibernateTest_Junit4.class,
	SolicitacaoExameManagerTest.class,
	ExameManagerTest_JUnit4.class,
	SolicitacaoExameListActionTest.class,
	SolicitacaoExameEditActionTest.class,
	SolicitacaoExameDWRTest.class,

	ExameSolicitacaoExameDaoHibernateTest.class,
	ExameSolicitacaoExameManagerTest.class,

	RealizacaoExameDaoHibernateTest.class,
	RealizacaoExameManagerTest.class,

	ExameListActionTest.class,
	ExameListActionTest_JUnit4.class,
	ExameEditActionTest.class,
	ExameDaoHibernateTest.class,
	ExameManagerTest.class,
	ExamesPrevistosRelatorioTest.class,

	EngenheiroResponsavelEditActionTest.class,
	EngenheiroResponsavelListActionTest.class,
	EngenheiroResponsavelDaoHibernateTest.class,
	EngenheiroResponsavelManagerTest.class,

	MedicoCoordenadorEditActionTest.class,
	MedicoCoordenadorListActionTest.class,
	MedicoCoordenadorDaoHibernateTest.class,
	MedicoCoordenadorManagerTest.class,

	PcmsoListActionTest.class,
	PcmsoManagerTest.class,

	PppEditActionTest.class,

	EpiHistoricoEditActionTest.class,
	EpiHistoricoManagerTest.class,
	EpiHistoricoDaoHibernateTest.class,

	ClinicaAutorizadaEditActionTest.class,
	ClinicaAutorizadaListActionTest.class,
	ClinicaAutorizadaManagerTest.class,
	ClinicaAutorizadaDaoHibernateTest.class,

	SolicitacaoEpiDaoHibernateTest.class,
	SolicitacaoEpiManagerTest.class,
	SolicitacaoEpiManagerTest_Junit4.class,
	SolicitacaoEpiItemDaoHibernateTest.class,
	SolicitacaoEpiItemManagerTest.class,
	        
	SolicitacaoEpiItemEntregaManagerTest.class,
	SolicitacaoEpiItemEntregaDaoHibernateTest.class,

	SolicitacaoEpiItemDevolucaoManagerTest.class,
	SolicitacaoEpiItemDevolucaoDaoHibernateTest.class,
	SolicitacaoEpiEditActionTest.class,
	SolicitacaoEpiListActionTest.class,
	SolicitacaoEpiListActionTest_Junit4.class,

	EtapaProcessoEleitoralTest.class,
	EtapaProcessoEleitoralDaoHibernateTest.class,
	EtapaProcessoEleitoralManagerTest.class,
	EtapaProcessoEleitoralEditActionTest.class,

	EleicaoDaoHibernateTest.class,
	EleicaoManagerTest.class,
	EleicaoManagerTest_Junit4.class,
	EleicaoListActionTest.class,
	EleicaoEditActionTest.class,

	CandidatoEleicaoDaoHibernateTest.class,
	CandidatoEleicaoManagerTest.class,
	CandidatoEleicaoTest.class,
	CandidatoEleicaoListActionTest.class,

	ComissaoTest.class,
	ComissaoEleicaoDaoHibernateTest.class,
	ComissaoEleicaoManagerTest.class,
	ComissaoEleicaoTest.class,
	ComissaoEleicaoListActionTest.class,

	ComissaoDaoHibernateTest.class,
	ComissaoManagerTest.class,
	ComissaoEditActionTest.class,

	ComissaoPeriodoDaoHibernateTest.class,
	ComissaoPeriodoManagerTest.class,
	ComissaoPeriodoEditActionTest.class,
	ComissaoPeriodoDWRTest.class,

	ComissaoMembroDaoHibernateTest.class,
	ComissaoMembroManagerTest.class,

	ComissaoReuniaoDaoHibernateTest.class,
	ComissaoReuniaoManagerTest.class,
	ComissaoReuniaoManagerTest_Junit4.class,
	ComissaoReuniaoEditActionTest.class,
	ComissaoReuniaoDWRTest.class,

	ComissaoReuniaoPresencaDaoHibernateTest.class,
	ComissaoReuniaoPresencaManagerTest.class,

	ComissaoPlanoTrabalhoDaoHibernateTest.class,
	ComissaoPlanoTrabalhoManagerTest.class,
	ComissaoPlanoTrabalhoEditActionTest.class,
	ComissaoPlanoTrabalhoDWRTest.class,

	AfastamentoDaoHibernateTest.class,
	AfastamentoManagerTest.class,
	AfastamentoEditActionTest.class,
	AfastamentoDWRTest.class,

	ColaboradorAfastamentoDaoHibernateTest_JUnit4.class,
	ColaboradorAfastamentoManagerTest_JUnit4.class,
	ColaboradorAfastamentoManagerTest.class,
	ColaboradorAfastamentoEditActionTest.class,
	ColaboradorAfastamentoListActionTest.class,

	ExtintorDaoHibernateTest.class,
	ExtintorManagerTest.class,
	ExtintorEditActionTest.class,
	ExtintorDWRTest.class,

	HistoricoExtintorDaoHibernateTest.class,
	HistoricoExtintorManagerTest.class,

	ExtintorInspecaoDaoHibernateTest.class,
	ExtintorInspecaoManagerTest.class,
	ExtintorInspecaoEditActionTest.class,

	ExtintorManutencaoDaoHibernateTest.class,
	ExtintorManutencaoManagerTest.class,
	ExtintorManutencaoEditActionTest.class,
	MotivoExtintorManutencaoTest.class,

	MedicaoRiscoDaoHibernateTest.class,
	MedicaoRiscoManagerTest.class,
	MedicaoRiscoEditActionTest.class,

	RiscoMedicaoRiscoDaoHibernateTest.class,
	RiscoMedicaoRiscoManagerTest.class,

	EventoDaoHibernateTest.class,
	EventoEditActionTest.class,

	AgendaTest.class,
	AgendaDaoHibernateTest.class,
	AgendaManagerTest.class,
	AgendaEditActionTest.class,

	PppRelatorioTest.class,

	AsoRelatorioTest.class,
	AnuncioEditActionTest.class,

	IndicadorTreinamentosListActionTest.class,
	AreaFormacaoManagerTest.class,
	CertificacaoEditActionTest.class,
	        
	ObraDaoHibernateTest.class,
	ObraManagerTest.class,
	ObraEditActionTest.class,
	        
	FaseDaoHibernateTest.class,
	FaseManagerTest.class,
	FaseEditActionTest.class,
	        
	MedidaSegurancaDaoHibernateTest.class,
	MedidaSegurancaManagerTest.class,
	MedidaSegurancaEditActionTest.class,
	        
	AreaVivenciaDaoHibernateTest.class,
	AreaVivenciaManagerTest.class,
	AreaVivenciaEditActionTest.class,
	        
	PcmatDaoHibernateTest.class,
	PcmatManagerTest.class,
	PcmatEditActionTest.class,

	AreaVivenciaPcmatDaoHibernateTest.class,
	AreaVivenciaPcmatManagerTest.class,
	AreaVivenciaPcmatEditActionTest.class,
	        
	AtividadeSegurancaPcmatDaoHibernateTest.class,
	AtividadeSegurancaPcmatManagerTest.class,
	AtividadeSegurancaPcmatEditActionTest.class,
	        
	EpiPcmatDaoHibernateTest.class,
	EpiPcmatManagerTest.class,
	EpiPcmatEditActionTest.class,
	        
	EpcPcmatDaoHibernateTest.class,
	EpcPcmatManagerTest.class,
	EpcPcmatEditActionTest.class,
	        
	MotivoSolicitacaoEpiDaoHibernateTest.class,
	MotivoSolicitacaoEpiEditActionTest.class,
	        
	TamanhoEPIManagerTest.class,
	TamanhoEpiEditActionTest.class,
	TamanhoEPIListActionTest.class,

	TipoTamanhoEPIDaoHibernateTest.class,
	TipoEpiEditActionTest.class,
	TipoEpiListActionTest.class,
	
	OrdemDeServicoDaoHibernateTest.class,
	OrdemDeServicoManagerTest.class,
	OrdemDeServicoEditActionTest.class,
	OrdemDeServicoDWRTest.class,
	        
    // UTILITARIOS
	AutenticadorTest.class,
	AuthenticatorImplTest.class,
	CheckListBoxUtilTest.class,
	CnpjUtilTest.class,
	CollectionUtilTest.class,
	ComparatorStringTest.class,
	ConverterUtilTest.class,
	DateUtilTest.class,
	HistoricoColaboradorUtilTest.class,
	LongUtilTest.class,
	IntegerUtilTest.class,
	MailTest.class,
	MathUtilTest.class,
	MontaRelatorioItextTest.class,
	SpringUtilTest.class,
	StringUtilTest.class,
	ModelUtilTest.class,
	BooleanUtilTest.class,
	ZipTest.class,
	ArquivoUtilTest.class,
	NoticiaDaoHibernateTest.class,

    // Avaliacao
	AvaliacaoDaoHibernateTest.class,
	AvaliacaoDaoHibernateTest_Junit4.class,
	AvaliacaoManagerTest.class,
	AvaliacaoManagerTest_JUnit4.class,
	AvaliacaoEditActionTest.class,
	AvaliacaoEditActionTest_Junit4.class,
	AvaliacaoTurmaDaoHibernateTest.class,
	        
    // Avaliacao de Experiencia
	PeriodoExperienciaDaoHibernateTest.class,
	PeriodoExperienciaDaoHibernateTest_Junit4.class,
	PeriodoExperienciaManagerTest.class,
	PeriodoExperienciaManagerTest_Junit4.class,
	PeriodoExperienciaEditActionTest.class,
	PeriodoExperienciaEditActionTest_JUnit4.class,
	AvaliacaoExperienciaEditActionTest.class,
	PerguntaAvaliacaoEditActionTest.class,
	AcompanhamentoExperienciaColaboradorTest.class,

    // Avaliacao de Desempenho
	AvaliacaoDesempenhoDaoHibernateTest.class,
	AvaliacaoDesempenhoManagerTest.class,
	AvaliacaoDesempenhoManagerTest_Junit4.class,
	AvaliacaoDesempenhoEditActionTest.class,
	RelatorioAnaliseDesempenhoColaboradorTest.class,
	        
	ConfiguracaoCompetenciaAvaliacaoDesempenhoDaoHibernateTest.class,

    //Avaliação Prática
	AvaliacaoPraticaDaoHibernateTest.class,
	AvaliacaoPraticaManagerTest.class,
	AvaliacaoPraticaEditActionTest.class,
	        
    // DICIONARIO
	StatusRetornoACTest.class,
	TipoAplicacaoIndiceTest.class,
	TipoPerguntaTest.class,
	TipoQuestionarioTest.class,
	AreasFormacaoTest.class,
	DeficienciaTest.class,
	EstadoTest.class,
	FiltrosRelatorioTest.class,
	MotivoHistoricoColaboradorTest.class,
	NivelIdiomaTest.class,
	OrigemCandidatoTest.class,
	SexoTest.class,
	SituacaoColaboradorTest.class,
	SituacaoFormacaoTest.class,
	SituacaoSolicitacaoTest.class,
	TipoAvaliacaoTest.class,
	TipoBuscaHistoricoColaboradorTest.class,
	TipoDeExposicaoTest.class,
	TipoFormacaoTest.class,
	TipoReajusteTest.class,
	TipoReajusteColaboradorTest.class,
	TipoExtintorTest.class,
	MotivoSolicitacaoExameTest.class,
	AptoTest.class,
	TipoAcidenteTest.class,
	TipoAvaliacaoCursoTest.class,
	StatusCandidatoSolicitacaoTest.class,
	OperacaoTest.class,
	MeioComunicacaoTest.class,
	EnviarParaTest.class,
	NivelIdiomaTest.class,
	ReajusteTest.class,

    //EXCEPTIONS
	ColecaoVaziaExceptionTest.class,
	IntegraACExceptionTest.class,
	AreaColaboradorExceptionTest.class,

    //WEB ACTION
	IndexTest.class,
	MyActionSupportEditTest.class,
	MyActionSupportListTest.class,

    //DWR
	AmbienteDWRTest.class,
	AreaOrganizacionalDWRTest.class,
	AreaOrganizacionalDWRTest_JUnit4.class,
	AspectoDWRTest.class,
	BairroDWRTest.class,
	CandidatoDWRTest.class,
	CargoDWRTest.class,
	CidadeDWRTest.class,
	ColaboradorDWRTest.class,
	ColaboradorDWRTest_JUnit4.class,
	ConhecimentoDWRTest.class,
	DiaTurmaDWRTest.class,
	DntDWRTest.class,
	EstabelecimentoDWRTest.class,
	GrupoOcupacionalDWRTest.class,
	FuncaoDWRTest.class,
	ReajusteDWRTest.class,
	HistoricoCandidatoDWRTest.class,
	ListaPresencaDWRTest.class,
	PerguntaDWRTest.class,
	RiscosDWRTest.class,
	TurmaDWRTest.class,
	TurmaDWRTest_Junit4.class,
	FaixaSalarialDWRTest.class,
	SolicitacaoDWRTest.class,
	OcorrenciaDWRTest.class,
	AvaliacaoDesempenhoDWRTest.class,
	MorroDWRTest.class,
	lntDWRTest.class,
	ParticipanteCursoLntDWRTest.class,

    //MODEL
	HistoricoColaboradorTest.class,
	UsuarioTest.class,
	ColaboradorRespostaTest.class,
	AfastamentoTest.class,
	CandidatoTest.class,
	PendenciaACTest.class,
	AreaOrganizacionalTest.class,
	ReportColumnTest.class,
	SolicitacaoExameTest.class,

	ResultadoPesquisaTest.class,
	ProgressaoColaboradorTest.class,

	AuditorialDaoHibernateTest.class,
	CoberturaGetSetTest.class,

    //SECURITY
	AuditoriaManagerTest.class,
	MenuTest.class,
	UserDetailsImplTest.class,

    //crud de Clientes somente para uso interno na Fortes Informática
	ClienteDaoHibernateTest.class,
	ClienteManagerTest.class,
	ClienteEditActionTest.class,
	        
    // Config e Listener
	Log4jInitListenerTest.class,
	LogCleanerJobTest.class,
	ScriptReaderTest.class,

    // BACKUP e Ant Script
	BackupCleanerJobTest.class,
	BackupJobTest.class,
	BackupServiceImplTest.class,
	RunAntScriptTest.class,

    // AUDITORIA
	AuditoriaGeralAdviceTest.class,
	AuditoriaPointcutTest.class,
	GeraDadosAuditadosTest.class,
	ProcuraChaveNaEntidadeTest.class,
	InsertAuditorCallbackImplTest.class,
	RemoveAuditorCallbackImplTest.class,
	UpdateAuditorCallbackImplTest.class,
	ColaboradorQuestionarioAuditorCallbackImplTest.class,
	DateFormatJsonValueProcessorTest.class,
	AbstractModelJsonValueProcessorTest.class,
	CandidatoSolicitacaoAuditorCallbackImplTest.class,
	        
	LinkTagTest.class,
	ExportacaoActionTest.class,

    //SERVICES
	RHServiceTest.class,
	RHServiceIntranetTest.class,
	
	//EXTERNO
	ExternoActionTest.class
	
})
public class AllUnitTests { }


