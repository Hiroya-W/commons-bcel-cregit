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
comment|/**  * Equality of instructions isn't clearly to be defined. You might  * wish, for example, to compare whether instructions have the same  * meaning. E.g., whether two INVOKEVIRTUALs describe the same  * call.<br>The DEFAULT comparator however, considers two instructions  * to be equal if they have same opcode and point to the same indexes  * (if any) in the constant pool or the same local variable index. Branch  * instructions must have the same target.  *  * @see Instruction  * @version $Id$  */
end_comment

begin_interface
specifier|public
interface|interface
name|InstructionComparator
block|{
specifier|public
specifier|static
specifier|final
name|InstructionComparator
name|DEFAULT
init|=
operator|new
name|InstructionComparator
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Instruction
name|i1
parameter_list|,
name|Instruction
name|i2
parameter_list|)
block|{
if|if
condition|(
name|i1
operator|.
name|getOpcode
argument_list|()
operator|==
name|i2
operator|.
name|getOpcode
argument_list|()
condition|)
block|{
if|if
condition|(
name|i1
operator|instanceof
name|Select
condition|)
block|{
name|InstructionHandle
index|[]
name|t1
init|=
operator|(
operator|(
name|Select
operator|)
name|i1
operator|)
operator|.
name|getTargets
argument_list|()
decl_stmt|;
name|InstructionHandle
index|[]
name|t2
init|=
operator|(
operator|(
name|Select
operator|)
name|i2
operator|)
operator|.
name|getTargets
argument_list|()
decl_stmt|;
if|if
condition|(
name|t1
operator|.
name|length
operator|==
name|t2
operator|.
name|length
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|t1
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|t1
index|[
name|i
index|]
operator|!=
name|t2
index|[
name|i
index|]
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
return|return
literal|true
return|;
block|}
block|}
if|else if
condition|(
name|i1
operator|instanceof
name|BranchInstruction
condition|)
block|{
return|return
operator|(
operator|(
name|BranchInstruction
operator|)
name|i1
operator|)
operator|.
name|getTarget
argument_list|()
operator|==
operator|(
operator|(
name|BranchInstruction
operator|)
name|i2
operator|)
operator|.
name|getTarget
argument_list|()
return|;
block|}
if|else if
condition|(
name|i1
operator|instanceof
name|ConstantPushInstruction
condition|)
block|{
return|return
operator|(
operator|(
name|ConstantPushInstruction
operator|)
name|i1
operator|)
operator|.
name|getValue
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|ConstantPushInstruction
operator|)
name|i2
operator|)
operator|.
name|getValue
argument_list|()
argument_list|)
return|;
block|}
if|else if
condition|(
name|i1
operator|instanceof
name|IndexedInstruction
condition|)
block|{
return|return
operator|(
operator|(
name|IndexedInstruction
operator|)
name|i1
operator|)
operator|.
name|getIndex
argument_list|()
operator|==
operator|(
operator|(
name|IndexedInstruction
operator|)
name|i2
operator|)
operator|.
name|getIndex
argument_list|()
return|;
block|}
if|else if
condition|(
name|i1
operator|instanceof
name|NEWARRAY
condition|)
block|{
return|return
operator|(
operator|(
name|NEWARRAY
operator|)
name|i1
operator|)
operator|.
name|getTypecode
argument_list|()
operator|==
operator|(
operator|(
name|NEWARRAY
operator|)
name|i2
operator|)
operator|.
name|getTypecode
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
block|}
decl_stmt|;
name|boolean
name|equals
parameter_list|(
name|Instruction
name|i1
parameter_list|,
name|Instruction
name|i2
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

