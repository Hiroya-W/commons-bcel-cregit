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
class|class
name|EnumElementValue
extends|extends
name|ElementValue
block|{
comment|// For enum types, these two indices point to the type and value
specifier|private
specifier|final
name|int
name|typeIdx
decl_stmt|;
specifier|private
specifier|final
name|int
name|valueIdx
decl_stmt|;
specifier|public
name|EnumElementValue
parameter_list|(
specifier|final
name|int
name|type
parameter_list|,
specifier|final
name|int
name|typeIdx
parameter_list|,
specifier|final
name|int
name|valueIdx
parameter_list|,
specifier|final
name|ConstantPool
name|cpool
parameter_list|)
block|{
name|super
argument_list|(
name|type
argument_list|,
name|cpool
argument_list|)
expr_stmt|;
if|if
condition|(
name|type
operator|!=
name|ENUM_CONSTANT
condition|)
block|{
throw|throw
operator|new
name|ClassFormatException
argument_list|(
literal|"Only element values of type enum can be built with this ctor - type specified: "
operator|+
name|type
argument_list|)
throw|;
block|}
name|this
operator|.
name|typeIdx
operator|=
name|typeIdx
expr_stmt|;
name|this
operator|.
name|valueIdx
operator|=
name|valueIdx
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|dump
parameter_list|(
specifier|final
name|DataOutputStream
name|dos
parameter_list|)
throws|throws
name|IOException
block|{
name|dos
operator|.
name|writeByte
argument_list|(
name|super
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
comment|// u1 type of value (ENUM_CONSTANT == 'e')
name|dos
operator|.
name|writeShort
argument_list|(
name|typeIdx
argument_list|)
expr_stmt|;
comment|// u2
name|dos
operator|.
name|writeShort
argument_list|(
name|valueIdx
argument_list|)
expr_stmt|;
comment|// u2
block|}
specifier|public
name|String
name|getEnumTypeString
parameter_list|()
block|{
return|return
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|getConstantUtf8
argument_list|(
name|typeIdx
argument_list|)
operator|.
name|getBytes
argument_list|()
return|;
block|}
specifier|public
name|String
name|getEnumValueString
parameter_list|()
block|{
return|return
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|getConstantUtf8
argument_list|(
name|valueIdx
argument_list|)
operator|.
name|getBytes
argument_list|()
return|;
block|}
specifier|public
name|int
name|getTypeIndex
parameter_list|()
block|{
return|return
name|typeIdx
return|;
block|}
specifier|public
name|int
name|getValueIndex
parameter_list|()
block|{
return|return
name|valueIdx
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|stringifyValue
parameter_list|()
block|{
return|return
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|getConstantUtf8
argument_list|(
name|valueIdx
argument_list|)
operator|.
name|getBytes
argument_list|()
return|;
block|}
block|}
end_class

end_unit

