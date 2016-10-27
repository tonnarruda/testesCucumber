# language: pt

Funcionalidade: Histórico de Versões

  Cenário: Histórico de Versões
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Utilitários > Histórico de Versões"
    Então eu devo ver o título "Histórico de Versões"
    E eu devo ver "1.0.0.0"
    E eu devo ver "Liberação da versão comercial."