# language: pt

Funcionalidade: Relatório Análise das Etapas Seletivas

  Cenário: Relatório Análise das Etapas Seletivas
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista o cargo "analista"
    Dado que exista a etapa seletiva "prova"
    Quando eu acesso o menu "R&S > Relatórios > Análise das Etapas Seletivas"
    Então eu devo ver o título "Análise das Etapas Seletivas"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu preencho "Ano" com "2007"
    E eu seleciono "analista" de "Cargo"
    E eu marco "prova"