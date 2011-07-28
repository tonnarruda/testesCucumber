# language: pt

Funcionalidade: Relatório de Solicitações Abertas

  Cenário: Relatório de Solicitações Abertas
    Dado que eu esteja logado
    Dado que exista a etapa seletiva "prova"
    Quando eu acesso o menu "R&S > Relatórios > Solicitações Abertas"
    Então eu devo ver o título "Relatório de Solicitações Abertas"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu marco "prova"
    E eu clico no botão "Relatorio"
    Entao eu devo ver "Não existem dados para o filtro informado."