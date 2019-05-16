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
name|generic
package|;
end_package

begin_comment
comment|/**  * BranchHandle is returned by specialized InstructionList.append() whenever a  * BranchInstruction is appended. This is useful when the target of this  * instruction is not known at time of creation and must be set later  * via setTarget().  *  * @see InstructionHandle  * @see Instruction  * @see InstructionList  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|BranchHandle
extends|extends
name|InstructionHandle
block|{
comment|// This is also a cache in case the InstructionHandle#swapInstruction() method is used
comment|// See BCEL-273
specifier|private
name|BranchInstruction
name|bi
decl_stmt|;
comment|// An alias in fact, but saves lots of casts
specifier|private
name|BranchHandle
parameter_list|(
specifier|final
name|BranchInstruction
name|i
parameter_list|)
block|{
name|super
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|bi
operator|=
name|i
expr_stmt|;
block|}
comment|/** Factory method.      */
specifier|static
name|BranchHandle
name|getBranchHandle
parameter_list|(
specifier|final
name|BranchInstruction
name|i
parameter_list|)
block|{
return|return
operator|new
name|BranchHandle
argument_list|(
name|i
argument_list|)
return|;
block|}
comment|/* Override InstructionHandle methods: delegate to branch instruction.      * Through this overriding all access to the private i_position field should      * be prevented.      */
annotation|@
name|Override
specifier|public
name|int
name|getPosition
parameter_list|()
block|{
return|return
name|bi
operator|.
name|getPosition
argument_list|()
return|;
block|}
annotation|@
name|Override
name|void
name|setPosition
parameter_list|(
specifier|final
name|int
name|pos
parameter_list|)
block|{
comment|// Original code: i_position = bi.position = pos;
name|bi
operator|.
name|setPosition
argument_list|(
name|pos
argument_list|)
expr_stmt|;
name|super
operator|.
name|setPosition
argument_list|(
name|pos
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|int
name|updatePosition
parameter_list|(
specifier|final
name|int
name|offset
parameter_list|,
specifier|final
name|int
name|max_offset
parameter_list|)
block|{
specifier|final
name|int
name|x
init|=
name|bi
operator|.
name|updatePosition
argument_list|(
name|offset
argument_list|,
name|max_offset
argument_list|)
decl_stmt|;
name|super
operator|.
name|setPosition
argument_list|(
name|bi
operator|.
name|getPosition
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|x
return|;
block|}
comment|/**      * Pass new target to instruction.      */
specifier|public
name|void
name|setTarget
parameter_list|(
specifier|final
name|InstructionHandle
name|ih
parameter_list|)
block|{
name|bi
operator|.
name|setTarget
argument_list|(
name|ih
argument_list|)
expr_stmt|;
block|}
comment|/**      * Update target of instruction.      */
specifier|public
name|void
name|updateTarget
parameter_list|(
specifier|final
name|InstructionHandle
name|old_ih
parameter_list|,
specifier|final
name|InstructionHandle
name|new_ih
parameter_list|)
block|{
name|bi
operator|.
name|updateTarget
argument_list|(
name|old_ih
argument_list|,
name|new_ih
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return target of instruction.      */
specifier|public
name|InstructionHandle
name|getTarget
parameter_list|()
block|{
return|return
name|bi
operator|.
name|getTarget
argument_list|()
return|;
block|}
comment|/**      * Set new contents. Old instruction is disposed and may not be used anymore.      */
annotation|@
name|Override
comment|// This is only done in order to apply the additional type check; could be merged with super impl.
specifier|public
name|void
name|setInstruction
parameter_list|(
specifier|final
name|Instruction
name|i
parameter_list|)
block|{
comment|// TODO could be package-protected?
name|super
operator|.
name|setInstruction
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
operator|(
name|i
operator|instanceof
name|BranchInstruction
operator|)
condition|)
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Assigning "
operator|+
name|i
operator|+
literal|" to branch handle which is not a branch instruction"
argument_list|)
throw|;
block|}
name|bi
operator|=
operator|(
name|BranchInstruction
operator|)
name|i
expr_stmt|;
block|}
block|}
end_class

end_unit

