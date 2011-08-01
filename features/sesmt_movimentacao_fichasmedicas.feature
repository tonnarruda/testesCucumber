# language: pt

Funcionalidade: Fichas Médicas

  Cenário: Cadastro de Fichas Médicas
    Dado que exista um colaborador "geraldo", da area "administracao", com o cargo "desenvolvedor" e a faixa salarial "I"
    Dado que exista um modelo de ficha medica "admissional" com a pergunta "alérgico?"

    Dado que eu esteja logado
    Quando eu acesso o menu "SESMT > Movimentações > Fichas Médicas"
    Então eu devo ver o título "Fichas Médicas"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Fichas Médicas"
    E eu seleciono "Colaborador" de "Ficha para"
    E eu preencho "Nome" com "Gera"
    E eu clico no botão "Pesquisar"
    E eu clico no botão "Avancar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu seleciono "geraldo" de "Colaborador"
    E eu seleciono "admissional" de "ficha"
    E eu clico no botão "Avancar"

    Então eu devo ver o título "Ficha médica - admissional"
    E eu devo ver "Colaborador: geraldo"
    E eu preencho o campo (JS) "respondidaEm" com "29/07/2011"
    E eu preencho "RS1" com "acido acetil salicilico"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Fichas Médicas"
    E eu devo ver "Respostas gravadas com sucesso."
    Então eu clico no botão "Voltar"
    E eu devo ver "29/07/2011"
    E eu devo ver "geraldo"

    Entao eu clico em editar "29/07/2011"
    E eu devo ver o título "Ficha médica - admissional"
    E eu devo ver "Colaborador: geraldo"
    E o campo "respondidaEm" deve conter "29/07/2011"
    E eu preencho o campo (JS) "respondidaEm" com "27/07/2011"
    E eu preencho "RS1" com "dipirona"
    E eu clico no botão "Gravar"
    E eu devo ver "27/07/2011"

    Então eu clico em excluir "27/07/2011"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Ficha médica excluída com sucesso."
    E eu não devo ver "geraldo"

