/*
 * Copyright 2016-2018 Leon Chen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.i1314i.syncerplusredis.replicator;


import com.i1314i.syncerplusredis.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Leon Chen
 * @since 2.1.0
 */
public class DefaultExceptionListener implements ExceptionListener {
    private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionListener.class);

    @Override
    public void handle(Replicator replicator, Throwable throwable, Event event) {
        logger.error("error on event [{}]", event, throwable);
    }
}
