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

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang3
operator|.
name|ArrayUtils
import|;
end_import

begin_comment
comment|/**  * This class represents the table of exceptions that are thrown by a method. This attribute may be used once per  * method. The name of this class is<em>ExceptionTable</em> for historical reasons; The Java Virtual Machine  * Specification, Second Edition defines this attribute using the name<em>Exceptions</em> (which is inconsistent with  * the other classes).  *  * @see Code  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ExceptionTable
extends|extends
name|Attribute
block|{
specifier|private
name|int
index|[]
name|exceptionIndexTable
decl_stmt|;
comment|// constant pool
comment|/**      * Initialize from another object. Note that both objects use the same references (shallow copy). Use copy() for a      * physical copy.      */
specifier|public
name|ExceptionTable
parameter_list|(
specifier|final
name|ExceptionTable
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
name|getExceptionIndexTable
argument_list|()
argument_list|,
name|c
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Construct object from input stream.      *      * @param nameIndex Index in constant pool      * @param length Content length in bytes      * @param input Input stream      * @param constantPool Array of constants      * @throws IOException if an I/O error occurs.      */
name|ExceptionTable
parameter_list|(
specifier|final
name|int
name|nameIndex
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
name|constantPool
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|(
name|nameIndex
argument_list|,
name|length
argument_list|,
operator|(
name|int
index|[]
operator|)
literal|null
argument_list|,
name|constantPool
argument_list|)
expr_stmt|;
specifier|final
name|int
name|number_of_exceptions
init|=
name|input
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|exceptionIndexTable
operator|=
operator|new
name|int
index|[
name|number_of_exceptions
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
name|number_of_exceptions
condition|;
name|i
operator|++
control|)
block|{
name|exceptionIndexTable
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
comment|/**      * @param name_index Index in constant pool      * @param length Content length in bytes      * @param exceptionIndexTable Table of indices in constant pool      * @param constant_pool Array of constants      */
specifier|public
name|ExceptionTable
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
name|int
index|[]
name|exceptionIndexTable
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
name|ATTR_EXCEPTIONS
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
name|exceptionIndexTable
operator|=
name|exceptionIndexTable
operator|!=
literal|null
condition|?
name|exceptionIndexTable
else|:
name|ArrayUtils
operator|.
name|EMPTY_INT_ARRAY
expr_stmt|;
block|}
comment|/**      * Called by objects that are traversing the nodes of the tree implicitely defined by the contents of a Java class.      * I.e., the hierarchy of methods, fields, attributes, etc. spawns a tree of objects.      *      * @param v Visitor object      */
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
name|visitExceptionTable
argument_list|(
name|this
argument_list|)
expr_stmt|;
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
name|constantPool
parameter_list|)
block|{
specifier|final
name|ExceptionTable
name|c
init|=
operator|(
name|ExceptionTable
operator|)
name|clone
argument_list|()
decl_stmt|;
if|if
condition|(
name|exceptionIndexTable
operator|!=
literal|null
condition|)
block|{
name|c
operator|.
name|exceptionIndexTable
operator|=
operator|new
name|int
index|[
name|exceptionIndexTable
operator|.
name|length
index|]
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|exceptionIndexTable
argument_list|,
literal|0
argument_list|,
name|c
operator|.
name|exceptionIndexTable
argument_list|,
literal|0
argument_list|,
name|exceptionIndexTable
operator|.
name|length
argument_list|)
expr_stmt|;
block|}
name|c
operator|.
name|setConstantPool
argument_list|(
name|constantPool
argument_list|)
expr_stmt|;
return|return
name|c
return|;
block|}
comment|/**      * Dump exceptions attribute to file stream in binary format.      *      * @param file Output file stream      * @throws IOException if an I/O error occurs.      */
annotation|@
name|Override
specifier|public
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
name|file
operator|.
name|writeShort
argument_list|(
name|exceptionIndexTable
operator|.
name|length
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|int
name|index
range|:
name|exceptionIndexTable
control|)
block|{
name|file
operator|.
name|writeShort
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @return Array of indices into constant pool of thrown exceptions.      */
specifier|public
name|int
index|[]
name|getExceptionIndexTable
parameter_list|()
block|{
return|return
name|exceptionIndexTable
return|;
block|}
comment|/**      * @return class names of thrown exceptions      */
specifier|public
name|String
index|[]
name|getExceptionNames
parameter_list|()
block|{
specifier|final
name|String
index|[]
name|names
init|=
operator|new
name|String
index|[
name|exceptionIndexTable
operator|.
name|length
index|]
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
name|exceptionIndexTable
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|names
index|[
name|i
index|]
operator|=
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|getConstantString
argument_list|(
name|exceptionIndexTable
index|[
name|i
index|]
argument_list|,
name|Const
operator|.
name|CONSTANT_Class
argument_list|)
operator|.
name|replace
argument_list|(
literal|'/'
argument_list|,
literal|'.'
argument_list|)
expr_stmt|;
block|}
return|return
name|names
return|;
block|}
comment|/**      * @return Length of exception table.      */
specifier|public
name|int
name|getNumberOfExceptions
parameter_list|()
block|{
return|return
name|exceptionIndexTable
operator|==
literal|null
condition|?
literal|0
else|:
name|exceptionIndexTable
operator|.
name|length
return|;
block|}
comment|/**      * @param exceptionIndexTable the list of exception indexes Also redefines number_of_exceptions according to table      *        length.      */
specifier|public
name|void
name|setExceptionIndexTable
parameter_list|(
specifier|final
name|int
index|[]
name|exceptionIndexTable
parameter_list|)
block|{
name|this
operator|.
name|exceptionIndexTable
operator|=
name|exceptionIndexTable
operator|!=
literal|null
condition|?
name|exceptionIndexTable
else|:
name|ArrayUtils
operator|.
name|EMPTY_INT_ARRAY
expr_stmt|;
block|}
comment|/**      * @return String representation, i.e., a list of thrown exceptions.      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
specifier|final
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|String
name|str
decl_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"Exceptions: "
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
name|exceptionIndexTable
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|str
operator|=
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|getConstantString
argument_list|(
name|exceptionIndexTable
index|[
name|i
index|]
argument_list|,
name|Const
operator|.
name|CONSTANT_Class
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|Utility
operator|.
name|compactClassName
argument_list|(
name|str
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|exceptionIndexTable
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

