# language: pt

Funcionalidade: Relatório Acompanhamento de Experiência Previsto

  Cenário: Relatório Acompanhamento de Experiência Previsto
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista a área organizacional "geral"
    Dado que exista o estabelecimento "centro"

    Quando eu acesso o menu "Aval. Desempenho > Relatórios > Acompanhamento de Experiência Previsto"
    Então eu devo ver o título "Acompanhamento de Experiência Previsto"
    E eu clico no botão "Relatorio"
    Então eu devo ver "Não existem colaboradores com os filtros selecionados"

    E eu preencho "tempoDeEmpresa" com "2"
    E eu marco "geral"
    E eu marco "centro"

    E eu clico no botão "Relatorio"
    Então eu devo ver "Não existem colaboradores com os filtros selecionados"

