# language: pt

Funcionalidade: Relatório de Descrição de Cargos

  Cenário: Relatório de Descrição de Cargos
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista o cargo "analista"
    Dado que exista a área organizacional "geral"
    Quando eu acesso o menu "C&S > Relatórios > Descrição de Cargos"
    Então eu devo ver o título "Descrição de Cargos"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu marco "geral"
    E eu clico no botão "Relatorio"
