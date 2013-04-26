package com.fortes.webwork.interceptor;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.webwork.interceptor.MessageStoreInterceptor;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ValidationAware;

public class MyMessageStoreInterceptor extends MessageStoreInterceptor
{
	private static final long serialVersionUID = -2503657036967681135L;
	
	private static final Log _log = LogFactory.getLog(MyMessageStoreInterceptor.class);

	public static String actionWarningsSessionKey = "__MessageStoreInterceptor_ActionWarnings_SessionKey";
    public static String actionSuccessSessionKey = "__MessageStoreInterceptor_ActionSuccess_SessionKey";
    
    protected void before(ActionInvocation invocation) throws Exception 
    {
        String reqOperationMode = getRequestOperationMode(invocation);
        
		if (RETRIEVE_MODE.equalsIgnoreCase(reqOperationMode) || RETRIEVE_MODE.equalsIgnoreCase(getOperationModel())) {

			Object action = invocation.getAction();
			if (action instanceof ValidationAware) {
				// retrieve error / message from session
				Map session = (Map) invocation.getInvocationContext().get(ActionContext.SESSION);
				MyActionSupport myActionSupport = (MyActionSupport) action;

				_log.debug("retrieve error / message from session to populate into action [" + action + "]");

				Collection actionErrors = (Collection) session.get(actionErrorsSessionKey);
				Collection actionMessages = (Collection) session.get(actionMessagesSessionKey);
				Collection actionWarnings = (Collection) session.get(actionWarningsSessionKey);
				Collection actionSuccess = (Collection) session.get(actionSuccessSessionKey);
				Map fieldErrors = (Map) session.get(fieldErrorsSessionKey);

				if (actionErrors != null && actionErrors.size() > 0) {
					Collection mergedActionErrors = mergeCollection(myActionSupport.getActionErrors(), actionErrors);
					myActionSupport.setActionErrors(mergedActionErrors);
				}

				if (actionMessages != null && actionMessages.size() > 0) {
					Collection mergedActionMessages = mergeCollection(myActionSupport.getActionMessages(), actionMessages);
					myActionSupport.setActionMessages(mergedActionMessages);
				}

				if (actionWarnings != null && actionWarnings.size() > 0) {
					Collection mergedActionWarnings = mergeCollection(myActionSupport.getActionWarnings(), actionWarnings);
					myActionSupport.setActionWarnings(mergedActionWarnings);
				}

				if (actionSuccess != null && actionSuccess.size() > 0) {
					Collection mergedActionSuccess = mergeCollection(myActionSupport.getActionSuccess(), actionSuccess);
					myActionSupport.setActionSuccess(mergedActionSuccess);
				}

				if (fieldErrors != null && fieldErrors.size() > 0) {
					Map mergedFieldErrors = mergeMap(myActionSupport.getFieldErrors(), fieldErrors);
					myActionSupport.setFieldErrors(mergedFieldErrors);
				}

				session.remove(actionErrorsSessionKey);
				session.remove(actionMessagesSessionKey);
				session.remove(actionWarningsSessionKey);
				session.remove(actionSuccessSessionKey);
				session.remove(fieldErrorsSessionKey);
			}
		}
    }
    
	protected void after(ActionInvocation invocation, String result) throws Exception {

		String reqOperationMode = getRequestOperationMode(invocation);
		if (STORE_MODE.equalsIgnoreCase(reqOperationMode) || STORE_MODE.equalsIgnoreCase(getOperationModel())) {

			Object action = invocation.getAction();
			if (action instanceof ValidationAware) {
				// store error / messages into session
				Map session = (Map) invocation.getInvocationContext().get(ActionContext.SESSION);

				_log.debug("store action [" + action + "] error/messages into session ");

				MyActionSupport myActionSupport = (MyActionSupport) action;
				session.put(actionErrorsSessionKey, myActionSupport.getActionErrors());
				session.put(actionMessagesSessionKey, myActionSupport.getActionMessages());
				session.put(actionWarningsSessionKey, myActionSupport.getActionWarnings());
				session.put(actionSuccessSessionKey, myActionSupport.getActionSuccess());
				session.put(fieldErrorsSessionKey, myActionSupport.getFieldErrors());
			} else {
				_log.debug("Action [" + action + "] is not ValidationAware, no message / error that are storeable");
			}
		}
	}
}