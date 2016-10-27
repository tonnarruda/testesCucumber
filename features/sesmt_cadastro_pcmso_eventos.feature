# language: pt

Funcionalidade: PCMSO Eventos

  Cenário: Cadastro de Eventos do PCMSO
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > PCMSO > Eventos"
    Então eu devo ver o título "Eventos"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Evento"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Eventos"

    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Evento"
    E eu preencho "Nome" com "evento20"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Eventos"
    E eu devo ver "evento20"

    Entao eu clico em editar "evento20"
    E eu devo ver o título "Editar Evento"
    E o campo "Nome" deve conter "evento20"
    E eu preencho "Nome" com "evento33"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Eventos"
    Então eu devo ver "evento33"

   # TODO babau agenda precisa de um evento
   # Então eu clico em excluir "evento33"
   # E eu devo ver o alert do confirmar exclusão e clico no ok
   # E eu devo ver "Evento excluído com sucesso."
   # Então eu não devo ver na listagem "evento33"
