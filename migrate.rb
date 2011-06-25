require 'pg'
require 'term/ansicolor'
include Term::ANSIColor

def exec_sql sql
	begin
    conn = PGconn.connect( :dbname => "fortesrh", :user => "postgres")
    conn.exec(sql)
	ensure
	   conn.finish if conn
  end
end	

if ARGV.empty?
	result = exec_sql "select dbversao from parametrosdosistema;"
	@versao = result.first['dbversao']
else
	file_name = ARGV[0] || 'sem_comentario_hein'
	content = ARGV[1] || ''
	File.open("#{Time.now.strftime('%Y%m%d%H%M%S')}_#{file_name}.txt",'w'){|f| f.write content}
end

Dir.glob("./web/WEB-INF/metadata/migrate/*.sql").each do |file| 
  if (file =~ /(\d{14})/ and @versao < $1)
    print "Executando #{file.split('/').last}".ljust(80)
    begin
      exec_sql File.read(file)
      exec_sql "update parametrosdosistema set dbversao='#{$1}';"
      puts " [ #{green("SUCESSO")} ]" 
    rescue Exception => e
      puts " [#{red("ERRO".center(9))}]"
      puts red e.message
      exit 1
    end  
  end 
end