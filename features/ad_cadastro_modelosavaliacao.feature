# language: pt

Funcionalidade: Cadastrar Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência

  Cenário: Cadastro de Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência
    Dado que eu esteja logado

    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Avaliações de Desempenho/Acomp. do Período de Experiência"
    Então eu devo ver o título "Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Modelo de Avaliação"
    E eu clico no botão "Avancar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Modelo de Avaliação"
    E eu preencho "Título" com "_avaliacao I"
    E eu preencho "Observação" com "_experiencia"
    E eu seleciono "Avaliação de Desempenho" de "Tipo de Avaliação"
    E eu clico no botão "Avancar"
    E eu devo ver o título "Inserir Critério de Avaliação"
    E eu preencho "aspecto" com "_aspecto I"
    E eu seleciono "1" de "Ordem"
    E eu preencho "peso" com "1"
    E eu preencho "criterio" com "_criterio I"
    E eu seleciono "Subjetiva" de "Tipo de Resposta"
    E eu clico no botão "Gravar"
    Então eu devo ver "Critério gravado com sucesso."

    E eu clico no botão "Voltar"
    E eu devo ver o título "Critérios de Avaliação"
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência"
    E eu devo ver "_avaliacao I"

    Entao eu clico em editar "_avaliacao I"
    E eu devo ver o título "Editar Modelo de Avaliação"
    E o campo "Título" deve conter "_avaliacao I"
    E eu preencho "Título" com "_avaliacao II"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência"

