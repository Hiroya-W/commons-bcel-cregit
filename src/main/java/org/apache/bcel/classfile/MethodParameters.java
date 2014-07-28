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
comment|/**  * This class represents a MethodParameters attribute.  *  * @see<a href="http://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.24">The class File Format : The MethodParameters Attribute</a>  * @since 6.0  */
end_comment

begin_class
specifier|public
class|class
name|MethodParameters
extends|extends
name|Attribute
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|2500272580422360140L
decl_stmt|;
specifier|private
name|MethodParameter
index|[]
name|parameters
init|=
operator|new
name|MethodParameter
index|[
literal|0
index|]
decl_stmt|;
name|MethodParameters
parameter_list|(
name|int
name|name_index
parameter_list|,
name|int
name|length
parameter_list|,
name|DataInputStream
name|file
parameter_list|,
name|ConstantPool
name|constant_pool
parameter_list|)
throws|throws
name|IOException
block|{
name|super
argument_list|(
name|Constants
operator|.
name|ATTR_METHOD_PARAMETERS
argument_list|,
name|name_index
argument_list|,
name|length
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"new MethodParameters"
argument_list|)
expr_stmt|;
name|int
name|parameters_count
init|=
name|file
operator|.
name|readUnsignedByte
argument_list|()
decl_stmt|;
name|parameters
operator|=
operator|new
name|MethodParameter
index|[
name|parameters_count
index|]
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
name|parameters_count
condition|;
name|i
operator|++
control|)
block|{
name|parameters
index|[
name|i
index|]
operator|=
operator|new
name|MethodParameter
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|MethodParameter
index|[]
name|getParameters
parameter_list|()
block|{
return|return
name|parameters
return|;
block|}
specifier|public
name|void
name|setParameters
parameter_list|(
name|MethodParameter
index|[]
name|parameters
parameter_list|)
block|{
name|this
operator|.
name|parameters
operator|=
name|parameters
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
name|visitMethodParameters
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
name|_constant_pool
parameter_list|)
block|{
name|MethodParameters
name|c
init|=
operator|(
name|MethodParameters
operator|)
name|clone
argument_list|()
decl_stmt|;
name|c
operator|.
name|parameters
operator|=
operator|new
name|MethodParameter
index|[
name|parameters
operator|.
name|length
index|]
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
name|parameters
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|c
operator|.
name|parameters
index|[
name|i
index|]
operator|=
name|parameters
index|[
name|i
index|]
operator|.
name|copy
argument_list|()
expr_stmt|;
block|}
name|c
operator|.
name|constant_pool
operator|=
name|_constant_pool
expr_stmt|;
return|return
name|c
return|;
block|}
comment|/**      * Dump method parameters attribute to file stream in binary format.      *      * @param file Output file stream      * @throws IOException      */
annotation|@
name|Override
specifier|public
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
name|writeByte
argument_list|(
name|parameters
operator|.
name|length
argument_list|)
expr_stmt|;
for|for
control|(
name|MethodParameter
name|parameter
range|:
name|parameters
control|)
block|{
name|parameter
operator|.
name|dump
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

