# language: pt

Funcionalidade: Cadastrar Plano de Treinamento

  Cenário: Plano de Treinamento
    Dado que exista um curso "tdd"
    Dado que exista uma turma "a1" para o curso "tdd"

    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "T&D > Movimentações > Plano de Treinamento"
    Então eu devo ver o título "Plano de Treinamento"
    E eu clico "Exibir Filtro"
    E eu seleciono "tdd" de "Curso"
    E eu seleciono "Não" de "Realizada"
    E eu preencho "dataIni" com "01/01/2013"
    E eu clico no botão "Pesquisar"
    E eu devo ver "tdd"
    
    Entao eu clico na linha "tdd" da imagem "Editar Turma"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Plano de Treinamento" 
    
    Entao eu clico na linha "tdd" da imagem "Editar Turma"
    E eu devo ver o título "Editar Turma - a1"
    E o campo "Descrição" deve conter "a1"
    E eu preencho "Descrição" com "turma1"
    E eu preencho "Horário" com "08:00"
    E eu preencho "Nome" com "joao"
    E eu preencho "Custo (R$)" com "100,00"
    E eu preencho "Instituição" com "fortes"
    E eu preencho "Qtd. Prevista de Participantes" com "25"
    E eu seleciono "Não" de "Realizada"
    E eu preencho o campo (JS) "prevIni" com "01/01/2013"
    E eu preencho o campo (JS) "prevFim" com "15/01/2013"
    E eu marco "02012013D"
    E eu marco "03012013D"
    E eu marco "04012013D"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Plano de Treinamento"
    E eu devo ver "tdd"
    E eu devo ver "turma1"

    Entao eu clico na linha "tdd" da imagem "Excluir Turma"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver o título "Plano de Treinamento"
    E eu devo ver "Turma excluída com sucesso."
    