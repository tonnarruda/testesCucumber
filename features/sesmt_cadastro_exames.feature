# language: pt

Funcionalidade: Exames

  Cenário: Cadastro de Exames
    Dado que eu esteja logado com o usuário "SOS"
    
    Quando eu acesso o menu "SESMT > Cadastros > Exames"
    Então eu devo ver o título "Exames"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Exame"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Exames"

    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Exame"
    E eu preencho "Nome" com "barriga"
    E eu preencho "Periodicidade (em meses)" com "11"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Exames"
    E eu devo ver "barriga"
    E eu devo ver "Sim"
    E eu devo ver "11"

    Entao eu clico em editar "barriga"
    E eu devo ver o título "Editar Exame"
    E o campo "Nome" deve conter "barriga"
    E eu preencho "Nome" com "barriga2"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Exames"
    Então eu devo ver "barriga2"

    Então eu clico em excluir "barriga2"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Exame excluído com sucesso."
    Então eu não devo ver na listagem "barriga2"
