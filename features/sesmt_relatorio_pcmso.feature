# language: pt

Funcionalidade: Relatório de PCMSO - Programa de Controle Médico de Saúde Ocupacional

  Cenário: Relatório de PCMSO - Programa de Controle Médico de Saúde Ocupacional

    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Relatórios > PCMSO"
    Então eu devo ver o título "PCMSO - Programa de Controle Médico de Saúde Ocupacional"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok

    E eu preencho o campo (JS) "dataIni" com "01/07/2011"
    E eu preencho o campo (JS) "dataFim" com "29/07/2011"
    Então eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu marco "Agenda"
    E eu marco "Distribuição de Colaboradores por Setor"
    E eu marco "Riscos Ambientais"
    E eu marco "EPIs por Função"
    E eu marco "Tabela de Exames Realizados"
    E eu marco "Acidentes de Trabalho"