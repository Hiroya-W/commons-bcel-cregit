begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataInputStream
import|;
end_import

begin_comment
comment|/**  * Utility class that implements a sequence of bytes which can be read via the 'readByte()' method. This is used to  * implement a wrapper for the Java byte code stream to gain some more readability.  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ByteSequence
extends|extends
name|DataInputStream
block|{
specifier|private
specifier|static
specifier|final
class|class
name|ByteArrayStream
extends|extends
name|ByteArrayInputStream
block|{
name|ByteArrayStream
parameter_list|(
specifier|final
name|byte
index|[]
name|bytes
parameter_list|)
block|{
name|super
argument_list|(
name|bytes
argument_list|)
expr_stmt|;
block|}
name|int
name|getPosition
parameter_list|()
block|{
comment|// pos is protected in ByteArrayInputStream
return|return
name|pos
return|;
block|}
name|void
name|unreadByte
parameter_list|()
block|{
if|if
condition|(
name|pos
operator|>
literal|0
condition|)
block|{
name|pos
operator|--
expr_stmt|;
block|}
block|}
block|}
specifier|private
specifier|final
name|ByteArrayStream
name|byteStream
decl_stmt|;
specifier|public
name|ByteSequence
parameter_list|(
specifier|final
name|byte
index|[]
name|bytes
parameter_list|)
block|{
name|super
argument_list|(
operator|new
name|ByteArrayStream
argument_list|(
name|bytes
argument_list|)
argument_list|)
expr_stmt|;
name|byteStream
operator|=
operator|(
name|ByteArrayStream
operator|)
name|in
expr_stmt|;
block|}
specifier|public
name|int
name|getIndex
parameter_list|()
block|{
return|return
name|byteStream
operator|.
name|getPosition
argument_list|()
return|;
block|}
name|void
name|unreadByte
parameter_list|()
block|{
name|byteStream
operator|.
name|unreadByte
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

