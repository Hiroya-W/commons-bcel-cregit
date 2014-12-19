begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  *  Licensed to the Apache Software Foundation (ASF) under one or more  *  contributor license agreements.  See the NOTICE file distributed with  *  this work for additional information regarding copyright ownership.  *  The ASF licenses this file to You under the Apache License, Version 2.0  *  (the "License"); you may not use this file except in compliance with  *  the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  */
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
comment|/**  * This attribute exists for local or   * anonymous classes and ... there can be only one.  *   * @since 6.0  */
end_comment

begin_class
specifier|public
class|class
name|EnclosingMethod
extends|extends
name|Attribute
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|9136852385761725494L
decl_stmt|;
comment|// Pointer to the CONSTANT_Class_info structure representing the
comment|// innermost class that encloses the declaration of the current class.
specifier|private
name|int
name|classIndex
decl_stmt|;
comment|// If the current class is not immediately enclosed by a method or
comment|// constructor, then the value of the method_index item must be zero.
comment|// Otherwise, the value of the  method_index item must point to a
comment|// CONSTANT_NameAndType_info structure representing the name and the
comment|// type of a method in the class referenced by the class we point
comment|// to in the class_index.  *It is the compiler responsibility* to
comment|// ensure that the method identified by this index is the closest
comment|// lexically enclosing method that includes the local/anonymous class.
specifier|private
name|int
name|methodIndex
decl_stmt|;
comment|// Ctors - and code to read an attribute in.
name|EnclosingMethod
parameter_list|(
name|int
name|nameIndex
parameter_list|,
name|int
name|len
parameter_list|,
name|DataInput
name|input
parameter_list|,
name|ConstantPool
name|cpool
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|(
name|nameIndex
argument_list|,
name|len
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
expr_stmt|;
block|}
specifier|private
name|EnclosingMethod
parameter_list|(
name|int
name|nameIndex
parameter_list|,
name|int
name|len
parameter_list|,
name|int
name|classIdx
parameter_list|,
name|int
name|methodIdx
parameter_list|,
name|ConstantPool
name|cpool
parameter_list|)
block|{
name|super
argument_list|(
name|Constants
operator|.
name|ATTR_ENCLOSING_METHOD
argument_list|,
name|nameIndex
argument_list|,
name|len
argument_list|,
name|cpool
argument_list|)
expr_stmt|;
name|classIndex
operator|=
name|classIdx
expr_stmt|;
name|methodIndex
operator|=
name|methodIdx
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|accept
parameter_list|(
name|Visitor
name|v
parameter_list|)
block|{
name|v
operator|.
name|visitEnclosingMethod
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Attribute
name|copy
parameter_list|(
name|ConstantPool
name|constant_pool
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Not implemented yet!"
argument_list|)
throw|;
comment|// is this next line sufficient?
comment|// return (EnclosingMethod)clone();
block|}
comment|// Accessors
specifier|public
specifier|final
name|int
name|getEnclosingClassIndex
parameter_list|()
block|{
return|return
name|classIndex
return|;
block|}
specifier|public
specifier|final
name|int
name|getEnclosingMethodIndex
parameter_list|()
block|{
return|return
name|methodIndex
return|;
block|}
specifier|public
specifier|final
name|void
name|setEnclosingClassIndex
parameter_list|(
name|int
name|idx
parameter_list|)
block|{
name|classIndex
operator|=
name|idx
expr_stmt|;
block|}
specifier|public
specifier|final
name|void
name|setEnclosingMethodIndex
parameter_list|(
name|int
name|idx
parameter_list|)
block|{
name|methodIndex
operator|=
name|idx
expr_stmt|;
block|}
specifier|public
specifier|final
name|ConstantClass
name|getEnclosingClass
parameter_list|()
block|{
name|ConstantClass
name|c
init|=
operator|(
name|ConstantClass
operator|)
name|constant_pool
operator|.
name|getConstant
argument_list|(
name|classIndex
argument_list|,
name|Constants
operator|.
name|CONSTANT_Class
argument_list|)
decl_stmt|;
return|return
name|c
return|;
block|}
specifier|public
specifier|final
name|ConstantNameAndType
name|getEnclosingMethod
parameter_list|()
block|{
if|if
condition|(
name|methodIndex
operator|==
literal|0
condition|)
block|{
return|return
literal|null
return|;
block|}
name|ConstantNameAndType
name|nat
init|=
operator|(
name|ConstantNameAndType
operator|)
name|constant_pool
operator|.
name|getConstant
argument_list|(
name|methodIndex
argument_list|,
name|Constants
operator|.
name|CONSTANT_NameAndType
argument_list|)
decl_stmt|;
return|return
name|nat
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|void
name|dump
parameter_list|(
name|DataOutputStream
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|super
operator|.
name|dump
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|classIndex
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|methodIndex
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

