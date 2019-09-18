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

package com.i1314i.syncerplusredis.cmd.impl;

/**
 * @author Leon Chen
 * @since 2.1.0
 */
public class PFAddCommand extends GenericKeyCommand {

    private static final long serialVersionUID = 1L;

    private byte[][] elements;

    public PFAddCommand() {
    }

    public PFAddCommand(byte[] key, byte[][] elements) {
        super(key);
        this.elements = elements;
    }

    public byte[][] getElements() {
        return elements;
    }

    public void setElements(byte[][] elements) {
        this.elements = elements;
    }
}
