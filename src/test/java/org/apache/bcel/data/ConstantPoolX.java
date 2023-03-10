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
name|data
package|;
end_package

begin_comment
comment|/*  * This file is a modified copy of org.apache.bcel.classfile.ConstantPool.java.  * It was chosen as it generates both tableswitch and lookupswitch byte codes.  * It has been modified to compile stand alone within the BCEL test environment.  * The code is not executed but the classfile is used as input to a BCEL test case.  */
end_comment

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
name|classfile
operator|.
name|ClassFormatException
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
name|classfile
operator|.
name|Constant
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
name|classfile
operator|.
name|ConstantCP
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
name|classfile
operator|.
name|ConstantClass
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
name|classfile
operator|.
name|ConstantDouble
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
name|classfile
operator|.
name|ConstantFloat
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
name|classfile
operator|.
name|ConstantInteger
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
name|classfile
operator|.
name|ConstantInvokeDynamic
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
name|classfile
operator|.
name|ConstantLong
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
name|classfile
operator|.
name|ConstantMethodHandle
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
name|classfile
operator|.
name|ConstantMethodType
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
name|classfile
operator|.
name|ConstantModule
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
name|classfile
operator|.
name|ConstantNameAndType
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
name|classfile
operator|.
name|ConstantPackage
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
name|classfile
operator|.
name|ConstantString
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
name|classfile
operator|.
name|ConstantUtf8
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
name|classfile
operator|.
name|Node
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
name|classfile
operator|.
name|Utility
import|;
end_import

begin_comment
comment|/**  * This class represents the constant pool, i.e., a table of constants, of a parsed classfile. It may contain null  * references, due to the JVM specification that skips an entry after an 8-byte constant (double, long) entry. Those  * interested in generating constant pools programatically should see<a href="../generic/ConstantPoolGen.html">  * ConstantPoolGen</a>.  *  * @see Constant  * @see org.apache.bcel.generic.ConstantPoolGen  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|ConstantPoolX
implements|implements
name|Cloneable
implements|,
name|Node
block|{
specifier|private
specifier|static
name|String
name|escape
parameter_list|(
specifier|final
name|String
name|str
parameter_list|)
block|{
specifier|final
name|int
name|len
init|=
name|str
operator|.
name|length
argument_list|()
decl_stmt|;
specifier|final
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|(
name|len
operator|+
literal|5
argument_list|)
decl_stmt|;
specifier|final
name|char
index|[]
name|ch
init|=
name|str
operator|.
name|toCharArray
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
name|len
condition|;
name|i
operator|++
control|)
block|{
switch|switch
condition|(
name|ch
index|[
name|i
index|]
condition|)
block|{
case|case
literal|'\n'
case|:
name|buf
operator|.
name|append
argument_list|(
literal|"\\n"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'\r'
case|:
name|buf
operator|.
name|append
argument_list|(
literal|"\\r"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'\t'
case|:
name|buf
operator|.
name|append
argument_list|(
literal|"\\t"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'\b'
case|:
name|buf
operator|.
name|append
argument_list|(
literal|"\\b"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'"'
case|:
name|buf
operator|.
name|append
argument_list|(
literal|"\\\""
argument_list|)
expr_stmt|;
break|break;
default|default:
name|buf
operator|.
name|append
argument_list|(
name|ch
index|[
name|i
index|]
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
specifier|private
name|Constant
index|[]
name|constantPool
decl_stmt|;
comment|/**      * Resolves constant to a string representation.      *      * @param c Constant to be printed      * @return String representation      */
specifier|public
name|String
name|constantToString
parameter_list|(
name|Constant
name|c
parameter_list|)
throws|throws
name|ClassFormatException
block|{
name|String
name|str
decl_stmt|;
name|int
name|i
decl_stmt|;
specifier|final
name|byte
name|tag
init|=
name|c
operator|.
name|getTag
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|tag
condition|)
block|{
case|case
name|Const
operator|.
name|CONSTANT_Class
case|:
name|i
operator|=
operator|(
operator|(
name|ConstantClass
operator|)
name|c
operator|)
operator|.
name|getNameIndex
argument_list|()
expr_stmt|;
name|c
operator|=
name|getConstant
argument_list|(
name|i
argument_list|,
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
expr_stmt|;
name|str
operator|=
name|Utility
operator|.
name|compactClassName
argument_list|(
operator|(
operator|(
name|ConstantUtf8
operator|)
name|c
operator|)
operator|.
name|getBytes
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_String
case|:
name|i
operator|=
operator|(
operator|(
name|ConstantString
operator|)
name|c
operator|)
operator|.
name|getStringIndex
argument_list|()
expr_stmt|;
name|c
operator|=
name|getConstant
argument_list|(
name|i
argument_list|,
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
expr_stmt|;
name|str
operator|=
literal|"\""
operator|+
name|escape
argument_list|(
operator|(
operator|(
name|ConstantUtf8
operator|)
name|c
operator|)
operator|.
name|getBytes
argument_list|()
argument_list|)
operator|+
literal|"\""
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_Utf8
case|:
name|str
operator|=
operator|(
operator|(
name|ConstantUtf8
operator|)
name|c
operator|)
operator|.
name|getBytes
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_Double
case|:
name|str
operator|=
name|String
operator|.
name|valueOf
argument_list|(
operator|(
operator|(
name|ConstantDouble
operator|)
name|c
operator|)
operator|.
name|getBytes
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_Float
case|:
name|str
operator|=
name|String
operator|.
name|valueOf
argument_list|(
operator|(
operator|(
name|ConstantFloat
operator|)
name|c
operator|)
operator|.
name|getBytes
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_Long
case|:
name|str
operator|=
name|String
operator|.
name|valueOf
argument_list|(
operator|(
operator|(
name|ConstantLong
operator|)
name|c
operator|)
operator|.
name|getBytes
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_Integer
case|:
name|str
operator|=
name|String
operator|.
name|valueOf
argument_list|(
operator|(
operator|(
name|ConstantInteger
operator|)
name|c
operator|)
operator|.
name|getBytes
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_NameAndType
case|:
name|str
operator|=
name|constantToString
argument_list|(
operator|(
operator|(
name|ConstantNameAndType
operator|)
name|c
operator|)
operator|.
name|getNameIndex
argument_list|()
argument_list|,
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
operator|+
literal|" "
operator|+
name|constantToString
argument_list|(
operator|(
operator|(
name|ConstantNameAndType
operator|)
name|c
operator|)
operator|.
name|getSignatureIndex
argument_list|()
argument_list|,
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_InterfaceMethodref
case|:
case|case
name|Const
operator|.
name|CONSTANT_Methodref
case|:
case|case
name|Const
operator|.
name|CONSTANT_Fieldref
case|:
name|str
operator|=
name|constantToString
argument_list|(
operator|(
operator|(
name|ConstantCP
operator|)
name|c
operator|)
operator|.
name|getClassIndex
argument_list|()
argument_list|,
name|Const
operator|.
name|CONSTANT_Class
argument_list|)
operator|+
literal|"."
operator|+
name|constantToString
argument_list|(
operator|(
operator|(
name|ConstantCP
operator|)
name|c
operator|)
operator|.
name|getNameAndTypeIndex
argument_list|()
argument_list|,
name|Const
operator|.
name|CONSTANT_NameAndType
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_MethodHandle
case|:
comment|// Note that the ReferenceIndex may point to a Fieldref, Methodref or
comment|// InterfaceMethodref - so we need to peek ahead to get the actual type.
specifier|final
name|ConstantMethodHandle
name|cmh
init|=
operator|(
name|ConstantMethodHandle
operator|)
name|c
decl_stmt|;
name|str
operator|=
name|Const
operator|.
name|getMethodHandleName
argument_list|(
name|cmh
operator|.
name|getReferenceKind
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|constantToString
argument_list|(
name|cmh
operator|.
name|getReferenceIndex
argument_list|()
argument_list|,
name|getConstant
argument_list|(
name|cmh
operator|.
name|getReferenceIndex
argument_list|()
argument_list|)
operator|.
name|getTag
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_MethodType
case|:
specifier|final
name|ConstantMethodType
name|cmt
init|=
operator|(
name|ConstantMethodType
operator|)
name|c
decl_stmt|;
name|str
operator|=
name|constantToString
argument_list|(
name|cmt
operator|.
name|getDescriptorIndex
argument_list|()
argument_list|,
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_InvokeDynamic
case|:
specifier|final
name|ConstantInvokeDynamic
name|cid
init|=
operator|(
name|ConstantInvokeDynamic
operator|)
name|c
decl_stmt|;
name|str
operator|=
name|cid
operator|.
name|getBootstrapMethodAttrIndex
argument_list|()
operator|+
literal|":"
operator|+
name|constantToString
argument_list|(
name|cid
operator|.
name|getNameAndTypeIndex
argument_list|()
argument_list|,
name|Const
operator|.
name|CONSTANT_NameAndType
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_Module
case|:
name|i
operator|=
operator|(
operator|(
name|ConstantModule
operator|)
name|c
operator|)
operator|.
name|getNameIndex
argument_list|()
expr_stmt|;
name|c
operator|=
name|getConstant
argument_list|(
name|i
argument_list|,
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
expr_stmt|;
name|str
operator|=
name|Utility
operator|.
name|compactClassName
argument_list|(
operator|(
operator|(
name|ConstantUtf8
operator|)
name|c
operator|)
operator|.
name|getBytes
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_Package
case|:
name|i
operator|=
operator|(
operator|(
name|ConstantPackage
operator|)
name|c
operator|)
operator|.
name|getNameIndex
argument_list|()
expr_stmt|;
name|c
operator|=
name|getConstant
argument_list|(
name|i
argument_list|,
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
expr_stmt|;
name|str
operator|=
name|Utility
operator|.
name|compactClassName
argument_list|(
operator|(
operator|(
name|ConstantUtf8
operator|)
name|c
operator|)
operator|.
name|getBytes
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
break|break;
default|default:
comment|// Never reached
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unknown constant type "
operator|+
name|tag
argument_list|)
throw|;
block|}
return|return
name|str
return|;
block|}
comment|/**      * Retrieves constant at 'index' from constant pool and resolve it to a string representation.      *      * @param index of constant in constant pool      * @param tag expected type      * @return String representation      */
specifier|public
name|String
name|constantToString
parameter_list|(
specifier|final
name|int
name|index
parameter_list|,
specifier|final
name|byte
name|tag
parameter_list|)
throws|throws
name|ClassFormatException
block|{
specifier|final
name|Constant
name|c
init|=
name|getConstant
argument_list|(
name|index
argument_list|,
name|tag
argument_list|)
decl_stmt|;
return|return
name|constantToString
argument_list|(
name|c
argument_list|)
return|;
block|}
comment|/**      * Dump constant pool to file stream in binary format.      *      * @param file Output file stream      * @throws IOException      */
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
name|file
operator|.
name|writeShort
argument_list|(
name|constantPool
operator|.
name|length
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|constantPool
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|constantPool
index|[
name|i
index|]
operator|!=
literal|null
condition|)
block|{
name|constantPool
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
block|}
block|}
comment|/**      * Gets constant from constant pool.      *      * @param index Index in constant pool      * @return Constant value      * @see Constant      */
specifier|public
name|Constant
name|getConstant
parameter_list|(
specifier|final
name|int
name|index
parameter_list|)
block|{
if|if
condition|(
name|index
operator|>=
name|constantPool
operator|.
name|length
operator|||
name|index
operator|<
literal|0
condition|)
block|{
throw|throw
operator|new
name|ClassFormatException
argument_list|(
literal|"Invalid constant pool reference: "
operator|+
name|index
operator|+
literal|". Constant pool size is: "
operator|+
name|constantPool
operator|.
name|length
argument_list|)
throw|;
block|}
return|return
name|constantPool
index|[
name|index
index|]
return|;
block|}
comment|/**      * Gets constant from constant pool and check whether it has the expected type.      *      * @param index Index in constant pool      * @param tag Tag of expected constant, i.e., its type      * @return Constant value      * @see Constant      * @throws ClassFormatException      */
specifier|public
name|Constant
name|getConstant
parameter_list|(
specifier|final
name|int
name|index
parameter_list|,
specifier|final
name|byte
name|tag
parameter_list|)
throws|throws
name|ClassFormatException
block|{
name|Constant
name|c
decl_stmt|;
name|c
operator|=
name|getConstant
argument_list|(
name|index
argument_list|)
expr_stmt|;
if|if
condition|(
name|c
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ClassFormatException
argument_list|(
literal|"Constant pool at index "
operator|+
name|index
operator|+
literal|" is null."
argument_list|)
throw|;
block|}
if|if
condition|(
name|c
operator|.
name|getTag
argument_list|()
operator|!=
name|tag
condition|)
block|{
throw|throw
operator|new
name|ClassFormatException
argument_list|(
literal|"Expected class '"
operator|+
name|Const
operator|.
name|getConstantName
argument_list|(
name|tag
argument_list|)
operator|+
literal|"' at index "
operator|+
name|index
operator|+
literal|" and got "
operator|+
name|c
argument_list|)
throw|;
block|}
return|return
name|c
return|;
block|}
comment|/**      * @return Array of constants.      * @see Constant      */
specifier|public
name|Constant
index|[]
name|getConstantPool
parameter_list|()
block|{
return|return
name|constantPool
return|;
block|}
comment|/**      * Gets string from constant pool and bypass the indirection of 'ConstantClass' and 'ConstantString' objects. I.e. these      * classes have an index field that points to another entry of the constant pool of type 'ConstantUtf8' which contains      * the real data.      *      * @param index Index in constant pool      * @param tag Tag of expected constant, either ConstantClass or ConstantString      * @return Contents of string reference      * @see ConstantClass      * @see ConstantString      * @throws ClassFormatException      */
specifier|public
name|String
name|getConstantString
parameter_list|(
specifier|final
name|int
name|index
parameter_list|,
specifier|final
name|byte
name|tag
parameter_list|)
throws|throws
name|ClassFormatException
block|{
name|Constant
name|c
decl_stmt|;
name|int
name|i
decl_stmt|;
name|c
operator|=
name|getConstant
argument_list|(
name|index
argument_list|,
name|tag
argument_list|)
expr_stmt|;
comment|/*          * This switch() is not that elegant, since the four classes have the same contents, they just differ in the name of the          * index field variable. But we want to stick to the JVM naming conventions closely though we could have solved these          * more elegantly by using the same variable name or by subclassing.          */
switch|switch
condition|(
name|tag
condition|)
block|{
case|case
name|Const
operator|.
name|CONSTANT_Class
case|:
name|i
operator|=
operator|(
operator|(
name|ConstantClass
operator|)
name|c
operator|)
operator|.
name|getNameIndex
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_String
case|:
name|i
operator|=
operator|(
operator|(
name|ConstantString
operator|)
name|c
operator|)
operator|.
name|getStringIndex
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_Module
case|:
name|i
operator|=
operator|(
operator|(
name|ConstantModule
operator|)
name|c
operator|)
operator|.
name|getNameIndex
argument_list|()
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|CONSTANT_Package
case|:
name|i
operator|=
operator|(
operator|(
name|ConstantPackage
operator|)
name|c
operator|)
operator|.
name|getNameIndex
argument_list|()
expr_stmt|;
break|break;
default|default:
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"getConstantString called with illegal tag "
operator|+
name|tag
argument_list|)
throw|;
block|}
comment|// Finally get the string from the constant pool
name|c
operator|=
name|getConstant
argument_list|(
name|i
argument_list|,
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
expr_stmt|;
return|return
operator|(
operator|(
name|ConstantUtf8
operator|)
name|c
operator|)
operator|.
name|getBytes
argument_list|()
return|;
block|}
comment|/**      * @return Length of constant pool.      */
specifier|public
name|int
name|getLength
parameter_list|()
block|{
return|return
name|constantPool
operator|==
literal|null
condition|?
literal|0
else|:
name|constantPool
operator|.
name|length
return|;
block|}
comment|/**      * @param constant Constant to set      */
specifier|public
name|void
name|setConstant
parameter_list|(
specifier|final
name|int
name|index
parameter_list|,
specifier|final
name|Constant
name|constant
parameter_list|)
block|{
name|constantPool
index|[
name|index
index|]
operator|=
name|constant
expr_stmt|;
block|}
comment|/**      * @param constantPool      */
specifier|public
name|void
name|setConstantPool
parameter_list|(
specifier|final
name|Constant
index|[]
name|constantPool
parameter_list|)
block|{
name|this
operator|.
name|constantPool
operator|=
name|constantPool
expr_stmt|;
block|}
comment|/**      * @return String representation.      */
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
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|constantPool
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
name|i
argument_list|)
operator|.
name|append
argument_list|(
literal|")"
argument_list|)
operator|.
name|append
argument_list|(
name|constantPool
index|[
name|i
index|]
argument_list|)
operator|.
name|append
argument_list|(
literal|"\n"
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
block|}
end_class

end_unit

