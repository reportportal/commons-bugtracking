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
/*
 * This file is part of Report Portal.
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.epam.reportportal.extension.bugtracking;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

/**
 * @author Andrei Varabyeu
 */
public class CommonPredicatesTest {

	@Test
	public void testCollectionEmpty() {
		Assert.assertTrue("IS Empty Collection Predicate Failed", CommonPredicates.IS_EMPTY.test(Collections.emptyList()));
		Assert.assertFalse("IS Empty Collection Predicate NOT Failed", CommonPredicates.IS_EMPTY.test(Collections.singletonList("item")));
	}

	@Test
	public void testMapCollectionEmpty() {
		Assert.assertTrue("IS Empty Map Predicate Failed", CommonPredicates.IS_MAP_EMPTY.test(Collections.emptyMap()));
		Assert.assertFalse("IS Empty Map Predicate NOT Failed",
				CommonPredicates.IS_MAP_EMPTY.test(Collections.singletonMap("itemKey", "itemValue")));
	}
}
