# language: pt

Funcionalidade: Etapas do Processo Eleitoral

  Cenário: Cadastro de Etapas do Processo Eleitoral
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > CIPA > Etapas do Processo Eleitoral"
    Então eu devo ver o título "CIPA - Etapas do Processo Eleitoral"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Etapa do Processo Eleitoral"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "CIPA - Etapas do Processo Eleitoral"

    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Etapa do Processo Eleitoral"
    E eu preencho "Nome" com "primeira etapa"
    E eu preencho "_prazo" com "232"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "CIPA - Etapas do Processo Eleitoral"
    E eu devo ver "primeira etapa"

    E eu clico em editar "primeira etapa"
    Então eu devo ver o título "Editar Etapa do Processo Eleitoral"
    E o campo "Nome" deve conter "primeira etapa"
    E eu preencho "Nome" com "segunda etapa"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "CIPA - Etapas do Processo Eleitoral"
    E eu devo ver "segunda etapa"

    E eu clico em excluir "segunda etapa"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver o título "CIPA - Etapas do Processo Eleitoral"
    E eu não devo ver "segunda etapa"