# language: pt

Funcionalidade: Relatório de Cronograma de Treinamento

  Cenário: Relatório de Cronograma de Treinamento
    Dado que exista um curso "java"
    Dado que exista uma turma "turma1" para o curso "java"
    Dado que exista uma turma "turma2" para o curso "java"

    Dado que eu esteja logado com o usuário "SOS"
    
    Quando eu acesso o menu "T&D > Relatórios > Cronograma de Treinamento"
    Então eu devo ver o título "Cronograma de Treinamento"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok

    Então eu preencho "dataIni" com "01/01/2013"
    E eu preencho "dataFim" com "31/01/2013"
    E eu seleciono "Não" de "Realizada"
    E eu clico no botão "CarregarTurmas"
    Então eu devo ver "java / turma1"
    E eu devo ver "java / turma2"
    
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok