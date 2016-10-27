# language: pt

Funcionalidade: Motivos de Afastamentos

  Cenário: Cadastro de Motivos de Afastamentos
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Motivos de Afastamentos"
    Então eu devo ver o título "Motivos de Afastamentos"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Motivo de Afastamento"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Motivos de Afastamentos"

    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Motivo de Afastamento"
    E eu preencho "Descrição" com "pequebrado"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Motivos de Afastamentos"
    E eu devo ver "pequebrado"

    Entao eu clico em editar "pequebrado"
    E eu devo ver o título "Editar Motivo de Afastamento"
    E o campo "Descrição" deve conter "pequebrado"
    E eu preencho "Descrição" com "joelhoquebrado"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Motivos de Afastamentos"
    Então eu devo ver "joelhoquebrado"

    Então eu clico em excluir "joelhoquebrado"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Afastamento excluído com sucesso."
    Então eu não devo ver na listagem "joelhoquebrado"
