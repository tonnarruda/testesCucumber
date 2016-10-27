# language: pt

Funcionalidade: Relatório de Estatísticas de Vagas por Motivo

  Cenário: Relatório de Estatísticas de Vagas por Motivo
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista o estabelecimento "centro"
    Dado que exista a área organizacional "geral"
    Quando eu acesso o menu "R&S > Relatórios > Estatísticas de Vagas por Motivo"
    Então eu devo ver o título "Estatísticas de Vagas por Motivo"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu preencho o campo (JS) "dataDe" com "02/02/2000"
    E eu preencho o campo (JS) "dataAte" com "05/05/2000"
    E eu marco "centro"
    E eu marco "geral"
    E eu seleciono "Abertas e Encerradas" de "Considerar Solicitações de Pessoal"
    E eu seleciono "Abertas" de "Considerar Solicitações de Pessoal"
    E eu seleciono "Encerradas" de "Considerar Solicitações de Pessoal"
    E eu clico no botão "Relatorio"
    Entao eu devo ver "Não existem dados para o filtro informado."