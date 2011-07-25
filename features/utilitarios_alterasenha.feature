# language: pt

Funcionalidade: Alterar Senha

  Cenário: Alterar Senha
    Dado que eu esteja logado

    Quando eu acesso o menu "Utilitários > Alterar Senha"
    Então eu devo ver o título "Alterar senha de Usuário"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok

    Então eu preencho "Senha atual" com "1234"
    E eu preencho "Nova senha" com "1234"
    E eu preencho "Confirmação nova senha" com "1234"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert "Sua senha foi alterada com sucesso." e clico no ok