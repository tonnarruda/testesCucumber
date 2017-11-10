# language: pt

Funcionalidade: Logar no sistema
  Para utilizar o sistema
  Como um usuário
  Eu quero logar
  
  Esquema do Cenario: Tentativas de Logar com usuários/senhas válidos e inválidos
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
    | "SOS"           | "1234"| ""                                  |

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

#------------------------------------------------------------------------------------------------------------------------

  Esquema do Cenario: Esqueci Minha Senha
    Dado que exista um usuario "admin" associado a um empregado   
    Dado que exista um usuario "usuario" sem senha, associado a um empregado
    Dado que eu esteja deslogado
       E eu clico "Esqueci minha senha"
       E eu preencho "cpf" com <CPF>
       E eu clico no botão "Enviar"
   Então eu devo ver <mensagem>    

  Exemplos:
    | CPF              | mensagem                                                                                 |
    | "060.607.223-34" | "Caro(a) Sr(a), não identificamos um endereço de e-mail associado ao seu usuário."       |
    | "123.213.623-91" | "Sua senha foi enviada para seu E-mail."                                                 |
    | "344.251.645-55" | "Caro(a) Sr(a), não identificamos uma senha associada ao seu cpf na empresa selecionada."|