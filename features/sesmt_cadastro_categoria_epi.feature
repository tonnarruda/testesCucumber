# language: pt

Funcionalidade: Categorias de EPI/Fardamento

  Cenário: Cadastro de Categorias de EPI/Fardamento
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Categorias de EPI/Fardamento"
    Então eu devo ver o título "Categorias de EPI/Fardamento"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Categoria de EPI"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Categorias de EPI/Fardamento"
    E eu clico no botão "Inserir"
    E eu preencho "Nome" com "fogo"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Categorias de EPI/Fardamento"
    Então eu devo ver "fogo"

    Entao eu clico em editar "fogo"
    E eu devo ver o título "Editar Categoria de EPI"
    E o campo "Nome" deve conter "fogo"
    E eu preencho "Nome" com "agua"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Categorias de EPI/Fardamento"
    Então eu devo ver "agua"

    Então eu clico em excluir "agua"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Tipo de EPI excluído com sucesso."
    Então eu não devo ver na listagem "agua"












