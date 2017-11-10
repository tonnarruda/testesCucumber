# language: pt

Funcionalidade: Funções

  Esquema do Cenario: Cadastro de Funções  
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Funções"
         E eu clico no botão "Inserir"
         E eu devo ver "Ajuda eSocial"
         E eu clico no botão do dialog "Fechar"
     Então eu preencho "Nome da Função" com <Funcao>
         E eu preencho o campo (JS) "A partir de" com <Data>
         E eu preencho "Cód. CBO" com <CBO>
         E eu preencho "Descrição das Atividades Executadas pela Função" com <Descricao>
         E eu clico no botão <Botao>
         E eu clico no botão "Gravar"
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
      Dado que exista uma funcao "Eletricista" no cargo "Gerente de Operações Elétricas" 
      Dado que exista um histórico para a funcao "Eletricista" 
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Funções"
     Entao eu clico na linha "Eletricista" da imagem "Históricos"
         E eu clico em editar "01/10/2017"
     Então eu preencho "Nome da Função" com "Operadores"  
         E eu clico no botão "Gravar" 
     Então eu devo ver "Histórico da função atualizado com sucesso."   

#-----------------------------------------------------------------------------------------------------------------------------------------------

  Esquema do Cenário: Inclusão do Cadastro de Histórico de Funções   
      Dado que exista uma funcao "Eletricista" no cargo "Gerente de Operações Elétricas" 
      Dado que exista um histórico para a funcao "Eletricista" 
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Funções"
     Entao eu clico na linha "Eletricista" da imagem "Históricos"
         E eu clico no botão "Inserir"
         E eu devo ver "Ajuda eSocial"
         E eu clico no botão do dialog "Fechar"
     Então eu preencho "Nome da Função" com <Funcao>
         E eu preencho o campo (JS) "A partir de" com <Data>
         E eu preencho "Cód. CBO" com <CBO>
         E eu preencho "Descrição das Atividades Executadas pela Função" com <Descricao>
         E eu clico no botão <Botao>
     Então eu devo ver <Mensagem>    
    
  Exemplos:
    | Funcao        | Data         | CBO   | Descricao               | Botao      | Mensagem                                        |
    | "Eletricista" | "01/09/2017" | "123" | "Descrição de Trabalho" | "Cancelar" | "Históricos da Função"                          | 
    | "Eletricista" | "01/10/2017" | "123" | "Descrição de Trabalho" | "Gravar"   | "Já existe um histórico para a data informada." | 
    | "Eletricista" | "01/11/2017" | "123" | "Descrição de Trabalho" | "Gravar"   | "Histórico da função cadastrado com sucesso."   |

#-----------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Exclusão do Cadastro de Histórico de Funções   
      Dado que exista uma funcao "Eletricista" no cargo "Gerente de Operações Elétricas" 
      Dado que exista um histórico para a funcao "Eletricista" na data "01/10/2017"
      Dado que exista um histórico para a funcao "Eletricista" na data "01/11/2017"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Funções"
<<<<<<< HEAD
    Então eu devo ver o título "Funções"
    E eu clico no botão novo "Inserir"
    Entao eu devo ver o título "Inserir Função"

    E eu preencho o campo (JS) "A partir de" com "02/02/2011"
    E eu preencho "Nome da Função" com "carregador"
    E eu preencho "Descrição das Atividades Executadas pela Função" com "descricao"
    E eu clico no botão novo "Gravar"
    Então eu devo ver "Função carregador cadastrada com sucesso."

    Então eu clico no icone "Editar" da linha contendo "carregador"
    E eu devo ver o título "Históricos da Função carregador"
    
    Então eu clico no icone "Editar" da linha contendo "02/02/2011"
    E eu preencho "Nome da Função" com "vendedor"
    E eu clico no botão novo "Gravar"
    E eu devo ver o título "Históricos da Função vendedor"
    E eu clico no botão novo "Voltar"

	Então eu clico no icone "Excluir" da linha contendo "vendedor"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Função excluída com sucesso."
=======
     Entao eu clico na linha "Eletricista" da imagem "Históricos"
         E eu clico em excluir "01/11/2017"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Histórico da função excluído com sucesso."   
     
#-----------------------------------------------------------------------------------------------------------------------------------------------

  Cenário: Exclusão do Cadastro de Funções   
      Dado que exista uma funcao "Eletricista" no cargo "Gerente de Operações Elétricas" 
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Funções"
         E eu clico em excluir "Eletricista"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Função excluída com sucesso." 


>>>>>>> Georgem: Testes Cucumber 10-11-2017
