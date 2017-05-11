# language: pt

Funcionalidade: Editar Cadastro de EPI

  Cenario: Edição de Cadastro de EPI sem alterações
    Dado que exista o EPI "Bota" da categoria "Proteção"
    Dado que eu esteja logado com o usuário "SOS"
       E eu acesso o menu "SESMT > Cadastros > EPI"
       E eu clico em editar "Bota"
       E eu clico no botão "Gravar"
   Então eu devo ver o título "EPIs (Equipamentos de Proteção Individual)"


  Cenario: Edição do Nome do Fabricante no Cadastro de EPI
    Dado que exista o EPI "Bota" da categoria "Proteção"
    Dado que eu esteja logado com o usuário "SOS"
       E eu acesso o menu "SESMT > Cadastros > EPI"
       E eu clico em editar "Bota"
   Então eu preencho "Nome do Fabricante" com "Fabricante de EPI"
       E eu clico no botão "Gravar"
   Então eu devo ver o título "EPIs (Equipamentos de Proteção Individual)"


  Cenario: Edição do Historico de EPI no Cadastro de EPI
    Dado que exista o EPI "Bota" da categoria "Proteção"
    Dado que eu esteja logado com o usuário "SOS"
       E eu acesso o menu "SESMT > Cadastros > EPI"
       E eu clico em editar "Bota"
   Então eu preencho "Nome do Fabricante" com "Fabricante de EPI"
       E eu clico em editar "01/02/2011"
   Entao eu preencho "Período Recomendado de uso (em dias)" com "80"
       E eu clico no botão "Gravar"
    Entao eu preencho "Nome do Fabricante" com "Fabricante de EPI"
           E eu clico no botão "Gravar"
   Então eu devo ver o título "EPIs (Equipamentos de Proteção Individual)"