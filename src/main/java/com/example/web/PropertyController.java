/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.web;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.example.domain.Property;
import com.example.domain.PropertyCatalog;
import com.example.domain.PropertyDao;
import com.example.domain.Property.Feature;

@Named
@ConversationScoped
public class PropertyController implements Serializable {

	private static final long serialVersionUID = 8928231838675940312L;

	@Inject
	private Conversation conversation;

	@Inject
	private FacesContext facesContext;

	@Inject
	private PropertyDao propertyDao;

	@Inject
	private PropertyCatalog propertyCatalog;

	@Named
	@Produces
	@ConversationScoped
	private Property newProperty = new Property();

	@Named
	@Produces
	@RequestScoped
	private Property queryProperty = new Property();

	@Named
	@Produces
	@ConversationScoped
	private Property displayProperty = new Property();

	private String propertyname;

	private String lookupResponse;

	public String beginNewProperty() {
		conversation.begin();

		return "propertycreate";
	}

	public void create() {
		try {
			Property.Feature[] features = newProperty.getFeatures();
			if (features != null) {
				String[] existingList = newProperty.getFeaturelist();
				String[] newList = new String[features.length];

				for (Feature f : features) {
					if (existingList != null && f.getFeatureNumber() < existingList.length) {
						newList[f.getFeatureNumber()] = f.getFeatureDescription();
						f.setEditable(false);
					}
				}
				if (features != null
						&& (features.length > 0 && existingList == null || features.length > existingList.length)) {
					System.out.println("features added.");
					int len = 0;
					if (existingList != null) {
						len = len + existingList.length;
					}
					for (int i = len; i < features.length; i++) {
						newList[features[i].getFeatureNumber()] = features[i].getFeatureDescription();
						features[i].setEditable(false);
					}
				}
				newProperty.setFeaturelist(newList);
			}
			propertyDao.createProperty(newProperty);
			conversation.end();
			String message = "A new property with id " + newProperty.getId() + " has been created successfully";
			facesContext.addMessage(null, new FacesMessage(message));
		} catch (Exception e) {
			String message = "An error has occured while creating the property (see log for details)";
			facesContext.addMessage(null, new FacesMessage(message));
		}
	}

	public void update() {
		try {
			Property.Feature[] features = displayProperty.getFeatures();
			String[] existingList = displayProperty.getFeaturelist();
			String[] newList = new String[features.length];

			for (Feature f : features) {
				if (existingList != null && f.getFeatureNumber() < existingList.length) {
					newList[f.getFeatureNumber()] = f.getFeatureDescription();
					f.setEditable(false);
				}
			}
			if (features != null
					&& (features.length > 0 && existingList == null || features.length > existingList.length)) {
				System.out.println("features added.");
				int len = 0;
				if (existingList != null) {
					len = len + existingList.length;
				}
				for (int i = len; i < features.length; i++) {
					newList[features[i].getFeatureNumber()] = features[i].getFeatureDescription();
					features[i].setEditable(false);
				}
			}
			displayProperty.setFeaturelist(newList);
			propertyDao.updateProperty(displayProperty);
			String message = "Property with id " + displayProperty.getId() + " has been updated successfully";
			facesContext.addMessage(null, new FacesMessage(message));
		} catch (Exception e) {
			String message = "An error has occured while updating the property (see log for details)";
			facesContext.addMessage(null, new FacesMessage(message));
		}
	}

	public void delete() {
		try {
			propertyDao.removeProperty(queryProperty.getId());
			String message = "Property with id " + displayProperty.getId() + " has been deleted successfully";
			facesContext.addMessage(null, new FacesMessage(message));
		} catch (Exception e) {
			String message = "An error has occured while deleting the property (see log for details)";
			facesContext.addMessage(null, new FacesMessage(message));
		}
	}

	public String lookup() {
		if (!conversation.isTransient()) {
			conversation.end();
		}
		Property property = propertyDao.getById(queryProperty.getId());
		if (property != null) {
			conversation.begin();
			displayProperty = property;
			// String message = "Property: " + displayProperty.getId() + " " +
			// displayProperty.getName() + " " + displayProperty.getFeatures() +
			// " has been looked up successfully";
			// facesContext.addMessage(null, new FacesMessage(message));
		} else {
			String message = "No such property exists!";
			facesContext.addMessage(null, new FacesMessage(message));
		}
		return "showproperty";
	}

	private void getall() {
		List<Property> properties = propertyDao.getProperties();
		if (properties != null) {
			propertyCatalog.setProperties(properties);
		} else {
			String message = "No properties have been added yet!";
			facesContext.addMessage(null, new FacesMessage(message));
		}
	}

	public String getPropertyname() {
		return propertyname;
	}

	public void setPropertyname(String propertyname) {
		this.propertyname = propertyname;
	}

	public String getLookupResponse() {
		return lookupResponse;
	}

	public void setLookupResponse(String lookupResponse) {
		this.lookupResponse = lookupResponse;
	}

	public PropertyCatalog getPropertyCatalog() {
		if (propertyCatalog == null) {
			propertyCatalog = new PropertyCatalog();
		}
		getall();
		return propertyCatalog;
	}

	public void setPropertyCatalog(PropertyCatalog propertyCatalog) {
		this.propertyCatalog = propertyCatalog;
	}

}
