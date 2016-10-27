# language: pt

Funcionalidade: Enviar Mensagem

  Cenário: Enviar Mensagem
    Dado que exista um usuario "joao"
    
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Utilitários > Enviar Mensagem"
    Então eu devo ver o título "Enviar Mensagem"

    Então eu marco "joao"
    E eu preencho "Mensagem" com "aviso"
    E eu clico no botão "Enviar"
    
    Então eu devo ver "Mensagem enviada com sucesso"
    Então eu devo ver o título "Enviar Mensagem"
