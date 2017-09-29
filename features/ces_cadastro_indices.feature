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

   Cenário: Cadastro de Índices | Exclusão do índice
      Dado que exista um indice "Salário Mínimo do Brasil" com historico na data "01/10/2000" e valor "3000"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Índices"
         E eu clico em excluir "Salário Mínimo do Brasil"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Índice excluído com sucesso." 
         E eu devo deslogar do sistema

#----------------------------------------------------------------------------------------------------------------------------------

  Cenário: Cadastro de Índices | Integrado com o Pessoal
      Dado que a empresa esteja integrada com o Pessoal 
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Índices"
     Então eu devo ver "A manutenção do Cadastro de Índices deve ser realizada no Fortes Pessoal."
         E eu devo deslogar do sistema    