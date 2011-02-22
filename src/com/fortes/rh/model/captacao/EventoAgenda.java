package com.fortes.rh.model.captacao;



public class EventoAgenda
{
//	  weekcalendar.js
//    "id":1,
//    "start": new Date(year, month, day, 12),
//    "end": new Date(year, month, day, 13, 30),
//    "title":"Recrutamento de seleção <br>Cand.: <a href='www.google.com'>Rafaela Melo</a><br>Resp.: João da Silva Sauro",
//	  "body":"teste"
	
    private Long id;
    private String start;
    private String end;
    private String title;
    private String body;
    
	public EventoAgenda(Long id, String title, String body, String start, String end) 
	{
		super();
		this.id = id;
		this.start = start;
		this.end = end;
		this.title = title;
		this.body = body;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
}