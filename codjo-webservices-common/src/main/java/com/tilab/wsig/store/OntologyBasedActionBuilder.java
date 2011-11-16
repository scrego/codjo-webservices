/*****************************************************************
JADE - Java Agent DEvelopment Framework is a framework to develop 
multi-agent systems in compliance with the FIPA specifications.
Copyright (C) 2002 TILAB

GNU Lesser General Public License

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation, 
version 2.1 of the License. 

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the
Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA  02111-1307, USA.
*****************************************************************/

package com.tilab.wsig.store;

import jade.content.AgentAction;
import jade.content.abs.AbsAgentAction;
import jade.content.abs.AbsHelper;
import jade.content.abs.AbsTerm;
import jade.content.onto.Ontology;
import jade.content.schema.AgentActionSchema;

import java.util.Vector;

import org.apache.log4j.Logger;

public class OntologyBasedActionBuilder implements ActionBuilder {

	private static Logger log = Logger.getLogger(OntologyBasedActionBuilder.class.getName());
	
	private String ontoActionName;
	private Ontology onto;

	/**
	 * ReflectionBasedActionBuilder
	 * @param actionClass
	 */
	public OntologyBasedActionBuilder(Ontology onto, String ontoActionName) {
		this.onto = onto;
		this.ontoActionName = ontoActionName;
	}

	/**
	 * getAgentAction
	 */
	public AgentAction getAgentAction(Vector<ParameterInfo> soapParams) throws Exception {

		AgentActionSchema schema = (AgentActionSchema) onto.getSchema(ontoActionName);
		AbsAgentAction actionAbsObj = (AbsAgentAction) schema.newInstance();
		if (soapParams != null) {
			for (ParameterInfo param : soapParams) {
				String slotName = param.getName();
				AbsTerm slotValue = param.getAbsValue();
				AbsHelper.setAttribute(actionAbsObj, slotName, slotValue);
			}
		}
		return actionAbsObj;
	}
	
	public String getOntoActionName() {
		return ontoActionName;
	}	
}
