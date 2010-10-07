package com.fortes.rh.test.util.mockObjects;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.AxisFault;
import org.apache.axis.Handler;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.client.Service;
import org.apache.axis.client.Transport;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.encoding.DeserializerFactory;
import org.apache.axis.encoding.SerializerFactory;
import org.apache.axis.encoding.TypeMapping;
import org.apache.axis.handlers.soap.SOAPService;
import org.apache.axis.message.RPCElement;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.soap.SOAPConstants;

public class MockCall
{
	public void addAttachmentPart(Object arg0)
	{
	}

	public void addFault(QName arg0, Class arg1, QName arg2, boolean arg3)
	{
	}

	public void addHeader(SOAPHeaderElement arg0)
	{
	}

	public void addParameter(QName arg0, QName arg1, Class arg2, ParameterMode arg3)
	{
	}

	public void addParameter(QName arg0, QName arg1, ParameterMode arg2)
	{
	}

	public void addParameter(String arg0, QName arg1, Class arg2, ParameterMode arg3)
	{
	}

	public void addParameter(String arg0, QName arg1, ParameterMode arg2)
	{
	}

	public void addParameterAsHeader(QName arg0, QName arg1, Class arg2, ParameterMode arg3, ParameterMode arg4)
	{
	}

	public void addParameterAsHeader(QName arg0, QName arg1, ParameterMode arg2, ParameterMode arg3)
	{
	}

	public void clearHeaders()
	{
	}

	public void clearOperation()
	{
	}

	public String getEncodingStyle()
	{
		return "";
	}

	public boolean getMaintainSession()
	{
		return true;
	}

	public MessageContext getMessageContext()
	{
		return null;
	}

	public OperationDesc getOperation()
	{
		return null;
	}

	public QName getOperationName()
	{
		return null;
	}

	public Style getOperationStyle()
	{
		return null;
	}

	public Use getOperationUse()
	{
		return null;
	}

	public Map getOutputParams()
	{
		return null;
	}

	public List getOutputValues()
	{
		return null;
	}

	public QName getParameterTypeByName(String arg0)
	{
		return null;
	}

	public QName getParameterTypeByQName(QName arg0)
	{
		return null;
	}

	public String getPassword()
	{
		return null;
	}

	public QName getPortName()
	{
		return null;
	}

	public QName getPortTypeName()
	{
		return null;
	}

	public Object getProperty(String arg0)
	{
		return null;
	}

	public Iterator getPropertyNames()
	{
		return null;
	}

	public Message getResponseMessage()
	{
		return null;
	}

	public QName getReturnType()
	{
		return null;
	}

	public Service getService()
	{
		return null;
	}

	public String getSOAPActionURI()
	{
		return null;
	}

	public boolean getStreaming()
	{
		return true;
	}

	public String getTargetEndpointAddress()
	{
		return null;
	}

	public Integer getTimeout()
	{
		return null;
	}

	public Transport getTransportForProtocol(String arg0)
	{
		return null;
	}

	public TypeMapping getTypeMapping()
	{
		return null;
	}

	public String getUsername()
	{
		return null;
	}

	public void invoke() throws AxisFault
	{
	}

	public SOAPEnvelope invoke(Message arg0) throws AxisFault
	{
		return null;
	}

	/**
	 * Apenas para mockar, passamos no parametro um inteiro que diz o tipo de retorno.
	 * (em alguns casos é um string do codigoAC, em outros é um boolean, etc..)
	 *
	 * Dessa forma passamos:
	 * "1" - retorna String
	 * "2" - retorna Boolean
	 */
	public Object invoke(Object[] arg0) throws RemoteException
	{
		if (arg0 != null && arg0.length >= 1)
		{
			return arg0[0].toString().equals("1") ? "1" : true;
		}

		return null;
	}

	public Object invoke(QName arg0, Object[] arg1) throws RemoteException
	{
		return null;
	}

	public Object invoke(RPCElement arg0) throws AxisFault
	{
		return null;
	}

	public SOAPEnvelope invoke(SOAPEnvelope arg0) throws AxisFault
	{
		return null;
	}

	public Object invoke(String arg0, Object[] arg1) throws AxisFault
	{
		return null;
	}

	public Object invoke(String arg0, String arg1, Object[] arg2) throws AxisFault
	{
		return null;
	}

	public void invokeOneWay(Object[] arg0)
	{
	}

	public boolean isParameterAndReturnSpecRequired(QName arg0)
	{
		return true;
	}

	public boolean isPropertySupported(String arg0)
	{
		return true;
	}

	public void registerTypeMapping(Class arg0, QName arg1, Class arg2, Class arg3, boolean arg4)
	{
	}

	public void registerTypeMapping(Class arg0, QName arg1, Class arg2, Class arg3)
	{
	}

	public void registerTypeMapping(Class arg0, QName arg1, SerializerFactory arg2, DeserializerFactory arg3, boolean arg4)
	{
	}

	public void registerTypeMapping(Class arg0, QName arg1, SerializerFactory arg2, DeserializerFactory arg3)
	{
	}

	public void removeAllParameters()
	{
	}

	public void removeProperty(String arg0)
	{
	}

	public void setClientHandlers(Handler arg0, Handler arg1)
	{
	}

	public void setEncodingStyle(String arg0)
	{
	}

	public void setMaintainSession(boolean arg0)
	{
	}

	public void setOperation(OperationDesc arg0)
	{
	}

	public void setOperation(QName arg0, QName arg1)
	{
	}

	public void setOperation(QName arg0, String arg1)
	{
	}

	public void setOperation(String arg0)
	{
	}

	public void setOperationName(QName arg0)
	{
	}

	public void setOperationName(String arg0)
	{
	}

	public void setOperationStyle(String arg0)
	{
	}

	public void setOperationStyle(Style arg0)
	{
	}

	public void setOperationUse(String arg0)
	{
	}

	public void setOperationUse(Use arg0)
	{
	}

	public void setOption(String arg0, Object arg1)
	{
	}

	public void setPassword(String arg0)
	{
	}

	public void setPortName(QName arg0)
	{
	}

	public void setPortTypeName(QName arg0)
	{
	}

	public void setProperty(String arg0, Object arg1)
	{
	}

	public void setRequestMessage(Message arg0)
	{
	}

	public void setReturnClass(Class arg0)
	{
	}

	public void setReturnQName(QName arg0)
	{
	}

	public void setReturnType(QName arg0, Class arg1)
	{
	}

	public void setReturnType(QName arg0)
	{
	}

	public void setReturnTypeAsHeader(QName arg0, Class arg1)
	{
	}

	public void setReturnTypeAsHeader(QName arg0)
	{
	}

	public void setSOAPActionURI(String arg0)
	{
	}

	public void setSOAPService(SOAPService arg0)
	{
	}

	public void setSOAPVersion(SOAPConstants arg0)
	{
	}

	public void setStreaming(boolean arg0)
	{
	}

	public void setTargetEndpointAddress(String arg0)
	{
	}

	public void setTargetEndpointAddress(URL arg0)
	{
	}

	public void setTimeout(Integer arg0)
	{
	}

	public void setTransport(Transport arg0)
	{
	}

	public void setUsername(String arg0)
	{
	}

	public void setUseSOAPAction(boolean arg0)
	{
	}

	public boolean useSOAPAction()
	{
		return true;
	}

	protected Object clone() throws CloneNotSupportedException
	{
		return new MockCall();
	}

	protected void finalize() throws Throwable
	{
		System.out.println("Finalizando MockCall.");
	}

	// public boolean equals(Object obj)
	// {
	// return true;
	// }
	// public int hashCode()
	// {
	// return 1;
	// }
	// public String toString()
	// {
	// return "";
	// }
}
