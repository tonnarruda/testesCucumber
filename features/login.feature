# language: pt

Funcionalidade: Logar no sistema
  Para utilizar o sistema
  Como um usuário
  Eu quero logar
  
  Cenário: Logar com o usuario e senha validos
    Dado que eu esteja logado com o usuário "SOS"
#   Então eu devo ver "Bem-vindo(a)"
#   Entao eu acesso o menu "Sair"
#    Dado que eu esteja na pagina de login
#    E eu preencho "username" com "fortes"
#    E eu preencho "password" com "1234"
#    E eu clico em "Entrar"
#    Então eu devo ver "Bem-vindo(a)"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Logar com o usuario e senha invalidos
    Dado que eu esteja deslogado
       E eu preencho "username" com "usuario_teste"
       E eu preencho "password" com "1234"
       E eu clico em "Entrar"
   Então eu devo ver "Usuário sem permissão de acesso"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Logar com o SOS com senha inválida
    Dado que eu esteja deslogado
       E eu preencho "username" com "SOS"
       E eu preencho "password" com "9999"
       E eu clico em "Entrar"
   Então eu devo ver "Máquina sem autorização de acesso"
       E eu espero 5 segundos