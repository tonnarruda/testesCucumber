# language: pt

Funcionalidade: Alterar Senha

  Cenário: Alterar Senha
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Utilitários > Alterar Senha"
    Então eu devo ver o título "Alterar senha de Usuário"
    E eu devo ver "Sua conta de usuário não está vinculada à um colaborador."