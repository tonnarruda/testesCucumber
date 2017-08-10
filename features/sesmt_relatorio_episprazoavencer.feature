# language: pt

Funcionalidade: Relatório de EPIs com Prazo a Vencer

  Cenário: Relatório de EPIs com Prazo a Vencer
    Dado que exista o EPI "jaleco" da categoria "vestuario"
    Dado que exista o EPI "oculos" da categoria "visao"
    Dado que exista a área organizacional "ambulatorio"

    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Relatórios > EPI > EPIs com Prazo a Vencer"
    Então eu devo ver o título "EPIs com Prazo a Vencer"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok

    Então eu preencho o campo (JS) "data" com "29/07/2011"
    E eu seleciono "Colaborador" de "Agrupar por"
    E eu marco "vestuario"
    E eu marco "visao"
    E eu marco "Estabelecimento Padrão"
    E eu marco "ambulatorio"
    E eu marco "Exibir Vencimento do CA"

    Então eu clico no botão "Relatorio"
    E eu devo ver "Não existem EPIs com prazo a vencer para os filtros informados."


#REFATORAR

# Nova regra para considerar o vencimento do EPI somente se o mesmo não tiver sido devolvido.    