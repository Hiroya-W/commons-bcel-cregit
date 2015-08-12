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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
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
name|commons
operator|.
name|bcel6
operator|.
name|ExceptionConstants
import|;
end_import

begin_comment
comment|/**   * INVOKESPECIAL - Invoke instance method; special handling for superclass, private  * and instance initialization method invocations  *  *<PRE>Stack: ..., objectref, [arg1, [arg2 ...]] -&gt; ...</PRE>  *  * @version $Id$  */
end_comment

begin_class
specifier|public
class|class
name|INVOKESPECIAL
extends|extends
name|InvokeInstruction
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|6615779239416262849L
decl_stmt|;
comment|/**      * Empty constructor needed for the Class.newInstance() statement in      * Instruction.readInstruction(). Not to be used otherwise.      */
name|INVOKESPECIAL
parameter_list|()
block|{
block|}
specifier|public
name|INVOKESPECIAL
parameter_list|(
name|int
name|index
parameter_list|)
block|{
name|super
argument_list|(
name|Constants
operator|.
name|INVOKESPECIAL
argument_list|,
name|index
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|getExceptions
parameter_list|()
block|{
return|return
name|ExceptionConstants
operator|.
name|createExceptions
argument_list|(
name|ExceptionConstants
operator|.
name|EXCS
operator|.
name|EXCS_FIELD_AND_METHOD_RESOLUTION
argument_list|,
name|ExceptionConstants
operator|.
name|NULL_POINTER_EXCEPTION
argument_list|,
name|ExceptionConstants
operator|.
name|INCOMPATIBLE_CLASS_CHANGE_ERROR
argument_list|,
name|ExceptionConstants
operator|.
name|ABSTRACT_METHOD_ERROR
argument_list|,
name|ExceptionConstants
operator|.
name|UNSATISFIED_LINK_ERROR
argument_list|)
return|;
block|}
comment|/**      * Call corresponding visitor method(s). The order is:      * Call visitor methods of implemented interfaces first, then      * call methods according to the class hierarchy in descending order,      * i.e., the most specific visitXXX() call comes last.      *      * @param v Visitor object      */
annotation|@
name|Override
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
name|visitExceptionThrower
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
name|visitStackConsumer
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
name|visitLoadClass
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitCPInstruction
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitFieldOrMethod
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitInvokeInstruction
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitINVOKESPECIAL
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

