# language: pt

Funcionalidade: Cadastrar Atitudes

  Cenário: Cadastro de Atitudes
    Dado que eu esteja logado com o usuário "SOS"
    Dado que exista a área organizacional "Administração"
    Dado que exista a área organizacional "Administração > Desenvolvimento"

    Quando eu acesso o menu "C&S > Cadastros > Atitudes"
    Então eu devo ver o título "Atitudes"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Atitude"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Atitudes"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Atitude"
    E eu preencho "Nome" com "_Organização"
    E eu marco "Administração"
    E eu preencho "Observação" com "_muito organizado"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Atitudes"
    E eu devo ver "_Organização"

    Entao eu clico em editar "_Organização"
    E eu devo ver o título "Editar Atitude"
    E o campo "Nome" deve conter "_Organização"
    E eu preencho "Nome" com "_Responsabilidade"
    E eu desmarco "Administração"
    E eu marco "Administração > Desenvolvimento"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Atitudes"

    Então eu clico em excluir "_Responsabilidade"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Atitude excluída com sucesso."
    E eu não devo ver "_Responsabilidade"