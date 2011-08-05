
insert into public."usuario" ("id", "nome", "login", "senha", "acessosistema")
values (1, 'Fortes', 'fortes', 'MTIzNA==', true);

alter sequence usuario_sequence restart with 2;

INSERT INTO grupoac (id, codigo, descricao, acurlsoap, acurlwsdl, acusuario, acsenha) VALUES (1,'001','AC Padrão','http://localhost:1024/soap/IAcPessoal','http://localhost:1024/wsdl/IAcPessoal','ADMIN','');
alter sequence grupoac_sequence restart with 2;

INSERT INTO empresa(ID,NOME,CNPJ,RAZAOSOCIAL,codigoAC,acintegra,emailRemetente,emailRespSetorPessoal,maxcandidatacargo,logourl,exibirsalario,grupoac, campoextracolaborador, mensagemmoduloexterno) VALUES (1,'Empresa Padrão','00000000','Empresa Padrão',null,false,'rh@empresapadrao.com.br','sp@empresapadrao.com.br', 5,'fortes.gif',true,'001',false, 'Se você não é registrado, cadastre já seu currículo e tenha acesso às vagas disponíveis em nossa empresa.');
alter sequence empresa_sequence restart with 2;

insert into exame (id, nome, periodicidade, periodico, empresa_id) values (1, 'ASO', 0, false, 1);
alter sequence exame_sequence restart with 2;

update empresa set exame_id =1;

INSERT INTO estabelecimento (id, nome, complementocnpj, empresa_id) values (1,'Estabelecimento Padrão','0000',1);
alter sequence estabelecimento_sequence restart with 2;

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (495, 'ROLE_VISUALIZAR_MSG', 'Visualizar Caixa de Mensagens', '#', 1, false, null);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (411, 'ROLE_VISUALIZAR_PENDENCIA_AC', 'Visualizar as pendências do AC', '', 2, false, null);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (382, 'ROLE_AVALDESEMPENHO', 'Aval. Desempenho', '#', 6, true, 'v', NULL);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (365, 'ROLE_T&D', 'T&D', '#', 7, true, 'T', NULL);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (373, 'ROLE_COLAB', 'Info. Funcionais', '#', 8, true, 'I', NULL);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (75, 'ROLE_SESMT', 'SESMT', '#', 10, true, 'S', NULL);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (37, 'ROLE_UTI', 'Utilitários', '#', 11, true, 'U', NULL);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (361, 'ROLE_C&S', 'C&S', '#', 4, true, 'C', NULL);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (353, 'ROLE_PES', 'Pesquisas', '#', 5, true, 'P', NULL);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) VALUES (357, 'ROLE_R&S', 'R&S', '#', 3, true, 'R', NULL);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (463, 'INATIVOS', 'Inativos', '#', 99, true, 37);

-- Modulo R&S
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (496, 'ROLE_VISUALIZAR_SOLICITACAO_PESSOAL', 'Visualizar Solicitação de Pessoal', '#', 14, false, 357);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (461, 'ROLE_R&S_IND', 'Indicadores', '#', 4, true, 357);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (358, 'ROLE_R&S_CAD', 'Cadastros', '#', 1, true, 357);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (2, 'ROLE_CAD_CANDIDATO', 'Candidatos', '/captacao/candidato/list.action', 1, true, 358);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (3, 'ROLE_CAD_ETAPA', 'Etapas Seletivas', '/captacao/etapaSeletiva/list.action', 2, true, 358);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (6, 'ROLE_CAD_BDS_EMPRESA', 'Empresas BDS', '/captacao/empresaBds/list.action', 3, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (4, 'ROLE_CAD_AREA', 'Áreas de Interesse', '/geral/areaInteresse/list.action', 4, true, 358);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (397, 'ROLE_AREAFORMACAO', 'Áreas de Formação', '/geral/areaFormacao/list.action', 5, true, 358);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (57, 'ROLE_MOTIVO_SOLICITACAO', 'Motivos de Solicitação de Pessoal', '/captacao/motivoSolicitacao/list.action', 6, true, 358);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (492, 'ROLE_MOV_SOLICITACAO', 'Modelos de Avaliação do Candidato', '/avaliacao/modeloCandidato/list.action?modeloAvaliacao=S', 7, true, 358);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (507, 'ROLE_COMO_FICOU_SABENDO_VAGA', 'Como Ficou Sabendo da Vaga', '/geral/comoFicouSabendoVaga/list.action', 8, true, 358);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (359, 'ROLE_R&S_MOV', 'Movimentações', '#', 2, true, 357);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (21, 'ROLE_MOV_SOLICITACAO', 'Solicitação de Pessoal', '/captacao/solicitacao/list.action', 1, true, 359);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (500, 'ROLE_SOLICITACAO_AGENDA', 'Agenda', '/captacao/solicitacao/agenda.action', 2, true, 359);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (510, 'ROLE_MOV_PALOGRAFICO', 'Exame Palográfico', '/captacao/candidato/prepareExamePalografico.action', 3, true, 359);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (50, 'ROLE_BD_SOLIDARIO', 'Banco de Dados Solidário', '/captacao/candidato/prepareBusca.action?BDS=true', 2, true, 463);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (360, 'ROLE_R&S_REL', 'Relatórios', '#', 3, true, 357);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (46, 'ROLE_REL_SOLICITACAO', 'Solicitações Abertas', '/captacao/solicitacao/prepareRelatorio.action', 1, true, 360);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (48, 'ROLE_REL_PROCESSO_SELETIVO', 'Análise das Etapas Seletivas', '/captacao/solicitacao/prepareRelatorioProcessoSeletivo.action', 2, true, 360);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (424, 'ROLE_REL_AVALIACAO_CANDIDATOS', 'Avaliações de Candidatos', '/captacao/candidato/prepareRelatorioAvaliacaoCandidatos.action', 3, true, 360);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (508, 'ROLE_COMO_FICOU_SABENDO_VAGA', 'Estatística de Divulgação da Vaga', '/geral/comoFicouSabendoVaga/prepareRelatorioComoFicouSabendoVaga.action', 4, true, 360);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (56, 'ROLE_LIBERA_SOLICITACAO', 'Liberador de Solicitação', '#', 4, false, 357);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (45, 'ROLE_MOV_SOLICITACAO_SELECAO', 'Recrutador(a)', '#', 4, false, 357);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (22, 'ROLE_MOV_SOLICITACAO_CANDIDATO', 'Ver Candidatos da Solicitação', '#', 4, false, 357);
-- Fim R&S

-- Modulo C&S
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (362, 'ROLE_C&S_CAD', 'Cadastros', '#', 1, true, 361);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (9, 'ROLE_CAD_AREA', 'Áreas Organizacionais', '/geral/areaOrganizacional/list.action', 1, true, 362);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (10, 'ROLE_CAD_GRUPO', 'Grupos Ocupacionais', '/cargosalario/grupoOcupacional/list.action', 2, true, 362);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (5, 'ROLE_CAD_CONHECIMENTO', 'Conhecimentos', '/captacao/conhecimento/list.action', 3, true, 362);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (493, 'ROLE_CAD_HABILIDADE', 'Habilidades', '/captacao/habilidade/list.action', 4, true, 362);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (494, 'ROLE_CAD_ATITUDE', 'Atitudes', '/captacao/atitude/list.action', 5, true, 362);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (516, 'ROLE_CAD_NIVEL_COMPETENCIA', 'Níveis de Competência', '/captacao/nivelCompetencia/list.action', 6, true, 362);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (11, 'ROLE_CAD_CARGO', 'Cargos e Faixas', '/cargosalario/cargo/list.action', 7, true, 362);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (499, 'ROLE_CAD_FAIXA_SALARIAL', 'Exibir Faixa Salarial de Cargos', '', 8, false, 362);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (404, 'ROLE_CAD_INDICE', 'Índices', '/cargosalario/indice/list.action', 9, true, 362);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (363, 'ROLE_C&S_MOV', 'Movimentações', '#', 2, true, 361);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (26, 'ROLE_MOV_SIMULACAOREAJUSTE', 'Planejamentos de Realinhamentos', '/cargosalario/tabelaReajusteColaborador/list.action', 1, true, 363);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (49, 'ROLE_MOV_SOLICITACAOREAJUSTE', 'Solicitação de Realinhamento de C&S', '/cargosalario/reajusteColaborador/prepareSolicitacaoReajuste.action', 2, true, 363);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (395, 'ROLE_DISSIDIO', 'Reajuste Coletivo', '/cargosalario/reajusteColaborador/prepareDissidio.action', 3, true, 363);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (478, 'ROLE_VER_AREAS', 'Visualizar todas as Áreas Organizacionais', '#', 0, false, 363);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (512, 'ROLE_MOV_DISSIDIO', 'Ajuste de Situação (Dissídio)', '/cargosalario/historicoColaborador/prepareAjusteDissidio.action', 4, true, 363);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (364, 'ROLE_C&S_REL', 'Relatórios', '#', 3, true, 361);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (54, 'ROLE_REL_CARGO', 'Descrição de Cargos', '/cargosalario/cargo/prepareRelatorioCargo.action', 1, true, 364);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (471, 'ROLE_REL_COLAB_CARGO', 'Colaboradores por Cargo', '/cargosalario/cargo/prepareRelatorioColaboradorCargo.action', 2, true, 364);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (35, 'ROLE_REL_SIMULACAOREAJUSTE', 'Realinhamentos', '/cargosalario/reajusteRelatorio/formFiltro.action', 3, true, 364);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (396, 'ROLE_REL_AREAORGANIZACIONAL', 'Colaboradores por Área Organizacional', '/geral/areaOrganizacionalRelatorio/formFiltro.action', 4, true, 364);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (406, 'ROLE_MOV_TABELA', 'Análise de Tabela Salarial', '/cargosalario/faixaSalarialHistorico/analiseTabelaSalarialFiltro.action', 5, true, 364);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (407, 'ROLE_REL_PROJECAO_SALARIAL', 'Projeção Salarial', '/geral/colaborador/prepareProjecaoSalarialFiltro.action',6, true, 364);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (473, 'ROLE_REL_SITUACOES', 'Situações', '/cargosalario/historicoColaborador/prepareRelatorioSituacoes.action', 7, true, 364);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (511, 'ROLE_REL_COLAB_SEM_REAJUSTE', 'Colaboradores sem Reajuste Salarial', '/cargosalario/historicoColaborador/prepareRelatorioUltimasPromocoes.action', 7, true, 364);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (417, 'ROLE_TRANSFERIR_FAIXAS_AC', 'Transferir Faixas entre Cargos', '', 4, false, 361);
-- Fim C&S
-- Modulo Pesquisas
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (355, 'ROLE_PES_MOV', 'Movimentações', '#', 1, true, 353);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (28, 'ROLE_MOV_QUESTIONARIO', 'Pesquisas', '/pesquisa/pesquisa/list.action', 1, true, 355);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (401, 'ROLE_RESPONDER_PESQUISA_POR_OUTRO_USUARIO', 'Pode Responder Pesquisa Por Outro Usuário', '#', 4, false, 353);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (59, 'ROLE_PESQUISA', 'Pode ver e responder Pesquisa', '#', 4, false, 353);
-- Fim Pesquisas
-- Modulo Aval. Desempenho
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (486, 'ROLE_REL_AVALIACAO', 'Relatórios', '#', 3, true, 382);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (481, 'ROLE_CAD_AVALIACAO', 'Cadastros', '#', 1, true, 382);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (482, 'ROLE_CAD_AVALIACAO', 'Avaliações de Desempenho/Acomp. do Período de Experiência', '/avaliacao/modelo/list.action', 1, true, 481);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (467, 'ROLE_CAD_PERIODOEXPERIENCIA', 'Dias do Acompanhamento do Período de Experiência', '/avaliacao/periodoExperiencia/list.action', 2, true, 481);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (384, 'ROLE_MOV_AVALIACAO', 'Movimentações', '#', 2, true, 382);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (484, 'ROLE_RESPONDE_AVALIACAO', 'Pode ver e responder Aval. Desempenho', '#', 3, false, 382);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (55, 'ROLE_MOV_AVALIACAO', 'Avaliações de Desempenho', '/avaliacao/desempenho/list.action', 1, true, 384);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (483, 'ROLE_MOV_AVALIACAO', 'Responder Avaliações de Desempenho', '/avaliacao/desempenho/avaliacaoDesempenhoQuestionarioList.action', 2, true, 384);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (487, 'ROLE_RESPONDER_AVALIACAO_POR_OUTRO_USUARIO', 'Pode Responder Avaliação Por Outro Usuário', '#', 2, false, null);
-- Fim Aval. Desempenho
-- Modulo T&D
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (366, 'ROLE_T&D_CAD', 'Cadastros', '#', 1, true, 365);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (13, 'ROLE_CAD_PRIORIDADETREINAMENTO', 'Prioridades de Treinamento', '/desenvolvimento/prioridadeTreinamento/list.action', 1, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (23, 'ROLE_MOV_CURSO', 'Cursos/Treinamentos', '/desenvolvimento/curso/list.action', 2, true, 366);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (413, 'ROLE_MOV_AVALIACAO_CURSO', 'Avaliações dos Cursos', '/desenvolvimento/avaliacaoCurso/list.action', 3, true, 366);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (419, 'ROLE_AVALIACAO_TURMA', 'Modelos de Avaliação de Turma', '/pesquisa/avaliacaoTurma/list.action', 4, true, 366);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (420, 'ROLE_CAD_CERTIFICACAO', 'Certificações', '/desenvolvimento/certificacao/list.action', 5, true, 366);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (367, 'ROLE_T&D_MOV', 'Movimentações', '#', 2, true, 365);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (64, 'ROLE_MOV_CURSO_DNT', 'DNT', '/desenvolvimento/dnt/list.action', 1, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (72, 'ROLE_MOV_CURSO_DNT_GESTOR', 'Preenchimento da DNT', '/desenvolvimento/dnt/list.action?gestor=true', 2, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (71, 'ROLE_FREQUENCIA', 'Frequência', '/desenvolvimento/turma/prepareFrequencia.action', 3, true, 367);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (414, 'ROLE_MOV_PLANO_TREINAMENTO', 'Plano de Treinamento', '/desenvolvimento/turma/filtroPlanoTreinamento.action', 4, true, 367);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (368, 'ROLE_T&D_REL', 'Relatórios', '#', 3, true, 365);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (31, 'ROLE_REL_MATRIZ', 'Matriz de Qualificação', '/desenvolvimento/turma/prepareImprimirMatriz.action', 1, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (32, 'ROLE_REL_PLANO', 'Plano de Treinamento', '/desenvolvimento/turma/prepareImprimirTurma.action', 2, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (33, 'ROLE_REL_SEM_INDICACAO', 'Colaboradores sem Indicação de Trein.', '/desenvolvimento/colaboradorTurma/prepareRelatorioColaboradorSemIndicacao.action', 3, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (43, 'ROLE_REL_DESENVOLVIMENTO_LISTA_PRESENCA', 'Lista de Frequência', '/desenvolvimento/relatorioPresenca/prepareRelatorio.action', 4, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (61, 'ROLE_CERTIFICADO_CURSO', 'Certificados', '/desenvolvimento/turma/prepareImprimirCertificado.action', 5, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (418, 'ROLE_CRONOGRAMA_TREINAMENTO', 'Cronograma de Treinamento', '/desenvolvimento/turma/filtroCronogramaTreinamento.action', 6, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (415, 'ROLE_REL_COLABORADOR_SEM_TREINAMENTO', 'Colaboradores sem Treinamentos', '/desenvolvimento/colaboradorTurma/prepareRelatorioColaborador.action', 7, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (416, 'ROLE_REL_COLABORADOR_COM_TREINAMENTO', 'Colaboradores com Treinamentos', '/desenvolvimento/colaboradorTurma/prepareRelatorioColaborador.action?comTreinamento=true', 8, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (421, 'ROLE_REL_MATRIZ_TREINAMENTO', 'Matriz de Treinamentos', '/desenvolvimento/certificacao/matrizTreinamento.action', 9, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (422, 'ROLE_REL_HISTORICO_TREINAMENTOS', 'Histórico de Treinamentos', '/desenvolvimento/colaboradorTurma/prepareFiltroHistoricoTreinamentos.action', 10, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (465, 'ROLE_REL_COLABORADORES_CERTIFICACOES', 'Colaboradores x Certificações', '/desenvolvimento/colaboradorTurma/prepareRelatorioColaboradorCertificacao.action', 11, true, 368);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (498, 'ROLE_CRONOGRAMA_TREINAMENTO', 'Relatorio de investimento de T&D', '/desenvolvimento/turma/relatorioInvestimento.action', 12, true, 368);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (454, 'ROLE_T&D_REL', 'Painel de Indicadores', '/desenvolvimento/indicadores/list.action', 4, true, 365);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (63, 'ROLE_MOV_TURMA', 'Pode Cadastrar Turma', '/desenvolvimento/turma/list.action', 4, false, 365);
-- Fim T&D
-- Modulo Info. Funcionais
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (374, 'ROLE_COLAB_CAD', 'Cadastros', '#', 1, true, 373);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (15, 'ROLE_CAD_GRUPOGASTO', 'Grupos de Investimento', '/geral/grupoGasto/list.action', 1, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (16, 'ROLE_CAD_GASTO', 'Investimentos', '/geral/gasto/list.action', 2, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (12, 'ROLE_CAD_BENEFICIO', 'Benefícios', '/geral/beneficio/list.action', 3, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (60, 'ROLE_CAD_MOTIVO_DEMISSAO', 'Motivos de Desligamento', '/geral/motivoDemissao/list.action', 4, true, 374);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (402, 'ROLE_MOV_QUESTIONARIO', 'Modelos de Entrevistas de Desligamento', '/pesquisa/entrevista/list.action', 5, true, 374);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (62, 'ROLE_CAD_OCORRENCIA', 'Tipos de Ocorrência', '/geral/ocorrencia/list.action', 6, true, 374);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (8, 'ROLE_CAD_COLABORADOR', 'Colaboradores', '/geral/colaborador/list.action', 7, true, 374);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (412, 'ROLE_CAD_INFO_PESSOAL', 'Atualizar meus dados', '/geral/colaborador/prepareUpdateInfoPessoais.action', 8, true, 374);


-- Modulo Info. Funcionais Movimentacoes
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (469, 'ROLE_COLAB_MOV', 'Movimentações', '#', 2, true, 373);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (470, 'ROLE_MOV_PERIODOEXPERIENCIA', 'Acompanhamento do Período de Experiência', '/avaliacao/avaliacaoExperiencia/periodoExperienciaQuestionarioList.action', 4, true, 384);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (480, 'ROLE_CAD_OCORRENCIA', 'Ocorrências', '/geral/colaboradorOcorrencia/list.action', 2, true, 469);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (29, 'ROLE_MOV_GASTO_GASTOEMPRESA', 'Investimentos da Empresa', '/geral/gastoEmpresa/list.action', 11, true, 463);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (377, 'ROLE_COLAB_REL', 'Relatórios', '#', 3, true, 373);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (93, 'ROLE_REL_OCORRENCIA', 'Ocorrências', '/geral/ocorrencia/prepareRelatorioOcorrencia.action', 1, true, 377);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (400, 'ROLE_REL_MOTIVO_DEMISSAO', 'Desligamentos', '/geral/motivoDemissao/prepareRelatorioMotivoDemissao.action', 2, true, 377);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (403, 'ROLE_MOV_QUESTIONARIO', 'Resultados das Entrevistas', '/pesquisa/questionario/prepareResultadoEntrevista.action', 3, true, 377);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (423, 'ROLE_REL_ANIVERSARIANTES', 'Aniversariantes do mês', '/geral/colaborador/prepareRelatorioAniversariantes.action', 4, true, 377);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (472, 'ROLE_REL_ADMITIDOS', 'Admitidos', '/geral/colaborador/prepareRelatorioAdmitidos.action', 5, true, 377);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (479, 'ROLE_CAD_PERIODOEXPERIENCIA', 'Resultado das Avaliações de Desempenho e Acompanhamento do Período de Experiência', '/avaliacao/avaliacaoExperiencia/prepareResultado.action', 1, true, 486);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (490, 'ROLE_REL_ACOMP_EXPERIENCIA_PREVISTO', 'Acompanhamento de Experiência Previsto', '/avaliacao/periodoExperiencia/prepareRelatorioAcompanhamentoExperienciaPrevisto.action', 2, true, 486);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (513, 'ROLE_REL_ACOMP_PERIODO_EXPERIENCIA', 'Acompanhamento do Período de Experiência', '/avaliacao/periodoExperiencia/prepareRelatorioAcompanhamentoExperiencia.action', 3, true, 486);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (491, 'ROLE_REL_ACOMP_RANKING_PERIODO_EXPERIENCIA', 'Ranking de Performance das Avaliações de Desempenho','/avaliacao/periodoExperiencia/prepareRelatorioRankingPerformancePeriodoDeExperiencia.action', 4, true,486);

-- Fim Info. Funcionais
-- Modulo Indicadores
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (73, 'ROLE_IND', 'Estatísticas de Vagas por Motivo', '/indicador/duracaoPreenchimentoVaga/prepareMotivo.action', 1, true, 461);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (69, 'ROLE_IND', 'Duração para Preenchimento de Vagas', '/indicador/duracaoPreenchimentoVaga/prepare.action', 2, true, 461);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (47, 'ROLE_REL_PRODUTIVIDADE', 'Análise das Etapas Seletivas', '/captacao/produtividade/prepareProdutividade.action', 3, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (70, 'ROLE_REL_PROMOCAO', 'Promoções', '/cargosalario/historicoColaborador/prepareRelatorioPromocoes.action', 6, true, 364);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (398, 'ROLE_REL_TURNOVER', 'Turnover (rotatividade)', '/indicador/indicadorTurnOver/prepare.action', 6, true, 377);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (509, 'ROLE_REL_ABSENTEISMO', 'Absenteísmo', '/geral/colaboradorOcorrencia/prepareRelatorioAbsenteismo.action', 7, true, 377);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (506, 'ROLE_REL_LISTA_COLAB', 'Listagem de Colaboradores', '/geral/colaborador/prepareRelatorioDinamico.action', 8, true, 377);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (504, 'ROLE_INFO_PAINEL_IND', 'Painel de Indicadores', '/cargosalario/historicoColaborador/painelIndicadores.action', 3, true, 373);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (36, 'ROLE_REL_GASTOEMPRESA', 'Investimentos da Empresa', '/geral/gastoEmpresa/prepareImprimir.action', 6, true, 463);
-- Fim Indicadores
-- Modulo Utilitarios
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (390, 'ROLE_UTI', 'Cadastros', '#', 1, true, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (58, 'ROLE_UTI_EMPRESA', 'Empresas', '/geral/empresa/list.action', 1, true, 390);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (18, 'ROLE_CAD_PERFIL', 'Perfis', '/acesso/perfil/list.action', 2, true, 390);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (393, 'ROLE_CAD_ESTABELECIMENTO', 'Estabelecimentos', '/geral/estabelecimento/list.action', 3, true, 390);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (19, 'ROLE_CAD_USUARIO', 'Usuários', '/acesso/usuario/list.action', 4, true, 390);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (394, 'ROLE_CAD_BAIRRO', 'Bairros', '/geral/bairro/list.action', 5, true, 390);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (501, 'ROLE_CAD_GRUPOAC', 'Grupos AC', '/geral/grupoAC/list.action', 6, true, 390);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (464, 'ROLE_IMPORTA_CADASTROS', 'Importar Cadastros', '/geral/empresa/prepareImportarCadastros.action', 8, true, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (38, 'ROLE_UTI_SENHA', 'Alterar Senha', '/acesso/usuario/prepareUpdateSenhaUsuario.action', 2, true, 37);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (41, 'ROLE_CONFIGURACAO', 'Configurações', '#', 3, true, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (485, 'ROLE_CAMPO_EXTRA', 'Campos Extras', '/geral/configuracaoCampoExtra/prepareUpdate.action', 2, true, 41);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (502, 'ROLE_UTI_CONFIGURACAO', 'Sistema', '/geral/parametrosDoSistema/prepareUpdate.action', 1, true, 41);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (503, 'ROLE_CONFIG_CANDIDATO_EXT', 'Cadastro de Candidato (externo)', '/geral/parametrosDoSistema/listCamposCandidato.action', 3, true, 41);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (39, 'ROLE_UTI_AUDITORIA', 'Auditoria', '/security/auditoria/prepareList.action', 5, true, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (44, 'ROLE_UTI_HISTORICO_VERSAO', 'Histórico de Versões', '/geral/documentoVersao/list.action', 6, true, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (409, 'ROLE_UTI', 'Enviar Mensagem', '/geral/usuarioMensagem/prepareUpdate.action', 7, true, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (466, 'ROLE_UTI_EMPRESA', 'Sobre...', '/geral/empresa/sobre.action', 8, true, 37);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (408, 'ROLE_MOV_SOLICITACAO_REALINHAMENTO', 'Pode Solicitar Realinhamento', '', 4, false, 361);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (505, 'ROLE_C&S_PAINEL_IND', 'Painel de Indicadores', '/cargosalario/historicoColaborador/painelIndicadoresCargoSalario.action', 4, true, 361);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (410, 'RECEBE_ALERTA_SETORPESSOAL', 'Recebe Mensagem do AC Pessoal', '', 5, false, 37);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (451, 'ROLE_LOGGING', 'Logs', '/logging/list.action', 8, true, 37);

-- Fim Utilitarios
-- Modulo SESMT
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (497, 'ROLE_RECEBE_EXAMES_PREVISTOS', 'Recebe email de exames previstos', '#', 1, false, 75);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (385, 'ROLE_SESMT', 'Cadastros', '#', 2, true, 75);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (456, 'ROLE_CAD_PCMSO', 'PCMSO', '#', 9, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (452, 'ROLE_CAD_EVENTO', 'Eventos', '/sesmt/evento/list.action', 1, true, 456);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (453, 'ROLE_CAD_AGENDA', 'Agenda', '/sesmt/agenda/list.action', 2, true, 456);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (76, 'ROLE_CAD_AMBIENTE', 'Ambientes', '/sesmt/ambiente/list.action', 5, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (77, 'ROLE_CAD_TIPO_EPI', 'Categorias de EPI', '/sesmt/tipoEPI/list.action', 1, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (78, 'ROLE_CAD_EPI', 'EPI', '/sesmt/epi/list.action', 2, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (86, 'ROLE_CAD_EPC', 'EPC', '/sesmt/epc/list.action', 3, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (79, 'ROLE_CAD_RISCO', 'Riscos', '/sesmt/risco/list.action', 4, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (89, 'ROLE_CAD_ENGENHEIRO_TRABALHO', 'Engenheiros Responsáveis', '/sesmt/engenheiroResponsavel/list.action', 6, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (90, 'ROLE_CAD_MEDICO_COORDENADOR', 'Médicos Coordenadores', '/sesmt/medicoCoordenador/list.action', 11, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (91, 'ROLE_CAD_CLINICA_AUTORIZADA', 'Clínicas e Médicos Autorizados', '/sesmt/clinicaAutorizada/list.action', 12, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (87, 'ROLE_CAD_EXAME', 'Exames', '/sesmt/exame/list.action', 10, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (427, 'ROLE_CAD_FICHAMEDICA', 'Modelos de Fichas Médicas', '/sesmt/fichaMedica/list.action', 13, true, 385);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (455, 'ROLE_CAD_ELEICAO', 'CIPA', '#', 8, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (437, 'ROLE_CAD_ETAPAPROCESSOELEITORAL', 'Etapas do Processo Eleitoral', '/sesmt/etapaProcessoEleitoral/list.action', 1, true, 455);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (438, 'ROLE_CAD_ELEICAO', 'Eleições', '/sesmt/eleicao/list.action', 2, true, 455);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (439, 'ROLE_CAD_ELEICAO', 'Comissões', '/sesmt/comissao/list.action', 3, true, 455);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (440, 'ROLE_CAD_AFASTAMENTO', 'Motivos de Afastamentos', '/sesmt/afastamento/list.action', 14, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (445, 'ROLE_CAD_EXTINTOR', 'Extintores', '/sesmt/extintor/list.action', 7, true, 385);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (476, 'ROLE_FUNCAO', 'Funções', '/sesmt/funcao/listFiltro.action', 15, true, 385);


INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (386, 'ROLE_SESMT', 'Movimentações', '#', 3, true, 75);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (426, 'ROLE_CAD_SOLICITACAOEXAME', 'Solicitações/Atendimentos Médicos', '/sesmt/solicitacaoExame/list.action', 5, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (66, 'ROLE_SESMT_MUDANCA_FUNCAO', 'Mudança de Função', '/sesmt/funcao/mudancaFuncaoFiltro.action', 5, true, 463);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (425, 'ROLE_CAD_PRONTUARIO', 'Registro de Prontuário', '/sesmt/prontuario/list.action', 6, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (428, 'ROLE_CAD_FICHAMEDICA', 'Fichas Médicas', '/sesmt/fichaMedica/listPreenchida.action', 7, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (433, 'ROLE_CAD_SOLICITACAOEPI', 'Solicitação de EPIs', '/sesmt/solicitacaoEpi/list.action', 2, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (435, 'ROLE_CAD_ENTREGAEPI', 'Entrega de EPIs', '/sesmt/solicitacaoEpi/list.action', 3, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (441, 'ROLE_CAD_AFASTAMENTO', 'Afastamentos', '/sesmt/colaboradorAfastamento/list.action', 8, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (443, 'ROLE_CAT', 'CATs (Acidentes de Trabalho)', '/sesmt/cat/list.action', 9, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (457, 'ROLE_CAD_EXTINTOR', 'Extintores', '#', 4, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (446, 'ROLE_CAD_EXTINTOR', 'Inspeção', '/sesmt/extintorInspecao/list.action', 1, true, 457);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (447, 'ROLE_CAD_EXTINTOR', 'Manutenção', '/sesmt/extintorManutencao/list.action', 2, true, 457);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (387, 'ROLE_SESMT', 'Relatórios', '#', 4, true, 75);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (488, 'ROLE_CAT', 'CATs (Acidentes de Trabalho)', '/sesmt/cat/prepareRelatorioCats.action', 16, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (388, 'ROLE_PPRA', 'PPRA e LTCAT', '/sesmt/ppra/prepareRelatorio.action', 1, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (389, 'ROLE_CAD_PCMSO', 'PCMSO', '/sesmt/pcmso/prepareRelatorio.action', 7, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (85, 'ROLE_PPP', 'PPP', '/sesmt/ppp/list.action', 2, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (429, 'ROLE_REL_PRONTUARIO', 'Prontuário', '/sesmt/prontuario/prepareRelatorioProntuario.action', 8, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (431, 'ROLE_REL_EXAMES_PREVISTOS', 'Exames Previstos', '/sesmt/exame/prepareRelatorioExamesPrevistos.action', 10, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (432, 'ROLE_REL_FICHA_EPI', 'Ficha de EPI', '/sesmt/epi/prepareImprimirFicha.action', 3, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (434, 'ROLE_CAD_EPICAVENCER', 'EPIs com CA a Vencer', '/sesmt/epi/prepareImprimirVencimentoCa.action', 4, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (436, 'ROLE_REL_EPIVENCIMENTO', 'EPIs com Prazo a Vencer', '/sesmt/solicitacaoEpi/prepareRelatorioVencimentoEpi.action', 5, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (442, 'ROLE_CAD_AFASTAMENTO', 'Afastamentos', '/sesmt/colaboradorAfastamento/prepareRelatorioAfastamentos.action', 11, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (448, 'ROLE_CAD_EXTINTOR', 'Extintores - Manutenção e Inspeção', '/sesmt/extintor/prepareRelatorio.action', 6, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (449, 'ROLE_CAD_FICHAMEDICA', 'Resultado de Fichas Médicas', '/sesmt/fichaMedica/prepareResultadoFichaMedica.action', 12, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (450, 'ROLE_CAD_MEDICAORISCO', 'Medição dos Riscos nos Ambientes', '/sesmt/medicaoRisco/list.action', 1, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (477, 'ROLE_AMBIENTE', 'Ambientes e Funções do Colaborador', '/cargosalario/historicoColaborador/prepareUpdateAmbientesEFuncoes.action', 5, true, 386);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (459, 'ROLE_CAD_SOLICITACAOEXAME', 'Atendimentos Médicos', '/sesmt/solicitacaoExame/prepareRelatorioAtendimentosMedicos.action', 14, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (460, 'ROLE_CAD_SOLICITACAOEXAME', 'Exames Realizados', '/sesmt/exame/prepareRelatorioExamesRealizados.action', 15, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (458, 'ROLE_FUNCAO', 'Distribuição de Colaboradores por Função', '/sesmt/funcao/prepareRelatorioQtdPorFuncao.action', 13, true, 387);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (489, 'ROLE_REL_EPIVENCIMENTO', 'EPIs Entregues', '/sesmt/solicitacaoEpi/prepareRelatorioEntregaEpi.action', 4, true, 387);

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (74, 'ROLE_FUNCAO', 'Funções', '/sesmt/funcao/list.action', 5, false, 75);
-- Fim SESMT

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (474, 'ROLE_COMPROU_SESMT', 'Exibir informações do SESMT', '#', 0, false, null);
INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (475, 'ROLE_CAD_CLIENTE', 'Clientes', '/geral/cliente/list.action', 12, false, null);

alter sequence papel_sequence restart with 517;

insert into public."perfil" ("id", "nome") values (1, 'Administrador');
insert into public."perfil" ("id", "nome") values (2, 'Usuário');

insert into public."perfil_papel" ("perfil_id", "papeis_id") values (2, 37);
insert into public."perfil_papel" ("perfil_id", "papeis_id") values (2, 38);

alter sequence perfil_sequence restart with 3;

insert into public."areaformacao" ("id", "nome") values (1, 'Administrativa');
insert into public."areaformacao" ("id", "nome") values (2, 'Administrativo Comercial');

alter sequence areaformacao_sequence restart with 75;

insert into public."idioma" ("id", "nome") values (1, 'Inglês');
insert into public."idioma" ("id", "nome") values (2, 'Espanhol');
insert into public."idioma" ("id", "nome") values (3, 'Francês');
insert into public."idioma" ("id", "nome") values (4, 'Alemão');
insert into public."idioma" ("id", "nome") values (5, 'Italiano');

alter sequence idioma_sequence restart with 6;

INSERT INTO estado VALUES (nextval('estado_sequence'), 'CE', 'Ceará');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'PE', 'Pernambuco');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'RN', 'Rio Grande do Norte');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'RJ', 'Rio de Janeiro');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'SP', 'São Paulo');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'RS', 'Rio Grande do Sul');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'MG', 'Minas Gerais');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'AC', 'Acre');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'AL', 'Alagoas');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'AM', 'Amazonas');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'AP', 'Amapá');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'BA', 'Bahia');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'DF', 'Distrito Federal');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'ES', 'Espírito Santo');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'GO', 'Goiás');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'MA', 'Maranhão');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'MS', 'Mato Grosso do Sul');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'MT', 'Mato Grosso');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'PA', 'Pará');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'PB', 'Paraíba');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'PI', 'Piauí');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'PR', 'Paraná');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'RO', 'Rondônia');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'RR', 'Roraima');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'SC', 'Santa Catarina');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'SE', 'Sergipe');
INSERT INTO estado VALUES (nextval('estado_sequence'), 'TO', 'Tocantins');

insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'RO'),'00031','Cabixi');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04350','Forquilha');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04400','Fortaleza');
insert into cidade(id, uf_id, codigoac, nome) VALUES (nextval('cidade_sequence'),(select e.id from estado e where e.sigla = 'CE'),'04459','Fortim');

INSERT INTO extintorinspecaoitem VALUES(1, 'Lacre');
INSERT INTO extintorinspecaoitem VALUES(2, 'Selo');
INSERT INTO extintorinspecaoitem VALUES(3, 'Trava');
INSERT INTO extintorinspecaoitem VALUES(4, 'Manômetro');
INSERT INTO extintorinspecaoitem VALUES(5, 'Sinalização Vertical');
INSERT INTO extintorinspecaoitem VALUES(6, 'Sinalização Horizontal');
INSERT INTO extintorinspecaoitem VALUES(7, 'Localização');
INSERT INTO extintorinspecaoitem VALUES(8, 'Alça');
INSERT INTO extintorinspecaoitem VALUES(9, 'Gatilho');
INSERT INTO extintorinspecaoitem VALUES(10, 'Mangueira');
INSERT INTO extintorInspecaoItem (id, descricao) VALUES (11, 'Outro');

INSERT INTO extintormanutencaoservico VALUES(1, 'Recarga');
INSERT INTO extintormanutencaoservico VALUES(2, 'Pintura');
INSERT INTO extintormanutencaoservico VALUES(3, 'Teste Hidrostático');
INSERT INTO extintormanutencaoservico VALUES(4, 'Manômetro');
INSERT INTO extintormanutencaoservico VALUES(5, 'Substituição de Gatilho');
INSERT INTO extintormanutencaoservico VALUES(6, 'Válvula de Segurança');
INSERT INTO extintormanutencaoservico VALUES(7, 'Substituição de Difusor');
INSERT INTO extintormanutencaoservico VALUES(8, 'Válvula Completa');
INSERT INTO extintormanutencaoservico VALUES(9, 'Mangote');
INSERT INTO extintormanutencaoservico VALUES(10, 'Válvula Cilindro Adicional');

insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (1,'f','f','texto1','Campo de Texto 1', 1,'texto',1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (2,'f','f','texto2','Campo de Texto 2', 1,'texto',2);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (3,'f','f','texto3','Campo de Texto 3', 1,'texto',3);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (4,'f','f','data1','Campo de Data 1', 1,'data',11);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (5,'f','f','data2','Campo de Data 2', 1,'data',12);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (6,'f','f','data3','Campo de Data 3', 1,'data',13);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (7,'f','f','valor1','Campo de Valor 1', 1,'valor',14);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (8,'f','f','valor2','Campo de Valor 2', 1,'valor',15);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (9,'f','f','numero1','Campo de Numero', 1,'numero',16);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (10,'f','f','texto4','Campo de Texto 4', 1,'texto',4);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (11,'f','f','texto5','Campo de Texto 5', 1,'texto',5);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (12,'f','f','texto6','Campo de Texto 6', 1,'texto',6);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (13,'f','f','texto7','Campo de Texto 7', 1,'texto',7);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (14,'f','f','texto8','Campo de Texto 8', 1,'texto',8);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (15,'f','f','texto9','Campo de Texto 9', 1,'texto',9);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao) values (16,'f','f','texto10','Campo de Texto 10', 1,'texto',10);

insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (17,'f','f','texto1','Campo de Texto 1', 1,'texto',1, 1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (18,'f','f','texto2','Campo de Texto 2', 1,'texto',2, 1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (19,'f','f','texto3','Campo de Texto 3', 1,'texto',3, 1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (20,'f','f','data1','Campo de Data 1', 1,'data',11, 1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (21,'f','f','data2','Campo de Data 2', 1,'data',12, 1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (22,'f','f','data3','Campo de Data 3', 1,'data',13, 1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (23,'f','f','valor1','Campo de Valor 1', 1,'valor',14, 1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (24,'f','f','valor2','Campo de Valor 2', 1,'valor',15, 1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (25,'f','f','numero1','Campo de Numero', 1,'numero',16, 1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (26,'f','f','texto4','Campo de Texto 4', 1,'texto',4, 1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (27,'f','f','texto5','Campo de Texto 5', 1,'texto',5, 1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (28,'f','f','texto6','Campo de Texto 6', 1,'texto',6, 1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (29,'f','f','texto7','Campo de Texto 7', 1,'texto',7, 1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (30,'f','f','texto8','Campo de Texto 8', 1,'texto',8, 1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (31,'f','f','texto9','Campo de Texto 9', 1,'texto',9, 1);
insert into configuracaocampoextra (id,ativocolaborador,ativocandidato,nome,descricao,ordem,tipo,posicao, empresa_id) values (32,'f','f','texto10','Campo de Texto 10', 1,'texto',10, 1);

alter sequence configuracaocampoextra_sequence restart with 33;

insert into codigoCBO (codigo, descricao) values ('519805','Profissional do sexo');
insert into codigoCBO (codigo, descricao) values ('317105','Programador de internet');
insert into codigoCBO (codigo, descricao) values ('317120','Programador de multimídia');
insert into codigoCBO (codigo, descricao) values ('317115','Programador de máquinas - ferramenta com comando numérico');
insert into codigoCBO (codigo, descricao) values ('317110','Programador de sistemas de informaçao');

insert into comoFicouSabendoVaga (id, nome) values (1, 'Outro');

insert into cid (codigo, descricao) values ('A000','Cólera devida a Vibrio cholerae 01, biótipo cholerae');
insert into cid (codigo, descricao) values ('A001','Cólera devida a Vibrio cholerae 01, biótipo El Tor');
insert into cid (codigo, descricao) values ('A009','Cólera não especificada');
insert into cid (codigo, descricao) values ('A010','Febre tifóide');
insert into cid (codigo, descricao) values ('A011','Febre paratifóide A');
insert into cid (codigo, descricao) values ('A012','Febre paratifóide B');

insert into parametrosdosistema (id, appurl, appcontext, appversao, servidorremprot, emailport, uppercase, enviaremail, perfilpadrao_id, acversaowebservicecompativel, diasLembretePeriodoExperiencia, camposCandidatoVisivel, camposCandidatoObrigatorio, camposCandidatoTabs)
values (1, 'http://localhost:8080/fortesrh', '/fortesrh', '1.1.48.40', '', '25', false,false, 2, '1.0.1.44', 3, 
'nome,nascimento,naturalidade,sexo,cpf,escolaridade,endereco,email,fone,celular,nomeContato,parentes,estadoCivil,qtdFilhos,nomeConjuge,profConjuge,nomePai,profPai,nomeMae,profMae,pensao,possuiVeiculo,deficiencia,formacao,idioma,desCursos,funcaoPretendida,areasInteresse,conhecimentos,colocacao,expProfissional,infoAdicionais,identidade,cartairaHabilitacao,tituloEleitoral,certificadoMilitar,ctps',
'nome,cpf,escolaridade,ende,num,cidade,fone',
'abaDocumentos,abaExperiencias,abaPerfilProfissional,abaFormacaoEscolar,abaDadosPessoais,abaCurriculo'
);

alter sequence parametrosdosistema_sequence restart with 2;
