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

begin_comment
comment|/**   * LCONST - Push 0 or 1, other values cause an exception  *  *<PRE>Stack: ... -&gt; ...,<i></PRE>  *  * @version $Id$  * @author<A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
class|class
name|LCONST
extends|extends
name|Instruction
implements|implements
name|ConstantPushInstruction
implements|,
name|TypedInstruction
block|{
specifier|private
name|long
name|value
decl_stmt|;
comment|/**    * Empty constructor needed for the Class.newInstance() statement in    * Instruction.readInstruction(). Not to be used otherwise.    */
name|LCONST
parameter_list|()
block|{
block|}
specifier|public
name|LCONST
parameter_list|(
name|long
name|l
parameter_list|)
block|{
name|super
argument_list|(
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Constants
operator|.
name|LCONST_0
argument_list|,
operator|(
name|short
operator|)
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|l
operator|==
literal|0
condition|)
name|opcode
operator|=
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Constants
operator|.
name|LCONST_0
expr_stmt|;
if|else if
condition|(
name|l
operator|==
literal|1
condition|)
name|opcode
operator|=
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Constants
operator|.
name|LCONST_1
expr_stmt|;
else|else
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"LCONST can be used only for 0 and 1: "
operator|+
name|l
argument_list|)
throw|;
name|value
operator|=
name|l
expr_stmt|;
block|}
specifier|public
name|Number
name|getValue
parameter_list|()
block|{
return|return
operator|new
name|Long
argument_list|(
name|value
argument_list|)
return|;
block|}
comment|/** @return Type.LONG    */
specifier|public
name|Type
name|getType
parameter_list|(
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
return|return
name|Type
operator|.
name|LONG
return|;
block|}
comment|/**    * Call corresponding visitor method(s). The order is:    * Call visitor methods of implemented interfaces first, then    * call methods according to the class hierarchy in descending order,    * i.e., the most specific visitXXX() call comes last.    *    * @param v Visitor object    */
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
name|visitPushInstruction
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitStackProducer
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitTypedInstruction
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitConstantPushInstruction
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitLCONST
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

