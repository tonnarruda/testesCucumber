# language: pt

Funcionalidade: Logar no sistema
  Para utilizar o sistema
  Como um usuário
  Eu quero logar
  
  Cenário: Logar com o usuario e senha validos
    Dado que eu esteja logado com o usuário "SOS"

#------------------------------------------------------------------------------------------------------------------------

  Esquema do Cenario: Logar com o usuario e/ou senha invalidos
    Dado que eu esteja deslogado
       E eu preencho "username" com <usuario>
       E eu preencho "password" com <senha>
       E eu clico em "Entrar"
   Então eu devo ver <mensagem>
       E eu devo deslogar do sistema

  Exemplos:
    | usuario         | senha | mensagem                            |
    | "usuario_teste" | "1234"| "Usuário sem permissão de acesso"   |
    | ""              | ""    | "Usuário sem permissão de acesso"   |
    | "SOS"           | "9999"| "Máquina sem autorização de acesso" |

#------------------------------------------------------------------------------------------------------------------------

  Esquema do Cenario: Validar Captcha
    Dado que exista um usuario "admin"    
    Dado que a configuração do captcha esteja ativa
    Dado que eu esteja deslogado
       E eu preencho "username" com <usuario>
       E eu preencho "password" com <senha>
       E eu clico em "Entrar"
   Então eu devo ver <mensagem>
       E eu devo deslogar do sistema

  Exemplos:
    | usuario | senha | mensagem                            |
    | "admin" | "1234"| "Usuário sem permissão de acesso"   |
    | "SOS"   | "1234"| ""                                  |