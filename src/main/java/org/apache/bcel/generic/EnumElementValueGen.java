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
name|ConstantUtf8
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
name|ElementValue
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
name|EnumElementValue
import|;
end_import

begin_class
specifier|public
class|class
name|EnumElementValueGen
extends|extends
name|ElementValueGen
block|{
comment|// For enum types, these two indices point to the type and value
specifier|private
name|int
name|typeIdx
decl_stmt|;
specifier|private
name|int
name|valueIdx
decl_stmt|;
comment|/**      * This ctor assumes the constant pool already contains the right type and      * value - as indicated by typeIdx and valueIdx. This ctor is used for      * deserialization      */
specifier|protected
name|EnumElementValueGen
parameter_list|(
name|int
name|typeIdx
parameter_list|,
name|int
name|valueIdx
parameter_list|,
name|ConstantPoolGen
name|cpool
parameter_list|)
block|{
name|super
argument_list|(
name|ElementValueGen
operator|.
name|ENUM_CONSTANT
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
name|RuntimeException
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
comment|/**      * Return immutable variant of this EnumElementValue      */
annotation|@
name|Override
specifier|public
name|ElementValue
name|getElementValue
parameter_list|()
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Duplicating value: "
operator|+
name|getEnumTypeString
argument_list|()
operator|+
literal|":"
operator|+
name|getEnumValueString
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|new
name|EnumElementValue
argument_list|(
name|type
argument_list|,
name|typeIdx
argument_list|,
name|valueIdx
argument_list|,
name|cpGen
operator|.
name|getConstantPool
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|EnumElementValueGen
parameter_list|(
name|ObjectType
name|t
parameter_list|,
name|String
name|value
parameter_list|,
name|ConstantPoolGen
name|cpool
parameter_list|)
block|{
name|super
argument_list|(
name|ElementValueGen
operator|.
name|ENUM_CONSTANT
argument_list|,
name|cpool
argument_list|)
expr_stmt|;
name|typeIdx
operator|=
name|cpool
operator|.
name|addUtf8
argument_list|(
name|t
operator|.
name|getSignature
argument_list|()
argument_list|)
expr_stmt|;
comment|// was addClass(t);
name|valueIdx
operator|=
name|cpool
operator|.
name|addUtf8
argument_list|(
name|value
argument_list|)
expr_stmt|;
comment|// was addString(value);
block|}
specifier|public
name|EnumElementValueGen
parameter_list|(
name|EnumElementValue
name|value
parameter_list|,
name|ConstantPoolGen
name|cpool
parameter_list|,
name|boolean
name|copyPoolEntries
parameter_list|)
block|{
name|super
argument_list|(
name|ENUM_CONSTANT
argument_list|,
name|cpool
argument_list|)
expr_stmt|;
if|if
condition|(
name|copyPoolEntries
condition|)
block|{
name|typeIdx
operator|=
name|cpool
operator|.
name|addUtf8
argument_list|(
name|value
operator|.
name|getEnumTypeString
argument_list|()
argument_list|)
expr_stmt|;
comment|// was
comment|// addClass(value.getEnumTypeString());
name|valueIdx
operator|=
name|cpool
operator|.
name|addUtf8
argument_list|(
name|value
operator|.
name|getEnumValueString
argument_list|()
argument_list|)
expr_stmt|;
comment|// was
comment|// addString(value.getEnumValueString());
block|}
else|else
block|{
name|typeIdx
operator|=
name|value
operator|.
name|getTypeIndex
argument_list|()
expr_stmt|;
name|valueIdx
operator|=
name|value
operator|.
name|getValueIndex
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|dump
parameter_list|(
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
name|type
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
annotation|@
name|Override
specifier|public
name|String
name|stringifyValue
parameter_list|()
block|{
name|ConstantUtf8
name|cu8
init|=
operator|(
name|ConstantUtf8
operator|)
name|getConstantPool
argument_list|()
operator|.
name|getConstant
argument_list|(
name|valueIdx
argument_list|)
decl_stmt|;
return|return
name|cu8
operator|.
name|getBytes
argument_list|()
return|;
comment|// ConstantString cu8 =
comment|// (ConstantString)getConstantPool().getConstant(valueIdx);
comment|// return
comment|// ((ConstantUtf8)getConstantPool().getConstant(cu8.getStringIndex())).getBytes();
block|}
comment|// BCELBUG: Should we need to call utility.signatureToString() on the output
comment|// here?
specifier|public
name|String
name|getEnumTypeString
parameter_list|()
block|{
comment|// Constant cc = getConstantPool().getConstant(typeIdx);
comment|// ConstantClass cu8 =
comment|// (ConstantClass)getConstantPool().getConstant(typeIdx);
comment|// return
comment|// ((ConstantUtf8)getConstantPool().getConstant(cu8.getNameIndex())).getBytes();
return|return
operator|(
operator|(
name|ConstantUtf8
operator|)
name|getConstantPool
argument_list|()
operator|.
name|getConstant
argument_list|(
name|typeIdx
argument_list|)
operator|)
operator|.
name|getBytes
argument_list|()
return|;
comment|// return Utility.signatureToString(cu8.getBytes());
block|}
specifier|public
name|String
name|getEnumValueString
parameter_list|()
block|{
return|return
operator|(
operator|(
name|ConstantUtf8
operator|)
name|getConstantPool
argument_list|()
operator|.
name|getConstant
argument_list|(
name|valueIdx
argument_list|)
operator|)
operator|.
name|getBytes
argument_list|()
return|;
comment|// ConstantString cu8 =
comment|// (ConstantString)getConstantPool().getConstant(valueIdx);
comment|// return
comment|// ((ConstantUtf8)getConstantPool().getConstant(cu8.getStringIndex())).getBytes();
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
specifier|public
name|int
name|getTypeIndex
parameter_list|()
block|{
return|return
name|typeIdx
return|;
block|}
block|}
end_class

end_unit

