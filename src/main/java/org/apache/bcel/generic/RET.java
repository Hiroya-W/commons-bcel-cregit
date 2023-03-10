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
comment|/**  * RET - Return from subroutine  *  *<PRE>  * Stack: ... -&gt; ...  *</PRE>  */
end_comment

begin_class
specifier|public
class|class
name|RET
extends|extends
name|Instruction
implements|implements
name|IndexedInstruction
implements|,
name|TypedInstruction
block|{
specifier|private
name|boolean
name|wide
decl_stmt|;
specifier|private
name|int
name|index
decl_stmt|;
comment|// index to local variable containg the return address
comment|/**      * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.      */
name|RET
parameter_list|()
block|{
block|}
specifier|public
name|RET
parameter_list|(
specifier|final
name|int
name|index
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
name|RET
argument_list|,
operator|(
name|short
operator|)
literal|2
argument_list|)
expr_stmt|;
name|setIndex
argument_list|(
name|index
argument_list|)
expr_stmt|;
comment|// May set wide as side effect
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
name|visitRET
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * Dump instruction as byte code to stream out.      *      * @param out Output stream      */
annotation|@
name|Override
specifier|public
name|void
name|dump
parameter_list|(
specifier|final
name|DataOutputStream
name|out
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|wide
condition|)
block|{
name|out
operator|.
name|writeByte
argument_list|(
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|WIDE
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|writeByte
argument_list|(
name|super
operator|.
name|getOpcode
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|wide
condition|)
block|{
name|out
operator|.
name|writeShort
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|out
operator|.
name|writeByte
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @return index of local variable containg the return address      */
annotation|@
name|Override
specifier|public
specifier|final
name|int
name|getIndex
parameter_list|()
block|{
return|return
name|index
return|;
block|}
comment|/**      * @return return address type      */
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
name|ReturnaddressType
operator|.
name|NO_TARGET
return|;
block|}
comment|/**      * Read needed data (e.g. index) from file.      */
annotation|@
name|Override
specifier|protected
name|void
name|initFromFile
parameter_list|(
specifier|final
name|ByteSequence
name|bytes
parameter_list|,
specifier|final
name|boolean
name|wide
parameter_list|)
throws|throws
name|IOException
block|{
name|this
operator|.
name|wide
operator|=
name|wide
expr_stmt|;
if|if
condition|(
name|wide
condition|)
block|{
name|index
operator|=
name|bytes
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|super
operator|.
name|setLength
argument_list|(
literal|4
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|index
operator|=
name|bytes
operator|.
name|readUnsignedByte
argument_list|()
expr_stmt|;
name|super
operator|.
name|setLength
argument_list|(
literal|2
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Set index of local variable containg the return address      */
annotation|@
name|Override
specifier|public
specifier|final
name|void
name|setIndex
parameter_list|(
specifier|final
name|int
name|n
parameter_list|)
block|{
if|if
condition|(
name|n
operator|<
literal|0
condition|)
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Negative index value: "
operator|+
name|n
argument_list|)
throw|;
block|}
name|index
operator|=
name|n
expr_stmt|;
name|setWide
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|setWide
parameter_list|()
block|{
name|wide
operator|=
name|index
operator|>
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
operator|.
name|MAX_BYTE
expr_stmt|;
if|if
condition|(
name|wide
condition|)
block|{
name|super
operator|.
name|setLength
argument_list|(
literal|4
argument_list|)
expr_stmt|;
comment|// Including the wide byte
block|}
else|else
block|{
name|super
operator|.
name|setLength
argument_list|(
literal|2
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @return mnemonic for instruction      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|(
specifier|final
name|boolean
name|verbose
parameter_list|)
block|{
return|return
name|super
operator|.
name|toString
argument_list|(
name|verbose
argument_list|)
operator|+
literal|" "
operator|+
name|index
return|;
block|}
block|}
end_class

end_unit

