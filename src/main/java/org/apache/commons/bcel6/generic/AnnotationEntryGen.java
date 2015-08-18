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
name|generic
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|classfile
operator|.
name|AnnotationEntry
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
name|classfile
operator|.
name|Attribute
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
name|commons
operator|.
name|bcel6
operator|.
name|classfile
operator|.
name|ElementValuePair
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
name|classfile
operator|.
name|RuntimeInvisibleAnnotations
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
name|classfile
operator|.
name|RuntimeInvisibleParameterAnnotations
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
name|classfile
operator|.
name|RuntimeVisibleAnnotations
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
name|classfile
operator|.
name|RuntimeVisibleParameterAnnotations
import|;
end_import

begin_comment
comment|/**  * @since 6.0  */
end_comment

begin_class
specifier|public
class|class
name|AnnotationEntryGen
block|{
specifier|private
name|int
name|typeIndex
decl_stmt|;
specifier|private
name|List
argument_list|<
name|ElementValuePairGen
argument_list|>
name|evs
decl_stmt|;
specifier|private
specifier|final
name|ConstantPoolGen
name|cpool
decl_stmt|;
specifier|private
name|boolean
name|isRuntimeVisible
init|=
literal|false
decl_stmt|;
comment|/**      * Here we are taking a fixed annotation of type Annotation and building a      * modifiable AnnotationGen object. If the pool passed in is for a different      * class file, then copyPoolEntries should have been passed as true as that      * will force us to do a deep copy of the annotation and move the cpool      * entries across. We need to copy the type and the element name value pairs      * and the visibility.      */
specifier|public
name|AnnotationEntryGen
parameter_list|(
name|AnnotationEntry
name|a
parameter_list|,
name|ConstantPoolGen
name|cpool
parameter_list|,
name|boolean
name|copyPoolEntries
parameter_list|)
block|{
name|this
operator|.
name|cpool
operator|=
name|cpool
expr_stmt|;
if|if
condition|(
name|copyPoolEntries
condition|)
block|{
name|typeIndex
operator|=
name|cpool
operator|.
name|addUtf8
argument_list|(
name|a
operator|.
name|getAnnotationType
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|typeIndex
operator|=
name|a
operator|.
name|getAnnotationTypeIndex
argument_list|()
expr_stmt|;
block|}
name|isRuntimeVisible
operator|=
name|a
operator|.
name|isRuntimeVisible
argument_list|()
expr_stmt|;
name|evs
operator|=
name|copyValues
argument_list|(
name|a
operator|.
name|getElementValuePairs
argument_list|()
argument_list|,
name|cpool
argument_list|,
name|copyPoolEntries
argument_list|)
expr_stmt|;
block|}
specifier|private
name|List
argument_list|<
name|ElementValuePairGen
argument_list|>
name|copyValues
parameter_list|(
name|ElementValuePair
index|[]
name|in
parameter_list|,
name|ConstantPoolGen
name|cpool
parameter_list|,
name|boolean
name|copyPoolEntries
parameter_list|)
block|{
name|List
argument_list|<
name|ElementValuePairGen
argument_list|>
name|out
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|ElementValuePair
name|nvp
range|:
name|in
control|)
block|{
name|out
operator|.
name|add
argument_list|(
operator|new
name|ElementValuePairGen
argument_list|(
name|nvp
argument_list|,
name|cpool
argument_list|,
name|copyPoolEntries
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|out
return|;
block|}
specifier|private
name|AnnotationEntryGen
parameter_list|(
name|ConstantPoolGen
name|cpool
parameter_list|)
block|{
name|this
operator|.
name|cpool
operator|=
name|cpool
expr_stmt|;
block|}
comment|/**      * Retrieve an immutable version of this AnnotationGen      */
specifier|public
name|AnnotationEntry
name|getAnnotation
parameter_list|()
block|{
name|AnnotationEntry
name|a
init|=
operator|new
name|AnnotationEntry
argument_list|(
name|typeIndex
argument_list|,
name|cpool
operator|.
name|getConstantPool
argument_list|()
argument_list|,
name|isRuntimeVisible
argument_list|)
decl_stmt|;
for|for
control|(
name|ElementValuePairGen
name|element
range|:
name|evs
control|)
block|{
name|a
operator|.
name|addElementNameValuePair
argument_list|(
name|element
operator|.
name|getElementNameValuePair
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|a
return|;
block|}
specifier|public
name|AnnotationEntryGen
parameter_list|(
name|ObjectType
name|type
parameter_list|,
name|List
argument_list|<
name|ElementValuePairGen
argument_list|>
name|elements
parameter_list|,
name|boolean
name|vis
parameter_list|,
name|ConstantPoolGen
name|cpool
parameter_list|)
block|{
name|this
operator|.
name|cpool
operator|=
name|cpool
expr_stmt|;
name|this
operator|.
name|typeIndex
operator|=
name|cpool
operator|.
name|addUtf8
argument_list|(
name|type
operator|.
name|getSignature
argument_list|()
argument_list|)
expr_stmt|;
name|evs
operator|=
name|elements
expr_stmt|;
name|isRuntimeVisible
operator|=
name|vis
expr_stmt|;
block|}
specifier|public
specifier|static
name|AnnotationEntryGen
name|read
parameter_list|(
name|DataInput
name|dis
parameter_list|,
name|ConstantPoolGen
name|cpool
parameter_list|,
name|boolean
name|b
parameter_list|)
throws|throws
name|IOException
block|{
name|AnnotationEntryGen
name|a
init|=
operator|new
name|AnnotationEntryGen
argument_list|(
name|cpool
argument_list|)
decl_stmt|;
name|a
operator|.
name|typeIndex
operator|=
name|dis
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|int
name|elemValuePairCount
init|=
name|dis
operator|.
name|readUnsignedShort
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
name|elemValuePairCount
condition|;
name|i
operator|++
control|)
block|{
name|int
name|nidx
init|=
name|dis
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|a
operator|.
name|addElementNameValuePair
argument_list|(
operator|new
name|ElementValuePairGen
argument_list|(
name|nidx
argument_list|,
name|ElementValueGen
operator|.
name|readElementValue
argument_list|(
name|dis
argument_list|,
name|cpool
argument_list|)
argument_list|,
name|cpool
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|a
operator|.
name|isRuntimeVisible
argument_list|(
name|b
argument_list|)
expr_stmt|;
return|return
name|a
return|;
block|}
specifier|public
name|void
name|dump
parameter_list|(
name|DataOutputStream
name|dos
parameter_list|)
throws|throws
name|IOException
block|{
name|dos
operator|.
name|writeShort
argument_list|(
name|typeIndex
argument_list|)
expr_stmt|;
comment|// u2 index of type name in cpool
name|dos
operator|.
name|writeShort
argument_list|(
name|evs
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
comment|// u2 element_value pair count
for|for
control|(
name|ElementValuePairGen
name|envp
range|:
name|evs
control|)
block|{
name|envp
operator|.
name|dump
argument_list|(
name|dos
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|addElementNameValuePair
parameter_list|(
name|ElementValuePairGen
name|evp
parameter_list|)
block|{
if|if
condition|(
name|evs
operator|==
literal|null
condition|)
block|{
name|evs
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
block|}
name|evs
operator|.
name|add
argument_list|(
name|evp
argument_list|)
expr_stmt|;
block|}
specifier|public
name|int
name|getTypeIndex
parameter_list|()
block|{
return|return
name|typeIndex
return|;
block|}
specifier|public
specifier|final
name|String
name|getTypeSignature
parameter_list|()
block|{
comment|// ConstantClass c = (ConstantClass)cpool.getConstant(typeIndex);
name|ConstantUtf8
name|utf8
init|=
operator|(
name|ConstantUtf8
operator|)
name|cpool
operator|.
name|getConstant
argument_list|(
name|typeIndex
comment|/* c.getNameIndex() */
argument_list|)
decl_stmt|;
return|return
name|utf8
operator|.
name|getBytes
argument_list|()
return|;
block|}
specifier|public
specifier|final
name|String
name|getTypeName
parameter_list|()
block|{
return|return
name|getTypeSignature
argument_list|()
return|;
comment|// BCELBUG: Should I use this instead?
comment|// Utility.signatureToString(getTypeSignature());
block|}
comment|/**      * Returns list of ElementNameValuePair objects      */
specifier|public
name|List
argument_list|<
name|ElementValuePairGen
argument_list|>
name|getValues
parameter_list|()
block|{
return|return
name|evs
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|StringBuilder
name|s
init|=
operator|new
name|StringBuilder
argument_list|(
literal|32
argument_list|)
decl_stmt|;
name|s
operator|.
name|append
argument_list|(
literal|"AnnotationGen:["
argument_list|)
operator|.
name|append
argument_list|(
name|getTypeName
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|" #"
argument_list|)
operator|.
name|append
argument_list|(
name|evs
operator|.
name|size
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|" {"
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
name|evs
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|s
operator|.
name|append
argument_list|(
name|evs
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|+
literal|1
operator|<
name|evs
operator|.
name|size
argument_list|()
condition|)
block|{
name|s
operator|.
name|append
argument_list|(
literal|","
argument_list|)
expr_stmt|;
block|}
block|}
name|s
operator|.
name|append
argument_list|(
literal|"}]"
argument_list|)
expr_stmt|;
return|return
name|s
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|String
name|toShortString
parameter_list|()
block|{
name|StringBuilder
name|s
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|s
operator|.
name|append
argument_list|(
literal|"@"
argument_list|)
operator|.
name|append
argument_list|(
name|getTypeName
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"("
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
name|evs
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|s
operator|.
name|append
argument_list|(
name|evs
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|+
literal|1
operator|<
name|evs
operator|.
name|size
argument_list|()
condition|)
block|{
name|s
operator|.
name|append
argument_list|(
literal|","
argument_list|)
expr_stmt|;
block|}
block|}
name|s
operator|.
name|append
argument_list|(
literal|")"
argument_list|)
expr_stmt|;
return|return
name|s
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
name|void
name|isRuntimeVisible
parameter_list|(
name|boolean
name|b
parameter_list|)
block|{
name|isRuntimeVisible
operator|=
name|b
expr_stmt|;
block|}
specifier|public
name|boolean
name|isRuntimeVisible
parameter_list|()
block|{
return|return
name|isRuntimeVisible
return|;
block|}
comment|/**      * Converts a list of AnnotationGen objects into a set of attributes      * that can be attached to the class file.      *      * @param cp  The constant pool gen where we can create the necessary name refs      * @param annotationEntryGens An array of AnnotationGen objects      */
specifier|static
name|Attribute
index|[]
name|getAnnotationAttributes
parameter_list|(
name|ConstantPoolGen
name|cp
parameter_list|,
name|AnnotationEntryGen
index|[]
name|annotationEntryGens
parameter_list|)
block|{
if|if
condition|(
name|annotationEntryGens
operator|.
name|length
operator|==
literal|0
condition|)
block|{
return|return
operator|new
name|Attribute
index|[
literal|0
index|]
return|;
block|}
try|try
block|{
name|int
name|countVisible
init|=
literal|0
decl_stmt|;
name|int
name|countInvisible
init|=
literal|0
decl_stmt|;
comment|//  put the annotations in the right output stream
for|for
control|(
name|AnnotationEntryGen
name|a
range|:
name|annotationEntryGens
control|)
block|{
if|if
condition|(
name|a
operator|.
name|isRuntimeVisible
argument_list|()
condition|)
block|{
name|countVisible
operator|++
expr_stmt|;
block|}
else|else
block|{
name|countInvisible
operator|++
expr_stmt|;
block|}
block|}
name|ByteArrayOutputStream
name|rvaBytes
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|ByteArrayOutputStream
name|riaBytes
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|DataOutputStream
name|rvaDos
init|=
operator|new
name|DataOutputStream
argument_list|(
name|rvaBytes
argument_list|)
decl_stmt|;
name|DataOutputStream
name|riaDos
init|=
operator|new
name|DataOutputStream
argument_list|(
name|riaBytes
argument_list|)
decl_stmt|;
name|rvaDos
operator|.
name|writeShort
argument_list|(
name|countVisible
argument_list|)
expr_stmt|;
name|riaDos
operator|.
name|writeShort
argument_list|(
name|countInvisible
argument_list|)
expr_stmt|;
comment|// put the annotations in the right output stream
for|for
control|(
name|AnnotationEntryGen
name|a
range|:
name|annotationEntryGens
control|)
block|{
if|if
condition|(
name|a
operator|.
name|isRuntimeVisible
argument_list|()
condition|)
block|{
name|a
operator|.
name|dump
argument_list|(
name|rvaDos
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|a
operator|.
name|dump
argument_list|(
name|riaDos
argument_list|)
expr_stmt|;
block|}
block|}
name|rvaDos
operator|.
name|close
argument_list|()
expr_stmt|;
name|riaDos
operator|.
name|close
argument_list|()
expr_stmt|;
name|byte
index|[]
name|rvaData
init|=
name|rvaBytes
operator|.
name|toByteArray
argument_list|()
decl_stmt|;
name|byte
index|[]
name|riaData
init|=
name|riaBytes
operator|.
name|toByteArray
argument_list|()
decl_stmt|;
name|int
name|rvaIndex
init|=
operator|-
literal|1
decl_stmt|;
name|int
name|riaIndex
init|=
operator|-
literal|1
decl_stmt|;
if|if
condition|(
name|rvaData
operator|.
name|length
operator|>
literal|2
condition|)
block|{
name|rvaIndex
operator|=
name|cp
operator|.
name|addUtf8
argument_list|(
literal|"RuntimeVisibleAnnotations"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|riaData
operator|.
name|length
operator|>
literal|2
condition|)
block|{
name|riaIndex
operator|=
name|cp
operator|.
name|addUtf8
argument_list|(
literal|"RuntimeInvisibleAnnotations"
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|Attribute
argument_list|>
name|newAttributes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|rvaData
operator|.
name|length
operator|>
literal|2
condition|)
block|{
name|newAttributes
operator|.
name|add
argument_list|(
operator|new
name|RuntimeVisibleAnnotations
argument_list|(
name|rvaIndex
argument_list|,
name|rvaData
operator|.
name|length
argument_list|,
operator|new
name|DataInputStream
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|rvaData
argument_list|)
argument_list|)
argument_list|,
name|cp
operator|.
name|getConstantPool
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|riaData
operator|.
name|length
operator|>
literal|2
condition|)
block|{
name|newAttributes
operator|.
name|add
argument_list|(
operator|new
name|RuntimeInvisibleAnnotations
argument_list|(
name|riaIndex
argument_list|,
name|riaData
operator|.
name|length
argument_list|,
operator|new
name|DataInputStream
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|riaData
argument_list|)
argument_list|)
argument_list|,
name|cp
operator|.
name|getConstantPool
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|newAttributes
operator|.
name|toArray
argument_list|(
operator|new
name|Attribute
index|[
name|newAttributes
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"IOException whilst processing annotations"
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
comment|/**      * Annotations against a class are stored in one of four attribute kinds:      * - RuntimeVisibleParameterAnnotations      * - RuntimeInvisibleParameterAnnotations      */
specifier|static
name|Attribute
index|[]
name|getParameterAnnotationAttributes
parameter_list|(
name|ConstantPoolGen
name|cp
parameter_list|,
name|List
argument_list|<
name|AnnotationEntryGen
argument_list|>
index|[]
comment|/*Array of lists, array size depends on #params */
name|vec
parameter_list|)
block|{
name|int
name|visCount
index|[]
init|=
operator|new
name|int
index|[
name|vec
operator|.
name|length
index|]
decl_stmt|;
name|int
name|totalVisCount
init|=
literal|0
decl_stmt|;
name|int
name|invisCount
index|[]
init|=
operator|new
name|int
index|[
name|vec
operator|.
name|length
index|]
decl_stmt|;
name|int
name|totalInvisCount
init|=
literal|0
decl_stmt|;
try|try
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
name|vec
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|vec
index|[
name|i
index|]
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|AnnotationEntryGen
name|element
range|:
name|vec
index|[
name|i
index|]
control|)
block|{
if|if
condition|(
name|element
operator|.
name|isRuntimeVisible
argument_list|()
condition|)
block|{
name|visCount
index|[
name|i
index|]
operator|++
expr_stmt|;
name|totalVisCount
operator|++
expr_stmt|;
block|}
else|else
block|{
name|invisCount
index|[
name|i
index|]
operator|++
expr_stmt|;
name|totalInvisCount
operator|++
expr_stmt|;
block|}
block|}
block|}
block|}
comment|// Lets do the visible ones
name|ByteArrayOutputStream
name|rvaBytes
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|DataOutputStream
name|rvaDos
init|=
operator|new
name|DataOutputStream
argument_list|(
name|rvaBytes
argument_list|)
decl_stmt|;
name|rvaDos
operator|.
name|writeByte
argument_list|(
name|vec
operator|.
name|length
argument_list|)
expr_stmt|;
comment|// First goes number of parameters
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|vec
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|rvaDos
operator|.
name|writeShort
argument_list|(
name|visCount
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|visCount
index|[
name|i
index|]
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|AnnotationEntryGen
name|element
range|:
name|vec
index|[
name|i
index|]
control|)
block|{
if|if
condition|(
name|element
operator|.
name|isRuntimeVisible
argument_list|()
condition|)
block|{
name|element
operator|.
name|dump
argument_list|(
name|rvaDos
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
name|rvaDos
operator|.
name|close
argument_list|()
expr_stmt|;
comment|// Lets do the invisible ones
name|ByteArrayOutputStream
name|riaBytes
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|DataOutputStream
name|riaDos
init|=
operator|new
name|DataOutputStream
argument_list|(
name|riaBytes
argument_list|)
decl_stmt|;
name|riaDos
operator|.
name|writeByte
argument_list|(
name|vec
operator|.
name|length
argument_list|)
expr_stmt|;
comment|// First goes number of parameters
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|vec
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|riaDos
operator|.
name|writeShort
argument_list|(
name|invisCount
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|invisCount
index|[
name|i
index|]
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|AnnotationEntryGen
name|element
range|:
name|vec
index|[
name|i
index|]
control|)
block|{
if|if
condition|(
operator|!
name|element
operator|.
name|isRuntimeVisible
argument_list|()
condition|)
block|{
name|element
operator|.
name|dump
argument_list|(
name|riaDos
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
name|riaDos
operator|.
name|close
argument_list|()
expr_stmt|;
name|byte
index|[]
name|rvaData
init|=
name|rvaBytes
operator|.
name|toByteArray
argument_list|()
decl_stmt|;
name|byte
index|[]
name|riaData
init|=
name|riaBytes
operator|.
name|toByteArray
argument_list|()
decl_stmt|;
name|int
name|rvaIndex
init|=
operator|-
literal|1
decl_stmt|;
name|int
name|riaIndex
init|=
operator|-
literal|1
decl_stmt|;
if|if
condition|(
name|totalVisCount
operator|>
literal|0
condition|)
block|{
name|rvaIndex
operator|=
name|cp
operator|.
name|addUtf8
argument_list|(
literal|"RuntimeVisibleParameterAnnotations"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|totalInvisCount
operator|>
literal|0
condition|)
block|{
name|riaIndex
operator|=
name|cp
operator|.
name|addUtf8
argument_list|(
literal|"RuntimeInvisibleParameterAnnotations"
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|Attribute
argument_list|>
name|newAttributes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|totalVisCount
operator|>
literal|0
condition|)
block|{
name|newAttributes
operator|.
name|add
argument_list|(
operator|new
name|RuntimeVisibleParameterAnnotations
argument_list|(
name|rvaIndex
argument_list|,
name|rvaData
operator|.
name|length
argument_list|,
operator|new
name|DataInputStream
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|rvaData
argument_list|)
argument_list|)
argument_list|,
name|cp
operator|.
name|getConstantPool
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|totalInvisCount
operator|>
literal|0
condition|)
block|{
name|newAttributes
operator|.
name|add
argument_list|(
operator|new
name|RuntimeInvisibleParameterAnnotations
argument_list|(
name|riaIndex
argument_list|,
name|riaData
operator|.
name|length
argument_list|,
operator|new
name|DataInputStream
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|riaData
argument_list|)
argument_list|)
argument_list|,
name|cp
operator|.
name|getConstantPool
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|newAttributes
operator|.
name|toArray
argument_list|(
operator|new
name|Attribute
index|[
name|newAttributes
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"IOException whilst processing parameter annotations"
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

