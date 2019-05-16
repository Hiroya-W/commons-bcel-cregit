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
comment|/**  * This class is derived from<em>Attribute</em> and denotes that this is a  * deprecated method.  * It is instantiated from the<em>Attribute.readAttribute()</em> method.  *  * @see     Attribute  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|Deprecated
extends|extends
name|Attribute
block|{
specifier|private
name|byte
index|[]
name|bytes
decl_stmt|;
comment|/**      * Initialize from another object. Note that both objects use the same      * references (shallow copy). Use clone() for a physical copy.      */
specifier|public
name|Deprecated
parameter_list|(
specifier|final
name|Deprecated
name|c
parameter_list|)
block|{
name|this
argument_list|(
name|c
operator|.
name|getNameIndex
argument_list|()
argument_list|,
name|c
operator|.
name|getLength
argument_list|()
argument_list|,
name|c
operator|.
name|getBytes
argument_list|()
argument_list|,
name|c
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param name_index Index in constant pool to CONSTANT_Utf8      * @param length Content length in bytes      * @param bytes Attribute contents      * @param constant_pool Array of constants      */
specifier|public
name|Deprecated
parameter_list|(
specifier|final
name|int
name|name_index
parameter_list|,
specifier|final
name|int
name|length
parameter_list|,
specifier|final
name|byte
index|[]
name|bytes
parameter_list|,
specifier|final
name|ConstantPool
name|constant_pool
parameter_list|)
block|{
name|super
argument_list|(
name|Const
operator|.
name|ATTR_DEPRECATED
argument_list|,
name|name_index
argument_list|,
name|length
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
name|this
operator|.
name|bytes
operator|=
name|bytes
expr_stmt|;
block|}
comment|/**      * Construct object from input stream.      *      * @param name_index Index in constant pool to CONSTANT_Utf8      * @param length Content length in bytes      * @param input Input stream      * @param constant_pool Array of constants      * @throws IOException      */
name|Deprecated
parameter_list|(
specifier|final
name|int
name|name_index
parameter_list|,
specifier|final
name|int
name|length
parameter_list|,
specifier|final
name|DataInput
name|input
parameter_list|,
specifier|final
name|ConstantPool
name|constant_pool
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|(
name|name_index
argument_list|,
name|length
argument_list|,
operator|(
name|byte
index|[]
operator|)
literal|null
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
if|if
condition|(
name|length
operator|>
literal|0
condition|)
block|{
name|bytes
operator|=
operator|new
name|byte
index|[
name|length
index|]
expr_stmt|;
name|input
operator|.
name|readFully
argument_list|(
name|bytes
argument_list|)
expr_stmt|;
name|println
argument_list|(
literal|"Deprecated attribute with length> 0"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Called by objects that are traversing the nodes of the tree implicitely      * defined by the contents of a Java class. I.e., the hierarchy of methods,      * fields, attributes, etc. spawns a tree of objects.      *      * @param v Visitor object      */
annotation|@
name|Override
specifier|public
name|void
name|accept
parameter_list|(
specifier|final
name|Visitor
name|v
parameter_list|)
block|{
name|v
operator|.
name|visitDeprecated
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * Dump source file attribute to file stream in binary format.      *      * @param file Output file stream      * @throws IOException      */
annotation|@
name|Override
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
name|super
operator|.
name|dump
argument_list|(
name|file
argument_list|)
expr_stmt|;
if|if
condition|(
name|super
operator|.
name|getLength
argument_list|()
operator|>
literal|0
condition|)
block|{
name|file
operator|.
name|write
argument_list|(
name|bytes
argument_list|,
literal|0
argument_list|,
name|super
operator|.
name|getLength
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @return data bytes.      */
specifier|public
specifier|final
name|byte
index|[]
name|getBytes
parameter_list|()
block|{
return|return
name|bytes
return|;
block|}
comment|/**      * @param bytes the raw bytes that represents this byte array      */
specifier|public
specifier|final
name|void
name|setBytes
parameter_list|(
specifier|final
name|byte
index|[]
name|bytes
parameter_list|)
block|{
name|this
operator|.
name|bytes
operator|=
name|bytes
expr_stmt|;
block|}
comment|/**      * @return attribute name      */
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|toString
parameter_list|()
block|{
return|return
name|Const
operator|.
name|getAttributeName
argument_list|(
name|Const
operator|.
name|ATTR_DEPRECATED
argument_list|)
return|;
block|}
comment|/**      * @return deep copy of this attribute      */
annotation|@
name|Override
specifier|public
name|Attribute
name|copy
parameter_list|(
specifier|final
name|ConstantPool
name|_constant_pool
parameter_list|)
block|{
specifier|final
name|Deprecated
name|c
init|=
operator|(
name|Deprecated
operator|)
name|clone
argument_list|()
decl_stmt|;
if|if
condition|(
name|bytes
operator|!=
literal|null
condition|)
block|{
name|c
operator|.
name|bytes
operator|=
operator|new
name|byte
index|[
name|bytes
operator|.
name|length
index|]
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|bytes
argument_list|,
literal|0
argument_list|,
name|c
operator|.
name|bytes
argument_list|,
literal|0
argument_list|,
name|bytes
operator|.
name|length
argument_list|)
expr_stmt|;
block|}
name|c
operator|.
name|setConstantPool
argument_list|(
name|_constant_pool
argument_list|)
expr_stmt|;
return|return
name|c
return|;
block|}
block|}
end_class

end_unit

