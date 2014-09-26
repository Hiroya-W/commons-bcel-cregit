begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|generic
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
name|Constants
import|;
end_import

begin_comment
comment|/**   * Denotes basic type such as int.  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|BasicType
extends|extends
name|Type
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|6546010740455512176L
decl_stmt|;
comment|/**      * Constructor for basic types such as int, long, `void'      *      * @param type one of T_INT, T_BOOLEAN, ..., T_VOID      * @see org.apache.bcel.Constants      */
name|BasicType
parameter_list|(
name|byte
name|type
parameter_list|)
block|{
name|super
argument_list|(
name|type
argument_list|,
name|Constants
operator|.
name|SHORT_TYPE_NAMES
index|[
name|type
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|type
operator|<
name|Constants
operator|.
name|T_BOOLEAN
operator|)
operator|||
operator|(
name|type
operator|>
name|Constants
operator|.
name|T_VOID
operator|)
condition|)
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Invalid type: "
operator|+
name|type
argument_list|)
throw|;
block|}
block|}
specifier|public
specifier|static
name|BasicType
name|getType
parameter_list|(
name|byte
name|type
parameter_list|)
block|{
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|Constants
operator|.
name|T_VOID
case|:
return|return
name|VOID
return|;
case|case
name|Constants
operator|.
name|T_BOOLEAN
case|:
return|return
name|BOOLEAN
return|;
case|case
name|Constants
operator|.
name|T_BYTE
case|:
return|return
name|BYTE
return|;
case|case
name|Constants
operator|.
name|T_SHORT
case|:
return|return
name|SHORT
return|;
case|case
name|Constants
operator|.
name|T_CHAR
case|:
return|return
name|CHAR
return|;
case|case
name|Constants
operator|.
name|T_INT
case|:
return|return
name|INT
return|;
case|case
name|Constants
operator|.
name|T_LONG
case|:
return|return
name|LONG
return|;
case|case
name|Constants
operator|.
name|T_DOUBLE
case|:
return|return
name|DOUBLE
return|;
case|case
name|Constants
operator|.
name|T_FLOAT
case|:
return|return
name|FLOAT
return|;
default|default:
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Invalid type: "
operator|+
name|type
argument_list|)
throw|;
block|}
block|}
comment|/** @return a hash code value for the object.      */
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|type
return|;
block|}
comment|/** @return true if both type objects refer to the same type      */
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|_type
parameter_list|)
block|{
return|return
operator|(
name|_type
operator|instanceof
name|BasicType
operator|)
condition|?
operator|(
operator|(
name|BasicType
operator|)
name|_type
operator|)
operator|.
name|type
operator|==
name|this
operator|.
name|type
else|:
literal|false
return|;
block|}
block|}
end_class

end_unit

