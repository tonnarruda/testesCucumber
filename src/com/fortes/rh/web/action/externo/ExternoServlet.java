package com.fortes.rh.web.action.externo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fortes.rh.util.ArquivoUtil;

public class ExternoServlet extends HttpServlet
{
	private static final long serialVersionUID = 723528027961444443L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// parametro que especifica qual arquivo buscar
		String tipo = request.getParameter("tipo");
		TipoParametro tipoParametro = TipoParametro.valueOf(tipo);
		response.setContentType(tipoParametro.getArquivoTipo());

		String empresaId = request.getParameter("empresaId");
		
		String nomeArquivo = ArquivoUtil.getPathExterno() + tipoParametro.getArquivoNome();
		
		if (empresaId != null)
		{
			String caminhoExternoEmpresa = ArquivoUtil.getPathExternoEmpresa(Long.parseLong(empresaId));
			File dirExternoEmpresa = new File(caminhoExternoEmpresa);
			
			if (dirExternoEmpresa.exists() && dirExternoEmpresa.isDirectory())
				nomeArquivo = ArquivoUtil.getPathExternoEmpresa(Long.parseLong(empresaId)) + tipoParametro.getArquivoNome();
		}
		
		File arquivo = new File(nomeArquivo);

		ServletOutputStream servletOutputStream = response.getOutputStream();
		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(arquivo));

		int buffer;
		byte[] buf = new byte[10240];

		while ((buffer = inputStream.read(buf, 0, 10240)) != -1)
		{
			servletOutputStream.write(buf, 0, buffer);
		}

		servletOutputStream.close();
		inputStream.close();
	}

	private enum TipoParametro
	{
		logotipo("logotipo.png","image/jpeg"),
		menu1("topo_bg.jpg","image/jpeg"),
		menu2("topo_bg_right.jpg","image/jpeg"),
		menu3("topo_img_right.png","image/jpeg"),
		layout("layout.css","text/css"),
		trafego("trafego.css","text/css");

		TipoParametro(String arquivoNome, String arquivoTipo)
		{
			this.arquivoNome = arquivoNome;
			this.arquivoTipo = arquivoTipo;
		}

		private String arquivoNome;
		private String arquivoTipo;

		public String getArquivoNome()
		{
			return arquivoNome;
		}
		public String getArquivoTipo()
		{
			return arquivoTipo;
		}
	}
}
