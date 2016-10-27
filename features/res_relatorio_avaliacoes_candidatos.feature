# language: pt

Funcionalidade: Relatório de Avaliações de Candidatos

  Cenário: Relatório de Avaliações de Candidatos
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista o estabelecimento "centro"
    Dado que exista a área organizacional "geral"
    Dado que exista o grupo ocupacional "grupo 1"
    Quando eu acesso o menu "R&S > Relatórios > Avaliações de Candidatos"
    Então eu devo ver o título "Avaliações de Candidatos"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu preencho o campo (JS) "dataIni" com "02/02/2000"
    E eu preencho o campo (JS) "dataFim" com "05/05/2000"
    E eu seleciono "Grupo Ocupacional" de "Filtrar Colaboradores por"
    E eu marco "grupo 1"
    E eu seleciono "Área Organizacional" de "Filtrar Colaboradores por"
    E eu marco "centro"
    E eu marco "geral"
    E eu seleciono "Abertas e Encerradas" de "Considerar Solicitações de Pessoal"
    E eu seleciono "Abertas" de "Considerar Solicitações de Pessoal"
    E eu seleciono "Encerradas" de "Considerar Solicitações de Pessoal"
    E eu clico no botão "Relatorio"
    Entao eu devo ver "Não existem dados para o filtro informado."