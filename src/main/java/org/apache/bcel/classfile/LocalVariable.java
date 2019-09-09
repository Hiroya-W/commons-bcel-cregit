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
comment|/**  * This class represents a local variable within a method. It contains its  * scope, name, signature and index on the method's frame.  It is used both  * to represent an element of the LocalVariableTable as well as an element  * of the LocalVariableTypeTable.  The nomenclature used here may be a bit confusing;  * while the two items have the same layout in a class file, a LocalVariableTable  * attribute contains a descriptor_index, not a signature_index.  The  * LocalVariableTypeTable attribute does have a signature_index.  * @see org.apache.bcel.classfile.Utility for more details on the difference.  *  * @see     LocalVariableTable  * @see     LocalVariableTypeTable  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|LocalVariable
implements|implements
name|Cloneable
implements|,
name|Node
implements|,
name|Constants
block|{
specifier|private
name|int
name|start_pc
decl_stmt|;
comment|// Range in which the variable is valid
specifier|private
name|int
name|length
decl_stmt|;
specifier|private
name|int
name|name_index
decl_stmt|;
comment|// Index in constant pool of variable name
comment|// Technically, a decscriptor_index for a local variable table entry
comment|// and a signature_index for a local variable type table entry.
specifier|private
name|int
name|signature_index
decl_stmt|;
comment|// Index of variable signature
specifier|private
name|int
name|index
decl_stmt|;
comment|/* Variable is index'th local variable on      * this method's frame.      */
specifier|private
name|ConstantPool
name|constant_pool
decl_stmt|;
specifier|private
name|int
name|orig_index
decl_stmt|;
comment|// never changes; used to match up with LocalVariableTypeTable entries
comment|/**      * Initializes from another LocalVariable. Note that both objects use the same      * references (shallow copy). Use copy() for a physical copy.      *      * @param localVariable Another LocalVariable.      */
specifier|public
name|LocalVariable
parameter_list|(
specifier|final
name|LocalVariable
name|localVariable
parameter_list|)
block|{
name|this
argument_list|(
name|localVariable
operator|.
name|getStartPC
argument_list|()
argument_list|,
name|localVariable
operator|.
name|getLength
argument_list|()
argument_list|,
name|localVariable
operator|.
name|getNameIndex
argument_list|()
argument_list|,
name|localVariable
operator|.
name|getSignatureIndex
argument_list|()
argument_list|,
name|localVariable
operator|.
name|getIndex
argument_list|()
argument_list|,
name|localVariable
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|orig_index
operator|=
name|localVariable
operator|.
name|getOrigIndex
argument_list|()
expr_stmt|;
block|}
comment|/**      * Constructs object from file stream.      * @param file Input stream      * @throws IOException      */
name|LocalVariable
parameter_list|(
specifier|final
name|DataInput
name|file
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
name|file
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|file
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|file
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|file
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|file
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param start_pc Range in which the variable      * @param length ... is valid      * @param name_index Index in constant pool of variable name      * @param signature_index Index of variable's signature      * @param index Variable is `index'th local variable on the method's frame      * @param constant_pool Array of constants      */
specifier|public
name|LocalVariable
parameter_list|(
specifier|final
name|int
name|start_pc
parameter_list|,
specifier|final
name|int
name|length
parameter_list|,
specifier|final
name|int
name|name_index
parameter_list|,
specifier|final
name|int
name|signature_index
parameter_list|,
specifier|final
name|int
name|index
parameter_list|,
specifier|final
name|ConstantPool
name|constant_pool
parameter_list|)
block|{
name|this
operator|.
name|start_pc
operator|=
name|start_pc
expr_stmt|;
name|this
operator|.
name|length
operator|=
name|length
expr_stmt|;
name|this
operator|.
name|name_index
operator|=
name|name_index
expr_stmt|;
name|this
operator|.
name|signature_index
operator|=
name|signature_index
expr_stmt|;
name|this
operator|.
name|index
operator|=
name|index
expr_stmt|;
name|this
operator|.
name|constant_pool
operator|=
name|constant_pool
expr_stmt|;
name|this
operator|.
name|orig_index
operator|=
name|index
expr_stmt|;
block|}
comment|/**      * @param start_pc Range in which the variable      * @param length ... is valid      * @param name_index Index in constant pool of variable name      * @param signature_index Index of variable's signature      * @param index Variable is `index'th local variable on the method's frame      * @param constant_pool Array of constants      * @param orig_index Variable is `index'th local variable on the method's frame prior to any changes      */
specifier|public
name|LocalVariable
parameter_list|(
specifier|final
name|int
name|start_pc
parameter_list|,
specifier|final
name|int
name|length
parameter_list|,
specifier|final
name|int
name|name_index
parameter_list|,
specifier|final
name|int
name|signature_index
parameter_list|,
specifier|final
name|int
name|index
parameter_list|,
specifier|final
name|ConstantPool
name|constant_pool
parameter_list|,
specifier|final
name|int
name|orig_index
parameter_list|)
block|{
name|this
operator|.
name|start_pc
operator|=
name|start_pc
expr_stmt|;
name|this
operator|.
name|length
operator|=
name|length
expr_stmt|;
name|this
operator|.
name|name_index
operator|=
name|name_index
expr_stmt|;
name|this
operator|.
name|signature_index
operator|=
name|signature_index
expr_stmt|;
name|this
operator|.
name|index
operator|=
name|index
expr_stmt|;
name|this
operator|.
name|constant_pool
operator|=
name|constant_pool
expr_stmt|;
name|this
operator|.
name|orig_index
operator|=
name|orig_index
expr_stmt|;
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
name|visitLocalVariable
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * Dumps local variable to file stream in binary format.      *      * @param dataOutputStream Output file stream      * @exception IOException if an I/O error occurs.      * @see java.io.FilterOutputStream#out      */
specifier|public
name|void
name|dump
parameter_list|(
specifier|final
name|DataOutputStream
name|dataOutputStream
parameter_list|)
throws|throws
name|IOException
block|{
name|dataOutputStream
operator|.
name|writeShort
argument_list|(
name|start_pc
argument_list|)
expr_stmt|;
name|dataOutputStream
operator|.
name|writeShort
argument_list|(
name|length
argument_list|)
expr_stmt|;
name|dataOutputStream
operator|.
name|writeShort
argument_list|(
name|name_index
argument_list|)
expr_stmt|;
name|dataOutputStream
operator|.
name|writeShort
argument_list|(
name|signature_index
argument_list|)
expr_stmt|;
name|dataOutputStream
operator|.
name|writeShort
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Constant pool used by this object.      */
specifier|public
name|ConstantPool
name|getConstantPool
parameter_list|()
block|{
return|return
name|constant_pool
return|;
block|}
comment|/**      * @return Variable is valid within getStartPC() .. getStartPC()+getLength()      */
specifier|public
name|int
name|getLength
parameter_list|()
block|{
return|return
name|length
return|;
block|}
comment|/**      * @return Variable name.      */
specifier|public
name|String
name|getName
parameter_list|()
block|{
name|ConstantUtf8
name|c
decl_stmt|;
name|c
operator|=
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
expr_stmt|;
return|return
name|c
operator|.
name|getBytes
argument_list|()
return|;
block|}
comment|/**      * @return Index in constant pool of variable name.      */
specifier|public
name|int
name|getNameIndex
parameter_list|()
block|{
return|return
name|name_index
return|;
block|}
comment|/**      * @return Signature.      */
specifier|public
name|String
name|getSignature
parameter_list|()
block|{
name|ConstantUtf8
name|c
decl_stmt|;
name|c
operator|=
operator|(
name|ConstantUtf8
operator|)
name|constant_pool
operator|.
name|getConstant
argument_list|(
name|signature_index
argument_list|,
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
expr_stmt|;
return|return
name|c
operator|.
name|getBytes
argument_list|()
return|;
block|}
comment|/**      * @return Index in constant pool of variable signature.      */
specifier|public
name|int
name|getSignatureIndex
parameter_list|()
block|{
return|return
name|signature_index
return|;
block|}
comment|/**      * @return index of register where variable is stored      */
specifier|public
name|int
name|getIndex
parameter_list|()
block|{
return|return
name|index
return|;
block|}
comment|/**      * @return index of register where variable was originally stored      */
specifier|public
name|int
name|getOrigIndex
parameter_list|()
block|{
return|return
name|orig_index
return|;
block|}
comment|/**      * @return Start of range where the variable is valid      */
specifier|public
name|int
name|getStartPC
parameter_list|()
block|{
return|return
name|start_pc
return|;
block|}
comment|/*      * Helper method shared with LocalVariableTypeTable      */
name|String
name|toStringShared
parameter_list|(
specifier|final
name|boolean
name|typeTable
parameter_list|)
block|{
specifier|final
name|String
name|name
init|=
name|getName
argument_list|()
decl_stmt|;
specifier|final
name|String
name|signature
init|=
name|Utility
operator|.
name|signatureToString
argument_list|(
name|getSignature
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
specifier|final
name|String
name|label
init|=
literal|"LocalVariable"
operator|+
operator|(
name|typeTable
condition|?
literal|"Types"
else|:
literal|""
operator|)
decl_stmt|;
return|return
name|label
operator|+
literal|"(start_pc = "
operator|+
name|start_pc
operator|+
literal|", length = "
operator|+
name|length
operator|+
literal|", index = "
operator|+
name|index
operator|+
literal|":"
operator|+
name|signature
operator|+
literal|" "
operator|+
name|name
operator|+
literal|")"
return|;
block|}
comment|/**      * @param constant_pool Constant pool to be used for this object.      */
specifier|public
name|void
name|setConstantPool
parameter_list|(
specifier|final
name|ConstantPool
name|constant_pool
parameter_list|)
block|{
name|this
operator|.
name|constant_pool
operator|=
name|constant_pool
expr_stmt|;
block|}
comment|/**      * @param length the length of this local variable      */
specifier|public
name|void
name|setLength
parameter_list|(
specifier|final
name|int
name|length
parameter_list|)
block|{
name|this
operator|.
name|length
operator|=
name|length
expr_stmt|;
block|}
comment|/**      * @param name_index the index into the constant pool for the name of this variable      */
specifier|public
name|void
name|setNameIndex
parameter_list|(
specifier|final
name|int
name|name_index
parameter_list|)
block|{
comment|// TODO unused
name|this
operator|.
name|name_index
operator|=
name|name_index
expr_stmt|;
block|}
comment|/**      * @param signature_index the index into the constant pool for the signature of this variable      */
specifier|public
name|void
name|setSignatureIndex
parameter_list|(
specifier|final
name|int
name|signature_index
parameter_list|)
block|{
comment|// TODO unused
name|this
operator|.
name|signature_index
operator|=
name|signature_index
expr_stmt|;
block|}
comment|/**      * @param index the index in the local variable table of this variable      */
specifier|public
name|void
name|setIndex
parameter_list|(
specifier|final
name|int
name|index
parameter_list|)
block|{
comment|// TODO unused
name|this
operator|.
name|index
operator|=
name|index
expr_stmt|;
block|}
comment|/**      * @param start_pc Specify range where the local variable is valid.      */
specifier|public
name|void
name|setStartPC
parameter_list|(
specifier|final
name|int
name|start_pc
parameter_list|)
block|{
comment|// TODO unused
name|this
operator|.
name|start_pc
operator|=
name|start_pc
expr_stmt|;
block|}
comment|/**      * @return string representation.      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|toStringShared
argument_list|(
literal|false
argument_list|)
return|;
block|}
comment|/**      * @return deep copy of this object      */
specifier|public
name|LocalVariable
name|copy
parameter_list|()
block|{
try|try
block|{
return|return
operator|(
name|LocalVariable
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
block|}
end_class

end_unit

