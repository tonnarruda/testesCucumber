# language: pt

Funcionalidade: Solicitações/Atendimentos Médicos

  Cenário: Cadastro de Solicitações/Atendimentos Médicos
    Dado que exista um colaborador "geraldo", da area "administracao", com o cargo "desenvolvedor" e a faixa salarial "I"
    Dado que exista um medico coordenador "alfredo"
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "SESMT > Movimentações > Solicitações/Atendimentos Médicos"
    Então eu devo ver o título "Solicitações/Atendimentos Médicos"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Solicitação/Atendimento Médico"
    E eu seleciono "Colaborador" de "Exames para"
    E eu clico no botão "Pesquisar"
    E eu seleciono "geraldo - 123.213.623-91" de "Colaborador"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok

    E eu seleciono "geraldo - 123.213.623-91" de "Colaborador"
    E eu preencho o campo (JS) "data" com "01/01/1999"
    E eu saio do campo "data"
    E eu devo ver o alert "Data solicitação não pode ser menor que a data de admissão." e clico no ok
    E eu preencho o campo (JS) "data" com "28/07/2011"
    E eu seleciono "ASO Admissional" de "Motivo do Atendimento"
    E eu seleciono "alfredo" de "Médico Coordenador"
    E eu preencho "Observação" com "urgente"
    E eu marco "examesId"
    E eu seleciono "Nenhuma clínica" de "selectClinicas"
    E eu preencho "periodicidades" com "9"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Solicitações/Atendimentos Médicos"

    Então eu clico na linha "28/07/2011" da imagem "Resultados"
    E eu devo ver o título "Resultados dos Exames de geraldo (geraldo)"
    E eu devo ver "Avaliação Clínica e Anamnese Ocupacional"
    E eu preencho o campo (JS) "datasRealizacaoExames" com "01/08/2011"
    E eu seleciono "Não Realizado" de "selectResultados"
    E eu preencho "observacoes" com "testes"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Resultados dos Exames de geraldo (geraldo)"
    E eu devo ver "Resultados gravados com sucesso."
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Solicitações/Atendimentos Médicos"

    Então eu clico na linha "28/07/2011" da imagem "Marcar o resultado de todos os exames não informados como normal"
    E eu devo ver o alert "Marcar o resultado de todos os exames não informados como normal?" e clico no ok

    Entao eu clico em editar "28/07/2011"
    E eu devo ver o título "Editar Solicitação/Atendimento Médico"
    E eu devo ver "Atenção: Este atendimento já possui resultados gravados. A edição implicará na perda dos resultados."
    E o campo "data" deve conter "28/07/2011"
    E eu preencho o campo (JS) "data" com "01/01/1999"
    E eu saio do campo "data"
    E eu preencho o campo (JS) "data" com "25/07/2011"
    E eu preencho "periodicidades" com "6"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Solicitações/Atendimentos Médicos"

    Entao eu clico na linha "25/07/2011" da imagem "Imprimir ASO"
    E eu seleciono "NR35" de "formConsiderarRiscoPor_utilizarAsoNR35"
  

    Então eu clico em excluir "25/07/2011"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Solicitação/Atendimento Médico excluído com sucesso."
    E eu não devo ver "25/07/2011"

