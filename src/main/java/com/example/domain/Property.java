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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

@Entity
@Table(name = "Properties")
public class Property implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@Type(type = "com.example.domain.StringArrayType")
	@Column(name = "feature_list", columnDefinition = "varchar[]")
	private String[] featurelist;
	
	@Column(name = "geo_coordinates", columnDefinition = "Geometry")
	private Point geo_coordinates;
	
	private Integer zoomlevel;
	
	// private Address address;

	// private Owner owner;

	// private Schedule schedule;

	// private Collection<Images> images;	
	
	/******************************/
	/* Transient properties begin */
	/******************************/
	@Transient
	private boolean[] featureEditable;

	@Transient
	private String featurelistadd;

	@Transient
	private Feature[] features;
	
	@Transient
	private String mapUrl;

	/******************************/
	/* Transient properties end */
	/******************************/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getFeaturelist() {
		return featurelist;
	}

	public void setFeaturelist(String[] featurelist) {
		this.featurelist = featurelist;
		this.features = new Feature[featurelist.length];
		int i = 0;
		for (String s : featurelist) {
			features[i] = new Feature(i, s);
			i++;
		}
	}
	
	public Point getGeo_coordinates() {
		return geo_coordinates;
	}

	public void setGeo_coordinates(Point geo_coordinates) {
		this.geo_coordinates = geo_coordinates;
	}
	
	public Integer getZoomlevel() {
		if (zoomlevel == null) {
			zoomlevel = new Integer(0);
		}
		return zoomlevel;
	}

	public void setZoomlevel(Integer zoomlevel) {
		this.zoomlevel = zoomlevel;
	}

	
	/******************************/
	/* Transient properties begin */
	/******************************/
	
	public boolean[] getFeatureEditable() {
		if (featureEditable == null) {
			featureEditable = new boolean[featurelist.length];
		}
		return featureEditable;
	}

	public void setFeatureEditable(boolean[] featureEditable) {
		this.featureEditable = featureEditable;
	}

	public String getFeaturelistadd() {
		return featurelistadd;
	}

	public void setFeaturelistadd(String featurelistadd) {
		this.featurelistadd = featurelistadd;
	}

	public String addAction(String destination) {
		doAdd();
		return destination;
	}


	private void doAdd() {
		int newlength = 1;
		if (features != null) {
			newlength = newlength + features.length;
		}
		Feature[] newFeatures = new Feature[newlength];
		int i = 0;
		if (features != null) {
			for (Feature f : features) {
				newFeatures[i] = f;
				newFeatures[i].setEditable(false);
				i++;
			}
		}
		newFeatures[i] = new Feature(i, "");
		newFeatures[i].setEditable(true);
		this.features = newFeatures;
		return;
	}

	public String deleteAction(int index) {
		doDelete(index);
		return "showproperty";
	}
	
	public String deleteAction2(int index) {
		doDelete(index);
		return "propertycreate";
	}
	
	private void doDelete(int index) {
		String[] existingList = this.getFeaturelist();
		if (features != null) {
			int newlen = features.length - 1;
			String[] newList = new String[newlen];
			Feature[] newFlist = new Feature[newlen];
			int i = 0;
			for (Feature f : features) {
				if (existingList != null && f.getFeatureNumber() < existingList.length
						&& f.getFeatureNumber() != index) {
					f.setFeatureNumber(i);
					newList[i] = f.getFeatureDescription();
					f.setEditable(false);
					newFlist[i] = f;
					i++;
				}
			}
			this.setFeatures(newFlist);
			this.setFeaturelist(newList);
		}
	}

	public String editAction(int index) {
		getFeatures()[index].setEditable(true);
		return "showproperty";
	}
	
	public String editAction2(int index) {
		getFeatures()[index].setEditable(true);
		return "propertycreate";
	}

	public Feature[] getFeatures() {
		if (this.features == null && featurelist != null && featurelist.length > 0) {
			this.features = new Feature[featurelist.length];
			int i = 0;
			for (String s : featurelist) {
				features[i] = new Feature(i, s);
				i++;
			}
		}
		return features;
	}

	public void setFeatures(Feature[] features) {
		this.features = features;
	}

	
	public String getMapUrl() {
		Geometry geom = (Geometry) geo_coordinates;
		String coords = geom.toText().replace("POINT (", "");
		coords = coords.replace(")", "");
		Integer zl = 20 - getZoomlevel();
		mapUrl = "https://www.google.com/maps/embed/v1/view?center=" + coords + "&zoom=" + zl + "&maptype=satellite&key=AIzaSyBS0-IfvXl3JYDb6-y5X2S70sOoPSMU_CQ";
		return mapUrl;
	}

	/******************************/
	/* Transient properties end */
	/******************************/

	public static class Feature {
		int featureNumber;
		String featureDescription;

		boolean editable;

		public Feature() {
			super();
		}

		public Feature(int featureNumber, String featureDescription) {
			super();
			this.featureNumber = featureNumber;
			this.featureDescription = featureDescription;
			this.editable = false;
		}

		public boolean isEditable() {
			return editable;
		}

		public void setEditable(boolean editable) {
			this.editable = editable;
		}

		public int getFeatureNumber() {
			return featureNumber;
		}

		public void setFeatureNumber(int featureNumber) {
			this.featureNumber = featureNumber;
		}

		public String getFeatureDescription() {
			return featureDescription;
		}

		public void setFeatureDescription(String featureDescription) {
			this.featureDescription = featureDescription;
		}

	}

}
