# language: pt

Funcionalidade: Cadastrar Nível de Competência

  Cenário: Cadastro de Nível de Competência
    Dado que eu esteja logado com o usuário "fortes"

    Quando eu acesso o menu "C&S > Cadastros > Níveis de Competência > Cadastros"
    Então eu devo ver o título "Níveis de Competência"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Nível de Competência"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Níveis de Competência"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Nível de Competência"
    E eu preencho "Descrição" com "nivel1"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Níveis de Competência"
    E eu devo ver "nivel1"

    Entao eu clico em editar "nivel1"
    E eu devo ver o título "Editar Nível de Competência"
    E o campo "Descrição" deve conter "nivel1"
    E eu preencho "Descrição" com "nivel01"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Níveis de Competência"

    Então eu clico em excluir "nivel01"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Nível de competência excluído com sucesso"
    E eu não devo ver "nivel01"

    Então eu clico no botão "Inserir"
        E eu preencho "Descrição" com "nivel1"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Descrição" com "nivel2"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Descrição" com "nivel3"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Descrição" com "nivel4"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Descrição" com "nivel5"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Descrição" com "nivel6"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Descrição" com "nivel7"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Descrição" com "nivel8"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Descrição" com "nivel9"
    E eu clico no botão "Gravar"

    Então eu clico no botão "Inserir"
    E eu preencho "Descrição" com "nivel10"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Níveis de Competência"

    Então eu clico no botão "Inserir"
    E eu devo ver "Não é permitido cadastrar mais que 10(dez) níveis de competência."

    Entao eu acesso o menu "C&S > Cadastros > Níveis de Competência > Historicos"
    E eu devo ver o título "Históricos dos Níveis de Competência"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Histórico do Nível de Competência"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Níveis de Competência"

    Então eu clico no botão "Inserir"
    E eu preencho campo pelo id "peso_0" com "1"
    E eu preencho campo pelo id "peso_1" com "10"
    E eu preencho campo pelo id "peso_2" com "2"
    E eu preencho campo pelo id "peso_3" com "3"
    E eu preencho campo pelo id "peso_4" com "4"
    E eu preencho campo pelo id "peso_5" com "5"
    E eu preencho campo pelo id "peso_6" com "6"
    E eu preencho campo pelo id "peso_7" com "7"
    E eu preencho campo pelo id "peso_8" com "8"
    E eu preencho campo pelo id "peso_9" com "9"
    E eu clico no botão "Gravar"
    E eu devo ver "Histórico de níveis de competência salvo com sucesso."

    Então eu devo ver o título "Níveis de Competência"
    Entao eu clico na imagem com o título "Excluir"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Histórico dos níveis de competência excluído com sucesso."

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Histórico do Nível de Competência"
    E eu marco o checkbox com name ""
    E eu desmarco o checkbox com id "configHistoricoNivel_0"
    E eu desmarco o checkbox com id "configHistoricoNivel_1"
    E eu desmarco o checkbox com id "configHistoricoNivel_5"
    E eu desmarco o checkbox com id "configHistoricoNivel_7"
    E eu preencho campo pelo id "peso_2" com "2"
    E eu preencho campo pelo id "peso_3" com "3"
    E eu preencho campo pelo id "peso_4" com "4"
    E eu preencho campo pelo id "peso_6" com "6"
    E eu preencho campo pelo id "peso_8" com "8"
    E eu preencho campo pelo id "peso_9" com "9"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Níveis de Competência"
    E eu devo ver "Histórico de níveis de competência salvo com sucesso."

    Entao eu clico na imagem com o título "Editar"
    E eu devo ver o título "Editar Histórico do Nível de Competência"
    E eu não devo ver "nivel1"
    E eu devo ver "nivel2"
    E eu devo ver "nivel3"
    E eu devo ver "nivel4"
    E eu não devo ver "nivel5"
    E eu devo ver "nivel6"
    E eu não devo ver "nivel7"
    E eu devo ver "nivel8"
    E eu devo ver "nivel9"
    E eu não devo ver "nivel10"
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Níveis de Competência"
    Entao eu clico na imagem com o título "Excluir"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Histórico dos níveis de competência excluído com sucesso."






