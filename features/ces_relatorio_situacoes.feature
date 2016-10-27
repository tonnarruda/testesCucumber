# language: pt

Funcionalidade: Relatório de Situações

  Cenário: Relatório de Situações
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista o estabelecimento "estabelecimento"
    Dado que exista a área organizacional "geral"

    Quando eu acesso o menu "C&S > Relatórios > Situações"
    Então eu devo ver o título "Relatório de Situações"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok

    E eu preencho o campo (JS) "dataIni" com "01/01/2011"
    E eu preencho o campo (JS) "dataFim" com "01/01/2011"
    E eu marco "estabelecimento"
    E eu marco "geral"

