begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
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

begin_comment
comment|/* ====================================================================  * The Apache Software License, Version 1.1  *  * Copyright (c) 2001 The Apache Software Foundation.  All rights  * reserved.  *  * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions  * are met:  *  * 1. Redistributions of source code must retain the above copyright  *    notice, this list of conditions and the following disclaimer.  *  * 2. Redistributions in binary form must reproduce the above copyright  *    notice, this list of conditions and the following disclaimer in  *    the documentation and/or other materials provided with the  *    distribution.  *  * 3. The end-user documentation included with the redistribution,  *    if any, must include the following acknowledgment:  *       "This product includes software developed by the  *        Apache Software Foundation (http://www.apache.org/)."  *    Alternately, this acknowledgment may appear in the software itself,  *    if and wherever such third-party acknowledgments normally appear.  *  * 4. The names "Apache" and "Apache Software Foundation" and  *    "Apache BCEL" must not be used to endorse or promote products  *    derived from this software without prior written permission. For  *    written permission, please contact apache@apache.org.  *  * 5. Products derived from this software may not be called "Apache",  *    "Apache BCEL", nor may "Apache" appear in their name, without  *    prior written permission of the Apache Software Foundation.  *  * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED  * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES  * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE  * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR  * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF  * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND  * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,  * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT  * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF  * SUCH DAMAGE.  * ====================================================================  *  * This software consists of voluntary contributions made by many  * individuals on behalf of the Apache Software Foundation.  For more  * information on the Apache Software Foundation, please see  *<http://www.apache.org/>.  */
end_comment

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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * This class represents a reference to an unknown (i.e.,  * application-specific) attribute of a class.  It is instantiated  * from the<em>Attribute.readAttribute()</em> method.  *  * @version $Id$  * @author<A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|Unknown
extends|extends
name|Attribute
block|{
specifier|private
name|byte
index|[]
name|bytes
decl_stmt|;
specifier|private
name|String
name|name
decl_stmt|;
specifier|private
specifier|static
name|HashMap
name|unknown_attributes
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
comment|/** @return array of unknown attributes, but just one for each kind.    */
specifier|static
name|Unknown
index|[]
name|getUnknownAttributes
parameter_list|()
block|{
name|Unknown
index|[]
name|unknowns
init|=
operator|new
name|Unknown
index|[
name|unknown_attributes
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|Iterator
name|entries
init|=
name|unknown_attributes
operator|.
name|values
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|entries
operator|.
name|hasNext
argument_list|()
condition|;
name|i
operator|++
control|)
name|unknowns
index|[
name|i
index|]
operator|=
operator|(
name|Unknown
operator|)
name|entries
operator|.
name|next
argument_list|()
expr_stmt|;
name|unknown_attributes
operator|.
name|clear
argument_list|()
expr_stmt|;
return|return
name|unknowns
return|;
block|}
comment|/**    * Initialize from another object. Note that both objects use the same    * references (shallow copy). Use clone() for a physical copy.    */
specifier|public
name|Unknown
parameter_list|(
name|Unknown
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
name|getBytes
argument_list|()
argument_list|,
name|c
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**    * Create a non-standard attribute.    *    * @param name_index Index in constant pool    * @param length Content length in bytes    * @param bytes Attribute contents    * @param constant_pool Array of constants    */
specifier|public
name|Unknown
parameter_list|(
name|int
name|name_index
parameter_list|,
name|int
name|length
parameter_list|,
name|byte
index|[]
name|bytes
parameter_list|,
name|ConstantPool
name|constant_pool
parameter_list|)
block|{
name|super
argument_list|(
name|Constants
operator|.
name|ATTR_UNKNOWN
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
name|bytes
operator|=
name|bytes
expr_stmt|;
name|name
operator|=
operator|(
operator|(
name|ConstantUtf8
operator|)
name|constant_pool
operator|.
name|getConstant
argument_list|(
name|name_index
argument_list|,
name|Constants
operator|.
name|CONSTANT_Utf8
argument_list|)
operator|)
operator|.
name|getBytes
argument_list|()
expr_stmt|;
name|unknown_attributes
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**    * Construct object from file stream.    * @param name_index Index in constant pool    * @param length Content length in bytes    * @param file Input stream    * @param constant_pool Array of constants    * @throw IOException    */
name|Unknown
parameter_list|(
name|int
name|name_index
parameter_list|,
name|int
name|length
parameter_list|,
name|DataInputStream
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
name|byte
index|[]
operator|)
literal|null
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
if|if
condition|(
name|length
operator|>
literal|0
condition|)
block|{
name|bytes
operator|=
operator|new
name|byte
index|[
name|length
index|]
expr_stmt|;
name|file
operator|.
name|readFully
argument_list|(
name|bytes
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**    * Called by objects that are traversing the nodes of the tree implicitely    * defined by the contents of a Java class. I.e., the hierarchy of methods,    * fields, attributes, etc. spawns a tree of objects.    *    * @param v Visitor object    */
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
name|visitUnknown
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**    * Dump unknown bytes to file stream.    *    * @param file Output file stream    * @throw IOException    */
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
if|if
condition|(
name|length
operator|>
literal|0
condition|)
name|file
operator|.
name|write
argument_list|(
name|bytes
argument_list|,
literal|0
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
comment|/**    * @return data bytes.    */
specifier|public
specifier|final
name|byte
index|[]
name|getBytes
parameter_list|()
block|{
return|return
name|bytes
return|;
block|}
comment|/**    * @return name of attribute.    */
specifier|public
specifier|final
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
comment|/**    * @param bytes.    */
specifier|public
specifier|final
name|void
name|setBytes
parameter_list|(
name|byte
index|[]
name|bytes
parameter_list|)
block|{
name|this
operator|.
name|bytes
operator|=
name|bytes
expr_stmt|;
block|}
comment|/**    * @return String representation.    */
specifier|public
specifier|final
name|String
name|toString
parameter_list|()
block|{
if|if
condition|(
name|length
operator|==
literal|0
operator|||
name|bytes
operator|==
literal|null
condition|)
return|return
literal|"(Unknown attribute "
operator|+
name|name
operator|+
literal|")"
return|;
name|String
name|hex
decl_stmt|;
if|if
condition|(
name|length
operator|>
literal|10
condition|)
block|{
name|byte
index|[]
name|tmp
init|=
operator|new
name|byte
index|[
literal|10
index|]
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|bytes
argument_list|,
literal|0
argument_list|,
name|tmp
argument_list|,
literal|0
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|hex
operator|=
name|Utility
operator|.
name|toHexString
argument_list|(
name|tmp
argument_list|)
operator|+
literal|"... (truncated)"
expr_stmt|;
block|}
else|else
name|hex
operator|=
name|Utility
operator|.
name|toHexString
argument_list|(
name|bytes
argument_list|)
expr_stmt|;
return|return
literal|"(Unknown attribute "
operator|+
name|name
operator|+
literal|": "
operator|+
name|hex
operator|+
literal|")"
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
name|Unknown
name|c
init|=
operator|(
name|Unknown
operator|)
name|clone
argument_list|()
decl_stmt|;
if|if
condition|(
name|bytes
operator|!=
literal|null
condition|)
name|c
operator|.
name|bytes
operator|=
operator|(
name|byte
index|[]
operator|)
name|bytes
operator|.
name|clone
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
block|}
end_class

end_unit

