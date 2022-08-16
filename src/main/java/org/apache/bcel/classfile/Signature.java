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
name|ByteArrayInputStream
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
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
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
comment|/**  * This class is derived from<em>Attribute</em> and represents a reference to a GJ attribute.  *  * @see Attribute  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|Signature
extends|extends
name|Attribute
block|{
comment|/**      * Extends ByteArrayInputStream to make 'unreading' chars possible.      */
specifier|private
specifier|static
specifier|final
class|class
name|MyByteArrayInputStream
extends|extends
name|ByteArrayInputStream
block|{
name|MyByteArrayInputStream
parameter_list|(
specifier|final
name|String
name|data
parameter_list|)
block|{
name|super
argument_list|(
name|data
operator|.
name|getBytes
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|getData
parameter_list|()
block|{
return|return
operator|new
name|String
argument_list|(
name|buf
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
return|;
block|}
name|void
name|unread
parameter_list|()
block|{
if|if
condition|(
name|pos
operator|>
literal|0
condition|)
block|{
name|pos
operator|--
expr_stmt|;
block|}
block|}
block|}
specifier|private
specifier|static
name|boolean
name|identStart
parameter_list|(
specifier|final
name|int
name|ch
parameter_list|)
block|{
return|return
name|ch
operator|==
literal|'T'
operator|||
name|ch
operator|==
literal|'L'
return|;
block|}
comment|// @since 6.0 is no longer final
specifier|public
specifier|static
name|boolean
name|isActualParameterList
parameter_list|(
specifier|final
name|String
name|s
parameter_list|)
block|{
return|return
name|s
operator|.
name|startsWith
argument_list|(
literal|"L"
argument_list|)
operator|&&
name|s
operator|.
name|endsWith
argument_list|(
literal|">;"
argument_list|)
return|;
block|}
comment|// @since 6.0 is no longer final
specifier|public
specifier|static
name|boolean
name|isFormalParameterList
parameter_list|(
specifier|final
name|String
name|s
parameter_list|)
block|{
return|return
name|s
operator|.
name|startsWith
argument_list|(
literal|"<"
argument_list|)
operator|&&
name|s
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
operator|>
literal|0
return|;
block|}
specifier|private
specifier|static
name|void
name|matchGJIdent
parameter_list|(
specifier|final
name|MyByteArrayInputStream
name|in
parameter_list|,
specifier|final
name|StringBuilder
name|buf
parameter_list|)
block|{
name|int
name|ch
decl_stmt|;
name|matchIdent
argument_list|(
name|in
argument_list|,
name|buf
argument_list|)
expr_stmt|;
name|ch
operator|=
name|in
operator|.
name|read
argument_list|()
expr_stmt|;
if|if
condition|(
name|ch
operator|==
literal|'<'
operator|||
name|ch
operator|==
literal|'('
condition|)
block|{
comment|// Parameterized or method
comment|// System.out.println("Enter<");
name|buf
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|ch
argument_list|)
expr_stmt|;
name|matchGJIdent
argument_list|(
name|in
argument_list|,
name|buf
argument_list|)
expr_stmt|;
while|while
condition|(
operator|(
name|ch
operator|=
name|in
operator|.
name|read
argument_list|()
operator|)
operator|!=
literal|'>'
operator|&&
name|ch
operator|!=
literal|')'
condition|)
block|{
comment|// List of parameters
if|if
condition|(
name|ch
operator|==
operator|-
literal|1
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Illegal signature: "
operator|+
name|in
operator|.
name|getData
argument_list|()
operator|+
literal|" reaching EOF"
argument_list|)
throw|;
block|}
comment|// System.out.println("Still no>");
name|buf
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|in
operator|.
name|unread
argument_list|()
expr_stmt|;
name|matchGJIdent
argument_list|(
name|in
argument_list|,
name|buf
argument_list|)
expr_stmt|;
comment|// Recursive call
block|}
comment|// System.out.println("Exit>");
name|buf
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|ch
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|in
operator|.
name|unread
argument_list|()
expr_stmt|;
block|}
name|ch
operator|=
name|in
operator|.
name|read
argument_list|()
expr_stmt|;
if|if
condition|(
name|identStart
argument_list|(
name|ch
argument_list|)
condition|)
block|{
name|in
operator|.
name|unread
argument_list|()
expr_stmt|;
name|matchGJIdent
argument_list|(
name|in
argument_list|,
name|buf
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|ch
operator|==
literal|')'
condition|)
block|{
name|in
operator|.
name|unread
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|ch
operator|!=
literal|';'
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Illegal signature: "
operator|+
name|in
operator|.
name|getData
argument_list|()
operator|+
literal|" read "
operator|+
operator|(
name|char
operator|)
name|ch
argument_list|)
throw|;
block|}
block|}
specifier|private
specifier|static
name|void
name|matchIdent
parameter_list|(
specifier|final
name|MyByteArrayInputStream
name|in
parameter_list|,
specifier|final
name|StringBuilder
name|buf
parameter_list|)
block|{
name|int
name|ch
decl_stmt|;
if|if
condition|(
operator|(
name|ch
operator|=
name|in
operator|.
name|read
argument_list|()
operator|)
operator|==
operator|-
literal|1
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Illegal signature: "
operator|+
name|in
operator|.
name|getData
argument_list|()
operator|+
literal|" no ident, reaching EOF"
argument_list|)
throw|;
block|}
comment|// System.out.println("return from ident:" + (char)ch);
if|if
condition|(
operator|!
name|identStart
argument_list|(
name|ch
argument_list|)
condition|)
block|{
specifier|final
name|StringBuilder
name|buf2
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|int
name|count
init|=
literal|1
decl_stmt|;
while|while
condition|(
name|Character
operator|.
name|isJavaIdentifierPart
argument_list|(
operator|(
name|char
operator|)
name|ch
argument_list|)
condition|)
block|{
name|buf2
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|ch
argument_list|)
expr_stmt|;
name|count
operator|++
expr_stmt|;
name|ch
operator|=
name|in
operator|.
name|read
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|ch
operator|==
literal|':'
condition|)
block|{
comment|// Ok, formal parameter
name|in
operator|.
name|skip
argument_list|(
literal|"Ljava/lang/Object"
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|buf2
argument_list|)
expr_stmt|;
name|ch
operator|=
name|in
operator|.
name|read
argument_list|()
expr_stmt|;
name|in
operator|.
name|unread
argument_list|()
expr_stmt|;
comment|// System.out.println("so far:" + buf2 + ":next:" +(char)ch);
block|}
else|else
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
name|count
condition|;
name|i
operator|++
control|)
block|{
name|in
operator|.
name|unread
argument_list|()
expr_stmt|;
block|}
block|}
return|return;
block|}
specifier|final
name|StringBuilder
name|buf2
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|ch
operator|=
name|in
operator|.
name|read
argument_list|()
expr_stmt|;
do|do
block|{
name|buf2
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|ch
argument_list|)
expr_stmt|;
name|ch
operator|=
name|in
operator|.
name|read
argument_list|()
expr_stmt|;
comment|// System.out.println("within ident:"+ (char)ch);
block|}
do|while
condition|(
name|ch
operator|!=
operator|-
literal|1
operator|&&
operator|(
name|Character
operator|.
name|isJavaIdentifierPart
argument_list|(
operator|(
name|char
operator|)
name|ch
argument_list|)
operator|||
name|ch
operator|==
literal|'/'
operator|)
condition|)
do|;
name|buf
operator|.
name|append
argument_list|(
name|buf2
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|'/'
argument_list|,
literal|'.'
argument_list|)
argument_list|)
expr_stmt|;
comment|// System.out.println("regular return ident:"+ (char)ch + ":" + buf2);
if|if
condition|(
name|ch
operator|!=
operator|-
literal|1
condition|)
block|{
name|in
operator|.
name|unread
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|String
name|translate
parameter_list|(
specifier|final
name|String
name|s
parameter_list|)
block|{
comment|// System.out.println("Sig:" + s);
specifier|final
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|matchGJIdent
argument_list|(
operator|new
name|MyByteArrayInputStream
argument_list|(
name|s
argument_list|)
argument_list|,
name|buf
argument_list|)
expr_stmt|;
return|return
name|buf
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
name|int
name|signatureIndex
decl_stmt|;
comment|/**      * Construct object from file stream.      *      * @param name_index Index in constant pool to CONSTANT_Utf8      * @param length Content length in bytes      * @param input Input stream      * @param constant_pool Array of constants      * @throws IOException if an I/O error occurs.      */
name|Signature
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
name|input
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param name_index Index in constant pool to CONSTANT_Utf8      * @param length Content length in bytes      * @param signatureIndex Index in constant pool to CONSTANT_Utf8      * @param constant_pool Array of constants      */
specifier|public
name|Signature
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
name|signatureIndex
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
name|ATTR_SIGNATURE
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
name|signatureIndex
operator|=
name|signatureIndex
expr_stmt|;
block|}
comment|/**      * Initialize from another object. Note that both objects use the same references (shallow copy). Use clone() for a      * physical copy.      */
specifier|public
name|Signature
parameter_list|(
specifier|final
name|Signature
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
name|getSignatureIndex
argument_list|()
argument_list|,
name|c
operator|.
name|getConstantPool
argument_list|()
argument_list|)
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
comment|// System.err.println("Visiting non-standard Signature object");
name|v
operator|.
name|visitSignature
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
return|return
operator|(
name|Attribute
operator|)
name|clone
argument_list|()
return|;
block|}
comment|/**      * Dump source file attribute to file stream in binary format.      *      * @param file Output file stream      * @throws IOException if an I/O error occurs.      */
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
name|signatureIndex
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return GJ signature.      */
specifier|public
name|String
name|getSignature
parameter_list|()
block|{
specifier|final
name|ConstantUtf8
name|c
init|=
operator|(
name|ConstantUtf8
operator|)
name|super
operator|.
name|getConstantPool
argument_list|()
operator|.
name|getConstant
argument_list|(
name|signatureIndex
argument_list|,
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
decl_stmt|;
return|return
name|c
operator|.
name|getBytes
argument_list|()
return|;
block|}
comment|/**      * @return Index in constant pool of source file name.      */
specifier|public
name|int
name|getSignatureIndex
parameter_list|()
block|{
return|return
name|signatureIndex
return|;
block|}
comment|/**      * @param signatureIndex the index info the constant pool of this signature      */
specifier|public
name|void
name|setSignatureIndex
parameter_list|(
specifier|final
name|int
name|signatureIndex
parameter_list|)
block|{
name|this
operator|.
name|signatureIndex
operator|=
name|signatureIndex
expr_stmt|;
block|}
comment|/**      * @return String representation      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
specifier|final
name|String
name|s
init|=
name|getSignature
argument_list|()
decl_stmt|;
return|return
literal|"Signature: "
operator|+
name|s
return|;
block|}
block|}
end_class

end_unit

