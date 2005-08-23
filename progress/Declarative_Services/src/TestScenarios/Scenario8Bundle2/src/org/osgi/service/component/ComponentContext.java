/*
 * $Header: /cvshome/build/org.osgi.service.component/src/org/osgi/service/component/ComponentContext.java,v 1.16 2005/07/13 12:40:03 pkriens Exp $
 *
 * Copyright (c) OSGi Alliance (2004, 2005). All Rights Reserved.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this 
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html.
 */
package org.osgi.service.component;

import java.util.Dictionary;

import org.osgi.framework.*;

/**
 * A ComponentContext object is used by an activated Service Component
 * configuration to interact with its execution context including locating
 * services by reference name. Each activated Service Component configuration
 * has a unique ComponentContext.
 * 
 * <p>
 * A component's implementation class may optionaly implement an activate
 * method:
 * 
 * <pre>
 * protected void activate(ComponentContext context);
 * </pre>
 * 
 * If a component implements this method, this method will be called when a
 * component configuration is activated to provide the component configuration's
 * ComponentContext object.
 * 
 * <p>
 * A component's implementation class may optionaly implement a deactivate
 * method:
 * 
 * <pre>
 * protected void deactivate(ComponentContext context);
 * </pre>
 * 
 * If a component implements this method, this method will be called when the
 * component configuration is deactivated.
 * 
 * <p>
 * The activate and deactivate methods will be called using reflection and must
 * be protected or public accessible. These methods do not need to be public
 * methods so that they do not appear as public methods on the component's
 * provided service object. The methods will be located by looking through the
 * component's implementation class hierarchy for the first declaration of the
 * method. If the method is declared protected or public, the method will be
 * called.
 * 
 * @version $Revision: 1.16 $
 */
public interface ComponentContext {
	/**
	 * Returns the component properties for this ComponentContext.
	 * 
	 * @return The properties for this ComponentContext. The properties are read
	 *         only and cannot be modified.
	 */
	public Dictionary getProperties();

	/**
	 * Returns the service object for the specified reference name.
	 * 
	 * <p>
	 * If the cardinality of the reference is <code>0..n</code> or
	 * <code>1..n</code> and multiple services are bound to the reference, the
	 * service with the highest ranking (as specified in its
	 * <code>Constants.SERVICE_RANKING</code> property) is returned.
	 * If there is a tie in ranking, the service with the lowest service ID (as
	 * specified in its <code>Constants.SERVICE_ID</code> property); that is, the
	 * service that was registered first is returned.

	 * @param name The name of a reference as specified in a
	 *        <code>reference</code> element in this component's description.
	 * @return A service object for the referenced service or <code>null</code>
	 *         if the reference cardinality is <code>0..1</code> or
	 *         <code>0..n</code> and no bound service is available.
	 * @throws ComponentException If the Service Component Runtime catches an
	 *         exception while activating the bound service.
	 */
	public Object locateService(String name);

	/**
	 * Returns the service objects for the specified reference name.
	 * 
	 * @param name The name of a reference as specified in a
	 *        <code>reference</code> element in this component's description.
	 * @return An array of service objects for the referenced service or
	 *         <code>null</code> if the reference cardinality is
	 *         <code>0..1</code> or <code>0..n</code> and no bound service
	 *         is available.
	 * @throws ComponentException If the Service Component Runtime catches an
	 *         exception while activating a bound service.
	 */
	public Object[] locateServices(String name);

	/**
	 * Returns the BundleContext of the bundle which contains this component.
	 * 
	 * @return The BundleContext of the bundle containing this component.
	 */
	public BundleContext getBundleContext();

	/**
	 * If this component configuration is registered as a service using the
	 * <code>servicefactory=&quot;true&quot;</code> attribute, then this
	 * method returns the bundle using the service provided by this component
	 * configuration.
	 * <p>
	 * This method will return <code>null</code> if:
	 * <ul>
	 * <li>This component is not a service, then no bundle can be using it as a
	 * service.
	 * <li>This component is a service but did not specify the
	 * <code>servicefactory=&quot;true&quot;</code> attribute, then all
	 * bundles using the service provided by this component will share this
	 * component configuration.
	 * <li>The service provided by this component is not currently being used
	 * by any bundle.
	 * </ul>
	 * 
	 * @return The bundle using this component configuration as a service or
	 *         <code>null</code>.
	 */
	public Bundle getUsingBundle();

	/**
	 * Returns the ComponentInstance object for this component configuration.
	 * 
	 * @return The ComponentInstance object for this component configuration.
	 */
	public ComponentInstance getComponentInstance();

	/**
	 * Enables the specified component name. The specified component name must
	 * be in the same bundle as this component.
	 * 
	 * @param name The name of a component or <code>null</code> to indicate
	 *        all components in the bundle.
	 */
	public void enableComponent(String name);

	/**
	 * Disables the specified component name. The specified component name must
	 * be in the same bundle as this component.
	 * 
	 * @param name The name of a component.
	 */
	public void disableComponent(String name);

	/**
	 * If this component configuration is registered as a service using the
	 * <code>service</code> element, then this method returns the service
	 * reference of the service provided by this component configuration.
	 * <p>
	 * This method will return <code>null</code> if this component
	 * configuration is not registered as a service.
	 * 
	 * @return The <code>ServiceReference</code> object for this component
	 *         configuration or <code>null</code> if this component
	 *         configuration is not registered as a service.
	 */
	public ServiceReference getServiceReference();

}