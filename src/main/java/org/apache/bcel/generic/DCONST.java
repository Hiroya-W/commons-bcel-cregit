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
name|generic
package|;
end_package

begin_comment
comment|/**  * DCONST - Push 0.0 or 1.0, other values cause an exception  *  *<PRE>  * Stack: ... -&gt; ...,  *</PRE>  */
end_comment

begin_class
specifier|public
class|class
name|DCONST
extends|extends
name|Instruction
implements|implements
name|ConstantPushInstruction
block|{
specifier|private
specifier|final
name|double
name|value
decl_stmt|;
comment|/**      * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.      */
name|DCONST
parameter_list|()
block|{
name|this
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
specifier|public
name|DCONST
parameter_list|(
specifier|final
name|double
name|f
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
name|Const
operator|.
name|DCONST_0
argument_list|,
operator|(
name|short
operator|)
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|f
operator|==
literal|0.0
condition|)
block|{
name|super
operator|.
name|setOpcode
argument_list|(
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|DCONST_0
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|f
operator|==
literal|1.0
condition|)
block|{
name|super
operator|.
name|setOpcode
argument_list|(
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|DCONST_1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"DCONST can be used only for 0.0 and 1.0: "
operator|+
name|f
argument_list|)
throw|;
block|}
name|value
operator|=
name|f
expr_stmt|;
block|}
comment|/**      * Call corresponding visitor method(s). The order is: Call visitor methods of implemented interfaces first, then call      * methods according to the class hierarchy in descending order, i.e., the most specific visitXXX() call comes last.      *      * @param v Visitor object      */
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
name|visitDCONST
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Type.DOUBLE      */
annotation|@
name|Override
specifier|public
name|Type
name|getType
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
return|return
name|Type
operator|.
name|DOUBLE
return|;
block|}
annotation|@
name|Override
specifier|public
name|Number
name|getValue
parameter_list|()
block|{
return|return
name|Double
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
return|;
block|}
block|}
end_class

end_unit

