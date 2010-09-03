begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.   *  */
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

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|util
operator|.
name|ByteSequence
import|;
end_import

begin_comment
comment|/**   * Select - Abstract super class for LOOKUPSWITCH and TABLESWITCH instructions.  *   *<p>We use our super's<code>target</code> property as the default target.  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  * @see LOOKUPSWITCH  * @see TABLESWITCH  * @see InstructionList  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|Select
extends|extends
name|BranchInstruction
implements|implements
name|VariableLengthInstruction
implements|,
name|StackProducer
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|2806771744559217250L
decl_stmt|;
specifier|protected
name|int
index|[]
name|match
decl_stmt|;
comment|// matches, i.e., case 1: ...
specifier|protected
name|int
index|[]
name|indices
decl_stmt|;
comment|// target offsets
specifier|protected
name|InstructionHandle
index|[]
name|targets
decl_stmt|;
comment|// target objects in instruction list
specifier|protected
name|int
name|fixed_length
decl_stmt|;
comment|// fixed length defined by subclasses
specifier|protected
name|int
name|match_length
decl_stmt|;
comment|// number of cases
specifier|protected
name|int
name|padding
init|=
literal|0
decl_stmt|;
comment|// number of pad bytes for alignment
comment|/**      * Empty constructor needed for the Class.newInstance() statement in      * Instruction.readInstruction(). Not to be used otherwise.      */
name|Select
parameter_list|()
block|{
block|}
comment|/**      * (Match, target) pairs for switch.      * `Match' and `targets' must have the same length of course.      *      * @param match array of matching values      * @param targets instruction targets      * @param defaultTarget default instruction target      */
name|Select
parameter_list|(
name|short
name|opcode
parameter_list|,
name|int
index|[]
name|match
parameter_list|,
name|InstructionHandle
index|[]
name|targets
parameter_list|,
name|InstructionHandle
name|defaultTarget
parameter_list|)
block|{
name|super
argument_list|(
name|opcode
argument_list|,
name|defaultTarget
argument_list|)
expr_stmt|;
name|this
operator|.
name|targets
operator|=
name|targets
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|targets
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|notifyTarget
argument_list|(
literal|null
argument_list|,
name|targets
index|[
name|i
index|]
argument_list|,
name|this
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|match
operator|=
name|match
expr_stmt|;
if|if
condition|(
operator|(
name|match_length
operator|=
name|match
operator|.
name|length
operator|)
operator|!=
name|targets
operator|.
name|length
condition|)
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Match and target array have not the same length: Match length: "
operator|+
name|match
operator|.
name|length
operator|+
literal|" Target length: "
operator|+
name|targets
operator|.
name|length
argument_list|)
throw|;
block|}
name|indices
operator|=
operator|new
name|int
index|[
name|match_length
index|]
expr_stmt|;
block|}
comment|/**      * Since this is a variable length instruction, it may shift the following      * instructions which then need to update their position.      *      * Called by InstructionList.setPositions when setting the position for every      * instruction. In the presence of variable length instructions `setPositions'      * performs multiple passes over the instruction list to calculate the      * correct (byte) positions and offsets by calling this function.      *      * @param offset additional offset caused by preceding (variable length) instructions      * @param max_offset the maximum offset that may be caused by these instructions      * @return additional offset caused by possible change of this instruction's length      */
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
name|position
operator|+=
name|offset
expr_stmt|;
comment|// Additional offset caused by preceding SWITCHs, GOTOs, etc.
name|short
name|old_length
init|=
name|length
decl_stmt|;
comment|/* Alignment on 4-byte-boundary, + 1, because of tag byte.          */
name|padding
operator|=
operator|(
literal|4
operator|-
operator|(
operator|(
name|position
operator|+
literal|1
operator|)
operator|%
literal|4
operator|)
operator|)
operator|%
literal|4
expr_stmt|;
name|length
operator|=
operator|(
name|short
operator|)
operator|(
name|fixed_length
operator|+
name|padding
operator|)
expr_stmt|;
comment|// Update length
return|return
name|length
operator|-
name|old_length
return|;
block|}
comment|/**      * Dump instruction as byte code to stream out.      * @param out Output stream      */
specifier|public
name|void
name|dump
parameter_list|(
name|DataOutputStream
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|writeByte
argument_list|(
name|opcode
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|padding
condition|;
name|i
operator|++
control|)
block|{
name|out
operator|.
name|writeByte
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
name|index
operator|=
name|getTargetOffset
argument_list|()
expr_stmt|;
comment|// Write default target offset
name|out
operator|.
name|writeInt
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
comment|/**      * Read needed data (e.g. index) from file.      */
specifier|protected
name|void
name|initFromFile
parameter_list|(
name|ByteSequence
name|bytes
parameter_list|,
name|boolean
name|wide
parameter_list|)
throws|throws
name|IOException
block|{
name|padding
operator|=
operator|(
literal|4
operator|-
operator|(
name|bytes
operator|.
name|getIndex
argument_list|()
operator|%
literal|4
operator|)
operator|)
operator|%
literal|4
expr_stmt|;
comment|// Compute number of pad bytes
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|padding
condition|;
name|i
operator|++
control|)
block|{
name|bytes
operator|.
name|readByte
argument_list|()
expr_stmt|;
block|}
comment|// Default branch target common for both cases (TABLESWITCH, LOOKUPSWITCH)
name|index
operator|=
name|bytes
operator|.
name|readInt
argument_list|()
expr_stmt|;
block|}
comment|/**      * @return mnemonic for instruction      */
specifier|public
name|String
name|toString
parameter_list|(
name|boolean
name|verbose
parameter_list|)
block|{
name|StringBuffer
name|buf
init|=
operator|new
name|StringBuffer
argument_list|(
name|super
operator|.
name|toString
argument_list|(
name|verbose
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|verbose
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
name|match_length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|s
init|=
literal|"null"
decl_stmt|;
if|if
condition|(
name|targets
index|[
name|i
index|]
operator|!=
literal|null
condition|)
block|{
name|s
operator|=
name|targets
index|[
name|i
index|]
operator|.
name|getInstruction
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
name|buf
operator|.
name|append
argument_list|(
literal|"("
argument_list|)
operator|.
name|append
argument_list|(
name|match
index|[
name|i
index|]
argument_list|)
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|s
argument_list|)
operator|.
name|append
argument_list|(
literal|" = {"
argument_list|)
operator|.
name|append
argument_list|(
name|indices
index|[
name|i
index|]
argument_list|)
operator|.
name|append
argument_list|(
literal|"})"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|buf
operator|.
name|append
argument_list|(
literal|" ..."
argument_list|)
expr_stmt|;
block|}
return|return
name|buf
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Set branch target for `i'th case      */
specifier|public
name|void
name|setTarget
parameter_list|(
name|int
name|i
parameter_list|,
name|InstructionHandle
name|target
parameter_list|)
block|{
name|notifyTarget
argument_list|(
name|targets
index|[
name|i
index|]
argument_list|,
name|target
argument_list|,
name|this
argument_list|)
expr_stmt|;
name|targets
index|[
name|i
index|]
operator|=
name|target
expr_stmt|;
block|}
comment|/**      * @param old_ih old target      * @param new_ih new target      */
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
name|boolean
name|targeted
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|target
operator|==
name|old_ih
condition|)
block|{
name|targeted
operator|=
literal|true
expr_stmt|;
name|setTarget
argument_list|(
name|new_ih
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|targets
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|targets
index|[
name|i
index|]
operator|==
name|old_ih
condition|)
block|{
name|targeted
operator|=
literal|true
expr_stmt|;
name|setTarget
argument_list|(
name|i
argument_list|,
name|new_ih
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|targeted
condition|)
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Not targeting "
operator|+
name|old_ih
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return true, if ih is target of this instruction      */
specifier|public
name|boolean
name|containsTarget
parameter_list|(
name|InstructionHandle
name|ih
parameter_list|)
block|{
if|if
condition|(
name|target
operator|==
name|ih
condition|)
block|{
return|return
literal|true
return|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|targets
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|targets
index|[
name|i
index|]
operator|==
name|ih
condition|)
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
specifier|protected
name|Object
name|clone
parameter_list|()
throws|throws
name|CloneNotSupportedException
block|{
name|Select
name|copy
init|=
operator|(
name|Select
operator|)
name|super
operator|.
name|clone
argument_list|()
decl_stmt|;
name|copy
operator|.
name|match
operator|=
operator|(
name|int
index|[]
operator|)
name|match
operator|.
name|clone
argument_list|()
expr_stmt|;
name|copy
operator|.
name|indices
operator|=
operator|(
name|int
index|[]
operator|)
name|indices
operator|.
name|clone
argument_list|()
expr_stmt|;
name|copy
operator|.
name|targets
operator|=
operator|(
name|InstructionHandle
index|[]
operator|)
name|targets
operator|.
name|clone
argument_list|()
expr_stmt|;
return|return
name|copy
return|;
block|}
comment|/**      * Inform targets that they're not targeted anymore.      */
name|void
name|dispose
parameter_list|()
block|{
name|super
operator|.
name|dispose
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|targets
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|targets
index|[
name|i
index|]
operator|.
name|removeTargeter
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @return array of match indices      */
specifier|public
name|int
index|[]
name|getMatchs
parameter_list|()
block|{
return|return
name|match
return|;
block|}
comment|/**      * @return array of match target offsets      */
specifier|public
name|int
index|[]
name|getIndices
parameter_list|()
block|{
return|return
name|indices
return|;
block|}
comment|/**      * @return array of match targets      */
specifier|public
name|InstructionHandle
index|[]
name|getTargets
parameter_list|()
block|{
return|return
name|targets
return|;
block|}
block|}
end_class

end_unit

