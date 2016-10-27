# language: pt

Funcionalidade: Modelos de Entrevistas de Desligamento

  Cenário: Cadastro de Modelos de Entrevistas de Desligamento
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Modelos de Entrevistas de Desligamento"
    Então eu devo ver o título "Modelos de Entrevistas de Desligamento"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Modelo de Entrevista de Desligamento"
    E eu clico no botão "Avancar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Modelos de Entrevistas de Desligamento"
    E eu clico no botão "Inserir"
    E eu preencho "Título" com "Padrão"
    E eu clico no botão "Avancar"
    Então eu devo ver o título "Entrevista de Desligamento - Padrão"
    E eu clico no botão "Voltar"
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Modelos de Entrevistas de Desligamento"

    Entao eu clico em editar "Padrão"
    E eu devo ver o título "Editar Modelo de Entrevista de Desligamento"
    E o campo "Título" deve conter "Padrão"
    E eu preencho "Título" com "Unico"
    E eu clico no botão "Avancar"
    E eu devo ver o título "Entrevista de Desligamento - Unico"
    E eu clico no botão "Voltar"
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Modelos de Entrevistas de Desligamento"

    Então eu clico na linha "Unico" da imagem "Clonar"
    E eu devo ver "Clonar: Unico"
    E eu marco "Empresa Padrão"
    E eu clico no botão "Clonar"
    E eu devo ver "Unico (Clone)"

    Então eu clico em excluir "Unico (Clone)"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Entrevista excluída com sucesso."
    E eu não devo ver "Unico (Clone)"

    Então eu clico em excluir "Unico"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Entrevista excluída com sucesso."
    E eu não devo ver "Unico"












