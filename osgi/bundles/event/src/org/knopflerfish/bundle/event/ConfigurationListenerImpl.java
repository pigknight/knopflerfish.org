/*
 * Copyright (c) 2005-2013, KNOPFLERFISH project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following
 * conditions are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution.
 *
 * - Neither the name of the KNOPFLERFISH project nor the names of its
 *   contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.knopflerfish.bundle.event;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationEvent;
import org.osgi.service.cm.ConfigurationListener;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;

/**
 * Listen for ConfigurationEvent
 *
 * @author Bj\u00f6rn Andersson
 */
public class ConfigurationListenerImpl implements ConfigurationListener {
  private final static String PREFIX = "org/osgi/service/cm/ConfigurationEvent/";
  private final static String CM_UPDATED_TOPIC = PREFIX + "CM_UPDATED";
  private final static String CM_DELETED_TOPIC = PREFIX + "CM_DELETED";
  ServiceRegistration<ConfigurationListener> r = null;

  void start() {
    r = Activator.bc.registerService(ConfigurationListener.class, this, null);
  }

  void stop() {
    if(r != null) {
      r.unregister();
      r = null;
    }
  }

  public void configurationEvent(ConfigurationEvent event) {
    Map<String,Object> props = new HashMap<String,Object>();
    String topic = null;
    boolean knownMessageType = true;
    switch (event.getType()) {
    case ConfigurationEvent.CM_UPDATED:
      topic = CM_UPDATED_TOPIC;
      break;
    case ConfigurationEvent.CM_DELETED:
      topic = CM_DELETED_TOPIC;
      break;
    default:
      knownMessageType = false;
      break;
    }

    /* Stores the properties of the event in the dictionary, if the event is known */
    if (knownMessageType) {
      if(!Activator.handlerTracker.anyHandlersMatching(topic)) {
        return;
      }
      putProp(props, EventConstants.EVENT, event);
      putProp(props, "cm.factoryPid", event.getFactoryPid());
      putProp(props, "cm.pid", event.getPid());
      putProp(props, "service", event.getReference());
      putProp(props, "service.id", event.getReference().getProperty(Constants.SERVICE_ID));
      putProp(props, "service.objectClass", event.getReference().getProperty(Constants.OBJECTCLASS));
      putProp(props, "service.pid", event.getReference().getProperty(Constants.SERVICE_PID));

      try {
        Activator.eventAdmin.postEvent(new Event(topic, props));
      } catch (Exception e) {
        Activator.log.error("EXCEPTION in configurationEvent()", e);
      }
    } else {
      Activator.log.error("Recieved unknown configuration event (type="
                +event.getType() +"), discarding");
    }
  }

  private void putProp(Map<String, Object> props, String key, Object value) {
    if (value != null) {
      props.put(key, value);
    }
  }

}
