# language: pt

Funcionalidade: Modelos de Fichas Médicas

  Cenário: Cadastro de Modelos de Fichas Médicas
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Modelos de Fichas Médicas"
    Então eu devo ver o título "Modelos de Fichas Médicas"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Modelo de Ficha Médica"
    E eu clico no botão "Avancar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Modelos de Fichas Médicas"

    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Modelo de Ficha Médica"
    E eu preencho "Título" com "ficha1"
    E eu clico no botão "Avancar"

    Então eu devo ver o título "Ficha Médica - ficha1"
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Editar Modelo de Ficha Médica"
    E o campo "Título" deve conter "ficha1"
    E eu preencho "Título" com "ficha2"
    E eu clico no botão "Avancar"
    Então eu devo ver o título "Ficha Médica - ficha2"
    E eu clico no botão "Voltar"
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Modelos de Fichas Médicas"

    Então eu clico na linha "ficha2" da imagem "Clonar"
    E eu devo ver "Clonar: ficha2"
    E eu marco "Empresa Padrão"
    E eu clico no botão "Clonar"
    E eu devo ver "ficha2 (Clone)"

    Então eu clico em excluir "ficha2 (Clone)"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Ficha Médica excluída com sucesso."
    Então eu não devo ver na listagem "ficha2 (Clone)"

    Então eu clico em excluir "ficha2"
    E eu devo ver o alert do confirmar e clico no ok
    E eu devo ver "Ficha Médica excluída com sucesso."
    Então eu não devo ver na listagem "ficha2"