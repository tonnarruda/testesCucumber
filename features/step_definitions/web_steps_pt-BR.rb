# encoding: utf-8
# IMPORTANT: This file is generated by cucumber-rails - edit at your own peril.
# It is recommended to regenerate this file in the future when you upgrade to a 
# newer version of cucumber-rails. Consider adding your own code to a new file 
# instead of editing this one. Cucumber will automatically load all features/**/*.rb
# files.

require File.expand_path(File.join(File.dirname(__FILE__), "..", "support", "paths"))

Dado /^que eu esteja logado com o usuário "([^"]*)"$/ do |nome|
  exec_sql "update parametrosdosistema set servidorremprot = 'FORTESAG'"
  unless page.has_selector?('.saudacao') && page.has_selector?('.nomeUsuario')
    # Evita problema quando o firefox eh instanciado com a janela menor do que o necessario
    page.execute_script("window.resizeTo(screen.width, screen.height);window.moveTo(0,0);window.focus()")
    Dado %{que eu esteja na pagina de login}
    E %{eu preencho "username" com "#{nome}"}
    E %{eu saio do campo "username"}
    E %{eu preencho "password" com "1234"}
    E %{eu clico em "Entrar"}
    Então %{eu devo ver "Bem-vindo(a)"}
  end
end

Dado /^que eu esteja deslogado$/ do
  page.execute_script("window.location = 'http://localhost:8080/fortesrh/logout.action'")
end

Dado /^que a obrigatoriedade dos dados complementares da solicitação de pessoal seja "([^"]*)"$/ do |obrig_dadoscomp|
  exec_sql "update empresa set solPessoalObrigarDadosComplementares = #{obrig_dadoscomp};"
end

Dado /^que a opção de solicitação de confirmação de desligamento para a empresa seja "([^"]*)"$/ do |solicitar_desligamento|
  exec_sql "update empresa set solicitarConfirmacaoDesligamento = #{solicitar_desligamento};"
end

Dado /^que a opção apresentar performance de forma parcial ao responder avaliação de desempenho seja "([^"]*)"$/ do |apresentar_performance|
  exec_sql "update empresa set mostrarPerformanceAvalDesempenho = #{apresentar_performance};"
end

Quando /^eu acesso "([^"]*)"$/ do |path|
  page.execute_script("window.location = 'http://localhost:8080/fortesrh/#{path}'")
end

Quando /^eu acesso o menu "([^"]*)"$/ do |menu_path|
  items = menu_path.strip.split(/\s*>\s*/)
  menu_master = items.shift;
  link = find("#menuDropDown > li > a:contains('" + menu_master + "')")
  items.each do |item|
     link = link.find(:xpath, "../ul/li/a[text()='" + item + "']")
  end
  page.execute_script("window.location = '#{link[:href]}'")
end

Quando /^eu clico em "Entrar"?$/ do
    find('.btnEntrar').click
end

Quando /^eu clico no botão "([^"]*)"$/ do |text|
    find('.btn' + text).click
end

Quando /^eu clico no botão de Id "([^"]*)"$/ do |text|
    find('#' + text).click
end

Quando /^eu clico em excluir "([^"]*)"$/ do |text|
  find(:xpath, "//td[contains(text(), '#{text}')]/../td/a/img[@title='Excluir']").click
  #find(:xpath, "//td[text()='#{text}']/../td")
end

Quando /^eu clico em editar "([^"]*)"$/ do |text|
  find(:xpath, "//td[contains(text(), '#{text}')]/../td/a/img[@title='Editar']").click
end

Quando /^eu clico em imprimir "([^"]*)"$/ do |text|
  find(:xpath, "//td[contains(text(), '#{text}')]/../td/a/img[@title='Imprimir']").click
end

Quando /^eu clico na ação "([^"]*)" de "([^"]*)"$/ do |acao, text|
  find(:xpath, "//td[contains(text(), '#{text}')]/../td/a/img[@title='#{acao}']").click
end

Quando /^eu clico na linha "([^"]*)" da imagem "([^"]*)"$/ do |desc, img|
  find(:xpath, "//td[contains(text(), '#{desc}')]/../td/a/img[@title='#{img}']").click
end

Quando /^eu clico na imagem "([^"]*)" da pergunta "([^"]*)"$/ do |img, titulo|
  find(:xpath, "//p[contains(text(), '#{titulo}')]/../div[@class='acaoPerguntas']/a/img[@title='#{img}']").click
end

Quando /^eu clico na imagem com o título "([^"]*)"$/ do |titulo|
  find(:xpath, "//img[@title='#{titulo}']").click
end

Quando /^eu preencho campo pelo id "([^"]*)" com "([^"]*)"$/ do |desc, value|
  field = find(:xpath, "//input[contains(@id, '#{desc}')]")
  field.set(value)
end

Quando /^eu preencho campo pelo class "([^"]*)" com "([^"]*)"$/ do |desc, value|
   page.execute_script("$('.#{desc}').val('#{value}')")
end

Quando /^eu preencho o campo do item "([^"]*)" com "([^"]*)"$/ do |desc, value|
  field = find(:xpath, "//td[contains(text(), '#{desc}')]/../td/input[@type='text']")
  field.set(value)
end

Quando /^eu clico no botão com o texto "([^"]*)"$/ do |label|
  find(:xpath, "//button/descendant::*[contains(text(), '#{label}')]").click
end

def get_field_input field
  label = (all(:xpath, "//input[contains(class(), 'despesa moeda')]"))[0]
  field = label[:for] unless label.nil?
  field
end

Quando /^eu clico no ícone com o título "([^"]*)"$/ do |titulo|
  find(:xpath, "//a[@title='#{titulo}']").click
end

Então /^eu devo ver o título "([^"]*)"$/ do |text|
  Then %{I should see "#{text}" within "#waDivTitulo"}
end

Dado /^que eu esteja na (.+)$/ do |page_name|
  Given %{I am on #{page_name}}
end

Quando /^eu vou para (.+)$/ do |page_name|
  When %{I go to #{page_name}}
end

Quando /^eu aperto "([^"]*)"$/ do |button|
  When %{I press "#{button}"}
end

Quando /^eu clico "([^"]*)"$/ do |link|
  When %{I follow "#{link}"}
end

Quando /^eu clico "([^"]*)" dentro de "([^"]*)"$/ do |link, parent|
  When %{I follow "#{link}" within "#{parent}"}
end

Quando /^eu preencho "([^"]*)" com "([^"]*)"$/ do |field, value|
  field = get_field(field)
  When %{I fill in "#{field}" with "#{value}"}
end

Quando /^eu preencho o campo \(JS\) "([^"]*)" com "([^"]*)"$/ do |field, value|
  field = get_field(field)
  page.execute_script("$('##{field}').val('#{value}')")
end

Quando /^eu preencho o select autocomplete \(JS\) "([^"]*)" com "([^"]*)"$/ do |field, value|
  find(:xpath, "//button[@type='button']").click
  field = get_field(field)
  page.execute_script("$('.#{field}').val('#{value}').blur()")
end

Quando /^eu preencho "([^"]*)" para "([^"]*)"$/ do |value, field|
  When %{I fill in "#{value}" for "#{field}"}
end

Quando /^eu preencho o seguinte:$/ do |fields|
  When %{I fill in the following:}, fields
end

Quando /^eu seleciono \(JS\) "([^"]*)" de "([^"]*)"$/ do |value, field|
  field = get_field(field)
  page.execute_script("$('##{field}').val('#{value}')")
end

Quando /^eu adiciono o avaliado no avaliador da avaliação de desempenho$/ do
  page.execute_script("$('#1').addClass('ui-selected')")
  page.execute_script("$('#relacionar_selecionados').removeClass('disabled')")
  page.execute_script("$('#relacionar_selecionados').click()")
end

Quando /^eu seleciono "([^"]*)" de "([^"]*)"$/ do |value, field|
  field = get_field(field)
  When %{I select "#{value}" from "#{field}"}
end

Quando /^eu seleciono "([^"]*)" como a data e a hora$/ do |time|
  When %{I select "#{time}" as the date and time}
end

Quando /^eu seleciono "([^"]*)" como a data e a hora "([^"]*)"$/ do |datetime, datetime_label|
  When %{I select "#{datetime}" as the "#{datetime_label}" date and time}
end

Quando /^eu seleciono "([^"]*)" como a hora$/ do |time|
  When %{I select "#{time}" as the time}
end

Quando /^eu seleciono "([^"]*)" como a hora "([^"]*)"$/ do |time, time_label|
  When %{I select "#{time}" as the "#{time_label}" time}
end

Quando /^eu seleciono "([^"]*)" como a data$/ do |date|
  When %{I select "#{date}" as the date}
end

Quando /^eu seleciono "([^"]*)" como a data "([^"]*)"$/ do |date, date_label|
  When %{I select "#{date}" as the "#{date_label}" date}
end

Quando /^eu seleciono "([^"]*)" como "([^"]*)"$/ do |date, date_label|
  When %{I select "#{date}" as the "#{date_label}" date}
end

Quando /^eu marco "([^"]*)"$/ do |field|
  When %{I check "#{field}"}
end

Quando /^eu marco o checkbox com name "([^"]*)"$/ do |field|
  page.execute_script("$(\"input[name='#{field}']\").attr('checked',true)")
end

Quando /^eu desmarco o checkbox com id "([^"]*)"$/ do |field|
  field = find_field(field)
  page.execute_script("$('##{field[:id]}').attr('checked',false)")
end

Quando /^eu desmarco "([^"]*)"$/ do |field|
  When %{I uncheck "#{field}"}
end

Quando /^eu escolho "([^"]*)"$/ do |field|
  When %{I choose "#{field}"}
end

Quando /^eu anexo o arquivo em "([^"]*)" a "([^"]*)"$/ do |path, field|
  When %{I attach the file "#{path}" to "#{field}"}
end

Então /^eu devo ver "([^"]*)"$/ do |text|
  Then %{I should see "#{text}"}
end

Então /^eu devo ver "([^"]*)" dentro de "([^"]*)"$/ do |text, selector|
  Then %{I should see "#{text}" within "#{selector}"}
end

Então /^eu devo ver \/([^\/]*)\/$/ do |regexp|
  Then %{I should see /#{regexp}/}
end

Então /^eu devo ver \/([^\/]*)\/ dentro de "([^"]*)"$/ do |regexp, selector|
  Then %{I should see /#{regexp}/ within "#{selector}"}
end

Então /^eu não devo ver "([^"]*)"$/ do |text|
  Then %{I should not see "#{text}"}
end

Então /^eu não devo ver na listagem "([^"]*)"$/ do |text|
  Then %{I should not see "#{text}" within ".dados"}
end

Então /^eu não devo ver "([^"]*)" dentro de "([^"]*)"$/ do |text, selector|
  Then %{I should not see "#{text}" within "#{selector}"}
end

Então /^eu não devo ver \/([^\/]*)\/$/ do |regexp|
  Then %{I should not see /#{regexp}/}
end

Então /^eu não devo ver \/([^\/]*)\/ dentro de "([^"]*)"$/ do |regexp, selector|
  Then %{I should not see /#{regexp}/ within "#{selector}"}
end

Então /^o campo "([^"]*)" deve conter "([^"]*)"$/ do |field, value|
  Then %{the "#{field}" field should contain "#{value}"}
end

Então /^o campo "([^"]*)" não deve conter "([^"]*)"$/ do |field, value|
  Then %{the "#{field}" field should not contain "#{value}"}
end

Então /^o checkbox "([^"]*)" deve estar marcado$/ do |label|
  Then %{the "#{label}" checkbox should be checked}
end

Então /^o checkbox "([^"]*)" não deve estar marcado$/ do |label|
  Then %{the "#{label}" checkbox should not be checked}
end

Então /^eu devo estar na (.+)$/ do |page_name|
  Then %{I should be on #{page_name}}
end

Então /^mostre-me a página$/ do
  Then %{show me the page}
end

Então /^eu devo ver o alert do valida campos e clico no ok/ do
  Then %{I should see "Preencha os campos indicados."}
  When %{I press "OK"}
end

Então /^eu devo ver o alert "([^"]*)" e clico no ok/ do |msg_alert|
  Then %{I should see "#{msg_alert}"}
  When %{I press "OK"}
end

Então /^eu devo ver o alert do confirmar exclusão e clico no ok/ do
  Then %{I should see "Confirma exclusão?"}
  When %{I press "OK"}
end

Então /^eu devo ver o alert do confirmar e clico no ok/ do
  When %{I press "OK"}
end

Quando /^eu espero (\d+) segundo[s]?$/ do |segundos|
  sleep segundos.to_i
end

Quando /^eu saio do campo "([^"]*)"$/ do |field|
  field = find_field(field)
  page.execute_script("$('##{field[:id]}').blur()")
end

Então /^eu não devo ver a aba "([^"]*)"$/ do |titulo_aba|
  page.should_not have_xpath("//div[@id='abas']/div/a", :text => /#{titulo_aba}/i)
end

Então /^eu devo ver a aba "([^"]*)"$/ do |titulo_aba|
  page.should have_xpath("//div[@id='abas']/div/a", :text => /#{titulo_aba}/i)
end

Quando /^eu clico na aba "([^"]*)"$/ do |text|
   find(:xpath, "//div[@id='abas']/div/a", :text => /#{text}/i).click
end

Então /^o campo "([^"]*)" deve ter "([^"]*)" selecionado$/ do |field, value|
  field = find_field(field)
  option = field.find(:xpath, "//option[contains(text(), '#{value}')]")
  value=option.value
  Então %{o campo "#{field[:id]}" deve conter "#{value}"}
end

Dado /^que exista o evento "([^"]*)"$/ do |nome_evento|
   insert :evento do
     nome nome_evento
   end
end

Dado /^que exista o estabelecimento "([^"]*)"$/ do |nome|
   insert :estabelecimento do
     nome nome
     empresa :id => 1
   end
end

Dado /^que exista a área organizacional "([^"]*)"$/ do |nome_area|
   insert :areaorganizacional do
     nome nome_area
     empresa :id => 1
   end
end

Dado /^que exista o grupo ocupacional "([^"]*)"$/ do |nome_grupo_ocupacional|
   insert :grupoocupacional do
     nome nome_grupo_ocupacional
     empresa :id => 1
    end
end 

Dado /^que exista a área organizacional "([^"]*)", filha de "([^"]*)"$/ do |nome_area, nome_area_mae|
   insert :areaorganizacional do
     nome nome_area
     empresa :id => 1
     areamae :areaorganizacional, :nome => nome_area_mae
   end
end

Dado /^que exista o cargo "([^"]*)"$/ do |nome_cargo|
   insert :cargo do
     nome nome_cargo
     nomemercado nome_cargo
     empresa :id => 1
   end
end

Dado /^que exista um indice "([^"]*)" com historico na data "([^"]*)" e valor "([^"]*)"$/ do |nome_indice, data_historico, valor_historico|
   insert :indice do
     nome nome_indice
   end
    insert :indicehistorico do
      data data_historico
      indice :nome => nome_indice
      valor valor_historico
   end
end

Dado /^que exista o cargo "([^"]*)" na área organizacional "([^"]*)"$/ do |nome_cargo, nome_area|
   insert :cargo do
     nome nome_cargo
     nomemercado nome_cargo
     empresa :id => 1
   end

   insert :cargo_areaorganizacional, :sem_id => true do
     cargo :nome => nome_cargo
     areasorganizacionais :areaorganizacional, :nome => nome_area
   end
end

Dado /^que exista a tabela de reajuste "([^"]*)" na data "([^"]*)" aprovada "([^"]*)" com o tipo de reajuste "([^"]*)"$/ do |nome, data, aprovada, tiporeajuste|
   insert :tabelareajustecolaborador do
     nome nome
     data data
     aprovada aprovada
     tiporeajuste tiporeajuste
     empresa :id => 1
   end
end

Dado /^que exista um reajuste para o colaborador "([^"]*)" com a tabela de reajuste "([^"]*)" com estabelecimento, area e faixa de id "([^"]*)" com valor atual "([^"]*)" e valor proposto "([^"]*)"$/ do |nome_colaborador, nome_tabela_reajuste, id, valor_atual, valor_proposto|

    insert :estabelecimento do
      nome 'Matriz'
      empresa :nome => 'Empresa Padrão'
    end

   insert :areaorganizacional do
     id id
     nome "Desenvolvimento"
     empresa :nome => 'Empresa Padrão'
     ativo true
   end

   insert :faixasalarial do
     id id
     cargo :nome => "Desenvolvedor"
   end

   insert :reajustecolaborador do
     colaborador :nome => nome_colaborador
     tabelareajustecolaborador :nome => nome_tabela_reajuste
     estabelecimentoatual_id id
     estabelecimentoproposto_id id
     areaorganizacionalatual_id id
     areaorganizacionalproposta_id id
     faixasalarialatual_id id
     faixasalarialproposta_id id
     tiposalarioatual 3 
     tiposalarioproposto 3 
     salarioatual valor_atual 
     salarioproposto valor_proposto 
   end
end

Dado /^que exista um reajuste para a faixa salarial "([^"]*)" com a tabela de reajuste "([^"]*)" com valor atual "([^"]*)" e valor proposto "([^"]*)"$/ do |nome_faixa, nome_tabela_reajuste, valor_atual, valor_proposto|
   insert :reajustefaixasalarial do
     faixasalarial :nome => nome_faixa
     tabelareajustecolaborador :nome => nome_tabela_reajuste
     valoratual valor_atual 
     valorproposto valor_proposto 
   end
end

Dado /^que exista um reajuste para o indice "([^"]*)" com a tabela de reajuste "([^"]*)" com valor atual "([^"]*)" e valor proposto "([^"]*)"$/ do |nome_indice, nome_tabela_reajuste, valor_atual, valor_proposto|
   insert :reajusteindice do
     indice :nome => nome_indice
     tabelareajustecolaborador :nome => nome_tabela_reajuste
     valoratual valor_atual 
     valorproposto valor_proposto 
   end
end

Dado /^que exista a faixa salarial "([^"]*)" no cargo "([^"]*)"$/ do |faixa_nome, cargo_nome|
   insert :faixasalarial do
     nome faixa_nome
     cargo :nome => cargo_nome
   end
end

Dado /^que exista um historico para a faixa salarial "([^"]*)" na data "([^"]*)"$/ do |faixa_nome, faixa_data|
   insert :faixasalarialhistorico do
     data faixa_data
     tipo 3
     valor 500
     status 1
     faixasalarial :nome => faixa_nome
   end
end

Dado /^que exista um bairro "([^"]*)" na cidade de "([^"]*)"$/ do |bairro_nome, cidade_nome|
   insert :bairro do
     nome bairro_nome
     cidade :nome => cidade_nome
   end
end

Dado /^que exista um modelo avaliacao aluno "([^"]*)"$/ do |avaliacao_titulo|
   insert :avaliacao do
     titulo avaliacao_titulo
     tipomodeloavaliacao 'L'
     ativo true
     empresa :id => 1
   end
end

Dado /^que exista um modelo avaliacao desempenho "([^"]*)"$/ do |avaliacao_titulo|
   insert :avaliacao do
     titulo avaliacao_titulo
     tipomodeloavaliacao 'D'
     ativo true
     empresa :id => 1
   end
end

Dado /^que exista uma avaliacao desempenho "([^"]*)"$/ do |avaliacaodesempenho_titulo|
   insert :avaliacaodesempenho do
     titulo avaliacaodesempenho_titulo
     liberada true
     avaliacao :id => 1
   end
end

Dado /^que exista uma avaliacao de curso "([^"]*)"$/ do |avaliacaocurso_titulo|
   insert :avaliacaocurso do
     titulo avaliacaocurso_titulo
     tipo 'n'
   end
end

Dado /^que exista uma etapa seletiva "([^"]*)"$/ do |nome_etapa|
   insert :etapaseletiva do
     nome nome_etapa
     ordem 1
     empresa :id => 1
   end
end

Dado /^que exista um tipo de despesa "([^"]*)"$/ do |tipodespesa_descricao|
   insert :tipodespesa do
     descricao tipodespesa_descricao
     empresa :id => 1
   end
end

Dado /^que exista um usuario "([^"]*)"$/ do |usuario_nome|
   insert :usuario do
     nome usuario_nome
     acessosistema true
     superadmin false
     caixasmensagens '{"caixasDireita":["T","C","F","U"],"caixasEsquerda":["P","D","A","S"],"caixasMinimizadas":[]}'
   end
   insert :usuarioempresa do
     usuario :nome => usuario_nome
     empresa :id => 1
   end
end

Dado /^que exista um curso "([^"]*)"$/ do |curso_nome|
   insert :curso do
     nome curso_nome
     empresa :id => 1
   end
end

Dado /^que exista uma turma "([^"]*)" para o curso "([^"]*)"$/ do |turma_descricao, curso_nome|
   insert :turma do
     descricao turma_descricao
     curso :nome => curso_nome
     realizada false
     dataprevini '01/01/2013'
     dataprevfim '15/01/2013'
     empresa :id => 1
   end
end

Dado /^que exista uma turma "([^"]*)" para o curso "([^"]*)" realizada$/ do |turma_descricao, curso_nome|
   insert :turma do
     descricao turma_descricao
     curso :nome => curso_nome
     realizada true
     dataprevini '01/07/2012'
     dataprevfim '15/07/2012'
     empresa :id => 1
   end
end

Dado /^que exista uma certificacao "([^"]*)" para o curso "([^"]*)"$/ do |certificacao_nome, curso_nome|
   insert :certificacao do
     nome certificacao_nome
     empresa :id=> 1
   end

   insert :certificacao_curso, :sem_id => true do
     certificacaos :certificacao, :nome => certificacao_nome
     cursos :curso, :nome => curso_nome
   end
end

Dado /^que exista uma empresa "([^"]*)"$/ do |empresa_nome|
   insert :empresa do
      nome empresa_nome
      acintegra false
      maxcandidatacargo 10
      exibirsalario true
      solPessoalExibirSalario true
	    solPessoalObrigarDadosComplementares true
   end
end

Dado /^que exista um ambiente "([^"]*)" com o risco "([^"]*)"$/ do |ambiente_nome, risco_descricao|
   insert :ambiente do
      nome ambiente_nome
      empresa :nome => 'Empresa Padrão'
      estabelecimento :nome => 'Estabelecimento Padrão'
   end

   insert :historicoambiente do
      descricao ambiente_nome
      data '25/07/2011'
      tempoexposicao '8h'
      ambiente :nome => ambiente_nome
   end

   insert :risco do
      descricao risco_descricao
      empresa :nome => 'Empresa Padrão'
   end

   insert :riscoambiente do
      epceficaz true
      historicoambiente :descricao => ambiente_nome
      risco :descricao => risco_descricao
   end
end

Dado /^que exista o EPI "([^"]*)" da categoria "([^"]*)"$/ do |epi_nome, tipoepi_nome|
  insert :tipoepi do
    nome tipoepi_nome
    empresa :nome => 'Empresa Padrão'
  end

  insert :epi do
    nome epi_nome
    fardamento true
    fabricante 'apiguana'
    tipoepi :nome => tipoepi_nome
    empresa :nome => 'Empresa Padrão'
    ativo true
  end
  
  insert :epihistorico do
    data '01/02/2011'
    ca 'a0a1a2a3'
    validadeUso 30
    epi :nome => epi_nome
  end
end

Dado /^que exista um candidato "([^"]*)"$/ do |candidato_nome|
  insert :candidato do
    nome candidato_nome
    conjugetrabalha true
    sexo 'M'
    blacklist false
    colocacao 'E'
    contratado false
    deficiencia 0
    disponivel true
    origem 'C'
    pagapensao false
    possuiveiculo true
    qtdfilhos 1
    quantidade 0
    escolaridade '10'
    empresa :nome => 'Empresa Padrão'
  end
end

Dado /^que exista um colaborador "([^"]*)", da area "([^"]*)", com o cargo "([^"]*)" e a faixa salarial "([^"]*)"$/ do |colaborador_nome, areaorganizacional_nome, cargo_nome, faixasalarial_nome|
  insert :areaorganizacional do
    nome areaorganizacional_nome
    empresa :nome => 'Empresa Padrão'
    ativo true
  end

  insert :cargo do
    nome cargo_nome
    nomemercado cargo_nome
    empresa :nome => 'Empresa Padrão'
  end

  insert :faixasalarial do
    nome faixasalarial_nome
    cargo :nome => cargo_nome
  end

  insert :candidato do
    nome colaborador_nome
    conjugetrabalha true
    sexo 'M'
    blacklist false
    colocacao 'E'
    contratado false
    deficiencia 0
    disponivel true
    origem 'C'
    pagapensao false
    possuiveiculo true
    qtdfilhos 1
    quantidade 0
    escolaridade '10'
    empresa :nome => 'Empresa Padrão'
  end

  insert :colaborador do
    nome colaborador_nome
    nomecomercial colaborador_nome
    dataadmissao '01/07/2011'
    desligado false
    conjugetrabalha true
    qtdfilhos 0
    sexo 'M'
    naointegraac false
    deficiencia 'N'
    respondeuentrevista true
    empresa :nome => 'Empresa Padrão'
  end

  insert :historicocolaborador do
    data '01/07/2011'
    colaborador :nome => colaborador_nome
    faixasalarial :nome => faixasalarial_nome
    areaorganizacional :nome => areaorganizacional_nome
    estabelecimento :id => 1
    motivo 'C'
    tiposalario 3
    salario 1000
    status 1
  end
end

Dado /^que exista um novo historico para o colaborador "([^"]*)", na area "([^"]*)", na faixa salarial "([^"]*)"$/ do |colaborador_nome, areaorganizacional_nome,faixasalarial_nome|
  insert :historicocolaborador do
    data '14/10/2012'
    colaborador :nome => colaborador_nome
    faixasalarial :nome => faixasalarial_nome
    areaorganizacional :nome => areaorganizacional_nome
    estabelecimento :id => 1
    motivo 'P'
    tiposalario 3
    salario 1040
    status 1
  end
end

Dado /^que exista um extintor localizado em "([^"]*)"$/ do |extintor_localizacao|
  insert :extintor do
    numerocilindro 123
    tipo '1'
    ativo true
    empresa :nome => 'Empresa Padrão'
  end

  insert :historicoextintor do
    estabelecimento :nome => 'Estabelecimento Padrão'
    localizacao extintor_localizacao
    data '24/11/2011'
    extintor :numerocilindro => 123
  end
end

Dado /^que exista um medico coordenador "([^"]*)"$/ do |medico_nome|
  insert :medicocoordenador do
    nome medico_nome
    inicio '28/07/2011'
    empresa :nome => 'Empresa Padrão'
  end
end

Dado /^que exista uma funcao "([^"]*)" no cargo "([^"]*)"$/ do |funcao_nome, cargo_nome|
  insert :funcao do
    nome funcao_nome
    cargo :nome => cargo_nome
  end
end

Dado /^que exista um modelo de ficha medica "([^"]*)" com a pergunta "([^"]*)"$/ do |fichamedica_nome, pergunta|
  insert :questionario do
    titulo fichamedica_nome
    tipo 4
    liberado true
    anonimo false
    aplicarporaspecto false
    empresa :id => 1
  end

  insert :pergunta do
    texto pergunta
    questionario :titulo => fichamedica_nome
    tipo 3
    ordem 1
    comentario false
  end

  insert :fichamedica do
    questionario :titulo => fichamedica_nome
    ativa true
  end
end

Dado /^que exista uma solicitacao "([^"]*)" para área "([^"]*)" na faixa "([^"]*)"$/ do |solicitacao_nome, area_nome, faixa_nome|
  insert :solicitacao do
    quantidade 1
    encerrada false
    suspensa false
    empresa :id => 1
    descricao solicitacao_nome
    status 'A'
    areaorganizacional :areaorganizacional, :nome => area_nome
    faixasalarial :faixasalarial, :nome => faixa_nome
  end
end

Dado /^que exista um afastamento "([^"]*)"$/ do |afastamento_descricao|
  insert :afastamento do
    descricao afastamento_descricao
    inss true
  end
end

Dado /^que exista um conhecimento "([^"]*)"$/ do |conhecimento_nome|
  insert :conhecimento do
    nome conhecimento_nome
    empresa :id => 1
  end
end

Dado /^que exista um conhecimento "([^"]*)" na area organizacional "([^"]*)"$/ do |conhecimento_nome, area_nome|
  insert :conhecimento_areaorganizacional, :sem_id => true do
     conhecimentos :conhecimento, :nome => conhecimento_nome
     areaorganizacionals :areaorganizacional, :nome => area_nome
  end
end

Dado /^que exista um conhecimento "([^"]*)" no cargo "([^"]*)"$/ do |conhecimento_nome, cargo_nome|
  insert :cargo_conhecimento, :sem_id => true do
     cargo :nome => cargo_nome
     conhecimentos :conhecimento, :nome => conhecimento_nome
  end
end

Dado /^que exista um nivel de competencia "([^"]*)"$/ do |nivelcompetencia_descricao|
  insert :nivelcompetencia do
    descricao nivelcompetencia_descricao
    empresa :id => 1
  end
end

Dado /^que exista um historico de nivel de competencia na data "([^"]*)"$/ do |data|
  insert :nivelcompetenciahistorico do
    data data
    empresa :id => 1
  end
end

Dado /^que exista uma configuracao de nivel de competencia com nivel "([^"]*)" no historico do nivel de data "([^"]*)" na ordem (\d+)$/ do |nivelcompetenciadescricao, hsitoriconivelcompetenciadata, numero_ordem|
  insert :confighistoriconivel do
    nivelcompetencia :nivelcompetencia, :descricao => nivelcompetenciadescricao
    nivelcompetenciahistorico :nivelcompetenciahistorico, :data => hsitoriconivelcompetenciadata
    ordem numero_ordem
  end
end

Dado /^que exista uma connfiguracao de nivel de competencia da faixa salarial "([^"]*)" na data "([^"]*)"$/ do |faixasalarial_nome, competencia_data|
  insert :configuracaonivelcompetenciafaixasalarial do
    data competencia_data
    faixasalarial :faixasalarial, :nome => faixasalarial_nome
    id 1
  end
end

Dado /^que exista uma connfiguracao de nivel de competencia "([^"]*)" no conhecimento "([^"]*)" para connfiguracao de nivel de competencia da faixa salarial na data "([^"]*)"$/ do |nivelcompetencia_descricao, conhecimento_nome, competencia_data|
  insert :configuracaonivelcompetencia do
    nivelcompetencia :nivelcompetencia, :descricao => nivelcompetencia_descricao
    configuracaonivelcompetenciafaixasalarial :configuracaonivelcompetenciafaixasalarial, :data => competencia_data
    competencia :competencia, :nome => conhecimento_nome
    tipocompetencia 'C'
  end
end

Dado /^que exista uma connfiguracao de nivel de competencia "([^"]*)" no conhecimento "([^"]*)" para a faixa salarial "([^"]*)"$/ do |nivelcompetencia_descricao, conhecimento_nome, faixasalarial_nome|
  insert :configuracaonivelcompetencia do
    nivelcompetencia :nivelcompetencia, :descricao => nivelcompetencia_descricao
    faixasalarial :faixasalarial, :nome => faixasalarial_nome
    competencia :competencia, :nome => conhecimento_nome
    tipocompetencia 'C'
  end
end

Dado /^que exista um periodo de experiencia "([^"]*)" de (\d+) dias$/ do |periodo_descricao, periodo_dias|
  insert :periodoexperiencia do
    descricao periodo_descricao
    dias periodo_dias
    empresa :id => 1
  end
end

Dado /^que exista um papel "([^"]*)"$/ do |papel_nome|
   insert :papel do
     nome papel_nome
     codigo 'ROLE'
     ordem 1
     menu false
   end
end

Dado /^que exista um motivo de desligamento "([^"]*)"$/ do |motivo_nome|
   insert :motivodemissao do
     motivo motivo_nome
     empresa :id => 1
    end
end
 
Dado /^que exista a etapa seletiva "([^"]*)"$/ do |etapaseletiva_nome|
   exec_sql "insert into etapaseletiva (id,nome,ordem,empresa_id) values(nextval('etapaseletiva_sequence'),'#{etapaseletiva_nome}', 1,  1);"
end

Dado /^que exista o motivo da solicitacao "([^"]*)"$/ do |motivosolicitacao_descricao|
   exec_sql "insert into motivosolicitacao (id,descricao) values(nextval('motivosolicitacao_sequence'),'#{motivosolicitacao_descricao}');"
end

Dado /^que haja um[a]? (.*) com (.*)$/ do |entidade, atributos|
  propriedades = Hash.new
  atributos.scan(/(\w+)\s*("[^"]*"|'[^']*'|\d*)(\s*[,|e]?\s*)/) {|campo,valor| propriedades[campo] = valor.gsub('"','').to_sql_param }
  create entidade.gsub(/\b[a-z]{1,2}\b/, "").gsub(/\s+/, ""), propriedades
end

def get_field field
  label = all(:xpath, "//label[contains(text(), '#{field}')]").select{|e| e.text.match("^\s*#{field}\:?")}.first
  field = label[:for] unless label.nil?
  field
end