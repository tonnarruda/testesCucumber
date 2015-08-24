/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.fortes.webwork.interceptor;

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fortes.model.type.FileUtil;
import com.fortes.thumb.GeradorDeThumbnailUtils;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequestWrapper;
import com.opensymphony.webwork.interceptor.FileUploadInterceptor;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ValidationAware;
import com.opensymphony.xwork.util.LocalizedTextUtil;

public class FileUploadToPropertyInterceptor extends FileUploadInterceptor
{
	private static final String DEFAULT_MESSAGE = "no.message.found";
	protected Long maximumSize;
	protected String acceptContentTypes;
	
	public void setMaximumSize(Long maximumSize)
	{
		this.maximumSize = maximumSize;
	}

	public void setAcceptContentTypes(String acceptContentTypes)
	{
		this.acceptContentTypes = acceptContentTypes;
	}

	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception
	{
		ActionContext ac = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);

		if (!(request instanceof MultiPartRequestWrapper))
		{
			if (log.isDebugEnabled())
			{
				ActionProxy proxy = invocation.getProxy();
				log.debug(getTextMessage("webwork.messages.bypass.request", new Object[] { proxy.getNamespace(), proxy.getActionName() }, ActionContext.getContext().getLocale()));
			}

			return invocation.invoke();
		}

		final Object action = invocation.getAction();
		ValidationAware validation = null;

		if (action instanceof ValidationAware)
		{
			validation = (ValidationAware) action;
		}

		MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) request;

		if (multiWrapper.hasErrors())
		{
			for (Iterator errorIter = multiWrapper.getErrors().iterator(); errorIter.hasNext();)
			{
				String error = (String) errorIter.next();

				if (validation != null)
				{
					validation.addActionError(error);
				}

				log.error(error);
			}
		}

		boolean compactaImg = false;
		
		Map parameters = ac.getParameters();

		// Bind allowed Files
		Enumeration fileParameterNames = multiWrapper.getFileParameterNames();
		while (fileParameterNames != null && fileParameterNames.hasMoreElements())
		{
			// get the value of this input tag
			String inputName = (String) fileParameterNames.nextElement();

			if(inputName != null && (inputName.equalsIgnoreCase("candidato.foto") || inputName.equalsIgnoreCase("colaborador.foto")))
				compactaImg = true;
			
			// get the content type
			String[] contentTypes = multiWrapper.getContentTypes(inputName);

			if (isNonEmpty(contentTypes) && verificaExtensao(contentTypes))
			{
				// get the name of the file from the input tag
				String[] fileName = multiWrapper.getFileNames(inputName);

				if (isNonEmpty(fileName))
				{
					// Get a File object for the uploaded File
					File[] files = multiWrapper.getFiles(inputName);
					if (files != null)
					{
						com.fortes.model.type.File[] fs = new com.fortes.model.type.File[files.length];
						for (int index = 0; index < files.length; index++)
						{
							getTextMessage("webwork.messages.current.file", new Object[] { inputName, contentTypes[index], fileName[index], files[index] }, ActionContext.getContext().getLocale());

							super.setMaximumSize(maximumSize);

							if (acceptFile(files[index], contentTypes[index], inputName, validation, ac.getLocale()))
							{
								//com.fortes.model.type.File f = new com.fortes.model.type.File(fileName[0], contentType[0], FileUtil.getFileBytes(files[0]));

								File arquivo = files[index];
								String contentType = contentTypes[index];
								if (compactaImg && isImage(contentType)) {
									arquivo = getThumbnail(files[index]);
									contentType = "image/jpeg";
								}
								
								com.fortes.model.type.File f = new com.fortes.model.type.File();
								f.setName(fileName[index]);
								f.setContentType(contentType); // thumbnail sempre gera image/jpeg 
								f.setBytes(FileUtil.getFileBytes(arquivo));
								f.setSize(arquivo.length());
								fs[index] = f;
								//f.setLocation(files[0].getAbsolutePath());

								// Setam as propriedades na propriedade do tipo File
								//parameters.put(inputName, files);
								//parameters.put(inputName + "contentType", contentType);
								//parameters.put(inputName + "fileName", fileName);
							}
						}
						parameters.put(inputName, fs);
					}
				}
				else
				{
					log.error(getTextMessage("webwork.messages.invalid.file", new Object[] { inputName }, ActionContext.getContext().getLocale()));
				}
			}
			else
			{
				log.error(getTextMessage("webwork.messages.invalid.content.type", new Object[] { inputName }, ActionContext.getContext().getLocale()));
				validation.addFieldError(inputName, "Tipo de Arquivo não permitido");
			}
		}

		// invoke action
		String result = invocation.invoke();

		// cleanup
		fileParameterNames = multiWrapper.getFileParameterNames();
		while (fileParameterNames != null && fileParameterNames.hasMoreElements())
		{
			String inputValue = (String) fileParameterNames.nextElement();
			File[] file = multiWrapper.getFiles(inputValue);
			for (int index = 0; index < file.length; index++)
			{
				File currentFile = file[index];
				log.info(getTextMessage("webwork.messages.removing.file", new Object[] { inputValue, currentFile }, ActionContext.getContext().getLocale()));

				if ((currentFile != null) && currentFile.isFile())
				{
					currentFile.delete();
				}
			}
		}

		return result;
	}

	/**
	 * Verifica se o arquivo é do tipo imagem através do content-type.
	 */
	private boolean isImage(String contentType) {
		return contentType.substring(0, 5).contains("image");
	}

	/**
	 * Gera thumbnail da foto original postada pelo usuário.
	 */
	private File getThumbnail(File fotoEmTamanhoOriginal) {
		try {
			GeradorDeThumbnailUtils gerador = new GeradorDeThumbnailUtils();
			return gerador.gera(fotoEmTamanhoOriginal.getAbsolutePath());
		} catch (Exception e) {
			log.error("Erro ao tentar gerar thumbnail. Continuando upload com imagem original.", e);
		}
		return fotoEmTamanhoOriginal;
	}

	private boolean verificaExtensao(String[] contentType)
	{
		boolean result = false;

		if(acceptContentTypes != null && acceptContentTypes.equals(contentType[0].substring(0,acceptContentTypes.length())))
		{
			result = true;
		}
		else if(acceptContentTypes == null)
		{
			result = true;
		}

		return result;
	}

	/*protected boolean acceptFile(File file, String contentType, String inputName, ValidationAware validation, Locale locale)
	{
		boolean accepted = super.acceptFile(file, contentType, inputName, validation, locale);
		if (accepted)
		{
            if (validation != null) {
                validation.addFieldError(inputName, "Could not upload file.");
            }
		}
		return false;
	}*/

	public boolean isNonEmpty(Object[] objArray)
	{
		boolean result = false;
		for (int index = 0; index < objArray.length && !result; index++)
		{
			if (objArray[index] != null)
			{
				result = true;
			}
		}
		return result;
	}

	public String getTextMessage(String messageKey, Object[] args, Locale locale)
	{
		if (args == null || args.length == 0)
		{
			return LocalizedTextUtil.findText(this.getClass(), messageKey, locale);
		}
		else
		{
			return LocalizedTextUtil.findText(this.getClass(), messageKey, locale, DEFAULT_MESSAGE, args);
		}
	}
}
