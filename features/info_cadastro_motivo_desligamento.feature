# language: pt

Funcionalidade: Motivos de Desligamento

  Cenário: Cadastro de Motivos de Desligamento
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Motivos de Desligamento"
    Então eu devo ver o título "Motivos de Desligamento"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Motivo de Desligamento"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Motivos de Desligamento"
    E eu clico no botão "Inserir"
    E eu preencho "Motivo" com "muitas faltas"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Motivos de Desligamento"
    Então eu devo ver "muitas faltas"

    Entao eu clico em editar "muitas faltas"
    E eu devo ver o título "Editar Motivo de Desligamento"
    E o campo "Motivo" deve conter "muitas faltas"
    E eu preencho "Motivo" com "gritou com o colega"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Motivos de Desligamento"

    Então eu clico em excluir "gritou com o colega"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Motivo de desligamento excluído com sucesso."
    E eu não devo ver "gritou com o colega"












