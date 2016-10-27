# language: pt

Funcionalidade: Relatório Matriz de Treinamentos

  Cenário: Relatório Matriz de Treinamentos
    Dado que exista a área organizacional "adm"
    Dado que exista o cargo "motorista"
    Dado que exista a faixa salarial "I" no cargo "motorista"

    Dado que eu esteja logado com o usuário "SOS"
    
    Quando eu acesso o menu "T&D > Relatórios > Matriz de Treinamentos"
    Então eu devo ver o título "Matriz de Treinamentos"
    E eu marco "adm"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok