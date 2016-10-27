# language: pt

Funcionalidade: Cadastrar Grupos Ocupacionais

  Cenário: Cadastro de Grupos Ocupacionais
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Grupos Ocupacionais"
    Então eu devo ver o título "Grupos Ocupacionais"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Grupo Ocupacional"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Grupos Ocupacionais"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Grupo Ocupacional"
    E eu preencho "Nome" com "_grupo ocup"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Grupos Ocupacionais"
    E eu devo ver "_grupo ocup"

    Entao eu clico em editar "_grupo ocup"
    E eu devo ver o título "Editar Grupo Ocupacional"
    E o campo "Nome" deve conter "_grupo ocup"
    E eu preencho "Nome" com "_grupo ocupacional"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Grupos Ocupacionais"

    Então eu clico em excluir "_grupo ocupacional"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Grupo Ocupacional excluído com sucesso."
    E eu não devo ver "_grupo ocupacional"