# language: pt

Funcionalidade: Cadastrar Perfil

  Cenário: Cadastro de Perfil
    Dado que exista um papel "configuracoes" 
    Dado que exista um papel "mensagens"
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Utilitários > Cadastros > Perfis"
    Então eu devo ver o título "Perfis"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Perfil"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Perfis"
    E eu clico no botão "Inserir"
    E eu preencho "Nome" com "gerente"
    E eu marco "configuracoes"
    E eu marco "mensagens"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Perfis"
    Então eu devo ver "gerente"

    Entao eu clico em editar "gerente"
    E eu devo ver o título "Editar Perfil"
    E o campo "Nome" deve conter "gerente"
    E eu preencho "Nome" com "gestor"
    E eu desmarco "configuracoes"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Perfis"
    Então eu devo ver "gestor"

    Então eu clico em excluir "gestor"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Perfil excluído com sucesso."
    Então eu não devo ver na listagem "gestor"