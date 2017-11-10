# language: pt

Funcionalidade: Gerenciamento do Cadastro de Habilidades
@teste
  Esquema do Cenario: Cadastro de Habilidades
      Dado que exista a área organizacional "Administração"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Habilidades" 
     Então  eu clico no botão "Inserir"  
         E eu preencho "Nome" com <Habilidade>
         E eu marco <Lotação>
         E eu clico "Adicionar comportamento"
         E eu preencho "habilidade.criteriosAvaliacaoCompetencia[0].descricao" com <Criterio>
         E eu preencho "Observação" com <Observação>
     Então eu clico no botão "Gravar"
         E eu devo ver <Mensagem>

Exemplos:
    | Habilidade    | Observação                                                          | Lotação         | Mensagem                        | Criterio                                                            |
    | ""            | ""                                                                  | ""              | "Preencha os campos indicados." | ""                                                                  |
    | "Organização" | "Será exigido que o Candidato/Colaborador seja bastante organizado" | "Administração" | ""                              | "Ser capaz de realizar uma comunicação de forma totalmente efetiva" |
  
#------------------------------------------------------------------------------------------------------------------------

  Cenário: Edição do Cadastro de Habilidades
      Dado que exista a área organizacional "Administração"
      Dado que exista a área organizacional "Administração > Desenvolvimento"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Habilidades"
     Então eu clico no botão "Inserir"
         E eu preencho "Nome" com "Comunicação"
         E eu marco "Administração"
         E eu clico "Adicionar comportamento"
         E eu preencho "habilidade.criteriosAvaliacaoCompetencia[0].descricao" com "Ser capaz de realizar uma comunicação de forma totalmente efetiva"
         E eu preencho "Observação" com "Deve-se utilizar apenas para a Lotação Administração"
         E eu clico no botão "Gravar"
     Então eu devo ver o título "Habilidades"
     Entao eu clico em editar "Comunicação"
         E eu preencho "Nome" com "Proatividade"
         E eu desmarco "Administração"
         E eu marco "Administração > Desenvolvimento"
         E eu clico no botão "Gravar"
         E eu devo ver o título "Habilidades"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Exclusão do Cadastro de Habilidades
      Dado que exista a área organizacional "Administração"
      Dado que exista a área organizacional "Administração > Desenvolvimento"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Habilidades"
     Então eu clico no botão "Inserir"
         E eu preencho "Nome" com "Comunicação"
         E eu marco "Administração"
         E eu clico "Adicionar comportamento"
         E eu preencho "habilidade.criteriosAvaliacaoCompetencia[0].descricao" com "Ser capaz de realizar uma comunicação de forma totalmente efetiva"
         E eu preencho "Observação" com "Deve-se utilizar apenas para a Lotação Administração"
         E eu clico no botão "Gravar"
     Entao eu clico em excluir "Comunicação"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Habilidade excluída com sucesso."
         E eu devo ver o título "Habilidades"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Exclusão do Cadastro de Habilidades Associado ao Cargo
      Dado que exista a área organizacional "Administração"
      Dado que exista o cargo "Desenvolvedor" na área organizacional "Administração"
      Dado que exista uma habilidade "Proatividade"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Cadastros > Cargos e Faixas"
         E eu clico em editar "Desenvolvedor"
         E eu marco o checkbox com name "habilidadesCheck"
         E eu clico no botão "Gravar"
     Então eu acesso o menu "C&S > Cadastros > Habilidades"
         E eu clico em excluir "Proatividade"
         E eu devo ver o alert do confirmar exclusão e clico no ok
     Então eu devo ver "Habilidade excluída com sucesso."

