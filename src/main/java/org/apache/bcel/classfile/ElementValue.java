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
name|classfile
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataInput
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_comment
comment|/**  * @since 6.0  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|ElementValue
block|{
comment|/**      * @deprecated (since 6.0) will be made private and final; do not access directly, use getter      */
annotation|@
name|java
operator|.
name|lang
operator|.
name|Deprecated
specifier|protected
name|int
name|type
decl_stmt|;
comment|// TODO should be final
comment|/**      * @deprecated (since 6.0) will be made private and final; do not access directly, use getter      */
annotation|@
name|java
operator|.
name|lang
operator|.
name|Deprecated
specifier|protected
name|ConstantPool
name|cpool
decl_stmt|;
comment|// TODO should be final
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|stringifyValue
argument_list|()
return|;
block|}
specifier|protected
name|ElementValue
parameter_list|(
specifier|final
name|int
name|type
parameter_list|,
specifier|final
name|ConstantPool
name|cpool
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
name|this
operator|.
name|cpool
operator|=
name|cpool
expr_stmt|;
block|}
specifier|public
name|int
name|getElementValueType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
specifier|public
specifier|abstract
name|String
name|stringifyValue
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|void
name|dump
parameter_list|(
name|DataOutputStream
name|dos
parameter_list|)
throws|throws
name|IOException
function_decl|;
specifier|public
specifier|static
specifier|final
name|byte
name|STRING
init|=
literal|'s'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|byte
name|ENUM_CONSTANT
init|=
literal|'e'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|byte
name|CLASS
init|=
literal|'c'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|byte
name|ANNOTATION
init|=
literal|'@'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|byte
name|ARRAY
init|=
literal|'['
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|byte
name|PRIMITIVE_INT
init|=
literal|'I'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|byte
name|PRIMITIVE_BYTE
init|=
literal|'B'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|byte
name|PRIMITIVE_CHAR
init|=
literal|'C'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|byte
name|PRIMITIVE_DOUBLE
init|=
literal|'D'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|byte
name|PRIMITIVE_FLOAT
init|=
literal|'F'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|byte
name|PRIMITIVE_LONG
init|=
literal|'J'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|byte
name|PRIMITIVE_SHORT
init|=
literal|'S'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|byte
name|PRIMITIVE_BOOLEAN
init|=
literal|'Z'
decl_stmt|;
specifier|public
specifier|static
name|ElementValue
name|readElementValue
parameter_list|(
specifier|final
name|DataInput
name|input
parameter_list|,
specifier|final
name|ConstantPool
name|cpool
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|byte
name|type
init|=
name|input
operator|.
name|readByte
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|PRIMITIVE_BYTE
case|:
case|case
name|PRIMITIVE_CHAR
case|:
case|case
name|PRIMITIVE_DOUBLE
case|:
case|case
name|PRIMITIVE_FLOAT
case|:
case|case
name|PRIMITIVE_INT
case|:
case|case
name|PRIMITIVE_LONG
case|:
case|case
name|PRIMITIVE_SHORT
case|:
case|case
name|PRIMITIVE_BOOLEAN
case|:
case|case
name|STRING
case|:
return|return
operator|new
name|SimpleElementValue
argument_list|(
name|type
argument_list|,
name|input
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpool
argument_list|)
return|;
case|case
name|ENUM_CONSTANT
case|:
return|return
operator|new
name|EnumElementValue
argument_list|(
name|ENUM_CONSTANT
argument_list|,
name|input
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|input
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpool
argument_list|)
return|;
case|case
name|CLASS
case|:
return|return
operator|new
name|ClassElementValue
argument_list|(
name|CLASS
argument_list|,
name|input
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpool
argument_list|)
return|;
case|case
name|ANNOTATION
case|:
comment|// TODO isRuntimeVisible
return|return
operator|new
name|AnnotationElementValue
argument_list|(
name|ANNOTATION
argument_list|,
name|AnnotationEntry
operator|.
name|read
argument_list|(
name|input
argument_list|,
name|cpool
argument_list|,
literal|false
argument_list|)
argument_list|,
name|cpool
argument_list|)
return|;
case|case
name|ARRAY
case|:
specifier|final
name|int
name|numArrayVals
init|=
name|input
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
specifier|final
name|ElementValue
index|[]
name|evalues
init|=
operator|new
name|ElementValue
index|[
name|numArrayVals
index|]
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|numArrayVals
condition|;
name|j
operator|++
control|)
block|{
name|evalues
index|[
name|j
index|]
operator|=
name|ElementValue
operator|.
name|readElementValue
argument_list|(
name|input
argument_list|,
name|cpool
argument_list|)
expr_stmt|;
block|}
return|return
operator|new
name|ArrayElementValue
argument_list|(
name|ARRAY
argument_list|,
name|evalues
argument_list|,
name|cpool
argument_list|)
return|;
default|default:
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unexpected element value kind in annotation: "
operator|+
name|type
argument_list|)
throw|;
block|}
block|}
comment|/** @since 6.0 */
specifier|final
name|ConstantPool
name|getConstantPool
parameter_list|()
block|{
return|return
name|cpool
return|;
block|}
comment|/** @since 6.0 */
specifier|final
name|int
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
specifier|public
name|String
name|toShortString
parameter_list|()
block|{
return|return
name|stringifyValue
argument_list|()
return|;
block|}
block|}
end_class

end_unit

