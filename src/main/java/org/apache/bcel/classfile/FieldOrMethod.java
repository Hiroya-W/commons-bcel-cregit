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
name|Arrays
import|;
end_import

begin_comment
comment|/**  * Abstract super class for fields and methods.  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|FieldOrMethod
extends|extends
name|AccessFlags
implements|implements
name|Cloneable
implements|,
name|Node
block|{
comment|/**      * @deprecated (since 6.0) will be made private; do not access directly, use getter/setter      */
annotation|@
name|java
operator|.
name|lang
operator|.
name|Deprecated
specifier|protected
name|int
name|name_index
decl_stmt|;
comment|// Points to field name in constant pool
comment|/**      * @deprecated (since 6.0) will be made private; do not access directly, use getter/setter      */
annotation|@
name|java
operator|.
name|lang
operator|.
name|Deprecated
specifier|protected
name|int
name|signature_index
decl_stmt|;
comment|// Points to encoded signature
comment|/**      * @deprecated (since 6.0) will be made private; do not access directly, use getter/setter      */
annotation|@
name|java
operator|.
name|lang
operator|.
name|Deprecated
specifier|protected
name|Attribute
index|[]
name|attributes
decl_stmt|;
comment|// Collection of attributes
comment|/**      * @deprecated (since 6.0) will be removed (not needed)      */
annotation|@
name|java
operator|.
name|lang
operator|.
name|Deprecated
specifier|protected
name|int
name|attributes_count
decl_stmt|;
comment|// No. of attributes
comment|// @since 6.0
specifier|private
name|AnnotationEntry
index|[]
name|annotationEntries
decl_stmt|;
comment|// annotations defined on the field or method
comment|/**      * @deprecated (since 6.0) will be made private; do not access directly, use getter/setter      */
annotation|@
name|java
operator|.
name|lang
operator|.
name|Deprecated
specifier|protected
name|ConstantPool
name|constant_pool
decl_stmt|;
specifier|private
name|String
name|signatureAttributeString
decl_stmt|;
specifier|private
name|boolean
name|searchedForSignatureAttribute
decl_stmt|;
name|FieldOrMethod
parameter_list|()
block|{
block|}
comment|/**      * Construct object from file stream.      *      * @param file Input stream      * @throws IOException if an I/O error occurs.      */
specifier|protected
name|FieldOrMethod
parameter_list|(
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
literal|null
argument_list|,
name|constantPool
argument_list|)
expr_stmt|;
specifier|final
name|int
name|attributes_count
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
name|attributes_count
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
name|attributes_count
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
name|this
operator|.
name|attributes_count
operator|=
name|attributes_count
expr_stmt|;
comment|// init deprecated field
block|}
comment|/**      * Construct object from file stream.      *      * @param file Input stream      * @throws IOException if an I/O error occurs.      * @deprecated (6.0) Use {@link #FieldOrMethod(java.io.DataInput, ConstantPool)} instead.      */
annotation|@
name|java
operator|.
name|lang
operator|.
name|Deprecated
specifier|protected
name|FieldOrMethod
parameter_list|(
specifier|final
name|DataInputStream
name|file
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
operator|(
name|DataInput
operator|)
name|file
argument_list|,
name|constantPool
argument_list|)
expr_stmt|;
block|}
comment|/**      * Initialize from another object. Note that both objects use the same references (shallow copy). Use clone() for a      * physical copy.      */
specifier|protected
name|FieldOrMethod
parameter_list|(
specifier|final
name|FieldOrMethod
name|c
parameter_list|)
block|{
name|this
argument_list|(
name|c
operator|.
name|getAccessFlags
argument_list|()
argument_list|,
name|c
operator|.
name|getNameIndex
argument_list|()
argument_list|,
name|c
operator|.
name|getSignatureIndex
argument_list|()
argument_list|,
name|c
operator|.
name|getAttributes
argument_list|()
argument_list|,
name|c
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param access_flags Access rights of method      * @param name_index Points to field name in constant pool      * @param signature_index Points to encoded signature      * @param attributes Collection of attributes      * @param constant_pool Array of constants      */
specifier|protected
name|FieldOrMethod
parameter_list|(
specifier|final
name|int
name|access_flags
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
name|Attribute
index|[]
name|attributes
parameter_list|,
specifier|final
name|ConstantPool
name|constant_pool
parameter_list|)
block|{
name|super
argument_list|(
name|access_flags
argument_list|)
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
name|constant_pool
operator|=
name|constant_pool
expr_stmt|;
name|setAttributes
argument_list|(
name|attributes
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return deep copy of this field      */
specifier|protected
name|FieldOrMethod
name|copy_
parameter_list|(
specifier|final
name|ConstantPool
name|constantPool
parameter_list|)
block|{
try|try
block|{
specifier|final
name|FieldOrMethod
name|c
init|=
operator|(
name|FieldOrMethod
operator|)
name|clone
argument_list|()
decl_stmt|;
name|c
operator|.
name|constant_pool
operator|=
name|constant_pool
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
name|c
operator|.
name|attributes_count
operator|=
name|attributes_count
expr_stmt|;
comment|// init deprecated field
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
name|constant_pool
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|c
return|;
block|}
catch|catch
parameter_list|(
specifier|final
name|CloneNotSupportedException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
comment|/**      * Dump object to file stream on binary format.      *      * @param file Output file stream      * @throws IOException if an I/O error occurs.      */
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
name|file
operator|.
name|writeShort
argument_list|(
name|super
operator|.
name|getAccessFlags
argument_list|()
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|name_index
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|signature_index
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|attributes_count
argument_list|)
expr_stmt|;
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
name|attribute
operator|.
name|dump
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * @return Annotations on the field or method      * @since 6.0      */
specifier|public
name|AnnotationEntry
index|[]
name|getAnnotationEntries
parameter_list|()
block|{
if|if
condition|(
name|annotationEntries
operator|==
literal|null
condition|)
block|{
name|annotationEntries
operator|=
name|AnnotationEntry
operator|.
name|createAnnotationEntries
argument_list|(
name|getAttributes
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|annotationEntries
return|;
block|}
comment|/**      * @return Collection of object attributes.      */
specifier|public
specifier|final
name|Attribute
index|[]
name|getAttributes
parameter_list|()
block|{
return|return
name|attributes
return|;
block|}
comment|/**      * @return Constant pool used by this object.      */
specifier|public
specifier|final
name|ConstantPool
name|getConstantPool
parameter_list|()
block|{
return|return
name|constant_pool
return|;
block|}
comment|/**      * Hunts for a signature attribute on the member and returns its contents. So where the 'regular' signature may be      * (Ljava/util/Vector;)V the signature attribute may in fact say 'Ljava/lang/Vector&lt;Ljava/lang/String&gt;;' Coded for      * performance - searches for the attribute only when requested - only searches for it once.      *      * @since 6.0      */
specifier|public
specifier|final
name|String
name|getGenericSignature
parameter_list|()
block|{
if|if
condition|(
operator|!
name|searchedForSignatureAttribute
condition|)
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
operator|!
name|found
operator|&&
name|i
operator|<
name|attributes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|attributes
index|[
name|i
index|]
operator|instanceof
name|Signature
condition|)
block|{
name|signatureAttributeString
operator|=
operator|(
operator|(
name|Signature
operator|)
name|attributes
index|[
name|i
index|]
operator|)
operator|.
name|getSignature
argument_list|()
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
name|searchedForSignatureAttribute
operator|=
literal|true
expr_stmt|;
block|}
return|return
name|signatureAttributeString
return|;
block|}
comment|/**      * @return Name of object, i.e., method name or field name      */
specifier|public
specifier|final
name|String
name|getName
parameter_list|()
block|{
return|return
name|constant_pool
operator|.
name|getConstantUtf8
argument_list|(
name|name_index
argument_list|)
operator|.
name|getBytes
argument_list|()
return|;
block|}
comment|/**      * @return Index in constant pool of object's name.      */
specifier|public
specifier|final
name|int
name|getNameIndex
parameter_list|()
block|{
return|return
name|name_index
return|;
block|}
comment|/**      * @return String representation of object's type signature (java style)      */
specifier|public
specifier|final
name|String
name|getSignature
parameter_list|()
block|{
return|return
name|constant_pool
operator|.
name|getConstantUtf8
argument_list|(
name|signature_index
argument_list|)
operator|.
name|getBytes
argument_list|()
return|;
block|}
comment|/**      * @return Index in constant pool of field signature.      */
specifier|public
specifier|final
name|int
name|getSignatureIndex
parameter_list|()
block|{
return|return
name|signature_index
return|;
block|}
comment|/**      * @param attributes Collection of object attributes.      */
specifier|public
specifier|final
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
expr_stmt|;
name|this
operator|.
name|attributes_count
operator|=
name|attributes
operator|!=
literal|null
condition|?
name|attributes
operator|.
name|length
else|:
literal|0
expr_stmt|;
comment|// init deprecated field
block|}
comment|/**      * @param constant_pool Constant pool to be used for this object.      */
specifier|public
specifier|final
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
comment|/**      * @param name_index Index in constant pool of object's name.      */
specifier|public
specifier|final
name|void
name|setNameIndex
parameter_list|(
specifier|final
name|int
name|name_index
parameter_list|)
block|{
name|this
operator|.
name|name_index
operator|=
name|name_index
expr_stmt|;
block|}
comment|/**      * @param signature_index Index in constant pool of field signature.      */
specifier|public
specifier|final
name|void
name|setSignatureIndex
parameter_list|(
specifier|final
name|int
name|signature_index
parameter_list|)
block|{
name|this
operator|.
name|signature_index
operator|=
name|signature_index
expr_stmt|;
block|}
block|}
end_class

end_unit

