# language: pt

Funcionalidade: Cadastrar Etapa Seletiva

  Cenário: Cadastro de Etapa Seletiva
    Dado que eu esteja logado
    Quando eu acesso o menu "R&S > Cadastros > Etapas Seletivas"
    Então eu devo ver o título "Etapas Seletivas"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Etapa Seletiva"
    E eu preencho "nome" com "entrevista com chefia"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Etapas Seletivas"
    Então eu devo ver "entrevista com chefia"
    E eu clico em editar "entrevista com chefia"
    E eu devo ver o título "Editar Etapa Seletiva"
    E o campo "ordem" deve conter "2"
    E o campo "nome" deve conter "entrevista com chefia"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Etapas Seletivas"
    E eu clico em excluir "entrevista com chefia"
    Então eu devo ver "Confirma exclusão?"
    E eu aperto "OK"
    Então eu devo ver "Etapa Seletiva excluída com sucesso."
    E eu não devo ver "entrevista com chefia"