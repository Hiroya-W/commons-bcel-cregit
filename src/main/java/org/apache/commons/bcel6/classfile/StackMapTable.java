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
name|Constants
import|;
end_import

begin_comment
comment|/**  * This class represents a stack map attribute used for  * preverification of Java classes for the<a  * href="http://java.sun.com/j2me/"> Java 2 Micro Edition</a>  * (J2ME). This attribute is used by the<a  * href="http://java.sun.com/products/cldc/">KVM</a> and contained  * within the Code attribute of a method. See CLDC specification  * ï¿½ï¿½?5.3.1.2  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  * @see     Code  * @see     StackMapEntry  * @see     StackMapType  * @since 6.0  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|StackMapTable
extends|extends
name|Attribute
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|5802191977296683162L
decl_stmt|;
specifier|private
name|StackMapTableEntry
index|[]
name|map
decl_stmt|;
comment|// Table of stack map entries
comment|/*      * @param name_index Index of name      * @param length Content length in bytes      * @param map Table of stack map entries      * @param constant_pool Array of constants      */
specifier|public
name|StackMapTable
parameter_list|(
name|int
name|name_index
parameter_list|,
name|int
name|length
parameter_list|,
name|StackMapTableEntry
index|[]
name|map
parameter_list|,
name|ConstantPool
name|constant_pool
parameter_list|)
block|{
name|super
argument_list|(
name|Constants
operator|.
name|ATTR_STACK_MAP_TABLE
argument_list|,
name|name_index
argument_list|,
name|length
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
name|setStackMapTable
argument_list|(
name|map
argument_list|)
expr_stmt|;
block|}
comment|/**      * Construct object from file stream.      * @param name_index Index of name      * @param length Content length in bytes      * @param file Input stream      * @param constant_pool Array of constants      * @throws IOException      */
name|StackMapTable
parameter_list|(
name|int
name|name_index
parameter_list|,
name|int
name|length
parameter_list|,
name|DataInput
name|file
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
name|StackMapTableEntry
index|[]
operator|)
literal|null
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
name|int
name|map_length
init|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|map
operator|=
operator|new
name|StackMapTableEntry
index|[
name|map_length
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
name|map_length
condition|;
name|i
operator|++
control|)
block|{
name|map
index|[
name|i
index|]
operator|=
operator|new
name|StackMapTableEntry
argument_list|(
name|file
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Dump line number table attribute to file stream in binary format.      *      * @param file Output file stream      * @throws IOException      */
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
name|map
operator|.
name|length
argument_list|)
expr_stmt|;
for|for
control|(
name|StackMapTableEntry
name|entry
range|:
name|map
control|)
block|{
name|entry
operator|.
name|dump
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @return Array of stack map entries      */
specifier|public
specifier|final
name|StackMapTableEntry
index|[]
name|getStackMapTable
parameter_list|()
block|{
return|return
name|map
return|;
block|}
comment|/**      * @param map Array of stack map entries      */
specifier|public
specifier|final
name|void
name|setStackMapTable
parameter_list|(
name|StackMapTableEntry
index|[]
name|map
parameter_list|)
block|{
name|this
operator|.
name|map
operator|=
name|map
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
argument_list|(
literal|"StackMapTable("
argument_list|)
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
name|map
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
name|map
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|map
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
literal|", "
argument_list|)
expr_stmt|;
block|}
block|}
name|buf
operator|.
name|append
argument_list|(
literal|')'
argument_list|)
expr_stmt|;
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
name|StackMapTable
name|c
init|=
operator|(
name|StackMapTable
operator|)
name|clone
argument_list|()
decl_stmt|;
name|c
operator|.
name|map
operator|=
operator|new
name|StackMapTableEntry
index|[
name|map
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
name|map
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|c
operator|.
name|map
index|[
name|i
index|]
operator|=
name|map
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
name|visitStackMapTable
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|int
name|getMapLength
parameter_list|()
block|{
return|return
name|map
operator|==
literal|null
condition|?
literal|0
else|:
name|map
operator|.
name|length
return|;
block|}
block|}
end_class

end_unit

