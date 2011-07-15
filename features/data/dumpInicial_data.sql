--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

--
-- Name: afastamento_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('afastamento_sequence', 1, false);


--
-- Name: agenda_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('agenda_sequence', 1, false);


--
-- Name: ambiente_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ambiente_sequence', 1, false);


--
-- Name: anexo_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('anexo_sequence', 1, false);


--
-- Name: anuncio_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('anuncio_sequence', 1, false);


--
-- Name: aproveitamentoavaliacaocurso_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('aproveitamentoavaliacaocurso_sequence', 1, false);


--
-- Name: areaformacao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('areaformacao_sequence', 75, false);


--
-- Name: areainteresse_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('areainteresse_sequence', 1, true);


--
-- Name: areaorganizacional_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('areaorganizacional_sequence', 2, true);


--
-- Name: aspecto_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('aspecto_sequence', 1, false);


--
-- Name: atitude_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('atitude_sequence', 1, true);


--
-- Name: auditoria_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('auditoria_sequence', 11, true);


--
-- Name: avaliacaocurso_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('avaliacaocurso_sequence', 1, false);


--
-- Name: avaliacaodesempenho_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('avaliacaodesempenho_sequence', 1, false);


--
-- Name: avaliacaoturma_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('avaliacaoturma_sequence', 1, false);


--
-- Name: bairro_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('bairro_sequence', 1, false);


--
-- Name: beneficio_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('beneficio_sequence', 1, false);


--
-- Name: camposextras_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('camposextras_sequence', 1, false);


--
-- Name: candidato_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('candidato_sequence', 1, false);


--
-- Name: candidatocurriculo_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('candidatocurriculo_sequence', 1, false);


--
-- Name: candidatoeleicao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('candidatoeleicao_sequence', 1, false);


--
-- Name: candidatoidioma_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('candidatoidioma_sequence', 1, false);


--
-- Name: candidatosolicitacao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('candidatosolicitacao_sequence', 1, false);


--
-- Name: cargo_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cargo_sequence', 1, true);


--
-- Name: cat_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cat_sequence', 1, false);


--
-- Name: certificacao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('certificacao_sequence', 1, false);


--
-- Name: cidade_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cidade_sequence', 5507, true);


--
-- Name: cliente_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cliente_sequence', 1, false);


--
-- Name: clinicaautorizada_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('clinicaautorizada_sequence', 1, false);


--
-- Name: colaborador_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('colaborador_sequence', 1, false);


--
-- Name: colaboradorafastamento_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('colaboradorafastamento_sequence', 1, false);


--
-- Name: colaboradoridioma_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('colaboradoridioma_sequence', 1, false);


--
-- Name: colaboradorocorrencia_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('colaboradorocorrencia_sequence', 1, false);


--
-- Name: colaboradorpresenca_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('colaboradorpresenca_sequence', 1, false);


--
-- Name: colaboradorquestionario_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('colaboradorquestionario_sequence', 1, false);


--
-- Name: colaboradorresposta_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('colaboradorresposta_sequence', 1, false);


--
-- Name: colaboradorturma_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('colaboradorturma_sequence', 1, false);


--
-- Name: comissao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comissao_sequence', 1, false);


--
-- Name: comissaoeleicao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comissaoeleicao_sequence', 1, false);


--
-- Name: comissaomembro_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comissaomembro_sequence', 1, false);


--
-- Name: comissaoperiodo_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comissaoperiodo_sequence', 1, false);


--
-- Name: comissaoplanotrabalho_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comissaoplanotrabalho_sequence', 1, false);


--
-- Name: comissaoreuniao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comissaoreuniao_sequence', 1, false);


--
-- Name: comissaoreuniaopresenca_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comissaoreuniaopresenca_sequence', 1, false);


--
-- Name: configuracaocampoextra_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('configuracaocampoextra_sequence', 10, false);


--
-- Name: configuracaoimpressaocurriculo_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('configuracaoimpressaocurriculo_sequence', 1, false);


--
-- Name: configuracaoperformance_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('configuracaoperformance_sequence', 1, false);


--
-- Name: configuracaorelatoriodinamico_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('configuracaorelatoriodinamico_sequence', 1, false);


--
-- Name: conhecimento_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('conhecimento_sequence', 1, true);


--
-- Name: curso_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('curso_sequence', 1, false);


--
-- Name: dependente_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('dependente_sequence', 1, false);


--
-- Name: diaturma_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('diaturma_sequence', 1, false);


--
-- Name: dnt_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('dnt_sequence', 1, false);


--
-- Name: documentoanexo_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('documentoanexo_sequence', 1, false);


--
-- Name: eleicao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('eleicao_sequence', 1, false);


--
-- Name: empresa_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('empresa_sequence', 2, false);


--
-- Name: empresabds_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('empresabds_sequence', 1, false);


--
-- Name: engenheiroresponsavel_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('engenheiroresponsavel_sequence', 1, false);


--
-- Name: entrevista_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('entrevista_sequence', 1, false);


--
-- Name: epc_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('epc_sequence', 1, false);


--
-- Name: epi_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('epi_sequence', 1, false);


--
-- Name: epihistorico_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('epihistorico_sequence', 1, false);


--
-- Name: estabelecimento_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('estabelecimento_sequence', 2, false);


--
-- Name: estado_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('estado_sequence', 27, true);


--
-- Name: etapaprocessoeleitoral_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('etapaprocessoeleitoral_sequence', 1, false);


--
-- Name: etapaseletiva_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('etapaseletiva_sequence', 1, true);


--
-- Name: evento_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('evento_sequence', 1, false);


--
-- Name: exame_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('exame_sequence', 2, false);


--
-- Name: examesolicitacaoexame_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('examesolicitacaoexame_sequence', 1, false);


--
-- Name: experiencia_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('experiencia_sequence', 1, false);


--
-- Name: extintor_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('extintor_sequence', 1, false);


--
-- Name: extintorinspecao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('extintorinspecao_sequence', 1, false);


--
-- Name: extintorinspecaoitem_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('extintorinspecaoitem_sequence', 1, false);


--
-- Name: extintormanutencao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('extintormanutencao_sequence', 1, false);


--
-- Name: extintormanutencaoservico_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('extintormanutencaoservico_sequence', 1, false);


--
-- Name: faixasalarial_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('faixasalarial_sequence', 1, true);


--
-- Name: faixasalarialhistorico_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('faixasalarialhistorico_sequence', 1, true);


--
-- Name: fichamedica_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('fichamedica_sequence', 1, false);


--
-- Name: formacao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('formacao_sequence', 1, false);


--
-- Name: funcao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('funcao_sequence', 1, false);


--
-- Name: gasto_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gasto_sequence', 1, false);


--
-- Name: gastoempresa_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gastoempresa_sequence', 1, false);


--
-- Name: gastoempresaitem_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('gastoempresaitem_sequence', 1, false);


--
-- Name: grupoac_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('grupoac_sequence', 2, false);


--
-- Name: grupogasto_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('grupogasto_sequence', 1, false);


--
-- Name: grupoocupacional_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('grupoocupacional_sequence', 1, true);


--
-- Name: habilidade_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('habilidade_sequence', 1, true);


--
-- Name: historicoambiente_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('historicoambiente_sequence', 1, false);


--
-- Name: historicobeneficio_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('historicobeneficio_sequence', 1, false);


--
-- Name: historicocandidato_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('historicocandidato_sequence', 1, false);


--
-- Name: historicocolaborador_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('historicocolaborador_sequence', 1, false);


--
-- Name: historicocolaboradorbeneficio_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('historicocolaboradorbeneficio_sequence', 1, false);


--
-- Name: historicofuncao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('historicofuncao_sequence', 1, false);


--
-- Name: idioma_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('idioma_sequence', 6, false);


--
-- Name: indice_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indice_sequence', 1, true);


--
-- Name: indicehistorico_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('indicehistorico_sequence', 1, true);


--
-- Name: medicaorisco_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('medicaorisco_sequence', 1, false);


--
-- Name: medicocoordenador_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('medicocoordenador_sequence', 1, false);


--
-- Name: mensagem_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('mensagem_sequence', 1, false);


--
-- Name: motivodemissao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('motivodemissao_sequence', 1, false);


--
-- Name: motivosolicitacao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('motivosolicitacao_sequence', 1, true);


--
-- Name: ocorrencia_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ocorrencia_sequence', 1, false);


--
-- Name: papel_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('papel_sequence', 505, false);


--
-- Name: parametrosdosistema_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('parametrosdosistema_sequence', 2, false);


--
-- Name: perfil_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('perfil_sequence', 3, false);


--
-- Name: pergunta_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pergunta_sequence', 1, false);


--
-- Name: periodoexperiencia_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('periodoexperiencia_sequence', 1, true);


--
-- Name: pesquisa_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pesquisa_sequence', 1, false);


--
-- Name: prioridadetreinamento_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('prioridadetreinamento_sequence', 1, false);


--
-- Name: prontuario_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('prontuario_sequence', 1, false);


--
-- Name: questionario_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('questionario_sequence', 1, true);


--
-- Name: reajustecolaborador_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('reajustecolaborador_sequence', 1, false);


--
-- Name: realizacaoexame_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('realizacaoexame_sequence', 1, false);


--
-- Name: resposta_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('resposta_sequence', 1, false);


--
-- Name: risco_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('risco_sequence', 1, false);


--
-- Name: riscoambiente_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('riscoambiente_sequence', 1, false);


--
-- Name: riscomedicaorisco_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('riscomedicaorisco_sequence', 1, false);


--
-- Name: solicitacao_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('solicitacao_sequence', 1, false);


--
-- Name: solicitacaobds_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('solicitacaobds_sequence', 1, false);


--
-- Name: solicitacaoepi_item_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('solicitacaoepi_item_sequence', 1, false);


--
-- Name: solicitacaoepi_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('solicitacaoepi_sequence', 1, false);


--
-- Name: solicitacaoexame_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('solicitacaoexame_sequence', 1, false);


--
-- Name: tabelareajustecolaborador_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tabelareajustecolaborador_sequence', 1, false);


--
-- Name: tipoepi_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tipoepi_sequence', 1, false);


--
-- Name: turma_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('turma_sequence', 1, false);


--
-- Name: usuario_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuario_sequence', 2, false);


--
-- Name: usuarioempresa_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuarioempresa_sequence', 1, false);


--
-- Name: usuariomensagem_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuariomensagem_sequence', 1, false);


--
-- Data for Name: afastamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY afastamento (id, inss, descricao) FROM stdin;
\.


--
-- Data for Name: estado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY estado (id, sigla, nome) FROM stdin;
1	CE	Ceará
2	PE	Pernambuco
\.


--
-- Data for Name: cidade; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cidade (id, nome, codigoac, uf_id) FROM stdin;
937	Crateús	04103	1
946	Fortaleza	04400	1
\.


--
-- Data for Name: grupoac; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY grupoac (id, codigo, descricao, acurlsoap, acurlwsdl, acusuario, acsenha) FROM stdin;
1	001	AC Padrão	http://localhost:1024/soap/IAcPessoal	http://localhost:1024/wsdl/IAcPessoal	ADMIN	
\.


--
-- Data for Name: empresa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY empresa (id, nome, cnpj, razaosocial, codigoac, emailremetente, emailrespsetorpessoal, emailresprh, cnae, grauderisco, representantelegal, nitrepresentantelegal, horariotrabalho, endereco, acintegra, maxcandidatacargo, logourl, exibirsalario, uf_id, cidade_id, atividade, mensagemmoduloexterno, exibirdadosambiente, logocertificadourl, grupoac) FROM stdin;
1	Empresa Padrão	00000000	Empresa Padrão	\N	rh@empresapadrao.com.br	sp@empresapadrao.com.br	\N	\N	\N	\N	\N	\N	\N	f	5	fortes.gif	t	\N	\N	\N	\N	f	\N	001
\.


--
-- Data for Name: estabelecimento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY estabelecimento (id, nome, logradouro, numero, complemento, bairro, cep, complementocnpj, codigoac, uf_id, cidade_id, empresa_id) FROM stdin;
1	Estabelecimento Padrão	\N	\N	\N	\N	\N	0000	\N	\N	\N	1
\.


--
-- Data for Name: evento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY evento (id, nome) FROM stdin;
\.


--
-- Data for Name: agenda; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY agenda (id, data, evento_id, estabelecimento_id) FROM stdin;
\.


--
-- Data for Name: ambiente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ambiente (id, nome, empresa_id, estabelecimento_id) FROM stdin;
\.


--
-- Data for Name: anexo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY anexo (id, nome, observacao, url, origem, origemid) FROM stdin;
\.


--
-- Data for Name: areaorganizacional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY areaorganizacional (id, nome, codigoac, areamae_id, responsavel_id, empresa_id, ativo) FROM stdin;
1	Administração		\N	\N	1	t
2	Desenvolvimento		1	\N	1	t
\.


--
-- Data for Name: periodoexperiencia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY periodoexperiencia (id, dias, empresa_id) FROM stdin;
1	30	1
\.


--
-- Data for Name: avaliacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY avaliacao (id, titulo, cabecalho, ativo, empresa_id, tipomodeloavaliacao, periodoexperiencia_id) FROM stdin;
\.


--
-- Data for Name: grupoocupacional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY grupoocupacional (id, nome, empresa_id) FROM stdin;
1	Desenvolvimento	1
\.


--
-- Data for Name: cargo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cargo (id, nome, nomemercado, missao, competencias, responsabilidades, escolaridade, experiencia, recrutamento, selecao, observacao, cbocodigo, grupoocupacional_id, empresa_id, ativo, exibirmoduloexterno, atitude) FROM stdin;
1	Analista	Analista							\N			\N	1	t	t	
\.


--
-- Data for Name: faixasalarial; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY faixasalarial (id, nome, codigoac, cargo_id, nomeacpessoal) FROM stdin;
1	Senior		1	
\.


--
-- Data for Name: motivosolicitacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY motivosolicitacao (id, descricao) FROM stdin;
1	Aumento de quadro
\.


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuario (id, nome, login, senha, acessosistema, ultimologin) FROM stdin;
1	Fortes	fortes	MTIzNA==	t	2011-04-22 09:28:32.077
\.


--
-- Data for Name: solicitacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY solicitacao (id, data, dataencerramento, quantidade, vinculo, escolaridade, remuneracao, idademinima, idademaxima, sexo, infocomplementares, encerrada, liberada, suspensa, obssuspensao, motivosolicitacao_id, areaorganizacional_id, estabelecimento_id, solicitante_id, cidade_id, empresa_id, faixasalarial_id, descricao, avaliacao_id, liberador_id) FROM stdin;
\.


--
-- Data for Name: anuncio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY anuncio (id, titulo, cabecalho, informacoes, mostraconhecimento, mostrabeneficio, mostrasalario, mostracargo, mostrasexo, mostraidade, exibirmoduloexterno, solicitacao_id) FROM stdin;
\.


--
-- Data for Name: avaliacaocurso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY avaliacaocurso (id, titulo, tipo, minimoaprovacao) FROM stdin;
\.


--
-- Data for Name: questionario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY questionario (id, titulo, cabecalho, datainicio, datafim, liberado, anonimo, aplicarporaspecto, tipo, empresa_id) FROM stdin;
\.


--
-- Data for Name: avaliacaoturma; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY avaliacaoturma (id, ativa, questionario_id) FROM stdin;
\.


--
-- Data for Name: camposextras; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY camposextras (id, texto1, texto2, texto3, data1, data2, data3, valor1, valor2, numero1, texto4, texto5, texto6, texto7, texto8, texto9, texto10) FROM stdin;
\.


--
-- Data for Name: candidato; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY candidato (id, nome, senha, name, contenttype, bytes, size, logradouro, numero, complemento, bairro, cep, ddd, fonefixo, fonecelular, email, cpf, pis, rg, naturalidade, pai, mae, conjuge, profissaopai, profissaomae, profissaoconjuge, conjugetrabalha, parentesamigos, qtdfilhos, sexo, datanascimento, escolaridade, estadocivil, pagapensao, quantidade, valor, possuiveiculo, numerohab, registro, emissao, vencimento, categoria, colocacao, pretencaosalarial, disponivel, blacklist, contratado, observacao, observacaoblacklist, cursos, dataatualizacao, origem, ocrtexto, uf_id, cidade_id, observacaorh, deficiencia, rgorgaoemissor, rguf_id, rgdataexpedicao, titeleitnumero, titeleitzona, titeleitsecao, certmilnumero, certmiltipo, certmilserie, ctpsnumero, ctpsserie, ctpsdv, ctpsuf_id, ctpsdataexpedicao, empresa_id, indicadopor, nomecontato, datacadastro, idf2rh) FROM stdin;
\.


--
-- Data for Name: motivodemissao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY motivodemissao (id, motivo, empresa_id) FROM stdin;
\.


--
-- Data for Name: colaborador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY colaborador (id, matricula, nome, nomecomercial, desligado, datadesligamento, observacao, dataadmissao, logradouro, numero, complemento, bairro, cep, cpf, pis, rg, naturalidade, pai, mae, conjuge, profissaopai, profissaomae, profissaoconjuge, conjugetrabalha, parentesamigos, qtdfilhos, sexo, datanascimento, escolaridade, estadocivil, ddd, fonefixo, fonecelular, email, vinculo, codigoac, cursos, regimerevezamento, naointegraac, empresa_id, uf_id, cidade_id, usuario_id, candidato_id, motivodemissao_id, deficiencia, rgorgaoemissor, rguf_id, rgdataexpedicao, numerohab, registro, emissao, vencimento, categoria, titeleitnumero, titeleitzona, titeleitsecao, certmilnumero, certmiltipo, certmilserie, ctpsnumero, ctpsserie, ctpsdv, ctpsuf_id, ctpsdataexpedicao, respondeuentrevista, indicadopor, name, contenttype, bytes, size, nomecontato, camposextras_id, solicitacao_id, dataatualizacao, observacaodemissao) FROM stdin;
\.


--
-- Data for Name: curso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY curso (id, nome, conteudoprogramatico, empresa_id, cargahoraria, percentualminimofrequencia, criterioavaliacao) FROM stdin;
\.


--
-- Data for Name: dnt; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY dnt (id, nome, data, empresa_id) FROM stdin;
\.


--
-- Data for Name: prioridadetreinamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY prioridadetreinamento (id, descricao, sigla, numero) FROM stdin;
\.


--
-- Data for Name: turma; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY turma (id, descricao, instrutor, custo, dataprevini, dataprevfim, empresa_id, instituicao, horario, realizada, qtdparticipantesprevistos, curso_id, avaliacaoturma_id) FROM stdin;
\.


--
-- Data for Name: colaboradorturma; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY colaboradorturma (id, origemdnt, aprovado, colaborador_id, prioridadetreinamento_id, turma_id, curso_id, dnt_id) FROM stdin;
\.


--
-- Data for Name: aproveitamentoavaliacaocurso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY aproveitamentoavaliacaocurso (id, colaboradorturma_id, avaliacaocurso_id, valor) FROM stdin;
\.


--
-- Data for Name: areaformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY areaformacao (id, nome) FROM stdin;
1	Administrativa
2	Administrativo Comercial
\.


--
-- Data for Name: areainteresse; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY areainteresse (id, nome, observacao, empresa_id) FROM stdin;
1	Banco de dados		1
\.


--
-- Data for Name: areainteresse_areaorganizacional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY areainteresse_areaorganizacional (areasinteresse_id, areasorganizacionais_id) FROM stdin;
1	2
1	2
\.


--
-- Data for Name: aspecto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY aspecto (id, nome, questionario_id, avaliacao_id) FROM stdin;
\.


--
-- Data for Name: atitude; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY atitude (id, nome, observacao, empresa_id) FROM stdin;
1	Pro ativo	tem que ser	1
\.


--
-- Data for Name: atitude_areaorganizacional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY atitude_areaorganizacional (atitudes_id, areaorganizacionals_id) FROM stdin;
1	2
1	2
\.


--
-- Data for Name: auditoria; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY auditoria (id, data, operacao, entidade, dados, usuario_id, chave, empresa_id) FROM stdin;
\.


--
-- Data for Name: avaliacaodesempenho; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY avaliacaodesempenho (id, titulo, inicio, fim, anonima, permiteautoavaliacao, liberada, avaliacao_id) FROM stdin;
\.


--
-- Data for Name: bairro; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY bairro (id, nome, cidade_id) FROM stdin;
\.


--
-- Data for Name: beneficio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY beneficio (id, nome, empresa_id) FROM stdin;
\.


--
-- Data for Name: candidato_areainteresse; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY candidato_areainteresse (candidato_id, areasinteresse_id) FROM stdin;
\.


--
-- Data for Name: candidato_cargo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY candidato_cargo (candidato_id, cargos_id) FROM stdin;
\.


--
-- Data for Name: conhecimento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY conhecimento (id, nome, observacao, empresa_id) FROM stdin;
1	Java	Tem que ter aprendido isso	1
\.


--
-- Data for Name: candidato_conhecimento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY candidato_conhecimento (candidato_id, conhecimentos_id) FROM stdin;
\.


--
-- Data for Name: candidatocurriculo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY candidatocurriculo (id, curriculo, candidato_id) FROM stdin;
\.


--
-- Data for Name: eleicao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY eleicao (id, posse, votacaoini, votacaofim, horariovotacaoini, horariovotacaofim, qtdvotonulo, qtdvotobranco, inscricaocandidatoini, inscricaocandidatofim, localinscricao, localvotacao, empresa_id, sindicato, apuracao, horarioapuracao, localapuracao, descricao, textoataeleicao, estabelecimento_id) FROM stdin;
\.


--
-- Data for Name: candidatoeleicao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY candidatoeleicao (id, qtdvoto, eleito, candidato_id, eleicao_id) FROM stdin;
\.


--
-- Data for Name: idioma; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY idioma (id, nome) FROM stdin;
1	Inglês
2	Espanhol
3	Francês
4	Alemão
5	Italiano
\.


--
-- Data for Name: candidatoidioma; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY candidatoidioma (id, nivel, candidato_id, idioma_id) FROM stdin;
\.


--
-- Data for Name: candidatosolicitacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY candidatosolicitacao (id, triagem, candidato_id, solicitacao_id, status) FROM stdin;
\.


--
-- Data for Name: cargo_areaformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cargo_areaformacao (cargo_id, areaformacaos_id) FROM stdin;
\.


--
-- Data for Name: cargo_areaorganizacional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cargo_areaorganizacional (cargo_id, areasorganizacionais_id) FROM stdin;
1	2
1	2
\.


--
-- Data for Name: cargo_atitude; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cargo_atitude (cargo_id, atitudes_id) FROM stdin;
\.


--
-- Data for Name: cargo_conhecimento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cargo_conhecimento (cargo_id, conhecimentos_id) FROM stdin;
\.


--
-- Data for Name: etapaseletiva; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY etapaseletiva (id, nome, ordem, empresa_id) FROM stdin;
1	Entrevista com RH	1	1
\.


--
-- Data for Name: cargo_etapaseletiva; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cargo_etapaseletiva (cargo_id, etapaseletivas_id) FROM stdin;
\.


--
-- Data for Name: habilidade; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY habilidade (id, nome, observacao, empresa_id) FROM stdin;
1	Programar	Tem que saber programar	1
\.


--
-- Data for Name: cargo_habilidade; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cargo_habilidade (cargo_id, habilidades_id) FROM stdin;
\.


--
-- Data for Name: cat; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cat (id, data, numerocat, observacao, gerouafastamento, colaborador_id) FROM stdin;
\.


--
-- Data for Name: certificacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY certificacao (id, nome, empresa_id) FROM stdin;
\.


--
-- Data for Name: certificacao_curso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY certificacao_curso (certificacaos_id, cursos_id) FROM stdin;
\.


--
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cliente (id, nome, enderecointerno, enderecoexterno, senhafortes, versao, dataatualizacao, modulosadquiridos, contatogeral, contatoti, observacao) FROM stdin;
\.


--
-- Data for Name: clinicaautorizada; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY clinicaautorizada (id, nome, crm, cnpj, tipo, data, datainativa, empresa_id, endereco, telefone, horarioatendimento) FROM stdin;
\.


--
-- Data for Name: exame; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY exame (id, nome, periodicidade, periodico, empresa_id) FROM stdin;
1	ASO	0	f	1
\.


--
-- Data for Name: clinicaautorizada_exame; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY clinicaautorizada_exame (clinicaautorizada_id, exames_id) FROM stdin;
\.


--
-- Data for Name: codigocbo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY codigocbo (codigo, descricao) FROM stdin;
519805	Profissional do sexo
317110	Programador de sistemas de informaçao
\.


--
-- Data for Name: colaboradorafastamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY colaboradorafastamento (id, inicio, fim, mediconome, medicocrm, observacao, cid, afastamento_id, colaborador_id) FROM stdin;
\.


--
-- Data for Name: colaboradoridioma; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY colaboradoridioma (id, nivel, colaborador_id, idioma_id) FROM stdin;
\.


--
-- Data for Name: ocorrencia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY ocorrencia (id, descricao, pontuacao, codigoac, integraac, empresa_id) FROM stdin;
\.


--
-- Data for Name: colaboradorocorrencia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY colaboradorocorrencia (id, dataini, datafim, observacao, colaborador_id, ocorrencia_id) FROM stdin;
\.


--
-- Data for Name: diaturma; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY diaturma (id, dia, turma_id) FROM stdin;
\.


--
-- Data for Name: colaboradorpresenca; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY colaboradorpresenca (id, presenca, colaboradorturma_id, diaturma_id) FROM stdin;
\.


--
-- Data for Name: colaboradorquestionario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY colaboradorquestionario (id, colaborador_id, questionario_id, respondida, respondidaem, turma_id, candidato_id, avaliacao_id, performance, observacao, avaliacaodesempenho_id, avaliador_id, solicitacao_id) FROM stdin;
\.


--
-- Data for Name: pergunta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pergunta (id, ordem, texto, comentario, textocomentario, tipo, aspecto_id, questionario_id, notaminima, notamaxima, peso, avaliacao_id) FROM stdin;
\.


--
-- Data for Name: resposta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY resposta (id, texto, ordem, pergunta_id, peso) FROM stdin;
\.


--
-- Data for Name: colaboradorresposta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY colaboradorresposta (id, comentario, valor, pergunta_id, resposta_id, colaboradorquestionario_id, areaorganizacional_id, estabelecimento_id) FROM stdin;
\.


--
-- Data for Name: comissao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY comissao (id, dataini, datafim, atapossetexto1, atapossetexto2, eleicao_id) FROM stdin;
\.


--
-- Data for Name: comissaoeleicao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY comissaoeleicao (id, colaborador_id, eleicao_id, funcao) FROM stdin;
\.


--
-- Data for Name: comissaoperiodo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY comissaoperiodo (id, apartirde, comissao_id) FROM stdin;
\.


--
-- Data for Name: comissaomembro; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY comissaomembro (id, funcao, tipo, colaborador_id, comissaoperiodo_id) FROM stdin;
\.


--
-- Data for Name: comissaoplanotrabalho; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY comissaoplanotrabalho (id, prazo, descricao, situacao, prioridade, parecer, detalhes, responsavel_id, comissao_id) FROM stdin;
\.


--
-- Data for Name: comissaoreuniao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY comissaoreuniao (id, data, descricao, localizacao, horario, tipo, ata, comissao_id, obsreuniaoanterior) FROM stdin;
\.


--
-- Data for Name: comissaoreuniaopresenca; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY comissaoreuniaopresenca (id, comissaoreuniao_id, colaborador_id, presente, justificativafalta) FROM stdin;
\.


--
-- Data for Name: configuracaocampoextra; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY configuracaocampoextra (id, ativo, nome, descricao, titulo, ordem, tipo, posicao) FROM stdin;
1	f	texto1	Campo de Texto 1	\N	1	texto	1
2	f	texto2	Campo de Texto 2	\N	1	texto	2
3	f	texto3	Campo de Texto 3	\N	1	texto	3
4	f	data1	Campo de Data 1	\N	1	data	11
5	f	data2	Campo de Data 2	\N	1	data	12
6	f	data3	Campo de Data 3	\N	1	data	13
7	f	valor1	Campo de Valor 1	\N	1	valor	14
8	f	valor2	Campo de Valor 2	\N	1	valor	15
9	f	numero1	Campo de Numero	\N	1	numero	16
10	f	texto4	Campo de Texto 4	\N	1	texto	4
11	f	texto5	Campo de Texto 5	\N	1	texto	5
12	f	texto6	Campo de Texto 6	\N	1	texto	6
13	f	texto7	Campo de Texto 7	\N	1	texto	7
14	f	texto8	Campo de Texto 8	\N	1	texto	8
15	f	texto9	Campo de Texto 9	\N	1	texto	9
16	f	texto10	Campo de Texto 10	\N	1	texto	10
\.


--
-- Data for Name: configuracaoimpressaocurriculo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY configuracaoimpressaocurriculo (id, exibirconhecimento, exibircurso, exibirexperiencia, exibirinformacao, exibirobservacao, exibirhistorico, exibiridioma, exibirformacao, exibirinformacaosocioeconomica, exibirassinatura1, assinatura1, exibirassinatura2, assinatura2, exibirassinatura3, assinatura3, usuario_id, empresa_id) FROM stdin;
\.


--
-- Data for Name: configuracaoperformance; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY configuracaoperformance (id, usuario_id, caixa, ordem, aberta) FROM stdin;
\.


--
-- Data for Name: configuracaorelatoriodinamico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY configuracaorelatoriodinamico (id, usuario_id, campos, titulo) FROM stdin;
\.


--
-- Data for Name: conhecimento_areaorganizacional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY conhecimento_areaorganizacional (conhecimentos_id, areaorganizacionals_id) FROM stdin;
1	2
1	2
\.


--
-- Data for Name: curso_avaliacaocurso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY curso_avaliacaocurso (cursos_id, avaliacaocursos_id) FROM stdin;
\.


--
-- Data for Name: dependente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY dependente (id, nome, datanascimento, seqac, colaborador_id) FROM stdin;
\.


--
-- Data for Name: documentoanexo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY documentoanexo (id, descricao, data, observacao, url, origem, origemid, etapaseletiva_id) FROM stdin;
\.


--
-- Data for Name: empresabds; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY empresabds (id, nome, contato, fone, email, ddd, empresa_id) FROM stdin;
\.


--
-- Data for Name: engenheiroresponsavel; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY engenheiroresponsavel (id, nome, inicio, crea, fim, nit, empresa_id) FROM stdin;
\.


--
-- Data for Name: entrevista; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY entrevista (id, ativa, questionario_id) FROM stdin;
\.


--
-- Data for Name: epc; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY epc (id, codigo, descricao, empresa_id) FROM stdin;
\.


--
-- Data for Name: tipoepi; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tipoepi (id, nome, empresa_id) FROM stdin;
\.


--
-- Data for Name: epi; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY epi (id, nome, fabricante, empresa_id, tipoepi_id, fardamento) FROM stdin;
\.


--
-- Data for Name: epihistorico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY epihistorico (id, atenuacao, vencimentoca, validadeuso, ca, data, epi_id) FROM stdin;
\.


--
-- Data for Name: etapaprocessoeleitoral; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY etapaprocessoeleitoral (id, nome, prazolegal, prazo, data, eleicao_id, empresa_id) FROM stdin;
\.


--
-- Data for Name: medicocoordenador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY medicocoordenador (id, nome, inicio, crm, empresa_id, registro, especialidade, name, contenttype, bytes, size, fim, nit) FROM stdin;
\.


--
-- Data for Name: realizacaoexame; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY realizacaoexame (id, data, observacao, resultado) FROM stdin;
\.


--
-- Data for Name: solicitacaoexame; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY solicitacaoexame (id, data, motivo, observacao, candidato_id, colaborador_id, medicocoordenador_id, empresa_id) FROM stdin;
\.


--
-- Data for Name: examesolicitacaoexame; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY examesolicitacaoexame (id, periodicidade, exame_id, solicitacaoexame_id, clinicaautorizada_id, realizacaoexame_id) FROM stdin;
\.


--
-- Data for Name: experiencia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY experiencia (id, empresa, dataadmissao, datadesligamento, observacao, nomemercado, salario, motivosaida, candidato_id, colaborador_id, cargo_id) FROM stdin;
\.


--
-- Data for Name: extintor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY extintor (id, localizacao, tipo, numerocilindro, capacidade, fabricante, periodomaxrecarga, periodomaxinspecao, periodomaxhidrostatico, ativo, empresa_id, estabelecimento_id) FROM stdin;
\.


--
-- Data for Name: extintorinspecao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY extintorinspecao (id, data, empresaresponsavel, observacao, extintor_id) FROM stdin;
\.


--
-- Data for Name: extintorinspecaoitem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY extintorinspecaoitem (id, descricao) FROM stdin;
1	Lacre
2	Selo
3	Trava
4	Manômetro
5	Sinalização Vertical
6	Sinalização Horizontal
7	Localização
8	Alça
9	Gatilho
10	Mangueira
\.


--
-- Data for Name: extintorinspecao_extintorinspecaoitem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY extintorinspecao_extintorinspecaoitem (extintorinspecao_id, itens_id) FROM stdin;
\.


--
-- Data for Name: extintormanutencao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY extintormanutencao (id, saida, retorno, motivo, outromotivo, observacao, extintor_id) FROM stdin;
\.


--
-- Data for Name: extintormanutencaoservico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY extintormanutencaoservico (id, descricao) FROM stdin;
1	Recarga
2	Pintura
3	Teste Hidrostático
4	Manômetro
5	Substituição de Gatilho
6	Válvula de Segurança
7	Substituição de Difusor
8	Válvula Completa
9	Mangote
10	Válvula Cilindro Adicional
\.


--
-- Data for Name: extintormanutencao_extintormanutencaoservico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY extintormanutencao_extintormanutencaoservico (extintormanutencao_id, servicos_id) FROM stdin;
\.


--
-- Data for Name: faixasalarial_certificacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY faixasalarial_certificacao (faixasalarials_id, certificacaos_id) FROM stdin;
\.


--
-- Data for Name: indice; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY indice (id, nome, codigoac, grupoac) FROM stdin;
1	Minimo		\N
\.


--
-- Data for Name: faixasalarialhistorico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY faixasalarialhistorico (id, data, valor, tipo, quantidade, faixasalarial_id, indice_id, status) FROM stdin;
1	2011-03-01	3322	3	0	1	\N	1
\.


--
-- Data for Name: fichamedica; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY fichamedica (id, ativa, rodape, questionario_id) FROM stdin;
\.


--
-- Data for Name: formacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY formacao (id, situacao, tipo, curso, local, conclusao, candidato_id, colaborador_id, areaformacao_id) FROM stdin;
\.


--
-- Data for Name: funcao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY funcao (id, nome, cargo_id) FROM stdin;
\.


--
-- Data for Name: grupogasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY grupogasto (id, nome, empresa_id) FROM stdin;
\.


--
-- Data for Name: gasto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY gasto (id, nome, naoimportar, codigoac, grupogasto_id, empresa_id) FROM stdin;
\.


--
-- Data for Name: gastoempresa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY gastoempresa (id, mesano, colaborador_id, empresa_id) FROM stdin;
\.


--
-- Data for Name: gastoempresaitem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY gastoempresaitem (id, valor, gasto_id, gastoempresa_id) FROM stdin;
\.


--
-- Data for Name: habilidade_areaorganizacional; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY habilidade_areaorganizacional (habilidades_id, areaorganizacionals_id) FROM stdin;
1	2
1	2
\.


--
-- Data for Name: historicoambiente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicoambiente (id, descricao, data, datainativo, tempoexposicao, ambiente_id) FROM stdin;
\.


--
-- Data for Name: historicoambiente_epc; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicoambiente_epc (historicoambiente_id, epcs_id) FROM stdin;
\.


--
-- Data for Name: historicobeneficio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicobeneficio (id, data, valor, paracolaborador, paradependentedireto, paradependenteindireto, beneficio_id) FROM stdin;
\.


--
-- Data for Name: historicocandidato; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicocandidato (id, data, responsavel, observacao, etapaseletiva_id, candidatosolicitacao_id, horaini, horafim, apto) FROM stdin;
\.


--
-- Data for Name: tabelareajustecolaborador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tabelareajustecolaborador (id, nome, data, observacao, aprovada, empresa_id) FROM stdin;
\.


--
-- Data for Name: reajustecolaborador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY reajustecolaborador (id, salarioatual, salarioproposto, colaborador_id, tabelareajustecolaborador_id, areaorganizacionalatual_id, areaorganizacionalproposta_id, funcaoatual_id, funcaoproposta_id, ambienteatual_id, ambienteproposto_id, estabelecimentoatual_id, estabelecimentoproposto_id, tiposalarioproposto, indiceproposto_id, quantidadeindiceproposto, tiposalarioatual, indiceatual_id, quantidadeindiceatual, observacao, faixasalarialatual_id, faixasalarialproposta_id) FROM stdin;
\.


--
-- Data for Name: historicocolaborador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicocolaborador (id, salario, data, motivo, gfip, colaborador_id, areaorganizacional_id, historicoanterior_id, funcao_id, ambiente_id, estabelecimento_id, tiposalario, indice_id, quantidadeindice, faixasalarial_id, reajustecolaborador_id, status, movimentosalarialid) FROM stdin;
\.


--
-- Data for Name: historicocolaboradorbeneficio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicocolaboradorbeneficio (id, data, dataate, colaborador_id) FROM stdin;
\.


--
-- Data for Name: historicocolaboradorbeneficio_beneficio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicocolaboradorbeneficio_beneficio (historicocolaboradorbeneficio_id, beneficios_id) FROM stdin;
\.


--
-- Data for Name: historicofuncao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicofuncao (id, data, descricao, funcao_id) FROM stdin;
\.


--
-- Data for Name: historicofuncao_epi; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicofuncao_epi (historicofuncao_id, epis_id) FROM stdin;
\.


--
-- Data for Name: historicofuncao_exame; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY historicofuncao_exame (historicofuncao_id, exames_id) FROM stdin;
\.


--
-- Data for Name: indicehistorico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY indicehistorico (id, data, valor, indice_id) FROM stdin;
1	2011-02-01	500	1
\.


--
-- Data for Name: medicaorisco; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY medicaorisco (id, data, ambiente_id) FROM stdin;
\.


--
-- Data for Name: mensagem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY mensagem (id, remetente, link, data, texto) FROM stdin;
\.


--
-- Data for Name: papel; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY papel (id, codigo, nome, url, ordem, menu, accesskey, papelmae_id) FROM stdin;
495	ROLE_VISUALIZAR_MSG	Visualizar Caixa de Mensagens	#	1	f	\N	\N
411	ROLE_VISUALIZAR_PENDENCIA_AC	Visualizar as pendências do AC		2	f	\N	\N
382	ROLE_AVALDESEMPENHO	Aval. Desempenho	#	6	t	v	\N
365	ROLE_T&D	T&D	#	7	t	T	\N
373	ROLE_COLAB	Info. Funcionais	#	8	t	I	\N
75	ROLE_SESMT	SESMT	#	10	t	S	\N
37	ROLE_UTI	Utilitários	#	11	t	U	\N
361	ROLE_C&S	C&S	#	4	t	C	\N
353	ROLE_PES	Pesquisas	#	5	t	P	\N
357	ROLE_R&S	R&S	#	3	t	R	\N
463	INATIVOS	Inativos	#	99	t	\N	37
496	ROLE_VISUALIZAR_SOLICITACAO_PESSOAL	Visualizar Solicitação de Pessoal	#	14	f	\N	357
461	ROLE_R&S_IND	Indicadores	#	4	t	\N	357
358	ROLE_R&S_CAD	Cadastros	#	1	t	\N	357
2	ROLE_CAD_CANDIDATO	Candidatos	/captacao/candidato/list.action	1	t	\N	358
3	ROLE_CAD_ETAPA	Etapas Seletivas	/captacao/etapaSeletiva/list.action	2	t	\N	358
6	ROLE_CAD_BDS_EMPRESA	Empresas BDS	/captacao/empresaBds/list.action	3	t	\N	463
4	ROLE_CAD_AREA	Áreas de Interesse	/geral/areaInteresse/list.action	4	t	\N	358
397	ROLE_AREAFORMACAO	Áreas de Formação	/geral/areaFormacao/list.action	5	t	\N	358
57	ROLE_MOTIVO_SOLICITACAO	Motivos de Solicitação de Pessoal	/captacao/motivoSolicitacao/list.action	6	t	\N	358
492	ROLE_MOV_SOLICITACAO	Modelos de Avaliação do Candidato	/avaliacao/modeloCandidato/list.action?modeloAvaliacao=S	7	t	\N	358
359	ROLE_R&S_MOV	Movimentações	#	2	t	\N	357
21	ROLE_MOV_SOLICITACAO	Solicitação de Pessoal	/captacao/solicitacao/list.action	1	t	\N	359
500	ROLE_SOLICITACAO_AGENDA	Agenda	/captacao/solicitacao/agenda.action	2	t	\N	359
50	ROLE_BD_SOLIDARIO	Banco de Dados Solidário	/captacao/candidato/prepareBusca.action?BDS=true	2	t	\N	463
360	ROLE_R&S_REL	Relatórios	#	3	t	\N	357
46	ROLE_REL_SOLICITACAO	Solicitações Abertas	/captacao/solicitacao/prepareRelatorio.action	1	t	\N	360
48	ROLE_REL_PROCESSO_SELETIVO	Análise das Etapas Seletivas	/captacao/solicitacao/prepareRelatorioProcessoSeletivo.action	2	t	\N	360
424	ROLE_REL_AVALIACAO_CANDIDATOS	Avaliações de Candidatos	/captacao/candidato/prepareRelatorioAvaliacaoCandidatos.action	3	t	\N	360
56	ROLE_LIBERA_SOLICITACAO	Liberador de Solicitação	#	4	f	\N	357
45	ROLE_MOV_SOLICITACAO_SELECAO	Recrutador(a)	#	4	f	\N	357
22	ROLE_MOV_SOLICITACAO_CANDIDATO	Ver Candidatos da Solicitação	#	4	f	\N	357
362	ROLE_C&S_CAD	Cadastros	#	1	t	\N	361
9	ROLE_CAD_AREA	Áreas Organizacionais	/geral/areaOrganizacional/list.action	1	t	\N	362
10	ROLE_CAD_GRUPO	Grupos Ocupacionais	/cargosalario/grupoOcupacional/list.action	2	t	\N	362
5	ROLE_CAD_CONHECIMENTO	Conhecimentos	/captacao/conhecimento/list.action	3	t	\N	362
493	ROLE_CAD_HABILIDADE	Habilidades	/captacao/habilidade/list.action	4	t	\N	362
494	ROLE_CAD_ATITUDE	Atitudes	/captacao/atitude/list.action	5	t	\N	362
11	ROLE_CAD_CARGO	Cargos e Faixas	/cargosalario/cargo/list.action	6	t	\N	362
499	ROLE_CAD_FAIXA_SALARIAL	Exibir Faixa Salarial de Cargos		7	f	\N	362
404	ROLE_CAD_INDICE	Índices	/cargosalario/indice/list.action	8	t	\N	362
363	ROLE_C&S_MOV	Movimentações	#	2	t	\N	361
26	ROLE_MOV_SIMULACAOREAJUSTE	Planejamentos de Realinhamentos	/cargosalario/tabelaReajusteColaborador/list.action	1	t	\N	363
49	ROLE_MOV_SOLICITACAOREAJUSTE	Solicitação de Realinhamento de C&S	/cargosalario/reajusteColaborador/prepareSolicitacaoReajuste.action	2	t	\N	363
395	ROLE_DISSIDIO	Reajuste Coletivo	/cargosalario/reajusteColaborador/prepareDissidio.action	3	t	\N	363
478	ROLE_VER_AREAS	Visualizar todas as Áreas Organizacionais	#	0	f	\N	363
364	ROLE_C&S_REL	Relatórios	#	3	t	\N	361
54	ROLE_REL_CARGO	Descrição de Cargos	/cargosalario/cargo/prepareRelatorioCargo.action	1	t	\N	364
471	ROLE_REL_COLAB_CARGO	Colaboradores por Cargo	/cargosalario/cargo/prepareRelatorioColaboradorCargo.action	2	t	\N	364
35	ROLE_REL_SIMULACAOREAJUSTE	Realinhamentos	/cargosalario/reajusteRelatorio/formFiltro.action	3	t	\N	364
396	ROLE_REL_AREAORGANIZACIONAL	Colaboradores por Área Organizacional	/geral/areaOrganizacionalRelatorio/formFiltro.action	4	t	\N	364
406	ROLE_MOV_TABELA	Análise de Tabela Salarial	/cargosalario/faixaSalarialHistorico/analiseTabelaSalarialFiltro.action	5	t	\N	364
407	ROLE_REL_PROJECAO_SALARIAL	Projeção Salarial	/geral/colaborador/prepareProjecaoSalarialFiltro.action	6	t	\N	364
473	ROLE_REL_SITUACOES	Situações	/cargosalario/historicoColaborador/prepareRelatorioSituacoes.action	7	t	\N	364
417	ROLE_TRANSFERIR_FAIXAS_AC	Transferir Faixas entre Cargos		4	f	\N	361
355	ROLE_PES_MOV	Movimentações	#	1	t	\N	353
28	ROLE_MOV_QUESTIONARIO	Pesquisas	/pesquisa/pesquisa/list.action	1	t	\N	355
401	ROLE_RESPONDER_PESQUISA_POR_OUTRO_USUARIO	Pode Responder Pesquisa Por Outro Usuário	#	4	f	\N	353
59	ROLE_PESQUISA	Pode ver e responder Pesquisa	#	4	f	\N	353
486	ROLE_REL_AVALIACAO	Relatórios	#	3	t	\N	382
481	ROLE_CAD_AVALIACAO	Cadastros	#	1	t	\N	382
482	ROLE_CAD_AVALIACAO	Avaliações de Desempenho/Acomp. do Período de Experiência	/avaliacao/modelo/list.action	1	t	\N	481
467	ROLE_CAD_PERIODOEXPERIENCIA	Dias do Acompanhamento do Período de Experiência	/avaliacao/periodoExperiencia/list.action	2	t	\N	481
384	ROLE_MOV_AVALIACAO	Movimentações	#	2	t	\N	382
484	ROLE_RESPONDE_AVALIACAO	Pode ver e responder Aval. Desempenho	#	3	f	\N	382
55	ROLE_MOV_AVALIACAO	Avaliações de Desempenho	/avaliacao/desempenho/list.action	1	t	\N	384
483	ROLE_MOV_AVALIACAO	Responder Avaliações de Desempenho	/avaliacao/desempenho/avaliacaoDesempenhoQuestionarioList.action	2	t	\N	384
487	ROLE_RESPONDER_AVALIACAO_POR_OUTRO_USUARIO	Pode Responder Avaliação Por Outro Usuário	#	2	f	\N	\N
366	ROLE_T&D_CAD	Cadastros	#	1	t	\N	365
13	ROLE_CAD_PRIORIDADETREINAMENTO	Prioridades de Treinamento	/desenvolvimento/prioridadeTreinamento/list.action	1	t	\N	463
23	ROLE_MOV_CURSO	Cursos/Treinamentos	/desenvolvimento/curso/list.action	2	t	\N	366
413	ROLE_MOV_AVALIACAO_CURSO	Avaliações dos Cursos	/desenvolvimento/avaliacaoCurso/list.action	3	t	\N	366
419	ROLE_AVALIACAO_TURMA	Modelos de Avaliação de Turma	/pesquisa/avaliacaoTurma/list.action	4	t	\N	366
420	ROLE_CAD_CERTIFICACAO	Certificações	/desenvolvimento/certificacao/list.action	5	t	\N	366
367	ROLE_T&D_MOV	Movimentações	#	2	t	\N	365
64	ROLE_MOV_CURSO_DNT	DNT	/desenvolvimento/dnt/list.action	1	t	\N	463
72	ROLE_MOV_CURSO_DNT_GESTOR	Preenchimento da DNT	/desenvolvimento/dnt/list.action?gestor=true	2	t	\N	463
71	ROLE_FREQUENCIA	Frequência	/desenvolvimento/turma/prepareFrequencia.action	3	t	\N	367
414	ROLE_MOV_PLANO_TREINAMENTO	Plano de Treinamento	/desenvolvimento/turma/filtroPlanoTreinamento.action	4	t	\N	367
368	ROLE_T&D_REL	Relatórios	#	3	t	\N	365
31	ROLE_REL_MATRIZ	Matriz de Qualificação	/desenvolvimento/turma/prepareImprimirMatriz.action	1	t	\N	463
32	ROLE_REL_PLANO	Plano de Treinamento	/desenvolvimento/turma/prepareImprimirTurma.action	2	t	\N	463
33	ROLE_REL_SEM_INDICACAO	Colaboradores sem Indicação de Trein.	/desenvolvimento/colaboradorTurma/prepareRelatorioColaboradorSemIndicacao.action	3	t	\N	368
43	ROLE_REL_DESENVOLVIMENTO_LISTA_PRESENCA	Lista de Frequência	/desenvolvimento/relatorioPresenca/prepareRelatorio.action	4	t	\N	368
61	ROLE_CERTIFICADO_CURSO	Certificados	/desenvolvimento/turma/prepareImprimirCertificado.action	5	t	\N	368
418	ROLE_CRONOGRAMA_TREINAMENTO	Cronograma de Treinamento	/desenvolvimento/turma/filtroCronogramaTreinamento.action	6	t	\N	368
415	ROLE_REL_COLABORADOR_SEM_TREINAMENTO	Colaboradores sem Treinamentos	/desenvolvimento/colaboradorTurma/prepareRelatorioColaborador.action	7	t	\N	368
416	ROLE_REL_COLABORADOR_COM_TREINAMENTO	Colaboradores com Treinamentos	/desenvolvimento/colaboradorTurma/prepareRelatorioColaborador.action?comTreinamento=true	8	t	\N	368
421	ROLE_REL_MATRIZ_TREINAMENTO	Matriz de Treinamentos	/desenvolvimento/certificacao/matrizTreinamento.action	9	t	\N	368
422	ROLE_REL_HISTORICO_TREINAMENTOS	Histórico de Treinamentos	/desenvolvimento/colaboradorTurma/prepareFiltroHistoricoTreinamentos.action	10	t	\N	368
465	ROLE_REL_COLABORADORES_CERTIFICACOES	Colaboradores x Certificações	/desenvolvimento/colaboradorTurma/prepareRelatorioColaboradorCertificacao.action	11	t	\N	368
498	ROLE_CRONOGRAMA_TREINAMENTO	Relatorio de investimento de T&D	/desenvolvimento/turma/relatorioInvestimento.action	12	t	\N	368
454	ROLE_T&D_REL	Painel de Indicadores	/desenvolvimento/indicadores/list.action	4	t	\N	365
63	ROLE_MOV_TURMA	Pode Cadastrar Turma	/desenvolvimento/turma/list.action	4	f	\N	365
374	ROLE_COLAB_CAD	Cadastros	#	1	t	\N	373
15	ROLE_CAD_GRUPOGASTO	Grupos de Investimento	/geral/grupoGasto/list.action	1	t	\N	463
16	ROLE_CAD_GASTO	Investimentos	/geral/gasto/list.action	2	t	\N	463
12	ROLE_CAD_BENEFICIO	Benefícios	/geral/beneficio/list.action	3	t	\N	463
60	ROLE_CAD_MOTIVO_DEMISSAO	Motivos de Desligamento	/geral/motivoDemissao/list.action	4	t	\N	374
402	ROLE_MOV_QUESTIONARIO	Modelos de Entrevistas de Desligamento	/pesquisa/entrevista/list.action	5	t	\N	374
62	ROLE_CAD_OCORRENCIA	Tipos de Ocorrência	/geral/ocorrencia/list.action	6	t	\N	374
8	ROLE_CAD_COLABORADOR	Colaboradores	/geral/colaborador/list.action	7	t	\N	374
412	ROLE_CAD_INFO_PESSOAL	Atualizar meus dados	/geral/colaborador/prepareUpdateInfoPessoais.action	8	t	\N	374
469	ROLE_COLAB_MOV	Movimentações	#	2	t	\N	373
470	ROLE_MOV_PERIODOEXPERIENCIA	Acompanhamento do Período de Experiência	/avaliacao/avaliacaoExperiencia/periodoExperienciaQuestionarioList.action	4	t	\N	384
480	ROLE_CAD_OCORRENCIA	Ocorrências	/geral/colaboradorOcorrencia/list.action	2	t	\N	469
29	ROLE_MOV_GASTO_GASTOEMPRESA	Investimentos da Empresa	/geral/gastoEmpresa/list.action	11	t	\N	463
377	ROLE_COLAB_REL	Relatórios	#	3	t	\N	373
93	ROLE_REL_OCORRENCIA	Ocorrências	/geral/ocorrencia/prepareRelatorioOcorrencia.action	1	t	\N	377
400	ROLE_REL_MOTIVO_DEMISSAO	Desligamentos	/geral/motivoDemissao/prepareRelatorioMotivoDemissao.action	2	t	\N	377
403	ROLE_MOV_QUESTIONARIO	Resultados das Entrevistas	/pesquisa/questionario/prepareResultadoEntrevista.action	3	t	\N	377
423	ROLE_REL_ANIVERSARIANTES	Aniversariantes do mês	/geral/colaborador/prepareRelatorioAniversariantes.action	4	t	\N	377
472	ROLE_REL_ADMITIDOS	Admitidos	/geral/colaborador/prepareRelatorioAdmitidos.action	5	t	\N	377
479	ROLE_CAD_PERIODOEXPERIENCIA	Resultado das Avaliações de Desempenho e Acompanhamento do Período de Experiência	/avaliacao/avaliacaoExperiencia/prepareResultado.action	1	t	\N	486
490	ROLE_REL_ACOMPANHAMENTO_EXPERIENCIA	Acompanhamento do Período de Experiência	/avaliacao/periodoExperiencia/prepareRelatorioAcopanhamentoExperiencia.action	2	t	\N	486
491	ROLE_REL_ACOMP_RANKING_PERIODO_EXPERIENCIA	Ranking de Performace das Avaliações de Desempenho	/avaliacao/periodoExperiencia/prepareRelatorioRankingPerformancePeriodoDeExperiencia.action	3	t	\N	486
73	ROLE_IND	Estatísticas de Vagas por Motivo	/indicador/duracaoPreenchimentoVaga/prepareMotivo.action	1	t	\N	461
69	ROLE_IND	Duração para Preenchimento de Vagas	/indicador/duracaoPreenchimentoVaga/prepare.action	2	t	\N	461
47	ROLE_REL_PRODUTIVIDADE	Análise das Etapas Seletivas	/captacao/produtividade/prepareProdutividade.action	3	t	\N	461
70	ROLE_REL_PROMOCAO	Promoções	/cargosalario/historicoColaborador/prepareRelatorioPromocoes.action	6	t	\N	463
398	ROLE_REL_TURNOVER	Turnover (rotatividade)	/indicador/indicadorTurnOver/prepare.action	7	t	\N	377
504	ROLE_INFO_PAINEL_IND	Painel de Indicadores	/cargosalario/historicoColaborador/painelIndicadores.action	3	t	\N	373
36	ROLE_REL_GASTOEMPRESA	Investimentos da Empresa	/geral/gastoEmpresa/prepareImprimir.action	6	t	\N	463
390	ROLE_UTI	Cadastros	#	1	t	\N	37
58	ROLE_UTI_EMPRESA	Empresas	/geral/empresa/list.action	1	t	\N	390
18	ROLE_CAD_PERFIL	Perfis	/acesso/perfil/list.action	2	t	\N	390
393	ROLE_CAD_ESTABELECIMENTO	Estabelecimentos	/geral/estabelecimento/list.action	3	t	\N	390
19	ROLE_CAD_USUARIO	Usuários	/acesso/usuario/list.action	4	t	\N	390
394	ROLE_CAD_BAIRRO	Bairros	/geral/bairro/list.action	5	t	\N	390
501	ROLE_CAD_GRUPOAC	Grupos AC	/geral/grupoAC/list.action	6	t	\N	390
464	ROLE_IMPORTA_CADASTROS	Importar Cadastros	/geral/empresa/prepareImportarCadastros.action	8	t	\N	37
38	ROLE_UTI_SENHA	Alterar Senha	/acesso/usuario/prepareUpdateSenhaUsuario.action	2	t	\N	37
41	ROLE_CONFIGURACAO	Configurações	#	3	t	\N	37
485	ROLE_CAMPO_EXTRA	Campos Extras	/geral/configuracaoCampoExtra/prepareUpdate.action	2	t	\N	41
502	ROLE_UTI_CONFIGURACAO	Sistema	/geral/parametrosDoSistema/prepareUpdate.action	1	t	\N	41
503	ROLE_CONFIG_CANDIDATO_EXT	Cadastro de Candidato (externo)	/geral/parametrosDoSistema/listCamposCandidato.action	3	t	\N	41
39	ROLE_UTI_AUDITORIA	Auditoria	/security/auditoria/prepareList.action	5	t	\N	37
44	ROLE_UTI_HISTORICO_VERSAO	Histórico de Versões	/geral/documentoVersao/list.action	6	t	\N	37
409	ROLE_UTI	Enviar Mensagem	/geral/usuarioMensagem/prepareUpdate.action	7	t	\N	37
466	ROLE_UTI_EMPRESA	Sobre...	/geral/empresa/sobre.action	8	t	\N	37
408	ROLE_MOV_SOLICITACAO_REALINHAMENTO	Pode Solicitar Realinhamento		4	f	\N	361
410	RECEBE_ALERTA_SETORPESSOAL	Recebe Mensagem do AC Pessoal		5	f	\N	37
451	ROLE_LOGGING	Logs	/logging/list.action	8	t	\N	37
497	ROLE_RECEBE_EXAMES_PREVISTOS	Recebe email de exames previstos	#	1	f	\N	75
385	ROLE_SESMT	Cadastros	#	2	t	\N	75
456	ROLE_CAD_PCMSO	PCMSO	#	9	t	\N	385
452	ROLE_CAD_EVENTO	Eventos	/sesmt/evento/list.action	1	t	\N	456
453	ROLE_CAD_AGENDA	Agenda	/sesmt/agenda/list.action	2	t	\N	456
76	ROLE_CAD_AMBIENTE	Ambientes	/sesmt/ambiente/list.action	5	t	\N	385
77	ROLE_CAD_TIPO_EPI	Categorias de EPI	/sesmt/tipoEPI/list.action	1	t	\N	385
78	ROLE_CAD_EPI	EPI	/sesmt/epi/list.action	2	t	\N	385
86	ROLE_CAD_EPC	EPC	/sesmt/epc/list.action	3	t	\N	385
79	ROLE_CAD_RISCO	Riscos	/sesmt/risco/list.action	4	t	\N	385
89	ROLE_CAD_ENGENHEIRO_TRABALHO	Engenheiros Responsáveis	/sesmt/engenheiroResponsavel/list.action	6	t	\N	385
90	ROLE_CAD_MEDICO_COORDENADOR	Médicos Coordenadores	/sesmt/medicoCoordenador/list.action	11	t	\N	385
91	ROLE_CAD_CLINICA_AUTORIZADA	Clínicas e Médicos Autorizados	/sesmt/clinicaAutorizada/list.action	12	t	\N	385
87	ROLE_CAD_EXAME	Exames	/sesmt/exame/list.action	10	t	\N	385
427	ROLE_CAD_FICHAMEDICA	Modelos de Fichas Médicas	/sesmt/fichaMedica/list.action	13	t	\N	385
455	ROLE_CAD_ELEICAO	CIPA	#	8	t	\N	385
437	ROLE_CAD_ETAPAPROCESSOELEITORAL	Etapas do Processo Eleitoral	/sesmt/etapaProcessoEleitoral/list.action	1	t	\N	455
438	ROLE_CAD_ELEICAO	Eleições	/sesmt/eleicao/list.action	2	t	\N	455
439	ROLE_CAD_ELEICAO	Comissões	/sesmt/comissao/list.action	3	t	\N	455
440	ROLE_CAD_AFASTAMENTO	Motivos de Afastamentos	/sesmt/afastamento/list.action	14	t	\N	385
445	ROLE_CAD_EXTINTOR	Extintores	/sesmt/extintor/list.action	7	t	\N	385
476	ROLE_FUNCAO	Funções	/sesmt/funcao/listFiltro.action	15	t	\N	385
386	ROLE_SESMT	Movimentações	#	3	t	\N	75
426	ROLE_CAD_SOLICITACAOEXAME	Solicitações/Atendimentos Médicos	/sesmt/solicitacaoExame/list.action	5	t	\N	386
66	ROLE_SESMT_MUDANCA_FUNCAO	Mudança de Função	/sesmt/funcao/mudancaFuncaoFiltro.action	5	t	\N	463
425	ROLE_CAD_PRONTUARIO	Registro de Prontuário	/sesmt/prontuario/list.action	6	t	\N	386
428	ROLE_CAD_FICHAMEDICA	Fichas Médicas	/sesmt/fichaMedica/listPreenchida.action	7	t	\N	386
433	ROLE_CAD_SOLICITACAOEPI	Solicitação de EPIs	/sesmt/solicitacaoEpi/list.action	2	t	\N	386
435	ROLE_CAD_ENTREGAEPI	Entrega de EPIs	/sesmt/solicitacaoEpi/list.action	3	t	\N	386
441	ROLE_CAD_AFASTAMENTO	Afastamentos	/sesmt/colaboradorAfastamento/list.action	8	t	\N	386
443	ROLE_CAT	CATs (Acidentes de Trabalho)	/sesmt/cat/list.action	9	t	\N	386
457	ROLE_CAD_EXTINTOR	Extintores	#	4	t	\N	386
446	ROLE_CAD_EXTINTOR	Inspeção	/sesmt/extintorInspecao/list.action	1	t	\N	457
447	ROLE_CAD_EXTINTOR	Manutenção	/sesmt/extintorManutencao/list.action	2	t	\N	457
387	ROLE_SESMT	Relatórios	#	4	t	\N	75
488	ROLE_CAT	CATs (Acidentes de Trabalho)	/sesmt/cat/prepareRelatorioCats.action	16	t	\N	387
388	ROLE_PPRA	PPRA e LTCAT	/sesmt/ppra/prepareRelatorio.action	1	t	\N	387
389	ROLE_CAD_PCMSO	PCMSO	/sesmt/pcmso/prepareRelatorio.action	7	t	\N	387
85	ROLE_PPP	PPP	/sesmt/ppp/list.action	2	t	\N	387
429	ROLE_REL_PRONTUARIO	Prontuário	/sesmt/prontuario/prepareRelatorioProntuario.action	8	t	\N	387
431	ROLE_REL_EXAMES_PREVISTOS	Exames Previstos	/sesmt/exame/prepareRelatorioExamesPrevistos.action	10	t	\N	387
432	ROLE_REL_FICHA_EPI	Ficha de EPI	/sesmt/epi/prepareImprimirFicha.action	3	t	\N	387
434	ROLE_CAD_EPICAVENCER	EPIs com CA a Vencer	/sesmt/epi/prepareImprimirVencimentoCa.action	4	t	\N	387
436	ROLE_REL_EPIVENCIMENTO	EPIs com Prazo a Vencer	/sesmt/solicitacaoEpi/prepareRelatorioVencimentoEpi.action	5	t	\N	387
442	ROLE_CAD_AFASTAMENTO	Afastamentos	/sesmt/colaboradorAfastamento/prepareRelatorioAfastamentos.action	11	t	\N	387
448	ROLE_CAD_EXTINTOR	Extintores - Manutenção e Inspeção	/sesmt/extintor/prepareRelatorio.action	6	t	\N	387
449	ROLE_CAD_FICHAMEDICA	Resultado de Fichas Médicas	/sesmt/fichaMedica/prepareResultadoFichaMedica.action	12	t	\N	387
450	ROLE_CAD_MEDICAORISCO	Medição dos Riscos nos Ambientes	/sesmt/medicaoRisco/list.action	1	t	\N	386
477	ROLE_AMBIENTE	Ambientes e Funções do Colaborador	/cargosalario/historicoColaborador/prepareUpdateAmbientesEFuncoes.action	5	t	\N	386
459	ROLE_CAD_SOLICITACAOEXAME	Atendimentos Médicos	/sesmt/solicitacaoExame/prepareRelatorioAtendimentosMedicos.action	14	t	\N	387
460	ROLE_CAD_SOLICITACAOEXAME	Exames Realizados	/sesmt/exame/prepareRelatorioExamesRealizados.action	15	t	\N	387
458	ROLE_FUNCAO	Distribuição de Colaboradores por Função	/sesmt/funcao/prepareRelatorioQtdPorFuncao.action	13	t	\N	387
489	ROLE_REL_EPIVENCIMENTO	EPIs Entregues	/sesmt/solicitacaoEpi/prepareRelatorioEntregaEpi.action	4	t	\N	387
74	ROLE_FUNCAO	Funções	/sesmt/funcao/list.action	5	f	\N	75
474	ROLE_COMPROU_SESMT	Exibir informações do SESMT	#	0	f	\N	\N
475	ROLE_CAD_CLIENTE	Clientes	/geral/cliente/list.action	12	f	\N	\N
\.


--
-- Data for Name: perfil; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY perfil (id, nome) FROM stdin;
1	Administrador
2	Usuário
\.


--
-- Data for Name: parametrosdosistema; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY parametrosdosistema (id, mailnaoaptos, appurl, appcontext, appversao, emailsmtp, emailport, emailuser, emailpass, atualizadorpath, servidorremprot, diaslembretepesquisa, enviaremail, atualizadosucesso, perfilpadrao_id, exame_id, acversaowebservicecompativel, uppercase, modulos, atualizapapeisidsapartirde, diaslembreteperiodoexperiencia, emaildosuportetecnico, campoextracolaborador, codempresasuporte, codclientesuporte, camposcandidatovisivel, camposcandidatoobrigatorio, camposcandidatotabs, emailcandidatonaoapto) FROM stdin;
1	\N	http://localhost:8080/fortesrh	/fortesrh	1.1.43.35	\N	25	\N	\N	\N		\N	f	\N	2	1	1.0.1.44	f	\N	\N	3	\N	f	\N	\N	nome,nascimento,naturalidade,sexo,cpf,escolaridade,endereco,email,fone,celular,nomeContato,parentes,estadoCivil,qtdFilhos,nomeConjuge,profConjuge,nomePai,profPai,nomeMae,profMae,pensao,possuiVeiculo,deficiencia,formacao,idioma,desCursos,funcaoPretendida,areasInteresse,conhecimentos,colocacao,expProfissional,infoAdicionais,identidade,cartairaHabilitacao,tituloEleitoral,certificadoMilitar,ctps	nome,cpf,escolaridade,ende,num,cidade,fone	abaDocumentos,abaExperiencias,abaPerfilProfissional,abaFormacaoEscolar,abaDadosPessoais	\N
\.


--
-- Data for Name: perfil_papel; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY perfil_papel (perfil_id, papeis_id) FROM stdin;
1	2
1	3
1	4
1	5
1	8
1	9
1	10
1	11
1	18
1	19
1	21
1	22
1	23
1	26
1	28
1	33
1	35
1	37
1	38
1	39
1	41
1	43
1	44
1	45
1	46
1	47
1	48
1	49
1	54
1	55
1	56
1	57
1	58
1	59
1	60
1	61
1	62
1	63
1	66
1	69
1	70
1	71
1	73
1	74
1	75
1	76
1	77
1	78
1	79
1	85
1	86
1	87
1	89
1	90
1	91
1	93
1	353
1	355
1	357
1	358
1	359
1	360
1	361
1	362
1	363
1	364
1	365
1	366
1	367
1	368
1	373
1	374
1	377
1	382
1	384
1	385
1	386
1	387
1	388
1	389
1	390
1	393
1	394
1	395
1	396
1	397
1	398
1	400
1	401
1	402
1	403
1	404
1	406
1	407
1	408
1	409
1	410
1	411
1	412
1	413
1	414
1	415
1	416
1	417
1	418
1	419
1	420
1	421
1	422
1	423
1	424
1	425
1	426
1	427
1	428
1	429
1	431
1	432
1	433
1	434
1	435
1	436
1	437
1	438
1	439
1	440
1	441
1	442
1	443
1	445
1	446
1	447
1	448
1	449
1	450
1	451
1	452
1	453
1	454
1	455
1	456
1	457
1	458
1	459
1	460
1	461
1	464
1	465
1	466
1	467
1	469
1	470
1	471
1	472
1	473
1	474
1	476
1	477
1	478
1	479
1	480
1	481
1	482
1	483
1	484
1	485
1	486
1	487
1	488
1	489
2	37
2	38
1	2
1	3
1	4
1	5
1	8
1	9
1	10
1	11
1	18
1	19
1	21
1	22
1	23
1	26
1	28
1	33
1	35
1	37
1	38
1	39
1	41
1	43
1	44
1	45
1	46
1	47
1	48
1	49
1	54
1	55
1	56
1	57
1	58
1	59
1	60
1	61
1	62
1	63
1	66
1	69
1	70
1	71
1	73
1	74
1	75
1	76
1	77
1	78
1	79
1	85
1	86
1	87
1	89
1	90
1	91
1	93
1	353
1	355
1	357
1	358
1	359
1	360
1	361
1	362
1	363
1	364
1	365
1	366
1	367
1	368
1	373
1	374
1	377
1	382
1	384
1	385
1	386
1	387
1	388
1	389
1	390
1	393
1	394
1	395
1	396
1	397
1	398
1	400
1	401
1	402
1	403
1	404
1	406
1	407
1	408
1	409
1	410
1	411
1	412
1	413
1	414
1	415
1	416
1	417
1	418
1	419
1	420
1	421
1	422
1	423
1	424
1	425
1	426
1	427
1	428
1	429
1	431
1	432
1	433
1	434
1	435
1	436
1	437
1	438
1	439
1	440
1	441
1	442
1	443
1	445
1	446
1	447
1	448
1	449
1	450
1	451
1	452
1	453
1	454
1	455
1	456
1	457
1	458
1	459
1	460
1	461
1	464
1	465
1	466
1	467
1	469
1	470
1	471
1	472
1	473
1	474
1	476
1	477
1	478
1	479
1	480
1	481
1	482
1	483
1	484
1	485
1	486
1	487
1	488
1	489
2	37
2	38
\.


--
-- Data for Name: pesquisa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY pesquisa (id, questionario_id) FROM stdin;
\.


--
-- Data for Name: prontuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY prontuario (id, descricao, data, colaborador_id, usuario_id) FROM stdin;
\.


--
-- Data for Name: risco; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY risco (id, descricao, gruporisco, empresa_id) FROM stdin;
\.


--
-- Data for Name: risco_epi; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY risco_epi (risco_id, epis_id) FROM stdin;
\.


--
-- Data for Name: riscoambiente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY riscoambiente (id, epceficaz, historicoambiente_id, risco_id) FROM stdin;
\.


--
-- Data for Name: riscomedicaorisco; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY riscomedicaorisco (id, descricaoppra, descricaoltcat, intensidademedida, tecnicautilizada, medicaorisco_id, risco_id) FROM stdin;
\.


--
-- Data for Name: solicitacao_bairro; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY solicitacao_bairro (solicitacao_id, bairros_id) FROM stdin;
\.


--
-- Data for Name: solicitacaobds; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY solicitacaobds (id, data, tipo, escolaridade, experiencia, sexo, idademinima, idademaxima, observacao, cargo_id, areaorganizacional_id) FROM stdin;
\.


--
-- Data for Name: solicitacaobds_empresabds; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY solicitacaobds_empresabds (solicitacaobds_id, empresasbdss_id) FROM stdin;
\.


--
-- Data for Name: solicitacaoepi; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY solicitacaoepi (id, data, entregue, colaborador_id, cargo_id, empresa_id) FROM stdin;
\.


--
-- Data for Name: solicitacaoepi_item; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY solicitacaoepi_item (id, epi_id, solicitacaoepi_id, qtdsolicitado, qtdentregue) FROM stdin;
\.


--
-- Data for Name: usuarioempresa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuarioempresa (id, usuario_id, perfil_id, empresa_id) FROM stdin;
\.


--
-- Data for Name: usuariomensagem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuariomensagem (id, usuario_id, mensagem_id, empresa_id, lida) FROM stdin;
\.


--
-- PostgreSQL database dump complete
--

