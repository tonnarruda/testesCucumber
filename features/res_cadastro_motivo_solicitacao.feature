# language: pt

Funcionalidade: Cadastrar Motivos de Solicitação

  Cenário: Cadastro de Motivos de Solicitação
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "R&S > Cadastros > Motivos de Solicitação de Pessoal"
    Então eu devo ver o título "Motivos de Solicitação"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Motivo de Solicitação"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Motivos de Solicitação"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Motivo de Solicitação"
    E eu preencho "Descrição" com "falta de mão de obra"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Motivos de Solicitação"
    E eu devo ver "falta de mão de obra"

    Entao eu clico em editar "falta de mão de obra"
    E eu devo ver o título "Editar Motivo de Solicitação"
    E o campo "Descrição" deve conter "falta de mão de obra"
    E eu preencho "Descrição" com "falta de mão de obra 2"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Motivos de Solicitação"

    Então eu clico em excluir "falta de mão de obra 2"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Motivo de solicitação excluído com sucesso."
    E eu não devo ver "falta de mão de obra 2"