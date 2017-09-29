# language: pt

Funcionalidade: Gerenciamento de EPIs

  Cenário: Solicitação de EPIs SEM marcar nenhum EPI
     Dado que exista o EPI "Bota" da categoria "Proteção_Membro_Inferior"
     Dado que exista o EPI "Luvas" da categoria "Proteção_Membro_Superior"
     Dado que exista o EPI "Capacete" da categoria "Proteção da Cabeça"
     Dado que exista um colaborador "David Cameron", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Davi"
         E eu clico no botão "Pesquisar"
         E eu seleciono "David Cameron - 123.213.623-91" de "Colaborador"
         E eu clico no botão "Gravar"
     Então eu devo ver o alert "Selecione pelo menos um EPI." e clico no ok
         E eu clico no botão "Voltar"
     Então eu devo ver o título "Gerenciamento de EPIs"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Solicitação/Entrega de vários EPIs
     Dado que exista o EPI "Bota" da categoria "Proteção_Membro_Inferior"
     Dado que exista o EPI "Luvas" da categoria "Proteção_Membro_Superior"
     Dado que exista o EPI "Capacete" da categoria "Proteção da Cabeça"
     Dado que exista um colaborador "David Cameron", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Davi"
         E eu clico no botão "Pesquisar"
         E eu seleciono "David Cameron - 123.213.623-91" de "Colaborador"
         E eu marco "md"
         E eu seleciono "Sim" de "Entregues"
         E eu clico no botão "Gravar"
     Então eu devo ver o título "Gerenciamento de EPIs"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Solicitação de vários EPIs NÃO entregues
     Dado que exista o EPI "Bota" da categoria "Proteção_Membro_Inferior"
     Dado que exista o EPI "Luvas" da categoria "Proteção_Membro_Superior"
     Dado que exista o EPI "Capacete" da categoria "Proteção da Cabeça"
     Dado que exista um colaborador "David Cameron", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Davi"
         E eu clico no botão "Pesquisar"
         E eu seleciono "David Cameron - 123.213.623-91" de "Colaborador"
         E eu marco "md"
         E eu clico no botão "Gravar"
     Então eu devo ver o título "Gerenciamento de EPIs"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Entrega de EPI Solicitado
     Dado que exista o EPI "Bota" da categoria "Proteção_Membro_Inferior"
     Dado que exista o EPI "Luvas" da categoria "Proteção_Membro_Superior"
     Dado que exista o EPI "Capacete" da categoria "Proteção da Cabeça"
     Dado que exista um colaborador "David Cameron", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Davi"
         E eu clico no botão "Pesquisar"
         E eu seleciono "David Cameron - 123.213.623-91" de "Colaborador"
         E eu marco "md"
         E eu preencho o campo (JS) "Data" com "01/05/2017"
         E eu clico no botão "Gravar"
     Então eu devo ver o título "Gerenciamento de EPIs"
         E eu clico na linha "David Cameron" da imagem "Entregar/Devolver"
         E eu clico na imagem com o título "Inserir entrega"
         E eu preencho o campo (JS) "dataEntrega" com "11/05/2017"
         E eu preencho "Qtd. Entregue" com "1"
         E eu seleciono "01/02/2011 - a0a1a2a3 - 30" de "epiHistoricoId"
         E eu clico no botão "Gravar"
         E eu devo ver o título "Entrega/Devolução de EPIs"

#------------------------------------------------------------------------------------------------------------------------
@teste
  Cenário: Entrega de EPI Solicitado com quantidade superior a solicitada
     Dado que exista o EPI "Bota" da categoria "Proteção_Membro_Inferior"
     Dado que exista o EPI "Luvas" da categoria "Proteção_Membro_Superior"
     Dado que exista o EPI "Capacete" da categoria "Proteção da Cabeça"
     Dado que exista um colaborador "David Cameron", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Davi"
         E eu clico no botão "Pesquisar"
         E eu seleciono "David Cameron - 123.213.623-91" de "Colaborador"
         E eu marco "md"
         E eu preencho o campo (JS) "Data" com "01/05/2017"
         E eu clico no botão "Gravar"
     Então eu devo ver o título "Gerenciamento de EPIs"
         E eu clico na linha "David Cameron" da imagem "Entregar/Devolver"
         E eu clico na imagem com o título "Inserir entrega"
         E eu preencho "Qtd. Entregue" com "90"
         E eu preencho o campo (JS) "dataEntrega" com "11/05/2017"
         E eu seleciono "01/02/2011 - a0a1a2a3 - 30" de "epiHistoricoId"
         E eu clico no botão "Gravar"
         E eu devo ver "O total de itens entregues não pode ser superior à quantidade solicitada"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Entrega de EPI Solicitado com data anterior a solicitação
     Dado que exista o EPI "Bota" da categoria "Proteção_Membro_Inferior"
     Dado que exista o EPI "Luvas" da categoria "Proteção_Membro_Superior"
     Dado que exista o EPI "Capacete" da categoria "Proteção da Cabeça"
     Dado que exista um colaborador "David Cameron", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Davi"
         E eu clico no botão "Pesquisar"
         E eu seleciono "David Cameron - 123.213.623-91" de "Colaborador"
         E eu marco "md"
         E eu preencho o campo (JS) "Data" com "01/05/2017"
         E eu clico no botão "Gravar"
     Então eu devo ver o título "Gerenciamento de EPIs"
         E eu clico na linha "David Cameron" da imagem "Entregar/Devolver"
         E eu clico na imagem com o título "Inserir entrega"
         E eu preencho "Qtd. Entregue" com "1"
         E eu preencho o campo (JS) "dataEntrega" com "01/04/2015"
         E eu seleciono "01/02/2011 - a0a1a2a3 - 30" de "epiHistoricoId"
         E eu clico no botão "Gravar"
         E eu devo ver "A data de entrega não pode ser anterior à data de solicitação"

#------------------------------------------------------------------------------------------------------------------------
  
  Cenário: Devolução de EPI Solicitado
     Dado que exista o EPI "Bota" da categoria "Proteção_Membro_Inferior"
     Dado que exista o EPI "Luvas" da categoria "Proteção_Membro_Superior"
     Dado que exista o EPI "Capacete" da categoria "Proteção da Cabeça"
     Dado que exista um colaborador "David Cameron", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Davi"
         E eu clico no botão "Pesquisar"
         E eu seleciono "David Cameron - 123.213.623-91" de "Colaborador"
         E eu marco "md"
         E eu preencho o campo (JS) "Data" com "01/05/2017"
         E eu clico no botão "Gravar"
     Então eu devo ver o título "Gerenciamento de EPIs"
         E eu clico na linha "David Cameron" da imagem "Entregar/Devolver"
         E eu clico na imagem com o título "Inserir entrega"
         E eu preencho "Qtd. Entregue" com "1"
         E eu preencho o campo (JS) "dataEntrega" com "11/05/2017"
         E eu seleciono "01/02/2011 - a0a1a2a3 - 30" de "epiHistoricoId"
         E eu clico no botão "Gravar"
         E eu clico na imagem com o título "Inserir Devolução"
         E eu preencho "Qtd. Devolvida" com "1"
         E eu preencho o campo (JS) "dataDevolucao" com "13/05/2017"
         E eu preencho "Observação" com "Devolução de EPI do Empregado David Cameron"
         E eu clico no botão "Gravar"
         E eu devo ver o título "Entrega/Devolução de EPIs"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Devolução de EPI Solicitado com quantidade superior a solicitada
     Dado que exista o EPI "Bota" da categoria "Proteção_Membro_Inferior"
     Dado que exista o EPI "Luvas" da categoria "Proteção_Membro_Superior"
     Dado que exista o EPI "Capacete" da categoria "Proteção da Cabeça"
     Dado que exista um colaborador "David Cameron", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Davi"
         E eu clico no botão "Pesquisar"
         E eu seleciono "David Cameron - 123.213.623-91" de "Colaborador"
         E eu marco "md"
         E eu preencho o campo (JS) "Data" com "01/05/2017"
         E eu clico no botão "Gravar"
     Então eu devo ver o título "Gerenciamento de EPIs"
         E eu clico na linha "David Cameron" da imagem "Entregar/Devolver"
         E eu clico na imagem com o título "Inserir entrega"
         E eu preencho o campo (JS) "dataEntrega" com "11/05/2017"
         E eu preencho "Qtd. Entregue" com "1"
         E eu seleciono "01/02/2011 - a0a1a2a3 - 30" de "epiHistoricoId"
         E eu clico no botão "Gravar"
         E eu clico na imagem com o título "Inserir Devolução"
         E eu preencho o campo (JS) "dataDevolucao" com "13/05/2017"
         E eu preencho "Qtd. Devolvida" com "90"
         E eu saio do campo "Qtd. Devolvida"
         E eu devo ver "Não é possível inserir uma devolução nessa data"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Devolução de EPI Solicitado com data Anterior a data de solicitação
     Dado que exista o EPI "Bota" da categoria "Proteção_Membro_Inferior"
     Dado que exista o EPI "Luvas" da categoria "Proteção_Membro_Superior"
     Dado que exista o EPI "Capacete" da categoria "Proteção da Cabeça"
     Dado que exista um colaborador "David Cameron", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Davi"
         E eu clico no botão "Pesquisar"
         E eu seleciono "David Cameron - 123.213.623-91" de "Colaborador"
         E eu marco "md"
         E eu preencho o campo (JS) "Data" com "01/05/2017"
         E eu clico no botão "Gravar"
     Então eu devo ver o título "Gerenciamento de EPIs"
         E eu clico na linha "David Cameron" da imagem "Entregar/Devolver"
         E eu clico na imagem com o título "Inserir entrega"
         E eu preencho "Qtd. Entregue" com "1"
         E eu preencho o campo (JS) "dataEntrega" com "11/05/2017"
         E eu seleciono "01/02/2011 - a0a1a2a3 - 30" de "epiHistoricoId"
         E eu clico no botão "Gravar"
         E eu clico na imagem com o título "Inserir Devolução"
         E eu preencho o campo (JS) "dataDevolucao" com "01/01/2000"
         E eu saio do campo "dataDevolucao"
         E eu devo ver "A data de devolução não pode ser anterior à data de solicitação"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Exclusão de Devolução e exclusão de entrega de EPI
     Dado que exista o EPI "Bota" da categoria "Proteção_Membro_Inferior"
     Dado que exista o EPI "Luvas" da categoria "Proteção_Membro_Superior"
     Dado que exista o EPI "Capacete" da categoria "Proteção da Cabeça"
     Dado que exista um colaborador "David Cameron", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Davi"
         E eu clico no botão "Pesquisar"
         E eu seleciono "David Cameron - 123.213.623-91" de "Colaborador"
         E eu marco "md"
         E eu preencho o campo (JS) "Data" com "01/05/2017"
         E eu clico no botão "Gravar"
     Então eu devo ver o título "Gerenciamento de EPIs"
         E eu clico na linha "David Cameron" da imagem "Entregar/Devolver"
         E eu clico na imagem com o título "Inserir entrega"
         E eu preencho o campo (JS) "dataEntrega" com "11/05/2017"
         E eu preencho "Qtd. Entregue" com "1"
         E eu seleciono "01/02/2011 - a0a1a2a3 - 30" de "epiHistoricoId"
         E eu clico no botão "Gravar"
         E eu clico na imagem com o título "Inserir Devolução"
         E eu preencho "Qtd. Devolvida" com "1"
         E eu preencho o campo (JS) "dataDevolucao" com "13/05/2017"
         E eu preencho "Observação" com "Devolução de EPI do Empregado David Cameron"
         E eu clico no botão "Gravar"
     Então eu clico em excluir "13/05/2017"
         E eu devo ver o alert "Confirma exclusão?" e clico no ok
         E eu devo ver "Devolução do EPI excluída com sucesso"
     Então eu clico em excluir "11/05/2017"
         E eu devo ver o alert "Confirma exclusão?" e clico no ok
         E eu devo ver "Entrega de EPI excluída com sucesso"
         E eu clico no botão "Voltar"

#------------------------------------------------------------------------------------------------------------------------
@testes
  Cenário: Exclusão da Solicitação de EPI
     Dado que exista o EPI "Bota" da categoria "Proteção_Membro_Inferior"
     Dado que exista o EPI "Luvas" da categoria "Proteção_Membro_Superior"
     Dado que exista o EPI "Capacete" da categoria "Proteção da Cabeça"
     Dado que exista um colaborador "David Cameron", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Davi"
         E eu clico no botão "Pesquisar"
         E eu seleciono "David Cameron - 123.213.623-91" de "Colaborador"
         E eu marco "md"
         E eu preencho o campo (JS) "Data" com "01/05/2017"
         E eu clico no botão "Gravar"
     Então eu devo ver o título "Gerenciamento de EPIs"
         E eu clico na linha "David Cameron" da imagem "Entregar/Devolver"
         E eu clico na imagem com o título "Inserir entrega"
         E eu preencho "Qtd. Entregue" com "1"
         E eu preencho o campo (JS) "dataEntrega" com "11/05/2017"
         E eu seleciono "01/02/2011 - a0a1a2a3 - 30" de "epiHistoricoId"
         E eu clico no botão "Gravar"
         E eu clico na imagem com o título "Inserir Devolução"
         E eu preencho "Qtd. Devolvida" com "1"
         E eu preencho o campo (JS) "dataDevolucao" com "13/05/2017"
         E eu preencho "Observação" com "Devolução de EPI do Empregado David Cameron"
         E eu clico no botão "Gravar"
     Então eu clico em excluir "13/05/2017"
         E eu devo ver o alert "Confirma exclusão?" e clico no ok
         E eu devo ver "Devolução do EPI excluída com sucesso"
     Então eu clico em excluir "11/05/2017"
         E eu devo ver o alert "Confirma exclusão?" e clico no ok
         E eu devo ver "Entrega de EPI excluída com sucesso"
         E eu clico no botão "Voltar"
     Então eu clico em excluir "David Cameron"
         E eu devo ver o alert "Confirma exclusão?" e clico no ok
         E eu devo ver "Gerenciamento de EPIs excluído com sucesso."

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Imprimir Solicitação de EPI
     Dado que exista o EPI "Bota" da categoria "Proteção_Membro_Inferior"
     Dado que exista o EPI "Luvas" da categoria "Proteção_Membro_Superior"
     Dado que exista o EPI "Capacete" da categoria "Proteção da Cabeça"
     Dado que exista um colaborador "David Cameron", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"

     Então eu acesso o menu "SESMT > Movimentações > Gerenciamento de EPIs"
         E eu clico no botão "Inserir"
     Então eu preencho "Nome" com "Davi"
         E eu clico no botão "Pesquisar"
         E eu seleciono "David Cameron - 123.213.623-91" de "Colaborador"
         E eu marco "md"
         E eu seleciono "Sim" de "Entregues"
         E eu clico no botão "Gravar"
     Então eu devo ver o título "Gerenciamento de EPIs"
         E eu clico no botão "Imprimir"