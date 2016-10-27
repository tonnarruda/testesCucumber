# language: pt

Funcionalidade: Cadastrar Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência

  Cenário: Cadastro de Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência
    Dado que exista um colaborador "Paula", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "3"
    Dado que exista um colaborador "Samuel", da area "Desenvolvimento1", com o cargo "Desenvolvedor1" e a faixa salarial "2"
    Dado que a opção apresentar performance de forma parcial ao responder avaliação de desempenho seja "true"
    Dado que exista um nivel de competencia "ruim"
    Dado que exista um nivel de competencia "regular"
    Dado que exista um nivel de competencia "bom"
    Dado que exista um historico de nivel de competencia na data "01/01/2010"
    Dado que exista uma configuracao de nivel de competencia com nivel "ruim" no historico do nivel de data "01/01/2010" na ordem 1
    Dado que exista uma configuracao de nivel de competencia com nivel "regular" no historico do nivel de data "01/01/2010" na ordem 2
    Dado que exista uma configuracao de nivel de competencia com nivel "bom" no historico do nivel de data "01/01/2010" na ordem 3
    Dado que exista um conhecimento "Java"
    Dado que exista um conhecimento "Java" na area organizacional "Desenvolvimento"
    Dado que eu esteja logado com o usuário "SOS"
    
    Quando eu acesso o menu "C&S > Cadastros > Cargos e Faixas"
    E eu clico em editar "Desenvolvedor"
    E eu marco "Desenvolvimento (Ativa)"
    E eu marco "Java"
    E eu clico no botão "Gravar"
    
    E eu clico na linha "Desenvolvedor" da imagem "Faixas Salariais"
    E eu clico na linha "3" da imagem "Níveis de Competência"
    E eu clico no botão "Inserir"
    E eu marco "Java"
    E eu escolho "niveisCompetenciaFaixaSalariaisConhecimento[0].nivelCompetencia.id"
    E eu clico no botão "Gravar"

    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Avaliações de Desempenho/Acomp. do Período de Experiência"
    Então eu devo ver o título "Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência"
    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Modelo de Avaliação"
    E eu preencho "Título" com "_avaliacao I"
    E eu preencho "Observação" com "_experiencia"
    E eu seleciono "Avaliação de Desempenho" de "Tipo de Avaliação"
    E eu marco "Avaliar também as competências exigidas pelo cargo"
    E eu clico no botão "Avancar"

    Então eu devo ver o título "Inserir Pergunta da Avaliação"
    E eu preencho "aspecto" com "_aspecto 1"
    E eu seleciono "1" de "Ordem"
    E eu preencho "pergunta" com "_pergunta 1"
    E eu preencho "peso" com "1"
    E eu seleciono "Subjetiva" de "Tipo de Resposta"
    E eu clico no botão "Gravar"
    E eu devo ver "Pergunta gravada com sucesso."

    Então eu devo ver o título "Inserir Pergunta da Avaliação"
    E eu preencho "aspecto" com "_aspecto 1"
    E eu seleciono "2" de "Ordem"
    E eu espero 1 segundo
    E eu preencho "pergunta" com "_pergunta 2"
    E eu preencho "peso" com "2"
    E eu seleciono "Nota" de "Tipo de Resposta"
    E eu preencho "notaMinima" com "5"
    E eu preencho "notaMaxima" com "10"
    E eu marco "Solicitar comentário"
    E eu preencho "textoComentario" com "_comentário"
    E eu clico no botão "Gravar"
    E eu devo ver "Pergunta gravada com sucesso."

    Então eu devo ver o título "Inserir Pergunta da Avaliação"
    E eu preencho "aspecto" com "_aspecto 2"
    E eu seleciono "3" de "Ordem"
    E eu espero 1 segundo
    E eu preencho "pergunta" com "_pergunta 3"
    E eu preencho "peso" com "3"
    E eu seleciono "Objetiva" de "Tipo de Resposta"
    E eu preencho "respostaObjetiva" com "P3 Item A"
    E eu preencho "pesoRespostaObjetiva_0" com "1"
    E eu clico "Mais uma opção de resposta"
    E eu preencho "ro_1" com "P3 Item b"
    E eu preencho "pesoRespostaObjetiva_1" com "1"
    E eu clico "Mais uma opção de resposta"
    E eu preencho "ro_2" com "P3 Item c"
    E eu preencho "pesoRespostaObjetiva_2" com "1"
    E eu clico no botão "Gravar"
    E eu devo ver "Pergunta gravada com sucesso."

    Então eu devo ver o título "Inserir Pergunta da Avaliação"
    E eu preencho "aspecto" com "_aspecto 2"
    E eu seleciono "4" de "Ordem"
    E eu espero 1 segundo
    E eu preencho "pergunta" com "_pergunta 4"
    E eu preencho "peso" com "4"
    E eu seleciono "Múltipla Escolha" de "Tipo de Resposta"
    E eu preencho "multiplaResposta" com "P4 Item A"
    E eu preencho "pesoRespostaMultipla" com "-5"
    E eu clico "adicionarMultiplaResposta"
    E eu preencho "multiplaResposta_1" com "P4 Item B"
    E eu preencho "pesoRespostaMultipla_1" com "5"
    E eu clico "adicionarMultiplaResposta"
    E eu preencho "multiplaResposta_2" com "Nao se aplica"
    E eu preencho "pesoRespostaMultipla_2" com ""
    E eu clico no botão "Gravar"
    E eu devo ver "Pergunta gravada com sucesso."

    E eu clico no botão "Voltar"
    E eu devo ver o título "Perguntas da Avaliação"
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência"
    E eu devo ver "_avaliacao I"

    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
    Então eu devo ver o título "Avaliações de Desempenho"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Avaliação de Desempenho"
    E eu preencho "Título" com "_avaliacao 1"
    E eu preencho o campo (JS) "inicio" com "01/06/2011"
    E eu preencho o campo (JS) "fim" com "30/06/2011"
    E eu seleciono "_avaliacao I" de "modelo"
    E eu seleciono "Não" de "anonima"
    E eu seleciono "Sim" de "permiteAutoAvaliacao"
    E eu clico no botão "Avancar"

    Então eu devo ver o título "Participantes - _avaliacao 1"
    E eu clico no botão de Id "inserir_Avaliador"
    E eu espero 1 segundos
    E eu devo ver "Inserir Avaliador"
    E eu clico no botão "Pesquisar"
    E eu marco "Samuel"
    E eu clico no botão "Gravar"
    E eu espero 1 segundos

    Entao eu clico no botão de Id "inserir_Avaliado"
    E eu clico no botão "Pesquisar"
    E eu marco "Paula"
    E eu clico no botão "Gravar"
    E eu espero 1 segundos
    E eu clico no botão de class "nome"
    E eu espero 1 segundos
    E eu clico no botão de Id "relacionar_selecionados"
    E eu espero 1 segundos
    E eu clico no botão de class "for-all"
    E eu espero 1 segundos

    Entao eu clico no botão de Id "btnGravar"
    E eu devo ver "Gravado com sucesso"
    E eu adiciono o avaliado no avaliador da avaliação de desempenho
    Entao eu clico no botão de Id "btnGravar"
    E eu devo ver "Gravado com sucesso"
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Avaliações de Desempenho"
    E eu clico em editar "_avaliacao 1"
    E eu clico no botão "Voltar"

    Então eu clico na linha "_avaliacao 1" da imagem "Liberar"
    Então eu devo ver o alert do confirmar e clico no ok

    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Responder Avaliações de Desempenho"
    E eu clico "Exibir Filtro"
    E eu clico no botão "Pesquisar"
    E eu devo ver o alert "Preencha os campos indicados." e clico no ok 

    E eu seleciono "Samuel" de "Avaliador"
    E eu seleciono "Todas" de "Situação"
    E eu clico no botão "Pesquisar"

    Quando eu clico na imagem com o título "Responder"
    E eu devo ver o título "Responder Avaliação de Desempenho"
    E eu preencho campo pelo class "opcaoResposta1" com "Respota 1"
    E eu seleciono "8" de "Selecione a nota de 5 a 10"
    E eu marco o checkbox com name "perguntas[2].colaboradorRespostas[0].resposta.id"
    E eu marco o checkbox com name "perguntas[3].colaboradorRespostas[1].resposta.id"
    E eu clico no botão "Gravar"
    E eu devo ver "Respostas gravadas com sucesso."
    Entao eu devo ver "90,7%"

    Quando eu clico na imagem com o título "Editar respostas"
    E eu devo ver o título "Responder Avaliação de Desempenho"
    E eu marco o checkbox com name "perguntas[3].colaboradorRespostas[2].resposta.id"
    E eu clico no botão "Gravar"
    E eu devo ver "Respostas gravadas com sucesso."
    Entao eu devo ver "90,7%"

    Quando eu clico na imagem com o título "Editar respostas"
    E eu devo ver o título "Responder Avaliação de Desempenho"
    E eu espero 1 segundo
    E eu devo ver "90.70%"
    E eu marco "Java"

    Entao eu escolho "niveisCompetenciaFaixaSalariais[0].nivelCompetencia.id"
    E eu clico no botão "Gravar"
    E eu devo ver "Respostas gravadas com sucesso."
    E eu devo ver "2,17%"
    E eu devo ver "84,78%"

    Entao eu clico na imagem com o título "Editar respostas"
    E eu espero 1 segundo
    E eu devo ver "2.17%"
    E eu devo ver "84.78%"












