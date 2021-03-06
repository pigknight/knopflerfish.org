/*
 * Copyright (c) OSGi Alliance (2008, 2013). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.osgi.service.blueprint.reflect;

import org.osgi.annotation.versioning.ConsumerType;

/**
 * Metadata for a value that cannot {@code null}. All Metadata subtypes extend
 * this type except for {@link NullMetadata}.
 * 
 * <p>
 * This Metadata type is used for keys in Maps because they cannot be
 * {@code null}.
 * 
 * @ThreadSafe
 * @author $Id: 4c9fab2aad74af7e399ebd57adf3acbd705a81c1 $
 */
@ConsumerType
public interface NonNullMetadata extends Metadata {
	// marker interface
}
