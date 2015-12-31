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
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class StringArrayType implements UserType {

	@Override
	public Object assemble(Serializable cached, Object owner) {
		return cached;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value == null)
			return null;
		if (!(value instanceof String[]))
			throw new UnsupportedOperationException("can't convert " + value.getClass());
	
		return (String[]) value;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		if (!(value instanceof String[]))
			throw new UnsupportedOperationException("can't convert " + value.getClass());
		return (String[]) value;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == null) {
			if (y == null) return true;
			else return false;
		}
		return x.equals(y);
	}

	@Override
	public int hashCode(Object value) throws HibernateException {
		return value.hashCode();
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor sessionImpl, Object owner)
			throws HibernateException, SQLException {
		Array value = rs.getArray(names[0]);
		if (value == null)
			return null;
				
		String[] s = (String[])value.getArray();
		
		return s;
	}

	@Override
	public void nullSafeSet(PreparedStatement stmt,	Object value, int index, SessionImplementor sessionImpl)	throws HibernateException, SQLException {
		if (value==null) {
			stmt.setNull(index,	Types.ARRAY);
			return;
		}
		if	(!(value instanceof	String[])) throw new	UnsupportedOperationException("can't convert "+value.getClass());
		
		String[] data = (String[])value;		
		
		stmt.setArray(index, sessionImpl.connection().createArrayOf("varchar", data));
		
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	@Override
	public Class<String[]> returnedClass() {
		return String[].class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.ARRAY };
	}

}

