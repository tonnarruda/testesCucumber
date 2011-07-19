# language: pt

Funcionalidade: Cadastrar Empresa

  @dev
  Cenário: Cadastro de Empresa
    Dado que eu esteja logado

    Quando eu acesso o menu "Utilitários > Cadastros > Empresas"
    Então eu devo ver o título "Empresas"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Empresa"
    E eu preencho "nome" com "_Empresa Fortes"
    E eu preencho "Denominação Social" com "Fortes"
    E eu preencho "Endereço" com "Rua Antonio Fortes 2020"
    E eu seleciono "CE" de "Estado"
    E eu seleciono "Fortaleza" de "cidade"
    E eu preencho "Base CNPJ" com "00000000"
    E eu preencho "Atividade" com "Desenvolver Software"
    E eu preencho "CNAE (Classificação Nacional de Atividades Econômicas)" com "Desenv. Software"
    E eu preencho "Grau de Risco" com "1"
    E eu preencho "Email Remetente" com "fortes@fortes.com"
    E eu preencho "Email Resp. Setor Pessoal" com "fortes@fortes.com"
    E eu preencho "Email Resp. RH" com "fortes@fortes.com"
    E eu preencho "Representante Legal" com "fortes"
    E eu preencho "NIT do Representante Legal" com "@fortes"
    E eu preencho "Horário de Trabalho" com "8:00 as 18:00"
    E eu preencho "Máximo de Cargos por Candidato" com "2"
    E eu marco "Exibir valor do salário na Solicitação de Realinhamento e na Solicitação de Pessoal"
    E eu marco "Exibir dados do Ambiente nos Relatórios do SESMT"
    E eu preencho "Texto para email de candidatos não aptos" com "não apto."
    E eu preencho "Mensagem a ser exibida no módulo externo" com "cadastre aqui."
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Empresas"

    Quando eu clico em editar "_Empresa Fortes"
    Então o campo "nome" deve conter "_Empresa Fortes"
    E eu preencho "nome" com "_Fortes"
    Então o campo "Denominação Social" deve conter "Fortes"

    Quando eu clico no botão "Gravar"
    Então eu devo ver "_Fortes"

    Quando eu clico em excluir "_Fortes"
    Então eu devo ver "Confirma exclusão?"
    Quando eu aperto "OK"
    E eu não devo ver "_Fortes"
