# language: pt

Funcionalidade: Logar no sistema
  Para utilizar o sistema
  Como um usuário
  Eu quero logar
  
  Cenário: Logar com o usuario e senha validos
    Dado que eu esteja logado com o usuário "SOS"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Logar com o usuario e senha invalidos
    Dado que eu esteja deslogado
       E eu preencho "username" com "usuario_teste"
       E eu preencho "password" com "1234"
       E eu clico em "Entrar"
   Então eu devo ver "Usuário sem permissão de acesso"
       E eu devo deslogar do sistema

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Logar com o SOS com senha inválida
    Dado que eu esteja deslogado
       E eu preencho "username" com "SOS"
       E eu preencho "password" com "9999"
       E eu clico em "Entrar"
   Então eu devo ver "Máquina sem autorização de acesso"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Logar com usuário e senha embranco
    Dado que eu esteja deslogado
       E eu preencho "username" com ""
       E eu preencho "password" com ""
       E eu clico em "Entrar"
   Então eu devo ver "Usuário sem permissão de acesso"
       E eu devo deslogar do sistema