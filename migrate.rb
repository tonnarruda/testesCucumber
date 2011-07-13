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
	result = exec_sql "select name from migrations order by name;"
	@migrations = result.map{|r| r['name']}
	
	Dir.glob("./web/WEB-INF/metadata/migrate/*.sql").each do |file| 
	  if (file =~ /(\d{14})/ and !@migrations.include? $1)
	    print "Executando #{file.split('/').last}".ljust(80)
	    begin
	      exec_sql File.read(file)
	      exec_sql "insert into migrations values('#{$1}');"
	      puts " [ #{green("SUCESSO")} ]" 
	    rescue Exception => e
	      puts " [#{red("ERRO".center(9))}]"
	      puts red e.message
	      exit 1
	    end  
	  end 
	end	
else
	file_name = ARGV[0] || 'sem_comentario_hein'
	content = ARGV[1] || ''
	File.open("./web/WEB-INF/metadata/migrate/#{Time.now.strftime('%Y%m%d%H%M%S')}_#{file_name}.sql",'w'){|f| f.write content}
end