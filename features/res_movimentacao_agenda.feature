# language: pt

Funcionalidade: Movimentação Agenda

  Cenário: Movimentação de Agenda
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "R&S > Movimentações > Agenda"
    Então eu devo ver "Seg"
    Então eu devo ver "Ter"
    Então eu devo ver "Qua"
    Então eu devo ver "Qui"
    Então eu devo ver "Sex"
    Então eu devo ver "Sáb"
    Então eu devo ver "Dom"
    Então eu devo ver "7:00"
    Então eu devo ver "19:00"
