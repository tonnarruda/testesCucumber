# language: pt

Funcionalidade: Movimentação Solicitações de Pessoal

  Cenário: Movimentação de Solicitações de Pessoal
    Dado que exista a área organizacional "Financeiro"
    Dado que exista o cargo "Contador"
    Dado que exista a faixa salarial "I" no cargo "Contador"
    Dado que exista um historico para a faixa salarial "I" na data "2011-06-01"
    Dado que exista o motivo da solicitacao "Aumento de quadro"

    Dado que eu esteja logado
    Quando eu acesso o menu "R&S > Movimentações > Solicitação de Pessoal"
    Então eu devo ver o título "Solicitação de Pessoal"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Solicitação de Pessoal"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Solicitação de Pessoal"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Solicitação de Pessoal"
    E eu preencho o campo (JS) "Data" com "10/06/2011"
    E eu preencho "Descrição" com "Vaga java"
    E eu preencho "Horário comercial" com "8h às 18h"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu seleciono "Financeiro" de "Área Organizacional"
    E eu seleciono "Contador I" de "Cargo/Faixa"
    E eu seleciono "Aumento de quadro" de "Motivo da Solicitação"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Solicitação de Pessoal"
    E eu devo ver "Vaga java"

    Entao eu clico em editar "Vaga java"
    E eu devo ver o título "Editar Solicitação de Pessoal"
    E o campo "Descrição" deve conter "Vaga java"
    E eu preencho "Descrição" com "Vaga java EE"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Solicitação de Pessoal"

    Então eu clico em excluir "Vaga java EE"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Solicitação excluída com sucesso."
    E eu não devo ver "Vaga java EE"