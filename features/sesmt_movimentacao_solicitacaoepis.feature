# language: pt

Funcionalidade: Solicitações de EPI

  Cenário: Cadastro de Solicitações de EPI
    Dado que exista o EPI "jaleco" da categoria "roupas"
    Dado que exista um colaborador "geraldo", da area "administracao", com o cargo "quimico" e a faixa salarial "I"

    Dado que eu esteja logado com o usuário "fortes"
    Quando eu acesso o menu "SESMT > Movimentações > Solicitação de EPIs"
    Então eu devo ver o título "Solicitações de EPI"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Solicitação de EPIs"
    E eu preencho "Nome" com "Gera"
    E eu clico no botão "Pesquisar"
    E eu seleciono "geraldo" de "colaborador"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert "Selecione pelo menos um EPI." e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Solicitações de EPI"

    Então eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Solicitação de EPIs"
    E eu preencho "Nome" com "Gera"
    E eu clico no botão "Pesquisar"
    E eu seleciono "geraldo" de "Colaborador"
    E eu preencho o campo (JS) "data" com "28/07/2011"
    E eu marco "epiIds"
    E eu preencho o campo (JS) "selectQtdSolicitado" com "2"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Solicitações de EPI"
    E eu devo ver "geraldo"

    Entao eu clico em editar "geraldo"
    E eu devo ver o título "Editar Solicitação de EPIs"
    E o campo "Data" deve conter "28/07/2011"
    E eu preencho o campo (JS) "data" com "25/07/2011"
    E eu preencho o campo (JS) "selectQtdSolicitado" com "3"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Solicitações de EPI"

    Então eu clico na linha "geraldo" da imagem "Entrega"
    E eu devo ver o título "Entrega de EPIs"
    E eu clico na imagem com o título "Inserir entrega"
    E eu clico no botão "Gravar"
    E eu devo ver o alert do confirmar e clico no ok
    E eu preencho o campo (JS) "dataEntrega" com "28/07/2011"
    E eu preencho "Qtd. Entregue" com "1"
    E eu seleciono "01/02/2011 - a0a1a2a3 - 30" de "epiHistoricoId"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Entrega de EPIs"
    Então eu clico no botão "Voltar"

    Então eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Solicitação de EPIs"
    E eu preencho "Nome" com "Gera"
    E eu clico no botão "Pesquisar"
    E eu seleciono "geraldo" de "Colaborador"
    E eu preencho o campo (JS) "data" com "28/07/2011"
    E eu marco "epiIds"
    E eu preencho o campo (JS) "selectQtdSolicitado" com "2"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Solicitações de EPI"
    E eu devo ver "geraldo"

    Então eu clico em excluir "geraldo"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Solicitação de EPIs excluída com sucesso."
    
    Então eu clico "Exibir Filtro"
    E eu seleciono "Aberta" de "Situação"
    E eu clico no botão "Pesquisar"
    E eu não devo ver "geraldo"

