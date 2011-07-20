# language: pt

Funcionalidade: Cadastrar Plano de Treinamento

  Cenário: Plano de Treinamento
    Dado que exista um curso "tdd"
    Dado que exista uma turma "a1" para o curso "tdd"

    Dado que eu esteja logado

    Quando eu acesso o menu "T&D > Movimentações > Plano de Treinamento"
    Então eu devo ver o título "Plano de Treinamento"
    E eu clico "Exibir Filtro"
    E eu seleciono "tdd" de "Curso"
    E eu seleciono "Não" de "Realizada"
    E eu clico no botão "Pesquisar"
    E eu devo ver "tdd"
    
    Entao eu clico na linha "tdd" da imagem "Editar Turma"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Plano de Treinamento" 
    
    Entao eu clico na linha "tdd" da imagem "Editar Turma"
    E eu devo ver o título "Editar Turma - tdd"
    E o campo "Descrição" deve conter "a1"
    E eu preencho "Descrição" com "turma1"
    E eu preencho "Horário" com "08:00"
    E eu preencho "Instrutor" com "joao"
    E eu preencho "Custo (R$)" com "100,00"
    E eu preencho "Instituição" com "fortes"
    E eu preencho "Qtd. Prevista de Participantes" com "25"
    E eu seleciono "Não" de "Realizada"
    E eu preencho o campo (JS) "prevIni" com "01/07/2011"
    E eu preencho o campo (JS) "prevFim" com "15/07/2011"
    E eu marco "01/07/2011 - sexta-feira"
    E eu marco "04/07/2011 - segunda-feira"
    E eu marco "06/07/2011 - quarta-feira"
    E eu marco "08/07/2011 - sexta-feira"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Plano de Treinamento"
    E eu devo ver "tdd"
    E eu devo ver "turma1"

    Entao eu clico na linha "tdd" da imagem "Excluir Turma"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver o título "Plano de Treinamento"
    E eu devo ver "Turma excluída com sucesso."
    