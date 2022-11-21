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
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|ClassFormatException
import|;
end_import

begin_comment
comment|/**  * Argument validation.  *  * @since 6.7.0  */
end_comment

begin_class
specifier|public
class|class
name|Args
block|{
comment|/**      * Requires a specific value.      *      * @param value    The value to test.      * @param required The required value.      * @param message  The message prefix      * @return The value to test.      */
specifier|public
specifier|static
name|int
name|require
parameter_list|(
specifier|final
name|int
name|value
parameter_list|,
specifier|final
name|int
name|required
parameter_list|,
specifier|final
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|value
operator|!=
name|required
condition|)
block|{
throw|throw
operator|new
name|ClassFormatException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s [Value must be 0: %,d]"
argument_list|,
name|message
argument_list|,
name|value
argument_list|)
argument_list|)
throw|;
block|}
return|return
name|value
return|;
block|}
comment|/**      * Requires a 0 value.      *      * @param value   The value to test.      * @param message The message prefix      * @return The value to test.      */
specifier|public
specifier|static
name|int
name|require0
parameter_list|(
specifier|final
name|int
name|value
parameter_list|,
specifier|final
name|String
name|message
parameter_list|)
block|{
return|return
name|require
argument_list|(
name|value
argument_list|,
literal|0
argument_list|,
name|message
argument_list|)
return|;
block|}
comment|/**      * Requires a u1 value.      *      * @param value   The value to test.      * @param message The message prefix      * @return The value to test.      */
specifier|public
specifier|static
name|int
name|requireU1
parameter_list|(
specifier|final
name|int
name|value
parameter_list|,
specifier|final
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|value
operator|<
literal|0
operator|||
name|value
operator|>
name|Const
operator|.
name|MAX_BYTE
condition|)
block|{
throw|throw
operator|new
name|ClassFormatException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s [Value out of range (0 - %,d) for type u1: %,d]"
argument_list|,
name|message
argument_list|,
name|Const
operator|.
name|MAX_BYTE
argument_list|,
name|value
argument_list|)
argument_list|)
throw|;
block|}
return|return
name|value
return|;
block|}
comment|/**      * Requires a u2 value of at least {@code min} and not above {@code max}.      *      * @param value   The value to test.      * @param min     The minimum required u2 value.      * @param max     The maximum required u2 value.      * @param message The message prefix      * @return The value to test.      */
specifier|public
specifier|static
name|int
name|requireU2
parameter_list|(
specifier|final
name|int
name|value
parameter_list|,
specifier|final
name|int
name|min
parameter_list|,
specifier|final
name|int
name|max
parameter_list|,
specifier|final
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|max
operator|>
name|Const
operator|.
name|MAX_SHORT
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Programming error: max %,d> %,d"
argument_list|,
name|max
argument_list|,
name|Const
operator|.
name|MAX_SHORT
argument_list|)
argument_list|)
throw|;
block|}
if|if
condition|(
name|min
operator|<
literal|0
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Programming error: min %,d< 0"
argument_list|,
name|min
argument_list|)
argument_list|)
throw|;
block|}
if|if
condition|(
name|value
argument_list|<
name|min
operator|||
name|value
argument_list|>
name|max
condition|)
block|{
throw|throw
operator|new
name|ClassFormatException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s [Value out of range (%,d - %,d) for type u2: %,d]"
argument_list|,
name|message
argument_list|,
name|min
argument_list|,
name|Const
operator|.
name|MAX_SHORT
argument_list|,
name|value
argument_list|)
argument_list|)
throw|;
block|}
return|return
name|value
return|;
block|}
comment|/**      * Requires a u2 value of at least {@code min}.      *      * @param value   The value to test.      * @param min     The minimum required value.      * @param message The message prefix      * @return The value to test.      */
specifier|public
specifier|static
name|int
name|requireU2
parameter_list|(
specifier|final
name|int
name|value
parameter_list|,
specifier|final
name|int
name|min
parameter_list|,
specifier|final
name|String
name|message
parameter_list|)
block|{
return|return
name|requireU2
argument_list|(
name|value
argument_list|,
name|min
argument_list|,
name|Const
operator|.
name|MAX_SHORT
argument_list|,
name|message
argument_list|)
return|;
block|}
comment|/**      * Requires a u2 value.      *      * @param value   The value to test.      * @param message The message prefix      * @return The value to test.      */
specifier|public
specifier|static
name|int
name|requireU2
parameter_list|(
specifier|final
name|int
name|value
parameter_list|,
specifier|final
name|String
name|message
parameter_list|)
block|{
return|return
name|requireU2
argument_list|(
name|value
argument_list|,
literal|0
argument_list|,
name|message
argument_list|)
return|;
block|}
comment|/**      * Requires a u4 value of at least {@code min}.      *      * @param value   The value to test.      * @param min     The minimum required value.      * @param message The message prefix      * @return The value to test.      */
specifier|public
specifier|static
name|int
name|requireU4
parameter_list|(
specifier|final
name|int
name|value
parameter_list|,
specifier|final
name|int
name|min
parameter_list|,
specifier|final
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|min
operator|<
literal|0
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Programming error: min %,d< 0"
argument_list|,
name|min
argument_list|)
argument_list|)
throw|;
block|}
if|if
condition|(
name|value
operator|<
name|min
condition|)
block|{
throw|throw
operator|new
name|ClassFormatException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s [Value out of range (%,d - %,d) for type u2: %,d]"
argument_list|,
name|message
argument_list|,
name|min
argument_list|,
name|Integer
operator|.
name|MAX_VALUE
argument_list|,
name|value
operator|&
literal|0xFFFFFFFFL
argument_list|)
argument_list|)
throw|;
block|}
return|return
name|value
return|;
block|}
comment|/**      * Requires a u4 value.      *      * @param value   The value to test.      * @param message The message prefix      * @return The value to test.      */
specifier|public
specifier|static
name|int
name|requireU4
parameter_list|(
specifier|final
name|int
name|value
parameter_list|,
specifier|final
name|String
name|message
parameter_list|)
block|{
return|return
name|requireU4
argument_list|(
name|value
argument_list|,
literal|0
argument_list|,
name|message
argument_list|)
return|;
block|}
block|}
end_class

end_unit

