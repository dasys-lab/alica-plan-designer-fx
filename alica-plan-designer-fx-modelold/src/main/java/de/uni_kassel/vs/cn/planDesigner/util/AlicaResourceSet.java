// Copyright 2009 Distributed Systems Group, University of Kassel
// This program is distributed under the GNU Lesser General Public License (LGPL).
//
// This file is part of the Carpe Noctem Software Framework.
//
//    The Carpe Noctem Software Framework is free software: you can redistribute it and/or modify
//    it under the terms of the GNU Lesser General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    The Carpe Noctem Software Framework is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU Lesser General Public License for more details.
package de.uni_kassel.vs.cn.generator;

import de.uni_kassel.vs.cn.generator.configuration.Configuration;
import de.uni_kassel.vs.cn.generator.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.generator.plugin.PluginManager;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.util.AlicaSerializationHelper;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import java.io.File;
import java.util.Map;


public class AlicaResourceSet extends ResourceSetImpl {

	// SINGLETON
	private static volatile  AlicaResourceSet instance;
	public static AlicaResourceSet getInstance() {
		if (instance == null) {
			synchronized (AlicaResourceSet.class) {
				if (instance == null) {
					instance = new AlicaResourceSet();
					instance.getResources().forEach(e -> e.setTrackingModification(true));
				}
			}
		}
		return instance;
	}

	private AlicaResourceSet() {
		super();
		this.setURIConverter(new ExtensibleURIConverterImpl() {
			@Override
			public URI normalize(URI uri) {
				String fragment = uri.fragment();
				String query = uri.query();
				URI trimmedURI = uri.trimFragment().trimQuery();
				URI result = this.getInternalURIMap().getURI(trimmedURI);
				String scheme = result.scheme();
				if (scheme == null) {
					if (result.hasAbsolutePath()) {
						result = URI.createURI("file:" + result);
					} else {
						Configuration configuration = ConfigurationManager.getInstance().getActiveConfiguration();
						if (result.toString().contains(".beh") || result.toString().contains(".pml") || result.toString().contains(".pty")) {
							result = URI.createFileURI((new File(configuration.getPlansPath() + "/" + result)).getAbsolutePath());
						} else {
							result = URI.createFileURI((new File(configuration.getTasksPath() + "/" + result)).getAbsolutePath());
						}
					}
				}

				if (result == trimmedURI) {
					return uri;
				} else {
					if (query != null) {
						result = result.appendQuery(query);
					}

					if (fragment != null) {
						result = result.appendFragment(fragment);
					}

					return this.normalize(result);
				}
			}
		});
	}
	
	@Override
	public Map<Object, Object> getLoadOptions() {
		if (loadOptions == null) {
			loadOptions = AlicaSerializationHelper.getInstance().getLoadSaveOptions();
		}

		return loadOptions;
	}
	
	
}
