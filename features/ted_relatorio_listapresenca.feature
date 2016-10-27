# language: pt

Funcionalidade: Relatório de Lista de Frequência

  Cenário: Relatório de Lista de Frequência
    Dado que haja um curso com id 1, nome "java",empresa_id 1
    Dado que haja uma turma com id 1, descricao "turma1", realizada "false", dataprevini "01/01/2013", dataprevfim "15/01/2013", porTurno "true" ,curso_id 1,empresa_id 1
    Dado que haja um diaTurma com id 1, dia "02/01/2013", turma_id 1, turno "T"
    Dado que haja um diaTurma com id 2, dia "03/01/2013", turma_id 1, turno "M"
    Dado que haja um diaTurma com id 3, dia "04/01/2013", turma_id 1, turno "N"
    Dado que haja um diaTurma com id 4, dia "05/01/2013", turma_id 1, turno "M"
    Dado que eu esteja logado com o usuário "SOS"
    
    Quando eu acesso o menu "T&D > Relatórios > Lista de Presença"
    Então eu devo ver o título "Lista de Presença"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok

    Então eu seleciono "java" de "Curso"
    E eu seleciono "turma1" de "Turma"
    E eu marco "02/01/2013 - quarta-feira - tarde"
    E eu marco "03/01/2013 - quinta-feira - manhã"
    E eu marco "04/01/2013 - sexta-feira - noite"
    E eu marco "05/01/2013 - sábado - manhã"
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