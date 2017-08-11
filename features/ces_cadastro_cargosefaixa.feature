# language: pt

Funcionalidade: Cadastrar Cargos e Faixas

  Cenário: Cadastro Completo de Cargos e Faixas
      Dado que exista a área organizacional "Administração"
      Dado que exista a área organizacional "Administração > Desenvolvimento"
      Dado que exista a etapa seletiva "Entrevista com RH"
      Dado que exista um conhecimento "BDD"
      Dado que exista um conhecimento "Java"
      Dado que exista um conhecimento "BDD" na area organizacional "Administração"
      Dado que exista um conhecimento "Java" na area organizacional "Administração > Desenvolvimento"
      Dado que exista um nivel de competencia "Ruim"
      Dado que exista um nivel de competencia "Regular"
      Dado que exista um nivel de competencia "Bom"
      Dado que exista um nivel de competencia "Excelente"
      Dado que exista um historico de nivel de competencia na data "01/01/2017"
      Dado que exista uma configuracao de nivel de competencia com nivel "Ruim" no historico do nivel de data "01/01/2017" na ordem 1
      Dado que exista uma configuracao de nivel de competencia com nivel "Regular" no historico do nivel de data "01/01/2017" na ordem 2
      Dado que exista uma configuracao de nivel de competencia com nivel "Bom" no historico do nivel de data "01/01/2017" na ordem 3
      Dado que exista uma configuracao de nivel de competencia com nivel "Excelente" no historico do nivel de data "01/01/2017" na ordem 4
      Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "C&S > Cadastros > Cargos e Faixas"
         E eu clico no botão "Inserir"
         E eu preencho "Nomenclatura" com "Desenvolvedor Ruby on Rails"
         E eu marco "Exibir no modulo externo"
         E eu preencho "Nomenclatura" com "Desenvolvedor"
         E eu preencho "Nomenclatura de Mercado" com "Desenvolvedor"
         E eu marco "Administração"
         E eu marco "Administração > Desenvolvimento"
         E eu marco "BDD"
         E eu marco "Java"
         E eu preencho "Missão do Cargo" com "Programar sistemas Web com Orientaçao a Objetos"
         E eu preencho "Fontes de Recrutamento" com "Quero Workar"
         E eu marco "Entrevista com RH"
         E eu preencho "Responsabilidades Correlatas" com "Regras de negócio, BDD, TDD"
         E eu preencho "Complemento dos Conhecimentos" com "Testes Unitários"
         E eu preencho "Complemento das Habilidades" com "Trabalho em equipe"
         E eu preencho "Complemento das Atitudes" com "Proativo"
         E eu seleciono "Especialização completa" de "Escolaridade"
         E eu marco "Administrativa"
         E eu preencho "Experiência Desejada" com "5 (cinco) anos"
         E eu preencho "Observações" com "Salário negociável"
         E eu clico no botão "Gravar"
     Então eu devo ver o título "Inserir Faixa Salarial"
         E eu preencho "Faixa" com "1 A"
         E eu preencho "Cód. CBO" com "123"
         E eu preencho o campo (JS) "A partir de" com "01/01/2017"
         E eu seleciono "Por valor" de "Tipo"
         E eu preencho "Valor" com "3335,00"
         E eu clico no botão "Gravar"
         E eu devo ver o título "Faixas Salariais"
         E eu clico no botão "Voltar"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Ediçao do cadastro de Cargos e Faixas
      Dado que exista o cargo "Desenvolvedor Java"
      Dado que exista a área organizacional "Administração"
      Dado que exista a etapa seletiva "Entrevista com RH"
      Dado que exista um conhecimento "BDD"
      Dado que exista um conhecimento "BDD" na area organizacional "Administração"
      Dado que exista um nivel de competencia "Ruim"
      Dado que exista um nivel de competencia "Bom"
      Dado que exista um historico de nivel de competencia na data "01/01/2017"
      Dado que exista uma configuracao de nivel de competencia com nivel "Ruim" no historico do nivel de data "01/01/2017" na ordem 1
      Dado que exista uma configuracao de nivel de competencia com nivel "Bom" no historico do nivel de data "01/01/2017" na ordem 2
      Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "C&S > Cadastros > Cargos e Faixas"
         E eu clico em editar "Desenvolvedor Java"
         E eu preencho "Nomenclatura" com "Analista de Desenvolvimento Java"
         E eu marco "Administração"
         E eu marco "BDD"
         E eu clico no botão "Gravar"
     Então eu clico na linha "Analista de Desenvolvimento Java" da imagem "Faixas Salariais"
         E eu clico no botão "Inserir"
         E eu preencho "Faixa" com "1 A"
         E eu preencho "Cód. CBO" com "456"
         E eu preencho o campo (JS) "A partir de" com "01/01/2017"
         E eu seleciono "Por valor" de "Tipo"
         E eu preencho "Valor" com "3335,00"
         E eu clico no botão "Gravar"
     Então eu clico em editar "1 A"
         E eu clico em editar "01/01/2017"
         E eu preencho "Valor" com "3500"
         E eu clico no botão "Gravar"
         E eu clico no botão "Cancelar"
     Então eu clico na linha "1 A" da imagem "Níveis de Competência"
         E eu clico no botão "Inserir"
         E eu marco "BDD"
         E eu clico no botão "Gravar"
         E eu devo ver o alert "Confira os pesos e os níveis para as competências indicadas." e clico no ok
         E eu escolho "niveisCompetenciaFaixaSalariaisConhecimento[0].nivelCompetencia.id"
         E eu clico no botão "Gravar"
     Então eu devo ver "Níveis de competência da faixa salarial salvos com sucesso."
         E eu clico no botão "Voltar"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Exclusao do cadastro de Cargos sem Faixa
      Dado que exista o cargo "Desenvolvedor Java"
      Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "C&S > Cadastros > Cargos e Faixas"
     Então eu clico em excluir "Desenvolvedor Java"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Cargo excluído com sucesso"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Exclusao do cadastro de Faixa
      Dado que exista o cargo "Desenvolvedor Java"
      Dado que haja uma faixa salarial com id 1, nome "1 A", cargo_id 1
      Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "C&S > Cadastros > Cargos e Faixas"
     Então eu clico na linha "Desenvolvedor Java" da imagem "Faixas Salariais"
         E eu clico em excluir "1 A"
     Então eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Faixa salarial excluída com sucesso."
         E eu clico no botão "Voltar"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Exclusao do cadastro de Cargo e Faixa
      Dado que exista o cargo "Desenvolvedor Java"
      Dado que haja uma faixa salarial com id 1, nome "1 A", cargo_id 1
      Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "C&S > Cadastros > Cargos e Faixas"
     Então eu clico em excluir "Desenvolvedor Java"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Cargo excluído com sucesso"

#------------------------------------------------------------------------------------------------------------------------
  @erro
  Cenário: Exclusao do cadastro de Cargo e Faixa associado ao Empregado

      Dado que exista um colaborador "Tony Blair", da area "Desenvolvimento", com o cargo "Desenvolvedor Java" e a faixa salarial "1 A"
      Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "C&S > Cadastros > Cargos e Faixas"
     Então eu clico em excluir "Desenvolvedor Java"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Entao eu devo ver /Entidade "faixa salarial" possui dependências em "histórico do colaborador"./






