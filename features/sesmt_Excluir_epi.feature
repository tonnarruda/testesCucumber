# language: pt

Funcionalidade: Excluir Cadastro de EPI

  Cenario: Exclusão de Cadastro de EPI sem vínculo com Colaborador
    Dado que exista o EPI "EPI Proteção" da categoria "Proteção"
    Dado que eu esteja logado com o usuário "SOS"
       E eu acesso o menu "SESMT > Cadastros > EPI"
       E eu clico em excluir "EPI Proteção"
   Então eu devo ver o alert do confirmar exclusão e clico no ok
       E eu devo ver "EPI excluído com sucesso."

#------------------------------------------------------------------------------------------------------------------------

  Cenario: Exclusão de Cadastro de EPI entregue ao Colaborador
    Dado que exista o EPI "EPI Proteção" da categoria "Proteção"
    Dado que exista um colaborador "David Cameron", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "I"
    Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Davi"
         E eu clico no botão "Pesquisar"
         E eu seleciono "David Cameron - 123.213.623-91" de "Colaborador"
         E eu marco "epiIds"
         E eu preencho o campo (JS) "data" com "11/05/2017"
         E eu preencho o campo (JS) "selectQtdSolicitado_1" com "2"
         E eu seleciono "Sim" de "Entregues"
         E eu clico no botão "Gravar"
     Entao eu acesso o menu "SESMT > Cadastros > EPI"
         E eu clico em excluir "EPI Proteção"
     Então eu devo ver o alert do confirmar exclusão e clico no ok
         E eu devo ver "Erro ao remover EPI."

#------------------------------------------------------------------------------------------------------------------------

  Cenario: Exclusão de Cadastro de EPI entregue parcialmente ao Colaborador
    Dado que exista o EPI "EPI Proteção" da categoria "Proteção"
    Dado que exista um colaborador "David Cameron", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "I"
    Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Davi"
         E eu clico no botão "Pesquisar"
         E eu seleciono "David Cameron - 123.213.623-91" de "Colaborador"
         E eu marco "epiIds"
         E eu preencho o campo (JS) "data" com "11/05/2017"
         E eu preencho o campo (JS) "selectQtdSolicitado_1" com "10"
         E eu clico no botão "Gravar"
     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico na linha "David Cameron" da imagem "Entregar/Devolver"
         E eu clico na imagem com o título "Inserir entrega"
         E eu preencho o campo (JS) "dataEntrega" com "11/05/2017"
         E eu preencho "Qtd. Entregue" com "3"
         E eu seleciono "01/02/2011 - a0a1a2a3 - 30" de "epiHistoricoId"
         E eu clico no botão "Gravar"
         E eu clico no botão "Voltar"
    Entao eu acesso o menu "SESMT > Cadastros > EPI"
        E eu clico em excluir "EPI Proteção"
    Então eu devo ver o alert do confirmar exclusão e clico no ok
        E eu devo ver "Erro ao remover EPI."

#------------------------------------------------------------------------------------------------------------------------

  Cenario: Exclusão de Cadastro de EPI não entregue ao Colaborador
    Dado que exista o EPI "EPI Proteção" da categoria "Proteção"
    Dado que exista um colaborador "David Cameron", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "I"
    Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Davi"
         E eu clico no botão "Pesquisar"
         E eu seleciono "David Cameron - 123.213.623-91" de "Colaborador"
         E eu marco "epiIds"
         E eu preencho o campo (JS) "data" com "11/05/2017"
         E eu preencho o campo (JS) "selectQtdSolicitado_1" com "2"
         E eu clico no botão "Gravar"
     Entao eu acesso o menu "SESMT > Cadastros > EPI"
         E eu clico em excluir "EPI Proteção"
     Então eu devo ver o alert do confirmar exclusão e clico no ok
         E eu devo ver "Não foi possível remover o EPI. Existem registros vinculados a ele."