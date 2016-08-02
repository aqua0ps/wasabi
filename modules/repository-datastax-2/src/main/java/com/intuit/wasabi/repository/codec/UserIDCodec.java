/*******************************************************************************
 * Copyright 2016 Intuit
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.wasabi.repository.codec;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.utils.Bytes;
import com.intuit.wasabi.assignmentobjects.User;

public class UserIDCodec extends TypeCodec<User.ID> {

    private final Charset charset = Charset.forName("UTF-8");

    public UserIDCodec() {
        super(DataType.text(), User.ID.class);
    }

    @Override
    public String format(User.ID value) {
        if (value == null)
            return "NULL";
        return value.toString();
    }

    @Override
    public User.ID parse(String value) {
        if (value == null )
            return null;

        return User.ID.valueOf(value);
    }

    @Override
    public User.ID deserialize(ByteBuffer bytes, ProtocolVersion protocolVersion) {
        if (bytes == null ||bytes.remaining() == 0 )
            return null;
        return User.ID.valueOf(new String(Bytes.getArray(bytes), charset));
    }

    @Override
    public ByteBuffer serialize(User.ID value, ProtocolVersion protocolVersion) {
        return value == null ? null : ByteBuffer.wrap(value.toString().getBytes(charset));
    }

}