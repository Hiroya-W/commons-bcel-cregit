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

begin_comment
comment|/**  * This class is derived from<em>Attribute</em> and represents a constant   * value, i.e., a default value for initializing a class field.  * This class is instantiated by the<em>Attribute.readAttribute()</em> method.  *  * @version $Id$  * @author<A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>  * @see     Attribute  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ConstantValue
extends|extends
name|Attribute
block|{
specifier|private
name|int
name|constantvalue_index
decl_stmt|;
comment|/**    * Initialize from another object. Note that both objects use the same    * references (shallow copy). Use clone() for a physical copy.    */
specifier|public
name|ConstantValue
parameter_list|(
name|ConstantValue
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
name|getConstantValueIndex
argument_list|()
argument_list|,
name|c
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**    * Construct object from file stream.    * @param name_index Name index in constant pool    * @param length Content length in bytes    * @param file Input stream    * @param constant_pool Array of constants    * @throw IOException    */
name|ConstantValue
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
name|int
operator|)
name|file
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
block|}
comment|/**    * @param name_index Name index in constant pool    * @param length Content length in bytes    * @param constantvalue_index Index in constant pool    * @param constant_pool Array of constants    */
specifier|public
name|ConstantValue
parameter_list|(
name|int
name|name_index
parameter_list|,
name|int
name|length
parameter_list|,
name|int
name|constantvalue_index
parameter_list|,
name|ConstantPool
name|constant_pool
parameter_list|)
block|{
name|super
argument_list|(
name|Constants
operator|.
name|ATTR_CONSTANT_VALUE
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
name|constantvalue_index
operator|=
name|constantvalue_index
expr_stmt|;
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
name|visitConstantValue
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**    * Dump constant value attribute to file stream on binary format.    *    * @param file Output file stream    * @throw IOException    */
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
name|constantvalue_index
argument_list|)
expr_stmt|;
block|}
comment|/**    * @return Index in constant pool of constant value.    */
specifier|public
specifier|final
name|int
name|getConstantValueIndex
parameter_list|()
block|{
return|return
name|constantvalue_index
return|;
block|}
comment|/**    * @param constantvalue_index.    */
specifier|public
specifier|final
name|void
name|setConstantValueIndex
parameter_list|(
name|int
name|constantvalue_index
parameter_list|)
block|{
name|this
operator|.
name|constantvalue_index
operator|=
name|constantvalue_index
expr_stmt|;
block|}
comment|/**    * @return String representation of constant value.    */
specifier|public
specifier|final
name|String
name|toString
parameter_list|()
throws|throws
name|InternalError
block|{
name|Constant
name|c
init|=
name|constant_pool
operator|.
name|getConstant
argument_list|(
name|constantvalue_index
argument_list|)
decl_stmt|;
name|String
name|buf
decl_stmt|;
name|int
name|i
decl_stmt|;
comment|// Print constant to string depending on its type
switch|switch
condition|(
name|c
operator|.
name|getTag
argument_list|()
condition|)
block|{
case|case
name|Constants
operator|.
name|CONSTANT_Long
case|:
name|buf
operator|=
literal|""
operator|+
operator|(
operator|(
name|ConstantLong
operator|)
name|c
operator|)
operator|.
name|getBytes
argument_list|()
expr_stmt|;
break|break;
case|case
name|Constants
operator|.
name|CONSTANT_Float
case|:
name|buf
operator|=
literal|""
operator|+
operator|(
operator|(
name|ConstantFloat
operator|)
name|c
operator|)
operator|.
name|getBytes
argument_list|()
expr_stmt|;
break|break;
case|case
name|Constants
operator|.
name|CONSTANT_Double
case|:
name|buf
operator|=
literal|""
operator|+
operator|(
operator|(
name|ConstantDouble
operator|)
name|c
operator|)
operator|.
name|getBytes
argument_list|()
expr_stmt|;
break|break;
case|case
name|Constants
operator|.
name|CONSTANT_Integer
case|:
name|buf
operator|=
literal|""
operator|+
operator|(
operator|(
name|ConstantInteger
operator|)
name|c
operator|)
operator|.
name|getBytes
argument_list|()
expr_stmt|;
break|break;
case|case
name|Constants
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
name|constant_pool
operator|.
name|getConstant
argument_list|(
name|i
argument_list|,
name|Constants
operator|.
name|CONSTANT_Utf8
argument_list|)
expr_stmt|;
name|buf
operator|=
literal|"\""
operator|+
name|Utility
operator|.
name|convertString
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
default|default:
throw|throw
operator|new
name|InternalError
argument_list|(
literal|"Type of ConstValue invalid: "
operator|+
name|c
argument_list|)
throw|;
block|}
return|return
name|buf
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
name|ConstantValue
name|c
init|=
operator|(
name|ConstantValue
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
return|return
name|c
return|;
block|}
block|}
end_class

end_unit

