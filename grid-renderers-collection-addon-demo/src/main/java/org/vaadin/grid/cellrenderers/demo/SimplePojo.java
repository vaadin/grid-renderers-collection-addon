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
    private Date date;
    private BigDecimal number;
    private Double stars;
    private String breakfastTime;

    public SimplePojo() {
    }

    public SimplePojo(final long id, final String description, final boolean yes, final Date date, final BigDecimal number, final Double stars,
            final String breakfastTime) {
        this.id = id;
        this.description = description;
        this.yes = yes;
        this.date = date;
        this.number = number;
        this.stars = (double) stars;
        this.breakfastTime = breakfastTime;
    }

    public Double getStars() {
        return this.stars;
    }

    public void setStars(final Double stars) {
        this.stars = stars;
    }

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public BigDecimal getNumber() {
        return this.number;
    }

    public void setNumber(final BigDecimal number) {
        this.number = number;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean isYes() {
        return this.yes;
    }

    public void setYes(final boolean yes) {
        this.yes = yes;
    }

    public boolean isTruth() {
        return this.truth;
    }

    public void setTruth(final boolean truth) {
        this.truth = truth;
    }

    public String getBreakfastTime() {
        return this.breakfastTime;
    }

    public void setBreakfastTime(final String breakfastTime) {
        this.breakfastTime = breakfastTime;
    }
}
