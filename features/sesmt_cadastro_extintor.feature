# language: pt

Funcionalidade: Extintor

  Esquema do Cenario: Cadastro de Extintores
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Extintores"
         E eu clico no botão "Inserir"
     Então eu seleciono <Estabelecimento> de "Estabelecimento"
         E eu seleciono <Tipo> de "Tipo"
         E eu preencho "Localização" com <Local>
         E eu preencho "Fabricante" com <Fabricante>
     Então eu clico no botão "Gravar"
         E eu devo ver <Mensagem>  

  Exemplos:
    | Estabelecimento           | Tipo                                | Local    | Fabricante  | Mensagem                        |
    | "Selecione..."            | "Selecione..."                      | ""       | ""          | "Preencha os campos indicados." |
    | "Estabelecimento Padrão"  | "Selecione..."                      | ""       | ""          | "Preencha os campos indicados." |
    | "Estabelecimento Padrão"  | "AP - Água Pressurizada (Classe A)" | ""       | ""          | "Preencha os campos indicados." |
    | "Estabelecimento Padrão"  | "AP - Água Pressurizada (Classe A)" | "Escada" | "Extinfogo" | "Extintor gravado com sucesso." |


@teste
  Esquema do Cenario: Excluir de Extintores
      Dado que exista uma inspeção para o extintor localizado em "Escada"
      Dado que exista um extintor localizado em "Copa"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Extintores"
         E eu clico em excluir <Extintor>
         E eu devo ver o alert do confirmar exclusão e clico no ok
         E eu devo ver <Mensagem>  

  Exemplos:
    | Extintor | Mensagem                                                         |
    | "Escada" | "Entidade extintor possui dependências em inspeção do extintor." |
    | "Copa"   | "Extintores"                                                     |



  Cenário: Cadastro de Extintor
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Extintores"
    Então eu devo ver o título "Extintores"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Extintor"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Extintores"
    E eu clico no botão "Inserir"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu seleciono "AP - Água Pressurizada (Classe A)" de "Tipo"
    E eu preencho "Localização" com "escada2"
    E eu preencho "Fabricante" com "extfogo"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Inserir Extintor"
    E eu devo ver "Extintor gravado com sucesso."

    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Extintores"
    E eu clico em editar "escada2"
    Então eu devo ver o título "Editar Extintor"
    E eu devo ver "escada2"
    
    Então eu clico em editar "escada2" 
    E o campo "Localização" deve conter "escada2"
    E eu preencho "Localização" com "escada3"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Editar Extintor"
    E eu devo ver "escada3"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Histórico para o Extintor AP"
    E eu preencho o campo (JS) "dataHist" com "24/11/2011"
    E eu preencho "Hora" com "08:00"
    E eu seleciono "Estabelecimento Padrão" de "Estabelecimento"
    E eu preencho "Localização" com "escada4"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Editar Extintor"
    Então eu devo ver "escada3"
    E eu devo ver "escada4"
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Extintores"
    E eu clico em excluir "escada3"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver o título "Extintores"
    E eu não devo ver "escada3"