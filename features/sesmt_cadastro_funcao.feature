# language: pt

Funcionalidade: Funções

  Esquema do Cenario: Cadastro de Funções  
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Funções"
         E eu clico no botão novo "Inserir"
         E eu devo ver "Ajuda eSocial"
         E eu clico no botão do dialog "Fechar"
     Então eu preencho o campo (JS) "funcaoNome" com <Funcao>
         E eu preencho o campo (JS) "dataHist" com <Data>
         E eu preencho o campo (JS) "codigoCBO" com <CBO>
         E eu preencho o campo (JS) "descricao" com <Descricao>
         E eu clico no botão novo <Botao>
         E eu clico no botão novo "Gravar"
     Então eu devo ver <Mensagem>


  Exemplos:
    | Funcao          | Data         | CBO   | Descricao                          | Botao      | Mensagem                                       |
    | ""              | ""           | ""    | ""                                 | "Cancelar" | "Funções"                                      |
    | ""              | ""           | ""    | ""                                 | "Gravar"   | "Preencha os campos indicados."                | 
    | "Product Owner" | ""           | ""    | ""                                 | "Gravar"   | "Preencha os campos indicados."                | 
    | "Product Owner" | "01/10/2016" | ""    | ""                                 | "Gravar"   | "Preencha os campos indicados."                | 
    | "Product Owner" | "01/10/2016" | "123" | ""                                 | "Gravar"   | "Preencha os campos indicados."                | 
    | "Product Owner" | "01/10/2016" | "123" | "Gerir o produto baseado no SCRUM" | "Gravar"   | "Função Product Owner cadastrada com sucesso." |     

#-----------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Edição do Cadastro de Histórico de Funções   
      Dado que exista uma funcao "Eletricista"
      Dado que exista um histórico para a funcao "Eletricista" 
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Funções"
     Entao eu clico na linha "Eletricista" da imagem nova "Históricos"
         E eu clico em editar novo "01/10/2017"
         E eu devo ver "Ajuda eSocial"
         E eu clico no botão do dialog "Fechar"
     Então eu preencho o campo (JS) "funcaoNome" com "Operadores"  
     E eu preencho o campo (JS) "codigoCBO" com "123456"
     E eu preencho o campo (JS) "descricao" com "Diabo é isso"
         E eu clico no botão novo "Gravar" 
     Então eu devo ver "Histórico da função atualizado com sucesso."   

#-----------------------------------------------------------------------------------------------------------------------------------------------

  Esquema do Cenário: Inclusão do Cadastro de Histórico de Funções   
      Dado que exista uma funcao "Eletricista"
      Dado que exista um histórico para a funcao "Eletricista" 
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Funções"
     Entao eu clico na linha "Eletricista" da imagem nova "Históricos"
         E eu clico no botão novo "Inserir"
         E eu devo ver "Ajuda eSocial"
         E eu clico no botão do dialog "Fechar"
     Então eu preencho o campo (JS) "funcaoNome" com <Funcao>
         E eu preencho o campo (JS) "dataHist" com <Data>
         E eu preencho o campo (JS) "codigoCBO" com <CBO>
         E eu preencho o campo (JS) "descricao" com <Descricao>
         E eu clico no botão novo <Botao>
     Então eu devo ver <Mensagem>    
    
  Exemplos:
    | Funcao        | Data         | CBO   | Descricao               | Botao      | Mensagem                                        |
    | "Eletricista" | "01/09/2017" | "123" | "Descrição de Trabalho" | "Cancelar" | "Históricos da Função"                          | 
    | "Eletricista" | "01/10/2017" | "123" | "Descrição de Trabalho" | "Gravar"   | "Já existe um histórico para a data informada." | 
    | "Eletricista" | "01/11/2017" | "123" | "Descrição de Trabalho" | "Gravar"   | "Histórico da função cadastrado com sucesso."   |

#-----------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Exclusão do Cadastro de Histórico de Funções   
      Dado que exista uma funcao "Eletricista"
      Dado que exista um histórico para a funcao "Eletricista" na data "01/10/2017"
      Dado que exista um histórico para a funcao "Eletricista" na data "01/11/2017"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Funções"
     Entao eu clico na linha "Eletricista" da imagem nova "Históricos"
         E eu clico em excluir novo "01/11/2017"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Histórico da função excluído com sucesso."   
     
#-----------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Exclusão do Cadastro de Funções   
      Dado que exista uma funcao "Eletricista"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Funções"
         E eu clico em excluir novo "Eletricista"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Função excluída com sucesso." 


