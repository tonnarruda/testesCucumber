# language: pt

Funcionalidade: Relatório de PPP

  Cenário: Relatório de PPP
    Dado que exista um colaborador "geraldo", da area "desenvolvimento", com o cargo "desenvolvedor" e a faixa salarial "I"

    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Relatórios > PPP"
    Então eu devo ver o título "Lista de Colaboradores"
    E eu clico "Exibir Filtro"
    E eu preencho "Nome Comercial" com "Gera"
    E eu clico no botão "Pesquisar"

    Então eu devo ver o título "Lista de Colaboradores"
    E eu devo ver "geraldo"
    
    Então eu clico na linha "geraldo" da imagem "Gerar PPP"
    E eu devo ver o título "PPP do colaborador - geraldo"
    E eu clico no botão "Relatorio"
    Então eu devo ver o alert do valida campos e clico no ok

    Então eu preencho "NIT do Representante Legal" com "156984"
    E eu preencho "Representante Legal" com "gouveia"
    E eu preencho "Observações" com "recem contratado"
    Então eu clico no botão "Voltar"
    E eu devo ver o título "Lista de Colaboradores"