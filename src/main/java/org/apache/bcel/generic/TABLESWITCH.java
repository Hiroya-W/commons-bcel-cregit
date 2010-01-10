begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright  2000-2009 The Apache Software Foundation  *  *  Licensed under the Apache License, Version 2.0 (the "License");   *  you may not use this file except in compliance with the License.  *  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.   *  */
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
comment|/**   * TABLESWITCH - Switch within given range of values, i.e., low..high  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  * @see SWITCH  */
end_comment

begin_class
specifier|public
class|class
name|TABLESWITCH
extends|extends
name|Select
block|{
comment|/**      * Empty constructor needed for the Class.newInstance() statement in      * Instruction.readInstruction(). Not to be used otherwise.      */
name|TABLESWITCH
parameter_list|()
block|{
block|}
comment|/**      * @param match sorted array of match values, match[0] must be low value,       * match[match_length - 1] high value      * @param targets where to branch for matched values      * @param defaultTarget default branch      */
specifier|public
name|TABLESWITCH
parameter_list|(
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
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Constants
operator|.
name|TABLESWITCH
argument_list|,
name|match
argument_list|,
name|targets
argument_list|,
name|defaultTarget
argument_list|)
expr_stmt|;
name|length
operator|=
operator|(
name|short
operator|)
operator|(
literal|13
operator|+
name|match_length
operator|*
literal|4
operator|)
expr_stmt|;
comment|/* Alignment remainder assumed          * 0 here, until dump time */
name|fixed_length
operator|=
name|length
expr_stmt|;
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
name|super
operator|.
name|dump
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|int
name|low
init|=
operator|(
name|match_length
operator|>
literal|0
operator|)
condition|?
name|match
index|[
literal|0
index|]
else|:
literal|0
decl_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|low
argument_list|)
expr_stmt|;
name|int
name|high
init|=
operator|(
name|match_length
operator|>
literal|0
operator|)
condition|?
name|match
index|[
name|match_length
operator|-
literal|1
index|]
else|:
literal|0
decl_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|high
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
name|match_length
condition|;
name|i
operator|++
control|)
block|{
name|out
operator|.
name|writeInt
argument_list|(
name|indices
index|[
name|i
index|]
operator|=
name|getTargetOffset
argument_list|(
name|targets
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|super
operator|.
name|initFromFile
argument_list|(
name|bytes
argument_list|,
name|wide
argument_list|)
expr_stmt|;
name|int
name|low
init|=
name|bytes
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|int
name|high
init|=
name|bytes
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|match_length
operator|=
name|high
operator|-
name|low
operator|+
literal|1
expr_stmt|;
name|fixed_length
operator|=
operator|(
name|short
operator|)
operator|(
literal|13
operator|+
name|match_length
operator|*
literal|4
operator|)
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
name|match
operator|=
operator|new
name|int
index|[
name|match_length
index|]
expr_stmt|;
name|indices
operator|=
operator|new
name|int
index|[
name|match_length
index|]
expr_stmt|;
name|targets
operator|=
operator|new
name|InstructionHandle
index|[
name|match_length
index|]
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
name|match_length
condition|;
name|i
operator|++
control|)
block|{
name|match
index|[
name|i
index|]
operator|=
name|low
operator|+
name|i
expr_stmt|;
name|indices
index|[
name|i
index|]
operator|=
name|bytes
operator|.
name|readInt
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Call corresponding visitor method(s). The order is:      * Call visitor methods of implemented interfaces first, then      * call methods according to the class hierarchy in descending order,      * i.e., the most specific visitXXX() call comes last.      *      * @param v Visitor object      */
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
name|visitVariableLengthInstruction
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
name|visitBranchInstruction
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitSelect
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|v
operator|.
name|visitTABLESWITCH
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

