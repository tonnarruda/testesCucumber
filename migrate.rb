require 'rubygems'
require 'pg'
require 'term/ansicolor'
include Term::ANSIColor

$db_name = "fortesrh"
def exec_sql sql
  begin
    conn = PGconn.connect(:dbname => $db_name, :user => "postgres", :host => "localhost")
    conn.exec(sql)
	ensure
	   conn.finish if conn
  end
end	

if ARGV.empty? || ARGV[0] == '--db'
  $db_name = ARGV[1] if ARGV[0] == '--db'
  
	result = exec_sql "select name from migrations order by name;"
	@migrations = result.map{|r| r['name']}
	
	Dir.glob("./**/*/migrate/*.sql").sort.each do |file|
	  if (file =~ /(\d{14})/ and !@migrations.include? $1)
	    print "Executando #{File.basename(file)}".ljust(80)
	    begin
	      exec_sql File.read(file)
	      exec_sql "insert into migrations values('#{$1}');"
	      puts "[ #{green("SUCESSO")} ]" 
	    rescue Exception => e
	      puts "[#{red("ERRO".center(9))}]"
	      puts red e.message
	    end  
	  end 
	end	
elsif ARGV[0] == '--deploy'
	version = ARGV[1] || 'INSIRA_NUMERO_VERSAO'
	fortesrh_home = ARGV[2] || '.'
	
	last_migrate = File.readlines("#{fortesrh_home}/web/WEB-INF/metadata/update.sql").grep(/insert into migrations values\('(\d{14})'\);--.go/){$1}.last
	puts "migrate atual no update.sql #{last_migrate}"
	
	sql_migrates = ""
	Dir.glob("#{fortesrh_home}/web/WEB-INF/metadata/migrate/*.sql").sort_by {|f| File.basename f}.each do |file|
	  if (File.basename(file) =~ /^(\d{14})/ and $1 > last_migrate)
	    print "Buscando migrate: #{$1}".ljust(80)
	    sql_migrates << "\n" + File.read(file)
	    sql_migrates << "\ninsert into migrations values('#{$1}');--.go"
	    puts "[ #{green("SUCESSO")} ]"
	  end
	end
	
	if sql_migrates.empty?
	  puts "update.sql ja esta com a migrate mais nova!"
	end
	
	close_version = "\nupdate parametrosdosistema set appversao = '#{version}';--.go"
	File.open("#{fortesrh_home}/web/WEB-INF/metadata/update.sql",'a'){|f| f.write "\n-- versao #{version}\n" + sql_migrates + close_version}
	puts "update editado com sucesso."
	
elsif ARGV[0] == '--valida_migrates'
	fortesrh_home = ARGV[1] || '.'
	
	migrates_update = File.readlines("#{fortesrh_home}/web/WEB-INF/metadata/update.sql").grep(/insert into migrations values\('(\d{14})'\);--.go/){$1}

  migrates_nao_contidas_no_update = []
	Dir.glob("#{fortesrh_home}/web/WEB-INF/metadata/migrate/*.sql").sort_by {|f| File.basename f}.each do |file| 
    unless migrates_update.include?(file.match(/\d{14}/).to_s)
      migrates_nao_contidas_no_update << file.match(/\d{14}.*/)
    end
	end
  
  if migrates_nao_contidas_no_update.size > 0
    puts "Migrates nao contidas no update.sql."
    migrates_nao_contidas_no_update.each{|nome| puts nome}
  else
    puts "As migrates foram avaliadas com sucesso e nao existem divergencias."
  end
  
elsif ARGV[0] == '--update-db-vazio'
  	version = ARGV[1] || 'INSIRA_NUMERO_VERSAO'
  	fortesrh_home = ARGV[2] || '.'
  	$db_name = ARGV[3]
  	comandos = ""
  	executa = false
	File.readlines("#{fortesrh_home}/web/WEB-INF/metadata/update.sql").each do |line|
    unless (line =~ /^UPDATE parametrosdosistema SET atualizaPapeisIdsAPartirDe.+/i)
  		executa = true if line.start_with?("-- versao #{version}")
  		comandos << line if executa
    end 
	end
	
  exec_sql comandos if comandos != ""
else
	file_name = ARGV[0]
	content = ARGV[1] || ''
	print "Criando migrate \"#{file_name}\" ...".ljust(80) 
	File.open("./web/WEB-INF/metadata/migrate/#{Time.now.strftime('%Y%m%d%H%M%S')}_#{file_name}.sql",'w'){|f| f.write content}
	puts "[ #{green("SUCESSO")} ]"	
  puts "MIGRATE criada sem ;--.go".ljust(80) + "[" + yellow("AVISO".center(9)) + "]" unless content.empty? or content =~ /;--.go/
end
