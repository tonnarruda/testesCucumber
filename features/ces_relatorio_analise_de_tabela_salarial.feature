# language: pt

Funcionalidade: Relatório de Análise de Tabela Salarial

  Cenário: Relatório de Análise de Tabela Salarial
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista o grupo ocupacional "grupo"

    
    Quando eu acesso o menu "C&S > Relatórios > Análise de Tabela Salarial"
    Então eu devo ver o título "Análise de Tabela Salarial"
    E eu clico no botão "Avancar"
    Então eu devo ver o alert do valida campos e clico no ok

    E eu preencho o campo (JS) "data" com "01/08/2011"
    E eu marco "grupo"

    E eu clico no botão "Avancar"
    E eu devo ver "Não existem dados para o filtro informado."
