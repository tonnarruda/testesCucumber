# language: pt

Funcionalidade: Relatório de Duração para Preenchimento de Vagas

  Cenário: Relatório de Duração para Preenchimento de Vagas
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista o estabelecimento "centro"
    Dado que exista a área organizacional "geral"
    Quando eu acesso o menu "R&S > Relatórios > Duração para Preenchimento de Vagas"
    Então eu devo ver o título "Duração para Preenchimento de Vagas"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu preencho o campo (JS) "dataDe" com "02/02/2000"
    E eu preencho o campo (JS) "dataAte" com "05/05/2000"
    E eu marco "centro"
    E eu marco "geral"
    E eu clico no botão "Relatorio"
    Entao eu devo ver "Não existem dados para o filtro informado."
    E eu clico no botão "RelatorioExportar"
    Entao eu devo ver "Não existem dados para o filtro informado."