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
    Dado que exista uma connfiguracao de nivel de competencia da faixa salarial "3" na data "01/01/2010"
    Dado que exista uma connfiguracao de nivel de competencia "regular" no conhecimento "Java" para connfiguracao de nivel de competencia da faixa salarial na data "01/01/2010"
    
    Quando eu acesso o menu "C&S > Cadastros > Cargos e Faixas"
    E eu clico em editar "Desenvolvedor"
    E eu marco "Desenvolvimento (Ativa)"
    E eu marco "Java"
    E eu clico no botão "Gravar"

    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
    Então eu devo ver o título "Avaliações de Desempenho"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Avaliação de Desempenho"
    E eu preencho "Título" com "_avaliacao 1"
    E eu preencho o campo (JS) "inicio" com "01/06/2011"
    E eu preencho o campo (JS) "fim" com "30/06/2011"
    E eu marco "Avaliar somente as competências (Sem utilização de modelo)"
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
    E eu marco "Java"
    E eu seleciono a opçao de valor "3" do rádio de id "niveisCompetenciaFaixaSalariais_0"
    E eu clico no botão "Gravar"
    E eu devo ver "Respostas gravadas com sucesso."
    Entao eu devo ver "100%"

    Quando eu clico na imagem com o título "Editar respostas"
    E eu devo ver o título "Responder Avaliação de Desempenho"
    E eu clico no botão "Gravar"
    E eu devo ver "Respostas gravadas com sucesso."
    Entao eu devo ver "100%"

#-------------------------------------------------------------------------------------------------------------------------------------------------

