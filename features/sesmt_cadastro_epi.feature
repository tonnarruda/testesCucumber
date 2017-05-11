# language: pt

Funcionalidade: Cadastro de EPI

  Cenario: Cadastro de EPI sem preencher as informações necessárias
    Dado que eu esteja logado com o usuário "SOS"
       E eu acesso o menu "SESMT > Cadastros > EPI"
       E eu clico no botão "Inserir"
       E eu clico no botão "Gravar"
   Então eu devo ver o alert do valida campos e clico no ok


  Cenario: Cadastro de EPI sem o primeiro historíco
    Dado que eu esteja logado com o usuário "SOS"
       E eu acesso o menu "SESMT > Cadastros > Categorias de EPI/Fardamento"
       E eu clico no botão "Inserir"
   Então eu preencho "Nome" com "Categoria de EPI Teste"
       E eu clico no botão "Gravar"
   Então eu acesso o menu "SESMT > Cadastros > EPI"
       E eu clico no botão "Inserir"
   Então eu preencho "Nome" com "Bota"
       E eu preencho "Nome do Fabricante" com "Fabricante de EPI"
       E eu clico no botão "Gravar"
   Então eu devo ver o alert do valida campos e clico no ok


  Cenario: Cadastro de EPI completo
    Dado que eu esteja logado com o usuário "SOS"
       E eu acesso o menu "SESMT > Cadastros > Categorias de EPI/Fardamento"
       E eu clico no botão "Inserir"
   Então eu preencho "Nome" com "Categoria de EPI Teste"
       E eu clico no botão "Gravar"
   Então eu acesso o menu "SESMT > Cadastros > EPI"
       E eu clico no botão "Inserir"
   Então eu preencho "Nome" com "Bota"
       E eu preencho "Nome do Fabricante" com "Fabricante de EPI"
       E eu preencho "Número do CA" com "1245"
       E eu preencho o campo (JS) "Vencimento do CA" com "31/12/2017"
       E eu preencho "Percentual de Atenuação do Risco" com "20"
       E eu preencho "Período Recomendado de uso (em dias)" com "60"
   Então eu clico no botão "Gravar"



