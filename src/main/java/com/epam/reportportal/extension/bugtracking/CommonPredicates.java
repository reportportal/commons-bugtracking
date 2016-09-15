/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
 * 
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */ 
package com.epam.reportportal.extension.bugtracking;


import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Some common {@link org.apache.commons.collections.Predicate}
 *
 * @author Andrei Varabyeu
 */
public final class CommonPredicates {

    private CommonPredicates() {
        //static only
    }

    public static final Predicate<Collection<?>> IS_EMPTY = input -> null == input || input.isEmpty();

    public static final Predicate<Map<?, ?>> IS_MAP_EMPTY = input -> null == input || input.isEmpty();
}