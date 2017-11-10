# language: pt

Funcionalidade: Alterar Senha

  Cenário: Alterar Senha
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Utilitários > Alterar Senha"
    E eu devo ver "Sua conta de usuário não está vinculada à um colaborador."
    E eu devo deslogar do sistema

#--------------------------------------------------------------------------------------------------------------------

Esquema do Cenario: Alterar Senha de Usuários
    Dado que exista um usuario "admin" associado a um empregado  
    Dado que eu esteja logado com o usuário <Login_Usu>  
  Quando eu acesso o menu "Utilitários > Alterar Senha"
   Então eu preencho "Senha atual" com <SenhaAtual>
       E eu preencho "Nova senha" com <NovaSenha>
       E eu preencho "Confirmação nova senha" com <ConfirmaSenha>
   Então eu clico no botão "Gravar"    
       E eu devo ver <Mensagem>

Exemplos:
  | Login_Usu | SenhaAtual | NovaSenha | ConfirmaSenha | Mensagem                                                  |
  | "admin"   | ""         | "123456"  | "123456"      | "Preencha os campos indicados."                           |
  | "admin"   | "1234"     | ""        | "123456"      | "Preencha os campos indicados."                           |
  | "admin"   | "1234"     | "123456"  | ""            | "Preencha os campos indicados."                           |
  | "admin"   | "1234"     | "123456"  | "123456"      | "Sua senha foi alterada com sucesso."                     |
  | "admin"   | "@1234"    | "123456"  | "123456"      | "A senha informada não confere com a senha do seu login." |
  | "admin"   | "1234"     | "123756"  | "123456"      | "A senha não foi confirmada corretamente."                |