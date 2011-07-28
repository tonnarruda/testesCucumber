# language: pt

Funcionalidade: Relatório de Avaliações de Candidatos

  Cenário: Relatório de Avaliações de Candidatos
    Dado que eu esteja logado
    Dado que exista o estabelecimento "centro"
    Dado que exista a área organizacional "geral"
    Quando eu acesso o menu "R&S > Relatórios > Avaliações de Candidatos"
    Então eu devo ver o título "Relatório de Avaliações de Candidatos"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu preencho o campo (JS) "dataIni" com "02/02/2000"
    E eu preencho o campo (JS) "dataFim" com "05/05/2000"
    E eu marco "centro"
    E eu marco "geral"
    E eu clico no botão "Relatorio"
    Entao eu devo ver "Não existem dados para o filtro informado."