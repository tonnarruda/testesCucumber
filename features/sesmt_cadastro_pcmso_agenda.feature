# language: pt

Funcionalidade: Agenda

  Cenário: Cadastro de Agenda
    Dado que eu esteja logado
    Quando eu acesso o menu "SESMT > Cadastros > PCMSO > Agenda"
    Então eu devo ver o título "Agenda"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Agenda"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Agenda"

    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Agenda"
    E eu preencho a data "Mês/Ano" com "02/2011"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu seleciono "evento33" de "Evento"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Agenda"
    E eu devo ver "evento33"
    E eu devo ver "02/2011"

    Então eu clico em excluir "evento33"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Agenda excluída com sucesso."
    Então eu não devo ver na listagem "evento33"
