# language: pt

Funcionalidade: Cadastrar Etapa Seletiva

  Cenário: Cadastro de Etapa Seletiva
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "R&S > Cadastros > Etapas Seletivas"
    Então eu devo ver o título "Etapas Seletivas"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Etapa Seletiva"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Etapas Seletivas"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Etapa Seletiva"
    E eu preencho "nome" com "entrevista com chefia"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Etapas Seletivas"
    E eu devo ver "entrevista com chefia"

    Entao eu clico em editar "entrevista com chefia"
    E eu devo ver o título "Editar Etapa Seletiva"
    E o campo "ordem" deve conter "1"
    E o campo "nome" deve conter "entrevista com chefia"
    E eu preencho "nome" com "entrevista com chefia 2"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Etapas Seletivas"

    Então eu clico em excluir "entrevista com chefia 2"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Etapa Seletiva excluída com sucesso."
    E eu não devo ver "entrevista com chefia 2"