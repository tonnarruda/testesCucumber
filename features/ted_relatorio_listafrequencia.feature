# language: pt

Funcionalidade: Relatório de Lista de Frequência

  Cenário: Relatório de Lista de Frequência
    Dado que exista um curso "java"
    Dado que exista uma turma "turma1" para o curso "java"

    Dado que eu esteja logado
    
    Quando eu acesso o menu "T&D > Relatórios > Lista de Frequência"
    Então eu devo ver o título "Lista de Freqüência"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok

    Então eu seleciono "java" de "Curso"
    E eu seleciono "turma1" de "Turma"
    E eu marco "02/07/2012 - segunda-feira"
    E eu marco "03/07/2012 - terça-feira"
    E eu marco "04/07/2012 - quarta-feira"
    E eu marco "05/07/2012 - quinta-feira"
    E eu marco "Exibir conteúdo programático"
    E eu marco "Exibir conteúdo programático"
    E eu marco "Exibir critérios de avaliação"
    E eu marco "Exibir nome comercial"
    E eu marco "Exibir espaço para nota"
    E eu preencho "Qtd. Linhas" com "15"

    Então eu clico no botão "Relatorio"
    E eu devo ver o alert "É necessário selecionar exatamente duas colunas extras." e clico no ok
    E eu marco "Cargo"
    E eu marco "Estabelecimento"