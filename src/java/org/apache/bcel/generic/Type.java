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
name|*
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

begin_comment
comment|/**   * Abstract super class for all possible java types, namely basic types  * such as int, object types like String and array types, e.g. int[]  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|Type
implements|implements
name|java
operator|.
name|io
operator|.
name|Serializable
block|{
specifier|protected
name|byte
name|type
decl_stmt|;
specifier|protected
name|String
name|signature
decl_stmt|;
comment|// signature for the type
comment|/** Predefined constants    */
specifier|public
specifier|static
specifier|final
name|BasicType
name|VOID
init|=
operator|new
name|BasicType
argument_list|(
name|Constants
operator|.
name|T_VOID
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|BasicType
name|BOOLEAN
init|=
operator|new
name|BasicType
argument_list|(
name|Constants
operator|.
name|T_BOOLEAN
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|BasicType
name|INT
init|=
operator|new
name|BasicType
argument_list|(
name|Constants
operator|.
name|T_INT
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|BasicType
name|SHORT
init|=
operator|new
name|BasicType
argument_list|(
name|Constants
operator|.
name|T_SHORT
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|BasicType
name|BYTE
init|=
operator|new
name|BasicType
argument_list|(
name|Constants
operator|.
name|T_BYTE
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|BasicType
name|LONG
init|=
operator|new
name|BasicType
argument_list|(
name|Constants
operator|.
name|T_LONG
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|BasicType
name|DOUBLE
init|=
operator|new
name|BasicType
argument_list|(
name|Constants
operator|.
name|T_DOUBLE
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|BasicType
name|FLOAT
init|=
operator|new
name|BasicType
argument_list|(
name|Constants
operator|.
name|T_FLOAT
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|BasicType
name|CHAR
init|=
operator|new
name|BasicType
argument_list|(
name|Constants
operator|.
name|T_CHAR
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|ObjectType
name|OBJECT
init|=
operator|new
name|ObjectType
argument_list|(
literal|"java.lang.Object"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|ObjectType
name|CLASS
init|=
operator|new
name|ObjectType
argument_list|(
literal|"java.lang.Class"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|ObjectType
name|STRING
init|=
operator|new
name|ObjectType
argument_list|(
literal|"java.lang.String"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|ObjectType
name|STRINGBUFFER
init|=
operator|new
name|ObjectType
argument_list|(
literal|"java.lang.StringBuffer"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|ObjectType
name|THROWABLE
init|=
operator|new
name|ObjectType
argument_list|(
literal|"java.lang.Throwable"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Type
index|[]
name|NO_ARGS
init|=
operator|new
name|Type
index|[
literal|0
index|]
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|ReferenceType
name|NULL
init|=
operator|new
name|ReferenceType
argument_list|()
block|{}
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Type
name|UNKNOWN
init|=
operator|new
name|Type
argument_list|(
name|Constants
operator|.
name|T_UNKNOWN
argument_list|,
literal|"<unknown object>"
argument_list|)
block|{}
decl_stmt|;
specifier|protected
name|Type
parameter_list|(
name|byte
name|t
parameter_list|,
name|String
name|s
parameter_list|)
block|{
name|type
operator|=
name|t
expr_stmt|;
name|signature
operator|=
name|s
expr_stmt|;
block|}
comment|/**    * @return signature for given type.    */
specifier|public
name|String
name|getSignature
parameter_list|()
block|{
return|return
name|signature
return|;
block|}
comment|/**    * @return type as defined in Constants    */
specifier|public
name|byte
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
comment|/**    * @return stack size of this type (2 for long and double, 0 for void, 1 otherwise)    */
specifier|public
name|int
name|getSize
parameter_list|()
block|{
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|Constants
operator|.
name|T_DOUBLE
case|:
case|case
name|Constants
operator|.
name|T_LONG
case|:
return|return
literal|2
return|;
case|case
name|Constants
operator|.
name|T_VOID
case|:
return|return
literal|0
return|;
default|default:
return|return
literal|1
return|;
block|}
block|}
comment|/**    * @return Type string, e.g. `int[]'    */
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|(
operator|(
name|this
operator|.
name|equals
argument_list|(
name|Type
operator|.
name|NULL
argument_list|)
operator|||
operator|(
name|type
operator|>=
name|Constants
operator|.
name|T_UNKNOWN
operator|)
operator|)
operator|)
condition|?
name|signature
else|:
name|Utility
operator|.
name|signatureToString
argument_list|(
name|signature
argument_list|,
literal|false
argument_list|)
return|;
block|}
comment|/**    * Convert type to Java method signature, e.g. int[] f(java.lang.String x)    * becomes (Ljava/lang/String;)[I    *    * @param return_type what the method returns    * @param arg_types what are the argument types    * @return method signature for given type(s).    */
specifier|public
specifier|static
name|String
name|getMethodSignature
parameter_list|(
name|Type
name|return_type
parameter_list|,
name|Type
index|[]
name|arg_types
parameter_list|)
block|{
name|StringBuffer
name|buf
init|=
operator|new
name|StringBuffer
argument_list|(
literal|"("
argument_list|)
decl_stmt|;
name|int
name|length
init|=
operator|(
name|arg_types
operator|==
literal|null
operator|)
condition|?
literal|0
else|:
name|arg_types
operator|.
name|length
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
name|length
condition|;
name|i
operator|++
control|)
name|buf
operator|.
name|append
argument_list|(
name|arg_types
index|[
name|i
index|]
operator|.
name|getSignature
argument_list|()
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|')'
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|return_type
operator|.
name|getSignature
argument_list|()
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
specifier|static
name|ThreadLocal
name|consumed_chars
init|=
operator|new
name|ThreadLocal
argument_list|()
block|{
specifier|protected
name|Object
name|initialValue
parameter_list|()
block|{
return|return
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
return|;
block|}
block|}
decl_stmt|;
comment|//int consumed_chars=0; // Remember position in string, see getArgumentTypes
specifier|private
specifier|static
name|int
name|unwrap
parameter_list|(
name|ThreadLocal
name|tl
parameter_list|)
block|{
return|return
operator|(
operator|(
name|Integer
operator|)
name|tl
operator|.
name|get
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
return|;
block|}
specifier|private
specifier|static
name|void
name|wrap
parameter_list|(
name|ThreadLocal
name|tl
parameter_list|,
name|int
name|value
parameter_list|)
block|{
name|tl
operator|.
name|set
argument_list|(
operator|new
name|Integer
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**    * Convert signature to a Type object.    * @param signature signature string such as Ljava/lang/String;    * @return type object    */
specifier|public
specifier|static
specifier|final
name|Type
name|getType
parameter_list|(
name|String
name|signature
parameter_list|)
throws|throws
name|StringIndexOutOfBoundsException
block|{
name|byte
name|type
init|=
name|Utility
operator|.
name|typeOfSignature
argument_list|(
name|signature
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|<=
name|Constants
operator|.
name|T_VOID
condition|)
block|{
comment|//corrected concurrent private static field acess
name|wrap
argument_list|(
name|consumed_chars
argument_list|,
literal|1
argument_list|)
expr_stmt|;
return|return
name|BasicType
operator|.
name|getType
argument_list|(
name|type
argument_list|)
return|;
block|}
if|else if
condition|(
name|type
operator|==
name|Constants
operator|.
name|T_ARRAY
condition|)
block|{
name|int
name|dim
init|=
literal|0
decl_stmt|;
do|do
block|{
comment|// Count dimensions
name|dim
operator|++
expr_stmt|;
block|}
do|while
condition|(
name|signature
operator|.
name|charAt
argument_list|(
name|dim
argument_list|)
operator|==
literal|'['
condition|)
do|;
comment|// Recurse, but just once, if the signature is ok
name|Type
name|t
init|=
name|getType
argument_list|(
name|signature
operator|.
name|substring
argument_list|(
name|dim
argument_list|)
argument_list|)
decl_stmt|;
comment|//corrected concurrent private static field acess
comment|//  consumed_chars += dim; // update counter - is replaced by
name|int
name|_temp
init|=
name|unwrap
argument_list|(
name|consumed_chars
argument_list|)
operator|+
name|dim
decl_stmt|;
name|wrap
argument_list|(
name|consumed_chars
argument_list|,
name|_temp
argument_list|)
expr_stmt|;
return|return
operator|new
name|ArrayType
argument_list|(
name|t
argument_list|,
name|dim
argument_list|)
return|;
block|}
else|else
block|{
comment|// type == T_REFERENCE
name|int
name|index
init|=
name|signature
operator|.
name|indexOf
argument_list|(
literal|';'
argument_list|)
decl_stmt|;
comment|// Look for closing `;'
if|if
condition|(
name|index
operator|<
literal|0
condition|)
throw|throw
operator|new
name|ClassFormatException
argument_list|(
literal|"Invalid signature: "
operator|+
name|signature
argument_list|)
throw|;
comment|//corrected concurrent private static field acess
name|wrap
argument_list|(
name|consumed_chars
argument_list|,
name|index
operator|+
literal|1
argument_list|)
expr_stmt|;
comment|// "Lblabla;" `L' and `;' are removed
return|return
operator|new
name|ObjectType
argument_list|(
name|signature
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|index
argument_list|)
operator|.
name|replace
argument_list|(
literal|'/'
argument_list|,
literal|'.'
argument_list|)
argument_list|)
return|;
block|}
block|}
comment|/**    * Convert return value of a method (signature) to a Type object.    *    * @param signature signature string such as (Ljava/lang/String;)V    * @return return type    */
specifier|public
specifier|static
name|Type
name|getReturnType
parameter_list|(
name|String
name|signature
parameter_list|)
block|{
try|try
block|{
comment|// Read return type after `)'
name|int
name|index
init|=
name|signature
operator|.
name|lastIndexOf
argument_list|(
literal|')'
argument_list|)
operator|+
literal|1
decl_stmt|;
return|return
name|getType
argument_list|(
name|signature
operator|.
name|substring
argument_list|(
name|index
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|StringIndexOutOfBoundsException
name|e
parameter_list|)
block|{
comment|// Should never occur
throw|throw
operator|new
name|ClassFormatException
argument_list|(
literal|"Invalid method signature: "
operator|+
name|signature
argument_list|)
throw|;
block|}
block|}
comment|/**    * Convert arguments of a method (signature) to an array of Type objects.    * @param signature signature string such as (Ljava/lang/String;)V    * @return array of argument types    */
specifier|public
specifier|static
name|Type
index|[]
name|getArgumentTypes
parameter_list|(
name|String
name|signature
parameter_list|)
block|{
name|ArrayList
name|vec
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|int
name|index
decl_stmt|;
name|Type
index|[]
name|types
decl_stmt|;
try|try
block|{
comment|// Read all declarations between for `(' and `)'
if|if
condition|(
name|signature
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|!=
literal|'('
condition|)
throw|throw
operator|new
name|ClassFormatException
argument_list|(
literal|"Invalid method signature: "
operator|+
name|signature
argument_list|)
throw|;
name|index
operator|=
literal|1
expr_stmt|;
comment|// current string position
while|while
condition|(
name|signature
operator|.
name|charAt
argument_list|(
name|index
argument_list|)
operator|!=
literal|')'
condition|)
block|{
name|vec
operator|.
name|add
argument_list|(
name|getType
argument_list|(
name|signature
operator|.
name|substring
argument_list|(
name|index
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//corrected concurrent private static field acess
name|index
operator|+=
name|unwrap
argument_list|(
name|consumed_chars
argument_list|)
expr_stmt|;
comment|// update position
block|}
block|}
catch|catch
parameter_list|(
name|StringIndexOutOfBoundsException
name|e
parameter_list|)
block|{
comment|// Should never occur
throw|throw
operator|new
name|ClassFormatException
argument_list|(
literal|"Invalid method signature: "
operator|+
name|signature
argument_list|)
throw|;
block|}
name|types
operator|=
operator|new
name|Type
index|[
name|vec
operator|.
name|size
argument_list|()
index|]
expr_stmt|;
name|vec
operator|.
name|toArray
argument_list|(
name|types
argument_list|)
expr_stmt|;
return|return
name|types
return|;
block|}
comment|/** Convert runtime java.lang.Class to BCEL Type object.    * @param cl Java class    * @return corresponding Type object    */
specifier|public
specifier|static
name|Type
name|getType
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Class
name|cl
parameter_list|)
block|{
if|if
condition|(
name|cl
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Class must not be null"
argument_list|)
throw|;
block|}
comment|/* That's an amzingly easy case, because getName() returns      * the signature. That's what we would have liked anyway.      */
if|if
condition|(
name|cl
operator|.
name|isArray
argument_list|()
condition|)
block|{
return|return
name|getType
argument_list|(
name|cl
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
if|else if
condition|(
name|cl
operator|.
name|isPrimitive
argument_list|()
condition|)
block|{
if|if
condition|(
name|cl
operator|==
name|Integer
operator|.
name|TYPE
condition|)
block|{
return|return
name|INT
return|;
block|}
if|else if
condition|(
name|cl
operator|==
name|Void
operator|.
name|TYPE
condition|)
block|{
return|return
name|VOID
return|;
block|}
if|else if
condition|(
name|cl
operator|==
name|Double
operator|.
name|TYPE
condition|)
block|{
return|return
name|DOUBLE
return|;
block|}
if|else if
condition|(
name|cl
operator|==
name|Float
operator|.
name|TYPE
condition|)
block|{
return|return
name|FLOAT
return|;
block|}
if|else if
condition|(
name|cl
operator|==
name|Boolean
operator|.
name|TYPE
condition|)
block|{
return|return
name|BOOLEAN
return|;
block|}
if|else if
condition|(
name|cl
operator|==
name|Byte
operator|.
name|TYPE
condition|)
block|{
return|return
name|BYTE
return|;
block|}
if|else if
condition|(
name|cl
operator|==
name|Short
operator|.
name|TYPE
condition|)
block|{
return|return
name|SHORT
return|;
block|}
if|else if
condition|(
name|cl
operator|==
name|Byte
operator|.
name|TYPE
condition|)
block|{
return|return
name|BYTE
return|;
block|}
if|else if
condition|(
name|cl
operator|==
name|Long
operator|.
name|TYPE
condition|)
block|{
return|return
name|LONG
return|;
block|}
if|else if
condition|(
name|cl
operator|==
name|Character
operator|.
name|TYPE
condition|)
block|{
return|return
name|CHAR
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Ooops, what primitive type is "
operator|+
name|cl
argument_list|)
throw|;
block|}
block|}
else|else
block|{
comment|// "Real" class
return|return
operator|new
name|ObjectType
argument_list|(
name|cl
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
block|}
comment|/**    * Convert runtime java.lang.Class[] to BCEL Type objects.    * @param classes an array of runtime class objects    * @return array of corresponding Type objects    */
specifier|public
specifier|static
name|Type
index|[]
name|getTypes
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Class
index|[]
name|classes
parameter_list|)
block|{
name|Type
index|[]
name|ret
init|=
operator|new
name|Type
index|[
name|classes
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
name|ret
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|ret
index|[
name|i
index|]
operator|=
name|getType
argument_list|(
name|classes
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|String
name|getSignature
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Method
name|meth
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|(
literal|"("
argument_list|)
decl_stmt|;
name|Class
index|[]
name|params
init|=
name|meth
operator|.
name|getParameterTypes
argument_list|()
decl_stmt|;
comment|// avoid clone
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|params
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|getType
argument_list|(
name|params
index|[
name|j
index|]
argument_list|)
operator|.
name|getSignature
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|")"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|getType
argument_list|(
name|meth
operator|.
name|getReturnType
argument_list|()
argument_list|)
operator|.
name|getSignature
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

