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
comment|/**  * BranchHandle is returned by specialized InstructionList.append() whenever a  * BranchInstruction is appended. This is useful when the target of this  * instruction is not known at time of creation and must be set later  * via setTarget().  *  * @see InstructionHandle  * @see Instruction  * @see InstructionList  * @version $Id$  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|BranchHandle
extends|extends
name|InstructionHandle
block|{
specifier|private
name|BranchHandle
parameter_list|(
name|BranchInstruction
name|i
parameter_list|)
block|{
name|super
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
comment|/** Factory methods.      */
specifier|private
specifier|static
name|BranchHandle
name|bh_list
init|=
literal|null
decl_stmt|;
comment|// List of reusable handles
specifier|static
name|BranchHandle
name|getBranchHandle
parameter_list|(
name|BranchInstruction
name|i
parameter_list|)
block|{
if|if
condition|(
name|bh_list
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|BranchHandle
argument_list|(
name|i
argument_list|)
return|;
block|}
name|BranchHandle
name|bh
init|=
name|bh_list
decl_stmt|;
name|bh_list
operator|=
operator|(
name|BranchHandle
operator|)
name|bh
operator|.
name|getNext
argument_list|()
expr_stmt|;
name|bh
operator|.
name|setInstruction
argument_list|(
name|i
argument_list|)
expr_stmt|;
return|return
name|bh
return|;
block|}
comment|/** Handle adds itself to the list of resuable handles.      */
annotation|@
name|Override
specifier|protected
name|void
name|addHandle
parameter_list|()
block|{
name|super
operator|.
name|setNext
argument_list|(
name|bh_list
argument_list|)
expr_stmt|;
name|bh_list
operator|=
name|this
expr_stmt|;
block|}
comment|// get the instruction as a BranchInstruction
comment|// (do the cast once)
specifier|private
name|BranchInstruction
name|getBI
parameter_list|()
block|{
return|return
operator|(
name|BranchInstruction
operator|)
name|super
operator|.
name|getInstruction
argument_list|()
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
name|getBI
argument_list|()
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
name|int
name|pos
parameter_list|)
block|{
comment|// Original code: i_position = bi.position = pos;
name|getBI
argument_list|()
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
name|int
name|offset
parameter_list|,
name|int
name|max_offset
parameter_list|)
block|{
name|int
name|x
init|=
name|getBI
argument_list|()
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
name|getBI
argument_list|()
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
name|InstructionHandle
name|ih
parameter_list|)
block|{
name|getBI
argument_list|()
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
name|InstructionHandle
name|old_ih
parameter_list|,
name|InstructionHandle
name|new_ih
parameter_list|)
block|{
name|getBI
argument_list|()
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
name|getBI
argument_list|()
operator|.
name|getTarget
argument_list|()
return|;
block|}
comment|/**       * Set new contents. Old instruction is disposed and may not be used anymore.      */
annotation|@
name|Override
comment|// This is only done in order to apply the additional type check; could be merged with super impl.
specifier|public
name|void
name|setInstruction
parameter_list|(
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
block|}
block|}
end_class

end_unit

