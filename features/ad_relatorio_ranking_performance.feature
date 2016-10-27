# language: pt

Funcionalidade:  Ranking de Performance das Avaliações de Desempenho

  Cenário:  Ranking de Performance das Avaliações de Desempenho
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista a área organizacional "geral"
    Dado que exista o estabelecimento "centro"

    Quando eu acesso o menu "Aval. Desempenho > Relatórios > Ranking de Performance das Avaliações de Desempenho"
    Então eu devo ver o título "Ranking de Performance das Avaliações de Desempenho"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok

    Entao eu preencho o campo (JS) "periodoIni" com "01/01/2011"
    E eu preencho o campo (JS) "periodoFim" com "01/02/2011"

    E eu marco "centro"
    E eu marco "geral"

    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok

