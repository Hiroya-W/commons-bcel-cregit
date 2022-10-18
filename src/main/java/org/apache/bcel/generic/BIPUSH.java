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
comment|/**  * BIPUSH - Push byte on stack  *  *<PRE>  * Stack: ... -&gt; ..., value  *</PRE>  */
end_comment

begin_class
specifier|public
class|class
name|BIPUSH
extends|extends
name|Instruction
implements|implements
name|ConstantPushInstruction
block|{
specifier|private
name|byte
name|b
decl_stmt|;
comment|/**      * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.      */
name|BIPUSH
parameter_list|()
block|{
block|}
comment|/**      * Push byte on stack      */
specifier|public
name|BIPUSH
parameter_list|(
specifier|final
name|byte
name|b
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
name|BIPUSH
argument_list|,
operator|(
name|short
operator|)
literal|2
argument_list|)
expr_stmt|;
name|this
operator|.
name|b
operator|=
name|b
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
name|visitBIPUSH
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * Dump instruction as byte code to stream out.      */
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
name|super
operator|.
name|dump
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeByte
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Type.BYTE      */
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
name|BYTE
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
name|Integer
operator|.
name|valueOf
argument_list|(
name|b
argument_list|)
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
name|super
operator|.
name|setLength
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|b
operator|=
name|bytes
operator|.
name|readByte
argument_list|()
expr_stmt|;
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
name|b
return|;
block|}
block|}
end_class

end_unit

