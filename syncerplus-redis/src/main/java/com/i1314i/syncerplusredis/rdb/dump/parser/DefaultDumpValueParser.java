/*
 * Copyright 2016-2017 Leon Chen
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

package com.i1314i.syncerplusredis.rdb.dump.parser;


import com.i1314i.syncerplusredis.replicator.Replicator;
import com.i1314i.syncerplusredis.event.EventListener;
import com.i1314i.syncerplusredis.io.RedisInputStream;
import com.i1314i.syncerplusredis.rdb.DefaultRdbValueVisitor;
import com.i1314i.syncerplusredis.rdb.RdbValueVisitor;
import com.i1314i.syncerplusredis.rdb.datatype.KeyValuePair;
import com.i1314i.syncerplusredis.rdb.datatype.KeyValuePairs;
import com.i1314i.syncerplusredis.rdb.dump.datatype.DumpKeyValuePair;
import com.i1314i.syncerplusredis.util.objectutil.ByteArray;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import static com.i1314i.syncerplusredis.replicator.Constants.*;

/**
 * @author Leon Chen
 * @since 3.1.0
 */
public class DefaultDumpValueParser implements DumpValueParser {

    protected final Replicator replicator;
    protected final RdbValueVisitor valueVisitor;

    public DefaultDumpValueParser(Replicator replicator) {
        Objects.requireNonNull(replicator);
        this.replicator = replicator;
        this.valueVisitor = new DefaultRdbValueVisitor(replicator);
    }

    public void parse(DumpKeyValuePair kv, EventListener listener) {
        Objects.requireNonNull(listener).onEvent(replicator, parse(kv));
    }

    public KeyValuePair<?, ?> parse(DumpKeyValuePair kv) {
        Objects.requireNonNull(kv);
        try (RedisInputStream in = new RedisInputStream(new ByteArray(kv.getValue()))) {
            int valueType = in.read();
            switch (valueType) {
                case RDB_TYPE_STRING:
                    return KeyValuePairs.string(kv, valueVisitor.applyString(in, 0));
                case RDB_TYPE_LIST:
                    return KeyValuePairs.list(kv, valueVisitor.applyList(in, 0));
                case RDB_TYPE_SET:
                    return KeyValuePairs.set(kv, valueVisitor.applySet(in, 0));
                case RDB_TYPE_ZSET:
                    return KeyValuePairs.zset(kv, valueVisitor.applyZSet(in, 0));
                case RDB_TYPE_ZSET_2:
                    return KeyValuePairs.zset(kv, valueVisitor.applyZSet2(in, 0));
                case RDB_TYPE_HASH:
                    return KeyValuePairs.hash(kv, valueVisitor.applyHash(in, 0));
                case RDB_TYPE_HASH_ZIPMAP:
                    return KeyValuePairs.hash(kv, valueVisitor.applyHashZipMap(in, 0));
                case RDB_TYPE_LIST_ZIPLIST:
                    return KeyValuePairs.list(kv, valueVisitor.applyListZipList(in, 0));
                case RDB_TYPE_SET_INTSET:
                    return KeyValuePairs.set(kv, valueVisitor.applySetIntSet(in, 0));
                case RDB_TYPE_ZSET_ZIPLIST:
                    return KeyValuePairs.zset(kv, valueVisitor.applyZSetZipList(in, 0));
                case RDB_TYPE_HASH_ZIPLIST:
                    return KeyValuePairs.hash(kv, valueVisitor.applyHashZipList(in, 0));
                case RDB_TYPE_LIST_QUICKLIST:
                    return KeyValuePairs.list(kv, valueVisitor.applyListQuickList(in, 0));
                case RDB_TYPE_MODULE:
                    return KeyValuePairs.module(kv, valueVisitor.applyModule(in, 0));
                case RDB_TYPE_MODULE_2:
                    return KeyValuePairs.module(kv, valueVisitor.applyModule2(in, 0));
                case RDB_TYPE_STREAM_LISTPACKS:
                    return KeyValuePairs.stream(kv, valueVisitor.applyStreamListPacks(in, 0));
                default:
                    throw new AssertionError("unexpected value type:" + valueType);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
