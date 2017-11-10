# language: pt

Funcionalidade: Cadastrar Índices
   
  Cenário: Cadastro de Índices | Valida Campos Obrigatórios
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Índices"
         E eu clico no botão "Inserir"
         E eu clico no botão "Gravar"
     Então eu devo ver o alert do valida campos e clico no ok
         E eu preencho "Nome" com "Índice Salário Mínimo"
         E eu clico no botão "Gravar"
     Então eu devo ver o alert do valida campos e clico no ok
         E eu preencho o campo (JS) "A partir de" com "01/01/2017"
         E eu clico no botão "Gravar"
     Então eu devo ver o alert do valida campos e clico no ok
         E eu preencho "Valor" com "1000"
         E eu clico no botão "Gravar"

#----------------------------------------------------------------------------------------------------------------------------------         

  Cenário: Cadastro de Índices 
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Índices"
         E eu clico no botão "Inserir"
         E eu preencho "Nome" com "Índice Salário Mínimo"
         E eu preencho o campo (JS) "A partir de" com "01/01/2017"
         E eu preencho "Valor" com "1000"
         E eu clico no botão "Gravar"

#----------------------------------------------------------------------------------------------------------------------------------

   Cenário: Cadastro de Índices | Edição do histórico
      Dado que exista um indice "Salário Mínimo do Brasil" com historico na data "01/10/2000" e valor "3000"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Índices"
         E eu clico em editar "Salário Mínimo do Brasil"
         E eu clico em editar "01/10/2000"
        E eu clico no botão "Gravar"

#----------------------------------------------------------------------------------------------------------------------------------

  Cenário: Cadastro de Índices | Edição
      Dado que exista um indice "Salário Mínimo do Brasil" com historico na data "01/10/2000" e valor "3000"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Índices"
         E eu clico em editar "Salário Mínimo do Brasil"
         E eu preencho "Nome" com "Salário Mínimo"
         E eu clico no botão "Gravar"

#----------------------------------------------------------------------------------------------------------------------------------

  Esquema do Cenario: Exclusão de Indices    
    Dado que exista um indice "Salário Mínimo" com historico na data "01/10/2000" e valor "1000"
    Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
    Dado que exista um novo historico por indice para o colaborador "Theresa May" na faixa salarial "I"
    Dado que exista um indice "Salário Aprendizes" com historico na data "01/10/2000" e valor "900"
    Dado que eu esteja logado com o usuário "SOS"
  Quando eu acesso o menu "C&S > Cadastros > Índices"
       E eu clico em excluir <Indice>
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver <mensagem> 
         E eu devo deslogar do sistema

  Exemplos:
    | Indice               |  mensagem                                                           |
    | "Salário Aprendizes" |  "Índice excluído com sucesso."                                     |
    | "Salário Mínimo"     |  "Entidade índice possui dependências em histórico do colaborador." |
  
#----------------------------------------------------------------------------------------------------------------------------------

  Cenário: Cadastro de Índices | Integrado com o Pessoal
      Dado que a empresa esteja integrada com o Pessoal 
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Índices"
     Então eu devo ver "A manutenção do Cadastro de Índices deve ser realizada no Fortes Pessoal."
         E eu devo deslogar do sistema    