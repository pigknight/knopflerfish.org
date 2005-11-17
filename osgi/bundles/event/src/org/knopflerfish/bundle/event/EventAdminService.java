/*
 * @(#)EventAdminService.java        1.0 2005/06/28
 *
 * Copyright (c) 2003-2005 Gatespace telematics AB
 * Otterhallegatan 2, 41670,Gothenburgh, Sweden.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Gatespace telematics AB. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Gatespace telematics AB.
 */
package org.knopflerfish.bundle.event;

import java.util.Calendar;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;

import org.knopflerfish.service.log.LogRef;

/**
 * Default implementation of the EventAdmin interface this is a singleton class
 * and should always be active. The implementation is responsible for track
 * eventhandlers and check their permissions. It will also
 * host two threads sending diffrent types of data. If an eventhandler is subscribed to the
 * published event the EventAdmin service will put the event on one of the two internal sendstacks
 * depending on what type of deliverance the event requires.
 *
 * @author Magnus Klack (refactoring by Bj�rn Andersson)
 */
public class EventAdminService implements EventAdmin {

  /** the local representation of the bundle context */
  private BundleContext bundleContext;

  /** variable holding the synchronus send procedure */
  private QueueHandler queueHandlerSynch;

  /** variable holding the asynchronus send procedure */
  private QueueHandler queueHandlerAsynch;

  private Object semaphore = new Object();

  /**
   * the constructor use this to create a new Event admin service.
   *
   * @param context the BundleContext
   */
  public EventAdminService(BundleContext context) {
    synchronized(this){
      /* assign the context to the local variable */
      bundleContext = context;

      /* create the asynchronus queue handler */
      queueHandlerAsynch = new QueueHandler();
      /* start the handler */
      queueHandlerAsynch.start();

      new MultiListener(this, context);
    }
  }

  /**
   * This method should be used when an asynchronus events are to be
   * published.
   *
   * @param event the event to publish
   */
  public void postEvent(Event event) {
    try {
      queueHandlerAsynch.addEvent(new InternalAdminEvent(event, getReferences()));
    } catch(Exception e){
      Activator.log.error("Unknown exception in postEvent():", e);
    }
  }

  /**
   * This method should be used when synchronous events are to be published
   *
   * @param event the event to publish
   * @author Magnus Klack
   */
  public void sendEvent(Event event) {
    try {
      new InternalAdminEvent(event, getReferences()).deliver();
    } catch(Exception e){
      Activator.log.error("Unknown exception in sendEvent():", e);
    }
  }

  /**
   * returns the servicereferences
   *
   * @return ServiceReferences[] array if any else null
   * @throws InvalidSyntaxException if syntax error
   */
  ServiceReference[] getReferences() {
    try {
      ServiceReference[] refs = bundleContext.getServiceReferences(EventHandler.class.getName(), null);
      return refs;
    } catch (InvalidSyntaxException ignore) {
      // What? We're not even using a filter!
      return null;
    } catch (IllegalStateException e) {
      // The bundleContext is invalid. We're probably being stopped.
      return null;
    }
  }

  void stop() {
    //queueHandlerSynch.stopIt();
    queueHandlerAsynch.stopIt();
  }

}