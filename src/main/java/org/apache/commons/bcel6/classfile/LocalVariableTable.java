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
name|commons
operator|.
name|bcel6
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
name|commons
operator|.
name|bcel6
operator|.
name|Const
import|;
end_import

begin_comment
comment|/**  * This class represents colection of local variables in a  * method. This attribute is contained in the<em>Code</em> attribute.  *  * @version $Id$  * @see     Code  * @see LocalVariable  */
end_comment

begin_class
specifier|public
class|class
name|LocalVariableTable
extends|extends
name|Attribute
block|{
specifier|private
name|LocalVariable
index|[]
name|local_variable_table
decl_stmt|;
comment|// variables
comment|/**      * Initialize from another object. Note that both objects use the same      * references (shallow copy). Use copy() for a physical copy.      */
specifier|public
name|LocalVariableTable
parameter_list|(
name|LocalVariableTable
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
name|getLocalVariableTable
argument_list|()
argument_list|,
name|c
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param name_index Index in constant pool to `LocalVariableTable'      * @param length Content length in bytes      * @param local_variable_table Table of local variables      * @param constant_pool Array of constants      */
specifier|public
name|LocalVariableTable
parameter_list|(
name|int
name|name_index
parameter_list|,
name|int
name|length
parameter_list|,
name|LocalVariable
index|[]
name|local_variable_table
parameter_list|,
name|ConstantPool
name|constant_pool
parameter_list|)
block|{
name|super
argument_list|(
name|Const
operator|.
name|ATTR_LOCAL_VARIABLE_TABLE
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
name|local_variable_table
operator|=
name|local_variable_table
expr_stmt|;
block|}
comment|/**      * Construct object from input stream.      * @param name_index Index in constant pool      * @param length Content length in bytes      * @param input Input stream      * @param constant_pool Array of constants      * @throws IOException      */
name|LocalVariableTable
parameter_list|(
name|int
name|name_index
parameter_list|,
name|int
name|length
parameter_list|,
name|DataInput
name|input
parameter_list|,
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
name|LocalVariable
index|[]
operator|)
literal|null
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
name|int
name|local_variable_table_length
init|=
name|input
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|local_variable_table
operator|=
operator|new
name|LocalVariable
index|[
name|local_variable_table_length
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
name|local_variable_table_length
condition|;
name|i
operator|++
control|)
block|{
name|local_variable_table
index|[
name|i
index|]
operator|=
operator|new
name|LocalVariable
argument_list|(
name|input
argument_list|,
name|constant_pool
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
name|Visitor
name|v
parameter_list|)
block|{
name|v
operator|.
name|visitLocalVariableTable
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * Dump local variable table attribute to file stream in binary format.      *      * @param file Output file stream      * @throws IOException      */
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
name|local_variable_table
operator|.
name|length
argument_list|)
expr_stmt|;
for|for
control|(
name|LocalVariable
name|variable
range|:
name|local_variable_table
control|)
block|{
name|variable
operator|.
name|dump
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @return Array of local variables of method.      */
specifier|public
specifier|final
name|LocalVariable
index|[]
name|getLocalVariableTable
parameter_list|()
block|{
return|return
name|local_variable_table
return|;
block|}
comment|/**       *       * @param index the variable slot      *       * @return the first LocalVariable that matches the slot or null if not found      *       * @deprecated since 5.2 because multiple variables can share the      *             same slot, use getLocalVariable(int index, int pc) instead.      */
annotation|@
name|java
operator|.
name|lang
operator|.
name|Deprecated
specifier|public
specifier|final
name|LocalVariable
name|getLocalVariable
parameter_list|(
name|int
name|index
parameter_list|)
block|{
for|for
control|(
name|LocalVariable
name|variable
range|:
name|local_variable_table
control|)
block|{
if|if
condition|(
name|variable
operator|.
name|getIndex
argument_list|()
operator|==
name|index
condition|)
block|{
return|return
name|variable
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/**       *       * @param index the variable slot      * @param pc the current pc that this variable is alive      *       * @return the LocalVariable that matches or null if not found      */
specifier|public
specifier|final
name|LocalVariable
name|getLocalVariable
parameter_list|(
name|int
name|index
parameter_list|,
name|int
name|pc
parameter_list|)
block|{
for|for
control|(
name|LocalVariable
name|variable
range|:
name|local_variable_table
control|)
block|{
if|if
condition|(
name|variable
operator|.
name|getIndex
argument_list|()
operator|==
name|index
condition|)
block|{
name|int
name|start_pc
init|=
name|variable
operator|.
name|getStartPC
argument_list|()
decl_stmt|;
name|int
name|end_pc
init|=
name|start_pc
operator|+
name|variable
operator|.
name|getLength
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|pc
operator|>=
name|start_pc
operator|)
operator|&&
operator|(
name|pc
operator|<=
name|end_pc
operator|)
condition|)
block|{
return|return
name|variable
return|;
block|}
block|}
block|}
return|return
literal|null
return|;
block|}
specifier|public
specifier|final
name|void
name|setLocalVariableTable
parameter_list|(
name|LocalVariable
index|[]
name|local_variable_table
parameter_list|)
block|{
name|this
operator|.
name|local_variable_table
operator|=
name|local_variable_table
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
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|local_variable_table
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|buf
operator|.
name|append
argument_list|(
name|local_variable_table
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|local_variable_table
operator|.
name|length
operator|-
literal|1
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|'\n'
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
comment|/**      * @return deep copy of this attribute      */
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
name|LocalVariableTable
name|c
init|=
operator|(
name|LocalVariableTable
operator|)
name|clone
argument_list|()
decl_stmt|;
name|c
operator|.
name|local_variable_table
operator|=
operator|new
name|LocalVariable
index|[
name|local_variable_table
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
name|local_variable_table
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|c
operator|.
name|local_variable_table
index|[
name|i
index|]
operator|=
name|local_variable_table
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
name|setConstantPool
argument_list|(
name|_constant_pool
argument_list|)
expr_stmt|;
return|return
name|c
return|;
block|}
specifier|public
specifier|final
name|int
name|getTableLength
parameter_list|()
block|{
return|return
name|local_variable_table
operator|==
literal|null
condition|?
literal|0
else|:
name|local_variable_table
operator|.
name|length
return|;
block|}
block|}
end_class

end_unit

