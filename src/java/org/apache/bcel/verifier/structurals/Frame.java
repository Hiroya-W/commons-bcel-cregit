begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|verifier
operator|.
name|structurals
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
name|generic
operator|.
name|*
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
name|verifier
operator|.
name|exc
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
comment|/**  * This class represents a JVM execution frame; that means,  * a local variable array and an operand stack.  *  * @version $Id$  * @author<A HREF="http://www.inf.fu-berlin.de/~ehaase"/>Enver Haase</A>  */
end_comment

begin_class
specifier|public
class|class
name|Frame
block|{
comment|/** 	 * For instance initialization methods, it is important to remember 	 * which instance it is that is not initialized yet. It will be 	 * initialized invoking another constructor later. 	 * NULL means the instance already *is* initialized. 	 */
specifier|protected
specifier|static
name|UninitializedObjectType
name|_this
decl_stmt|;
comment|/** 	 * 	 */
specifier|private
name|LocalVariables
name|locals
decl_stmt|;
comment|/** 	 * 	 */
specifier|private
name|OperandStack
name|stack
decl_stmt|;
comment|/** 	 * 	 */
specifier|public
name|Frame
parameter_list|(
name|int
name|maxLocals
parameter_list|,
name|int
name|maxStack
parameter_list|)
block|{
name|locals
operator|=
operator|new
name|LocalVariables
argument_list|(
name|maxLocals
argument_list|)
expr_stmt|;
name|stack
operator|=
operator|new
name|OperandStack
argument_list|(
name|maxStack
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * 	 */
specifier|public
name|Frame
parameter_list|(
name|LocalVariables
name|locals
parameter_list|,
name|OperandStack
name|stack
parameter_list|)
block|{
name|this
operator|.
name|locals
operator|=
name|locals
expr_stmt|;
name|this
operator|.
name|stack
operator|=
name|stack
expr_stmt|;
block|}
comment|/** 	 * 	 */
specifier|protected
name|Object
name|clone
parameter_list|()
block|{
name|Frame
name|f
init|=
operator|new
name|Frame
argument_list|(
name|locals
operator|.
name|getClone
argument_list|()
argument_list|,
name|stack
operator|.
name|getClone
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|f
return|;
block|}
comment|/** 	 * 	 */
specifier|public
name|Frame
name|getClone
parameter_list|()
block|{
return|return
operator|(
name|Frame
operator|)
name|clone
argument_list|()
return|;
block|}
comment|/** 	 * 	 */
specifier|public
name|LocalVariables
name|getLocals
parameter_list|()
block|{
return|return
name|locals
return|;
block|}
comment|/** 	 * 	 */
specifier|public
name|OperandStack
name|getStack
parameter_list|()
block|{
return|return
name|stack
return|;
block|}
comment|/** 	 * 	 */
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|o
operator|instanceof
name|Frame
operator|)
condition|)
return|return
literal|false
return|;
comment|// implies "null" is non-equal.
name|Frame
name|f
init|=
operator|(
name|Frame
operator|)
name|o
decl_stmt|;
return|return
name|this
operator|.
name|stack
operator|.
name|equals
argument_list|(
name|f
operator|.
name|stack
argument_list|)
operator|&&
name|this
operator|.
name|locals
operator|.
name|equals
argument_list|(
name|f
operator|.
name|locals
argument_list|)
return|;
block|}
comment|/** 	 * Returns a String representation of the Frame instance. 	 */
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|String
name|s
init|=
literal|"Local Variables:\n"
decl_stmt|;
name|s
operator|+=
name|locals
expr_stmt|;
name|s
operator|+=
literal|"OperandStack:\n"
expr_stmt|;
name|s
operator|+=
name|stack
expr_stmt|;
return|return
name|s
return|;
block|}
block|}
end_class

end_unit

