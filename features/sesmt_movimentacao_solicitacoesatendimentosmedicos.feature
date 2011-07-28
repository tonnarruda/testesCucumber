# language: pt

Funcionalidade: Solicitações/Atendimentos Médicos

  Cenário: Cadastro de Solicitações/Atendimentos Médicos
    Dado que exista um colaborador "geraldo", com o cargo "desenvolvedor" e a faixa salarial "I"
    Dado que exista um medico coordenador "alfredo"
    Dado que eu esteja logado

    Quando eu acesso o menu "SESMT > Movimentações > Solicitações/Atendimentos Médicos"
    Então eu devo ver o título "Solicitações/Atendimentos Médicos"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Solicitação/Atendimento Médico"
    E eu seleciono "Colaborador" de "Exames para"
    E eu clico no botão "Pesquisar"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok

    E eu seleciono "geraldo" de "Colaborador"
    E eu preencho o campo (JS) "data" com "28/07/2011"
    E eu seleciono "Consulta comum" de "Motivo do Atendimento"
    E eu seleciono "alfredo" de "Médico Coordenador"
    E eu preencho "Observação" com "urgente"
    E eu marco "examesId"
    E eu seleciono "Nenhuma clínica" de "selectClinicas"
    E eu preencho "periodicidades" com "9"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Solicitações/Atendimentos Médicos"

    Entao eu clico em editar "28/07/2011"
    E eu devo ver o título "Editar Solicitação/Atendimento Médico"
    E o campo "data" deve conter "28/07/2011"
    E eu preencho o campo (JS) "data" com "25/07/2011"
    E eu preencho "periodicidades" com "6"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Solicitações/Atendimentos Médicos"

    Então eu clico em excluir "25/07/2011"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Solicitação/Atendimento Médico excluído com sucesso."
    E eu não devo ver "25/07/2011"

