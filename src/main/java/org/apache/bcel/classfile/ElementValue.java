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
name|classfile
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataInputStream
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
comment|/**  * @version $Id: ElementValue  * @author<A HREF="mailto:dbrosius@qis.net">D. Brosius</A>  * @since 6.0  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|ElementValue
block|{
specifier|protected
name|int
name|type
decl_stmt|;
specifier|protected
name|ConstantPool
name|cpool
decl_stmt|;
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
name|int
name|type
parameter_list|,
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
name|int
name|STRING
init|=
literal|'s'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|ENUM_CONSTANT
init|=
literal|'e'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|CLASS
init|=
literal|'c'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|ANNOTATION
init|=
literal|'@'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|ARRAY
init|=
literal|'['
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_INT
init|=
literal|'I'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_BYTE
init|=
literal|'B'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_CHAR
init|=
literal|'C'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_DOUBLE
init|=
literal|'D'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_FLOAT
init|=
literal|'F'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_LONG
init|=
literal|'J'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_SHORT
init|=
literal|'S'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_BOOLEAN
init|=
literal|'Z'
decl_stmt|;
specifier|public
specifier|static
name|ElementValue
name|readElementValue
parameter_list|(
name|DataInputStream
name|dis
parameter_list|,
name|ConstantPool
name|cpool
parameter_list|)
throws|throws
name|IOException
block|{
name|byte
name|type
init|=
name|dis
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
literal|'B'
case|:
comment|// byte
return|return
operator|new
name|SimpleElementValue
argument_list|(
name|PRIMITIVE_BYTE
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpool
argument_list|)
return|;
case|case
literal|'C'
case|:
comment|// char
return|return
operator|new
name|SimpleElementValue
argument_list|(
name|PRIMITIVE_CHAR
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpool
argument_list|)
return|;
case|case
literal|'D'
case|:
comment|// double
return|return
operator|new
name|SimpleElementValue
argument_list|(
name|PRIMITIVE_DOUBLE
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpool
argument_list|)
return|;
case|case
literal|'F'
case|:
comment|// float
return|return
operator|new
name|SimpleElementValue
argument_list|(
name|PRIMITIVE_FLOAT
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpool
argument_list|)
return|;
case|case
literal|'I'
case|:
comment|// int
return|return
operator|new
name|SimpleElementValue
argument_list|(
name|PRIMITIVE_INT
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpool
argument_list|)
return|;
case|case
literal|'J'
case|:
comment|// long
return|return
operator|new
name|SimpleElementValue
argument_list|(
name|PRIMITIVE_LONG
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpool
argument_list|)
return|;
case|case
literal|'S'
case|:
comment|// short
return|return
operator|new
name|SimpleElementValue
argument_list|(
name|PRIMITIVE_SHORT
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpool
argument_list|)
return|;
case|case
literal|'Z'
case|:
comment|// boolean
return|return
operator|new
name|SimpleElementValue
argument_list|(
name|PRIMITIVE_BOOLEAN
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpool
argument_list|)
return|;
case|case
literal|'s'
case|:
comment|// String
return|return
operator|new
name|SimpleElementValue
argument_list|(
name|STRING
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpool
argument_list|)
return|;
case|case
literal|'e'
case|:
comment|// Enum constant
return|return
operator|new
name|EnumElementValue
argument_list|(
name|ENUM_CONSTANT
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpool
argument_list|)
return|;
case|case
literal|'c'
case|:
comment|// Class
return|return
operator|new
name|ClassElementValue
argument_list|(
name|CLASS
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpool
argument_list|)
return|;
case|case
literal|'@'
case|:
comment|// Annotation
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
name|dis
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
literal|'['
case|:
comment|// Array
name|int
name|numArrayVals
init|=
name|dis
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
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
name|dis
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
name|RuntimeException
argument_list|(
literal|"Unexpected element value kind in annotation: "
operator|+
name|type
argument_list|)
throw|;
block|}
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

