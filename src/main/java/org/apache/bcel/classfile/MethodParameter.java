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
comment|/**  * Entry of the parameters table.  *   * @see<a href="http://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.24">  * The class File Format : The MethodParameters Attribute</a>  * @since 6.0  */
end_comment

begin_class
specifier|public
class|class
name|MethodParameter
implements|implements
name|Cloneable
block|{
comment|/** Index of the CONSTANT_Utf8_info structure in the constant_pool table representing the name of the parameter */
specifier|private
name|int
name|name_index
decl_stmt|;
comment|/** The access flags */
specifier|private
name|int
name|access_flags
decl_stmt|;
specifier|public
name|MethodParameter
parameter_list|()
block|{
block|}
comment|/**      * Construct object from input stream.      *       * @param input Input stream      * @throws java.io.IOException      * @throws ClassFormatException      */
name|MethodParameter
parameter_list|(
specifier|final
name|DataInput
name|input
parameter_list|)
throws|throws
name|IOException
block|{
name|name_index
operator|=
name|input
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|access_flags
operator|=
name|input
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getNameIndex
parameter_list|()
block|{
return|return
name|name_index
return|;
block|}
specifier|public
name|void
name|setNameIndex
parameter_list|(
specifier|final
name|int
name|name_index
parameter_list|)
block|{
name|this
operator|.
name|name_index
operator|=
name|name_index
expr_stmt|;
block|}
comment|/**      * Returns the name of the parameter.      */
specifier|public
name|String
name|getParameterName
parameter_list|(
specifier|final
name|ConstantPool
name|constant_pool
parameter_list|)
block|{
if|if
condition|(
name|name_index
operator|==
literal|0
condition|)
block|{
return|return
literal|null
return|;
block|}
return|return
operator|(
operator|(
name|ConstantUtf8
operator|)
name|constant_pool
operator|.
name|getConstant
argument_list|(
name|name_index
argument_list|,
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
operator|)
operator|.
name|getBytes
argument_list|()
return|;
block|}
specifier|public
name|int
name|getAccessFlags
parameter_list|()
block|{
return|return
name|access_flags
return|;
block|}
specifier|public
name|void
name|setAccessFlags
parameter_list|(
specifier|final
name|int
name|access_flags
parameter_list|)
block|{
name|this
operator|.
name|access_flags
operator|=
name|access_flags
expr_stmt|;
block|}
specifier|public
name|boolean
name|isFinal
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_FINAL
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
name|boolean
name|isSynthetic
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_SYNTHETIC
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
name|boolean
name|isMandated
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_MANDATED
operator|)
operator|!=
literal|0
return|;
block|}
comment|/**      * Dump object to file stream on binary format.      *      * @param file Output file stream      * @throws IOException      */
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
name|name_index
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|access_flags
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return deep copy of this object      */
specifier|public
name|MethodParameter
name|copy
parameter_list|()
block|{
try|try
block|{
return|return
operator|(
name|MethodParameter
operator|)
name|clone
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
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
block|}
end_class

end_unit
