begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|generic
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

begin_comment
comment|/**   * Denotes array type, such as int[][]  *  * @version $Id$  * @author<A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ArrayType
extends|extends
name|ReferenceType
block|{
specifier|private
name|int
name|dimensions
decl_stmt|;
specifier|private
name|Type
name|basic_type
decl_stmt|;
comment|/**    * Convenience constructor for array type, e.g. int[]    *    * @param type array type, e.g. T_INT    */
specifier|public
name|ArrayType
parameter_list|(
name|byte
name|type
parameter_list|,
name|int
name|dimensions
parameter_list|)
block|{
name|this
argument_list|(
name|BasicType
operator|.
name|getType
argument_list|(
name|type
argument_list|)
argument_list|,
name|dimensions
argument_list|)
expr_stmt|;
block|}
comment|/**    * Convenience constructor for reference array type, e.g. Object[]    *    * @param class_name complete name of class (java.lang.String, e.g.)    */
specifier|public
name|ArrayType
parameter_list|(
name|String
name|class_name
parameter_list|,
name|int
name|dimensions
parameter_list|)
block|{
name|this
argument_list|(
operator|new
name|ObjectType
argument_list|(
name|class_name
argument_list|)
argument_list|,
name|dimensions
argument_list|)
expr_stmt|;
block|}
comment|/**    * Constructor for array of given type    *    * @param type type of array (may be an array itself)    */
specifier|public
name|ArrayType
parameter_list|(
name|Type
name|type
parameter_list|,
name|int
name|dimensions
parameter_list|)
block|{
name|super
argument_list|(
name|Constants
operator|.
name|T_ARRAY
argument_list|,
literal|"<dummy>"
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|dimensions
operator|<
literal|1
operator|)
operator|||
operator|(
name|dimensions
operator|>
name|Constants
operator|.
name|MAX_BYTE
operator|)
condition|)
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Invalid number of dimensions: "
operator|+
name|dimensions
argument_list|)
throw|;
switch|switch
condition|(
name|type
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|Constants
operator|.
name|T_ARRAY
case|:
name|ArrayType
name|array
init|=
operator|(
name|ArrayType
operator|)
name|type
decl_stmt|;
name|this
operator|.
name|dimensions
operator|=
name|dimensions
operator|+
name|array
operator|.
name|dimensions
expr_stmt|;
name|basic_type
operator|=
name|array
operator|.
name|basic_type
expr_stmt|;
break|break;
case|case
name|Constants
operator|.
name|T_VOID
case|:
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Invalid type: void[]"
argument_list|)
throw|;
default|default:
comment|// Basic type or reference
name|this
operator|.
name|dimensions
operator|=
name|dimensions
expr_stmt|;
name|basic_type
operator|=
name|type
expr_stmt|;
break|break;
block|}
name|StringBuffer
name|buf
init|=
operator|new
name|StringBuffer
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
name|this
operator|.
name|dimensions
condition|;
name|i
operator|++
control|)
name|buf
operator|.
name|append
argument_list|(
literal|'['
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|basic_type
operator|.
name|getSignature
argument_list|()
argument_list|)
expr_stmt|;
name|signature
operator|=
name|buf
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
comment|/**    * @return basic type of array, i.e., for int[][][] the basic type is int    */
specifier|public
name|Type
name|getBasicType
parameter_list|()
block|{
return|return
name|basic_type
return|;
block|}
comment|/**    * @return element type of array, i.e., for int[][][] the element type is int[][]    */
specifier|public
name|Type
name|getElementType
parameter_list|()
block|{
if|if
condition|(
name|dimensions
operator|==
literal|1
condition|)
return|return
name|basic_type
return|;
else|else
return|return
operator|new
name|ArrayType
argument_list|(
name|basic_type
argument_list|,
name|dimensions
operator|-
literal|1
argument_list|)
return|;
block|}
comment|/** @return number of dimensions of array    */
specifier|public
name|int
name|getDimensions
parameter_list|()
block|{
return|return
name|dimensions
return|;
block|}
comment|/** @return a hash code value for the object.    */
specifier|public
name|int
name|hashcode
parameter_list|()
block|{
return|return
name|basic_type
operator|.
name|hashCode
argument_list|()
operator|^
name|dimensions
return|;
block|}
comment|/** @return true if both type objects refer to the same array type.    */
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|instanceof
name|ArrayType
condition|)
block|{
name|ArrayType
name|array
init|=
operator|(
name|ArrayType
operator|)
name|type
decl_stmt|;
return|return
operator|(
name|array
operator|.
name|dimensions
operator|==
name|dimensions
operator|)
operator|&&
name|array
operator|.
name|basic_type
operator|.
name|equals
argument_list|(
name|basic_type
argument_list|)
return|;
block|}
else|else
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

