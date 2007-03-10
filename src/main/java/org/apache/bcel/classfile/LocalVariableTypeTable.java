begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/* *******************************************************************  * Copyright (c) 2004 IBM Corporation  *   * All rights reserved.   * This program and the accompanying materials are made available   * under the terms of the Common Public License v1.0   * which accompanies this distribution and is available at   * http://www.eclipse.org/legal/cpl-v10.html   *    * Contributors:   *    Andy Clement     initial implementation   *    Heavily based on LocalVariableTable  * ******************************************************************/
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
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Constants
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
import|;
end_import

begin_comment
comment|// The new table is used when generic types are about...
end_comment

begin_comment
comment|//LocalVariableTable_attribute {
end_comment

begin_comment
comment|//	   u2 attribute_name_index;
end_comment

begin_comment
comment|//	   u4 attribute_length;
end_comment

begin_comment
comment|//	   u2 local_variable_table_length;
end_comment

begin_comment
comment|//	   {  u2 start_pc;
end_comment

begin_comment
comment|//	      u2 length;
end_comment

begin_comment
comment|//	      u2 name_index;
end_comment

begin_comment
comment|//	      u2 descriptor_index;
end_comment

begin_comment
comment|//	      u2 index;
end_comment

begin_comment
comment|//	   } local_variable_table[local_variable_table_length];
end_comment

begin_comment
comment|//	 }
end_comment

begin_comment
comment|//LocalVariableTypeTable_attribute {
end_comment

begin_comment
comment|//    u2 attribute_name_index;
end_comment

begin_comment
comment|//    u4 attribute_length;
end_comment

begin_comment
comment|//    u2 local_variable_type_table_length;
end_comment

begin_comment
comment|//    {
end_comment

begin_comment
comment|//      u2 start_pc;
end_comment

begin_comment
comment|//      u2 length;
end_comment

begin_comment
comment|//      u2 name_index;
end_comment

begin_comment
comment|//      u2 signature_index;
end_comment

begin_comment
comment|//      u2 index;
end_comment

begin_comment
comment|//    } local_variable_type_table[local_variable_type_table_length];
end_comment

begin_comment
comment|//  }
end_comment

begin_comment
comment|// J5TODO: Needs some testing !
end_comment

begin_class
specifier|public
class|class
name|LocalVariableTypeTable
extends|extends
name|Attribute
block|{
specifier|private
name|int
name|local_variable_type_table_length
decl_stmt|;
comment|// Table of local
specifier|private
name|LocalVariable
index|[]
name|local_variable_type_table
decl_stmt|;
comment|// variables
specifier|public
name|LocalVariableTypeTable
parameter_list|(
name|LocalVariableTypeTable
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
name|getLocalVariableTypeTable
argument_list|()
argument_list|,
name|c
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|LocalVariableTypeTable
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
name|Constants
operator|.
name|ATTR_LOCAL_VARIABLE_TYPE_TABLE
argument_list|,
name|name_index
argument_list|,
name|length
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
name|setLocalVariableTable
argument_list|(
name|local_variable_table
argument_list|)
expr_stmt|;
block|}
name|LocalVariableTypeTable
parameter_list|(
name|int
name|nameIdx
parameter_list|,
name|int
name|len
parameter_list|,
name|DataInputStream
name|dis
parameter_list|,
name|ConstantPool
name|cpool
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|(
name|nameIdx
argument_list|,
name|len
argument_list|,
operator|(
name|LocalVariable
index|[]
operator|)
literal|null
argument_list|,
name|cpool
argument_list|)
expr_stmt|;
name|local_variable_type_table_length
operator|=
operator|(
name|dis
operator|.
name|readUnsignedShort
argument_list|()
operator|)
expr_stmt|;
name|local_variable_type_table
operator|=
operator|new
name|LocalVariable
index|[
name|local_variable_type_table_length
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
name|local_variable_type_table_length
condition|;
name|i
operator|++
control|)
name|local_variable_type_table
index|[
name|i
index|]
operator|=
operator|new
name|LocalVariable
argument_list|(
name|dis
argument_list|,
name|cpool
argument_list|)
expr_stmt|;
block|}
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
name|visitLocalVariableTypeTable
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
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
name|local_variable_type_table_length
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
name|local_variable_type_table_length
condition|;
name|i
operator|++
control|)
name|local_variable_type_table
index|[
name|i
index|]
operator|.
name|dump
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|LocalVariable
index|[]
name|getLocalVariableTypeTable
parameter_list|()
block|{
return|return
name|local_variable_type_table
return|;
block|}
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
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|local_variable_type_table_length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|local_variable_type_table
index|[
name|i
index|]
operator|.
name|getIndex
argument_list|()
operator|==
name|index
condition|)
return|return
name|local_variable_type_table
index|[
name|i
index|]
return|;
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
name|local_variable_type_table
operator|=
name|local_variable_table
expr_stmt|;
name|local_variable_type_table_length
operator|=
operator|(
name|local_variable_table
operator|==
literal|null
operator|)
condition|?
literal|0
else|:
name|local_variable_table
operator|.
name|length
expr_stmt|;
block|}
comment|/**    * @return String representation.    */
specifier|public
specifier|final
name|String
name|toString
parameter_list|()
block|{
name|StringBuffer
name|buf
init|=
operator|new
name|StringBuffer
argument_list|(
literal|""
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
name|local_variable_type_table_length
condition|;
name|i
operator|++
control|)
block|{
name|buf
operator|.
name|append
argument_list|(
name|local_variable_type_table
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
name|local_variable_type_table_length
operator|-
literal|1
condition|)
name|buf
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
return|return
name|buf
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**    * @return deep copy of this attribute    */
specifier|public
name|Attribute
name|copy
parameter_list|(
name|ConstantPool
name|constant_pool
parameter_list|)
block|{
name|LocalVariableTypeTable
name|c
init|=
operator|(
name|LocalVariableTypeTable
operator|)
name|clone
argument_list|()
decl_stmt|;
name|c
operator|.
name|local_variable_type_table
operator|=
operator|new
name|LocalVariable
index|[
name|local_variable_type_table_length
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
name|local_variable_type_table_length
condition|;
name|i
operator|++
control|)
name|c
operator|.
name|local_variable_type_table
index|[
name|i
index|]
operator|=
name|local_variable_type_table
index|[
name|i
index|]
operator|.
name|copy
argument_list|()
expr_stmt|;
name|c
operator|.
name|constant_pool
operator|=
name|constant_pool
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
name|local_variable_type_table_length
return|;
block|}
block|}
end_class

end_unit
