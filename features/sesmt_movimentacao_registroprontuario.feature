# language: pt

Funcionalidade: Registro de Prontuário

  Cenário: Registro de Prontuário
    Dado que exista um colaborador "geraldo", da area "administracao", com o cargo "desenvolvedor" e a faixa salarial "I"
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "SESMT > Movimentações > Registro de Prontuário"
    Então eu devo ver o título "Registro de Prontuário"
    E eu preencho "Nome" com "Gera"
    E eu clico no botão "Pesquisar"
    E eu seleciono "geraldo - 123.213.623-91" de "Colaborador"
    E eu devo ver "Colaborador: geraldo"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Registrar Prontuário"
    E eu clico no botão "Gravar"
    E eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Registro de Prontuário"
    E eu preencho "Nome" com "Gera"
    E eu clico no botão "Pesquisar"
    E eu seleciono "geraldo - 123.213.623-91" de "Colaborador"
    E eu devo ver "Colaborador: geraldo"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Registrar Prontuário"
    E eu preencho o campo (JS) "data" com "28/07/2011"
    E eu preencho "Descrição" com "doença"
    Então eu clico no botão "Gravar"

    Então eu devo ver o título "Registro de Prontuário"
    E eu devo ver "28/07/2011"
    E eu devo ver "doença"

    Então eu clico em editar "28/07/2011"
    E eu devo ver o título "Editar Prontuário"
    E o campo "data" deve conter "28/07/2011"
    E eu preencho o campo (JS) "data" com "25/07/2011"
    E eu preencho "Descrição" com "gripe"
    Então eu clico no botão "Gravar"

    Então eu devo ver o título "Registro de Prontuário"
    E eu clico em excluir "25/07/2011"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Prontuário excluído com sucesso."
    E eu não devo ver "25/07/2011"
