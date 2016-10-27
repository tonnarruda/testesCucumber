# language: pt

Funcionalidade: Cadastrar Conhecimentos

  Cenário: Cadastro de Conhecimentos
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista a área organizacional "Administração"
    Dado que exista a área organizacional "Administração > Desenvolvimento"

    Quando eu acesso o menu "C&S > Cadastros > Conhecimentos"
    Então eu devo ver o título "Conhecimentos"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Conhecimento"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Conhecimentos"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Conhecimento"
    E eu preencho "Nome" com "_Ruby"
    E eu marco "Administração"
    E eu preencho "Observação" com "_Ruby super ferramenta"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Conhecimentos"
    E eu devo ver "_Ruby"

    Entao eu clico em editar "_Ruby"
    E eu devo ver o título "Editar Conhecimento"
    E o campo "Nome" deve conter "_Ruby"
    E eu preencho "Nome" com "_Ruby Rails"
    E eu desmarco "Administração"
    E eu marco "Administração > Desenvolvimento"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Conhecimentos"

    Então eu clico em excluir "_Ruby Rails"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Conhecimento excluído com sucesso."
    E eu não devo ver "_Ruby Rails"