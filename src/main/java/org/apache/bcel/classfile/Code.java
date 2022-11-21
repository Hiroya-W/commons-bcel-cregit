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

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|util
operator|.
name|Args
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
comment|/**  * This class represents a chunk of Java byte code contained in a method. It is instantiated by the  *<em>Attribute.readAttribute()</em> method. A<em>Code</em> attribute contains informations about operand stack, local  * variables, byte code and the exceptions handled within this method.  *  * This attribute has attributes itself, namely<em>LineNumberTable</em> which is used for debugging purposes and  *<em>LocalVariableTable</em> which contains information about the local variables.  *  *<pre>  * Code_attribute {  *   u2 attribute_name_index;  *   u4 attribute_length;  *   u2 max_stack;  *   u2 max_locals;  *   u4 code_length;  *   u1 code[code_length];  *   u2 exception_table_length;  *   {  *     u2 start_pc;  *     u2 end_pc;  *     u2 handler_pc;  *     u2 catch_type;  *   } exception_table[exception_table_length];  *   u2 attributes_count;  *   attribute_info attributes[attributes_count];  * }  *</pre>  * @see Attribute  * @see CodeException  * @see LineNumberTable  * @see LocalVariableTable  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|Code
extends|extends
name|Attribute
block|{
specifier|private
name|int
name|maxStack
decl_stmt|;
comment|// Maximum size of stack used by this method // TODO this could be made final (setter is not used)
specifier|private
name|int
name|maxLocals
decl_stmt|;
comment|// Number of local variables // TODO this could be made final (setter is not used)
specifier|private
name|byte
index|[]
name|code
decl_stmt|;
comment|// Actual byte code
specifier|private
name|CodeException
index|[]
name|exceptionTable
decl_stmt|;
comment|// Table of handled exceptions
specifier|private
name|Attribute
index|[]
name|attributes
decl_stmt|;
comment|// or LocalVariable
comment|/**      * Initialize from another object. Note that both objects use the same references (shallow copy). Use copy() for a      * physical copy.      *      * @param code The source Code.      */
specifier|public
name|Code
parameter_list|(
specifier|final
name|Code
name|code
parameter_list|)
block|{
name|this
argument_list|(
name|code
operator|.
name|getNameIndex
argument_list|()
argument_list|,
name|code
operator|.
name|getLength
argument_list|()
argument_list|,
name|code
operator|.
name|getMaxStack
argument_list|()
argument_list|,
name|code
operator|.
name|getMaxLocals
argument_list|()
argument_list|,
name|code
operator|.
name|getCode
argument_list|()
argument_list|,
name|code
operator|.
name|getExceptionTable
argument_list|()
argument_list|,
name|code
operator|.
name|getAttributes
argument_list|()
argument_list|,
name|code
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param nameIndex Index pointing to the name<em>Code</em>      * @param length Content length in bytes      * @param file Input stream      * @param constantPool Array of constants      */
name|Code
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
name|file
parameter_list|,
specifier|final
name|ConstantPool
name|constantPool
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Initialize with some default values which will be overwritten later
name|this
argument_list|(
name|nameIndex
argument_list|,
name|length
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
operator|(
name|byte
index|[]
operator|)
literal|null
argument_list|,
operator|(
name|CodeException
index|[]
operator|)
literal|null
argument_list|,
operator|(
name|Attribute
index|[]
operator|)
literal|null
argument_list|,
name|constantPool
argument_list|)
expr_stmt|;
specifier|final
name|int
name|codeLength
init|=
name|Args
operator|.
name|requireU4
argument_list|(
name|file
operator|.
name|readInt
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"Code length attribute"
argument_list|)
decl_stmt|;
name|code
operator|=
operator|new
name|byte
index|[
name|codeLength
index|]
expr_stmt|;
comment|// Read byte code
name|file
operator|.
name|readFully
argument_list|(
name|code
argument_list|)
expr_stmt|;
comment|/*          * Read exception table that contains all regions where an exception handler is active, i.e., a try { ... } catch()          * block.          */
specifier|final
name|int
name|exceptionTableLength
init|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|exceptionTable
operator|=
operator|new
name|CodeException
index|[
name|exceptionTableLength
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
name|exceptionTableLength
condition|;
name|i
operator|++
control|)
block|{
name|exceptionTable
index|[
name|i
index|]
operator|=
operator|new
name|CodeException
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
comment|/*          * Read all attributes, currently `LineNumberTable' and `LocalVariableTable'          */
specifier|final
name|int
name|attributesCount
init|=
name|file
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|attributes
operator|=
operator|new
name|Attribute
index|[
name|attributesCount
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
name|attributesCount
condition|;
name|i
operator|++
control|)
block|{
name|attributes
index|[
name|i
index|]
operator|=
name|Attribute
operator|.
name|readAttribute
argument_list|(
name|file
argument_list|,
name|constantPool
argument_list|)
expr_stmt|;
block|}
comment|/*          * Adjust length, because of setAttributes in this(), s.b. length is incorrect, because it didn't take the internal          * attributes into account yet! Very subtle bug, fixed in 3.1.1.          */
name|super
operator|.
name|setLength
argument_list|(
name|length
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param nameIndex Index pointing to the name<em>Code</em>      * @param length Content length in bytes      * @param maxStack Maximum size of stack      * @param maxLocals Number of local variables      * @param code Actual byte code      * @param exceptionTable of handled exceptions      * @param attributes Attributes of code: LineNumber or LocalVariable      * @param constantPool Array of constants      */
specifier|public
name|Code
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
name|int
name|maxStack
parameter_list|,
specifier|final
name|int
name|maxLocals
parameter_list|,
specifier|final
name|byte
index|[]
name|code
parameter_list|,
specifier|final
name|CodeException
index|[]
name|exceptionTable
parameter_list|,
specifier|final
name|Attribute
index|[]
name|attributes
parameter_list|,
specifier|final
name|ConstantPool
name|constantPool
parameter_list|)
block|{
name|super
argument_list|(
name|Const
operator|.
name|ATTR_CODE
argument_list|,
name|nameIndex
argument_list|,
name|length
argument_list|,
name|constantPool
argument_list|)
expr_stmt|;
name|this
operator|.
name|maxStack
operator|=
name|Args
operator|.
name|requireU2
argument_list|(
name|maxStack
argument_list|,
literal|"maxStack"
argument_list|)
expr_stmt|;
name|this
operator|.
name|maxLocals
operator|=
name|Args
operator|.
name|requireU2
argument_list|(
name|maxLocals
argument_list|,
literal|"maxLocals"
argument_list|)
expr_stmt|;
name|this
operator|.
name|code
operator|=
name|code
operator|!=
literal|null
condition|?
name|code
else|:
name|ArrayUtils
operator|.
name|EMPTY_BYTE_ARRAY
expr_stmt|;
name|this
operator|.
name|exceptionTable
operator|=
name|exceptionTable
operator|!=
literal|null
condition|?
name|exceptionTable
else|:
name|CodeException
operator|.
name|EMPTY_CODE_EXCEPTION_ARRAY
expr_stmt|;
name|Args
operator|.
name|requireU2
argument_list|(
name|this
operator|.
name|exceptionTable
operator|.
name|length
argument_list|,
literal|"exceptionTable.length"
argument_list|)
expr_stmt|;
name|this
operator|.
name|attributes
operator|=
name|attributes
operator|!=
literal|null
condition|?
name|attributes
else|:
name|EMPTY_ARRAY
expr_stmt|;
name|super
operator|.
name|setLength
argument_list|(
name|calculateLength
argument_list|()
argument_list|)
expr_stmt|;
comment|// Adjust length
block|}
comment|/**      * Called by objects that are traversing the nodes of the tree implicitly defined by the contents of a Java class.      * I.e., the hierarchy of methods, fields, attributes, etc. spawns a tree of objects.      *      * @param v Visitor object      */
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
name|visitCode
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return the full size of this code attribute, minus its first 6 bytes, including the size of all its contained      *         attributes      */
specifier|private
name|int
name|calculateLength
parameter_list|()
block|{
name|int
name|len
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|attributes
operator|!=
literal|null
condition|)
block|{
for|for
control|(
specifier|final
name|Attribute
name|attribute
range|:
name|attributes
control|)
block|{
name|len
operator|+=
name|attribute
operator|.
name|getLength
argument_list|()
operator|+
literal|6
comment|/* attribute header size */
expr_stmt|;
block|}
block|}
return|return
name|len
operator|+
name|getInternalLength
argument_list|()
return|;
block|}
comment|/**      * @return deep copy of this attribute      *      * @param constantPool the constant pool to duplicate      */
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
name|Code
name|c
init|=
operator|(
name|Code
operator|)
name|clone
argument_list|()
decl_stmt|;
if|if
condition|(
name|code
operator|!=
literal|null
condition|)
block|{
name|c
operator|.
name|code
operator|=
name|code
operator|.
name|clone
argument_list|()
expr_stmt|;
block|}
name|c
operator|.
name|setConstantPool
argument_list|(
name|constantPool
argument_list|)
expr_stmt|;
name|c
operator|.
name|exceptionTable
operator|=
operator|new
name|CodeException
index|[
name|exceptionTable
operator|.
name|length
index|]
expr_stmt|;
name|Arrays
operator|.
name|setAll
argument_list|(
name|c
operator|.
name|exceptionTable
argument_list|,
name|i
lambda|->
name|exceptionTable
index|[
name|i
index|]
operator|.
name|copy
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|attributes
operator|=
operator|new
name|Attribute
index|[
name|attributes
operator|.
name|length
index|]
expr_stmt|;
name|Arrays
operator|.
name|setAll
argument_list|(
name|c
operator|.
name|attributes
argument_list|,
name|i
lambda|->
name|attributes
index|[
name|i
index|]
operator|.
name|copy
argument_list|(
name|constantPool
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|c
return|;
block|}
comment|/**      * Dump code attribute to file stream in binary format.      *      * @param file Output file stream      * @throws IOException if an I/O error occurs.      */
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
name|maxStack
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|maxLocals
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeInt
argument_list|(
name|code
operator|.
name|length
argument_list|)
expr_stmt|;
name|file
operator|.
name|write
argument_list|(
name|code
argument_list|,
literal|0
argument_list|,
name|code
operator|.
name|length
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|exceptionTable
operator|.
name|length
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|CodeException
name|exception
range|:
name|exceptionTable
control|)
block|{
name|exception
operator|.
name|dump
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
name|file
operator|.
name|writeShort
argument_list|(
name|attributes
operator|.
name|length
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|Attribute
name|attribute
range|:
name|attributes
control|)
block|{
name|attribute
operator|.
name|dump
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @return Collection of code attributes.      * @see Attribute      */
specifier|public
name|Attribute
index|[]
name|getAttributes
parameter_list|()
block|{
return|return
name|attributes
return|;
block|}
comment|/**      * @return Actual byte code of the method.      */
specifier|public
name|byte
index|[]
name|getCode
parameter_list|()
block|{
return|return
name|code
return|;
block|}
comment|/**      * @return Table of handled exceptions.      * @see CodeException      */
specifier|public
name|CodeException
index|[]
name|getExceptionTable
parameter_list|()
block|{
return|return
name|exceptionTable
return|;
block|}
comment|/**      * @return the internal length of this code attribute (minus the first 6 bytes) and excluding all its attributes      */
specifier|private
name|int
name|getInternalLength
parameter_list|()
block|{
return|return
literal|2
comment|/* maxStack */
operator|+
literal|2
comment|/* maxLocals */
operator|+
literal|4
comment|/* code length */
operator|+
name|code
operator|.
name|length
comment|/* byte-code */
operator|+
literal|2
comment|/* exception-table length */
operator|+
literal|8
operator|*
operator|(
name|exceptionTable
operator|==
literal|null
condition|?
literal|0
else|:
name|exceptionTable
operator|.
name|length
operator|)
comment|/* exception table */
operator|+
literal|2
comment|/* attributes count */
return|;
block|}
comment|/**      * @return LineNumberTable of Code, if it has one      */
specifier|public
name|LineNumberTable
name|getLineNumberTable
parameter_list|()
block|{
for|for
control|(
specifier|final
name|Attribute
name|attribute
range|:
name|attributes
control|)
block|{
if|if
condition|(
name|attribute
operator|instanceof
name|LineNumberTable
condition|)
block|{
return|return
operator|(
name|LineNumberTable
operator|)
name|attribute
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/**      * @return LocalVariableTable of Code, if it has one      */
specifier|public
name|LocalVariableTable
name|getLocalVariableTable
parameter_list|()
block|{
for|for
control|(
specifier|final
name|Attribute
name|attribute
range|:
name|attributes
control|)
block|{
if|if
condition|(
name|attribute
operator|instanceof
name|LocalVariableTable
condition|)
block|{
return|return
operator|(
name|LocalVariableTable
operator|)
name|attribute
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/**      * @return Number of local variables.      */
specifier|public
name|int
name|getMaxLocals
parameter_list|()
block|{
return|return
name|maxLocals
return|;
block|}
comment|/**      * @return Maximum size of stack used by this method.      */
specifier|public
name|int
name|getMaxStack
parameter_list|()
block|{
return|return
name|maxStack
return|;
block|}
comment|/**      * @param attributes the attributes to set for this Code      */
specifier|public
name|void
name|setAttributes
parameter_list|(
specifier|final
name|Attribute
index|[]
name|attributes
parameter_list|)
block|{
name|this
operator|.
name|attributes
operator|=
name|attributes
operator|!=
literal|null
condition|?
name|attributes
else|:
name|EMPTY_ARRAY
expr_stmt|;
name|super
operator|.
name|setLength
argument_list|(
name|calculateLength
argument_list|()
argument_list|)
expr_stmt|;
comment|// Adjust length
block|}
comment|/**      * @param code byte code      */
specifier|public
name|void
name|setCode
parameter_list|(
specifier|final
name|byte
index|[]
name|code
parameter_list|)
block|{
name|this
operator|.
name|code
operator|=
name|code
operator|!=
literal|null
condition|?
name|code
else|:
name|ArrayUtils
operator|.
name|EMPTY_BYTE_ARRAY
expr_stmt|;
name|super
operator|.
name|setLength
argument_list|(
name|calculateLength
argument_list|()
argument_list|)
expr_stmt|;
comment|// Adjust length
block|}
comment|/**      * @param exceptionTable exception table      */
specifier|public
name|void
name|setExceptionTable
parameter_list|(
specifier|final
name|CodeException
index|[]
name|exceptionTable
parameter_list|)
block|{
name|this
operator|.
name|exceptionTable
operator|=
name|exceptionTable
operator|!=
literal|null
condition|?
name|exceptionTable
else|:
name|CodeException
operator|.
name|EMPTY_CODE_EXCEPTION_ARRAY
expr_stmt|;
name|super
operator|.
name|setLength
argument_list|(
name|calculateLength
argument_list|()
argument_list|)
expr_stmt|;
comment|// Adjust length
block|}
comment|/**      * @param maxLocals maximum number of local variables      */
specifier|public
name|void
name|setMaxLocals
parameter_list|(
specifier|final
name|int
name|maxLocals
parameter_list|)
block|{
name|this
operator|.
name|maxLocals
operator|=
name|maxLocals
expr_stmt|;
block|}
comment|/**      * @param maxStack maximum stack size      */
specifier|public
name|void
name|setMaxStack
parameter_list|(
specifier|final
name|int
name|maxStack
parameter_list|)
block|{
name|this
operator|.
name|maxStack
operator|=
name|maxStack
expr_stmt|;
block|}
comment|/**      * @return String representation of code chunk.      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|toString
argument_list|(
literal|true
argument_list|)
return|;
block|}
comment|/**      * Converts this object to a String.      *      * @param verbose Provides verbose output when true.      * @return String representation of code chunk.      */
specifier|public
name|String
name|toString
parameter_list|(
specifier|final
name|boolean
name|verbose
parameter_list|)
block|{
specifier|final
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|(
literal|100
argument_list|)
decl_stmt|;
comment|// CHECKSTYLE IGNORE MagicNumber
name|buf
operator|.
name|append
argument_list|(
literal|"Code(maxStack = "
argument_list|)
operator|.
name|append
argument_list|(
name|maxStack
argument_list|)
operator|.
name|append
argument_list|(
literal|", maxLocals = "
argument_list|)
operator|.
name|append
argument_list|(
name|maxLocals
argument_list|)
operator|.
name|append
argument_list|(
literal|", code_length = "
argument_list|)
operator|.
name|append
argument_list|(
name|code
operator|.
name|length
argument_list|)
operator|.
name|append
argument_list|(
literal|")\n"
argument_list|)
operator|.
name|append
argument_list|(
name|Utility
operator|.
name|codeToString
argument_list|(
name|code
argument_list|,
name|super
operator|.
name|getConstantPool
argument_list|()
argument_list|,
literal|0
argument_list|,
operator|-
literal|1
argument_list|,
name|verbose
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|exceptionTable
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|"\nException handler(s) = \n"
argument_list|)
operator|.
name|append
argument_list|(
literal|"From\tTo\tHandler\tType\n"
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|CodeException
name|exception
range|:
name|exceptionTable
control|)
block|{
name|buf
operator|.
name|append
argument_list|(
name|exception
operator|.
name|toString
argument_list|(
name|super
operator|.
name|getConstantPool
argument_list|()
argument_list|,
name|verbose
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|attributes
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|"\nAttribute(s) = "
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|Attribute
name|attribute
range|:
name|attributes
control|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
operator|.
name|append
argument_list|(
name|attribute
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|":"
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
operator|.
name|append
argument_list|(
name|attribute
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

