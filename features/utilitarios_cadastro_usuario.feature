# language: pt

Funcionalidade: Cadastrar Usuário

  Cenário: Cadastro de Usuário
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Utilitários > Cadastros > Usuários"
    Então eu devo ver o título "Usuários"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Usuário"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Usuários"
    E eu clico no botão "Inserir"
    E eu preencho "Nome" com "joao"
    E eu preencho "Login" com "joao"
    E eu preencho "Senha" com "1234"
    E eu preencho "Confirmar Senha" com "1234"
    E eu seleciono "Sim" de "Ativo"
    E eu marco "Super Administrador (é permitido apenas um Super Admin. por sistema)"
    E eu seleciono "Administrador" de "selectPerfil_1"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Usuários"
    Então eu devo ver "joao"

    Entao eu clico em editar "joao"
    E eu devo ver o título "Editar Usuário"
    E o campo "Nome" deve conter "joao"
    E eu preencho "Nome" com "jorge"
    E eu preencho "Login" com "SOS"
    Então eu clico no botão "Gravar"
    E eu devo ver "Este login já existe."
    E eu preencho "Nome" com "jorge"
    E eu preencho "Login" com "jorge"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Usuários"
    E eu não devo ver "joao"
    Então eu devo ver "jorge"

    Então eu clico em excluir "jorge"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Usuário excluído com sucesso."
    Então eu não devo ver "jorge"