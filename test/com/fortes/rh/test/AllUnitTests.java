package com.fortes.rh.test;


import junit.framework.TestSuite;

import com.fortes.f2rh.test.F2rhFacadeTest;
import com.fortes.rh.config.Log4jInitListenerTest;
import com.fortes.rh.config.LogCleanerJobTest;
import com.fortes.rh.config.ScriptReaderTest;
import com.fortes.rh.config.backup.BackupCleanerJobTest;
import com.fortes.rh.config.backup.BackupJobTest;
import com.fortes.rh.config.backup.BackupServiceImplTest;
import com.fortes.rh.config.backup.RunAntScriptTest;
import com.fortes.rh.security.spring.aop.AbstractModelJsonValueProcessorTest;
import com.fortes.rh.security.spring.aop.AtributosDaAuditoriaTest;
import com.fortes.rh.security.spring.aop.AuditoriaGeralAdviceTest;
import com.fortes.rh.security.spring.aop.AuditoriaPointcutTest;
import com.fortes.rh.security.spring.aop.DateFormatJsonValueProcessorTest;
import com.fortes.rh.security.spring.aop.GeraDadosAuditadosTest;
import com.fortes.rh.security.spring.aop.ProcuraChaveNaEntidadeTest;
import com.fortes.rh.security.spring.aop.callback.crud.CrudAuditorCallbackImplTest;
import com.fortes.rh.security.spring.aop.callback.crud.InsertAuditorCallbackImplTest;
import com.fortes.rh.security.spring.aop.callback.crud.RemoveAuditorCallbackImplTest;
import com.fortes.rh.security.spring.aop.callback.crud.UpdateAuditorCallbackImplTest;
import com.fortes.rh.test.business.acesso.PapelManagerTest;
import com.fortes.rh.test.business.acesso.PerfilManagerTest;
import com.fortes.rh.test.business.acesso.UsuarioEmpresaManagerTest;
import com.fortes.rh.test.business.acesso.UsuarioManagerTest;
import com.fortes.rh.test.business.avaliacao.AvaliacaoDesempenhoManagerTest;
import com.fortes.rh.test.business.avaliacao.AvaliacaoManagerTest;
import com.fortes.rh.test.business.avaliacao.AvaliacaoPraticaManagerTest;
import com.fortes.rh.test.business.avaliacao.PeriodoExperienciaManagerTest;
import com.fortes.rh.test.business.captacao.AnuncioManagerTest;
import com.fortes.rh.test.business.captacao.AtitudeManagerTest;
import com.fortes.rh.test.business.captacao.CandidatoCurriculoManagerTest;
import com.fortes.rh.test.business.captacao.CandidatoIdiomaManagerTest;
import com.fortes.rh.test.business.captacao.CandidatoManagerTest;
import com.fortes.rh.test.business.captacao.CandidatoSolicitacaoManagerTest;
import com.fortes.rh.test.business.captacao.ConfiguracaoImpressaoCurriculoManagerTest;
import com.fortes.rh.test.business.captacao.ConfiguracaoNivelCompetenciaManagerTest;
import com.fortes.rh.test.business.captacao.ConhecimentoManagerTest;
import com.fortes.rh.test.business.captacao.DuracaoPreenchimentoVagaManagerTest;
import com.fortes.rh.test.business.captacao.EmpresaBdsManagerTest;
import com.fortes.rh.test.business.captacao.EtapaSeletivaManagerTest;
import com.fortes.rh.test.business.captacao.ExperienciaManagerTest;
import com.fortes.rh.test.business.captacao.FormacaoManagerTest;
import com.fortes.rh.test.business.captacao.HabilidadeManagerTest;
import com.fortes.rh.test.business.captacao.HistoricoCandidatoManagerTest;
import com.fortes.rh.test.business.captacao.NivelCompetenciaManagerTest;
import com.fortes.rh.test.business.captacao.SolicitacaoBDSManagerTest;
import com.fortes.rh.test.business.captacao.SolicitacaoManagerTest;
import com.fortes.rh.test.business.cargosalario.CargoManagerTest;
import com.fortes.rh.test.business.cargosalario.FaixaSalarialHistoricoManagerTest;
import com.fortes.rh.test.business.cargosalario.FaixaSalarialManagerTest;
import com.fortes.rh.test.business.cargosalario.FaturamentoMensalManagerTest;
import com.fortes.rh.test.business.cargosalario.GrupoOcupacionalManagerTest;
import com.fortes.rh.test.business.cargosalario.HistoricoColaboradorManagerTest;
import com.fortes.rh.test.business.cargosalario.IndiceHistoricoManagerTest;
import com.fortes.rh.test.business.cargosalario.IndiceManagerTest;
import com.fortes.rh.test.business.cargosalario.ReajusteColaboradorManagerTest;
import com.fortes.rh.test.business.cargosalario.TabelaReajusteColaboradorManagerTest;
import com.fortes.rh.test.business.desenvolvimento.AproveitamentoAvaliacaoCursoManagerTest;
import com.fortes.rh.test.business.desenvolvimento.CertificacaoManagerTest;
import com.fortes.rh.test.business.desenvolvimento.ColaboradorPresencaManagerTest;
import com.fortes.rh.test.business.desenvolvimento.ColaboradorTurmaManagerTest;
import com.fortes.rh.test.business.desenvolvimento.CursoManagerTest;
import com.fortes.rh.test.business.desenvolvimento.DNTManagerTest;
import com.fortes.rh.test.business.desenvolvimento.DiaTurmaManagerTest;
import com.fortes.rh.test.business.desenvolvimento.TurmaDocumentoAnexoManagerTest;
import com.fortes.rh.test.business.desenvolvimento.TurmaManagerTest;
import com.fortes.rh.test.business.geral.AreaFormacaoManagerTest;
import com.fortes.rh.test.business.geral.AreaInteresseManagerTest;
import com.fortes.rh.test.business.geral.AreaOrganizacionalManagerTest;
import com.fortes.rh.test.business.geral.BairroManagerTest;
import com.fortes.rh.test.business.geral.BeneficioManagerTest;
import com.fortes.rh.test.business.geral.CamposExtrasManagerTest;
import com.fortes.rh.test.business.geral.CidadeManagerTest;
import com.fortes.rh.test.business.geral.ClienteManagerTest;
import com.fortes.rh.test.business.geral.ColaboradorIdiomaManagerTest;
import com.fortes.rh.test.business.geral.ColaboradorManagerTest;
import com.fortes.rh.test.business.geral.ColaboradorOcorrenciaManagerTest;
import com.fortes.rh.test.business.geral.ColaboradorPeriodoExperienciaAvaliacaoManagerTest;
import com.fortes.rh.test.business.geral.ComoFicouSabendoVagaManagerTest;
import com.fortes.rh.test.business.geral.ConfiguracaoPerformanceManagerTest;
import com.fortes.rh.test.business.geral.ConfiguracaoRelatorioDinamicoManagerTest;
import com.fortes.rh.test.business.geral.DocumentoAnexoManagerTest;
import com.fortes.rh.test.business.geral.EmpresaManagerTest;
import com.fortes.rh.test.business.geral.EstabelecimentoManagerTest;
import com.fortes.rh.test.business.geral.EstadoManagerTest;
import com.fortes.rh.test.business.geral.GastoEmpresaManagerTest;
import com.fortes.rh.test.business.geral.GastoManagerTest;
import com.fortes.rh.test.business.geral.GerenciadorComunicacaoManagerTest;
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
import com.fortes.rh.test.business.importacao.ImportacaoColaboradorManagerTest;
import com.fortes.rh.test.business.pesquisa.AspectoManagerTest;
import com.fortes.rh.test.business.pesquisa.ColaboradorQuestionarioManagerTest;
import com.fortes.rh.test.business.pesquisa.ColaboradorRespostaManagerTest;
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
import com.fortes.rh.test.business.sesmt.AnexoManagerTest;
import com.fortes.rh.test.business.sesmt.AreaVivenciaManagerTest;
import com.fortes.rh.test.business.sesmt.AreaVivenciaPcmatManagerTest;
import com.fortes.rh.test.business.sesmt.AtividadeSegurancaPcmatManagerTest;
import com.fortes.rh.test.business.sesmt.CandidatoEleicaoManagerTest;
import com.fortes.rh.test.business.sesmt.CatManagerTest;
import com.fortes.rh.test.business.sesmt.ClinicaAutorizadaManagerTest;
import com.fortes.rh.test.business.sesmt.ColaboradorAfastamentoManagerTest;
import com.fortes.rh.test.business.sesmt.ComissaoEleicaoManagerTest;
import com.fortes.rh.test.business.sesmt.ComissaoManagerTest;
import com.fortes.rh.test.business.sesmt.ComissaoMembroManagerTest;
import com.fortes.rh.test.business.sesmt.ComissaoPeriodoManagerTest;
import com.fortes.rh.test.business.sesmt.ComissaoPlanoTrabalhoManagerTest;
import com.fortes.rh.test.business.sesmt.ComissaoReuniaoManagerTest;
import com.fortes.rh.test.business.sesmt.ComissaoReuniaoPresencaManagerTest;
import com.fortes.rh.test.business.sesmt.EleicaoManagerTest;
import com.fortes.rh.test.business.sesmt.EngenheiroResponsavelManagerTest;
import com.fortes.rh.test.business.sesmt.EpcManagerTest;
import com.fortes.rh.test.business.sesmt.EpcPcmatManagerTest;
import com.fortes.rh.test.business.sesmt.EpiHistoricoManagerTest;
import com.fortes.rh.test.business.sesmt.EpiManagerTest;
import com.fortes.rh.test.business.sesmt.EpiPcmatManagerTest;
import com.fortes.rh.test.business.sesmt.EtapaProcessoEleitoralManagerTest;
import com.fortes.rh.test.business.sesmt.ExameManagerTest;
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
import com.fortes.rh.test.business.sesmt.SolicitacaoExameManagerTest;
import com.fortes.rh.test.business.sesmt.TamanhoEPIManagerTest;
import com.fortes.rh.test.business.ws.RHServiceIntranetTest;
import com.fortes.rh.test.business.ws.RHServiceTest;
import com.fortes.rh.test.dao.hibernate.acesso.PapelDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.acesso.PerfilDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.acesso.UsuarioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.acesso.UsuarioEmpresaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.avaliacao.AvaliacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.avaliacao.AvaliacaoDesempenhoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.avaliacao.AvaliacaoPraticaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.avaliacao.PeriodoExperienciaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.AnuncioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.AtitudeDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.CandidatoCurriculoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.CandidatoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.CandidatoIdiomaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.CandidatoSolicitacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.CompetenciaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.ConfiguracaoImpressaoCurriculoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.ConfiguracaoNivelCompetenciaColaboradorDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.ConhecimentoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.EmpresaBdsDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.EtapaSeletivaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.ExperienciaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.FormacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.HabilidadeDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.HistoricoCandidatoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.IdiomaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.MotivoSolicitacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.NivelCompetenciaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.SolicitacaoAvaliacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.SolicitacaoBDSDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.captacao.SolicitacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.CargoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.FaixaSalarialDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.FaixaSalarialHistoricoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.FaturamentoMensalDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.GrupoOcupacionalDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.cargosalario.HistoricoColaboradorDaoHibernateTest;
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
import com.fortes.rh.test.dao.hibernate.desenvolvimento.ColaboradorPresencaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.ColaboradorTurmaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.CursoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.DNTDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.DiaTurmaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.TurmaDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.desenvolvimento.TurmaDocumentoAnexoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.AreaFormacaoDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.AreaInteresseDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.AreaOrganizacionalDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.BairroDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.BeneficioDaoHibernateTest;
import com.fortes.rh.test.dao.hibernate.geral.CamposExtrasDaoHibernateTest;
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
import com.fortes.rh.test.dao.hibernate.sesmt.ColaboradorAfastamentoDaoHibernateTest;
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
import com.fortes.rh.test.exception.FaixaJaCadastradaExceptionTest;
import com.fortes.rh.test.exception.IntegraACExceptionTest;
import com.fortes.rh.test.model.acesso.UsuarioTest;
import com.fortes.rh.test.model.avaliacao.AcompanhamentoExperienciaColaboradorTest;
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
import com.fortes.rh.test.web.action.avaliacao.AvaliacaoExperienciaEditActionTest;
import com.fortes.rh.test.web.action.avaliacao.AvaliacaoPraticaEditActionTest;
import com.fortes.rh.test.web.action.avaliacao.PerguntaAvaliacaoEditActionTest;
import com.fortes.rh.test.web.action.avaliacao.PeriodoExperienciaEditActionTest;
import com.fortes.rh.test.web.action.backup.BackupActionTest;
import com.fortes.rh.test.web.action.captacao.AnuncioListActionTest;
import com.fortes.rh.test.web.action.captacao.AtitudeEditActionTest;
import com.fortes.rh.test.web.action.captacao.CandidatoEditActionTest;
import com.fortes.rh.test.web.action.captacao.CandidatoListActionTest;
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
import com.fortes.rh.test.web.action.captacao.SolicitacaoEditActionTest;
import com.fortes.rh.test.web.action.captacao.SolicitacaoListActionTest;
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
import com.fortes.rh.test.web.action.desenvolvimento.PrioridadeTreinamentoEditActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.PrioridadeTreinamentoListActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.RelatorioPresencaActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.TurmaEditActionTest;
import com.fortes.rh.test.web.action.desenvolvimento.TurmaListActionTest;
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
import com.fortes.rh.test.web.action.geral.ColaboradorEditActionTest;
import com.fortes.rh.test.web.action.geral.ColaboradorListActionTest;
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
import com.fortes.rh.test.web.action.pesquisa.ColaboradorRespostaEditActionTest;
import com.fortes.rh.test.web.action.pesquisa.ColaboradorRespostaListActionTest;
import com.fortes.rh.test.web.action.pesquisa.EntrevistaListActionTest;
import com.fortes.rh.test.web.action.pesquisa.FichaMedicaListActionTest;
import com.fortes.rh.test.web.action.pesquisa.PerguntaEditActionTest;
import com.fortes.rh.test.web.action.pesquisa.PerguntaListActionTest;
import com.fortes.rh.test.web.action.pesquisa.PesquisaEditActionTest;
import com.fortes.rh.test.web.action.pesquisa.PesquisaListActionTest;
import com.fortes.rh.test.web.action.pesquisa.QuestionarioListActionTest;
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
import com.fortes.rh.test.web.action.sesmt.PcmatEditActionTest;
import com.fortes.rh.test.web.action.sesmt.PcmsoListActionTest;
import com.fortes.rh.test.web.action.sesmt.PppEditActionTest;
import com.fortes.rh.test.web.action.sesmt.PpraEditActionTest;
import com.fortes.rh.test.web.action.sesmt.ProntuarioEditActionTest;
import com.fortes.rh.test.web.action.sesmt.RiscoEditActionTest;
import com.fortes.rh.test.web.action.sesmt.SolicitacaoEpiEditActionTest;
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
import com.fortes.rh.test.web.dwr.AspectoDWRTest;
import com.fortes.rh.test.web.dwr.AvaliacaoDesempenhoDWRTest;
import com.fortes.rh.test.web.dwr.BairroDWRTest;
import com.fortes.rh.test.web.dwr.CandidatoDWRTest;
import com.fortes.rh.test.web.dwr.CargoDWRTest;
import com.fortes.rh.test.web.dwr.CidadeDWRTest;
import com.fortes.rh.test.web.dwr.ColaboradorDWRTest;
import com.fortes.rh.test.web.dwr.ComissaoPeriodoDWRTest;
import com.fortes.rh.test.web.dwr.ComissaoPlanoTrabalhoDWRTest;
import com.fortes.rh.test.web.dwr.ComissaoReuniaoDWRTest;
import com.fortes.rh.test.web.dwr.CompetenciaDWRTest;
import com.fortes.rh.test.web.dwr.ConfiguracaoPerformanceDWRTest;
import com.fortes.rh.test.web.dwr.ConhecimentoDWRTest;
import com.fortes.rh.test.web.dwr.DiaTurmaDWRTest;
import com.fortes.rh.test.web.dwr.DntDWRTest;
import com.fortes.rh.test.web.dwr.EnderecoDWRTest;
import com.fortes.rh.test.web.dwr.EstabelecimentoDWRTest;
import com.fortes.rh.test.web.dwr.ExtintorDWRTest;
import com.fortes.rh.test.web.dwr.FaixaSalarialDWRTest;
import com.fortes.rh.test.web.dwr.FuncaoDWRTest;
import com.fortes.rh.test.web.dwr.GrupoOcupacionalDWRTest;
import com.fortes.rh.test.web.dwr.HistoricoCandidatoDWRTest;
import com.fortes.rh.test.web.dwr.ListaPresencaDWRTest;
import com.fortes.rh.test.web.dwr.MorroDWRTest;
import com.fortes.rh.test.web.dwr.OcorrenciaDWRTest;
import com.fortes.rh.test.web.dwr.PerguntaDWRTest;
import com.fortes.rh.test.web.dwr.ReajusteDWRTest;
import com.fortes.rh.test.web.dwr.RiscoAmbienteDWRTest;
import com.fortes.rh.test.web.dwr.RiscosDWRTest;
import com.fortes.rh.test.web.dwr.SolicitacaoDWRTest;
import com.fortes.rh.test.web.dwr.SolicitacaoExameDWRTest;
import com.fortes.rh.test.web.dwr.TurmaDWRTest;
import com.fortes.rh.test.web.dwr.UsuarioMensagemDWRTest;
import com.fortes.rh.test.web.dwr.UtilDWRTest;
import com.fortes.rh.web.action.exportacao.ExportacaoActionTest;
import com.fortes.test.web.tags.LinkTagTest;

public class AllUnitTests extends TestSuite
{
	public static TestSuite suite()
    {
        TestSuite suite = new TestSuite();

        //ACESSO
        suite.addTestSuite(PapelManagerTest.class);

        suite.addTestSuite(PapelDaoHibernateTest.class);
        suite.addTestSuite(PerfilDaoHibernateTest.class);
        suite.addTestSuite(PerfilManagerTest.class);
        suite.addTestSuite(UsuarioDaoHibernateTest.class);
        suite.addTestSuite(UsuarioManagerTest.class);
        suite.addTestSuite(UsuarioEditActionTest.class);
        suite.addTestSuite(UsuarioEmpresaDaoHibernateTest.class);
        suite.addTestSuite(UsuarioEmpresaManagerTest.class);

        //CAPTAÇÃO
        suite.addTestSuite(ConfiguracaoImpressaoCurriculoDaoHibernateTest.class);
        suite.addTestSuite(ConfiguracaoImpressaoCurriculoManagerTest.class);
        suite.addTestSuite(AnuncioListActionTest.class);
        suite.addTestSuite(AnuncioManagerTest.class);
        suite.addTestSuite(AnuncioDaoHibernateTest.class);
        suite.addTestSuite(CandidatoDaoHibernateTest.class);
        suite.addTestSuite(CandidatoManagerTest.class);
        suite.addTestSuite(CandidatoEditActionTest.class);
        suite.addTestSuite(CandidatoListActionTest.class);
        suite.addTestSuite(ConhecimentoEditActionTest.class);
        suite.addTestSuite(ConhecimentoListActionTest.class);
        suite.addTestSuite(ConhecimentoDaoHibernateTest.class);
        suite.addTestSuite(ConhecimentoManagerTest.class);
        
        suite.addTestSuite(AtitudeEditActionTest.class);
        suite.addTestSuite(AtitudeDaoHibernateTest.class);
        suite.addTestSuite(AtitudeManagerTest.class);
        suite.addTestSuite(HabilidadeEditActionTest.class);
        suite.addTestSuite(HabilidadeDaoHibernateTest.class);
        suite.addTestSuite(HabilidadeManagerTest.class);
        suite.addTestSuite(DuracaoPreenchimentoVagaManagerTest.class);
        suite.addTestSuite(ExperienciaManagerTest.class);
        suite.addTestSuite(ExperienciaDaoHibernateTest.class);
        suite.addTestSuite(FormacaoDaoHibernateTest.class);
        suite.addTestSuite(FormacaoManagerTest.class);
        suite.addTestSuite(IdiomaDaoHibernateTest.class);
        suite.addTestSuite(SolicitacaoDaoHibernateTest.class);
        suite.addTestSuite(SolicitacaoAvaliacaoDaoHibernateTest.class);
        suite.addTestSuite(SolicitacaoManagerTest.class);
        suite.addTestSuite(SolicitacaoListActionTest.class);
        suite.addTestSuite(SolicitacaoBDSDaoHibernateTest.class);
        suite.addTestSuite(CandidatoIdiomaTest.class);
        suite.addTestSuite(CandidatoIdiomaManagerTest.class);
        suite.addTestSuite(CandidatoIdiomaDaoHibernateTest.class);
        suite.addTestSuite(EtapaSeletivaDaoHibernateTest.class);
        suite.addTestSuite(EtapaSeletivaManagerTest.class);
        suite.addTestSuite(EtapaSeletivaListActionTest.class);
        suite.addTestSuite(EtapaSeletivaEditActionTest.class);
        suite.addTestSuite(EmpresaBdsListActionTest.class);
        suite.addTestSuite(EmpresaBdsEditActionTest.class);
        suite.addTestSuite(EmpresaBdsManagerTest.class);
        suite.addTestSuite(EmpresaBdsDaoHibernateTest.class);
        suite.addTestSuite(HistoricoCandidatoManagerTest.class);
        suite.addTestSuite(HistoricoCandidatoDaoHibernateTest.class);
        suite.addTestSuite(RelatorioPromocoesTest.class);
        suite.addTestSuite(CandidatoSolicitacaoManagerTest.class);
        suite.addTestSuite(CandidatoSolicitacaoDaoHibernateTest.class);
        suite.addTestSuite(MotivoSolicitacaoListActionTest.class);
        suite.addTestSuite(MotivoSolicitacaoEditActionTest.class);
        suite.addTestSuite(MotivoSolicitacaoDaoHibernateTest.class);
        suite.addTestSuite(SolicitacaoBDSManagerTest.class);
        suite.addTestSuite(CandidatoCurriculoDaoHibernateTest.class);
        suite.addTestSuite(CandidatoCurriculoManagerTest.class);
        suite.addTestSuite(ExperienciaTest.class);
        suite.addTestSuite(FormacaoTest.class);
        suite.addTestSuite(CandidatoSolicitacaoTest.class);
        suite.addTestSuite(SolicitacaoEditActionTest.class);
        suite.addTestSuite(NivelCompetenciaEditActionTest.class);
        suite.addTestSuite(NivelCompetenciaManagerTest.class);
        suite.addTestSuite(NivelCompetenciaDaoHibernateTest.class);
        suite.addTestSuite(ConfiguracaoNivelCompetenciaManagerTest.class);
        suite.addTestSuite(ConfiguracaoNivelCompetenciaColaboradorDaoHibernateTest.class);
        suite.addTestSuite(CompetenciaDaoHibernateTest.class);
        suite.addTestSuite(CompetenciaDWRTest.class);

        suite.addTestSuite(CtpsTest.class);

        //Indicadores
        suite.addTestSuite(IndicadorTurnOverListActionTest.class);

        //CARGOSALARIO
        suite.addTestSuite(CargoManagerTest.class);
        suite.addTestSuite(CargoDaoHibernateTest.class);
        suite.addTestSuite(CargoEditActionTest.class);

        suite.addTestSuite(FaixaSalarialDaoHibernateTest.class);
        suite.addTestSuite(FaixaSalarialManagerTest.class);
        suite.addTestSuite(FaixaSalarialHistoricoDaoHibernateTest.class);
        suite.addTestSuite(FaixaSalarialHistoricoManagerTest.class);
        suite.addTestSuite(FaixaSalarialHistoricoListActionTest.class);
        suite.addTestSuite(FaixaSalarialHistoricoEditActionTest.class);

        suite.addTestSuite(GrupoOcupacionalManagerTest.class);
        suite.addTestSuite(GrupoOcupacionalDaoHibernateTest.class);

        suite.addTestSuite(HistoricoColaboradorDaoHibernateTest.class);
        suite.addTestSuite(HistoricoColaboradorManagerTest.class);
        suite.addTestSuite(HistoricoColaboradorListActionTest.class);
        suite.addTestSuite(HistoricoColaboradorEditActionTest.class);

        suite.addTestSuite(RelatorioListaFrequenciaTest.class);

        suite.addTestSuite(ColaboradorEditActionTest.class);
        suite.addTestSuite(ColaboradorListActionTest.class);

        suite.addTestSuite(ReajusteColaboradorDaoHibernateTest.class);
        suite.addTestSuite(ReajusteColaboradorManagerTest.class);
        suite.addTestSuite(ReajusteColaboradorEditActionTest.class);

        suite.addTestSuite(ReajusteFaixaSalarialDaoHibernateTest.class);
        suite.addTestSuite(ReajusteIndiceDaoHibernateTest.class);

        suite.addTestSuite(TabelaReajusteColaboradorDaoHibernateTest.class);
        suite.addTestSuite(TabelaReajusteColaboradorManagerTest.class);
        suite.addTestSuite(IndiceEditActionTest.class);
        suite.addTestSuite(IndiceListActionTest.class);
        suite.addTestSuite(IndiceDaoHibernateTest.class);
        suite.addTestSuite(IndiceManagerTest.class);
        suite.addTestSuite(IndiceHistoricoDaoHibernateTest.class);
        suite.addTestSuite(IndiceHistoricoManagerTest.class);
        suite.addTestSuite(IndiceHistoricoEditActionTest.class);
        suite.addTestSuite(IndiceHistoricoListActionTest.class);

        suite.addTestSuite(ConfiguracaoLimiteColaboradorDaoHibernateTest.class);
        suite.addTestSuite(QuantidadeLimiteColaboradoresPorCargoDaoHibernateTest.class);
        suite.addTestSuite(QuantidadeLimiteColaboradoresPorCargoManagerTest.class);
        suite.addTestSuite(FaturamentoMensalEditActionTest.class);
        suite.addTestSuite(FaturamentoMensalManagerTest.class);
        suite.addTestSuite(FaturamentoMensalDaoHibernateTest.class);

        //DESENVOLVIMENTO
        suite.addTestSuite(CursoDaoHibernateTest.class);
        suite.addTestSuite(CursoManagerTest.class);
        suite.addTestSuite(CursoEditActionTest.class);
        suite.addTestSuite(CursoListActionTest.class);

        suite.addTestSuite(DiaTurmaDaoHibernateTest.class);
        suite.addTestSuite(DiaTurmaEditActionTest.class);
        suite.addTestSuite(DiaTurmaListActionTest.class);
        suite.addTestSuite(DiaTurmaManagerTest.class);

        suite.addTestSuite(TurmaTest.class);
        suite.addTestSuite(TurmaDaoHibernateTest.class);
        suite.addTestSuite(TurmaEditActionTest.class);
        suite.addTestSuite(TurmaListActionTest.class);
        suite.addTestSuite(TurmaManagerTest.class);
        
        suite.addTestSuite(TurmaDocumentoAnexoDaoHibernateTest.class);
        suite.addTestSuite(TurmaDocumentoAnexoManagerTest.class);

        suite.addTestSuite(DNTDaoHibernateTest.class);
        suite.addTestSuite(DNTManagerTest.class);
        suite.addTestSuite(DNTEditActionTest.class);
        suite.addTestSuite(DNTListActionTest.class);

        suite.addTestSuite(ColaboradorTurmaEditActionTest.class);
        suite.addTestSuite(ColaboradorTurmaListActionTest.class);
        suite.addTestSuite(ColaboradorTurmaManagerTest.class);
        suite.addTestSuite(ColaboradorTurmaDaoHibernateTest.class);

        suite.addTestSuite(ColaboradorPresencaEditActionTest.class);
        suite.addTestSuite(ColaboradorPresencaListActionTest.class);
        suite.addTestSuite(ColaboradorPresencaDaoHibernateTest.class);
        suite.addTestSuite(ColaboradorPresencaManagerTest.class);

        suite.addTestSuite(PrioridadeTreinamentoEditActionTest.class);
        suite.addTestSuite(PrioridadeTreinamentoListActionTest.class);

        suite.addTestSuite(RelatorioPresencaActionTest.class);

        suite.addTestSuite(AvaliacaoCursoDaoHibernateTest.class);
        suite.addTestSuite(AvaliacaoCursoEditActionTest.class);
        suite.addTestSuite(AvaliacaoCursoListActionTest.class);

        suite.addTestSuite(AproveitamentoAvaliacaoCursoDaoHibernateTest.class);
        suite.addTestSuite(AproveitamentoAvaliacaoCursoManagerTest.class);

        suite.addTestSuite(CertificacaoDaoHibernateTest.class);
        suite.addTestSuite(CertificacaoManagerTest.class);
        suite.addTestSuite(CertificacaoListActionTest.class);
        suite.addTestSuite(CertificadoTest.class);

        // Backup
        suite.addTestSuite(BackupActionTest.class);
        
        //GERAL
        suite.addTestSuite(ParametrosDoSistemaTest.class);
        suite.addTestSuite(AreaFormacaoManagerTest.class);
        suite.addTestSuite(AreaFormacaoDaoHibernateTest.class);
        suite.addTestSuite(AreaFormacaoListActionTest.class);
        suite.addTestSuite(AreaFormacaoEditActionTest.class);
        suite.addTestSuite(AreaInteresseDaoHibernateTest.class);
        suite.addTestSuite(AreaInteresseManagerTest.class);
        suite.addTestSuite(AreaInteresseListActionTest.class);
        suite.addTestSuite(AreaInteresseEditActionTest.class);
        suite.addTestSuite(AreaOrganizacionalDaoHibernateTest.class);
        suite.addTestSuite(AreaOrganizacionalManagerTest.class);
        suite.addTestSuite(ColaboradorTest.class);
        suite.addTestSuite(ColaboradorManagerTest.class);
        suite.addTestSuite(ColaboradorDaoHibernateTest.class);
        suite.addTestSuite(ColaboradorOcorrenciaDaoHibernateTest.class);
        suite.addTestSuite(ColaboradorOcorrenciaManagerTest.class);
        suite.addTestSuite(ColaboradorIdiomaManagerTest.class);
        suite.addTestSuite(ColaboradorIdiomaDaoHibernateTest.class);
        suite.addTestSuite(ImportadorGastosACTest.class);
        suite.addTestSuite(EmpresaManagerTest.class);
        suite.addTestSuite(EmpresaListActionTest.class);
        suite.addTestSuite(EmpresaEditActionTest.class);
        suite.addTestSuite(EstabelecimentoEditActionTest.class);
        suite.addTestSuite(EstabelecimentoDaoHibernateTest.class);
        suite.addTestSuite(EstabelecimentoListActionTest.class);
        suite.addTestSuite(EstabelecimentoManagerTest.class);
        suite.addTestSuite(BeneficioListActionTest.class);
        suite.addTestSuite(BeneficioEditActionTest.class);
        suite.addTestSuite(BeneficioDaoHibernateTest.class);
        suite.addTestSuite(BeneficioManagerTest.class);
        suite.addTestSuite(HistoricoBeneficioDaoHibernateTest.class);
        suite.addTestSuite(HistoricoBeneficioManagerTest.class);
        suite.addTestSuite(HistoricoBeneficioEditActionTest.class);
        suite.addTestSuite(HistoricoBeneficioListActionTest.class);
        suite.addTestSuite(BairroEditActionTest.class);
        suite.addTestSuite(BairroListActionTest.class);
        suite.addTestSuite(BairroManagerTest.class);
        suite.addTestSuite(BairroDaoHibernateTest.class);
        suite.addTestSuite(CidadeManagerTest.class);
        suite.addTestSuite(CidadeDaoHibernateTest.class);
        suite.addTestSuite(ColaboradorReportActionTest.class);
        suite.addTestSuite(UsuarioMensagemDaoHibernateTest.class);
        suite.addTestSuite(UsuarioMensagemManagerTest.class);
        suite.addTestSuite(UsuarioMensagemEditActionTest.class);
        suite.addTestSuite(UsuarioMensagemListActionTest.class);
        suite.addTestSuite(UsuarioMensagemDWRTest.class);
        suite.addTestSuite(MensagemManagerTest.class);
        suite.addTestSuite(OcorrenciaEditActionTest.class);
        suite.addTestSuite(OcorrenciaDaoHibernateTest.class);
        suite.addTestSuite(OcorrenciaManagerTest.class);
        suite.addTestSuite(EstadoDaoHibernateTest.class);
        suite.addTestSuite(EstadoManagerTest.class);
        suite.addTestSuite(CamposExtrasDaoHibernateTest.class);
        suite.addTestSuite(CamposExtrasManagerTest.class);
        suite.addTestSuite(ConfiguracaoPerformanceManagerTest.class);
        suite.addTestSuite(ConfiguracaoPerformanceDaoHibernateTest.class);
        suite.addTestSuite(ConfiguracaoPerformanceDWRTest.class);
        suite.addTestSuite(ConfiguracaoRelatorioDinamicoDaoHibernateTest.class);
        suite.addTestSuite(ConfiguracaoRelatorioDinamicoManagerTest.class);
        suite.addTestSuite(ConfiguracaoRelatorioDinamicoEditActionTest.class);
        suite.addTestSuite(GrupoACDaoHibernateTest.class);
        suite.addTestSuite(GrupoACEditActionTest.class);
        suite.addTestSuite(GrupoACManagerTest.class);
        suite.addTestSuite(CodigoCBODaoHibernateTest.class);
        suite.addTestSuite(CidDaoHibernateTest.class);
        suite.addTestSuite(ConfiguracaoCampoExtraDaoHibernateTest.class);
        suite.addTestSuite(CodigoCBOEditActionTest.class);
        suite.addTestSuite(CidEditActionTest.class);
        suite.addTestSuite(ComoFicouSabendoVagaDaoHibernateTest.class);
        suite.addTestSuite(ComoFicouSabendoVagaManagerTest.class);
        suite.addTestSuite(ComoFicouSabendoVagaEditActionTest.class);
        suite.addTestSuite(ImportacaoColaboradorManagerTest.class);
        suite.addTestSuite(AbsenteismoCollectionTest.class);
        suite.addTestSuite(ColaboradorPeriodoExperienciaAvaliacaoManagerTest.class);
        suite.addTestSuite(ColaboradorPeriodoExperienciaAvaliacaoDaoHibernateTest.class);
        suite.addTestSuite(TipoDocumentoEditActionTest.class);
        suite.addTestSuite(TipoDocumentoManagerTest.class);
        suite.addTestSuite(TipoDocumentoDaoHibernateTest.class);
        suite.addTestSuite(TipoDespesaEditActionTest.class);
        suite.addTestSuite(TipoDespesaManagerTest.class);
        suite.addTestSuite(TipoDespesaDaoHibernateTest.class);
        suite.addTestSuite(TurmaTipoDespesaDaoHibernateTest.class);
        suite.addTestSuite(TurmaTipoDespesaManagerTest.class);
        suite.addTestSuite(ProvidenciaDaoHibernateTest.class);
        suite.addTestSuite(ProvidenciaManagerTest.class);
        suite.addTestSuite(ProvidenciaEditActionTest.class);
        suite.addTestSuite(GerenciadorComunicacaoDaoHibernateTest.class);
        suite.addTestSuite(GerenciadorComunicacaoManagerTest.class);
        suite.addTestSuite(GerenciadorComunicacaoEditActionTest.class);

        
        //contatoTest embedded Colaborador
        suite.addTestSuite(DependenteDaoHibernateTest.class);
        suite.addTestSuite(EmpresaDaoHibernateTest.class);
        //endereçoTest embedded Colaborador
        suite.addTestSuite(GrupoGastoDaoHibernateTest.class);
        suite.addTestSuite(GrupoGastoManagerTest.class);
        suite.addTestSuite(ParametrosDoSistemaDaoHibernateTest.class);
        //pessoalTest embedded Colaborador
        suite.addTestSuite(MotivoDemissaoDaoHibernateTest.class);
        suite.addTestSuite(MotivoDemissaoListActionTest.class);
        suite.addTestSuite(MotivoDemissaoManagerTest.class);
        suite.addTestSuite(DocumentoAnexoDaoHibernateTest.class);
        suite.addTestSuite(DocumentoAnexoManagerTest.class);
        suite.addTestSuite(DocumentoAnexoListActionTest.class);
        suite.addTestSuite(DocumentoAnexoEditActionTest.class);
        suite.addTestSuite(PerfilListActionTest.class);
        suite.addTestSuite(PerfilEditActionTest.class);
        suite.addTestSuite(MensagemDaoHibernateTest.class);
        suite.addTestSuite(ParametrosDoSistemaManagerTest.class);
        suite.addTestSuite(ParametrosDoSistemaEditActionTest.class);

        // beneficios
        suite.addTestSuite(HistoricoColaboradorBeneficioEditActionTest.class);
        suite.addTestSuite(HistoricoColaboradorBeneficioListActionTest.class);
        suite.addTestSuite(HistoricoColaboradorBeneficioDaoHibernateTest.class);
        suite.addTestSuite(HistoricoColaboradorBeneficioManagerTest.class);

        // gastos
        suite.addTestSuite(GastoEmpresaManagerTest.class);
        suite.addTestSuite(GastoManagerTest.class);
        suite.addTestSuite(GastoDaoHibernateTest.class);
        suite.addTestSuite(GastoEmpresaDaoHibernateTest.class);
        suite.addTestSuite(GastoEmpresaItemDaoHibernateTest.class);

        //PESQUISA
        suite.addTestSuite(ColaboradorRespostaEditActionTest.class);
        suite.addTestSuite(ColaboradorRespostaManagerTest.class);
        suite.addTestSuite(ColaboradorRespostaDaoHibernateTest.class);
        suite.addTestSuite(ColaboradorRespostaListActionTest.class);
        suite.addTestSuite(PerguntaTest.class);
        suite.addTestSuite(PerguntaDaoHibernateTest.class);
        suite.addTestSuite(PerguntaManagerTest.class);
        suite.addTestSuite(PerguntaListActionTest.class);
        suite.addTestSuite(PerguntaEditActionTest.class);
        suite.addTestSuite(RespostaDaoHibernateTest.class);
        suite.addTestSuite(RespostaManagerTest.class);
        suite.addTestSuite(AspectoDaoHibernateTest.class);
        suite.addTestSuite(AspectoManagerTest.class);
        suite.addTestSuite(AspectoEditActionTest.class);
        suite.addTestSuite(AspectoListActionTest.class);
        suite.addTestSuite(QuestionarioDaoHibernateTest.class);
        suite.addTestSuite(QuestionarioManagerTest.class);
        suite.addTestSuite(PesquisaDaoHibernateTest.class);
        suite.addTestSuite(PesquisaManagerTest.class);
        suite.addTestSuite(QuestionarioListActionTest.class);
        suite.addTestSuite(PesquisaListActionTest.class);
        suite.addTestSuite(PesquisaEditActionTest.class);
        suite.addTestSuite(LembretePesquisaTest.class);
        suite.addTestSuite(ColaboradorQuestionarioDaoHibernateTest.class);
        suite.addTestSuite(ColaboradorQuestionarioManagerTest.class);
        suite.addTestSuite(ColaboradorQuestionarioListActionTest.class);
        suite.addTestSuite(ColaboradorQuestionarioEditActionTest.class);
        suite.addTestSuite(EntrevistaDaoHibernateTest.class);
        suite.addTestSuite(EntrevistaManagerTest.class);
        suite.addTestSuite(EntrevistaListActionTest.class);
        suite.addTestSuite(FichaMedicaDaoHibernateTest.class);
        suite.addTestSuite(FichaMedicaManagerTest.class);
        suite.addTestSuite(FichaMedicaListActionTest.class);
        suite.addTestSuite(FichaMedicaEditActionTest.class);
        suite.addTestSuite(ResultadoQuestionarioTest.class);

        //SESMT
        suite.addTestSuite(NaturezaLesaoDaoHibernateTest.class);
        suite.addTestSuite(NaturezaLesaoEditActionTest.class);
        suite.addTestSuite(NaturezaLesaoManagerTest.class);
        
        suite.addTestSuite(ProntuarioDaoHibernateTest.class);
        suite.addTestSuite(ProntuarioManagerTest.class);
        suite.addTestSuite(ProntuarioEditActionTest.class);

        suite.addTestSuite(AnexoDaoHibernateTest.class);
        suite.addTestSuite(AnexoManagerTest.class);

        suite.addTestSuite(CatDaoHibernateTest.class);
        suite.addTestSuite(CatManagerTest.class);
        suite.addTestSuite(CatEditActionTest.class);

        suite.addTestSuite(FuncaoManagerTest.class);
        suite.addTestSuite(FuncaoDaoHibernateTest.class);
        suite.addTestSuite(FuncaoListActionTest.class);
        suite.addTestSuite(FuncaoEditActionTest.class);

        suite.addTestSuite(HistoricoFuncaoManagerTest.class);
        suite.addTestSuite(HistoricoFuncaoDaoHibernateTest.class);
        suite.addTestSuite(HistoricoFuncaoListActionTest.class);
        suite.addTestSuite(HistoricoFuncaoEditActionTest.class);

        suite.addTestSuite(AmbienteEditActionTest.class);
        suite.addTestSuite(AmbienteDaoHibernateTest.class);
        suite.addTestSuite(AmbienteManagerTest.class);

        suite.addTestSuite(HistoricoAmbienteDaoHibernateTest.class);
        suite.addTestSuite(HistoricoAmbienteManagerTest.class);
        suite.addTestSuite(HistoricoAmbienteEditActionTest.class);

        suite.addTestSuite(RiscoAmbienteDaoHibernateTest.class);
        suite.addTestSuite(RiscoAmbienteManagerTest.class);
        suite.addTestSuite(RiscoAmbienteDWRTest.class);

        suite.addTestSuite(RiscoFuncaoManagerTest.class);
        suite.addTestSuite(RiscoFuncaoDaoHibernateTest.class);

        suite.addTestSuite(TipoEpiEditActionTest.class);
        suite.addTestSuite(TipoEpiListActionTest.class);

        suite.addTestSuite(EpiEditActionTest.class);
        suite.addTestSuite(EpiListActionTest.class);
        suite.addTestSuite(EpiManagerTest.class);
        suite.addTestSuite(EpiDaoHibernateTest.class);

        suite.addTestSuite(RiscoDaoHibernateTest.class);
        suite.addTestSuite(RiscoManagerTest.class);
        suite.addTestSuite(RiscoEditActionTest.class);

        suite.addTestSuite(AnexoListActionTest.class);
        suite.addTestSuite(AnexoEditActionTest.class);

        suite.addTestSuite(PpraEditActionTest.class);

        suite.addTestSuite(EpcListActionTest.class);
        suite.addTestSuite(EpcEditActionTest.class);
        suite.addTestSuite(EpcDaoHibernateTest.class);
        suite.addTestSuite(EpcManagerTest.class);

        suite.addTestSuite(SolicitacaoExameDaoHibernateTest.class);
        suite.addTestSuite(SolicitacaoExameManagerTest.class);
        suite.addTestSuite(SolicitacaoExameListActionTest.class);
        suite.addTestSuite(SolicitacaoExameEditActionTest.class);
        suite.addTestSuite(SolicitacaoExameDWRTest.class);

        suite.addTestSuite(ExameSolicitacaoExameDaoHibernateTest.class);
        suite.addTestSuite(ExameSolicitacaoExameManagerTest.class);

        suite.addTestSuite(RealizacaoExameDaoHibernateTest.class);
        suite.addTestSuite(RealizacaoExameManagerTest.class);

        suite.addTestSuite(ExameListActionTest.class);
        suite.addTestSuite(ExameEditActionTest.class);
        suite.addTestSuite(ExameDaoHibernateTest.class);
        suite.addTestSuite(ExameManagerTest.class);
        suite.addTestSuite(ExamesPrevistosRelatorioTest.class);

        suite.addTestSuite(EngenheiroResponsavelEditActionTest.class);
        suite.addTestSuite(EngenheiroResponsavelListActionTest.class);
        suite.addTestSuite(EngenheiroResponsavelDaoHibernateTest.class);
        suite.addTestSuite(EngenheiroResponsavelManagerTest.class);

        suite.addTestSuite(MedicoCoordenadorEditActionTest.class);
        suite.addTestSuite(MedicoCoordenadorListActionTest.class);
        suite.addTestSuite(MedicoCoordenadorDaoHibernateTest.class);
        suite.addTestSuite(MedicoCoordenadorManagerTest.class);

        suite.addTestSuite(PcmsoListActionTest.class);
        suite.addTestSuite(PcmsoManagerTest.class);

        suite.addTestSuite(PppEditActionTest.class);

        suite.addTestSuite(EpiHistoricoEditActionTest.class);
        suite.addTestSuite(EpiHistoricoManagerTest.class);
        suite.addTestSuite(EpiHistoricoDaoHibernateTest.class);

        suite.addTestSuite(ClinicaAutorizadaEditActionTest.class);
        suite.addTestSuite(ClinicaAutorizadaListActionTest.class);
        suite.addTestSuite(ClinicaAutorizadaManagerTest.class);
        suite.addTestSuite(ClinicaAutorizadaDaoHibernateTest.class);

        suite.addTestSuite(SolicitacaoEpiDaoHibernateTest.class);
        suite.addTestSuite(SolicitacaoEpiManagerTest.class);
        suite.addTestSuite(SolicitacaoEpiItemDaoHibernateTest.class);
        suite.addTestSuite(SolicitacaoEpiItemManagerTest.class);
        
        suite.addTestSuite(SolicitacaoEpiItemEntregaManagerTest.class);
        suite.addTestSuite(SolicitacaoEpiItemEntregaDaoHibernateTest.class);

        suite.addTestSuite(SolicitacaoEpiItemDevolucaoManagerTest.class);
        suite.addTestSuite(SolicitacaoEpiItemDevolucaoDaoHibernateTest.class);
        suite.addTestSuite(SolicitacaoEpiEditActionTest.class);

        suite.addTestSuite(EtapaProcessoEleitoralTest.class);
        suite.addTestSuite(EtapaProcessoEleitoralDaoHibernateTest.class);
        suite.addTestSuite(EtapaProcessoEleitoralManagerTest.class);
        suite.addTestSuite(EtapaProcessoEleitoralEditActionTest.class);

        suite.addTestSuite(EleicaoDaoHibernateTest.class);
        suite.addTestSuite(EleicaoManagerTest.class);
        suite.addTestSuite(EleicaoListActionTest.class);
        suite.addTestSuite(EleicaoEditActionTest.class);

        suite.addTestSuite(CandidatoEleicaoDaoHibernateTest.class);
        suite.addTestSuite(CandidatoEleicaoManagerTest.class);
        suite.addTestSuite(CandidatoEleicaoTest.class);
        suite.addTestSuite(CandidatoEleicaoListActionTest.class);

        suite.addTestSuite(ComissaoTest.class);
        suite.addTestSuite(ComissaoEleicaoDaoHibernateTest.class);
        suite.addTestSuite(ComissaoEleicaoManagerTest.class);
        suite.addTestSuite(ComissaoEleicaoTest.class);
        suite.addTestSuite(ComissaoEleicaoListActionTest.class);

        suite.addTestSuite(ComissaoDaoHibernateTest.class);
        suite.addTestSuite(ComissaoManagerTest.class);
        suite.addTestSuite(ComissaoEditActionTest.class);

        suite.addTestSuite(ComissaoPeriodoDaoHibernateTest.class);
        suite.addTestSuite(ComissaoPeriodoManagerTest.class);
        suite.addTestSuite(ComissaoPeriodoEditActionTest.class);
        suite.addTestSuite(ComissaoPeriodoDWRTest.class);

        suite.addTestSuite(ComissaoMembroDaoHibernateTest.class);
        suite.addTestSuite(ComissaoMembroManagerTest.class);

        suite.addTestSuite(ComissaoReuniaoDaoHibernateTest.class);
        suite.addTestSuite(ComissaoReuniaoManagerTest.class);
        suite.addTestSuite(ComissaoReuniaoEditActionTest.class);
        suite.addTestSuite(ComissaoReuniaoDWRTest.class);

        suite.addTestSuite(ComissaoReuniaoPresencaDaoHibernateTest.class);
        suite.addTestSuite(ComissaoReuniaoPresencaManagerTest.class);

        suite.addTestSuite(ComissaoPlanoTrabalhoDaoHibernateTest.class);
        suite.addTestSuite(ComissaoPlanoTrabalhoManagerTest.class);
        suite.addTestSuite(ComissaoPlanoTrabalhoEditActionTest.class);
        suite.addTestSuite(ComissaoPlanoTrabalhoDWRTest.class);

        suite.addTestSuite(AfastamentoDaoHibernateTest.class);
        suite.addTestSuite(AfastamentoManagerTest.class);
        suite.addTestSuite(AfastamentoEditActionTest.class);
        suite.addTestSuite(AfastamentoDWRTest.class);

        suite.addTestSuite(ColaboradorAfastamentoDaoHibernateTest.class);
        suite.addTestSuite(ColaboradorAfastamentoManagerTest.class);
        suite.addTestSuite(ColaboradorAfastamentoEditActionTest.class);
        suite.addTestSuite(ColaboradorAfastamentoListActionTest.class);

        suite.addTestSuite(ExtintorDaoHibernateTest.class);
        suite.addTestSuite(ExtintorManagerTest.class);
        suite.addTestSuite(ExtintorEditActionTest.class);
        suite.addTestSuite(ExtintorDWRTest.class);

        suite.addTestSuite(HistoricoExtintorDaoHibernateTest.class);
        suite.addTestSuite(HistoricoExtintorManagerTest.class);

        suite.addTestSuite(ExtintorInspecaoDaoHibernateTest.class);
        suite.addTestSuite(ExtintorInspecaoManagerTest.class);
        suite.addTestSuite(ExtintorInspecaoEditActionTest.class);

        suite.addTestSuite(ExtintorManutencaoDaoHibernateTest.class);
        suite.addTestSuite(ExtintorManutencaoManagerTest.class);
        suite.addTestSuite(ExtintorManutencaoEditActionTest.class);
        suite.addTestSuite(MotivoExtintorManutencaoTest.class);

        suite.addTestSuite(MedicaoRiscoDaoHibernateTest.class);
        suite.addTestSuite(MedicaoRiscoManagerTest.class);
        suite.addTestSuite(MedicaoRiscoEditActionTest.class);

        suite.addTestSuite(RiscoMedicaoRiscoDaoHibernateTest.class);
        suite.addTestSuite(RiscoMedicaoRiscoManagerTest.class);

        suite.addTestSuite(EventoDaoHibernateTest.class);
        suite.addTestSuite(EventoEditActionTest.class);

        suite.addTestSuite(AgendaTest.class);
        suite.addTestSuite(AgendaDaoHibernateTest.class);
        suite.addTestSuite(AgendaManagerTest.class);
        suite.addTestSuite(AgendaEditActionTest.class);

        suite.addTestSuite(PppRelatorioTest.class);

        suite.addTestSuite(AsoRelatorioTest.class);
        suite.addTestSuite(AnuncioEditActionTest.class);

        suite.addTestSuite(IndicadorTreinamentosListActionTest.class);
        suite.addTestSuite(AreaFormacaoManagerTest.class);
        suite.addTestSuite(CertificacaoEditActionTest.class);
        
        suite.addTestSuite(ObraDaoHibernateTest.class);
        suite.addTestSuite(ObraManagerTest.class);
        suite.addTestSuite(ObraEditActionTest.class);
        
        suite.addTestSuite(FaseDaoHibernateTest.class);
        suite.addTestSuite(FaseManagerTest.class);
        suite.addTestSuite(FaseEditActionTest.class);
        
        suite.addTestSuite(MedidaSegurancaDaoHibernateTest.class);
        suite.addTestSuite(MedidaSegurancaManagerTest.class);
        suite.addTestSuite(MedidaSegurancaEditActionTest.class);
        
        suite.addTestSuite(AreaVivenciaDaoHibernateTest.class);
        suite.addTestSuite(AreaVivenciaManagerTest.class);
        suite.addTestSuite(AreaVivenciaEditActionTest.class);
        
        suite.addTestSuite(PcmatDaoHibernateTest.class);
        suite.addTestSuite(PcmatManagerTest.class);
        suite.addTestSuite(PcmatEditActionTest.class);

        suite.addTestSuite(AreaVivenciaPcmatDaoHibernateTest.class);
        suite.addTestSuite(AreaVivenciaPcmatManagerTest.class);
        suite.addTestSuite(AreaVivenciaPcmatEditActionTest.class);
        
        suite.addTestSuite(AtividadeSegurancaPcmatDaoHibernateTest.class);
        suite.addTestSuite(AtividadeSegurancaPcmatManagerTest.class);
        suite.addTestSuite(AtividadeSegurancaPcmatEditActionTest.class);
        
        suite.addTestSuite(EpiPcmatDaoHibernateTest.class);
        suite.addTestSuite(EpiPcmatManagerTest.class);
        suite.addTestSuite(EpiPcmatEditActionTest.class);
        
        suite.addTestSuite(EpcPcmatDaoHibernateTest.class);
        suite.addTestSuite(EpcPcmatManagerTest.class);
        suite.addTestSuite(EpcPcmatEditActionTest.class);
        
        suite.addTestSuite(MotivoSolicitacaoEpiDaoHibernateTest.class);
        suite.addTestSuite(MotivoSolicitacaoEpiEditActionTest.class);
        
        suite.addTestSuite(TamanhoEPIManagerTest.class);
        suite.addTestSuite(TamanhoEpiEditActionTest.class);
        suite.addTestSuite(TamanhoEPIListActionTest.class);

        suite.addTestSuite(TipoTamanhoEPIDaoHibernateTest.class);
        suite.addTestSuite(TipoEpiEditActionTest.class);
        suite.addTestSuite(TipoEpiListActionTest.class);
        
        // UTILITARIOS
        suite.addTestSuite(AutenticadorTest.class);
        suite.addTestSuite(AuthenticatorImplTest.class);
        suite.addTestSuite(CheckListBoxUtilTest.class);
        suite.addTestSuite(CnpjUtilTest.class);
        suite.addTestSuite(CollectionUtilTest.class);
        suite.addTestSuite(ComparatorStringTest.class);
        suite.addTestSuite(ConverterUtilTest.class);
        suite.addTestSuite(DateUtilTest.class);
        suite.addTestSuite(HistoricoColaboradorUtilTest.class);
        suite.addTestSuite(LongUtilTest.class);
        suite.addTestSuite(IntegerUtilTest.class);
        suite.addTestSuite(MailTest.class);
        suite.addTestSuite(MathUtilTest.class);
        suite.addTestSuite(MontaRelatorioItextTest.class);
        suite.addTestSuite(SpringUtilTest.class);
        suite.addTestSuite(StringUtilTest.class);
        suite.addTestSuite(BooleanUtilTest.class);
        suite.addTestSuite(ZipTest.class);
        suite.addTestSuite(ArquivoUtilTest.class);
        suite.addTestSuite(NoticiaDaoHibernateTest.class);

        // Avaliacao
        suite.addTestSuite(AvaliacaoDaoHibernateTest.class);
        suite.addTestSuite(AvaliacaoManagerTest.class);
        suite.addTestSuite(AvaliacaoEditActionTest.class);
        suite.addTestSuite(AvaliacaoTurmaDaoHibernateTest.class);
        
        // Avaliacao de Experiencia
        suite.addTestSuite(PeriodoExperienciaDaoHibernateTest.class);
        suite.addTestSuite(PeriodoExperienciaManagerTest.class);
        suite.addTestSuite(PeriodoExperienciaEditActionTest.class);
        suite.addTestSuite(AvaliacaoExperienciaEditActionTest.class);
        suite.addTestSuite(PerguntaAvaliacaoEditActionTest.class);
        suite.addTestSuite(AcompanhamentoExperienciaColaboradorTest.class);

        // Avaliacao de Desempenho
        suite.addTestSuite(AvaliacaoDesempenhoDaoHibernateTest.class);
        suite.addTestSuite(AvaliacaoDesempenhoManagerTest.class);
        suite.addTestSuite(AvaliacaoDesempenhoEditActionTest.class);

        //Avaliação Prática
        suite.addTestSuite(AvaliacaoPraticaDaoHibernateTest.class);
        suite.addTestSuite(AvaliacaoPraticaManagerTest.class);
        suite.addTestSuite(AvaliacaoPraticaEditActionTest.class);
        
        // DICIONARIO
        suite.addTestSuite(StatusRetornoACTest.class);
        suite.addTestSuite(TipoAplicacaoIndiceTest.class);
        suite.addTestSuite(TipoPerguntaTest.class);
        suite.addTestSuite(TipoQuestionarioTest.class);
        suite.addTestSuite(AreasFormacaoTest.class);
        suite.addTestSuite(DeficienciaTest.class);
        suite.addTestSuite(EstadoTest.class);
        suite.addTestSuite(FiltrosRelatorioTest.class);
        suite.addTestSuite(MotivoHistoricoColaboradorTest.class);
        suite.addTestSuite(NivelIdiomaTest.class);
        suite.addTestSuite(OrigemCandidatoTest.class);
        suite.addTestSuite(SexoTest.class);
        suite.addTestSuite(SituacaoColaboradorTest.class);
        suite.addTestSuite(SituacaoFormacaoTest.class);
        suite.addTestSuite(SituacaoSolicitacaoTest.class);
        suite.addTestSuite(TipoAvaliacaoTest.class);
        suite.addTestSuite(TipoBuscaHistoricoColaboradorTest.class);
        suite.addTestSuite(TipoDeExposicaoTest.class);
        suite.addTestSuite(TipoFormacaoTest.class);
        suite.addTestSuite(TipoReajusteTest.class);
        suite.addTestSuite(TipoReajusteColaboradorTest.class);
        suite.addTestSuite(TipoExtintorTest.class);
        suite.addTestSuite(MotivoSolicitacaoExameTest.class);
        suite.addTestSuite(AptoTest.class);
        suite.addTestSuite(TipoAcidenteTest.class);
        suite.addTestSuite(TipoAvaliacaoCursoTest.class);
        suite.addTestSuite(StatusCandidatoSolicitacaoTest.class);
        suite.addTestSuite(OperacaoTest.class);
        suite.addTestSuite(MeioComunicacaoTest.class);
        suite.addTestSuite(EnviarParaTest.class);
        suite.addTestSuite(NivelIdiomaTest.class);
        suite.addTestSuite(ReajusteTest.class);

        //EXCEPTIONS
        suite.addTestSuite(ColecaoVaziaExceptionTest.class);
        suite.addTestSuite(FaixaJaCadastradaExceptionTest.class);
        suite.addTestSuite(IntegraACExceptionTest.class);
        suite.addTestSuite(AreaColaboradorExceptionTest.class);

        //WEB ACTION
        suite.addTestSuite(IndexTest.class);
        suite.addTestSuite(MyActionSupportEditTest.class);
        suite.addTestSuite(MyActionSupportListTest.class);

        //DWR
        suite.addTestSuite(AmbienteDWRTest.class);
        suite.addTestSuite(AreaOrganizacionalDWRTest.class);
        suite.addTestSuite(AspectoDWRTest.class);
        suite.addTestSuite(BairroDWRTest.class);
        suite.addTestSuite(CandidatoDWRTest.class);
        suite.addTestSuite(CargoDWRTest.class);
        suite.addTestSuite(CidadeDWRTest.class);
        suite.addTestSuite(ColaboradorDWRTest.class);
        suite.addTestSuite(ConhecimentoDWRTest.class);
        suite.addTestSuite(DiaTurmaDWRTest.class);
        suite.addTestSuite(DntDWRTest.class);
        suite.addTestSuite(EstabelecimentoDWRTest.class);
        suite.addTestSuite(GrupoOcupacionalDWRTest.class);
        suite.addTestSuite(FuncaoDWRTest.class);
        suite.addTestSuite(ReajusteDWRTest.class);
        suite.addTestSuite(HistoricoCandidatoDWRTest.class);
        suite.addTestSuite(ListaPresencaDWRTest.class);
        suite.addTestSuite(PerguntaDWRTest.class);
        suite.addTestSuite(RiscosDWRTest.class);
        suite.addTestSuite(TurmaDWRTest.class);
        suite.addTestSuite(UtilDWRTest.class);
        suite.addTestSuite(FaixaSalarialDWRTest.class);
        suite.addTestSuite(EnderecoDWRTest.class);
        suite.addTestSuite(SolicitacaoDWRTest.class);
        suite.addTestSuite(OcorrenciaDWRTest.class);
        suite.addTestSuite(AvaliacaoDesempenhoDWRTest.class);
        suite.addTestSuite(MorroDWRTest.class);

        //MODEL
        suite.addTestSuite(HistoricoColaboradorTest.class);
        suite.addTestSuite(UsuarioTest.class);
        suite.addTestSuite(ColaboradorRespostaTest.class);
        suite.addTestSuite(AfastamentoTest.class);
        suite.addTestSuite(CandidatoTest.class);
        suite.addTestSuite(PendenciaACTest.class);
        suite.addTestSuite(AreaOrganizacionalTest.class);
        suite.addTestSuite(ReportColumnTest.class);
        suite.addTestSuite(SolicitacaoExameTest.class);

        suite.addTestSuite(ResultadoPesquisaTest.class);
        suite.addTestSuite(ProgressaoColaboradorTest.class);

        suite.addTestSuite(AuditorialDaoHibernateTest.class);
        suite.addTestSuite(CoberturaGetSetTest.class);

        //SECURITY
        suite.addTestSuite(AuditoriaManagerTest.class);
        suite.addTestSuite(MenuTest.class);
        suite.addTestSuite(UserDetailsImplTest.class);

        //crud de Clientes somente para uso interno na Fortes Informática
        suite.addTestSuite(ClienteDaoHibernateTest.class);
        suite.addTestSuite(ClienteManagerTest.class);
        suite.addTestSuite(ClienteEditActionTest.class);
        
        // Config e Listener
        suite.addTestSuite(Log4jInitListenerTest.class);
        suite.addTestSuite(LogCleanerJobTest.class);
        suite.addTestSuite(ScriptReaderTest.class);

        // BACKUP e Ant Script
        suite.addTestSuite(BackupCleanerJobTest.class);
        suite.addTestSuite(BackupJobTest.class);
        suite.addTestSuite(BackupServiceImplTest.class);
        suite.addTestSuite(RunAntScriptTest.class);

        // AUDITORIA
        suite.addTestSuite(AtributosDaAuditoriaTest.class);
        suite.addTestSuite(AuditoriaGeralAdviceTest.class);
        suite.addTestSuite(AuditoriaPointcutTest.class);
        suite.addTestSuite(GeraDadosAuditadosTest.class);
        suite.addTestSuite(ProcuraChaveNaEntidadeTest.class);
        suite.addTestSuite(CrudAuditorCallbackImplTest.class);
        suite.addTestSuite(InsertAuditorCallbackImplTest.class);
        suite.addTestSuite(RemoveAuditorCallbackImplTest.class);
        suite.addTestSuite(UpdateAuditorCallbackImplTest.class);
        suite.addTestSuite(DateFormatJsonValueProcessorTest.class);
        suite.addTestSuite(AbstractModelJsonValueProcessorTest.class);
        
        suite.addTestSuite(LinkTagTest.class);
        suite.addTestSuite(F2rhFacadeTest.class);
        suite.addTestSuite(ExportacaoActionTest.class);

        //SERVICES
        suite.addTestSuite(RHServiceTest.class);
        suite.addTestSuite(RHServiceIntranetTest.class);
        
        //NoAllUnitTests (Deixar comentado pois não funciona no jenkins é só para o coverage)
//        suite.addTestSuite(ManagerAuditaTest.class);
//        suite.addTestSuite(TestsNoIncludeAllUnitTest.class);
        
        return suite;
    }
}