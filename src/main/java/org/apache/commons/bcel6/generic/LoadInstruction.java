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

begin_comment
comment|/**  * Denotes an unparameterized instruction to load a value from a local  * variable, e.g. ILOAD.  *  * @version $Id$  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|LoadInstruction
extends|extends
name|LocalVariableInstruction
implements|implements
name|PushInstruction
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3661924741022212247L
decl_stmt|;
comment|/**      * Empty constructor needed for the Class.newInstance() statement in      * Instruction.readInstruction(). Not to be used otherwise.      * tag and length are defined in readInstruction and initFromFile, respectively.      */
name|LoadInstruction
parameter_list|(
name|short
name|canon_tag
parameter_list|,
name|short
name|c_tag
parameter_list|)
block|{
name|super
argument_list|(
name|canon_tag
argument_list|,
name|c_tag
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param opcode Instruction opcode      * @param c_tag Instruction number for compact version, ALOAD_0, e.g.      * @param n local variable index (unsigned short)      */
specifier|protected
name|LoadInstruction
parameter_list|(
name|short
name|opcode
parameter_list|,
name|short
name|c_tag
parameter_list|,
name|int
name|n
parameter_list|)
block|{
name|super
argument_list|(
name|opcode
argument_list|,
name|c_tag
argument_list|,
name|n
argument_list|)
expr_stmt|;
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
name|visitStackProducer
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitPushInstruction
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
name|visitLocalVariableInstruction
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitLoadInstruction
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

