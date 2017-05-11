# language: pt

Funcionalidade: Excluir Cadastro de EPI

  Cenario: Exclusão de Cadastro de EPI sem vínculo com Colaborador
    Dado que exista o EPI "EPI Proteção" da categoria "Proteção"
    Dado que eu esteja logado com o usuário "SOS"
       E eu acesso o menu "SESMT > Cadastros > EPI"
       E eu clico em excluir "EPI Proteção"
   Então eu devo ver o alert do confirmar exclusão e clico no ok
       E eu devo ver "EPI excluído com sucesso."



  Cenario: Exclusão de Cadastro de EPI com vínculo de Colaborador
    Dado que exista o EPI "EPI Proteção" da categoria "Proteção"
    Dado que exista um colaborador "geraldo", da area "administracao", com o cargo "quimico" e a faixa salarial "I"
    Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Gera"
         E eu clico no botão "Pesquisar"
         E eu seleciono "geraldo - 123.213.623-91" de "Colaborador"
         E eu preencho o campo (JS) "data" com "11/05/2017"
         E eu marco "epiIds"
         E eu preencho o campo (JS) "selectQtdSolicitado" com "2"
            #O teste nao esta passando por esta linha, o valor salvo é 1
         E eu seleciono "Sim" de "Entregues"
         E eu clico no botão "Gravar"
     Entao eu acesso o menu "SESMT > Cadastros > EPI"
         E eu clico em excluir "EPI Proteção"
     Então eu devo ver o alert do confirmar exclusão e clico no ok
         E eu devo ver "Erro ao remover EPI."
             #Ao tentar excluir o EPI vinculado ao colaborador o sistema esta permitindo a exclusao

