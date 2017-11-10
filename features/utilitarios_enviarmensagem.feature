# language: pt

Funcionalidade: Enviar Mensagem

  Esquema do Cenario: Enviar Mensagem    
    Dado que exista um usuario "admin"
    Dado que eu esteja logado com o usuário "SOS"
  Quando eu acesso o menu "Utilitários > Enviar Mensagem"
     Então eu marco <Usuario>
         E eu preencho "Mensagem" com <Mensagem>
         E eu clico no botão "Enviar"
     Então eu devo ver <Alerta>    

  Exemplos:
    | Usuario | Mensagem | Alerta                         |
    | "admin" | "aviso"  | "Mensagem enviada com sucesso" |