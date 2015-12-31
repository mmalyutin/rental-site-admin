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
package com.example.domain;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;


@Stateless
public class EJBPropertyDao implements PropertyDao {

	@Inject
	private EntityManager entityManager;

	@Override
	public Property getByName(String name) {
        try {
            Query query = entityManager.createQuery("select p from Property p where p.name = ?");
            query.setParameter(1, name);
            return (Property) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}
	
	

	@Override
	public Property getById(Long id) {
		Property property = entityManager.find(Property.class, id);
		if (property == null) {
			throw new IllegalArgumentException("Unknown property id");
		}
		return property;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Property> getProperties() {
		List<Property> props;
        try {
            Query query = entityManager.createQuery("select p from Property p");
//            query.setParameter(1, name);
            props = query.getResultList();
            return props;
        } catch (NoResultException e) {
            return null;
        }
	}



	@Override
	public void createProperty(Property property) {
		entityManager.persist(property);
	}

	@Override
	public void removeProperty(Long id) {
		Property property = entityManager.find(Property.class, id);
		if (property == null) {
			throw new IllegalArgumentException("Unknown property id");
		}
		entityManager.remove(property);
	}

	@Override
	public void updateProperty(Property property) {
		Property _property = entityManager.find(Property.class, property.getId());
		if (_property == null) {
			throw new IllegalArgumentException("Unknown property");
		}
		property = entityManager.merge(property);
	}

}
