/*
 * Copyright (c) 2006, KNOPFLERFISH project
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
package org.knopflerfish.bundle.endurance_test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import org.knopflerfish.service.bundleEnd7_test.*;

class Test6 implements EnduranceTest {

  private static int NUM_LISTENERS = 1000;
  private BundleContext bc;
  private ServiceTracker tracker;
  
  Test6(BundleContext bc) {
    this.bc = bc;
  }
  
  public void prepare() {
    tracker = new ServiceTracker(bc, Control.class.getName(), null); 
    tracker.open();
  }
  
  private static class MyServiceListener implements ServiceListener {
    public void serviceChanged(ServiceEvent event) {
      // do nothing.
    }
  }

  public boolean runTest() {
    ServiceListener[] listeners = new ServiceListener[NUM_LISTENERS];
    for (int i = 0; i < NUM_LISTENERS; i++) {
      listeners[i] = new MyServiceListener();
      bc.addServiceListener(listeners[i]);
    }
    
    Control control = (Control)tracker.getService();
    control.register();
    control.unregister();
    
    for (int i = 0; i < NUM_LISTENERS; i++) {
      bc.removeServiceListener(listeners[i]);
    }
    
    return true;
  }

  public void cleanup() {
    // clean up in test 7
    tracker.close();
  }

  public int getNoRuns() {
    return 1;
  }

  public String testName() {
    return "ServiceEvent listeners";
  }
}
