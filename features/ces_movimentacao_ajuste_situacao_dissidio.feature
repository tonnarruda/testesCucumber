# language: pt

Funcionalidade: Promoção para Dissídio

  Cenário: Alteração de Promoção para Dissídio
    Dado que exista um colaborador "Davi", da area "Desenvolvimento", com o cargo "Diretor do Nada" e a faixa salarial "I"
    Dado que exista um novo historico para o colaborador "Davi", na area "Desenvolvimento", na faixa salarial "I"
    Dado que eu esteja logado com o usuário "SOS"
    
    Quando eu acesso o menu "C&S > Movimentações > Alteração de Promoção para Dissídio"

    Então eu devo ver o título "Alteração de Promoção para Dissídio"
    E eu preencho o campo (JS) "dataIni" com "14/08/2012"
    E eu preencho o campo (JS) "percentualDissidio" com "5"
    E eu marco "Estabelecimento Padrão"
    E eu clico no botão "Pesquisar"

    E eu devo ver "Promoção"
    E eu clico no botão "Gravar"
    E eu devo ver o alert "Nenhuma Situação modificada nessa página." e clico no ok

    Então eu marco "marcarTodos"
    E eu clico no botão "Gravar"
    E eu devo ver o alert "Página gravada com sucesso." e clico no ok
    E eu devo ver "Dissídio"

