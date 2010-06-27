/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://knowhowlab.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.osgilab.testing.commons.utils;

import org.osgi.framework.*;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * OSGi Services utilities class
 *
 * @author dmytro.pishchukhin
 * @version 1.0
 * @see org.osgi.framework.BundleContext
 * @see org.osgi.util.tracker.ServiceTracker
 * @see org.osgi.util.tracker.ServiceTrackerCustomizer
 * @see org.osgi.framework.Filter
 */
public class ServiceUtils {
    /**
     * Utility class. Only static methods are available.
     */
    private ServiceUtils() {
    }

    /**
     * Get ServiceReference by filter
     *
     * @param bc     BundleContext
     * @param filter filter
     * @return ServiceReference instance or <code>null</code>
     *
     * @throws NullPointerException If <code>bc</code> or <code>filter</code> are <code>null</code>
     */
    public static ServiceReference getServiceReference(BundleContext bc, Filter filter) {
        ServiceTracker tracker = new ServiceTracker(bc, filter, null);
        tracker.open();
        try {
            return tracker.getServiceReference();
        } finally {
            tracker.close();
        }
    }

    /**
     * Get ServiceReference by filter with timeout.
     *
     * @param bc      BundleContext
     * @param filter  filter
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @return ServiceReference instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code> or <code>filter</code> are <code>null</code>
     */
    public static ServiceReference getServiceReference(BundleContext bc, Filter filter, long timeout) {
        return getServiceReference(bc, filter, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Get ServiceReference by filter with timeout.
     *
     * @param bc       BundleContext
     * @param filter   filter
     * @param timeout  time interval to wait. If zero, the method will wait indefinately.
     * @param timeUnit time unit for the time interval
     * @return ServiceReference instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>filter</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     */
    public static ServiceReference getServiceReference(BundleContext bc, Filter filter, long timeout, TimeUnit timeUnit) {
        final ReentrantLock lock = new ReentrantLock();
        long timeoutInMillis = timeUnit.toMillis(timeout);
        ServiceTracker tracker = new ServiceTracker(bc, filter, new ServiceTrackerCustomizerWithLock(bc, lock));
        tracker.open();
        try {
            return waitForServiceReference(tracker, timeoutInMillis, lock);
        } catch (InterruptedException e) {
            return null;
        } finally {
            tracker.close();
        }
    }

    /**
     * Get ServiceReference by class name
     *
     * @param bc        BundleContext
     * @param className className
     * @return ServiceReference instance or <code>null</code>
     *
     * @throws NullPointerException If <code>bc</code> or <code>className</code> are <code>null</code>
     */
    public static ServiceReference getServiceReference(BundleContext bc, String className) {
        ServiceTracker tracker = new ServiceTracker(bc, className, null);
        tracker.open();
        try {
            return tracker.getServiceReference();
        } finally {
            tracker.close();
        }
    }

    /**
     * Get ServiceReference by class name with timeout.
     *
     * @param bc        BundleContext
     * @param className className
     * @param timeout   time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @return ServiceReference instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code> or <code>className</code> are <code>null</code>
     */
    public static ServiceReference getServiceReference(BundleContext bc, String className, long timeout) {
        return getServiceReference(bc, className, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Get ServiceReference by class name with timeout.
     *
     * @param bc        BundleContext
     * @param className className
     * @param timeout   time interval to wait. If zero, the method will wait indefinately.
     * @param timeUnit  time unit for the time interval
     * @return ServiceReference instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>className</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     */
    public static ServiceReference getServiceReference(BundleContext bc, String className, long timeout, TimeUnit timeUnit) {
        final ReentrantLock lock = new ReentrantLock();
        long timeoutInMillis = timeUnit.toMillis(timeout);
        ServiceTracker tracker = new ServiceTracker(bc, className, new ServiceTrackerCustomizerWithLock(bc, lock));
        tracker.open();
        try {
            return waitForServiceReference(tracker, timeoutInMillis, lock);
        } catch (InterruptedException e) {
            return null;
        } finally {
            tracker.close();
        }
    }

    /**
     * Get ServiceReference by class
     *
     * @param bc    BundleContext
     * @param clazz Class
     * @return ServiceReference instance or <code>null</code>
     *
     * @throws NullPointerException If <code>bc</code> or <code>clazz</code> are <code>null</code>
     */
    public static ServiceReference getServiceReference(BundleContext bc, Class clazz) {
        ServiceTracker tracker = new ServiceTracker(bc, clazz.getName(), null);
        tracker.open();
        try {
            return tracker.getServiceReference();
        } finally {
            tracker.close();
        }
    }

    /**
     * Get ServiceReference by class with timeout.
     *
     * @param bc      BundleContext
     * @param clazz   Class
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @return ServiceReference instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code> or <code>filter</code> are <code>null</code>
     */
    public static ServiceReference getServiceReference(BundleContext bc, Class clazz, long timeout) {
        return getServiceReference(bc, clazz, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Get ServiceReference by class with timeout.
     *
     * @param bc       BundleContext
     * @param clazz    Class
     * @param timeout  time interval to wait. If zero, the method will wait indefinately.
     * @param timeUnit time unit for the time interval
     * @return ServiceReference instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>filter</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     */
    public static ServiceReference getServiceReference(BundleContext bc, Class clazz, long timeout, TimeUnit timeUnit) {
        final ReentrantLock lock = new ReentrantLock();
        long timeoutInMillis = timeUnit.toMillis(timeout);
        ServiceTracker tracker = new ServiceTracker(bc, clazz.getName(), new ServiceTrackerCustomizerWithLock(bc, lock));
        tracker.open();
        try {
            return waitForServiceReference(tracker, timeoutInMillis, lock);
        } catch (InterruptedException e) {
            return null;
        } finally {
            tracker.close();
        }
    }

    /**
     * Get service instance by filter
     *
     * @param bc     BundleContext
     * @param filter filter
     * @return service instance or <code>null</code>
     *
     * @throws NullPointerException If <code>bc</code> or <code>filter</code> are <code>null</code>
     */
    public static Object getService(BundleContext bc, Filter filter) {
        ServiceTracker tracker = new ServiceTracker(bc, filter, null);
        tracker.open();
        try {
            return tracker.getService();
        } finally {
            tracker.close();
        }
    }

    /**
     * Get service instance by filter with timeout.
     *
     * @param bc      BundleContext
     * @param filter  filter
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @return service instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code> or <code>filter</code> are <code>null</code>
     */
    public static Object getService(BundleContext bc, Filter filter, long timeout) {
        return getService(bc, filter, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Get service instance by filter with timeout.
     *
     * @param bc       BundleContext
     * @param filter   filter
     * @param timeout  time interval to wait. If zero, the method will wait indefinately.
     * @param timeUnit time unit for the time interval
     * @return service instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>filter</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     */
    public static Object getService(BundleContext bc, Filter filter, long timeout, TimeUnit timeUnit) {
        ServiceTracker tracker = new ServiceTracker(bc, filter, null);
        tracker.open();
        try {
            return tracker.waitForService(timeUnit.toMillis(timeout));
        } catch (InterruptedException e) {
            return null;
        } finally {
            tracker.close();
        }
    }

    /**
     * Get service instance by class name
     *
     * @param bc        BundleContext
     * @param className className
     * @return service instance or <code>null</code>
     *
     * @throws NullPointerException If <code>bc</code> or <code>className</code> are <code>null</code>
     */
    public static Object getService(BundleContext bc, String className) {
        ServiceTracker tracker = new ServiceTracker(bc, className, null);
        tracker.open();
        try {
            return tracker.getService();
        } finally {
            tracker.close();
        }
    }

    /**
     * Get service instance by class name with timeout.
     *
     * @param bc        BundleContext
     * @param className className
     * @param timeout   time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @return service instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code> or <code>className</code> are <code>null</code>
     */
    public static Object getService(BundleContext bc, String className, long timeout) {
        return getService(bc, className, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Get service instance by className with timeout.
     *
     * @param bc       BundleContext
     * @param className   className
     * @param timeout  time interval to wait. If zero, the method will wait indefinately.
     * @param timeUnit time unit for the time interval
     * @return service instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>className</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     */
    public static Object getService(BundleContext bc, String className, long timeout, TimeUnit timeUnit) {
        ServiceTracker tracker = new ServiceTracker(bc, className, null);
        tracker.open();
        try {
            return tracker.waitForService(timeUnit.toMillis(timeout));
        } catch (InterruptedException e) {
            return null;
        } finally {
            tracker.close();
        }
    }

    /**
     * Get service instance by class
     *
     * @param bc    BundleContext
     * @param clazz Class
     * @return service instance instance or <code>null</code>
     *
     * @throws NullPointerException If <code>bc</code> or <code>clazz</code> are <code>null</code>
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz) {
        ServiceTracker tracker = new ServiceTracker(bc, clazz.getName(), null);
        tracker.open();
        try {
            //noinspection unchecked
            return (T) tracker.getService();
        } finally {
            tracker.close();
        }
    }

    /**
     * Get service instance by class with timeout.
     *
     * @param bc      BundleContext
     * @param clazz   Class
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @return service instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code> or <code>clazz</code> are <code>null</code>
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, long timeout) {
        return getService(bc, clazz, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Get service instance by class with timeout.
     *
     * @param bc       BundleContext
     * @param clazz    Class
     * @param timeout  time interval to wait. If zero, the method will wait indefinately.
     * @param timeUnit time unit for the time interval
     * @return service instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>clazz</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, long timeout, TimeUnit timeUnit) {
        ServiceTracker tracker = new ServiceTracker(bc, clazz.getName(), null);
        tracker.open();
        try {
            //noinspection unchecked
            return (T) tracker.waitForService(timeUnit.toMillis(timeout));
        } catch (InterruptedException e) {
            return null;
        } finally {
            tracker.close();
        }
    }

    /**
     * Get service instance by class
     *
     * @param bc     BundleContext
     * @param clazz  Class
     * @param filter filter
     * @return service instance instance or <code>null</code>
     *
     * @throws NullPointerException   If <code>bc</code> or <code>clazz</code> are <code>null</code>
     * @throws InvalidSyntaxException If <code>filter</code> contains an
     *                                invalid filter string that cannot be parsed
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, String filter) throws InvalidSyntaxException {
        return getService(bc, clazz, FrameworkUtil.createFilter(filter));
    }

    /**
     * Get service instance by class with timeout.
     *
     * @param bc      BundleContext
     * @param clazz   Class
     * @param filter  filter
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @return service instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code> or <code>clazz</code> are <code>null</code>
     * @throws InvalidSyntaxException   If <code>filter</code> contains an
     *                                  invalid filter string that cannot be parsed
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, String filter, long timeout) throws InvalidSyntaxException {
        return getService(bc, clazz, filter, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Get service instance by class with timeout.
     *
     * @param bc       BundleContext
     * @param clazz    Class
     * @param filter   filter
     * @param timeout  time interval to wait. If zero, the method will wait indefinately.
     * @param timeUnit time unit for the time interval
     * @return service instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>clazz</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     * @throws InvalidSyntaxException   If <code>filter</code> contains an
     *                                  invalid filter string that cannot be parsed
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, String filter, long timeout, TimeUnit timeUnit) throws InvalidSyntaxException {
        return getService(bc, clazz, FrameworkUtil.createFilter(filter), timeout, timeUnit);
    }

    /**
     * Get service instance by class
     *
     * @param bc     BundleContext
     * @param clazz  Class
     * @param filter filter
     * @return service instance instance or <code>null</code>
     *
     * @throws NullPointerException   If <code>bc</code> or <code>clazz</code> are <code>null</code>
     * @throws InvalidSyntaxException If <code>filter</code> contains an
     *                                invalid filter string that cannot be parsed
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, Filter filter) throws InvalidSyntaxException {
        ServiceTracker tracker = new ServiceTracker(bc, FilterUtils.create(clazz, filter), null);
        tracker.open();
        try {
            //noinspection unchecked
            return (T) tracker.getService();
        } finally {
            tracker.close();
        }
    }

    /**
     * Get service instance by class with timeout.
     *
     * @param bc      BundleContext
     * @param clazz   Class
     * @param filter  filter
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @return service instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code> or <code>clazz</code> are <code>null</code>
     * @throws InvalidSyntaxException   If <code>filter</code> contains an
     *                                  invalid filter string that cannot be parsed
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, Filter filter, long timeout) throws InvalidSyntaxException {
        return getService(bc, clazz, filter, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Get service instance by class with timeout.
     *
     * @param bc       BundleContext
     * @param clazz    Class
     * @param filter   filter
     * @param timeout  time interval to wait. If zero, the method will wait indefinately.
     * @param timeUnit time unit for the time interval
     * @return service instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>clazz</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     * @throws InvalidSyntaxException   If <code>filter</code> contains an
     *                                  invalid filter string that cannot be parsed
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, Filter filter, long timeout, TimeUnit timeUnit) throws InvalidSyntaxException {
        ServiceTracker tracker = new ServiceTracker(bc, FilterUtils.create(clazz, filter), null);
        tracker.open();
        try {
            //noinspection unchecked
            return (T) tracker.waitForService(timeUnit.toMillis(timeout));
        } catch (InterruptedException e) {
            return null;
        } finally {
            tracker.close();
        }
    }

    /**
     * Wait for at least one ServiceReference to be tracked by ServiceTracker
     *
     * @param tracker         ServiceTracker
     * @param timeoutInMillis time interval in milliseconds to wait.
     *                        If zero, the method will wait indefinately.
     * @param lock            external lock that is used to handle new service adding to ServiceTracker
     * @return ServiceReference instance or <code>null</code>
     *
     * @throws IllegalArgumentException If the value of timeout is negative.
     * @throws InterruptedException     If another thread has interrupted the current thread.
     */
    private static ServiceReference waitForServiceReference(ServiceTracker tracker, long timeoutInMillis, ReentrantLock lock)
            throws InterruptedException {
        if (timeoutInMillis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        ServiceReference reference = tracker.getServiceReference();
        if (reference == null) {
            lock.wait(timeoutInMillis);
            return tracker.getServiceReference();
        } else {
            return reference;
        }
    }

    /**
     * ServiceTrackerCustomizer with lock support.
     *
     * @see java.util.concurrent.locks.ReentrantLock
     * @see org.osgi.util.tracker.ServiceTrackerCustomizer
     */
    private static class ServiceTrackerCustomizerWithLock implements ServiceTrackerCustomizer {
        private final BundleContext bc;
        private final ReentrantLock lock;

        public ServiceTrackerCustomizerWithLock(BundleContext bc, ReentrantLock lock) {
            this.bc = bc;
            this.lock = lock;
        }

        public Object addingService(ServiceReference serviceReference) {
            try {
                return bc.getService(serviceReference);
            } finally {
                // uplock the lock
                lock.unlock();
            }
        }

        public void modifiedService(ServiceReference serviceReference, Object o) {
        }

        public void removedService(ServiceReference serviceReference, Object o) {
        }
    }
}
