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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|Const
import|;
end_import

begin_comment
comment|/**  * This class represents a bootstrap method attribute, i.e., the bootstrap method ref, the number of bootstrap arguments  * and an array of the bootstrap arguments.  *  * @see<a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.23"> The class File Format :  *      The BootstrapMethods Attribute</a>  * @since 6.0  */
end_comment

begin_class
specifier|public
class|class
name|BootstrapMethod
implements|implements
name|Cloneable
block|{
comment|/** Index of the CONSTANT_MethodHandle_info structure in the constant_pool table */
specifier|private
name|int
name|bootstrapMethodRef
decl_stmt|;
comment|/** Array of references to the constant_pool table */
specifier|private
name|int
index|[]
name|bootstrapArguments
decl_stmt|;
comment|/**      * Initialize from another object.      *      * @param c Source to copy.      */
specifier|public
name|BootstrapMethod
parameter_list|(
specifier|final
name|BootstrapMethod
name|c
parameter_list|)
block|{
name|this
argument_list|(
name|c
operator|.
name|getBootstrapMethodRef
argument_list|()
argument_list|,
name|c
operator|.
name|getBootstrapArguments
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Construct object from input stream.      *      * @param input Input stream      * @throws IOException if an I/O error occurs.      */
name|BootstrapMethod
parameter_list|(
specifier|final
name|DataInput
name|input
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|(
name|input
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|input
operator|.
name|readUnsignedShort
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|bootstrapArguments
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|bootstrapArguments
index|[
name|i
index|]
operator|=
name|input
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
block|}
block|}
comment|// helper method
specifier|private
name|BootstrapMethod
parameter_list|(
specifier|final
name|int
name|bootstrapMethodRef
parameter_list|,
specifier|final
name|int
name|numBootstrapArguments
parameter_list|)
block|{
name|this
argument_list|(
name|bootstrapMethodRef
argument_list|,
operator|new
name|int
index|[
name|numBootstrapArguments
index|]
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param bootstrapMethodRef int index into constant_pool of CONSTANT_MethodHandle      * @param bootstrapArguments int[] indices into constant_pool of CONSTANT_[type]_info      */
specifier|public
name|BootstrapMethod
parameter_list|(
specifier|final
name|int
name|bootstrapMethodRef
parameter_list|,
specifier|final
name|int
index|[]
name|bootstrapArguments
parameter_list|)
block|{
name|this
operator|.
name|bootstrapMethodRef
operator|=
name|bootstrapMethodRef
expr_stmt|;
name|this
operator|.
name|bootstrapArguments
operator|=
name|bootstrapArguments
expr_stmt|;
block|}
comment|/**      * @return deep copy of this object      */
specifier|public
name|BootstrapMethod
name|copy
parameter_list|()
block|{
try|try
block|{
return|return
operator|(
name|BootstrapMethod
operator|)
name|clone
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
specifier|final
name|CloneNotSupportedException
name|e
parameter_list|)
block|{
comment|// TODO should this throw?
block|}
return|return
literal|null
return|;
block|}
comment|/**      * Dump object to file stream in binary format.      *      * @param file Output file stream      * @throws IOException if an I/O error occurs.      */
specifier|public
specifier|final
name|void
name|dump
parameter_list|(
specifier|final
name|DataOutputStream
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|file
operator|.
name|writeShort
argument_list|(
name|bootstrapMethodRef
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|bootstrapArguments
operator|.
name|length
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|int
name|bootstrapArgument
range|:
name|bootstrapArguments
control|)
block|{
name|file
operator|.
name|writeShort
argument_list|(
name|bootstrapArgument
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @return int[] of bootstrap_method indices into constant_pool of CONSTANT_[type]_info      */
specifier|public
name|int
index|[]
name|getBootstrapArguments
parameter_list|()
block|{
return|return
name|bootstrapArguments
return|;
block|}
comment|/**      * @return index into constant_pool of bootstrap_method      */
specifier|public
name|int
name|getBootstrapMethodRef
parameter_list|()
block|{
return|return
name|bootstrapMethodRef
return|;
block|}
comment|/**      * @return count of number of boostrap arguments      */
specifier|public
name|int
name|getNumBootstrapArguments
parameter_list|()
block|{
return|return
name|bootstrapArguments
operator|.
name|length
return|;
block|}
comment|/**      * @param bootstrapArguments int[] indices into constant_pool of CONSTANT_[type]_info      */
specifier|public
name|void
name|setBootstrapArguments
parameter_list|(
specifier|final
name|int
index|[]
name|bootstrapArguments
parameter_list|)
block|{
name|this
operator|.
name|bootstrapArguments
operator|=
name|bootstrapArguments
expr_stmt|;
block|}
comment|/**      * @param bootstrapMethodRef int index into constant_pool of CONSTANT_MethodHandle      */
specifier|public
name|void
name|setBootstrapMethodRef
parameter_list|(
specifier|final
name|int
name|bootstrapMethodRef
parameter_list|)
block|{
name|this
operator|.
name|bootstrapMethodRef
operator|=
name|bootstrapMethodRef
expr_stmt|;
block|}
comment|/**      * @return String representation.      */
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"BootstrapMethod("
operator|+
name|bootstrapMethodRef
operator|+
literal|", "
operator|+
name|bootstrapArguments
operator|.
name|length
operator|+
literal|", "
operator|+
name|Arrays
operator|.
name|toString
argument_list|(
name|bootstrapArguments
argument_list|)
operator|+
literal|")"
return|;
block|}
comment|/**      * @return Resolved string representation      */
specifier|public
specifier|final
name|String
name|toString
parameter_list|(
specifier|final
name|ConstantPool
name|constantPool
parameter_list|)
block|{
specifier|final
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
specifier|final
name|String
name|bootstrapMethodName
init|=
name|constantPool
operator|.
name|constantToString
argument_list|(
name|bootstrapMethodRef
argument_list|,
name|Const
operator|.
name|CONSTANT_MethodHandle
argument_list|)
decl_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|Utility
operator|.
name|compactClassName
argument_list|(
name|bootstrapMethodName
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
specifier|final
name|int
name|bootstrapArgumentsLen
init|=
name|bootstrapArguments
operator|.
name|length
decl_stmt|;
if|if
condition|(
name|bootstrapArgumentsLen
operator|>
literal|0
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|"\nMethod Arguments:"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|bootstrapArgumentsLen
condition|;
name|i
operator|++
control|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|"\n  "
argument_list|)
operator|.
name|append
argument_list|(
name|i
argument_list|)
operator|.
name|append
argument_list|(
literal|": "
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|constantPool
operator|.
name|constantToString
argument_list|(
name|constantPool
operator|.
name|getConstant
argument_list|(
name|bootstrapArguments
index|[
name|i
index|]
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|buf
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

