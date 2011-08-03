# language: pt

Funcionalidade: Relatório Acompanhamento de Experiência Previsto

  Cenário: Relatório Acompanhamento de Experiência Previsto
    Dado que eu esteja logado
    Dado que exista a área organizacional "geral"
    Dado que exista o estabelecimento "centro"

    Quando eu acesso o menu "Aval. Desempenho > Relatórios > Acompanhamento do Período de Experiência"
    Então eu devo ver o título "Acompanhamento do Período de Experiência"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok

    E eu preencho "tempoDeEmpresa" com "2"
    E eu marco "geral"
    E eu marco "centro"

    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok


