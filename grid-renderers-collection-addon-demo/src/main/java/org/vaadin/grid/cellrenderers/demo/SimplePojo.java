/*
 * Copyright 2000-2014 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.vaadin.grid.cellrenderers.demo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * Provides various helper methods for connectors. Meant for internal use.
 *
 * @author Vaadin Ltd
 */
public class SimplePojo implements Serializable {
    private long id;
    private String description;
    private boolean yes;
    private boolean truth;
    private LocalDate date;
    private BigDecimal number;
    private Double stars;
    private Integer choice;
    private byte[] image;
    private boolean action = false;
	private boolean starsChanged;
    
    public SimplePojo() {
    }

    public SimplePojo(long id, String description, boolean yes, LocalDate date, BigDecimal number, Double stars, Integer choice) {
        this.id = id;
        this.description = description;
        this.yes = yes;
        this.date = date;
        this.choice = choice;
        this.number = number;
		if (stars > 1.0) this.action = true;
		this.stars =  (double) stars;
    }

    public boolean getAction() {
    	return action;
    }
    
    public void setAction(Boolean action) {
    	this.action = action;
    }
    
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}

	public Double getStars() {
		return stars;
	}
	
	public void setStars(Double stars) {
		if (stars > 1.0) this.action = true;
		else this.action = false;
		this.stars = stars;
		setStarsChanged();
	}
	
	
	public void setStarsChanged() {
		this.starsChanged = true;		
	}
	
	public boolean isStarsChanged() {
		boolean isChanged = starsChanged;
		starsChanged = false; 
		return isChanged;
	}
	
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public Integer getChoice() {
        return choice;
    }

    public void setChoice(Integer choice) {
        this.choice = choice;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isYes() {
        return yes;
    }

    public void setYes(boolean yes) {
        this.yes = yes;
    }

    public boolean isTruth() {
        return truth;
    }

    public void setTruth(boolean truth) {
        this.truth = truth;
    }
}
